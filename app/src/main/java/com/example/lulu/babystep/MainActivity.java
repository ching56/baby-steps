package com.example.lulu.babystep;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;

import com.astuetz.PagerSlidingTabStrip;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    SampleFragmentPagerAdapter s = new SampleFragmentPagerAdapter(getSupportFragmentManager());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(s);

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
    }


    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;
        private String tabTitles[] = new String[] { "List", "Card" };
        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        PageFragmentFirst p1 = PageFragmentFirst.newInstance(0);
        PageFragmentSecond p2 = PageFragmentSecond.newInstance(1);
        PageFragmentThird p3 = PageFragmentThird.newInstance(2);

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return p1;
                case 1:
                    return p3;
                default:
                    return null;
            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
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

    public void addEvent(View view) {
        PageFragmentThird p = (PageFragmentThird) s.getItem(2);
        p.addEvent(view);
    }

    public void onClick1(View v) {
        Calendar temp = Calendar.getInstance();
        int PassYear = temp.get(Calendar.YEAR);
        int PassMonth = temp.get(Calendar.MONTH) + 1;
        int PassDate = temp.get(Calendar.DAY_OF_MONTH);

        Intent intent = new Intent();
        intent.setClass(MainActivity.this, showDay.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Year", PassYear);
        bundle.putInt("Month", PassMonth);
        bundle.putInt("Date", PassDate);

        intent.putExtras(bundle);
        startActivity(intent);

        return;
    }
    public void onClick2(View v) {
        Calendar temp = Calendar.getInstance();
        temp.add(Calendar.DATE, -1);
        int PassYear = temp.get(Calendar.YEAR);
        int PassMonth = temp.get(Calendar.MONTH);
        int PassDate = temp.get(Calendar.DAY_OF_MONTH);

        Intent intent = new Intent();
        intent.setClass(MainActivity.this, showDay.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Year", PassYear);
        bundle.putInt("Month", PassMonth+1);
        bundle.putInt("Date", PassDate);

        intent.putExtras(bundle);
        startActivity(intent);

        return;
    }
    public void onClick3(View v) {
        Calendar temp = Calendar.getInstance();
        temp.add(Calendar.DATE, -2);
        int PassYear = temp.get(Calendar.YEAR);
        int PassMonth = temp.get(Calendar.MONTH);
        int PassDate = temp.get(Calendar.DAY_OF_MONTH);

        Intent intent = new Intent();
        intent.setClass(MainActivity.this, showDay.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Year", PassYear);
        bundle.putInt("Month", PassMonth+1);
        bundle.putInt("Date", PassDate);

        intent.putExtras(bundle);
        startActivity(intent);

        return;
    }
    public void onClick4(View v) {
        Calendar temp = Calendar.getInstance();
        temp.add(Calendar.DATE, -3);
        int PassYear = temp.get(Calendar.YEAR);
        int PassMonth = temp.get(Calendar.MONTH);
        int PassDate = temp.get(Calendar.DAY_OF_MONTH);

        Intent intent = new Intent();
        intent.setClass(MainActivity.this, showDay.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Year", PassYear);
        bundle.putInt("Month", PassMonth+1);
        bundle.putInt("Date", PassDate);

        intent.putExtras(bundle);
        startActivity(intent);

        return;
    }
    public void onClick5(View v) {
        Calendar temp = Calendar.getInstance();
        temp.add(Calendar.DATE, -4);
        int PassYear = temp.get(Calendar.YEAR);
        int PassMonth = temp.get(Calendar.MONTH);
        int PassDate = temp.get(Calendar.DAY_OF_MONTH);

        Intent intent = new Intent();
        intent.setClass(MainActivity.this, showDay.class);
        Bundle bundle = new Bundle();
        bundle.putInt("Year", PassYear);
        bundle.putInt("Month", PassMonth+1);
        bundle.putInt("Date", PassDate);

        intent.putExtras(bundle);
        startActivity(intent);

        return;
    }
    public void pickDate(View v){
//        DatePickerFragment d = new DatePickerFragment();
//        d.show(getFragmentManager(), "datePicker");

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DATE);

        // Launch Time Picker Dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int PassYear, int PassMonth, int PassDay) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, showDay.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("Year", PassYear);
                        bundle.putInt("Month", PassMonth+1);
                        bundle.putInt("Date", PassDay);

                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                }, mYear, mMonth,mDay);
        datePickerDialog.show();
    }
}
