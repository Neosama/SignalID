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

package tool;

import java.io.IOException;
import java.util.Arrays;

import org.jtransforms.fft.DoubleFFT_1D;

import salome.Pair;
import wavReader.Reader;
import window.Hamming;

public class T_FFT {

	private String out = "";
	private Pair[] frequenciesArray_fft = null;

	private int n_fft = 1024 * 64; // 65536
	private int sampleRate = 44100;
	private int top = 64;

	public T_FFT(String pPath, int pTop) throws IOException {
		Reader reader = new Reader(pPath, false);
		double[] allData = reader.getByteArray();

		if (allData.length < n_fft) {
			return;
		}
		
		top = pTop;

		Hamming windowHamming_fft = new Hamming();
		double[] hammingData_fft = windowHamming_fft.toHammingWindow(allData, allData.length);

		DoubleFFT_1D fft = new DoubleFFT_1D(n_fft);
		fft.realForward(hammingData_fft);

		double[] result_fft = new double[hammingData_fft.length / 2];
		for (int s = 0; s < result_fft.length; s++) {
			double re = hammingData_fft[s * 2];
			double im = hammingData_fft[s * 2 + 1];
			result_fft[s] = (double) Math.sqrt(re * re + im * im) / result_fft.length;
		}

		frequenciesArray_fft = new Pair[result_fft.length];

		for (int i = 0; i < result_fft.length; i++) {
			frequenciesArray_fft[i] = new Pair(i, result_fft[i]);
		}
		Arrays.sort(frequenciesArray_fft);
	}

	public String get() {
		out = "";

		for(int i = 0; i < frequenciesArray_fft.length; i++) {
			if(i <= top) {
				int fq = frequenciesArray_fft[i].index * sampleRate / n_fft;

				out += String.valueOf(fq) + System.lineSeparator();
			}
		}

		return out;
	}
	
	public Pair[] getRaw() {
		return frequenciesArray_fft;
	}
}
