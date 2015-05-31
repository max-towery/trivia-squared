package games.camera;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Max Towery on 5/30/2015.
 */
public class CameraStyles {

    public static final float LERP = .05f;

    public static void lerpToTarget(Camera camera, int xDest, int yDest){

        camera.position.lerp(new Vector3(xDest, yDest, 0), LERP);
        camera.update();
    }


}
