package WizardTD;

import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PImage;

import java.util.*;

public class Wave {
    private PApplet pApplet;
    private float pre_wave_pause;
    private int duration;
    private ArrayList<Monster> monster_list;
    private ArrayList<Integer> monster_quantity;
    private ArrayList<Monster> monster_live;

    private HashMap<Integer, ArrayList<PVector>> path_to_wizard;
    private int frame_duration;
    private int frame_wait;
    private int frame_spawn;

    private int get_second_wait()
    {
        return (int) frame_wait / 60;
    }

    public float getPre_wave_pause() {
        return pre_wave_pause;
    }

    public void setPre_wave_pause(float pre_wave_pause) {
        this.pre_wave_pause = pre_wave_pause;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ArrayList<Monster> getMonster_list() {
        return monster_list;
    }

    public void setMonster_list(ArrayList<Monster> monster_list) {
        this.monster_list = monster_list;
    }

    public ArrayList<Integer> getMonster_quantity() {
        return monster_quantity;
    }

    public void setMonster_quantity(ArrayList<Integer> monster_quantity) {
        this.monster_quantity = monster_quantity;
    }

    public ArrayList<Monster> getMonster_live() {
        return monster_live;
    }

    public void setMonster_live(ArrayList<Monster> monster_live) {
        this.monster_live = monster_live;
    }

    public HashMap<Integer, ArrayList<PVector>> getPath_to_wizard() {
        return path_to_wizard;
    }

    public void setPath_to_wizard(HashMap<Integer, ArrayList<PVector>> path_to_wizard) {
        this.path_to_wizard = path_to_wizard;
    }

    public int getFrame_duration() {
        return frame_duration;
    }

    public void setFrame_duration(int frame_duration) {
        this.frame_duration = frame_duration;
    }

    public int getFrame_wait() {
        return frame_wait;
    }

    public void setFrame_wait(int frame_wait) {
        this.frame_wait = frame_wait;
    }

    public int getFrame_spawn() {
        return frame_spawn;
    }

    public void setFrame_spawn(int frame_spawn) {
        this.frame_spawn = frame_spawn;
    }

    public Wave(PApplet pApplet, float pre_wave_pause, int duration, ArrayList<Monster> monster_list, ArrayList<Integer> monster_quantity)
    {
        this.pApplet = pApplet;
        this.pre_wave_pause = pre_wave_pause;
        this.frame_wait = (int) pre_wave_pause * 60;
        this.duration = duration;
        this.frame_duration = duration * 60;
        int total_monster = 0;
        for (int value : monster_quantity) {total_monster += value;}
        this.frame_spawn = (int) frame_duration/ total_monster;
        this.monster_list = monster_list;
        this.monster_quantity = monster_quantity;
    }

    public boolean check_monster_died()
    {
        if (monster_live.size() == 0) {
            return true;
        }
        return false;
    }

    public boolean check_monster_spawn()
    {
        if (frame_duration == 0)
        {
            return true;
        }
        return false;
    }

    public void spawn()
    {
        if (monster_quantity.size() != 0)
        {
            Random random = new Random();
            int index = random.nextInt(monster_quantity.size());
            int quantity = monster_quantity.get(index);
            quantity -= 1;
            monster_quantity.set(index, quantity);

            Monster monster_demo = monster_list.get(index);
            Monster monster = new Monster(pApplet, monster_demo.getType(), monster_demo.getHp_cap(), monster_demo.getSpeed(), monster_demo.getArmour(), monster_demo.getMana_gained_on_kill());

            monster.setDie_img((ArrayList<PImage>) monster_demo.getDie_img().clone());

            monster_live.add(monster);

            if (quantity == 0)
            {
                monster_list.remove(index);
                monster_quantity.remove(index);
            }
        }
    }

    public void display()
    {
        if (frame_wait > 0)
        {
            frame_wait--;
        }
        if (frame_wait == 0)
        {
            if (frame_duration > 0)
            {
                frame_duration--;
            }
            if (monster_live.size() != 0)
            {
                for(int i = 0; i < monster_live.size(); i++)
                {
                    Monster monster = monster_live.get(i);
                    String status = monster.update_status();
                    if (status == "live")
                    {
                        monster.display();
                    }
                    if (status == "died")
                    {
                        monster.display();
                    }
                    if (status == "wizard")
                    {
                        monster_live.remove(i);
                    }
                }
            }

            for (Monster monster: monster_live)
            {
                if (monster.check_status() == "live")
                {
                    monster.display_hp();
                }
            }
            if (frame_duration % frame_spawn == 0)
            {
                spawn();
            }
        }
    }

}
