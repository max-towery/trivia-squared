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
import games.containers.Room;
import games.containers.Score;
import games.screens.*;
import games.utils.QuestionHolder;

import java.util.Calendar;


//this class now serves as the game state manager class
public class Application extends Game {

	//application static content
	public static final String TITLE = "Trivia-Squared";
	public static final float VERSION = 0.1f;
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

	//assets
	public BitmapFont font30, font50, font30white, pointFont, font300white, font20, timeFont;
	public AssetManager assets;

	//screens
	public LoadingScreen loadingScreen;
	public MainMenuScreen mainMenuScreen;
	public PlayScreen playScreen;
	public PauseScreen pauseScreen;
	public MapScreen mapScreen;
    public TriviaScreen triviaScreen;
	public DifficultyScreen difficultyScreen;
	public EndGameScreen endGameScreen;

	//room grid
	public Room[][] grid;
	public boolean gridCreated;

	//questions stuff
    public QuestionHolder holder;

	//score and points stuff
	public Score score;
	public Calendar startCal;
	public Calendar currentCal;
	public boolean atLeastOneMinute;

	public int startTime, currentTime;
	int curMin;


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
		score = new Score(1);
		curMin = 0;
		atLeastOneMinute = false;


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
		loadingScreen = new LoadingScreen(this);
		playScreen = new PlayScreen(this);
		pauseScreen = new PauseScreen(this);

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

		String serial = grid[i][j].serialToBinary();
		grid[i][j].setImage(new Image(assets.get("images/rooms/" + serial +".png", Texture.class)));
		grid[i][j].getImage().setBounds(playerLoc[0],playerLoc[1], Application.V_WIDTH, Application.V_HEIGHT);
		playScreen.stage.addActor(grid[i][j].getImage());

		serial = grid[k][l].serialToBinary();
		grid[k][l].setImage(new Image(assets.get("images/rooms/" + serial +".png", Texture.class)));
		grid[k][l].getImage().setBounds(failedXLoc,failedYLoc, Application.V_WIDTH, Application.V_HEIGHT);
		playScreen.stage.addActor(grid[k][l].getImage());



	}


	public void displayTime(BitmapFont font, int x, int y){

			currentCal = Calendar.getInstance();
			currentTime = (int)currentCal.getTimeInMillis() /1000;
			if (currentTime - startTime >= 60){
				curMin += 1;

				startCal = Calendar.getInstance();
				startTime = (int)startCal.getTimeInMillis() /1000;
				atLeastOneMinute = true;
			}

			int timeInSeconds = currentTime - startTime;
			String timeInSecondsToString = String.valueOf(timeInSeconds);

			if (timeInSeconds < 10){
				timeInSecondsToString = "0" + timeInSecondsToString;
			}
			if (atLeastOneMinute){
				font.draw(batch, "Time: " + curMin + ":"+ String.valueOf(timeInSecondsToString), x, y);

			}
			else{
				font.draw(batch, "Time: " + String.valueOf(timeInSecondsToString), x, y);
			}

	}

	public void checkIfPlayerWon(){

		System.out.println("not in if");
		if (endLoc[0] == playerIndex[0] && endLoc[1] == playerIndex[1]){
			System.out.println("in if");
			endGameScreen = new EndGameScreen(this);
			endGameScreen.isWon = true;
			setScreen(endGameScreen);


		}
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
	}
}
