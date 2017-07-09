package com.carboncopystudios.pilgrimscrossing;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;

public class Samurai implements SpriteCharacter {

    public volatile boolean reacting;
    public long lastFrameChangeTime;
    public int frameLengthInMilliseconds;
    public SpriteView spriteView;
    private Sprite center;
    private Sprite left;
    private Sprite topLeft;
    private Sprite top;
    private Sprite topRight;
    private Sprite right;
    private Sprite bottomRight;
    private Sprite bottom;
    private Sprite bottomLeft;
    private Sprite idle;
    private Sprite render;


    public Samurai(Resources res, SpriteView spriteView) {
        this.spriteView = spriteView;
        initSprites(res);
    }

    public Sprite getSprite() { return this.render; }

    public void initSprites(Resources res) {

        /* initialize sprites that will be rendered in the scene */
        Bitmap centerImage = BitmapFactory.decodeResource(res, R.mipmap.samurai_idle_norm_noglow);
        center = new Sprite(0, 0, false, centerImage, 0, 0, 4, 2, 8, 1, 14.22, 25.347, 9.667, 2.0, 15.55, 12.78, true, false);
        placeMiddle(center, spriteView);

        Bitmap leftImage = BitmapFactory.decodeResource(res, R.mipmap.samurai_sprinting_norm_noglow);
        left = new Sprite(-60, 0, true, leftImage, 0, 0, 4, 2, 8, 1, 14.22, 25.347, 9.667, 2.0, 15.55, 12.78, true, true);
        placeMiddle(left, spriteView);

        Bitmap topLeftImage = BitmapFactory.decodeResource(res, R.mipmap.samurai_running_norm_noglow);
        topLeft = new Sprite(-30, 0, true, topLeftImage, 0, 0, 4, 2, 8, 1, 14.22, 25.347, 9.667, 2.0, 15.55, 12.78, true, true);
        placeMiddle(topLeft, spriteView);

        Bitmap topImage = BitmapFactory.decodeResource(res, R.mipmap.samurai_idle_norm_noglow);
        top = new Sprite(0, 0, false, topImage, 0, 0, 4, 2, 8, 1, 14.22, 25.347, 9.667, 2.0, 15.55, 12.78, true, false);
        placeMiddle(top, spriteView);

        Bitmap topRightImage = BitmapFactory.decodeResource(res, R.mipmap.samurai_running_norm_noglow);
        topRight = new Sprite(30, 0, true, topRightImage, 0, 0, 4, 2, 8, 1, 14.22, 25.347, 9.667, 2.0, 15.55, 12.78, true, false);
        placeMiddle(topRight, spriteView);

        Bitmap rightImage = BitmapFactory.decodeResource(res, R.mipmap.samurai_sprinting_norm_noglow);
        right = new Sprite(60, 0, true, rightImage, 0, 0, 4, 2, 8, 1, 14.22, 25.347, 9.667, 2.0, 15.55, 12.78, true, false);
        placeMiddle(right, spriteView);

        Bitmap bottomRightImage = BitmapFactory.decodeResource(res, R.mipmap.samurai_running_norm_noglow);
        bottomRight = new Sprite(30, 0, true, bottomRightImage, 0, 0, 4, 2, 8, 1, 14.22, 25.347, 9.667, 2.0, 15.55, 12.78, true, false);
        placeMiddle(bottomRight, spriteView);

        Bitmap bottomImage = BitmapFactory.decodeResource(res, R.mipmap.samurai_idle_norm_noglow);
        bottom = new Sprite(0, 0, false, bottomImage, 0, 0, 4, 2, 8, 1, 14.22, 25.347, 9.667, 2.0, 15.55, 12.78, true, false);
        placeMiddle(bottom, spriteView);

        Bitmap bottomLeftImage = BitmapFactory.decodeResource(res, R.mipmap.samurai_running_norm_noglow);
        bottomLeft = new Sprite(-30, 0, true, bottomLeftImage, 0, 0, 4, 2, 8, 1, 14.22, 25.347, 9.667, 2.0, 15.55, 12.78, true, true);
        placeMiddle(bottomLeft, spriteView);

        Bitmap idleImage = BitmapFactory.decodeResource(res, R.mipmap.samurai_idle_norm_noglow);
        idle = new Sprite(0, 0, false, idleImage, 0, 0, 4, 2, 8, 1, 14.22, 25.347, 9.667, 2.0, 15.55, 12.78, true, false);
        placeMiddle(idle, spriteView);

        refreshView(idle, idle);
        render = idle;
        reacting = false;

    }

    public void refreshView(Sprite before, Sprite after) {

        if(before.getFlipped()) {
            after.setXCurrentFrame(before.getXFrameCount() - 1);
            after.setYCurrentFrame(before.getYFrameCount() - 1);
        }
        else {
            after.setXCurrentFrame(0);
            after.setYCurrentFrame(0);
        }
        after.setFlipped(before.getFlipped());
        after.setCurrentFrame(0);
        after.setXPos(before.getXPos());
        after.setYPos(before.getYPos());
        after.setLoop(before.getLoop());
        updateView(after);

    }

    public void updateView(Sprite sprite) {

        if(sprite.getIsMoving()){
            sprite.setXPos(sprite.getXPos() + sprite.getXDelta());
            sprite.setYPos(sprite.getYPos() + sprite.getYDelta());
        }

        /* set bounding box */
        updateBoundingBox(sprite);

    }

    public void updateBoundingBox(Sprite sprite) {

        float left;
        float top;
        float right;
        float bottom;

        /* find percentage to place from the center outwards */
        if(sprite.getFlipped()) {
            left = (float) (sprite.getLeft() - sprite.getXDimension() / 2) / (float) sprite.getXDimension();
            top = (float) (sprite.getTop() - sprite.getYDimension() / 2) / (float) sprite.getYDimension();
            right = (float) (sprite.getXDimension() / 2 - sprite.getRight()) / (float) sprite.getXDimension();
            bottom = (float) (sprite.getYDimension() / 2 - sprite.getBottom()) / (float) sprite.getYDimension();
        }
        else {
            left = (float) (sprite.getXDimension() / 2 - sprite.getLeft()) / (float) sprite.getXDimension();
            top = (float) (sprite.getYDimension() / 2 - sprite.getTop()) / (float) sprite.getYDimension();
            right = (float) (sprite.getRight() - sprite.getXDimension() / 2) / (float) sprite.getXDimension();
            bottom = (float) (sprite.getBottom() - sprite.getYDimension() / 2) / (float) sprite.getYDimension();
        }

        /* centerOfBoundingBox = centerOfSprite - centerOfScreen */
        RectF position = sprite.getWhereToDraw();
        sprite.setBoundingBox(new RectF(
                position.centerX() - (position.width() * left),
                position.centerY() - (position.height() * top),
                position.centerX() + (position.width() * right),
                position.centerY() + (position.height() * bottom)
        ));

    }

    private void placeMiddle(Sprite sprite, SpriteView spriteView) {

        sprite.setXPos((spriteView.getWidth() - sprite.getSpriteWidth()) / 2);
        sprite.setYPos((spriteView.getHeight() - sprite.getSpriteHeight()) / 2);

    }

    public void getCurrentFrame(Sprite sprite){

        long time  = System.currentTimeMillis();
        if ( time > lastFrameChangeTime + frameLengthInMilliseconds) {

            lastFrameChangeTime = time;

            if(sprite.getFlipped()) {
                sprite.setCurrentFrame(sprite.getCurrentFrame() + 1);
                sprite.setXCurrentFrame(sprite.getXCurrentFrame() - 1);
                if ((sprite.getXCurrentFrame() < 0) || (sprite.getCurrentFrame() >= sprite.getFrameCount())) {
                    sprite.setYCurrentFrame(sprite.getYCurrentFrame() - 1);
                    if ((sprite.getYCurrentFrame() < 0) || (sprite.getCurrentFrame() >= sprite.getFrameCount())) {
                        sprite.setYCurrentFrame(sprite.getYFrameCount() - 1);
                        sprite.setCurrentFrame(0);
                        if(!sprite.getLoop()) {
                            //reacting = false;
                            refreshView(render, idle);
                            render = idle;
                        }
                    }
                    sprite.setXCurrentFrame(sprite.getXFrameCount() - 1);
                }
            }
            else {
                sprite.setCurrentFrame(sprite.getCurrentFrame() + 1);
                sprite.setXCurrentFrame(sprite.getXCurrentFrame() + 1);
                if ((sprite.getXCurrentFrame() >= sprite.getXFrameCount()) || (sprite.getCurrentFrame() >= sprite.getFrameCount())) {
                    sprite.setYCurrentFrame(sprite.getYCurrentFrame() + 1);
                    if ((sprite.getYCurrentFrame() >= sprite.getYFrameCount()) || (sprite.getCurrentFrame() >= sprite.getFrameCount())) {
                        sprite.setYCurrentFrame(0);
                        sprite.setCurrentFrame(0);
                        if (!sprite.getLoop()) {
                            //reacting = false;
                            refreshView(render, idle);
                            render = idle;
                        }
                    }
                    sprite.setXCurrentFrame(0);
                }
            }

        }

        /* update the next frame from the spritesheet that will be drawn */
        Rect rect = new Rect();
        rect.left = sprite.getXCurrentFrame() * sprite.getFrameWidth();
        rect.right = rect.left + sprite.getFrameWidth();
        rect.top = sprite.getYCurrentFrame() * sprite.getFrameHeight();
        rect.bottom = rect.top + sprite.getFrameHeight();
        sprite.setFrameToDraw(rect);

    }

    public void onTouchEvent(boolean touched, float xTouchedPos, float yTouchedPos) {
            if (render != null) {
                RectF boundingBox = render.getBoundingBox();
                if (xTouchedPos >= boundingBox.left && xTouchedPos <= boundingBox.right) {
                    /* center of the screen */
                    if (yTouchedPos >= boundingBox.top && yTouchedPos <= boundingBox.bottom) {
                        refreshView(render, center);
                        render = center;
                    }
                    /* top of the screen */
                    else if (yTouchedPos < boundingBox.top) {
                        refreshView(render, top);
                        render = top;
                    }
                    /* bottom of the screen */
                    else if (yTouchedPos > boundingBox.bottom) {
                        refreshView(render, bottom);
                        render = bottom;
                    }
                } else if (xTouchedPos < boundingBox.left) {
                    /* left side of the screen */
                    if (yTouchedPos >= boundingBox.top && yTouchedPos <= boundingBox.bottom) {
                        refreshView(render, left);
                        render = left;
                    }
                        /* top left side of the screen */
                    else if (yTouchedPos < boundingBox.top) {
                        refreshView(render, topLeft);
                        render = topLeft;
                    }
                        /* bottom left side of the screen */
                    else if (yTouchedPos > boundingBox.bottom) {
                        refreshView(render, bottomLeft);
                        render = bottomLeft;
                    }
                    /* keep the current faced direction */
                    if (!render.getFlipped()) {
                        flipSprite(render);
                        render.setFlipped(true);
                    }
                }
                /* right side of the screen */
                else if (xTouchedPos > boundingBox.right) {
                    /* right side of the screen */
                    if (yTouchedPos >= boundingBox.top && yTouchedPos <= boundingBox.bottom) {
                        refreshView(render, right);
                        render = right;
                    }
                        /* top right side of the screen */
                    else if (yTouchedPos < boundingBox.top) {
                        refreshView(render, topRight);
                        render = topRight;
                    }
                        /* bottom right side of the screen */
                    else if (yTouchedPos > boundingBox.bottom) {
                        refreshView(render, bottomRight);
                        render = bottomRight;
                    }
                    /* keep the current faced direction */
                    if (render.getFlipped()) {
                        flipSprite(render);
                        render.setFlipped(false);
                    }
                }
            }
    }

    private void flipSprite(Sprite sprite) {
        Bitmap bitmap = sprite.getSpriteSheet();
        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1, bitmap.getWidth() / 2f, bitmap.getHeight() / 2f);
        sprite.setSpriteSheet(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true));
        sprite.setFlipped(true);

    }

}
