package com.pixarninja.pilgrims_crossing;

import android.graphics.RectF;
import java.util.LinkedHashMap;

public class SpriteCharacter extends SpriteEntity {

    int count;
    int delta;

    public int getCount() { return this.count; }
    public void setCount(int count) { this.count = count; }

    public int getDelta() { return this.delta; }
    public void setDelta(int delta) { this.delta = delta; }

    @Override
    public void onTouchEvent(SpriteView spriteView, LinkedHashMap.Entry<String, SpriteController> entry, LinkedHashMap<String, SpriteController> controllerMap, boolean move, boolean jump, float xTouchedPos, float yTouchedPos) {
        if(move && !controller.getReacting()) {
            RectF boundingBox = render.getBoundingBox();
            if (xTouchedPos >= boundingBox.left && xTouchedPos <= boundingBox.right) {
                /* center of the sprite */
                if (yTouchedPos >= boundingBox.top && yTouchedPos <= boundingBox.bottom) {
                    refreshEntity("center");
                    controller.setReacting(true);
                }
                /* top of the sprite */
                else if (yTouchedPos < boundingBox.top) {
                    refreshEntity("top");
                    controller.setReacting(true);
                }
                /* bottom of the sprite */
                else if (yTouchedPos > boundingBox.bottom) {
                    refreshEntity("bottom");
                    controller.setReacting(true);
                }
            }
            else if (xTouchedPos < boundingBox.left) {
                /* left side of the sprite */
                if (yTouchedPos >= boundingBox.top && yTouchedPos <= boundingBox.bottom) {
                    refreshEntity("left");
                    controller.setReacting(true);
                }
                    /* top left side of the sprite */
                else if (yTouchedPos < boundingBox.top) {
                    refreshEntity("topLeft");
                    controller.setReacting(true);
                }
                    /* bottom right side of the sprite */
                else if (yTouchedPos > boundingBox.bottom) {
                    refreshEntity("bottomLeft");
                    controller.setReacting(true);
                }
            }
            /* right side of the sprite */
            else if (xTouchedPos > boundingBox.right) {
                /* right side of the screen */
                if (yTouchedPos >= boundingBox.top && yTouchedPos <= boundingBox.bottom) {
                    refreshEntity("right");
                    controller.setReacting(true);
                }
                    /* top right side of the sprite */
                else if (yTouchedPos < boundingBox.top) {
                    refreshEntity("topRight");
                    controller.setReacting(true);
                }
                    /* bottom right side of the sprite */
                else if (yTouchedPos > boundingBox.bottom) {
                    refreshEntity("bottomRight");
                    controller.setReacting(true);
                }
            }
        }
    }

    @Override
    public void onCollisionEvent(LinkedHashMap.Entry<String, SpriteController> entry, LinkedHashMap<String, SpriteController> controllerMap) {

        if(!controller.getReacting() && !entry.getValue().getReacting() && (entry.getValue().getEntity().getSprite().getBoundingBox() != null)) {
            RectF entryBox = entry.getValue().getEntity().getSprite().getBoundingBox();
            float left = entryBox.left;
            float top = entryBox.top;
            float right = entryBox.left - entryBox.left;
            float bottom = entryBox.bottom - entryBox.top;
            RectF entryLeft = new RectF(left, bottom / 3f, right / 3f, 2 * bottom / 3f);
            RectF entryTopLeft = new RectF(left, top, right / 3f, bottom / 3f);
            RectF entryTop = new RectF(right / 3f, top, 2 * right / 3f, bottom / 3f);
            RectF entryTopRight = new RectF(2 * right / 3f, top, right, bottom / 3f);
            RectF entryRight = new RectF(2 * right / 3f, bottom / 3f, right, 2 * bottom / 3f);
            RectF entryBottomRight = new RectF(2 * right / 3f, 2 * bottom / 3f, right, bottom);
            RectF entryBottom = new RectF(right / 3f, 2 * bottom / 3f, 2 * right / 3f, bottom);

            for (LinkedHashMap.Entry<String, SpriteController> test : controllerMap.entrySet()) {
                if(!test.getKey().equals(entry.getKey())) {
                    if (test.getValue().getEntity().getSprite().getBoundingBox() != null) {
                        RectF compareBox = test.getValue().getEntity().getSprite().getBoundingBox();
                        /* if the objects intersect, find where they intersect for the entry bounding box */
                        if (entryBox.intersect(compareBox)) {
                            if (entryLeft.intersect(compareBox)) {
                                entry.getValue().getEntity().refreshEntity("inherit left");
                            } else if (entryTop.intersect(compareBox)) {
                                entry.getValue().getEntity().refreshEntity("inherit top");
                            } else if (entryRight.intersect(compareBox)) {
                                entry.getValue().getEntity().refreshEntity("inherit right");
                            } else if (entryBottom.intersect(compareBox)) {
                                entry.getValue().getEntity().refreshEntity("inherit bottom");
                            } else if (entryTopLeft.intersect(compareBox)) {
                                entry.getValue().getEntity().refreshEntity("inherit topLeft");
                            } else if (entryTopRight.intersect(compareBox)) {
                                entry.getValue().getEntity().refreshEntity("inherit topRight");
                            } else if (entryBottomRight.intersect(compareBox)) {
                                entry.getValue().getEntity().refreshEntity("inherit bottomRight");
                            } else {
                                entry.getValue().getEntity().refreshEntity("inherit bottomLeft");
                            }
                            break;
                        }
                    }
                }
            }
        }

    }

}