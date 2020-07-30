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

// InmarsatAero - TDM

// @NOT_HF

import com.tortillum.salome.salome.Pair;
import com.tortillum.salome.salome.Range;

public class InmarsatAero {
    private int MARGIN_ERROR = 5; // in Hertz

    //  10.5 kbps
    private int frequencie_A_10_5 = 7015;
    private int frequencie_B_10_5 = 6360;
    private boolean find_frequencie_A_10_5 = false;
    private boolean find_frequencie_B_10_5 = false;
    private int margin_frequencies_10_5 = (frequencie_A_10_5 - frequencie_B_10_5) / 2;
    private int frequencie_MID_A_10_5 = frequencie_A_10_5 - margin_frequencies_10_5;
    private int frequencie_MID_B_10_5 = frequencie_B_10_5 + margin_frequencies_10_5;
    private int score_10_5 = 0;

    // 600 bps
    private int frequencie_A_600 = 1225;
    private int frequencie_B_600 = 1110;
    private boolean find_frequencie_A_600 = false;
    private boolean find_frequencie_B_600 = false;
    private int margin_frequencies_600 = (frequencie_A_600 - frequencie_B_600) / 2;
    private int frequencie_MID_A_600 = frequencie_A_600 - margin_frequencies_600;
    private int frequencie_MID_B_600 = frequencie_B_600 + margin_frequencies_600;
    private int score_600 = 0;

    public InmarsatAero(int sampleRate, int n_fft, Pair[] listFqs) {
        for (int i = 0; i < 64; i++) {
            int fq = listFqs[i].index * sampleRate / n_fft;

            // =========
            // 10.5 kbps
            // =========

            Range fq_range = new Range(frequencie_A_10_5, MARGIN_ERROR, 0);
            int[] tmp_range = fq_range.get();
            for(int j = 0; j < tmp_range.length; j++) {
                if(i < 12) {
                    if(fq == tmp_range[j]) {
                        find_frequencie_A_10_5 = true;
                    }
                }
            }

            fq_range = new Range(frequencie_B_10_5, MARGIN_ERROR, 0);
            tmp_range = fq_range.get();
            for(int j = 0; j < tmp_range.length; j++) {
                if(i < 12) {
                    if(fq == tmp_range[j]) {
                        find_frequencie_B_10_5 = true;
                    }
                }
            }

            if(find_frequencie_A_10_5 && find_frequencie_B_10_5) {
                fq_range = new Range(frequencie_MID_A_10_5, margin_frequencies_10_5 + MARGIN_ERROR, 1);
                tmp_range = fq_range.get();
                for(int j = 0; j < tmp_range.length; j++) {
                    if(fq == tmp_range[j]) {
                        score_10_5++;
                    }
                }

                fq_range = new Range(frequencie_MID_B_10_5, margin_frequencies_10_5 + MARGIN_ERROR, 2);
                tmp_range = fq_range.get();
                for(int j = 0; j < tmp_range.length; j++) {
                    if(fq == tmp_range[j]) {
                        score_10_5++;
                    }
                }
            }

            // =======
            // 600 bps
            // =======

            fq_range = new Range(frequencie_A_600, MARGIN_ERROR, 0);
            tmp_range = fq_range.get();
            for(int j = 0; j < tmp_range.length; j++) {
                if(i < 32) {
                    if(fq == tmp_range[j]) {
                        find_frequencie_A_600 = true;
                    }
                }
            }

            fq_range = new Range(frequencie_B_600, MARGIN_ERROR, 0);
            tmp_range = fq_range.get();
            for(int j = 0; j < tmp_range.length; j++) {
                if(i < 32) {
                    if(fq == tmp_range[j]) {
                        find_frequencie_B_600 = true;
                    }
                }
            }

            if(find_frequencie_A_600 && find_frequencie_B_600) {
                fq_range = new Range(frequencie_MID_A_600, margin_frequencies_600 + MARGIN_ERROR, 1);
                tmp_range = fq_range.get();
                for(int j = 0; j < tmp_range.length; j++) {
                    if(fq == tmp_range[j]) {
                        score_600++;
                    }
                }

                fq_range = new Range(frequencie_MID_B_600, margin_frequencies_600 + MARGIN_ERROR, 2);
                tmp_range = fq_range.get();
                for(int j = 0; j < tmp_range.length; j++) {
                    if(fq == tmp_range[j]) {
                        score_600++;
                    }
                }
            }
        }
    }

    public boolean is10_5() {
        if(find_frequencie_A_10_5 && find_frequencie_B_10_5 && score_10_5 <= 20 && score_10_5 >= 15)
            return true;
        return false;
    }

    public boolean is600() {
        if(find_frequencie_A_600 && find_frequencie_B_600 && score_600 <= 15 && score_600 >= 5)
            return true;
        return false;
    }
}
