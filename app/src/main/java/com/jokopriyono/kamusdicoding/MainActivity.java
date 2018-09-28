package com.jokopriyono.kamusdicoding;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jokopriyono.kamusdicoding.adapter.CariKataAdapter;
import com.jokopriyono.kamusdicoding.data.database.helper.KamusHelper;
import com.jokopriyono.kamusdicoding.data.database.model.KamusModel;
import com.lapism.searchview.Search;
import com.lapism.searchview.widget.SearchBar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private KamusHelper kamusHelper;
    private SearchBar searchView;
    private RecyclerView recyclerView;
    private boolean bahasa = true;
    private TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTitle = findViewById(R.id.txt_title);
        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.recycler);
        FloatingActionButton fab = findViewById(R.id.fab);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        kamusHelper = new KamusHelper(this);

        searchView.setHint(getString(R.string.hint_eng));
        searchView.setOnQueryTextListener(new Search.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(CharSequence query) {
                searchView.close();
                return true;
            }

            @Override
            public void onQueryTextChange(CharSequence query) {
                loadKata(query.toString().trim());

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bahasa){
                    txtTitle.setText(getString(R.string.idn_to_eng));
                    searchView.setHint(getString(R.string.hint_idn));
                    bahasa = false;
                } else {
                    txtTitle.setText(getString(R.string.eng_to_idn));
                    searchView.setHint(getString(R.string.hint_eng));
                    bahasa = true;
                }
            }
        });
    }

    private void loadKata(String query){
        List<KamusModel> kamusModels;
        try {
            kamusHelper = kamusHelper.open();
            kamusModels = kamusHelper.cariKata(query, bahasa);

            CariKataAdapter adapter = new CariKataAdapter(MainActivity.this, kamusModels);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            recyclerView.setAdapter(adapter);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            kamusHelper.close();
        }

    }
}
