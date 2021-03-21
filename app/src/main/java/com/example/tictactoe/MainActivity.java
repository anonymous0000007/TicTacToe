package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import androidx.gridlayout.widget.GridLayout;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    boolean isGameActive = true;
    byte[] inceptionPositions = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    byte[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
    // 0 - O and 1 - X;
    byte activePlayer = (byte) Math.round(Math.random());
    Button restartButton;
    TextView statusText,stat;
    int totalMatch, winByO, winByX, matchDrawn = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restartButton = findViewById(R.id.restart);
        statusText = findViewById(R.id.status);
        stat = findViewById(R.id.stat);
        statusText.setText(activePlayer == 0 ? "Player O turn" : "Player X turn");
        stat.setText(getResources().getString(R.string.statText,winByO,winByX,matchDrawn,totalMatch));
    }

    // game logic function;
    public void ticTacToe(View view) {
        ImageView currentBox = (ImageView) view;
        byte currentPosition = Byte.parseByte(currentBox.getTag().toString());
        if(isGameActive && inceptionPositions[currentPosition-1] == 2) {
            inceptionPositions[currentPosition-1] = activePlayer;
            switch(activePlayer) {
                case 0: {
                    currentBox.setTranslationX(-1200);
                    currentBox.setImageResource(R.drawable.o);
                    currentBox.setColorFilter(getResources().getColor(R.color.teal_200));
                    currentBox.animate().translationX(0).setDuration(80);
                    activePlayer = 1;
                    break;
                }
                case 1: {
                    currentBox.setTranslationY(-1200);
                    currentBox.setImageResource(R.drawable.x);
                    currentBox.setColorFilter(getResources().getColor(R.color.red));
                    currentBox.animate().translationY(0).setDuration(80);
                    activePlayer = 0;
                    break;
                }
                default: break;
            }
            for (byte[] winningPosition : winningPositions) {
                if (inceptionPositions[winningPosition[0]] != 2 && inceptionPositions[winningPosition[0]] == inceptionPositions[winningPosition[1]] && inceptionPositions[winningPosition[1]] == inceptionPositions[winningPosition[2]]) {
                    ImageView winningLine = (ImageView) findViewById(R.id.win_line);
                    // winning line position reset before set;
                    winningLine.setRotation(0);
                    winningLine.setTranslationX(0);
                    winningLine.setTranslationY(0);
                    winningLine.setScaleX(1f);
                    winningLine.setScaleY(1f);
                    switch (String.valueOf(winningPosition[0])+String.valueOf(winningPosition[1])+String.valueOf(winningPosition[2])) {
                        case "012": {
                            winningLine.setTranslationY(-225f);
                            winningLine.setScaleX(0.95f);
                            break;
                        }
                        case "345": {
                            winningLine.setTranslationY(25f);
                            winningLine.setScaleX(0.95f);
                            break;
                        }
                        case "678": {
                            winningLine.setTranslationY(275f);
                            winningLine.setScaleX(0.95f);
                            break;
                        }
                        case "036": {
                            winningLine.setRotation(90);
                            winningLine.setTranslationX(-280f);
                            winningLine.setScaleY(1.3f);
                            break;
                        }
                        case "147": {
                            winningLine.setRotation(90);
                            winningLine.setTranslationX(-30f);
                            winningLine.setScaleY(1.3f);
                            break;
                        }
                        case "258": {
                            winningLine.setRotation(90);
                            winningLine.setTranslationX(220f);
                            winningLine.setScaleY(1.3f);
                            break;
                        }
                        case "048": {
                            winningLine.setRotation(225);
                            winningLine.setTranslationX(10f);
                            winningLine.setTranslationY(-25f);
                            winningLine.setScaleX(1.3f);
                            break;
                        }
                        default: {
                            winningLine.setRotation(135);
                            winningLine.setTranslationX(-10f);
                            winningLine.setTranslationY(-25f);
                            winningLine.setScaleX(1.3f);
                            break;
                        }
                    }
                    winningLine.setAlpha(1f);
                    statusText.setText(activePlayer == 1 ? "Player O win" : "Player X win");
                    restartButton.setAlpha(1);
                    isGameActive = false;
                    if(activePlayer == 1) winByO++;
                    else winByX++;
                    totalMatch++;
                    stat.setText(getResources().getString(R.string.statText,winByO,winByX,matchDrawn,totalMatch));
                    return;
                }
            }
            if(!Arrays.toString(inceptionPositions).contains("2")) {
                restartButton.setAlpha(1);
                statusText.setText(getResources().getString(R.string.match_drawn));
                matchDrawn++;
                totalMatch++;
                stat.setText(getResources().getString(R.string.statText,winByO,winByX,matchDrawn,totalMatch));
                return;
            }
            statusText.setText(activePlayer == 1 ? "Player X turn" : "Player O turn");
        }
    }

    public void restartGame(View view) {
        ImageView winningLine = (ImageView) findViewById(R.id.win_line);
        winningLine.setAlpha(0f);
        restartButton.setAlpha(0);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.grid_layout);
        for(byte i=0; i<gridLayout.getChildCount(); i++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setImageDrawable(null);
            inceptionPositions[i] = 2;
        }
        isGameActive = true;
        activePlayer = (byte) Math.round(Math.random());
        statusText.setText(activePlayer == 0 ? "Player O turn" : "Player X turn");
    }
}