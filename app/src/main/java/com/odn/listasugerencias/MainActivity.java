package com.odn.listasugerencias;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.odn.listasugerencias.api.ServiceApi;
import com.odn.listasugerencias.model.Sugerencia;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<List<Sugerencia>>, SwipeRefreshLayout.OnRefreshListener, ImageAdapter.ClickListener {

    private List<Sugerencia> imageMemeList;
    RecyclerView rvImageTop;
    private Context context;
    private ImageAdapter imageAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        rvImageTop = (RecyclerView) findViewById(R.id.fi_rv_image_top);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        imageMemeList = new ArrayList<>();
        swipeRefreshLayout.setOnRefreshListener(this);

        loadList();
    }

    public void loadList() {

        ServiceApi serviceApi = ServiceApi.retrofit.create(ServiceApi.class);
        final Call<List<Sugerencia>> call = serviceApi.getSugerencia();
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<List<Sugerencia>> call, Response<List<Sugerencia>> response) {
        swipeRefreshLayout.setRefreshing(false);
        try {
            imageMemeList = response.body();
            initRv(imageMemeList);
        } catch (Exception e) {
            Log.e("error de imagen", e + "");
        }
    }

    @Override
    public void onFailure(Call<List<Sugerencia>> call, Throwable t) {
        swipeRefreshLayout.setRefreshing(false);
        Log.e("error", String.valueOf(t));
    }

    void initRv(List<Sugerencia> medioFolioses) {
        imageAdapter = new ImageAdapter(context, medioFolioses);
        imageAdapter.setClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        rvImageTop.setLayoutManager(mLayoutManager);
        rvImageTop.setItemAnimator(new DefaultItemAnimator());
        rvImageTop.setAdapter(imageAdapter);
    }

    @Override
    protected void onRestart() {
        loadList();
        super.onRestart();
    }

    @Override
    public void onRefresh() {
        loadList();
    }

    @Override
    public void itemClicked() {
        loadList();
    }
}
