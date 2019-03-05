package com.dreytech.clientdreymart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;

import com.dreytech.clientdreymart.Adapter.BarangAdapter;
import com.dreytech.clientdreymart.Model.Barang;
import com.dreytech.clientdreymart.Retrofit.IDreyMarketAPI;
import com.dreytech.clientdreymart.Utils.Common;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {

    List<String> suggestList = new ArrayList<>();
    List<Barang> localDataSource = new ArrayList<>();
    MaterialSearchBar searchBar;

    IDreyMarketAPI mService;
    RecyclerView recycler_search;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    BarangAdapter searchAdapter, adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Init Service
        mService = Common.getAPI();

        recycler_search = (RecyclerView)findViewById(R.id.recycler_search);
        recycler_search.setLayoutManager(new GridLayoutManager(this,2));

        searchBar = (MaterialSearchBar)findViewById(R.id.searchBar);
        searchBar.setHint("Masukkan Nama Barang");

        loadAllDrink();

        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<>();
                for(String search:suggestList)
                {
                    if (search.toLowerCase().contains(searchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                searchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled)
                    recycler_search.setAdapter(adapter); // kembali ke tampilan awal
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

    }

    private void startSearch(CharSequence text) {
        List<Barang> result = new ArrayList<>();
        for (Barang barang:localDataSource)
            if (barang.Name.contains(text))
                result.add(barang);
        searchAdapter = new  BarangAdapter(this,result);
        recycler_search.setAdapter(searchAdapter);
    }


    private void loadAllDrink() {
        compositeDisposable.add(mService.getAllBarangs().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(new Consumer<List<Barang>>() {
                @Override
                public void accept(List<Barang> barangs) throws Exception {
                    displayListBarang(barangs);
                    buildSuggestList(barangs);
                }
            }));
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();

    }

    private void buildSuggestList(List<Barang> barangs) {
        for (Barang barang:barangs)
            suggestList.add(barang.Name);
        searchBar.setLastSuggestions(suggestList);

    }

    private void displayListBarang(List<Barang> barangs) {
        localDataSource = barangs;
        adapter = new BarangAdapter(this,barangs);
        recycler_search.setAdapter(adapter);
    }
}
