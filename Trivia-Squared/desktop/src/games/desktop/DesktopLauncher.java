package games.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import games.GameStateManager;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = GameStateManager.TITLE + " v" + GameStateManager.VERSION;
		config.width = GameStateManager.V_WIDTH;
		config.height = GameStateManager.V_HEIGHT;
		config.backgroundFPS = 60;
		config.foregroundFPS = 60;
		config.resizable = false;
		new LwjglApplication(new GameStateManager(), config);

	}

}
