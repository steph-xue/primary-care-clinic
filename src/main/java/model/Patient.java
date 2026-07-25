package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// A class representing a patient with a first name, last name, date of birth (DOB), age, personal health number (PHN),
// list of current allergies, medications, medical conditions, and clinical notes; first and last name are capitalized, 
// list of Strings for allergies, medications, and medical conditions are lowercase
public class Patient implements Writable {

    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private int age;
    private long personalHealthNumber;
    private List<String> allergies;
    private List<String> medications;
    private List<String> medicalConditions;
    private List<ClinicalNote> clinicalNotes;

    // REQUIRES: age > 0, personalHealthNumber > 0
    // EFFECTS: constructs a new record for a patient with their first name, last name, DOB, age, PHN, and an empty
    // list of current allergies, medications, medical conditions, and clinical notes
    public Patient(String firstName, String lastName, Date dateOfBirth, int age, long personalHealthNumber) {
        String firstNameCapitalized = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        this.firstName = firstNameCapitalized;

        String lastNameCapitalized = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
        this.lastName = lastNameCapitalized;

        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.personalHealthNumber = personalHealthNumber;
        allergies = new ArrayList<>();
        medications = new ArrayList<>();
        medicalConditions = new ArrayList<>();
        clinicalNotes = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds allergy to patient's current list of allergies, returns true if successfully added (not 
    // already in current list to prevent duplicates) and false if not successfully added (duplicate entry);
    // add and compare allergy in lowercase
    public boolean addAllergy(String allergy) {
        String allergyLowerCase = allergy.toLowerCase();

        if (!this.allergies.contains(allergyLowerCase)) {
            this.allergies.add(allergyLowerCase);
            EventLog.getInstance().logEvent(new Event("Added new allergy " + allergyLowerCase + " for patient " 
                    + this.firstName + " " + this.lastName));
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: removes allergy from patient's current list of allergies if present, returns true if successfully 
    // removed and false if not succesfully removed (not found in list); compare and remove allergy in lowercase
    public boolean removeAllergy(String allergy) {
        String allergyLowerCase = allergy.toLowerCase();

        if (this.allergies.contains(allergyLowerCase)) {
            this.allergies.remove(allergyLowerCase);
            EventLog.getInstance().logEvent(new Event("Removed allergy " + allergyLowerCase + " for patient " 
                    + this.firstName + " " + this.lastName));
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: if present in current list of allergies, replaces old allergy with new edited allergy, returns true
    // if edit is successful and false if not successful (allergy to edit not found in list); compare and edit allergy
    // in lowercase
    public boolean editAllergy(String oldAllergy, String newAllergy) {
        String oldAllergyLowerCase = oldAllergy.toLowerCase();
        String newAllergyLowerCase = newAllergy.toLowerCase();

        if (this.allergies.contains(oldAllergyLowerCase)) {
            int index = this.allergies.indexOf(oldAllergyLowerCase);
            this.allergies.set(index, newAllergyLowerCase);
            EventLog.getInstance().logEvent(new Event("Allergy " + oldAllergyLowerCase + " set to " 
                    + newAllergyLowerCase + " for patient " + this.firstName + " " + this.lastName));
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds medication to patient's current list of medications, returns true if successfully added 
    // (not already in current list to prevent duplicates) and false if not successfully added (duplicate entry)
    // add and compare medication in lowercase
    public boolean addMedication(String medication) {
        String medicationLowerCase = medication.toLowerCase();

        if (!this.medications.contains(medicationLowerCase)) {
            this.medications.add(medicationLowerCase);
            EventLog.getInstance().logEvent(new Event("Added new medication " + medicationLowerCase + " for patient " 
                    + this.firstName + " " + this.lastName));
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: removes medication from patient's current list of medications if present, returns true
    // if successfully removed and false if not succesfully removed (not found in list); compare and 
    // remove medication in lowercase
    public boolean removeMedication(String medication) {
        String medicationLowerCase = medication.toLowerCase();

        if (this.medications.contains(medicationLowerCase)) {
            this.medications.remove(medicationLowerCase);
            EventLog.getInstance().logEvent(new Event("Removed medication " + medicationLowerCase + " for patient " 
                    + this.firstName + " " + this.lastName));
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: if present in current list of medications, replaces old with new edited medication, 
    // returns true if edit is successful and false if not successful (medication to edit not found in list) 
    // compare and edit medication in lowercase
    public boolean editMedication(String oldMedication, String newMedication) {
        String oldMedicationLowerCase = oldMedication.toLowerCase();
        String newMedicationLowerCase = newMedication.toLowerCase();

        if (this.medications.contains(oldMedicationLowerCase)) {
            int index = this.medications.indexOf(oldMedicationLowerCase);
            this.medications.set(index, newMedicationLowerCase);
            EventLog.getInstance().logEvent(new Event("Medication " + oldMedicationLowerCase + " set to " 
                    + newMedicationLowerCase + " for patient " + this.firstName + " " + this.lastName));
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds medical condition to patient's current list of medical conditions, returns true if successfully
    // added (not already in current list to prevent duplicates) and false if not successfully added (duplicate entry)
    // add and compare medication in lowercase
    public boolean addMedicalCondition(String medicalCondition) {
        String medicalConditionLowerCase = medicalCondition.toLowerCase();

        if (!this.medicalConditions.contains(medicalConditionLowerCase)) {
            this.medicalConditions.add(medicalConditionLowerCase);
            EventLog.getInstance().logEvent(new Event("Added new medical condition " + medicalConditionLowerCase
                    + " for patient " + this.firstName + " " + this.lastName));
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: removes medical condition from patient's current list of medical conditions if present, returns true
    // if successfully removed and false if not succesfully removed (not found in list); compare and remove medication 
    // in lowercase
    public boolean removeMedicalCondition(String medicalCondition) {
        String medicalConditionLowerCase = medicalCondition.toLowerCase();

        if (this.medicalConditions.contains(medicalConditionLowerCase)) {
            this.medicalConditions.remove(medicalConditionLowerCase);
            EventLog.getInstance().logEvent(new Event("Removed medical condition " + medicalConditionLowerCase 
                    + " for patient " + this.firstName + " " + this.lastName));
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: if present in current list of medical conditions, replaces old with new edited medical condition,
    // returns true if edit is successful and false if not successful (medical condition to edit not found in list)
    // compare and edit medication in lowercase
    public boolean editMedicalCondition(String oldMedicalCondition, String newMedicalCondition) {
        String oldMedicalConditionLowerCase = oldMedicalCondition.toLowerCase();
        String newMedicalConditionLowerCase = newMedicalCondition.toLowerCase();

        if (this.medicalConditions.contains(oldMedicalConditionLowerCase)) {
            int index = this.medicalConditions.indexOf(oldMedicalConditionLowerCase);
            this.medicalConditions.set(index, newMedicalConditionLowerCase);
            EventLog.getInstance().logEvent(new Event("Medical condition " + oldMedicalConditionLowerCase + " set to "
                    + newMedicalConditionLowerCase + " for patient " + this.firstName + " " + this.lastName));
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds clinical note to patient's list of clinical notes, returns true if successfully added (not 
    // already in current list to prevent duplicates) and false if not successfully added (duplicate entry)
    public boolean addClinicalNote(ClinicalNote note) {
        if (!this.clinicalNotes.contains(note)) {
            this.clinicalNotes.add(note);
            EventLog.getInstance().logEvent(new Event("Added new clinical note for patient " 
                    + this.firstName + " " + this.lastName));
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: removes clinical note from patient's list of clinical notes if present, returns true
    // if successfully removed and false if not succesfully removed (not found in list)
    public boolean removeClinicalNote(ClinicalNote note) {
        if (this.clinicalNotes.contains(note)) {
            this.clinicalNotes.remove(note);
            EventLog.getInstance().logEvent(new Event("Removed clinical note for patient " 
                    + this.firstName + " " + this.lastName));
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: prints patient's current list of allergies in string format
    // separated by commas, or prints no allergies if list is empty
    public String printAllergies() {
        if (allergies.isEmpty()) {
            return "No allergies";
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < allergies.size(); i++) {
            String allergy = allergies.get(i);

            if (i != 0) {
                result.append(", ");
            }

            if (i == 0) {
                allergy = allergy.substring(0, 1).toUpperCase() + allergy.substring(1);
            }
            result.append(allergy);
        }
        return result.toString();
    }

    // EFFECTS: prints patient's current list of medications in string format
    // separated by commas, or prints no medications if list is empty
    public String printMedications() {
        if (medications.isEmpty()) {
            return "No medications";
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < medications.size(); i++) {
            String medication = medications.get(i);

            if (i != 0) {
                result.append(", ");
            }

            if (i == 0) {
                medication = medication.substring(0, 1).toUpperCase() + medication.substring(1);
            }
            result.append(medication);
        }
        return result.toString();
    }

    // EFFECTS: prints patient's current list of medical conditions in string format
    // separated by commas, or prints no medical conditions if list is empty
    public String printMedicalConditions() {
        if (medicalConditions.isEmpty()) {
            return "No medical conditions";
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < medicalConditions.size(); i++) {
            String medicalCondition = medicalConditions.get(i);

            if (i != 0) {
                result.append(", ");
            }

            if (i == 0) {
                medicalCondition = medicalCondition.substring(0, 1).toUpperCase() + medicalCondition.substring(1);
            }
            result.append(medicalCondition);
        }
        return result.toString();
    }

    // EFFECTS: prints details of patient's list of clinical notes 
    public String printClinicalNotes() {
        if (clinicalNotes.isEmpty()) {
            StringBuilder result = new StringBuilder();
            result.append("---------------------------------------------------------------------------------" + "\n");
            result.append("No clinical notes");
            return result.toString();
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < clinicalNotes.size(); i++) {
            ClinicalNote note = clinicalNotes.get(i);
            result.append("---------------------------------------------------------------------------------" + "\n");
            result.append(String.valueOf(i + 1) + ". ");
            result.append(note.getClinicalNoteTitle() + "\n");
            result.append(note.getClinicalNoteBody() + "\n");
            result.append(note.getClinicalNoteProvider() + " ");
            result.append(note.getClinicalNoteDate().printDate() + "\n");
        }
        return result.toString();
    }

    // Setters
    // MODIFIES: this
    // EFFECTS: sets the patient's first name to a new given first name
    public void setFirstName(String firstName) {
        String oldFirstName = this.firstName;
        String firstNameCapitalized = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        this.firstName = firstNameCapitalized;
        EventLog.getInstance().logEvent(new Event("Set first name of patient " + oldFirstName + " " + lastName
                + " to " + this.firstName));
    }

    // MODIFIES: this
    // EFFECTS: sets the patient's last name to a new given last name
    public void setLastName(String lastName) {
        String oldLastName = this.lastName;
        String lastNameCapitalized = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
        this.lastName = lastNameCapitalized;
        EventLog.getInstance().logEvent(new Event("Set last name of patient " + firstName +  " " + oldLastName 
                + " to " + this.lastName));
    }

    // MODIFIES: this
    // EFFECTS: sets the patient's DOB new given DOB 
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        EventLog.getInstance().logEvent(new Event("Set date of birth of patient " + this.firstName 
                + " " + this.lastName + " to " + this.dateOfBirth.printDate()));
    }

    // REQUIRES: age > 0
    // MODIFIES: this
    // EFFECTS: sets the patient's age to a new given age
    public void setAge(int age) {
        this.age = age;
        EventLog.getInstance().logEvent(new Event("Set age of patient " + this.firstName 
                + " " + this.lastName + " to " + this.age));
    }

    // REQUIRES: personalHealthNumber > 0
    // MODIFIES: this
    // EFFECTS: sets the patient's PHN to a new given PHN
    public void setPersonalHealthNumber(long personalHealthNumber) {
        this.personalHealthNumber = personalHealthNumber;
        EventLog.getInstance().logEvent(new Event("Set personal health number of patient " + this.firstName
                + " " + this.lastName + " to " + this.personalHealthNumber));
    }

    // Getters
    // EFFECTS: gets patient's first name
    public String getFirstName() {
        return firstName;
    }

    // EFFECTS: gets patient's last name
    public String getLastName() {
        return lastName;
    }

    // EFFECTS: gets patient's full name
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // EFFECTS: gets patient's DOB
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    // EFFECTS: gets patient's age
    public int getAge() {
        return age;
    }

    // EFFECTS: gets patient's PHN
    public long getPersonalHealthNumber() {
        return personalHealthNumber;
    }

    // EFFECTS: gets patient's current list of allergies
    public List<String> getAllergies() {
        return allergies;
    }

    // EFFECTS: gets patient's current list of medications
    public List<String> getMedications() {
        return medications;
    }

    // EFFECTS: gets patient's current list of medical conditions
    public List<String> getMedicalConditions() {
        return medicalConditions;
    }

    // EFFECTS: gets patient's list of clinical notes
    public List<ClinicalNote> getClinicalNotes() {
        return clinicalNotes;
    }

    // EFFECTS: returns patient as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("firstname", firstName);
        json.put("lastname", lastName);
        json.put("dateofbirth", dateOfBirthToJson());
        json.put("age", age);
        json.put("personalhealthnumber", personalHealthNumber);
        json.put("allergies", allergiesToJson());
        json.put("medications", medicationsToJson());
        json.put("medicalconditions", medicalConditionsToJson());
        json.put("clinicalnotes", clinicalNotesToJson());
        return json;
    }

    // EFFECTS: returns date of birth (DOB) for this patient as a JSON object
    private JSONObject dateOfBirthToJson() {
        JSONObject json = new JSONObject();
        json.put("month", dateOfBirth.getMonth());
        json.put("day", dateOfBirth.getDay());
        json.put("year", dateOfBirth.getYear());
        return json;
    }

    // EFFECTS: returns allergies for this patient as a JSON array
    private JSONArray allergiesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String allergy: allergies) {
            jsonArray.put(allergy);
        }

        return jsonArray;
    }

    // EFFECTS: returns medications for this patient as a JSON array
    private JSONArray medicationsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String medication: medications) {
            jsonArray.put(medication);
        }

        return jsonArray;
    }

    // EFFECTS: returns medical conditions for this patient as a JSON array
    private JSONArray medicalConditionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String medicalCondition: medicalConditions) {
            jsonArray.put(medicalCondition);
        }

        return jsonArray;
    }

    // EFFECTS: returns clinical notes for this patient as a JSON array
    private JSONArray clinicalNotesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (ClinicalNote note : clinicalNotes) {
            jsonArray.put(note.toJson());
        }

        return jsonArray;
    }
}
