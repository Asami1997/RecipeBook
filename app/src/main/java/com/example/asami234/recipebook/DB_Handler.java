package com.example.asami234.recipebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

// interacts with the database
public class DB_Handler extends SQLiteOpenHelper {

    // initializing variables related to DB
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "recipeDB.db";
    private static final String TABLE_RECIPES = "recipes";


    // initializing variables related to table "recipes"
    private static final String COLUMN_RECIPE_ID ="recipe_id";
    private static final String COLUMN_RECIPE_TITLE = "recipe_title";
    private static final String COLUMN_RECIPE_CONTENT = "recipe_content";


    public DB_Handler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
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

        // get database ( writable because we want to write to the database)
        SQLiteDatabase db = this.getWritableDatabase();

        // insert new recipe to the table "recipes" in the database
        db.insert(TABLE_RECIPES,null,contentValues);
    }


    // query a recipe in the database
    public Recipe findRecipe(String recipe_title){

        String query = "SELECT * FROM "+ TABLE_RECIPES + " WHERE " + COLUMN_RECIPE_TITLE + " = \" "
                + recipe_title + "\" ";SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query,null);

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
        db.close();
        return recipe;
    }

    // delete recipe from database
    public boolean deleteRecipe(String recipeTitle){
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_RECIPE_TITLE + " = \" " + recipeTitle + "\" ";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Recipe recipe = new Recipe();
        if (cursor.moveToFirst()) {
            recipe.setRecipe_id(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_RECIPES, COLUMN_RECIPE_ID + " = ?", new String[] { String.valueOf(recipe.getRecipe_id()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

}
