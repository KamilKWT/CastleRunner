package com.samsung.project.Objects.InteractiveObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.samsung.project.CastleRunner;
import com.samsung.project.Rooms.ActiveRoom;
import com.samsung.project.Screens.GameScreen;

public class Goal extends InteractiveObjects{
    public Goal(GameScreen screen, World world, TiledMap map, MapObject object, ActiveRoom activeRoom) {
        super(screen, world, map, object, activeRoom, false);
        fixture.setUserData(this);
        setCategoryFilter(CastleRunner.BONUS_BIT);
    }

    @Override
    public void onHit() {
        screen.win = true;
        screen.game.setScreen(screen.endScreen);

        activeRoom.room7.elevator.muteSounds();
        for (int i = 0; i < 7; i++) {
            activeRoom.room7.lasers[i].muteSound();
        }
    }
}