package com.pixarninja.pilgrims_crossing;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import java.util.LinkedHashMap;

public class MainMenuActivity extends AppCompatActivity {

    private SpriteView spriteView;
    private LinkedHashMap<String, SpriteController> controllerMap;
    private SpriteEntity entity;
    public static Context context;

    public static Context getContext() { return context; }
    public void setContext(Context context) { this.context = context; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        setContext(this);

        /* initialize the controller map */
        controllerMap = new LinkedHashMap<>();

        /* set center view */
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        spriteView = (SpriteView) findViewById(R.id.spriteView);

        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        int maxRes = width / 2;

        /* initialize background controller */
        entity = new SpriteProp(getResources(), width, height, maxRes, maxRes, R.mipmap.prop_main_menu_background,
                0, 0, 0, 0, 1, 1, 1, 1, 1, 1,
                0, 0, 1, 1, "loop", "forwards", null, "player idle right", "init");
        controllerMap.put("BackgroundController", entity.getController());

        /* initialize start game button controller */
        entity = new SpriteButton(getResources(), width, height, maxRes, maxRes, R.mipmap.button_start, R.mipmap.button_start, R.mipmap.button_start,
                0, 0, (width / 2), (height / 2), 1, 1, 1, 1, 1, 0.4, 0, 0, 1, 1, null, "button start game", "init");
        controllerMap.put("StartGameButtonController", entity.getController());

        /* initialize settings button controller */
        entity = new SpriteButton(getResources(), width, height, maxRes, maxRes, R.mipmap.button_settings, R.mipmap.button_settings, R.mipmap.button_settings,
                0, 0, (width / 2), controllerMap.get("StartGameButtonController").getYPos() + 250, 1, 1, 1, 1, 1, 0.4, 0, 0, 1, 1, null, "button settings", "init");
        controllerMap.put("SettingsButtonController", entity.getController());

        /* initialize loading screen controller */
        entity = new LoadingScreen(getResources(), width, height, maxRes, maxRes, "init");
        controllerMap.put("ScreenController", entity.getController());

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

}
