package com.odn.listasugerencias.utils;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by EDGAR ARANA on 27/04/2017.
 * esta clase se usa para castear los valores y madarlos pór post
 */

public class ValuePost {

    public static RequestBody getValue(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }
}
