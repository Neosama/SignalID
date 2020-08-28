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

package com.tortillum.salome.tool;

import com.tortillum.salome.other.Pair;
import com.tortillum.salome.recognizer.Score;
import com.tortillum.salome.signal.ALE_400;
import com.tortillum.salome.signal.FT8;
import com.tortillum.salome.signal.INFO_SIGNAL;
import com.tortillum.salome.signal.RTTY;
import com.tortillum.salome.signal.STANAG_4285_8PSK;
import com.tortillum.salome.signal.STANAG_4285_GEN;
import com.tortillum.salome.signal.STANAG_4285_IDLE;
import com.tortillum.salome.signal.STANAG_4285_TFC;
import com.tortillum.salome.signal.STANAG_4285_TS3000;
import com.tortillum.salome.signal.TETRAPOL;
import com.tortillum.salome.wavReader.Reader;
import com.tortillum.salome.window.Hamming;

import java.io.IOException;
import java.util.Arrays;

import org.jtransforms.fft.DoubleFFT_1D;

public class T_FFT {
    private Pair[] frequenciesArray_fft = null;

    private int n_fft = 1024 * 64; // 65536

    public T_FFT(String pPath, Score[] pScores) throws IOException {
        Reader reader = new Reader(pPath, false);
        double[] allData = reader.getByteArray();

        if (allData.length < n_fft) {
            return;
        }

        Hamming windowHamming_fft = new Hamming();
        double[] hammingData_fft = windowHamming_fft.toHammingWindow(allData, allData.length);

        DoubleFFT_1D fft = new DoubleFFT_1D(n_fft);
        fft.realForward(hammingData_fft);

        double[] result_fft = new double[hammingData_fft.length / 2];
        for (int s = 0; s < result_fft.length; s++) {
            double re = hammingData_fft[s * 2];
            double im = hammingData_fft[s * 2 + 1];
            result_fft[s] = (double) Math.sqrt(re * re + im * im) / result_fft.length;
        }

        frequenciesArray_fft = new Pair[result_fft.length];

        for (int i = 0; i < result_fft.length; i++) {
            frequenciesArray_fft[i] = new Pair(i, result_fft[i]);
        }
        Arrays.sort(frequenciesArray_fft);

        // =================
        // HERE CHECK SIGNAL
        // =================

        // RTTY
        RTTY rtty_check = new RTTY(44100, n_fft, frequenciesArray_fft);
        if(rtty_check.isCommercial85Hz()) {
            pScores[INFO_SIGNAL.ID_RTTY_85_commercial].addScore_FFT(INFO_SIGNAL.WEIGHT_RTTY_85_commercial * INFO_SIGNAL.WEIGHT_RTTY_85_commercial);
        }

        if(rtty_check.isCommercial170Hz()) {
            pScores[INFO_SIGNAL.ID_RTTY_170_commercial].addScore_FFT(INFO_SIGNAL.WEIGHT_RTTY_170_commercial * INFO_SIGNAL.WEIGHT_RTTY_170_commercial);
        }

        if(rtty_check.isAmateurs170Hz()) {
            pScores[INFO_SIGNAL.ID_RTTY_170_amateurs].addScore_FFT(INFO_SIGNAL.WEIGHT_RTTY_170_amateurs * INFO_SIGNAL.WEIGHT_RTTY_170_amateurs);
        }

        if(rtty_check.isCommercial450Hz()) {
            pScores[INFO_SIGNAL.ID_RTTY_450_commercial].addScore_FFT(INFO_SIGNAL.WEIGHT_RTTY_450_commercial * INFO_SIGNAL.WEIGHT_RTTY_450_commercial);
        }

        if(rtty_check.isCommercial850Hz()) {
            pScores[INFO_SIGNAL.ID_RTTY_850_commercial].addScore_FFT(INFO_SIGNAL.WEIGHT_RTTY_850_commercial * INFO_SIGNAL.WEIGHT_RTTY_850_commercial);
        }

        // STANAG 4285 GEN
        STANAG_4285_GEN stanag_4285_GEN_check = new STANAG_4285_GEN(44100, n_fft, frequenciesArray_fft);
        pScores[INFO_SIGNAL.ID_STANAG_4285_GEN].addScore_FFT(stanag_4285_GEN_check.getScore() * INFO_SIGNAL.WEIGHT_STANAG_4285_GEN);

        // STANAG 4285 IDLE
        STANAG_4285_IDLE stanag_4285_IDLE_check = new STANAG_4285_IDLE(44100, n_fft, frequenciesArray_fft);
        pScores[INFO_SIGNAL.ID_STANAG_4285_IDLE].addScore_FFT(stanag_4285_IDLE_check.getScore() * INFO_SIGNAL.WEIGHT_STANAG_4285_IDLE);

        // STANAG 4285 TFC
        STANAG_4285_TFC stanag_4285_TFC_check = new STANAG_4285_TFC(44100, n_fft, frequenciesArray_fft);
        pScores[INFO_SIGNAL.ID_STANAG_4285_TFC].addScore_FFT(stanag_4285_TFC_check.getScore() * INFO_SIGNAL.WEIGHT_STANAG_4285_TFC);

        // STANAG 4285 8PSK
        STANAG_4285_8PSK stanag_4285_8PSK_check = new STANAG_4285_8PSK(44100, n_fft, frequenciesArray_fft);
        pScores[INFO_SIGNAL.ID_STANAG_4285_8PSK].addScore_FFT(stanag_4285_8PSK_check.getScore() * INFO_SIGNAL.WEIGHT_STANAG_4285_8PSK);

        // STANAG 4285 TS3000
        STANAG_4285_TS3000 stanag_4285_TS3000_check = new STANAG_4285_TS3000(44100, n_fft, frequenciesArray_fft);
        pScores[INFO_SIGNAL.ID_STANAG_4285_TS3000].addScore_FFT(stanag_4285_TS3000_check.getScore() * INFO_SIGNAL.WEIGHT_STANAG_4285_TS3000);

        // FT8
        FT8 ft8_check = new FT8(44100, n_fft, frequenciesArray_fft);
        pScores[INFO_SIGNAL.ID_FT8].addScore_FFT(ft8_check.getScore() * INFO_SIGNAL.WEIGHT_FT8);

        // TETRAPOL
        TETRAPOL tetrapol_check = new TETRAPOL(44100, n_fft, frequenciesArray_fft);
        pScores[INFO_SIGNAL.ID_TETRAPOL].addScore_FFT(tetrapol_check.getScore() * INFO_SIGNAL.WEIGHT_TETRAPOL);

        // ALE-400
        ALE_400 ale_400_check = new ALE_400(44100, n_fft, frequenciesArray_fft);
        pScores[INFO_SIGNAL.ID_ALE_400].addScore_FFT(ale_400_check.getScore() * INFO_SIGNAL.WEIGHT_ALE_400);
    }
}
