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
import java.util.List;

public class Board {
    PApplet pApplet;
    String layout;
    int[][] map;
    Cell[][] grid;
    HashMap<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> path_to_wizard;
    int  x_wizard_house = 0;
    int y_wizard_house = 0;


    public Board(PApplet pApplet)
    {
        this.pApplet = pApplet;
        this.map = new int[20][20];
        this.grid = new Cell[20][20];
        this.path_to_wizard = new HashMap<>();
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
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

    public HashMap<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> get_path_to_wizard() {
        return path_to_wizard;
    }

    public PVector check_valid_build(float x, float y)
    {
        int x_pos = (int) x / 32;
        int y_pos = (int) (y - 40) / 32;

        if (map[x_pos][y_pos] == 0)
        {
            return new PVector(getPixelX(x_pos), getPixelY(y_pos));
        }

        return new PVector(-1, -1);
    }

    public void set_tower(float x, float y)
    {
        int x_pos = (int) x / 32;
        int y_pos = (int) (y - 40) / 32;
        map[x_pos][y_pos] = 2;
    }

    public void setup_map(HashMap<String, PImage> Image)
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


    public void load_map(String src) {
        try {
            File file = new File(src);
            BufferedReader buffer = new BufferedReader(new FileReader(file));
            String line;
            int i = 0;
            while ((line = buffer.readLine()) != null) {
                for (int j = 0; j < line.length(); j++) {
                    char char_ = line.charAt(j);
                    if (char_ == ' ') {
                        map[j][i] = 0;
                    } else if (char_ == 'S') {
                        map[j][i] = 1;
                    } else if (char_ == 'X') {
                        map[j][i] = 10;
                    } else if (char_ == 'W') {
                        map[j][i] = -1;
                    }
                }

                i++;
            }
        } catch (IOException e) {
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
                    } else if (up && left && !down && !right) {
                        map[i][j] = 12;
                    } else if (!up && left && down && !right) {
                        map[i][j] = 13;
                    } else if (!up && !left && down && right) {
                        map[i][j] = 14;
                    } else if (up && !left && !down && right) {
                        map[i][j] = 15;
                    } else if (up && left && !down && right) {
                        map[i][j] = 16;
                    } else if (up && left && down && !right) {
                        map[i][j] = 17;
                    } else if (!up && left && down && right) {
                        map[i][j] = 18;
                    } else if (up && !left && down && right) {
                        map[i][j] = 19;
                    } else if (up && left && down && right) {
                        map[i][j] = 20;
                    }

                }
            }
        }
    }


    public ArrayList<ArrayList<Integer>> getStartPoints()
    {
        ArrayList<ArrayList<Integer>> startPoints = new ArrayList<>();
        for (int i : new int[]{0, 19}) {
            for (int j = 0; j < 20; j++) {
                if (map[i][j] == 10) {
                    ArrayList<Integer> temp = new ArrayList<>();
                    temp.add(i);
                    temp.add(j);
                    startPoints.add(temp);
                }
            }
        }

        for (int j : new int[]{0, 19}) {
            for (int i = 0; i < 20; i++) {
                if (map[i][j] == 11) {
                    ArrayList<Integer> temp = new ArrayList<>();
                    temp.add(i);
                    temp.add(j);
                    startPoints.add(temp);
                }
            }
        }
        return startPoints;
    }

    public boolean move(List<List<Integer>> map, int x, int y, List<List<Boolean>> check)
    {
        if ((map.get(x).get(y) >= 10) && (check.get(x).get(y) == true))
        {
            return true;
        }
        return false;
    }

    public HashMap<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> findPath(ArrayList<Integer> StartPoint, ArrayList<Integer> EndPoint, List<List<Integer>> map)
    {
        int count = 0;
        List<List<Boolean>> check = new ArrayList<>();
        HashMap<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> path = new HashMap<>();

        for (int i = 0; i < 20; i++){
            List<Boolean> row = new ArrayList<>();
            for (int j = 0; j < 20; j++){
                row.add(false);
            }
            check.add(row);
        }

        int x_start = StartPoint.get(0);
        int y_start = StartPoint.get(1);
        int x_end = EndPoint.get(0);
        int y_end = EndPoint.get(1);

        // Create a list to store the path
        ArrayList<ArrayList<Integer>> currentPath = new ArrayList<>();
        ArrayList<Integer> startPoint = new ArrayList<>();
        startPoint.add(x_start);
        startPoint.add(y_start);
        currentPath.add(startPoint); // Start point

        while (true) {
            count++;

            if ((x_start == x_end && y_start == y_end) || (count >= 400))
            {
                break;
            }

            if ((x_start - 1 >= 0) && (check.get(x_start - 1).get(y_start) == false)) {
                boolean check_left = move(map, x_start - 1, y_start, check);
                if (check_left == true) {
                    check.get(x_start - 1).set(y_start, true);
                    x_start = x_start - 1;
                    // Add the current position to the path
                    ArrayList<Integer> currentPosition = new ArrayList<>();
                    currentPosition.add(x_start);
                    currentPosition.add(y_start);
                    currentPath.add(currentPosition);
                    continue;
                }
            }
            if ((x_start + 1 <= 20) && (check.get(x_start + 1).get(y_start) == false)) {
                boolean check_right = move(map, x_start + 1, y_start, check);
                if (check_right == true) {
                    check.get(x_start + 1).set(y_start, true);
                    x_start = x_start + 1;
                    // Add the current position to the path
                    ArrayList<Integer> currentPosition = new ArrayList<>();
                    currentPosition.add(x_start);
                    currentPosition.add(y_start);
                    currentPath.add(currentPosition);
                    continue;
                }
            }
            if ((y_start - 1 >= 0) && (check.get(x_start).get(y_start - 1) == false)) {
                boolean check_up = move(map, x_start, y_start - 1, check);
                if (check_up == true) {
                    check.get(x_start).set(y_start - 1, true);
                    y_start = y_start - 1;
                    // Add the current position to the path
                    ArrayList<Integer> currentPosition = new ArrayList<>();
                    currentPosition.add(x_start);
                    currentPosition.add(y_start);
                    currentPath.add(currentPosition);
                    continue;
                }
            }
            if ((y_start + 1 >= 20) && (check.get(x_start).get(y_start + 1) == false)) {
                boolean check_down = move(map, x_start, y_start + 1, check);
                if (check_down == true) {
                    check.get(x_start).set(y_start + 1, true);
                    y_start = y_start + 1;
                    // Add the current position to the path
                    ArrayList<Integer> currentPosition = new ArrayList<>();
                    currentPosition.add(x_start);
                    currentPosition.add(y_start);
                    currentPath.add(currentPosition);
                    continue;
                }
            }
        }
        ArrayList<Integer> start = currentPath.get(0);
        path.put(start, new ArrayList<>(currentPath));
        return path;
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
                    x_wizard_house = i;
                    y_wizard_house = j;
                }
                else {
                    pApplet.image(cell.getImg(), i * 32, j * 32 + 40, 32, 32);
                }
            }
        }

        pApplet.image(grid[x_grass][y_grass].getImg(), x_wizard_house * 32 - 10, y_wizard_house * 32 - 7 + 40, 32, 32);

        // display wizard house
        pApplet.image(grid[x_wizard_house][y_wizard_house].getImg(), x_wizard_house * 32 - 10, y_wizard_house * 32 - 7 + 40, 48, 48);

    }

}