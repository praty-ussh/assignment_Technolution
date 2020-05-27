package com.example.tab1and2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ToDoTask extends AppCompatActivity {

    FloatingActionButton addTask;
    FloatingActionButton deleteTask;
    FloatingActionButton editTask;
    CheckBox checkBox;
    ListView listView;
    DataBaseHelper myDb;

    private final static String TAG = "com.example.tab1and2";

    ArrayList<String> tasks = new ArrayList<>();
    String task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_task);
        myDb = new DataBaseHelper(this);
        listView = findViewById(R.id.listView);

        //Get all the data from the table
        Cursor data = myDb.getAllData();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ToDoTask.this,R.layout.row,R.id.textView2,tasks);
        if(data.getCount() == 0){
            Toast.makeText(this,"No task today.",Toast.LENGTH_SHORT).show();
        }
        else{
            while(data.moveToNext()){
                //Add the tasks to the list
                tasks.add(data.getString(1));

                listView.setAdapter(arrayAdapter);
            }
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;

                String itemValue = (String)listView.getItemAtPosition(position);

                editTask();
            }
        });

        // TO DELETE TASK IMPLEMENT THE FOLLOWING COMMENTED CODE STILL A LOT OF RECTIFICATION IS REQUIRED

//        deleteTask.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SparseBooleanArray sparseBooleanArray = listView.getCheckedItemPositions();
//                int itemCount = listView.getCount();
//                for (int i = itemCount - 1; i >= 0; i++) {
//                    if (sparseBooleanArray.get(i)) {
//                        String task1 = listView.getItemAtPosition(i).toString();
//                        Cursor data1 = myDb.getItemId(task1);
//                        if (data1.getCount() == 0)
//                            Toast.makeText(ToDoTask.this, "No task. Create one to delete.", Toast.LENGTH_SHORT).show();
//                        else
//                            myDb.deleteData(data1.toString());
//                        arrayAdapter.remove(tasks.get(i));
//                    }
//                }
//                sparseBooleanArray.clear();
//                arrayAdapter.notifyDataSetChanged();
//                listView.setAdapter(arrayAdapter);
//            }
//        });


        addTask = findViewById(R.id.floatingActionButton);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddTask();
            }
        });

        deleteTask = findViewById(R.id.floatingActionButton2);
        editTask = findViewById(R.id.floatingActionButton3);
        checkBox = findViewById(R.id.checkBox);



//        editTask.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editTask();
//            }
//        });

    }

    //TO EDIT THE TASK IMPLEMENT THE FOLLOWING CODE, A BIT OF RECTIFICATION IS REQUIRED
    public void editTask(){
        showDialog();
    }
//
    private void showDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Task");

        // Set up the input for editor
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        builder.setView(input);

        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                task = input.getText().toString();
                EditData(task);
                dialog.dismiss();
            }
        });

        builder.create().show();

    }
//
    public void EditData(String task){
        Cursor id = myDb.getItemId(task);
        boolean isUpdated = myDb.updateData(id.toString(),task);
        if(isUpdated)
            Toast.makeText(ToDoTask.this,"Task updated successfully",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(ToDoTask.this,"Task could not update.",Toast.LENGTH_SHORT).show();
    }

    //To enter task in dialog box
    public void onAddTask(){
        showAlertDialog();
    }

    private void showAlertDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Task");

        // Set up the input
        final EditText input = new EditText(this);

        // Specify the type of input expected; this, for example, sets the input as a text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        builder.setView(input);

        builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                task = input.getText().toString();
//                tasks.add(task);
                AddData(task);
                dialog.dismiss();
            }
        });

        builder.create().show();

    }
    public void AddData(String str){
        boolean isInserted = myDb.insertData(task);
        if(isInserted){
            Toast.makeText(this,"Task inserted successfully.",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,"Task not inserted successfully.",Toast.LENGTH_SHORT).show();
    }
}
