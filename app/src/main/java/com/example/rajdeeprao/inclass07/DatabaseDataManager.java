package com.example.rajdeeprao.inclass07;
/*
    InClass07
    DatabaseDataManager.java
    Yateen Kedare | Rajdeep Rao
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

/**
 * Created by rajdeeprao on 2/26/17.
 */

public class DatabaseDataManager {
    private Context context;
    private DatabaseOpenHelper openHelper;
    private SQLiteDatabase db;
    private NotesDAO notesDAO;

    public DatabaseDataManager(Context context){
        this.context=context;
        openHelper=new DatabaseOpenHelper(this.context);
        db=openHelper.getWritableDatabase();
        notesDAO=new NotesDAO(db);
        Log.d("DATAMANAGER: ","Created");

    }
    public void close(){
        if(db!=null){
            db.close();
        }
    }
    public NotesDAO getNotesDAO(){
        return this.notesDAO;
    }

    public long saveNote(Note note) {
        Log.d("SAVENOTE: ","Created");
        return this.notesDAO.saveNote(note);
    }

    public boolean updatedNote(Note note) {
        return notesDAO.updatedNote(note);
    }

    public boolean deleteNote(Note note) {
        return this.notesDAO.deleteNote(note);
    }

    public List<Note> getAll() {
        return this.notesDAO.getAll();
    }

    public Note get(long id) {
        return this.notesDAO.get(id);
    }


    }
