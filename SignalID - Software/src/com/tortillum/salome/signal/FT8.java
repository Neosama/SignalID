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

// NAME : FT8
// REVISION : 1

package com.tortillum.salome.signal;

import com.tortillum.salome.detector.D1_TONE;
import com.tortillum.salome.other.Pair;

public class FT8 {
    private int MARGIN_ERROR = 5; // in Hertz

    D1_TONE d1_tone;
    int[] listFrequencies_FFT = {375, 485, 865, 990, 995, 1000, 1005, 1010, 1015, 1020, 1025, 1030, 1055, 1060, 1070, 1080, 1150, 1155, 1160, 1165, 1175, 1180, 1185, 1190, 1215, 1285, 1290, 1295, 1395, 1405, 1420, 1445, 1450, 1485, 1495, 1500, 1515, 1520, 1525, 1535, 1580, 1620, 1630, 1645, 1675, 1700, 1850, 1855, 1860, 1865, 1870, 1875, 1880, 1885, 1925, 1945, 1950, 1990, 2010, 2025, 2085, 2090, 2095, 2100, 2105, 2185, 2195, 2200, 2205, 2210, 2220, 2225, 2230, 2235, 2245, 2255, 2535, 2550, 2560};
    int[] listFrequencies_SFFT = {475, 515, 555, 775, 815, 860, 905, 945, 990, 1035, 1075, 1120, 1160, 1205, 1245, 1290, 1335, 1375, 1420, 1465, 1505, 1550, 1590, 1635, 1680, 1720, 1765, 1810, 1850, 1895, 1935, 1980, 2025, 2065, 2110, 2155, 2195, 2240, 2280, 2325, 2455, 2495, 2540, 2585};

    public FT8(int sampleRate, int n_fft, Pair[] listFqs) {
        if (n_fft == 65536) // FFT
            d1_tone = new D1_TONE(listFrequencies_FFT, MARGIN_ERROR, 12, sampleRate, n_fft, listFqs);
        else // SFFT
            d1_tone = new D1_TONE(listFrequencies_SFFT, MARGIN_ERROR, 12, sampleRate, n_fft, listFqs);
    }

    public int getScore(){
        return d1_tone.getScore();
    }

}
