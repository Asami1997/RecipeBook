package com.example.asami234.recipebook.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.example.asami234.recipebook.DB_Handler;

public class RecipeContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.asami234.recipebok.contentprovider";
    private static final String RECIPES_TABLE = "recipes";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + RECIPES_TABLE);

    public static final int RECIPES = 1;
    public static final int RECIPES_ID = 2;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private DB_Handler db_handler;
    static
    {
        sURIMatcher.addURI(AUTHORITY,RECIPES_TABLE, RECIPES);
        sURIMatcher.addURI(AUTHORITY, RECIPES_TABLE +"/#", RECIPES_ID);
    }

    public RecipeContentProvider() {

    }

    @Override public int delete(Uri uri, String selection, String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = db_handler.getWritableDatabase();

        int rowsDeleted = 0;

        switch (uriType) {

            case RECIPES:

                rowsDeleted = sqlDB.delete(DB_Handler.TABLE_RECIPES, selection, selectionArgs);
                break;

            case RECIPES_ID:

                String id = uri.getLastPathSegment();

                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(DB_Handler.TABLE_RECIPES, DB_Handler.COLUMN_RECIPE_ID
                                    + "=" + id, null);
                }else {
                    rowsDeleted = sqlDB.delete(DB_Handler.TABLE_RECIPES, DB_Handler.COLUMN_RECIPE_ID + "="
                                    + id + " and " + selection,
                            selectionArgs);
                }

                break;

            default:

                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
//TODO: Implement this to handle requests for the MIME type of the data at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public
    Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = db_handler.getWritableDatabase();
        long id = 0;

        switch (uriType) {
            case RECIPES:
                id = sqlDB.insert(DB_Handler.TABLE_RECIPES, null,values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: "+ uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.parse(RECIPES_TABLE + "/" + id);
    }

    @Override
    public boolean onCreate() {
//TODO: Implement this to initialize your content provider on startup.

        db_handler = new DB_Handler(getContext(),null,null,1);
        return false;
    }



    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(DB_Handler.TABLE_RECIPES);

        int uriType = sURIMatcher.match(uri);

        switch (uriType) {

            case RECIPES_ID:
                queryBuilder.appendWhere(DB_Handler.COLUMN_RECIPE_ID + "=" + uri.getLastPathSegment());
                break;

            case RECIPES:
                break;

            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        Cursor cursor = queryBuilder.query(db_handler.getReadableDatabase(), projection, selection, selectionArgs, null, null,
                sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),
                uri);
        return cursor;

    }


    @Override public int update(Uri uri, ContentValues values, String selection,
           String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = db_handler.getWritableDatabase();

        int rowsUpdated = 0;

        switch (uriType) {

            case RECIPES :
                rowsUpdated = sqlDB.update(DB_Handler.TABLE_RECIPES, values, selection, selectionArgs);
                break;

            case RECIPES_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(DB_Handler.TABLE_RECIPES, values, DB_Handler.COLUMN_RECIPE_ID + "="
                                            + id, null);
                }else {
                    rowsUpdated = sqlDB.update(DB_Handler.TABLE_RECIPES, values, DB_Handler.COLUMN_RECIPE_ID + "="
                                            + id + " and " + selection, selectionArgs);
                }

                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;

    }
}