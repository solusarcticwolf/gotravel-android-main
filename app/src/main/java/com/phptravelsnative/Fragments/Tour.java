package com.phptravelsnative.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.phptravelsnative.Activities.SearchViewActivity;
import com.phptravelsnative.Activities.SearchingCarTourOffers;
import com.phptravelsnative.Activities.SingleDay;
import com.phptravelsnative.Models.Auto_Model;
import com.phptravelsnative.Models.Model_Tour;
import com.phptravelsnative.Network.Parser.TourRequest;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.ComplexPreferences;
import com.phptravelsnative.utality.Extra.Constant;
import com.phptravelsnative.utality.Extra.DateSeter;
import com.phptravelsnative.utality.Views.LightSpinner;
import com.phptravelsnative.utality.Views.TourSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Tour extends Fragment {

    AutoCompleteTextView auto_tour;
    ArrayList<Auto_Model> auto_array_list=new ArrayList<>();
    ArrayList<Auto_Model> tour_type=new ArrayList<>();


    ArrayList<String> tour_type_arr=new ArrayList<String>();
    ArrayList<String> type_id=new ArrayList<String>();
    Auto_Model SearchName=new Auto_Model();
    ComplexPreferences sharedPreferences;
    String MyPREFERENCES = "MyPrefs";
    DateSeter checkDate;
    TextView date_to;
    String date_to_api;
    MaterialSpinner tourSpinner;
    ImageView tour_icon;
    LightSpinner adult1;
    MaterialSpinner adult;
    ProgressBar pb;
    Button search;
    View inflated;
    View date;
    String datatourid="AllCategories";

    private String item_url = Constant.domain_name+"tours/tourtypes?appKey="+Constant.key+"&lang="+Constant.default_lang;
    public Tour() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        inflated= inflater.inflate(R.layout.activity_tours, container, false);

        sharedPreferences =new ComplexPreferences(getContext(),MyPREFERENCES, Context.MODE_PRIVATE);
        checkDate = new DateSeter(getContext());
        auto_tour= (AutoCompleteTextView) inflated.findViewById(R.id.tour_auto_search);
         date=inflated.findViewById(R.id.date);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  OnClickHotelSelect();

                opendcalander();
            }
        });

        SearchName=sharedPreferences.getObject("TourObject",Auto_Model.class,SearchName);




        String namefrom=SearchName.getName();
        if (TextUtils.isEmpty(namefrom))
        {

            auto_tour.setText(getActivity().getString(R.string.hint_tour));
        }else {

            auto_tour.setText(SearchName.getName());
        }


        //auto_tour.setText(SearchName.getName());




        auto_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),SearchViewActivity.class);
                intent.putExtra("ModuleTyple","Tour");
                startActivityForResult(intent,1);
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        date_to=(TextView)inflated.findViewById(R.id.date_from);
        search=(Button) inflated.findViewById(R.id.search_hotels);
        tour_icon=(ImageView) inflated.findViewById(R.id.tour_icon);


        /*tour_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tourSpinner.performClick();
            }
        });*/

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButton();
                //opendcalander();
            }
        });



        adult=(MaterialSpinner) inflated.findViewById(R.id.adult);
      //  adult4=(MaterialSpinner) inflated.findViewById(R.id.adult4);
        tourSpinner=(MaterialSpinner) inflated.findViewById(R.id.tour_type);

        adult.setTextColor(getResources().getColor(R.color.black));
        tourSpinner.setTextColor(getResources().getColor(R.color.black));
        adult.setItems("Adult 1", "Adult 2","Adult 3", "Adult 4","Adult 5","Adult 6","Adult 7", "Adult 8","Adult 9", "Adult 10");
        adult.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                // Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                adult.setText(item);
            }
        });



        tour_type_arr.add("All Categories");
        tourSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {




               if (position==0)
                {
                    datatourid="AllCategories";
                }else
                {
                    datatourid  = type_id.get(position-1);
                }




             //  Snackbar.make(view, datatourid, Snackbar.LENGTH_LONG).show();


            }
        });


       // adult.setText(String.format("%s 2", getString(R.string.adult)));
       //adult.setTag("2");



        pb= (ProgressBar) inflated.findViewById(R.id.tour_type_progress);
        checkResultForTerm();

        date_to_api=checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentDay()+"/"+checkDate.getCurrentYear();

        date_to.setText(convertDate(checkDate.getCurrentDay()+"/"+checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentYear()));

        return inflated;
    }
    public void onClickButton()
    {





        Bundle bundle=new Bundle();
        Model_Tour t=new Model_Tour();
        Intent intent;

        if (auto_tour.getText().toString().equals("")) {

            t.setId(0);
            t.setType(datatourid);
            t.setDate(date_to_api);
            t.setTitle(" "+auto_tour.getText().toString());
            t.setGusest(adult.getText().charAt(adult.length()-1)+"");
            bundle.putParcelable("tours",t);
            intent=new Intent(getContext(),SearchingCarTourOffers.class);
            intent.putExtra("type","tour");
            intent.putExtras(bundle);

            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        }else
        {
            t.setId(SearchName.getId());
            t.setType(datatourid);
            t.setDate(date_to_api);
            t.setTitle(auto_tour.getText().toString());
            t.setGusest(adult.getText().charAt(adult.length()-1)+"");
            bundle.putParcelable("tours",t);

            if(SearchName.getType().equals("tour"))
            {
                TourRequest tourRequest=new TourRequest(getContext(),SearchName.getId(),t);
                tourRequest.checkResult();

            }else if(SearchName.getType().equals("location")){
                intent=new Intent(getContext(),SearchingCarTourOffers.class);
                intent.putExtra("type","tour");
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);


            }else
            {

                Toast.makeText(getContext(),"Nothing Found"+auto_tour.getText().toString(),Toast.LENGTH_LONG).show();
            }
        }

    }


    public  void checkResultForTerm()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,item_url, new Response.Listener() {
            @Override
            public void onResponse(Object response) {

                JSONObject parentObject = null;
                try {
                    parentObject = new JSONObject(response.toString());
                    JSONArray jsonArray= parentObject.getJSONArray("response");

                    for (int i = 0; i <jsonArray.length(); i++) {
                        JSONObject jo = jsonArray.getJSONObject(i);
                        tour_type_arr.add(jo.getString("name"));
                        type_id.add(jo.getInt("id")+"");

                     //   adult4.setItems("ADULT 1");
                    }
                }
                catch(JSONException e){

                    Log.d("abcwwwwd",e.getMessage());
                }




                tourSpinner.setVisibility(View.VISIBLE);
                //tourSpinner.listAdapter(tour_type_arr,type_id);
                tourSpinner.setItems(tour_type_arr);




                pb.setVisibility(View.GONE);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error == null)
                    return;
                String logText;
                if (error.networkResponse == null) {
                    logText = error.getMessage();
                } else {
                    logText = error.getMessage() + ", status "
                            + error.networkResponse.statusCode
                            + " - " + error.networkResponse.toString();
                }
                Log.e("HamzaError" + "-", logText, error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
    public void OnClickHotelSelect()
    {
        Intent intent=new Intent(getContext(),SingleDay.class);
        intent.putExtra("check","tour");
        startActivityForResult(intent,0);
        getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0) {
            if(data!=null) {
                date_to_api = data.getStringExtra("date_to_api");
                date_to.setText(data.getStringExtra("date_to"));
            }
        }else if(requestCode==1)
        {
            SearchName=sharedPreferences.getObject("TourObject",Auto_Model.class,SearchName);
            auto_tour.setText(" "+SearchName.getName());
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
                date_to_api=date;
               // date_to.setText(date);
                date_to.setText(convertDate(dayOfMonth+"/"+monthOfYear+"/"+year));

            }
        }, mYear, mMonth, mDay);

        // c.add(Calendar.DATE, 6);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        datePickerDialog.show();

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
}
