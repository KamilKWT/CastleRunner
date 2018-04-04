package com.samsung.project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.samsung.project.Screens.GameScreen;
import com.samsung.project.Screens.SplashScreen;

public class CastleRunner extends Game {

	public float r_width;
	public float r_height;
	public static final int V_WIDTH = 640;
	public static final int V_HEIGHT = 512;
	public static final float PPM = 32;

	public float VPPRP;

	public static final short DEFAULT_BIT = 1;
	public static final short NULL_BIT = 2;
	public static final short WALL_BIT = 4;
	public static final short PLAYER_BIT = 8;
	public static final short COIN_BIT = 16;
	public static final short PIPE_BIT = 32;

	private OrthographicCamera gameCamera;
	private Viewport gameViewport;
	public SpriteBatch gameBatch;

	public BitmapFont gameFont;
	public AssetManager assets;

	public SplashScreen splashScreen;
	public GameScreen gameScreen;

	@Override
	public void create () {
		r_width = Gdx.app.getGraphics().getWidth();
		r_height = Gdx.app.getGraphics().getHeight();

		assets = new AssetManager();

		gameCamera = new OrthographicCamera();
		gameCamera.setToOrtho(false, V_WIDTH, V_HEIGHT);
		//camera.translate(- V_WIDTH / 2, - V_HEIGHT / 2);
		gameViewport = new FitViewport(V_WIDTH, V_HEIGHT, gameCamera);

		gameBatch = new SpriteBatch();
		gameFont = new BitmapFont();
		gameFont.setColor(Color.BLACK);

		splashScreen = new SplashScreen(this, gameViewport);
		gameScreen = new GameScreen(this, gameCamera, gameViewport);

		this.setScreen(gameScreen);
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
		assets.dispose();

		splashScreen.dispose();
		gameScreen.dispose();
	}

	public void setScreenScale() {
		float VPPRProb = 0;
		VPPRProb = r_width / V_WIDTH;
		if (V_HEIGHT * VPPRProb <= r_height) {
			VPPRP = VPPRProb;
		} else {
			VPPRP = r_height / V_HEIGHT;
		}
	}

	public float convertToRealPosition(float vpos, boolean width) {
		if (width) {
			if (Math.round(V_WIDTH * VPPRP) == r_width) {
				return vpos * VPPRP;
			} else {
				return ((r_width - (V_WIDTH * VPPRP)) / 2) + vpos * VPPRP;
			}
		} else {
			if (Math.round(V_HEIGHT * VPPRP) == r_height) {
				return vpos * VPPRP;
			} else {
				return ((r_height - (V_HEIGHT * VPPRP)) / 2) + vpos * VPPRP;
			}
		}
	}
}