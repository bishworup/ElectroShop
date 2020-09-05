package com.bishworup.electroshop.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bishworup.electroshop.Api.UserApi;
import com.bishworup.electroshop.Models.User;
import com.bishworup.electroshop.R;
import com.bishworup.electroshop.Response.NodeResponse;
import com.bishworup.electroshop.Url.Url;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    EditText firstname,lastname,phonenumber, email,password, admin;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        phonenumber = findViewById(R.id.phonenumber);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

    }
    private void signUp() {
        String FName=firstname.getText().toString();
        String LName=lastname.getText().toString();
        String Email=email.getText().toString();
        String PhoneNumber=phonenumber.getText().toString();
        String Password=password.getText().toString();


        User users=new User(FName,LName,PhoneNumber,Email,Password);

        UserApi userAPI= Url.getInstance().create(UserApi.class);
        Call<NodeResponse> signUpCall=userAPI.registerUser(users);
        signUpCall.enqueue(new Callback<NodeResponse>() {
            @Override
            public void onResponse(Call<NodeResponse> call, Response<NodeResponse> response) {
                if (!response.isSuccessful()){
                    if(response.code()==401)
                    {
                        Toast.makeText(SignupActivity.this, "Username already exit", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(SignupActivity.this, "Code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    Toast.makeText(SignupActivity.this, "Registered User", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                }

            }
            @Override
            public void onFailure(Call<NodeResponse> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}