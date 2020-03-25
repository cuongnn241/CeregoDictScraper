package Cerego;

import java.util.ArrayList;

public class Word {
    private String word;
    private String IPA;
    private String pronunciationURL;
    private String def;
    private ArrayList<Example> examples;
    private ArrayList<String> inflections;

    Word(String word, String IPA, String pronunciationURL, String def, ArrayList<Example> examples, ArrayList<String> inflections) {
        this.word = word;
        this.IPA = IPA;
        this.pronunciationURL = pronunciationURL;
        this.def = def;
        this.examples = examples;
        this.inflections = inflections;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setIPA(String IPA) {
        this.IPA = IPA;
    }

    public String getIPA() {
        return IPA;
    }

    public void setPronunciationURL(String pronunciationURL) {
        this.pronunciationURL = pronunciationURL;
    }

    public String getPronunciationURL() {
        return pronunciationURL;
    }

    public void setExamples(ArrayList<Example> examples) {
        this.examples = examples;
    }

    public ArrayList<Example> getExamples() {
        return examples;
    }

    public ArrayList<String> getInflections() {
        return inflections;
    }

    public void setInflections(ArrayList<String> inflections) {
        this.inflections = inflections;
    }

    public void addExample(Example example) {
        examples.add(example);
    }

    @Override
    public String toString() {
        StringBuilder wordToPrint = new StringBuilder("Word=" + word + '\n' +
                "IPA=" + IPA + '\n' +
                "PronunciationURL=" + pronunciationURL + '\n');
        wordToPrint.append("Infections=\n");
        for(String infection : inflections) {
            wordToPrint.append("\t").append(infection).append("\n");
        }
        wordToPrint.append("Def= ").append(def).append("\n");
        wordToPrint.append("Example=\n");
        for(Example example : examples) {
            wordToPrint.append("\t").append(example.toString()).append("\n");
        }
        return wordToPrint.toString();
    }
}
