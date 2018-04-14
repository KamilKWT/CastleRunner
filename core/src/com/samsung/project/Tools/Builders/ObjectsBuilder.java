package com.samsung.project.Tools.Builders;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.samsung.project.Objects.InteractiveObjects.Coin;
import com.samsung.project.Objects.InteractiveObjects.Crown;
import com.samsung.project.Objects.InteractiveObjects.Door;
import com.samsung.project.Objects.InteractiveObjects.Goal;
import com.samsung.project.Objects.InteractiveObjects.Heart;
import com.samsung.project.Objects.InteractiveObjects.I_Wall;
import com.samsung.project.Objects.InteractiveObjects.Key;
import com.samsung.project.Objects.InteractiveObjects.Laser;
import com.samsung.project.Objects.InteractiveObjects.Pipe;
import com.samsung.project.Rooms.ActiveRoom;
import com.samsung.project.Screens.GameScreen;

public class ObjectsBuilder {

    private GameScreen screen;
    private World world;
    private TiledMap map;
    private ActiveRoom activeRoom;

    private Laser[] lasers;
    private I_Wall[] i_walls;

    public ObjectsBuilder(GameScreen screen, World world, TiledMap map, ActiveRoom activeRoom) {
        this.screen = screen;
        this.world = world;
        this.map = map;
        this.activeRoom = activeRoom;
    }

    public void generateCoins() {
        for (MapObject object: map.getLayers().get("Coins - obj").getObjects().getByType(RectangleMapObject.class)) {
            new Coin(screen, world, map, object, activeRoom);
        }
    }

    public void generateCrowns() {
        for (MapObject object: map.getLayers().get("Crowns - obj").getObjects().getByType(RectangleMapObject.class)) {
            new Crown(screen, world, map, object, activeRoom);
        }
    }

    public void generateHearts() {
        for (MapObject object: map.getLayers().get("Hearts - obj").getObjects().getByType(RectangleMapObject.class)) {
            new Heart(screen, world, map, object, activeRoom);
        }
    }

    public void generateKeys(int ID) {
        for (MapObject object: map.getLayers().get("Keys - obj").getObjects().getByType(RectangleMapObject.class)) {
            new Key(screen, world, map, object, activeRoom, ID);
        }
    }

    public void generatePipes() {
        for (MapObject object: map.getLayers().get("Pipes - obj").getObjects().getByType(RectangleMapObject.class)) {
            new Pipe(screen, world, map, object, activeRoom);
        }
    }

    public void generateDoors(int ID, boolean lastMap) {
        for (MapObject object: map.getLayers().get("Doors - obj").getObjects().getByType(RectangleMapObject.class)) {
            new Door(screen, world, map, object, activeRoom, ID, lastMap);
            if (lastMap) {
                ID++;
            }
        }
    }

    public Laser[] generateLasers() {
        lasers = new Laser[map.getLayers().get("Lasers - obj").getObjects().getByType(RectangleMapObject.class).size];
        int index = 0;
        for (MapObject object: map.getLayers().get("Lasers - obj").getObjects().getByType(RectangleMapObject.class)) {
            lasers[index] = new Laser(screen, world, map, object, activeRoom);
            index++;
        }
        return lasers;
    }

    public I_Wall[] generateI_Walls(boolean secret) {
        i_walls = new I_Wall[map.getLayers().get("I_Walls - obj").getObjects().getByType(RectangleMapObject.class).size];
        int index = 0;
        for (MapObject object: map.getLayers().get("I_Walls - obj").getObjects().getByType(RectangleMapObject.class)) {
            i_walls[index] = new I_Wall(screen, world, map, object, activeRoom, secret);
            index++;
        }
        return i_walls;
    }

    public void generateGoals() {
        for (MapObject object: map.getLayers().get("Goal - obj").getObjects().getByType(RectangleMapObject.class)) {
            new Goal(screen, world, map, object, activeRoom);
        }
    }
}