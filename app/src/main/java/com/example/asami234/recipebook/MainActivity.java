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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import com.example.asami234.recipebook.contentprovider.RecipeContentProvider;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // declaring variables

    ListView listView;

    ArrayList<String> recipeTitles;

    ArrayList<String> recipeContents;

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("recipeapp","in onCreate ");

        // initializing variables
        listView = findViewById(R.id.recipesListView);

        recipeTitles = new ArrayList<String>();

        recipeContents = new ArrayList<String>();

        // get recipes from database
        getRecipes();

        // listen to user's item clicks
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Log.i("recipeapp","listview item clicked");

                // go to display recipe class
                Intent intent = new Intent(getApplicationContext(),Display_Activity.class);

                // add title of recipe as extra
                intent.putExtra("recipetitle",recipeTitles.get(i));
                // add recipe content as extra
                intent.putExtra("recipecontent",recipeContents.get(i));

                startActivityForResult(intent,2);
            }
        });

    }

    // called when the user clicks on the add button
    public void goToAddActivity(View view){

        Intent intent = new Intent(getApplicationContext(),AddRecipe.class);
        startActivityForResult(intent,1);
    }

    //get recipes from database
    public void getRecipes(){

        // clear array lists
        recipeContents.clear();
        recipeTitles.clear();

        // specify which columns to get from the database
        String[] mProjection = new String[] {
                Recipe_Contract.COLUMN_RECIPE_ID,
                Recipe_Contract.COLUMN_RECIPE_TITLE,
                Recipe_Contract.COLUMN_RECIPE_CONTENT
        };


        DB_Handler db_handler = new DB_Handler(this,null,null,1);
        ContentResolver contentResolver = db_handler.getContentResolver();

        // get the whole database
        Cursor cursor = contentResolver.query(Recipe_Contract.CONTENT_URI, mProjection,
                null, null, null);


        // loop through the cursor contents
        while (cursor!=null &&cursor.moveToNext()) {
            Recipe recipe = new Recipe(
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(Recipe_Contract.COLUMN_RECIPE_ID))),
                    cursor.getString(cursor.getColumnIndex(Recipe_Contract.COLUMN_RECIPE_TITLE)),
                    cursor.getString(cursor.getColumnIndex(Recipe_Contract.COLUMN_RECIPE_CONTENT)));
            recipeTitles.add(recipe.getRecipe_title());
            recipeContents.add(recipe.getRecipe_content());

        }


        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,recipeTitles);

        arrayAdapter.notifyDataSetChanged();
        //set array adapter to the listview
        listView.setAdapter(arrayAdapter);

    }


    // handle the happens when the user is directed back to main activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==  1){
            //reset array adapter
            getRecipes();
        }else if(requestCode == 2){
            // reset array adapter
            getRecipes();
        }
    }

    // handle the search menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // inflating the menu
        getMenuInflater().inflate(R.menu.search_menu,menu);

        MenuItem menuItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        // listen to user queries for searching the list view
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                // filter list that is populated based on the user query
                ArrayList<String> filterList = new ArrayList<>();

                for(String tempRecipe : recipeTitles){

                    if(tempRecipe.toLowerCase().startsWith(s.toLowerCase())){

                        filterList.add(tempRecipe);
                    }
                }

                // create array adapter specifically for the filter list
               ArrayAdapter<String> filterArrayAdapter = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_list_item_1,filterList);

                // update the list view
                listView.setAdapter(filterArrayAdapter);

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i("recipeapp","in onDestroy ");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("recipeapp","in onResume  ");

    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i("recipeapp","in onStart ");

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i("recipeapp","in onRestart ");

    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i("recipeapp","in onStop ");

    }
}

