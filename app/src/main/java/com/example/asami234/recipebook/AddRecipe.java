package com.example.asami234.recipebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddRecipe extends AppCompatActivity {

    // declaring variables

    EditText recipeTitleET;
    EditText recipeContentET;
    TextView recipeIDET;
    String recipeTitle;
    String recipeContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        //initializing variables
        recipeTitleET = findViewById(R.id.recipeTitleET);
        recipeContentET = findViewById(R.id.recipeContentMET);
        recipeIDET = findViewById(R.id.recipeID);

    }


    // called when user clicks add button
    public void addRecipe (View view) {

        Log.i("recipeapp","in add recipe function ");


        // get title from title edit_text
        recipeTitle = recipeTitleET.getText().toString();
        // get instructions from instructions edit text
        recipeContent = recipeContentET.getText().toString();

        // checks if the user did not input the title or the instructions filed and notify
        if(recipeTitle.isEmpty() || recipeContent.isEmpty()){

            Toast.makeText(this, "Please enter both the title and the instructions", Toast.LENGTH_SHORT).show();
        }else{

            DB_Handler dbHandler = new DB_Handler(this,"recipes", null,1);

            // creates a new recipe object
            Recipe recipe = new Recipe(recipeTitle,recipeContent);

            dbHandler.addRecipe(recipe);

            // resets textviews
            recipeTitleET.setText("");
            recipeContentET.setText("");
            recipeIDET.setText("");

            // go back to main activity
            finish();
        }

    }

}
