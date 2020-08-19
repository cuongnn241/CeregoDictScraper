package Cerego;

import java.util.ArrayList;

public class ExportedWord {
    private String word;
    private String IPA;
    private String pronunciationURL;
    private String def;
    private ArrayList<String> examples;

    ExportedWord(String word, String IPA, String pronunciationURL, String def, ArrayList<String> examples) {
        this.word = word;
        this.IPA = IPA;
        this.pronunciationURL = pronunciationURL;
        this.def = def;
        this.examples = examples;
    }

    ExportedWord(String word, String IPA, String pronunciationURL, String def) {
        this.word = word;
        this.IPA = IPA;
        this.pronunciationURL = pronunciationURL;
        this.def = def;
    }

    public ArrayList<String> toStringArray() {
        ArrayList<String> stringData = new ArrayList<>();
        stringData.add(word);
        stringData.add(pronunciationURL);
        stringData.add(def);
        stringData.addAll(examples);
        return stringData;
    }

    public ArrayList<String> getExamples() {
        return examples;
    }

    public String getWord() {
        return word;
    }

    public String getDef() {
        return def;
    }

    public String getIPA() {
        return IPA;
    }

    public String getPronunciationURL() {
        return pronunciationURL;
    }
}
