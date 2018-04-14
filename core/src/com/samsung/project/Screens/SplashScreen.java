package com.samsung.project.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.samsung.project.CastleRunner;

public class SplashScreen extends AbstractScreen{
    private CastleRunner game;
    private Stage stage;

    private Viewport viewport;

    private Image splashIMG;
    private Texture splashTexture;

    public SplashScreen(CastleRunner game, Viewport viewport) {
        this.game = game;
        this.viewport = viewport;
    }

    @Override
    public void show() {
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        Runnable endSplash = new Runnable() {
            @Override
            public void run() {
                dispose();
                game.music.play();
                game.setScreen(new MenuScreen(game, game.gameCamera, game.gameViewport, null, true, true));
            }
        };

        splashTexture = new Texture(Gdx.files.internal("images/PowerSoft.png"));
        splashIMG = new Image(splashTexture);
        splashIMG.setOrigin(splashIMG.getWidth() / 2, splashIMG.getHeight() / 2);
        splashIMG.setPosition(stage.getWidth() / 2 - 100, stage.getHeight() + 100);
        splashIMG.addAction(Actions.sequence(Actions.alpha(0f), Actions.scaleTo(0.1f, 0.1f),
                Actions.parallel(Actions.fadeIn(5f, Interpolation.pow2),
                        Actions.scaleTo(1f, 1f, 5.5f, Interpolation.pow5),
                        Actions.moveTo(stage.getWidth() /2 - 100, stage.getHeight() /2 - 100, 5f, Interpolation.swing)),
                Actions.delay(1.5f), Actions.fadeOut(1.25f), Actions.run(endSplash)));

        stage.addActor(splashIMG);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1686274509803922f, 0.1686274509803922f, 0.1686274509803922f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();
    }

    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    @Override
    public void dispose() {
        stage.dispose();
        //splashTexture.dispose();
        //splashIMG.remove();
    }
}