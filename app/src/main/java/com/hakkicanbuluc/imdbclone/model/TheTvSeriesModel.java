package com.hakkicanbuluc.imdbclone.model;

import java.util.HashMap;

public class TheTvSeriesModel {
    String name;

    public static TheTvSeriesModel fromMap(HashMap map) {
        TheTvSeriesModel theTvSeriesModel = new TheTvSeriesModel();
        theTvSeriesModel.name = (String) map.get("name");
        return theTvSeriesModel;
    }

    public String getName() {
        return name;
    }
}
