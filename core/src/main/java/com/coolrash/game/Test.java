package com.coolrash.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.coolrash.game.gameobject.Block;
import com.coolrash.game.gameobject.Platform;
import com.coolrash.game.gameobject.Player;
import com.coolrash.game.screens.GameScreen;
import com.coolrash.game.screens.LoseScreen;
import com.coolrash.game.screens.MenuScreen;

import java.util.LinkedList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Test extends Game {

	public StretchViewport screenPort;
	public Additions additions;



	@Override
	public void create() {
		additions = new Additions();
		OrthographicCamera camera = new OrthographicCamera();

		camera.setToOrtho(false,Additions.WIDTH, Additions.HEIGHT);
		screenPort = new StretchViewport(Additions.WIDTH, Additions.HEIGHT, camera);
		this.setScreen(new MenuScreen(this));

	}

	public void gotoGameScreen() {
		GameScreen gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	public void gotoMenuScreen() {
		MenuScreen menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}

	public void gotoLoseScreen(){
		LoseScreen loseScreen = new LoseScreen(this);
		setScreen(loseScreen);
	}



	@Override
	public void render() {
		super.render();

	}

	@Override
	public void dispose() {
		super.dispose();
	}
}