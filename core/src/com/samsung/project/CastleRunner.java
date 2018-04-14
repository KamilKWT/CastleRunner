package com.samsung.project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.samsung.project.Screens.SplashScreen;

public class CastleRunner extends Game {

	public static final int V_WIDTH = 640;
	public static final int V_HEIGHT = 512;
	public static final float PPM = 32;

	public static final short DEFAULT_BIT = 1;
	public static final short NULL_BIT = 2;
	public static final short WALL_BIT = 4;
	public static final short PLAYER_BIT = 8;
	public static final short ENEMY_BIT = 16;
	public static final short BONUS_BIT = 32;
	public static final short PIPE_BIT = 64;
	public static final short ELEVATOR_TOP_BIT = 128;
	public static final short ELEVATOR_BOTTOM_BIT = 128;

	public OrthographicCamera gameCamera;
	public Viewport gameViewport;
	public SpriteBatch gameBatch;

	public BitmapFont gameFont;

	public SplashScreen splashScreen;

	public Music music;
	public float volume;

	@Override
	public void create () {
		gameCamera = new OrthographicCamera();
		gameCamera.setToOrtho(false, V_WIDTH, V_HEIGHT);
		gameViewport = new FitViewport(V_WIDTH, V_HEIGHT, gameCamera);

		gameBatch = new SpriteBatch();
		gameFont = new BitmapFont();
		gameFont.setColor(Color.BLACK);

		volume = 1f;
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/CastleRunner.mp3"));
		music.setLooping(true);
		music.setVolume(volume / 2);

		splashScreen = new SplashScreen(this, gameViewport);

		this.setScreen(splashScreen);
	}

	@Override
	public void render() {
		super.render();

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}

	@Override
	public void resize(int width, int height) {
		gameViewport.update(width, height);
	}

	@Override
	public void dispose() {
		gameBatch.dispose();
		gameFont.dispose();
	}
}