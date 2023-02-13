package com.example.tutorial;

import org.jetbrains.annotations.Contract;

public enum AccionesPersonaje {
    IDDLE(0),PUNCH(1),STRONG_PUNCH(2), ATTACK_FORWARD(3), ATTACK_BACKWARDS(4),PROJECTILE(5),UPPERCUT(6),
    LOWKICK(7),TAKING_LIGHT_DAMAGE(8),TAKING_HEAVY_DAMAGE(9),TAUNT(10),LOSE(11), WIN(12),MOVE_FORWARD(13),
    MOVE_BACKWARDS(14),CROUCH(15);

     private int action;

    AccionesPersonaje(int action){
        this.action =action;
    }

    @Contract(pure = true)
    public int getAction(){
        return action;
    }
}
