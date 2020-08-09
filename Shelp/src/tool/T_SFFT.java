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

public class T_SFFT {
	private String out = "";
	private Pair[] frequenciesArray_sfft = null;

	private int n_sfft = 1024;
	int windowStep = 128+1;
	private int sampleRate = 44100;
	private int top = 64;

	public T_SFFT(String pPath, int pTop) throws IOException {
		Reader reader = new Reader(pPath, false);
		double[] allData = reader.getByteArray();
		
		top = pTop;
		
		int totalWS = 0;
        for(int i = 0; i < allData.length/windowStep; i++) {
            if(totalWS+(allData.length/windowStep) < allData.length) {
                double[] WS_array = Arrays.copyOfRange(allData, totalWS, totalWS+(allData.length/windowStep));

                Hamming windowHamming_sfft = new Hamming();
                double[] hammingData_sfft = windowHamming_sfft.toHammingWindow(WS_array, WS_array.length);

                DoubleFFT_1D sfft = new DoubleFFT_1D(n_sfft);
                sfft.realForward(hammingData_sfft);

                double[] result_sfft = new double[hammingData_sfft.length / 2];
                for(int s = 0; s < result_sfft.length; s++) {
                    double re = hammingData_sfft[s * 2];
                    double im = hammingData_sfft[s * 2 + 1];
                    result_sfft[s] = (double) Math.sqrt(re * re + im * im) / result_sfft.length;
                }

                frequenciesArray_sfft = new Pair[result_sfft.length];

                for (int j = 0; j < result_sfft.length; j++) {
                    frequenciesArray_sfft[j] = new Pair(j, result_sfft[j]);
                }
                Arrays.sort(frequenciesArray_sfft);

                // XXXXXX
                
        		for(int j = 0; j < frequenciesArray_sfft.length; j++) {
        			if(j <= top) {
        				int fq = frequenciesArray_sfft[j].index * sampleRate / n_sfft;

        				out += String.valueOf(fq) + System.lineSeparator();
        			}
        		}
        		
        		out += "=========[" + i + "]=========" + System.lineSeparator();
                totalWS += WS_array.length;
            }
        }
	}
	
	public String get() {
		return out;
	}	
}
