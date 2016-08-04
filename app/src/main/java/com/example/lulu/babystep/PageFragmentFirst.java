package com.example.lulu.babystep;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by lulu on 2016/8/1.
 */



public class PageFragmentFirst extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static PageFragmentFirst newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragmentFirst fragment = new PageFragmentFirst();
        fragment.setArguments(args);
        return fragment;
    }

    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH) + 1; //取出月，月份的編號是由0~11 故+1
    int date = c.get(Calendar.DAY_OF_MONTH); //取出日

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        //  TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        // tvTitle.setText("Fragment #" + mPage);

        TextView monthString = (TextView) view.findViewById(R.id.firstmonth);
        TextView dateString = (TextView) view.findViewById(R.id.firstdate);
        switch (month){
            case 1:monthString.setText("JAN");break;
            case 2:monthString.setText("FEB");break;
            case 3:monthString.setText("MAR");break;
            case 4:monthString.setText("APR");break;
            case 5:monthString.setText("MAY");break;
            case 6:monthString.setText("JUN");break;
            case 7:monthString.setText("JUL");break;
            case 8:monthString.setText("AUG");break;
            case 9:monthString.setText("SEP");break;
            case 10:monthString.setText("OCT");break;
            case 11:monthString.setText("NOV");break;
            case 12:monthString.setText("DEC");break;
        }
        dateString.setText(String.valueOf(date));
        monthString = (TextView) view.findViewById(R.id.secondmonth);
        dateString = (TextView) view.findViewById(R.id.seconddate);
        c.add(Calendar.DATE, -1);
        month = c.get(Calendar.MONTH) + 1;
        date = c.get(Calendar.DAY_OF_MONTH);
        switch (month){
            case 1:monthString.setText("JAN");break;
            case 2:monthString.setText("FEB");break;
            case 3:monthString.setText("MAR");break;
            case 4:monthString.setText("APR");break;
            case 5:monthString.setText("MAY");break;
            case 6:monthString.setText("JUN");break;
            case 7:monthString.setText("JUL");break;
            case 8:monthString.setText("AUG");break;
            case 9:monthString.setText("SEP");break;
            case 10:monthString.setText("OCT");break;
            case 11:monthString.setText("NOV");break;
            case 12:monthString.setText("DEC");break;
        }
        dateString.setText(String.valueOf(date));
        monthString = (TextView) view.findViewById(R.id.thirdmonth);
        dateString = (TextView) view.findViewById(R.id.thirddate);
        c.add(Calendar.DATE, -1);
        month = c.get(Calendar.MONTH) + 1;
        date = c.get(Calendar.DAY_OF_MONTH);
        switch (month){
            case 1:monthString.setText("JAN");break;
            case 2:monthString.setText("FEB");break;
            case 3:monthString.setText("MAR");break;
            case 4:monthString.setText("APR");break;
            case 5:monthString.setText("MAY");break;
            case 6:monthString.setText("JUN");break;
            case 7:monthString.setText("JUL");break;
            case 8:monthString.setText("AUG");break;
            case 9:monthString.setText("SEP");break;
            case 10:monthString.setText("OCT");break;
            case 11:monthString.setText("NOV");break;
            case 12:monthString.setText("DEC");break;
        }
        dateString.setText(String.valueOf(date));
        monthString = (TextView) view.findViewById(R.id.fourmonth);
        dateString = (TextView) view.findViewById(R.id.fourdate);
        c.add(Calendar.DATE, -1);
        month = c.get(Calendar.MONTH) + 1;
        date = c.get(Calendar.DAY_OF_MONTH);
        switch (month){
            case 1:monthString.setText("JAN");break;
            case 2:monthString.setText("FEB");break;
            case 3:monthString.setText("MAR");break;
            case 4:monthString.setText("APR");break;
            case 5:monthString.setText("MAY");break;
            case 6:monthString.setText("JUN");break;
            case 7:monthString.setText("JUL");break;
            case 8:monthString.setText("AUG");break;
            case 9:monthString.setText("SEP");break;
            case 10:monthString.setText("OCT");break;
            case 11:monthString.setText("NOV");break;
            case 12:monthString.setText("DEC");break;
        }
        dateString.setText(String.valueOf(date));
        monthString = (TextView) view.findViewById(R.id.fivemonth);
        dateString = (TextView) view.findViewById(R.id.fivedate);
        c.add(Calendar.DATE, -1);
        month = c.get(Calendar.MONTH) + 1;
        date = c.get(Calendar.DAY_OF_MONTH);
        switch (month){
            case 1:monthString.setText("JAN");break;
            case 2:monthString.setText("FEB");break;
            case 3:monthString.setText("MAR");break;
            case 4:monthString.setText("APR");break;
            case 5:monthString.setText("MAY");break;
            case 6:monthString.setText("JUN");break;
            case 7:monthString.setText("JUL");break;
            case 8:monthString.setText("AUG");break;
            case 9:monthString.setText("SEP");break;
            case 10:monthString.setText("OCT");break;
            case 11:monthString.setText("NOV");break;
            case 12:monthString.setText("DEC");break;
        }
        dateString.setText(String.valueOf(date));
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        date = c.get(Calendar.DAY_OF_MONTH);



        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }



}