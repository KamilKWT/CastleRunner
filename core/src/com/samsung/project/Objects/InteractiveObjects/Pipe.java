package com.samsung.project.Objects.InteractiveObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.samsung.project.CastleRunner;
import com.samsung.project.Screens.GameScreen;

public class Pipe extends InteractiveObjects {
    public Pipe(GameScreen screen, World world, TiledMap map, MapObject object) {
        super(screen, world, map, object);
        fixture.setUserData(this);
        setCategoryFilter(CastleRunner.PIPE_BIT);
    }

    @Override
    public void onHit() {
    }
}