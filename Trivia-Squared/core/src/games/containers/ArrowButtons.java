package games.containers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import games.Application;
import games.camera.CameraStyles;
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

    public void updateArrowButtons(final Application app, float delta){

            if (app.playerIndex[0] == RoomGenerator.GRIDSIZE -1)
                buttons[0].setSize(0,0);
            else
                buttons[0].setSize(74,85);
            if (app.playerIndex[0] == 0)
                buttons[1].setSize(0,0);
            else
                buttons[1].setSize(74,85);
            if (app.playerIndex[1] == 0)
                buttons[2].setSize(0,0);
            else
                buttons[2].setSize(84,74);
            if (app.playerIndex[1] == RoomGenerator.GRIDSIZE - 1)
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


    private void addListeners(final Application app){
        buttons[0].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!app.isCameraMoving()){
                    app.playScreen.yDest = (int) (app.camera.position.y + app.V_HEIGHT);
                    app.score.increase();
                    app.playerIndex[0]++;
                }
            }
        });

        buttons[1].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!app.isCameraMoving()){
                    app.playScreen.yDest = (int) (app.camera.position.y - app.V_HEIGHT);
                    app.score.increase();
                    app.playerIndex[0]--;
                }
            }
        });

        buttons[2].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!app.isCameraMoving()){
                    app.playScreen.xDest = (int) (app.camera.position.x - app.V_WIDTH);
                    app.score.increase();
                    app.playerIndex[1]--;
                }
            }
        });

        buttons[3].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!app.isCameraMoving()){
                    app.playScreen.xDest = (int) (app.camera.position.x + app.V_WIDTH);
                    app.score.increase();
                    app.playerIndex[1]++;
                }
            }
        });
    }
}
