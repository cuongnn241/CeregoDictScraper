package Cerego;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.util.ArrayList;

public class CambridgeDict {
    private static final String searchQuery = "https://dictionary.cambridge.org/search/english/direct/?source=gadgets&q=";
    private String url;
    private String headWord;
    private String IPA;
    private String soundURL;
    private String pos;
    private String freqUse;
    private String def;
    private ArrayList<String> inflections;
    private ArrayList<Example> examples;

    CambridgeDict(String keyWord) {
        try {
            url = Jsoup.connect(searchQuery + keyWord)
                    .followRedirects(true)
                    .execute().url().toExternalForm();
            updateFields();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    CambridgeDict(String keyWord, String url) {
        this.url = url;
        updateFields();
    }

    private void updateFields()  {
        try {
            Document page = Jsoup.connect(url).get();

            //Get Definition
            Elements meanings = page.select(".pr .dictionary[data-id=\"cald4\"] .entry-body .pos-body > .pr > .sense-body");
            Element meaningField;
            if (meanings.size() > 1) {
                JFrame frame = new JFrame();
                int userChoice = Integer.parseInt(JOptionPane.showInputDialog(frame, "Cambridge: There are more than 1 meaning, choose one meaning"));
                meaningField = meanings.get(userChoice - 1);
            } else {
                meaningField = meanings.first();
            }
            Elements specMeaning = meaningField.select("> .def-block");
            Element defAndEx;
            if (specMeaning.size() > 1) {
                JFrame frame = new JFrame();
                int userChoice = Integer.parseInt(JOptionPane.showInputDialog(frame, "Cambridge: There are more than 1 meaning, choose one meaning"));
                defAndEx = specMeaning.get(userChoice - 1);
            } else {
                defAndEx = specMeaning.first();
            }
            def = defAndEx.select("> .ddef_h > .def").text().replace(":", "");
            Elements elementsOfExamples = defAndEx.select("> .def-body > .examp");
            examples = new ArrayList<>();
            for (Element ex : elementsOfExamples) {
                if (ex.children().size() == 1) {
                    //The example doesn't have the headWord
                    examples.add(new Example(ex.select(".eg").text()));
                } else {
                    //The example has the headWord
                    examples.add(new Example(ex.select(".gram,.lab").text(), ex.getElementsByClass("eg").text()));
                }
            }

            //Check for Extra Example
            Elements extraExamples = meaningField.select("> .daccord .eg");
            for (Element ex : extraExamples) {
                examples.add(new Example(ex.text()));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

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
