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

package console;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import org.jtransforms.fft.DoubleFFT_1D;

import salome.Pair;
import salome.Range;
import testers.RangeTones;
import wavReader.Reader;
import window.Hamming;

public class Main {

	public static void main(String[] args) throws IOException {

		System.out.println("Shelp");

		boolean modeNew = true;
		boolean modeTest = !modeNew;

		// Paths WAV (SP=44100, 16B_PCM, 5 seconds)
		String pathWavA = "C:\\Users\\Rafael\\Desktop\\ABC_A.wav";
		String pathWavB = "C:\\Users\\Rafael\\Desktop\\ABC_B.wav"; // Only for modeTest
		String pathCmp = "C:\\Users\\Rafael\\Desktop\\out_A.txt"; // Only for modeTest
		
		if(modeNew) {
			int marginCompareFqs = 5;

			String[] pathsWav = {pathWavA, pathWavB};

			for(int x = 0; x < pathsWav.length; x++) {
				String pathWav = pathsWav[x];
				String pathTextOut = pathWav.substring(0, pathWav.lastIndexOf('.')) + "_fqs.txt";

				String tmp = "filename = " + pathWav + System.lineSeparator();
				Files.write(Paths.get(pathTextOut), tmp.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

				Reader wavReader = new Reader(pathWav, false);

				double[] pcmData = wavReader.getByteArray();
				System.out.println("FILENAME = " + pathWav + "\nSIZE = " + pcmData.length + "\nSR = " + (long)wavReader.getSR());

				double[] tmp_pcmData = pcmData;

				int n_fft_65536 = 1024*64; // 65536

				if(tmp_pcmData.length < n_fft_65536) {
					System.out.println("File too small! [65536] (" + tmp_pcmData.length + " < " + n_fft_65536 + ")");
					return;
				}

				Hamming windowHamming_65536 = new Hamming();
				double[] hammingData_65536 = windowHamming_65536.toHammingWindow(tmp_pcmData, tmp_pcmData.length);

				DoubleFFT_1D fft_fsk = new DoubleFFT_1D(n_fft_65536);
				fft_fsk.realForward(hammingData_65536);

				// Extract Real part
				double[] result_65536 = new double[hammingData_65536.length / 2];
				for(int s = 0; s < result_65536.length; s++) {
					//result[s] = Math.abs(signal[2*s]);
					double re = hammingData_65536[s * 2];
					double im = hammingData_65536[s * 2 + 1];
					result_65536[s] = (double) Math.sqrt(re * re + im * im) / result_65536.length;
				}

				// frequency  = index * SamplingFrequency / N_FFT

				Pair[] frequenciesArray_65536 = new Pair[result_65536.length];

				//fill the array
				for(int i = 0; i < result_65536.length; i++) {
					frequenciesArray_65536[i] = new Pair(i, result_65536[i]);
				}
				Arrays.sort(frequenciesArray_65536);

				for(int i = 0; i <= 64; i++) {
					int fq = frequenciesArray_65536[i].index * (int)wavReader.getSR() / n_fft_65536;

					tmp = fq + System.lineSeparator();
					Files.write(Paths.get(pathTextOut), tmp.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
				}
			}


			String pathFqsA = pathsWav[0].substring(0, pathsWav[0].lastIndexOf('.')) + "_fqs.txt";
			String pathFqsB = pathsWav[1].substring(0, pathsWav[1].lastIndexOf('.')) + "_fqs.txt";

			String tmp = "FILES = " + pathsWav[0].substring(0, pathsWav[0].lastIndexOf('.')) + "/" + pathsWav[1].substring(0, pathsWav[1].lastIndexOf('.')) + System.lineSeparator();
			Files.write(Paths.get(pathCmp), tmp.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

			String contentA = Files.readString(Paths.get(pathFqsA));
			String contentB = Files.readString(Paths.get(pathFqsB));

			int[] arrayA = new int[128];
			int[] arrayB = new int[128];

			// Parsing
			String[] linesA = contentA.split(System.getProperty("line.separator"));
			String[] linesB = contentB.split(System.getProperty("line.separator"));

			for(int i = 1; i < linesA.length-1; i++) {
				arrayA[i] = Integer.valueOf(linesA[i]);
				arrayB[i] = Integer.valueOf(linesB[i]);
			}

			if(marginCompareFqs <= 0) {
				for(int i = 0; i < arrayA.length; i++) {
					for(int j = 0; j < arrayB.length; j++) {
						if(arrayA[i] == arrayB[j]) {
							if(arrayA[i] != 0) {
								tmp = "[" + i + "] [" + j + "] = " + arrayA[i] + System.lineSeparator();
								Files.write(Paths.get(pathCmp), tmp.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
							}
						}
					}
				}
			} else {
				for(int i = 0; i < arrayA.length; i++) {
					for(int j = 0; j < arrayB.length; j++) {
						Range range = new Range(arrayB[j], marginCompareFqs, 0);
						for(int k = 0; k < range.get().length; k++) {
							if(arrayA[i] == range.get()[k]) {
								if(k > 0) {
									if(arrayA[i] != 0) {
										tmp = "(diff=" + k + ")[" + i + "] [" + j + "] = " + arrayA[i] + System.lineSeparator();
										Files.write(Paths.get(pathCmp), tmp.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
									}
								} else {
									if(arrayA[i] != 0) {
										tmp = "[" + i + "] [" + j + "] = " + arrayA[i] + System.lineSeparator();
										Files.write(Paths.get(pathCmp), tmp.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
									}
								}
							}
						}
					}
				}
			}

			Files.deleteIfExists(Paths.get(pathFqsA)); 
			Files.deleteIfExists(Paths.get(pathFqsB)); 

			System.out.println("Success");
		}

		if(modeTest) {
			int fqA = 1105;
			int fqB = 955;
			int marginError = 5;
			int topA = 16; // TOP 12, 16, 32

			String pathWav = "C:\\Users\\Rafael\\Desktop\\ABC.wav"; // 44100, 16_PCM, 5 seconds

			// ====
			Reader wavReader = new Reader(pathWav, false);

			double[] pcmData = wavReader.getByteArray();
			System.out.println("FILENAME = " + pathWav + "\nSIZE = " + pcmData.length + "\nSR = " + (long)wavReader.getSR());
			System.out.println("fqA = " + fqA + "\nfqB = " + fqB + "\nmarginError = " + marginError + "\ntopA = " + topA);

			double[] tmp_pcmData = pcmData;

			int n_fft_65536 = 1024*64; // 65536

			if(tmp_pcmData.length < n_fft_65536) {
				System.out.println("File too small! [65536] (" + tmp_pcmData.length + " < " + n_fft_65536 + ")");
				return;
			}

			Hamming windowHamming_65536 = new Hamming();
			double[] hammingData_65536 = windowHamming_65536.toHammingWindow(tmp_pcmData, tmp_pcmData.length);

			DoubleFFT_1D fft_fsk = new DoubleFFT_1D(n_fft_65536);
			fft_fsk.realForward(hammingData_65536);

			// Extract Real part
			double[] result_65536 = new double[hammingData_65536.length / 2];
			for(int s = 0; s < result_65536.length; s++) {
				//result[s] = Math.abs(signal[2*s]);
				double re = hammingData_65536[s * 2];
				double im = hammingData_65536[s * 2 + 1];
				result_65536[s] = (double) Math.sqrt(re * re + im * im) / result_65536.length;
			}

			// frequency  = index * SamplingFrequency / N_FFT

			Pair[] frequenciesArray_65536 = new Pair[result_65536.length];

			//fill the array
			for(int i = 0; i < result_65536.length; i++) {
				frequenciesArray_65536[i] = new Pair(i, result_65536[i]);
			}
			Arrays.sort(frequenciesArray_65536);
			// ====

			RangeTones rangeTones = new RangeTones(fqA, fqB, marginError, topA, (int)wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
			System.out.println("Score = " + rangeTones.getScore());
		}
	}
}
