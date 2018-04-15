package com.samsung.project.Rooms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.samsung.project.Sprites.Enemy;
import com.samsung.project.Tools.Builders.ObjectsBuilder;
import com.samsung.project.CastleRunner;
import com.samsung.project.Objects.InteractiveObjects.I_Wall;
import com.samsung.project.Objects.InteractiveObjects.Laser;
import com.samsung.project.Objects.Walls;
import com.samsung.project.Scenes.ControlPanel;
import com.samsung.project.Screens.GameScreen;
import com.samsung.project.Sprites.Player;
import com.samsung.project.Tools.WorldContactListener;

public class Room3 {

    private static final float PPM = CastleRunner.PPM;

    private CastleRunner game;
    private ObjectsBuilder objectsBuilder;
    private ControlPanel controlPanel;
    private ActiveRoom activeRoom;

    private GameScreen screen;
    private OrthographicCamera camera;

    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;

    private Box2DDebugRenderer renderer;
    private World world;
    public Player player;
    public Rectangle rectangle;

    public Laser[] lasers;
    private I_Wall[] i_walls;

    public Enemy enemy;

    public Room3(CastleRunner game, OrthographicCamera camera, GameScreen screen, ControlPanel controlPanel, ActiveRoom activeRoom) {
        this.game = game;
        this.camera = camera;
        this.screen = screen;
        this.controlPanel = controlPanel;
        this.activeRoom = activeRoom;

        world = new World(new Vector2(0, -9.8f), false);
        renderer = new Box2DDebugRenderer();

        world.setContactListener(new WorldContactListener());

        map = game.assetsLoader.findMap("map-room_3");
        tmr = new OrthogonalTiledMapRenderer(map);

        objectsBuilder = new ObjectsBuilder(screen, world, map, activeRoom);

        Walls.getWalls(world, map.getLayers().get("Floors - obj").getObjects(), "Floor");
        Walls.getWalls(world, map.getLayers().get("Walls - obj").getObjects(), "Wall");
        objectsBuilder.generateCoins();
        objectsBuilder.generateCrowns();
        objectsBuilder.generateKeys(1);
        lasers = objectsBuilder.generateLasers(true);
        i_walls = objectsBuilder.generateI_Walls(false);

        enemy = new Enemy(world, screen, activeRoom, 224, 160, 544, 160, false);
    }

    public void createPlayer(int playerX, int playerY, int jump) {
        activeRoom.roomToRender = 3;
        screen.rooms[2] = true;
        player = new Player(screen, world, activeRoom, controlPanel, playerX, playerY);
        if (jump != 0) {
            player.player.applyForceToCenter(0, jump, false);
        }
    }

    public void render(float delta, SpriteBatch batch) {
        update(Gdx.graphics.getDeltaTime());

        batch.setProjectionMatrix(camera.combined);
        renderer.render(world, camera.combined.scl(PPM));

        tmr.render();
        player.render(delta, batch);
        enemy.render(delta, batch);
    }

    public void update(float delta) {
        world.step(1 / 60f, 6, 2);

        if (player.isDeath) {
            player.player.setActive(false);
        }

        camera.update();
        tmr.setView(camera);
        rectangle = player.getRectangle();

        for (int i = 0; i < 4; i++) {
            lasers[i].update(delta, 3);
            i_walls[i].update(delta, 4, false);
        }
    }

    public void dispose() {
        world.dispose();
        renderer.dispose();
        tmr.dispose();
        map.dispose();
    }
}