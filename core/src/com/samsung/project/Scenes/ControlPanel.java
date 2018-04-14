package com.samsung.project.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.samsung.project.CastleRunner;

public class ControlPanel {

    private CastleRunner game;

    public OrthographicCamera camera;
    private Viewport viewport;
    private Viewport gameViewport;
    public Stage stage;

    private Image btnMenu;
    private Image btnUp;
    private Image btnLeft;
    private Image btnRight;

    private Texture btnMenuImg;
    private Texture btnUpImg;
    private Texture btnLeftImg;
    private Texture btnRightImg;

    private Vector2 touchPos;

    public ControlPanel(SpriteBatch batch, CastleRunner game, Viewport gameViewport) {
        this.game = game;
        this.gameViewport = gameViewport;

        camera = new OrthographicCamera();
        viewport = new FitViewport(CastleRunner.V_WIDTH, CastleRunner.V_HEIGHT, camera);
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        touchPos = new Vector2();

        btnMenuImg = new Texture("images/btn/menu.png");
        btnUpImg = new Texture("images/btn/up.png");
        btnLeftImg = new Texture("images/btn/left.png");
        btnRightImg = new Texture("images/btn/right.png");

        btnMenu = createButton(CastleRunner.V_WIDTH * 0.5f, btnMenuImg);
        btnUp = createButton(CastleRunner.V_WIDTH * 0.125f, btnUpImg);
        btnLeft = createButton((CastleRunner.V_WIDTH * 0.875f) - 75, btnLeftImg);
        btnRight = createButton((CastleRunner.V_WIDTH * 0.875f) + 25, btnRightImg);

        stage.addActor(btnMenu);
        stage.addActor(btnUp);
        stage.addActor(btnLeft);
        stage.addActor(btnRight);

        stage.draw();
    }

    public Image createButton(float x, Texture texture) {
        Image btn = new Image(texture);
        btn.setPosition(x - (btn.getWidth() / 2), 32f - (btn.getHeight() / 2));
        return btn;
    }

    public boolean isJustTouched() {
        if (Gdx.input.justTouched()) {
            return true;
        }
        return false;
    }

    public boolean touchMenu() {
        for (int i = 0; i < 2; i++) {
            if (Gdx.input.isTouched(i)) {
                touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i));
                viewport.unproject(touchPos);

                if (isBtnTouch(btnMenu, touchPos)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean touchUp() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            return true;
        } else {
            for (int i = 0; i < 2; i++) {
                if (Gdx.input.isTouched(i) && isJustTouched()) {
                    touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i));
                    viewport.unproject(touchPos);

                    if (isBtnTouch(btnUp, touchPos)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean touchLeft() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            return true;
        } else {
            for (int i = 0; i < 2; i++) {
                if (Gdx.input.isTouched(i)) {
                    touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i));
                    viewport.unproject(touchPos);

                    if (isBtnTouch(btnLeft, touchPos)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean touchRight() {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            return true;
        } else {
            for (int i = 0; i < 2; i++) {
                if (Gdx.input.isTouched(i)) {
                    touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i));
                    viewport.unproject(touchPos);

                    if (isBtnTouch(btnRight, touchPos)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isBtnTouch(Image btn, Vector2 touchPosition) {
        return ((touchPosition.x >= btn.getX()
                && touchPosition.x <= btn.getX() + btn.getWidth())
                && (touchPosition.y >= btn.getY()
                && touchPosition.y <= btn.getY() + btn.getHeight()));
    }
}