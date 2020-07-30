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

// Funcube-1

// @NOT_HF

import com.tortillum.salome.salome.Pair;
import com.tortillum.salome.salome.Range;

public class Funcube1 {
    private int MARGIN_ERROR = 5; // in Hertz

    private int frequencie_A = 2075;
    private int frequencie_B = 1035;
    private boolean find_frequencie_A = false;
    private boolean find_frequencie_B = false;
    private int margin_frequencies = (frequencie_A - frequencie_B) / 2;
    private int frequencie_MID_A = frequencie_A - margin_frequencies;
    private int frequencie_MID_B = frequencie_B + margin_frequencies;
    private int score = 0;

    public Funcube1(int sampleRate, int n_fft, Pair[] listFqs) {
        for (int i = 0; i < 64; i++) {
            int fq = listFqs[i].index * sampleRate / n_fft;

            Range fq_range = new Range(frequencie_A, MARGIN_ERROR, 0);
            int[] tmp_range = fq_range.get();
            for(int j = 0; j < tmp_range.length; j++) {
                if(i < 32) {
                    if(fq == tmp_range[j]) {
                        find_frequencie_A = true;
                    }
                }
            }

            fq_range = new Range(frequencie_B, MARGIN_ERROR, 0);
            tmp_range = fq_range.get();
            for(int j = 0; j < tmp_range.length; j++) {
                if(i < 32) {
                    if(fq == tmp_range[j]) {
                        find_frequencie_B = true;
                    }
                }
            }

            if(find_frequencie_A && find_frequencie_B) {
                fq_range = new Range(frequencie_MID_A, margin_frequencies + MARGIN_ERROR, 1);
                tmp_range = fq_range.get();
                for(int j = 0; j < tmp_range.length; j++) {
                    if(fq == tmp_range[j]) {
                        score++;
                    }
                }

                fq_range = new Range(frequencie_MID_B, margin_frequencies + MARGIN_ERROR, 2);
                tmp_range = fq_range.get();
                for(int j = 0; j < tmp_range.length; j++) {
                    if(fq == tmp_range[j]) {
                        score++;
                    }
                }
            }
        }
    }

    public boolean isFuncube1() {
        if(find_frequencie_A && find_frequencie_B && score <= 45 && score >= 30)
            return true;
        return false;
    }
}
