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

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private static final String TAG = ">>>>>> DetailActivity.java <<<<<<";

    private TextView mOriginTV;
    private TextView mDescriptionTV;
    private TextView mIngredientsTV;
    private TextView mAkaTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mOriginTV = (TextView) findViewById(R.id.origin_tv);
        mDescriptionTV = (TextView) findViewById(R.id.description_tv);
        mIngredientsTV = (TextView) findViewById(R.id.ingredients_tv);
        mAkaTV = (TextView) findViewById(R.id.also_known_tv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
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
        String mainName = sandwich.getMainName();
        String originBody = ((sandwich.getPlaceOfOrigin().isEmpty()) ? "n/a" : sandwich.getPlaceOfOrigin());
        String descriptionBody = ((sandwich.getDescription().isEmpty()) ? "n/a" : sandwich.getDescription());
        List<String> akaBody = sandwich.getAlsoKnownAs();
        List<String> ingredientBody = sandwich.getIngredients();

        mOriginTV.append("- " + originBody);
        mDescriptionTV.append(descriptionBody);

        if (akaBody.isEmpty()) { mAkaTV.append("- n/a" + "\n"); }
        for (String akaItem : akaBody) {
            mAkaTV.append("- " + akaItem + "\n");
        }

        if (ingredientBody.isEmpty()) { mIngredientsTV.append("- n/a" + "\n"); }
        for (String ingredientItem : ingredientBody) {
            mIngredientsTV.append( "- " + ingredientItem + "\n");
        }
    }
}
