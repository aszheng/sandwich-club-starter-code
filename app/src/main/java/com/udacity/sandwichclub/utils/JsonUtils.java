package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        JSONObject rawJsonObject = null;
        try {
            rawJsonObject = new JSONObject(json);
            JSONObject name = rawJsonObject.optJSONObject("name");

            String mainName = name.optString("mainName");
            String placeOfOrigin = rawJsonObject.optString("placeOfOrigin");
            String description = rawJsonObject.optString("description");
            String image = rawJsonObject.optString("image");

            List<String> alsoKnownAs = new ArrayList<>();
            List<String> ingredients = new ArrayList<>();
            JSONArray alsoKnownAsARR = name.optJSONArray("alsoKnownAs");
            JSONArray ingredientsARR = rawJsonObject.optJSONArray("ingredients");

            if (alsoKnownAsARR != null) {
                for (int i=0;i<alsoKnownAsARR.length();i++){
                    alsoKnownAs.add(alsoKnownAsARR.getString(i));
                }
            }

            if (ingredientsARR != null) {
                for (int i=0;i<ingredientsARR.length();i++){
                    ingredients.add(ingredientsARR.getString(i));
                }
            }

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
