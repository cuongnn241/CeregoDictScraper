package Cerego;

import javafx.scene.control.TextInputDialog;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class CambridgeDict extends Dictionary {
    public static final String searchQuery = "https://dictionary.cambridge.org/search/english/direct/?source=gadgets&q=";

    CambridgeDict(String keyWord) {
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

    public void updateFields(String wordURL)  {
        clearFields();
        setUrl(wordURL);
        try {
            setPage();
            setDefAndEx();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    CambridgeDict(String keyWord, String keyWordURL) {
        findWordWithURL(keyWordURL);
    }

    public void setDefAndEx() {
        //Get Definition
        Elements meanings = getPage().select(".pr .dictionary[data-id=\"cald4\"] .entry-body .pos-body > .pr > .sense-body");
        Element meaningField;
        if (meanings.size() > 1) {
            TextInputDialog meaningChoiseDialog = new TextInputDialog("1");
            meaningChoiseDialog.setTitle("Cambridge Dictionary");
            meaningChoiseDialog.setHeaderText("There are more than 1 meaning, choose one meaning");
            meaningChoiseDialog.showAndWait();
            int userChoice = Integer.parseInt(meaningChoiseDialog.getEditor().getText());
            meaningField = meanings.get(userChoice - 1);
        } else {
            meaningField = meanings.first();
        }
        Elements specMeaning = meaningField.select("> .def-block");
        Element defAndEx;
        if (specMeaning.size() > 1) {
            TextInputDialog meaningChoiseDialog = new TextInputDialog("1");
            meaningChoiseDialog.setTitle("Cambridge Dictionary");
            meaningChoiseDialog.setHeaderText("There are more than 1 meaning, choose one meaning");
            meaningChoiseDialog.showAndWait();
            int userChoice = Integer.parseInt(meaningChoiseDialog.getEditor().getText());
            defAndEx = specMeaning.get(userChoice - 1);
        } else {
            defAndEx = specMeaning.first();
        }
        setDef(defAndEx.select("> .ddef_h > .def").text().replace(":", ""));
        Elements elementsOfExamples = defAndEx.select("> .def-body > .examp");
        ArrayList<Example> examples = new ArrayList<>();
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
        setExamples(examples);
    }
}
