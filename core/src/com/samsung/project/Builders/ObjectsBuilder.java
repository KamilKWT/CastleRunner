package com.samsung.project.Builders;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.samsung.project.Objects.InteractiveObjects.Coin;
import com.samsung.project.Objects.InteractiveObjects.Pipe;
import com.samsung.project.Screens.GameScreen;

public class ObjectsBuilder {

    private GameScreen screen;
    private World world;
    private TiledMap map;

    public Coin[] coins;

    public ObjectsBuilder(GameScreen screen, World world, TiledMap map) {
        this.screen = screen;
        this.world = world;
        this.map = map;

        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;
    }

    public Coin[] generateCoins() {
        coins = new Coin[map.getLayers().get("Coins - obj").getObjects().getByType(RectangleMapObject.class).size];
        int index = 0;
        for (MapObject object: map.getLayers().get("Coins - obj").getObjects().getByType(RectangleMapObject.class)) {
            coins[index] = new Coin(screen, world, map, object);
            index++;
        }
        return coins;
    }

    public void generatePipes() {
        for (MapObject object: map.getLayers().get("Pipes - obj").getObjects().getByType(RectangleMapObject.class)) {
            new Pipe(screen, world, map, object);
        }
    }
}