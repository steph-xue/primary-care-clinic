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

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterFileDoesNotExist() {
        try {
            Clinic clinic = new Clinic("My Clinic");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Clinic clinic = new Clinic("My Clinic");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyClinic.json");
            writer.open();
            writer.write(clinic);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyClinic.json");
            clinic = reader.read();
            assertEquals("My Clinic", clinic.getClinicName());
            assertEquals(0, clinic.getPatients().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralClinic() {
        try {
            Clinic clinic = new Clinic("My Clinic");
            Patient patient1 = makePatient1();
            clinic.addPatient(patient1);
            Patient patient2 = makePatient2();
            clinic.addPatient(patient2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralClinic.json");
            writer.open();
            writer.write(clinic);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralClinic.json");
            clinic = reader.read();

            List<Patient> patients = clinic.getPatients();
            checkClinicInitial(clinic, patients);
            checkPatient1(patients);
            checkPatient2(patients);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    public Patient makePatient1() {
        Date dateOfBirth1 = new Date(9, 11, 1968);
        Patient patient1 = new Patient("Ashley", "Davis", dateOfBirth1, 56, 920215344);
        patient1.addAllergy("amoxicillin");
        patient1.addAllergy("ibuprofen");
        patient1.addMedication("ramipril");
        patient1.addMedication("amlodipine");
        patient1.addMedication("escitalopram");
        patient1.addMedicalCondition("hypertension");
        patient1.addMedicalCondition("anxiety");
        String title1 = "Constipation";
        String body1 = "Patient presenting with severe constipation";
        String provider1 = "Dr. Johnson";
        Date visitDate1 = new Date(1, 10, 2025);
        ClinicalNote note1 = new ClinicalNote(title1, body1, provider1, visitDate1);
        String title2 = "Urinary tract infection";
        String body2 = "Patient presenting with bladder infection";
        String provider2 = "Dr. Kaur";
        Date visitDate2 = new Date(2, 15, 2025);
        ClinicalNote note2 = new ClinicalNote(title2, body2, provider2, visitDate2);
        patient1.addClinicalNote(note1);
        patient1.addClinicalNote(note2);
        return patient1;
    }

    public Patient makePatient2() {
        Date dateOfBirth2 = new Date(1, 6, 1971);
        Patient patient2 = new Patient("Thomas", "Smith", dateOfBirth2, 54, 905845652);
        patient2.addAllergy("tetracycline");
        patient2.addAllergy("aspirin");
        patient2.addMedication("metformin");
        patient2.addMedication("valproic acid");
        patient2.addMedicalCondition("diabetes");
        patient2.addMedicalCondition("epilepsy");
        String title3 = "Allergic rhinitis";
        String body3 = "Patient presenting with nasal congestion";
        String provider3 = "Dr. Rodriguez";
        Date visitDate3 = new Date(1, 18, 2025);
        ClinicalNote note3 = new ClinicalNote(title3, body3, provider3, visitDate3);
        String title4 = "Cold sore";
        String body4 = "Patient presenting with new cold sore";
        String provider4 = "Dr. Young";
        Date visitDate4 = new Date(2, 12, 2025);
        ClinicalNote note4 = new ClinicalNote(title4, body4, provider4, visitDate4);
        patient2.addClinicalNote(note3);
        patient2.addClinicalNote(note4);
        return patient2;
    }

    public void checkClinicInitial(Clinic clinic, List<Patient> patients) {
        assertEquals("My Clinic", clinic.getClinicName());
        assertEquals(2, patients.size());
    }

    public void checkPatient1(List<Patient> patients) {
        checkPatientGeneral1(patients);
        checkPatientAllergies1(patients);
        checkPatientMedications1(patients);
        checkPatientMedicalConditions1(patients);
        checkClinicalNotes1(patients);
    }

    public void checkPatientGeneral1(List<Patient> patients) {
        Date dateOfBirth1 = new Date(9, 11, 1968);
        checkPatientGeneral("Ashley", "Davis", dateOfBirth1, 56, 920215344, patients.get(0));
    }
    
    public void checkPatientAllergies1(List<Patient> patients) {
        List<String> allergies1 = new ArrayList<>();
        allergies1.add("amoxicillin");
        allergies1.add("ibuprofen");
        checkPatientAllergies(allergies1, patients.get(0));
    }

    public void checkPatientMedications1(List<Patient> patients) {
        List<String> medications1 = new ArrayList<>();
        medications1.add("ramipril");
        medications1.add("amlodipine");
        medications1.add("escitalopram");
        checkPatientMedications(medications1, patients.get(0));
    }

    public void checkPatientMedicalConditions1(List<Patient> patients) {
        List<String> medicalConditions1 = new ArrayList<>();
        medicalConditions1.add("hypertension");
        medicalConditions1.add("anxiety");
        checkPatientMedicalConditions(medicalConditions1, patients.get(0));
    }

    public void checkClinicalNotes1(List<Patient> patients) {
        Date visitDate1 = new Date(1, 10, 2025);
        ClinicalNote note1 = new ClinicalNote("Constipation", "Patient presenting with severe constipation", 
                "Dr. Johnson", visitDate1);
        Date visitDate2 = new Date(2, 15, 2025);
        ClinicalNote note2 = new ClinicalNote("Urinary tract infection", "Patient presenting with bladder infection",
                "Dr. Kaur", visitDate2);
        List<ClinicalNote> notesList1 = new ArrayList<>();
        notesList1.add(note1);
        notesList1.add(note2);

        checkPatientClinicalNotes(notesList1, patients.get(0));
        checkClinicalNote("Constipation", "Patient presenting with severe constipation",
                "Dr. Johnson", visitDate1, note1);
        checkClinicalNote("Urinary tract infection", "Patient presenting with bladder infection", 
                "Dr. Kaur", visitDate2, note2);
    }

    public void checkPatient2(List<Patient> patients) {
        checkPatientGeneral2(patients);
        checkPatientAllergies2(patients);
        checkPatientMedications2(patients);
        checkPatientMedicalConditions2(patients);
        checkClinicalNotes2(patients);
    }

    public void checkPatientGeneral2(List<Patient> patients) {
        Date dateOfBirth2 = new Date(1, 6, 1971);
        checkPatientGeneral("Thomas", "Smith", dateOfBirth2, 54, 905845652, patients.get(1));
    }

    public void checkPatientAllergies2(List<Patient> patients) {
        List<String> allergies2 = new ArrayList<>();
        allergies2.add("tetracycline");
        allergies2.add("aspirin");
        checkPatientAllergies(allergies2, patients.get(1));
    }

    public void checkPatientMedications2(List<Patient> patients) {
        List<String> medications2 = new ArrayList<>();
        medications2.add("metformin");
        medications2.add("valproic acid");
        checkPatientMedications(medications2, patients.get(1));
    }

    public void checkPatientMedicalConditions2(List<Patient> patients) {
        List<String> medicalConditions2 = new ArrayList<>();
        medicalConditions2.add("diabetes");
        medicalConditions2.add("epilepsy");
        checkPatientMedicalConditions(medicalConditions2, patients.get(1));
    }

    public void checkClinicalNotes2(List<Patient> patients) {
        Date visitDate3 = new Date(1, 18, 2025);
        ClinicalNote note3 = new ClinicalNote("Allergic rhinitis", "Patient presenting with nasal congestion",
                "Dr. Rodriguez", visitDate3);
        Date visitDate4 = new Date(2, 12, 2025);
        ClinicalNote note4 = new ClinicalNote("Cold sore", "Patient presenting with new cold sore",
                "Dr. Young", visitDate4);
        List<ClinicalNote> notesList2 = new ArrayList<>();
        notesList2.add(note3);
        notesList2.add(note4);

        checkPatientClinicalNotes(notesList2, patients.get(1));
        checkClinicalNote("Allergic rhinitis", "Patient presenting with nasal congestion", 
                "Dr. Rodriguez", visitDate3, note3);
        checkClinicalNote("Cold sore", "Patient presenting with new cold sore", 
                "Dr. Young", visitDate4, note4);
    }
}