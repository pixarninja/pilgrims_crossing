package com.carboncopystudios.pilgrimscrossing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SpriteView extends SurfaceView {

    public SpriteCharacter[] render;
    public volatile boolean touched = false;
    public volatile float xTouchedPos;
    public volatile float yTouchedPos;
    private SpriteThread spriteThread;


    public SpriteView(Context context) {
        super(context);

        initView();

    }

    public SpriteView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView();

    }

    public SpriteView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initView();

    }

    public SpriteCharacter[] getCharacter() { return this.render; }
    public void setCharacter(SpriteCharacter[] render) { this.render = render; }

    private void initView(){

        /* start thread */
        spriteThread = new SpriteThread(this);
        setZOrderOnTop(true);
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        surfaceHolder.addCallback(new SurfaceHolder.Callback(){

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                spriteThread.setRunning(true);
                spriteThread.start();
            }
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {}

        });

    }

    protected void drawSprite() {

        Canvas canvas = getHolder().lockCanvas();

        if(canvas != null){
            synchronized (getHolder()) {
                /* refresh scene */
                canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);

                /* render scene */
                for(SpriteCharacter character : render) {
                    Sprite sprite = character.getSprite();
                    if (character != null) {
                        character.updateView(sprite);
                        RectF rectF = new RectF();
                        rectF.set((int) sprite.getXPos(), (int) sprite.getYPos(), (int) sprite.getXPos() + sprite.getSpriteWidth(), (int) sprite.getYPos() + sprite.getSpriteHeight());
                        sprite.setWhereToDraw(rectF);
                        character.getCurrentFrame(sprite);
                        canvas.drawBitmap(sprite.getSpriteSheet(), sprite.getFrameToDraw(), sprite.getWhereToDraw(), null);
                    }
                }

            }
            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        xTouchedPos = event.getX();
        yTouchedPos = event.getY();

        int action = event.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                //System.out.println("DOWN -- X: " + xTouchedPos + ", Y: " + yTouchedPos);
                touched = true;
                break;
            case MotionEvent.ACTION_MOVE:
                //System.out.println("MOVED -- X: " + xTouchedPos + ", Y: " + yTouchedPos);
                //touched = true;
                touched = false;
                break;
            case MotionEvent.ACTION_UP:
                //System.out.println("LIFT -- X: " + xTouchedPos + ", Y: " + yTouchedPos);
                touched = false;
                break;
            case MotionEvent.ACTION_CANCEL:
                touched = false;
                break;
            case MotionEvent.ACTION_OUTSIDE:
                touched = false;
                break;
            default:
        }
        if (render != null) {
            for(SpriteCharacter character : render) {
                character.onTouchEvent(touched, xTouchedPos, yTouchedPos);
            }
        }

        return true;
    }

    /*public void onResume(){
        isRunning = true;
        spriteThread = new SpriteThread(render[0], this);
        spriteThread.start();
    }

    public void onPause(){
        boolean retry = true;
        isRunning = false;
        while(retry){
            try {
                spriteThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/

}