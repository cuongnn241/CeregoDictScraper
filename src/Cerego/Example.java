package Cerego;

public class Example {
    private String headExample;
    private String example;

    Example(String example) {
        headExample = null;
        this.example = example;
    }

    Example(String headExample, String example) {
        this.headExample = headExample;
        this.example = example;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getHeadExample() {
        return headExample;
    }

    public void setHeadExample(String headExample) {
        this.headExample = headExample;
    }

    @Override
    public String toString() {
        StringBuilder stringToReturn = new StringBuilder();
        if (headExample != null && !headExample.isEmpty())
            stringToReturn.append(headExample).append(" | ");
        stringToReturn.append(example);
        return stringToReturn.toString();
    }
}


