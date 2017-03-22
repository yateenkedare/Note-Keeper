package com.example.rajdeeprao.inclass07;
/*
    InClass07
    NotesTable.java
    Yateen Kedare | Rajdeep Rao
 */
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by rajdeeprao on 2/26/17.
 */

public class NotesTable {
    static final String TABLENAME="notes";
    static final String COLUMN_ID="_id";
    static final String COLUMN_PRIORITY="priority";
    static final String COLUMN_STATUS="status";
    static final String COLUMN_NOTE="note";
    static final String COLUMN_TIME="time";

    public static void onCreate(SQLiteDatabase db){
        StringBuilder sb=new StringBuilder();
        sb.append("CREATE TABLE "+TABLENAME+" (");
        sb.append(COLUMN_ID+" integer primary key autoincrement, ");
        sb.append(COLUMN_NOTE+" text not null, ");
        sb.append(COLUMN_PRIORITY+" text not null, ");
        sb.append(COLUMN_TIME+" text not null, ");
        sb.append(COLUMN_STATUS+" text not null);");

        try {
            db.execSQL(sb.toString());
        }catch (SQLException ex){
            ex.printStackTrace();
        }

    }

    public static void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLENAME);
        NotesTable.onCreate(db);

    }
}
