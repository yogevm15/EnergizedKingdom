package com.yogever.commando.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.yogever.commando.Ball;
import com.yogever.commando.EnergiezdCommando;
import com.yogever.commando.Portal;
import com.yogever.commando.Zombie;

import java.awt.*;
import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {
    public GameScreen(int randomBound,EnergiezdCommando game){
        zombieRandom = randomBound;
        this.game = game;
        System.out.println(zombieRandom);
    }
    EnergiezdCommando game;
    int zombieRandom;
    float playerEnergy;
    int castleEnergy;
    Music shootingMusic;
    ShapeRenderer SP;
    float speed;
    Viewport viewport;
    float playerY;
    float playerX;
    SpriteBatch spriteBatch;
    BitmapFont font;
    float playerWidth;
    float playerHeight;
    Image backgroundImage;
    Texture background;
    int jumping;
    int falling;
    Sprite player;
    Texture playerSprite;
    Texture castleTexture;
    int counter;
    boolean isLeft;
    int floorHeight;
    boolean shooting;
    float stateTime;
    Array<Ball> balls = new Array <Ball>();
    float fromStartShoot;
    Portal right;
    Portal left;
    Array<Zombie> zombies = new Array<Zombie>();
    Array<Zombie> deathZombies = new Array<Zombie>();
    int castleHP;
    float HPx;

    @Override
    public void show() {
        shootingMusic = Gdx.audio.newMusic(Gdx.files.internal("Shoot.wav"));
        shootingMusic.setVolume(1f);
        castleHP = 15000;
        viewport = new FitViewport(1920, 1080);
        right = new Portal(true,viewport.getWorldWidth()*2-200,250,viewport.getWorldWidth() * 2 - 534, 120);
        left = new Portal(false,-viewport.getWorldWidth()+155,250,-viewport.getWorldWidth() + 150, 120);
        speed = 5;
        HPx = viewport.getWorldWidth()/2;
        floorHeight = 250;
        SP = new ShapeRenderer();
        playerWidth = 100;
        playerHeight = 200;
        playerY = floorHeight;
        playerX = viewport.getWorldWidth()/2-playerWidth/2;
        spriteBatch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("pixelfont.fnt"));
        GlyphLayout layout = new GlyphLayout(font, "Difficulty: 0.0");
        font.setColor(Color.RED);
        background = new Texture("finalNight.PNG");
        backgroundImage = new Image(background);
        backgroundImage.setHeight(viewport.getWorldHeight());
        backgroundImage.setWidth(viewport.getWorldWidth());
        playerSprite = new Texture(Gdx.files.internal("Idle_01.png"));
        castleTexture = new Texture(Gdx.files.internal("sheet-platforms.png"));
        player = new Sprite(playerSprite,128,128);
        player.setScale(3);
        jumping = 0;
        falling = 0;
        counter = 1;
        shooting = false;
        isLeft = false;
        stateTime = 0f;
        fromStartShoot =100;
        playerEnergy = 0.05f;
    }

    @Override
    public void render(float delta) {
        SP.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        stateTime += delta;
        updatePlayers(delta);
        handleHeight();
        updateZombies(delta);
        updateBalls(delta);
        updatePortals(delta);
        if((playerX<viewport.getWorldWidth()*1.5)&&(playerX>(viewport.getWorldWidth()*-0.5))){
            viewport.getCamera().position.lerp(new Vector3(playerX,viewport.getWorldHeight()/2, 0),0.2f);
            HPx = viewport.getCamera().position.x-100;
        }
        viewport.apply();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        drawBackground();
        spriteBatch.end();
        SP.begin(ShapeRenderer.ShapeType.Filled);
        drawGround();
        SP.end();
        spriteBatch.begin();
        drawCastle();
        drawPlayer();
        drawPortals(delta);
        drawBalls();
        drawZombies();
        font.setColor(0,1,0,1);
        spriteBatch.end();
        SP.begin(ShapeRenderer.ShapeType.Filled);
        drawHPBar();
        SP.end();
        check();
    }

    public void updatePlayers(float delta){
        if (Gdx.input.isKeyPressed(Input.Keys.W) && playerY <= floorHeight) {
            jumping = 10;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)&&EnergiezdCommando.assets.playerAction.isAnimationFinished(fromStartShoot)&&playerX<(viewport.getWorldWidth()*2)-(viewport.getWorldWidth()*2/7.5)) {
            playerX+=10;
            player.setTexture(EnergiezdCommando.assets.playerWalk.getKeyFrame(stateTime));
            if (isLeft){
                isLeft = false;
                player.flip(true,false);
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.A)&&EnergiezdCommando.assets.playerAction.isAnimationFinished(fromStartShoot)&&playerX>viewport.getWorldWidth()*-1-(viewport.getWorldWidth()*-2/7.5)) {
            playerX-=10;
            player.setTexture(EnergiezdCommando.assets.playerWalk.getKeyFrame(stateTime));
            if (!isLeft){
                isLeft = true;
                player.flip(true,false);
            }
        }
        else{
            player.setTexture(EnergiezdCommando.assets.playerIdle.getKeyFrame(stateTime));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if(EnergiezdCommando.assets.playerAction.isAnimationFinished(fromStartShoot)){
                 fromStartShoot = 0;
                EnergiezdCommando.assets.playerAction.setFrameDuration(playerEnergy);
                if(playerEnergy<0.045) {
                    playerEnergy += 0.005;
                }

            }
        }
        if (!EnergiezdCommando.assets.playerAction.isAnimationFinished(fromStartShoot)){
            fromStartShoot += delta;
            player.setTexture(EnergiezdCommando.assets.playerAction.getKeyFrame(fromStartShoot));
            if(EnergiezdCommando.assets.playerAction.isAnimationFinished(fromStartShoot)){
                if(isLeft){
                    balls.add(new Ball(playerX,playerY,false));
                    shootingMusic.play();
                }
                else{
                    balls.add(new Ball(playerX,playerY,true));
                    shootingMusic.play();
                }
                shooting = false;
            }
        }
    }

    public void handleHeight(){
        if (jumping == 0 && playerY > floorHeight && falling == 0){
            falling = 10;
        }
        if (jumping >0){
            playerY += jumping;
            jumping--;
        }
        if (falling>0){
            playerY-=10-falling+1;
            falling--;
        }
    }


    public void updateBalls(float delta){
        int counter = 0;
        int counter2 = 0;
        boolean removedFlag = false;
        for (Ball b:balls) {
            removedFlag = false;
            b.updateBall(delta);
            counter2 = 0;
            for(Zombie z:zombies){
                if(b.direction){
                    if(b.getBall().getX()>z.getZombie().getX()&&z.getZombie().getX()>b.startX){
                        zombies.get(counter2).fromDeath = 0;
                        zombies.get(counter2).death(0);
                        deathZombies.add(zombies.get(counter2));
                        zombies.removeIndex(counter2);
                        balls.removeIndex(counter);
                        removedFlag = true;
                        castleEnergy+=10;
                        if(playerEnergy>=0.02) {
                            playerEnergy -= 0.01;
                        }
                        else{
                            playerEnergy = 0.01f;
                        }
                        break;
                    }
                }
                else{
                    if(b.getBall().getX()<z.getZombie().getX()+50&&z.getZombie().getX()<b.startX){
                        zombies.get(counter2).fromDeath = 0;
                        deathZombies.add(zombies.get(counter2));
                        zombies.removeIndex(counter2);
                        balls.removeIndex(counter);
                        removedFlag = true;
                        castleEnergy+=10;
                        if(playerEnergy>=0.02) {
                            playerEnergy -= 0.01;
                        }
                        else{
                            playerEnergy = 0.01f;
                        }
                        break;
                    }
                }
                counter2++;
            }
            if(removedFlag){
                break;
            }
            if(right != null) {
                if (b.getBall().getX() > viewport.getWorldWidth() * 2 - 370) {
                    balls.removeIndex(counter);
                    right.HP --;
                }
            }
            if(left!=null) {
                if (b.getBall().getX() < viewport.getWorldWidth() * -1 + 370) {
                    balls.removeIndex(counter);
                    left.HP --;
                }
            }
            if(b.TTL==0){
                balls.removeIndex(counter);
            }
            counter++;

        }
    }


    public void drawBackground(){
        backgroundImage.setDrawable(new SpriteDrawable(new Sprite(background)));
        backgroundImage.setHeight(viewport.getWorldHeight());
        backgroundImage.setWidth(viewport.getWorldWidth());
        backgroundImage.setPosition(0,0);
        backgroundImage.draw(spriteBatch,1);
        backgroundImage.setPosition(backgroundImage.getImageWidth(),0);
        backgroundImage.draw(spriteBatch,1);
        backgroundImage.setPosition(-backgroundImage.getImageWidth(),0);
        backgroundImage.draw(spriteBatch,1);
    }

    public void drawGround(){
        SP.setColor(Color.BROWN);
        SP.rect(0, 0, viewport.getWorldWidth(), 200);
        SP.rect(backgroundImage.getImageWidth(), 0, viewport.getWorldWidth(), 200);
        SP.rect(-backgroundImage.getImageWidth(), 0, viewport.getWorldWidth(), 200);
        SP.setColor(Color.WHITE);
    }
    public void drawCastle(){
        backgroundImage.setDrawable(new SpriteDrawable(new Sprite(castleTexture)));
        backgroundImage.setHeight(496);
        backgroundImage.setWidth(336);
        backgroundImage.setPosition(viewport.getWorldWidth()/2 - 167,100);
        backgroundImage.draw(spriteBatch,1);
    }

    public void updatePortals(float delta){
        if(right!=null) {
            String s = right.updatePortal(delta, zombieRandom);
            if(s=="spawn"){
                zombies.add(new Zombie(true,(int)viewport.getWorldWidth() * 2 - 370,300));
            }
            else if(s=="destroy"){
                right = null;

            }
        }
        if(left!=null) {
            String s = left.updatePortal(delta,zombieRandom);
            if(s=="spawn"){
                zombies.add(new Zombie(false,(int)viewport.getWorldWidth() * -1 + 370,300));
            }
            else if (s=="destroy"){
                left = null;
            }
        }
    }

    public void drawPortals(float delta){
        if(right!=null) {
            right.draw(spriteBatch);
        }
        if(left!=null){
            left.draw(spriteBatch);
        }
    }

    public void drawHPBar(){
        if(right != null) {
            if (right.HP > 0) {
                SP.rect(viewport.getWorldWidth() * 2 - 300, 450, 175, 50);
                SP.setColor(0, 1, 0, 1);
                SP.rect(viewport.getWorldWidth() * 2 - 300, 450, (float) right.HP / 50 * 175, 50);
            }
        }
        if(left != null) {
            if (left.HP > 0) {
                SP.setColor(1, 1, 1, 1);
                SP.rect(viewport.getWorldWidth() * -1 + 150, 450, 175, 50);
                SP.setColor(0, 1, 0, 1);
                SP.rect(viewport.getWorldWidth() * -1 + 150, 450, (float) left.HP / 50 * 175, 50);
            }
        }
        if(viewport.getWorldWidth()/2 - 120>viewport.getCamera().position.x-viewport.getWorldWidth()/2&&viewport.getWorldWidth()/2+50<viewport.getCamera().position.x+viewport.getWorldWidth()/2){
            HPx = viewport.getWorldWidth()/2 - 120;
        }
        SP.setColor(1, 1, 1, 1);
        SP.rect(HPx, viewport.getWorldHeight()-100, 200, 50);
        SP.setColor(0, 1, 0, 1);
        SP.rect(HPx, viewport.getWorldHeight()-100, (float)castleHP/15000*200, 50);
        SP.setColor(1, 1, 1, 1);
        SP.rect(playerX-100, playerY+100, 200, 50);
        SP.setColor(0, 0, 1, 1);
        SP.rect(playerX-100,playerY+100,200-(200*(playerEnergy-0.01f))/0.04f,50);
    }


    public void updateZombies(float delta) {
        for(Zombie z:zombies) {
            if(z.updateZombie(delta, (int) viewport.getWorldWidth() / 2 - 167)){
                castleHP--;
            }
        }
        for(Zombie z:deathZombies){
            if(z.death(delta)){
                deathZombies.removeValue(z,false);
            }
        }
    }

    public void drawZombies(){
        for(Zombie z:zombies){
            z.getZombie().draw(spriteBatch);
        }
        for(Zombie z:deathZombies){
            z.getZombie().draw(spriteBatch);
        }
    }

    public void drawPlayer(){
        player.setCenter(playerX,playerY);
        player.draw(spriteBatch,1);
    }


    public void drawBalls(){
        for (Ball b:balls) {
            b.getBall().draw(spriteBatch);
        }
    }

    public void check(){
        if(right == null&&left == null){
            game.setScreen(new WinScreen(game));
        }
        if(castleHP<1){
            game.setScreen(new LoseScreen(game));
        }
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        SP.dispose();
        spriteBatch.dispose();
        font.dispose();
        playerSprite.dispose();
        background.dispose();
        castleTexture.dispose();

    }
}