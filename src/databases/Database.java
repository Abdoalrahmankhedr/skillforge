package databases;

import models.Model;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Andrew :)

/*
    Parent abstract class for all databases
    It keeps track of current ID, and increments on it. This means that IDing is automatic

    This class provides base methods for all databases:
        - getRecords()      -> Returns all records within JSON file
        - deleteRecord()    -> Deletes a record based on its ID
        - getRecordById()   -> Get a specific record using an ID

    Most importantly,
        - readFile()    -> This is called whenever you need to refresh the database memory with data stored
                            within the JSON file. This is always called on initialisation.
        - saveToFile()  -> This is called whenever you need to save any changes made using the previously mentioned
                            record methods. If not used on program termination, any changes made on runtime
                            WILL NOT be saved.
*/

public abstract class Database<T extends Model> {
    protected final String logName;
    protected final String filename;
    protected final Class<T> classType;
    protected final ObjectMapper mapper = new ObjectMapper();
    protected final List<T> records = new ArrayList<>();
    protected int index = 1;

    public Database(String filename, Class<T> type) {
        this.filename = filename;
        this.classType = type;
        this.logName = this.getClass().getSimpleName();

        readFile();
        this.index = this.records.size();
    }

    public List<T> getRecords() {
        return this.records;
    }

    protected void insertRecord(T record) {
        if (record != null) {
            if (getRecordById(record.getId()) == null) {
                this.records.add(record);
            }
        }
    }

    public T getRecordById(int id) {
        for (T record : this.records) {
            if (record.getId() == id) {
                return record;
            }
        }

        return null;
    }

    public void deleteRecord(int id) {
        this.records.removeIf((record) -> record.getId() == id);
    }

    public void readFile() {
        /* Read records from JSON file */
        File file = new File(this.filename);

        if (!file.exists() || !file.getName().endsWith(".json")) {
            System.out.printf("[%s]: Unable to read file!\n", logName);
            return;
        }

        if (file.length() == 0) {
            mapper.writeValue(file, Collections.emptyList());
            return;
        }

        try {
            this.records.clear();
            this.records.addAll(this.mapper.readValue(file,
                    this.mapper.getTypeFactory().constructCollectionType(List.class, this.classType)
            ));
            System.out.printf("[%s]: Successfully read all records!\n", logName);
        } catch (Exception e) {
            System.out.printf("[%s]: Error occurred, check trace: %s\n", logName, e.getMessage());
        }
    }

    public void saveToFile() {
        /* Overwrite JSON file to store records */
        File file = new File(this.filename);
        this.mapper.writeValue(file, records);
    }
}
