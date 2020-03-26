package Cerego;

import javafx.scene.control.TextInputDialog;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MacMillanDict extends Dictionary{
    public static final String searchQuery = "https://www.macmillandictionary.com/search/british/direct/?q=";

    MacMillanDict(String keyWord) {
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

    MacMillanDict(String keyWord, String keyWordURL) {
        findWordWithURL(keyWordURL);
    }

    public void setDefAndEx() {
        //Get Definition
        Elements meanings = getPage().select(".entry-sense.anchor");
        Element defAndEx;
        if (meanings.size() > 1) {
            TextInputDialog meaningChoiseDialog = new TextInputDialog("1");
            meaningChoiseDialog.setTitle("MacMillan Dictionary");
            meaningChoiseDialog.setHeaderText("There are more than 1 meaning, choose one meaning");
            meaningChoiseDialog.showAndWait();
            int userChoice = Integer.parseInt(meaningChoiseDialog.getEditor().getText());
            defAndEx = meanings.get(userChoice - 1);
        } else {
            defAndEx = meanings.first();
        }
        setDef(defAndEx.select(".DEFINITION").text());
        Elements elementsOfExamples = defAndEx.select(".EXAMPLES");
        ArrayList<Example> listOfExamples = new ArrayList<>();
        for (Element ex : elementsOfExamples) {
            Example thisExample = new Example(ex.select("> .EXAMPLE").text());
            if (!ex.select("> .PATTERNS-COLLOCATIONS").isEmpty()) {
                String headWord = ex.select("> .PATTERNS-COLLOCATIONS").text();
                thisExample.setHeadExample(headWord);
            }
            listOfExamples.add(thisExample);
        }
        setExamples(listOfExamples);
    }
}
