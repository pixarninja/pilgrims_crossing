package com.pixarninja.pilgrims_crossing;

import android.graphics.RectF;

import java.util.LinkedHashMap;

public class Enemy extends SpriteEntity {

    int hit = 0;

    @Override
    public LinkedHashMap<String, SpriteController> onCollisionEvent(LinkedHashMap.Entry<String, SpriteController> entry, LinkedHashMap<String, SpriteController> controllerMap) {

        LinkedHashMap<String, SpriteController> map = new LinkedHashMap<>();

        if (!entry.getValue().getReacting() && (entry.getValue().getEntity().getSprite().getBoundingBox() != null)) {
            RectF entryBox = entry.getValue().getEntity().getSprite().getBoundingBox();
            for (LinkedHashMap.Entry<String, SpriteController> test : controllerMap.entrySet()) {
                if (test.getKey().equals("PlayerController") && !test.getValue().getReacting()) {
                    if (test.getValue().getEntity().getSprite().getBoundingBox() != null) {
                        RectF compareBox = test.getValue().getEntity().getSprite().getBoundingBox();
                        /* if the objects intersect, find where they intersect for the entry bounding box */
                        if (entryBox.intersect(compareBox)) {
                            /* increment hit value */
                            hit = hit + 1;
                            if(hit > 8) {
                                refreshEntity("destroyed");
                            }
                            else if(hit > 4) {
                                refreshEntity("stage3");
                            }
                            else if(hit > 2) {
                                refreshEntity("stage2");
                            }
                            else if(hit > 0) {
                                refreshEntity("stage1");
                            }
                        }
                    }
                }
            }
        }

        return map;

    }

}