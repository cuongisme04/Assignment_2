package WizardTD;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.*;
import java.util.*;
public class Game {
    private PApplet pApplet;
    private Board board;
    private Element element;
    private ArrayList<Tower> towers;
    private Tower tower;
    private ArrayList<Wave> waves;
    private Wave wave;
    private int num_wave;
    private ArrayList<Monster> monsters;


    public Game(PApplet pApplet)
    {
        this.pApplet = pApplet;
        this.board = new Board(pApplet);
        this.element = new Element(pApplet);
        this.waves = new ArrayList<Wave>();
        this.monsters = new ArrayList<>();

    }


    public void loadJSON(String src, HashMap<String,PImage> images) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonData = (JSONObject) parser.parse(new FileReader(src));


            // load layout of the map
            String layout = (String) jsonData.get("layout");
            board.setLayout(layout);

            // load initial tower properties
            float initialTowerRange = Float.parseFloat(jsonData.get("initial_tower_range").toString());
            float initialTowerFiringSpeed = Float.parseFloat(jsonData.get("initial_tower_firing_speed").toString());
            float initialTowerDamage = Float.parseFloat(jsonData.get("initial_tower_damage").toString());
            float towerCost = Float.parseFloat(jsonData.get("tower_cost").toString());

            // load tower images
            ArrayList<PImage> img_towers = new ArrayList<>();
            img_towers.add(images.get("Tower0Image"));
            img_towers.add(images.get("Tower1Image"));
            img_towers.add(images.get("Tower2Image"));

            tower = new Tower(pApplet, img_towers, towerCost, initialTowerRange, initialTowerFiringSpeed, initialTowerDamage);

            // load die img of monster
            ArrayList<PImage> Died_img = new ArrayList<>();
            Died_img.add(images.get("gremlin1"));
            Died_img.add(images.get("gremlin2"));
            Died_img.add(images.get("gremlin3"));
            Died_img.add(images.get("gremlin4"));
            Died_img.add(images.get("gremlin5"));

            // load waves information
            JSONArray waves = (JSONArray) jsonData.get("waves");
            Iterator waves_iterator = waves.iterator();
            ArrayList<Wave> tempWaves = new ArrayList<>();
            while (waves_iterator.hasNext()) {
                JSONObject waveData = (JSONObject) waves_iterator.next();

                int duration = Integer.parseInt(waveData.get("duration").toString());
                float pre_wave_pause = Float.parseFloat(waveData.get("pre_wave_pause").toString());

                JSONArray monsters = (JSONArray) waveData.get("monsters");
                ArrayList<Monster> waveMonsters = new ArrayList<>();
                ArrayList<Integer> waveMonsterQuantities = new ArrayList<>();

                for (Object monsterObj : monsters) {
                    JSONObject monsterData = (JSONObject) monsterObj;

                    String monsterType = (String) monsterData.get("type");
                    float hp = Float.parseFloat(monsterData.get("hp").toString());
                    float speed = Float.parseFloat(monsterData.get("speed").toString());
                    float armour = Float.parseFloat(monsterData.get("armour").toString());
                    int manaGainedOnKill = Integer.parseInt(monsterData.get("mana_gained_on_kill").toString());
                    int quantity = Integer.parseInt(monsterData.get("quantity").toString());

                    Monster monster = new Monster(pApplet, images.get(monsterType), hp, speed, armour, manaGainedOnKill);
                    monster.setDie_img((ArrayList<PImage>) Died_img.clone());

                    waveMonsters.add(monster);
                    waveMonsterQuantities.add(quantity);
                }

                Wave wave = new Wave(pApplet, pre_wave_pause, duration, waveMonsters, waveMonsterQuantities);
                tempWaves.add(wave);



            }
            waves.add(tempWaves);

        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setup(HashMap<String, PImage> images, String srcJson)
    {
        loadJSON(srcJson, images);
        board.load_map(board.getLayout());
        board.setup_map(images);
        //for (Wave wave : waves)
        //{
           // wave.setPath_to_wizard(board.findPath());
        //}

    }
    public void display()
    {
        board.display();
        display_element();
        //display_Wave();
    }

    public void display_element()
    {
        //element.setWait_Wave(wave.getFrame_wait() / 60);
        element.setNum_Wave(num_wave + 1);
        element.display();

    }

    public void display_wave()
    {
        wave.display();
        if (wave.check_monster_spawn() && wave.check_monster_died())
        {
            num_wave += 1;
            if (num_wave >= waves.size())
            {
                return;
            }
            wave = waves.get(num_wave);
        }


    }
}


