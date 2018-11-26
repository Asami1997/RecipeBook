package com.example.asami234.recipebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

// display recipe content
public class Display_Activity extends AppCompatActivity {

    // declaring variables
    String recipe_title;

    String recipe_content;

    TextView title_textview;

    TextView instructions_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_);

        // initializing variables
        title_textview = findViewById(R.id.titleRecipeAdd);

        instructions_textview = findViewById(R.id.recipeInstructions);

        Intent intent = getIntent();

        // get the recipe title from the extras passed with this intent
        recipe_title = intent.getStringExtra("recipetitle");

        // get the recipe instructions from the extras passed with this intent
        recipe_content = intent.getStringExtra("recipecontent");

        setTextViews();
    }

    // set the title and instructions textviews
    private void setTextViews() {

        // set recipe title
        title_textview.setText(recipe_title);

        // set recipe instructions
        instructions_textview.setText(recipe_content);
    }



    // called when the user clicks on the delete button
    public void deleteProduct (View view) {

        DB_Handler dbHandler = new DB_Handler(this, null, null,1);
        boolean result = dbHandler.deleteRecipe(recipe_title);
        if (result) {
            finish();
            Toast.makeText(this, "Recipe deleted", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, " Failed to delete recipe deleted", Toast.LENGTH_SHORT).show();
        }
    }
}
