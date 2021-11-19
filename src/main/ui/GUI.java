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
    private Writer jsonWriter;
    private Reader jsonReader;
    private static final String JSON_STORE = "./data/worktodo.json";
    private JButton back;
    private ImageIcon newIcon;

    //EFFECTS: creates the main frame and panel
    public GUI() {
        wl = new WorkToDo();
        jsonWriter = new Writer(JSON_STORE);
        jsonReader = new Reader(JSON_STORE);
        frame = new JFrame("To-DO Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        panel = new JPanel();

        back = new JButton("Back");
        back.setActionCommand("back");
        back.addActionListener(this);

        titleBar();
        guiButtons();

        frame.add(panel);
        frame.setVisible(true);
    }

    private void titleBar() {
        JMenuBar m = new JMenuBar();
        JMenu mj = new JMenu("Save/Load");
        JMenuItem save = new JMenuItem("Save Work");
        JMenuItem load = new JMenuItem("Load Work");

        save.setActionCommand("save");
        save.addActionListener(this);
        load.setActionCommand("load");
        load.addActionListener(this);

        m.add(mj);
        mj.add(save);
        mj.add(load);
        frame.add(BorderLayout.EAST, m);
    }


    private void guiButtons() {
        JButton add = new JButton("Add Work");
        JButton delete = new JButton("Remove Work");
        JButton view = new JButton("View Work");

        add.setActionCommand("Add Work");
        add.addActionListener(this);

        view.setActionCommand("View Work");
        view.addActionListener(this);

        delete.setActionCommand("Delete Work");
        delete.addActionListener(this);

        panel.add(add);
        panel.add(delete);
        panel.add(delete);
    }

    //EFFECTS: creates the add frame and panel
    private void addWorkFrame() {
        frame = new JFrame();
        frame.setSize(400, 400);
        panel = new JPanel();

        addFrameFields();
        addFrameLabels();

        JButton add = new JButton("Add Work");
        add.setActionCommand("Add Work");
        add.addActionListener(this);
        add.setBounds(150, 210, 80, 40);
        back.setBounds(150, 210, 60, 40);

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
        JLabel l1 = new JLabel("Name of Work");
        JLabel l2 = new JLabel("Duration of Work");

        l1.setBounds(100, 40, 200, 60);
        l2.setBounds(100, 40, 200, 60);

        panel.add(l1);
        panel.add(l2);
    }

    //EFFECTS: creates remove work frame and panel

    public void deleteWorkFrame() {
        frame = new JFrame();
        frame.setSize(325, 175);

        panel = new JPanel();
        name = new JTextField();

        JLabel label = new JLabel("Name of Work");
        label.setBounds(50, 20, 80, 40);
        name.setBounds(120, 30, 103, 33);
        JButton delete = new JButton("Delete");
        delete.setBounds(120, 75, 104, 33);
        back.setBounds(120, 100, 105, 35);
        delete.setActionCommand("Delete Work");
        delete.addActionListener(this);

        panel.add(label);
        panel.add(name);
        panel.add(delete);
        panel.add(back);
        panel.setLayout(null);

        frame.add(panel);
        frame.setVisible(true);
    }

    //EFFECTS: creates the view frame and panel
    public void viewWorkFrame() {
        frame = new JFrame();
        panel = new JPanel();
        String str;
        List<String> formatted = new ArrayList<>();
        if (wl.getWorks() != null) {
            for (Work o : wl.getWorks()) {
                str = "Name of Work:" + o.getName() + " " + "Duration of Work" + o.getTime();
                formatted.add(str);
            }
        }

        JList<Object> j = new JList<>(formatted.toArray());
        panel.add(j);
        frame.add(panel);
        panel.add(BorderLayout.SOUTH, back);
        frame.pack();
        frame.setVisible(true);
    }

    //EFFECTS: creates image icon
    private void icon() {
        ImageIcon imageIcon = new ImageIcon("to-do-list-apps.png");
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        newIcon = new ImageIcon(newImage);
    }

    //EFFECTS: adds work with name, duration
    private void add() {
        int i = Integer.parseInt(duration.getText());

        Work work = new Work(name.getText(), i);
        wl.addWork(work);
        icon();

        //Have to add something here urgently!!!
    }

    //EFFECTS: save wl to file
    private void save() {
        try {
            jsonWriter.open();
            jsonWriter.write(wl);
            jsonWriter.close();
            System.out.println("Saved " + wl + " to " + JSON_STORE);
        } catch (FileNotFoundException ev) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

    }

    //EFFECTS: load wl from the file
    private void load() {
        try {
            wl = jsonReader.read();

            System.out.println("Loaded " + wl + " from " + JSON_STORE);

        } catch (IOException ev) {

            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private void remove(){


    }

    private void toDo(){


    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add order")) {
            addWorkFrame();
        } else if (e.getActionCommand().equals("Add Work")) {
            add();
        } else if (e.getActionCommand().equals("View Work")) {
            viewWorkFrame();
        } else if (e.getActionCommand().equals("Save Work")) {
            save();
        } else if (e.getActionCommand().equals("Load Work")) {
            load();
        } else if (e.getActionCommand().equals("Delete Work")) {
            deleteWorkFrame();
        } else if (e.getActionCommand().equals("remove")) {
            remove();
        } else if (e.getActionCommand().equals("back")) {
            frame.dispose();
        } else if (e.getActionCommand().equals("ats")) {
            toDo();
        }
    }

    public static void main(String[] args) {
        new GUI();
    }
}
