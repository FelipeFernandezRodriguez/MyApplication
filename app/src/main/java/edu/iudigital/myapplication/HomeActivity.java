package edu.iudigital.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;

import edu.iudigital.myapplication.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private EditText changeName;
    private EditText changeLastName;
    private EditText changeEmail;
    private Button buttonUpdate;
    private Button buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String email = getIntent().getStringExtra("user_email");

        List<userModel> listUser = userModel.find(userModel.class, "email = ?", email);
        userModel user = listUser.get(0);

        TextView nameTextView = findViewById(R.id.txtChangeName);
        nameTextView.setText(user.getName());

        TextView lastNameTextView = findViewById(R.id.txtChangeLastName);
        lastNameTextView.setText(user.getLastName());

        TextView emailTextView = findViewById(R.id.txtChangeEmail);
        emailTextView.setText(user.getEmail());

        changeName = findViewById(R.id.txtChangeName);
        changeLastName = findViewById(R.id.txtChangeLastName);
        changeEmail = findViewById(R.id.txtChangeEmail);
        buttonUpdate = findViewById(R.id.btnUpdate);
        buttonDelete = findViewById(R.id.btnDelete);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = changeName.getText().toString();
                String lastName = changeLastName.getText().toString();
                String email = changeEmail.getText().toString();

                if (name.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "Please fill the fields name, last name and email", Toast.LENGTH_SHORT).show();
                } else {
                    if (!email.equals(user.getEmail())) {
                        if (isEmailUnique(email)) {
                            user.setEmail(email);
                        } else {
                            Toast.makeText(HomeActivity.this, "Email already registered.", Toast.LENGTH_SHORT).show();
                            emailTextView.setText(user.getEmail());
                        }
                    }
                    user.setName(name);
                    user.setLastName(lastName);
                    user.save();
                    nameTextView.setText(user.getName());
                    lastNameTextView.setText(user.getLastName());
                    emailTextView.setText(user.getEmail());
                    Toast.makeText(HomeActivity.this, "Successful Update.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user.delete();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(HomeActivity.this, "User deleted", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public boolean isEmailUnique(String email) {
        List<userModel> users = userModel.find(userModel.class, "email = ?", email);
        return users.isEmpty();
    }

}