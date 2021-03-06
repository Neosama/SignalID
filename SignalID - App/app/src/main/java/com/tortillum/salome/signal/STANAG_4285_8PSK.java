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

// NAME : STANAG 4285 8PSK
// REVISION : 1

package com.tortillum.salome.signal;

import com.tortillum.salome.detector.D1_TONE;
import com.tortillum.salome.detector.D2_TONES;
import com.tortillum.salome.other.Pair;

public class STANAG_4285_8PSK {
    private int MARGIN_ERROR = 5; // in Hertz
    private int n_fft;

    // 4285 (8PSK)
    private int top_A = 12;
    private int top_B = 12;
    private int top_C = 12;

    private int top_SFFT = 16;

    private D2_TONES d2_tones_A;
    private int frequency_A_A = 2397;
    private int frequencie_B_A = 1160;

    private D2_TONES d2_tones_B;
    private int frequency_A_B = 2397;
    private int frequencie_B_B = 2078;

    private D2_TONES d2_tones_C;
    private int frequency_A_C = 2078;
    private int frequencie_B_C = 1544;

    private D1_TONE d1_tone_1;
    private int frequency_tone_1 = 1162;

    private D1_TONE d1_tone_2;
    private int frequency_tone_2 = 1335;

    private D1_TONE d1_tone_3;
    private int frequency_tone_3 = 1464;

    private D1_TONE d1_tone_4;
    private int frequency_tone_4 = 1378;

    private D1_TONE d1_tone_5;
    private int frequency_tone_5 = 1248;

    private D1_TONE d1_tone_6;
    private int frequency_tone_6 = 2153;

    private D1_TONE d1_tone_7;
    private int frequency_tone_7 = 2110;

    private D1_TONE d1_tone_8;
    private int frequency_tone_8 = 1550;

    private D1_TONE d1_tone_9;
    private int frequency_tone_9 = 1851;

    private D1_TONE d1_tone_10;
    private int frequency_tone_10 = 1421;

    private D1_TONE d1_tone_11;
    private int frequency_tone_11 = 2411;

    private D1_TONE d1_tone_12;
    private int frequency_tone_12 = 2454;

    private D1_TONE d1_tone_13;
    private int frequency_tone_13 = 2627;

    private D1_TONE d1_tone_14;
    private int frequency_tone_14 = 2239;

    private D1_TONE d1_tone_15;
    private int frequency_tone_15 = 2799;

    private D1_TONE d1_tone_16;
    private int frequency_tone_16 = 2325;

    private D1_TONE d1_tone_17;
    private int frequency_tone_17 = 1937;

    private D1_TONE d1_tone_18;
    private int frequency_tone_18 = 2497;

    private D1_TONE d1_tone_19;
    private int frequency_tone_19 = 2540;

    private D1_TONE d1_tone_20;
    private int frequency_tone_20 = 2067;

    private D1_TONE d1_tone_21;
    private int frequency_tone_21 = 861;

    private D1_TONE d1_tone_22;
    private int frequency_tone_22 = 1291;

    public STANAG_4285_8PSK(int sampleRate, int pN_fft, Pair[] listFqs) {
        n_fft = pN_fft;

        // MODE FFT
        if (n_fft == 65536) {
            d2_tones_A = new D2_TONES(frequency_A_A, frequencie_B_A, MARGIN_ERROR, top_A, sampleRate, n_fft, listFqs); // Variant A
            d2_tones_B = new D2_TONES(frequency_A_B, frequencie_B_B, MARGIN_ERROR, top_B, sampleRate, n_fft, listFqs);
            d2_tones_C = new D2_TONES(frequency_A_C, frequencie_B_C, MARGIN_ERROR, top_C, sampleRate, n_fft, listFqs);
        } else { // MODE SFFT
            d1_tone_1 = new D1_TONE(frequency_tone_1, MARGIN_ERROR, top_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_2 = new D1_TONE(frequency_tone_2, MARGIN_ERROR, top_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_3 = new D1_TONE(frequency_tone_3, MARGIN_ERROR, top_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_4 = new D1_TONE(frequency_tone_4, MARGIN_ERROR, top_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_5 = new D1_TONE(frequency_tone_5, MARGIN_ERROR, top_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_6 = new D1_TONE(frequency_tone_6, MARGIN_ERROR, top_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_7 = new D1_TONE(frequency_tone_7, MARGIN_ERROR, top_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_8 = new D1_TONE(frequency_tone_8, MARGIN_ERROR, top_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_9 = new D1_TONE(frequency_tone_9, MARGIN_ERROR, top_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_10 = new D1_TONE(frequency_tone_10, MARGIN_ERROR, top_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_11 = new D1_TONE(frequency_tone_11, MARGIN_ERROR, top_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_12 = new D1_TONE(frequency_tone_12, MARGIN_ERROR, top_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_13 = new D1_TONE(frequency_tone_13, MARGIN_ERROR, top_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_14 = new D1_TONE(frequency_tone_14, MARGIN_ERROR, top_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_15 = new D1_TONE(frequency_tone_15, MARGIN_ERROR, top_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_16 = new D1_TONE(frequency_tone_16, MARGIN_ERROR, top_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_17 = new D1_TONE(frequency_tone_17, MARGIN_ERROR, top_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_18 = new D1_TONE(frequency_tone_18, MARGIN_ERROR, top_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_19 = new D1_TONE(frequency_tone_19, MARGIN_ERROR, top_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_20 = new D1_TONE(frequency_tone_20, MARGIN_ERROR, top_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_21 = new D1_TONE(frequency_tone_21, MARGIN_ERROR, top_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_22 = new D1_TONE(frequency_tone_22, MARGIN_ERROR, top_SFFT, sampleRate, n_fft, listFqs);
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

        } else { // SFFT
            if (d1_tone_1.check())
                tmpScore += 1;

            if (d1_tone_2.check())
                tmpScore += 1;

            if (d1_tone_3.check())
                tmpScore += 1;

            if (d1_tone_4.check())
                tmpScore += 1;

            if (d1_tone_5.check())
                tmpScore += 1;

            if (d1_tone_6.check())
                tmpScore += 1;

            if (d1_tone_7.check())
                tmpScore += 1;

            if (d1_tone_8.check())
                tmpScore += 1;

            if (d1_tone_9.check())
                tmpScore += 1;

            if (d1_tone_10.check())
                tmpScore += 1;

            if (d1_tone_11.check())
                tmpScore += 1;

            if (d1_tone_12.check())
                tmpScore += 1;

            if (d1_tone_13.check())
                tmpScore += 1;

            if (d1_tone_14.check())
                tmpScore += 1;

            if (d1_tone_15.check())
                tmpScore += 1;

            if (d1_tone_16.check())
                tmpScore += 1;

            if (d1_tone_17.check())
                tmpScore += 1;

            if (d1_tone_18.check())
                tmpScore += 1;

            if (d1_tone_19.check())
                tmpScore += 1;

            if (d1_tone_20.check())
                tmpScore += 1;

            if (d1_tone_21.check())
                tmpScore += 1;

            if (d1_tone_22.check())
                tmpScore += 1;
        }

        return tmpScore;
    }
}
