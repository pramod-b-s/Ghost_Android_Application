package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.KeyEvent.Callback;


import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.*;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private Random random = new Random();
    private Button reset;
    private Button chalng;
    private TextView text;
    private TextView status;
    private String s;
    private Character c;
    private int ctr=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        reset=(Button)findViewById(R.id.button2);
        chalng=(Button)findViewById(R.id.button);
        text=(TextView)findViewById(R.id.ghostText);
        status=(TextView)findViewById(R.id.gameStatus);
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new SimpleDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        onStart(null);

        reset.setOnClickListener(resettxt);
        chalng.setOnClickListener(chlngtxt);
        status.setText(USER_TURN);
    }

    View.OnClickListener resettxt=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            text.setText("");
            status.setText(USER_TURN);
            ctr=-1;
        }
    };

    View.OnClickListener chlngtxt=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(dictionary.isWord(text.getText().toString())){
                return;
            }
            else{
                text.setText(dictionary.getAnyWordStartingWith(text.getText().toString()));
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        status.setText(USER_TURN);
        //ctr++;
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode >=event.KEYCODE_A&&keyCode<=event.KEYCODE_Z){
            s=text.getText().toString();
            c=(char)event.getUnicodeChar();
            text.setText(s+""+c);
            if(dictionary.isWord(text.getText().toString())){
                status.setText("ANDROID WINS !");
                return true;
            }
            if(dictionary.getAnyWordStartingWith(text.getText().toString())==""){
                status.setText("ANDROID WINS !");
            }
            computerTurn();
        }
        return super.onKeyUp(keyCode, event);
    }

    private void computerTurn() {
        status.setText(COMPUTER_TURN);
        ctr+=2;
        s=text.getText().toString();

        if(dictionary.isWord(s)==true){
            status.setText("ANDROID WINS !");
            return;
        }
        else if(dictionary.getAnyWordStartingWith(s)!=""){
            String tmp=dictionary.getAnyWordStartingWith(s);
            text.setText(s+tmp.charAt(ctr),TextView.BufferType.EDITABLE);
            if(dictionary.isWord(text.getText().toString())==true){
                status.setText("USER WINS !");
                return;
            }
        }
        else if(dictionary.getAnyWordStartingWith(s)==""){
            status.setText("ANDROID WINS !");
            return;
        }
        // Do computer turn stuff then make it the user's turn again
        status.setText(USER_TURN);
    }
}
