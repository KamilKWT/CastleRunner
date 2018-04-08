package com.samsung.project.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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

    private Image btnUp;
    private Image btnLeft;
    private Image btnRight;

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

        touchPos = new Vector2();

        btnUpImg = new Texture(Gdx.files.internal("images/btn-up.png"));
        btnLeftImg = new Texture(Gdx.files.internal("images/btn-left.png"));
        btnRightImg = new Texture(Gdx.files.internal("images/btn-right.png"));

        btnUp = createButton(CastleRunner.V_WIDTH * 0.125f, btnUpImg);
        btnLeft = createButton((CastleRunner.V_WIDTH * 0.875f) - 25, btnLeftImg);
        btnRight = createButton((CastleRunner.V_WIDTH * 0.875f) + 25, btnRightImg);
        btnUp.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Touch", "touchDown() UP");
                return true;
            }
        });

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

    public boolean touchUp() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            return true;
        } else {
            if (Gdx.input.justTouched()) {
                touchPos.set(Gdx.input.getX(), Gdx.input.getY());
                viewport.unproject(touchPos);

                if (isBtnTouch(btnUp, touchPos)) {
                    Gdx.app.log("Touch", "UP");
                    return true;
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
                        Gdx.app.log("Touch", "LEFT");
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
                        Gdx.app.log("Touch", "RIGHT");
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
