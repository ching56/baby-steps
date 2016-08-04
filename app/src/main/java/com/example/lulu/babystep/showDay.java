package com.example.lulu.babystep;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ViewSwitcher;

import java.util.Calendar;

/**
 * Created by Kathy on 2016/8/4.
 */
public class showDay extends AppCompatActivity {
    public int month, date, year;
    public int moodMode = 0;
    private int outdoorMode = 0, sleepMode = 0, poopMode = 0, foodMode = 0, milkMode = 0;
    private String outdoor, sleep, poop, food, milk, mood, other;
    private String outdoorTime, sleepTime, foodTime, milkTime, poopTime;
    private TextView txtWakeTime, txtBedTime, setMonth, setDate, txtMilkInfo, txtFoodInfo, txtPoopInfo, txtSleepInfo, txtOutdoorInfo, txtBathInfo, txtOtherInfo;
    private ImageView imgDay, imgNight, imgMilk, imgFood, imgPoop, imgSleep, imgOutdoor, imgBath, imgOther;
    private ImageSwitcher moodSwitcher;
    private int mHour, mMinute;
    private SQLiteImplement database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_day);
        txtWakeTime = (TextView) this.findViewById(R.id.wakeUpTime);
        txtBedTime = (TextView) this.findViewById(R.id.bedTime);
        setMonth = (TextView) this.findViewById(R.id.monthView);
        setDate = (TextView) this.findViewById(R.id.dateView);
        txtMilkInfo = (TextView) this.findViewById(R.id.milkInfo);
        txtFoodInfo = (TextView) this.findViewById(R.id.foodInfo);
        txtPoopInfo = (TextView) this.findViewById(R.id.poopInfo);
        txtSleepInfo = (TextView) this.findViewById(R.id.sleepInfo);
        txtOutdoorInfo = (TextView) this.findViewById(R.id.outdoorInfo);
        txtBathInfo = (TextView) this.findViewById(R.id.bathInfo);
        txtOtherInfo = (TextView) this.findViewById(R.id.othersInfo) ;

        imgDay = (ImageView) this.findViewById(R.id.day);
        imgNight = (ImageView) this.findViewById(R.id.night);
        imgMilk = (ImageView) this.findViewById(R.id.milk);
        imgFood = (ImageView) this.findViewById(R.id.food);
        imgPoop = (ImageView) this.findViewById(R.id.poop);
        imgSleep = (ImageView) this.findViewById(R.id.sleep);
        imgOutdoor = (ImageView) this.findViewById(R.id.outdoor);
        imgBath = (ImageView) this.findViewById(R.id.bath);
        imgOther = (ImageView) this.findViewById(R.id.others);

        moodSwitcher = (ImageSwitcher) this.findViewById(R.id.mood);

        imgDay.setOnClickListener(pickDayTime);
        imgNight.setOnClickListener(pickNightTime);
        imgMilk.setOnClickListener(pickMilkTime);
        imgFood.setOnClickListener(pickFoodTime);
        imgPoop.setOnClickListener(pickPoopTime);
        imgSleep.setOnClickListener(pickSleepTime);
        imgOutdoor.setOnClickListener(pickOutdoorTime);
        imgBath.setOnClickListener(pickBathTime);
        imgOther.setOnClickListener(writeOthers);

        database = new SQLiteImplement(this);

        moodSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                myView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return myView;
            }
        });
        moodSwitcher.setImageResource(R.drawable.mood_default);

        //getCurrentDate();
        Bundle bundle = getIntent().getExtras();
        year = bundle.getInt("Year");
        month = bundle.getInt("Month");
        date = bundle.getInt("Date");

        Log.d("DATE INFO", year+"/"+month+"/"+date);
        readData(year+"/"+month+"/"+date);
        setMonth.setText(transferMonth());
        setDate.setText(String.valueOf(date));

        Button button = (Button) findViewById(R.id.shareButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_SUBJECT,"Baby data from babySteps");
                intent.putExtra(Intent.EXTRA_TEXT, "DATA");
                if (intent.resolveActivity(showDay.this.getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    public void saveMood(String Date, String mood) {
        Data temp;
        for (long i = 1; i <= database.getCount(); i++) {
            temp = database.getData(i);
            if (temp.getDate() != null) {
                if (temp.getDate().equals(Date)) {
                    if (temp.getEvent().equals("mood")) {
                        temp.setMark(mood);
                        database.updateData(temp);
                        return;
                    }
                }
            }
        }
        Data test1 = new Data("mood", Date, null, mood);
        database.insertData(test1);
    }

    public void saveWakeTime(String Date, String time) {
        Data temp;
        for (long i = 1; i <= database.getCount(); i++) {
            temp = database.getData(i);
            if (temp.getDate() != null) {
                if (temp.getDate().equals(Date)) {
                    if (temp.getEvent().equals("wake up time")) {
                        temp.setMark(time);
                        database.updateData(temp);
                        return;
                    }
                }
            }
        }
        Data test1 = new Data("wake up time", Date, null, time);
        database.insertData(test1);
    }

    public void saveBedTime(String Date, String time) {
        Data temp;
        for (long i = 1; i <= database.getCount(); i++) {
            temp = database.getData(i);
            if (temp.getDate() != null) {
                if (temp.getDate().equals(Date)) {
                    if (temp.getEvent().equals("bed time")) {
                        temp.setMark(time);
                        database.updateData(temp);
                        return;
                    }
                }
            }
        }
        Data test1 = new Data("bed time", Date, null, time);
        database.insertData(test1);
    }

    public void saveBathTime(String Date, String time) {
        Data temp;
        for (long i = 1; i <= database.getCount(); i++) {
            temp = database.getData(i);
            if (temp.getDate() != null) {
                if (temp.getDate().equals(Date)) {
                    if (temp.getEvent().equals("bath time")) {
                        temp.setMark(time);
                        database.updateData(temp);
                        return;
                    }
                }
            }
        }
        Data test1 = new Data("bath time", Date, null, time);
        database.insertData(test1);
    }

    public void saveSleepTime(String EventItem, String Date, String time) {
        Data temp;
        for (long i = 1; i <= database.getCount(); i++) {
            temp = database.getData(i);
            if (temp.getDate() != null) {
                if (temp.getDate().equals(Date)) {
                    if (temp.getEvent().equals(EventItem)) {
                        temp.setEvent("nap time");
                        temp.setMark(time);
                        database.updateData(temp);
                        return;
                    }
                }
            }
        }
        Data test1 = new Data(EventItem, Date, null, time);
        database.insertData(test1);
    }

    public void saveOutdoorTime(String EventItem, String Date, String time) {
        Data temp;
        for (long i = 1; i <= database.getCount(); i++) {
            temp = database.getData(i);
            if (temp.getDate() != null) {
                if (temp.getDate().equals(Date)) {
                    if (temp.getEvent().equals(EventItem)) {
                        database.deleteData(i);
                        break;
                    }
                }
            }
        }
        Data test1 = new Data("outdoor", Date, null, time);
        database.insertData(test1);
    }

    public void nextMood (View view)
    {
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        moodSwitcher.setInAnimation(in);
        moodSwitcher.setOutAnimation(out);
        switch (moodMode)
        {
            case 0:
                moodSwitcher.setImageResource(R.drawable.mood_happy);
                mood = "happy"; moodMode ++; break;
            case 1:
                moodSwitcher.setImageResource(R.drawable.mood_sad);
                mood = "sad"; moodMode ++; break;
            case 2:
                moodSwitcher.setImageResource(R.drawable.mood_worried);
                mood = "worried"; moodMode ++; break;
            case 3:
                moodSwitcher.setImageResource(R.drawable.mood_sick);
                mood = "sick"; moodMode++; break;
            case 4:
                moodSwitcher.setImageResource(R.drawable.mood_angry);
                mood = "angry"; moodMode=0; break;
            default:
                moodSwitcher.setImageResource(R.drawable.mood_default);
                mood = "error"; moodMode=-1; break;
        }
        saveMood(year+"/"+month+"/"+date,mood);

    }

    //get the system date
    public void getCurrentDate()
    {
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH)+1;
        date = cal.get(Calendar.DATE);

        Log.d("Date Error", String.valueOf(month));

        String monthVoc = transferMonth();

        setMonth.setText(monthVoc);
        setDate.setText(String.valueOf(date));
    }

    // transfers month from numeric to alphabetic
    public String transferMonth ()
    {

        switch (month)
        {
            case 1: return "January";
            case 2: return "February";
            case 3: return "March";
            case 4: return "April";
            case 5: return "May";
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
            default: return "error";
        }
    }

    // choose when the baby wakes up in the morning
    public View.OnClickListener pickDayTime = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(showDay.this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtWakeTime.setText(toZeroZero(hourOfDay) + ":" + toZeroZero(minute));
                            saveWakeTime(year+"/"+month+"/"+date,toZeroZero(hourOfDay) + ":" + toZeroZero(minute));

                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    };

    // choose when the baby sleeps at night
    public View.OnClickListener pickNightTime = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(showDay.this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtBedTime.setText(toZeroZero(hourOfDay) + ":" + toZeroZero(minute));
                            saveBedTime(year+"/"+month+"/"+date,toZeroZero(hourOfDay) + ":" + toZeroZero(minute));
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    };

    // choose when the baby drank milk and the amount of milk
    public View.OnClickListener pickMilkTime = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            if (milkMode==0)
            {
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(showDay.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                milkTime = toZeroZero(hourOfDay) + ":" + toZeroZero(minute) + "  " ;
                                txtMilkInfo.setText(milkTime);
                                milkMode++;
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

            else if (milkMode==1)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(showDay.this);
                // Get the layout inflater
                LayoutInflater inflater = showDay.this.getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(inflater.inflate(R.layout.milk_dialog, null))
                        // Add action buttons
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Dialog d = (Dialog) dialog;
                                EditText edtMilkAmount = (EditText) d.findViewById(R.id.milkAmountInfo);
                                if (milk!=null)
                                    milk = milk + milkTime + "   " + edtMilkAmount.getText().toString() + " mL\n";
                                else
                                    milk = milkTime + "   " + edtMilkAmount.getText().toString() + " mL\n";
                                txtMilkInfo.setText(milk);
                                Data data1 = new Data("milk", year+"/"+month+"/"+date, milkTime, edtMilkAmount.getText().toString() + " mL");
                                data1 = database.insertData(data1);
                                milkMode++;
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.create();
                builder.show();
            }
            else if (milkMode==2)
            {
                TimePickerDialog timePickerDialog = new TimePickerDialog(showDay.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                milkTime = toZeroZero(hourOfDay) + ":" + toZeroZero(minute) + "  " ;
                                txtMilkInfo.setText(milk+milkTime);
                                milkMode = 1;
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        }
    };

    // choose when the baby ate and the details of the food
    public View.OnClickListener pickFoodTime = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            if (foodMode==0)
            {
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(showDay.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                foodTime = toZeroZero(hourOfDay) + ":" + toZeroZero(minute) + "  " ;
                                txtFoodInfo.setText(foodTime);
                                foodMode++;
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

            else if (foodMode==1)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(showDay.this);
                // Get the layout inflater
                LayoutInflater inflater = showDay.this.getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(inflater.inflate(R.layout.food_dialog, null))
                        // Add action buttons
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Dialog d = (Dialog) dialog;
                                EditText edtFood = (EditText) d.findViewById(R.id.foodTypeInfo);
                                EditText edtFoodAmount = (EditText) d.findViewById(R.id.foodAmountInfo);
                                if (food!=null)
                                    food = food + foodTime + edtFoodAmount.getText().toString() + " of " + edtFood.getText().toString() + "\n";
                                else
                                    food = foodTime + edtFoodAmount.getText().toString() + " of " + edtFood.getText().toString() + "\n";
                                txtFoodInfo.setText(food);
                                Data data1 = new Data("food", year+"/"+month+"/"+date, foodTime, edtFoodAmount.getText().toString() + " of " + edtFood.getText().toString());
                                data1 = database.insertData(data1);
                                foodMode++;
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.create();
                builder.show();
            }

            else if (foodMode==2)
            {
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(showDay.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                foodTime = toZeroZero(hourOfDay) + ":" + toZeroZero(minute) + "  " ;
                                txtFoodInfo.setText(food+foodTime);
                                foodMode=1;
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        }
    };

    // choose when the baby changes diaper and the appearance of the poop
    public View.OnClickListener pickPoopTime = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            if (poopMode==0)
            {
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(showDay.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                poopTime = toZeroZero(hourOfDay) + ":" + toZeroZero(minute) + "  " ;
                                txtPoopInfo.setText(poopTime);
                                poopMode++;
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

            else if (poopMode==1)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(showDay.this);
                // Get the layout inflater
                LayoutInflater inflater = showDay.this.getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(inflater.inflate(R.layout.poop_dialog, null))
                        // Add action buttons
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Dialog d = (Dialog) dialog;
                                EditText edtColor = (EditText) d.findViewById(R.id.colorInfo);
                                EditText edtShape = (EditText) d.findViewById(R.id.shapeInfo);
                                if (poop!=null)
                                    poop = poop + poopTime + edtColor.getText().toString() + "  " + edtShape.getText().toString() + "\n";
                                else
                                    poop = poopTime + edtColor.getText().toString() + "  " + edtShape.getText().toString() + "\n";
                                Data data1 = new Data("stool", year+"/"+month+"/"+date, poopTime, edtColor.getText().toString() + "  " + edtShape.getText().toString());
                                data1 = database.insertData(data1);
                                txtPoopInfo.setText(poop);
                                poopMode++;
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.create();
                builder.show();
            }

            else if (poopMode==2)
            {
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(showDay.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                poopTime = toZeroZero(hourOfDay) + ":" + toZeroZero(minute) + "  " ;
                                txtPoopInfo.setText(poop+poopTime);
                                poopMode=1;
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        }
    };

    // choose when the baby naps and wake up time
    public View.OnClickListener pickSleepTime = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(showDay.this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            if (sleepMode==0)
                            {
                                sleepTime = toZeroZero(hourOfDay) + ":" + toZeroZero(minute) + "  ~  " ;
                                txtSleepInfo.setText(sleepTime);
                                Data data1 = new Data("napTmp", year+"/"+month+"/"+date, null, sleepTime);
                                data1 = database.insertData(data1);
                                sleepMode++;
                            }
                            else if (sleepMode==1)
                            {
                                sleepTime = sleepTime + toZeroZero(hourOfDay) + ":" + toZeroZero(minute);
                                if (sleep!=null)
                                    sleep = sleep + sleepTime + "\n";
                                else
                                    sleep = sleepTime + "\n";
                                txtSleepInfo.setText(sleep);
                                saveSleepTime("napTmp", year+"/"+month+"/"+date, sleepTime + "\n");
                                sleepMode++;
                            }
                            else if (sleepMode==2)
                            {
                                sleepTime = toZeroZero(hourOfDay) + ":" + toZeroZero(minute) + "  ~  " ;
                                txtSleepInfo.setText(sleep + sleepTime);
                                Data data1 = new Data("napTmp", year+"/"+month+"/"+date, null, sleepTime);
                                data1 = database.insertData(data1);
                                sleepMode=1;
                            }
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    };

    // choose when the baby goes outdoors
    public View.OnClickListener pickOutdoorTime = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(showDay.this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            if (outdoorMode==0)
                            {
                                outdoorTime = toZeroZero(hourOfDay) + ":" + toZeroZero(minute) + "  ~  " ;
                                txtOutdoorInfo.setText(outdoorTime);
                                Data data1 = new Data("outdoorTmp", year+"/"+month+"/"+date, null, outdoorTime);
                                data1 = database.insertData(data1);
                                outdoorMode++;
                            }
                            else if (outdoorMode==1)
                            {
                                outdoorTime = outdoorTime + toZeroZero(hourOfDay) + ":" + toZeroZero(minute);
                                if (outdoor!=null)
                                    outdoor = outdoor + outdoorTime + "\n" ;
                                else
                                    outdoor = outdoorTime + "\n" ;
                                txtOutdoorInfo.setText(outdoor);
                                saveOutdoorTime("outdoorTmp", year+"/"+month+"/"+date, outdoorTime + "\n");
                                outdoorMode++;
                            }
                            else if (outdoorMode==2)
                            {
                                outdoorTime = toZeroZero(hourOfDay) + ":" + toZeroZero(minute) + "  ~  " ;
                                txtOutdoorInfo.setText(outdoor+outdoorTime);
                                Data data1 = new Data("outdoorTmp", year+"/"+month+"/"+date, null, outdoorTime);
                                data1 = database.insertData(data1);
                                outdoorMode=1;
                            }

                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    };

    // choose when the baby takes a bath
    public View.OnClickListener pickBathTime = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(showDay.this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtBathInfo.setText(toZeroZero(hourOfDay) + ":" + toZeroZero(minute));
                            saveBathTime(year+"/"+month+"/"+date,toZeroZero(hourOfDay) + ":" + toZeroZero(minute));
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    };

    public View.OnClickListener writeOthers = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            AlertDialog.Builder builder = new AlertDialog.Builder(showDay.this);
            // Get the layout inflater
            LayoutInflater inflater = showDay.this.getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.other_dialog, null))
                    // Add action buttons
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Dialog d = (Dialog) dialog;
                            EditText edtOtherInfoBox = (EditText) d.findViewById(R.id.otherInfoBox);
                            if (other!=null)
                                other = other + edtOtherInfoBox.getText().toString() + "\n";
                            else
                                other = edtOtherInfoBox.getText().toString() + "\n";
                            txtOtherInfo.setText(other);
                            Data data1 = new Data("other", year+"/"+month+"/"+date, null, other);
                            data1 = database.insertData(data1);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            builder.create();
            builder.show();
        }
    };


    // adding zero to the number smaller than 10
    public String toZeroZero (int i) {
        if(i < 10) {
            return "0" + Integer.toString(i);
        }
        else
            return Integer.toString(i);
    }

    public void readData (String Date)
    {
        Data temp;
        for (long i = 1; i <= database.getCount(); i++) {
            temp = database.getData(i);
            if (temp.getDate() != null) {
                if (temp.getDate().equals(Date)) {
                    if (temp.getEvent().equals("mood"))
                    {
                        mood = temp.getMark();
                        switch (mood)
                        {
                            case "happy": moodSwitcher.setImageResource(R.drawable.mood_happy); moodMode = 0; break;
                            case "sad": moodSwitcher.setImageResource(R.drawable.mood_sad); moodMode = 1; break;
                            case "sick": moodSwitcher.setImageResource(R.drawable.mood_sick); moodMode = 2; break;
                            case "angry": moodSwitcher.setImageResource(R.drawable.mood_angry); moodMode = 4; break;
                            case "worried": moodSwitcher.setImageResource(R.drawable.mood_worried); moodMode = 3; break;
                        }
                    }

                    else if (temp.getEvent().equals("wake up time"))
                        txtWakeTime.setText(temp.getMark());

                    else if (temp.getEvent().equals("bed time"))
                        txtBedTime.setText(temp.getMark());

                    else if (temp.getEvent().equals("milk"))
                    {
                        if (milk!=null)
                            milk = milk + temp.getTime()  + temp.getMark();
                        else
                            milk = temp.getTime()  + temp.getMark();
                        txtMilkInfo.setText(milk);
                    }

                    else if (temp.getEvent().equals("food"))
                    {
                        if (food!=null)
                            food = food + temp.getTime()  + temp.getMark();
                        else
                            food = temp.getTime()  + temp.getMark();
                        txtFoodInfo.setText(food);
                    }

                    else if (temp.getEvent().equals("stool"))
                    {
                        if (poop!=null)
                            poop = poop + temp.getTime()  + temp.getMark();
                        else
                            poop = temp.getTime()  + temp.getMark();
                        txtPoopInfo.setText(poop);
                    }

                    else if (temp.getEvent().equals("nap time"))
                    {
                        if (sleep!=null)
                            sleep = sleep + temp.getMark();
                        else
                            sleep = temp.getMark();
                        txtSleepInfo.setText(sleep);
                    }

                    else if (temp.getEvent().equals("napTmp"))
                    {
                        sleepTime = temp.getMark();

                        if (sleep!=null)
                            txtSleepInfo.setText(sleep + sleepTime);
                        else
                            txtSleepInfo.setText(sleepTime);
                    }

                    else if (temp.getEvent().equals("outdoor"))
                    {
                        if (outdoor!=null)
                            outdoor = outdoor + temp.getMark();
                        else
                            outdoor = temp.getMark();
                        txtOutdoorInfo.setText(outdoor);
                    }

                    else if (temp.getEvent().equals("outdoorTmp"))
                    {
                        outdoorTime = temp.getMark();

                        if (outdoor!=null)
                            txtOutdoorInfo.setText(outdoor + outdoorTime);
                        else
                            txtOutdoorInfo.setText(outdoorTime);
                    }

                    else if (temp.getEvent().equals("bath time"))
                    {
                        txtBathInfo.setText(temp.getMark());
                        Log.d("BATH", temp.getMark());
                    }

                    else if (temp.getEvent().equals("other"))
                        txtOtherInfo.setText(temp.getMark());
                }
            }
        }
    }




}
