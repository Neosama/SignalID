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

import com.tortillum.salome.signal.INFO_SIGNAL;
import com.tortillum.salome.tool.T_FFT;
import com.tortillum.salome.tool.T_SFFT;
import com.tortillum.salome.wavReader.Reader;
import com.tortillum.salome.wavsplitter.WavFile;
import com.tortillum.salome.wavsplitter.WavFileException;

import java.io.File;
import java.io.IOException;

import vavi.sound.pcm.resampling.ssrc.SSRC;

public class Recognizer {
    private T_FFT t_fft;
    private T_SFFT t_sfft;

    private Score[] scores;

    private String filePathWav = "";
    private String tmpResampledFilePath = "";
    private String tmpSplitFilePath = "";

    private boolean sampleRateChanged;

    private int top = 1024;
    
    private String result = "";

    public Recognizer(String pFilePathWav, boolean showDBG) throws IOException, WavFileException {
        filePathWav = pFilePathWav;
        
        if(!filePathWav.toLowerCase().contains(".wav")) {
        	System.out.println("[ERROR] Only WAV file !");
        	return;
        }

        // Init Scores
        scores = new Score[INFO_SIGNAL.NB_SIGNALS];
        scores[INFO_SIGNAL.ID_RTTY_85_commercial] = new Score(INFO_SIGNAL.NAME_RTTY_85_commercial);
        scores[INFO_SIGNAL.ID_RTTY_170_commercial] = new Score(INFO_SIGNAL.NAME_RTTY_170_commercial);
        scores[INFO_SIGNAL.ID_RTTY_170_amateurs] = new Score(INFO_SIGNAL.NAME_RTTY_170_amateurs);
        scores[INFO_SIGNAL.ID_RTTY_450_commercial] = new Score(INFO_SIGNAL.NAME_RTTY_450_commercial);
        scores[INFO_SIGNAL.ID_RTTY_850_commercial] = new Score(INFO_SIGNAL.NAME_RTTY_850_commercial);
        scores[INFO_SIGNAL.ID_STANAG_4285_GEN] = new Score(INFO_SIGNAL.NAME_STANAG_4285_GEN);
        scores[INFO_SIGNAL.ID_STANAG_4285_IDLE] = new Score(INFO_SIGNAL.NAME_STANAG_4285_IDLE);
        scores[INFO_SIGNAL.ID_STANAG_4285_TFC] = new Score(INFO_SIGNAL.NAME_STANAG_4285_TFC);
        scores[INFO_SIGNAL.ID_STANAG_4285_8PSK] = new Score(INFO_SIGNAL.NAME_STANAG_4285_8PSK);
        scores[INFO_SIGNAL.ID_STANAG_4285_TS3000] = new Score(INFO_SIGNAL.NAME_STANAG_4285_TS3000);
        scores[INFO_SIGNAL.ID_FT8] = new Score(INFO_SIGNAL.NAME_FT8);
        scores[INFO_SIGNAL.ID_TETRAPOL] = new Score(INFO_SIGNAL.NAME_TETRAPOL);
        scores[INFO_SIGNAL.ID_ALE_400] = new Score(INFO_SIGNAL.NAME_ALE_400);

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

            // Change SampleRate for 44100
            sampleRateChanged = false;
            if (checkFile.checkSR()) {
            	if(showDBG)
            		System.out.println("[DBG] SAMPLERATE CHANGE START");
                new SSRC(new String[]{"--rate", "44100", filePathWav, tmpResampledFilePath});
                checkFile = new Reader(tmpResampledFilePath, false);
                sampleRateChanged = true;
            	if(showDBG)
            		System.out.println("[DBG] SampleRate changed");
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
                        t_sfft = new T_SFFT(tmpSplitFilePath, scores);
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
                        t_sfft = new T_SFFT(tmpResampledFilePath, scores);
                    } else {
                        t_fft = new T_FFT(filePathWav, scores);
                        t_sfft = new T_SFFT(filePathWav, scores);
                    }
                } else {
                    System.out.println("[ERROR] checkFile2 KO (DURATION < 5 seconds)");
                }

            	if(showDBG)
            		System.out.println("[DBG] NOSPLIT END");
            }
        } else {
            System.out.println("[ERROR] MAX DURATION FILE 30 SECONDS!");
        }

        // HERE DEDUCTION
        String nameMaxFFT = "";
        String nameMaxSFFT = "";
        int maxFFT = 0;
        int maxSFFT = 0;
        
        for (Score score : scores) {
        	if(showDBG)
        		System.out.println("[RESULT]CHECK (" + pFilePathWav + ") -> [" + score.getName() + "] = (FFT) : " + score.getScore_FFT() + " | (SFFT) : " + score.getScore_SFFT());
        
            if(maxFFT < score.getScore_FFT()) {
            	maxFFT = score.getScore_FFT();
            	nameMaxFFT = score.getName();
            }
            
            if(maxSFFT < score.getScore_SFFT()) {
            	maxSFFT = score.getScore_SFFT();
            	nameMaxSFFT = score.getName();
            }
            
            result = "Result (FFT) = " + nameMaxFFT + " (" + maxFFT + ")\nResult (SFFT) = " + nameMaxSFFT + " (" + maxSFFT + ")";
            
        }
    }
    
    public String getResult() {
    	return result;
    }
}
