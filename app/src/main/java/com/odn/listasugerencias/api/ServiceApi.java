package com.odn.listasugerencias.api;

import com.odn.listasugerencias.model.Response;
import com.odn.listasugerencias.model.Sugerencia;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by EDGAR ARANA on 16/05/2017.
 */

public interface ServiceApi {

    // TODO: 08/11/2016  este objeto retrofit recibe la url general
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://360scripts.com.mx/train_v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("manager_sugerencia/sugerencias.php")
    Call<List<Sugerencia>> getSugerencia();

    @Multipart
    @POST("manager_sugerencia/unlink_sugerencia.php")
    Call<Response> setCancel(@Part("id") RequestBody id);

    @Multipart
    @POST("manager_sugerencia/aceptar_sugerencia.php")
    Call<Response> setAcepet(@Part("id") RequestBody id, @Part("desc") RequestBody desc);

}
