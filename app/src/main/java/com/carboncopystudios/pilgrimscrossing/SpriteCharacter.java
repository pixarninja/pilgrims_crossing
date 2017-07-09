package com.carboncopystudios.pilgrimscrossing;

import android.content.Context;
import android.content.res.Resources;

public interface SpriteCharacter {

    abstract Sprite getSprite();

    abstract void initSprites(Resources res);

    public void refreshView(Sprite before, Sprite after);

    abstract void updateView(Sprite sprite);

    public void updateBoundingBox(Sprite sprite);

    abstract void getCurrentFrame(Sprite sprite);

    abstract void onTouchEvent(boolean touched, float xTouchedPos, float yTouchedPos);

}
