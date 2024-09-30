package edu.iudigital.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText saveName;
    private EditText saveLastName;
    private EditText saveEmail;
    private EditText savePassword;
    private EditText saveConfirmPassword;
    private Button buttonSave;
    private Button buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        saveName = findViewById(R.id.txtSaveName);
        saveLastName = findViewById(R.id.txtSaveLastName);
        saveEmail = findViewById(R.id.txtSaveEmail);
        savePassword = findViewById(R.id.txtSavePassword);
        saveConfirmPassword = findViewById(R.id.txtSaveConfirmPassword);
        buttonSave = findViewById(R.id.btnSave);
        buttonCancel = findViewById(R.id.btnCancel);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = saveName.getText().toString();
                String lastName = saveLastName.getText().toString();
                String email = saveEmail.getText().toString();
                String password = savePassword.getText().toString();
                String confirmPassword = saveConfirmPassword.getText().toString();

                if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                } else if (isEmailUnique(email)) {
                    if (password.equals(confirmPassword)){
                        userModel user = new userModel(name, lastName, email, password);
                        user.save();
                        Toast.makeText(RegisterActivity.this, "Successful registration", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "The passwords don't match.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Email already registered.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean isEmailUnique(String email) {
        List<userModel> users = userModel.find(userModel.class, "email = ?", email);
        return users.isEmpty();
    }

}