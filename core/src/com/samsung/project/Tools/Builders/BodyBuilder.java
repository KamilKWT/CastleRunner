package com.samsung.project.Tools.Builders;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.samsung.project.CastleRunner;
import com.samsung.project.Sprites.Player;

public class BodyBuilder {

    private static final float PPM = CastleRunner.PPM;

    private World world;
    private Player player;

    public Fixture fixture;

    public BodyBuilder(World world, Player player) {
        this.world = world;
        this.player = player;
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
        fixtureDef.filter.maskBits = CastleRunner.WALL_BIT
                | CastleRunner.ENEMY_BIT
                | CastleRunner.BONUS_BIT
                | CastleRunner.PIPE_BIT
                | CastleRunner.ELEVATOR_TOP_BIT
                | CastleRunner.ELEVATOR_BOTTOM_BIT;

        fixtureDef.shape = shape;
        fixtureDef.density = 0.6f;
        fixture = pBody.createFixture(fixtureDef);
        fixture.setUserData(player);
        shape.dispose();
        return pBody;
    }
}