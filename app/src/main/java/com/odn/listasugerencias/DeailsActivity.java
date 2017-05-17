package com.odn.listasugerencias;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.odn.listasugerencias.api.ServiceApi;
import com.odn.listasugerencias.model.Response;
import com.odn.listasugerencias.utils.ValuePost;

import retrofit2.Call;
import retrofit2.Callback;

public class DeailsActivity extends AppCompatActivity implements View.OnClickListener {

    String id;
    TextView txt;
    EditText editText;
    Button button;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deails);
        context = this;
        id = getIntent().getExtras().getString("id");
        txt = (TextView) findViewById(R.id.ad_txt_id);
        editText = (EditText) findViewById(R.id.ad_et_desc);
        button = (Button) findViewById(R.id.ad_btn_send);
        txt.setText(id);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String desc = editText.getText().toString();
        if (!desc.trim().equals("")) {
            aceppt(id, desc);
            Log.e("id", id);
            Log.e("desc", desc);
        } else {
            Toast.makeText(this, "las descripcion esta vacia", Toast.LENGTH_SHORT).show();
        }
    }

    void aceppt(String id, String desc) {

        ServiceApi serviceApi = ServiceApi.retrofit.create(ServiceApi.class);
        Call<Response> call = serviceApi.setAcepet(ValuePost.getValue(id), ValuePost.getValue(desc));
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                try {
                    if (response.body().getValue().equals("1")) {

                        new MaterialDialog.Builder(context)
                                .title("Imagen se a aceptado")
                                .positiveText("Aceptar").cancelable(false)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        finish();
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

    void error() {
        new MaterialDialog.Builder(context)
                .title("Erro")
                .positiveText("Aceptar")
                .show();
    }
}
