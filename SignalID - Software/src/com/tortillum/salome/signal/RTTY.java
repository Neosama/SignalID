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

// NAME : RTTY
// REVISION : 1

package com.tortillum.salome.signal;

import com.tortillum.salome.detector.D2_TONES;
import com.tortillum.salome.other.Pair;

public class RTTY {

    private int MARGIN_ERROR = 5; // in Hertz

    D2_TONES d2_tones_85Hz_commercial;
    private int frequencie_A_85Hz_commercial = 1040;
    private int frequencie_B_85Hz_commercial = 955;

    D2_TONES d2_tones_170Hz_commercial;
    private int frequencie_A_170Hz_commercial = 1085;
    private int frequencie_B_170Hz_commercial = 915;

    D2_TONES d2_tones_170Hz_amateurs;
    private int frequencie_A_170Hz_amateurs = 900;
    private int frequencie_B_170Hz_amateurs = 730;

    //private int frequencie_A_225Hz = ??;
    //private int frequencie_B_225Hz = ??;

    D2_TONES d2_tones_450Hz_commercial;
    private int frequencie_A_450Hz_commercial = 1225;
    private int frequencie_B_450Hz_commercial = 775;

    D2_TONES d2_tones_850Hz_commercial;
    private int frequencie_A_850Hz_commercial = 1425;
    private int frequencie_B_850Hz_commercial = 575;

    public RTTY(int sampleRate, int n_fft, Pair[] listFqs) {
        // 85Hz COMMERCIAL
        d2_tones_85Hz_commercial = new D2_TONES(frequencie_A_85Hz_commercial, frequencie_B_85Hz_commercial, MARGIN_ERROR, 12, sampleRate, n_fft, listFqs);

        // 170Hz COMMERCIAL
        d2_tones_170Hz_commercial = new D2_TONES(frequencie_A_170Hz_commercial, frequencie_B_170Hz_commercial, MARGIN_ERROR, 12, sampleRate, n_fft, listFqs);

        // 170Hz AMATEURS
        d2_tones_170Hz_amateurs = new D2_TONES(frequencie_A_170Hz_amateurs, frequencie_B_170Hz_amateurs, MARGIN_ERROR, 12, sampleRate, n_fft, listFqs);

        // 450Hz COMMERCIAL
        d2_tones_450Hz_commercial = new D2_TONES(frequencie_A_450Hz_commercial, frequencie_B_450Hz_commercial, MARGIN_ERROR, 12, sampleRate, n_fft, listFqs);

        // 850Hz COMMERCIAL
        d2_tones_850Hz_commercial = new D2_TONES(frequencie_A_850Hz_commercial, frequencie_B_850Hz_commercial, MARGIN_ERROR, 12, sampleRate, n_fft, listFqs);
    }

    public boolean isCommercial85Hz() {
        return d2_tones_85Hz_commercial.check();
    }

    public boolean isCommercial170Hz() {
        return d2_tones_170Hz_commercial.check();
    }

    public boolean isAmateurs170Hz() {
        return d2_tones_170Hz_amateurs.check();
    }

    public boolean isCommercial450Hz() {
        return d2_tones_450Hz_commercial.check();
    }

    public boolean isCommercial850Hz() {
        return d2_tones_850Hz_commercial.check();
    }
}
