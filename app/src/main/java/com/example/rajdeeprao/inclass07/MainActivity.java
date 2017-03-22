package com.example.rajdeeprao.inclass07;
/*
    InClass07
    MainActivity.java
    Yateen Kedare | Rajdeep Rao
 */
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CustomAdapter.IData{
    static DatabaseDataManager dm;
    Button add;
    ListView listView;
    ArrayList<Note> noteArrayList;



    String SortBy, Show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SortBy = "PRIORITY";
        Show = "All";
        dm=new DatabaseDataManager(this);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final EditText editText= (EditText) findViewById(R.id.editText);
        listView= (ListView) findViewById(R.id.listview);



        add= (Button) findViewById(R.id.button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note=editText.getText().toString();
                String priority=spinner.getSelectedItem().toString();
                String time=new Date().toString();
                List<Note> notes=dm.getAll();
                Log.d("Notes: ",notes.toString());
                Note n =new Note(note,"PENDING",priority,time);
                dm.saveNote(n);

                noteArrayList.add(n);
                updateListView();

            }
        });

        noteArrayList = (ArrayList<Note>) dm.getAll();
        updateListView();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {



                final Note n = new Note(noteArrayList.get(position).getNote(),noteArrayList.get(position).getStatus(),noteArrayList.get(position).getPriority(),noteArrayList.get(position).getUpdate_time());
                n.set_id(noteArrayList.get(position).get_id());

                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);


                builder.setMessage("Do you want to Delete it?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dm.deleteNote(n);
                                callbackAdapter();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("NO: ","Clicked No");

                            }
                        });
                final AlertDialog alert=builder.create();
                alert.show();

                callbackAdapter();


                return true;
            }
        });
    }

    private void updateListView(){
        if(SortBy.equals("PRIORITY")) {
            Collections.sort(noteArrayList, new CustomComparator());
        }
        else{
            Collections.sort(noteArrayList, new TimeComparator());
        }
        CustomAdapter notesAdapter=new CustomAdapter(MainActivity.this,noteArrayList, Show, this);
        listView.setAdapter(notesAdapter);
        listView.invalidate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.MenuAll:
                SortBy = "PRIORITY";
                Show = "All";
                updateListView();
                return true;
            case R.id.MenuCompleted:
                SortBy = "PRIORITY";
                Show = "COMPLETED";
                updateListView();
                return true;
            case R.id.MenuPending:
                SortBy = "PRIORITY";
                Show ="PENDING";
                updateListView();
                return true;
            case R.id.MenuPriority:
                SortBy = "PRIORITY";
                Show = "All";
                updateListView();
                return true;
            case R.id.MenuTime:
                SortBy = "TIME";
                Show = "All";
                updateListView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void callbackAdapter() {
        noteArrayList = (ArrayList<Note>) dm.getAll();
        updateListView();
    }
}
