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

// NAME : STANAG 4285 GEN
// REVISION : 2

package com.tortillum.salome.signal;

import com.tortillum.salome.detector.D1_TONE;
import com.tortillum.salome.detector.D2_TONES;
import com.tortillum.salome.other.Pair;

public class STANAG_4285_GEN {
    private int MARGIN_ERROR = 5; // in Hertz
    private int n_fft;

    // 4285 (GEN) (src: FUG8)
    private int top_GEN_A = 12;
    private int top_GEN_B = 12;
    private int top_GEN_C = 16;
    private int top_GEN_SFFT = 16;

    private D2_TONES d2_tones_GEN_A;
    private int frequency_A_GEN_A = 2413;
    private int frequencie_B_GEN_A = 1401;

    private D2_TONES d2_tones_GEN_B;
    private int frequencie_A_GEN_B = 2188;
    private int frequencie_B_GEN_B = 1401;

    private D2_TONES d2_tones_GEN_C;
    private int frequencie_A_GEN_C = 2642;
    private int frequencie_B_GEN_C = 636;

    private D1_TONE d1_tone_GEN_1;
    private int frequency_tone_GEN_1 = 1550;

    private D1_TONE d1_tone_GEN_2;
    private int frequency_tone_GEN_2 = 2713;

    private D1_TONE d1_tone_GEN_3;
    private int frequency_tone_GEN_3 = 1335;

    private D1_TONE d1_tone_GEN_4;
    private int frequency_tone_GEN_4 = 1507;

    private D1_TONE d1_tone_GEN_5;
    private int frequency_tone_GEN_5 = 2282;

    private D1_TONE d1_tone_GEN_6;
    private int frequency_tone_GEN_6 = 2454;

    private D1_TONE d1_tone_GEN_7;
    private int frequency_tone_GEN_7 = 2583;

    private D1_TONE d1_tone_GEN_8;
    private int frequency_tone_GEN_8 = 1421;

    private D1_TONE d1_tone_GEN_9;
    private int frequency_tone_GEN_9 = 1119;

    public STANAG_4285_GEN(int sampleRate, int pN_fft, Pair[] listFqs) {
        n_fft = pN_fft;

        // MODE FFT
        if (n_fft == 65536) {
            // GEN
            d2_tones_GEN_A = new D2_TONES(frequency_A_GEN_A, frequencie_B_GEN_A, MARGIN_ERROR, top_GEN_A, sampleRate, n_fft, listFqs); // Variant A
            d2_tones_GEN_B = new D2_TONES(frequencie_A_GEN_B, frequencie_B_GEN_B, MARGIN_ERROR, top_GEN_B, sampleRate, n_fft, listFqs); // Variant B
            d2_tones_GEN_C = new D2_TONES(frequencie_A_GEN_C, frequencie_B_GEN_C, MARGIN_ERROR, top_GEN_C, sampleRate, n_fft, listFqs); // Variant C
        } else { // MODE SFFT
            d1_tone_GEN_1 = new D1_TONE(frequency_tone_GEN_1, MARGIN_ERROR, top_GEN_SFFT, sampleRate, n_fft, listFqs); // Variant 1
            d1_tone_GEN_2 = new D1_TONE(frequency_tone_GEN_2, MARGIN_ERROR, top_GEN_SFFT, sampleRate, n_fft, listFqs); // Variant 2
            d1_tone_GEN_3 = new D1_TONE(frequency_tone_GEN_3, MARGIN_ERROR, top_GEN_SFFT, sampleRate, n_fft, listFqs); // Variant 3
            d1_tone_GEN_4 = new D1_TONE(frequency_tone_GEN_4, MARGIN_ERROR, top_GEN_SFFT, sampleRate, n_fft, listFqs); // Variant 4
            d1_tone_GEN_5 = new D1_TONE(frequency_tone_GEN_5, MARGIN_ERROR, top_GEN_SFFT, sampleRate, n_fft, listFqs); // Variant 5
            d1_tone_GEN_6 = new D1_TONE(frequency_tone_GEN_6, MARGIN_ERROR, top_GEN_SFFT, sampleRate, n_fft, listFqs); // Variant 6
            d1_tone_GEN_7 = new D1_TONE(frequency_tone_GEN_7, MARGIN_ERROR, top_GEN_SFFT, sampleRate, n_fft, listFqs); // Variant 7
            d1_tone_GEN_8 = new D1_TONE(frequency_tone_GEN_8, MARGIN_ERROR, top_GEN_SFFT, sampleRate, n_fft, listFqs); // Variant 8
            d1_tone_GEN_9 = new D1_TONE(frequency_tone_GEN_9, MARGIN_ERROR, top_GEN_SFFT, sampleRate, n_fft, listFqs); // Variant 9
        }
    }

    public int getScore() {
        int tmpScore = 0;

        if (n_fft == 65536) {
            if (d2_tones_GEN_A.check())
                tmpScore += 1;
            if (d2_tones_GEN_B.check())
                tmpScore += 1;
            if(d2_tones_GEN_C.check())
                tmpScore += 1;

        } else { // SFFT
            if (d1_tone_GEN_1.check())
                tmpScore += 1;

            if (d1_tone_GEN_2.check())
                tmpScore += 1;

            if (d1_tone_GEN_3.check())
                tmpScore += 1;

            if (d1_tone_GEN_4.check())
                tmpScore += 1;

            if (d1_tone_GEN_5.check())
                tmpScore += 1;

            if (d1_tone_GEN_6.check())
                tmpScore += 1;

            if (d1_tone_GEN_7.check())
                tmpScore += 1;

            if (d1_tone_GEN_8.check())
                tmpScore += 1;

            if (d1_tone_GEN_9.check())
                tmpScore += 1;
        }

        return tmpScore;
    }
}
