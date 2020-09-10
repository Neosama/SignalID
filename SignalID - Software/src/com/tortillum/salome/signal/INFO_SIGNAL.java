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

package com.tortillum.salome.signal;

public class INFO_SIGNAL {
	// Type MODE
	public static int TYPE_MODE_USB = 0;
	public static int TYPE_MODE_NFM = 1;
	
	// TYPE FREQUENCIES
	public static int TYPE_FREQUENCIES_L30 = 0; // 0 - 30 MHz
	public static int TYPE_FREQUENCIES_M30 = 1; // 30 - INFINITE MHz
	public static int TYPE_FREQUENCIES_ALL = 2; // 0 - INFINITE MHz
	
	public static int NB_SIGNALS = 14;
	public static int NB_DURATIONS = 101;

	public static int TYPE_MODE_RTTY = TYPE_MODE_USB;
	public static int TYPE_FREQUENCIES_RTTY = TYPE_FREQUENCIES_L30;
	
	public static int ID_RTTY_85_commercial = 0;
	public static int WEIGHT_RTTY_85_commercial = 1;
	public static String NAME_RTTY_85_commercial = "RTTY (85Hz commercial)";

	public static int ID_RTTY_170_commercial = 1;
	public static int WEIGHT_RTTY_170_commercial = 1;
	public static String NAME_RTTY_170_commercial = "RTTY (170Hz commercial)";

	public static int ID_RTTY_170_amateurs = 2;
	public static int WEIGHT_RTTY_170_amateurs = 1;
	public static String NAME_RTTY_170_amateurs = "RTTY (170Hz amateurs)";

	public static int ID_RTTY_450_commercial = 3;
	public static int WEIGHT_RTTY_450_commercial = 1;
	public static String NAME_RTTY_450_commercial = "RTTY (450Hz commercial)";

	public static int ID_RTTY_850_commercial = 4;
	public static int WEIGHT_RTTY_850_commercial = 1;
	public static String NAME_RTTY_850_commercial = "RTTY (850Hz commercial)";
	
	public static int TYPE_MODE_STANAG_4285 = TYPE_MODE_USB;
	public static int TYPE_FREQUENCIES_STANAG_4285 = TYPE_FREQUENCIES_L30;

	public static int ID_STANAG_4285_GEN = 5;
	public static int WEIGHT_STANAG_4285_GEN = 1;
	public static String NAME_STANAG_4285_GEN = "STANAG 4285 (GEN)";

	public static int ID_STANAG_4285_IDLE = 6;
	public static int WEIGHT_STANAG_4285_IDLE = 1;
	public static String NAME_STANAG_4285_IDLE = "STANAG 4285 (IDLE)";

	public static int ID_STANAG_4285_TFC = 7;
	public static int WEIGHT_STANAG_4285_TFC = 1;
	public static String NAME_STANAG_4285_TFC = "STANAG 4285 (TFC)";

	public static int ID_STANAG_4285_8PSK = 8;
	public static int WEIGHT_STANAG_4285_8PSK = 1;
	public static String NAME_STANAG_4285_8PSK = "STANAG 4285 (8PSK)";

	public static int ID_STANAG_4285_TS3000 = 9;
	public static int WEIGHT_STANAG_4285_TS3000 = 1;
	public static String NAME_STANAG_4285_TS3000 = "STANAG 4285 (TS3000)";

	// FT8
	// RANGE DURATION ID : 0 -> 46
	public static int TYPE_MODE_FT8 = TYPE_MODE_USB;
	public static int TYPE_FREQUENCIES_FT8 = TYPE_FREQUENCIES_L30;
	
	public static int ID_FT8 = 10;
	public static int WEIGHT_FT8 = 1;
	public static String NAME_FT8 = "FT8";
	public static int DURATION_ID_FT8_0475 = 0;
	public static int DURATION_ID_FT8_0515 = 1;
	public static int DURATION_ID_FT8_0555 = 2;
	public static int DURATION_ID_FT8_0775 = 3;
	public static int DURATION_ID_FT8_0815 = 4;
	public static int DURATION_ID_FT8_0860 = 5;
	public static int DURATION_ID_FT8_0905 = 6;
	public static int DURATION_ID_FT8_0945 = 7;
	public static int DURATION_ID_FT8_0990 = 8;
	public static int DURATION_ID_FT8_1035 = 9;
	public static int DURATION_ID_FT8_1075 = 10;
	public static int DURATION_ID_FT8_1120 = 11;
	public static int DURATION_ID_FT8_1145 = 12;
	public static int DURATION_ID_FT8_1160 = 13;
	public static int DURATION_ID_FT8_1205 = 14;
	public static int DURATION_ID_FT8_1245 = 15;
	public static int DURATION_ID_FT8_1290 = 16;
	public static int DURATION_ID_FT8_1335 = 17;
	public static int DURATION_ID_FT8_1375 = 18;
	public static int DURATION_ID_FT8_1420 = 19;
	public static int DURATION_ID_FT8_1465 = 20;
	public static int DURATION_ID_FT8_1505 = 21;
	public static int DURATION_ID_FT8_1550 = 22;
	public static int DURATION_ID_FT8_1590 = 23;
	public static int DURATION_ID_FT8_1635 = 24;
	public static int DURATION_ID_FT8_1680 = 25;
	public static int DURATION_ID_FT8_1720 = 26;
	public static int DURATION_ID_FT8_1765 = 27;
	public static int DURATION_ID_FT8_1810 = 28;
	public static int DURATION_ID_FT8_1850 = 29;
	public static int DURATION_ID_FT8_1860 = 30;
	public static int DURATION_ID_FT8_1895 = 31;
	public static int DURATION_ID_FT8_1935 = 32;
	public static int DURATION_ID_FT8_1980 = 33;
	public static int DURATION_ID_FT8_2025 = 34;
	public static int DURATION_ID_FT8_2065 = 35;
	public static int DURATION_ID_FT8_2110 = 36;
	public static int DURATION_ID_FT8_2155 = 37;
	public static int DURATION_ID_FT8_2195 = 38;
	public static int DURATION_ID_FT8_2240 = 39;
	public static int DURATION_ID_FT8_2265 = 40;
	public static int DURATION_ID_FT8_2280 = 41;
	public static int DURATION_ID_FT8_2325 = 42;
	public static int DURATION_ID_FT8_2455 = 43;
	public static int DURATION_ID_FT8_2495 = 44;
	public static int DURATION_ID_FT8_2540 = 45;
	public static int DURATION_ID_FT8_2585 = 46;

	// Check it !
	public static int TYPE_MODE_TETRAPOL = TYPE_MODE_NFM;
	public static int TYPE_FREQUENCIES_TETRAPOL = TYPE_FREQUENCIES_M30;
	
	public static int ID_TETRAPOL = 11;
	public static int WEIGHT_TETRAPOL = 1;
	public static String NAME_TETRAPOL = "TETRAPOL";

	// Check it !
	public static int TYPE_MODE_ALE_400 = TYPE_MODE_USB;
	public static int TYPE_FREQUENCIES_ALE_400 = TYPE_FREQUENCIES_ALL;
	
	public static int ID_ALE_400 = 12;
	public static int WEIGHT_ALE_400 = 1;
	public static String NAME_ALE_400 = "ALE-400";

	// FT4
	// RANGE DURATION ID : 47 -> 100
	public static int TYPE_MODE_FT4 = TYPE_MODE_USB;
	public static int TYPE_FREQUENCIES_FT4 = TYPE_FREQUENCIES_L30;
	
	public static int ID_FT4 = 13;
	public static int WEIGHT_FT4 = 1;
	public static String NAME_FT4 = "FT4";
	public static int DURATION_ID_FT4_0345 = 47 + 0;
	public static int DURATION_ID_FT4_0385 = 47 + 1;
	public static int DURATION_ID_FT4_0430 = 47 + 2;
	public static int DURATION_ID_FT4_0475 = 47 + 3;
	public static int DURATION_ID_FT4_0515 = 47 + 4;
	public static int DURATION_ID_FT4_0555 = 47 + 5;
	public static int DURATION_ID_FT4_0600 = 47 + 6;
	public static int DURATION_ID_FT4_0645 = 47 + 7;
	public static int DURATION_ID_FT4_0690 = 47 + 8;
	public static int DURATION_ID_FT4_0730 = 47 + 9;
	public static int DURATION_ID_FT4_0775 = 47 + 10;
	public static int DURATION_ID_FT4_0815 = 47 + 11;
	public static int DURATION_ID_FT4_0860 = 47 + 12;
	public static int DURATION_ID_FT4_0905 = 47 + 13;
	public static int DURATION_ID_FT4_0945 = 47 + 14;
	public static int DURATION_ID_FT4_0990 = 47 + 15;
	public static int DURATION_ID_FT4_1035 = 47 + 16;
	public static int DURATION_ID_FT4_1075 = 47 + 17;
	public static int DURATION_ID_FT4_1120 = 47 + 18;
	public static int DURATION_ID_FT4_1160 = 47 + 19;
	public static int DURATION_ID_FT4_1205 = 47 + 20;
	public static int DURATION_ID_FT4_1245 = 47 + 21;
	public static int DURATION_ID_FT4_1290 = 47 + 22;
	public static int DURATION_ID_FT4_1335 = 47 + 23;
	public static int DURATION_ID_FT4_1375 = 47 + 24;
	public static int DURATION_ID_FT4_1420 = 47 + 25;
	public static int DURATION_ID_FT4_1465 = 47 + 26;
	public static int DURATION_ID_FT4_1505 = 47 + 27;
	public static int DURATION_ID_FT4_1550 = 47 + 28;
	public static int DURATION_ID_FT4_1590 = 47 + 29;
	public static int DURATION_ID_FT4_1635 = 47 + 30;
	public static int DURATION_ID_FT4_1680 = 47 + 31;
	public static int DURATION_ID_FT4_1720 = 47 + 32;
	public static int DURATION_ID_FT4_1765 = 47 + 33;
	public static int DURATION_ID_FT4_1810 = 47 + 34;
	public static int DURATION_ID_FT4_1850 = 47 + 35;
	public static int DURATION_ID_FT4_1895 = 47 + 36;
	public static int DURATION_ID_FT4_1935 = 47 + 37;
	public static int DURATION_ID_FT4_1980 = 47 + 38;
	public static int DURATION_ID_FT4_2025 = 47 + 39;
	public static int DURATION_ID_FT4_2065 = 47 + 40;
	public static int DURATION_ID_FT4_2110 = 47 + 41;
	public static int DURATION_ID_FT4_2155 = 47 + 42;
	public static int DURATION_ID_FT4_2195 = 47 + 43;
	public static int DURATION_ID_FT4_2240 = 47 + 44;
	public static int DURATION_ID_FT4_2280 = 47 + 45;
	public static int DURATION_ID_FT4_2325 = 47 + 46;
	public static int DURATION_ID_FT4_2410 = 47 + 47;
	public static int DURATION_ID_FT4_2455 = 47 + 48;
	public static int DURATION_ID_FT4_2495 = 47 + 49;
	public static int DURATION_ID_FT4_2540 = 47 + 50;
	public static int DURATION_ID_FT4_2585 = 47 + 51;
	public static int DURATION_ID_FT4_2627 = 47 + 52;
	public static int DURATION_ID_FT4_2670 = 47 + 53;
}
