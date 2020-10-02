package com.company;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.company.TextEditor.readFileAsString;

public class TextEditorLogic {
    ArrayList<String> searchResults = new ArrayList<>();
    int entry = 0;
    TextEditor textEditor;
    JCheckBox regexCheckButton;
    JTextArea fileContentArea;
    JFileChooser fileChooser;
    JTextField searchField;

    public TextEditorLogic(TextEditor textEditor){
        this.textEditor = textEditor;
        regexCheckButton = textEditor.getRegexCheckButton();
        fileContentArea = textEditor.getFileContentArea();
        fileChooser = textEditor.getFileChooser();
        searchField = textEditor.getSearchField();
    }


    public void openFile() {
        fileChooser.setDialogTitle("Specify a file to open");

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                if (!selectedFile.exists()) {
                    fileContentArea.setText("");
                } else {
                    fileContentArea.setText(readFileAsString(selectedFile.getAbsolutePath()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            fileContentArea.setText("");
        }
    }

    public void saveFile() {
        fileChooser.setDialogTitle("Specify a file to save");
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (PrintWriter printWriter = new PrintWriter(fileToSave)) {
                printWriter.print(fileContentArea.getText()); // prints a string
            } catch (IOException e) {
                System.out.printf("An exception occurs %s", e.getMessage());
            }
        }
    }

    public void searchFile() {
        Matcher matcher;
        String searchTerm = searchField.getText();
        String searchSource = fileContentArea.getText();

        Pattern regexPattern = Pattern.compile(searchTerm);

        if(searchTerm.equals("")){
            return;
        }

        if(searchSource == null){
            return;
        }

        if(!searchResults.isEmpty()){
            searchResults.clear();
        }

        if (regexCheckButton.isSelected()){
            System.out.println("regex search");
            matcher = regexPattern.matcher(searchSource);
            while (matcher.find()) {
                searchResults.add(matcher.start()+"|"+matcher.group());
            }
            entry = 0;
            String[] tempArray = splitSearchResults(searchResults.get(entry));
            int start = Integer.parseInt(tempArray[0]);
            String group = tempArray[1];
            highlightText(start, group);
        }
        else{
            literalSearch(searchSource, searchTerm);
            entry = 0;
            String[] tempArray = splitSearchResults(searchResults.get(entry));
            int start = Integer.parseInt(tempArray[0]);
            String group = tempArray[1];
            highlightText(start, group);
        }

    }

    public void nextField() {
        entry++;
        if(entry == searchResults.size()){
            entry = 0;
        }
        String[] tempArray = splitSearchResults(searchResults.get(entry));
        int start = Integer.parseInt(tempArray[0]);
        String group = tempArray[1];
        highlightText(start, group);
    }

    public void previousField() {
        entry--;
        if(entry == -1){
            entry = searchResults.size()-1;
        }
        String[] tempArray = splitSearchResults(searchResults.get(entry));
        int start = Integer.parseInt(tempArray[0]);
        String group = tempArray[1];
        highlightText(start, group);

    }

    public void highlightText(int startPosition, String matchingWord){
        System.out.println(startPosition);
        if (regexCheckButton.isSelected()) {
            fileContentArea.setCaretPosition(startPosition + matchingWord.length());
            fileContentArea.select(startPosition, startPosition + matchingWord.length());

        } else {
            fileContentArea.setCaretPosition(startPosition);
            fileContentArea.select(startPosition - matchingWord.length(), startPosition);
        }
        fileContentArea.grabFocus();
    }

    public String[] splitSearchResults(String result){
        return result.split("\\|",2);
    }

    public void literalSearch(String sourceString, String searchTerm) {
        int index = 0;
        while (true) {
            index = sourceString.indexOf(searchTerm, index);
            if (index != -1) {
                index += searchTerm.length();
                searchResults.add(index+"|"+searchTerm);
            } else {
                break;
            }
        }
    }

    public void toggleCheckbox(){
        regexCheckButton.setSelected(!regexCheckButton.isSelected());
    }


}
