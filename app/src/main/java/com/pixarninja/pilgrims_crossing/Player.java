package com.pixarninja.pilgrims_crossing;

import android.graphics.RectF;
import java.util.LinkedHashMap;
import java.util.Random;

public class Player extends SpriteEntity {

    @Override
    public void onTouchEvent(SpriteView spriteView, LinkedHashMap.Entry<String, SpriteController> entry, LinkedHashMap<String, SpriteController> controllerMap, boolean poke, boolean move, boolean jump, float xTouchedPos, float yTouchedPos) {

        if(!move) {

            String transition;
            String ID;
            SpriteController playerController = controllerMap.get("PlayerController");

            /* set player */
            Player oldPlayer = (Player) playerController.getEntity();
            if(oldPlayer.getController().getID().equals("run right") || oldPlayer.getController().getID().equals("sprint right")  || oldPlayer.getController().getID().equals("idle right")) {
                ID = "idle right";
            }
            else {
                ID = "idle left";
            }
            transition = playerController.getTransition();

            if (transition.equals("idle")) {
                if(playerController.getID().equals("idle right") || playerController.getID().equals("idle left")) {
                    transition = "inherit idle";
                }
                else {
                    transition = "reset idle";
                }
            } else {
                transition = "idle";
            }
            Player newPlayer = new PlayerIdle(spriteView, oldPlayer.res, oldPlayer.percentOfScreen, oldPlayer.xRes, oldPlayer.yRes, width, height, playerController, ID, transition);
            newPlayer.setCount(oldPlayer.getCount());
            newPlayer.setDelta(oldPlayer.getDelta());
            playerController.setEntity(newPlayer);

                /* move character */
            playerController.setXDelta(0);

            controllerMap.put("PlayerController", playerController);

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
            Random random = new Random();

            for (LinkedHashMap.Entry<String, SpriteController> test : controllerMap.entrySet()) {

                if(!test.getKey().equals(entry.getKey()) && !test.getKey().contains("Bridge") && !test.getKey().contains("Swipe") && !test.getValue().getReacting()) {

                    if (test.getValue().getEntity().getSprite().getBoundingBox() != null) {

                        RectF compareBox = test.getValue().getEntity().getSprite().getBoundingBox();

                        /* if the objects intersect, find where they intersect for the entry bounding box */
                        if (entryBox.intersect(compareBox)) {

                            /* first call the comparison's collision handler */
                            test.getValue().getEntity().onCollisionEvent(test, controllerMap);

                            if (entryLeft.intersect(compareBox)) {
                                SpriteController playerController = controllerMap.get("PlayerController");
                                SpriteEntity entity = new Swipe(spriteView, res, width, height, xRes, yRes, playerController.getXPos() - 2 * boxWidth / 3 + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "left");
                                while(controllerMap.get("Swipe" + i) != null) {
                                    i++;
                                }
                                map.put("Swipe" + i, entity.getController());
                            } else if (entryTop.intersect(compareBox)) {
                                SpriteController playerController = controllerMap.get("PlayerController");
                                SpriteEntity entity = new Swipe(spriteView, res, width, height, xRes, yRes, playerController.getXPos() - boxWidth / 4 + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() - boxHeight / 3 + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "top");
                                while(controllerMap.get("Swipe" + i) != null) {
                                    i++;
                                }
                                map.put("Swipe" + i, entity.getController());
                            } else if (entryRight.intersect(compareBox)) {
                                SpriteController playerController = controllerMap.get("PlayerController");
                                SpriteEntity entity = new Swipe(spriteView, res, width, height, xRes, yRes, playerController.getXPos() + boxWidth / 3 + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "right");
                                while(controllerMap.get("Swipe" + i) != null) {
                                    i++;
                                }
                                map.put("Swipe" + i, entity.getController());
                            } else if (entryBottom.intersect(compareBox)) {
                                SpriteController playerController = controllerMap.get("PlayerController");
                                SpriteEntity entity = new Swipe(spriteView, res, width, height, xRes, yRes, playerController.getXPos() - boxWidth / 3 + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "bottom");
                                while(controllerMap.get("Swipe" + i) != null) {
                                    i++;
                                }
                                map.put("Swipe" + i, entity.getController());
                            } else if (entryTopLeft.intersect(compareBox)) {
                                SpriteController playerController = controllerMap.get("PlayerController");
                                SpriteEntity entity = new Swipe(spriteView, res, width, height, xRes, yRes, playerController.getXPos() - boxWidth / 3 + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() - boxHeight / 4 + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "topLeft");
                                while(controllerMap.get("Swipe" + i) != null) {
                                    i++;
                                }
                                map.put("Swipe" + i, entity.getController());
                            } else if (entryTopRight.intersect(compareBox)) {
                                SpriteController playerController = controllerMap.get("PlayerController");
                                SpriteEntity entity = new Swipe(spriteView, res, width, height, xRes, yRes, playerController.getXPos() + boxWidth / 3 + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() - boxHeight / 4 + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "topRight");
                                while(controllerMap.get("Swipe" + i) != null) {
                                    i++;
                                }
                                map.put("Swipe" + i, entity.getController());
                            } else if (entryBottomRight.intersect(compareBox)) {
                                SpriteController playerController = controllerMap.get("PlayerController");
                                SpriteEntity entity = new Swipe(spriteView, res, width, height, xRes, yRes, playerController.getXPos() + 2 * boxWidth / 3 + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() + boxHeight / 3 + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "bottomRight");
                                while(controllerMap.get("Swipe" + i) != null) {
                                    i++;
                                }
                                map.put("Swipe" + i, entity.getController());
                            } else {
                                SpriteController playerController = controllerMap.get("PlayerController");
                                SpriteEntity entity = new Swipe(spriteView, res, width, height, xRes, yRes, playerController.getXPos() - 2 * boxWidth / 3 + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() + boxHeight / 3 + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "bottomLeft");
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