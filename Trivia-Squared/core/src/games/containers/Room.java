package games.containers;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Max Towery on 5/30/2015.
 */
public class Room {

    private Image image;
    private boolean leftDoor, rightDoor, topDoor, botDoor;
    private boolean playerInRoom;



    public Room(Image image) {
        this.image = image;
        leftDoor = true; rightDoor = true; topDoor = true; botDoor = true;
        playerInRoom = false;
    }

    public Room(){
        leftDoor = true; rightDoor = true; topDoor = true; botDoor = true;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isLeftDoor() {
        return leftDoor;
    }

    public void setLeftDoor(boolean leftDoor) {
        this.leftDoor = leftDoor;
    }

    public boolean isRightDoor() {
        return rightDoor;
    }

    public void setRightDoor(boolean rightDoor) {
        this.rightDoor = rightDoor;
    }

    public boolean isTopDoor() {
        return topDoor;
    }

    public void setTopDoor(boolean topDoor) {
        this.topDoor = topDoor;
    }

    public boolean isBotDoor() {
        return botDoor;
    }

    public void setBotDoor(boolean botDoor) {
        this.botDoor = botDoor;
    }

    public boolean isPlayerInRoom() {
        return playerInRoom;
    }

    public void setPlayerInRoom(boolean playerInRoom) {
        this.playerInRoom = playerInRoom;
    }

    public boolean isLocked(){
        if (!this.topDoor && !this.botDoor && !this.rightDoor && !this.leftDoor)
            return true;
        return false;
    }

    public void setToFalse(){
        this.leftDoor = false;
        this.rightDoor = false;
        this.topDoor = false;
        this.botDoor = false;
    }

    public void setTop()
    {
        this.topDoor = false;
    }
    public void setBot()
    {
        this.botDoor =false;
    }
    public void setRight()
    {
        this.rightDoor =false;
    }
    public void setLeft()
    {
        this.leftDoor = false;
    }



}
