package com.example.rajdeeprao.inclass07;
/*
    InClass07
    NoteDAO.java
    Yateen Kedare | Rajdeep Rao
 */
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajdeeprao on 2/26/17.
 */

public class NotesDAO {
    private SQLiteDatabase db;

    public NotesDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long saveNote(Note note){
        ContentValues cv=new ContentValues();
        cv.put(NotesTable.COLUMN_NOTE,note.getNote());
        cv.put(NotesTable.COLUMN_PRIORITY,note.getPriority());
        cv.put(NotesTable.COLUMN_TIME,note.getUpdate_time());
        cv.put(NotesTable.COLUMN_STATUS,note.getStatus());
        Log.d("SAVENOTEDAO: ",cv.toString());

        return db.insert(NotesTable.TABLENAME,null,cv);
    }

    public boolean updatedNote(Note note){
        ContentValues cv=new ContentValues();
        cv.put(NotesTable.COLUMN_NOTE,note.getNote());
        cv.put(NotesTable.COLUMN_PRIORITY,note.getPriority());
        cv.put(NotesTable.COLUMN_TIME,note.getUpdate_time());
        cv.put(NotesTable.COLUMN_STATUS,note.getStatus());
        return db.update(NotesTable.TABLENAME,cv,NotesTable.COLUMN_ID+"=?",new String[]{note.get_id()+""})>0;

    }

    public boolean deleteNote(Note note){
        return db.delete(NotesTable.TABLENAME,NotesTable.COLUMN_ID+"=?",new String[]{note.get_id()+""})>0;
    }


    public Note get(long id){
        Note note=null;
        Cursor cursor= db.query(true,NotesTable.TABLENAME,new String[]{NotesTable.COLUMN_ID,NotesTable.COLUMN_NOTE,NotesTable.COLUMN_PRIORITY,NotesTable.COLUMN_TIME,NotesTable.COLUMN_STATUS},NotesTable.COLUMN_ID+"=?",new String[]{id+""},null,null,null,null,null);
        if(cursor!=null && cursor.moveToFirst()){
            note=buildNoteFromCursor(cursor);
            cursor.close();
        }
        Log.d("GETDAO: ","Created");

        return null;
    }
    private Note buildNoteFromCursor(Cursor cursor){
        Note note=null;
        if (cursor!=null){
            note=new Note();
            note.set_id(cursor.getLong(0));
            note.setNote(cursor.getString(1));
            note.setPriority(cursor.getString(2));
            note.setUpdate_time(cursor.getString(3));
            note.setStatus(cursor.getString(4));
        }
        Log.d("NOTE PROCESSING: ",note.toString());
        return note;

    }

    public List<Note> getAll(){

        List<Note> notes=new ArrayList<Note>();
        Cursor cursor= db.query(NotesTable.TABLENAME,new String[]{NotesTable.COLUMN_ID,NotesTable.COLUMN_NOTE,NotesTable.COLUMN_PRIORITY,NotesTable.COLUMN_TIME,NotesTable.COLUMN_STATUS},null,null,null,null,null);
        if(cursor!=null && cursor.moveToFirst())
        {
            do {
                Note note=buildNoteFromCursor(cursor);
                if(note!=null)
                    notes.add(note);
                Log.d("GETALLDAO: ",note.toString());


            }while (cursor.moveToNext());

            if(!cursor.isClosed())
                cursor.close();
        }
        return notes;
    }
}
