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

// Link-11

import com.tortillum.salome.salome.Pair;
import com.tortillum.salome.salome.Range;

public class Link11 {
    private int MARGIN_ERROR = 5; // in Hertz

    // CLEW
    private int frequencie_A_CLEW = 615;
    private int frequencie_B_CLEW = 600;
    private boolean find_frequencie_A_CLEW = false;
    private boolean find_frequencie_B_CLEW = false;
    private int margin_frequencies_CLEW = (frequencie_A_CLEW - frequencie_B_CLEW) / 2;
    private int frequencie_MID_A_CLEW = frequencie_A_CLEW - margin_frequencies_CLEW;
    private int frequencie_MID_B_CLEW = frequencie_B_CLEW + margin_frequencies_CLEW;
    private int score_CLEW = 0;

    // SLEW
    private int frequencie_A_SLEW = 630;
    private int frequencie_B_SLEW = 620;
    private boolean find_frequencie_A_SLEW = false;
    private boolean find_frequencie_B_SLEW = false;
    private int margin_frequencies_SLEW = (frequencie_A_SLEW - frequencie_B_SLEW) / 2;
    private int frequencie_MID_A_SLEW = frequencie_A_SLEW - margin_frequencies_SLEW;
    private int frequencie_MID_B_SLEW = frequencie_B_SLEW + margin_frequencies_SLEW;
    private int score_SLEW = 0;

    // GEN - CLEW
    private int frequencie_A_GEN_CLEW = 700;
    private int frequencie_B_GEN_CLEW = 110;
    private boolean find_frequencie_A_GEN_CLEW = false;
    private boolean find_frequencie_B_GEN_CLEW = false;
    private int margin_frequencies_GEN_CLEW = (frequencie_A_GEN_CLEW - frequencie_B_GEN_CLEW) / 2;
    private int frequencie_MID_A_GEN_CLEW = frequencie_A_GEN_CLEW - margin_frequencies_GEN_CLEW;
    private int frequencie_MID_B_GEN_CLEW = frequencie_B_GEN_CLEW + margin_frequencies_GEN_CLEW;
    private int score_GEN_CLEW = 0;

    public Link11(int sampleRate, int n_fft, Pair[] listFqs) {
        for (int i = 0; i < 64; i++) {
            int fq = listFqs[i].index * sampleRate / n_fft;

            // ====
            // CLEW
            // ====

            Range fq_range = new Range(frequencie_A_CLEW, MARGIN_ERROR, 0);
            int[] tmp_range = fq_range.get();
            for(int j = 0; j < tmp_range.length; j++) {
                if(i < 16) {
                    if(fq == tmp_range[j]) {
                        find_frequencie_A_CLEW = true;
                    }
                }
            }

            fq_range = new Range(frequencie_B_CLEW, MARGIN_ERROR, 0);
            tmp_range = fq_range.get();
            for(int j = 0; j < tmp_range.length; j++) {
                if(i < 16) {
                    if(fq == tmp_range[j]) {
                        find_frequencie_B_CLEW = true;
                    }
                }
            }

            if(find_frequencie_A_CLEW && find_frequencie_B_CLEW) {
                fq_range = new Range(frequencie_MID_A_CLEW, margin_frequencies_CLEW + MARGIN_ERROR, 1);
                tmp_range = fq_range.get();
                for(int j = 0; j < tmp_range.length; j++) {
                    if(fq == tmp_range[j]) {
                        score_CLEW++;
                    }
                }

                fq_range = new Range(frequencie_MID_B_CLEW, margin_frequencies_CLEW + MARGIN_ERROR, 2);
                tmp_range = fq_range.get();
                for(int j = 0; j < tmp_range.length; j++) {
                    if(fq == tmp_range[j]) {
                        score_CLEW++;
                    }
                }
            }

            // ====
            // SLEW
            // ====

            fq_range = new Range(frequencie_A_SLEW, MARGIN_ERROR, 0);
            tmp_range = fq_range.get();
            for(int j = 0; j < tmp_range.length; j++) {
                if(i < 16) {
                    if(fq == tmp_range[j]) {
                        find_frequencie_A_SLEW = true;
                    }
                }
            }

            fq_range = new Range(frequencie_B_SLEW, MARGIN_ERROR, 0);
            tmp_range = fq_range.get();
            for(int j = 0; j < tmp_range.length; j++) {
                if(i < 16) {
                    if(fq == tmp_range[j]) {
                        find_frequencie_B_SLEW = true;
                    }
                }
            }

            if(find_frequencie_A_SLEW && find_frequencie_B_SLEW) {
                fq_range = new Range(frequencie_MID_A_SLEW, margin_frequencies_SLEW + MARGIN_ERROR, 1);
                tmp_range = fq_range.get();
                for(int j = 0; j < tmp_range.length; j++) {
                    if(fq == tmp_range[j]) {
                        score_SLEW++;
                    }
                }

                fq_range = new Range(frequencie_MID_B_SLEW, margin_frequencies_SLEW + MARGIN_ERROR, 2);
                tmp_range = fq_range.get();
                for(int j = 0; j < tmp_range.length; j++) {
                    if(fq == tmp_range[j]) {
                        score_SLEW++;
                    }
                }
            }

            // ==========
            // GEN - CLEW
            // ==========

            fq_range = new Range(frequencie_A_GEN_CLEW, MARGIN_ERROR, 0);
            tmp_range = fq_range.get();
            for(int j = 0; j < tmp_range.length; j++) {
                if(i < 32) {
                    if(fq == tmp_range[j]) {
                        find_frequencie_A_GEN_CLEW = true;
                    }
                }
            }

            fq_range = new Range(frequencie_B_GEN_CLEW, MARGIN_ERROR, 0);
            tmp_range = fq_range.get();
            for(int j = 0; j < tmp_range.length; j++) {
                if(i < 32) {
                    if(fq == tmp_range[j]) {
                        find_frequencie_B_GEN_CLEW = true;
                    }
                }
            }

            if(find_frequencie_A_GEN_CLEW && find_frequencie_B_GEN_CLEW) {
                fq_range = new Range(frequencie_MID_A_GEN_CLEW, margin_frequencies_GEN_CLEW + MARGIN_ERROR, 1);
                tmp_range = fq_range.get();
                for(int j = 0; j < tmp_range.length; j++) {
                    if(fq == tmp_range[j]) {
                        score_GEN_CLEW++;
                    }
                }

                fq_range = new Range(frequencie_MID_B_GEN_CLEW, margin_frequencies_GEN_CLEW + MARGIN_ERROR, 2);
                tmp_range = fq_range.get();
                for(int j = 0; j < tmp_range.length; j++) {
                    if(fq == tmp_range[j]) {
                        score_GEN_CLEW++;
                    }
                }
            }
        }
    }

    public boolean isCLEW() {
        if(find_frequencie_A_CLEW && find_frequencie_B_CLEW && score_CLEW <= 30 && score_CLEW >= 20)
            return true;
        return false;
    }

    public boolean isGenCLEW() {
        if(find_frequencie_A_GEN_CLEW && find_frequencie_B_GEN_CLEW && score_GEN_CLEW <= 20 && score_GEN_CLEW >= 10)
            return true;
        return false;
    }

    public boolean isSLEW() {
        if(find_frequencie_A_SLEW && find_frequencie_B_SLEW && score_SLEW <= 25 && score_SLEW >= 10)
            return true;
        return false;
    }
}
