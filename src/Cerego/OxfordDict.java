package Cerego;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.util.ArrayList;

public class OxfordDict {
    private static final String searchQuery = "https://www.oxfordlearnersdictionaries.com/search/english/?q=";
    private String url;
    private String headWord;
    private String IPA;
    private String soundURL;
    private String pos;
    private String freqUse;
    private String def;
    private ArrayList<String> inflections;
    private ArrayList<Example> examples;

    OxfordDict(String keyWord) {
        try {
            url = Jsoup.connect(searchQuery + keyWord)
                    .followRedirects(true)
                    .execute().url().toExternalForm();
            updateFields();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    OxfordDict(String keyWord, String url) {
        this.url = url;
        updateFields();
    }

    private void updateFields()  {
        try {
            Document page = Jsoup.connect(url).get();
            //Get Pronunciation URL
            soundURL = page.getElementsByClass("sound audio_play_button pron-us icon-audio").
                    first().attr("data-src-mp3");

            //Get Word
            headWord = page.select(".headword[hclass=\"headword\"]").text();

            //Get IPA
            IPA = page.getElementsByClass("phons_n_am").first().text();

            //Get Part Of Speech
            pos = page.getElementsByClass("pos").first().text();

            //Get Possible Conj
            inflections = new ArrayList<>();
            if (pos.equals("noun")) {
                Elements plural = page.select(".inflections > .inflected_form");
                for (Element form : plural) {
                    inflections.add(form.ownText());
                }
            }
            if (pos.equals("verb")) {
                Elements verbForms = page.select(".verb_form > .verb_form");
                if (verbForms.size() != 0) {
                    for (Element form : verbForms) {
                        inflections.add(form.ownText());
                    }
                }
            }
            if (pos.equals("adjective")) {
                Elements comparison = page.select(".inflections > .inflected_form");
                for (Element form : comparison) {
                    inflections.add(form.text());
                }
            }
            inflections.add(headWord + "s");
            inflections.add(headWord + "es");
            inflections.add(headWord);

            //Get Definition
            Elements defAndEx = page.select(".entry > .sense_single");
            if (defAndEx.isEmpty()) {
                //The word has more than 1 meaning
                JFrame frame = new JFrame();
                int userChoice = Integer.parseInt(JOptionPane.showInputDialog(frame, "Oxford: There are more than 1 meaning, choose one meaning"));
                System.out.println(userChoice);
                String meaningTag = "[sensenum=" + userChoice + "]";
                defAndEx = page.select(".entry > .senses_multiple .sense" + meaningTag);
            }
            freqUse = defAndEx.select(".sensetop > .cf").text();
            def = defAndEx.select(".dis-g").text() + " "+ defAndEx.select(".def").text();

            //Get Example
            Elements elementsOfExamples = defAndEx.select(".sense > .examples [htag=\"li\"]");
            examples = new ArrayList<>();
            for (Element ex : elementsOfExamples) {
                if (ex.children().size() == 1) {
                    //The example doesn't have the headWord
                    examples.add(new Example(ex.getElementsByClass("x").text()));
                } else {
                    //The example has the headWord
                    examples.add(new Example(ex.getElementsByClass("cf").text(), ex.getElementsByClass("x").text()));
                }
            }
            //Check for Extra example
            Elements extraExamples = defAndEx.select(".collapse .examples [unbox=\"extra_examples\"] [htag=\"li\"]");
            for (Element ex : extraExamples) {
                //Extra examples don't have headword
                examples.add(new Example(ex.getElementsByClass("unx").text()));
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
