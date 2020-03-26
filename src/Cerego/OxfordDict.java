package Cerego;

import javafx.scene.control.TextInputDialog;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class OxfordDict extends Dictionary{
    public static final String searchQuery = "https://www.oxfordlearnersdictionaries.com/search/english/?q=";

    OxfordDict(String keyWord) {
        try {
            String directedURL = Jsoup.connect(searchQuery + keyWord)
                    .followRedirects(true)
                    .execute().url().toExternalForm();
            updateFields(directedURL);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void findWordWithURL(String wordURL) {
        try {
            updateFields(wordURL);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    OxfordDict(String keyWord, String keyWordURL) {
        findWordWithURL(keyWordURL);
    }

    public void setSoundURL() {
        super.setSoundURL(getPage().getElementsByClass("sound audio_play_button pron-us icon-audio").
                first().attr("data-src-mp3"));
    }

    public void setHeadWord() {
        super.setHeadWord(getPage().select(".headword[hclass=\"headword\"]").text());
    }

    public void setIPA() {
        super.setIPA(getPage().getElementsByClass("phons_n_am").first().text());
    }

    public void setPos() {
        super.setPos(getPage().getElementsByClass("pos").first().text());
    }

    public void setInflections() {
        ArrayList<Inflection> inflections = new ArrayList<>();
        if (getPos().equals("noun")) {
            Elements plural = getPage().select(".inflections > .inflected_form");
            for (Element form : plural) {
                inflections.add(new Inflection(form.ownText()));
            }
            inflections.add(new Inflection(getHeadWord() + "s"));
            inflections.add(new Inflection(getHeadWord() + "es"));
        }
        if (getPos().equals("verb")) {
            Elements verbForms = getPage().select(".verb_form > .verb_form");
            if (verbForms.size() != 0) {
                for (Element form : verbForms) {
                    Inflection inflectionToAdd = new Inflection(form.ownText());
                    String typeString = form.select("> .vf_prefix").text();
                    inflectionToAdd.setType(typeString);
                    inflections.add(inflectionToAdd);
                }
            }
        }
        if (getPos().equals("adjective")) {
            Elements comparison = getPage().select(".inflections > .inflected_form");
            for (Element form : comparison) {
                inflections.add(new Inflection(form.text()));
            }
        }
        for (int i = 0; i < inflections.size(); i++)
            if (inflections.get(i).getInflection().equals(getHeadWord()))
                inflections.remove(i);
        inflections.add(new Inflection(getHeadWord()));
        super.setInflections(inflections);
    }

    @Override
    public void setDefAndEx() {
        //Get Definition
        Elements defAndEx = getPage().select(".entry > .sense_single");
        if (defAndEx.isEmpty()) {
            //The word has more than 1 meaning
            TextInputDialog meaningChoiseDialog = new TextInputDialog("1");
            meaningChoiseDialog.setTitle("Oxford Dictionary");
            meaningChoiseDialog.setHeaderText("There are more than 1 meaning, choose one meaning");
            meaningChoiseDialog.showAndWait();
            int userChoice = Integer.parseInt(meaningChoiseDialog.getEditor().getText());
            System.out.println(userChoice);
            String meaningTag = "[sensenum=" + userChoice + "]";
            defAndEx = getPage().select(".entry > .senses_multiple .sense" + meaningTag);
        }
        setFreqUse(defAndEx.select(".sensetop > .cf").text());
        setDef(defAndEx.select(".def").text());

        //Get Example
        Elements elementsOfExamples = defAndEx.select(".sense > .examples [htag=\"li\"]");
        ArrayList<Example> examples = new ArrayList<>();
        for (Element ex : elementsOfExamples) {
            if (ex.children().size() == 1) {
                //The example doesn't have the headWord
                examples.add(new Example(ex.getElementsByClass("x").text()));
            }  else if (ex.children().size() == 2) {
                //The example has the headWord
                examples.add(new Example(ex.select(".cf,.labels").text(), ex.getElementsByClass("x").text()));
            } else {
                examples.add(new Example(ex.select(".cf").text() + " " + ex.select(".labels").text()
                        , ex.getElementsByClass("x").text()));
            }
        }
        //Check for Extra example
        Elements extraExamples = defAndEx.select(".collapse .examples [unbox=\"extra_examples\"] [htag=\"li\"]");
        for (Element ex : extraExamples) {
            //Extra examples don't have headword
            examples.add(new Example(ex.getElementsByClass("unx").text()));
        }
        setExamples(examples);
    }

    public void updateFields(String wordURL)  {
        clearFields();
        setUrl(wordURL);
        try {
            setPage();
            setSoundURL();
            setHeadWord();
            setIPA();
            setPos();
            setInflections();
            setDefAndEx();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
