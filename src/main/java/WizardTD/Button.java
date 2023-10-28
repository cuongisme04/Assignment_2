package WizardTD;

import processing.core.PApplet;

public class Button {
    private PApplet pApplet;
    private float x;
    private float y;
    private float width;
    private float height;
    private String symbol;
    private String description;
    private boolean pressed = false;

    public Button(PApplet pApplet, float x, float y, String symbol, String description)
    {
        this.pApplet = pApplet;
        this.x = x;
        this.y = y;
        this.width = 45;
        this.height = 45;
        this.symbol = symbol;
        this.description = description;
    }

    public void press()
    {
        pressed = !pressed;
    }

    public boolean isPressed()
    {
        return pressed;
    }

    public void display_Button()
    {
        int[]  color;
        if (pressed)
        {
            color = Colors.YELLOW.color.getColor();
        }
        else
        {
            color = Colors.BROWN.color.getColor();
        }
        pApplet.fill(color[0], color[1], color[2]);
        pApplet.strokeWeight(2);
        pApplet.stroke(0);
        pApplet.rect(x, y, width, height);
        pApplet.noStroke();

        pApplet.fill(0);
        pApplet.textSize(24);
        pApplet.textAlign(pApplet.CENTER, pApplet.CENTER);
        pApplet.text(symbol, x, y, 45, 45);

        pApplet.fill(0);
        pApplet.textSize(11);
        pApplet.textAlign(pApplet.LEFT);
        pApplet.text(description, x + 50, y + 2, 65, 45);
    }
}
