package com.example.asami234.recipebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddRecipe extends AppCompatActivity {

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

        recipeTitle = recipeTitleET.getText().toString();
        recipeContent = recipeContentET.getText().toString();

    }


    public void addRecipe (View view) {


    }


    public void deleteProduct (View view) {

 }

}
