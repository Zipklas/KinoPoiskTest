package com.example.kinopoisk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import Database.Dabase;
import Database.User;

public class MainActivity extends AppCompatActivity {
    EditText Login;
    EditText Password;
    Button button;
    Dabase db;
    HashMap<String, String> empass = new HashMap<String, String>();
    public static int id = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login=findViewById(R.id.editTextLogin);
        Password=findViewById(R.id.editTextPassword);
        button=findViewById(R.id.buttonLog);
        db = Room.databaseBuilder(getApplicationContext(),Dabase.class,"Database").build();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<User> usersBd = db.getUserDao().getAllUser();
                if(!usersBd.isEmpty()){
                for (User currentUser : usersBd) {
                    empass.put(currentUser.getLogin(),currentUser.getPassword());
                    Log.i("user" ,""+currentUser.getId() + "   " + currentUser.getLogin() + "  " + currentUser.getPassword() + '\n');
                }
                id = db.getUserDao().getUserId(Login.getText().toString());
            }}
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void LogIn(View view) throws InterruptedException{
        if(empass.containsKey(Login.getText().toString()))
        {

            String pass1 = Password.getText().toString();
            if(Objects.equals(pass1,empass.get(Login.getText().toString())))
            {
                Userid.user_id = id;
                Intent FilmsIntent =new Intent(this,Films_activity.class);
                startActivity(FilmsIntent);
            }
            else
            {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Неверный пароль", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else
        {
            User newUser= new User(Login.getText().toString(), Password.getText().toString());
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    User user = db.getUserDao().getUser(newUser.login);
                    if (user == null) {
                        db.getUserDao().addUser(newUser);
                    }
                };
            });
            thread.start();
            thread.join();
            Userid.user_id = id;
            Intent FilmsIntent =new Intent(this,Films_activity.class);
            startActivity(FilmsIntent);
        }
    }
}