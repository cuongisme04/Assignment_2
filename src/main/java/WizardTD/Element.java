package WizardTD;

import processing.core.PApplet;

public class Element {
    private PApplet pApplet;
    Button FF, P, T, U1, U2, U3, M;
    private int num_Wave;
    private int wait_Wave;
    private int mana_cap;
    private int mana_current;
    private int max_Wave;
    private boolean pressed = false;

    public int getNum_Wave() {
        return num_Wave;
    }

    public void setNum_Wave(int num_Wave) {
        this.num_Wave = num_Wave;
    }

    public int getWait_Wave() {
        return wait_Wave;
    }

    public void setWait_Wave(int wait_Wave) {
        this.wait_Wave = wait_Wave;
    }

    public int getMana_cap() {
        return mana_cap;
    }

    public void setMana_cap(int mana_cap) {
        this.mana_cap = mana_cap;
    }

    public int getMana_current() {
        return mana_current;
    }

    public void setMana_current(int mana_current) {
        this.mana_current = mana_current;
    }

    public Element(PApplet pApplet)
    {
        this.pApplet = pApplet;
        this.num_Wave = 3;
        this.wait_Wave = 2;
        this.mana_cap = 1000;
        this.mana_current = 200;
        this.max_Wave = 4;
        FF = new Button(pApplet, 648, 50, "FF", "2x speed");
        P = new Button(pApplet, 648, 110, "F", "PAUSE");
        T = new Button(pApplet, 648, 170, "T", "Build tower");
        U1 = new Button(pApplet, 648, 230, "U1", "Upgrade range");
        U2 = new Button(pApplet, 648, 290, "U2", "Upgrade speed");
        U3 = new Button(pApplet, 648, 350, "U3", "Upgrade damage");
        M = new Button(pApplet, 648, 410, "M", "Mana pool cost: 100");
    }

    public void buttonInteract()
    {
        if (pApplet.key == 't')
        {
            T.press();
            pApplet.key = 'x';
        }
    }

    public void display()
    {
        // side menu
        int[] color = Colors.BROWN.color.getColor();
        pApplet.fill(color[0], color[1], color[2]);
        pApplet.rect(640, 40, 120, 680);
        pApplet.noStroke();

        // top of the game
        pApplet.fill(color[0], color[1], color[2]);
        pApplet.rect(0, 0, 760, 40);
        pApplet.noStroke();

        //wave
        pApplet.textSize(20);
        pApplet.fill(0);
        if (num_Wave < max_Wave)
        {
            String display_Waves = String.format("Wave %d start: %d", num_Wave, wait_Wave);
            pApplet.text(display_Waves, 16, 10, 192, 32);
        }

        //mana
        pApplet.textSize(20);
        pApplet.fill(0);
        pApplet.text("MANA:", 316, 10, 70, 64);

        //mana bar
        pApplet.fill(255);
        pApplet.stroke(0);
        pApplet.strokeWeight(2);
        pApplet.rect(390, 10, 300, 20);
        pApplet.noStroke();

        int mana_current = 175;
        int mana_cap = 1000;
        float pixelCurrentMana = (float) (300.0/mana_cap) * mana_current;
        color = Colors.LIGHT_BLUE.color.getColor();
        pApplet.fill(color[0], color[1], color[2]);
        pApplet.strokeWeight(2);
        pApplet.stroke(0);
        pApplet.rect(390, 11, pixelCurrentMana, 18);
        pApplet.noStroke();
        pApplet.fill(0);
        pApplet.textSize(18);
        pApplet.textAlign(pApplet.CENTER);
        String displayMana = String.format("%d/%d", mana_current, mana_cap);
        pApplet.text(displayMana, 390, 10, 300, 64);

        buttonInteract();

        FF.display_Button();
        P.display_Button();
        T.display_Button();
        U1.display_Button();
        U2.display_Button();
        U3.display_Button();
        M.display_Button();
    }
}
