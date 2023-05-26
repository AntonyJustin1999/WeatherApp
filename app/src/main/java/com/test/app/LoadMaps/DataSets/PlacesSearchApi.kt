package com.test.app.LoadMaps.DataSets

import com.google.gson.annotations.SerializedName

class PlacesSearchApi {
        @SerializedName("features")
        var features: ArrayList<Features>? = null
    }

    class Features {
        @SerializedName("place_name")
        var place_name: String? = null
        @SerializedName("center")
        var center: ArrayList<String>? = null
    }