package Cerego;

public class Inflection {
    private String inflection;
    private String type;
    public static final String PAST_SIMPLE = "past simple";
    public static final String PAST_PARTICIPLE = "past participle";
    public static final String ING = "-ing form";

    Inflection(String inflection) {
        this.inflection = inflection;
    }


    public String getType() {
        return type;
    }

    public void setType(String typeString) {
        if (typeString.contains(PAST_SIMPLE))
            type = PAST_SIMPLE;
        else if (typeString.contains(PAST_PARTICIPLE))
            type = PAST_PARTICIPLE;
        else if (typeString.contains(ING))
            type = ING;
        else
            type = null;
    }

    public boolean hasType() {
        return type != null;
    }

    public String getInflection() {
        return inflection;
    }

    public void setInflection(String inflection) {
        this.inflection = inflection;
    }
}
