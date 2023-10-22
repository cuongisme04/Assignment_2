package WizardTD;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.FileReader;
import java.util.HashMap;
public class Game {
    private PApplet pApplet;
    private Board board;
    private Element element;

    public Game(PApplet pApplet)
    {
        this.pApplet = pApplet;
        this.board = new Board(pApplet);
        this.element = new Element(pApplet);
    }

    public void loadJSON(String src, HashMap<String,PImage> images) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(src));
            JSONObject jsonObject = (JSONObject) obj;

            String layout = (String) jsonObject.get("layout");
            board.setRaw_map(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setup(HashMap<String, PImage> images, String srcJson)
    {
        loadJSON(srcJson, images);
        board.loadMap(board.getRaw_map());
        board.setupMap(images);
    }
    public void display()
    {
        board.display();
        board.displayWizard();
        display_Element();
    }

    public void display_Element()
    {
        element.display();
    }
}


