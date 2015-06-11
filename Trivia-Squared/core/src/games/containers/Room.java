package games.containers;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import games.utils.dbUtils.Question;

/**
 * Created by Max Towery on 5/30/2015.
 */
public class Room {

    private Image image, image2;
    private boolean visited;
    public boolean isEnd;
    private boolean leftDoor, rightDoor, topDoor, botDoor;
    public int [] location;
    public int [] index;
    public String[] doorSerial;
    public int questionType; //0 = multiple choice, 1 = t/f, 2 = short answer
    public String answer;
    public String textEntry;

    public Question question;

    public Room(){
        leftDoor = true; rightDoor = true; topDoor = true; botDoor = true;
        location = new int[2];
        location[0] = 0; location[1] = 0;
        index = new int[2];
        doorSerial = new String[4];
        for(int i = 0; i < doorSerial.length; i++)
            doorSerial[i] = "0";
        questionType = 0;
        answer = "right";
        textEntry = "";
        visited = false;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {

        this.image = image;
    }

    public void setImage2(Image image){
        this.image2 = image;
    }

    public void setTopDoor(boolean topDoor) {

        this.topDoor = topDoor;
        if (!topDoor)
            doorSerial[0] = "1";
        else
            doorSerial[0] = "0";
    }
    public void setBotDoor(boolean botDoor) {

        this.botDoor = botDoor;
        if (!botDoor)
            doorSerial[1] = "1";
        else
            doorSerial[1] = "0";
    }
    public void setLeftDoor(boolean leftDoor) {
        this.leftDoor = leftDoor;
        if (!leftDoor)
            doorSerial[2] = "1";
        else
            doorSerial[2] = "0";
    }
    public void setRightDoor(boolean rightDoor) {
        this.rightDoor = rightDoor;
        if (!rightDoor)
            doorSerial[3] = "1";
        else
            doorSerial[3] = "0";
    }

    public String serialToBinary(){
        String doorSerial = "";
        for(int i = 0; i < this.doorSerial.length; i++){
            doorSerial += this.doorSerial[i];
        }
        return doorSerial;
    }

    public Image getImage2() {
        return image2;
    }
    public boolean isLeftDoor() {
        return leftDoor;
    }
    public boolean isRightDoor() {
        return rightDoor;
    }
    public boolean isTopDoor() {
        return topDoor;
    }
    public boolean isBotDoor() {
        return botDoor;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean getVisited() {
        return visited;
    }
}
