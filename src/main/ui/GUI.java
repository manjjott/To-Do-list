package ui;

import model.Work;
import model.WorkToDo;
import persistence.Reader;
import persistence.Writer;

import java.io.FileNotFoundException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//GUI of To-Do Application
public class GUI implements ActionListener {

    private JFrame frame;
    private JPanel panel;
    private WorkToDo wl;
    private JTextField name;
    private JTextField duration;
    private ImageIcon newIcon;
    private Writer writer;
    private Reader reader;
    private static final String JSON_STORE = "./data/worktodo.json";
    private JButton back;

    //EFFECTS: creates the main frame and panel
    public GUI() throws FileNotFoundException {
        wl = new WorkToDo();
        writer = new Writer(JSON_STORE);
        reader = new Reader(JSON_STORE);
        frame = new JFrame("To-DO Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 155);
        panel = new JPanel();

        back = new JButton("Back");
        back.setActionCommand("back");
        back.addActionListener(this);


        buttons();

        frame.add(panel);
        frame.setVisible(true);
    }

    //EFFECTS: creates the buttons for the frame
    private void buttons() {
        JButton add = new JButton("Add Work");
        JButton delete = new JButton("Delete Work");
        JButton view = new JButton("View Work");
        JButton save = new JButton("Save Work");
        JButton load = new JButton("Load Work");

        add.setActionCommand("add work");
        add.addActionListener(this);

        view.setActionCommand("view work");
        view.addActionListener(this);

        delete.setActionCommand("delete work");
        delete.addActionListener(this);

        save.setActionCommand("save");
        save.addActionListener(this);

        load.setActionCommand("load");
        load.addActionListener(this);

        panel.add(add);
        panel.add(delete);
        panel.add(view);
        panel.add(save);
        panel.add(load);
    }

    //EFFECTS: creates the add frame and panel
    private void addFrame() {
        frame = new JFrame();
        frame.setSize(400, 400);
        panel = new JPanel();

        addFrameFields();
        addFrameLabels();

        JButton add = new JButton("Add Work");
        add.setActionCommand("add");
        add.addActionListener(this);
        add.setBounds(120, 200, 200, 50);
        back.setBounds(120, 245, 200, 50);

        panel.add(name);
        panel.add(duration);
        panel.add(add);
        panel.add(back);
        panel.setLayout(null);

        frame.add(panel);
        frame.setVisible(true);

    }

    //EFFECTS: creates fields of work
    private void addFrameFields() {

        name = new JTextField();
        duration = new JTextField();

        name.setBounds(120, 20, 100, 40);
        duration.setBounds(120, 60, 100, 40);
    }

    //EFFECTS: creates labels of add work
    private void addFrameLabels() {
        JLabel l1 = new JLabel("Name");
        JLabel l2 = new JLabel("Duration");

        l1.setBounds(50, 10, 100, 35);
        l2.setBounds(50, 50, 100, 35);

        panel.add(l1);
        panel.add(l2);
    }

    //REQUIRES: add different work everytime
    //EFFECTS: adds work with name, duration
    private void add() {
        photo();
        int i = Integer.parseInt(duration.getText());
        Work work = new Work(name.getText(), i);
        wl.addWork(work);
        for (Work w : wl.getWorks()) {
            if (w.getName().equals(name.getText())) {
                w.completeWork();
                JOptionPane.showMessageDialog(null, "Work Added!!", "Message",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    //EFFECTS: creates remove work frame and panel
    public void deleteFrame() {
        frame = new JFrame();
        frame.setSize(325, 175);

        panel = new JPanel();
        name = new JTextField();

        JLabel label = new JLabel("Name");
        label.setBounds(50, 20, 80, 40);
        name.setBounds(120, 30, 103, 33);
        JButton delete = new JButton("Delete Work");
        delete.setBounds(120, 75, 104, 33);
        back.setBounds(120, 100, 105, 35);
        delete.setActionCommand("delete");
        delete.addActionListener(this);

        panel.add(label);
        panel.add(name);
        panel.add(delete);
        panel.add(back);
        panel.setLayout(null);

        frame.add(panel);
        frame.setVisible(true);
    }

    //MODIFIES: wl
    //EFFECTS: delete work with the entered name
    private void delete() {
        photo();
        if (!wl.containWork(name.getText())) {
            JOptionPane.showMessageDialog(null, "Work not in list", "Message",
                    JOptionPane.PLAIN_MESSAGE, newIcon);
        } else {
            wl.deleteWork(name.getText());
            if (!wl.containWork(name.getText())) {
                JOptionPane.showMessageDialog(null, "Work Deleted", "Message",
                        JOptionPane.PLAIN_MESSAGE, newIcon);
            } else {
                JOptionPane.showMessageDialog(null, "Cannot Delete", "Message",
                        JOptionPane.PLAIN_MESSAGE, newIcon);
            }
        }
    }

    //EFFECTS: creates the view frame and panel
    public void viewFrame() {
        frame = new JFrame();
        panel = new JPanel();
        String str;
        List<String> formatted = new ArrayList<>();
        if (wl.getWorks() != null) {
            for (Work o : wl.getWorks()) {
                str = "Name of Work:" + o.getName() + " " + "Duration of Work:" + o.getTime();
                formatted.add(str);
            }
        }

        JList<Object> j = new JList<>(formatted.toArray());
        panel.add(j);
        panel.add(BorderLayout.SOUTH, back);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    //EFFECTS: creates image icon
    private void photo() {
        ImageIcon imageIcon = new ImageIcon("todo.png");
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        newIcon = new ImageIcon(newImage);
    }


    //EFFECTS: save wl to file
    private void save() {
        try {
            writer.open();
            writer.write(wl);
            writer.close();
            System.out.println("Saved " + wl + " to " + JSON_STORE);
        } catch (FileNotFoundException ev) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

    }

    //EFFECTS: load wl from the file
    private void load() {
        try {
            wl = reader.read();

            System.out.println("Loaded " + wl + " from " + JSON_STORE);

        } catch (IOException ev) {

            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add work")) {
            addFrame();
        } else if (e.getActionCommand().equals("add")) {
            add();
        } else if (e.getActionCommand().equals("view work")) {
            viewFrame();
        } else if (e.getActionCommand().equals("save")) {
            save();
        } else if (e.getActionCommand().equals("load")) {
            load();
        } else if (e.getActionCommand().equals("delete work")) {
            deleteFrame();
        } else if (e.getActionCommand().equals("delete")) {
            delete();
        } else if (e.getActionCommand().equals("back")) {
            frame.dispose();
        }
    }

    //EFFECTS: runs the gui
    public static void main(String[] args) {
        try {
            new GUI();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run GUI: file not found");
        }
    }
}
