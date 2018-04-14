package com.samsung.project.Objects.InteractiveObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.samsung.project.CastleRunner;
import com.samsung.project.Rooms.ActiveRoom;
import com.samsung.project.Screens.GameScreen;

public class Crown extends InteractiveObjects {

    private Music sound;

    public Crown(GameScreen screen, World world, TiledMap map, MapObject object, ActiveRoom activeRoom) {
        super(screen, world, map, object, activeRoom, true);
        fixture.setUserData(this);
        setCategoryFilter(CastleRunner.BONUS_BIT);

        sound = Gdx.audio.newMusic(Gdx.files.internal("sounds/crown.mp3"));
        sound.setLooping(false);
        sound.setVolume(screen.game.volume);
    }

    @Override
    public void onHit() {
        setCategoryFilter(CastleRunner.NULL_BIT);
        getCell(0, 0).setTile(null);

        screen.hud.addScore(500);
        screen.collectedCrowns++;

        sound.setVolume(screen.game.volume);
        sound.play();
    }
}