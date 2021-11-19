package model;

import org.json.JSONObject;
import persistence.Writable;

public class Work implements Writable {

    //This is the single item in the work to do
    private String name;
    private int time;
    private boolean completed;


    public Work(String name, int time) {
        this.name = name;
        this.time = time;
        this.completed = false;
    }

    //EFFECTS: Returns the name of the task
    public String getName() {
        return name;
    }


    //EFFECTS: Returns if the task is completed or not
    public boolean isCompleted() {
        return completed;
    }

    // EFFECTS: Returns time needed for work to complete
    public int getTime() {
        return time;
    }

    //EFFECTS: Mark any work as true
    public void completeWork() {
        this.completed = true;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("time",time);
        json.put("completed", isCompleted());
        return json;
    }
}
