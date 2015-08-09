package com.example.ryananderson.soundsurfer;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


// This shall be the main menu
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide(); //Hide action bar

        //TEMPORARY!
        //Immediately goes to GameActivity
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);

    }

}
