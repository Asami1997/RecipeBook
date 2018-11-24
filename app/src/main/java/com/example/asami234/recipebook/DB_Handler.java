package com.example.asami234.recipebook;

import android.content.Context;
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
}
