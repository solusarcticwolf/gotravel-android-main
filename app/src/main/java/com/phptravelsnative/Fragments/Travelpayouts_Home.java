package com.phptravelsnative.Fragments;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.devspark.robototextview.widget.RobotoCheckedTextView;
import com.devspark.robototextview.widget.RobotoTextView;
import com.phptravelsnative.Activities.Cabin_Class;
import com.phptravelsnative.Activities.DateActivity;
import com.phptravelsnative.Activities.Find_Taxi;
import com.phptravelsnative.Activities.ManualFlightActivity;
import com.phptravelsnative.Activities.ManualFlightActivity_New;
import com.phptravelsnative.Activities.SearchViewActivity;
import com.phptravelsnative.Activities.SearchingHotels;
import com.phptravelsnative.Activities.SingleDay;
import com.phptravelsnative.Activities.TravelPortActivity;
import com.phptravelsnative.Models.Amenities_Model;
import com.phptravelsnative.Models.Auto_Model;
import com.phptravelsnative.Models.Hotel_data;
import com.phptravelsnative.Models.OverView;
import com.phptravelsnative.Models.TravelPort;
import com.phptravelsnative.Network.Parser.DetailRequest;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.ComplexPreferences;
import com.phptravelsnative.utality.Extra.DateSeter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;//Travelpayouts_Home


public class Travelpayouts_Home extends Fragment  {

    Hotel_data hotel_data;
    ComplexPreferences sharedPreferences;
    String MyPREFERENCES = "MyPrefs";
    DatePickerDialog.OnDateSetListener dateSetListener;

    DateSeter checkDate;

    String date_from_api;
    String date_to_api;
    String checkTourType = "oneway";
    TravelPort cabin_data = new TravelPort();

    View inflated;
    Auto_Model searchHotel=new Auto_Model();
    Auto_Model to_travel=new Auto_Model();
    RelativeLayout btn_trip_class;
    RelativeLayout btn_passengers;
    LinearLayout btn_origin ;
    LinearLayout btn_destination;
    LinearLayout rl_deprat,rl_return;

    TextView tv_fromdate,tv_dayfrom;
/*
    RobotoTextView hotel_name,tv_id,tv_cityfrom,tv_from,tv_airportfrom,tv_airportto;
    RobotoTextView tv_fromdate,tv_dayfrom;
    RobotoTextView tv_dateretun,tv_dayretun;
  */

    RobotoTextView tv_trip_class;
    RobotoTextView tv_adults,tv_children,tv_infants;
    ImageView btn_return_date_switch;

    Button search;
    String type = "";
    Boolean checkBoxState =false;

    ImageButton returncheck;
    RobotoTextView tv_cityto,tv_cityfrom;
    public static Fragment newInstance(String type) {
        Travelpayouts_Home fragment = new Travelpayouts_Home();


        Bundle args = new Bundle();
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    public Travelpayouts_Home() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        inflated  =  inflater.inflate(R.layout.newflight_desgin, container, false);

        hotel_data = new Hotel_data();
        sharedPreferences =new ComplexPreferences(getContext(),MyPREFERENCES, Context.MODE_PRIVATE);



        hotel_data = new Hotel_data();
        sharedPreferences =new ComplexPreferences(getContext(),MyPREFERENCES, Context.MODE_PRIVATE);
        rl_return = (LinearLayout) inflated.findViewById(R.id.rl_return);
        rl_deprat= (LinearLayout) inflated.findViewById(R.id.rl_deprat);
        tv_cityto =  inflated.findViewById(R.id.tv_cityto);
        tv_cityfrom  =  inflated.findViewById(R.id.tv_cityfrom);
        btn_origin =  inflated.findViewById(R.id.btn_origin);
        btn_destination=  inflated.findViewById(R.id.btn_destination);
        tv_fromdate=  inflated.findViewById(R.id.tv_fromdate);
        tv_dayfrom=  inflated.findViewById(R.id.tv_dateretun);
        tv_trip_class=  inflated.findViewById(R.id.tv_trip_class);
        tv_adults= (RobotoTextView) inflated.findViewById(R.id.tv_adults);
        tv_children= (RobotoTextView) inflated.findViewById(R.id.tv_children);
        tv_infants= (RobotoTextView) inflated.findViewById(R.id.tv_infants);


        search= (Button) inflated.findViewById(R.id.search_hotels);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("yesworking","working2");
                onClickButton();
            }
        });

        checkDate=new DateSeter(getContext());

        btn_origin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),SearchViewActivity.class);
                intent.putExtra("ModuleTyple","TravelPort_from");
                startActivityForResult(intent,1);
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });

        btn_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),SearchViewActivity.class);
                intent.putExtra("ModuleTyple","TravelPort_to");
                startActivityForResult(intent,4);
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });


        returncheck=inflated.findViewById(R.id.returncheck);
        returncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_dayfrom.setText("Return Date");
                checkTourType = "oneway";
                returncheck.setVisibility(View.GONE);
            }
        });



        type = getArguments().getString("type");
        cabin_data.setAdults("1");
        cabin_data.setChild("0");
        cabin_data.setInfants("0");
        cabin_data.setClass_type("economy");
        checkTourType = "oneway";








        searchHotel=sharedPreferences.getObject("TravelPort_from",Auto_Model.class,searchHotel);
        String namefrom=searchHotel.getCountryCode();
        if (TextUtils.isEmpty(namefrom))
        {

           // tv_cityfrom.setText("Destination ");
            tv_cityto.setText("Origin ");
            // tv_id.setText(" ");
            //tv_airportto.setText("");
        }else {

            tv_cityto.setText(searchHotel.getName());
            // tv_id.setText(searchHotel.getType());
            //tv_airportto.setText(searchHotel.getCountryCode());
        }



        to_travel=sharedPreferences.getObject("TravelPort_to",Auto_Model.class,searchHotel);
        String nameto=to_travel.getCountryCode();
        if (TextUtils.isEmpty(nameto))
        {

            tv_cityfrom.setText("Destination ");
           // tv_cityto.setText("Origin ");
            // tv_from.setText(to_travel.getType());
            //tv_airportfrom.setText(to_travel.getCountryCode());
        }else {

            tv_cityfrom.setText(to_travel.getName());
            // tv_from.setText(to_travel.getType());
            ///tv_airportfrom.setText(to_travel.getCountryCode());
        }










        //date_to.setTypeface(null, Typeface.BOLD);


        btn_passengers =(RelativeLayout)  inflated.findViewById(R.id.btn_passengers);
        btn_passengers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogtripe_passengers();
            }
        });


        btn_trip_class= (RelativeLayout) inflated.findViewById(R.id.btn_trip_class);
        btn_trip_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogtrip_class();
            }
        });








        checkDate=new DateSeter(getContext());
        checkDate.incrementBy();
        checkDate.incrementBy();
        checkDate.incrementBy();


        tv_fromdate.setText(convertDate(checkDate.getCurrentDay()+"/"+checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentYear()));
        // tv_dayfrom.setText(convertday(checkDate.getCurrentDay()+"/"+checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentYear()));
        date_from_api=checkDate.getCurrentYear()+"-"+checkDate.getCurrentMonthN()+"-"+checkDate.getCurrentDay();


        checkDate.incrementBy();

        date_to_api=checkDate.getCurrentYear()+"-"+checkDate.getCurrentMonthN()+"-"+checkDate.getCurrentDay();



        rl_deprat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                opendcalander();

            }
        });
        rl_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                checkBoxState=true;
                checkTourType = "round";
                opendcalanderreturn();


            }
        });


        tv_dayfrom.setText("Return Date");

        return inflated;
    }

    public void onClickButton() {
        //Toast.makeText(getContext(),"im here", Toast.LENGTH_LONG).show();

        if(checkTourType.equals("oneway"))
        {
            cabin_data.setFrom_id(searchHotel.getType());
            cabin_data.setTo_id(to_travel.getType());
            cabin_data.setFrom_date(date_from_api);
            cabin_data.setTo_date(date_to_api);
            cabin_data.setTour_type(checkTourType);
            Intent intent = new Intent(getContext(), ManualFlightActivity.class);
            Bundle b = new Bundle();
            b.putParcelable("cabin_date",cabin_data);
            intent.putExtras(b);
            intent.putExtra("Model","flights");
            startActivity(intent);

        }else{


            cabin_data.setFrom_id(searchHotel.getType());
            cabin_data.setTo_id(to_travel.getType());
            cabin_data.setFrom_date(date_from_api);
            cabin_data.setTo_date(date_to_api);
            cabin_data.setTour_type(checkTourType);

            Intent intent = new Intent(getContext(),ManualFlightActivity.class);
            Bundle b = new Bundle();
            b.putParcelable("cabin_date",cabin_data);
            intent.putExtras(b);
            startActivity(intent);
            // Toast.makeText(getContext(),"im here", Toast.LENGTH_LONG).show();


        }


    }





    public void OnClickHotelSelect()
    {


        if (!checkTourType.equals("oneway")){

            Intent intent=new Intent(getContext(),SingleDay.class);
            intent.putExtra("check","travel_port");
            startActivityForResult(intent,0);
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        }else {

            Date date = new Date();
            checkTourType = "round";
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                // return_text.setText("Return");
                date = dateFormat.parse(date_from_api);
                calendar.setTime(date);
                calendar.add(Calendar.DATE,1);
                // oneway.setVisibility(View.VISIBLE);
                date_to_api=calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
                // tv_dateretun.setText(convertDate(calendar.get(Calendar.DAY_OF_MONTH)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR)));
                // tv_dayretun.setText(convertday(calendar.get(Calendar.DAY_OF_MONTH)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR)));
                //  date_to.setTypeface(null, Typeface.NORMAL);

            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if(requestCode==0) {
            if(data!=null) {
                date_to_api = data.getStringExtra("date_to_api");
                //  tv_dateretun.setText(convertDate(data.getStringExtra("date_to")));
                //  tv_dayretun.setText(convertday(data.getStringExtra("date_to")));
                cabin_data.setTo_date(date_to_api);
                btn_return_date_switch.setVisibility(View.VISIBLE);
                // // date_to.setTypeface(null, Typeface.NORMAL);

            }
        }else if(requestCode==1)
        {
            searchHotel=sharedPreferences.getObject("TravelPort_from",Auto_Model.class,searchHotel);
            String namefrom=searchHotel.getName();
            if (TextUtils.isEmpty(namefrom))
            {

               // tv_cityto.setText("From City ");
                //tv_cityfrom.setText("Destination ");
                tv_cityto.setText("Origin ");
                // tv_id.setText(" ");
                //tv_airportto.setText("");
            }else {

                tv_cityto.setText(searchHotel.getName());
                //tv_id.setText(searchHotel.getType());
                //tv_airportto.setText(searchHotel.getCountryCode());
            }






            // tv_id.setText(searchHotel.getType());
            ///  tv_airportto.setText(searchHotel.getCountryCode());
        }else if(requestCode==4)
        {
            to_travel=sharedPreferences.getObject("TravelPort_to",Auto_Model.class,searchHotel);
            String namefrom=to_travel.getName();
            if (TextUtils.isEmpty(namefrom))
            {

                tv_cityfrom.setText("Destination ");
                //tv_id.setText(" ");
                //tv_airportto.setText("");
            }else {

                tv_cityfrom.setText(to_travel.getName());
                //tv_from.setText(to_travel.getType());
                //tv_airportfrom.setText(to_travel.getCountryCode());
            }

        }else if(requestCode==2 && (resultCode == 2))
        {
            tv_fromdate.setText(convertDate(checkDate.getCurrentDay()+"/"+checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentYear()));
            tv_dayfrom.setText(convertday(checkDate.getCurrentDay()+"/"+checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentYear()));

            date_from_api = data.getStringExtra("date_to_api");
            // cabin_data.setFrom_date(date_from_api);

        }else if((requestCode==3) && (resultCode == 3))
        {
            cabin_data = data.getExtras().getParcelable("cabinData");
            tv_trip_class.setText(""+cabin_data);
            //   cabin_return.setText(cabin_data.getAdults()+" Adults "+cabin_data.getChild()+" Childs "+cabin_data.getInfants()+" Infants "
            // +cabin_data.getClass_type());
        }
    }


    public void openDialogtrip_class() {
    /*  final Dialog dialog = new Dialog(getActivity()); // Context, this, etc.
      dialog.setContentView(R.layout.trip_class_picker_fragment);
      dialog.show();*/


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.trip_class_picker_fragment, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        FrameLayout economyclass = (FrameLayout) dialogView.findViewById(R.id.economyclass);
        FrameLayout  classbusiness = (FrameLayout) dialogView.findViewById(R.id.classbusiness);
        FrameLayout  classpremium = (FrameLayout) dialogView.findViewById(R.id.classpremium);

        final ImageView image_economy = (ImageView) dialogView.findViewById(R.id.image_economy);
        final ImageView image_business = (ImageView) dialogView.findViewById(R.id.image_business);
        final ImageView image_premium = (ImageView) dialogView.findViewById(R.id.image_premium);


        final RobotoCheckedTextView tv_economy = (RobotoCheckedTextView) dialogView.findViewById(R.id.tv_economy);
        final RobotoCheckedTextView tv_business = (RobotoCheckedTextView) dialogView.findViewById(R.id.tv_business);
        final RobotoCheckedTextView tv_premium = (RobotoCheckedTextView) dialogView.findViewById(R.id.tv_premium);



        if (tv_trip_class.getText().equals(tv_economy.getText()))
        {
            image_economy.setBackgroundResource(R.drawable.radio_button_checked);
        }else if (tv_trip_class.getText().equals(tv_business.getText()))
        {
            image_business.setBackgroundResource(R.drawable.radio_button_checked);
        }else
        {
            image_premium.setBackgroundResource(R.drawable.radio_button_checked);
        }






        economyclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //cabin_data.setClass_type("economy");
                image_economy.setBackgroundResource(R.drawable.radio_button_checked);
                image_business.setBackgroundResource(R.drawable.radio_button);
                image_premium.setBackgroundResource(R.drawable.radio_button);
                // alertDialog.dismiss();
                tv_trip_class.setText(tv_economy.getText());
                final Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        alertDialog.dismiss();
                        t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                    }
                }, 200);
            }
        });
        classbusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // cabin_data.setClass_type(""+tv_business.getText());
                image_business.setBackgroundResource(R.drawable.radio_button_checked);
                image_economy.setBackgroundResource(R.drawable.radio_button);
                image_premium.setBackgroundResource(R.drawable.radio_button);
                tv_trip_class.setText(tv_business.getText());
                final Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        alertDialog.dismiss();
                        t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                    }
                }, 200);
            }
        });

        classpremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cabin_data.setClass_type("premium");
                image_business.setBackgroundResource(R.drawable.radio_button);
                image_economy.setBackgroundResource(R.drawable.radio_button);
                image_premium.setBackgroundResource(R.drawable.radio_button_checked);
                tv_trip_class.setText(tv_premium.getText());
                final Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        alertDialog.dismiss();
                        t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                    }
                }, 200);
            }
        });

    }


    public void openDialogtripe_passengers() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.passangers_picker_fragment, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        ImageView a_decrease = (ImageView) dialogView.findViewById(R.id.a_decrease);
        ImageView a_increase = (ImageView) dialogView.findViewById(R.id.a_increase);
        final RobotoTextView a_number=(RobotoTextView) dialogView.findViewById(R.id.a_number);
        //int atotal = Integer.parseInt(String.valueOf(a_number.getText()));




        a_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                a_number.setText(subfuncation(String.valueOf(a_number.getText())));
            }
        });

        a_increase .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                a_number.setText(addfuncation(String.valueOf(a_number.getText())));
            }
        });


        ImageView c_decrease = (ImageView) dialogView.findViewById(R.id.c_decrease);
        ImageView c_increase = (ImageView) dialogView.findViewById(R.id.c_increase);
        final RobotoTextView c_number=(RobotoTextView) dialogView.findViewById(R.id.c_number);

        c_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c_number.setText(subfuncation(String.valueOf(c_number.getText())));
            }
        });

        c_increase .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c_number.setText(addfuncation(String.valueOf(c_number.getText())));
            }
        });



        ImageView f_decrease = (ImageView) dialogView.findViewById(R.id.f_decrease);
        ImageView f_increase = (ImageView) dialogView.findViewById(R.id.f_increase);
        final RobotoTextView f_number=(RobotoTextView) dialogView.findViewById(R.id.f_number);

        f_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                f_number.setText(subfuncation(String.valueOf(f_number.getText())));
            }
        });

        f_increase .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                f_number.setText(addfuncation(String.valueOf(f_number.getText())));
            }
        });



        if (tv_adults.getText().equals("0"))
        {
            a_number.setText("1");
        }else
        {
            a_number.setText(tv_adults.getText());
        }
        c_number.setText(tv_children.getText());
        f_number.setText(tv_infants.getText());

        RobotoTextView postive_button= (RobotoTextView) dialogView.findViewById(R.id.tv_positive_btn);
        postive_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_adults.setText(a_number.getText());
                tv_children.setText(c_number.getText());
                tv_infants.setText(f_number.getText());

                cabin_data.setAdults(""+tv_adults.getText());
                cabin_data.setChild(""+tv_children.getText());
                cabin_data.setInfants(""+tv_infants.getText());

                alertDialog.dismiss();
            }
        });

    }


    public String subfuncation(String number)
    {
        int atotal = Integer.parseInt(number);

        if (atotal >=1 )
        {
            atotal = atotal -1;
        }


        return ""+atotal;
    }

    public String addfuncation(String number)
    {
        int atotal = Integer.parseInt(number);

        if (atotal <9 )
        {
            atotal = atotal +1;
        }


        return ""+atotal;
    }


    String convertDate(String inputDate) {

        DateFormat theDateFormat = new SimpleDateFormat("dd/MM/yyyy");
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

    String convertday(String inputDate) {

        DateFormat theDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;

        try {
            date = theDateFormat.parse(inputDate);
        } catch (ParseException parseException) {
            // Date is invalid. Do what you want.
        } catch(Exception exception) {
            // Generic catch. Do what you want.
        }
        //20,September,2019
        theDateFormat = new SimpleDateFormat("EEEE");

        return theDateFormat.format(date);
    }




    public void opendcalanderreturn()
    {

        Toast.makeText(getActivity(),"select retrun date ",Toast.LENGTH_LONG).show();

        String strDate =String.valueOf(tv_fromdate.getText());
        SimpleDateFormat sdf = new SimpleDateFormat("dd, MMMM , yyyy");
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                        monthOfYear=monthOfYear+1;
                        String date = dayOfMonth+"/"+monthOfYear+"/"+year;
                        tv_dayfrom.setText(convertDate(date));
                        // tv_dayfrom.setText(convertday(date));
                        //cabin_data.setTo_date(date_to_api);
//                        btn_return_date_switch.setVisibility(View.VISIBLE);
                        cabin_data.setTo_date(date);
                        String date2  = year+"-"+monthOfYear+"-"+dayOfMonth;
                        date_to_api=date2;
                        returncheck.setVisibility(View.VISIBLE);
                    }
                }, mYear, mMonth, mDay);

        //cal.add(Calendar.DATE, 6);
        datePickerDialog.setTitle("ewwwwwwww");
        datePickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        datePickerDialog.show();













    }


    public void opendcalander()
    {
        Toast.makeText(getActivity(),"select start date ",Toast.LENGTH_LONG).show();



        String strDate =String.valueOf(tv_fromdate.getText());
        SimpleDateFormat sdf = new SimpleDateFormat("dd, MMMM , yyyy");
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        //cal.add(Calendar.DATE, 3);
        int mDay =   cal.get(Calendar.DAY_OF_MONTH);
        int  mYear = cal.get(Calendar.YEAR);
        int  mMonth = cal.get(Calendar.MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                        monthOfYear=monthOfYear+1;
                        String date  = dayOfMonth+"/"+monthOfYear+"/"+year;
                        tv_fromdate.setText(convertDate(checkDate.getCurrentDay()+"/"+checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentYear()));
                        //  tv_dayfrom.setText(convertday(checkDate.getCurrentDay()+"/"+checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentYear()));

                        tv_fromdate.setText(convertDate(date));
                        //tv_dayfrom.setText(convertDate(date));
                        cabin_data.setFrom_date(date);
                        String date2  = year+"-"+monthOfYear+"-"+dayOfMonth;
                        date_from_api =date2;
                        //   Toast.makeText(getActivity(),date,Toast.LENGTH_LONG).show();
                    }
                }, mYear, mMonth, mDay);

        //cal.add(Calendar.DATE, 6);
        datePickerDialog.setTitle("ewwwwwwww");
        datePickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        datePickerDialog.show();




        /*Calendar cal = Calendar.getInstance();
        int mDay =   cal.get(Calendar.DAY_OF_MONTH);
        int  mYear = cal.get(Calendar.YEAR);
        int  mMonth = cal.get(Calendar.MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                monthOfYear=monthOfYear+1;
                String date  = dayOfMonth+"/"+monthOfYear+"/"+year;
                 tv_fromdate.setText(convertDate(checkDate.getCurrentDay()+"/"+checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentYear()));
               //  tv_dayfrom.setText(convertday(checkDate.getCurrentDay()+"/"+checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentYear()));

                tv_fromdate.setText(convertDate(date));
                //tv_dayfrom.setText(convertDate(date));
                cabin_data.setFrom_date(date);
                String date2  = year+"-"+monthOfYear+"-"+dayOfMonth;
                date_from_api =date2;
             //   Toast.makeText(getActivity(),date,Toast.LENGTH_LONG).show();
            }
        }, mYear, mMonth, mDay);

        //c.add(Calendar.DATE, 6);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        datePickerDialog.show();*/

    }
}


