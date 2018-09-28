package com.jokopriyono.kamusdicoding.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jokopriyono.kamusdicoding.CustomPopup;
import com.jokopriyono.kamusdicoding.R;
import com.jokopriyono.kamusdicoding.data.database.model.KamusModel;

import java.util.List;

public class CariKataAdapter extends RecyclerView.Adapter<CariKataAdapter.Holder> {

    private List<KamusModel> kamusModels;
    private Activity activity;

    public CariKataAdapter(Activity activity, List<KamusModel> kamusModels) {
        this.kamusModels = kamusModels;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Holder(
                LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.cari_kata_item, viewGroup, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int pos) {
        final String word = kamusModels.get(pos).getWord();
        final String translate = kamusModels.get(pos).getTranslate();
        holder.txtWord.setText(word);
        holder.txtTranslate.setText(translate);
        holder.cardKata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomPopup customPopup = new CustomPopup(activity, word, translate);
                customPopup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return kamusModels.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView txtWord, txtTranslate;
        CardView cardKata;

        Holder(@NonNull View view) {
            super(view);

            txtWord = view.findViewById(R.id.txt_word);
            txtTranslate = view.findViewById(R.id.txt_translate);
            cardKata = view.findViewById(R.id.card_kata);
        }
    }
}
