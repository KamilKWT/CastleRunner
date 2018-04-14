package com.samsung.project.Objects.InteractiveObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.samsung.project.CastleRunner;
import com.samsung.project.Rooms.ActiveRoom;
import com.samsung.project.Screens.GameScreen;

public class Laser extends InteractiveObjects {

    private float startTime = 0;
    private float time = 0;
    private String state = "visible";
    private Music sound;

    public Laser(GameScreen screen, World world, TiledMap map, MapObject object, ActiveRoom activeRoom) {
        super(screen, world, map, object, activeRoom, false);
        fixture.setUserData(this);
        setCategoryFilter(CastleRunner.BONUS_BIT);

        sound = Gdx.audio.newMusic(Gdx.files.internal("sounds/laser.mp3"));
        sound.setLooping(false);
        if (activeRoom.roomToRender == 3) {
            sound.setVolume(screen.game.volume);
        } else {
            sound.setVolume(screen.game.volume / 7 * 2);
        }
    }

    @Override
    public void onHit() {
        if (activeRoom.roomToRender == 3) {
            if (!activeRoom.room3.player.isDeath) {
                if (screen.hud.lives == 0) {
                    screen.win = false;
                    screen.game.setScreen(screen.endScreen);
                    for (int i = 0; i < 4; i++) {
                        activeRoom.room3.lasers[i].muteSound();
                    }
                    activeRoom.room3.enemy.muteSound();
                    return;
                }
                screen.hud.changeLives(false);
                activeRoom.room3.player.isDeath = true;
                activeRoom.room1.createPlayer(96, 116, 10);
                for (int i = 0; i < 4; i++) {
                    activeRoom.room3.lasers[i].muteSound();
                }
                activeRoom.room3.enemy.muteSound();
                activeRoom.room3.player.sound.setVolume(screen.game.volume);
                activeRoom.room3.player.sound.play();
            }
        } else if (activeRoom.roomToRender == 7) {
            if (!activeRoom.room7.player.isDeath) {
                if (screen.hud.lives == 0) {
                    screen.win = false;
                    screen.game.setScreen(screen.endScreen);
                    activeRoom.room7.elevator.muteSounds();
                    for (int i = 0; i < 7; i++) {
                        activeRoom.room7.lasers[i].muteSound();
                    }
                    return;
                }
                screen.hud.changeLives(false);
                activeRoom.room7.player.isDeath = true;
                activeRoom.room1.createPlayer(96, 116, 10);
                activeRoom.room7.elevator.muteSounds();
                for (int i = 0; i < 7; i++) {
                    activeRoom.room7.lasers[i].muteSound();
                }
                activeRoom.room7.player.sound.setVolume(screen.game.volume);
                activeRoom.room7.player.sound.play();
            }
        }
    }

    public void update(float delta, int duration, boolean up_down) {
        time += delta;

        if (activeRoom.roomToRender == 3) {
            sound.setVolume(screen.game.volume);
        } else {
            sound.setVolume(screen.game.volume / 7 * 2);
        }

        if (time - startTime >= duration) {
            if (state == "visible") {
                setCategoryFilter(CastleRunner.NULL_BIT);
                getCell(0, 0).setTile(null);
                state = "invisible";

                sound.stop();
            } else {
                setCategoryFilter(CastleRunner.BONUS_BIT);
                if (up_down) {
                    getCell(0, 0).setTile(map.getTileSets().getTile(72));
                } else {
                    getCell(0, 0).setTile(map.getTileSets().getTile(68));
                }
                state = "visible";

                sound.play();
            }
            startTime += duration;
        }
    }

    public void muteSound() {
        sound.stop();
    }

    public void playSound() {
        if (state == "visible") {
            if (activeRoom.roomToRender == 3) {
                sound.setVolume(screen.game.volume);
            } else {
                sound.setVolume(screen.game.volume / 7 * 2);
            }
            sound.play();
        }
    }
}