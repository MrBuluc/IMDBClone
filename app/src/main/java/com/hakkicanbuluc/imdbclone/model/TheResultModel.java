package com.hakkicanbuluc.imdbclone.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class TheResultModel {

    @SerializedName("results")
    List<HashMap> results;

    public List<HashMap> getResults() {
        return results;
    }
}
