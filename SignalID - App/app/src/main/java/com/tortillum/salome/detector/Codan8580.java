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

//CCIR 493-4 Selcall / Codan8580 / HF Selcall

//FSK
//Based on FFT
//Weight = 2 (2 tones detected)
//listFqs : TOP 12
//Range MODE = 0 (POS/NEG)

import com.tortillum.salome.salome.Pair;
import com.tortillum.salome.salome.Range;

public class Codan8580 {
	private int MARGIN_ERROR = 5; // in Hertz

	private int frequencie_A = 1845;
	private int frequencie_B = 1700;
	private boolean find_frequencie_A = false;	
	private boolean find_frequencie_B = false;
	
	public Codan8580(int sampleRate, int n_fft, Pair[] listFqs) {
		for(int i = 0; i < 12; i++) {
			int fq = listFqs[i].index * sampleRate / n_fft;

			// =========
			// Codan8580
			// =========
			
			Range fq_range = new Range(frequencie_A, MARGIN_ERROR, 0);
			int[] tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_A = true;
				}
			}

			fq_range = new Range(frequencie_B, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_B = true;
				}
			}
		}
	}
	
	public boolean isCodan8580() {
		if(find_frequencie_A && find_frequencie_B)
			return true;
		return false;
	}
}
