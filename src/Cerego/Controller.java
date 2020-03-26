package Cerego;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;

public class Controller {
    ArrayList<ExportedWord> listOfWords = new ArrayList<>();
    StringBuilder listOfExportedWord = new StringBuilder();
    private Word currentWord;

    @FXML private TextField keyWordTF;
    @FXML private TextField oxfordUrlTF;
    @FXML private TextField cambridgeUrlTF;
    @FXML private TextField longmanUrlTF;
    @FXML private TextField collinUrlTF;
    @FXML private TextField macMillanUrlTF;
    @FXML private TextArea checkTextArea;
    @FXML private TextField fileDirectoryTF;
    @FXML private HBox windowID;
    @FXML private TextArea exportedWordTextArea;

    public void keyWordEnterAction(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER))
            findButtonAction();
    }

    public void handleFindBtn(ActionEvent event) {
        findButtonAction();
    }

    private void openBrowser(String keyWord) {
        try {
            Desktop.getDesktop().browse(new URL(OxfordDict.searchQuery + keyWord).toURI());
            Desktop.getDesktop().browse(new URL(CambridgeDict.searchQuery + keyWord).toURI());
            Desktop.getDesktop().browse(new URL(LongmanDict.searchQuery + keyWord).toURI());
            Desktop.getDesktop().browse(new URL(CollinDict.searchQuery + keyWord).toURI());
            Desktop.getDesktop().browse(new URL(MacMillanDict.searchQuery + keyWord).toURI());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void findButtonAction() {
        openBrowser(keyWordTF.getText());
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

    private void clearTF() {
        keyWordTF.clear();
        oxfordUrlTF.clear();
        cambridgeUrlTF.clear();
        longmanUrlTF.clear();
        collinUrlTF.clear();
        macMillanUrlTF.clear();
        checkTextArea.clear();
    }

    public void handleAddBtn(ActionEvent event) {
        String rawText = checkTextArea.getText();
        String[] rawFields = rawText.split("\n");
        String headWord = rawFields[0];
        String IPA = rawFields[1];
        String soundURL = rawFields[2];
        String def = rawFields[3];
        ArrayList<String> examples = new ArrayList<>();
        for (int i = 4; i < rawFields.length; i++)
            examples.add(rawFields[i]);
        listOfWords.add(new ExportedWord(headWord, IPA, soundURL, def, examples));
        listOfExportedWord.append(headWord).append("\n");
        clearTF();
        exportedWordTextArea.setText(listOfExportedWord.toString());
    }

    public void handleBrowse(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName("CeregoWord");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Worksheet", "*.xlsx"));
        Stage stage = (Stage) windowID.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            fileDirectoryTF.setText(file.getAbsolutePath());
        }
    }

    public void handleExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exiting Confirmation");
        alert.setHeaderText("You are about to exit!");
        alert.setContentText("Are you sure with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) windowID.getScene().getWindow();
            stage.close();
        }
    }

    public void handleExportBtn(ActionEvent event) {
        if (!fileDirectoryTF.getText().isEmpty()) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Cerego Words");
            int rowCount = 0;

            //Write the header for Excel File
            ArrayList<String> header = new ArrayList<>();
            header.add("anchor_text");
            header.add("anchor_sound");
            header.add("association_1_text");
            int maxExampleNum = 0;
            for (ExportedWord entry : listOfWords) {
                if (entry.getExamples().size() > maxExampleNum)
                    maxExampleNum = entry.getExamples().size();
            }
            for (int i = 1 ; i <= maxExampleNum; i++)
                header.add("sentence_" + i + "_text");
            Row headerRow = sheet.createRow(rowCount++);
            int headerRowColumnCount = 0;
            for (String colValue : header) {
                Cell cell = headerRow.createCell(headerRowColumnCount++);
                cell.setCellValue(colValue);
            }
            for (ExportedWord entry : listOfWords) {
                Row row = sheet.createRow(rowCount++);
                int columnCount = 0;
                for (String colValue : entry.toStringArray()) {
                    Cell cell = row.createCell(columnCount++);
                    cell.setCellValue(colValue);
                }
            }
            try {
                FileOutputStream outputStream = new FileOutputStream(fileDirectoryTF.getText());
                workbook.write(outputStream);
                outputStream.close();
                workbook.close();

                //Show Message Box
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exported Successfully");
                alert.setHeaderText(null);
                alert.setContentText("You've successfully exported to " + fileDirectoryTF.getText());
                alert.showAndWait();
            } catch (IOException e) {
                System.out.println("Error!");
            }
        }
    }
}
