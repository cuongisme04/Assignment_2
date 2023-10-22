package WizardTD;
import processing.core.PImage;
public class Cell {
    private String type;
    private int x;
    private int y;
    private PImage img;

    public Cell(String type, PImage img, int x, int y)
    {
        this.type = type;
        this.img = img;
        this.x = x;
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public PImage getImg() {
        return img;
    }

    public void setImg(PImage img) {
        this.img = img;
    }
}
