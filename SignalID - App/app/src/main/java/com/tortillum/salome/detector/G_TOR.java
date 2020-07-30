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

// G-TOR

import com.tortillum.salome.salome.Pair;
import com.tortillum.salome.salome.Range;

public class G_TOR {
    private int MARGIN_ERROR = 5; // in Hertz

    // 100 bd
    private int frequencie_A_100 = 1195;
    private int frequencie_B_100 = 995;
    private boolean find_frequencie_A_100 = false;
    private boolean find_frequencie_B_100 = false;
    private int margin_frequencies_100 = (frequencie_A_100 - frequencie_B_100) / 2;
    private int frequencie_MID_A_100 = frequencie_A_100 - margin_frequencies_100;
    private int frequencie_MID_B_100 = frequencie_B_100 + margin_frequencies_100;
    private int score_100 = 0;

    // 200 bd
    private int frequencie_A_200 = 2315;
    private int frequencie_B_200 = 2215;
    private boolean find_frequencie_A_200 = false;
    private boolean find_frequencie_B_200 = false;
    private int margin_frequencies_200 = (frequencie_A_200 - frequencie_B_200) / 2;
    private int frequencie_MID_A_200 = frequencie_A_200 - margin_frequencies_200;
    private int frequencie_MID_B_200 = frequencie_B_200 + margin_frequencies_200;
    private int score_200 = 0;

    // 300 bd
    private int frequencie_A_300 = 1105;
    private int frequencie_B_300 = 955;
    private boolean find_frequencie_A_300 = false;
    private boolean find_frequencie_B_300 = false;
    private int margin_frequencies_300 = (frequencie_A_300 - frequencie_B_300) / 2;
    private int frequencie_MID_A_300 = frequencie_A_300 - margin_frequencies_300;
    private int frequencie_MID_B_300 = frequencie_B_300 + margin_frequencies_300;
    private int score_300 = 0;

    public G_TOR(int sampleRate, int n_fft, Pair[] listFqs) {
        for (int i = 0; i < 64; i++) {
            int fq = listFqs[i].index * sampleRate / n_fft;

            // ======
            // 100 bd
            // ======
            Range fq_range = new Range(frequencie_A_100, MARGIN_ERROR, 0);
            int[] tmp_range = fq_range.get();
            for(int j = 0; j < tmp_range.length; j++) {
                if(i < 12) {
                    if(fq == tmp_range[j]) {
                        find_frequencie_A_100 = true;
                    }
                }
            }

            fq_range = new Range(frequencie_B_100, MARGIN_ERROR, 0);
            tmp_range = fq_range.get();
            for(int j = 0; j < tmp_range.length; j++) {
                if(i < 12) {
                    if(fq == tmp_range[j]) {
                        find_frequencie_B_100 = true;
                    }
                }
            }

            if(find_frequencie_A_100 && find_frequencie_B_100) {
                fq_range = new Range(frequencie_MID_A_100, margin_frequencies_100 + MARGIN_ERROR, 1);
                tmp_range = fq_range.get();
                for(int j = 0; j < tmp_range.length; j++) {
                    if(fq == tmp_range[j]) {
                        score_100++;
                    }
                }

                fq_range = new Range(frequencie_MID_B_100, margin_frequencies_100 + MARGIN_ERROR, 2);
                tmp_range = fq_range.get();
                for(int j = 0; j < tmp_range.length; j++) {
                    if(fq == tmp_range[j]) {
                        score_100++;
                    }
                }
            }

            // ======
            // 200 bd
            // ======
            fq_range = new Range(frequencie_A_200, MARGIN_ERROR, 0);
            tmp_range = fq_range.get();
            for(int j = 0; j < tmp_range.length; j++) {
                if(i < 16) {
                    if(fq == tmp_range[j]) {
                        find_frequencie_A_200 = true;
                    }
                }
            }

            fq_range = new Range(frequencie_B_200, MARGIN_ERROR, 0);
            tmp_range = fq_range.get();
            for(int j = 0; j < tmp_range.length; j++) {
                if(i < 16) {
                    if(fq == tmp_range[j]) {
                        find_frequencie_B_200 = true;
                    }
                }
            }

            if(find_frequencie_A_200 && find_frequencie_B_200) {
                fq_range = new Range(frequencie_MID_A_200, margin_frequencies_200 + MARGIN_ERROR, 1);
                tmp_range = fq_range.get();
                for(int j = 0; j < tmp_range.length; j++) {
                    if(fq == tmp_range[j]) {
                        score_200++;
                    }
                }

                fq_range = new Range(frequencie_MID_B_200, margin_frequencies_200 + MARGIN_ERROR, 2);
                tmp_range = fq_range.get();
                for(int j = 0; j < tmp_range.length; j++) {
                    if(fq == tmp_range[j]) {
                        score_200++;
                    }
                }
            }

            // ======
            // 300 bd
            // ======
            fq_range = new Range(frequencie_A_300, MARGIN_ERROR, 0);
            tmp_range = fq_range.get();
            for(int j = 0; j < tmp_range.length; j++) {
                if(i < 16) {
                    if(fq == tmp_range[j]) {
                        find_frequencie_A_300 = true;
                    }
                }
            }

            fq_range = new Range(frequencie_B_300, MARGIN_ERROR, 0);
            tmp_range = fq_range.get();
            for(int j = 0; j < tmp_range.length; j++) {
                if(i < 16) {
                    if(fq == tmp_range[j]) {
                        find_frequencie_B_300 = true;
                    }
                }
            }

            if(find_frequencie_A_300 && find_frequencie_B_300) {
                fq_range = new Range(frequencie_MID_A_300, margin_frequencies_300 + MARGIN_ERROR, 1);
                tmp_range = fq_range.get();
                for(int j = 0; j < tmp_range.length; j++) {
                    if(fq == tmp_range[j]) {
                        score_300++;
                    }
                }

                fq_range = new Range(frequencie_MID_B_300, margin_frequencies_300 + MARGIN_ERROR, 2);
                tmp_range = fq_range.get();
                for(int j = 0; j < tmp_range.length; j++) {
                    if(fq == tmp_range[j]) {
                        score_300++;
                    }
                }
            }
        }
    }

    public boolean is100() {
        if(find_frequencie_A_100 && find_frequencie_B_100 && score_100 <= 60 && score_100 >= 50)
            return true;
        return false;
    }

    public boolean is200() {
        if(find_frequencie_A_200 && find_frequencie_B_200 && score_200 <= 45 && score_200 >= 30)
            return true;
        return false;
    }

    public boolean is300() {
        if(find_frequencie_A_300 && find_frequencie_B_300 && score_300 <= 40 && score_300 >= 20)
            return true;
        return false;
    }
}
