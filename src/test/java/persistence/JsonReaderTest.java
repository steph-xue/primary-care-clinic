package persistence;

import model.Clinic;
import model.ClinicalNote;
import model.Date;
import model.Patient;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderFileDoesNotExist() {
        JsonReader reader = new JsonReader("./src/test/resources/noExistingFile.json");
        try {
            Clinic clinic = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyClinic() {
        JsonReader reader = new JsonReader("./src/test/resources/testReaderEmptyClinic.json");
        try {
            Clinic clinic = reader.read();
            assertEquals("My Clinic", clinic.getClinicName());
            assertEquals(0, clinic.getPatients().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralClinic() {
        JsonReader reader = new JsonReader("./src/test/resources/testReaderGeneralClinic.json");
        try {
            Clinic clinic = reader.read();
            List<Patient> patients = clinic.getPatients();
            checkClinicInitial(clinic, patients);
            checkPatientGeneral1(patients);
            checkPatientAllergies1(patients);
            checkPatientMedications1(patients);
            checkPatientMedicalConditions1(patients);
            checkClinicalNotes1(patients);
            checkPatientGeneral2(patients);
            checkPatientAllergies2(patients);
            checkPatientMedications2(patients);
            checkPatientMedicalConditions2(patients);
            checkClinicalNotes2(patients);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    public void checkClinicInitial(Clinic clinic, List<Patient> patients) {
        assertEquals("My Clinic", clinic.getClinicName());
        assertEquals(2, patients.size());
    }

    public void checkPatientGeneral1(List<Patient> patients) {
        Date dateOfBirth1 = new Date(8, 16, 1965);
        checkPatientGeneral("Mya", "Cornell", dateOfBirth1, 59, 925245285, patients.get(0));
    }
    
    public void checkPatientAllergies1(List<Patient> patients) {
        List<String> allergies1 = new ArrayList<>();
        allergies1.add("penicillin");
        allergies1.add("codeine");
        checkPatientAllergies(allergies1, patients.get(0));
    }

    public void checkPatientMedications1(List<Patient> patients) {
        List<String> medications1 = new ArrayList<>();
        medications1.add("metformin");
        medications1.add("ramipril");
        medications1.add("amlodipine");
        checkPatientMedications(medications1, patients.get(0));
    }

    public void checkPatientMedicalConditions1(List<Patient> patients) {
        List<String> medicalConditions1 = new ArrayList<>();
        medicalConditions1.add("diabetes");
        medicalConditions1.add("hypertension");
        checkPatientMedicalConditions(medicalConditions1, patients.get(0));
    }

    public void checkClinicalNotes1(List<Patient> patients) {
        Date visitDate1 = new Date(1, 16, 2025);
        ClinicalNote note1 = new ClinicalNote("Coughing", "Patient presenting with severe cough", 
                "Dr. Campbell", visitDate1);
        Date visitDate2 = new Date(2, 20, 2025);
        ClinicalNote note2 = new ClinicalNote("Ear infection", "Patient presenting with infected ear",
                "Dr. Singh", visitDate2);
        List<ClinicalNote> notesList1 = new ArrayList<>();
        notesList1.add(note1);
        notesList1.add(note2);

        checkPatientClinicalNotes(notesList1, patients.get(0));
        checkClinicalNote("Coughing", "Patient presenting with severe cough", "Dr. Campbell", visitDate1, note1);
        checkClinicalNote("Ear infection", "Patient presenting with infected ear", "Dr. Singh", visitDate2, note2);
    }

    public void checkPatientGeneral2(List<Patient> patients) {
        Date dateOfBirth2 = new Date(11, 8, 1971);
        checkPatientGeneral("Amira", "Garcia", dateOfBirth2, 53, 925645120, patients.get(1));
    }

    public void checkPatientAllergies2(List<Patient> patients) {
        List<String> allergies2 = new ArrayList<>();
        allergies2.add("ciprofloxacin");
        allergies2.add("azithromycin");
        checkPatientAllergies(allergies2, patients.get(1));
    }

    public void checkPatientMedications2(List<Patient> patients) {
        List<String> medications2 = new ArrayList<>();
        medications2.add("rosuvastatin");
        medications2.add("bisoprolol");
        checkPatientMedications(medications2, patients.get(1));
    }

    public void checkPatientMedicalConditions2(List<Patient> patients) {
        List<String> medicalConditions2 = new ArrayList<>();
        medicalConditions2.add("hyperlipidemia");
        medicalConditions2.add("hypertension");
        checkPatientMedicalConditions(medicalConditions2, patients.get(1));
    }

    public void checkClinicalNotes2(List<Patient> patients) {
        Date visitDate3 = new Date(1, 18, 2025);
        ClinicalNote note3 = new ClinicalNote("Eye infection", "Patient has eye infection", 
                "Dr. Williams", visitDate3);
        Date visitDate4 = new Date(2, 12, 2025);
        ClinicalNote note4 = new ClinicalNote("Skin rash", "Patient presenting with skin rash",
                "Dr. Jones", visitDate4);
        List<ClinicalNote> notesList2 = new ArrayList<>();
        notesList2.add(note3);
        notesList2.add(note4);

        checkPatientClinicalNotes(notesList2, patients.get(1));
        checkClinicalNote("Eye infection", "Patient has eye infection", "Dr. Williams", visitDate3, note3);
        checkClinicalNote("Skin rash", "Patient presenting with skin rash", "Dr. Jones", visitDate4, note4);
    }
    
}
