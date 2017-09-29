package com.pixarninja.pilgrims_crossing;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
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
import java.util.Random;

public class SpriteView extends SurfaceView {

    public LinkedHashMap<String, SpriteController> controllerMap;
    public volatile boolean poke = false;
    public volatile boolean move = false;
    public volatile boolean jump = false;
    public volatile float xTouchedPos;
    public volatile float yTouchedPos;
    private SpriteThread spriteThread;
    private Resources res;
    private int width;
    private int height;
    private int maxRes;
    private Context context;

    private int spawnTime = 56; //check every 2 seconds
    private int frameCounter = 0;
    private int maxEnemyCount = 5;
    private int enemyCount;
    private SpriteEntity entity;

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

    public Resources getResources() { return res; }
    public void setResources(Resources res) {
        this.res = res;
    }

    public int getViewWidth() { return width; }
    public void setViewWidth(int width) {
        this.width = width;
    }

    public int getViewHeight() { return height;}
    public void setViewHeight(int height) {
        this.height = height;
    }

    public int getMaxRes() { return maxRes; }
    public void setMaxRes(int maxRes) {
        this.maxRes = maxRes;
    }

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

        frameCounter++;
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

                            /* don't draw the sprite if it is not on the screen */
                            if(((controller.getXPos() < -entity.getSprite().getSpriteWidth()) && (controller.getXPos() > width)) || ((controller.getYPos() < -entity.getSprite().getSpriteHeight()) && (controller.getYPos() > height))) {
                                continue;
                            }

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
                                   0x00.....00...00.....00 (black)
                                   ..alpha..red..green..blue */
                                if(controller.getID().equals("spider stage1")) {
                                    paint = new Paint();
                                    paint.setColorFilter(new LightingColorFilter(0x00EEFFFF, 0));
                                }
                                else if(controller.getID().equals("spider stage2")) {
                                    paint = new Paint();
                                    paint.setColorFilter(new LightingColorFilter(0x00998888, 0));
                                }
                                else if(controller.getID().equals("spider stage3")) {
                                    paint = new Paint();
                                    paint.setColorFilter(new LightingColorFilter(0x00663333, 0));
                                }
                                /* fire item drop */
                                else if(controller.getID().equals("item drop fire")) {
                                    paint = new Paint();
                                    paint.setColorFilter(new LightingColorFilter(0x00ff4216, 0));
                                }
                                /* light item drop */
                                else if(controller.getID().equals("item drop light")) {
                                    paint = new Paint();
                                    paint.setColorFilter(new LightingColorFilter(0x00fff84e, 0));
                                }
                                /* earth item drop */
                                else if(controller.getID().equals("item drop earth")) {
                                    paint = new Paint();
                                    paint.setColorFilter(new LightingColorFilter(0x00a5fd4d, 0));
                                }
                                /* water item drop */
                                else if(controller.getID().equals("item drop water")) {
                                    paint = new Paint();
                                    paint.setColorFilter(new LightingColorFilter(0x0070fff3, 0));
                                }
                                /* time item drop */
                                else if(controller.getID().equals("item drop time")) {
                                    paint = new Paint();
                                    paint.setColorFilter(new LightingColorFilter(0x00ec52ff, 0));
                                }
                                /* fire orb */
                                else if(controller.getID().equals("fire orb") && controller.getTransition().equals("crystallize")) {
                                    paint = new Paint();
                                    paint.setColorFilter(new LightingColorFilter(0x00ff4216, 0));
                                }
                                /* light orb */
                                else if(controller.getID().equals("light orb") && controller.getTransition().equals("crystallize")) {
                                    paint = new Paint();
                                    paint.setColorFilter(new LightingColorFilter(0x00fff84e, 0));
                                }
                                /* earth orb */
                                else if(controller.getID().equals("earth orb") && controller.getTransition().equals("crystallize")) {
                                    paint = new Paint();
                                    paint.setColorFilter(new LightingColorFilter(0x00a5fd4d, 0));
                                }
                                /* water orb */
                                else if(controller.getID().equals("water orb") && controller.getTransition().equals("crystallize")) {
                                    paint = new Paint();
                                    paint.setColorFilter(new LightingColorFilter(0x0070fff3, 0));
                                }
                                /* time orb */
                                else if(controller.getID().equals("time orb") && controller.getTransition().equals("crystallize")) {
                                    paint = new Paint();
                                    paint.setColorFilter(new LightingColorFilter(0x00ec52ff, 0));
                                }

                                canvas.drawBitmap(sprite.getSpriteSheet(), sprite.getFrameToDraw(), sprite.getWhereToDraw(), paint);

                            }
                        }

                        /* remove any dead controllers */
                        for (LinkedHashMap.Entry<String, SpriteController> entry : controllerMap.entrySet()) {
                            if (!entry.getValue().getAlive()) {

                                /* update score view */
                                if(entry.getKey().contains("Arrow")) {
                                    Activity activity = (Activity) context;
                                    /* the arrow hit the bridge */
                                    if(entry.getValue().getID().equals("hit bridge")) {
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
                                    /* the arrow hit the player */
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
                                                String newText = "Arrows Remaining: " + (remaining - 1) + "\nHit Bridge: " + hit;
                                                score.setText(newText);
                                            }
                                        });
                                    }

                                    /* remove the arrow */
                                    controllerMap.remove(entry.getKey());
                                    entry.getValue().getEntity().getSprite().getSpriteSheet().recycle();
                                }

                                /* crystallize the correct orb */
                                else if(entry.getKey().contains("ItemDrop")) {
                                    switch(entry.getValue().getID()) {
                                        case "item drop fire":
                                            for(int i = 0; i < 1; i++) {
                                                controllerMap.get("FireOrbController").getEntity().getCurrentFrame();
                                            }
                                            break;
                                        case "item drop light":
                                            for(int i = 0; i < 1; i++) {
                                                controllerMap.get("LightOrbController").getEntity().getCurrentFrame();
                                            }
                                            break;
                                        case "item drop earth":
                                            for(int i = 0; i < 1; i++) {
                                                controllerMap.get("EarthOrbController").getEntity().getCurrentFrame();
                                            }
                                            break;
                                        case "item drop water":
                                            for(int i = 0; i < 1; i++) {
                                                controllerMap.get("WaterOrbController").getEntity().getCurrentFrame();
                                            }
                                            break;
                                        case "item drop time":
                                            for(int i = 0; i < 1; i++) {
                                                controllerMap.get("TimeOrbController").getEntity().getCurrentFrame();
                                            }
                                            break;
                                    }

                                    /* remove the item */
                                    controllerMap.remove(entry.getKey());
                                    entry.getValue().getEntity().getSprite().getSpriteSheet().recycle();
                                }

                                /* regenerate spider
                                else if(entry.getKey().contains("Spider")) {
                                    /* initialize a new spider controller
                                    Random random = new Random();
                                    entry.getValue().setAlive(true);
                                    Spider spider = (Spider) entry.getValue().getEntity();
                                    spider.setHit(0);
                                    entry.getValue().setXPos((width - entry.getValue().getEntity().getSprite().getSpriteWidth()) * random.nextDouble());
                                    entry.getValue().getEntity().refreshEntity("idle");
                                    entry.getValue().setID("spider init");
                                }*/

                                /* re-enable player after swipe */
                                else if(entry.getKey().contains("Swipe")) {
                                    //controllerMap.get("PlayerController").setReacting(false);
                                    controllerMap.remove(entry.getKey());
                                    entry.getValue().getEntity().getSprite().getSpriteSheet().recycle();
                                }

                                else {
                                    /* remove all other dead entities */
                                    controllerMap.remove(entry.getKey());
                                    entry.getValue().getEntity().getSprite().getSpriteSheet().recycle();
                                }
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

        /* check if another spider needs to be spawned */
        if(frameCounter >= spawnTime) {

            frameCounter = 0;
            enemyCount = 0;
            for (LinkedHashMap.Entry<String, SpriteController> entry : controllerMap.entrySet()) {
                if (entry.getKey().contains("Spider")) {
                    enemyCount++;
                }
            }
            if(enemyCount < maxEnemyCount) {
                /* initialize a new spider controller */
                Random random = new Random();
                entity = new Spider(getResources(), width, height, maxRes, maxRes, width, controllerMap.get("PlayerController").getEntity().getSprite().getBoundingBox().bottom, "spider idle");
                entity.getController().setXPos((width - entity.getSprite().getSpriteWidth()) * random.nextDouble());
                entity.getController().setYPos(entity.getController().getYPos() - entity.getSprite().getSpriteHeight());
                int i = 1;
                while(controllerMap.get("Spider" + i + "Controller") != null) {
                    i++;
                }
                controllerMap.put("Spider" + i + "Controller", entity.getController());
            }

            /* refresh player controller so that it shows up on top */
            SpriteController playerController = controllerMap.get("PlayerController");
            controllerMap.remove("PlayerController");
            controllerMap.put("PlayerController", playerController);

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
                    if (entry.getValue().getEntity() != null) {
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