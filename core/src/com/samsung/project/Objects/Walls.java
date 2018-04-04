package com.samsung.project.Objects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.samsung.project.CastleRunner;

public class Walls {
    public static void getWalls(World world, MapObjects objects) {
        for (MapObject object: objects) {
            Shape shape;

            if (object instanceof PolylineMapObject) {
                shape = createShape((PolylineMapObject) object);
            } else {
                continue;
            }

            Body body;
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bodyDef);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.filter.categoryBits = CastleRunner.WALL_BIT;
            fixtureDef.filter.maskBits = CastleRunner.PLAYER_BIT;

            fixtureDef.shape = shape;
            fixtureDef.density = 1.0f;
            body.createFixture(fixtureDef).setUserData("Wall");
            shape.dispose();
        }
    }

    private static ChainShape createShape(PolylineMapObject polyline) {
        float[] vertices = polyline.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i< worldVertices.length; i++) {
            worldVertices[i] = new Vector2(vertices[i *2] / CastleRunner.PPM, vertices[i * 2 + 1] / CastleRunner.PPM);
        }

        ChainShape chainShape = new ChainShape();
        chainShape.createChain(worldVertices);
        return chainShape;
    }
}