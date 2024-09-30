package edu.iudigital.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail;
    private EditText loginPassword;
    private Button buttonLogin;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = findViewById(R.id.txtLoginEmail);
        loginPassword = findViewById(R.id.txtLoginPassword);
        buttonLogin = findViewById(R.id.btnLogin);
        buttonRegister = findViewById(R.id.btnRegister);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    List<userModel> listUser = userModel.find(userModel.class, "email = ?", email);

                    if(!listUser.isEmpty()) {
                        userModel user = listUser.get(0);
                        if(user.getPassword().equals(password)){
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.putExtra("user_email", user.getEmail());
                            startActivity(intent);
                            finish();
                            Toast.makeText(LoginActivity.this, "Successful login.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Email or Password incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Email or Password incorrect.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


}