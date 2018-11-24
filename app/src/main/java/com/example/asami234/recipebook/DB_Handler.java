package com.example.asami234.recipebook;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.asami234.recipebook.contentprovider.RecipeContentProvider;

// interacts with the database
public class DB_Handler extends SQLiteOpenHelper {

    // initializing variables related to DB
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "recipeDB.db";
    public static final String TABLE_RECIPES = "recipes";


    // initializing variables related to table "recipes"
    public static final String COLUMN_RECIPE_ID ="recipe_id";
    public static final String COLUMN_RECIPE_TITLE = "recipe_title";
    public static final String COLUMN_RECIPE_CONTENT = "recipe_content";

    public static ContentResolver contentResolver;

    public DB_Handler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

        contentResolver = context.getContentResolver();
    }

    // creates a new table in the database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // creates SQL statement to create table recipes
        String CREATE_RECIPES_TABLE = "CREATE TABLE " + TABLE_RECIPES + "(" + COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY," +
                COLUMN_RECIPE_TITLE + " TEXT," + COLUMN_RECIPE_CONTENT + " TEXT" + ")";

        // execute create table sql statement
        sqLiteDatabase.execSQL(CREATE_RECIPES_TABLE);
    }

    // called when the handler is invoked with a greater database version number from the one previously used
    // updates table schema in an existing database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // remove table if it already exists in the database
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(sqLiteDatabase);
    }


    // inserts new recipe to the database
    public void addRecipe(Recipe recipe){
        ContentValues contentValues = new ContentValues();
        // insert recipe ID
        contentValues.put(COLUMN_RECIPE_ID,recipe.getRecipe_id());
        // insert recipe title
        contentValues.put(COLUMN_RECIPE_TITLE,recipe.getRecipe_title());
        // insert recipe content
        contentValues.put(COLUMN_RECIPE_CONTENT,recipe.getRecipe_content());

        contentResolver.insert(RecipeContentProvider.CONTENT_URI,contentValues);

        Log.i("hisapp",recipe.getRecipe_title());

    }


    // query a recipe in the database
    public Recipe findRecipe(String recipe_title){

        String[] projection = {
                COLUMN_RECIPE_ID,
                COLUMN_RECIPE_TITLE,
                COLUMN_RECIPE_CONTENT};

        String selection = "recipe_title = \" " + recipe_title + "\"";

        Cursor cursor = contentResolver.query(RecipeContentProvider.CONTENT_URI,projection,selection,
                null,null);

        Recipe recipe = new Recipe();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            recipe.setRecipe_id(Integer.parseInt(cursor.getString(0)));
            recipe.setRecipe_title(cursor.getString(1));
            recipe.setRecipe_content(cursor.getString(2));
            cursor.close();
        }
        else
        {
            // if recipe dose not exist in the DB.
            recipe = null;
        }
        return recipe;
    }

    // delete recipe from database
    public boolean deleteRecipe(String recipeTitle){
        boolean result = false;
        String selection = "recipe_title = \"" + recipeTitle + "\" ";
        int rowsDeleted = contentResolver.delete(RecipeContentProvider.CONTENT_URI, selection,null);

        if(rowsDeleted > 0){
            result = true;
        }
        return result;
    }

}
