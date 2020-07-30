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

// PSK (31, 63, 500) / Binary PSK (BPSK) / Quadrature PSK (QPSK) / FEC

//PSK
//Based on FFT
//Weight = X (X tones detected)
//listFqs : TOP 12 for fq_A/fq_B & TOP 64 between 
//Range MODE = 1 (POS for fq_A / NEG for fq_B)

// For score_XXXX :
// Number of peaks between A and B.
// (score_max = fq_A - fq_B)

// !SCORES NOTES!
// QPSK500 : 57
// BPSK500 : 46
// QPSK_31 : 56
// BPSK_31 : 36
// QPSK_63 : 61
// BPSK_63 : ??

public class PSK {

	private int MARGIN_ERROR = 5; // in Hertz

	// QPSK_500 & BPSK_500 same frequencies
	private int frequencie_A_PSK_500 = 1750;
	private int frequencie_B_PSK_500 = 1250;
	private boolean find_frequencie_A_PSK_500 = false;	
	private boolean find_frequencie_B_PSK_500 = false;
	private int margin_frequencies_PSK_500 = (frequencie_A_PSK_500 - frequencie_B_PSK_500) / 2;
	private int frequencie_MID_A_PSK_500 = frequencie_A_PSK_500 - margin_frequencies_PSK_500;
	private int frequencie_MID_B_PSK_500 = frequencie_B_PSK_500 + margin_frequencies_PSK_500;
	private int score_PSK_500 = 0; // MAX = 500 (fq_A - fq_B without margin)

	// QPSK31 & BPSK31 same frequencies
	private int frequencie_A_PSK_31 = 1515;
	private int frequencie_B_PSK_31 = 1485;
	private boolean find_frequencie_A_PSK_31 = false;	
	private boolean find_frequencie_B_PSK_31 = false;
	private int margin_frequencies_PSK_31 = (frequencie_A_PSK_31 - frequencie_B_PSK_31) / 2;
	private int frequencie_MID_A_PSK_31 = frequencie_A_PSK_31 - margin_frequencies_PSK_31;
	private int frequencie_MID_B_PSK_31 = frequencie_B_PSK_31 + margin_frequencies_PSK_31;
	private int score_PSK_31 = 0; // MAX = 30 (fq_A - fq_B without margin)

	// QPSK63 & BPSK63 same frequencies
	private int frequencie_A_PSK_63 = 1530;
	private int frequencie_B_PSK_63 = 1465;
	private boolean find_frequencie_A_PSK_63 = false;	
	private boolean find_frequencie_B_PSK_63 = false;
	private int margin_frequencies_PSK_63 = (frequencie_A_PSK_63 - frequencie_B_PSK_63) / 2;
	private int frequencie_MID_A_PSK_63 = frequencie_A_PSK_63 - margin_frequencies_PSK_63;
	private int frequencie_MID_B_PSK_63 = frequencie_B_PSK_63 + margin_frequencies_PSK_63;
	private int score_PSK_63 = 0; // MAX = 65 (fq_A - fq_B without margin)

	// QPSK125 & BPSK125 same frequencies
	private int frequencie_A_PSK_125 = 1560;
	private int frequencie_B_PSK_125 = 1435;
	private boolean find_frequencie_A_PSK_125 = false;	
	private boolean find_frequencie_B_PSK_125 = false;
	private int margin_frequencies_PSK_125 = (frequencie_A_PSK_125 - frequencie_B_PSK_125) / 2;
	private int frequencie_MID_A_PSK_125 = frequencie_A_PSK_125 - margin_frequencies_PSK_125;
	private int frequencie_MID_B_PSK_125 = frequencie_B_PSK_125 + margin_frequencies_PSK_125;
	private int score_PSK_125 = 0; // MAX = 125 (fq_A - fq_B without margin)

	// QPSK250 & BPSK250 same frequencies
	private int frequencie_A_PSK_250 = 1625;
	private int frequencie_B_PSK_250 = 1375;
	private boolean find_frequencie_A_PSK_250 = false;	
	private boolean find_frequencie_B_PSK_250 = false;
	private int margin_frequencies_PSK_250 = (frequencie_A_PSK_250 - frequencie_B_PSK_250) / 2;
	private int frequencie_MID_A_PSK_250 = frequencie_A_PSK_250 - margin_frequencies_PSK_250;
	private int frequencie_MID_B_PSK_250 = frequencie_B_PSK_250 + margin_frequencies_PSK_250;
	private int score_PSK_250 = 0; // MAX = 250 (fq_A - fq_B without margin)

	public PSK(int sampleRate, int n_fft, Pair[] listFqs) {
		for(int i = 0; i < 64; i++) {
			int fq = listFqs[i].index * sampleRate / n_fft;

			// ===================
			// QPSK_500 / BPSK_500
			// ===================

			Range fq_range = new Range(frequencie_A_PSK_500, MARGIN_ERROR, 0);
			int[] tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(i < 12) {
					if(fq == tmp_range[j]) {
						find_frequencie_A_PSK_500 = true;
					}
				}
			}

			fq_range = new Range(frequencie_B_PSK_500, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(i < 12) {
					if(fq == tmp_range[j]) {
						find_frequencie_B_PSK_500 = true;
					}
				}
			}

			if(find_frequencie_A_PSK_500 && find_frequencie_B_PSK_500) {
				fq_range = new Range(frequencie_MID_A_PSK_500, margin_frequencies_PSK_500 + MARGIN_ERROR, 1);
				tmp_range = fq_range.get();
				for(int j = 0; j < tmp_range.length; j++) {
					if(fq == tmp_range[j]) {
						score_PSK_500++;
					}
				}

				fq_range = new Range(frequencie_MID_B_PSK_500, margin_frequencies_PSK_500 + MARGIN_ERROR, 2);
				tmp_range = fq_range.get();
				for(int j = 0; j < tmp_range.length; j++) {
					if(fq == tmp_range[j]) {
						score_PSK_500++;
					}
				}
			}

			// ===============
			// BPSK31 / QPSK31
			// ===============

			fq_range = new Range(frequencie_A_PSK_31, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(i < 12) {
					if(fq == tmp_range[j]) {
						find_frequencie_A_PSK_31 = true;
					}
				}
			}

			fq_range = new Range(frequencie_B_PSK_31, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(i < 12) {
					if(fq == tmp_range[j]) {
						find_frequencie_B_PSK_31 = true;
					}
				}
			}

			if(find_frequencie_A_PSK_31 && find_frequencie_B_PSK_31) {
				fq_range = new Range(frequencie_MID_A_PSK_31, margin_frequencies_PSK_31 + MARGIN_ERROR, 1);
				tmp_range = fq_range.get();
				for(int j = 0; j < tmp_range.length; j++) {
					if(fq == tmp_range[j]) {
						score_PSK_31++;
					}
				}

				fq_range = new Range(frequencie_MID_B_PSK_31, margin_frequencies_PSK_31 + MARGIN_ERROR, 2);
				tmp_range = fq_range.get();
				for(int j = 0; j < tmp_range.length; j++) {
					if(fq == tmp_range[j]) {
						score_PSK_31++;
					}
				}
			}

			// ===============
			// BPSK63 / QPSK63
			// ===============

			fq_range = new Range(frequencie_A_PSK_63, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(i < 12) {
					if(fq == tmp_range[j]) {
						find_frequencie_A_PSK_63 = true;
					}
				}
			}

			fq_range = new Range(frequencie_B_PSK_63, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(i < 12) {
					if(fq == tmp_range[j]) {
						find_frequencie_B_PSK_63 = true;
					}
				}
			}

			if(find_frequencie_A_PSK_63 && find_frequencie_B_PSK_63) {
				fq_range = new Range(frequencie_MID_A_PSK_63, margin_frequencies_PSK_63 + MARGIN_ERROR, 1);
				tmp_range = fq_range.get();
				for(int j = 0; j < tmp_range.length; j++) {
					if(fq == tmp_range[j]) {
						score_PSK_63++;
					}
				}

				fq_range = new Range(frequencie_MID_B_PSK_63, margin_frequencies_PSK_63 + MARGIN_ERROR, 2);
				tmp_range = fq_range.get();
				for(int j = 0; j < tmp_range.length; j++) {
					if(fq == tmp_range[j]) {
						score_PSK_63++;
					}
				}
			}

			// =================
			// BPSK125 / QPSK125
			// =================

			fq_range = new Range(frequencie_A_PSK_125, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(i < 12) {
					if(fq == tmp_range[j]) {
						find_frequencie_A_PSK_125 = true;
					}
				}
			}

			fq_range = new Range(frequencie_B_PSK_125, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(i < 12) {
					if(fq == tmp_range[j]) {
						find_frequencie_B_PSK_125 = true;
					}
				}
			}

			if(find_frequencie_A_PSK_125 && find_frequencie_B_PSK_125) {
				fq_range = new Range(frequencie_MID_A_PSK_125, margin_frequencies_PSK_125 + MARGIN_ERROR, 1);
				tmp_range = fq_range.get();
				for(int j = 0; j < tmp_range.length; j++) {
					if(fq == tmp_range[j]) {
						score_PSK_125++;
					}
				}

				fq_range = new Range(frequencie_MID_B_PSK_125, margin_frequencies_PSK_125 + MARGIN_ERROR, 2);
				tmp_range = fq_range.get();
				for(int j = 0; j < tmp_range.length; j++) {
					if(fq == tmp_range[j]) {
						score_PSK_125++;
					}
				}
			}

			// =================
			// BPSK250 / QPSK250
			// =================

			fq_range = new Range(frequencie_A_PSK_250, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(i < 12) {
					if(fq == tmp_range[j]) {
						find_frequencie_A_PSK_250 = true;
					}
				}
			}

			fq_range = new Range(frequencie_B_PSK_250, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(i < 12) {
					if(fq == tmp_range[j]) {
						find_frequencie_B_PSK_250 = true;
					}
				}
			}

			if(find_frequencie_A_PSK_250 && find_frequencie_B_PSK_250) {
				fq_range = new Range(frequencie_MID_A_PSK_250, margin_frequencies_PSK_250 + MARGIN_ERROR, 1);
				tmp_range = fq_range.get();
				for(int j = 0; j < tmp_range.length; j++) {
					if(fq == tmp_range[j]) {
						score_PSK_250++;
					}
				}

				fq_range = new Range(frequencie_MID_B_PSK_250, margin_frequencies_PSK_250 + MARGIN_ERROR, 2);
				tmp_range = fq_range.get();
				for(int j = 0; j < tmp_range.length; j++) {
					if(fq == tmp_range[j]) {
						score_PSK_250++;
					}
				}
			}
		}

		System.out.println("[DBG - PSK.java] score_PSK_31 : " + score_PSK_31);
		System.out.println("[DBG - PSK.java] score_PSK_63 : " + score_PSK_63);
		System.out.println("[DBG - PSK.java] score_PSK_125 : " + score_PSK_125);
		System.out.println("[DBG - PSK.java] score_PSK_250 : " + score_PSK_250);
		System.out.println("[DBG - PSK.java] score_PSK_500 : " + score_PSK_500);
	}

	public boolean isBPSK_31() {
		if(find_frequencie_A_PSK_31 && find_frequencie_B_PSK_31 && score_PSK_31 <= 50 && score_PSK_31 >= 40)
			return true;
		return false;
	}

	public boolean isQPSK_31() {
		if(find_frequencie_A_PSK_31 && find_frequencie_B_PSK_31 && score_PSK_31 <= 60 && score_PSK_31 >= 50)
			return true;
		return false;
	}

	public boolean isBPSK_63() {
		if(find_frequencie_A_PSK_63 && find_frequencie_B_PSK_63 && score_PSK_63 <= 55 && score_PSK_63 >= 40)
			return true;
		return false;
	}

	public boolean isQPSK_63() {
		if(find_frequencie_A_PSK_63 && find_frequencie_B_PSK_63 && score_PSK_63 <= 65 && score_PSK_63 >= 50)
			return true;
		return false;
	}

	public boolean isBPSK_125() {
		if(find_frequencie_A_PSK_125 && find_frequencie_B_PSK_125 && score_PSK_125 <= 55 && score_PSK_125 >= 40)
			return true;
		return false;
	}

	public boolean isQPSK_125() {
		if(find_frequencie_A_PSK_125 && find_frequencie_B_PSK_125 && score_PSK_125 <= 65 && score_PSK_125 >= 50)
			return true;
		return false;
	}
	
	public boolean isBPSK_250() {
		if(find_frequencie_A_PSK_250 && find_frequencie_B_PSK_250 && score_PSK_250 <= 55 && score_PSK_250 >= 40)
			return true;
		return false;
	}

	public boolean isQPSK_250() {
		if(find_frequencie_A_PSK_250 && find_frequencie_B_PSK_250 && score_PSK_250 <= 65 && score_PSK_250 >= 50)
			return true;
		return false;
	}
	
	public boolean isBPSK_500() {
		if(find_frequencie_A_PSK_500 && find_frequencie_B_PSK_500 && score_PSK_500 <= 50 && score_PSK_500 >= 40)
			return true;
		return false;
	}

	public boolean isQPSK_500() {
		if(find_frequencie_A_PSK_500 && find_frequencie_B_PSK_500 && score_PSK_500 <= 60 && score_PSK_500 >= 50)
			return true;
		return false;
	}
}
