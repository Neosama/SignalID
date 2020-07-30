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

// STANAG

//PSK
//Based on FFT
//Weight = X (X tones detected)
//listFqs : TOP 12, 16, 24 for fq_A/fq_B & TOP 64 between
//Range MODE = 1 (POS for fq_A / NEG for fq_B)

//For score_XXXX :
//Number of peaks between A and B.
//(score_max = fq_A - fq_B)

//!SCORES NOTES!
//4285_GEN : 32; 28

public class STANAG {

	private int MARGIN_ERROR = 5; // in Hertz

	// 4285 (GEN) (src: FUG8)
	private int frequencie_A_4285_GEN = 2415;
	private int frequencie_B_4285_GEN = 1405;
	private boolean find_frequencie_A_4285_GEN = false;	
	private boolean find_frequencie_B_4285_GEN = false;
	private int margin_frequencies_4285_GEN = (frequencie_A_4285_GEN - frequencie_B_4285_GEN) / 2;
	private int frequencie_MID_A_4285_GEN = frequencie_A_4285_GEN - margin_frequencies_4285_GEN;
	private int frequencie_MID_B_4285_GEN = frequencie_B_4285_GEN + margin_frequencies_4285_GEN;
	private int score_4285_GEN = 0; // MAX = 1010 (fq_A - fq_B without margin)
	
	// Thales Système - 3000 FEC Variant / SYS3000_FEC
	private int frequencie_A_4285_SYS3000_FEC = 2150;
	private int frequencie_B_4285_SYS3000_FEC = 1450;
	private boolean find_frequencie_A_4285_SYS3000_FEC = false;	
	private boolean find_frequencie_B_4285_SYS3000_FEC = false;
	private int margin_frequencies_4285_SYS3000_FEC = (frequencie_A_4285_SYS3000_FEC - frequencie_B_4285_SYS3000_FEC) / 2;
	private int frequencie_MID_A_4285_SYS3000_FEC = frequencie_A_4285_SYS3000_FEC - margin_frequencies_4285_SYS3000_FEC;
	private int frequencie_MID_B_4285_SYS3000_FEC = frequencie_B_4285_SYS3000_FEC + margin_frequencies_4285_SYS3000_FEC;
	private int score_4285_SYS3000_FEC = 0; // MAX = 700 (fq_A - fq_B without margin)
	
	// 8PSK
	private int frequencie_A_4285_8PSK = 2172;
	private int frequencie_B_4285_8PSK = 1160;
	private boolean find_frequencie_A_4285_8PSK = false;	
	private boolean find_frequencie_B_4285_8PSK = false;
	private int margin_frequencies_4285_8PSK = (frequencie_A_4285_8PSK - frequencie_B_4285_8PSK) / 2;
	private int frequencie_MID_A_4285_8PSK = frequencie_A_4285_8PSK - margin_frequencies_4285_8PSK;
	private int frequencie_MID_B_4285_8PSK = frequencie_B_4285_8PSK + margin_frequencies_4285_8PSK;
	private int score_4285_8PSK = 0; // MAX = 1012 (fq_A - fq_B without margin)
	
	// TFC
	private int frequencie_A_4285_TFC = 2423;
	private int frequencie_B_4285_TFC = 1880;
	private boolean find_frequencie_A_4285_TFC = false;	
	private boolean find_frequencie_B_4285_TFC = false;
	private int margin_frequencies_4285_TFC = (frequencie_A_4285_TFC - frequencie_B_4285_TFC) / 2;
	private int frequencie_MID_A_4285_TFC = frequencie_A_4285_TFC - margin_frequencies_4285_TFC;
	private int frequencie_MID_B_4285_TFC = frequencie_B_4285_TFC + margin_frequencies_4285_TFC;
	private int score_4285_TFC = 0; // MAX = 543 (fq_A - fq_B without margin)
	
	// IDLE
	private int frequencie_A_4285_IDLE = 2491;
	private int frequencie_B_4285_IDLE = 644;
	private boolean find_frequencie_A_4285_IDLE = false;	
	private boolean find_frequencie_B_4285_IDLE = false;
	private int margin_frequencies_4285_IDLE = (frequencie_A_4285_IDLE - frequencie_B_4285_IDLE) / 2;
	private int frequencie_MID_A_4285_IDLE = frequencie_A_4285_IDLE - margin_frequencies_4285_IDLE;
	private int frequencie_MID_B_4285_IDLE = frequencie_B_4285_IDLE + margin_frequencies_4285_IDLE;
	private int score_4285_IDLE = 0; // MAX = 1847 (fq_A - fq_B without margin)
	
	// Thales Système - 3000 Variant / SYS3000
	private int frequencie_A_4285_SYS3000 = 1390;
	private int frequencie_B_4285_SYS3000 = 937;
	private boolean find_frequencie_A_4285_SYS3000 = false;	
	private boolean find_frequencie_B_4285_SYS3000 = false;
	private int margin_frequencies_4285_SYS3000 = (frequencie_A_4285_SYS3000 - frequencie_B_4285_SYS3000) / 2;
	private int frequencie_MID_A_4285_SYS3000 = frequencie_A_4285_SYS3000 - margin_frequencies_4285_SYS3000;
	private int frequencie_MID_B_4285_SYS3000 = frequencie_B_4285_SYS3000 + margin_frequencies_4285_SYS3000;
	private int score_4285_SYS3000 = 453; // MAX = 1847 (fq_A - fq_B without margin)
	
	public STANAG(int sampleRate, int n_fft, Pair[] listFqs) {
		for(int i = 0; i < 64; i++) {
			int fq = listFqs[i].index * sampleRate / n_fft;

			// ==========
			// 4285 (GEN)
			// ==========

			Range fq_range = new Range(frequencie_A_4285_GEN, MARGIN_ERROR, 0);
			int[] tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(i < 12) {
					if(fq == tmp_range[j]) {
						find_frequencie_A_4285_GEN = true;
					}
				}
			}

			fq_range = new Range(frequencie_B_4285_GEN, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(i < 12) {
					if(fq == tmp_range[j]) {
						find_frequencie_B_4285_GEN = true;
					}
				}
			}

			if(find_frequencie_A_4285_GEN && find_frequencie_B_4285_GEN) {
				fq_range = new Range(frequencie_MID_A_4285_GEN, margin_frequencies_4285_GEN + MARGIN_ERROR, 1);
				tmp_range = fq_range.get();
				for(int j = 0; j < tmp_range.length; j++) {
					if(fq == tmp_range[j]) {
						score_4285_GEN++;
					}
				}

				fq_range = new Range(frequencie_MID_B_4285_GEN, margin_frequencies_4285_GEN + MARGIN_ERROR, 2);
				tmp_range = fq_range.get();
				for(int j = 0; j < tmp_range.length; j++) {
					if(fq == tmp_range[j]) {
						score_4285_GEN++;
					}
				}
			}
			
			// ===========
			// SYS3000_FEC
			// ===========
						
			fq_range = new Range(frequencie_A_4285_SYS3000_FEC, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(i < 16) {
					if(fq == tmp_range[j]) {
						find_frequencie_A_4285_SYS3000_FEC = true;
					}
				}
			}
			
			fq_range = new Range(frequencie_B_4285_SYS3000_FEC, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(i < 16) {
					if(fq == tmp_range[j]) {
						find_frequencie_B_4285_SYS3000_FEC = true;
					}
				}
			}
			
			if(find_frequencie_A_4285_SYS3000_FEC && find_frequencie_B_4285_SYS3000_FEC) {
				fq_range = new Range(frequencie_MID_A_4285_SYS3000_FEC, margin_frequencies_4285_SYS3000_FEC + MARGIN_ERROR, 1);
				tmp_range = fq_range.get();
				for(int j = 0; j < tmp_range.length; j++) {
					if(fq == tmp_range[j]) {
						score_4285_SYS3000_FEC++;
					}
				}

				fq_range = new Range(frequencie_MID_B_4285_SYS3000_FEC, margin_frequencies_4285_SYS3000_FEC + MARGIN_ERROR, 2);
				tmp_range = fq_range.get();
				for(int j = 0; j < tmp_range.length; j++) {
					if(fq == tmp_range[j]) {
						score_4285_SYS3000_FEC++;
					}
				}
			}
			
			// ====
			// 8PSK
			// ====
			
			fq_range = new Range(frequencie_A_4285_8PSK, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(i < 16) {
					if(fq == tmp_range[j]) {
						find_frequencie_A_4285_8PSK = true;
					}
				}
			}
			
			fq_range = new Range(frequencie_B_4285_8PSK, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(i < 16) {
					if(fq == tmp_range[j]) {
						find_frequencie_B_4285_8PSK = true;
					}
				}
			}
			
			if(find_frequencie_A_4285_8PSK && find_frequencie_B_4285_8PSK) {
				fq_range = new Range(frequencie_MID_A_4285_8PSK, margin_frequencies_4285_8PSK + MARGIN_ERROR, 1);
				tmp_range = fq_range.get();
				for(int j = 0; j < tmp_range.length; j++) {
					if(fq == tmp_range[j]) {
						score_4285_8PSK++;
					}
				}

				fq_range = new Range(frequencie_MID_B_4285_8PSK, margin_frequencies_4285_8PSK + MARGIN_ERROR, 2);
				tmp_range = fq_range.get();
				for(int j = 0; j < tmp_range.length; j++) {
					if(fq == tmp_range[j]) {
						score_4285_8PSK++;
					}
				}
			}
			
			// ===
			// TFC
			// ===
			
			fq_range = new Range(frequencie_A_4285_TFC, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(i < 24) {
					if(fq == tmp_range[j]) {
						find_frequencie_A_4285_TFC = true;
					}
				}
			}
			
			fq_range = new Range(frequencie_B_4285_TFC, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(i < 24) {
					if(fq == tmp_range[j]) {
						find_frequencie_B_4285_TFC = true;
					}
				}
			}
			
			if(find_frequencie_A_4285_TFC && find_frequencie_B_4285_TFC) {
				fq_range = new Range(frequencie_MID_A_4285_TFC, margin_frequencies_4285_TFC + MARGIN_ERROR, 1);
				tmp_range = fq_range.get();
				for(int j = 0; j < tmp_range.length; j++) {
					if(fq == tmp_range[j]) {
						score_4285_TFC++;
					}
				}

				fq_range = new Range(frequencie_MID_B_4285_TFC, margin_frequencies_4285_TFC + MARGIN_ERROR, 2);
				tmp_range = fq_range.get();
				for(int j = 0; j < tmp_range.length; j++) {
					if(fq == tmp_range[j]) {
						score_4285_TFC++;
					}
				}
			}
			
			// ====
			// IDLE
			// ====
			
			fq_range = new Range(frequencie_A_4285_IDLE, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(i < 12) {
					if(fq == tmp_range[j]) {
						find_frequencie_A_4285_IDLE = true;
					}
				}
			}
			
			fq_range = new Range(frequencie_B_4285_IDLE, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(i < 12) {
					if(fq == tmp_range[j]) {
						find_frequencie_B_4285_IDLE = true;
					}
				}
			}
			
			if(find_frequencie_A_4285_IDLE && find_frequencie_B_4285_IDLE) {
				fq_range = new Range(frequencie_MID_A_4285_IDLE, margin_frequencies_4285_IDLE + MARGIN_ERROR, 1);
				tmp_range = fq_range.get();
				for(int j = 0; j < tmp_range.length; j++) {
					if(fq == tmp_range[j]) {
						score_4285_IDLE++;
					}
				}

				fq_range = new Range(frequencie_MID_B_4285_IDLE, margin_frequencies_4285_IDLE + MARGIN_ERROR, 2);
				tmp_range = fq_range.get();
				for(int j = 0; j < tmp_range.length; j++) {
					if(fq == tmp_range[j]) {
						score_4285_IDLE++;
					}
				}
			}
			
			// =======
			// SYS3000
			// =======
			
			fq_range = new Range(frequencie_A_4285_SYS3000, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(i < 16) {
					if(fq == tmp_range[j]) {
						find_frequencie_A_4285_SYS3000 = true;
					}
				}
			}
			
			fq_range = new Range(frequencie_B_4285_SYS3000, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(i < 16) {
					if(fq == tmp_range[j]) {
						find_frequencie_B_4285_SYS3000 = true;
					}
				}
			}
			
			if(find_frequencie_A_4285_SYS3000 && find_frequencie_B_4285_SYS3000) {
				fq_range = new Range(frequencie_MID_A_4285_SYS3000, margin_frequencies_4285_SYS3000 + MARGIN_ERROR, 1);
				tmp_range = fq_range.get();
				for(int j = 0; j < tmp_range.length; j++) {
					if(fq == tmp_range[j]) {
						score_4285_SYS3000++;
					}
				}

				fq_range = new Range(frequencie_MID_B_4285_SYS3000, margin_frequencies_4285_SYS3000 + MARGIN_ERROR, 2);
				tmp_range = fq_range.get();
				for(int j = 0; j < tmp_range.length; j++) {
					if(fq == tmp_range[j]) {
						score_4285_SYS3000++;
					}
				}
			}
		}

		System.out.println("[DBG - STANAG.java] score_STANAG_4285 (GEN) : " + score_4285_GEN);
		System.out.println("[DBG - STANAG.java] score_4285_SYS3000_FEC : " + score_4285_SYS3000_FEC);
		System.out.println("[DBG - STANAG.java] score_4285_8PSK : " + score_4285_8PSK);
		System.out.println("[DBG - STANAG.java] score_4285_TFC : " + score_4285_TFC);
		System.out.println("[DBG - STANAG.java] score_4285_IDLE : " + score_4285_IDLE);
		System.out.println("[DBG - STANAG.java] score_4285_SYS3000 : " + score_4285_SYS3000);
	}

	public boolean is4285_GEN() {
		if(find_frequencie_A_4285_GEN && find_frequencie_B_4285_GEN && score_4285_GEN <= 35 && score_4285_GEN >= 25)
			return true;
		return false;
	}
	
	public boolean is4285_SYS3000_FEC() {
		if(find_frequencie_A_4285_SYS3000_FEC && find_frequencie_B_4285_SYS3000_FEC && score_4285_SYS3000_FEC <= 35 && score_4285_SYS3000_FEC >= 25)
			return true;
		return false;
	}
	
	public boolean is4285_8PSK() {
		if(find_frequencie_A_4285_8PSK && find_frequencie_B_4285_8PSK && score_4285_8PSK <= 35 && score_4285_8PSK >= 25)
			return true;
		return false;
	}
	
	public boolean is4285_TFC() {
		if(find_frequencie_A_4285_TFC && find_frequencie_B_4285_TFC && score_4285_TFC <= 30 && score_4285_TFC >= 15)
			return true;
		return false;
	}
	
	public boolean is4285_IDLE() {
		if(find_frequencie_A_4285_IDLE && find_frequencie_B_4285_IDLE && score_4285_IDLE <= 60 && score_4285_IDLE >= 50)
			return true;
		return false;
	}
	
	public boolean is4285_SYS3000() {
		if(find_frequencie_A_4285_SYS3000 && find_frequencie_B_4285_SYS3000 && score_4285_SYS3000 <= 495 && score_4285_SYS3000 >= 425)
			return true;
		return false;
	}
}
