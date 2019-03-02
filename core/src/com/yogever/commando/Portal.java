package com.yogever.commando;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Random;

public class Portal {
    Music Explosion;
    public int HP;
    boolean direction; // 0 - left, 1 - right
    float start;
    Sprite portalBuilding;
    Sprite portal;
    float fromDestroy;

    public Portal(boolean direction,float buildX, float buildY, float X, float Y){
        Explosion = Gdx.audio.newMusic(Gdx.files.internal("Explosion.wav"));
        fromDestroy = 0;
        portalBuilding = new Sprite(new Texture(Gdx.files.internal("teleport.png")),44,62);
        portalBuilding.setScale(8,5);
        portal = new Sprite(EnergiezdCommando.assets.portalAnimation.getKeyFrame(0));
        portalBuilding.setPosition(buildX,buildY);
        portal.setPosition(X,Y);
        this.direction = direction;
        if(!direction){
            portalBuilding.flip(true,false);
        }
        HP = 50;
        start = 0;
    }

    public String updatePortal(float delta, int bound){
        start+=delta;
        portal.setRegion(EnergiezdCommando.assets.portalAnimation.getKeyFrame(start));
        if(direction){
            portal.flip(true,false);
        }
        Random rand = new Random();
        if(HP<1){
            if(fromDestroy==0){
                Explosion.play();
            }
            if(destroy(delta)){
                return "destroy";
            }
            return "nothing";
        }
        if(rand.nextInt(bound)==9){
            return "spawn";
        }
        else{
            return "nothing";
        }
    }

    public void draw(Batch b){
        if(fromDestroy==0) {
            portalBuilding.draw(b);
            b.draw(portal,portal.getX(), portal.getY(), portal.getWidth() * 6, portal.getHeight() * 6);
        }
        else{
            b.draw(portal,portal.getX()-100, portal.getY(), portal.getWidth() * 6, portal.getHeight() * 6);
        }
    }



    public boolean destroy(float delta){
        fromDestroy+=delta;
        if(EnergiezdCommando.assets.Explosion.isAnimationFinished(fromDestroy)){
            System.out.println("im here");
            return true;
        }
        portal.setRegion(EnergiezdCommando.assets.Explosion.getKeyFrame(fromDestroy));
        if(direction){
            portal.flip(true,false);
        }
        return false;
    }
}
