package com.example.volley_crud_arsltech;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Create extends AppCompatActivity {

    EditText name_edtxt, email_edtxt, password_edtxt;
    Button  submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        name_edtxt= findViewById(R.id.name_id);
        email_edtxt= findViewById(R.id.email_id);
        password_edtxt= findViewById(R.id.password_id);

        submit_btn= findViewById(R.id.submit_id);


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertdata();
            }
        });

    }

    private void insertdata() {

        final String name= name_edtxt.getText().toString().trim();
        final String email= email_edtxt.getText().toString().trim();
        final String password= password_edtxt.getText().toString().trim();

        final ProgressDialog progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        if (name.isEmpty() && email.isEmpty() && password.isEmpty()){
            Toast.makeText(Create.this,"Fields cannot be empty", Toast.LENGTH_LONG).show();
        }

        else if (name.isEmpty()){
            name_edtxt.setError("Name is required");
        }
        else if (email.isEmpty()){
            email_edtxt.setError("Email is required");
        }
        else if (password.isEmpty()){
            password_edtxt.setError("Password is required");
        }

        else {

            progressDialog.show();
            StringRequest stringRequest= new StringRequest(Request.Method.POST, "https://sayedwahab0502.000webhostapp.com/insert.php",

                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                                progressDialog.dismiss();
                                startActivity(new Intent(Create.this, MainActivity.class));
                                Toast.makeText(Create.this,"Registered Successfully", Toast.LENGTH_LONG).show();

                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            progressDialog.dismiss();
                            Toast.makeText(Create.this,error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
            {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("name",name);
                    params.put("email",email);
                    params.put("password",password);

                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Create.this);
            requestQueue.add(stringRequest);

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
