package com.phptravelsnative.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import com.andexert.calendarlistview.library.DatePickerController;
import com.andexert.calendarlistview.library.DayPickerView;
import com.andexert.calendarlistview.library.SimpleMonthAdapter;
import com.phptravelsnative.Models.TravelPort;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.DateSeter;

public class SingleDay extends Drawer implements DatePickerController {
    TextView date_to;
    private DayPickerView dayPickerView;
    DateSeter dateSeter;
    String date_to_api;
    String check="tour";
    TravelPort cabin_class = new TravelPort();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.activity_single_day);
        View inflated = stub.inflate();
//cabin_class.getAdults()




        textView.setVisibility(View.GONE);

        closeBtn.setVisibility(View.VISIBLE);

        Intent intent=getIntent();

        cabin_class = intent.getExtras().getParcelable("cabin_date");
        check=intent.getStringExtra("check");



        dayPickerView = (DayPickerView) inflated.findViewById(R.id.pickerView);
        dayPickerView.setController(this);


        date_to = (TextView) inflated.findViewById(R.id.date_to);

        dateSeter = new DateSeter(this);

        date_to_api = dateSeter.getCurrentMonthN() + "/" + dateSeter.getCurrentDay() + "/" + dateSeter.getCurrentYear();
        date_to.setText(dateSeter.getCurrentDay() + "/" + dateSeter.getCurrentMonthN() + "/" + dateSeter.getCurrentYear());

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();


                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("dateselect", date_to.getText().toString()); // Storing string
                editor.commit();

                   intent.putExtra("date_to", date_to.getText().toString());
                   intent.putExtra("date_to_api", date_to_api);

                   setResult(2, intent);
                   finish();
                   overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);


            }
        });

    }

    @Override
    public int getMaxYear() {
        return dateSeter.getCurrentYear() + 1;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {

        String monthS;
        String dayS;
        month = month + 1;
        if (month < 10) {
            monthS = "0" + month;
        } else {
            monthS = "" + month;
        }
        if (day < 10) {
            dayS = "0" + day;
        } else {
            dayS = "" + day;
        }
        if(check.equals("travel_port"))
            date_to_api=year+"-"+monthS+"-"+dayS;
        else
            date_to_api=monthS+"/"+dayS+"/"+year;




        date_to.setText(dayS+"/"+monthS+"/"+year);
    }

    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }
}