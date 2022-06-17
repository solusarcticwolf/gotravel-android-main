package com.phptravelsnative.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.phptravelsnative.Activities.DateActivity;
import com.phptravelsnative.Activities.SearchViewActivity;
import com.phptravelsnative.Activities.SearchingCarTourOffers;
import com.phptravelsnative.Models.Auto_Model;
import com.phptravelsnative.Models.car_model;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.ComplexPreferences;
import com.phptravelsnative.utality.Extra.DateSeter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class car_home extends Fragment {


    EditText car_to;
    EditText car_from;

    TextView time_from,time_to;

    ComplexPreferences sharedPreferences;
    String MyPREFERENCES = "MyPrefs";
    SharedPreferences.Editor editor;

    DatePickerDialog date2;
    Calendar calendar = Calendar.getInstance();

    View inflated;

    Auto_Model car_form_m=new Auto_Model();
    Auto_Model car_to_m=new Auto_Model();

    String date_from_api;
    String date_to_api;

    Button search;
    TextView date_from,date_to;
    DateSeter checkDate;
    View keyBoard;

    ArrayList<Auto_Model> location_from=new ArrayList<>();
    ArrayList<Auto_Model> location_to=new ArrayList<>();
    private int day_to_int;
    String datefirts;
    public car_home() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflated = inflater.inflate(R.layout.car_layout, container, false);
        sharedPreferences =new ComplexPreferences(getContext(),MyPREFERENCES, Context.MODE_PRIVATE);
        checkDate = new DateSeter(getContext());

        car_to = (EditText) inflated.findViewById(R.id.car_to);
        car_from = (EditText) inflated.findViewById(R.id.car_from);

        car_form_m=sharedPreferences.getObject("carObjectFrom",Auto_Model.class,car_form_m);
        car_to_m=sharedPreferences.getObject("carObjectTo",Auto_Model.class,car_to_m);




        String resfrom = getActivity().getString(R.string.location_from);
        String location_to = getActivity().getString(R.string.location_to);

        car_from.setText(car_form_m.getName());
        car_to.setText( car_to_m.getName());

        Date d = new Date();
        CharSequence s  = DateFormat.format("dd-MM-yyyy ", d.getTime());
        datefirts = String.valueOf(s);

        car_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),SearchViewActivity.class);
                intent.putExtra("ModuleTyple","CarFrom");
                startActivityForResult(intent,0);
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
          car_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),SearchViewActivity.class);
                intent.putExtra("ModuleTyple","CarTo");
                startActivityForResult(intent,1);
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });



        date_from=(TextView)inflated.findViewById(R.id.date_from);
        date_to=(TextView)inflated.findViewById(R.id.date_to);


        search=(Button) inflated.findViewById(R.id.search_hotels);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButton();
            }
        });




        String datwefrom=checkDate.getCurrentDay()+"/"+checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentYear();
        date_from.setText(convertDate(datwefrom));

        date_from_api=checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentDay()+"/"+checkDate.getCurrentYear();

        checkDate.incrementBy();

        date_to_api=checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentDay()+"/"+checkDate.getCurrentYear();


        String date_ttoo=checkDate.getCurrentDay()+"/"+checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentYear();
        date_to.setText(convertDate(date_ttoo));




        time_from=(TextView)inflated.findViewById(R.id.time_from);
        time_to=(TextView)inflated.findViewById(R.id.time_to);


        time_from.setText(checkDate.getCurrentTime());
        time_to.setText(checkDate.getCurrentTime());


        date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendcalander();
            }
        });
        date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendcalanderreturn();
            }
        });
        time_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTimeSeter(v);
            }
        });
        time_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTimeSeter(v);
            }
        });


        return inflated;

    }


    private void showTimePicker(int a) {

        booking_car.TimePickerFragment Time = new booking_car.TimePickerFragment();



        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();

        args.putInt("mint", calender.get(Calendar.MINUTE));
        args.putInt("hour", calender.get(Calendar.HOUR_OF_DAY));
        args.putInt("a", a);
        Time.setArguments(args);


        if (a == 1) {
            Time.setCallBack(Time_from_listener);
        } else {
            Time.setCallBack(Time_to_listener);
        }
        Time.show(getFragmentManager(), "Date Picker");


    }
    public void OnClickHotelSelect()
    {
        opendcalander();
        /*Intent intent=new Intent(getContext(), DateActivity.class);
        startActivityForResult(intent,3);
        getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);*/
    }

    public void onTimeSeter(View v)
    {
        if(v.getId()== R.id.time_from)
            showTimePicker(0);
        else
            showTimePicker(1);
    }

    public void onClickButton()
    {






        Intent intent=new Intent(getContext(),SearchingCarTourOffers.class);
        car_model c=new car_model();
        c.setPickupDate(date_from_api);
        c.setDropOfDate(date_to_api);
        c.setDropOfTime(time_to.getText().toString());
        c.setPickupTime(time_from.getText().toString());
        Bundle bundle=new Bundle();
        bundle.putParcelable("carInfo",c);
        bundle.putString("type","cars");
        intent.putExtras(bundle);
        if(!car_to.getText().toString().equals("") || !car_from.getText().toString().equals(""))
        {
            if(car_form_m.getId()==0|| car_to_m.getId()==0)
            {
                Toast.makeText(getContext(),"Nothing Found",Toast.LENGTH_LONG).show();
            }else {
                c.setPickupId(car_form_m.getId());
                c.setDropOfId(car_to_m.getId());
                startActivity(intent);
            }
        }
        else
        {
            c.setPickupId(0);
            c.setDropOfId(0);
            bundle.putParcelable("carInfo",c);
            bundle.putString("type","cars");
            startActivity(intent);
        }
    }
    TimePickerDialog.OnTimeSetListener Time_from_listener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            String res = getActivity().getString(R.string.carfrom_time);
            time_from.setText("  "+hourOfDay+":"+minute);
            showTimePicker(0);
        }
    };
    TimePickerDialog.OnTimeSetListener Time_to_listener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            String res = getActivity().getString(R.string.carto_time);
            time_to.setText("  "+hourOfDay + ":"+minute);
        }
    };



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0)
        {


            String resfrom = getActivity().getString(R.string.location_from);
            String location_to = getActivity().getString(R.string.location_to);
            car_form_m=sharedPreferences.getObject("carObjectFrom",Auto_Model.class,car_form_m);
            car_from.setText(car_form_m.getName());
            car_to.setText(car_form_m.getName());



          /*  car_from.setText("from city : "+car_form_m.getName());
            car_to.setText("to city : "+car_to_m.getName());*/


        }else if(requestCode==1)
        {
            car_to_m=sharedPreferences.getObject("carObjectTo",Auto_Model.class,car_to_m);


           // String resfrom = getActivity().getString(R.string.location_from);
            String location_to = getActivity().getString(R.string.location_to);
            car_to.setText(car_to_m.getName());

        }else if(requestCode==3)
        {
            if(data!=null) {
                date_from.setText(data.getStringExtra("date_from"));
                date_from_api = data.getStringExtra("date_from_api");
                date_to_api = data.getStringExtra("date_to_api");
                date_to.setText(data.getStringExtra("date_to"));
            }
        }
    }

    public void opendcalander()
    {
        //Toast.makeText(getActivity(),"select start date ",Toast.LENGTH_LONG).show();
        Calendar cal = Calendar.getInstance();
        int mDay =   cal.get(Calendar.DAY_OF_MONTH);
        int  mYear = cal.get(Calendar.YEAR);
        int  mMonth = cal.get(Calendar.MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                monthOfYear=monthOfYear+1;
                String date  = dayOfMonth+"-"+monthOfYear+"-"+year;
                date_from_api= date ;
                datefirts=date;

                date_from.setText(date);


               // Toast.makeText(getActivity(),date,Toast.LENGTH_LONG).show();
                // tv_fromdate.setText(convertDate(checkDate.getCurrentDay()+"/"+checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentYear()));
                // tv_dayfrom.setText(convertday(checkDate.getCurrentDay()+"/"+checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentYear()));

                //tv_fromdate.setText(convertDate(date));
                //tv_dayfrom.setText(convertDate(date));
                //cabin_data.setFrom_date(date);





              //  date_to_api = data.getStringExtra("date_to_api");
                //date_from_api =date2;
                //   Toast.makeText(getActivity(),date,Toast.LENGTH_LONG).show();
            }
        }, mYear, mMonth, mDay);

        // c.add(Calendar.DATE, 6);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        datePickerDialog.show();

    }

    public void opendcalanderreturn()
    {

      //  Toast.makeText(getActivity(),"select retrun date ",Toast.LENGTH_LONG).show();

        String strDate = String.valueOf(datefirts);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);


        int mDay =   cal.get(Calendar.DAY_OF_MONTH);
        int  mYear = cal.get(Calendar.YEAR);
        int  mMonth = cal.get(Calendar.MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                        monthOfYear=monthOfYear+1;
                        String date = dayOfMonth+"-"+monthOfYear+"-"+year;



                        date_to_api=date;

                        date_to.setText(date);

                       /* tv_dateretun.setText(convertDate(date));
                        tv_dayretun.setText(convertday(date));
                        //cabin_data.setTo_date(date_to_api);
                        btn_return_date_switch.setVisibility(View.VISIBLE);
                        cabin_data.setTo_date(date);
                        String date2  = year+"-"+monthOfYear+"-"+dayOfMonth;*/

                    }
                }, mYear, mMonth, mDay);

        //cal.add(Calendar.DATE, 6);
        datePickerDialog.setTitle("ewwwwwwww");
        datePickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        datePickerDialog.show();













    }



    String convertDate(String inputDate) {

        java.text.DateFormat theDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;

        try {
            date = theDateFormat.parse(inputDate);
        } catch (ParseException parseException) {
            // Date is invalid. Do what you want.
        } catch(Exception exception) {
            // Generic catch. Do what you want.
        }
        //20,September,2019
        theDateFormat = new SimpleDateFormat("dd, MMMM , yyyy");

        return theDateFormat.format(date);
    }
}
