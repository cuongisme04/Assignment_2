package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Fireball {
    private PApplet pApplet;
    private PImage img;
    private PVector current_pos;
    private PVector target_pos;
    private float speed = 5;
    private float damage;
    private int ID_target;

    public PImage getImg() {
        return img;
    }

    public void setImg(PImage img) {
        this.img = img;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public int getID_target() {
        return ID_target;
    }

    public void setID_target(int ID_target) {
        this.ID_target = ID_target;
    }

    public Fireball(PApplet pApplet, PImage img, float damage)
    {
        this.pApplet = pApplet;
        this.img= img;
        this.damage = damage;
    }

    public Fireball(float damage, PVector current_pos, PVector target_pos, int ID_target)
    {
        this.damage = damage;
        this.current_pos = current_pos;
        this.target_pos = target_pos;
        this.ID_target = ID_target;
    }

    public void setup(PApplet pApplet, PImage img)
    {
        this.pApplet = pApplet;
        this.img = img;
    }

    public void move()
    {
        PVector direction = PVector.sub(target_pos, current_pos);
        if (direction.mag() > 0)
        {
            float newSpeed = Math.min(speed, direction.mag()); //change speed caculation
            direction.normalize();
            direction.mult(newSpeed);
            current_pos = current_pos.add(direction);
        }
    }

    public void display()
    {
        pApplet.image(img, current_pos.x, current_pos.y, 5, 5);
        move();
    }

    public boolean checkOnTarget()
    {
        PVector dir = PVector.sub(target_pos, current_pos);
        return dir.mag() == 0;
    }

}
