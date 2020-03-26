package Cerego;

import javafx.scene.control.TextInputDialog;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class LongmanDict extends Dictionary {
    public static final String searchQuery = "https://www.ldoceonline.com/search/english/direct/?q=";

    LongmanDict(String keyWord) {
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

    LongmanDict(String keyWord, String keyWordURL) {
        findWordWithURL(keyWordURL);
    }

    public void setDefAndEx() {
        Elements meanings = getPage().select(".dictionary > .dictentry").first().select(".Sense");
        Element defAndEx;
        if (meanings.size() > 1) {
            TextInputDialog meaningChoiseDialog = new TextInputDialog("1");
            meaningChoiseDialog.setTitle("Longman Dictionary");
            meaningChoiseDialog.setHeaderText("There are more than 1 meaning, choose one meaning");
            meaningChoiseDialog.showAndWait();
            int userChoice = Integer.parseInt(meaningChoiseDialog.getEditor().getText());
            defAndEx = meanings.get(userChoice - 1);
        } else {
            defAndEx = meanings.first();
        }
        setDef(defAndEx.select(".DEF").text());
        //Get the Example
        Elements elementsOfExamples = defAndEx.select("> .EXAMPLE,.GramExa");
        ArrayList<Example> listOfExamples = new ArrayList<>();
        for (Element ex : elementsOfExamples) {
            if (ex.hasClass("GramExa")) {
                String headWord = ex.select(".PROPFORM").text();
                Elements subExamples = ex.select(".EXAMPLE");
                for (Element subex : subExamples)
                    listOfExamples.add(new Example(headWord,subex.text()));
            } else {
                listOfExamples.add(new Example(ex.text()));
            }
        }
        setExamples(listOfExamples);
    }
}
