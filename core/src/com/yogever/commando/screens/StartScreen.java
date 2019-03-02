package com.yogever.commando.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.yogever.commando.EnergiezdCommando;
import sun.java2d.ScreenUpdateManager;

public class StartScreen extends ScreenAdapter {
    SpriteBatch batch;
    BitmapFont font;
    GlyphLayout layout;
    Viewport viewport;
    ShapeRenderer SP;
    float ap;
    Vector2 v;
    Vector2 v2;
    EnergiezdCommando game;
    Screen manuScreen;
    Color color;
    Music button;
    boolean isChanged;
    Music loading;
    Music backgroundMusic;

    public StartScreen(EnergiezdCommando game){
        this.game = game;
    }

    @Override
    public void show() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("day1_2.wav"));
        backgroundMusic.setLooping(true);
        button = Gdx.audio.newMusic(Gdx.files.internal("button.mp3"));
        loading = Gdx.audio.newMusic(Gdx.files.internal("Loading Sound Effect V01 (mp3cut.net).mp3"));
        font = new BitmapFont(Gdx.files.internal("pixelfont.fnt"));
        font.getData().setScale(3);
        batch = new SpriteBatch();
        viewport = new FitViewport(1920, 1080);
        layout = new GlyphLayout(font, "YoGever"); //dont do this every frame! Store it as member
        SP = new ShapeRenderer();
        manuScreen = new ManuScreen(game);
        isChanged = true;
    }

    @Override
    public void render(float delta) {
        if(!loading.isPlaying()&&ap<1){
            loading.play();
        }
        color = new Color(1,(float)0.5,0,ap);
        button();
        viewport.apply();
        SP.setProjectionMatrix(viewport.getCamera().combined);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        SP.begin(ShapeRenderer.ShapeType.Filled);
        SP.setColor(Color.LIGHT_GRAY);
        SP.rect(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        SP.setColor(color);
        SP.rect(viewport.getWorldWidth() / 2 - 200, 250, 400, 100);
        SP.end();
        batch.begin();
        font.getData().setScale(2);
        layout.setText(font,"Start");
        float width = layout.width;// contains the width of the current set text
        float height = layout.height; // contains the height of the current set text
        font.draw(batch, "Start", viewport.getWorldWidth() / 2 - width/2, 300 + height/2);
        font.getData().setScale(3);
        font.setColor(0,0,1,ap);
        layout.setText(font,"YoGever");
        width = layout.width;// contains the width of the current set text
        height = layout.height; // contains the height of the current set text
        font.draw(batch, "YoGever", viewport.getWorldWidth() / 2 - width/2, viewport.getWorldHeight() / 2 + height);
        layout.setText(font,"Games");
        width = layout.width;// contains the width of the current set text
        height = layout.height; // contains the height of the current set text
        font.draw(batch, "Games", viewport.getWorldWidth() / 2 - width/2, viewport.getWorldHeight() / 2 - height/2);
        batch.end();
        ap+=0.005;

    }

    public void button(){
        v = new Vector2(760, 250);
        v2 = new Vector2(1160, 350);
        v = viewport.project(v);
        v2 = viewport.project(v2);

        if(Gdx.input.getX() < v.x || Gdx.input.getX() > v2.x || Gdx.input.getY() > Gdx.graphics.getHeight() - v.y || Gdx.input.getY() < Gdx.graphics.getHeight() - v2.y){
            isChanged = true;
        }
        if (ap>=1 && Gdx.input.getX() > v.x && Gdx.input.getX() < v2.x && Gdx.input.getY() < Gdx.graphics.getHeight() - v.y && Gdx.input.getY() > Gdx.graphics.getHeight() - v2.y){
            if(isChanged) {
                button.play();
                isChanged = false;
            }
            color = new Color(1,(float)0.3,0,ap);
            if(Gdx.input.isTouched()){
                game.setScreen(manuScreen);
                backgroundMusic.play();

            }
        }
        else{
            System.out.println("input x: " + Gdx.input.getX() + " input y:" + Gdx.input.getY() + " first x: " + (v.x) + " second x: " + (v2.x) +" v.y: " + (Gdx.graphics.getHeight()-v.y) + " v2.y: " + (Gdx.graphics.getHeight() - v2.y));
        }
    }




    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        SP.dispose();
        batch.dispose();
        font.dispose();
    }
}
