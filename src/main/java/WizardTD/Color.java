package WizardTD;

public class Color {
    private int r;
    private int g;
    private int b;

    public Color(int r, int g, int b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color()
    {
        this.r = 0;
        this.g = 0;
        this.b = 0;
    }

    public int[] getColor()
    {
        return new int[]{r, g, b};
    }

}
