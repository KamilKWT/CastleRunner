package com.samsung.project.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Preferences;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.samsung.project.CastleRunner;

public class HighscoresScreen extends AbstractScreen {

    private static final String PREFERENCES_NAME = "CastleRunnerPreferences";
    private Preferences preferences;

    private CastleRunner game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private Stage stage;

    private OrthogonalTiledMapRenderer tmr;
    private TiledMap resultsMap;

    private boolean sounds;
    private int totalScore;
    private int pos;

    private Table table;

    Label titleLabel, emptyLabel;

    Label[] positionsLabels;
    Label[] nickLabels;
    Label[] scoreLabels;

    Label yourScoreLabel, scoreLabel;

    private String[] nicks;
    private int[] scores;

    public HighscoresScreen(int totalScore, CastleRunner game, SpriteBatch batch, boolean sounds) {
        this.game = game;
        this.batch = batch;
        this.totalScore = totalScore;
        this.sounds = sounds;

        positionsLabels = new Label[10];
        nickLabels = new Label[10];
        scoreLabels = new Label[10];

        nicks = new String[10];
        scores = new int[10];

        preferences = Gdx.app.getPreferences(PREFERENCES_NAME);

        camera = new OrthographicCamera();
        viewport = new FitViewport(CastleRunner.V_WIDTH, CastleRunner.V_HEIGHT, camera);

        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        resultsMap = game.assetsLoader.findMap("map-results");
        tmr = new OrthogonalTiledMapRenderer(resultsMap);

        table = new Table();
        table.center();
        table.setFillParent(true);

        loadData();
        refreshTable(checkPosition());
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
            if (sounds) {
                game.music.play();
            }
            game.setScreen(new MenuScreen(game, game.gameCamera, game.gameViewport, null, true, sounds));
        }
    }

    private void resetData() {
        preferences.clear();
        preferences.flush();

        loadData();
        refreshStage();
    }

    private void saveData() {
        preferences.putString("nick1", nicks[0]);
        preferences.putString("nick2", nicks[1]);
        preferences.putString("nick3", nicks[2]);
        preferences.putString("nick4", nicks[3]);
        preferences.putString("nick5", nicks[4]);
        preferences.putString("nick6", nicks[5]);
        preferences.putString("nick7", nicks[6]);
        preferences.putString("nick8", nicks[7]);
        preferences.putString("nick9", nicks[8]);
        preferences.putString("nick10", nicks[9]);

        preferences.putInteger("score1", scores[0]);
        preferences.putInteger("score2", scores[1]);
        preferences.putInteger("score3", scores[2]);
        preferences.putInteger("score4", scores[3]);
        preferences.putInteger("score5", scores[4]);
        preferences.putInteger("score6", scores[5]);
        preferences.putInteger("score7", scores[6]);
        preferences.putInteger("score8", scores[7]);
        preferences.putInteger("score9", scores[8]);
        preferences.putInteger("score10", scores[9]);

        preferences.flush();
    }

    private void loadData() {
        nicks[0] = preferences.getString("nick1", "Elizabeth");
        nicks[1] = preferences.getString("nick2", "Michael");
        nicks[2] = preferences.getString("nick3", "Lucy");
        nicks[3] = preferences.getString("nick4", "George");
        nicks[4] = preferences.getString("nick5", "Stephanie");
        nicks[5] = preferences.getString("nick6", "Jacob");
        nicks[6] = preferences.getString("nick7", "Anastasia");
        nicks[7] = preferences.getString("nick8", "Maximilian");
        nicks[8] = preferences.getString("nick9", "Isabella");
        nicks[9] = preferences.getString("nick10", "John");

        scores[0] = preferences.getInteger("score1", 20000);
        scores[1] = preferences.getInteger("score2", 18000);
        scores[2] = preferences.getInteger("score3", 16000);
        scores[3] = preferences.getInteger("score4", 14000);
        scores[4] = preferences.getInteger("score5", 12000);
        scores[5] = preferences.getInteger("score6", 10000);
        scores[6] = preferences.getInteger("score7", 8000);
        scores[7] = preferences.getInteger("score8", 6000);
        scores[8] = preferences.getInteger("score9", 4000);
        scores[9] = preferences.getInteger("score10", 2000);
    }

    private int checkPosition() {
        for (int i = 9; i > -1; i--) {
            if (totalScore <= scores[i]) {
                return i + 1;
            }
        }
        return 0;
    }

    private void refreshTable(int pos) {
        if (pos < 10) {
            for (int i = 9; i > pos; i--) {
                nicks[i] = nicks[i - 1];
                scores[i] = scores[i - 1];
            }
            this.pos = pos;
            enterPlayerData();
        } else {
            refreshStage();
        }
    }

    private void enterPlayerData() {
        Gdx.input.getTextInput(new TextInputListener() {
            @Override
            public void input(String text) {
                if (text.length() > 0) {
                    if (text.equals("~<resetData>")) {
                        resetData();
                    } else {
                        nicks[pos] = text;
                        refreshStage();
                    }
                } else {
                    nicks[pos] = "Unknown";
                    refreshStage();
                }
            }

            @Override
            public void canceled() {
                nicks[pos] = "Unknown";
                refreshStage();
            }
        }, "Enter your nickname", null, "Your nickname");
        scores[pos] = totalScore;
    }

    private void crateStage() {
        titleLabel = new Label("Highscores", new Label.LabelStyle(game.gameFont, Color.BLACK));
        emptyLabel = new Label(" ", new Label.LabelStyle(game.gameFont, Color.BLACK));

        for (int i = 0; i < 10; i++) {
            positionsLabels[i] = new Label("" + (i + 1), new Label.LabelStyle(game.gameFont, Color.BLACK));
            nickLabels[i] = new Label("" + nicks[i], new Label.LabelStyle(game.gameFont, Color.BLACK));
            scoreLabels[i] = new Label("" + scores[i], new Label.LabelStyle(game.gameFont, Color.BLACK));
        }

        yourScoreLabel = new Label("Your score:", new Label.LabelStyle(game.gameFont, Color.BLACK));
        scoreLabel = new Label("" + totalScore, new Label.LabelStyle(game.gameFont, Color.BLACK));

        table.add(emptyLabel).expandX().padBottom(5);
        table.add(titleLabel).expandX().padBottom(5);
        table.add(emptyLabel).expandX().padBottom(5);

        table.row();

        for (int i = 0; i < 10; i++) {
            table.add(positionsLabels[i]).expandX().padTop(5);
            table.add(nickLabels[i]).expandX().padTop(5);
            table.add(scoreLabels[i]).expandX().padTop(5);

            table.row();
        }

        table.add(emptyLabel).expandX().padTop(10);
        table.add(yourScoreLabel).expandX().padTop(10);
        table.add(scoreLabel).expandX().padTop(10);

        stage.addActor(table);
    }

    private void refreshStage() {
        table.remove();
        crateStage();
        saveData();
    }
}