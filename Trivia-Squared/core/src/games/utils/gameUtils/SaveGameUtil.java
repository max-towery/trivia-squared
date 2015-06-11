package games.utils.gameUtils;

import games.GameStateManager;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Max Towery on 6/8/2015.
 */
public class SaveGameUtil {

    private static String filehandle = "textfiles/savegame.txt";

    public static void saveGame(GameStateManager app) throws IOException {

        FileUtil.openFileForRead(filehandle);
        FileWriter fileWriter = new FileWriter(filehandle, false);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(app.score);
    }
}
