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

import org.jtransforms.fft.DoubleFFT_1D;

import java.io.IOException;
import java.util.Arrays;

public class T_SFFT {
    private Pair[] frequenciesArray_sfft = null;

    private int n_sfft = 1024;
    private int windowStep = 128+1;

    public T_SFFT(String pPath, Score[] pScores) throws IOException {
        Reader reader = new Reader(pPath, false);
        double[] allData = reader.getByteArray();

        int totalWS = 0;
        for(int i = 0; i < allData.length/windowStep; i++) {
            if(totalWS+(allData.length/windowStep) < allData.length) {
                double[] WS_array = Arrays.copyOfRange(allData, totalWS, totalWS+(allData.length/windowStep));

                Hamming windowHamming_sfft = new Hamming();
                double[] hammingData_sfft = windowHamming_sfft.toHammingWindow(WS_array, WS_array.length);

                DoubleFFT_1D sfft = new DoubleFFT_1D(n_sfft);
                sfft.realForward(hammingData_sfft);

                double[] result_sfft = new double[hammingData_sfft.length / 2];
                for(int s = 0; s < result_sfft.length; s++) {
                    double re = hammingData_sfft[s * 2];
                    double im = hammingData_sfft[s * 2 + 1];
                    result_sfft[s] = (double) Math.sqrt(re * re + im * im) / result_sfft.length;
                }

                frequenciesArray_sfft = new Pair[result_sfft.length];

                for (int j = 0; j < result_sfft.length; j++) {
                    frequenciesArray_sfft[j] = new Pair(j, result_sfft[j]);
                }
                Arrays.sort(frequenciesArray_sfft);

                // =================
                // HERE CHECK SIGNAL
                // =================

                // RTTY
                RTTY rtty_check = new RTTY(44100, n_sfft, frequenciesArray_sfft);

                if(rtty_check.isCommercial85Hz()) {
                    pScores[INFO_SIGNAL.ID_RTTY_85_commercial].addScore_SFFT(INFO_SIGNAL.WEIGHT_RTTY_85_commercial * INFO_SIGNAL.WEIGHT_RTTY_85_commercial);
                }

                if(rtty_check.isCommercial170Hz()) {
                    pScores[INFO_SIGNAL.ID_RTTY_170_commercial].addScore_SFFT(INFO_SIGNAL.WEIGHT_RTTY_170_commercial * INFO_SIGNAL.WEIGHT_RTTY_170_commercial);
                }

                if(rtty_check.isAmateurs170Hz()) {
                    pScores[INFO_SIGNAL.ID_RTTY_170_amateurs].addScore_SFFT(INFO_SIGNAL.WEIGHT_RTTY_170_amateurs * INFO_SIGNAL.WEIGHT_RTTY_170_amateurs);
                }

                if(rtty_check.isCommercial450Hz()) {
                    pScores[INFO_SIGNAL.ID_RTTY_450_commercial].addScore_SFFT(INFO_SIGNAL.WEIGHT_RTTY_450_commercial * INFO_SIGNAL.WEIGHT_RTTY_450_commercial);
                }

                if(rtty_check.isCommercial850Hz()) {
                    pScores[INFO_SIGNAL.ID_RTTY_850_commercial].addScore_SFFT(INFO_SIGNAL.WEIGHT_RTTY_850_commercial * INFO_SIGNAL.WEIGHT_RTTY_850_commercial);
                }

                // STANAG 4285 GEN
                STANAG_4285_GEN stanag_4285_GEN_check = new STANAG_4285_GEN(44100, n_sfft, frequenciesArray_sfft);
                pScores[INFO_SIGNAL.ID_STANAG_4285_GEN].addScore_SFFT(stanag_4285_GEN_check.getScore()  * INFO_SIGNAL.WEIGHT_STANAG_4285_GEN);

                // STANAG 4285 IDLE
                STANAG_4285_IDLE stanag_4285_IDLE_check = new STANAG_4285_IDLE(44100, n_sfft, frequenciesArray_sfft);
                pScores[INFO_SIGNAL.ID_STANAG_4285_IDLE].addScore_SFFT(stanag_4285_IDLE_check.getScore() * INFO_SIGNAL.WEIGHT_STANAG_4285_IDLE);

                // STANAG 4285 TFC
                STANAG_4285_TFC stanag_4285_TFC_check = new STANAG_4285_TFC(44100, n_sfft, frequenciesArray_sfft);
                pScores[INFO_SIGNAL.ID_STANAG_4285_TFC].addScore_SFFT(stanag_4285_TFC_check.getScore() * INFO_SIGNAL.WEIGHT_STANAG_4285_TFC);

                // STANAG 4285 8PSK
                STANAG_4285_8PSK stanag_4285_8PSK_check = new STANAG_4285_8PSK(44100, n_sfft, frequenciesArray_sfft);
                pScores[INFO_SIGNAL.ID_STANAG_4285_8PSK].addScore_SFFT(stanag_4285_8PSK_check.getScore() * INFO_SIGNAL.WEIGHT_STANAG_4285_8PSK);

                // STANAG 4285 TS3000
                STANAG_4285_TS3000 stanag_4285_TS3000_check = new STANAG_4285_TS3000(44100, n_sfft, frequenciesArray_sfft);
                pScores[INFO_SIGNAL.ID_STANAG_4285_TS3000].addScore_SFFT(stanag_4285_TS3000_check.getScore() * INFO_SIGNAL.WEIGHT_STANAG_4285_TS3000);

                // FT8
                FT8 ft8_check = new FT8(44100, n_sfft, frequenciesArray_sfft);
                pScores[INFO_SIGNAL.ID_FT8].addScore_SFFT(ft8_check.getScore() * INFO_SIGNAL.WEIGHT_FT8);

                // TETRAPOL
                TETRAPOL tetrapol_check = new TETRAPOL(44100, n_sfft, frequenciesArray_sfft);
                pScores[INFO_SIGNAL.ID_TETRAPOL].addScore_SFFT(tetrapol_check.getScore() * INFO_SIGNAL.WEIGHT_TETRAPOL);

                // ALE-400
                ALE_400 ale_400_check = new ALE_400(44100, n_sfft, frequenciesArray_sfft);
                pScores[INFO_SIGNAL.ID_ALE_400].addScore_SFFT(ale_400_check.getScore() * INFO_SIGNAL.WEIGHT_ALE_400);

                totalWS += WS_array.length;
            }
        }
    }
}
