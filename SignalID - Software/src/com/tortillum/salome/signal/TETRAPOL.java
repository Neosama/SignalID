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

// NAME : TETRAPOL
// REVISION : 1

package com.tortillum.salome.signal;

import com.tortillum.salome.detector.D1_TONE;
import com.tortillum.salome.detector.D2_TONES;
import com.tortillum.salome.other.Pair;

public class TETRAPOL {
    private int MARGIN_ERROR = 5; // in Hertz
    private int n_fft;

    private int top = 12;

    D2_TONES d2_tones_A;
    private int frequencie_A_A = 1349;
    private int frequencie_B_A = 1199;

    D2_TONES d2_tones_B;
    private int frequencie_A_B = 1199;
    private int frequencie_B_B = 900;

    D2_TONES d2_tones_C;
    private int frequencie_A_C = 1349;
    private int frequencie_B_C = 1000;

    D2_TONES d2_tones_D;
    private int frequencie_A_D = 1150;
    private int frequencie_B_D = 1050;

    D1_TONE d1_tone;
    int[] listFrequencies_SFFT = {215, 258, 301, 344, 387, 430, 473, 516, 559, 602, 645, 689, 732, 775, 818, 861, 904, 947, 990, 1033, 1076, 1119, 1162, 1205, 1248, 1291, 1335, 1378, 1421, 1464, 1507, 1550, 1593, 1636, 1679, 1722, 1765, 1808, 1851, 1894, 1937, 1981, 2067, 2110};

    public TETRAPOL(int sampleRate, int pN_fft, Pair[] listFqs) {
        n_fft = pN_fft;

        // MODE FFT
        if (n_fft == 65536) {
            d2_tones_A = new D2_TONES(frequencie_A_A, frequencie_B_A, MARGIN_ERROR, top, sampleRate, n_fft, listFqs); // Variant A
            d2_tones_B = new D2_TONES(frequencie_A_B, frequencie_B_B, MARGIN_ERROR, top, sampleRate, n_fft, listFqs);
            d2_tones_C = new D2_TONES(frequencie_A_C, frequencie_B_C, MARGIN_ERROR, top, sampleRate, n_fft, listFqs);
            d2_tones_D = new D2_TONES(frequencie_A_D, frequencie_B_D, MARGIN_ERROR, top, sampleRate, n_fft, listFqs);
        } else { // MODE SFFT
            d1_tone = new D1_TONE(listFrequencies_SFFT, MARGIN_ERROR, top, sampleRate, n_fft, listFqs);
        }
    }

    public int getScore() {
        int tmpScore = 0;

        if (n_fft == 65536) {
            if (d2_tones_A.check())
                tmpScore += 1;

            if (d2_tones_B.check())
                tmpScore += 1;

            if (d2_tones_C.check())
                tmpScore += 1;

            if (d2_tones_D.check())
                tmpScore += 1;

        } else { // SFFT
            tmpScore += d1_tone.getScore();
        }

        return tmpScore;
    }
}
