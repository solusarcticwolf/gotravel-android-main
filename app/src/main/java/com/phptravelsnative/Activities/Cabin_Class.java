package com.phptravelsnative.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phptravelsnative.Models.Hotel_data;
import com.phptravelsnative.Models.Model_Tour;
import com.phptravelsnative.Models.TravelPort;
import com.phptravelsnative.Models.TravelPortModel;
import com.phptravelsnative.R;

public class Cabin_Class extends Drawer {

    TextView a_text, c_text, i_text;
    ImageView img_first,img_economy,img_business;
    String classType = "Economy";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.activity_cabin__class);
        View inflated = stub.inflate();

        closeBtn.setVisibility(View.VISIBLE);

        closeBtn.setText("Apply");

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TravelPort cabin = new TravelPort();

                cabin.setAdults(a_text.getText().toString());
                cabin.setChild(c_text.getText().toString());
                cabin.setInfants(i_text.getText().toString());
                cabin.setClass_type(classType);

                Intent intent = new Intent();
                Bundle b = new Bundle();

                b.putParcelable("cabinData",cabin);

                intent.putExtras(b);

                setResult(3, intent);
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);


            }
        });

        a_text = (TextView) inflated.findViewById(R.id.a_text);
        c_text = (TextView) inflated.findViewById(R.id.c_text);
        i_text = (TextView) inflated.findViewById(R.id.i_text);

        img_first = (ImageView)inflated.findViewById(R.id.img_first);
        img_economy = (ImageView)inflated.findViewById(R.id.img_economy);
        img_business = (ImageView)inflated.findViewById(R.id.img_business);



    }

    public void ClickRadio(View v){

        if (v.getTag().toString().equals("0")){

            img_first.setImageResource(R.drawable.empty_circle);
            img_business.setImageResource(R.drawable.empty_circle);
            img_economy.setImageResource(R.drawable.fill_circle);
            classType = "Economy";


        }else if(v.getTag().toString().equals("1")){

            img_first.setImageResource(R.drawable.fill_circle);
            img_business.setImageResource(R.drawable.empty_circle);
            img_economy.setImageResource(R.drawable.empty_circle);
            classType = "First";

        }else if(v.getTag().toString().equals("2")){

            img_first.setImageResource(R.drawable.empty_circle);
            img_business.setImageResource(R.drawable.fill_circle);
            img_economy.setImageResource(R.drawable.empty_circle);
            classType = "Business";

        }

    }

    public void PassengerClick(View v) {

        Log.d("hamza",v.getTag().toString());

        if (v.getTag().toString().equals("0")) {

            if (Integer.parseInt(a_text.getText().toString()) < 9) {

                a_text.setText("" + (Integer.parseInt(a_text.getText().toString()) + 1));

            }
        } else if (v.getTag().toString().equals("1")) {
            if (Integer.parseInt(a_text.getText().toString()) > 1) {

                a_text.setText("" + (Integer.parseInt(a_text.getText().toString()) - 1));
            }
        }else if (v.getTag().toString().equals("2")) {
                if (Integer.parseInt(c_text.getText().toString()) < 5) {

                c_text.setText("" + (Integer.parseInt(c_text.getText().toString()) + 1));
            }
        }else if (v.getTag().toString().equals("3")) {
            if (Integer.parseInt(c_text.getText().toString()) > 0) {

                c_text.setText("" + (Integer.parseInt(c_text.getText().toString()) - 1));
            }
        }else if (v.getTag().toString().equals("4")) {
            if (Integer.parseInt(i_text.getText().toString()) < 4) {

                i_text.setText("" + (Integer.parseInt(i_text.getText().toString()) + 1));
            }
        }else if (v.getTag().toString().equals("5")) {
            if (Integer.parseInt(i_text.getText().toString()) > 0) {

                i_text.setText("" + (Integer.parseInt(i_text.getText().toString()) - 1));
            }
        }
    }
}
