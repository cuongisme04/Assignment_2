package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

public class Tower {
    private PApplet pApplet;
    private ArrayList<PImage> img;
    private float x;
    private float y;
    private float tower_cost;
    private float tower_range;
    private float tower_firing_speed;
    private float tower_damage;
    private int tower_level;
    private int range_level = 0;
    private int speed_level = 0;
    private int damage_level = 0;
    private int frame_shoot;

    public PApplet getpApplet() {
        return pApplet;
    }

    public void setpApplet(PApplet pApplet) {
        this.pApplet = pApplet;
    }

    public ArrayList<PImage> getImg() {
        return img;
    }

    public void setImg(ArrayList<PImage> img) {
        this.img = img;
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

    public float getTower_cost() {
        return tower_cost;
    }

    public void setTower_cost(float tower_cost) {
        this.tower_cost = tower_cost;
    }

    public float getTower_range() {
        return tower_range;
    }

    public void setTower_range(float tower_range) {
        this.tower_range = tower_range;
    }

    public float getTower_firing_speed() {
        return tower_firing_speed;
    }

    public void setTower_firing_speed(float tower_firing_speed) {
        this.tower_firing_speed = tower_firing_speed;
    }

    public float getTower_damage() {
        return tower_damage;
    }

    public void setTower_damage(float tower_damage) {
        this.tower_damage = tower_damage;
    }

    public int getTower_level() {
        return tower_level;
    }

    public void setTower_level(int tower_level) {
        this.tower_level = tower_level;
    }

    public int getRange_level() {
        return range_level;
    }

    public void setRange_level(int range_level) {
        this.range_level = range_level;
    }

    public int getSpeed_level() {
        return speed_level;
    }

    public void setSpeed_level(int speed_level) {
        this.speed_level = speed_level;
    }

    public int getDamage_level() {
        return damage_level;
    }

    public void setDamage_level(int damage_level) {
        this.damage_level = damage_level;
    }

    public PVector getPosition()
    {
        return new PVector(x, y);
    }
    public Tower(PApplet pApplet, ArrayList<PImage> img, float tower_cost, float tower_range, float tower_firing_speed, float tower_damage)
    {
        this.pApplet = pApplet;
        this.img = img;
        this.tower_cost = tower_cost;
        this.tower_range = tower_range;
        this.tower_firing_speed = tower_firing_speed;
        this.tower_damage = tower_damage;
        this.frame_shoot = (int) (60 / tower_firing_speed);
    }

    public void upgrade_range()
    {
        if (range_level <= 3)
        {
            range_level += 1;
        }
    }

    public void upgrade_speed()
    {
        if (speed_level <= 3)
        {
            speed_level += 1;
        }
    }

    public void upgrade_damage()
    {
        if (damage_level <= 3)
        {
            damage_level += 1;
        }
    }

    public void follow_Mouse(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public boolean check_over_tower(float MouseX, float MouseY)
    {
        if ((MouseX >= x && MouseX <= x + 32) && (MouseY >= y && MouseY <= y + 32))
        {
            return true;
        }
        return false;
    }

    public void displayRange()
    {
        int[] color = Colors.YELLOW.color.getColor();
        pApplet.stroke(color[0], color[1], color[2]);
        pApplet.fill(0, 0);
        pApplet.ellipse(x + 16, y + 16, tower_range * 2, tower_range * 2);
        pApplet.noStroke();
    }

    public void display()
    {
        pApplet.image(img.get(0), x, y, 32, 32);
    }

    public Fireball shoot(int idMonsterInRange, PVector posMonster)
    {
        if (idMonsterInRange == -1)
        {
            return null;
        }
        if(pApplet.frameCount % frame_shoot == 0)
        {
            return new Fireball(tower_damage, getPosition(), posMonster, idMonsterInRange);
        }
        return null;
    }
}
