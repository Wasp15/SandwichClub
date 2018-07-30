package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        String mainNameString = "";
        List<String> alsoKnownList = new ArrayList<>();
        String originString = "";
        String descriptionString = "";
        String image = "";
        List<String> ingredientsList = new ArrayList<>();

        try {
            JSONObject baseJSONResponse = new JSONObject(json);

            JSONObject namesObject = baseJSONResponse.getJSONObject("name");
            if (namesObject != null) {
                mainNameString = namesObject.getString("mainName");
                JSONArray alsoKnownJSONArray = namesObject.getJSONArray("alsoKnownAs");
                if (alsoKnownJSONArray != null && alsoKnownJSONArray.length() > 0) {
                    for (int i = 0; i < alsoKnownJSONArray.length(); i++)
                        alsoKnownList.add(alsoKnownJSONArray.get(i).toString());
                } else
                    alsoKnownList.add("Unknown");

            }

            String placeOfOriginTemp = baseJSONResponse.getString("placeOfOrigin");
            if (placeOfOriginTemp != null && placeOfOriginTemp.length() > 0)
                originString = placeOfOriginTemp;
            else
                originString = "Not Available";

            JSONArray ingredientsListTemp = baseJSONResponse.getJSONArray("ingredients");
            if (ingredientsListTemp != null && ingredientsListTemp.length() > 0) {
                for (int i = 0; i < ingredientsListTemp.length(); i++)
                    ingredientsList.add(ingredientsListTemp.get(i).toString());
            }

            String descriptionTemp = baseJSONResponse.getString("description");
            if (descriptionTemp != null && descriptionTemp.length() > 0)
                descriptionString = descriptionTemp;
            else
                originString = "Not Available";

            String imageTemp = baseJSONResponse.getString("image");
            if (imageTemp != null && imageTemp.length() > 0)
                image = imageTemp;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new Sandwich(mainNameString, alsoKnownList, originString, descriptionString, image, ingredientsList);
    }
}

