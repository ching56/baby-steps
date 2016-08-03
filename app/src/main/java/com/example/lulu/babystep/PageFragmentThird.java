package com.example.lulu.babystep;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by lulu on 2016/8/1.
 */


public class PageFragmentThird extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    Context context;
    // Construct the data source
    ArrayList<String> arrays;
    // Create the adapter to convert the array to views
    TimeLineAdapter adapter;
    SQLiteImplement database;
    EditText[] textArray = new EditText[8];
    String[] catalogArray = new String[8];
    EditText nameEditText;
    ListView listView;

    public static PageFragmentThird newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragmentThird fragment = new PageFragmentThird();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        //  TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        // tvTitle.setText("Fragment #" + mPage);

        arrays = new ArrayList<String>();
        adapter = new TimeLineAdapter(context, arrays);

        // Attach the adapter to a ListView
        database = new SQLiteImplement(context);

        listView = (ListView) view.findViewById(R.id.lsview);
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

                        System.out.println("position: "+position);
                        System.out.println("delete data: "+String.valueOf(Long.valueOf(queryData("DATE",arrays.get(position)))));
                        database.deleteData(Long.valueOf(queryData("DATE",arrays.get(position))));
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
        textArray[0] = (EditText) view.findViewById(R.id.infEditText1);
        textArray[1] = (EditText) view.findViewById(R.id.infEditText2);
        textArray[2] = (EditText) view.findViewById(R.id.infEditText3);
        textArray[3] = (EditText) view.findViewById(R.id.infEditText4);
        textArray[4] = (EditText) view.findViewById(R.id.infEditText5);
        textArray[5] = (EditText) view.findViewById(R.id.noticeEditText1);
        textArray[6] = (EditText) view.findViewById(R.id.noticeEditText2);
        textArray[7] = (EditText) view.findViewById(R.id.noticeEditText3);
        nameEditText = (EditText) view.findViewById(R.id.editText);
        nameEditText.setHint("Baby's name");
        textArray[0].setHint("F/M");
        textArray[1].setHint("yyyy/mm/dd");
        textArray[2].setHint("____________");
        textArray[3].setHint("____________");
        textArray[4].setHint("____________");
        textArray[5].setHint("____________");
        textArray[6].setHint("____________");
        textArray[7].setHint("____________");


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
                    if(queryData("EVENT",catalogArray[index])=="") {
                        Data D = new Data(catalogArray[index], textArray[index].getText().toString(), "", null);
                        D = database.insertData(D);
                    }else{
                        Data D = getData(catalogArray[index]);
                        D.setDate(textArray[index].getText().toString());
                        database.updateData(D);
                    }
                }
            }
        };

        View.OnFocusChangeListener EditTextF = new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    Data D = new Data("NAME", nameEditText.getText().toString(), "", null);
                    D = database.insertData(D);
                }
            }
        };

        nameEditText.setOnFocusChangeListener(EditTextF);

        //set Foucus listener
        for (int i = 0; i < 8; i++) {
            textArray[i].setOnFocusChangeListener(F);
        }

        //resotre the data when laugh the app
        for (int i = 0; i < 8; i++) {
            String s = queryData("EVENT", catalogArray[i]);
            if (!s.equals("")) {
                textArray[i].setText(s);
            }
        }
        String s = queryData("EVENT", "NAME");
        if (!s.equals("")) {
            nameEditText.setText(s);
        }
        if(getNumberOfEvent()!=0)
            for(int i = 1;i<=getNumberOfEvent();i++){
                adapter.add(queryEvent(String.valueOf(i)));
            }

        return view;
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
                            return temp.getDate();
                        }
                    }
                    break;
                case "DATE":
                    temp = database.getData(i);
                    if (temp.getDate() != null) {
                        if (temp.getDate().equals(string)) {
                            return String.valueOf(temp.getId());
                        }
                    }
                    break;
                default:
                    return "";
            }
        }
        if(column.equals("DATE"))
            return "0";
        return "";
    }

    public Data getData(String event) {
        Data temp;
        for (long i = 1; i <= database.getCount(); i++) {
                temp = database.getData(i);
                if (temp.getEvent() != null) {
                    if (temp.getEvent().equals(event)) {
                        return temp;
                    }
                }
        }
        return null;
    }

    //for timeline event
    public String queryEvent(String time) {
        Data temp;
        for (long i = 1; i <= database.getCount(); i++) {
            temp = database.getData(i);
            if(temp.getEvent()!=null)
                if (temp.getEvent().equals("TIMELINEEVENT")) {
                    if (temp.getTime().equals(time)) {
                        return temp.getDate();
                    }
                }
        }
        return "";
    }

    //get the number of event
    public int getNumberOfEvent() {
        Data temp;
        int b = 0;
        for (long i = 1; i <= database.getCount(); i++) {
            temp = database.getData(i);
            if(temp.getEvent() != null)
                if (temp.getEvent().equals("TIMELINEEVENT")) {
                    b++;
                }
        }
        return b;
    }


    //add a event on timeline when button clicked
    public void addEvent(View view) {
        if(context==null) {
            System.out.println("null context");
           return;
        }
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

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
                                int i = getNumberOfEvent() + 1;
                                Data D = new Data("TIMELINEEVENT", userInput.getText() + " " + userInput2.getText(), String.valueOf(i), null);
                                D = database.insertData(D);
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

}