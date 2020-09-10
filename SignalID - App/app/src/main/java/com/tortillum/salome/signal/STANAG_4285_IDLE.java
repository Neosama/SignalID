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

// NAME : STANAG 4285 IDLE
// REVISION : 1

package com.tortillum.salome.signal;

import com.tortillum.salome.detector.D1_TONE;
import com.tortillum.salome.detector.D2_TONES;
import com.tortillum.salome.other.Pair;

public class STANAG_4285_IDLE {
    private int MARGIN_ERROR = 5; // in Hertz
    private int n_fft;

    // 4285 (IDLE)
    private int top_IDLE_A = 12;
    private int top_IDLE_SFFT = 16;

    private D2_TONES d2_tones_IDLE_A;
    private int frequency_A_IDLE_A = 2491;
    private int frequencie_B_IDLE_A = 644;

    private D1_TONE d1_tone_IDLE_1;
    private int frequency_tone_IDLE_1 = 1119;

    private D1_TONE d1_tone_IDLE_2;
    private int frequency_tone_IDLE_2 = 2325;

    private D1_TONE d1_tone_IDLE_3;
    private int frequency_tone_IDLE_3 = 1808;

    private D1_TONE d1_tone_IDLE_4;
    private int frequency_tone_IDLE_4 = 818;

    private D1_TONE d1_tone_IDLE_5;
    private int frequency_tone_IDLE_5 = 2067;

    private D1_TONE d1_tone_IDLE_6;
    private int frequency_tone_IDLE_6 = 2368;

    private D1_TONE d1_tone_IDLE_7;
    private int frequency_tone_IDLE_7 = 1679;

    private D1_TONE d1_tone_IDLE_8;
    private int frequency_tone_IDLE_8 = 1076;

    private D1_TONE d1_tone_IDLE_9;
    private int frequency_tone_IDLE_9 = 1464;

    private D1_TONE d1_tone_IDLE_10;
    private int frequency_tone_IDLE_10 = 1722;

    private D1_TONE d1_tone_IDLE_11;
    private int frequency_tone_IDLE_11 = 1421;

    private D1_TONE d1_tone_IDLE_12;
    private int frequency_tone_IDLE_12 = 1378;

    private D1_TONE d1_tone_IDLE_13;
    private int frequency_tone_IDLE_13 = 2196;

    private D1_TONE d1_tone_IDLE_14;
    private int frequency_tone_IDLE_14 = 645;

    private D1_TONE d1_tone_IDLE_15;
    private int frequency_tone_IDLE_15 = 1335;

    private D1_TONE d1_tone_IDLE_16;
    private int frequency_tone_IDLE_16 = 1205;

    private D1_TONE d1_tone_IDLE_17;
    private int frequency_tone_IDLE_17 = 2153;

    private D1_TONE d1_tone_IDLE_18;
    private int frequency_tone_IDLE_18 = 2110;

    private D1_TONE d1_tone_IDLE_19;
    private int frequency_tone_IDLE_19 = 1894;

    private D1_TONE d1_tone_IDLE_20;
    private int frequency_tone_IDLE_20 = 1937;

    private D1_TONE d1_tone_IDLE_21;
    private int frequency_tone_IDLE_21 = 2497;

    public STANAG_4285_IDLE(int sampleRate, int pN_fft, Pair[] listFqs) {
        n_fft = pN_fft;

        // MODE FFT
        if (n_fft == 65536) {
            d2_tones_IDLE_A = new D2_TONES(frequency_A_IDLE_A, frequencie_B_IDLE_A, MARGIN_ERROR, top_IDLE_A, sampleRate, n_fft, listFqs); // Variant A
        } else { // MODE SFFT
            d1_tone_IDLE_1 = new D1_TONE(frequency_tone_IDLE_1, MARGIN_ERROR, top_IDLE_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_IDLE_2 = new D1_TONE(frequency_tone_IDLE_2, MARGIN_ERROR, top_IDLE_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_IDLE_3 = new D1_TONE(frequency_tone_IDLE_3, MARGIN_ERROR, top_IDLE_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_IDLE_4 = new D1_TONE(frequency_tone_IDLE_4, MARGIN_ERROR, top_IDLE_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_IDLE_5 = new D1_TONE(frequency_tone_IDLE_5, MARGIN_ERROR, top_IDLE_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_IDLE_6 = new D1_TONE(frequency_tone_IDLE_6, MARGIN_ERROR, top_IDLE_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_IDLE_7 = new D1_TONE(frequency_tone_IDLE_7, MARGIN_ERROR, top_IDLE_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_IDLE_8 = new D1_TONE(frequency_tone_IDLE_8, MARGIN_ERROR, top_IDLE_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_IDLE_9 = new D1_TONE(frequency_tone_IDLE_9, MARGIN_ERROR, top_IDLE_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_IDLE_10 = new D1_TONE(frequency_tone_IDLE_10, MARGIN_ERROR, top_IDLE_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_IDLE_11 = new D1_TONE(frequency_tone_IDLE_11, MARGIN_ERROR, top_IDLE_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_IDLE_12 = new D1_TONE(frequency_tone_IDLE_12, MARGIN_ERROR, top_IDLE_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_IDLE_13 = new D1_TONE(frequency_tone_IDLE_13, MARGIN_ERROR, top_IDLE_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_IDLE_14 = new D1_TONE(frequency_tone_IDLE_14, MARGIN_ERROR, top_IDLE_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_IDLE_15 = new D1_TONE(frequency_tone_IDLE_15, MARGIN_ERROR, top_IDLE_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_IDLE_16 = new D1_TONE(frequency_tone_IDLE_16, MARGIN_ERROR, top_IDLE_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_IDLE_17 = new D1_TONE(frequency_tone_IDLE_17, MARGIN_ERROR, top_IDLE_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_IDLE_18 = new D1_TONE(frequency_tone_IDLE_18, MARGIN_ERROR, top_IDLE_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_IDLE_19 = new D1_TONE(frequency_tone_IDLE_19, MARGIN_ERROR, top_IDLE_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_IDLE_20 = new D1_TONE(frequency_tone_IDLE_20, MARGIN_ERROR, top_IDLE_SFFT, sampleRate, n_fft, listFqs);
            d1_tone_IDLE_21 = new D1_TONE(frequency_tone_IDLE_21, MARGIN_ERROR, top_IDLE_SFFT, sampleRate, n_fft, listFqs);
        }
    }

    public int getScore() {
        int tmpScore = 0;

        if (n_fft == 65536) {
            if (d2_tones_IDLE_A.check())
                tmpScore += 1;

        } else { // SFFT
            if (d1_tone_IDLE_1.check())
                tmpScore += 1;

            if (d1_tone_IDLE_2.check())
                tmpScore += 1;

            if (d1_tone_IDLE_3.check())
                tmpScore += 1;

            if (d1_tone_IDLE_4.check())
                tmpScore += 1;

            if (d1_tone_IDLE_5.check())
                tmpScore += 1;

            if (d1_tone_IDLE_6.check())
                tmpScore += 1;

            if (d1_tone_IDLE_7.check())
                tmpScore += 1;

            if (d1_tone_IDLE_8.check())
                tmpScore += 1;

            if (d1_tone_IDLE_9.check())
                tmpScore += 1;

            if (d1_tone_IDLE_10.check())
                tmpScore += 1;

            if (d1_tone_IDLE_11.check())
                tmpScore += 1;

            if (d1_tone_IDLE_12.check())
                tmpScore += 1;

            if (d1_tone_IDLE_13.check())
                tmpScore += 1;

            if (d1_tone_IDLE_14.check())
                tmpScore += 1;

            if (d1_tone_IDLE_15.check())
                tmpScore += 1;

            if (d1_tone_IDLE_16.check())
                tmpScore += 1;

            if (d1_tone_IDLE_17.check())
                tmpScore += 1;

            if (d1_tone_IDLE_18.check())
                tmpScore += 1;

            if (d1_tone_IDLE_19.check())
                tmpScore += 1;

            if (d1_tone_IDLE_20.check())
                tmpScore += 1;

            if (d1_tone_IDLE_21.check())
                tmpScore += 1;
        }

        return tmpScore;
    }
}
