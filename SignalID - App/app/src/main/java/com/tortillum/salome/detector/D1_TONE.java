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

package com.tortillum.salome.detector;

import com.tortillum.salome.other.Pair;
import com.tortillum.salome.other.Range;

public class D1_TONE {
	private int MARGIN_ERROR = 5; // in Hertz

	private int score_multiples = 0; // Only with int[] method
	private boolean find_frequencie_A = false;

	public D1_TONE(int frequencie_A, int marginError, int topA, int sampleRate, int n_fft, Pair[] listFqs) {
		MARGIN_ERROR = marginError;

		for(int i = 0; i < listFqs.length; i++) {
			int fq = listFqs[i].index * sampleRate / n_fft;

			Range fq_range = new Range(frequencie_A, MARGIN_ERROR, 0);
			int[] tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(i < topA) {
					if(fq == tmp_range[j]) {
						find_frequencie_A = true;
					}
				}
			}
		}
	}

	//    public D1_TONE(int[] frequencies, int marginError, int topA, int sampleRate, int n_fft, Pair[] listFqs) {
	//        MARGIN_ERROR = marginError;
	//
	//        for(int frequencie_A : frequencies) {
	//            for (int i = 0; i < listFqs.length; i++) {
	//                int fq = listFqs[i].index * sampleRate / n_fft;
	//
	//                Range fq_range = new Range(frequencie_A, MARGIN_ERROR, 0);
	//                int[] tmp_range = fq_range.get();
	//                for (int j = 0; j < tmp_range.length; j++) {
	//                    if (i < topA) {
	//                        if (fq == tmp_range[j]) {
	//                            find_frequencie_A = true;
	//                            score_multiples++;
	//                        }
	//                    }
	//                }
	//            }
	//        }
	//    }

	public D1_TONE(int[] frequencies, int marginError, int topA, int sampleRate, int n_fft, Pair[] listFqs) {
		MARGIN_ERROR = marginError;

		int sizeList = 0;

		if(topA < listFqs.length)
			sizeList = topA;
		else
			sizeList = listFqs.length;

		for(int frequencie_A : frequencies) {
			for (int i = 0; i < sizeList; i++) {
				int fq = listFqs[i].index * sampleRate / n_fft;

				Range fq_range = new Range(frequencie_A, MARGIN_ERROR, 0);
				int[] tmp_range = fq_range.get();
				for (int j = 0; j < tmp_range.length; j++) {
					if (fq == tmp_range[j]) {
						find_frequencie_A = true;
						score_multiples++;
					}
				}
			}
		}
	}

	public boolean check() {
		return find_frequencie_A;
	}

	// Only with int[] method
	public int getScore(){
		return score_multiples;
	}
}
