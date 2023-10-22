package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    PApplet pApplet;
    String raw_map;
    int[][] map;
    Cell[][] grid;
    HashMap<Integer, ArrayList<PVector>> validPaths;
    int  x_wizardhouse = 0;
    int y_wizardhouse = 0;


    public Board(PApplet pApplet)
    {
        this.pApplet = pApplet;
        this.map = new int[20][20];
        this.grid = new Cell[20][20];
        this.validPaths = new HashMap<>();
    }

    public String getRaw_map() {
        return raw_map;
    }

    public void setRaw_map(String raw_map) {
        this.raw_map = raw_map;
    }

    public float getPixelX(float x) {
        return x * 32;
    }

    public float getPixelY(float y) {
        return y * 32 + 40;
    }
    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public Cell getCell(int x, int y) {
        return grid[x][y];
    }

    public PVector checkValidBuild(float x, float y)
    {
        int x_pos = (int) x / 32;
        int y_pos = (int) (y - 40) / 32;

        if (map[x_pos][y_pos] == 0)
        {
            return new PVector(getPixelX(x_pos), getPixelY(y_pos));
        }

        return new PVector(-1, -1);
    }

    public void setTower(float x, float y)
    {
        int x_pos = (int) x / 32;
        int y_pos = (int) (y - 40) / 32;
        map[x_pos][y_pos] = 2;
    }

    public void setupMap(HashMap<String, PImage> Image)
    {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (map[i][j] == 0) {
                    grid[i][j] = new Cell("grass", Image.get("GrassImage"), i, j);
                }
                else if (map[i][j] == 1) {
                    grid[i][j] = new Cell("shrub", Image.get("ShrubImage"), i, j);
                }
                else if (map[i][j] == -1) {
                    grid[i][j] = new Cell("wizardhouse", Image.get("WizardImage"), i, j);
                }
                else if (map[i][j] >= 10) {
                    grid[i][j] = new Cell("path", Image.get(String.format("PathImage%d", map[i][j])), i, j);
                }
            }
        }
    }

    public HashMap<Integer, ArrayList<PVector>> getPathToWizard()
    {
        return validPaths;
    }

    public void loadMap(String src)
    {
        try {
            File file = new File(src);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                for (int j = 0; j < line.length(); j++) {
                    char char_ = line.charAt(j);
                    if (char_ == ' ') {
                        map[j][i] = 0;
                    }
                    else if (char_ == 'S') {
                        map[j][i] = 1;
                    }
                    else if (char_ == 'X') {
                        map[j][i] = 10;
                    }
                    else if (char_ == 'W') {
                        map[j][i] = -1;
                    }
                }

                i++;
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (map[i][j] >= 10) {
                    boolean left = (i > 0 && map[i - 1][j] >= 10) ? true : false;
                    boolean right = (i < 19 && map[i + 1][j] >= 10) ? true : false;
                    boolean up = (j > 0 && map[i][j - 1] >= 10) ? true : false;
                    boolean down = (j < 19 && map[i][j + 1] >= 10) ? true : false;

                    if (!left && !right && (up || down)) {
                        map[i][j] = 11;
                    }
                    else if (up && left && !down && !right) {
                        map[i][j] = 12;
                    }
                    else if (!up && left && down && !right) {
                        map[i][j] = 13;
                    }
                    else if (!up && !left && down && right) {
                        map[i][j] = 14;
                    }
                    else if (up && !left && !down && right) {
                        map[i][j] = 15;
                    }
                    else if (up && left && !down && right) {
                        map[i][j] = 16;
                    }
                    else if (up && left && down && !right) {
                        map[i][j] = 17;
                    }
                    else if (!up && left && down && right) {
                        map[i][j] = 18;
                    }
                    else if (up && !left && down && right) {
                        map[i][j] = 19;
                    }
                    else if (up && left && down && right) {
                        map[i][j] = 20;
                    }

                }
            }
        }

        ArrayList<PVector> startPoints = new ArrayList<>();
        for (int i : new int[]{0, 19})
        {
            for(int j = 0; j < 20; j++)
            {
                if (map[i][j] == 10)
                {
                    startPoints.add(new PVector(i, j));
                }
            }
        }

        for(int j : new int[]{0, 19})
        {
            for(int i = 0; i < 20; i++)
            {
                if (map[i][j] == 11)
                {
                    startPoints.add(new PVector(i, j));
                }
            }
        }

    }

    public void display()
    {
        int x_grass = 0;
        int y_grass = 0;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Cell cell = grid[i][j];
                if (cell.getType() == "grass")
                {
                    x_grass = i;
                    y_grass = j;
                }
                if (cell.getType() == "wizardhouse") {
                    x_wizardhouse = i;
                    y_wizardhouse = j;
                }
                else {
                    pApplet.image(cell.getImg(), i * 32, j * 32 + 40, 32, 32);
                }
            }
        }
        pApplet.image(grid[x_grass][y_grass].getImg(), x_wizardhouse * 32 - 10, y_wizardhouse * 32 - 7 + 40, 32, 32);
    }

    public void displayWizard()
    {
        pApplet.image(grid[x_wizardhouse][y_wizardhouse].getImg(), x_wizardhouse * 32 - 10, y_wizardhouse * 32 - 7 + 40, 48, 48);
    }
}