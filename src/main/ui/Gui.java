package ui;


import model.WorkToDo;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import persistence.Reader;
import persistence.Writer;


public class Gui extends JPanel
        implements ListSelectionListener {
    private static final String JSON_STORE = "./data/worktodo.json";
    private JList list;
    private DefaultListModel listModel;
    private static final String addString = "Add";
    private static final String deleteString = "Delete";
    private JButton deleteButton;
    private JTextField workName;
    private JButton addButton;
    private WorkToDo workList;
    private JButton saveButton;
    private JButton clearButton;
    private HireListener hireListener;
    private FireListener fireListener;
    private JButton loadButton;


    public Gui() {

        super(new BorderLayout());
        startIn();

    }

    private void startIn() {
        workList = new WorkToDo();
        listModel = new DefaultListModel();
        //loadWork();


        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        startButton();
        saveButton = new JButton("Save");
        saveButton.setActionCommand("Save");
        saveButton.addActionListener(new SaveListener());

        loadButton = new JButton("Load");
        loadButton.setActionCommand("Load");
        loadButton.addActionListener(new LoadListener());


        workName = new JTextField(10);
        workName.addActionListener(hireListener);
        workName.getDocument().addDocumentListener(hireListener);

        startPane();
        add(listScrollPane, BorderLayout.CENTER);

    }

    private void startButton() {
        addButton = new JButton(addString);
        hireListener = new HireListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(hireListener);
        addButton.setEnabled(false);

        fireListener = new FireListener();
        deleteButton = new JButton(deleteString);
        deleteButton.setActionCommand(deleteString);
        deleteButton.addActionListener(new FireListener());


        clearButton = new JButton("Clear");
        clearButton.setActionCommand("Clear");
        clearButton.addActionListener(new ClearListener());
    }

    private void startPane() {
        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(deleteButton);
        buttonPane.add(clearButton);
        buttonPane.add(saveButton);
        buttonPane.add(addButton);
        buttonPane.add(loadButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(workName);

        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(buttonPane, BorderLayout.PAGE_END);
    }

    class ClearListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();


            for (int i = 0; i < listModel.size(); i++) {
                listModel.remove(i);
                for (int j = 1; j < listModel.size(); j++) {
                    listModel.remove(j);
                    for (int k = 1; k < listModel.size(); k++) {
                        listModel.remove(k);
                    }
                }
            }

            if (listModel.getSize() > 0) {
                listModel.remove(0);
            }
        }
    }


    class FireListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();
            listModel.remove(index);

            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                deleteButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            {
                Writer jsonWriter = new Writer(JSON_STORE);
                try {
                    jsonWriter.open();
                    jsonWriter.write(workList);
                    jsonWriter.close();
                    System.out.printf("Saved " + workList + " to " + JSON_STORE);
                } catch (FileNotFoundException f) {
                    System.out.printf("Unable to write to file: " + JSON_STORE);
                }

            }

        }
    }

    private class LoadListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            Reader jsonReader = new Reader(JSON_STORE);

            try {
                workList = jsonReader.read();
                System.out.println("Loaded " + workList + " from " + JSON_STORE);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }


    }


    //This listener is shared by the text field and the hire button.

    class HireListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public HireListener(JButton button) {
            this.button = button;
        }

        public void actionPerformed(ActionEvent e) {
            String name = workName.getText();

            //User didn't type in a unique name...
            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                workName.requestFocusInWindow();
                workName.selectAll();
                return;
            }

            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            listModel.insertElementAt(workName.getText(), index);
            workList.addWork(workName.getText());
            //If we just wanted to add to the end, we'd do this:
            //listModel.addElement(employeeName.getText());


            //Reset the text field.
            workName.requestFocusInWindow();
            workName.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }


        //This method tests for string equality. You could certainly
        //get more sophisticated about the algorithm.  For example,
        //you might want to ignore white space and capitalization.
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                deleteButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                deleteButton.setEnabled(true);
            }
        }
    }


    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TO-DO Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new Gui();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
