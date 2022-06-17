package com.phptravelsnative.Activities;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.phptravelsnative.Adapters.Tp_CheckOut_adapters;
import com.phptravelsnative.Fragments.Guest;
import com.phptravelsnative.Fragments.Login_Fragment;
import com.phptravelsnative.Models.ExpediaExtra;
import com.phptravelsnative.Models.Hotel_data;
import com.phptravelsnative.Models.PassengerModel;
import com.phptravelsnative.Models.TravelPortDetails;
import com.phptravelsnative.Models.car_model;
import com.phptravelsnative.Network.Post.ExpediaBooking;
import com.phptravelsnative.Network.Post.Tp_CheckoutRequest;
import com.phptravelsnative.Network.Post.TravelPort_Booking;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.Constant;
import com.phptravelsnative.utality.Views.SingleTonRequest;
import com.phptravelsnative.utality.Views.TourSpinner;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Tp_Card extends Drawer {

    private ViewGroup cardFront;
    private ViewGroup cardBack;
    boolean isBackShowing = false;

    EditText cvv;
    EditText card_number;
    EditText card_name;
    EditText date;
    TextView card_number_preview;
    TextView card_name_preview;
    TextView card_date_preview;
    TextView card_cvv_preview;
    ProgressDialog pd;
    Button book;
    String card_type;
    String expMonth = "";
    String travelportCheckoutResp = "";
    View background_view;
    String travelportCartResp = "";
    String expYear = "";
    TourSpinner creditCard;
    ArrayList<String> first_name = new ArrayList<>();
    ArrayList<String> last_name = new ArrayList<>();
    ArrayList<String> email = new ArrayList<>();
    ArrayList<String> nationality = new ArrayList<>();
    ArrayList<String> cdn = new ArrayList<>();
    ArrayList<String> mr = new ArrayList<>();
    ArrayList<String> phone = new ArrayList<>();




    LinearLayout mRelativeLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.tp_card);
        View inflated = stub.inflate();

        pd = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);
        pd.setIndeterminate(true);
        pd.setCancelable(true);
        pd.setMessage(getString(R.string.loading));


        mRelativeLayout=(LinearLayout)inflated.findViewById(R.id.main_layout);
        background_view=inflated.findViewById(R.id.dialog_background);

        Intent intent=getIntent();

        travelportCheckoutResp = intent.getExtras().getString("travelportCheckoutResp");
        travelportCartResp = intent.getExtras().getString("travelportCartResp");

        ArrayList<PassengerModel> pms = intent.getParcelableArrayListExtra("passenger_data");

        for(int i=0;i<pms.size();i++){

            PassengerModel pm = pms.get(i);
            first_name.add(pm.getFirst_name());
            last_name.add(pm.getInput_name());
            email.add(pm.getInput_email());
            cdn.add(pm.getCdn());
            mr.add(pm.getMr());
            phone.add(pm.getInput_phone());
            nationality.add(pm.getNationality());

        }



        cardFront = (ViewGroup) inflated.findViewById(R.id.card_preview_front);
        cardBack = (ViewGroup) inflated.findViewById(R.id.card_preview_back);





        cvv= (EditText) inflated.findViewById(R.id.cvc);
        card_number=(EditText)inflated.findViewById(R.id.card_number);
        card_name=(EditText)inflated.findViewById(R.id.card_name);
        date=(EditText)inflated.findViewById(R.id.expiry_date);
        creditCard=(TourSpinner) inflated.findViewById(R.id.tour_type);
        book=(Button)inflated.findViewById(R.id.book);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickHotelSelect();
            }
        });
        card_number_preview=(TextView)findViewById(R.id.card_preview_number);
        card_cvv_preview=(TextView)findViewById(R.id.card_preview_cvc);
        card_date_preview=(TextView)findViewById(R.id.card_preview_expiry);
        card_name_preview=(TextView)findViewById(R.id.card_preview_name);



        getCards();


        card_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text=s.toString();
                for(int i=s.length();i<16;i++)
                {
                    text=text+"X";
                }
                card_number_preview.setText(text);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
       card_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                card_name_preview.setText(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cvv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                showBack();
            }
        });
        card_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                showFront();
            }
        });
        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                showFront();
            }
        });
        card_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                showFront();
            }
        });


        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate()) {
                    SendBooking();

                }
            }
        });



    }

    private boolean validate() {


        if(date.getText().toString().equals("")) {
            date.setError(getString(R.string.spefiy_date));
            return false;
        }else if(cvv.getText().toString().length()<3)
        {
            cvv.setError(getString(R.string.cvv_number));
            return false;
        }else if(card_number.getText().toString().length()<16)
        {
            card_number.setError(getString(R.string.cvv_number));
            return false;
        }
        return true;
    }




    private void showBack() {
        if (!isBackShowing) {
            Animator cardFlipLeftIn = AnimatorInflater.loadAnimator(getBaseContext(), R.animator.card_flip_left_in);
            cardFlipLeftIn.setTarget(cardFront);
            cardFlipLeftIn.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    cardFront.setVisibility(View.GONE);
                    cardBack.setVisibility(View.VISIBLE);
                    isBackShowing = true;
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            cardFlipLeftIn.start();
        }
    }



    private void showFront() {
        if (isBackShowing) {
            Animator cardFlipRightIn = AnimatorInflater.loadAnimator(getBaseContext(), R.animator.card_flip_right_in);
            cardFlipRightIn.setTarget(cardBack);
            cardFlipRightIn.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    cardBack.setVisibility(View.GONE);
                    cardFront.setVisibility(View.VISIBLE);
                    isBackShowing = false;
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            cardFlipRightIn.start();

        }

    }


    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==1)
        {
            return checkDate.ShowDailog(listener_data) ;

        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener listener_data=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


            if(month<10)
                day_to="0"+month;
            else
                day_to= ""+month;

            expMonth = day_to;
            expYear = year+"";
            date.setText(day_to+"/"+year);
            card_date_preview.setText(day_to+"/"+year);

        }
    };
    public void OnClickHotelSelect()
    {
        showDialog(1);

    }
    private void getCards() {

        String[] name_card = new String[]{"Mastercard","Visa","American Express","Discover"};
        String[] name_code = new String[]{"CA","VI","Express","DS"};
        creditCard.listAdapter(name_card,name_code);


    }
    private void SendBooking(){

        pd.show();
        pd.setCancelable(false);
        Response.Listener<String> listener =new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject main_json = new JSONObject(response);
                    if(main_json.getString("status").equals("success")){

                        Dialog dialog = new Dialog(Tp_Card.this);
                        dialog.setContentView(R.layout.tp_congragulation);
                        dialog.setCancelable(false);

                        Button b = dialog.findViewById(R.id.goto_home);

                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), MainLayout.class);
                                intent.putExtra("CheckLayout", "MainList");
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });

                        dialog.show();
                        background_view.setVisibility(View.VISIBLE);


                        pd.dismiss();


                    }else{

                        Toast.makeText(getBaseContext(), main_json.getString("message"), Toast.LENGTH_LONG).show();
                        pd.dismiss();


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }

            }
        };


        Response.ErrorListener listener1 =new  Response.ErrorListener() {
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
                Toast.makeText(getBaseContext(),logText,Toast.LENGTH_LONG).show();
                pd.dismiss();
              //  finish();
            }
        };

        String userId = "0";
        if(sharedPreferences.getBoolean("Check_Login",true)) {
            userId = sharedPreferences.getString("id","0");
        }

            TravelPort_Booking requestRegister=new TravelPort_Booking(this.mr,this.first_name,this.last_name,this.phone,
                    this.email,this.nationality,this.cdn,(this.first_name.size()+1)+"",creditCard.getTag().toString(),this.expMonth
                    ,this.expYear,this.cvv.getText().toString(),this.card_number.getText().toString(),travelportCheckoutResp,travelportCartResp,userId,listener,listener1);
            requestRegister.setRetryPolicy(new DefaultRetryPolicy(50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = SingleTonRequest.getmInctance(this).getRequestQueue();
            requestQueue.add(requestRegister);

    }

}
