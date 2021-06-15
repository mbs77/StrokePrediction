package com.example.strokeprediction;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    String[] employment = {"Private Sector", "Self-Employed", "Public Sector", "Homemaker", "Never worked"};
    String[] smoking = {"Never Smoked", "Former Smoker", "Smoker"};

    int age = -1;
    float bmi =-1;
    boolean hypertension = false;
    boolean gender = false;
    boolean heartDisease = false;
    boolean everMarried = false;
    boolean isUrban = false;
    int employmentStatus = 0;
    int smokingStatus = 0;

    AlertDialog.Builder builder;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Spinner spin = (Spinner) findViewById(R.id.spinner_job);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,employment);
        spin.setAdapter(aa);

        Spinner spin2 = (Spinner) findViewById(R.id.spinner_smoking);
        ArrayAdapter aa2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,smoking);
        spin2.setAdapter(aa2);

    }

    // Provide Information
    public void sendPrivacy(View view) {
        builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);
        AlertDialog alert = builder.create();
        alert.setTitle("Privacy");
        alert.setMessage("All data submitted to this application is only used to provide you with information." +
                "\nThe Data provided is discarded after use." +
                "\n\nWe currently do not have the necessary data to process non-binary genders." +
                "\nUse any gender you prefer, however the results may be less reliable." +
                "\nInclusion of non-binary genders is a top priority for us.");
        alert.show();

    }
    /*
    public void sendBMI(View view) {
        builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);
        AlertDialog alert = builder.create();
        alert.setTitle("BMI");
        alert.setMessage("[TODO: Add BMI Calculator]");
        alert.show();

    }

    public void sendGender(View view) {
        builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);
        AlertDialog alert = builder.create();
        alert.setTitle("Gender");
        alert.setMessage("We currently do not have the necessary data to process non-binary genders." +
                "\nUse any gender you prefer, however the results may be less reliable." +
                "\nInclusion of non-binary genders is a top priority for us.");
        alert.show();

    } */


    // Set Values
    public void setHeartDisease(View view) {
        Switch swHT = (Switch)findViewById(R.id.swHD);
        TextView tV = (TextView)findViewById(R.id.tvHD);
        if (swHT.isChecked()){
            this.heartDisease = true;
            tV.setText("Yes");
        }
        else {
            this.heartDisease = false;
            tV.setText("No");
        }

    }
    public void setHypertension(View view) {

        Switch swHT = (Switch)findViewById(R.id.swHT);
        TextView tV = (TextView)findViewById(R.id.tvHyperTension);
        if (swHT.isChecked()){
            this.hypertension = true;
            tV.setText("Yes");
        }
        else {
            this.hypertension = false;
            tV.setText("No");
        }

    }
    public void setGender(View view) {
        Switch swHT = (Switch)findViewById(R.id.swGender);
        TextView tV = (TextView)findViewById(R.id.tvGender);
        if (swHT.isChecked()){
            this.gender = true;
            tV.setText("Male");
        }
        else {
            this.gender = false;
            tV.setText("Female");
        }

    }
    public void setResidence(View view) {
        Switch swHT = (Switch)findViewById(R.id.swResidence);
        TextView tV = (TextView)findViewById(R.id.tvResidence);
        if (swHT.isChecked()){
            this.isUrban = true;
            tV.setText("Rural");
        }
        else {
            this.isUrban = false;
            tV.setText("Urban");
        }

    }
    public void setEverMarried(View view) {
        Switch swHT = (Switch)findViewById(R.id.swMarried);
        TextView tV = (TextView)findViewById(R.id.tvMarried);
        if (swHT.isChecked()){
            this.everMarried = true;
            tV.setText("Yes");
        }
        else {
            this.everMarried = false;
            tV.setText("Never");
        }

    }


    public void sendPredication(View view) {
        EditText et  = (EditText)findViewById(R.id.etnAge);
        EditText et1  = (EditText)findViewById(R.id.etnBMI);
        String strBuffer = "";

        // Data Validation
        builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);
        AlertDialog alert = builder.create();
        alert.setTitle("Invalid Data");


        int age = -1;
        try {
            age = Integer.parseInt(et.getText().toString());
        } catch(NumberFormatException nfe) {
            strBuffer = "Invalid Age.\nEnter a valid number.";
            alert.setMessage(strBuffer);
            alert.show();
        }

        if (age > 0 && age < 121){
            this.age = age;
        } else {
            strBuffer = "Invalid Age.\nEnter an age between 1 and 120.";
            alert.setMessage(strBuffer);
            alert.show();
        }

        float bmi = -1;
        try {
            bmi = Float.parseFloat(et1.getText().toString());
        } catch(NumberFormatException nfe) {
            strBuffer = "Invalid BMI.\nEnter a valid number.";
            alert.setMessage(strBuffer);
            alert.show();
        }

        if (bmi >= 10.0 && bmi <= 50.0){
            this.bmi = bmi;
        } else {
            strBuffer = "Invalid BMI.\nEnter a BMI between 10 and 50.";
            alert.setMessage(strBuffer);
            alert.show();
        }

        if (this.age > 0 && this.bmi > 0) {
            Spinner spin = (Spinner) findViewById(R.id.spinner_job);
            Spinner spin2 = (Spinner) findViewById(R.id.spinner_smoking);

            this.employmentStatus = spin.getSelectedItemPosition();
            this.smokingStatus = spin2.getSelectedItemPosition();


        String buffer = this.age+ "," + this.bmi + "," +
                (this.hypertension? 1 : 0) + "," +
                (this.gender? 1 : 0) + "," +
                (this.heartDisease? 1 : 0) + "," +
                (this.everMarried? 1 : 0) + "," +
                (this.isUrban? 1 : 0) + "," +
                this.employmentStatus + "," + this.smokingStatus;

        //JSON
            JSONObject jsoData = new JSONObject();
            try {
                jsoData.put("gender", (this.gender? 1 : 0));
                jsoData.put("age", this.age);
                jsoData.put("hypertension", (this.hypertension? 1 : 0));
                jsoData.put("heart_disease", (this.heartDisease? 1 : 0));
                jsoData.put("ever_married", (this.everMarried? 1 : 0));
                jsoData.put("work_type", this.employmentStatus);
                jsoData.put("Residence_type", (this.isUrban? 1 : 0));
                jsoData.put("bmi", this.bmi);
                jsoData.put("smoking_status", this.smokingStatus);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Context context = getApplicationContext();
            String URL="https://stroke-prediction-316017.wl.r.appspot.com/predict_prob";
            this.Submit(URL, jsoData.toString(), context);


        }





    }

    public void Submit(String URL, String data, Context context) {
        final String savedata= data;

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject objres =new JSONObject(response);
                    String condition = objres.getString("Prediction");

                    String prob = objres.getString("Prob");


                    builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);
                    AlertDialog alert = builder.create();
                    alert.setTitle("Results:");
                    alert.setMessage( condition +"\n" + prob);
                    alert.show();




                } catch (JSONException e) {
                    Toast.makeText(context,"Server Error",Toast.LENGTH_LONG).show();

                }
                //Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                //Log.v("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return savedata == null ? null : savedata.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    //Log.v("Unsupported Encoding while trying to get the bytes", data);
                    return null;
                }
            }

        };
        requestQueue.add(stringRequest);
    }


}
