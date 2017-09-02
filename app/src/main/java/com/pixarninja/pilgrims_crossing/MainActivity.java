package com.pixarninja.pilgrims_crossing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {

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

        int height = (int)(displayMetrics.heightPixels * 0.9);
        int maxRes = height * 2;
        int width = (int)(displayMetrics.widthPixels);

        /* background */
        spriteView.setBackgroundResource(R.drawable.background_red);

        /* dark pattern */
        entity = new DarkPattern(spriteView, getResources(), 3, width, height, (int)(maxRes * 0.4), (int)(maxRes * 0.4),
                -1, 1, 0, -550, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, "loop", null, "init");
        controllerMap.put("DarkPatternController", entity.getController());

        /* light pattern */
        entity = new LightPattern(spriteView, getResources(), 3, width, height, (int)(maxRes * 0.4), (int)(maxRes * 0.4),
                1, -1, -550, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, "loop", null, "init");
        controllerMap.put("LightPatternController", entity.getController());

        /* sprint left button */
        entity = new SpriteButton(spriteView, getResources(), 0.07, width, height, (int)(maxRes * 0.025), (int)(maxRes * 0.025), R.mipmap.button_sprint_left, R.mipmap.button_sprint_left, R.mipmap.button_sprint_left,
                0, 0, (0.5 * width / 6), (8 * height / 10), 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, "loop", null, "init off");
        controllerMap.put("SprintLeftButtonController", entity.getController());

        /* run left button */
        entity = new SpriteButton(spriteView, getResources(), 0.07, width, height, (int)(maxRes * 0.025), (int)(maxRes * 0.025), R.mipmap.button_run_left, R.mipmap.button_run_left, R.mipmap.button_run_left,
                0, 0, (1.25 * width / 6), (8 * height / 10), 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, "loop", null, "init off");
        controllerMap.put("RunLeftButtonController", entity.getController());

        /* run right button */
        entity = new SpriteButton(spriteView, getResources(), 0.07, width, height, (int)(maxRes * 0.025), (int)(maxRes * 0.025), R.mipmap.button_run_right, R.mipmap.button_run_right, R.mipmap.button_run_right,
                0, 0, (2 * width / 6), (8 * height / 10), 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, "loop", null, "init off");
        controllerMap.put("RunRightButtonController", entity.getController());

        /* sprint right button */
        entity = new SpriteButton(spriteView, getResources(), 0.07, width, height, (int)(maxRes * 0.025), (int)(maxRes * 0.025), R.mipmap.button_sprint_right, R.mipmap.button_sprint_right, R.mipmap.button_sprint_right,
                0, 0, (2.75 * width / 6), (8 * height / 10), 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, "loop", null, "init off");
        controllerMap.put("SprintRightButtonController", entity.getController());

        /* jump button */
        entity = new SpriteButton(spriteView, getResources(), 0.07, width, height, (int)(maxRes * 0.025), (int)(maxRes * 0.025), R.mipmap.button_jump, R.mipmap.button_jump, R.mipmap.button_jump,
                0, 0, (5.5 * width / 6), (8 * height / 10), 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, "loop", null, "init off");
        controllerMap.put("JumpButtonController", entity.getController());

        /* initialize box controller */
        entity = new BoxRed(spriteView, getResources(), 0.65, maxRes / 2, maxRes / 2, width, height, null, "init red");
        controllerMap.put("BoxController", entity.getController());

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
        System.out.println("Avaliable Heap Size: " + availHeapSizeInMB + "MB");

    }

}
