package Cerego;

import javafx.scene.control.TextInputDialog;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class CollinDict extends Dictionary {
    public static final String searchQuery = "https://www.collinsdictionary.com/search/?dictCode=english&q=";

    CollinDict(String keyWord) {
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

    CollinDict(String keyWord, String keyWordURL) {
        findWordWithURL(keyWordURL);
    }

    public void setDefAndEx() {
        Elements meanings = getPage().select(".dictionary.Cob_Adv_Brit .sense");
        Element defAndEx;
        if (meanings.size() > 1) {
            TextInputDialog meaningChoiseDialog = new TextInputDialog("1");
            meaningChoiseDialog.setTitle("Collin Dictionary");
            meaningChoiseDialog.setHeaderText("There are more than 1 meaning, choose one meaning");
            meaningChoiseDialog.showAndWait();
            int userChoice = Integer.parseInt(meaningChoiseDialog.getEditor().getText());
            defAndEx = meanings.get(userChoice - 1);
        } else {
            defAndEx = meanings.first();
        }
        setDef(defAndEx.select("> .def").text());
        Elements elementsOfExamples = defAndEx.select("> .cit.type-example");
        ArrayList<Example> listOfExamples = new ArrayList<>();
        for (Element ex : elementsOfExamples) {
            Example thisExample = new Example(ex.select("> .quote").text());
            if (!ex.select("> .lbl").isEmpty()) {
                String headWord = ex.select("> .lbl").text();
                thisExample.setHeadExample(headWord);
            }
            listOfExamples.add(thisExample);
        }
        setExamples(listOfExamples);
    }
}
