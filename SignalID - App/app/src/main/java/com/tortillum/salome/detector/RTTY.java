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

public class RTTY {

	private int MARGIN_ERROR = 5; // in Hertz

	private int frequencie_A_85Hz_commercial = 1040;
	private int frequencie_B_85Hz_commercial = 955;
	private boolean find_frequencie_A_85Hz_commercial = false;	
	private boolean find_frequencie_B_85Hz_commercial = false;

	private int frequencie_A_170Hz_commercial = 1085;
	private int frequencie_B_170Hz_commercial = 915;
	private boolean find_frequencie_A_170Hz_commercial = false;	
	private boolean find_frequencie_B_170Hz_commercial = false;

	private int frequencie_A_170Hz_amateurs = 900;
	private int frequencie_B_170Hz_amateurs = 730;
	private boolean find_frequencie_A_170Hz_amateurs = false;	
	private boolean find_frequencie_B_170Hz_amateurs = false;

	//private int frequencie_A_225Hz = ??;
	//private int frequencie_B_225Hz = ??;

	private int frequencie_A_450Hz_commercial = 1225;
	private int frequencie_B_450Hz_commercial = 775;
	private boolean find_frequencie_A_450Hz_commercial = false;	
	private boolean find_frequencie_B_450Hz_commercial = false;

	private int frequencie_A_850Hz_commercial = 1425;
	private int frequencie_B_850Hz_commercial = 575;
	private boolean find_frequencie_A_850Hz_commercial = false;	
	private boolean find_frequencie_B_850Hz_commercial = false;

	public RTTY(int sampleRate, int n_fft, Pair[] listFqs) {
		for(int i = 0; i < 12; i++) {
			int fq = listFqs[i].index * sampleRate / n_fft;

			// ===============
			// 85Hz COMMERCIAL
			// ===============

			Range fq_range = new Range(frequencie_A_85Hz_commercial, MARGIN_ERROR, 0);
			int[] tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_A_85Hz_commercial = true;
				}
			}

			fq_range = new Range(frequencie_B_85Hz_commercial, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_B_85Hz_commercial = true;
				}
			}

			// ================
			// 170Hz COMMERCIAL
			// ================

			fq_range = new Range(frequencie_A_170Hz_commercial, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_A_170Hz_commercial = true;
				}
			}

			fq_range = new Range(frequencie_B_170Hz_commercial, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_B_170Hz_commercial = true;
				}
			}

			// ==============
			// 170Hz AMATEURS
			// ==============

			fq_range = new Range(frequencie_A_170Hz_amateurs, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_A_170Hz_amateurs = true;
				}
			}

			fq_range = new Range(frequencie_B_170Hz_amateurs, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_B_170Hz_amateurs = true;
				}
			}

			// ================
			// 450Hz COMMERCIAL
			// ================

			fq_range = new Range(frequencie_A_450Hz_commercial, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_A_450Hz_commercial = true;
				}
			}

			fq_range = new Range(frequencie_B_450Hz_commercial, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_B_450Hz_commercial = true;
				}
			}

			// ================
			// 850Hz COMMERCIAL
			// ================

			fq_range = new Range(frequencie_A_850Hz_commercial, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_A_850Hz_commercial = true;
				}
			}

			fq_range = new Range(frequencie_B_850Hz_commercial, MARGIN_ERROR, 0);
			tmp_range = fq_range.get();
			for(int j = 0; j < tmp_range.length; j++) {
				if(fq == tmp_range[j]) {
					find_frequencie_B_850Hz_commercial = true;
				}
			}
		}
	}

	public boolean isCommercial85Hz() {
		if(find_frequencie_A_85Hz_commercial && find_frequencie_B_85Hz_commercial)
			return true;
		return false;
	}

	public boolean isCommercial170Hz() {
		if(find_frequencie_A_170Hz_commercial && find_frequencie_B_170Hz_commercial)
			return true;
		return false;
	}

	public boolean isAmateurs170Hz() {
		if(find_frequencie_A_170Hz_amateurs && find_frequencie_B_170Hz_amateurs)
			return true;
		return false;
	}

	public boolean isCommercial450Hz() {
		if(find_frequencie_A_450Hz_commercial && find_frequencie_B_450Hz_commercial)
			return true;
		return false;
	}

	public boolean isCommercial850Hz() {
		if(find_frequencie_A_850Hz_commercial && find_frequencie_B_850Hz_commercial)
			return true;
		return false;
	}
}
