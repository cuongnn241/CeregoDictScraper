package Cerego;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Controller {
    private Word currentWord;

    @FXML
    private TextField keyWordTF;

    public void handleFindBtn(ActionEvent event) {
        OxfordDict oxfordAgent = new OxfordDict(keyWordTF.getText());
        currentWord = new Word(oxfordAgent.getHeadWord(), oxfordAgent.getIPA(), oxfordAgent.getSoundURL(), oxfordAgent.getDef(), oxfordAgent.getExamples(), oxfordAgent.getInflections());
        System.out.println(currentWord.toString());
        CambridgeDict cambridgeAgent = new CambridgeDict(ke)
    }
}
