package com.tortillum.salome.detector;
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

//FSK
//Based on FFT
//Weight = 2 (2 tones detected)
//listFqs : TOP 16
//Range MODE = 0 (POS/NEG)

import com.tortillum.salome.salome.Pair;
import com.tortillum.salome.salome.Range;

public class ASCII {
	private int MARGIN_ERROR = 5; // in Hertz

	private int frequencie_A_170Hz = 985;
	private int frequencie_B_170Hz = 815;
	private boolean find_frequencie_A_170Hz = false;	
	private boolean find_frequencie_B_170Hz = false;

	public ASCII(int sampleRate, int n_fft, Pair[] listFqs) {
		for(int i = 0; i < 16; i++) {
			int fq = listFqs[i].index * sampleRate / n_fft;

			// =====
			// 170Hz
			// =====
			
			Range fq_range = new Range(frequencie_A_170Hz, MARGIN_ERROR, 0);
			int[] tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_A_170Hz = true;
				}
			}

			fq_range = new Range(frequencie_B_170Hz, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_B_170Hz = true;
				}
			}
		}
	}
	
	public boolean is170Hz() {
		if(find_frequencie_A_170Hz && find_frequencie_B_170Hz)
			return true;
		return false;
	}
}
