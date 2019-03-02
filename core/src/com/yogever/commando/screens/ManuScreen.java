package com.yogever.commando.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.yogever.commando.EnergiezdCommando;

public class ManuScreen extends ScreenAdapter {
    EnergiezdCommando game;
    SpriteBatch batch;
    BitmapFont font;
    GlyphLayout layout;
    Viewport viewport;
    ShapeRenderer SP;
    Vector2 v1,v2;
    Color c1;
    Color c2;
    Color c3;
    Texture background;
    Image image;
    int Counter;
    float ap;
    Music button;
    boolean isChangedHard;
    boolean isChangedEasy;
    boolean isChangedHow;

    public ManuScreen(EnergiezdCommando game){
        this. game = game;
    }


    @Override
    public void show() {
        button = Gdx.audio.newMusic(Gdx.files.internal("button.mp3"));
        font = new BitmapFont(Gdx.files.internal("pixelfont.fnt"));
        font.getData().setScale(5);
        batch = new SpriteBatch();
        viewport = new FitViewport(1920, 1080);
        layout = new GlyphLayout(font, "Energized Commando"); //dont do this every frame! Store it as member
        SP = new ShapeRenderer();
        background = new Texture(Gdx.files.internal("B1013-0.png"));
        image = new Image(background);
        image.setPosition(0,0);
        image.setWidth(viewport.getWorldWidth());
        image.setHeight(viewport.getWorldHeight());
        Counter = 0;
        ap = 0;
    }


    @Override
    public void render(float delta) {
        viewport.apply();
        font.setColor(1,1,1,ap);
        SP.setProjectionMatrix(viewport.getCamera().combined);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.getData().setScale(3);
        image.draw(batch,ap);
        image.setPosition(viewport.getWorldWidth()/2 - image.getPrefWidth()/2,200);
        batch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        SP.begin(ShapeRenderer.ShapeType.Filled);
        c1 = new Color(0,(float)0.8,(float)0.4,ap);
        button();
        SP.setColor(c1);
        SP.rect(viewport.getWorldWidth()/2 - 200, 500,400,100);
        c2 = new Color(0,(float)0.8,(float)0.4,ap);
        button();
        SP.setColor(c2);
        SP.rect(viewport.getWorldWidth()/2 - 200, 300,400,100);
        c3 = new Color(0,(float)0.8,(float)0.4,ap);
        button();
        SP.setColor(c3);
        SP.rect(viewport.getWorldWidth()/2 - 200, 100,400,100);
        SP.end();
        batch.begin();
        layout.setText(font,"Energized Kingdom");
        font.draw(batch, "Energized Kingdom", viewport.getWorldWidth()/2- layout.width/2,viewport.getWorldHeight() - layout.height);
        font.getData().setScale((float)1.2);
        layout.setText(font,"Easy");
        font.draw(batch,"Easy", viewport.getWorldWidth()/2 - layout.width/2,550+layout.height/2);
        layout.setText(font,"Hard");
        font.draw(batch,"Hard", viewport.getWorldWidth()/2 - layout.width/2,350+layout.height/2);
        layout.setText(font,"How to play");
        font.draw(batch,"How to play", viewport.getWorldWidth()/2 - layout.width/2,150+layout.height/2);
        batch.end();
        background = new Texture(Gdx.files.internal("B1013-" + (Counter%24)/8 + ".png"));
        image = new Image(background);
        image.setWidth(viewport.getWorldWidth());
        image.setHeight(viewport.getWorldHeight());
        Counter++;
        ap+= 0.005;
    }


    public void button() {
        v1 = new Vector2(760, 300);
        v2 = new Vector2(1160, 400);
        v1 = viewport.project(v1);
        v2 = viewport.project(v2);


        if(Gdx.input.getX() < v1.x || Gdx.input.getX() > v2.x || Gdx.input.getY() > Gdx.graphics.getHeight() - v1.y || Gdx.input.getY() < Gdx.graphics.getHeight() - v2.y){
            isChangedHard = true;
        }


        if (ap>1&&Gdx.input.getX() > v1.x && Gdx.input.getX() < v2.x && Gdx.input.getY() < Gdx.graphics.getHeight() - v1.y && Gdx.input.getY() > Gdx.graphics.getHeight() - v2.y) {
            c2 = new Color(0,(float)0.8,(float)0.8,1);
            if(isChangedHard) {
                button.play();
                isChangedHard = false;
            }
            if (Gdx.input.isTouched()) {
                game.setScreen(new GameScreen(50,game));

            }
        }
        v1 = new Vector2(760, 500);
        v2 = new Vector2(1160, 600);
        v1 = viewport.project(v1);
        v2 = viewport.project(v2);
        if(Gdx.input.getX() < v1.x || Gdx.input.getX() > v2.x || Gdx.input.getY() > Gdx.graphics.getHeight() - v1.y || Gdx.input.getY() < Gdx.graphics.getHeight() - v2.y){
            isChangedEasy = true;
        }

        if (ap>1&&Gdx.input.getX() > v1.x && Gdx.input.getX() < v2.x && Gdx.input.getY() < Gdx.graphics.getHeight() - v1.y && Gdx.input.getY() > Gdx.graphics.getHeight() - v2.y) {
            c1 = new Color(0,(float)0.8,(float)0.8,1);
            if(isChangedEasy) {
                button.play();
                isChangedEasy = false;
            }
            if (Gdx.input.isTouched()) {
                game.setScreen(new GameScreen(100,game));

            }
        }



        v1 = new Vector2(760, 100);
        v2 = new Vector2(1160, 200);
        v1 = viewport.project(v1);
        v2 = viewport.project(v2);
        if(Gdx.input.getX() < v1.x || Gdx.input.getX() > v2.x || Gdx.input.getY() > Gdx.graphics.getHeight() - v1.y || Gdx.input.getY() < Gdx.graphics.getHeight() - v2.y){
            isChangedHow = true;
        }

        if (ap>1&&Gdx.input.getX() > v1.x && Gdx.input.getX() < v2.x && Gdx.input.getY() < Gdx.graphics.getHeight() - v1.y && Gdx.input.getY() > Gdx.graphics.getHeight() - v2.y) {
            c3 = new Color(0,(float)0.8,(float)0.8,1);
            if(isChangedHow) {
                button.play();
                isChangedHow = false;
            }
            if (Gdx.input.isTouched()) {
                game.setScreen(new HowToScreen(game));

            }
        }
    }



    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        SP.dispose();
        font.dispose();
    }
}
