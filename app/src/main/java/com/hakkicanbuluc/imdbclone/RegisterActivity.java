package com.hakkicanbuluc.imdbclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hakkicanbuluc.imdbclone.databinding.ActivityRegisterBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void signUp(View view) {
        String email = binding.registerEmail.getText().toString();
        String password = binding.registerPassword.getText().toString();
        String name = binding.registerName.getText().toString();
        String lName = binding.registerLname.getText().toString();

        if (email.equals("") || password.equals("") || name.equals("") || lName.equals("")) {
            Toast.makeText(this, "Enter email, password, name and lastname", Toast.LENGTH_LONG).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
                String uid = Objects.requireNonNull(authResult.getUser()).getUid();
                HashMap<String, Object> newUser = new HashMap<>();
                newUser.put("Name", name);
                newUser.put("Lastname", lName);
                newUser.put("Email", email);
                newUser.put("UserID", uid);
                newUser.put("Favorites", new ArrayList<String>());
                createNewUser(uid, newUser);
            }).addOnFailureListener(e -> Toast.makeText(RegisterActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show());
        }
    }

    private void createNewUser(String uid, HashMap<String, Object> newUser) {
        firebaseFirestore.collection("Users").document(uid).set(newUser).addOnSuccessListener(aVoid -> {
            Intent intent = new Intent(RegisterActivity.this, HomePage.class);
            startActivity(intent);
            finish();
        }).addOnFailureListener(e -> Toast.makeText(RegisterActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show());
    }
}