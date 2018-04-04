package com.samsung.project.Objects.InteractiveObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.samsung.project.CastleRunner;
import com.samsung.project.Screens.GameScreen;

public abstract class InteractiveObjects {

    protected static final float PPM = CastleRunner.PPM;

    protected GameScreen screen;
    protected World world;
    protected TiledMap map;
    protected Rectangle rectangle;
    protected Body body;
    protected MapObject object;
    protected Fixture fixture;

    public InteractiveObjects(GameScreen screen, World world, TiledMap map, MapObject object) {
        this.screen = screen;
        this.world = world;
        this.map = map;
        this.object = object;
        this.rectangle = ((RectangleMapObject) object).getRectangle();

        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / PPM, (rectangle.getY() + rectangle.getWidth() / 2) / PPM);

        body = world.createBody(bodyDef);

        shape.setAsBox(rectangle.getWidth() / 2 / PPM, rectangle.getHeight() / 2 / PPM);
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
    }

    public abstract void onHit();

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Objects");
        return layer.getCell((int) (body.getPosition().x * PPM / 32), (int) (body.getPosition().y * PPM / 32));
    }
}