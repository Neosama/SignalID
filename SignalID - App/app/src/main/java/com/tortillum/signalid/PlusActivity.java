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

package com.tortillum.signalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlusActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mTextViewOut;
    Button mButtonHelp;
    Button mButtonCredit;
    Button mButtonSignals;
    Button mButtonBack;

    private String str_Help = "To use it:\n" +
            "Set the frequency and bandwidth properly.\n" +
            "Selecting the frequency range. (0-30 MHz / Other)\n" +
            "Place the microphone of the device near the loudspeaker. (The quieter the environment is around, the fewer errors will occur)\n" +
            "Press the big button that dances.\n" +
            "Wait 5 seconds. (Time required for record)\n" +
            "\n" +
            "Tips :\n" +
            "The algorithm is based on frequency, a wrong tuning of your radio/SDR will result in an erroneous detection.\n" +
            "The recording is limited to 5 seconds, for practical reasons. Recognition of a signal may require several attempts.";
    private String str_Credit = "SignalID v1.2\n" +
            "By Tortillum.\n" +
            "Now open source !\n" +
            "\n" +
            "Follow us on Twitter : @tortillum\n";
    private String str_Signals = "The complete list of recognized signals :\n\n" +
            "RTTY (Commercial 85Hz, 170Hz, 450Hz, 850Hz, Amateur 170Hz)\n" +
            "PactorI (Standard, FSP, FEC, SELCALL)\n" +
            "ASCII (170Hz)\n" +
            "ALIS\n" +
            "Codan8580 (200Hz, 250Hz)\n" +
            "CIS36_50\n" +
            "CIS40_5\n" +
            "CIS50_50\n" +
            "STANAG 4285 (GEN, SYS3000 FEC, 8PSK, TFC, IDLE, SYS3000)\n" +
            "FT4\n" +
            "FT8\n" +
            "WEFAX (120, 240)\n" +
            "2G ALE\n" +
            "3G ALE\n" +
            "CHIP64\n" +
            "APRS (Burst)\n" +
            "ATIS\n" +
            "Tetrapol\n" +
            "POCSAG\n" +
            "FLEX (2FSK)\n" +
            "LINK-11 (GEN-CLEW, CLEW, SLEW)\n" +
            "Funcube-1 Telemetry\n" +
            "Inmarsat Aero TDM (10.5 kbps, 600 bps)\n" +
            "DSTAR\n" +
            "G-TOR (100 bd, 200 bd, 300 bd)\n" +
            "PSK (31, 63, 125, 250, 500)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);

        getSupportActionBar().hide();

        // Link UI
        mTextViewOut = findViewById(R.id.plus_textViewOut);
        mButtonHelp = findViewById(R.id.plus_buttonHelp);
        mButtonCredit = findViewById(R.id.plus_buttonCredit);
        mButtonSignals = findViewById(R.id.plus_buttonAllSignals);
        mButtonBack = findViewById(R.id.plus_buttonBack);

        mTextViewOut.setMovementMethod(new ScrollingMovementMethod());
        mButtonHelp.setOnClickListener(this);
        mButtonCredit.setOnClickListener(this);
        mButtonSignals.setOnClickListener(this);
        mButtonBack.setOnClickListener(this);

        // By default = Help
        mTextViewOut.setText(str_Help);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plus_buttonHelp:
                mTextViewOut.setText(str_Help);
                break;
            case R.id.plus_buttonCredit:
                mTextViewOut.setText(str_Credit);
                break;
            case R.id.plus_buttonAllSignals:
                mTextViewOut.setText(str_Signals);
                break;
            case R.id.plus_buttonBack:
                Intent myIntent = new Intent(this, MainActivity.class);
                startActivity(myIntent);
                break;
        }
    }
}