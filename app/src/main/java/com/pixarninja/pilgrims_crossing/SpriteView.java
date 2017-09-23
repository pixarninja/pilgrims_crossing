package com.pixarninja.pilgrims_crossing;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import java.util.ConcurrentModificationException;
import java.util.LinkedHashMap;

public class SpriteView extends SurfaceView {

    public LinkedHashMap<String, SpriteController> controllerMap;
    public volatile boolean poke = false;
    public volatile boolean move = false;
    public volatile boolean jump = false;
    public volatile float xTouchedPos;
    public volatile float yTouchedPos;
    private SpriteThread spriteThread;
    private Context context;


    public SpriteView(Context context) {
        super(context);
        this.context = context;

        initView();

    }

    public SpriteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        initView();

    }

    public SpriteView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;

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
                            SpriteEntity entity = controller.getEntity();

                            /* update the entity if necessary */
                            if(controllerMap.get("FlowButtonController") == null || controllerMap.get("FlowButtonController").getTransition().equals("off")) {
                                entity.updateView();
                            }
                            /* always update the flow control button */
                            else {
                                if(controller.getID() != null && controller.getID().equals("flow control")) {
                                    entity.updateView();
                                }
                            }

                            Sprite sprite = entity.getSprite();

                            if (sprite.getSpriteSheet() != null && sprite.getFrameToDraw() != null && sprite.getWhereToDraw() != null) {

                                    /* for debugging bounding boxes
                                    Paint paint;
                                    paint = new Paint();
                                    paint.setStyle(Paint.Style.STROKE);
                                    paint.setColor(Color.rgb(255, 255, 255));
                                    paint.setStrokeWidth(3);
                                    canvas.drawRect(sprite.getBoundingBox(), paint);
                                    if(entry.getKey().equals("PlayerController")) {
                                        float left = sprite.getWhereToDraw().left;
                                        float top = sprite.getWhereToDraw().top;
                                        float right = sprite.getWhereToDraw().right;
                                        float bottom = sprite.getWhereToDraw().bottom;
                                        float width = right - left;
                                        float height = bottom - top;
                                        RectF entryLeft = new RectF(left, top + height / 3f, left + width / 3f, top + 2 * height / 3f);
                                        canvas.drawRect(entryLeft, paint);
                                        RectF entryTopLeft = new RectF(left, top, left + width / 3f, top + height / 3f);
                                        canvas.drawRect(entryTopLeft, paint);
                                        RectF entryTop = new RectF(left + width / 3f, top, left + 2 * width / 3f, top + height / 3f);
                                        canvas.drawRect(entryTop, paint);
                                        RectF entryTopRight = new RectF(left + 2 * width / 3f, top, right, top + height / 3f);
                                        canvas.drawRect(entryTopRight, paint);
                                        RectF entryRight = new RectF(left + 2 * width / 3f, top + height / 3f, right, top + 2 * height / 3f);
                                        canvas.drawRect(entryRight, paint);
                                        RectF entryBottomRight = new RectF(left + 2 * width / 3f, top + 2 * height / 3f, right, bottom);
                                        canvas.drawRect(entryBottomRight, paint);
                                        RectF entryBottom = new RectF(left + width / 3f, top + 2 * height / 3f, left + 2 * width / 3f, bottom);
                                        canvas.drawRect(entryBottom, paint);
                                    }*/

                                    /* for debugging flipped spritesheets
                                    Paint paint;
                                    paint = new Paint();
                                    paint.setStyle(Paint.Style.STROKE);
                                    paint.setColor(Color.rgb(255, 255, 255));
                                    paint.setStrokeWidth(3);
                                    if(entry.getKey().equals("PlayerController")) {
                                        Matrix matrix = new Matrix();
                                        matrix.postScale(-1, 1);
                                        matrix.postTranslate(entity.getSprite().getSpriteSheet().getWidth(), 0);
                                        canvas.drawBitmap(entity.getSprite().getSpriteSheet(), matrix, null);

                                        canvas.drawRect(entity.getSprite().getFrameToDraw(), paint);
                                    }*/

                                Paint paint = null;
                                /* color code:
                                   0x00.....00...00.....00
                                   ..alpha..red..green..blue */
                                if(controller.getID().equals("enemy stage1")) {
                                    paint = new Paint();
                                    paint.setColorFilter(new LightingColorFilter(0x00DDFFFF, 0));
                                }
                                else if(controller.getID().equals("enemy stage2")) {
                                    paint = new Paint();
                                    paint.setColorFilter(new LightingColorFilter(0x00998888, 0));
                                }
                                else if(controller.getID().equals("enemy stage3")) {
                                    paint = new Paint();
                                    paint.setColorFilter(new LightingColorFilter(0x00550000, 0));
                                }

                                canvas.drawBitmap(sprite.getSpriteSheet(), sprite.getFrameToDraw(), sprite.getWhereToDraw(), paint);

                            }
                        }

                        /* remove any dead controllers */
                        for (LinkedHashMap.Entry<String, SpriteController> entry : controllerMap.entrySet()) {
                            if (!entry.getValue().getAlive()) {
                                if(entry.getKey().contains("Arrow")) {
                                    Activity activity = (Activity) context;
                                    if(entry.getValue().getID().equals("hit player")) {
                                        activity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                            TextView score = (TextView) ((Activity) context).findViewById(R.id.score);
                                            String text = score.getText().toString();
                                            String[] expression = text.split("\n");
                                            expression[0] = expression[0].replaceAll("\\D+", "");
                                            expression[1] = expression[1].replaceAll("\\D+", "");
                                            int remaining = Integer.parseInt(expression[0]);
                                            int hit = Integer.parseInt(expression[1]);
                                            String newText = "Arrows Remaining: " + (remaining - 1) + "\nHit Bridge: " + hit;
                                            score.setText(newText);
                                            }
                                        });
                                    }
                                    else {
                                        activity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                            TextView score = (TextView) ((Activity) context).findViewById(R.id.score);
                                            String text = score.getText().toString();
                                            String[] expression = text.split("\n");
                                            expression[0] = expression[0].replaceAll("\\D+", "");
                                            expression[1] = expression[1].replaceAll("\\D+", "");
                                            int remaining = Integer.parseInt(expression[0]);
                                            int hit = Integer.parseInt(expression[1]);
                                            String newText = "Arrows Remaining: " + (remaining - 1) + "\nHit Bridge: " + (hit + 1);
                                            score.setText(newText);
                                            }
                                        });
                                    }
                                }
                                controllerMap.remove(entry.getKey());
                            }
                        }
                        /* check for collisions and refresh the entity if necessary */
                        LinkedHashMap<String, SpriteController> map = new LinkedHashMap<>();
                        LinkedHashMap<String, SpriteController> additionMap = new LinkedHashMap<>();
                        for(LinkedHashMap.Entry<String, SpriteController> entry : controllerMap.entrySet()) {
                            if (entry.getValue().getEntity() != null && (entry.getKey().equals("PlayerController") || entry.getKey().contains("Bridge"))) {
                                map = entry.getValue().getEntity().onCollisionEvent(entry, controllerMap);
                            }
                            for(LinkedHashMap.Entry<String, SpriteController> add : map.entrySet()) {
                                additionMap.put(add.getKey(), add.getValue());
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
                poke = true;
                move = true;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                jump = true;
                System.out.println("JUMP!!!");
                break;
            case MotionEvent.ACTION_MOVE:
                //System.out.println("MOVED -- X: " + xTouchedPos + ", Y: " + yTouchedPos);
                poke = false;
                move = true;
                break;
            case MotionEvent.ACTION_UP:
                //System.out.println("LIFT -- X: " + xTouchedPos + ", Y: " + yTouchedPos);
                poke = false;
                move = false;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                jump = false;
                break;
            case MotionEvent.ACTION_CANCEL:
                poke = false;
                move = false;
                break;
            case MotionEvent.ACTION_OUTSIDE:
                poke = false;
                move = false;
                break;
            default:
        }
        if (controllerMap != null) {
            try {
                /* call the on touch events for all entities */
                for (LinkedHashMap.Entry<String, SpriteController> entry : controllerMap.entrySet()) {
                    if (entry.getValue().getEntity() != null  && !entry.getValue().getReacting()) {
                        entry.getValue().getEntity().onTouchEvent(this, entry, controllerMap, poke, move, jump, xTouchedPos, yTouchedPos);
                    }
                }
            } catch (ConcurrentModificationException e) {
                ;
            }
        }

        return true;
    }

}