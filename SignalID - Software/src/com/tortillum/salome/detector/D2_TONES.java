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

import com.tortillum.salome.other.Pair;
import com.tortillum.salome.other.Range;

public class D2_TONES {
    private int MARGIN_ERROR = 5; // in Hertz

    private boolean find_frequencie_A = false;
    private boolean find_frequencie_B = false;

    public D2_TONES(int frequencie_A, int frequencie_B, int marginError, int topA, int sampleRate, int n_fft, Pair[] listFqs) {
        MARGIN_ERROR = marginError;

        for(int i = 0; i < listFqs.length; i++) {
            int fq = listFqs[i].index * sampleRate / n_fft;

            Range fq_range = new Range(frequencie_A, MARGIN_ERROR, 0);
            int[] tmp_range = fq_range.get();
            for (int value : tmp_range) {
                if (i < topA) {
                    if (fq == value) {
                        find_frequencie_A = true;
                    }
                }
            }

            fq_range = new Range(frequencie_B, MARGIN_ERROR, 0);
            tmp_range = fq_range.get();
            for (int value : tmp_range) {
                if (i < topA) {
                    if (fq == value) {
                        find_frequencie_B = true;
                    }
                }
            }
        }
    }

    public boolean check() {
        return find_frequencie_A && find_frequencie_B;
    }
}
