package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        }catch(JSONException ex){
            Log.d(TAG, "Unexpected error during JSON parse: " + ex.getMessage());
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //Also Know
        TextView mAlsoKnowAsTV = findViewById(R.id.also_known_tv);
        String alsoKnowAs = "";
        for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
            alsoKnowAs += sandwich.getAlsoKnownAs().get(i);
            if (i < sandwich.getAlsoKnownAs().size() - 1) alsoKnowAs += ", ";
        }
        mAlsoKnowAsTV.setText(alsoKnowAs);

        //Description
        TextView mDescriptionTV = findViewById(R.id.description_tv);
        mDescriptionTV.setText(sandwich.getDescription());

        //Origin
        TextView mOriginTV = findViewById(R.id.origin_tv);
        mOriginTV.setText(sandwich.getPlaceOfOrigin());

        //Ingredients
        TextView mIngredientsTV = findViewById(R.id.ingredients_tv);
        String ingredients = "";
        for (int i = 0; i < sandwich.getIngredients().size(); i++) {
            ingredients += sandwich.getIngredients().get(i);
            if (i < sandwich.getIngredients().size() - 1) ingredients += ", ";
        }
        mIngredientsTV.setText(ingredients);
    }
}
