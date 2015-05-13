package games.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import games.Application;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Application.TITLE;
		config.width = Application.V_WIDTH;
		config.height = Application.V_HEIGHT;
		config.foregroundFPS = 60;
		config.backgroundFPS = 60;
		new LwjglApplication(new Application(), config);
	}
}
