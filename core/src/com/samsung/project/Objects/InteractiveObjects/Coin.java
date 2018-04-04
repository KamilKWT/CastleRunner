package com.samsung.project.Objects.InteractiveObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.samsung.project.CastleRunner;
import com.samsung.project.Screens.GameScreen;

public class Coin extends InteractiveObjects{
    public Coin(GameScreen screen, World world, TiledMap map, MapObject object) {
        super(screen, world, map, object);
        fixture.setUserData(this);
        setCategoryFilter(CastleRunner.COIN_BIT);
    }

    @Override
    public void onHit() {
        setCategoryFilter(CastleRunner.NULL_BIT);
        getCell().setTile(null);
        screen.hud.addScore(10);
    }
}