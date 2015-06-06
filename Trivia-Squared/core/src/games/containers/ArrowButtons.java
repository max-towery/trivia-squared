package games.containers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import games.Application;
import games.camera.CameraStyles;
import games.screens.TriviaScreen;
import games.utils.RoomGenerator;

/**
 * Created by Max Towery on 5/30/2015.
 */
public class ArrowButtons {
    private Skin skin;
    private TextureAtlas buttonAtlas;

    private Button.ButtonStyle [] bs; //0 = up, 1 = down, 2 = left, 3 = right
    private Button buttons [];

    public ArrowButtons(final Application app){

        //init stuff
        skin = new Skin();
        buttonAtlas = app.assets.get("buttons/arrows.pack", TextureAtlas.class);
        skin.addRegions(buttonAtlas);
        bs = new Button.ButtonStyle[4];
        buttons = new Button[4];

        for(int i = 0; i < 4; i++){
            bs[i] = new Button.ButtonStyle();
        }

        bs[0].up = skin.getDrawable("uau");
        bs[0].down = skin.getDrawable("uad");

        bs[1].up = skin.getDrawable("dau");
        bs[1].down = skin.getDrawable("dad");

        bs[2].up = skin.getDrawable("lau");
        bs[2].down = skin.getDrawable("lad");

        bs[3].up = skin.getDrawable("rau");
        bs[3].down = skin.getDrawable("rad");

        for (int i = 0; i < 4; i++){
            buttons[i] = new Button(bs[i]);
        }

        addListeners(app);

    }
    public Button [] getButtons(){return buttons;}

    public void updateArrowButtons(final Application app){
            int i = app.playerIndex[0];
            int j = app.playerIndex[1];

            if (!app.isCameraMoving()){

                if (!app.grid[i][j].isTopDoor())
                    buttons[0].setSize(0,0);
                else
                    buttons[0].setSize(74,85);
                if (!app.grid[i][j].isBotDoor())
                    buttons[1].setSize(0,0);
                else
                    buttons[1].setSize(74,85);
                if (!app.grid[i][j].isLeftDoor())
                    buttons[2].setSize(0,0);
                else
                    buttons[2].setSize(84,74);
                if (!app.grid[i][j].isRightDoor())
                    buttons[3].setSize(0,0);
                else
                    buttons[3].setSize(84,74);

                //up
                buttons[0].setX(app.camera.position.x - 35);
                buttons[0].setY(app.camera.position.y + 145);
                //down
                buttons[1].setX(app.camera.position.x - 35);
                buttons[1].setY(app.camera.position.y - 230);
                //left
                buttons[2].setX(app.camera.position.x - 350);
                buttons[2].setY(app.camera.position.y - 35);
                //right
                buttons[3].setX(app.camera.position.x + 265);
                buttons[3].setY(app.camera.position.y - 35);
            }
            else{
                for(int k = 0; k < 4; k++)
                    buttons[k].setSize(0,0);
            }

    }
        private void addListeners(final Application app){
            buttons[0].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    app.playerDirection = 0;
                    if (app.grid[app.playerIndex[0]+1][app.playerIndex[1]].question.answeredCorrectly){
                        app.updatePlayerData();
                    }
                    else{
                        app.triviaScreen = new TriviaScreen(app);
                        app.playScreen.mapButton.hide();
                        hide();
                        app.setScreen(app.triviaScreen);
                    }
                }
            });

            buttons[1].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    app.playerDirection = 1;
                    if (app.grid[app.playerIndex[0]-1][app.playerIndex[1]].question.answeredCorrectly){
                        app.updatePlayerData();
                    }
                    else{

                        app.triviaScreen = new TriviaScreen(app);
                        app.playScreen.mapButton.hide();
                        hide();
                        app.setScreen(app.triviaScreen);
                    }
                }
            });

            buttons[2].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    app.playerDirection = 2;
                    if (app.grid[app.playerIndex[0]][app.playerIndex[1]-1].question.answeredCorrectly){
                        app.updatePlayerData();
                    }
                    else{
                        app.triviaScreen = new TriviaScreen(app);
                        app.playScreen.mapButton.hide();
                        hide();
                        app.setScreen(app.triviaScreen);
                    }
                }
            });

            buttons[3].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    app.playerDirection = 3;
                    if (app.grid[app.playerIndex[0]][app.playerIndex[1]+1].question.answeredCorrectly){
                        app.updatePlayerData();
                    }
                    else{
                        app.grid[app.playerIndex[0]][app.playerIndex[1]+1].question.asked();
                        app.triviaScreen = new TriviaScreen(app);
                        app.playScreen.mapButton.hide();
                        hide();
                        app.setScreen(app.triviaScreen);
                    }
                }
            });
        }

        public void hide(){
            for(int i = 0; i < this.buttons.length; i++){
                buttons[i].setSize(0,0);
            }
        }
}