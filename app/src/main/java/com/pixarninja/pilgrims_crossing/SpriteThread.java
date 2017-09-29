package com.pixarninja.pilgrims_crossing;

import android.content.res.Resources;

import java.util.LinkedHashMap;

public class SpriteThread extends Thread implements Runnable {

    SpriteView spriteView;
    private boolean running = false;
    long time;

    public SpriteThread(SpriteView view) {
        spriteView = view;
    }

    public boolean getRunning() {
        return running;
    }
    public void setRunning(boolean running) { this.running = running; }

    @Override
    public void run() {
        while(running){

            if(spriteView.getControllerMap() != null) {
                spriteView.drawSprite();
            }

            try {
                sleep(spriteView.getFrameRate());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}