package com.odn.listasugerencias;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.odn.listasugerencias.api.ServiceApi;
import com.odn.listasugerencias.model.Response;
import com.odn.listasugerencias.model.Sugerencia;
import com.odn.listasugerencias.utils.ValuePost;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by EDGAR ARANA on 16/05/2017.
 */

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Sugerencia> imageMemeList;
    private Context context;
    private ClickListener clickListener;


    public ImageAdapter(Context context, List<Sugerencia> moviesList) {
        this.imageMemeList = moviesList;
        this.context = context;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_image, parent, false);

        return new ViewHolderImage(view);


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final Sugerencia sugerencia = imageMemeList.get(position);

        if (holder instanceof ViewHolderImage) {
            final ViewHolderImage viewHolderImage = (ViewHolderImage) holder;
            LinearLayout.LayoutParams layoutParams
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            viewHolderImage.tvDesc.setText(imageMemeList.get(position).getId() + "");

            viewHolderImage.btnAceppt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DeailsActivity.class);
                    intent.putExtra("id", sugerencia.getId() + "");
                    context.startActivity(intent);

                }
            });

            viewHolderImage.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


            viewHolderImage.progressImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewHolderImage.progressImageView.setAdjustViewBounds(true);
            viewHolderImage.progressImageView.setLayoutParams(layoutParams);
            Glide.with(context).load(sugerencia.getUrl()).into(viewHolderImage.progressImageView);
            viewHolderImage.main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }


    }


    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {

        if (holder instanceof ViewHolderImage) {
            ViewHolderImage viewHolderVideo = (ViewHolderImage) holder;
            super.onViewRecycled(viewHolderVideo);
        }

    }

    // TODO: 27/04/2017 holder de la iamgenes
    class ViewHolderImage extends RecyclerView.ViewHolder {
        FrameLayout main;
        TextView tvDesc;
        ImageView progressImageView;
        Button btnAceppt;
        Button btnCancel;


        ViewHolderImage(View view) {
            super(view);

            main = (FrameLayout) view.findViewById(R.id.almf_root);
            tvDesc = (TextView) view.findViewById(R.id.ii_text);
            progressImageView = (ImageView) view.findViewById(R.id.almf_iv_image);
            btnAceppt = (Button) view.findViewById(R.id.ii_btn_acept);
            btnCancel = (Button) view.findViewById(R.id.ii_btn_cancel);


        }
    }


    @Override
    public int getItemCount() {
        return imageMemeList.size();
    }


    void error() {
        new MaterialDialog.Builder(context)
                .title("Erro")
                .positiveText("Aceptar")
                .show();
    }

    void cancel(String id) {
        ServiceApi serviceApi = ServiceApi.retrofit.create(ServiceApi.class);
        Call<Response> call = serviceApi.setCancel(ValuePost.getValue(id));
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                try {
                    if (response.body().getValue().equals("1")) {
                        new MaterialDialog.Builder(context)
                                .title("Imagen se a Rechazada")
                                .positiveText("Aceptar")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        clickListener.itemClicked();
                                    }
                                }).show();

                    }

                } catch (Exception e) {
                    Log.e("catch", String.valueOf(e));
                    error();
                }

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.e("errer", String.valueOf(t));
                error();
            }
        });

    }

    public interface ClickListener {
        void itemClicked();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


}