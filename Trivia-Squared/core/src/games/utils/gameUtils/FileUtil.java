package games.utils.gameUtils;



import java.awt.*;
import java.io.*;
import java.util.Scanner;

/**
 * Created by Max Towery on 6/7/2015.
 */
public class FileUtil {


    private static final String filehandle = "docs/usermanual.pdf";

    public static Scanner openFileForRead(String filehandle) throws IOException {
        Scanner fin;
        try{
            fin = new Scanner(new File(filehandle));
        }
        catch(Exception e){
            new FileOutputStream(filehandle, false).close();
            fin = new Scanner(new File(filehandle));
        }
        return fin;
    }


    public static void openUserManual() throws IOException {

        try{
            Desktop file = Desktop.getDesktop();
            file.open(new File(filehandle));
        }
        catch(IllegalArgumentException e){
        }

    }

}
