package com.samsung.project.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.samsung.project.CastleRunner;
import com.samsung.project.Rooms.ActiveRoom;
import com.samsung.project.Scenes.ControlPanel;
import com.samsung.project.Scenes.HUD;

public class GameScreen extends AbstractScreen {

    public CastleRunner game;
    public ActiveRoom activeRoom;

    private OrthographicCamera camera;
    private Viewport viewport;

    public HUD hud;
    public ControlPanel controlPanel;
    public EndScreen endScreen;

    public Texture keyRed, keyBlue, keyGreen;

    public boolean[] keys;
    public boolean[] rooms;
    public int collectedCoins = 0;
    public int collectedCrowns = 0;
    public boolean win = false;

    public boolean sounds;

    public GameScreen(CastleRunner game, OrthographicCamera camera, Viewport viewport) {
        this.game = game;
        this.camera = camera;
        this.viewport = viewport;

        hud = new HUD(game.gameBatch, game);
        controlPanel = new ControlPanel(game.gameBatch, game, viewport);

        keyRed = game.assetsLoader.findTexture("sprites-key-red");
        keyBlue = game.assetsLoader.findTexture("sprites-key-blue");
        keyGreen = game.assetsLoader.findTexture("sprites-key-green");

        keys = new boolean[3];
        keys[0] = false;
        keys[1] = false;
        keys[2] = false;

        rooms = new boolean[7];
        for (int x = 0; x < 7; x++) {
            rooms[x] = false;
        }

        endScreen = new EndScreen(game, game.gameCamera, game.gameViewport, this);

        activeRoom = new ActiveRoom(game, camera, this, controlPanel);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        activeRoom.render(delta, game.gameBatch);

        game.gameBatch.setProjectionMatrix(hud.camera.combined);
        hud.stage.draw();
        game.gameBatch.begin();
        if (keys[0]) {
            game.gameBatch.draw(keyRed, 256 - 16, 480 - 16);
        }
        if (keys[1]) {
            game.gameBatch.draw(keyBlue, 320 - 16, 480 - 16);
        }
        if (keys[2]) {
            game.gameBatch.draw(keyGreen, 384 - 16, 480 - 16);
        }
        game.gameBatch.end();
        controlPanel.stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        if (controlPanel.touchMenu()) {
            if (activeRoom.roomToRender == 2) {
                activeRoom.room2.elevator.muteSounds();
                activeRoom.room2.enemy.muteSound();
            } else if (activeRoom.roomToRender == 3) {
                for (int i = 0; i < 4; i++) {
                    activeRoom.room3.lasers[i].muteSound();
                }
                activeRoom.room3.enemy.muteSound();
            } else if (activeRoom.roomToRender == 5) {
                activeRoom.room5.enemy.muteSound();
            } else if (activeRoom.roomToRender == 7) {
                activeRoom.room7.elevator.muteSounds();
                for (int i = 0; i < 7; i++) {
                    activeRoom.room7.lasers[i].muteSound();
                }
            }
            game.setScreen(new MenuScreen(game, game.gameCamera, game.gameViewport, this, false, sounds));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        activeRoom.dispose();
        endScreen.dispose();
    }
}