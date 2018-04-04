package com.samsung.project.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.samsung.project.CastleRunner;

public class ControlPanel {

    private CastleRunner game;

    public OrthographicCamera camera;
    private Viewport viewport;
    public Stage stage;

    private Image btnUp;
    private Image btnLeft;
    private Image btnRight;

    private Texture btnUpImg;
    private Texture btnLeftImg;
    private Texture btnRightImg;

    private int touchX = 0;
    private int touchY = 0;

    public ControlPanel(SpriteBatch batch, CastleRunner game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new FitViewport(CastleRunner.V_WIDTH, CastleRunner.V_HEIGHT, camera);
        stage = new Stage(viewport, batch);

        btnUpImg = new Texture(Gdx.files.internal("images/btn-up.png"));
        btnLeftImg = new Texture(Gdx.files.internal("images/btn-left.png"));
        btnRightImg = new Texture(Gdx.files.internal("images/btn-right.png"));

        stage.addActor(btnUp = createButton(CastleRunner.V_WIDTH * 0.125f, btnUpImg));
        stage.addActor(btnLeft = createButton((CastleRunner.V_WIDTH * 0.875f) - 25, btnLeftImg));
        stage.addActor(btnRight = createButton((CastleRunner.V_WIDTH * 0.875f) + 25, btnRightImg));

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
                touchX = Gdx.input.getX();
                touchY = Gdx.input.getY();
                if (isBtnTouch(btnUp, touchX, touchY)) {
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
                    touchX = Gdx.input.getX(i);
                    touchY = Gdx.input.getY(i);

                    if (isBtnTouch(btnLeft, touchX, touchY)) {
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
                    touchX = Gdx.input.getX(i);
                    touchY = Gdx.input.getY(i);

                    if (isBtnTouch(btnRight, touchX, touchY)) {
                        Gdx.app.log("Touch", "UP");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isBtnTouch(Image btn, int x, int y) {
        return ((x >= game.convertToRealPosition(btn.getX(), true) && x <= game.convertToRealPosition(btn.getX() + btn.getWidth(), true)) && (y >= game.convertToRealPosition(btn.getY(), false) && y >= game.convertToRealPosition(btn.getY() + btn.getHeight(), false)));
    }
}
