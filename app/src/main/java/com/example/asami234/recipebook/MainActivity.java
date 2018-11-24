package com.example.asami234.recipebook;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asami234.recipebook.contentprovider.RecipeContentProvider;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    ArrayList<String> recipes;

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.recipesListView);

        recipes = new ArrayList<String>();


        getRecipes();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String recipe = recipes.get(i);

                Toast.makeText(MainActivity.this, recipe, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void goToAddActivity(View view){

        Intent intent = new Intent(getApplicationContext(),AddRecipe.class);
        startActivity(intent);
    }

    //get recipes
    public void getRecipes(){


        String[] mProjection = new String[] {
                Recipe_Contract.COLUMN_RECIPE_ID,
                Recipe_Contract.COLUMN_RECIPE_TITLE,
                Recipe_Contract.COLUMN_RECIPE_CONTENT
        };


        Cursor cursor = DB_Handler.contentResolver.query(RecipeContentProvider.CONTENT_URI, mProjection,
                null, null, null);


        while (cursor!=null &&cursor.moveToNext()) {
            Log.i("craching","crach");
            Recipe recipe = new Recipe(
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(Recipe_Contract.COLUMN_RECIPE_ID))),
                    cursor.getString(cursor.getColumnIndex(Recipe_Contract.COLUMN_RECIPE_TITLE)),
                    cursor.getString(cursor.getColumnIndex(Recipe_Contract.COLUMN_RECIPE_CONTENT)));
            Log.i("craching","crach");
            recipes.add(recipe.getRecipe_title());
            Log.i("craching","crach");

        }


        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,recipes);


        listView.setAdapter(arrayAdapter);


    }
}

