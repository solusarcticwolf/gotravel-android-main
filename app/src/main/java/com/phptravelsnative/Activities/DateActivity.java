package com.phptravelsnative.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.calendarlistview.library.DatePickerController;
import com.andexert.calendarlistview.library.DayPickerView;
import com.andexert.calendarlistview.library.SimpleMonthAdapter;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.DateSeter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateActivity extends Drawer implements DatePickerController {

    TextView date_from,date_to;
    ImageView img_from,img_to;
    private DayPickerView dayPickerView;
    DateSeter dateSeter;
    String date_from_api;
    String date_to_api;
    boolean b=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.activity_date);
        View inflated = stub.inflate();


        textView.setVisibility(View.GONE);

        closeBtn.setVisibility(View.VISIBLE);



        dayPickerView = (DayPickerView)inflated.findViewById(R.id.pickerView);
        dayPickerView.setController(this);


        date_from=(TextView)inflated.findViewById(R.id.date_from);
        date_to=(TextView)inflated.findViewById(R.id.date_to);
        img_from=(ImageView)inflated.findViewById(R.id.img_from);
        img_to=(ImageView)inflated.findViewById(R.id.img_to);

        dateSeter=new DateSeter(this);
        date_from_api=dateSeter.getCurrentMonthN()+"/"+dateSeter.getCurrentDay()+"/"+dateSeter.getCurrentYear();
        date_from.setText(dateSeter.getCurrentDay()+"/"+dateSeter.getCurrentMonthN()+"/"+dateSeter.getCurrentYear());
        dateSeter.incrementBy();
        date_to_api=dateSeter.getCurrentMonthN()+"/"+dateSeter.getCurrentDay()+"/"+dateSeter.getCurrentYear();
        date_to.setText(dateSeter.getCurrentDay()+"/"+dateSeter.getCurrentMonthN()+"/"+dateSeter.getCurrentYear());


        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();

                intent.putExtra("date_from",date_from.getText().toString());
                intent.putExtra("date_to",date_to.getText().toString());
                intent.putExtra("date_from_api",date_from_api);
                intent.putExtra("date_to_api",date_to_api);

                setResult(2,intent);
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

            }
        });

    }

    @Override
    public int getMaxYear() {
        return dateSeter.getCurrentYear()+1;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {

        String monthS;
        String dayS;
        month=month+1;
        if(month<10)
        {
            monthS="0"+month;
        }else {
            monthS=""+month;
        }
        if(day<10)
        {
            dayS="0"+day;
        }else {
            dayS=""+day;
        }

        Date date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        if(b) {
            b=false;
            date_from.setTextColor(getResources().getColor(R.color.aluminum));
            date_to.setTextColor(getResources().getColor(R.color.Dark_blue));
            date_from.setText(dayS+"/"+monthS+"/"+year);
            date_from_api=monthS+"/"+dayS+"/"+year;
            img_from.setVisibility(View.GONE);
            img_to.setVisibility(View.VISIBLE);
        }else {
            try {
                date=sdf.parse(date_from_api);
                long time_from=date.getTime();
                date=sdf.parse(monthS+"/"+dayS+"/"+year);
                long time_to=date.getTime();
                if(time_to<time_from)
                {
                    date_to.setText(date_from.getText().toString());
                    date_to_api=date_from_api;
                    date_from.setText(dayS+"/"+monthS+"/"+year);
                    date_from_api=monthS+"/"+dayS+"/"+year;

                }else
                {
                    date_to.setText(dayS+"/"+monthS+"/"+year);
                    date_to_api=monthS+"/"+dayS+"/"+year;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
            date_to.setTextColor(getResources().getColor(R.color.aluminum));
            date_from.setTextColor(getResources().getColor(R.color.Dark_blue));
            img_from.setVisibility(View.VISIBLE);
            img_to.setVisibility(View.GONE);
            b=true;

        }

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
