package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        //Create the final fields which reflects the fields of Sandwich class
        final String SANDWICH_NAME = "name";
        final String SANDWICH_MAIN_NAME = "mainName";
        final String SANDWICH_ALSO_KNOW_AS = "alsoKnownAs";

        final String SANDWICH_PLACE_OF_ORIGIN = "placeOfOrigin";

        final String SANDWICH_DESCRIPTION = "description";

        final String SANDWICH_IMAGE = "image";

        final String SANDWICH_INGREDIENTS = "ingredients";

        Sandwich sandwich = new Sandwich();

        JSONObject sandwichJson = new JSONObject(json);

        //Sandwich name
        JSONObject sandwichName = sandwichJson.getJSONObject(SANDWICH_NAME);
        sandwich.setMainName(sandwichName.getString(SANDWICH_MAIN_NAME));
        JSONArray sandwichKnowAsJsonArray = sandwichName.getJSONArray(SANDWICH_ALSO_KNOW_AS);
        sandwich.setAlsoKnownAs(new ArrayList<String>());
        for (int i = 0; i < sandwichKnowAsJsonArray.length(); i++){
            sandwich.getAlsoKnownAs().add(sandwichKnowAsJsonArray.getString(i));
        }

        //Sandwich place of origin
        sandwich.setPlaceOfOrigin(sandwichJson.getString(SANDWICH_PLACE_OF_ORIGIN));

        //Sandwich description
        sandwich.setDescription(sandwichJson.getString(SANDWICH_DESCRIPTION));

        //Sandwich image
        sandwich.setImage(sandwichJson.getString(SANDWICH_IMAGE));

        //Sandwich ingredients
        JSONArray sandwichIngredientsJsonArray = sandwichJson.getJSONArray(SANDWICH_INGREDIENTS);
        sandwich.setIngredients(new ArrayList<String>());
        for (int i = 0; i < sandwichIngredientsJsonArray.length(); i++){
            sandwich.getIngredients().add(sandwichIngredientsJsonArray.getString(i));
        }

        return sandwich;
    }
}
