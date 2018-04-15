package com.samsung.project.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.HashMap;
import java.util.Map;

public class AssetsLoader {

    public Map<String, Texture> textureMap = new HashMap<>();
    public Map<String, Music> soundMap = new HashMap<>();
    public Map<String, TiledMap> mapMap = new HashMap<>();

    public static AssetsLoader loadAssets(){
        AssetsLoader assetsLoader = new AssetsLoader();

        loadImages(assetsLoader);
        loadSounds(assetsLoader);
        loadMaps(assetsLoader);

        return assetsLoader;
    }

    public static void loadImages(AssetsLoader assetsLoader) {
        assetsLoader.addAsset("PowerSoft", new Texture(Gdx.files.internal("images/PowerSoft.png")), null, null);
        assetsLoader.addAsset("btn-exit", new Texture(Gdx.files.internal("images/btn/exit.png")), null, null);
        assetsLoader.addAsset("btn-help", new Texture(Gdx.files.internal("images/btn/help.png")), null, null);
        assetsLoader.addAsset("btn-left", new Texture(Gdx.files.internal("images/btn/left.png")), null, null);
        assetsLoader.addAsset("btn-main", new Texture(Gdx.files.internal("images/btn/main.png")), null, null);
        assetsLoader.addAsset("btn-menu", new Texture(Gdx.files.internal("images/btn/menu.png")), null, null);
        assetsLoader.addAsset("btn-play", new Texture(Gdx.files.internal("images/btn/play.png")), null, null);
        assetsLoader.addAsset("btn-resume", new Texture(Gdx.files.internal("images/btn/resume.png")), null, null);
        assetsLoader.addAsset("btn-right", new Texture(Gdx.files.internal("images/btn/right.png")), null, null);
        assetsLoader.addAsset("btn-snd_off", new Texture(Gdx.files.internal("images/btn/snd_off.png")), null, null);
        assetsLoader.addAsset("btn-snd_on", new Texture(Gdx.files.internal("images/btn/snd_on.png")), null, null);
        assetsLoader.addAsset("btn-up", new Texture(Gdx.files.internal("images/btn/up.png")), null, null);
        assetsLoader.addAsset("help-page1", new Texture(Gdx.files.internal("images/help/page1.png")), null, null);
        assetsLoader.addAsset("help-page2", new Texture(Gdx.files.internal("images/help/page2.png")), null, null);
        assetsLoader.addAsset("help-page3", new Texture(Gdx.files.internal("images/help/page3.png")), null, null);
        assetsLoader.addAsset("help-page4", new Texture(Gdx.files.internal("images/help/page4.png")), null, null);
        assetsLoader.addAsset("player-jumpingLeft", new Texture(Gdx.files.internal("images/sprites/player/JumpingLeft.png")), null, null);
        assetsLoader.addAsset("player-jumpingRight", new Texture(Gdx.files.internal("images/sprites/player/JumpingRight.png")), null, null);
        assetsLoader.addAsset("player-standingLeft", new Texture(Gdx.files.internal("images/sprites/player/StandingLeft.png")), null, null);
        assetsLoader.addAsset("player-standingRight", new Texture(Gdx.files.internal("images/sprites/player/StandingRight.png")), null, null);
        assetsLoader.addAsset("sprites-bat", new Texture(Gdx.files.internal("images/sprites/bat.png")), null, null);
        assetsLoader.addAsset("sprites-elevator", new Texture(Gdx.files.internal("images/sprites/elevator.png")), null, null);
        assetsLoader.addAsset("sprites-key-blue", new Texture(Gdx.files.internal("images/sprites/key-blue.png")), null, null);
        assetsLoader.addAsset("sprites-key-green", new Texture(Gdx.files.internal("images/sprites/key-green.png")), null, null);
        assetsLoader.addAsset("sprites-key-red", new Texture(Gdx.files.internal("images/sprites/key-red.png")), null, null);
    }

    public static void loadSounds(AssetsLoader assetsLoader) {
        assetsLoader.addAsset("sound-music", null, Gdx.audio.newMusic(Gdx.files.internal("sounds/CastleRunner.mp3")), null);
        assetsLoader.addAsset("sound-coin", null, Gdx.audio.newMusic(Gdx.files.internal("sounds/coin.mp3")), null);
        assetsLoader.addAsset("sound-crown", null, Gdx.audio.newMusic(Gdx.files.internal("sounds/crown.mp3")), null);
        assetsLoader.addAsset("sound-doors", null, Gdx.audio.newMusic(Gdx.files.internal("sounds/doors.mp3")), null);
        assetsLoader.addAsset("sound-elevator_down", null, Gdx.audio.newMusic(Gdx.files.internal("sounds/elevator_down.mp3")), null);
        assetsLoader.addAsset("sound-elevator_up", null, Gdx.audio.newMusic(Gdx.files.internal("sounds/elevator_up.mp3")), null);
        assetsLoader.addAsset("sound-enemy", null, Gdx.audio.newMusic(Gdx.files.internal("sounds/enemy.mp3")), null);
        assetsLoader.addAsset("sound-heart", null, Gdx.audio.newMusic(Gdx.files.internal("sounds/heart.mp3")), null);
        assetsLoader.addAsset("sound-i_wall_invisible", null, Gdx.audio.newMusic(Gdx.files.internal("sounds/i_wall_invisible.mp3")), null);
        assetsLoader.addAsset("sound-i_wall_visible", null, Gdx.audio.newMusic(Gdx.files.internal("sounds/i_wall_visible.mp3")), null);
        assetsLoader.addAsset("sound-key", null, Gdx.audio.newMusic(Gdx.files.internal("sounds/key.mp3")), null);
        assetsLoader.addAsset("sound-laser", null, Gdx.audio.newMusic(Gdx.files.internal("sounds/laser.mp3")), null);
        assetsLoader.addAsset("sound-lose", null, Gdx.audio.newMusic(Gdx.files.internal("sounds/lose.mp3")), null);
        assetsLoader.addAsset("sound-pipe_down", null, Gdx.audio.newMusic(Gdx.files.internal("sounds/pipe_down.mp3")), null);
        assetsLoader.addAsset("sound-pipe_up", null, Gdx.audio.newMusic(Gdx.files.internal("sounds/pipe_up.mp3")), null);
        assetsLoader.addAsset("sound-player_death", null, Gdx.audio.newMusic(Gdx.files.internal("sounds/player_death.mp3")), null);
        assetsLoader.addAsset("sound-win", null, Gdx.audio.newMusic(Gdx.files.internal("sounds/win.mp3")), null);
    }

    public static void loadMaps(AssetsLoader assetsLoader) {
        assetsLoader.addAsset("map-lose", null, null, new TmxMapLoader().load("maps/lose.tmx"));
        assetsLoader.addAsset("map-main", null, null, new TmxMapLoader().load("maps/main.tmx"));
        assetsLoader.addAsset("map-menu", null, null, new TmxMapLoader().load("maps/menu.tmx"));
        assetsLoader.addAsset("map-results", null, null, new TmxMapLoader().load("maps/results.tmx"));
        assetsLoader.addAsset("map-room_1", null, null, new TmxMapLoader().load("maps/room_1.tmx"));
        assetsLoader.addAsset("map-room_2", null, null, new TmxMapLoader().load("maps/room_2.tmx"));
        assetsLoader.addAsset("map-room_3", null, null, new TmxMapLoader().load("maps/room_3.tmx"));
        assetsLoader.addAsset("map-room_4", null, null, new TmxMapLoader().load("maps/room_4.tmx"));
        assetsLoader.addAsset("map-room_5", null, null, new TmxMapLoader().load("maps/room_5.tmx"));
        assetsLoader.addAsset("map-room_6", null, null, new TmxMapLoader().load("maps/room_6.tmx"));
        assetsLoader.addAsset("map-room_7", null, null, new TmxMapLoader().load("maps/room_7.tmx"));
        assetsLoader.addAsset("map-win", null, null, new TmxMapLoader().load("maps/win.tmx"));
    }

    private void addAsset(String name, Texture texture, Music sound, TiledMap map) {
        if (texture != null) {
            this.textureMap.put(name, texture);
        } else if (sound != null) {
            this.soundMap.put(name, sound);
        } else if (map != null) {
            this.mapMap.put(name, map);
        }
    }

    public Texture findTexture(String name) {
        return textureMap.get(name);
    }

    public Music findSound(String name) {
        return soundMap.get(name);
    }

    public TiledMap findMap(String name) {
        return mapMap.get(name);
    }
}