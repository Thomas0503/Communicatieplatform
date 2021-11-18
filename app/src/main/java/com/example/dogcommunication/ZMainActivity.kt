package tenmb.kotlinandroid
//package com.example.dogcommunication


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
    }
}

//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle

//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        LoginButton(Intent(this,naarHomepage::class.java))


//    private lateinit var binding: ActivityMainBinding

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)

//        binding.LoginButton.setOnClickListener {
//        binding.LoginButton.text = "Hallo"
        // binding.editTextTextEmailAddress.text = ""

//        setSupportActionBar(toolbar)
//        binding.LoginButton.setOnClickListener{
//            startActivity(Intent(this,binding.naarHomepage::class.java))
//        }
//    }




    }
}
