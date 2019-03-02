package com.yogever.commando;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Assets {

    public Animation<Texture> ballTexture;
    public Animation<Texture> playerWalk;
    public Animation<Texture> playerIdle;
    public Animation<Texture> playerAction;
    public Animation<TextureRegion> zombieWalk;
    public Animation<TextureRegion> portalAnimation;
    public Animation<TextureRegion> zombieDeath;
    public Animation<TextureRegion> Explosion;

    public Assets() {
        Array<Texture> frames = new Array<Texture>();
        for (int i = 0;i<17;i++) {
            frames.add(new Texture(Gdx.files.internal("frame_"+(i+4)+"_delay-0.1s.gif")));
        }
        ballTexture = new Animation<Texture>(0.1f, frames, Animation.PlayMode.LOOP);



        frames.clear();
        for (int i = 0;i<6;i++){
            frames.add(new Texture(Gdx.files.internal("Walk_0"+ (i+1) +".png")));
        }
        playerWalk = new Animation<Texture>(0.1f,frames, Animation.PlayMode.LOOP);



        frames.clear();
        for (int i = 0;i<5;i++){
            frames.add(new Texture(Gdx.files.internal("Idle_0"+ (i+1) +".png")));
        }
        playerIdle = new Animation<Texture>(0.1f,frames, Animation.PlayMode.LOOP);



        frames.clear();
        for (int i = 0;i<19;i++){
            frames.add(new Texture(Gdx.files.internal("Action_"+ (i+1) +".png")));
        }
        playerAction = new Animation<Texture>(0.02f,frames, Animation.PlayMode.NORMAL);

        frames.clear();
        Texture t = new Texture(Gdx.files.internal("ZombieToast.png"));
        TextureRegion[][] framess = TextureRegion.split(t,t.getWidth()/25,t.getHeight());
        TextureRegion[] temp = new TextureRegion[4];
        for (int i = 0;i<4;i++){
            temp[i] = framess[0][i+2];
        }
        zombieWalk = new Animation<TextureRegion>(0.1f,temp);
        zombieWalk.setPlayMode(Animation.PlayMode.LOOP);

        frames.clear();
        temp = new TextureRegion[11];
        for (int i = 0;i<11;i++){
            temp[i] = framess[0][i+14];
        }
        zombieDeath = new Animation<TextureRegion>(0.1f,temp);
        zombieDeath.setPlayMode(Animation.PlayMode.LOOP);

        t = new Texture(Gdx.files.internal("Explosion.png"));
        TextureRegion[][] tempframess = TextureRegion.split(t,t.getWidth()/12,t.getHeight());
        Explosion = new Animation<TextureRegion>(0.1f,tempframess[0]);
        Explosion.setPlayMode(Animation.PlayMode.NORMAL);


        Texture portalSheet = new Texture(Gdx.files.internal("Green Portal Sprite Sheet.png"));
        TextureRegion[][] tempPortalAnimation = TextureRegion.split(portalSheet,portalSheet.getWidth()/8,portalSheet.getHeight()/3);
        portalAnimation = new Animation<TextureRegion>(0.1f,tempPortalAnimation[0]);
        portalAnimation.setPlayMode(Animation.PlayMode.LOOP);



    }

    public void dispose() {
        for(Texture t : ballTexture.getKeyFrames()) {
            t.dispose();
        }
        for(Texture t:playerWalk.getKeyFrames()){
            t.dispose();
        }
        for(Texture t:playerIdle.getKeyFrames()){
            t.dispose();
        }
        for(Texture t:playerAction.getKeyFrames()){
            t.dispose();
        }

    }
}
