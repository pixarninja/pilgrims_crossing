package com.carboncopystudios.pilgrimscrossing;

import android.graphics.Canvas;

public class SpriteThread extends Thread {

    SpriteView spriteView;
    private boolean running = false;

    public SpriteThread(SpriteView view) {
        spriteView = view;
    }


    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        while(running){

            if(spriteView.getCharacter() != null) {
                spriteView.drawSprite();
            }

            try {
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}