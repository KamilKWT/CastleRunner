package com.samsung.project.Rooms;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.samsung.project.CastleRunner;
import com.samsung.project.Scenes.ControlPanel;
import com.samsung.project.Screens.GameScreen;

public class ActiveRoom {

    private CastleRunner game;
    private OrthographicCamera camera;

    public Room1 room1;

    public ActiveRoom(CastleRunner game, OrthographicCamera camera, GameScreen screen, ControlPanel controlPanel) {
        this.game = game;
        this.camera = camera;

        room1 = new Room1(game, camera, screen, controlPanel);
    }

    public void render(float delta, SpriteBatch batch, int roomToRender) {
        switch (roomToRender) {
            case 1:
                room1.render(delta, batch);
        }
    }

    public void dispose() {
        room1.dispose();
    }
}