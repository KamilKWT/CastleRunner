package com.samsung.project.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.samsung.project.CastleRunner;

public class HUD {

    public OrthographicCamera camera;
    private Viewport viewport;
    public Stage stage;

    public int lives;
    public int score;

    Label livesTitleLabel;
    Label livesLabel;
    Label scoreTitleLabel;
    Label scoreLabel;

    public HUD(SpriteBatch batch, CastleRunner game) {
        lives = 3;
        score = 0;

        camera = new OrthographicCamera();
        viewport = new FitViewport(CastleRunner.V_WIDTH, CastleRunner.V_HEIGHT, camera);
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        livesTitleLabel = new Label("Lives", new Label.LabelStyle(game.gameFont, Color.BLACK));
        livesLabel = new Label(String.format("%01d", lives), new Label.LabelStyle(game.gameFont, Color.BLACK));
        scoreTitleLabel = new Label("Score", new Label.LabelStyle(game.gameFont, Color.BLACK));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(game.gameFont, Color.BLACK));

        table.add(livesTitleLabel).expandX().padTop(10);
        table.add(scoreTitleLabel).expandX().padTop(10);
        table.row();
        table.add(livesLabel).expandX().padTop(10);
        table.add(scoreLabel).expandX().padTop(10);

        stage.addActor(table);
    }

    public void addScore(int value) {
        score += value;

        scoreLabel.setText(String.format("%06d", score));
    }

    public void changeLives(boolean add) {
        if (add) lives++;
        else lives--;

        livesLabel.setText(String.format("%01d", lives));
    }
}