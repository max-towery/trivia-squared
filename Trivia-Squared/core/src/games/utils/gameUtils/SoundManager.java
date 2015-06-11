package games.utils.gameUtils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import games.GameStateManager;

/**
 * Created by Max Towery on 6/7/2015.
 */
public class SoundManager {

    public Sound click;
    public Music music;

    public SoundManager(final GameStateManager app) {
        click = app.assets.get("sounds/buttonclick.mp3");
        music = app.assets.get("music/techno.mp3");
    }

    public void buttonSound(GameStateManager app){
        this.click.play();
    }

    public void playMusic(GameStateManager app){
        this.music.setLooping(true);
        this.music.play();

    }


}
