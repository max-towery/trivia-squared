package games;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import games.containers.HighScore;
import games.containers.Room;
import games.utils.gameUtils.ScoreUtil;
import games.screens.*;
import games.utils.dbUtils.QuestionHolder;
import games.utils.gameUtils.SoundManager;

import java.util.ArrayList;


//this class now serves as the game state manager class
public class GameStateManager extends Game {

	//application static content
	public static final String TITLE = "Trivia-Squared";
	public static final float VERSION = 1.0f;
	public static final int V_WIDTH = 1080;
	public static final int V_HEIGHT = 720;
	public static int GRIDSIZE = 5;

    //camera stuff
	public OrthographicCamera camera;
	public float cameraAcc;
    public int playerDirection;

	public SpriteBatch batch;

	//player location stuff
	public int [] startLoc;
	public int [] endLoc;
	public int [] playerLoc;
	public int [] playerIndex;

	//user info
	public String username;
	public ArrayList<HighScore> scores;

	//assets
	public BitmapFont font30, font50, font30white, pointFont, font300white, font20, timeFont, font30red;
	public AssetManager assets;

	//screens
	public LoadingState loadingScreen;
	public MainMenuState mainMenuScreen;
	public PlayState playScreen;
	public PauseState pauseScreen;
	public MapState mapScreen;
    public TriviaState triviaScreen;
	public DifficultyState difficultyScreen;
	public EndGameState endGameScreen;
	public HighScoreState highScoreScreen;
	public AdminState adminScreen;

	//room grid
	public Room[][] grid;
	public boolean gridCreated;

	//questions stuff
    public QuestionHolder holder;

	//score and points stuff
	public ScoreUtil score;

	public SoundManager soundManager;


	@Override
	public void create () {

		assets = new AssetManager();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
		batch = new SpriteBatch();
		font30 = new BitmapFont(); font50 = new BitmapFont(); font30white = new BitmapFont();
		font300white = new BitmapFont();
		pointFont = new BitmapFont();

        playerDirection = -1;
		initFonts();

		//score stuff
		score = new ScoreUtil();


		try{
            holder = QuestionHolder.getQuestionHolder();
        }
        catch(Exception e){

        }

		//location data
		startLoc = new int[2];
		endLoc = new int[2];
		playerLoc = new int[2];
		playerIndex = new int[2];
		loadingScreen = new LoadingState(this);
		pauseScreen = new PauseState(this);
		playScreen = new PlayState(this);

		//set screen state at game initialization
			this.setScreen(loadingScreen);
		cameraAcc = 4.0f;


	}

	public boolean isCameraMoving(){
		float xEstimateUp = camera.position.x + .01f;
		float xEstimateDown = camera.position.x - .01f;
		float yEstimateUp = camera.position.y + .01f;
		float yEstimateDown = camera.position.y  - .01f;
		float X = playScreen.xDest;
		float Y = playScreen.yDest;
		if (X < xEstimateUp && X > xEstimateDown){
			if (Y < yEstimateUp && Y > yEstimateDown)
				return false;
		}
		return true;
	}

	private void initFonts() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/main.TTF"));
		FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

		params.size = 30;
		params.color = Color.WHITE;
		font30 = generator.generateFont(params);
		params.size = 50;
		font50 = generator.generateFont(params);
		params.size = 30;
		params.color = Color.WHITE;
		font30white = generator.generateFont(params);
		params.size = 30;
		params.color = Color.YELLOW;
		pointFont = generator.generateFont(params);
		params.size = 300;
		params.color = Color.WHITE;
		font300white = generator.generateFont(params);
		params.size = 20;
		font20 = generator.generateFont(params);
		params.size = 30;
		params.color = Color.ORANGE;
		timeFont = generator.generateFont(params);
		params.color = Color.RED;
		font30red = generator.generateFont(params);
	}

	public void updatePlayerData(){
			if (playerDirection == 0){
				playScreen.yDest = (int) (camera.position.y + V_HEIGHT);
				playerIndex[0]++;
				playerLoc[1]+= V_HEIGHT;
			}
			else if (playerDirection == 1){
				playScreen.yDest = (int) (camera.position.y - V_HEIGHT);
				playerIndex[0]--;
				playerLoc[1]-= V_HEIGHT;
			}
			else if (playerDirection == 2){
				playScreen.xDest = (int) (camera.position.x - V_WIDTH);
				playerIndex[1]--;
				playerLoc[0]-= V_WIDTH;
			}
			else if (playerDirection == 3){
				playScreen.xDest = (int) (camera.position.x + V_WIDTH);
				playerIndex[1]++;
				playerLoc[0]+= V_WIDTH;
			}

	}

	public void updateImageData(){
		int i = playerIndex[0];
		int j = playerIndex[1];
		int k = i; int l = j;
		int failedXLoc = playerLoc[0];
		int failedYLoc = playerLoc[1];
		if (playerDirection == 0){
			grid[i][j].setTopDoor(false);
			k = i + 1;
			failedYLoc += V_HEIGHT;
			if (!(k > grid.length -1))
				grid[k][l].setBotDoor(false);
		}

		else if (playerDirection == 1) {
			grid[i][j].setBotDoor(false);
			k = i - 1;
			failedYLoc -= V_HEIGHT;
			if (!(k < 0))
				grid[k][l].setTopDoor(false);
		}

		else if (playerDirection == 2) {
			grid[i][j].setLeftDoor(false);
			l = j - 1;
			failedXLoc -= V_WIDTH;
			if (!(l < 0))
				grid[k][l].setRightDoor(false);
		}

		else if (playerDirection == 3){
			grid[i][j].setRightDoor(false);
			l = j + 1;
			failedXLoc += V_WIDTH;
			if (!(l > grid.length -1))
				grid[k][l].setLeftDoor(false);
		}
		playScreen.arrowButtons.updateArrowButtons(this);

		//set current room
		String serial = grid[i][j].serialToBinary();
		grid[i][j].setImage(new Image(assets.get("images/rooms/" + serial +".png", Texture.class)));
		grid[i][j].getImage().setBounds(playerLoc[0],playerLoc[1], GameStateManager.V_WIDTH, GameStateManager.V_HEIGHT);
		playScreen.stage.addActor(grid[i][j].getImage());

		//set neighbor room
		serial = grid[k][l].serialToBinary();
		grid[k][l].setImage(new Image(assets.get("images/rooms/" + serial +".png", Texture.class)));
		grid[k][l].getImage().setBounds(failedXLoc,failedYLoc, GameStateManager.V_WIDTH, GameStateManager.V_HEIGHT);
		playScreen.stage.addActor(grid[k][l].getImage());
		if (grid[k][l].isEnd){
			playScreen.stage.addActor(grid[k][l].getImage2());
		}



	}




	public void checkIfPlayerWon(){

		if (endLoc[0] == playerIndex[0] && endLoc[1] == playerIndex[1]){
			endGameScreen = new EndGameState(this);
			endGameScreen.setisWon(true);
			setScreen(endGameScreen);
		}
	}


	private void update(){

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
		mainMenuScreen.dispose();
		playScreen.dispose();

		if (pauseScreen != null)
			pauseScreen.dispose();
		if (mapScreen != null)
			mapScreen.dispose();
		if (triviaScreen != null)
			triviaScreen.dispose();
		if (difficultyScreen != null)
			difficultyScreen.dispose();
		if (endGameScreen != null)
			endGameScreen.dispose();
		if (highScoreScreen != null)
			highScoreScreen.dispose();
		if (adminScreen != null)
			adminScreen.dispose();
	}

}