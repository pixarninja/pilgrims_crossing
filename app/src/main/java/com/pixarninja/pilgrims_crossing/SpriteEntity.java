package com.pixarninja.pilgrims_crossing;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import java.util.LinkedHashMap;

abstract class SpriteEntity {

    protected Resources res;
    protected double percentOfScreen;
    protected int xRes;
    protected int yRes;
    protected int width;
    protected int height;
    protected int xFrameCount;
    protected int yFrameCount;
    protected int frameCount;
    protected double xDimension;
    protected double yDimension;
    protected double spriteScale;
    protected double left;
    protected double top;
    protected double right;
    protected double bottom;
    protected String method;

    protected String message;
    protected SpriteController controller;
    protected Sprite render;
    protected SpriteView spriteView;

    public Sprite getSprite() { return this.render; }
    public void setSprite(Sprite sprite) { this.render = sprite; }

    public String getMessage() { return this.message; }
    public void setMessage(String message) { this.message = message; }

    public SpriteController getController() { return this.controller; }
    public void setController(SpriteController controller) { this.controller = controller; }

    protected static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        /* decode with inJustDecodeBounds=true to check dimensions */
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        /* calculate inSampleSize */
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        /* decode bitmap with inSampleSize set */
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);

    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        /* raw height and width of image */
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            /* calculate the largest inSampleSize value that is a power of 2 and keeps both
               height and width larger than the requested height and width. */
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;

    }

    public void refreshEntity(String ID) {
        render = new Sprite();
    }

    public void updateView() {

        controller.setXPos(controller.getXPos() + controller.getXDelta());
        controller.setYPos(controller.getYPos() + controller.getYDelta());
        getCurrentFrame();
        updateBoundingBox();

    }

    public void updateBoundingBox() {

        /* update sprite placement on screen */
        render.setWhereToDraw(new RectF((float)controller.getXPos(), (float)controller.getYPos(), (float)controller.getXPos() + render.getSpriteWidth(), (float)controller.getYPos() + render.getSpriteHeight()));

        /* find percentage to place from the center outwards */
        float left = (float) (render.getXDimension() / 2 - render.getLeft()) / (float) render.getXDimension();
        float top = (float) (render.getYDimension() / 2 - render.getTop()) / (float) render.getYDimension();
        float right = (float) (render.getRight() - render.getXDimension() / 2) / (float) render.getXDimension();
        float bottom = (float) (render.getBottom() - render.getYDimension() / 2) / (float) render.getYDimension();

        /* centerOfBoundingBox = centerOfSprite - centerOfScreen */
        RectF position = render.getWhereToDraw();
        if(position == null) {
            return;
        }
        render.setBoundingBox(new RectF(
                position.centerX() - (position.width() * left),
                position.centerY() - (position.height() * top),
                position.centerX() + (position.width() * right),
                position.centerY() + (position.height() * bottom)
        ));

    }

    public void getCurrentFrame(){

        long time  = System.currentTimeMillis();
        if ( time > controller.getLastFrameChangeTime() + controller.getFrameRate()) {

            controller.setLastFrameChangeTime(time);
            render.setCurrentFrame(render.getCurrentFrame() + 1);
            render.setXCurrentFrame(render.getXCurrentFrame() + 1);
            if ((render.getXCurrentFrame() >= render.getXFrameCount()) || (render.getCurrentFrame() >= render.getFrameCount())) {
                render.setYCurrentFrame(render.getYCurrentFrame() + 1);
                if ((render.getYCurrentFrame() >= render.getYFrameCount()) || (render.getCurrentFrame() >= render.getFrameCount())) {
                    render.setYCurrentFrame(0);
                    render.setCurrentFrame(0);
                }
                render.setXCurrentFrame(0);
            }

        }

        /* update the next frame from the spritesheet that will be drawn */
        Rect rect = new Rect();
        rect.left = render.getXCurrentFrame() * render.getFrameWidth();
        rect.right = rect.left + render.getFrameWidth();
        rect.top = render.getYCurrentFrame() * render.getFrameHeight();
        rect.bottom = rect.top + render.getFrameHeight();
        render.setFrameToDraw(rect);

    }

    public String parseID(String ID) {
        String[] expression = ID.split(" ");
        if(expression.length == 2) {
            /* inherit from a certain sprite */
            if(expression[0].equals("inherit")) {
                render = controller.getEntity().getSprite();
                return(expression[1]);
            }
            else {
                return("init");
            }
        }
        else if(expression.length == 1) {
            render = new Sprite();
            return(expression[0]);
        }
        else {
            return null;
        }
    }

    public void onTouchEvent(SpriteView spriteView, LinkedHashMap.Entry<String, SpriteController> entry, LinkedHashMap<String, SpriteController> controllerMap, boolean move, boolean jump, float xTouchedPos, float yTouchedPos) {}

    public LinkedHashMap<String, SpriteController> onCollisionEvent(LinkedHashMap.Entry<String, SpriteController> entry, LinkedHashMap<String, SpriteController> controllerMap) { return new LinkedHashMap<>(); }

}