package com.bishworup.electroshop.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bishworup.electroshop.Api.UserApi;
import com.bishworup.electroshop.MainActivity;
import com.bishworup.electroshop.Models.LoginClass;
import com.bishworup.electroshop.R;
import com.bishworup.electroshop.Response.NodeResponse;
import com.bishworup.electroshop.Url.Url;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button btnLogin, btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.logEmail);
        password = findViewById(R.id.logPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSingUp);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    public void Login()
    {
        String Email=email.getText().toString();
        String Password=password.getText().toString();

        LoginClass loginClass = new LoginClass(Email,Password);

        UserApi userAPI= Url.getInstance().create(UserApi.class);
        final Call<NodeResponse> loginCall=userAPI.login(loginClass);
        loginCall.enqueue(new Callback<NodeResponse>() {
            @Override
            public void onResponse(Call<NodeResponse> call, Response<NodeResponse> response) {
                if (!response.isSuccessful()){
                    if(response.code()==401)
                    {
                        Toast.makeText(LoginActivity.this, "Password doesn't matches", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(LoginActivity.this, "Code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    try {
                        Response<NodeResponse> loginResponse = loginCall.execute();
                        Url.token += loginResponse.body().getToken();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }

            }
            @Override
            public void onFailure(Call<NodeResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}