package com.example.asami234.recipebook;

import android.net.Uri;

public class Recipe_Contract {

    private static final String AUTHORITY = "com.example.asami234.recipebok.contentprovider";
    private static final String RECIPES_TABLE = "recipes";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + RECIPES_TABLE);
    public static final String COLUMN_RECIPE_ID ="recipe_id";
    public static final String COLUMN_RECIPE_TITLE = "recipe_title";
    public static final String COLUMN_RECIPE_CONTENT = "recipe_content";
}
