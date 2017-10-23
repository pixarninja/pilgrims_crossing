package com.pixarninja.pilgrims_crossing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    SpriteView spriteView;
    LinkedHashMap<String, SpriteController> controllerMap;
    SpriteEntity entity;
    Intent intent;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* initialize stored information object */
        SharedPreferences appInfo = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        /* initialize the render and controller maps */
        controllerMap = new LinkedHashMap<>();

        /* set center view */
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        spriteView = (SpriteView) findViewById(R.id.spriteView);

        /* set score view */
        TextView score = (TextView) findViewById(R.id.score);
        String newText = "Spiders Killed: " + appInfo.getInt("Spiders Killed", 0) + "\nBridge Damage: " + 0;
        score.setText(newText);

        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        int maxRes = width / 2;

        /* background */
        spriteView.setBackgroundResource(R.drawable.background);

        /* initialize player controller */
        entity = new PlayerIdle(getResources(), maxRes, maxRes, width, height, null, "player idle right", "init");
        controllerMap.put("PlayerController", entity.getController());

        /* initialize bridge controllers */
        for(int i = 0; i < 5; i++) {
            if(i == 0) {
                entity = new Bridge(getResources(), width, height, maxRes, maxRes, 0, controllerMap.get("PlayerController").getEntity().getSprite().getBoundingBox().bottom - controllerMap.get("PlayerController").getEntity().getSprite().getSpriteHeight() / 12f, "bridge" + i);
            }
            else {
                entity = new Bridge(getResources(), width, height, maxRes, maxRes, controllerMap.get("Bridge" + (i - 1) + "Controller").getEntity().getSprite().getBoundingBox().right, controllerMap.get("PlayerController").getEntity().getSprite().getBoundingBox().bottom - controllerMap.get("PlayerController").getEntity().getSprite().getSpriteHeight() / 12f, "bridge" + i);
            }
            controllerMap.put("Bridge" + i + "Controller", entity.getController());
        }

        /* initialize fire orb controller */
        entity = new FireOrb(getResources(), width, height, maxRes, maxRes, 0, controllerMap.get("PlayerController").getEntity().getSprite().getBoundingBox().bottom - controllerMap.get("PlayerController").getEntity().getSprite().getSpriteHeight() / 12f, "fire orb");
        controllerMap.put("FireOrbController", entity.getController());

        /* initialize light orb controller */
        entity = new LightOrb(getResources(), width, height, maxRes, maxRes, controllerMap.get("FireOrbController").getEntity().getSprite().getBoundingBox().right, controllerMap.get("PlayerController").getEntity().getSprite().getBoundingBox().bottom - controllerMap.get("PlayerController").getEntity().getSprite().getSpriteHeight() / 12f, "light orb");
        controllerMap.put("LightOrbController", entity.getController());

        /* initialize earth orb controller */
        entity = new EarthOrb(getResources(), width, height, maxRes, maxRes, controllerMap.get("LightOrbController").getEntity().getSprite().getBoundingBox().right, controllerMap.get("PlayerController").getEntity().getSprite().getBoundingBox().bottom - controllerMap.get("PlayerController").getEntity().getSprite().getSpriteHeight() / 12f, "earth orb");
        controllerMap.put("EarthOrbController", entity.getController());

        /* initialize water orb controller */
        entity = new WaterOrb(getResources(), width, height, maxRes, maxRes, controllerMap.get("EarthOrbController").getEntity().getSprite().getBoundingBox().right, controllerMap.get("PlayerController").getEntity().getSprite().getBoundingBox().bottom - controllerMap.get("PlayerController").getEntity().getSprite().getSpriteHeight() / 12f, "water orb");
        controllerMap.put("WaterOrbController", entity.getController());

        /* initialize time orb controller */
        entity = new TimeOrb(getResources(), width, height, maxRes, maxRes, controllerMap.get("WaterOrbController").getEntity().getSprite().getBoundingBox().right, controllerMap.get("PlayerController").getEntity().getSprite().getBoundingBox().bottom - controllerMap.get("PlayerController").getEntity().getSprite().getSpriteHeight() / 12f, "time orb");
        controllerMap.put("TimeOrbController", entity.getController());

        /* refresh player controller so that it shows up on top */
        SpriteController playerController = controllerMap.get("PlayerController");
        controllerMap.remove("PlayerController");
        controllerMap.put("PlayerController", playerController);

        /* player sprint left button */
        entity = new SpriteButton(getResources(), width, height, maxRes, maxRes, R.mipmap.button_sprint_left, R.mipmap.button_sprint_left, R.mipmap.button_sprint_left,
                0, 0, (width / 6), (7.25 * height / 10), 1, 1, 1, 1, 1, 0.12, 0, 0, 1, 1, null, "button sprint left", "init");
        controllerMap.put("SprintLeftButtonController", entity.getController());

        /* player sprint right button */
        entity = new SpriteButton(getResources(), width, height, maxRes, maxRes, R.mipmap.button_sprint_right, R.mipmap.button_sprint_right, R.mipmap.button_sprint_right,
                0, 0, (2 * width / 6), (7.25 * height / 10), 1, 1, 1, 1, 1, 0.12, 0, 0, 1, 1, null, "button sprint right", "init");
        controllerMap.put("SprintRightButtonController", entity.getController());

        /* flow control button
        entity = new SpriteButton(getResources(), width, height, maxRes, maxRes, R.mipmap.button_play, R.mipmap.button_play, R.mipmap.button_pause,
                0, 0, (3 * width / 6), (7.25 * height / 10), 1, 1, 1, 1, 1, 0.12, 0, 0, 1, 1, null, "button flow control", "init");
        controllerMap.put("FlowButtonController", entity.getController());

        /* selector button
        entity = new SpriteButton(getResources(), width, height, maxRes, maxRes, R.mipmap.button_swipe_left, R.mipmap.button_swipe_left, R.mipmap.button_swipe_left,
                0, 0, (5 * width / 6), (7.25 * height / 10), 1, 1, 1, 1, 1, 0.12, 0, 0, 1, 1, null, "button select", "init");
        controllerMap.put("SelectButtonController", entity.getController());

        /* set frame rate for all controllers */
        for(LinkedHashMap.Entry<String,SpriteController> controller : controllerMap.entrySet()) {
            controller.getValue().setFrameRate(35);
        }

        /* initialize the sprite thread and sprite view */
        spriteView.setControllerMap(controllerMap);
        spriteView.setViewWidth(width);
        spriteView.setViewHeight(height);
        spriteView.setResources(getResources());
        spriteView.setMaxRes(maxRes);
        SpriteThread spriteThread = new SpriteThread(spriteView);
        spriteThread.setRunning(true);
        spriteThread.start();
        spriteView.setSpriteThread(spriteThread);

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

        /* initialize stored information object */
        SharedPreferences appInfo = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        /* set center view */
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        spriteView = (SpriteView) findViewById(R.id.spriteView);

        /* set score view */
        TextView score = (TextView) findViewById(R.id.score);
        String newText = "Spiders Killed: " + appInfo.getInt("Spiders Killed", 0) + "\nBridge Damage: " + 0;
        score.setText(newText);

        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        int maxRes = width / 2;

        /* background */
        spriteView.setBackgroundResource(R.drawable.background);

        /* initialize player controller */
        entity = new PlayerIdle(getResources(), maxRes, maxRes, width, height, null, "player idle right", "init");
        controllerMap.put("PlayerController", entity.getController());

        /* initialize bridge controllers */
        for(int i = 0; i < 5; i++) {
            if(i == 0) {
                entity = new Bridge(getResources(), width, height, maxRes, maxRes, 0, controllerMap.get("PlayerController").getEntity().getSprite().getBoundingBox().bottom - controllerMap.get("PlayerController").getEntity().getSprite().getSpriteHeight() / 12f, "bridge" + i);
            }
            else {
                entity = new Bridge(getResources(), width, height, maxRes, maxRes, controllerMap.get("Bridge" + (i - 1) + "Controller").getEntity().getSprite().getBoundingBox().right, controllerMap.get("PlayerController").getEntity().getSprite().getBoundingBox().bottom - controllerMap.get("PlayerController").getEntity().getSprite().getSpriteHeight() / 12f, "bridge" + i);
            }
            controllerMap.put("Bridge" + i + "Controller", entity.getController());
        }

        /* initialize fire orb controller */
        entity = new FireOrb(getResources(), width, height, maxRes, maxRes, 0, controllerMap.get("PlayerController").getEntity().getSprite().getBoundingBox().bottom - controllerMap.get("PlayerController").getEntity().getSprite().getSpriteHeight() / 12f, "fire orb");
        controllerMap.put("FireOrbController", entity.getController());

        /* initialize light orb controller */
        entity = new LightOrb(getResources(), width, height, maxRes, maxRes, controllerMap.get("FireOrbController").getEntity().getSprite().getBoundingBox().right, controllerMap.get("PlayerController").getEntity().getSprite().getBoundingBox().bottom - controllerMap.get("PlayerController").getEntity().getSprite().getSpriteHeight() / 12f, "light orb");
        controllerMap.put("LightOrbController", entity.getController());

        /* initialize earth orb controller */
        entity = new EarthOrb(getResources(), width, height, maxRes, maxRes, controllerMap.get("LightOrbController").getEntity().getSprite().getBoundingBox().right, controllerMap.get("PlayerController").getEntity().getSprite().getBoundingBox().bottom - controllerMap.get("PlayerController").getEntity().getSprite().getSpriteHeight() / 12f, "earth orb");
        controllerMap.put("EarthOrbController", entity.getController());

        /* initialize water orb controller */
        entity = new WaterOrb(getResources(), width, height, maxRes, maxRes, controllerMap.get("EarthOrbController").getEntity().getSprite().getBoundingBox().right, controllerMap.get("PlayerController").getEntity().getSprite().getBoundingBox().bottom - controllerMap.get("PlayerController").getEntity().getSprite().getSpriteHeight() / 12f, "water orb");
        controllerMap.put("WaterOrbController", entity.getController());

        /* initialize time orb controller */
        entity = new TimeOrb(getResources(), width, height, maxRes, maxRes, controllerMap.get("WaterOrbController").getEntity().getSprite().getBoundingBox().right, controllerMap.get("PlayerController").getEntity().getSprite().getBoundingBox().bottom - controllerMap.get("PlayerController").getEntity().getSprite().getSpriteHeight() / 12f, "time orb");
        controllerMap.put("TimeOrbController", entity.getController());

        /* initialize spider controllers
        for(int i = 0; i < 3; i++) {
            entity = new Spider(getResources(), width, height, maxRes, maxRes, width, controllerMap.get("PlayerController").getEntity().getSprite().getBoundingBox().bottom, "spider idle");
            entity.getController().setXPos((width - entity.getSprite().getSpriteWidth()) * random.nextDouble());
            entity.getController().setYPos(entity.getController().getYPos() - entity.getSprite().getSpriteHeight());
            controllerMap.put("Spider" + i + "Controller", entity.getController());
        }*/

        /* refresh player controller so that it shows up on top */
        SpriteController playerController = controllerMap.get("PlayerController");
        controllerMap.remove("PlayerController");
        controllerMap.put("PlayerController", playerController);

        /* initialize arrow controllers
        for(int i = 0; i < num; i++) {
            entity = new Arrow(getResources(), width, height, maxRes, maxRes, "arrow" + i);
            controllerMap.put("Arrow" + i + "Controller", entity.getController());
        }*/

        /* player sprint left button */
        entity = new SpriteButton(getResources(), width, height, maxRes, maxRes, R.mipmap.button_sprint_left, R.mipmap.button_sprint_left, R.mipmap.button_sprint_left,
                0, 0, (width / 6), (7.25 * height / 10), 1, 1, 1, 1, 1, 0.12, 0, 0, 1, 1, null, "button sprint left", "init");
        controllerMap.put("SprintLeftButtonController", entity.getController());

        /* player sprint right button */
        entity = new SpriteButton(getResources(), width, height, maxRes, maxRes, R.mipmap.button_sprint_right, R.mipmap.button_sprint_right, R.mipmap.button_sprint_right,
                0, 0, (2 * width / 6), (7.25 * height / 10), 1, 1, 1, 1, 1, 0.12, 0, 0, 1, 1, null, "button sprint right", "init");
        controllerMap.put("SprintRightButtonController", entity.getController());

        /* flow control button */
        entity = new SpriteButton(getResources(), width, height, maxRes, maxRes, R.mipmap.button_play, R.mipmap.button_play, R.mipmap.button_pause,
                0, 0, (3 * width / 6), (7.25 * height / 10), 1, 1, 1, 1, 1, 0.12, 0, 0, 1, 1, null, "flow control", "init");
        controllerMap.put("FlowButtonController", entity.getController());

        /* jump button */
        entity = new SpriteButton(getResources(), width, height, maxRes, maxRes, R.mipmap.button_jump, R.mipmap.button_jump, R.mipmap.button_jump,
                0, 0, (5 * width / 6), (7.25 * height / 10), 1, 1, 1, 1, 1, 0.12, 0, 0, 1, 1, null, "jump", "init");
        controllerMap.put("JumpButtonController", entity.getController());

        /* set frame rate for all controllers */
        for(LinkedHashMap.Entry<String,SpriteController> controller : controllerMap.entrySet()) {
            controller.getValue().setFrameRate(35);
        }

        /* initialize the sprite thread and sprite view */
        spriteView.setControllerMap(controllerMap);
        spriteView.setViewWidth(width);
        spriteView.setViewHeight(height);
        spriteView.setResources(getResources());
        spriteView.setMaxRes(maxRes);
        SpriteThread spriteThread = new SpriteThread(spriteView);
        spriteThread.setRunning(true);
        spriteThread.start();
        spriteView.setSpriteThread(spriteThread);

    }

}
