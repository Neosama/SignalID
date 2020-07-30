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

// FSK
// Based on FFT
// Weight = 2 (2 tones detected)
// listFqs : TOP 12
// Range MODE = 0 (POS/NEG)

import com.tortillum.salome.salome.Pair;
import com.tortillum.salome.salome.Range;

public class PactorI {

	private int MARGIN_ERROR = 5; // in Hertz

	private int frequencie_A_standard = 1815;
	private int frequencie_B_standard = 1615;
	private boolean find_frequencie_A_standard = false;	
	private boolean find_frequencie_B_standard = false;

	private int frequencie_A_FSP = 1610;
	private int frequencie_B_FSP = 1410;
	private boolean find_frequencie_A_FSP = false;	
	private boolean find_frequencie_B_FSP = false;

	// ???????????????????????????????????????????????????
	private int frequencie_A_FEC = 1110;
	private int frequencie_B_FEC = 910;
	private boolean find_frequencie_A_FEC = false;	
	private boolean find_frequencie_B_FEC = false;
	// ???????????????????????????????????????????????????

	private int frequencie_A_SELCALL = 2315;
	private int frequencie_B_SELCALL = 2115;
	private boolean find_frequencie_A_SELCALL = false;	
	private boolean find_frequencie_B_SELCALL = false;

	public PactorI(int sampleRate, int n_fft, Pair[] listFqs) {
		for(int i = 0; i < 12; i++) {
			int fq = listFqs[i].index * sampleRate / n_fft;

			// ========
			// STANDARD
			// ========

			Range fq_range = new Range(frequencie_A_standard, MARGIN_ERROR, 0);
			int[] tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_A_standard = true;
				}
			}

			fq_range = new Range(frequencie_B_standard, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_B_standard = true;
				}
			}

			// ===
			// FSP
			// ===

			fq_range = new Range(frequencie_A_FSP, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_A_FSP = true;
				}
			}

			fq_range = new Range(frequencie_B_FSP, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_B_FSP = true;
				}
			}

			// ===
			// FEC
			// ===

			fq_range = new Range(frequencie_A_FEC, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_A_FEC = true;
				}
			}

			fq_range = new Range(frequencie_B_FEC, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_B_FEC = true;
				}
			}

			// =======
			// SELCALL
			// =======

			fq_range = new Range(frequencie_A_SELCALL, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_A_SELCALL = true;
				}
			}

			fq_range = new Range(frequencie_B_SELCALL, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_B_SELCALL = true;
				}
			}
		}
	}

	public boolean isStandard() {
		if(find_frequencie_A_standard && find_frequencie_B_standard)
			return true;
		return false;
	}

	public boolean isFSP() {
		if(find_frequencie_A_FSP && find_frequencie_B_FSP)
			return true;
		return false;
	}

	public boolean isFEC() {
		if(find_frequencie_A_FEC && find_frequencie_B_FEC)
			return true;
		return false;
	}

	public boolean isSELCALL() {
		if(find_frequencie_A_SELCALL && find_frequencie_B_SELCALL)
			return true;
		return false;
	}
}
