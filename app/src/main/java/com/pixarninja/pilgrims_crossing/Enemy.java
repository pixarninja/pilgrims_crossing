package com.pixarninja.pilgrims_crossing;

import android.graphics.RectF;

import java.util.LinkedHashMap;

public class Enemy extends SpriteEntity {

    protected int hit = 0;
    protected int attackCount = 0;
    protected int attackFrameCount = 0;
    protected int chargeFrameCount = 0;

    public int getHit() { return hit; }
    public void setHit(int hit) { this.hit = hit; }

    @Override
    public LinkedHashMap<String, SpriteController> onCollisionEvent(LinkedHashMap.Entry<String, SpriteController> entry, LinkedHashMap<String, SpriteController> controllerMap) {

        LinkedHashMap<String, SpriteController> map = new LinkedHashMap<>();

        if (!entry.getValue().getReacting() && (entry.getValue().getEntity().getSprite().getBoundingBox() != null)) {
            RectF entryBox = entry.getValue().getEntity().getSprite().getBoundingBox();
            for (LinkedHashMap.Entry<String, SpriteController> test : controllerMap.entrySet()) {
                if (!test.getKey().equals(entry.getKey()) && !test.getKey().contains("Bridge") && !test.getKey().contains("Orb") && !test.getKey().contains("Swipe") && !test.getValue().getReacting()) {
                    if (test.getValue().getEntity().getSprite().getBoundingBox() != null) {
                        RectF compareBox = test.getValue().getEntity().getSprite().getBoundingBox();
                        /* if the objects intersect, find where they intersect for the entry bounding box */
                        if (entryBox.intersect(compareBox)) {

                            /* increment hit value */
                            hit = hit + 1;
                            if(hit > 15) {
                                refreshEntity("destroyed");
                            }
                            else if(hit > 10) {
                                controller.setID("enemy stage3");
                            }
                            else if(hit > 5) {
                                controller.setID("enemy stage2");
                            }
                            else if(hit > 0) {
                                controller.setID("enemy stage1");
                            }

                        }
                    }
                }
            }
        }

        return map;

    }

}