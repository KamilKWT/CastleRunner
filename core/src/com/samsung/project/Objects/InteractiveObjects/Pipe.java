package com.samsung.project.Objects.InteractiveObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.samsung.project.CastleRunner;
import com.samsung.project.Rooms.ActiveRoom;
import com.samsung.project.Screens.GameScreen;

public class Pipe extends InteractiveObjects {

    private Music sound_up;
    private Music sound_down;

    public Pipe(GameScreen screen, World world, TiledMap map, MapObject object, ActiveRoom activeRoom) {
        super(screen, world, map, object, activeRoom, false);
        fixture.setUserData(this);
        setCategoryFilter(CastleRunner.PIPE_BIT);

        sound_up = Gdx.audio.newMusic(Gdx.files.internal("sounds/pipe_up.mp3"));
        sound_up.setLooping(false);
        sound_up.setVolume(screen.game.volume);

        sound_down = Gdx.audio.newMusic(Gdx.files.internal("sounds/pipe_down.mp3"));
        sound_down.setLooping(false);
        sound_down.setVolume(screen.game.volume);
    }

    @Override
    public void onHit() {
        if (screen.activeRoom.roomToRender == 1) {
            activeRoom.room1.player.isDeath = true;
            activeRoom.room2.createPlayer(128, 160, 450);

            activeRoom.room2.elevator.playSounds();
            activeRoom.room2.enemy.playSound();
            sound_up.stop();
            sound_up.setVolume(screen.game.volume);
            sound_up.play();
        } else {
            activeRoom.room2.player.isDeath = true;
            activeRoom.room1.createPlayer(128, 352, -450);

            activeRoom.room2.elevator.muteSounds();
            activeRoom.room2.enemy.muteSound();
            sound_down.stop();
            sound_down.setVolume(screen.game.volume);
            sound_down.play();
        }
    }
}