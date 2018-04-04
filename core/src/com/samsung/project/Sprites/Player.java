package com.samsung.project.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.samsung.project.Builders.BodyBuilder;
import com.samsung.project.CastleRunner;
import com.samsung.project.Scenes.ControlPanel;

public class Player extends Sprite{

    private static final float PPM = CastleRunner.PPM;

    private BodyBuilder bodyBuilder;
    private ControlPanel controlPanel;

    private World world;
    private Texture playerTex;
    private Body player;
    private Rectangle rectangle;

    public int test = 0;

    public Player(World world, ControlPanel controlPanel) {
        this.controlPanel = controlPanel;

        this.world = world;
        bodyBuilder = new BodyBuilder(world);
        playerTex = new Texture("images/player.png");
        rectangle = new Rectangle();

        player = bodyBuilder.createPlayer(96, 116, 40, 40, false);
    }

    public void render(float delta, SpriteBatch batch) {
        update(delta);

        batch.begin();
        batch.draw(playerTex, (player.getPosition().x * PPM) - (playerTex.getWidth() / 2), player.getPosition().y * PPM - (playerTex.getHeight() / 2));
        batch.end();
    }

    public void update(float delta) {
        inputUpdate(delta);
    }

    private void inputUpdate(float delta) {
        float force = 0;

        if(controlPanel.touchLeft()) {
            if (player.getLinearVelocity().x > -6.4) force = -50;
        } else if (controlPanel.touchRight()) {
            if (player.getLinearVelocity().x < 6.4) force = 50;
        } else {
            force = player.getLinearVelocity().x * -10;
        }

        if(controlPanel.touchUp()) {
            if (player.getLinearVelocity().y == 0) {
                player.applyForceToCenter(0, 650, false);
            }
        }

        player.applyForce(new Vector2(force, 0), player.getWorldCenter(), true);
        rectangle.set((player.getPosition().x - 16) * PPM, (player.getPosition().y - 16) * PPM, 32, 32);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}