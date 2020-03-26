package Cerego;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
    private Word currentWord;

    @FXML private TextField keyWordTF;
    @FXML private TextField oxfordUrlTF;
    @FXML private TextField cambridgeUrlTF;
    @FXML private TextField longmanUrlTF;
    @FXML private TextField collinUrlTF;
    @FXML private TextField macMillanUrlTF;
    @FXML private TextArea checkTextArea;

    public void handleFindBtn(ActionEvent event) {
        OxfordDict oxfordAgent = new OxfordDict(keyWordTF.getText());
        oxfordUrlTF.setText(oxfordAgent.getUrl());
        currentWord = new Word(oxfordAgent.getHeadWord(), oxfordAgent.getIPA(), oxfordAgent.getSoundURL(),
                oxfordAgent.getFreqUse(),oxfordAgent.getDef(), oxfordAgent.getExamples(), oxfordAgent.getInflections());

        CambridgeDict cambridgeAgent = new CambridgeDict(keyWordTF.getText());
        cambridgeUrlTF.setText(cambridgeAgent.getUrl());
        currentWord.addExample(cambridgeAgent.getExamples());

        LongmanDict longmanAgent = new LongmanDict(keyWordTF.getText());
        longmanUrlTF.setText(longmanAgent.getUrl());
        currentWord.addExample(longmanAgent.getExamples());

        CollinDict collinDictAgent = new CollinDict(keyWordTF.getText());
        collinUrlTF.setText(collinDictAgent.getUrl());
        currentWord.addExample(collinDictAgent.getExamples());

        MacMillanDict macMillanDictAgent = new MacMillanDict(keyWordTF.getText());
        macMillanUrlTF.setText(macMillanDictAgent.getUrl());
        currentWord.addExample(macMillanDictAgent.getExamples());

        currentWord.standardizeForCerego();
        checkTextArea.setText(currentWord.toString());
    }

    public void handleretryWithLink(ActionEvent event) {
        OxfordDict oxfordAgent = new OxfordDict(keyWordTF.getText(), oxfordUrlTF.getText());
        currentWord = new Word(oxfordAgent.getHeadWord(), oxfordAgent.getIPA(), oxfordAgent.getSoundURL(),
                oxfordAgent.getFreqUse(),oxfordAgent.getDef(), oxfordAgent.getExamples(), oxfordAgent.getInflections());

        CambridgeDict cambridgeAgent = new CambridgeDict(keyWordTF.getText(), cambridgeUrlTF.getText());
        cambridgeUrlTF.setText(cambridgeAgent.getUrl());
        currentWord.addExample(cambridgeAgent.getExamples());

        LongmanDict longmanAgent = new LongmanDict(keyWordTF.getText(), longmanUrlTF.getText());
        longmanUrlTF.setText(longmanAgent.getUrl());
        currentWord.addExample(longmanAgent.getExamples());

        CollinDict collinDictAgent = new CollinDict(keyWordTF.getText(), collinUrlTF.getText());
        collinUrlTF.setText(collinDictAgent.getUrl());
        currentWord.addExample(collinDictAgent.getExamples());

        MacMillanDict macMillanDictAgent = new MacMillanDict(keyWordTF.getText(), macMillanUrlTF.getText());
        macMillanUrlTF.setText(macMillanDictAgent.getUrl());
        currentWord.addExample(macMillanDictAgent.getExamples());

        currentWord.standardizeForCerego();
        checkTextArea.setText(currentWord.toString());
    }
}
