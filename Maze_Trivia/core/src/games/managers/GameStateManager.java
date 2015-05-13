package games.managers;

import games.Application;
import games.states.GameState;
import games.states.PlayState;
import games.states.SplashState;

import java.util.Stack;

/**
 * Created by Max Towery on 5/12/2015.
 */
public class GameStateManager {
    // Application Reference
    private final Application app;

    private Stack<GameState> states;

    public enum State {
        SPLASH,
        PLAY,
        MENU,
        CREDIT,
    }

    public GameStateManager(final Application app){
        this.app = app;
        this.states = new Stack<GameState>();
        this.setState(State.SPLASH);
    }

    public Application application(){
        return app;
    }

    public void update(float delta){
        states.peek().update(delta);
    }

    public void render(){
        states.peek().render();
    }

    public void dispose(){
        for (GameState gs: states){
            gs.dispose();
        }
        states.clear();
    }

    public void resize(int w, int h){
        states.peek().resize(w, h);
    }

    public void setState(State state) {
        if (states.size() >= 1){
            states.pop().dispose();
        }
        states.push(getState(state));
    }

    private GameState getState(State state){
        switch(state){

            case SPLASH: return new SplashState(this);
            case PLAY: return new PlayState(this);
            case MENU: return null;
            case CREDIT: return null;
        }
        return null;
    }
}
