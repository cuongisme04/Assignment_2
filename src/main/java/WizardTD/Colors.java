package WizardTD;

public enum Colors {
    BLACK(new Color(0, 0, 0)),
    WHITE(new Color(255, 255, 255)),
    YELLOW(new Color(239, 222, 66)),
    RED(new Color(255, 0 ,0)),
    BROWN(new Color(132, 114, 74)),
    LIGHT_BLUE(new Color(0, 255, 255)),
    BLUE(new Color(0, 102, 204)),
    GREEN(new Color(0, 255, 0)),
    MAGENTA(new Color(255, 0, 255));


    public final Color color;

    private Colors(Color color)
    {
        this.color = color;
    }
}
