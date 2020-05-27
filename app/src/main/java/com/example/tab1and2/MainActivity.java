package com.example.tab1and2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button tab1, tab2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
    }
    public void onBtnClick(View view){
        switch(view.getId()){
            case R.id.tab1:
                Intent intent = new Intent(this, ToDoTask.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tab2:
                Intent intent1 = new Intent (this, WifiNetworks.class);
                startActivity(intent1);
                finish();
                break;
        }
    }
}

