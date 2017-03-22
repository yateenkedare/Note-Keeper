package com.example.rajdeeprao.inclass07;
/*
    InClass07
    DatabaseOpenHelper.java
    Yateen Kedare | Rajdeep Rao
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rajdeeprao on 2/26/17.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    static final String DB_NAME="mynotes.db";
    static final int DB_VERSION=6;

    @Override
    public void onCreate(SQLiteDatabase db) {
        NotesTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        NotesTable.onUpgrade(db,oldVersion,newVersion);
    }

    public DatabaseOpenHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }
}
