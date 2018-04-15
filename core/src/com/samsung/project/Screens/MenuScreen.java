package com.samsung.project.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.samsung.project.CastleRunner;

public class MenuScreen extends AbstractScreen {

    private CastleRunner game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private GameScreen gameScreen;
    private MenuScreen menuScreen;

    private SpriteBatch batch;
    private Stage stage;

    private OrthogonalTiledMapRenderer tmr;
    private TiledMap mainMap;
    private TiledMap menuMap;

    private Image btnPlay_Resume;
    private Image btnHelp;
    private Image btnSnd_On_Off;
    private Image btnExit_Main;

    private Texture btnPlayImg;
    private Texture btnResumeImg;
    private Texture btnHelpImg;
    private Texture btnSnd_OnImg;
    private Texture btnSnd_OffImg;
    private Texture btnExitImg;
    private Texture btnMainImg;

    private boolean newGame;
    public boolean sounds;

    public MenuScreen(CastleRunner game, OrthographicCamera camera, Viewport viewport, GameScreen gameScreen, boolean newgame, boolean sounds) {
        this.game = game;
        this.camera = camera;
        this.viewport = viewport;
        this.newGame = newgame;
        this.sounds = sounds;
        menuScreen = this;

        if (newgame) {
            this.gameScreen = new GameScreen(game, game.gameCamera, game.gameViewport);
        } else {
            this.gameScreen = gameScreen;
        }

        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        btnPlayImg = game.assetsLoader.findTexture("btn-play");
        btnResumeImg = game.assetsLoader.findTexture("btn-resume");
        btnHelpImg = game.assetsLoader.findTexture("btn-help");
        btnSnd_OnImg = game.assetsLoader.findTexture("btn-snd_on");
        btnSnd_OffImg = game.assetsLoader.findTexture("btn-snd_off");
        btnExitImg = game.assetsLoader.findTexture("btn-exit");
        btnMainImg = game.assetsLoader.findTexture("btn-main");

        mainMap = game.assetsLoader.findMap("map-main");
        menuMap = game.assetsLoader.findMap("map-menu");
    }

    @Override
    public void show() {
        if (newGame) {
            btnPlay_Resume = createButton(CastleRunner.V_HEIGHT / 2 + 108, btnPlayImg);
            btnExit_Main = createButton(CastleRunner.V_HEIGHT / 2 - 108, btnExitImg);
            tmr = new OrthogonalTiledMapRenderer(mainMap);
        } else {
            btnPlay_Resume = createButton(CastleRunner.V_HEIGHT / 2 + 108, btnResumeImg);
            btnExit_Main = createButton(CastleRunner.V_HEIGHT / 2 - 108, btnMainImg);
            tmr = new OrthogonalTiledMapRenderer(menuMap);
        }
        btnHelp = createButton(CastleRunner.V_HEIGHT / 2 + 36, btnHelpImg);
        if (sounds) {
            btnSnd_On_Off = createButton(CastleRunner.V_HEIGHT / 2 - 36, btnSnd_OnImg);
        } else {
            btnSnd_On_Off = createButton(CastleRunner.V_HEIGHT / 2 - 36, btnSnd_OffImg);
        }
        gameScreen.sounds = sounds;

        setListeners();

        stage.addActor(btnPlay_Resume);
        stage.addActor(btnHelp);
        stage.addActor(btnSnd_On_Off);
        stage.addActor(btnExit_Main);
    }

    public Image createButton(float y, Texture texture) {
        Image btn = new Image(texture);
        btn.setPosition(CastleRunner.V_WIDTH / 2 - btn.getWidth() / 2, y - (btn.getHeight() / 2));
        return btn;
    }

    public void setListeners() {
        btnPlay_Resume.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(gameScreen);
                if (gameScreen.activeRoom.roomToRender == 2) {
                    gameScreen.activeRoom.room2.elevator.playSounds();
                    gameScreen.activeRoom.room2.enemy.playSound();
                } else if (gameScreen.activeRoom.roomToRender == 3) {
                    for (int i = 0; i < 4; i++) {
                        gameScreen.activeRoom.room3.lasers[i].playSound();
                    }
                    gameScreen.activeRoom.room3.enemy.playSound();
                } else if (gameScreen.activeRoom.roomToRender == 5) {
                    gameScreen.activeRoom.room5.enemy.playSound();
                } else if (gameScreen.activeRoom.roomToRender == 7) {
                    gameScreen.activeRoom.room7.elevator.playSounds();
                    for (int i = 0; i < 7; i++) {
                        gameScreen.activeRoom.room7.lasers[i].playSound();
                    }
                }
                return true;
            }
        });

        btnHelp.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new HelpScreen(game, menuScreen, batch));
                return true;
            }
        });

        btnSnd_On_Off.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                sounds = !sounds;
                if (sounds) {
                    game.music.play();
                    game.volume = 1f;
                } else {
                    game.music.stop();
                    game.volume = 0f;
                }
                show();
                gameScreen.sounds = sounds;
                return true;
            }
        });

        btnExit_Main.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (newGame) {
                    Gdx.app.exit();
                } else {
                    dispose();
                    game.setScreen(new MenuScreen(game, game.gameCamera, game.gameViewport, null, true, sounds));
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1686274509803922f, 0.1686274509803922f, 0.1686274509803922f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        tmr.render();
        stage.draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
    }

    public void update(float delta) {
        camera.update();
        tmr.setView(camera);
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}