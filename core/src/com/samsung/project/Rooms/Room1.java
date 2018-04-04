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
import com.samsung.project.Builders.ObjectsBuilder;
import com.samsung.project.CastleRunner;
import com.samsung.project.Objects.Walls;
import com.samsung.project.Scenes.ControlPanel;
import com.samsung.project.Screens.GameScreen;
import com.samsung.project.Sprites.Player;
import com.samsung.project.Tools.WorldContactListener;

public class Room1 {

    private static final float PPM = CastleRunner.PPM;

    private CastleRunner game;
    private ObjectsBuilder objectsBuilder;

    private OrthographicCamera camera;

    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;

    private Box2DDebugRenderer renderer;
    private World world;
    public Player player;
    public Rectangle rectangle;

    public Room1(CastleRunner game, OrthographicCamera camera, GameScreen screen, ControlPanel controlPanel) {
        this.game = game;
        this.camera = camera;

        world = new World(new Vector2(0, -9.8f), false);
        renderer = new Box2DDebugRenderer();

        world.setContactListener(new WorldContactListener());

        map = new TmxMapLoader().load("maps/room_1.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);

        objectsBuilder = new ObjectsBuilder(screen, world, map);

        player = new Player(world, controlPanel);

        Walls.getWalls(world, map.getLayers().get("Walls - obj").getObjects());
        objectsBuilder.generateCoins();
        objectsBuilder.generatePipes();
    }

    public void render(float delta, SpriteBatch batch) {
        update(Gdx.graphics.getDeltaTime());

        tmr.render();

        batch.setProjectionMatrix(camera.combined);
        player.render(delta, batch);

        renderer.render(world, camera.combined.scl(PPM));
    }

    public void update(float delta) {
        world.step(1 / 60f, 6, 2);
        camera.update();
        tmr.setView(camera);
        rectangle = player.getRectangle();
    }

    public void dispose() {
        world.dispose();
        renderer.dispose();
        tmr.dispose();
        map.dispose();
    }
}