package com.samsung.project.Objects.InteractiveObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.samsung.project.CastleRunner;
import com.samsung.project.Rooms.ActiveRoom;
import com.samsung.project.Screens.GameScreen;

public class I_Wall extends InteractiveObjects {

    private float startTime = 0;
    private float time = 0;
    private String state = "visible";

    private Music sound_visible;
    private Music sound_invisible;

    public I_Wall(GameScreen screen, World world, TiledMap map, MapObject object, ActiveRoom activeRoom, boolean secret) {
        super(screen, world, map, object, activeRoom, false);
        fixture.setUserData(this);
        setCategoryFilter(CastleRunner.BONUS_BIT);
        getCell(0, 0).setTile(map.getTileSets().getTile(2));

        if (secret) {
            getCell(0, 0).setTile(null);
            state = "invisible";
        }

        sound_visible = screen.game.assetsLoader.findSound("sound-i_wall_visible");
        sound_visible.setLooping(false);
        sound_visible.setVolume(screen.game.volume);

        sound_invisible = screen.game.assetsLoader.findSound("sound-i_wall_invisible");
        sound_invisible.setLooping(false);
        sound_invisible.setVolume(screen.game.volume);
    }

    @Override
    public void onHit() {

    }

    public void update(float delta, int duration, boolean secret) {
        time += delta;

        if (secret) {
            if (activeRoom.room5.secret) {
                startTime = 0;
                time = 0;
            }
            if (time - startTime >= duration) {
                if (state != "visible") {
                    setCategoryFilter(CastleRunner.BONUS_BIT);
                    getCell(0, 0).setTile(map.getTileSets().getTile(2));
                    state = "visible";

                    sound_visible.stop();
                    sound_visible.setVolume(screen.game.volume);
                    sound_visible.play();
                }
            } else {
                setCategoryFilter(CastleRunner.NULL_BIT);
                getCell(0, 0).setTile(null);
                state = "invisible";
            }
        } else {
            if (time - startTime >= duration) {
                if (state == "visible") {
                    setCategoryFilter(CastleRunner.NULL_BIT);
                    getCell(0, 0).setTile(null);
                    state = "invisible";

                    sound_invisible.stop();
                    sound_invisible.setVolume(screen.game.volume);
                    sound_invisible.play();
                } else {
                    setCategoryFilter(CastleRunner.BONUS_BIT);
                    getCell(0, 0).setTile(map.getTileSets().getTile(2));
                    state = "visible";

                    sound_visible.stop();
                    sound_visible.setVolume(screen.game.volume);
                    sound_visible.play();
                }
                startTime += duration;
            }
        }
    }
}