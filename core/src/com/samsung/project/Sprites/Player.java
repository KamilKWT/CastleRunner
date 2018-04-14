package com.samsung.project.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.samsung.project.Screens.GameScreen;
import com.samsung.project.Tools.Builders.BodyBuilder;
import com.samsung.project.CastleRunner;
import com.samsung.project.Rooms.ActiveRoom;
import com.samsung.project.Scenes.ControlPanel;

public class Player extends Sprite{

    private static final float PPM = CastleRunner.PPM;

    public BodyBuilder bodyBuilder;
    private ControlPanel controlPanel;
    private ActiveRoom activeRoom;

    private World world;
    public Body player;
    private Rectangle rectangle;

    private Texture StandingRightTex;
    private Texture StandingLeftTex;
    private Texture JumpingRightTex;
    private Texture JumpingLeftTex;
    private boolean facingRight = true;

    public int startPosX;
    public int startPosY;
    public boolean isDeath = false;

    public boolean onFloor;
    public boolean onElevator;
    public boolean onI_Wall;

    public Music sound;

    public Player(GameScreen screen, World world, ActiveRoom activeRoom, ControlPanel controlPanel, int startX, int startY) {
        this.controlPanel = controlPanel;
        this.activeRoom = activeRoom;

        this.world = world;
        bodyBuilder = new BodyBuilder(world, this);
        StandingRightTex = new Texture("images/sprites/player/StandingRight.png");
        StandingLeftTex = new Texture("images/sprites/player/StandingLeft.png");
        JumpingRightTex = new Texture("images/sprites/player/JumpingRight.png");
        JumpingLeftTex = new Texture("images/sprites/player/JumpingLeft.png");
        rectangle = new Rectangle();

        player = bodyBuilder.createPlayer(startX, startY, 52, 52, false);
        startPosX = startX;
        startPosY = startY;

        onFloor = false;
        onElevator = false;
        onI_Wall = false;

        sound = Gdx.audio.newMusic(Gdx.files.internal("sounds/player_death.mp3"));
        sound.setLooping(false);
        sound.setVolume(screen.game.volume);
    }

    public void render(float delta, SpriteBatch batch) {
        update(delta);

        batch.begin();
        if (!(onFloor || onElevator || onI_Wall)) {
            if (facingRight) {
                batch.draw(JumpingRightTex, (player.getPosition().x * PPM) - (StandingRightTex.getWidth() / 2), player.getPosition().y * PPM - (StandingRightTex.getHeight() / 2));
            } else {
                batch.draw(JumpingLeftTex, (player.getPosition().x * PPM) - (StandingRightTex.getWidth() / 2), player.getPosition().y * PPM - (StandingRightTex.getHeight() / 2));
            }
        } else {
            if (facingRight) {
                batch.draw(StandingRightTex, (player.getPosition().x * PPM) - (StandingRightTex.getWidth() / 2), player.getPosition().y * PPM - (StandingRightTex.getHeight() / 2));
            } else {
                batch.draw(StandingLeftTex, (player.getPosition().x * PPM) - (StandingRightTex.getWidth() / 2), player.getPosition().y * PPM - (StandingRightTex.getHeight() / 2));
            }
        }
        batch.end();
    }

    public void update(float delta) {
        inputUpdate(delta);
    }

    private void inputUpdate(float delta) {
        float force = 0;

        if(controlPanel.touchLeft()) {
            if (player.getLinearVelocity().x > -6.4) {
                force = -50;
                facingRight = false;
            }
        } else if (controlPanel.touchRight()) {
            if (player.getLinearVelocity().x < 6.4) {
                force = 50;
                facingRight = true;
            }
        } else {
            force = player.getLinearVelocity().x * -10;
        }

        if(controlPanel.touchUp()) {
            if (onFloor || onElevator || onI_Wall) {
                player.applyForceToCenter(0, 650, false);
                onFloor = false;
                onElevator = false;
                onI_Wall = false;
            }
        }

        player.applyForce(new Vector2(force, 0), player.getWorldCenter(), true);
        rectangle.set((player.getPosition().x - 20) * PPM, (player.getPosition().y - 20) * PPM, 40, 40);

        checkPosition();
    }

    private void checkPosition() {
        if ((int) (player.getPosition().x * PPM - (StandingRightTex.getWidth() / 2)) <= 5) {
            if (activeRoom.roomToRender == 2) {
                isDeath = true;
                activeRoom.room3.createPlayer(608, (int) (player.getPosition().y * PPM), 10);

                activeRoom.room2.elevator.muteSounds();
                activeRoom.room2.enemy.muteSound();
                for (int i = 0; i < 4; i++) {
                    activeRoom.room3.lasers[i].playSound();
                }
                activeRoom.room3.enemy.playSound();
            } else if (activeRoom.roomToRender == 5) {
                isDeath = true;
                activeRoom.room2.createPlayer(608, (int) (player.getPosition().y * PPM), 10);

                activeRoom.room5.enemy.muteSound();
                activeRoom.room2.elevator.playSounds();
                activeRoom.room2.enemy.playSound();
            } else if (activeRoom.roomToRender == 6) {
                isDeath = true;
                activeRoom.room1.createPlayer(608, (int) (player.getPosition().y * PPM), 10);
            } else if (activeRoom.roomToRender == 7) {
                isDeath = true;
                activeRoom.room5.secret = true;
                activeRoom.room5.createPlayer(608, (int) (player.getPosition().y * PPM), 10);

                activeRoom.room7.elevator.muteSounds();
                for (int i = 0; i < 7; i++) {
                    activeRoom.room7.lasers[i].muteSound();
                }
                activeRoom.room5.enemy.playSound();
            }
        }
        if ((int) (player.getPosition().x * PPM + (StandingRightTex.getWidth() / 2)) >= CastleRunner.V_WIDTH - 5) {
            if (activeRoom.roomToRender == 1) {
                isDeath = true;
                activeRoom.room6.createPlayer(32, (int) (player.getPosition().y * PPM), 10);
            } else if (activeRoom.roomToRender == 2) {
                isDeath = true;
                activeRoom.room5.secret = true;
                activeRoom.room5.createPlayer(32, (int) (player.getPosition().y * PPM), 10);

                activeRoom.room2.elevator.muteSounds();
                activeRoom.room2.enemy.muteSound();
                activeRoom.room5.enemy.playSound();
            } else if (activeRoom.roomToRender == 3) {
                isDeath = true;
                activeRoom.room2.createPlayer(32, (int) (player.getPosition().y * PPM), 10);

                for (int i = 0; i < 4; i++) {
                    activeRoom.room3.lasers[i].muteSound();
                }
                activeRoom.room3.enemy.muteSound();
                activeRoom.room2.elevator.playSounds();
                activeRoom.room2.enemy.playSound();
            } else if (activeRoom.roomToRender == 5) {
                isDeath = true;
                activeRoom.room7.createPlayer(32, (int) (player.getPosition().y * PPM), 10);

                activeRoom.room5.enemy.muteSound();
                activeRoom.room7.elevator.playSounds();
                for (int i = 0; i < 7; i++) {
                    activeRoom.room7.lasers[i].playSound();
                }
            }
        }
        if ((int) (player.getPosition().y * PPM + (StandingRightTex.getHeight() / 2) + 64) >= CastleRunner.V_HEIGHT - 5) {
            if (activeRoom.roomToRender == 3) {
                isDeath = true;
                activeRoom.room4.createPlayer((int) (player.getPosition().x * PPM), 32 + 64, 650);

                for (int i = 0; i < 4; i++) {
                    activeRoom.room3.lasers[i].muteSound();
                }
                activeRoom.room3.enemy.muteSound();
            }
        }
        if ((int) (player.getPosition().y * PPM - (StandingRightTex.getHeight() / 2) - 64) <= 5) {
            if (activeRoom.roomToRender == 4) {
                isDeath = true;
                activeRoom.room3.createPlayer((int) (player.getPosition().x * PPM), 480 - 64, -450);

                for (int i = 0; i < 4; i++) {
                    activeRoom.room3.lasers[i].playSound();
                }
                activeRoom.room3.enemy.playSound();
            } else if (activeRoom.roomToRender == 5) {
                isDeath = true;
                activeRoom.room6.createPlayer((int) (player.getPosition().x * PPM), 480 - 64, -450);

                activeRoom.room5.enemy.muteSound();
            }
        }
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}