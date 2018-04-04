package com.samsung.project.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.samsung.project.CastleRunner;
import com.samsung.project.Rooms.ActiveRoom;
import com.samsung.project.Scenes.ControlPanel;
import com.samsung.project.Scenes.HUD;

public class GameScreen extends AbstractScreen {

    private CastleRunner game;
    private ActiveRoom activeRoom;

    private OrthographicCamera camera;
    private Viewport viewport;

    public HUD hud;
    public ControlPanel controlPanel;

    private static int roomToRender;

    public GameScreen(CastleRunner game, OrthographicCamera camera, Viewport viewport) {
        this.game = game;
        this.camera = camera;
        this.viewport = viewport;

        hud = new HUD(game.gameBatch, game);
        controlPanel = new ControlPanel(game.gameBatch, game);

        activeRoom = new ActiveRoom(game, camera, this, controlPanel);
        roomToRender = 1;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        activeRoom.render(delta, game.gameBatch, roomToRender);

        game.gameBatch.setProjectionMatrix(hud.camera.combined);
        hud.stage.draw();
        controlPanel.stage.draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        game.setScreenScale();
    }

    @Override
    public void dispose() {
        activeRoom.dispose();
    }
}