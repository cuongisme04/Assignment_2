package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

public class Monster {
    private PApplet pApplet;
    private PImage type;
    private float x;
    private float y;
    private float hp_cap;
    private float hp_current;
    private float speed;
    private float armour;
    private int mana_gained_on_kill;
    private ArrayList<PImage> die_img;
    private ArrayList<PVector> path_to_wizard;
    private int path_index;

    public PImage getType() {
        return type;
    }

    public void setType(PImage type) {
        this.type = type;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getHp_cap() {
        return hp_cap;
    }

    public void setHp_cap(float hp_cap) {
        this.hp_cap = hp_cap;
    }

    public float getHp_current() {
        return hp_current;
    }

    public void setHp_current(float hp_current) {
        this.hp_current = hp_current;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getArmour() {
        return armour;
    }

    public void setArmour(float armour) {
        this.armour = armour;
    }

    public int getMana_gained_on_kill() {
        return mana_gained_on_kill;
    }

    public void setMana_gained_on_kill(int mana_gained_on_kill) {
        this.mana_gained_on_kill = mana_gained_on_kill;
    }

    public ArrayList<PImage> getDie_img() {
        return die_img;
    }

    public void setDie_img(ArrayList<PImage> die_img) {
        this.die_img = die_img;
    }

    public ArrayList<PVector> getPath_to_wizard() {
        return path_to_wizard;
    }

    public void setPath_to_wizard(ArrayList<PVector> path_to_wizard) {
        this.path_to_wizard = path_to_wizard;
    }

    public int getPath_index() {
        return path_index;
    }

    public void setPath_index(int path_index) {
        this.path_index = path_index;
    }

    public Monster(PApplet pApplet, PImage type, float hp_cap, float speed, float armour, int mana_gained_on_kill)
    {
        this.pApplet = pApplet;
        this.type = type;
        this.hp_cap = hp_cap;
        this.hp_current = hp_cap;
        this.speed = speed;
        this.armour = armour;
        this.mana_gained_on_kill = mana_gained_on_kill;
        this.path_index = 0;
    }

    public PVector get_position()
    {
        return new PVector(x, y);
    }

    public PVector get_center_position()
    {
        return new PVector(x + 12, y + 12);
    }

    public float getPixelX(float x)
    {
        return x * 32;
    }

    public float getPixelY(float y)
    {
        return y * 32 + 40;
    }

    public void display_hp()
    {
        int[]  color = Colors.RED.color.getColor();
        pApplet.fill(color[0], color[1], color[2]);
        pApplet.rect(x, y - 5, 25, 2);

        color = Colors.GREEN.color.getColor();
        pApplet.fill(color[0], color[1], color[2]);
        pApplet.rect(x, y - 5, (float) (25 / hp_cap * hp_current ), 2);
    }

    public void update_hp(float damge)
    {
        hp_current = Math.max(0, hp_current - damge);
    }

    public void spawn(ArrayList<PVector> path_to_wizard)
    {
        this.path_to_wizard = path_to_wizard;
        PVector spawn_position = path_to_wizard.get(0);
        this.x = getPixelX(spawn_position.x);
        this.y = getPixelY(spawn_position.y);
    }

    public String check_status() {
        if (hp_current == 0) {
            return "died";
        }
        if (path_index >= path_to_wizard.size()) {
            return "wizard";
        }
        return "live";
    }

    public void display()
    {
        pApplet.image(type, x, y, 20, 20);
    }

    public void move(PVector target)
    {
        if (x < getPixelX(target.x))
        {
            x += Math.min(speed, Math.abs(x - getPixelX(target.x)));
        }
        else if (x > getPixelX(target.x))
        {
            x -= Math.min(speed, Math.abs(x - getPixelX(target.x)));
        }
        else if (y < getPixelY(target.y))
        {
            y += Math.min(speed, Math.abs(y - getPixelY(target.y)));
        }
        else if (y > getPixelY(target.y))
        {
            y -= Math.min(speed, Math.abs(y - getPixelY(target.y)));
        }
    }


    public String update_status()
    {
        if (hp_current == 0)
        {
            return "died";
        }

        if (path_index >= path_to_wizard.size())
        {
            return "wizard";
        }
        PVector target = path_to_wizard.get(path_index);
        move(target);

        if(getPixelX(target.x) == x && getPixelY(target.y) == y)
        {
            path_index++;
        }

        return "live";
    }




}
