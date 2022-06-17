package com.phptravelsnative.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.phptravelsnative.Activities.DateActivity;
import com.phptravelsnative.Activities.SearchViewActivity;
import com.phptravelsnative.Activities.SearchingHotels;
import com.phptravelsnative.Models.Auto_Model;
import com.phptravelsnative.Models.Hotel_data;
import com.phptravelsnative.Network.Parser.DetailRequest;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.ComplexPreferences;
import com.phptravelsnative.utality.Extra.DateSeter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Default_Hotel extends Fragment {


    Hotel_data hotel_data;
    ComplexPreferences sharedPreferences;
    String MyPREFERENCES = "MyPrefs";
    TextView date_from, date_to;

    DateSeter checkDate;

    Auto_Model searchHotel=new Auto_Model();

    String datefirts;
    String datesecond;
    String date_from_api;
    String date_to_api;

    MaterialSpinner child,adult;
    View inflated;
    EditText hotel_name;


    Button search;


    public Default_Hotel() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        inflated  =  inflater.inflate(R.layout.hotels_layout, container, false);

        hotel_data = new Hotel_data();
        sharedPreferences =new ComplexPreferences(getContext(),MyPREFERENCES, Context.MODE_PRIVATE);

        hotel_name= (EditText) inflated.findViewById(R.id.tour_auto_search);

        searchHotel=sharedPreferences.getObject("Default_Hotel_Last_Search",Auto_Model.class,searchHotel);


        String namefrom=searchHotel.getName();
        if (TextUtils.isEmpty(namefrom))
        {

            hotel_name.setText(getString(R.string.hint_hotel));
        }else {

            hotel_name.setText(" "+searchHotel.getName());
        }






        adult= (MaterialSpinner) inflated.findViewById(R.id.adult);
        child= (MaterialSpinner) inflated.findViewById(R.id.child);

        // adult.setText(String.format("%s 2", getString(R.string.adult)));
        //child.setText(String.format("%s 0", getString(R.string.child)));

        adult.setTextColor(getResources().getColor(R.color.black));
        adult.setItems("Adult 1", "Adult 2","Adult 3", "Adult 4","Adult 5","Adult 6","Adult 7", "Adult 8","Adult 9", "Adult 10");
        adult.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                // Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                adult.setText(item);
            }
        });



        child.setTextColor(getResources().getColor(R.color.black));
        child.setItems("Child","Child 1", "Child 2","Child 3", "Child 4","Child 5","Child 6","Child 7", "Child 8","Child 9", "Child 10");
        child.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                // Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                child.setText(item);
            }
        });



        date_from=(TextView)inflated.findViewById(R.id.date_from);
        date_to=(TextView)inflated.findViewById(R.id.date_to);

        search= (Button) inflated.findViewById(R.id.search_hotels);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButton();
            }
        });

        date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                opendcalander();
                // OnClickHotelSelect();
            }
        });
        date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (datefirts.isEmpty())
                {
                    Toast.makeText(getContext(),"Select  first from",Toast.LENGTH_LONG).show();
                }else
                {
                    opendcalanderreturn();
                }


                //OnClickHotelSelect();
            }
        });

          checkDate=new DateSeter(getContext());

        hotel_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),SearchViewActivity.class);
                intent.putExtra("ModuleTyple","DefaultHotel");
                startActivityForResult(intent,1);
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });


        String dateformat_from =  formatdate(checkDate.getCurrentDay()+"-"+checkDate.getCurrentMonthN()+"-"+checkDate.getCurrentYear());
        date_from.setText(dateformat_from);

        date_from_api=checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentDay()+"/"+checkDate.getCurrentYear();
        datefirts =checkDate.getCurrentDay()+"-"+checkDate.getCurrentMonthN()+"-"+checkDate.getCurrentYear();


        checkDate.incrementBy();
        checkDate.incrementBy();
        datesecond= checkDate.getCurrentDay()+"-"+checkDate.getCurrentMonthN()+"-"+checkDate.getCurrentYear();
        date_to_api=checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentDay()+"/"+checkDate.getCurrentYear();




        String dateformat_to=formatdate(checkDate.getCurrentDay()+"-"+checkDate.getCurrentMonthN()+"-"+checkDate.getCurrentYear());
        date_to.setText(dateformat_to);




        date_from_api= checkDate.getCurrentDay()+"-"+checkDate.getCurrentMonthN()+"-"+checkDate.getCurrentYear();
        date_to_api= checkDate.getCurrentDay()+"-"+checkDate.getCurrentMonthN()+"-"+checkDate.getCurrentYear();



        date_from_api=dateconvertformt(""+date_from.getText());
        date_to_api =dateconvertformt(""+date_to.getText());

        return inflated;

    }

    public void onClickButton() {


        String childrndaccount=""+child.getText();
        if (childrndaccount.equals("Child"))
        {
            childrndaccount = "0";
        }else
        {
            childrndaccount = childrndaccount.replaceAll("\\D+","");
        }




        Intent intent;
        Hotel_data data = new Hotel_data();
        data.setFrom(date_from_api);
        data.setTo(date_to_api);
        data.setAdult(adult.getText().charAt(adult.length()-1)+"");
        data.setChild(childrndaccount);
        data.setLocation(hotel_name.getText().toString());
        if (hotel_name.getText().toString().equals("")) {

            data.setId(0);
            data.setLocation(hotel_name.getText().toString());
            intent = new Intent(getContext(), SearchingHotels.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("ho", data);
            intent.putExtra("check_ean", false);
            intent.putExtras(bundle);
            startActivity(intent);

        } else {
            data.setId(searchHotel.getId());
            switch (searchHotel.getType()) {
                case "hotel":
                    DetailRequest checkDetial = new DetailRequest(getContext(), searchHotel.getId(), data);
                    checkDetial.checkResult();
                    break;
                case "location":
                    intent = new Intent(getContext(), SearchingHotels.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("ho", data);
                    intent.putExtra("check_ean", false);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                default:
                    Toast.makeText(getContext(), "Nothing Found", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }





    public void OnClickHotelSelect()
    {
        Intent intent=new Intent(getContext(), DateActivity.class);
        startActivityForResult(intent,0);
        getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0) {
            if(data!=null) {
                date_from.setText(data.getStringExtra("date_from"));
                date_from_api = data.getStringExtra("date_from_api");
                date_to_api = data.getStringExtra("date_to_api");
                date_to.setText(data.getStringExtra("date_to"));
            }
        }else if(requestCode==1)
        {
            searchHotel=sharedPreferences.getObject("Default_Hotel_Last_Search",Auto_Model.class,searchHotel);
            String namefrom=searchHotel.getName();
            if (TextUtils.isEmpty(namefrom))
            {

                hotel_name.setText(getString(R.string.hint_hotel));
            }else {

                hotel_name.setText(" "+searchHotel.getName());
            }

        }
    }



    public void opendcalander()
    {



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
        // cal.add(Calendar.DATE, 1);

        int mDay =   cal.get(Calendar.DAY_OF_MONTH);
        int  mYear = cal.get(Calendar.YEAR);
        int  mMonth = cal.get(Calendar.MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                        monthOfYear=monthOfYear+1;
                        String date  = dayOfMonth+"-"+monthOfYear+"-"+year;


                        autodatesetter(date);
                        date_from_api= date ;
                        //datesecond=date;

                        String date_to=formatdate(date);

                        date_from.setText(date_to);

                    }
                }, mYear, mMonth, mDay);

        //cal.add(Calendar.DATE, 6);
        datePickerDialog.setTitle("ewwwwwwww");
        datePickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        datePickerDialog.show();

        /*//Toast.makeText(getActivity(),"select start date ",Toast.LENGTH_LONG).show();
        Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.DATE, 3);
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

                String date_to=formatdate(date);

                date_from.setText(date_to);

            }
        }, mYear, mMonth, mDay);

        // c.add(Calendar.DATE, 6);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        datePickerDialog.show();*/

    }

    public void opendcalanderreturn()
    {

        //  Toast.makeText(getActivity(),"select retrun date ",Toast.LENGTH_LONG).show();

        String strDate = String.valueOf(datesecond);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        // cal.add(Calendar.DATE, 2);
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
                        String date_from=formatdate(date);
                        date_to.setText(date_from);

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


    public String formatdate(String fdate)
    {
        String datetime=null;
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat d= new SimpleDateFormat("EEEE , dd-MMM-yyyy");
        try {
            Date convertedDate = null;
            try {
                convertedDate = inputFormat.parse(fdate);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            datetime = d.format(convertedDate);

        }catch (ParseException e)
        {

        }
        return  datetime;


    }


    public void autodatesetter(String date1)
    {

        //  Toast.makeText(getActivity(),"select retrun date ",Toast.LENGTH_LONG).show();

        String strDate = String.valueOf(date1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 2);
        int mDay =   cal.get(Calendar.DAY_OF_MONTH);
        int  mYear = cal.get(Calendar.YEAR);
        int  mMonth = cal.get(Calendar.MONTH);
        mMonth=mMonth+1;

        String datemake = mDay+"-"+mMonth+"-"+mYear;
        datesecond=datemake;
        date_to_api=datemake;
        String date_from=formatdate(datemake);
        date_to.setText(date_from);



    }



    public String dateconvertformt(String fdate)
    {
        String datetime=null;
        SimpleDateFormat d = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat inputFormat= new SimpleDateFormat("EEEE , dd-MMM-yyyy");


        try {
            Date convertedDate = null;
            try {
                convertedDate = inputFormat.parse(fdate);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            datetime = d.format(convertedDate);

        }catch (ParseException e)
        {

        }
        return  datetime;


    }



}
