package com.example.chatwithfirebase;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    Button loginButton;
    Button registerButton;

    Button logOut;
    String user;

    //FirebaseFirestore db;
    EditText findOtherUser;
    Button findOtherUserBtn;
    TextView chattingWith;
    Button sendMessage;
    EditText message;

    /*
    db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d(TAG, document.getId() + " => " + document.getData());
                }
            } else {
                Log.w(TAG, "Error getting documents.", task.getException());
            }
        }
    });
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        findAndSetLoginPage(mAuth);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void findAndSetLoginPage(FirebaseAuth mAuth) {

        emailEditText = (EditText)findViewById(R.id.emailEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        loginButton = (Button)findViewById(R.id.loginButton);
        registerButton = (Button)findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Registration Failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            user = email;
                            setContentView(R.layout.activity_chat);
                            findAndSetChat(mAuth);

                        } else {
                            Toast.makeText(MainActivity.this, "Login Failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    void findAndSetChat(FirebaseAuth myAuth) {

        findOtherUser = findViewById(R.id.find_user_text);
        findOtherUserBtn = findViewById(R.id.find_user_btn);
        logOut = findViewById(R.id.log_out);
        chattingWith = findViewById(R.id.chatting_with);
        sendMessage = findViewById(R.id.send_btn);
        message = findViewById(R.id.send_message);

        //db = FirebaseFirestore.getInstance();

        logOut.setOnClickListener(v -> {

            setContentView(R.layout.activity_main);
            findAndSetLoginPage(myAuth);
        });

        findOtherUserBtn.setOnClickListener(v -> {

            String otherUser = findOtherUser.getText().toString();

            if (otherUser.isEmpty() || otherUser.equals(user)) {

                Toast.makeText(MainActivity.this, "Invalid Input. Try again!", Toast.LENGTH_SHORT).show();

            } else {

                /*
                    TO-DO Search through usernames

                    if (theUserIsFound) {

                        Load in the chat

                        chattingWith.setText("Chatting With: " + otherUser);

                    } else {

                        Toast.makeText(MainActivity.this, "User Was Not Found!", Toast.LENGTH_SHORT).show();
                    }

                 */

            }
        });

        sendMessage.setOnClickListener(v -> {

            String message = sendMessage.getText().toString();

            //To-Do Send the message
        });
    }
}