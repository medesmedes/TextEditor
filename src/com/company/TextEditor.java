package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextEditor extends JFrame {
    private JCheckBox regexCheckButton;
    private JTextArea fileContentArea;
    public JFileChooser fileChooser;
    private JTextField searchField;
    TextEditorLogic textEditorLogic;


    public TextEditor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Text Editor");
        setVisible(true);
        makeUI();
        this.textEditorLogic = new TextEditorLogic(this);
    }

    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public void makeUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setVisible(true);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));

        JPanel toolbarPanel = new JPanel();
        toolbarPanel.setVisible(true);
        toolbarPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));

        JPanel textAreaPanel = new JPanel(new BorderLayout(2, 2));
        textAreaPanel.setVisible(true);
        textAreaPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));

        ImageIcon openIcon = new ImageIcon(new ImageIcon(getClass().getResource("./resources/open.png").getFile()).getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
        ImageIcon saveIcon = new ImageIcon(new ImageIcon(getClass().getResource("./resources/save.png").getFile()).getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
        ImageIcon searchIcon = new ImageIcon(new ImageIcon(getClass().getResource("./resources/search.png").getFile()).getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
        ImageIcon nextIcon = new ImageIcon(new ImageIcon(getClass().getResource("./resources/right.png").getFile()).getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
        ImageIcon previousIcon = new ImageIcon(new ImageIcon(getClass().getResource("./resources/left.png").getFile()).getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));


        JButton openButton = new JButton(openIcon);
        openButton.setName("OpenButton");
        openButton.setVisible(true);
        openButton.setPreferredSize(new Dimension(30, 30));
        toolbarPanel.add(openButton);

        JButton saveButton = new JButton(saveIcon);
        saveButton.setName("SaveButton");
        saveButton.setVisible(true);
        saveButton.setPreferredSize(new Dimension(30, 30));
        toolbarPanel.add(saveButton);

        searchField = new JTextField();
        searchField.setName("SearchField");
        searchField.setPreferredSize(new Dimension(150, 30));
        toolbarPanel.add(searchField);

        JButton searchButton = new JButton(searchIcon);
        searchButton.setName("StartSearchButton");
        searchButton.setVisible(true);
        searchButton.setPreferredSize(new Dimension(30, 30));
        toolbarPanel.add(searchButton);

        JButton previousButton = new JButton(previousIcon);
        previousButton.setName("PreviousMatchButton");
        previousButton.setVisible(true);
        previousButton.setPreferredSize(new Dimension(30, 30));
        toolbarPanel.add(previousButton);

        JButton nextButton = new JButton(nextIcon);
        nextButton.setName("NextMatchButton");
        nextButton.setVisible(true);
        nextButton.setPreferredSize(new Dimension(30, 30));
        toolbarPanel.add(nextButton);

        regexCheckButton = new JCheckBox("Use Regex");
        regexCheckButton.setName("UseRegExCheckbox");
        regexCheckButton.setVisible(true);
        toolbarPanel.add(regexCheckButton);

        fileContentArea = new JTextArea();
        fileContentArea.setName("TextArea");
        fileContentArea.setVisible(true);

        JScrollPane scrollPane = new JScrollPane(fileContentArea);
        scrollPane.setName("ScrollPane");
        scrollPane.setVisible(true);
        scrollPane.setPreferredSize(new Dimension(200, 300));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textAreaPanel.add(scrollPane);

        fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt", "text");
        fileChooser.setFileFilter(filter);
        fileChooser.setName("FileChooser");

        openButton.addActionListener(actionEvent -> textEditorLogic.openFile());

        saveButton.addActionListener(actionEvent -> textEditorLogic.saveFile());

        searchButton.addActionListener(actionEvent -> textEditorLogic.searchFile());

        nextButton.addActionListener(actionEvent -> textEditorLogic.nextField());

        previousButton.addActionListener(actionEvent -> textEditorLogic.previousField());


        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenu searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");
        searchMenu.setMnemonic(KeyEvent.VK_S);

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setName("MenuOpen");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");

        JMenuItem searchMenuItem = new JMenuItem("Start Search");
        searchMenuItem.setName("MenuStartSearch");
        JMenuItem nextMatchMenuItem = new JMenuItem("Next Match");
        nextMatchMenuItem.setName("MenuNextMatch");
        JMenuItem previousMatchMenuItem = new JMenuItem("Previous Match");
        previousMatchMenuItem.setName("MenuPreviousMatch");
        JMenuItem regExpMenuItem = new JMenuItem("Use Regular Expressions");
        regExpMenuItem.setName("MenuUseRegExp");

        openMenuItem.addActionListener(actionEvent -> textEditorLogic.openFile());

        saveMenuItem.addActionListener(actionEvent -> textEditorLogic.saveFile());

        exitMenuItem.addActionListener(actionEvent -> dispose());

        searchMenuItem.addActionListener(actionEvent -> textEditorLogic.searchFile());

        nextMatchMenuItem.addActionListener(actionEvent -> textEditorLogic.nextField());

        previousMatchMenuItem.addActionListener(actionEvent -> textEditorLogic.previousField());

        regExpMenuItem.addActionListener(actionEvent -> textEditorLogic.toggleCheckbox());


        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        searchMenu.add(searchMenuItem);
        searchMenu.add(nextMatchMenuItem);
        searchMenu.add(previousMatchMenuItem);
        searchMenu.add(regExpMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(searchMenu);
        setJMenuBar(menuBar);


        mainPanel.add(toolbarPanel);
        mainPanel.add(textAreaPanel);
        add(mainPanel);
        pack();
    }

    public JFileChooser getFileChooser(){
        return fileChooser;
    }

    public JTextArea getFileContentArea(){
        return fileContentArea;
    }

    public JCheckBox getRegexCheckButton(){
        return  regexCheckButton;
    }

    public JTextField getSearchField(){
        return searchField;
    }

}