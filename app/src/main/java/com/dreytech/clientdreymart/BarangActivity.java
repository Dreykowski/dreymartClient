package com.dreytech.clientdreymart;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dreytech.clientdreymart.Adapter.BarangAdapter;
import com.dreytech.clientdreymart.Model.Barang;
import com.dreytech.clientdreymart.Retrofit.IDreyMarketAPI;
import com.dreytech.clientdreymart.Utils.Common;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BarangActivity extends AppCompatActivity {

    IDreyMarketAPI mService;
    RecyclerView lst_barang;

    TextView txt_banner_name;

    //rx java
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang);

        mService = Common.getAPI();

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_to_refresh);


        lst_barang = (RecyclerView)findViewById(R.id.recycler_barangs);
        lst_barang.setLayoutManager(new GridLayoutManager(this,2));
        lst_barang.setHasFixedSize(true);

        txt_banner_name = (TextView)findViewById(R.id.txt_menu_name);
        txt_banner_name.setText(Common.currentCategory.Name);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                loadListBarang(Common.currentCategory.ID);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(true);
                loadListBarang(Common.currentCategory.ID);
            }
        });


    }

    private void loadListBarang(String menuId) {
        compositeDisposable.add(mService.getBarang(menuId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<Barang>>() {
            @Override
            public void accept(List<Barang> barangs) throws Exception {
                displayBarangList(barangs);
            }
        }));
    }

    private void displayBarangList(List<Barang> barangs) {
        BarangAdapter adapter = new BarangAdapter(this, barangs);

        lst_barang.setAdapter(adapter);

        swipeRefreshLayout.setRefreshing(false);
    }



    @Override
    protected void onResume() {
        super.onResume();

    }
}
