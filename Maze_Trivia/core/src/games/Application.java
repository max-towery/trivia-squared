package games;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import games.managers.GameStateManager;

public class Application extends ApplicationAdapter {


	// Game Info
	public static final String TITLE = "Tutorial";
	public static final int V_WIDTH  = 1080;
	public static final int V_HEIGHT = 720;
	private final float SCALE = 2.0f;

	private OrthographicCamera camera;
	private SpriteBatch batch;

	private GameStateManager gsm;

	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w / SCALE, h / SCALE);

		gsm = new GameStateManager(this);

	}

	@Override
	public void render () {
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render();

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();
	}

	@Override
	public void resize(int width, int height){
		gsm.resize((int)(width / SCALE), (int) (height / SCALE));

	}

	@Override
	public void dispose(){
		gsm.dispose();
		batch.dispose();

	}

	public OrthographicCamera getCamera(){
		return camera;
	}

	public SpriteBatch getBatch(){
		return batch;
	}
}
