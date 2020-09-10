/*
 *  Copyright (C) 2020 Tortillum / Neosama
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.tortillum.salome.recognizer;

import com.tortillum.salome.other.Duration;
import com.tortillum.salome.signal.INFO_SIGNAL;
import com.tortillum.salome.tool.T_FFT;
import com.tortillum.salome.tool.T_SFFT;
import com.tortillum.salome.wavReader.Reader;
import com.tortillum.salome.wavsplitter.WavFile;
import com.tortillum.salome.wavsplitter.WavFileException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Recognizer {
	private T_FFT t_fft;
	private T_SFFT t_sfft;

	private Score[] scores;
	private Duration[] durations;

	private String filePathWav = "";
	private String tmpResampledFilePath = "";
	private String tmpSplitFilePath = "";

	private boolean sampleRateChanged;

	private int top = 1024;

	private String result = "";

	public Recognizer(String pFilePathWav, int typeMode, int typeFrequencies, boolean showDBG) throws IOException, WavFileException {
		filePathWav = pFilePathWav;

		if(!filePathWav.toLowerCase().contains(".wav")) {
			System.out.println("[ERROR] Only WAV file !");
			result = "[ERROR] Only WAV file !";
			return;
		}

		// Init Scores
		scores = new Score[INFO_SIGNAL.NB_SIGNALS];
		scores[INFO_SIGNAL.ID_RTTY_85_commercial] = new Score(INFO_SIGNAL.NAME_RTTY_85_commercial, INFO_SIGNAL.TYPE_MODE_RTTY, INFO_SIGNAL.TYPE_FREQUENCIES_RTTY);
		scores[INFO_SIGNAL.ID_RTTY_170_commercial] = new Score(INFO_SIGNAL.NAME_RTTY_170_commercial, INFO_SIGNAL.TYPE_MODE_RTTY, INFO_SIGNAL.TYPE_FREQUENCIES_RTTY);
		scores[INFO_SIGNAL.ID_RTTY_170_amateurs] = new Score(INFO_SIGNAL.NAME_RTTY_170_amateurs, INFO_SIGNAL.TYPE_MODE_RTTY, INFO_SIGNAL.TYPE_FREQUENCIES_RTTY);
		scores[INFO_SIGNAL.ID_RTTY_450_commercial] = new Score(INFO_SIGNAL.NAME_RTTY_450_commercial, INFO_SIGNAL.TYPE_MODE_RTTY, INFO_SIGNAL.TYPE_FREQUENCIES_RTTY);
		scores[INFO_SIGNAL.ID_RTTY_850_commercial] = new Score(INFO_SIGNAL.NAME_RTTY_850_commercial, INFO_SIGNAL.TYPE_MODE_RTTY, INFO_SIGNAL.TYPE_FREQUENCIES_RTTY);
		scores[INFO_SIGNAL.ID_STANAG_4285_GEN] = new Score(INFO_SIGNAL.NAME_STANAG_4285_GEN, INFO_SIGNAL.TYPE_MODE_STANAG_4285, INFO_SIGNAL.TYPE_FREQUENCIES_STANAG_4285);
		scores[INFO_SIGNAL.ID_STANAG_4285_IDLE] = new Score(INFO_SIGNAL.NAME_STANAG_4285_IDLE, INFO_SIGNAL.TYPE_MODE_STANAG_4285, INFO_SIGNAL.TYPE_FREQUENCIES_STANAG_4285);
		scores[INFO_SIGNAL.ID_STANAG_4285_TFC] = new Score(INFO_SIGNAL.NAME_STANAG_4285_TFC, INFO_SIGNAL.TYPE_MODE_STANAG_4285, INFO_SIGNAL.TYPE_FREQUENCIES_STANAG_4285);
		scores[INFO_SIGNAL.ID_STANAG_4285_8PSK] = new Score(INFO_SIGNAL.NAME_STANAG_4285_8PSK, INFO_SIGNAL.TYPE_MODE_STANAG_4285, INFO_SIGNAL.TYPE_FREQUENCIES_STANAG_4285);
		scores[INFO_SIGNAL.ID_STANAG_4285_TS3000] = new Score(INFO_SIGNAL.NAME_STANAG_4285_TS3000, INFO_SIGNAL.TYPE_MODE_STANAG_4285, INFO_SIGNAL.TYPE_FREQUENCIES_STANAG_4285);
		scores[INFO_SIGNAL.ID_FT8] = new Score(INFO_SIGNAL.NAME_FT8, INFO_SIGNAL.TYPE_MODE_FT8, INFO_SIGNAL.TYPE_FREQUENCIES_FT8);
		scores[INFO_SIGNAL.ID_TETRAPOL] = new Score(INFO_SIGNAL.NAME_TETRAPOL, INFO_SIGNAL.TYPE_MODE_TETRAPOL, INFO_SIGNAL.TYPE_FREQUENCIES_TETRAPOL);
		scores[INFO_SIGNAL.ID_ALE_400] = new Score(INFO_SIGNAL.NAME_ALE_400, INFO_SIGNAL.TYPE_MODE_ALE_400, INFO_SIGNAL.TYPE_FREQUENCIES_ALE_400);
		scores[INFO_SIGNAL.ID_FT4] = new Score(INFO_SIGNAL.NAME_FT4, INFO_SIGNAL.TYPE_MODE_FT4, INFO_SIGNAL.TYPE_FREQUENCIES_FT4);

		// Init Durations
		durations = new Duration[INFO_SIGNAL.NB_DURATIONS];

		// FT8
		durations[INFO_SIGNAL.DURATION_ID_FT8_0475] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_0515] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_0555] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_0775] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_0815] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_0860] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_0905] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_0945] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_0990] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1035] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1075] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1120] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1145] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1160] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1205] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1245] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1290] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1335] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1375] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1420] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1465] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1505] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1550] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1590] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1635] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1680] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1720] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1765] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1810] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1850] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1860] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1895] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1935] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_1980] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_2025] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_2065] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_2110] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_2155] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_2195] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_2240] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_2265] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_2280] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_2325] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_2455] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_2495] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_2540] = new Duration(INFO_SIGNAL.NAME_FT8, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT8_2585] = new Duration(INFO_SIGNAL.NAME_FT8, 2);

		// FT4
		durations[INFO_SIGNAL.DURATION_ID_FT4_0345] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_0385] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_0430] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_0475] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_0515] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_0555] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_0600] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_0645] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_0690] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_0730] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_0775] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_0815] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_0860] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_0905] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_0945] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_0990] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_1035] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_1075] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_1120] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_1160] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_1205] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_1245] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_1290] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_1335] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_1375] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_1420] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_1465] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_1505] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_1550] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_1590] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_1635] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_1680] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_1720] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_1765] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_1810] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_1850] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_1895] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_1935] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_1980] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_2025] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_2065] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_2110] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_2155] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_2195] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_2240] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_2280] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_2325] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_2410] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_2455] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_2495] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_2540] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_2585] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_2627] = new Duration(INFO_SIGNAL.NAME_FT4, 2);
		durations[INFO_SIGNAL.DURATION_ID_FT4_2670] = new Duration(INFO_SIGNAL.NAME_FT4, 2);

		// Generate TMP paths
		File file = new File("resampled_tmpSignalID.wav");
		tmpResampledFilePath = file.getAbsolutePath();

		file = new File("split_tmpSignalID.wav");
		tmpSplitFilePath = file.getAbsolutePath();

		// Clean TMP files
		new File(tmpResampledFilePath).delete();
		new File(tmpSplitFilePath).delete();

		Reader checkFile = new Reader(filePathWav, false);

		if (checkFile.checkDurationFile(30)) {
			if(showDBG)
				System.out.println("[DBG] checkDurationFile OK");

			// Check SampleRate if 44100
			sampleRateChanged = false;
			if (checkFile.checkSR()) {
				System.out.println("[ERROR] Samplerate of the WAV file is not 44100 Hz!");
				result = "[ERROR] Samplerate of the WAV file is not 44100 Hz!";
				return;
			} else
				if(showDBG)
					System.out.println("[DBG] checkSR OK");

			// Split
			if (checkFile.checkSplitFiles()) {
				if(showDBG)
					System.out.println("[DBG] SPLIT START");
				WavFile inputWavFile;

				if (sampleRateChanged)
					inputWavFile = WavFile.openWavFile(new File(tmpResampledFilePath));
				else
					inputWavFile = WavFile.openWavFile(new File(filePathWav));

				int numChannels = inputWavFile.getNumChannels();
				int maxFramesPerFile = (int) inputWavFile.getSampleRate() * 5000 / 1000;
				double[] buffer = new double[maxFramesPerFile * numChannels];

				int framesRead;
				do {
					framesRead = inputWavFile.readFrames(buffer, maxFramesPerFile);
					if(showDBG)
						System.out.println("[DBG] frameRead (" + framesRead + ") START");

					WavFile outputWavFile = WavFile.newWavFile(
							new File(tmpSplitFilePath),
							inputWavFile.getNumChannels(),
							framesRead,
							inputWavFile.getValidBits(),
							inputWavFile.getSampleRate());

					outputWavFile.writeFrames(buffer, framesRead);
					outputWavFile.close();

					Reader checkFile2 = new Reader(tmpSplitFilePath, false);

					if (checkFile2.checkFile()) {
						if(showDBG)
							System.out.println("[DBG] checkFile2 OK");

						// FFT & SFFT
						t_fft = new T_FFT(tmpSplitFilePath, scores);
						t_sfft = new T_SFFT(tmpSplitFilePath, scores, durations);
					} else {
						System.out.println("[ERROR] checkFile2 KO (DURATION < 5 seconds)");
					}

					// Delete tmp split file
					new File(tmpSplitFilePath).delete();
				} while (framesRead != 0);
				inputWavFile.close();
				if(showDBG)
					System.out.println("[DBG] SPLIT END");
			} else {
				if(showDBG)
					System.out.println("[DBG] NOSPLIT START");

				Reader checkFile2;
				if (sampleRateChanged)
					checkFile2 = new Reader(tmpResampledFilePath, false);
				else
					checkFile2 = new Reader(filePathWav, false);

				if (checkFile2.checkFile()) {
					if(showDBG)
						System.out.println("[DBG] checkFile2 OK");

					// FFT & SFFT
					if (sampleRateChanged) {                    	
						t_fft = new T_FFT(tmpResampledFilePath, scores);
						t_sfft = new T_SFFT(tmpResampledFilePath, scores, durations);
					} else {
						t_fft = new T_FFT(filePathWav, scores);
						t_sfft = new T_SFFT(filePathWav, scores, durations);
					}
				} else {
					System.out.println("[ERROR] checkFile2 KO (DURATION < 5 seconds)");
				}

				if(showDBG)
					System.out.println("[DBG] NOSPLIT END");
			}
		} else {
			System.out.println("[ERROR] MAX DURATION FILE 30 SECONDS!");
			result = "[ERROR] MAX DURATION FILE 30 SECONDS!";
			return;
		}

		// HERE DEDUCTION
		String nameMaxFFT = "null";
		String nameMaxSFFT = "null";
		String nameAllDuration = "null";
		String nameMaxDuration = "null";
		String nameAllFFT1 = "null"; // TMP name
		String nameAllFFT2 = "null"; // TMP name

		int maxFFT = 0;
		int maxSFFT = 0;
		int allDuration = 0;
		int maxDuration = 0;

		// Set duration score
		scores[INFO_SIGNAL.ID_FT8].setScore_maxDuration(new Duration().FT8_check(durations)); // FT8
		scores[INFO_SIGNAL.ID_FT4].setScore_maxDuration(new Duration().FT4_check(durations)); // FT4

		for (Score score : scores) {
			if(typeMode == score.getTypeMode() && typeFrequencies == score.getTypeFrequencies() || typeMode == score.getTypeMode() && INFO_SIGNAL.TYPE_FREQUENCIES_ALL == score.getTypeFrequencies()) {
				if(showDBG)
					System.out.println("[RESULT]CHECK (" + pFilePathWav + ") -> [" + score.getName() + " + (" + score.getTypeMode() + "/" + score.getTypeMode() + ")] = (FFT) : " + score.getScore_FFT() + " | (SFFT) : " + score.getScore_SFFT() + " | (allDuration) : " + score.getScore_allDuration() + " | (maxDuration) : " + score.getScore_maxDuration());

				if(maxFFT < score.getScore_FFT()) {
					maxFFT = score.getScore_FFT();
					nameMaxFFT = score.getName();

					if(score.getScore_FFT() != 0 && score.getScore_SFFT() != 0)
						nameAllFFT1 = score.getName();
				}

				if(maxSFFT < score.getScore_SFFT()) {
					maxSFFT = score.getScore_SFFT();
					nameMaxSFFT = score.getName();

					if(score.getScore_FFT() != 0 && score.getScore_SFFT() != 0)
						nameAllFFT2 = score.getName();
				}

				if(allDuration < score.getScore_allDuration()) {
					allDuration = score.getScore_allDuration();
					nameAllDuration = score.getName();
				}

				if(maxDuration < score.getScore_maxDuration()) {
					maxDuration = score.getScore_maxDuration();
					nameMaxDuration = score.getName();
				}

			}
		}

		if(nameMaxFFT.contains("RTTY")) {
			result = nameMaxFFT;
		} else if(!nameAllFFT1.contentEquals("null")) {
			result = nameAllFFT1;
		} else if(!nameAllFFT2.contentEquals("null")) {
			result = nameAllFFT2;
		} else if(scores[INFO_SIGNAL.ID_FT8].getScore_maxDuration() > scores[INFO_SIGNAL.ID_FT4].getScore_maxDuration() && checkFile.getDuration() > 5) {
			result = INFO_SIGNAL.NAME_FT8;
		} else if(scores[INFO_SIGNAL.ID_FT8].getScore_maxDuration() < scores[INFO_SIGNAL.ID_FT4].getScore_maxDuration() && checkFile.getDuration() > 5) {
			result = INFO_SIGNAL.NAME_FT4;
		} else if(nameMaxFFT.contentEquals("null") && !nameMaxSFFT.contentEquals("null")) {
			result = nameMaxSFFT;

			if(nameAllFFT1.contentEquals("null") && nameAllFFT2.contentEquals("null"))
				result += " (low confidence level)";
		} else if(!nameMaxFFT.contentEquals("null") && nameMaxSFFT.contentEquals("null")) {
			result = nameMaxFFT;

			if(nameAllFFT1.contentEquals("null") && nameAllFFT2.contentEquals("null"))
				result += " (low confidence level)";
		} else if(nameMaxFFT.contains(nameMaxSFFT) && !nameMaxFFT.contentEquals("null")) {
			result = nameMaxFFT + " / " + nameMaxSFFT;
		} else {
			result = "[DEBUG INFO]\nnameMaxFFT = " + nameMaxFFT + "\nnameMaxSFFT = " + nameMaxSFFT + "\nnameAllDuration = " + nameAllDuration + "\nnameMaxDuration = " + nameMaxDuration + "\nnameAllFFT1 = " + nameAllFFT1 + "\nnameAllFFT2 = " + nameAllFFT2; 
		}

	}

	public String getResult() {
		return result;
	}
}
