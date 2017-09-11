package com.pixarninja.pilgrims_crossing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ConcurrentModificationException;
import java.util.LinkedHashMap;

public class SpriteView extends SurfaceView {

    public LinkedHashMap<String, SpriteController> controllerMap;
    public volatile boolean move = false;
    public volatile boolean jump = false;
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

    public LinkedHashMap<String, SpriteController> getControllerMap() { return this.controllerMap; }
    public void setControllerMap(LinkedHashMap<String, SpriteController> controllerMap) { this.controllerMap = controllerMap; }

    public SpriteThread getSpriteThread() { return this.spriteThread; }
    public void setSpriteThread(SpriteThread spriteThread) { this.spriteThread = spriteThread; }

    public int getFrameRate() {
        if(controllerMap != null) {
            for (LinkedHashMap.Entry<String, SpriteController> entry : controllerMap.entrySet()) {
                return entry.getValue().getFrameRate();
            }
        }
        return 35;
    }

    private void initView(){

        /* start thread */
        spriteThread = new SpriteThread(this);
        setZOrderOnTop(true);
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        surfaceHolder.addCallback(new SurfaceHolder.Callback(){

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                /*if (!spriteThread.isAlive()) {
                    spriteThread.start();
                }
                spriteThread.setRunning(true);*/
            }
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                spriteThread.setRunning(false);
                while (retry) {
                    try {
                        spriteThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                        ;
                    }
                }
            }

        });

    }

    public void onResume(){
        spriteThread = new SpriteThread(this);
        spriteThread.setRunning(true);
        spriteThread.start();
    }

    public void onPause(){
        spriteThread.setRunning(false);
        boolean retry = true;
        while(retry){
            try {
                spriteThread.join();
                spriteThread.setRunning(true);
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void drawSprite() {

        Canvas canvas;

        try {
            canvas = getHolder().lockCanvas();
        } catch(IllegalStateException e) {
            return;
        } catch(IllegalArgumentException e) {
            return;
        }

        if(canvas != null && spriteThread.getRunning()){
            synchronized (getHolder()) {
                /* refresh scene */
                canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);

                /* render scene */
                if(controllerMap != null) {
                    try {

                        spriteThread.setRunning(false);

                        /* render all entities to the screen */
                        for (LinkedHashMap.Entry<String, SpriteController> entry : controllerMap.entrySet()) {
                            SpriteController controller = entry.getValue();

                            /* implement jumping */
                            if (entry.getKey().equals("SamuraiController")) {
                                if (entry.getValue().getYDelta() > 0) {
                                    if (entry.getValue().getYDelta() < 30) {
                                        if (entry.getValue().getYDelta() < 20) {
                                            if (entry.getValue().getYDelta() < 10) {
                                                if (entry.getValue().getYDelta() >= 0) {
                                                    entry.getValue().setYDelta(entry.getValue().getYDelta() - 3);
                                                }
                                            } else {
                                                entry.getValue().setYDelta(entry.getValue().getYDelta() - 4);
                                            }
                                        } else {
                                            entry.getValue().setYDelta(entry.getValue().getYDelta() - 6);
                                        }
                                    } else {
                                        entry.getValue().setYDelta(entry.getValue().getYDelta() - 10);
                                    }
                                } else if (entry.getValue().getYDelta() < 0) {
                                    if (entry.getValue().getYDelta() > -30) {
                                        if (entry.getValue().getYDelta() > -20) {
                                            if (entry.getValue().getYDelta() > -10) {
                                                if (entry.getValue().getYDelta() <= 0) {
                                                    entry.getValue().setYDelta(entry.getValue().getYDelta() + 3);
                                                }
                                            } else {
                                                entry.getValue().setYDelta(entry.getValue().getYDelta() + 4);
                                            }
                                        } else {
                                            entry.getValue().setYDelta(entry.getValue().getYDelta() + 6);
                                        }
                                    } else {
                                        entry.getValue().setYDelta(entry.getValue().getYDelta() + 10);
                                    }
                                }
                                controllerMap.put("SamuraiController", entry.getValue());
                            }

                            SpriteEntity entity = controller.getEntity();
                            entity.updateView();

                            Sprite sprite = entity.getSprite();
                            if (sprite.getSpriteSheet() == null || sprite.getFrameToDraw() == null || sprite.getWhereToDraw() == null) {
                                //sprite.printSprite();
                                //System.exit(1);
                            } else {
                                /* for debug of bounding boxes
                                Paint paint = new Paint();
                                paint.setStyle(Paint.Style.STROKE);
                                paint.setColor(Color.rgb(255, 255, 255));
                                paint.setStrokeWidth(3);
                                canvas.drawRect(sprite.getBoundingBox(), paint);*/
                                canvas.drawBitmap(sprite.getSpriteSheet(), sprite.getFrameToDraw(), sprite.getWhereToDraw(), null);
                            }

                        }

                        /* remove any dead controllers */
                        for (LinkedHashMap.Entry<String, SpriteController> entry : controllerMap.entrySet()) {
                            if (!entry.getValue().getAlive()) {
                                controllerMap.remove(entry.getKey());
                            }
                        }
                        /* check for collisions and refresh the entity if necessary */
                        LinkedHashMap<String, SpriteController> additionMap = new LinkedHashMap<>();
                        for (LinkedHashMap.Entry<String, SpriteController> entry : controllerMap.entrySet()) {
                            if (entry.getValue().getEntity() != null && !entry.getValue().getReacting()) {
                                LinkedHashMap<String, SpriteController> map = entry.getValue().getEntity().onCollisionEvent(entry, controllerMap);
                                for(LinkedHashMap.Entry<String, SpriteController> addition : map.entrySet()) {
                                    additionMap.put(addition.getKey(), addition.getValue());
                                }
                            }
                        }
                        /* add any entities to the scene that need to be added */
                        for(LinkedHashMap.Entry<String, SpriteController> addition : additionMap.entrySet()) {
                            controllerMap.put(addition.getKey(), addition.getValue());
                        }

                        spriteThread = new SpriteThread(this);
                        spriteThread.setRunning(true);
                        spriteThread.start();

                    } catch (ConcurrentModificationException e) {
                        spriteThread = new SpriteThread(this);
                        spriteThread.setRunning(true);
                        spriteThread.start();
                    }
                }
            }
        }
        try {
            getHolder().unlockCanvasAndPost(canvas);
        } catch(IllegalStateException e) {
            return;
        } catch(IllegalArgumentException e) {
            return;
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
                move = true;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                jump = true;
                System.out.println("JUMP!!!");
                break;
            case MotionEvent.ACTION_MOVE:
                //System.out.println("MOVED -- X: " + xTouchedPos + ", Y: " + yTouchedPos);
                move = true;
                break;
            case MotionEvent.ACTION_UP:
                //System.out.println("LIFT -- X: " + xTouchedPos + ", Y: " + yTouchedPos);
                move = false;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                jump = false;
                break;
            case MotionEvent.ACTION_CANCEL:
                move = false;
                break;
            case MotionEvent.ACTION_OUTSIDE:
                move = false;
                break;
            default:
        }
        if (controllerMap != null) {
            try {
                /* call the on touch events for all entities */
                for (LinkedHashMap.Entry<String, SpriteController> entry : controllerMap.entrySet()) {
                    if (entry.getValue().getEntity() != null  && !entry.getValue().getReacting()) {
                        entry.getValue().getEntity().onTouchEvent(this, entry, controllerMap, move, jump, xTouchedPos, yTouchedPos);
                    }
                }
                /* for debugging purposes
                for (LinkedHashMap.Entry<String, SpriteController> entry : controllerMap.entrySet()) {
                    if(entry.getValue().getEntity() != null) {
                        entry.getValue().printController();
                    }
                }*/
            } catch (ConcurrentModificationException e) {
                ;
            }
        }

        return true;
    }

}