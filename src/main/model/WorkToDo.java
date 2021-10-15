package model;

import java.util.ArrayList;
import java.util.List;

public class WorkToDo {

    List<Work> workList;
    List<Work> completedWork;
    List<Work> incompleteWork;


    public WorkToDo() {
        workList = new ArrayList<>();
        completedWork = new ArrayList<>();
        incompleteWork = new ArrayList<>();

    }

    public void addWork(Work w) {
        this.workList.add(w);
    }


    public void deleteWork(String w) {
        for (int i = 0; i < workList.size(); i++) {
            if (w.equals(workList.get(i).getName())) {
                workList.remove(i);
            }
        }
    }

    public void markComplete(String w) {
        for (Work work : workList) {
            if (w.equals(work.getName())) {
                work.completeWork();
            }
        }
    }

    public int sizeOfList() {
        int i;
        i = workList.size();
        return i;
    }

    public Work getWork(int i) {

        return workList.get(i);

    }

    public List<Work> getWorks() {
        return workList;
    }


}
