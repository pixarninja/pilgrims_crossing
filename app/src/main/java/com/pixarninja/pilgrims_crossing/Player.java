package com.pixarninja.pilgrims_crossing;

import android.graphics.RectF;
import java.util.LinkedHashMap;
import java.util.Random;

public class Player extends SpriteEntity {

    @Override
    public void updateView() {

        controller.setXPos(controller.getXPos() + controller.getXDelta());
        controller.setYPos(controller.getYPos() + controller.getYDelta());
        if(controller.getXPos() < 0) {
            controller.setXPos(0);
        }
        else if(controller.getXPos() > width - controller.getEntity().getSprite().getSpriteWidth()) {
            controller.setXPos(width - controller.getEntity().getSprite().getSpriteWidth());
        }
        getCurrentFrame();
        updateBoundingBox();

    }

    @Override
    public void onTouchEvent(SpriteView spriteView, LinkedHashMap.Entry<String, SpriteController> entry, LinkedHashMap<String, SpriteController> controllerMap, boolean poke, boolean move, boolean jump, float xTouchedPos, float yTouchedPos) {

        if(!move) {

            String transition;
            String ID;
            SpriteController playerController = controllerMap.get("PlayerController");

            /* set player */
            Player oldPlayer = (Player) playerController.getEntity();
            if(oldPlayer.getController().getID().equals("run right") || oldPlayer.getController().getID().equals("player sprint right")  || oldPlayer.getController().getID().equals("player idle right")) {
                ID = "player idle right";
            }
            else {
                ID = "player idle left";
            }
            transition = playerController.getTransition();

            if (transition.equals("idle")) {
                if(playerController.getID().equals("player idle right") || playerController.getID().equals("player idle left")) {
                    transition = "inherit idle";
                }
                else {
                    transition = "reset idle";
                }
            } else {
                transition = "idle";
            }
            Player newPlayer = new PlayerIdle(oldPlayer.res, oldPlayer.xRes, oldPlayer.yRes, width, height, playerController, ID, transition);
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
        LinkedHashMap<String, SpriteController> colliderMap;

        if(!controller.getID().contains("idle") && !entry.getValue().getReacting() && (entry.getValue().getEntity().getSprite().getBoundingBox() != null)) {

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
                if((test.getKey().contains("Spider") || test.getKey().contains("ItemDrop")) && !test.getValue().getReacting()) {
                    if (test.getValue().getEntity().getSprite().getBoundingBox() != null) {
                        RectF compareBox = test.getValue().getEntity().getSprite().getBoundingBox();
                        /* if the objects intersect, find where they intersect for the entry bounding box */
                        if (entryBox.intersect(compareBox)) {

                            /* first call the comparison's collision handler */
                            colliderMap = test.getValue().getEntity().onCollisionEvent(test, controllerMap);
                            for(LinkedHashMap.Entry<String, SpriteController> add : colliderMap.entrySet()) {
                                map.put(add.getKey(), add.getValue());
                            }

                            /* don't add a swipe for an item drop */
                            if(test.getValue().getID().contains("item drop")) {
                                continue;
                            }

                            SpriteController playerController = controllerMap.get("PlayerController");
                            //System.out.println("Reacting: "+ playerController.getReacting());

                            /* use a different order for left and right */
                            if(controller.getID().contains("left")) {
                                if (entryLeft.intersect(compareBox)) {
                                    SpriteEntity entity = new Swipe(res, width, height, xRes, yRes, playerController.getXPos() - 2 * boxWidth / 3 + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "left");
                                    while (controllerMap.get("Swipe" + i + "Controller") != null) {
                                        i++;
                                    }
                                    map.put("Swipe" + i + "Controller", entity.getController());
                                } else if (entryRight.intersect(compareBox)) {
                                    SpriteEntity entity = new Swipe(res, width, height, xRes, yRes, playerController.getXPos() + boxWidth / 3 + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "right");
                                    while (controllerMap.get("Swipe" + i) != null) {
                                        i++;
                                    }
                                    map.put("Swipe" + i, entity.getController());
                                } else if (entryTop.intersect(compareBox)) {
                                    SpriteEntity entity = new Swipe(res, width, height, xRes, yRes, playerController.getXPos() - boxWidth / 2 + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() - boxHeight / 3 + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "top");
                                    while (controllerMap.get("Swipe" + i + "Controller") != null) {
                                        i++;
                                    }
                                    map.put("Swipe" + i + "Controller", entity.getController());
                                } else if (entryBottom.intersect(compareBox)) {
                                    SpriteEntity entity = new Swipe(res, width, height, xRes, yRes, playerController.getXPos() - boxWidth / 3 + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() - boxHeight / 6 + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "bottom");
                                    while (controllerMap.get("Swipe" + i + "Controller") != null) {
                                        i++;
                                    }
                                    map.put("Swipe" + i + "Controller", entity.getController());
                                } else if (entryTopLeft.intersect(compareBox)) {
                                    SpriteEntity entity = new Swipe(res, width, height, xRes, yRes, playerController.getXPos() - boxWidth / 3 + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "topLeft");
                                    while (controllerMap.get("Swipe" + i) != null) {
                                        i++;
                                    }
                                    map.put("Swipe" + i, entity.getController());
                                } else if (entryTopRight.intersect(compareBox)) {
                                    SpriteEntity entity = new Swipe(res, width, height, xRes, yRes, playerController.getXPos() + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "topRight");
                                    while (controllerMap.get("Swipe" + i + "Controller") != null) {
                                        i++;
                                    }
                                    map.put("Swipe" + i + "Controller", entity.getController());
                                } else if (entryBottomRight.intersect(compareBox)) {
                                    SpriteEntity entity = new Swipe(res, width, height, xRes, yRes, playerController.getXPos() - boxWidth / 3 + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() + boxHeight / 6 + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "bottomLeft");
                                    while (controllerMap.get("Swipe" + i + "Controller") != null) {
                                        i++;
                                    }
                                    map.put("Swipe" + i + "Controller", entity.getController());
                                } else {
                                    SpriteEntity entity = new Swipe(res, width, height, xRes, yRes, playerController.getXPos() + boxWidth / 6 + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() + boxHeight / 6 + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "bottomRight");
                                    while (controllerMap.get("Swipe" + i + "Controller") != null) {
                                        i++;
                                    }
                                    map.put("Swipe" + i + "Controller", entity.getController());
                                }
                            }
                            else {
                                if (entryRight.intersect(compareBox)) {
                                    SpriteEntity entity = new Swipe(res, width, height, xRes, yRes, playerController.getXPos() + boxWidth / 3 + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "right");
                                    while (controllerMap.get("Swipe" + i) != null) {
                                        i++;
                                    }
                                    map.put("Swipe" + i, entity.getController());
                                } else if (entryLeft.intersect(compareBox)) {
                                    SpriteEntity entity = new Swipe(res, width, height, xRes, yRes, playerController.getXPos() - 2 * boxWidth / 3 + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "left");
                                    while (controllerMap.get("Swipe" + i + "Controller") != null) {
                                        i++;
                                    }
                                    map.put("Swipe" + i + "Controller", entity.getController());
                                } else if (entryTop.intersect(compareBox)) {
                                    SpriteEntity entity = new Swipe(res, width, height, xRes, yRes, playerController.getXPos() - boxWidth / 2 + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() - boxHeight / 3 + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "top");
                                    while (controllerMap.get("Swipe" + i + "Controller") != null) {
                                        i++;
                                    }
                                    map.put("Swipe" + i + "Controller", entity.getController());
                                } else if (entryBottom.intersect(compareBox)) {
                                    SpriteEntity entity = new Swipe(res, width, height, xRes, yRes, playerController.getXPos() - boxWidth / 3 + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() - boxHeight / 6 + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "bottom");
                                    while (controllerMap.get("Swipe" + i + "Controller") != null) {
                                        i++;
                                    }
                                    map.put("Swipe" + i + "Controller", entity.getController());
                                } else if (entryTopRight.intersect(compareBox)) {
                                    SpriteEntity entity = new Swipe(res, width, height, xRes, yRes, playerController.getXPos() + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "topRight");
                                    while (controllerMap.get("Swipe" + i + "Controller") != null) {
                                        i++;
                                    }
                                    map.put("Swipe" + i + "Controller", entity.getController());
                                } else if (entryTopLeft.intersect(compareBox)) {
                                    SpriteEntity entity = new Swipe(res, width, height, xRes, yRes, playerController.getXPos() - boxWidth / 3 + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "topLeft");
                                    while (controllerMap.get("Swipe" + i) != null) {
                                        i++;
                                    }
                                    map.put("Swipe" + i, entity.getController());
                                } else if (entryBottomRight.intersect(compareBox)) {
                                    SpriteEntity entity = new Swipe(res, width, height, xRes, yRes, playerController.getXPos() + boxWidth / 6 + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() + boxHeight / 6 + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "bottomRight");
                                    while (controllerMap.get("Swipe" + i + "Controller") != null) {
                                        i++;
                                    }
                                    map.put("Swipe" + i + "Controller", entity.getController());
                                } else {
                                    SpriteEntity entity = new Swipe(res, width, height, xRes, yRes, playerController.getXPos() - boxWidth / 3 + random.nextDouble() * 20 - random.nextDouble() * 20, playerController.getYPos() + boxHeight / 6 + random.nextDouble() * 20 - random.nextDouble() * 20, "swipe", "bottomLeft");
                                    while (controllerMap.get("Swipe" + i + "Controller") != null) {
                                        i++;
                                    }
                                    map.put("Swipe" + i + "Controller", entity.getController());
                                }
                            }

                            //playerController.setReacting(true);
                            //controllerMap.put("PlayerController", playerController);

                            break;

                        }
                    }
                }
            }
        }

        return map;

    }

}