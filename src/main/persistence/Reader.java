package persistence;

import model.Event;
import model.EventLog;
import model.Work;
import model.WorkToDo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class Reader {
    private String origin;

    // EFFECTS: constructs reader to read from source file
    public Reader(String source) {
        this.origin = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WorkToDo read() throws IOException {
        String jsonData = readFile(origin);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("File Loaded!!"));
        return parseWorkToDo(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private WorkToDo parseWorkToDo(JSONObject jsonObject) {
        WorkToDo wtd = new WorkToDo();
        addWorks(wtd, jsonObject);
        return wtd;
    }

    // MODIFIES: wtd
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addWorks(WorkToDo wtd, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Work To Do");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addWork(wtd, nextThingy);
        }
    }

    // MODIFIES: wtd
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addWork(WorkToDo wtd, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int time = jsonObject.getInt("time");
        boolean completed = jsonObject.getBoolean("completed");
        Work w = new Work(name, time);
        if (completed) {
            w.completeWork();
        }
        wtd.addWork(w);
    }
}
