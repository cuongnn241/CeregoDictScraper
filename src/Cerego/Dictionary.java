package Cerego;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.util.ArrayList;

public class Dictionary {
    private String url;
    private String headWord;
    private String IPA;
    private String soundURL;
    private String pos;
    private String freqUse;
    private String def;
    private ArrayList<String> inflections;
    private ArrayList<Example> examples;

    public String getUrl() {
        return url;
    }

    public String getSoundURL() {
        return soundURL;
    }

    public String getHeadWord() {
        return headWord;
    }

    public String getIPA() {
        return IPA;
    }

    public String getDef() {
        return def;
    }

    public ArrayList<Example> getExamples() {
        return examples;
    }

    public String getPos() {
        return pos;
    }

    public ArrayList<String> getInflections() {
        return inflections;
    }

    public String getFreqUse() {
        return freqUse;
    }
}
