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

import com.tortillum.salome.salome.Pair;
import com.tortillum.salome.salome.Range;

//Based on FFT
//Weight = 2 (2 tones detected)
//listFqs : TOP 64
//Range MODE = 0 (POS/NEG)

public class WEFAX {
	private int MARGIN_ERROR = 5; // in Hertz

	private int frequencie_A_120 = 2300;
	private int frequencie_B_120 = 2280;
	private boolean find_frequencie_A_120 = false;	
	private boolean find_frequencie_B_120 = false;
	
	private int frequencie_A_240 = 2310;
	private int frequencie_B_240 = 1510;
	private boolean find_frequencie_A_240 = false;	
	private boolean find_frequencie_B_240 = false;

	public WEFAX(int sampleRate, int n_fft, Pair[] listFqs) {
		for(int i = 0; i < 64; i++) {
			int fq = listFqs[i].index * sampleRate / n_fft;

			// ===
			// 120
			// ===

			Range fq_range = new Range(frequencie_A_120, MARGIN_ERROR, 0);
			int[] tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_A_120 = true;
				}
			}

			fq_range = new Range(frequencie_B_120, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_B_120 = true;
				}
			}
			
			// ===
			// 240
			// ===
			
			fq_range = new Range(frequencie_A_240, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_A_240 = true;
				}
			}
			
			fq_range = new Range(frequencie_B_240, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_B_240 = true;
				}
			}
		}
	}

	public boolean is120() {
		if(find_frequencie_A_120 && find_frequencie_B_120)
			return true;
		return false;
	}
	
	public boolean is240() {
		if(find_frequencie_A_240 && find_frequencie_B_240)
			return true;
		return false;
	}
}
