package com.example.asami234.recipebook;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.asami234.recipebook.contentprovider.RecipeContentProvider;

import java.sql.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    // stores recipe title
    ArrayList<String> recipeTitles;

    ArrayList<String> recipeContents;

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.recipesListView);

        recipeTitles = new ArrayList<String>();

        recipeContents = new ArrayList<String>();

        getRecipes();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String recipe = recipeTitles.get(i);

                Toast.makeText(MainActivity.this, recipe, Toast.LENGTH_SHORT).show();

                // go to display recipe class

                Intent intent = new Intent(getApplicationContext(),Display_Activity.class);

                intent.putExtra("recipetitle",recipeTitles.get(i));
                intent.putExtra("recipecontent",recipeContents.get(i));

                startActivityForResult(intent,2);
            }
        });

    }

    public void goToAddActivity(View view){

        Intent intent = new Intent(getApplicationContext(),AddRecipe.class);
        startActivityForResult(intent,1);
    }

    //get recipes
    public void getRecipes(){

        recipeContents.clear();
        recipeTitles.clear();

        String[] mProjection = new String[] {
                Recipe_Contract.COLUMN_RECIPE_ID,
                Recipe_Contract.COLUMN_RECIPE_TITLE,
                Recipe_Contract.COLUMN_RECIPE_CONTENT
        };


        DB_Handler db_handler = new DB_Handler(this,null,null,1);
        ContentResolver contentResolver = db_handler.getContentResolver();

        Cursor cursor = contentResolver.query(RecipeContentProvider.CONTENT_URI, mProjection,
                null, null, null);


        while (cursor!=null &&cursor.moveToNext()) {
            Log.i("crashing","crash");
            Recipe recipe = new Recipe(
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(Recipe_Contract.COLUMN_RECIPE_ID))),
                    cursor.getString(cursor.getColumnIndex(Recipe_Contract.COLUMN_RECIPE_TITLE)),
                    cursor.getString(cursor.getColumnIndex(Recipe_Contract.COLUMN_RECIPE_CONTENT)));
            Log.i("crashing","crash");
            recipeTitles.add(recipe.getRecipe_title());
            recipeContents.add(recipe.getRecipe_content());
            Log.i("crashing","crash");

        }


        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,recipeTitles);

        arrayAdapter.notifyDataSetChanged();
        listView.setAdapter(arrayAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==  1){
            //reset array adapter
            getRecipes();
        }else if(requestCode == 2){
            getRecipes();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu,menu);

        MenuItem menuItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                ArrayList<String> filterList = new ArrayList<>();

                for(String tempRecipe : recipeTitles){

                    if(tempRecipe.toLowerCase().startsWith(s.toLowerCase())){

                        filterList.add(tempRecipe);
                    }
                }

                // create array adapter specifically
               ArrayAdapter<String> filterArrayAdapter = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_list_item_1,filterList);

                listView.setAdapter(filterArrayAdapter);

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}

