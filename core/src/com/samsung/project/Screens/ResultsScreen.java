package com.samsung.project.Screens;

import com.badlogic.gdx.Gdx;
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

public class ResultsScreen extends AbstractScreen {

    private CastleRunner game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private GameScreen screen;

    private SpriteBatch batch;
    private Stage stage;

    private OrthogonalTiledMapRenderer tmr;
    private TiledMap resultsMap;

    Label information;

    Label titleLabel;

    Label scoretitleLabel;
    Label scoreLabel;

    Label effecttitleLabel;
    Label effectstateLabel;
    Label effectscoreLabel;

    Label coinstitleLabel;
    Label coinsstateLabel;
    Label coinsmultipierLabel;
    Label coinsscoreLabel;

    Label crownstitleLabel;
    Label crownsstateLabel;
    Label crownsmultipierLabel;
    Label crownsscoreLabel;

    Label livestitleLabel;
    Label livesstateLabel;
    Label livesmultipierLabel;
    Label livesscoreLabel;

    Label roomstitleLabel;
    Label roomsstateLabel;

    Label normaltitleLabel;
    Label normalstateLabel;
    Label normalmultipierLabel;
    Label normalscoreLabel;

    Label secrettitleLabel;
    Label secretstateLabel;
    Label secretmultipierLabel;
    Label secretscoreLabel;

    Label totaltitleLabel;
    Label totalscoreLabel;

    Label emptyLabel;

    private int score;
    private int effectScore;

    private int collectedCoins;
    private int collectedCrowns;

    private int coinsScore;
    private int crownsScore;
    private int livesScore;
    private int livesState;

    private int normalScore;
    private int secretScore;
    private int roomsVisited;
    private int normalVisited;
    private int secretVisited;

    private int totalScore;

    public ResultsScreen(CastleRunner game, OrthographicCamera camera, Viewport viewport, GameScreen screen) {
        this.game = game;
        this.camera = camera;
        this.viewport = viewport;
        this.screen = screen;

        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        resultsMap = new TmxMapLoader().load("maps/results.tmx");
        tmr = new OrthogonalTiledMapRenderer(resultsMap);
    }

    @Override
    public void show() {
        collectedCoins = screen.collectedCoins;
        collectedCrowns = screen.collectedCrowns;

        calculateResults();
        createStage();
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
            game.setScreen(new HighscoresScreen(totalScore, game, game.gameBatch, screen.sounds));
        }
    }

    public void calculateResults() {
        score = screen.hud.score;
        if (screen.win) {
            effectScore = 3000;
        } else {
            effectScore = 0;
        }

        livesState = screen.hud.lives;

        coinsScore = ((collectedCoins * 1000) / 184);
        crownsScore = ((collectedCrowns * 5000) / 8);
        livesScore = livesState * 1000;

        for (int x = 0; x < 7; x++) {
            if (screen.rooms[x]) {
                roomsVisited++;
            }
        }
        if (screen.rooms[5]) {
            secretVisited = 1;
            normalVisited = roomsVisited - 1;
        } else {
            normalVisited = roomsVisited;
        }

        normalScore = normalVisited * 500;
        secretScore = secretVisited * 2000;

        totalScore = score + effectScore + coinsScore + crownsScore + livesScore + normalScore + secretScore;
    }

    public void createStage() {
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        titleLabel = new Label("Final Results:", new Label.LabelStyle(game.gameFont, Color.BLACK));

        scoretitleLabel = new Label("Score:", new Label.LabelStyle(game.gameFont, Color.BLACK));
        scoreLabel = new Label("" + score, new Label.LabelStyle(game.gameFont, Color.BLACK));

        effecttitleLabel = new Label("Game ended with:", new Label.LabelStyle(game.gameFont, Color.BLACK));
        if (screen.win) {
            effectstateLabel = new Label("Win", new Label.LabelStyle(game.gameFont, Color.BLACK));
        } else {
            effectstateLabel = new Label("Lose", new Label.LabelStyle(game.gameFont, Color.BLACK));
        }
        effectscoreLabel = new Label("" + effectScore, new Label.LabelStyle(game.gameFont, Color.BLACK));

        coinstitleLabel = new Label("Collected coins:", new Label.LabelStyle(game.gameFont, Color.BLACK));
        coinsstateLabel = new Label(collectedCoins + "/184", new Label.LabelStyle(game.gameFont, Color.BLACK));
        coinsmultipierLabel = new Label("* 1000", new Label.LabelStyle(game.gameFont, Color.BLACK));
        coinsscoreLabel = new Label("" + coinsScore, new Label.LabelStyle(game.gameFont, Color.BLACK));

        crownstitleLabel = new Label("Collected crowns:", new Label.LabelStyle(game.gameFont, Color.BLACK));
        crownsstateLabel = new Label(collectedCrowns + "/8", new Label.LabelStyle(game.gameFont, Color.BLACK));
        crownsmultipierLabel = new Label("* 5000", new Label.LabelStyle(game.gameFont, Color.BLACK));
        crownsscoreLabel = new Label("" + crownsScore, new Label.LabelStyle(game.gameFont, Color.BLACK));

        livestitleLabel = new Label("Lives:", new Label.LabelStyle(game.gameFont, Color.BLACK));
        livesstateLabel = new Label("" + livesState, new Label.LabelStyle(game.gameFont, Color.BLACK));
        livesmultipierLabel = new Label("* 1000", new Label.LabelStyle(game.gameFont, Color.BLACK));
        livesscoreLabel = new Label("" + livesScore, new Label.LabelStyle(game.gameFont, Color.BLACK));

        roomstitleLabel = new Label("Rooms visited:", new Label.LabelStyle(game.gameFont, Color.BLACK));
        roomsstateLabel = new Label(roomsVisited + " of 7", new Label.LabelStyle(game.gameFont, Color.BLACK));

        normaltitleLabel = new Label("- normal:", new Label.LabelStyle(game.gameFont, Color.BLACK));
        normalstateLabel = new Label(normalVisited + " of 6", new Label.LabelStyle(game.gameFont, Color.BLACK));
        normalmultipierLabel = new Label("* 500", new Label.LabelStyle(game.gameFont, Color.BLACK));
        normalscoreLabel = new Label("" + normalScore, new Label.LabelStyle(game.gameFont, Color.BLACK));

        secrettitleLabel = new Label("- secret:", new Label.LabelStyle(game.gameFont, Color.BLACK));
        secretstateLabel = new Label(secretVisited + " of 1", new Label.LabelStyle(game.gameFont, Color.BLACK));
        secretmultipierLabel = new Label("* 2000", new Label.LabelStyle(game.gameFont, Color.BLACK));
        secretscoreLabel = new Label("" + secretScore, new Label.LabelStyle(game.gameFont, Color.BLACK));

        totaltitleLabel = new Label("Total Score:", new Label.LabelStyle(game.gameFont, Color.BLACK));
        totalscoreLabel = new Label("" + totalScore, new Label.LabelStyle(game.gameFont, Color.BLACK));

        emptyLabel = new Label(" ", new Label.LabelStyle(game.gameFont, Color.BLACK));

        information = new Label("Tap to back to Main Menu.", new Label.LabelStyle(game.gameFont, Color.BLACK));

        table.add(emptyLabel).expandX();
        table.add(titleLabel).expandX();
        table.add(emptyLabel).expandX();

        table.row();

        table.add(emptyLabel).expandX().padTop(20);
        table.add(scoretitleLabel).expandX().padTop(20);
        table.add(emptyLabel).expandX().padTop(20);
        table.add(emptyLabel).expandX().padTop(20);
        table.add(scoreLabel).expandX().padTop(20);
        table.add(emptyLabel).expandX().padTop(20);

        table.row();

        table.add(emptyLabel).expandX().padTop(10);
        table.add(effecttitleLabel).expandX().padTop(10);
        table.add(effectstateLabel).expandX().padTop(10);
        table.add(emptyLabel).expandX().padTop(10);
        table.add(effectscoreLabel).expandX().padTop(10);
        table.add(emptyLabel).expandX().padTop(10);

        table.row();

        table.add(emptyLabel).expandX().padTop(20);
        table.add(coinstitleLabel).expandX().padTop(20);
        table.add(coinsstateLabel).expandX().padTop(20);
        table.add(coinsmultipierLabel).expandX().padTop(20);
        table.add(coinsscoreLabel).expandX().padTop(20);
        table.add(emptyLabel).expandX().padTop(20);

        table.row();

        table.add(emptyLabel).expandX().padTop(10);
        table.add(crownstitleLabel).expandX().padTop(10);
        table.add(crownsstateLabel).expandX().padTop(10);
        table.add(crownsmultipierLabel).expandX().padTop(10);
        table.add(crownsscoreLabel).expandX().padTop(10);
        table.add(emptyLabel).expandX().padTop(10);

        table.row();

        table.add(emptyLabel).expandX().padTop(10);
        table.add(livestitleLabel).expandX().padTop(10);
        table.add(livesstateLabel).expandX().padTop(10);
        table.add(livesmultipierLabel).expandX().padTop(10);
        table.add(livesscoreLabel).expandX().padTop(10);
        table.add(emptyLabel).expandX().padTop(10);

        table.row();

        table.add(emptyLabel).expandX().padTop(20);
        table.add(roomstitleLabel).expandX().padTop(20);
        table.add(roomsstateLabel).expandX().padTop(20);
        table.add(emptyLabel).expandX().padTop(20);
        table.add(emptyLabel).expandX().padTop(20);
        table.add(emptyLabel).expandX().padTop(20);

        table.row();

        table.add(emptyLabel).expandX().padTop(10);
        table.add(normaltitleLabel).expandX().padTop(10);
        table.add(normalstateLabel).expandX().padTop(10);
        table.add(normalmultipierLabel).expandX().padTop(10);
        table.add(normalscoreLabel).expandX().padTop(10);
        table.add(emptyLabel).expandX().padTop(10);

        table.row();

        table.add(emptyLabel).expandX().padTop(10);
        table.add(secrettitleLabel).expandX().padTop(10);
        table.add(secretstateLabel).expandX().padTop(10);
        table.add(secretmultipierLabel).expandX().padTop(10);
        table.add(secretscoreLabel).expandX().padTop(10);
        table.add(emptyLabel).expandX().padTop(10);

        table.row();

        table.add(emptyLabel).expandX().padTop(20);
        table.add(emptyLabel).expandX().padTop(20);
        table.add(emptyLabel).expandX().padTop(20);
        table.add(totaltitleLabel).expandX().padTop(20);
        table.add(totalscoreLabel).expandX().padTop(20);
        table.add(emptyLabel).expandX().padTop(20);

        Table table2 = new Table();
        table2.top();
        table2.setFillParent(true);

        table2.add(information).expandX().padTop(20);

        stage.addActor(table);
        stage.addActor(table2);

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}