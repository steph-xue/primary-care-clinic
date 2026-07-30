package persistence;

import model.ClinicalNote;
import model.Date;
import model.Patient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    // Checks that the patient's name, date of birth, age, and health number match the expected values
    protected void checkPatientGeneral(String firstName, String lastName, Date dob, int age, long phn, Patient pt) {
        assertEquals(firstName, pt.getFirstName());
        assertEquals(lastName, pt.getLastName());
        assertEquals(dob.getMonth(), pt.getDateOfBirth().getMonth());
        assertEquals(dob.getDay(), pt.getDateOfBirth().getDay());
        assertEquals(dob.getYear(), pt.getDateOfBirth().getYear());
        assertEquals(age, pt.getAge());
        assertEquals(phn, pt.getPersonalHealthNumber());
    }

    // Checks that the patient's allergies match the expected list in order
    protected void checkPatientAllergies(List<String> allergies, Patient pt) {
        int size = allergies.size();

        for (int i = 0; i < size; i++) {
            String expectedAllergy = allergies.get(i);
            String actualAllergy = pt.getAllergies().get(i);

            assertEquals(expectedAllergy, actualAllergy);
        }
    }

    // Checks that the patient's medications match the expected list in order
    protected void checkPatientMedications(List<String> medications, Patient pt) {
        int size = medications.size();

        for (int i = 0; i < size; i++) {
            String expectedMedication = medications.get(i);
            String actualMedication = pt.getMedications().get(i);

            assertEquals(expectedMedication, actualMedication);
        }
    }

    // Checks that the patient's medical conditions match the expected list in order
    protected void checkPatientMedicalConditions(List<String> medicalConditions, Patient pt) {
        int size = medicalConditions.size();

        for (int i = 0; i < size; i++) {
            String expectedMedicalCondition = medicalConditions.get(i);
            String actualMedicalCondition = pt.getMedicalConditions().get(i);

            assertEquals(expectedMedicalCondition, actualMedicalCondition);
        }
    }

    // Checks that each of the patient's clinical notes matches the expected note's fields
    protected void checkPatientClinicalNotes(List<ClinicalNote> notes, Patient pt) {
        int size = notes.size();

        for (int i = 0; i < size; i++) {
            ClinicalNote expectedNote = notes.get(i);
            ClinicalNote actualNote = pt.getClinicalNotes().get(i);

            assertEquals(expectedNote.getClinicalNoteTitle(), actualNote.getClinicalNoteTitle());
            assertEquals(expectedNote.getClinicalNoteBody(), actualNote.getClinicalNoteBody());
            assertEquals(expectedNote.getClinicalNoteProvider(), actualNote.getClinicalNoteProvider());
            assertEquals(expectedNote.getClinicalNoteDate().getMonth(), actualNote.getClinicalNoteDate().getMonth());
            assertEquals(expectedNote.getClinicalNoteDate().getDay(), actualNote.getClinicalNoteDate().getDay());
            assertEquals(expectedNote.getClinicalNoteDate().getYear(), actualNote.getClinicalNoteDate().getYear());
        }
    }

    // Checks that a single clinical note's title, body, provider, and date match the expected values
    protected void checkClinicalNote(String title, String body, String provider, Date visitDate, ClinicalNote note) {
        assertEquals(title, note.getClinicalNoteTitle());
        assertEquals(body, note.getClinicalNoteBody());
        assertEquals(provider, note.getClinicalNoteProvider());
        assertEquals(visitDate.getMonth(), note.getClinicalNoteDate().getMonth());
        assertEquals(visitDate.getDay(), note.getClinicalNoteDate().getDay());
        assertEquals(visitDate.getYear(), note.getClinicalNoteDate().getYear());
    }
}
