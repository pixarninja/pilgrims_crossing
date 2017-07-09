package com.carboncopystudios.pilgrimscrossing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    SpriteView centerView;
    SpriteCharacter[] render;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* set center view */
        centerView = (SpriteView) findViewById(R.id.centerSpriteView);
        render = new SpriteCharacter[3];
        render[0] = new Samurai(getResources(), centerView);
        centerView.setCharacter(render);

    }

}