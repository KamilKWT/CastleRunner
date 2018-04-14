package com.samsung.project.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.samsung.project.CastleRunner;

public class HelpScreen extends AbstractScreen {

    private CastleRunner game;
    private MenuScreen menuScreen;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;

    private Texture help1;
    private Texture help2;
    private Texture help3;
    private Texture help4;

    private int page;

    public HelpScreen(CastleRunner game, MenuScreen menuScreen, SpriteBatch batch) {
        this.game = game;
        this.menuScreen = menuScreen;
        this.batch = batch;

        camera = new OrthographicCamera();
        viewport = new FitViewport(CastleRunner.V_WIDTH, CastleRunner.V_HEIGHT, camera);

        help1 = new Texture("images/help/page1.png");
        help2 = new Texture("images/help/page2.png");
        help3 = new Texture("images/help/page3.png");
        help4 = new Texture("images/help/page4.png");

        page = 1;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1686274509803922f, 0.1686274509803922f, 0.1686274509803922f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if (page == 1) {
            batch.draw(help1, -help1.getWidth() / 2, -help1.getHeight() / 2);
        } else if (page == 2) {
            batch.draw(help2, -help2.getWidth() / 2, -help2.getHeight() / 2);
        } else if (page == 3) {
            batch.draw(help3, -help3.getWidth() / 2, -help3.getHeight() / 2);
        } else if (page == 4) {
            batch.draw(help4, -help4.getWidth() / 2, -help4.getHeight() / 2);
        }
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

        if (Gdx.input.justTouched()) {
            if (Gdx.input.getX() < Gdx.app.getGraphics().getWidth() * 0.4f) {
                page--;
                if (page == 0) {
                    game.setScreen(menuScreen);
                }
            } else if (Gdx.input.getX() > Gdx.app.getGraphics().getWidth() * 0.6f) {
                page++;
                if (page == 5) {
                    game.setScreen(menuScreen);
                }
            }
        }
    }

    public void update(float delta) {
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    @Override
    public void dispose() {
        help1.dispose();
        help2.dispose();
        help3.dispose();
        help4.dispose();
    }
}