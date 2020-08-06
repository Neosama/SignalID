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

package detector;

import salome.Pair;
import salome.Range;

public class D1_TONES {
    private int MARGIN_ERROR = 5; // in Hertz

    private boolean find_frequencie_A = false;

    public D1_TONES(int frequencie_A, int marginError, int topA, int sampleRate, int n_fft, Pair[] listFqs) {
        MARGIN_ERROR = marginError;

        for(int i = 0; i < listFqs.length; i++) {
            int fq = listFqs[i].index * sampleRate / n_fft;

            Range fq_range = new Range(frequencie_A, MARGIN_ERROR, 0);
            int[] tmp_range = fq_range.get();
            for(int j = 0; j < tmp_range.length; j++) {
                if(i < topA) {
                    if(fq == tmp_range[j]) {
                        find_frequencie_A = true;
                    }
                }
            }
        }
    }

    public boolean check() {
        return find_frequencie_A;
    }

}
