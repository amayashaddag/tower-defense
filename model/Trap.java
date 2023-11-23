package model;

import java.util.List;

public class Trap extends Item implements SingleTargetDamage {
    private int damage;
    Trap(List<Mob> targetMobs,int level){
        super(10+level, targetMobs, level);
    }   
    @Override
    void upgrade() {
        this.level++;
        this.damage+=5;
        this.dureeDeVie+=1;
    }
    @Override
    void doWhenDead(List<Mob> targetsMob) {
        this.damage=0; //Solution temporaire
    }
    @Override
    public void attack(Mob mob) {
        this.targetMobs.get(0).beingAttacked(this.damage);
    }
}
