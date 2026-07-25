package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// A class representing a clinic with a name and list of patient records (sorted alphabetically by 
// last name then first name)
public class Clinic implements Writable {

    private String name;
    private List<Patient> patients;

    // EFFECTS: constructs a clinic with a given name and an empty list of patients;
    // clinic name is capitalized
    public Clinic(String name) {
        String nameCapitalized = name.substring(0, 1).toUpperCase() + name.substring(1);
        this.name = nameCapitalized;
        patients = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds new patient to the clinic's patient records list and sorts patients list 
    // alphabetically by last name then first name, returns true if succesfully added 
    // (patient not already in list) or false if not successful (duplicate record found)
    public boolean addPatient(Patient patient) {
        if (!this.patients.contains(patient)) {
            this.patients.add(patient);
            EventLog.getInstance().logEvent(new Event("Patient " + patient.getFullName() 
                    + " added to the clinic"));
            patients.sort(Comparator.comparing((Patient p) -> p.getLastName().toLowerCase())
                    .thenComparing(p -> p.getFirstName().toLowerCase()));
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: removes patient from the clinic's patient records list; returns true if successfully
    // removed (patient is found in list) and false if not successful (patient not in list)
    public boolean removePatient(Patient patient) {
        if (this.patients.contains(patient)) {
            this.patients.remove(patient);
            EventLog.getInstance().logEvent(new Event("Patient " + patient.getFullName()
                    + " removed from the clinic"));
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: prints summary of patient records in the clinic in String format with name, DOB,
    // age, and PHN (sorted alphabetically by last name then first name)
    public String printPatientRecords() {
        if (patients.isEmpty()) {
            StringBuilder result = new StringBuilder();
            result.append("---------------------------------------------------------------------------------" + "\n");
            result.append("No patient records");
            return result.toString();
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < patients.size(); i++) {
            Patient patient = patients.get(i);
            result.append("---------------------------------------------------------------------------------" + "\n");
            result.append(String.valueOf(i + 1) + ". ");
            result.append(patient.getFullName() + "\n");
            result.append("Date of Birth: " + patient.getDateOfBirth().printDate() + "\n");
            result.append("Age: " + patient.getAge() + "\n");
            result.append("Personal Health Number: " + patient.getPersonalHealthNumber() + "\n");
        }
        return result.toString();
    }

    // Setters
    // MODIFIES: this
    // EFFECTS: sets clinic's name
    public void setClinicName(String name) {
        String nameCapitalized = name.substring(0, 1).toUpperCase() + name.substring(1);
        this.name = nameCapitalized;
        EventLog.getInstance().logEvent(new Event("Clinic name set to " + this.name));
    }

    // MODIFIES: this
    // EFFECTS: sets list of patients
    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    // Getters
    // EFFECTS: gets clinic's name
    public String getClinicName() {
        return name;
    }

    // EFFECTS: gets clinic's list of patient records (sorted alphabetically by last name then first name)
    public List<Patient> getPatients() {
        return patients;
    }

    // EFFECTS: returns clinic as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("patients", patientsToJson());
        return json;
    }

    // EFFECTS: returns patients in this clinic as a JSON array
    private JSONArray patientsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Patient patient : patients) {
            jsonArray.put(patient.toJson());
        }

        return jsonArray;
    }

}