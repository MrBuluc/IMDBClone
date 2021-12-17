package com.hakkicanbuluc.imdbclone.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hakkicanbuluc.imdbclone.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ActivityMainBinding binding;
    String elma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(MainActivity.this, HomePage.class);
            startActivity(intent);
            finish();
        }
    }

    public void login(View view) {
        String email = binding.loginEmail.getText().toString();
        String password = binding.loginPassword.getText().toString();

        if (email.equals("") || password.equals("")) {
            Toast.makeText(this, "Enter email and password", Toast.LENGTH_LONG).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
                Intent intent = new Intent(MainActivity.this, HomePage.class);
                startActivity(intent);
                finish();
            }).addOnFailureListener(e -> Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show());
        }
    }

    public void register(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}