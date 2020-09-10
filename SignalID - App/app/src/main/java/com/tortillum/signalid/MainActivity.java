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
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.tortillum.salome.recognizer.Recognizer;
import com.tortillum.salome.signal.INFO_SIGNAL;
import com.tortillum.salome.wavsplitter.WavFileException;

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
            "Press the big button to start recognition with microphone.",
            "Press the little button to start recognition with a WAV file.",
            "Press the \"+\" button for more help.",
            "Press the \"+\" button to get a list of all recognized signals.",
            "Adjust the receiver. (frequency, bandwith)"};

    private boolean permissionsOk = false;

    RecordManager recordManager;

    String pathSelectFile = "";
    String pathFileTmp;

    ImageButton mImageButtonPlus;
    TextView mTextViewInfo;
    ImageButton mImageButtonSearch;
    ImageButton mImageButtonSearchFromFile;
    TextView mTextViewResult;
    Button mButtonRangeFq;
    Button mButtonTwitter;
    Button mButtonGithub;

    boolean isHF = true;
    boolean animateActionButton = false;
    boolean inRecord = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        checkPermissions();

        File file = new File(this.getCacheDir(), "record_tmpSignalID.wav");
        pathFileTmp = file.getAbsolutePath();

        // Link UI
        mImageButtonPlus = findViewById(R.id.main_imageButtonPlus);
        mTextViewInfo = findViewById(R.id.main_textViewInfo);
        mImageButtonSearch = findViewById(R.id.main_imageButtonSearch);
        mImageButtonSearchFromFile = findViewById(R.id.main_imageButtonSearchFromFile);
        mTextViewResult = findViewById(R.id.main_textViewResult);
        mButtonRangeFq = findViewById(R.id.main_buttonRangeFq);
        mButtonTwitter = findViewById(R.id.main_buttonTwitter);
        mButtonGithub = findViewById(R.id.main_buttonGithub);

        mImageButtonPlus.setOnClickListener(this);
        mImageButtonSearch.setOnClickListener(this);
        mImageButtonSearchFromFile.setOnClickListener(this);
        mButtonRangeFq.setOnClickListener(this);
        mButtonTwitter.setOnClickListener(this);
        mButtonGithub.setOnClickListener(this);

        checkPermissions();

        if (!permissionsOk) {
            mImageButtonSearch.setClickable(false);
            mImageButtonSearchFromFile.setClickable(false);
            mTextViewInfo.setText("Permissions required to use this application, please check.");
        } else {
            mImageButtonSearch.setClickable(true);
            mImageButtonSearchFromFile.setClickable(true);
        }

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

                if (!mTextViewInfo.getText().toString().contentEquals("Recording") && !mTextViewInfo.getText().toString().contentEquals("Searching"))
                    mTextViewInfo.setText(informations[n]);

                handlerInformations.postDelayed(this, 10000);
            }
        };
        handlerInformations.postDelayed(rInformations, 10000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_imageButtonPlus:
                Intent myIntent = new Intent(this, PlusActivity.class);
                startActivity(myIntent);
                break;
            case R.id.main_imageButtonSearch:
                if (!inRecord && !mTextViewInfo.getText().toString().contentEquals("Searching")) {
                    inRecord = true;
                    mTextViewInfo.setText("Recording");
                    mTextViewResult.setText("");
                    new File(pathFileTmp).delete();
                    recordManager = new RecordManager(pathFileTmp);
                    recordManager.start();

                    mImageButtonSearch.setClickable(false);
                    mImageButtonSearchFromFile.setClickable(false);
                    mButtonRangeFq.setClickable(false);
                    mImageButtonPlus.setClickable(false);
                    animateActionButton = true;

                    // MIN time record file => 5 seconds
                    Handler handler = new Handler();
                    Runnable r = new Runnable() {
                        public void run() {
                            mImageButtonSearch.setClickable(true);
                            mImageButtonSearchFromFile.setClickable(true);
                            mButtonRangeFq.setClickable(true);
                            mImageButtonPlus.setClickable(true);
                        }
                    };
                    handler.postDelayed(r, 5010);

                    // MAX time record file => 30 seconds
                    Handler handlerTimeout = new Handler();
                    Runnable r2 = new Runnable() {
                        public void run() {
                            mImageButtonSearch.setClickable(true);
                            mImageButtonSearchFromFile.setClickable(true);
                            mButtonRangeFq.setClickable(true);
                            mImageButtonPlus.setClickable(true);
                            mImageButtonSearch.performClick();
                        }
                    };
                    handlerTimeout.postDelayed(r2, 29900);

                } else {
                    inRecord = false;
                    animateActionButton = false;
                    recordManager.stop();
                    mTextViewInfo.setText(informations[0]);

                    // Run SALOME algorithm
                    System.out.println("Run SALOME algorithm (MIC)");
                    mTextViewInfo.setText("Searching");
                    RecognizerThreadMic tRecognizer = new RecognizerThreadMic();
                    tRecognizer.start();
                }
                break;
            case R.id.main_imageButtonSearchFromFile:
                if(!mTextViewInfo.getText().toString().contentEquals("Recording")) {
                    mTextViewResult.setText("");
                    DialogProperties properties = new DialogProperties();
                    properties.selection_mode = DialogConfigs.SINGLE_MODE;
                    properties.selection_type = DialogConfigs.FILE_SELECT;
                    properties.root = new File(DialogConfigs.DEFAULT_DIR);
                    properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
                    properties.offset = new File(DialogConfigs.DEFAULT_DIR);
                    properties.extensions = null;

                    FilePickerDialog dialog = new FilePickerDialog(MainActivity.this, properties);
                    dialog.setTitle("Select a WAV File");

                    dialog.setDialogSelectionListener(new DialogSelectionListener() {
                        @Override
                        public void onSelectedFilePaths(String[] files) {
                            if (files[0].toLowerCase().contains(".wav")) {
                                pathSelectFile = files[0];
                                System.out.println("Run SALOME algorithm (FILE)");
                                mTextViewInfo.setText("Searching");
                                mImageButtonSearch.setClickable(false);
                                mImageButtonSearchFromFile.setClickable(false);
                                RecognizerThreadFile tRecognizer = new RecognizerThreadFile();
                                tRecognizer.start();
                            } else
                                mTextViewResult.setText("Select a WAV file");
                        }
                    });

                    dialog.show();
                }

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
            case R.id.main_buttonTwitter:
                Intent browserIntentTwitter = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/tortillum"));
                startActivity(browserIntentTwitter);
                break;
            case R.id.main_buttonGithub:
                Intent browserIntentGithub = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Neosama/SignalID"));
                startActivity(browserIntentGithub);
                break;
        }
    }

    public class RecognizerThreadMic extends Thread {
        public RecognizerThreadMic(){
            super();
        }
        public void run(){
            try {
                int typeFrequencies;
                if (isHF)
                    typeFrequencies = INFO_SIGNAL.TYPE_FREQUENCIES_L30;
                else
                    typeFrequencies = INFO_SIGNAL.TYPE_FREQUENCIES_M30;
                final Recognizer recognizer = new Recognizer(MainActivity.this, pathFileTmp, INFO_SIGNAL.TYPE_MODE_USB, typeFrequencies, false);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextViewResult.setText(recognizer.getResult());
                        mTextViewInfo.setText(informations[0]);
                    }
                });
            } catch (IOException | WavFileException e) {
                e.printStackTrace();
            }
        }
    }

    public class RecognizerThreadFile extends Thread {
        public RecognizerThreadFile(){
            super();
        }
        public void run(){
            try {
                int typeFrequencies;
                if (isHF)
                    typeFrequencies = INFO_SIGNAL.TYPE_FREQUENCIES_L30;
                else
                    typeFrequencies = INFO_SIGNAL.TYPE_FREQUENCIES_M30;
                final Recognizer recognizer = new Recognizer(MainActivity.this, pathSelectFile, INFO_SIGNAL.TYPE_MODE_USB, typeFrequencies, false);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextViewResult.setText(recognizer.getResult());
                        mTextViewInfo.setText(informations[0]);
                        mImageButtonSearch.setClickable(true);
                        mImageButtonSearchFromFile.setClickable(true);
                    }
                });
            } catch (IOException | WavFileException e) {
                e.printStackTrace();
            }
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