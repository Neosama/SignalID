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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tortillum.salome.detector.ALIS;
import com.tortillum.salome.detector.APRS;
import com.tortillum.salome.detector.ASCII;
import com.tortillum.salome.detector.ATIS;
import com.tortillum.salome.detector.CHIP64;
import com.tortillum.salome.detector.CIS36_50;
import com.tortillum.salome.detector.CIS40_5;
import com.tortillum.salome.detector.CIS50_50;
import com.tortillum.salome.detector.Codan8580;
import com.tortillum.salome.detector.DSTAR;
import com.tortillum.salome.detector.FLEX;
import com.tortillum.salome.detector.FT4;
import com.tortillum.salome.detector.FT8;
import com.tortillum.salome.detector.Funcube1;
import com.tortillum.salome.detector.G_TOR;
import com.tortillum.salome.detector.InmarsatAero;
import com.tortillum.salome.detector.Link11;
import com.tortillum.salome.detector.POCSAG;
import com.tortillum.salome.detector.PSK;
import com.tortillum.salome.detector.PactorI;
import com.tortillum.salome.detector.RTTY;
import com.tortillum.salome.detector.STANAG;
import com.tortillum.salome.detector.Tetrapol;
import com.tortillum.salome.detector.WEFAX;
import com.tortillum.salome.detector.x2G_ALE;
import com.tortillum.salome.detector.x3G_ALE;
import com.tortillum.salome.salome.Pair;
import com.tortillum.salome.wavReader.Reader;
import com.tortillum.salome.window.Hamming;

import org.jtransforms.fft.DoubleFFT_1D;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * permissions request code
     */
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;

    /**
     * Permissions that need to be explicitly requested from end user.
     */
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private String[] informations = new String[]{
            "Press the big button to start recognition.",
            "Press the \"+\" button for more help.",
            "Press the \"+\" button to get a list of all recognized signals.",
            "Adjust the receiver. (frequency, bandwith)"};

    private boolean permissionsOk = false;

    RecordManager recordManager;
    String pathFileTmp;

    ImageButton mImageButtonPlus;
    TextView mTextViewInfo;
    ImageButton mImageButtonSearch;
    TextView mTextViewResult;
    Button mButtonRangeFq;

    boolean isHF = true;
    boolean animateActionButton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        checkPermissions();

        File file = new File(this.getCacheDir(), "tmpSignalID.wav");
        pathFileTmp = file.getAbsolutePath();
        recordManager = new RecordManager(pathFileTmp);

        // Link UI
        mImageButtonPlus = findViewById(R.id.main_imageButtonPlus);
        mTextViewInfo = findViewById(R.id.main_textViewInfo);
        mImageButtonSearch = findViewById(R.id.main_imageButtonSearch);
        mTextViewResult = findViewById(R.id.main_textViewResult);
        mButtonRangeFq = findViewById(R.id.main_buttonRangeFq);

        mImageButtonPlus.setOnClickListener(this);
        mImageButtonSearch.setOnClickListener(this);
        mButtonRangeFq.setOnClickListener(this);

        checkPermissions();

        if(!permissionsOk){
            mImageButtonSearch.setClickable(false);
            mTextViewInfo.setText("Permissions required to use this application, please check.");
        } else
            mImageButtonSearch.setClickable(true);

        final Handler handlerButtonDance = new Handler();
        final Runnable rButtonDance = new Runnable() {

            @Override
            public void run() {
                int min = 0;
                int max = 0;

                if (animateActionButton) {
                    min = 90 + 32;
                    max = 270 - 32;
                } else {
                    min = 90 + 64;
                    max = 270 - 64;
                }
                Random rand = new Random();
                float degRotation = rand.nextFloat() * (max - min) + min;

                mImageButtonSearch.animate().rotation(degRotation).setInterpolator(new AccelerateDecelerateInterpolator());
                handlerButtonDance.postDelayed(this, 450);
            }
        };
        handlerButtonDance.postDelayed(rButtonDance, 450);

        final Handler handlerInformations = new Handler();
        final Runnable rInformations = new Runnable() {

            @Override
            public void run() {
                Random rand = new Random();
                int n = rand.nextInt(informations.length);

                if(!mTextViewInfo.getText().toString().contains("Searching"))
                    mTextViewInfo.setText(informations[n]);

                handlerInformations.postDelayed(this, 8000);
            }
        };
        handlerInformations.postDelayed(rInformations, 8000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_imageButtonPlus:
                Intent myIntent = new Intent(this, PlusActivity.class);
                startActivity(myIntent);
                break;
            case R.id.main_imageButtonSearch:
                mTextViewInfo.setText("Searching");
                mTextViewResult.setText("");
                new File(pathFileTmp).delete();
                recordManager = new RecordManager(pathFileTmp);
                recordManager.start();

                mImageButtonSearch.setClickable(false);
                mButtonRangeFq.setClickable(false);
                mImageButtonPlus.setClickable(false);
                animateActionButton = true;

                // Max time record file ====> 5sec
                Handler handler = new Handler();
                Runnable r = new Runnable() {
                    public void run() {
                        recordManager.stop();

                        // ======

                        Reader wavReader = null;
                        try {
                            wavReader = new Reader(pathFileTmp, false);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        double[] pcmData = wavReader.getByteArray();

                        double[] tmp_pcmData = pcmData;

                        int n_fft_65536 = 1024 * 64; // 65536

                        if (tmp_pcmData.length < n_fft_65536) {
                            return;
                        }

                        Hamming windowHamming_65536 = new Hamming();
                        double[] hammingData_65536 = windowHamming_65536.toHammingWindow(tmp_pcmData, tmp_pcmData.length);

                        DoubleFFT_1D fft_fsk = new DoubleFFT_1D(n_fft_65536);
                        fft_fsk.realForward(hammingData_65536);

                        // Extract Real part
                        double[] result_65536 = new double[hammingData_65536.length / 2];
                        for (int s = 0; s < result_65536.length; s++) {
                            //result[s] = Math.abs(signal[2*s]);
                            double re = hammingData_65536[s * 2];
                            double im = hammingData_65536[s * 2 + 1];
                            result_65536[s] = (double) Math.sqrt(re * re + im * im) / result_65536.length;
                        }

                        // frequency  = index * SamplingFrequency / N_FFT

                        Pair[] frequenciesArray_65536 = new Pair[result_65536.length];

                        //fill the array
                        for (int i = 0; i < result_65536.length; i++) {
                            frequenciesArray_65536[i] = new Pair(i, result_65536[i]);
                        }
                        Arrays.sort(frequenciesArray_65536);

                        // @@@@@@
                        RTTY rtty_test = new RTTY((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        PactorI pactorI_test = new PactorI((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        ASCII ascii_test = new ASCII((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        ALIS alis_test = new ALIS((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        Codan8580 codan8580_test = new Codan8580((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        CIS36_50 cis36_50_test = new CIS36_50((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        CIS40_5 cis40_5_test = new CIS40_5((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        CIS50_50 cis50_50_test = new CIS50_50((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        STANAG stanag_test = new STANAG((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        FT4 ft4_test = new FT4((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        FT8 ft8_test = new FT8((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        WEFAX wefax_test = new WEFAX((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        x2G_ALE x2G_ale_test = new x2G_ALE((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        x3G_ALE x3G_ale_test = new x3G_ALE((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        CHIP64 chip64_test = new CHIP64((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        APRS aprs_test = new APRS((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        ATIS atis_test = new ATIS((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        Tetrapol tetrapol_test = new Tetrapol((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        POCSAG pocsag_test = new POCSAG((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        FLEX flex_test = new FLEX((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        PSK psk_test = new PSK((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        Link11 link11_test = new Link11((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        Funcube1 funcube1_test = new Funcube1((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        InmarsatAero inmarsatAero_test = new InmarsatAero((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        DSTAR dstar_test = new DSTAR((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);
                        G_TOR g_tor_test = new G_TOR((int) wavReader.getSR(), n_fft_65536, frequenciesArray_65536);

                        if (isHF) { // 0-30 MHz
                            // RTTY
                            if (rtty_test.isCommercial85Hz())
                                mTextViewResult.setText("RTTY (Commercial 85Hz) detected!");
                            if (rtty_test.isCommercial170Hz())
                                mTextViewResult.setText("RTTY (Commercial 170Hz) detected!");
                            if (rtty_test.isCommercial450Hz())
                                mTextViewResult.setText("RTTY (Commercial 450Hz) detected!");
                            if (rtty_test.isCommercial850Hz())
                                mTextViewResult.setText("RTTY (Commercial 850Hz) detected!");
                            if (rtty_test.isAmateurs170Hz())
                                mTextViewResult.setText("RTTY (Amateur 170Hz) detected!");

                            // Pactor I
                            if (pactorI_test.isStandard())
                                mTextViewResult.setText("Pactor I (Standard) detected!");
                            if (pactorI_test.isFSP())
                                mTextViewResult.setText("Pactor I (FSP) detected!");
                            if (pactorI_test.isFEC())
                                mTextViewResult.setText("Pactor I (FEC) detected!");
                            if (pactorI_test.isSELCALL())
                                mTextViewResult.setText("Pactor I (SELCALL) detected!");

                            // ASCII
                            if (ascii_test.is170Hz())
                                mTextViewResult.setText("ASCII (170Hz) detected!");

                            // ALIS
                            if (alis_test.isALIS())
                                mTextViewResult.setText("ALIS detected!");

                            // Codan 8580
                            if (codan8580_test.isCodan8580())
                                mTextViewResult.setText("Codan 8580 detected!");

                            // CIS 36_50
                            if (cis36_50_test.is200Hz())
                                mTextViewResult.setText("CIS36_50 (200Hz) detected!");
                            if (cis36_50_test.is250Hz())
                                mTextViewResult.setText("CIS36_50 (250Hz) detected!");


                            // CIS 40_5
                            if (cis40_5_test.isCIS40_5())
                                mTextViewResult.setText("CIS40_5 detected!");

                            // CIS 50_50
                            if (cis50_50_test.is500Hz())
                                mTextViewResult.setText("CIS50_50 (500Hz) detected!");

                            // STANAG
                            if (stanag_test.is4285_GEN())
                                mTextViewResult.setText("STANAG 4285 (GEN) detected!");
                            if (stanag_test.is4285_SYS3000_FEC())
                                mTextViewResult.setText("STANAG 4285 (SYS3000 FEC) detected!");
                            if (stanag_test.is4285_8PSK())
                                mTextViewResult.setText("STANAG 4285 (8PSK) detected!");
                            if (stanag_test.is4285_TFC())
                                mTextViewResult.setText("STANAG 4285 (TFC) detected!");
                            if (stanag_test.is4285_IDLE())
                                mTextViewResult.setText("STANAG 4285 (IDLE) detected!");
                            if (stanag_test.is4285_SYS3000())
                                mTextViewResult.setText("STANAG 4285 (SYS3000) detected!");

                            // FT4
                            if (ft4_test.isFT4())
                                mTextViewResult.setText("FT4 detected!");

                            // FT8
                            if (ft8_test.isFT8())
                                mTextViewResult.setText("FT8 detected!");

                            // WEFAX
                            if (wefax_test.is120())
                                mTextViewResult.setText("WEFAX (120) detected!");
                            if (wefax_test.is240())
                                mTextViewResult.setText("WEFAX (240) detected!");

                            // 2G ALE
                            if (x2G_ale_test.is2G_ALE())
                                mTextViewResult.setText("2G ALE detected!");

                            // 3G ALE
                            if (x3G_ale_test.is3G_ALE())
                                mTextViewResult.setText("3G ALE detected!");

                            // CHIP64
                            if (chip64_test.isCHIP64())
                                mTextViewResult.setText("CHIP64 detected!");

                            // LINK-11
                            if(link11_test.isCLEW())
                                mTextViewResult.setText("LINK-11 (CLEW) detected!");
                            if(link11_test.isSLEW())
                                mTextViewResult.setText("LINK-11 (SLEW) detected!");
                            if(link11_test.isGenCLEW())
                                mTextViewResult.setText("LINK-11 (GEN/CLEW) detected!");

                            // G-TOR
                            if(g_tor_test.is100())
                                mTextViewResult.setText("G-TOR (100 bd) detected!");
                            if(g_tor_test.is200())
                                mTextViewResult.setText("G-TOR (200 bd) detected!");
                            if(g_tor_test.is300())
                                mTextViewResult.setText("G-TOR (300 bd) detected!");

                        } else { // 30 - INFINITE MHz
                            // APRS
                            if (aprs_test.isAPRS())
                                mTextViewResult.setText("APRS detected!");

                            // ATIS
                            if (atis_test.isBurst())
                                mTextViewResult.setText("ATIS (Burst) detected!");

                            // TETRAPOL
                            if (tetrapol_test.isTetrapol())
                                mTextViewResult.setText("TETRAPOL detected!");

                            // POCSAG
                            if (pocsag_test.isPOCSAG())
                                mTextViewResult.setText("POCSAG detected!");

                            // FLEX
                            if (flex_test.is2FSK())
                                mTextViewResult.setText("FLEX (2FSK) detected!");

                            // Funcube-1
                            if(funcube1_test.isFuncube1())
                                mTextViewResult.setText("Funcube-1 telemetry detected!");

                            // Inmersat_Aero
                            if(inmarsatAero_test.is10_5())
                                mTextViewResult.setText("Inmersat Aero (10.5 kbps) detected!");
                            if(inmarsatAero_test.is600())
                                mTextViewResult.setText("Inmersat Aero (600 bps) detected!");

                            // DSTAR
                            if(dstar_test.isDSTAR())
                                mTextViewResult.setText("DSTAR detected!");
                        }

                        // ======================
                        // 0-30 & 30-INFINITE MHz
                        // ======================

                        // PSK (31, 63, 125, 250, 500)
                        if (psk_test.isBPSK_31() || psk_test.isQPSK_31())
                            mTextViewResult.setText("PSK31 detected!");
                        if (psk_test.isBPSK_63() || psk_test.isQPSK_63())
                            mTextViewResult.setText("PSK63 detected!");
                        if (psk_test.isBPSK_125() || psk_test.isQPSK_125())
                            mTextViewResult.setText("PSK125 detected!");
                        if (psk_test.isBPSK_250() || psk_test.isQPSK_250())
                            mTextViewResult.setText("PSK250 detected!");
                        if (psk_test.isBPSK_500() || psk_test.isQPSK_500())
                            mTextViewResult.setText("PSK500 detected!");

                        // @@@@@@

                        if (!mTextViewResult.getText().toString().contains("!")) {
                            mTextViewResult.setText("UNKNOWN");
                        }

                        // ======
                        mImageButtonSearch.setClickable(true);
                        mButtonRangeFq.setClickable(true);
                        mImageButtonPlus.setClickable(true);
                        animateActionButton = false;
                        mTextViewInfo.setText(informations[0]);
                    }
                };
                handler.postDelayed(r, 5010);

                // ======

                break;
            case R.id.main_buttonRangeFq:
                if (isHF) {
                    mButtonRangeFq.setText("30 - ∞ MHz");
                    isHF = false;
                } else {
                    mButtonRangeFq.setText("0 - 30 MHz");
                    isHF = true;
                }

                break;
        }
    }

    /**
     * Checks the dynamically-controlled permissions and requests missing permissions from end user.
     */
    protected void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<String>();
        // check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            // request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        // exit the app if one permission is not granted
                        Toast.makeText(this, "Required permission '" + permissions[index]
                                + "' not granted, exiting", Toast.LENGTH_LONG).show();
                        permissionsOk = false;
                        finish();
                        return;
                    }
                }
                // all permissions were granted
                permissionsOk = true;
                break;
        }
    }
}