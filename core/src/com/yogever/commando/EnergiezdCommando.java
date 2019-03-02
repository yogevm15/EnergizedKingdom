package com.yogever.commando;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.yogever.commando.screens.GameScreen;
import com.yogever.commando.screens.StartScreen;

public class EnergiezdCommando extends Game {

	public static Assets assets;

	@Override
	public void create() {
		assets = new Assets();
		setScreen(new StartScreen(this));
	}

	@Override
	public void dispose() {
		assets.dispose();
	}
}
