package com.jokopriyono.kamusdicoding;

import android.content.Intent;
import android.content.res.Resources;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.jokopriyono.kamusdicoding.data.database.helper.KamusHelper;
import com.jokopriyono.kamusdicoding.data.database.model.KamusModel;
import com.jokopriyono.kamusdicoding.data.sharedpref.SessionPref;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PreloadActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private SessionPref sessionPref;
    private KamusHelper kamusHelper;

    private double progress;
    private double maxprogress = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preload);

        progressBar = findViewById(R.id.progress_bar);
        sessionPref = new SessionPref(this);
        kamusHelper = new KamusHelper(this);
        if (!sessionPref.checkPayload()) {
            progressBar.setProgress(0);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<KamusModel> kamusEng = loadKamus(R.raw.english_indonesia);
                    List<KamusModel> kamusIDN = loadKamus(R.raw.indonesia_english);

                    try {
                        kamusHelper.open();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    Double progressMax = 100.0;
                    Double progressDiff = (progressMax - progress) / (kamusEng.size() + kamusIDN.size());

                    kamusHelper.insertKataKata(kamusEng, true);
                    progress += progressDiff;
                    updateProgressBar(progress);

                    kamusHelper.insertKataKata(kamusIDN, false);
                    progress += progressDiff;
                    updateProgressBar(progress);

                    kamusHelper.close();
                    sessionPref.setPayload();

                    updateProgressBar(maxprogress);

                    startActivity(new Intent(PreloadActivity.this, MainActivity.class));
                    finish();
                }
            }).start();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void updateProgressBar(final double progress){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress((int) progress);
            }
        });
    }

    private List<KamusModel> loadKamus(int data){
        List<KamusModel> kamusModels = new ArrayList<>();
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream rawKamus = res.openRawResource(data);

            reader = new BufferedReader(new InputStreamReader(rawKamus));
            String line;
            while ((line = reader.readLine()) != null){
                String[] splitstr = line.split("\t");
                KamusModel kamusModel;
                kamusModel = new KamusModel();
                kamusModel.setWord(splitstr[0]);
                kamusModel.setTranslate(splitstr[1]);
                kamusModels.add(kamusModel);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return kamusModels;
    }
}
