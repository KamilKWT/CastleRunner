package com.samsung.project.Objects.InteractiveObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.samsung.project.CastleRunner;
import com.samsung.project.Rooms.ActiveRoom;
import com.samsung.project.Screens.GameScreen;

public class Door extends InteractiveObjects {

    private int ID;
    private boolean lastMap;
    private Music sound;

    public Door(GameScreen screen, World world, TiledMap map, MapObject object, ActiveRoom activeRoom, int ID, boolean lastMap) {
        super(screen, world, map, object, activeRoom, false);
        fixture.setUserData(this);
        setCategoryFilter(CastleRunner.BONUS_BIT);

        if (ID == 0) {
            if (lastMap) {
                getCell(0, 0).setTile(map.getTileSets().getTile(85));
                getCell(0, 1).setTile(map.getTileSets().getTile(81));
            } else {
                getCell(0, 0).setTile(map.getTileSets().getTile(81));
                getCell(0, -1).setTile(map.getTileSets().getTile(85));
            }
        } else if (ID == 1) {
            if (lastMap) {
                getCell(0, 0).setTile(map.getTileSets().getTile(93));
                getCell(0, 1).setTile(map.getTileSets().getTile(89));
            } else {
                getCell(0, 0).setTile(map.getTileSets().getTile(89));
                getCell(0, -1).setTile(map.getTileSets().getTile(93));
            }
        } else if (ID == 2) {
            if (lastMap) {
                getCell(0, 0).setTile(map.getTileSets().getTile(87));
                getCell(0, 1).setTile(map.getTileSets().getTile(83));
            } else {
                getCell(0, 0).setTile(map.getTileSets().getTile(83));
                getCell(0, -1).setTile(map.getTileSets().getTile(87));
            }
        }

        this.ID = ID;
        this.lastMap = lastMap;

        sound = screen.game.assetsLoader.findSound("sound-doors");
        sound.setLooping(false);
        sound.setVolume(screen.game.volume);
    }

    @Override
    public void onHit() {
        if (screen.keys[ID]) {
            setCategoryFilter(CastleRunner.NULL_BIT);
            getCell(0, 0).setTile(null);
            if (lastMap) {
                getCell(0, 1).setTile(null);
            } else {
                getCell(0, -1).setTile(null);
            }

            screen.hud.addScore(100);
            screen.keys[ID] = false;

            sound.setVolume(screen.game.volume);
            sound.stop();
            sound.play();
        }
    }
}