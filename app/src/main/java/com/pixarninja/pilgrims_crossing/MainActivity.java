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

    private int num;
    SpriteView spriteView;
    LinkedHashMap<String, SpriteController> controllerMap;
    SpriteEntity entity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        num = 100;

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
        String newText = "Arrows Remaining: " + num + "\nHit Bridge: " + 0;
        score.setText(newText);

        int height = (int)(displayMetrics.heightPixels);
        int maxRes = height * 2;
        int width = (int)(displayMetrics.widthPixels);
        Random random = new Random();

        /* background */
        spriteView.setBackgroundResource(R.drawable.background);

        /* initialize player controller */
        entity = new PlayerIdle(spriteView, getResources(), 0.65, maxRes / 2, maxRes / 2, width, height, null, "idle right", "init");
        controllerMap.put("PlayerController", entity.getController());

        /* initialize bridge controllers */
        for(int i = 0; i < 5; i++) {
            if(i == 0) {
                entity = new Bridge(spriteView, getResources(), width / 5, height, (int) (maxRes * 0.4), (int) (maxRes * 0.4), 0, controllerMap.get("PlayerController").getEntity().getSprite().getBoundingBox().bottom - 20, "bridge" + i);
            }
            else {
                entity = new Bridge(spriteView, getResources(), width / 5, height, (int) (maxRes * 0.4), (int) (maxRes * 0.4), controllerMap.get("Bridge" + (i - 1) + "Controller").getEntity().getSprite().getBoundingBox().right, controllerMap.get("PlayerController").getEntity().getSprite().getBoundingBox().bottom - 20, "bridge" + i);
            }
            controllerMap.put("Bridge" + i + "Controller", entity.getController());
        }

        /* place spider monster */
        entity = new SpriteProp(spriteView, getResources(), 0.1, width, height, maxRes / 2, maxRes / 2, R.mipmap.spritesheet_spider_idle_loop,
                0, 0, random.nextDouble() * width, controllerMap.get("PlayerController").getEntity().getSprite().getBoundingBox().bottom - 210, 1, 1, 1,
                1, 1, 1, 0, 0, 1, 1, "loop", "forwards", null, "spider", "init");
        controllerMap.put("SpiderController", entity.getController());

        SpriteController tmpController = controllerMap.get("PlayerController");
        controllerMap.remove("PlayerController");
        controllerMap.put("PlayerController", tmpController);

        /* initialize arrow controllers */
        for(int i = 0; i < num; i++) {
            entity = new Arrow(spriteView, getResources(), width, height, (int) (maxRes * 0.4), (int) (maxRes * 0.4), "arrow" + i);
            controllerMap.put("Arrow" + i + "Controller", entity.getController());
        }

        /* sprint left button */
        entity = new SpriteButton(spriteView, getResources(), 0.15, width, height, (int)(maxRes * 0.025), (int)(maxRes * 0.025), R.mipmap.button_sprint_left, R.mipmap.button_sprint_left, R.mipmap.button_sprint_left,
                0, 0, (width / 6), (7.25 * height / 10), 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, null, "sprint left", "init");
        controllerMap.put("SprintLeftButtonController", entity.getController());

        /* sprint right button */
        entity = new SpriteButton(spriteView, getResources(), 0.15, width, height, (int)(maxRes * 0.025), (int)(maxRes * 0.025), R.mipmap.button_sprint_right, R.mipmap.button_sprint_right, R.mipmap.button_sprint_right,
                0, 0, (2 * width / 6), (7.25 * height / 10), 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, null, "sprint right", "init");
        controllerMap.put("SprintRightButtonController", entity.getController());

        /* flow control button */
        entity = new SpriteButton(spriteView, getResources(), 0.15, width, height, (int)(maxRes * 0.025), (int)(maxRes * 0.025), R.mipmap.button_play, R.mipmap.button_play, R.mipmap.button_pause,
                0, 0, (3 * width / 6), (7.25 * height / 10), 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, null, "flow control", "init");
        controllerMap.put("FlowButtonController", entity.getController());

        /* jump button */
        entity = new SpriteButton(spriteView, getResources(), 0.15, width, height, (int)(maxRes * 0.025), (int)(maxRes * 0.025), R.mipmap.button_jump, R.mipmap.button_jump, R.mipmap.button_jump,
                0, 0, (5 * width / 6), (7.25 * height / 10), 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, null, "jump", "init");
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

        controllerMap.keySet().removeAll(controllerMap.keySet());
        spriteView.getSpriteThread().setRunning(false);

        /* set center view */
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        /* set score view */
        TextView score = (TextView) findViewById(R.id.score);
        String newText = "Arrows Remaining: " + num + "\nHit Bridge: " + 0;
        score.setText(newText);

        int height = (int)(displayMetrics.heightPixels);
        int maxRes = height * 2;
        int width = (int)(displayMetrics.widthPixels);
        Random random = new Random();

        /* background */
        spriteView.setBackgroundResource(R.drawable.background);

        /* initialize player controller */
        entity = new PlayerIdle(spriteView, getResources(), 0.65, maxRes / 2, maxRes / 2, width, height, null, "idle right", "init");
        controllerMap.put("PlayerController", entity.getController());

        /* initialize bridge controllers */
        for(int i = 0; i < 5; i++) {
            if(i == 0) {
                entity = new Bridge(spriteView, getResources(), width / 5, height, (int) (maxRes * 0.4), (int) (maxRes * 0.4), 0, controllerMap.get("PlayerController").getEntity().getSprite().getBoundingBox().bottom - 20, "bridge" + i);
            }
            else {
                entity = new Bridge(spriteView, getResources(), width / 5, height, (int) (maxRes * 0.4), (int) (maxRes * 0.4), controllerMap.get("Bridge" + (i - 1) + "Controller").getEntity().getSprite().getBoundingBox().right, controllerMap.get("PlayerController").getEntity().getSprite().getBoundingBox().bottom - 20, "bridge" + i);
            }
            controllerMap.put("Bridge" + i + "Controller", entity.getController());
        }

        /* place spider monster */
        entity = new SpriteProp(spriteView, getResources(), 0.1, width, height, maxRes / 2, maxRes / 2, R.mipmap.spritesheet_spider_idle_loop,
                0, 0, random.nextDouble() * width, controllerMap.get("PlayerController").getEntity().getSprite().getBoundingBox().bottom - 210, 1, 1, 1,
                1, 1, 1, 0, 0, 1, 1, "loop", "forwards", null, "spider", "init");
        controllerMap.put("SpiderController", entity.getController());

        SpriteController tmpController = controllerMap.get("PlayerController");
        controllerMap.remove("PlayerController");
        controllerMap.put("PlayerController", tmpController);

        /* initialize arrow controllers */
        for(int i = 0; i < num; i++) {
            entity = new Arrow(spriteView, getResources(), width, height, (int) (maxRes * 0.4), (int) (maxRes * 0.4), "arrow" + i);
            controllerMap.put("Arrow" + i + "Controller", entity.getController());
        }

        /* sprint left button */
        entity = new SpriteButton(spriteView, getResources(), 0.15, width, height, (int)(maxRes * 0.025), (int)(maxRes * 0.025), R.mipmap.button_sprint_left, R.mipmap.button_sprint_left, R.mipmap.button_sprint_left,
                0, 0, (width / 6), (7.25 * height / 10), 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, null, "sprint left", "init");
        controllerMap.put("SprintLeftButtonController", entity.getController());

        /* sprint right button */
        entity = new SpriteButton(spriteView, getResources(), 0.15, width, height, (int)(maxRes * 0.025), (int)(maxRes * 0.025), R.mipmap.button_sprint_right, R.mipmap.button_sprint_right, R.mipmap.button_sprint_right,
                0, 0, (2 * width / 6), (7.25 * height / 10), 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, null, "sprint right", "init");
        controllerMap.put("SprintRightButtonController", entity.getController());

        /* flow control button */
        entity = new SpriteButton(spriteView, getResources(), 0.15, width, height, (int)(maxRes * 0.025), (int)(maxRes * 0.025), R.mipmap.button_play, R.mipmap.button_play, R.mipmap.button_pause,
                0, 0, (3 * width / 6), (7.25 * height / 10), 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, null, "flow control", "init");
        controllerMap.put("FlowButtonController", entity.getController());

        /* jump button */
        entity = new SpriteButton(spriteView, getResources(), 0.15, width, height, (int)(maxRes * 0.025), (int)(maxRes * 0.025), R.mipmap.button_jump, R.mipmap.button_jump, R.mipmap.button_jump,
                0, 0, (5 * width / 6), (7.25 * height / 10), 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, null, "jump", "init");
        controllerMap.put("JumpButtonController", entity.getController());

        /* set frame rate for all controllers */
        for(LinkedHashMap.Entry<String,SpriteController> controller : controllerMap.entrySet()) {
            controller.getValue().setFrameRate(35);
        }

        SpriteThread spriteThread = new SpriteThread(spriteView);
        spriteThread.setRunning(true);
        spriteThread.start();
        spriteView.setSpriteThread(spriteThread);
        /* initialize the entity for the sprite view */
        spriteView.setControllerMap(controllerMap);

    }

}
