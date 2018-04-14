package com.samsung.project.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.samsung.project.CastleRunner;
import com.samsung.project.Screens.GameScreen;

public class Elevator {

    private static final float PPM = CastleRunner.PPM;

    private GameScreen screen;

    private World world;
    private Body body;
    private Texture elevatorTex;

    private Vector2 startPos;
    private Vector2 endPos;
    private boolean movingUp;

    private Music sound_up;
    private Music sound_down;

    public Elevator(GameScreen screen, World world, int startX, int startY, int endX, int endY) {
        this.screen = screen;
        this.world = world;

        startPos = new Vector2();
        endPos = new Vector2();
        movingUp = false;

        startPos.set(startX, startY);
        endPos.set(endX, endY);

        BodyDef bodyDef = new BodyDef();
        EdgeShape topLine = new EdgeShape();
        EdgeShape bottomLine = new EdgeShape();
        FixtureDef fixtureDef = new FixtureDef();
        elevatorTex = new Texture("images/sprites/elevator.png");

        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set((startX + 32) / PPM, (startY + 8) / PPM);

        body = world.createBody(bodyDef);

        topLine.set(new Vector2(-32 / PPM, 8 / PPM), new Vector2(32 / PPM, 8 / PPM));
        fixtureDef.shape = topLine;
        fixtureDef.density = 1.0f;
        fixtureDef.filter.categoryBits = CastleRunner.ELEVATOR_TOP_BIT;
        body.createFixture(fixtureDef).setUserData("Elevator");

        bottomLine.set(new Vector2(-32 / PPM, -8 / PPM), new Vector2(32 / PPM, -8 / PPM));
        fixtureDef.shape = bottomLine;
        fixtureDef.density = 0.0f;
        fixtureDef.filter.categoryBits = CastleRunner.ELEVATOR_BOTTOM_BIT;
        body.createFixture(fixtureDef).setUserData(this);

        sound_up = Gdx.audio.newMusic(Gdx.files.internal("sounds/elevator_up.mp3"));
        sound_up.setLooping(false);
        sound_up.setVolume(screen.game.volume);

        sound_down= Gdx.audio.newMusic(Gdx.files.internal("sounds/elevator_down.mp3"));
        sound_down.setLooping(false);
        sound_down.setVolume(screen.game.volume);
    }

    public void render(float delta, SpriteBatch batch) {
        update(delta);

        batch.begin();
        batch.draw(elevatorTex, body.getPosition().x * PPM - 32, body.getPosition().y * PPM - 8);
        batch.end();
    }

    private void update(float delta) {
        sound_up.setVolume(screen.game.volume);
        sound_down.setVolume(screen.game.volume);

        if (movingUp) {
            if (body.getPosition().y * PPM < endPos.y + 8) {
                body.setLinearVelocity(0, 2f);
            } else {
                body.setLinearVelocity(0, 0);
                movingUp = !movingUp;

                sound_up.stop();
                sound_down.stop();
                sound_down.play();
            }
        } else {
            if (body.getPosition().y * PPM > startPos.y + 8) {
                body.setLinearVelocity(0, -2f);
            } else {
                body.setLinearVelocity(0, 0);
                movingUp = !movingUp;

                sound_down.stop();
                sound_up.stop();
                sound_up.play();
            }
        }
    }

    public void onHit(boolean blocked) {
        if (movingUp) {
            if (blocked) {
                movingUp = !movingUp;
            }
        } else {
            movingUp = !movingUp;
        }

        muteSounds();
        playSounds();
    }

    public void muteSounds() {
        sound_up.stop();
        sound_down.stop();
    }

    public void playSounds() {
        sound_up.setVolume(screen.game.volume);
        sound_down.setVolume(screen.game.volume);

        if (movingUp) {
            sound_up.play();
        } else {
            sound_down.play();
        }
    }
}