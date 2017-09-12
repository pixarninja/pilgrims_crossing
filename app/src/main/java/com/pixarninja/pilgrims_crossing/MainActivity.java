package com.pixarninja.pilgrims_crossing;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Handler handler;
    SpriteView spriteView;
    LinkedHashMap<String, SpriteController> controllerMap;
    SpriteEntity entity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* initialize the render and controller maps */
        controllerMap = new LinkedHashMap<>();

        /* set center view */
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        spriteView = (SpriteView) findViewById(R.id.spriteView);
        SpriteThread spriteThread = new SpriteThread(spriteView);
        spriteThread.setRunning(true);
        spriteThread.start();
        spriteView.setSpriteThread(spriteThread);

        /* set score view */
        TextView score = (TextView) findViewById(R.id.score);
        score.setText("Arrows Remaining: " + 200);

        int height = (int)(displayMetrics.heightPixels);
        int maxRes = height * 2;
        int width = (int)(displayMetrics.widthPixels);

        /* background */
        spriteView.setBackgroundResource(R.drawable.background);

        /* initialize samurai controller */
        entity = new SamuraiIdle(spriteView, getResources(), 0.65, maxRes / 2, maxRes / 2, width, height, null, "idle right", "init");
        controllerMap.put("SamuraiController", entity.getController());

        /* initialize arrow controllers */
        for(int i = 0; i < 200; i++) {
            Random random = new Random();
            entity = new Arrow(spriteView, getResources(), 0.10, width, height, (int) (maxRes * 0.4), (int) (maxRes * 0.4),
                    0, 0, random.nextDouble() * width, random.nextDouble() * -height, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, null, "init");
            controllerMap.put("Arrow" + i + "Controller", entity.getController());
        }

        /* sprint left button */
        entity = new SpriteButton(spriteView, getResources(), 0.1, width, height, (int)(maxRes * 0.025), (int)(maxRes * 0.025), R.mipmap.button_sprint_left, R.mipmap.button_sprint_left, R.mipmap.button_sprint_left,
                0, 0, (0.5 * width / 6), (8 * height / 10), 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, "loop", null, "init");
        controllerMap.put("SprintLeftButtonController", entity.getController());

        /* run left button */
        entity = new SpriteButton(spriteView, getResources(), 0.1, width, height, (int)(maxRes * 0.025), (int)(maxRes * 0.025), R.mipmap.button_run_left, R.mipmap.button_run_left, R.mipmap.button_run_left,
                0, 0, (1.2 * width / 6), (8 * height / 10), 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, "loop", null, "init");
        controllerMap.put("RunLeftButtonController", entity.getController());

        /* stop button */
        entity = new SpriteButton(spriteView, getResources(), 0.1, width, height, (int)(maxRes * 0.025), (int)(maxRes * 0.025), R.mipmap.button_stop, R.mipmap.button_stop, R.mipmap.button_stop,
                0, 0, (1.9 * width / 6), (8 * height / 10), 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, "loop", null, "init");
        controllerMap.put("StopButtonController", entity.getController());

        /* run right button */
        entity = new SpriteButton(spriteView, getResources(), 0.1, width, height, (int)(maxRes * 0.025), (int)(maxRes * 0.025), R.mipmap.button_run_right, R.mipmap.button_run_right, R.mipmap.button_run_right,
                0, 0, (2.6 * width / 6), (8 * height / 10), 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, "loop", null, "init");
        controllerMap.put("RunRightButtonController", entity.getController());

        /* sprint right button */
        entity = new SpriteButton(spriteView, getResources(), 0.1, width, height, (int)(maxRes * 0.025), (int)(maxRes * 0.025), R.mipmap.button_sprint_right, R.mipmap.button_sprint_right, R.mipmap.button_sprint_right,
                0, 0, (3.3 * width / 6), (8 * height / 10), 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, "loop", null, "init");
        controllerMap.put("SprintRightButtonController", entity.getController());

        /* jump button */
        entity = new SpriteButton(spriteView, getResources(), 0.1, width, height, (int)(maxRes * 0.025), (int)(maxRes * 0.025), R.mipmap.button_jump, R.mipmap.button_jump, R.mipmap.button_jump,
                0, 0, (5.5 * width / 6), (8 * height / 10), 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, "loop", null, "init");
        controllerMap.put("JumpButtonController", entity.getController());

        /* set frame rate for all controllers */
        for(LinkedHashMap.Entry<String,SpriteController> controller : controllerMap.entrySet()) {
            controller.getValue().setFrameRate(35);
        }

        /* initialize the entity for the sprite view */
        spriteView.setControllerMap(controllerMap);

        /* print memory statistics */
        final Runtime runtime = Runtime.getRuntime();
        final long usedMemInMB=(runtime.totalMemory() - runtime.freeMemory()) / 1048576L;
        final long maxHeapSizeInMB=runtime.maxMemory() / 1048576L;
        final long availHeapSizeInMB = maxHeapSizeInMB - usedMemInMB;
        System.out.println("Memory Used: " + usedMemInMB + "MB");
        System.out.println("Max Heap Size: " + maxHeapSizeInMB + "MB");
        System.out.println("Available Heap Size: " + availHeapSizeInMB + "MB");

    }

    @Override
    protected void onResume() {
        super.onResume();
        spriteView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        spriteView.onPause();
    }

    public void restart(View view) {
        ;
    }

}
