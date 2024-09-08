package com.mohtashim.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import java.util.Random;

import android.app.AlertDialog;
import android.view.LayoutInflater;

public class MainActivity extends AppCompatActivity {

    AppCompatButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, resetBtn, exitBtn;
    String b1, b2, b3, b4, b5, b6, b7, b8, b9, winnerTxt = ""; // Initialize winnerTxt to avoid null pointer
    int flag, turnCount = 0, xScore = 0, oScore = 0;

    TextView xScoreBox, oScoreBox, turnTxt;
    RelativeLayout relativeParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Randomly select flag for first player
        Random rand = new Random();
        flag = rand.nextInt(2);

        // Initialize buttons and texts
        initialization();

        // Update the UI for the starting player
        updatePlayerTurnUI();

        resetBtn.setOnClickListener(v -> resetGame());
        exitBtn.setOnClickListener(v -> {
            finish(); // Close the current activity
            System.exit(0); // Optionally exit the app
        });
    }

    public void checkOnClick(View view) {
        AppCompatButton button = (AppCompatButton) view;
        turnCount++;

        if (button.getText().toString().equals("")) {
            if (flag == 0) {
                button.setText("X");
                button.setTextColor(getResources().getColor(R.color.red));
                flag = 1; // Switch to player O
            } else {
                button.setText("O");
                button.setTextColor(getResources().getColor(R.color.blue));
                flag = 0; // Switch to player X
            }

            // Update UI based on current player's turn
            updatePlayerTurnUI();

            // Check for winner only if more than 4 moves have been made
            if (turnCount > 4) {
                initializeStrings();
                checkConditions();
            }
        }
    }

    private void initializeStrings() {
        b1 = btn1.getText().toString();
        b2 = btn2.getText().toString();
        b3 = btn3.getText().toString();
        b4 = btn4.getText().toString();
        b5 = btn5.getText().toString();
        b6 = btn6.getText().toString();
        b7 = btn7.getText().toString();
        b8 = btn8.getText().toString();
        b9 = btn9.getText().toString();
    }

    private void initialization() {
        // Initialize buttons
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);

        resetBtn = findViewById(R.id.resetBtn);
        exitBtn = findViewById(R.id.exitBtn);

        // Initialize text views
        xScoreBox = findViewById(R.id.xScoreBox);
        oScoreBox = findViewById(R.id.oScoreBox);
        turnTxt = findViewById(R.id.turnTxt);
        relativeParent = findViewById(R.id.relativeParent);
    }

    @SuppressLint("SetTextI18n")
    private void updatePlayerTurnUI() {
        if (flag == 0) {
            turnTxt.setText("Player X turn");
            turnTxt.setTextColor(getResources().getColor(R.color.red));
            relativeParent.setBackgroundColor(getResources().getColor(R.color.lite_red));
            updateStatusBarColor(getResources().getColor(R.color.lite_red));
            resetBtn.setTextColor(getResources().getColor(R.color.blue));
            exitBtn.setTextColor(getResources().getColor(R.color.red));
        } else {
            turnTxt.setText("Player O turn");
            turnTxt.setTextColor(getResources().getColor(R.color.blue));
            relativeParent.setBackgroundColor(getResources().getColor(R.color.lite_blue));
            updateStatusBarColor(getResources().getColor(R.color.lite_blue));
            resetBtn.setTextColor(getResources().getColor(R.color.red));
            exitBtn.setTextColor(getResources().getColor(R.color.blue));
        }
    }

    private void checkConditions() {
        // Check for row-wise, column-wise, and diagonal wins
        if (checkForWin(b1, b2, b3)) {
            winnerTxt = b1;
        } else if (checkForWin(b4, b5, b6)) {
            winnerTxt = b4;
        } else if (checkForWin(b7, b8, b9)) {
            winnerTxt = b7;
        } else if (checkForWin(b1, b4, b7)) {
            winnerTxt = b1;
        } else if (checkForWin(b2, b5, b8)) {
            winnerTxt = b2;
        } else if (checkForWin(b3, b6, b9)) {
            winnerTxt = b3;
        } else if (checkForWin(b1, b5, b9)) {
            winnerTxt = b1;
        } else if (checkForWin(b3, b5, b7)) {
            winnerTxt = b3;
        } else if (turnCount == 9) {
            winnerTxt = "Tie"; // Handle tie case
            showWinnerDialog("Tie");
            resetGame();
        }

        if (!winnerTxt.equals("")) {
            updateScore();
            showWinnerDialog(winnerTxt);
            resetGame();
        }
    }

    private boolean checkForWin(String b1, String b2, String b3) {
        return b1.equals(b2) && b2.equals(b3) && !b1.equals("");
    }

    private void updateScore() {
        if (winnerTxt.equals("X")) {
            xScore++;
            xScoreBox.setText(String.valueOf(xScore));
        } else if (winnerTxt.equals("O")) {
            oScore++;
            oScoreBox.setText(String.valueOf(oScore));
        }
    }

    private void resetGame() {

        new Handler().postDelayed(() -> {
            clearBtns();
            turnCount = 0;

            // Reset winnerTxt after resetting the board

            switch (winnerTxt) {
                case "X":
                    flag = 0; // X starts the next round

                    break;
                case "O":
                    flag = 1; // O starts the next round

                    break;
                case "Tie":
                    Random rand = new Random();
                    flag = rand.nextInt(2); // Random player starts after a tie

                    break;
            }

            winnerTxt = ""; // Clear winner text after resetting

            // Update the UI for the next player's turn
            updatePlayerTurnUI();
        }, 1000);
    }

    private void clearBtns() {
        btn1.setText("");
        btn2.setText("");
        btn3.setText("");
        btn4.setText("");
        btn5.setText("");
        btn6.setText("");
        btn7.setText("");
        btn8.setText("");
        btn9.setText("");
    }

    // Method to update the status bar color
    @SuppressLint("ObsoleteSdkInt")
    private void updateStatusBarColor(int color) {
        // Check if the device is running Android Lollipop (API 21) or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            // Clear the existing flags
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // Add the FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS to allow custom status bar color
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // Set the new color for the status bar
            window.setStatusBarColor(color);
        }
    }

    @SuppressLint("SetTextI18n")
    private void showWinnerDialog(String winner) {
        // Inflate the custom layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_winner, null);

        // Get the TextView and Button from the custom layout
        TextView winnerMessage = dialogView.findViewById(R.id.winner_message);
        Button okButton = dialogView.findViewById(R.id.ok_button);

        // Set the message based on the winner
        if(winner.equals("X") || winner.equals("O")){
            winnerMessage.setText(winner + " wins!");
        } else {
            winnerMessage.setText(winner + "Play Again");
        }

        // Create and show the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        // Set the OK button to dismiss the dialog
        okButton.setOnClickListener(v -> {
            dialog.dismiss();
            resetGame();
        });

        dialog.show();
    }
}
