package persistence;

import model.Clinic;
import model.ClinicalNote;
import model.Date;
import model.Patient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads clinic from JSON data stored in file
// JsonReader code modeled from the JsonSerializationDemo
public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads clinic from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Clinic read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseClinic(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses clinic from JSON object and returns it
    private Clinic parseClinic(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Clinic clinic = new Clinic(name);
        addPatients(clinic, jsonObject);
        return clinic;
    }

    // MODIFIES: clinic
    // EFFECTS: parses patients from JSON object and adds them to clinic
    private void addPatients(Clinic clinic, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("patients");
        for (Object json : jsonArray) {
            JSONObject nextPatient = (JSONObject) json;
            addPatient(clinic, nextPatient);
        }
    }

    // MODIFIES: clinic
    // EFFECTS: parses patient from JSON object and adds it to clinic
    private void addPatient(Clinic clinic, JSONObject jsonObject) {
        String firstName = jsonObject.getString("firstname");
        String lastName = jsonObject.getString("lastname");
        Date dateOfBirth = getDateOfBirth(jsonObject);
        int age = jsonObject.getInt("age");
        long personalHealthNumber = jsonObject.getLong("personalhealthnumber");

        Patient patient = new Patient(firstName, lastName, dateOfBirth, age, personalHealthNumber);
        clinic.addPatient(patient);

        addAllergies(patient, jsonObject);
        addMedications(patient, jsonObject);
        addMedicalConditions(patient, jsonObject);
        addClinicalNotes(patient, jsonObject);
    }

    // EFFECTS: parses date of birth (DOB) from JSON object and returns the date object
    private Date getDateOfBirth(JSONObject jsonObject) {
        JSONObject dateOfBirthObject = jsonObject.getJSONObject("dateofbirth");
        int month = dateOfBirthObject.getInt("month");
        int day = dateOfBirthObject.getInt("day");
        int year = dateOfBirthObject.getInt("year");
        Date dateOfBirth = new Date(month, day, year);
        return dateOfBirth;
    }

    // MODIFIES: patient
    // EFFECTS: parses allergies from JSON object and adds each allergy to patient
    private void addAllergies(Patient patient, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("allergies");
        for (Object json : jsonArray) {
            String allergy = (String) json;
            patient.addAllergy(allergy);
        }
    }

    // MODIFIES: patient
    // EFFECTS: parses medications from JSON object and add each medication to patient
    private void addMedications(Patient patient, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("medications");
        for (Object json : jsonArray) {
            String medication = (String) json;
            patient.addMedication(medication);
        }
    }

    // MODIFIES: patient
    // EFFECTS: parses medical conditions from JSON object and adds each medical condition to patient
    private void addMedicalConditions(Patient patient, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("medicalconditions");
        for (Object json : jsonArray) {
            String medicalCondition = (String) json;
            patient.addMedicalCondition(medicalCondition);
        }
    }

    // MODIFIES: patient
    // EFFECTS: parses clinical notes from JSON object and adds them to patient
    private void addClinicalNotes(Patient patient, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("clinicalnotes");
        for (Object json : jsonArray) {
            JSONObject clinicalNote = (JSONObject) json;
            addClinicalNote(patient, clinicalNote);
        }
    }

    // MODIFIES: patient
    // EFFECTS: parses clinical note from JSON object and adds it to patient
    private void addClinicalNote(Patient patient, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String body = jsonObject.getString("body");
        String provider = jsonObject.getString("provider");
        Date visitDate = getVisitDate(jsonObject);
        ClinicalNote note = new ClinicalNote(title, body, provider, visitDate);
        patient.addClinicalNote(note);
    }

    // EFFECTS: parses visit date from JSON object and returns the date object
    private Date getVisitDate(JSONObject jsonObject) {
        JSONObject visitDateObject = jsonObject.getJSONObject("visitdate");
        int month = visitDateObject.getInt("month");
        int day = visitDateObject.getInt("day");
        int year = visitDateObject.getInt("year");
        Date visitDate = new Date(month, day, year);
        return visitDate;
    }
}
