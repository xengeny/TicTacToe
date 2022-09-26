package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class AddPlayers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);

        // Обьявляем элементы из activity_add_players
        final EditText playerOne = findViewById(R.id.playerOne);
        final EditText playerTwo = findViewById(R.id.playerTwo);
        final AppCompatButton startBtn = findViewById(R.id.startGameBtn);



        // Звук щелка при кнопке старт
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get Values of players from EditText to a String Variables
                final String playerOneName = playerOne.getText().toString();
                final String playerTwoName = playerTwo.getText().toString();

                // Проверка ввода данных имен
                if (playerOneName.isEmpty() || playerTwoName.isEmpty()) {
                    Toast.makeText(AddPlayers.this, "Please enter Player Names", Toast.LENGTH_SHORT).show();
                } else {


                    Intent intent = new Intent(AddPlayers.this, MainActivity.class);

                    // Указываем имена игроков
                    intent.putExtra("playerOne", playerOneName);
                    intent.putExtra("playerTwo", playerTwoName);

                    // открытие MainActivity.java Activity
                    startActivity(intent);
                }
            }
        });
    }
}