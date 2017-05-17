package com.odn.listasugerencias.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by edgararana on 06/05/17.
 */

public class Response {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("value")
    @Expose
    private String value;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
