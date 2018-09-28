package com.jokopriyono.kamusdicoding;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

public class CustomPopup extends Dialog {
    private String word;
    private String translate;

    public CustomPopup(@NonNull Context context, String word, String translate) {
        super(context);
        this.word = word;
        this.translate = translate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_popup);

        TextView txtWord = findViewById(R.id.txt_word);
        TextView txtTranslate = findViewById(R.id.txt_translate);

        txtWord.setText(word);
        txtTranslate.setText(translate);
    }
}
