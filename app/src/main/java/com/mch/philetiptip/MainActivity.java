package com.mch.philetiptip;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView prozentTextView;
    private TextView ladeSchrittTextView;
    private LadeStateMachine ladeStateMachine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        prozentTextView = findViewById(R.id.prozentTextView);
        ladeSchrittTextView = findViewById(R.id.ladeSchrittTextView);

        ladeStateMachine = new LadeStateMachine();
        ladeStateMachine.initStateMachine();

        /*
        while(ladeStateMachine.isRunning()){
            ladeStateMachine.processStatemachine();
        };
         */

        //Nur Uebergangsweise
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity after the splash screen duration
                Intent intent = new Intent(MainActivity.this, PhileMenue.class);
                startActivity(intent);
                finish();
            }
        }, 3000);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}