package com.phptravelsnative.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.phptravelsnative.Models.TravelPort;
import com.phptravelsnative.Network.Post.LoginRequest;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Views.SingleTonRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Tp_Login_Booking extends Drawer {


    EditText _emailText;
    EditText _passwordText;
    Button login_btn;
    Button guest_btn;
    TravelPort cabin_class = new TravelPort();
    String from_key = "";
    String to_key = "";
    String tp_detials = "";
    String searchPassenger = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.tp_login_booking);
        View inflated = stub.inflate();
        _emailText = (EditText)inflated.findViewById(R.id.input_email);
        _passwordText = (EditText)inflated.findViewById(R.id.input_password);
        login_btn = (Button) inflated.findViewById(R.id.btn_login);
        guest_btn = (Button) inflated.findViewById(R.id.btn_guest);

        Intent intent = getIntent();

        cabin_class = intent.getExtras().getParcelable("cabin_date");
        from_key = intent.getExtras().getString("from_keys");
        to_key = intent.getExtras().getString("to_keys");
        tp_detials = intent.getExtras().getString("tp_detials");
        searchPassenger = intent.getExtras().getString("searchPassenger");


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();


            }
        });
        guest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tp_Login_Booking.this,CheckOutDetails.class);
                Bundle b = new Bundle();
                b.putParcelable("cabin_date",cabin_class);
                b.putString("from_keys",from_key);
                b.putString("to_keys",to_key);
                b.putString("tp_detials",tp_detials);
                b.putString("searchPassenger",searchPassenger);
                intent.putExtras(b);
                startActivity(intent);

            }
        });




    }

    public void login() {

        if (!validate()) {
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.authenticating));
        progressDialog.show();

        String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();

        Response.Listener<String> listener =new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    progressDialog.dismiss();
                    JSONObject main_json = new JSONObject(response);
                    if(main_json.getBoolean("response"))
                    {
                        JSONObject userInfo_object=main_json.getJSONObject("userInfo");
                        editor.putBoolean("Check_Login",true);
                        editor.putString("email",userInfo_object.getString("email"));
                        editor.putString("name",userInfo_object.getString("firstName")+" "+userInfo_object.getString("lastName"));
                        editor.putString("password",password);
                        editor.putString("id",userInfo_object.getString("id"));
                        editor.commit();
                        View keyBoard =getCurrentFocus();
                        if (keyBoard != null) {
                            InputMethodManager imm = (InputMethodManager)getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(keyBoard.getWindowToken(), 0);
                        }
                        onLoginSuccess();
                    }
                    else
                    {
                        JSONObject error_object=main_json.getJSONObject("error");
                        Toast.makeText(Tp_Login_Booking.this,error_object.getString("msg"),Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        LoginRequest requestRegister=new LoginRequest(email,password,listener);

        RequestQueue requestQueue = SingleTonRequest.getmInctance(this).getRequestQueue();
        requestQueue.add(requestRegister);

    }

    private void onLoginSuccess() {

        Intent intent = new Intent(Tp_Login_Booking.this,CheckOutDetails.class);
        Bundle b = new Bundle();
        b.putParcelable("cabin_date",cabin_class);
        b.putString("from_keys",this.from_key);
        b.putString("to_keys",this.to_key);
        b.putString("tp_detials",tp_detials);
        b.putString("searchPassenger",searchPassenger);
        intent.putExtras(b);
        startActivity(intent);

    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
