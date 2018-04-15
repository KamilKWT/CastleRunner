package com.samsung.project.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.samsung.project.CastleRunner;

public class EndScreen extends AbstractScreen{

    private CastleRunner game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private GameScreen screen;
    private ResultsScreen resultsScreen;

    private SpriteBatch batch;
    private Stage stage;

    private OrthogonalTiledMapRenderer tmr;
    private TiledMap winMap;
    private TiledMap loseMap;

    Label topLabel;
    Label bottomLabel;

    private Music sound_win;
    private Music sound_lose;

    public EndScreen(CastleRunner game, OrthographicCamera camera, Viewport viewport, GameScreen screen) {
        this.game = game;
        this.camera = camera;
        this.viewport = viewport;
        this.screen = screen;

        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        winMap = game.assetsLoader.findMap("map-win");
        loseMap = game.assetsLoader.findMap("map-lose");

        resultsScreen = new ResultsScreen(game, camera, viewport, screen);

        sound_win = screen.game.assetsLoader.findSound("sound-win");
        sound_win.setLooping(false);
        sound_win.setVolume(screen.game.volume);

        sound_lose = screen.game.assetsLoader.findSound("sound-lose");
        sound_lose.setLooping(false);
        sound_lose.setVolume(screen.game.volume);
    }

    @Override
    public void show() {
        game.music.stop();
        sound_win.setVolume(game.volume);
        sound_lose.setVolume(game.volume);

        createStage();
        if (screen.win) {
            tmr = new OrthogonalTiledMapRenderer(winMap);
            sound_win.play();
        } else {
            tmr = new OrthogonalTiledMapRenderer(loseMap);
            sound_lose.play();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1686274509803922f, 0.1686274509803922f, 0.1686274509803922f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        tmr.render();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    private void update(float delta) {
        camera.update();
        tmr.setView(camera);
        stage.act(delta);

        if (Gdx.input.justTouched()) {
            dispose();
            game.setScreen(resultsScreen);
        }
    }

    public void createStage() {
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        if (screen.win) {
            topLabel = new Label("Congratulations!!!", new Label.LabelStyle(game.gameFont, Color.BLACK));
        } else {
            topLabel = new Label("Next time will be better.", new Label.LabelStyle(game.gameFont, Color.BLACK));
        }
        bottomLabel = new Label("Tap to view your results.", new Label.LabelStyle(game.gameFont, Color.BLACK));

        table.add(topLabel).expandX().padTop(10);
        table.row();
        table.add(bottomLabel).expandX().padTop(10);

        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
        resultsScreen.dispose();
    }
}