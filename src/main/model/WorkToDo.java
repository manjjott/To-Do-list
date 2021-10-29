package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

public class WorkToDo implements Writable {
    List<Work> workList;

    // This is the list of various works that user need to dealt with
    public WorkToDo() {
        workList = new ArrayList<>();
    }



    //MODIFIES: this
    //EFFECTS: Add the work into the work to do list
    public void addWork(Work w) {
        this.workList.add(w);
    }

    //REQUIRES: Atleast one element in the list
    //MODIFIES: this
    //EFFECTS: removes the work from the list
    public void deleteWork(String w) {
        for (int i = 0; i < workList.size(); i++) {
            if (w.equals(workList.get(i).getName())) {
                workList.remove(i);
            }
        }
    }

    //MODIFIES: this
    // EFFECTS:  marks the teh work as complete
    public void markComplete(String w) {
        for (Work work : workList) {
            if (w.equals(work.getName())) {
                work.completeWork();
            }
        }
    }

    //EFFECTS: Returns the size of the list
    public int sizeOfList() {
        int i;
        i = workList.size();
        return i;
    }

    //EFFECTS: returns the work from the list of work
    public Work getWork(int i) {
        return workList.get(i);

    }

    //EFFECTS: returns the list of work
    public List<Work> getWorks() {
        return workList;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Work To Do",toDoListToJson());
        return json;
    }

    public JSONArray toDoListToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Work w : workList) {
            jsonArray.put(w.toJson());
        }
        return jsonArray;

    }
}
