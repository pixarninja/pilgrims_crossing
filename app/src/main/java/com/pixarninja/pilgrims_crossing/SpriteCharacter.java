package com.pixarninja.pilgrims_crossing;

import android.graphics.Rect;
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
    public void getCurrentFrame(){

        long time  = System.currentTimeMillis();
        if ( time > controller.getLastFrameChangeTime() + controller.getFrameRate()) {

            controller.setLastFrameChangeTime(time);

            if(render.getDirection().equals("backwards")) {
                if(count == 0) {
                    delta = -1;
                }
                else {
                    delta = 1;
                }

                if(delta < 0) {
                    render.setCurrentFrame(render.getCurrentFrame() + delta);
                    render.setXCurrentFrame(render.getXCurrentFrame() + delta);
                    if ((render.getXCurrentFrame() < 0) || (render.getCurrentFrame() < 0)) {
                        render.setYCurrentFrame(render.getYCurrentFrame() + delta);
                        if ((render.getYCurrentFrame() < 0) || (render.getCurrentFrame() < 0)) {
                            if(render.getMethod().equals("once")) {
                                refreshEntity("idle");
                                count = 0;
                            }
                            else if(render.getMethod().equals("mirror") || render.getMethod().equals("poked")) {
                                render.setCurrentFrame(render.getFrameCount() - 1);
                                render.setXCurrentFrame(render.getXFrameCount() - 1);
                                render.setYCurrentFrame(render.getYFrameCount() - 1);
                                count++;
                            }
                            /* loop or idle */
                            else {
                                render.setYCurrentFrame(render.getXFrameCount() - 1);
                                render.setCurrentFrame(render.getFrameCount() - 1);
                                count = 0;
                            }
                        }
                        if (count <= 0) {
                            render.setXCurrentFrame(render.getXFrameCount() - 1);
                        }
                    }
                }
                else if (delta == 0) {
                    render.setCurrentFrame(0);
                    render.setXCurrentFrame(0);
                    render.setYCurrentFrame(0);
                    count++;
                }
                else {
                    render.setCurrentFrame(render.getCurrentFrame() + delta);
                    render.setXCurrentFrame(render.getXCurrentFrame() + delta);
                    if ((render.getXCurrentFrame() >= render.getXFrameCount()) || (render.getCurrentFrame() >= render.getFrameCount())) {
                        render.setYCurrentFrame(render.getYCurrentFrame() + delta);
                        if ((render.getYCurrentFrame() >= render.getYFrameCount()) || (render.getCurrentFrame() >= render.getFrameCount())) {
                            refreshEntity("idle");
                            count = 0;
                        }
                        if (count > 0) {
                            render.setXCurrentFrame(0);
                        }
                    }
                }
            }

            else if(render.getDirection().equals("flipped")) {
                if(count == 0) {
                    delta = -1;
                }
                else {
                    delta = 1;
                }

                if(delta < 0) {
                    render.setCurrentFrame(render.getCurrentFrame() - delta);
                    render.setXCurrentFrame(render.getXCurrentFrame() + delta);
                    if ((render.getXCurrentFrame() < 0) || (render.getCurrentFrame() >= render.getFrameCount())) {
                        render.setYCurrentFrame(render.getYCurrentFrame() - delta);
                        if ((render.getYCurrentFrame() >= render.getYFrameCount()) || (render.getCurrentFrame() >= render.getFrameCount())) {
                            if(render.getMethod().equals("once")) {
                                refreshEntity("idle");
                                count = 0;
                            }
                            else if(render.getMethod().equals("mirror") || render.getMethod().equals("mirror loop") || render.getMethod().equals("poked")) {
                                render.setCurrentFrame(0);
                                render.setXCurrentFrame(0);
                                render.setYCurrentFrame(render.getYFrameCount() - 1);
                                count++;
                            }
                            /* loop or idle */
                            else {
                                render.setYCurrentFrame(0);
                                render.setCurrentFrame(0);
                                count = 0;
                            }
                        }
                        if (count <= 0) {
                            render.setXCurrentFrame(render.getXFrameCount() - 1);
                        }
                    }
                }
                else if (delta == 0) {
                    render.setCurrentFrame(0);
                    render.setXCurrentFrame(render.getXFrameCount() - 1);
                    render.setYCurrentFrame(render.getYFrameCount() - 1);
                    count++;
                }
                else {
                    render.setCurrentFrame(render.getCurrentFrame() + delta);
                    render.setXCurrentFrame(render.getXCurrentFrame() + delta);
                    if ((render.getXCurrentFrame() >= render.getXFrameCount()) || (render.getCurrentFrame() >= render.getFrameCount())) {
                        render.setYCurrentFrame(render.getYCurrentFrame() - delta);
                        if ((render.getYCurrentFrame() >= render.getYFrameCount()) || (render.getCurrentFrame() >= render.getFrameCount())) {
                            refreshEntity("idle");
                            count = 0;
                            render.setXCurrentFrame(render.getXFrameCount() - 1);
                        }
                    }
                }
            }

            else {
                if(count == 0) {
                    delta = 1;
                }
                else {
                    delta = -1;
                }

                if(delta > 0) {
                    render.setCurrentFrame(render.getCurrentFrame() + delta);
                    render.setXCurrentFrame(render.getXCurrentFrame() + delta);
                    if ((render.getXCurrentFrame() >= render.getXFrameCount()) || (render.getCurrentFrame() >= render.getFrameCount())) {
                        render.setYCurrentFrame(render.getYCurrentFrame() + delta);
                        if ((render.getYCurrentFrame() >= render.getYFrameCount()) || (render.getCurrentFrame() >= render.getFrameCount())) {
                            if(render.getMethod().equals("once")) {
                                refreshEntity("idle");
                                count = 0;
                            }
                            else if(render.getMethod().equals("mirror") || render.getMethod().equals("mirror loop") || render.getMethod().equals("poked")) {
                                render.setCurrentFrame(render.getFrameCount() - 1);
                                render.setXCurrentFrame(render.getXFrameCount() - 1);
                                render.setYCurrentFrame(render.getYFrameCount() - 1);
                                count++;
                            }
                            /* loop or idle */
                            else {
                                render.setYCurrentFrame(0);
                                render.setCurrentFrame(0);
                                count = 0;
                            }
                        }
                        if (count <= 0) {
                            render.setXCurrentFrame(0);
                        }
                    }
                }
                else if (delta == 0) {
                    render.setCurrentFrame(render.getFrameCount());
                    render.setXCurrentFrame(render.getXFrameCount() - 1);
                    render.setYCurrentFrame(render.getYFrameCount() - 1);
                    count++;
                }
                else {
                    render.setCurrentFrame(render.getCurrentFrame() + delta);
                    render.setXCurrentFrame(render.getXCurrentFrame() + delta);
                    if ((render.getXCurrentFrame() < 0) || (render.getCurrentFrame() < 0)) {
                        render.setYCurrentFrame(render.getYCurrentFrame() + delta);
                        if ((render.getYCurrentFrame() < 0) || (render.getCurrentFrame() < 0)) {
                            refreshEntity("idle");
                            count = 0;
                        }
                        if (count > 0) {
                            render.setXCurrentFrame(render.getXFrameCount() - 1);
                        }
                    }
                }
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

    @Override
    public void onTouchEvent(SpriteView spriteView, LinkedHashMap.Entry<String, SpriteController> entry, LinkedHashMap<String, SpriteController> controllerMap, boolean move, boolean jump, float xTouchedPos, float yTouchedPos) {

        if(!move) {

            String transition;
            String ID;
            SpriteController samuraiController = controllerMap.get("SamuraiController");

            /* set samurai */
            SpriteCharacter oldSamurai = (SpriteCharacter) samuraiController.getEntity();
            if(oldSamurai.getController().getID().equals("run right") || oldSamurai.getController().getID().equals("sprint right")  || oldSamurai.getController().getID().equals("idle right")) {
                ID = "idle right";
            }
            else {
                ID = "idle left";
            }
            transition = samuraiController.getTransition();

            if (transition.equals("idle")) {
                if(samuraiController.getID().equals("idle right") || samuraiController.getID().equals("idle left")) {
                    transition = "inherit idle";
                }
                else {
                    transition = "reset idle";
                }
            } else {
                transition = "idle";
            }
            SpriteCharacter newSamurai = new SamuraiIdle(spriteView, oldSamurai.res, oldSamurai.percentOfScreen, oldSamurai.xRes, oldSamurai.yRes, width, height, samuraiController, ID, transition);
            newSamurai.setCount(oldSamurai.getCount());
            newSamurai.setDelta(oldSamurai.getDelta());
            samuraiController.setEntity(newSamurai);

                /* move character */
            samuraiController.setXDelta(0);

            controllerMap.put("SamuraiController", samuraiController);

        }

    }

    @Override
    public LinkedHashMap<String, SpriteController> onCollisionEvent(LinkedHashMap.Entry<String, SpriteController> entry, LinkedHashMap<String, SpriteController> controllerMap) {

        LinkedHashMap<String, SpriteController> map = new LinkedHashMap<>();

        if(!entry.getValue().getReacting() && (entry.getValue().getEntity().getSprite().getBoundingBox() != null)) {
            RectF entryBox = entry.getValue().getEntity().getSprite().getBoundingBox();
            float left = entry.getValue().getEntity().getSprite().getWhereToDraw().left;
            float top = entry.getValue().getEntity().getSprite().getWhereToDraw().top;
            float right = entry.getValue().getEntity().getSprite().getWhereToDraw().right;
            float bottom = entry.getValue().getEntity().getSprite().getWhereToDraw().bottom;
            float boxWidth = right - left;
            float boxHeight = bottom - top;
            RectF entryLeft = new RectF(left, top + boxHeight / 3f, left + boxWidth / 3f, top + 2 * boxHeight / 3f);
            RectF entryTopLeft = new RectF(left, top, left + boxWidth / 3f, top + boxHeight / 3f);
            RectF entryTop = new RectF(left + boxWidth / 3f, top, left + 2 * boxWidth / 3f, top + boxHeight / 3f);
            RectF entryTopRight = new RectF(left + 2 * boxWidth / 3f, top, right, top + boxHeight / 3f);
            RectF entryRight = new RectF(left + 2 * boxWidth / 3f, top + boxHeight / 3f, right, top + 2 * boxHeight / 3f);
            RectF entryBottomRight = new RectF(left + 2 * boxWidth / 3f, top + 2 * boxHeight / 3f, right, bottom);
            RectF entryBottom = new RectF(left + boxWidth / 3f, top + 2 * boxHeight / 3f, left + 2 * boxWidth / 3f, bottom);

            int i = 1;

            for (LinkedHashMap.Entry<String, SpriteController> test : controllerMap.entrySet()) {

                if(!test.getKey().equals(entry.getKey()) && !test.getKey().equals("BridgeController") && !test.getValue().getReacting()) {

                    if (test.getValue().getEntity().getSprite().getBoundingBox() != null) {

                        RectF compareBox = test.getValue().getEntity().getSprite().getBoundingBox();

                        /* if the objects intersect, find where they intersect for the entry bounding box */
                        if (entryBox.intersect(compareBox)) {

                            /* first call the comparison's collision handler */
                            test.getValue().getEntity().onCollisionEvent(test, controllerMap);

                            if (entryLeft.intersect(compareBox)) {
                                SpriteController samuraiController = controllerMap.get("SamuraiController");
                                SpriteEntity entity = new Swipe(spriteView, res, 0.10, width, height, xRes, yRes,
                                        0, 0, samuraiController.getXPos() - 2 * boxWidth / 3, samuraiController.getYPos(),
                                        0, 0, 1, 1, null, "swipe", "left");
                                while(controllerMap.get("Swipe" + i) != null) {
                                    i++;                                 }
                                map.put("Swipe" + i, entity.getController());
                            } else if (entryTop.intersect(compareBox)) {
                                SpriteController samuraiController = controllerMap.get("SamuraiController");
                                SpriteEntity entity = new Swipe(spriteView, res, 0.10, width, height, xRes, yRes,
                                        0, 0, samuraiController.getXPos() - boxWidth / 4, samuraiController.getYPos() - boxHeight / 3,
                                        0, 0, 1, 1, null, "swipe", "top");
                                while(controllerMap.get("Swipe" + i) != null) {
                                    i++;
                                }
                                map.put("Swipe" + i, entity.getController());
                            } else if (entryRight.intersect(compareBox)) {
                                SpriteController samuraiController = controllerMap.get("SamuraiController");
                                SpriteEntity entity = new Swipe(spriteView, res, 0.10, width, height, xRes, yRes,
                                        0, 0, samuraiController.getXPos() + boxWidth / 3, samuraiController.getYPos(),
                                        0, 0, 1, 1, null, "swipe", "right");
                                while(controllerMap.get("Swipe" + i) != null) {
                                    i++;
                                }
                                map.put("Swipe" + i, entity.getController());
                            } else if (entryBottom.intersect(compareBox)) {
                                SpriteController samuraiController = controllerMap.get("SamuraiController");
                                SpriteEntity entity = new Swipe(spriteView, res, 0.10, width, height, xRes, yRes,
                                        0, 0, samuraiController.getXPos() - boxWidth / 3, samuraiController.getYPos(),
                                        0, 0, 1, 1, null, "swipe", "bottom");
                                while(controllerMap.get("Swipe" + i) != null) {
                                    i++;
                                }
                                map.put("Swipe" + i, entity.getController());
                            } else if (entryTopLeft.intersect(compareBox)) {
                                SpriteController samuraiController = controllerMap.get("SamuraiController");
                                SpriteEntity entity = new Swipe(spriteView, res, 0.10, width, height, xRes, yRes,
                                        0, 0, samuraiController.getXPos() - boxWidth / 3, samuraiController.getYPos() - boxHeight / 4,
                                        0, 0, 1, 1, null, "swipe", "topLeft");
                                while(controllerMap.get("Swipe" + i) != null) {
                                    i++;
                                }
                                map.put("Swipe" + i, entity.getController());
                            } else if (entryTopRight.intersect(compareBox)) {
                                SpriteController samuraiController = controllerMap.get("SamuraiController");
                                SpriteEntity entity = new Swipe(spriteView, res, 0.10, width, height, xRes, yRes,
                                        0, 0, samuraiController.getXPos() + boxWidth / 3, samuraiController.getYPos() - boxHeight / 4,
                                        0, 0, 1, 1, null, "swipe", "topRight");
                                while(controllerMap.get("Swipe" + i) != null) {
                                    i++;
                                }
                                map.put("Swipe" + i, entity.getController());
                            } else if (entryBottomRight.intersect(compareBox)) {
                                SpriteController samuraiController = controllerMap.get("SamuraiController");
                                SpriteEntity entity = new Swipe(spriteView, res, 0.10, width, height, xRes, yRes,
                                        0, 0, samuraiController.getXPos() + 2 * boxWidth / 3, samuraiController.getYPos() + boxHeight / 3,
                                        0, 0, 1, 1, null, "swipe", "bottomRight");
                                while(controllerMap.get("Swipe" + i) != null) {
                                    i++;
                                }
                                map.put("Swipe" + i, entity.getController());
                            } else {
                                SpriteController samuraiController = controllerMap.get("SamuraiController");
                                SpriteEntity entity = new Swipe(spriteView, res, 0.10, width, height, xRes, yRes,
                                        0, 0, samuraiController.getXPos() - 2 * boxWidth / 3, samuraiController.getYPos() + boxHeight / 3,
                                        0, 0, 1, 1, null, "swipe", "bottomLeft");
                                while(controllerMap.get("Swipe" + i) != null) {
                                    i++;
                                }
                                map.put("Swipe" + i, entity.getController());
                            }
                            break;

                        }

                    }

                }

            }

        }

        return map;

    }

}