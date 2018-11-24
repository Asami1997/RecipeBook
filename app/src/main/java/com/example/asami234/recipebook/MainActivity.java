package com.example.asami234.recipebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void findRecipe(View view) {

        DB_Handler dbHandler = new DB_Handler(this, null, null, 1);

        Recipe recipe = dbHandler.findRecipe(recipeTitleET.getText().toString());

        if(recipe !=null) {
            recipeIDET.setText(recipe.getRecipe_id());
            recipeTitleET.setText(String.valueOf(recipe.getRecipe_title()));
            recipeContentET.setText(String.valueOf(recipe.getRecipe_content()));
        }else{
            recipeIDET.setText("No Match Found");
        }
    }


}
