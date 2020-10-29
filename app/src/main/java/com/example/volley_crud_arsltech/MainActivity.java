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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private Button login_btn;
    private EditText email_edt,password_edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton floatingActionButton= findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(MainActivity.this,Create.class);
                startActivity(intent);
            }
        });


        email_edt=(EditText)findViewById(R.id.email_id_login);
        password_edt=(EditText)findViewById(R.id.password_id_login);

        login_btn=(Button)findViewById(R.id.login_id);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startlogin();
            }
        });
    }


    private void startlogin() {

        final String email= email_edt.getText().toString().trim();
        final String password= password_edt.getText().toString().trim();

        final  String url= "https://sayedwahab0502.000webhostapp.com/login.php";

        final ProgressDialog progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        if (email.isEmpty() && password.isEmpty()){
            Toast.makeText(MainActivity.this,"Fields cannot be empty", Toast.LENGTH_LONG).show();
        }

        else if (email.isEmpty()){
            email_edt.setError("Email is required");
        }
        else if (password.isEmpty()){
            password_edt.setError("Password is required");
        }

        else {

            progressDialog.show();
            StringRequest stringRequest= new StringRequest(Request.Method.POST, url,

                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            progressDialog.dismiss();

                            if (response.equalsIgnoreCase("logged in successfully")) {
                                //email_edt.setText("");
                                //password_edt.setText("");
                               Intent intent= new Intent(MainActivity.this,HomePage.class);
                               startActivity(intent);
                                Toast.makeText(MainActivity.this, "You Have Registered Successfully", Toast.LENGTH_LONG).show();
                            }

                            else {
                                Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();

                            }

                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this,error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
            {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("email",email);
                    params.put("password",password);

                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);


        }
    }

}