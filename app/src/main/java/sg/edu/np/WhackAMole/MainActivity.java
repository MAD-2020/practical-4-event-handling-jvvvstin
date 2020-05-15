package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Whack-A-Mole 1.0!";
    final int[] score = {0};
    final int[] checkAdvance = {0};

    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(TAG, "Finished Pre-Initialisation!");


    }
    @Override
    protected void onStart(){
        super.onStart();
        String scoreString = Integer.toString(score[0]);
        TextView scoreTextView = (TextView) findViewById(R.id.aScoreTextView);
        scoreTextView.setText(scoreString);
        setNewMole();
        Log.v(TAG, "Starting GUI!");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(Button checkButton) {
        /* Checks for hit or miss and if user qualify for advanced page.
            Triggers nextLevelQuery().
         */
        TextView scoreTextView = (TextView) findViewById(R.id.aScoreTextView);
        if (checkButton.getText().equals("*")) {
            score[0]++;
            checkAdvance[0]++;
            Log.v(TAG, "Hit, score added!");
        }
        else {
            score[0]--;
            Log.v(TAG, "Missed, score deducted!");
        }
        setNewMole();

        if (checkAdvance[0] % 10 == 0 && checkAdvance[0] > 0) {
            nextLevelQuery();
        }
        scoreTextView.setText(score[0] + "");
    }

    private void nextLevelQuery(){
        /*
        Builds dialog box here.
        Log.v(TAG, "User accepts!");
        Log.v(TAG, "User decline!");
        Log.v(TAG, "Advance option given to user!");
        belongs here*/
        Log.v(TAG, "Advance option given to user!");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Warning! Insane Whack-A-Mole incoming!");
        builder.setMessage("Would you like to advance to advanced mode?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                Log.v(TAG, "User accepts!");
                nextLevel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                Log.v(TAG, "User decline!");
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void nextLevel(){
        /* Launch advanced page */
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        intent.putExtra("Score", score[0]);
        startActivity(intent);
    }

    private void setNewMole() {
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);
        Button leftButton = (Button) findViewById(R.id.leftButton);
        Button middleButton = (Button) findViewById(R.id.middleButton);
        Button rightButton = (Button) findViewById(R.id.rightButton);
        leftButton.setText("O");
        middleButton.setText("O");
        rightButton.setText("O");
        if (randomLocation == 0) {
            leftButton.setText("*");
        }
        else if (randomLocation == 1) {
            middleButton.setText("*");
        }
        else {
            rightButton.setText("*");
        }
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.leftButton) {
            Log.v(TAG, "Button Left Clicked");
            doCheck((Button) findViewById(R.id.leftButton));
        }
        else if (view.getId() == R.id.middleButton) {
            Log.v(TAG, "Button Middle Clicked");
            doCheck((Button) findViewById(R.id.middleButton));
        }
        else {
            Log.v(TAG, "Button Right Clicked");
            doCheck((Button) findViewById(R.id.rightButton));
        }
    }
}