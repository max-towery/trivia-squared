package games.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import games.managers.GameStateManager;

/**
 * Created by Max Towery on 5/12/2015.
 */
public class SplashState extends GameState {

    float acc = 0f;
    Texture tex;


    public SplashState(GameStateManager gsm) {
        super(gsm);
        tex = new Texture("Images/splash.png");
    }

    @Override
    public void update(float delta) {
        this.acc += delta;
        if (acc >= 3){
            gsm.setState(GameStateManager.State.PLAY);
        }

    }

    @Override
    public void render() {
        //render
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(tex, Gdx.graphics.getWidth() / 4 - tex.getWidth() / 2,
                Gdx.graphics.getHeight() / 4 - tex.getHeight() / 2);
        batch.end();
    }

    @Override
    public void dispose() {
        tex.dispose();
    }
}
