package com.yogever.commando;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Ball {
    Sprite ball;
    public boolean direction; // 0 - left, 1 - right
    float start;
    public int TTL = 100; //Time To Live
    public int startX;
    public Ball(float x, float y, boolean direction){
        this.direction = direction;
        startX = (int)x;
        ball = new Sprite(EnergiezdCommando.assets.ballTexture.getKeyFrame(start));
        ball.setScale(2);
        if(direction){
            ball.setPosition(x+100,y-20);
        }
        else{
            ball.setPosition(x-100,y-20);
        }
        start = 0;
    }

    public void updateBall(float delta){
        start += delta;
        ball.setTexture(EnergiezdCommando.assets.ballTexture.getKeyFrame(start));
        if (direction){
            ball.setPosition(ball.getX()+20,ball.getY());
        }
        else{
            ball.setPosition(ball.getX()-20,ball.getY());
        }
        TTL--;
    }



    public Sprite getBall(){
        return ball;
    }
}
