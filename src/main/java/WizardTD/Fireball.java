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
}
