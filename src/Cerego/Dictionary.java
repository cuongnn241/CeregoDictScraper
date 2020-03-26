package Cerego;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

public abstract class Dictionary {
    private String url;
    private String headWord;
    private String IPA;
    private String soundURL;
    private String pos;
    private String freqUse;
    private String def;
    private ArrayList<String> inflections;
    private ArrayList<Example> examples;
    private Document page;

    public void clearFields() {
        url = headWord = IPA = soundURL = pos = freqUse = def = null;
        inflections = null;
        examples = null;
        page = null;
    }

    public abstract void setDefAndEx();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeadWord() {
        return headWord;
    }

    public void setHeadWord(String headWord) {
        this.headWord = headWord;
    }

    public String getIPA() {
        return IPA;
    }

    public void setIPA(String IPA) {
        this.IPA = IPA;
    }

    public String getSoundURL() {
        return soundURL;
    }

    public void setSoundURL(String soundURL) {
        this.soundURL = soundURL;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getFreqUse() {
        return freqUse;
    }

    public void setFreqUse(String freqUse) {
        this.freqUse = freqUse;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public ArrayList<String> getInflections() {
        return inflections;
    }

    public void setInflections(ArrayList<String> inflections) {
        this.inflections = inflections;
    }

    public ArrayList<Example> getExamples() {
        return examples;
    }

    public void setExamples(ArrayList<Example> examples) {
        this.examples = examples;
    }

    public Document getPage() {
        return page;
    }

    public void setPage() {
        try {
            page = Jsoup.connect(getUrl()).get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
