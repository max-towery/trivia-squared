package games.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import games.managers.GameStateManager;
import games.utils.TiledObjectUtil;
import static games.utils.Constants.*;

/**
 * Created by Max Towery on 5/12/2015.
 */
public class PlayState extends  GameState {

    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;

    private Box2DDebugRenderer b2dr;
    private World world;
    private Body player;

    private Texture tex;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        world = new World(new Vector2(0,0), false);
        b2dr = new Box2DDebugRenderer();

        player = createBox(250, 190, 32, 32, false);

        //you can use this to create random stationary objects throughout the game
        //createBox(140, 130, 64, 32, true);

        batch = new SpriteBatch();
        tex = new Texture("Images/cat.png");

        map = new TmxMapLoader().load("Maps/test_map.tmx");
        tmr = new OrthogonalTiledMapRenderer(map); //renderer to display the map on screen

        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("collision-layer").getObjects());
    }

    @Override
    public void update(float delta) {
        world.step(1 / 60f, 6, 2);//smoothness feel for the world (6/2 ratio recommended)

        cameraUpdate();
        tmr.setView(camera);//make sure the map is proportionate to the camera
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        inputUpdate(Gdx.graphics.getDeltaTime());
        batch.begin();//opens the rendering protocol for textures (try to keep logic out of here)
        batch.draw(tex, player.getPosition().x * PPM - (tex.getWidth()/2)//ensures texture is bound to center of box
                , player.getPosition().y * PPM - (tex.getHeight()/2));
        batch.end();//closes the rendering protocol for textures

        tmr.render();

        b2dr.render(world, camera.combined.scl(PPM));//ensures the camera scales with the objects created on the screen
    }

    @Override
    public void dispose() {
        b2dr.dispose();
        world.dispose();
        tmr.dispose();
        map.dispose();
    }

    public void cameraUpdate(){
        Vector3 position = camera.position;
        //a + (b - a) * lerp
        //b = target
        //a = current camera position
        //this delays the cameras movement so it isn't so hard on the eyes
        //basically stops the motion sickness feeling
        position.x = camera.position.x + (player.getPosition().x * PPM - camera.position.x) * .1f;
        position.y = camera.position.y + (player.getPosition().y * PPM - camera.position.y) * .1f;
        camera.position.set(position);
        camera.update();

    }

    //need to outsource this to a separate class
    public void inputUpdate(float delta){
        int horizontalForce = 0;
        int verticalForce = 0;
       // player.setAwake(false);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            horizontalForce -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            horizontalForce += 1;

        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            //player.applyForceToCenter(0,50,true);
            verticalForce += 1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            //player.applyForceToCenter(0,-50,true);
            verticalForce -= 1;
        }

        player.setLinearVelocity(player.getLinearVelocity().x, verticalForce * PLAYER_SPEED);
        //5 represents meters/second
        player.setLinearVelocity(horizontalForce * PLAYER_SPEED, player.getLinearVelocity().y);

    }

    public Body createBox(int x, int y, int width, int height, boolean isStatic){
        Body pBody;
        BodyDef def = new BodyDef();

        if (isStatic)//static body = movable, dynamic = fixed
            def.type = BodyDef.BodyType.StaticBody;
        else
            def.type = BodyDef.BodyType.DynamicBody;


        def.position.set(x / PPM, y / PPM);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

        pBody.createFixture(shape, 1.0f);
        shape.dispose();

        return pBody;
    }
}
