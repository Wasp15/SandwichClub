package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        ingredientsIv.setImageResource(R.drawable.vada_pav);

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
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        TextView originTextView = findViewById(R.id.origin_tv);
        String originString = sandwich.getPlaceOfOrigin() + "\n";
        originTextView.setText(originString);

        TextView alsoKnownTextView = findViewById(R.id.also_known_tv);
        List<String> alsoKnownList = sandwich.getAlsoKnownAs();
        alsoKnownTextView.setText(usableListString(alsoKnownList));

        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);
        List<String> ingredientsList = sandwich.getIngredients();
        ingredientsTextView.setText(usableListString(ingredientsList));

        TextView descriptionTextView = findViewById(R.id.description_tv);
        descriptionTextView.setText(sandwich.getDescription());
    }

    private StringBuilder usableListString(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder("");
        for (String string : list) {
            String temp = string + "\n";
            stringBuilder.append(temp);
        }

        return stringBuilder;
    }
}

