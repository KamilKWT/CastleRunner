package com.samsung.project.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.samsung.project.CastleRunner;
import com.samsung.project.Rooms.ActiveRoom;
import com.samsung.project.Screens.GameScreen;

public class Enemy extends Sprite{

    private static final float PPM = CastleRunner.PPM;

    private GameScreen screen;
    private ActiveRoom activeRoom;

    private World world;
    private Body body;
    private Texture enemyTex;
    private Animation<TextureRegion> animation;

    private Vector2 startPos;
    private Vector2 endPos;
    private boolean up_down;
    private boolean movingToEndPos;

    private float gameTime = 0;

    private Music sound;

    public Enemy(World world, GameScreen screen, ActiveRoom activeRoom, int startX, int startY, int endX, int endY, boolean up_down) {
        this.world = world;
        this.screen = screen;
        this.activeRoom = activeRoom;
        this.up_down = up_down;

        startPos = new Vector2();
        endPos = new Vector2();
        movingToEndPos = true;

        startPos.set(startX, startY);
        endPos.set(endX, endY);

        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        enemyTex = screen.game.assetsLoader.findTexture("sprites-bat");
        animation = prepareAnimation(enemyTex, 8, 1);

        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set((startX + 32) / PPM, (startY + 16) / PPM);

        body = world.createBody(bodyDef);

        shape.setAsBox(64 / 2 / PPM, 32 / 2 / PPM);

        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.filter.categoryBits = CastleRunner.ENEMY_BIT;
        fixtureDef.filter.maskBits = CastleRunner.WALL_BIT | CastleRunner.PLAYER_BIT;

        body.createFixture(fixtureDef).setUserData(this);

        sound = screen.game.assetsLoader.findSound("sound-enemy");
        sound.setLooping(true);
        sound.setVolume(screen.game.volume * 0.75f);
    }

    private Animation<TextureRegion> prepareAnimation(Texture texture, int columnFrames, int rowFrames){
        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth()/columnFrames,
                texture.getHeight()/rowFrames);
        TextureRegion[] textureFrames = new TextureRegion[rowFrames * columnFrames];
        int indeks = 0;
        for (int i = 0; i < rowFrames; i++){
            for (int j = 0; j < columnFrames; j++){
                textureFrames[indeks++] = tmp[i][j];
            }
        }
        return new Animation<TextureRegion>(0.075f, textureFrames);
    }

    public TextureRegion frameToRender(float animationTime){
        return animation.getKeyFrame(animationTime, true);
    }

    public void render(float delta, SpriteBatch batch) {
        update(delta);

        batch.begin();
        batch.draw(frameToRender(gameTime), body.getPosition().x * PPM - 32, body.getPosition().y * PPM - 16);
        batch.end();
    }

    private void update(float delta) {
        gameTime += delta;
        sound.setVolume(screen.game.volume * 0.75f);

        if (up_down) {
            if (movingToEndPos) {
                if (body.getPosition().y * PPM < endPos.y + 16) {
                    body.setLinearVelocity(0, 2f);
                } else {
                    body.setLinearVelocity(0, 0);
                    movingToEndPos = !movingToEndPos;
                }
            } else {
                if (body.getPosition().y * PPM > startPos.y + 16) {
                    body.setLinearVelocity(0, -2f);
                } else {
                    body.setLinearVelocity(0, 0);
                    movingToEndPos = !movingToEndPos;
                }
            }
        } else {
            if (movingToEndPos) {
                if (body.getPosition().x * PPM < endPos.x + 32) {
                    body.setLinearVelocity(2f, 0);
                } else {
                    body.setLinearVelocity(0, 0);
                    movingToEndPos = !movingToEndPos;
                }
            } else {
                if (body.getPosition().x * PPM > startPos.x + 32) {
                    body.setLinearVelocity(-2f, 0);
                } else {
                    body.setLinearVelocity(0, 0);
                    movingToEndPos = !movingToEndPos;
                }
            }
        }
    }

    public void onHit() {
        if (activeRoom.roomToRender == 2) {
            if (!activeRoom.room2.player.isDeath) {
                if (screen.hud.lives == 0) {
                    screen.win = false;
                    screen.game.setScreen(screen.endScreen);

                    activeRoom.room2.elevator.muteSounds();
                    activeRoom.room2.enemy.muteSound();
                    return;
                }
                screen.hud.changeLives(false);
                activeRoom.room2.player.isDeath = true;
                activeRoom.room1.createPlayer(96, 116, 0);

                activeRoom.room2.elevator.muteSounds();
                activeRoom.room2.enemy.muteSound();
                activeRoom.room2.player.sound.setVolume(screen.game.volume);
                activeRoom.room2.player.sound.play();
            }
        } else if (activeRoom.roomToRender == 3) {
            if (!activeRoom.room3.player.isDeath) {
                if (screen.hud.lives == 0) {
                    screen.win = false;
                    screen.game.setScreen(screen.endScreen);

                    for (int i = 0; i < 4; i++) {
                        activeRoom.room3.lasers[i].muteSound();
                    }
                    activeRoom.room3.enemy.muteSound();
                    return;
                }
                screen.hud.changeLives(false);
                activeRoom.room3.player.isDeath = true;
                activeRoom.room1.createPlayer(96, 116, 0);

                for (int i = 0; i < 4; i++) {
                    activeRoom.room3.lasers[i].muteSound();
                }
                activeRoom.room3.enemy.muteSound();
                activeRoom.room3.player.sound.setVolume(screen.game.volume);
                activeRoom.room3.player.sound.play();
            }
        } else if (activeRoom.roomToRender == 5) {
            if (!activeRoom.room5.player.isDeath) {
                if (screen.hud.lives == 0) {
                    screen.win = false;
                    screen.game.setScreen(screen.endScreen);

                    activeRoom.room5.enemy.muteSound();
                    return;
                }
                screen.hud.changeLives(false);
                activeRoom.room5.player.isDeath = true;
                activeRoom.room1.createPlayer(96, 116, 0);

                activeRoom.room5.enemy.muteSound();
                activeRoom.room5.player.sound.setVolume(screen.game.volume);
                activeRoom.room5.player.sound.play();
            }
        }
    }

    public void muteSound() {
        sound.stop();
    }

    public void playSound() {
        sound.setVolume(screen.game.volume * 0.75f);
        sound.play();
    }
}