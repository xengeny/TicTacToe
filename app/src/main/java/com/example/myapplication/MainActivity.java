package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Звуковые эффекты
    private MediaPlayer ckr,draw;



    // Инициализирую комбинации, чтобы проверить выигрыш пользователя
    private final List<int[]> combinationsList = new ArrayList<>();

    // каждая позиция в ящике будет занята либо  Player One или Player Two
    private int[] boxPositions = {0, 0, 0, 0, 0, 0, 0, 0, 0};

    // playerTurn = 1 означает, что на очереди Player One соответвственно playerTurn = 2 это Player Two
    private int playerTurn = 1;

    // totalSelectedBoxes - Общее количество выбранных ящиков первым или вторым игроком
    private int totalSelectedBoxes = 1;

    private LinearLayout playerOne, playerTwo;
    private ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9;
    private TextView playerNameOne, playerNameTwo;


    @Override
    //данный метот позволяет нам сохранить данные при сворачивании экрана, чтоб все не стерлось
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализирую виджеты (кубики) из activity_main.xml файла
        playerOne = findViewById(R.id.OneLayout);
        playerTwo = findViewById(R.id.TwoLayout);
        playerNameOne = findViewById(R.id.player1);
        playerNameTwo = findViewById(R.id.player2);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        image6 = findViewById(R.id.image6);
        image7 = findViewById(R.id.image7);
        image8 = findViewById(R.id.image8);
        image9 = findViewById(R.id.image9);

        // Комбинации выйгрышей
        combinationsList.add(new int[]{0, 1, 2});
        combinationsList.add(new int[]{3, 4, 5});
        combinationsList.add(new int[]{6, 7, 8});
        combinationsList.add(new int[]{0, 3, 6});
        combinationsList.add(new int[]{1, 4, 7});
        combinationsList.add(new int[]{2, 5, 8});
        combinationsList.add(new int[]{2, 4, 6});
        combinationsList.add(new int[]{0, 4, 8});

        // Получаем имена из AddPlayers.java
        final String playerOneName = getIntent().getStringExtra("playerOne");
        final String playerTwoName = getIntent().getStringExtra("playerTwo");

        // Установка имен игроков в TextViews
        playerNameOne.setText(playerOneName);
        playerNameTwo.setText(playerTwoName);

        //Тут создаю уже элементы звукового сопровождения указывая путь
        ckr = MediaPlayer.create(this, R.raw.ckr);
        draw = MediaPlayer.create(this, R.raw.draw);


        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // проверяю, не установлен ли этот шаг ни перед первым игроком, ни перед вторым игроком
                if (isBoxSelectable(0)) {


                    performAction((ImageView) v, 0);
                }
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isBoxSelectable(1)) {

                    // Меняю форму для невозможности повторного нажатия
                    performAction((ImageView) v, 1);
                }
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isBoxSelectable(2)) {


                    performAction((ImageView) v, 2);
                }
            }
        });
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isBoxSelectable(3)) {


                    performAction((ImageView) v, 3);
                }
            }
        });
        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isBoxSelectable(4)) {


                    performAction((ImageView) v, 4);
                }
            }
        });
        image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isBoxSelectable(5)) {


                    performAction((ImageView) v, 5);
                }
            }
        });
        image7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isBoxSelectable(6)) {


                    performAction((ImageView) v, 6);
                }
            }
        });
        image8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isBoxSelectable(7)) {


                    performAction((ImageView) v, 7);
                }
            }
        });
        image9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isBoxSelectable(8)) {


                    performAction((ImageView) v, 8);
                }
            }
        });
    }

    /**
     * @param imageView           поле изображения, на которое мы собираемся установить крестик или нулик png в зависимости от хода игрока
     * @param selectedBoxPosition выбранная пользователем позиция поля, чтобы сделать это поле недоступным для выбора снова
     */
    private void performAction(ImageView imageView, int selectedBoxPosition) {

        // занять позицию в клетке игроком, который на очереди (либо 1, либо 2)
        boxPositions[selectedBoxPosition] = playerTurn;

        // Проверка, на очереди первый ли игрок, затем установка перекрестного изображения в ImageView, иначе установка ноль в ImageView.
        if (playerTurn == 1) {
            imageView.setImageResource(R.drawable.cross_icon);

            // checkPlayerWin функия если Player One win
            if (checkPlayerWin()) {

                // показываем диалог победы с сообщением вместе с именем победителя
                final WinDialog winDialog = new WinDialog(MainActivity.this, playerNameOne.getText().toString() + " has won the game");
                winDialog.show();
                soundPlayButton(ckr);

            } else if (totalSelectedBoxes == 9) { // over this game if there is no box left to be select

                // показаем диалоговое окно ничья
                final WinDialog winDialog = new WinDialog(MainActivity.this, "It is a Draw!");
                winDialog.show();
                soundPlayButton(draw);



            } else {

                // Установка для хода второго игрока следующим ходом
                changePlayerTurn(2);
                totalSelectedBoxes++;
            }

        } else {
            imageView.setImageResource(R.drawable.zero_icon);

            // checkPlayerWin проверка выигрыша
            if (checkPlayerWin()) {

                // диалог победы игрока
                final WinDialog winDialog = new WinDialog(MainActivity.this, playerNameTwo.getText().toString() + " has won the game");
                winDialog.setCancelable(false);
                winDialog.show();
                soundPlayButton(ckr);
            } else if (totalSelectedBoxes == 9) { // over this game if there is no box left to be select

                // диалог ничьи
                final WinDialog winDialog = new WinDialog(MainActivity.this, "It is a Draw!");
                winDialog.setCancelable(false);
                winDialog.show();
                soundPlayButton(draw);

            } else {
                // иначе ходит первый игрок
                changePlayerTurn(1);
                totalSelectedBoxes++;
            }

        }
    }

    private void soundPlayButton(MediaPlayer sound) {
        sound.start();
    }

    /**
     * @param currentPlayerTurn ход нового игрока
     */
    private void changePlayerTurn(int currentPlayerTurn) {

        // Выбирает ход следующего игрока (1 или 2)
        playerTurn = currentPlayerTurn;

        //Change Layout Design according to selected player
        if (playerTurn == 1) {
            playerOne.setBackgroundResource(R.drawable.round_back_dark_blue_stroke20); // select Player One
            playerTwo.setBackgroundResource(R.drawable.round_back_dark_blue20); // deSelect Player Two
        } else {
            playerTwo.setBackgroundResource(R.drawable.round_back_dark_blue_stroke20); // select Player Two
            playerOne.setBackgroundResource(R.drawable.round_back_dark_blue20); // deSelect Player One
        }
    }

    /**
     * @param boxPosition текущая выбранная позиция поля
     */
    private boolean isBoxSelectable(int boxPosition) {
        boolean response = false;

        if (boxPositions[boxPosition] == 0) {
            response = true;
        }

        return response;
    }

    private boolean checkPlayerWin() {

        boolean response = false;
        for (int i = 0; i < combinationsList.size(); i++) {

            final int[] combination = combinationsList.get(i);

            if (boxPositions[combination[0]] == playerTurn && boxPositions[combination[1]] == playerTurn && boxPositions[combination[2]] == playerTurn) {
                response = true;
            }
        }
        return response;
    }

    public void startMatchAgain() {

        // сброс позиции ящиков, которые заняли игроки в предыдущем матче
        boxPositions = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};

        // Задача чтоб первый игрок ходил
        playerTurn = 1;

        totalSelectedBoxes = 1;

        // Установка прозрачного изображения
        image1.setImageResource(R.drawable.transparent_back);
        image2.setImageResource(R.drawable.transparent_back);
        image3.setImageResource(R.drawable.transparent_back);
        image4.setImageResource(R.drawable.transparent_back);
        image5.setImageResource(R.drawable.transparent_back);
        image6.setImageResource(R.drawable.transparent_back);
        image7.setImageResource(R.drawable.transparent_back);
        image8.setImageResource(R.drawable.transparent_back);
        image9.setImageResource(R.drawable.transparent_back);

    }
}