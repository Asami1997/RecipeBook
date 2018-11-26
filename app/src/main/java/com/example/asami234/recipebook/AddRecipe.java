package com.example.asami234.recipebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    }


    public void addRecipe (View view) {

        recipeTitle = recipeTitleET.getText().toString();
        recipeContent = recipeContentET.getText().toString();

        if(recipeTitle.isEmpty() || recipeContent.isEmpty()){

            Toast.makeText(this, "Please enter both the title and the instructions", Toast.LENGTH_SHORT).show();
        }else{

            DB_Handler dbHandler = new DB_Handler(this,"recipes", null,1);

            Recipe recipe = new Recipe(recipeTitle,recipeContent);

            dbHandler.addRecipe(recipe);
            recipeTitleET.setText("");
            recipeContentET.setText("");
            recipeIDET.setText("");

            finish();
        }

    }

}
