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

// NAME : FT4
// REVISION : 1

// With duration check

package com.tortillum.salome.signal;

import com.tortillum.salome.detector.D1_TONE;
import com.tortillum.salome.other.Duration;
import com.tortillum.salome.other.Pair;

public class FT4 {
	private int MARGIN_ERROR = 5; // in Hertz

	D1_TONE d1_tone_FT4_0345;
	D1_TONE d1_tone_FT4_0385;
	D1_TONE d1_tone_FT4_0430;
	D1_TONE d1_tone_FT4_0475;
	D1_TONE d1_tone_FT4_0515;
	D1_TONE d1_tone_FT4_0555;
	D1_TONE d1_tone_FT4_0600;
	D1_TONE d1_tone_FT4_0645;
	D1_TONE d1_tone_FT4_0690;
	D1_TONE d1_tone_FT4_0730;
	D1_TONE d1_tone_FT4_0775;
	D1_TONE d1_tone_FT4_0815;
	D1_TONE d1_tone_FT4_0860;
	D1_TONE d1_tone_FT4_0905;
	D1_TONE d1_tone_FT4_0945;
	D1_TONE d1_tone_FT4_0990;
	D1_TONE d1_tone_FT4_1035;
	D1_TONE d1_tone_FT4_1075;
	D1_TONE d1_tone_FT4_1120;
	D1_TONE d1_tone_FT4_1160;
	D1_TONE d1_tone_FT4_1205;
	D1_TONE d1_tone_FT4_1245;
	D1_TONE d1_tone_FT4_1290;
	D1_TONE d1_tone_FT4_1335;
	D1_TONE d1_tone_FT4_1375;
	D1_TONE d1_tone_FT4_1420;
	D1_TONE d1_tone_FT4_1465;
	D1_TONE d1_tone_FT4_1505;
	D1_TONE d1_tone_FT4_1550;
	D1_TONE d1_tone_FT4_1590;
	D1_TONE d1_tone_FT4_1635;
	D1_TONE d1_tone_FT4_1680;
	D1_TONE d1_tone_FT4_1720;
	D1_TONE d1_tone_FT4_1765;
	D1_TONE d1_tone_FT4_1810;
	D1_TONE d1_tone_FT4_1850;
	D1_TONE d1_tone_FT4_1895;
	D1_TONE d1_tone_FT4_1935;
	D1_TONE d1_tone_FT4_1980;
	D1_TONE d1_tone_FT4_2025;
	D1_TONE d1_tone_FT4_2065;
	D1_TONE d1_tone_FT4_2110;
	D1_TONE d1_tone_FT4_2155;
	D1_TONE d1_tone_FT4_2195;
	D1_TONE d1_tone_FT4_2240;
	D1_TONE d1_tone_FT4_2280;
	D1_TONE d1_tone_FT4_2325;
	D1_TONE d1_tone_FT4_2410;
	D1_TONE d1_tone_FT4_2455;
	D1_TONE d1_tone_FT4_2495;
	D1_TONE d1_tone_FT4_2540;
	D1_TONE d1_tone_FT4_2585;
	D1_TONE d1_tone_FT4_2627;
	D1_TONE d1_tone_FT4_2670;

	int tone_FT4_0345 = 345;
	int tone_FT4_0385 = 385;
	int tone_FT4_0430 = 430;
	int tone_FT4_0475 = 475;
	int tone_FT4_0515 = 515;
	int tone_FT4_0555 = 555;
	int tone_FT4_0600 = 600;
	int tone_FT4_0645 = 645;
	int tone_FT4_0690 = 690;
	int tone_FT4_0730 = 730;
	int tone_FT4_0775 = 775;
	int tone_FT4_0815 = 815;
	int tone_FT4_0860 = 860;
	int tone_FT4_0905 = 905;
	int tone_FT4_0945 = 945;
	int tone_FT4_0990 = 990;
	int tone_FT4_1035 = 1035;
	int tone_FT4_1075 = 1075;
	int tone_FT4_1120 = 1120;
	int tone_FT4_1160 = 1160;
	int tone_FT4_1205 = 1205;
	int tone_FT4_1245 = 1245;
	int tone_FT4_1290 = 1290;
	int tone_FT4_1335 = 1335;
	int tone_FT4_1375 = 1375;
	int tone_FT4_1420 = 1420;
	int tone_FT4_1465 = 1465;
	int tone_FT4_1505 = 1505;
	int tone_FT4_1550 = 1550;
	int tone_FT4_1590 = 1590;
	int tone_FT4_1635 = 1635;
	int tone_FT4_1680 = 1680;
	int tone_FT4_1720 = 1720;
	int tone_FT4_1765 = 1765;
	int tone_FT4_1810 = 1810;
	int tone_FT4_1850 = 1850;
	int tone_FT4_1895 = 1895;
	int tone_FT4_1935 = 1935;
	int tone_FT4_1980 = 1980;
	int tone_FT4_2025 = 2025;
	int tone_FT4_2065 = 2065;
	int tone_FT4_2110 = 2110;
	int tone_FT4_2155 = 2155;
	int tone_FT4_2195 = 2195;
	int tone_FT4_2240 = 2240;
	int tone_FT4_2280 = 2280;
	int tone_FT4_2325 = 2325;
	int tone_FT4_2410 = 2410;
	int tone_FT4_2455 = 2455;
	int tone_FT4_2495 = 2495;
	int tone_FT4_2540 = 2540;
	int tone_FT4_2585 = 2585;
	int tone_FT4_2627 = 2627;
	int tone_FT4_2670 = 2670;

	public FT4(int sampleRate, int n_fft, Pair[] listFqs, Duration[] durations) {
		if(n_fft != 65536) { // SFFT ONLY
			d1_tone_FT4_0345 = new D1_TONE(tone_FT4_0345, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_0345].setState(d1_tone_FT4_0345.check());

			d1_tone_FT4_0385 = new D1_TONE(tone_FT4_0385, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_0385].setState(d1_tone_FT4_0385.check());

			d1_tone_FT4_0430 = new D1_TONE(tone_FT4_0430, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_0430].setState(d1_tone_FT4_0430.check());

			d1_tone_FT4_0475 = new D1_TONE(tone_FT4_0475, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_0475].setState(d1_tone_FT4_0475.check());

			d1_tone_FT4_0515 = new D1_TONE(tone_FT4_0515, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_0515].setState(d1_tone_FT4_0515.check());

			d1_tone_FT4_0555 = new D1_TONE(tone_FT4_0555, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_0555].setState(d1_tone_FT4_0555.check());

			d1_tone_FT4_0600 = new D1_TONE(tone_FT4_0600, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_0600].setState(d1_tone_FT4_0600.check());

			d1_tone_FT4_0645 = new D1_TONE(tone_FT4_0645, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_0645].setState(d1_tone_FT4_0645.check());

			d1_tone_FT4_0690 = new D1_TONE(tone_FT4_0690, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_0690].setState(d1_tone_FT4_0690.check());

			d1_tone_FT4_0730 = new D1_TONE(tone_FT4_0730, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_0730].setState(d1_tone_FT4_0730.check());

			d1_tone_FT4_0775 = new D1_TONE(tone_FT4_0775, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_0775].setState(d1_tone_FT4_0775.check());

			d1_tone_FT4_0815 = new D1_TONE(tone_FT4_0815, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_0815].setState(d1_tone_FT4_0815.check());

			d1_tone_FT4_0860 = new D1_TONE(tone_FT4_0860, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_0860].setState(d1_tone_FT4_0860.check());

			d1_tone_FT4_0905 = new D1_TONE(tone_FT4_0905, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_0905].setState(d1_tone_FT4_0905.check());

			d1_tone_FT4_0945 = new D1_TONE(tone_FT4_0945, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_0945].setState(d1_tone_FT4_0945.check());

			d1_tone_FT4_0990 = new D1_TONE(tone_FT4_0990, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_0990].setState(d1_tone_FT4_0990.check());

			d1_tone_FT4_1035 = new D1_TONE(tone_FT4_1035, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_1035].setState(d1_tone_FT4_1035.check());

			d1_tone_FT4_1075 = new D1_TONE(tone_FT4_1075, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_1075].setState(d1_tone_FT4_1075.check());

			d1_tone_FT4_1120 = new D1_TONE(tone_FT4_1120, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_1120].setState(d1_tone_FT4_1120.check());

			d1_tone_FT4_1160 = new D1_TONE(tone_FT4_1160, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_1160].setState(d1_tone_FT4_1160.check());

			d1_tone_FT4_1205 = new D1_TONE(tone_FT4_1205, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_1205].setState(d1_tone_FT4_1205.check());

			d1_tone_FT4_1245 = new D1_TONE(tone_FT4_1245, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_1245].setState(d1_tone_FT4_1245.check());

			d1_tone_FT4_1290 = new D1_TONE(tone_FT4_1290, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_1290].setState(d1_tone_FT4_1290.check());

			d1_tone_FT4_1335 = new D1_TONE(tone_FT4_1335, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_1335].setState(d1_tone_FT4_1335.check());

			d1_tone_FT4_1375 = new D1_TONE(tone_FT4_1375, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_1375].setState(d1_tone_FT4_1375.check());

			d1_tone_FT4_1420 = new D1_TONE(tone_FT4_1420, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_1420].setState(d1_tone_FT4_1420.check());

			d1_tone_FT4_1465 = new D1_TONE(tone_FT4_1465, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_1465].setState(d1_tone_FT4_1465.check());

			d1_tone_FT4_1505 = new D1_TONE(tone_FT4_1505, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_1505].setState(d1_tone_FT4_1505.check());

			d1_tone_FT4_1550 = new D1_TONE(tone_FT4_1550, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_1550].setState(d1_tone_FT4_1550.check());

			d1_tone_FT4_1590 = new D1_TONE(tone_FT4_1590, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_1590].setState(d1_tone_FT4_1590.check());

			d1_tone_FT4_1635 = new D1_TONE(tone_FT4_1635, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_1635].setState(d1_tone_FT4_1635.check());

			d1_tone_FT4_1680 = new D1_TONE(tone_FT4_1680, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_1680].setState(d1_tone_FT4_1680.check());

			d1_tone_FT4_1720 = new D1_TONE(tone_FT4_1720, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_1720].setState(d1_tone_FT4_1720.check());

			d1_tone_FT4_1765 = new D1_TONE(tone_FT4_1765, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_1765].setState(d1_tone_FT4_1765.check());

			d1_tone_FT4_1810 = new D1_TONE(tone_FT4_1810, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_1810].setState(d1_tone_FT4_1810.check());

			d1_tone_FT4_1850 = new D1_TONE(tone_FT4_1850, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_1850].setState(d1_tone_FT4_1850.check());

			d1_tone_FT4_1895 = new D1_TONE(tone_FT4_1895, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_1895].setState(d1_tone_FT4_1895.check());

			d1_tone_FT4_1935 = new D1_TONE(tone_FT4_1935, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_1935].setState(d1_tone_FT4_1935.check());

			d1_tone_FT4_1980 = new D1_TONE(tone_FT4_1980, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_1980].setState(d1_tone_FT4_1980.check());

			d1_tone_FT4_2025 = new D1_TONE(tone_FT4_2025, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_2025].setState(d1_tone_FT4_2025.check());

			d1_tone_FT4_2065 = new D1_TONE(tone_FT4_2065, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_2065].setState(d1_tone_FT4_2065.check());

			d1_tone_FT4_2110 = new D1_TONE(tone_FT4_2110, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_2110].setState(d1_tone_FT4_2110.check());

			d1_tone_FT4_2155 = new D1_TONE(tone_FT4_2155, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_2155].setState(d1_tone_FT4_2155.check());

			d1_tone_FT4_2195 = new D1_TONE(tone_FT4_2195, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_2195].setState(d1_tone_FT4_2195.check());

			d1_tone_FT4_2240 = new D1_TONE(tone_FT4_2240, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_2240].setState(d1_tone_FT4_2240.check());

			d1_tone_FT4_2280 = new D1_TONE(tone_FT4_2280, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_2280].setState(d1_tone_FT4_2280.check());

			d1_tone_FT4_2325 = new D1_TONE(tone_FT4_2325, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_2325].setState(d1_tone_FT4_2325.check());

			d1_tone_FT4_2410 = new D1_TONE(tone_FT4_2410, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_2410].setState(d1_tone_FT4_2410.check());

			d1_tone_FT4_2455 = new D1_TONE(tone_FT4_2455, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_2455].setState(d1_tone_FT4_2455.check());

			d1_tone_FT4_2495 = new D1_TONE(tone_FT4_2495, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_2495].setState(d1_tone_FT4_2495.check());

			d1_tone_FT4_2540 = new D1_TONE(tone_FT4_2540, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_2540].setState(d1_tone_FT4_2540.check());

			d1_tone_FT4_2585 = new D1_TONE(tone_FT4_2585, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_2585].setState(d1_tone_FT4_2585.check());

			d1_tone_FT4_2627 = new D1_TONE(tone_FT4_2627, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_2627].setState(d1_tone_FT4_2627.check());

			d1_tone_FT4_2670 = new D1_TONE(tone_FT4_2670, 5, 12, 44100, n_fft, listFqs);
			durations[INFO_SIGNAL.DURATION_ID_FT4_2670].setState(d1_tone_FT4_2670.check());
		}
	}
}
