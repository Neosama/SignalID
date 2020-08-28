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
    public static int NB_SIGNALS = 13;

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

    public static int ID_FT8 = 10;
    public static int WEIGHT_FT8 = 0; // !!DISABLED!!
    public static String NAME_FT8 = "FT8";

    public static int ID_TETRAPOL = 11;
    public static int WEIGHT_TETRAPOL = 1;
    public static String NAME_TETRAPOL = "TETRAPOL";

    public static int ID_ALE_400 = 12;
    public static int WEIGHT_ALE_400 = 1;
    public static String NAME_ALE_400 = "ALE-400";
}
