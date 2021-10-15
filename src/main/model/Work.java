package model;

public class Work {

    private String name;
    private int time;
    private boolean completed;


    public Work(String name, int time) {
        this.name = name;
        this.time = time;
        this.completed = false;
    }

    public String getName() {

        return name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public int getTime() {

        return time;
    }

    public void completeWork() {
        this.completed = true;
    }

}
