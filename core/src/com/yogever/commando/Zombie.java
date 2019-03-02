package com.yogever.commando;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Zombie {
    public boolean direction; // false - left, true - right
    float start;
    public float fromDeath;
    public Sprite zombie;

    public Zombie(boolean direction,int  x,int y){
        this.direction = direction;
        zombie = new Sprite(EnergiezdCommando.assets.zombieWalk.getKeyFrame(0));
        if(!direction){
            zombie.flip(true,false);
        }
        zombie.setPosition(x,y);
        zombie.setScale(2,5);
        start = 0;
    }
     public boolean updateZombie(float delta,int x){
        start+=delta;
        zombie.setRegion(EnergiezdCommando.assets.zombieWalk.getKeyFrame(start));
         if(!direction){
             zombie.flip(true,false);
         }
        if(direction&&zombie.getX()>x+285) {
            zombie.setPosition(zombie.getX() - 3, zombie.getY());
            return false;
        }
        else if(direction){
            return true;
         }
        if(zombie.getX()<x-25){
            zombie.setPosition(zombie.getX() + 3, zombie.getY());
            return false;
        }
        else{
            return true;
        }
     }

     public boolean death(float delta){
        fromDeath+=delta;
        if(EnergiezdCommando.assets.zombieDeath.isAnimationFinished(fromDeath)){
            return true;
        }
        zombie.setRegion(EnergiezdCommando.assets.zombieDeath.getKeyFrame(fromDeath));
        if(!direction){
            zombie.flip(true,false);
        }
        return false;
     }




     public  Sprite getZombie(){
        return this.zombie;
     }
}
