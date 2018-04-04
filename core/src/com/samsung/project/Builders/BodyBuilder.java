package com.samsung.project.Builders;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.samsung.project.CastleRunner;
import com.samsung.project.Sprites.Player;

public class BodyBuilder {

    private static final float PPM = CastleRunner.PPM;
    private World world;

    public BodyBuilder(World world) {
        this.world = world;
    }

    public Body createPlayer(int x, int y, int width, int height, boolean isStatic) {
        Body pBody;
        BodyDef bodyDef = new BodyDef();

        if(isStatic)
            bodyDef.type = BodyDef.BodyType.StaticBody;
        else
            bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(x / PPM, y / PPM);
        bodyDef.fixedRotation = true;
        pBody = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

        fixtureDef.filter.categoryBits = CastleRunner.PLAYER_BIT;
        fixtureDef.filter.maskBits = CastleRunner.WALL_BIT | CastleRunner.COIN_BIT | CastleRunner.PIPE_BIT;

        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        pBody.createFixture(fixtureDef).setUserData("Player");
        shape.dispose();

        return pBody;
    }
}