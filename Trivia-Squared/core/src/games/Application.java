package games;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import games.containers.Room;
import games.containers.Score;
import games.screens.*;


//this class now serves as the game state manager class
public class Application extends Game {

	public static final String TITLE = "Trivia-Squared";
	public static final float VERSION = 0.1f;
	public static final int V_WIDTH = 720;
	public static final int V_HEIGHT = 480;


	public OrthographicCamera camera;
	public float cameraAcc;
	public SpriteBatch batch;

	public int [] startLoc;
	public int [] endLoc;
	public int [] playerLoc;
	public int [] playerIndex;

	public BitmapFont font30, font50, font30white, pointFont;
	public BitmapFont font300white;
	public AssetManager assets;

	public LoadingScreen loadingScreen;
	public SplashScreen splashScreen;
	public MainMenuScreen mainMenuScreen;
	public PlayScreen playScreen;
	public PauseScreen pauseScreen;
	public MapScreen mapScreen;

	public Room[][] grid;
	public boolean gridCreated;


	public Score score;


	@Override
	public void create () {
		assets = new AssetManager();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
		batch = new SpriteBatch();
		font30 = new BitmapFont(); font50 = new BitmapFont(); font30white = new BitmapFont();
		font300white = new BitmapFont();
		pointFont = new BitmapFont();

		initFonts();

		score = new Score(1);

		//location data
		startLoc = new int[2];
		endLoc = new int[2];
		playerLoc = new int[2];
		playerIndex = new int[2];

		loadingScreen = new LoadingScreen(this);
		splashScreen = new SplashScreen(this);
		playScreen = new PlayScreen(this);
		mainMenuScreen = new MainMenuScreen(this);
		pauseScreen = new PauseScreen(this);


		//set screen state at game initialization
		this.setScreen(loadingScreen);

		cameraAcc = 4.0f;

	}

	public boolean isCameraMoving(){
		if (cameraAcc >= 3.25){
			cameraAcc = 0.0f;
			return false;
		}
		return true;
	}

	private void initFonts() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/main.TTF"));
		FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

		params.size = 30;
		params.color = Color.BLUE;
		font30 = generator.generateFont(params);
		params.size = 50;
		font50 = generator.generateFont(params);
		params.size = 30;
		params.color = Color.WHITE;
		font30white = generator.generateFont(params);
		params.size = 30;
		params.color = Color.BLACK.YELLOW;
		pointFont = generator.generateFont(params);
		params.size = 300;
		params.color = Color.BLACK.WHITE;
		font300white = generator.generateFont(params);
	}




	@Override
	public void render () {
		super.render();

		//for testing purposes (delete this once game implemented
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			Gdx.app.exit();
		}


	}

	@Override
	public void dispose(){
		batch.dispose();
		font30.dispose();
		font50.dispose();
		assets.dispose();
		loadingScreen.dispose();
		splashScreen.dispose();
		mainMenuScreen.dispose();
		playScreen.dispose();
	}
}
