package Cerego;

import java.util.ArrayList;

public class Word {
    private String word;
    private String IPA;
    private String pronunciationURL;
    private String freqUse;
    private String def;
    private ArrayList<Example> examples;
    private ArrayList<String> inflections;

    public void clearWord() {
        word = IPA = pronunciationURL = def = freqUse = null;
        examples = null;
        inflections = null;
    }

    Word(String word, String IPA, String pronunciationURL,String freqUse, String def, ArrayList<Example> examples, ArrayList<String> inflections) {
        this.word = word;
        this.IPA = IPA;
        this.pronunciationURL = pronunciationURL;
        this.freqUse = freqUse;
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

    public void addExample(ArrayList<Example> newListofExample) {
        examples.addAll(newListofExample);
    }

    public void standardizeForCerego() {
        removeHeadWordFromFreqUse();
        removeHeadWordFromExample();
    }

    public void removeHeadWordFromFreqUse() {
        for (String inflect : inflections) {
            if (freqUse != null && freqUse.contains(inflect))
                freqUse = freqUse.replace(inflect, "____");
        }
    }

    public void removeHeadWordFromExample() {
        for (int i = 0; i < examples.size(); i++) {
            for (String inflect : inflections) {
                if (examples.get(i).getHeadExample() != null && examples.get(i).getHeadExample().contains(inflect))
                    examples.get(i).setHeadExample(examples.get(i).getHeadExample().replace(inflect, "____"));
            }
            for (String inflect : inflections) {
                Boolean found = false;
                String thisExample = examples.get(i).getExample();
                String thisExampleLowerCase = thisExample.toLowerCase();
                if (thisExampleLowerCase.contains(inflect)) {
                    int firstIdx = thisExampleLowerCase.indexOf(inflect);
                    int lastIdx = firstIdx + inflect.length();
                    String editSentence = thisExample.substring(0, firstIdx) + "*" + thisExample.substring(firstIdx, lastIdx) + "*" + thisExample.substring(lastIdx);
                    examples.get(i).setExample(editSentence);
                    found = true;
                }
                if (found)
                    break;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder infoToPrint = new StringBuilder(word + '\n' + IPA + '\n' + pronunciationURL + '\n');
        if (!freqUse.isEmpty())
            infoToPrint.append(freqUse).append(" ");
            infoToPrint.append(def);
        for(Example example : examples) {
            infoToPrint.append("\n").append(example.toString());
        }
        return infoToPrint.toString();
    }

    public String getFreqUse() {
        return freqUse;
    }

    public void setFreqUse(String freqUse) {
        this.freqUse = freqUse;
    }
}
