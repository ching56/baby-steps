package com.example.ching.babysteps;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final Context context = this;
    // Construct the data source
    ArrayList<String> arrays;
    // Create the adapter to convert the array to views
    TimeLineAdapter adapter;
    SQLiteImplement database;
    EditText[] textArray = new EditText[8];
    String[] catalogArray = new String[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrays = new ArrayList<String>();
        adapter = new TimeLineAdapter(this, arrays);

        // Attach the adapter to a ListView
        database = new SQLiteImplement(this);
        ListView listView = (ListView) findViewById(R.id.lsview);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, final int position, long id) {
                //Do your tasks here


                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Are you sure to delete record");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do your work here
                        arrays.remove(position);//where arg2 is position of item you click
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();

                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();

                return true;
            }
        });


        textArray = new EditText[8];
        textArray[0] = (EditText) findViewById(R.id.infEditText1);
        textArray[1] = (EditText) findViewById(R.id.infEditText2);
        textArray[2] = (EditText) findViewById(R.id.infEditText3);
        textArray[3] = (EditText) findViewById(R.id.infEditText4);
        textArray[4] = (EditText) findViewById(R.id.infEditText5);
        textArray[5] = (EditText) findViewById(R.id.noticeEditText1);
        textArray[6] = (EditText) findViewById(R.id.noticeEditText2);
        textArray[7] = (EditText) findViewById(R.id.noticeEditText3);

        catalogArray = new String[8];
        catalogArray[0] = "Gender";
        catalogArray[1] = "Birthday";
        catalogArray[2] = "Diaper";
        catalogArray[3] = "Milk powder";
        catalogArray[4] = "Non-staple food";
        catalogArray[5] = "Clinic habits";
        catalogArray[6] = "Emergency contact";
        catalogArray[7] = "Special Diseases";


        View.OnFocusChangeListener F = new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    int index = -1;
                    for (int i = 0; i < 8; i++) {
                        if (textArray[i].getId() == v.getId()) {
                            index = i;
                            break;
                        }
                    }
                    Data D = new Data(catalogArray[index], textArray[index].getText().toString(), "", null);
                    D = database.insertData(D);
                }
            }
        };
        for (int i = 0; i < 8; i++) {
            textArray[i].setOnFocusChangeListener(F);
        }
        for(int i = 0;i<8;i++){
            String s = queryData("EVENT",catalogArray[i]);
            if(!s.equals("")){
                textArray[i].setText(s);
            }
        }

    }

    //function to search database
    public String queryData(String column, String string) {
        Data temp;
        int b;
        for (long i = 1; i <= database.getCount(); i++) {
            b = 0;
            switch (column) {
                case "EVENT":
                    temp = database.getData(i);
                    if (temp.getEvent() != null) {
                        if (temp.getEvent().equals(string)) {
                            b = 1;
                        }
                    }
                    break;
                case "DATE":
                    temp = database.getData(i);
                    if (temp.getDate() != null) {
                        if (temp.getDate().equals(string)) {
                            b = 1;
                        }
                    }
                    break;
                default:
                    return "";
            }
            if (b == 1) {
                return  temp.getDate();
            }
        }
        return "";
    }

   //add a event on timeline when button clicked
    public void addEvent(View view) {

        // get prompts.xml view
        Context context = this;
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);
        final EditText userInput2 = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput2);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                //result.setText(userInput.getText());
                                adapter.add(userInput.getText() + " " + userInput2.getText());

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    //lost focus when click anywhere outside the edittext
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
