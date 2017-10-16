package com.pixarninja.pilgrims_crossing;

import android.graphics.RectF;

import java.util.LinkedHashMap;

public class Enemy extends SpriteEntity {

    protected int hit;
    protected int health;
    protected int chargeCount;
    protected int startCharge;
    protected int attackCount;
    protected int startAttack;

    public int getHit() { return hit; }
    public void setHit(int hit) { this.hit = hit; }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }

    public int getAttackCount() { return attackCount; }
    public void setAttackCount(int attackCount) { this.attackCount = attackCount; }

    public int getStartAttack() { return startAttack; }
    public void setStartAttack(int startAttack) { this.startAttack = startAttack; }

    public int getChargeCount() { return chargeCount; }
    public void setChargeCount(int chargeCount) { this.chargeCount = chargeCount; }

    public int getStartCharge() { return startCharge; }
    public void setStartCharge(int startCharge) { this.startCharge = startCharge; }

}