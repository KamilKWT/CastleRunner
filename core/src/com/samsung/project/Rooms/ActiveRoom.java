package com.samsung.project.Rooms;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.samsung.project.CastleRunner;
import com.samsung.project.Scenes.ControlPanel;
import com.samsung.project.Screens.GameScreen;

public class ActiveRoom {

    private CastleRunner game;
    private OrthographicCamera camera;

    public int roomToRender;

    public Room1 room1;
    public Room2 room2;
    public Room3 room3;
    public Room4 room4;
    public Room5 room5;
    public Room6 room6;
    public Room7 room7;

    public ActiveRoom(CastleRunner game, OrthographicCamera camera, GameScreen screen, ControlPanel controlPanel) {
        this.game = game;
        this.camera = camera;

        room1 = new Room1(game, camera, screen, controlPanel, this);
        room2 = new Room2(game, camera, screen, controlPanel, this);
        room3 = new Room3(game, camera, screen, controlPanel, this);
        room4 = new Room4(game, camera, screen, controlPanel, this);
        room5 = new Room5(game, camera, screen, controlPanel, this);
        room6 = new Room6(game, camera, screen, controlPanel, this);
        room7 = new Room7(game, camera, screen, controlPanel, this);
        room1.createPlayer(96, 116, 10);
    }

    public void render(float delta, SpriteBatch batch) {
        if (roomToRender == 1) {
            room1.render(delta, batch);
        } else if (roomToRender == 2) {
            room2.render(delta, batch);
        } else if (roomToRender == 3) {
            room3.render(delta, batch);
        } else if (roomToRender == 4) {
            room4.render(delta, batch);
        } else if (roomToRender == 5) {
            room5.render(delta, batch);
        } else if (roomToRender == 6) {
            room6.render(delta, batch);
        } else if (roomToRender == 7) {
            room7.render(delta, batch);
        }
    }

    public void dispose() {
        room1.dispose();
        room2.dispose();
        room3.dispose();
        room4.dispose();
        room5.dispose();
        room6.dispose();
        room7.dispose();
    }
}