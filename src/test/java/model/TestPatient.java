package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPatient {

    private Date dateOfBirth1;
    private Date dateOfBirth2;
    private Patient patient1;
    private Date date1;
    private Date date2;
    private Date date3;
    private ClinicalNote clinicalNote1;
    private ClinicalNote clinicalNote2;
    private ClinicalNote clinicalNote3;
    
    @BeforeEach
    public void runBefore() {
        dateOfBirth1 = new Date(7, 27, 1973);
        dateOfBirth2 = new Date(6, 15, 1961);
        patient1 = new Patient("Paul", "Jackson", dateOfBirth1, 52, 9876543210L);
        date1 = new Date(1, 30, 2025);
        date2 = new Date(2, 3, 2025);
        date3 = new Date(2, 8, 2025);
        clinicalNote1 = new ClinicalNote("Fever and cough", "Detailed notes 1.", "Dr. K. Morris", date1);
        clinicalNote2 = new ClinicalNote("Skin infection", "Detailed notes 2.", "Dr. J. Lee", date2);
        clinicalNote3 = new ClinicalNote("Ear infection", "Detailed notes 3.", "Dr. S. Wong", date3);
    }

    @Test
    public void constructorTest() {
        assertEquals("Paul", patient1.getFirstName());
        assertEquals("Jackson", patient1.getLastName());
        assertEquals("Paul Jackson", patient1.getFullName());
        assertEquals(dateOfBirth1, patient1.getDateOfBirth());
        assertEquals(52, patient1.getAge());
        assertEquals(9876543210L, patient1.getPersonalHealthNumber());
        assertTrue(patient1.getAllergies().isEmpty());
        assertTrue(patient1.getMedications().isEmpty());
        assertTrue(patient1.getMedicalConditions().isEmpty());
        assertTrue(patient1.getClinicalNotes().isEmpty());
    }

    @Test
    public void addAllergyTest() {
        assertEquals(0, patient1.getAllergies().size());
        assertTrue(patient1.addAllergy("penicillin"));
        assertEquals(1, patient1.getAllergies().size());
        assertEquals("penicillin", patient1.getAllergies().get(0));
    }

    @Test
    public void addAllergyMultipleTest() {
        assertEquals(0, patient1.getAllergies().size());
        assertTrue(patient1.addAllergy("codeine"));
        assertTrue(patient1.addAllergy("ciprofloxacin"));
        assertEquals(2, patient1.getAllergies().size());
        assertEquals("codeine", patient1.getAllergies().get(0));
        assertEquals("ciprofloxacin", patient1.getAllergies().get(1));
    }

    @Test
    public void addAllergyDuplicateTest() {
        assertEquals(0, patient1.getAllergies().size());
        assertTrue(patient1.addAllergy("amoxicillin"));
        assertFalse(patient1.addAllergy("amoxicillin"));
        assertEquals(1, patient1.getAllergies().size());
        assertEquals("amoxicillin", patient1.getAllergies().get(0));
    }

    @Test
    public void removeAllergyTest() {
        assertTrue(patient1.addAllergy("ibuprofen"));
        assertEquals(1, patient1.getAllergies().size());
        assertEquals("ibuprofen", patient1.getAllergies().get(0));
        assertTrue(patient1.removeAllergy("ibuprofen"));
        assertEquals(0, patient1.getAllergies().size()); 
    }

    @Test
    public void removeAllergyMultipleTest() {
        assertTrue(patient1.addAllergy("naproxen"));
        assertTrue(patient1.addAllergy("nitrofurantoin"));
        assertEquals(2, patient1.getAllergies().size());
        assertEquals("naproxen", patient1.getAllergies().get(0));
        assertEquals("nitrofurantoin", patient1.getAllergies().get(1));
        assertTrue(patient1.removeAllergy("naproxen"));
        assertEquals(1, patient1.getAllergies().size());
        assertEquals("nitrofurantoin", patient1.getAllergies().get(0));
        assertTrue(patient1.removeAllergy("nitrofurantoin"));
        assertEquals(0, patient1.getAllergies().size());
    }

    @Test
    public void removeAllergyNotPresentTest() {
        assertTrue(patient1.addAllergy("codeine"));
        assertTrue(patient1.addAllergy("penicillin"));
        assertFalse(patient1.removeAllergy("naproxen"));
        assertEquals(2, patient1.getAllergies().size());
        assertEquals("codeine", patient1.getAllergies().get(0));
        assertEquals("penicillin", patient1.getAllergies().get(1));
    }

    @Test
    public void editAllergyTest() {
        assertTrue(patient1.addAllergy("penicillin"));
        assertEquals(1, patient1.getAllergies().size());
        assertEquals("penicillin", patient1.getAllergies().get(0));
        assertTrue(patient1.editAllergy("penicillin", "ampicillin"));
        assertEquals(1, patient1.getAllergies().size());
        assertEquals("ampicillin", patient1.getAllergies().get(0));
    }

    @Test
    public void editAllergyMultipleTest() {
        assertTrue(patient1.addAllergy("amoxicillin"));
        assertTrue(patient1.addAllergy("ciprofloxacin"));
        assertEquals(2, patient1.getAllergies().size());
        assertEquals("amoxicillin", patient1.getAllergies().get(0));
        assertEquals("ciprofloxacin", patient1.getAllergies().get(1));
        assertTrue(patient1.editAllergy("amoxicillin", "codeine"));
        assertTrue(patient1.editAllergy("ciprofloxacin", "nitrofurantoin"));
        assertEquals(2, patient1.getAllergies().size());
        assertEquals("codeine", patient1.getAllergies().get(0));
        assertEquals("nitrofurantoin", patient1.getAllergies().get(1));
    }

    @Test
    public void editAllergyNotPresentTest() {
        assertTrue(patient1.addAllergy("azithromycin"));
        assertEquals(1, patient1.getAllergies().size());
        assertEquals("azithromycin", patient1.getAllergies().get(0));
        assertFalse(patient1.editAllergy("penicillin", "amoxicillin"));
        assertEquals(1, patient1.getAllergies().size());
        assertEquals("azithromycin", patient1.getAllergies().get(0));
    }

    @Test
    public void addMedicationTest() {
        assertEquals(0, patient1.getMedications().size());
        assertTrue(patient1.addMedication("metformin"));
        assertEquals(1, patient1.getMedications().size());
        assertEquals("metformin", patient1.getMedications().get(0));
    }

    @Test
    public void addMedicationMultipleTest() {
        assertEquals(0, patient1.getMedications().size());
        assertTrue(patient1.addMedication("amlodipine"));
        assertTrue(patient1.addMedication("metformin"));
        assertEquals(2, patient1.getMedications().size());
        assertEquals("amlodipine", patient1.getMedications().get(0));
        assertEquals("metformin", patient1.getMedications().get(1));
    }

    @Test
    public void addMedicatioDuplicateTest() {
        assertEquals(0, patient1.getMedications().size());
        assertTrue(patient1.addMedication("rosuvastatin"));
        assertFalse(patient1.addMedication("rosuvastatin"));
        assertEquals(1, patient1.getMedications().size());
        assertEquals("rosuvastatin", patient1.getMedications().get(0));
    }

    @Test
    public void removeMedicationTest() {
        assertTrue(patient1.addMedication("bisoprolol"));
        assertEquals(1, patient1.getMedications().size());
        assertEquals("bisoprolol", patient1.getMedications().get(0));
        assertTrue(patient1.removeMedication("bisoprolol"));
        assertEquals(0, patient1.getMedications().size()); 
    }

    @Test
    public void removeMedicationMultipleTest() {
        assertTrue(patient1.addMedication("metformin"));
        assertTrue(patient1.addMedication("simvastatin"));
        assertEquals(2, patient1.getMedications().size());
        assertEquals("metformin", patient1.getMedications().get(0));
        assertEquals("simvastatin", patient1.getMedications().get(1));
        assertTrue(patient1.removeMedication("metformin"));
        assertEquals(1, patient1.getMedications().size());
        assertEquals("simvastatin", patient1.getMedications().get(0));
        assertTrue(patient1.removeMedication("simvastatin"));
        assertEquals(0, patient1.getMedications().size());
    }

    @Test
    public void removeMedicationNotPresentTest() {
        assertTrue(patient1.addMedication("dapagliflozin"));
        assertTrue(patient1.addMedication("metformin"));
        assertFalse(patient1.removeMedication("atorvastatin"));
        assertEquals(2, patient1.getMedications().size());
        assertEquals("dapagliflozin", patient1.getMedications().get(0));
        assertEquals("metformin", patient1.getMedications().get(1));
    }

    @Test
    public void editMedicationTest() {
        assertTrue(patient1.addMedication("atorvastatin"));
        assertEquals(1, patient1.getMedications().size());
        assertEquals("atorvastatin", patient1.getMedications().get(0));
        assertTrue(patient1.editMedication("atorvastatin", "rosuvastatin"));
        assertEquals(1, patient1.getMedications().size());
        assertEquals("rosuvastatin", patient1.getMedications().get(0));
    }

    @Test
    public void editMedicationMultipleTest() {
        assertTrue(patient1.addMedication("metformin"));
        assertTrue(patient1.addMedication("amlodipine"));
        assertEquals(2, patient1.getMedications().size());
        assertEquals("metformin", patient1.getMedications().get(0));
        assertEquals("amlodipine", patient1.getMedications().get(1));
        assertTrue(patient1.editMedication("metformin", "semaglutide"));
        assertTrue(patient1.editMedication("amlodipine", "felodipine"));
        assertEquals(2, patient1.getMedications().size());
        assertEquals("semaglutide", patient1.getMedications().get(0));
        assertEquals("felodipine", patient1.getMedications().get(1));
    }

    @Test
    public void editMedicationNotPresentTest() {
        assertTrue(patient1.addMedication("bisoprolol"));
        assertEquals(1, patient1.getMedications().size());
        assertEquals("bisoprolol", patient1.getMedications().get(0));
        assertFalse(patient1.editMedication("metformin", "atorvastatin"));
        assertEquals(1, patient1.getMedications().size());
        assertEquals("bisoprolol", patient1.getMedications().get(0));
    }

    @Test
    public void addMedicalConditionTest() {
        assertEquals(0, patient1.getMedicalConditions().size());
        assertTrue(patient1.addMedicalCondition("dyslipidemia"));
        assertEquals(1, patient1.getMedicalConditions().size());
        assertEquals("dyslipidemia", patient1.getMedicalConditions().get(0));
    }

    @Test
    public void addMedicalConditionMultipleTest() {
        assertEquals(0, patient1.getMedicalConditions().size());
        assertTrue(patient1.addMedicalCondition("diabetes mellitus"));
        assertTrue(patient1.addMedicalCondition("hypertension"));
        assertEquals(2, patient1.getMedicalConditions().size());
        assertEquals("diabetes mellitus", patient1.getMedicalConditions().get(0));
        assertEquals("hypertension", patient1.getMedicalConditions().get(1));
    }

    @Test
    public void addMedicalConditionDuplicateTest() {
        assertEquals(0, patient1.getMedicalConditions().size());
        assertTrue(patient1.addMedicalCondition("epilepsy"));
        assertFalse(patient1.addMedicalCondition("epilepsy"));
        assertEquals(1, patient1.getMedicalConditions().size());
        assertEquals("epilepsy", patient1.getMedicalConditions().get(0));
    }

    @Test
    public void removeMedicalConditionTest() {
        assertTrue(patient1.addMedicalCondition("impetigo"));
        assertEquals(1, patient1.getMedicalConditions().size());
        assertEquals("impetigo", patient1.getMedicalConditions().get(0));
        assertTrue(patient1.removeMedicalCondition("impetigo"));
        assertEquals(0, patient1.getMedicalConditions().size()); 
    }

    @Test
    public void removeMedicalConditionMultipleTest() {
        assertTrue(patient1.addMedicalCondition("diabetes mellitus"));
        assertTrue(patient1.addMedicalCondition("osteoarthritis"));
        assertEquals(2, patient1.getMedicalConditions().size());
        assertEquals("diabetes mellitus", patient1.getMedicalConditions().get(0));
        assertEquals("osteoarthritis", patient1.getMedicalConditions().get(1));
        assertTrue(patient1.removeMedicalCondition("diabetes mellitus"));
        assertEquals(1, patient1.getMedicalConditions().size());
        assertEquals("osteoarthritis", patient1.getMedicalConditions().get(0));
        assertTrue(patient1.removeMedicalCondition("osteoarthritis"));
        assertEquals(0, patient1.getMedicalConditions().size());
    }

    @Test
    public void removeMedicalConditionNotPresentTest() {
        assertTrue(patient1.addMedicalCondition("hypertension"));
        assertTrue(patient1.addMedicalCondition("rosacea"));
        assertFalse(patient1.removeMedicalCondition("osteoarthritis"));
        assertEquals(2, patient1.getMedicalConditions().size());
        assertEquals("hypertension", patient1.getMedicalConditions().get(0));
        assertEquals("rosacea", patient1.getMedicalConditions().get(1));
    }

    @Test
    public void editMedicalConditionTest() {
        assertTrue(patient1.addMedicalCondition("osteoarthritis"));
        assertEquals(1, patient1.getMedicalConditions().size());
        assertEquals("osteoarthritis", patient1.getMedicalConditions().get(0));
        assertTrue(patient1.editMedicalCondition("osteoarthritis", "rheumatoid arthritis"));
        assertEquals(1, patient1.getMedicalConditions().size());
        assertEquals("rheumatoid arthritis", patient1.getMedicalConditions().get(0));
    }

    @Test
    public void editMedicalConditionMultipleTest() {
        assertTrue(patient1.addMedicalCondition("hypertension"));
        assertTrue(patient1.addMedicalCondition("diabetes mellitus"));
        assertEquals(2, patient1.getMedicalConditions().size());
        assertEquals("hypertension", patient1.getMedicalConditions().get(0));
        assertEquals("diabetes mellitus", patient1.getMedicalConditions().get(1));
        assertTrue(patient1.editMedicalCondition("hypertension", "osteoarthritis"));
        assertTrue(patient1.editMedicalCondition("diabetes mellitus", "dermatitis"));
        assertEquals(2, patient1.getMedicalConditions().size());
        assertEquals("osteoarthritis", patient1.getMedicalConditions().get(0));
        assertEquals("dermatitis", patient1.getMedicalConditions().get(1));
    }

    @Test
    public void editMedicalConditionNotPresentTest() {
        assertTrue(patient1.addMedicalCondition("epilepsy"));
        assertEquals(1, patient1.getMedicalConditions().size());
        assertEquals("epilepsy", patient1.getMedicalConditions().get(0));
        assertFalse(patient1.editMedicalCondition("hypertension", "osteoarthritis"));
        assertEquals(1, patient1.getMedicalConditions().size());
        assertEquals("epilepsy", patient1.getMedicalConditions().get(0));
    }

    @Test
    public void addClinicalNoteTest() {
        assertEquals(0, patient1.getClinicalNotes().size());
        assertTrue(patient1.addClinicalNote(clinicalNote1));
        assertEquals(1, patient1.getClinicalNotes().size());
        assertEquals(clinicalNote1, patient1.getClinicalNotes().get(0));
    }

    @Test
    public void addClinicalNoteMultipleTest() {
        assertEquals(0, patient1.getClinicalNotes().size());
        assertTrue(patient1.addClinicalNote(clinicalNote2));
        assertTrue(patient1.addClinicalNote(clinicalNote3));
        assertEquals(2, patient1.getClinicalNotes().size());
        assertEquals(clinicalNote2, patient1.getClinicalNotes().get(0));
        assertEquals(clinicalNote3, patient1.getClinicalNotes().get(1));
    }

    @Test
    public void addClinicalNoteDuplicateTest() {
        assertEquals(0, patient1.getClinicalNotes().size());
        assertTrue(patient1.addClinicalNote(clinicalNote1));
        assertFalse(patient1.addClinicalNote(clinicalNote1));
        assertEquals(1, patient1.getClinicalNotes().size());
        assertEquals(clinicalNote1, patient1.getClinicalNotes().get(0));
    }

    @Test
    public void removeClinicalNoteTest() {
        assertTrue(patient1.addClinicalNote(clinicalNote2));
        assertEquals(1, patient1.getClinicalNotes().size());
        assertEquals(clinicalNote2, patient1.getClinicalNotes().get(0));
        assertTrue(patient1.removeClinicalNote(clinicalNote2));
        assertEquals(0, patient1.getClinicalNotes().size()); 
    }

    @Test
    public void removeClinicalNoteMultipleTest() {
        assertTrue(patient1.addClinicalNote(clinicalNote1));
        assertTrue(patient1.addClinicalNote(clinicalNote2));
        assertEquals(2, patient1.getClinicalNotes().size());
        assertEquals(clinicalNote1, patient1.getClinicalNotes().get(0));
        assertEquals(clinicalNote2, patient1.getClinicalNotes().get(1));
        assertTrue(patient1.removeClinicalNote(clinicalNote1));
        assertEquals(1, patient1.getClinicalNotes().size());
        assertEquals(clinicalNote2, patient1.getClinicalNotes().get(0));
        assertTrue(patient1.removeClinicalNote(clinicalNote2));
        assertEquals(0, patient1.getClinicalNotes().size());
    }

    @Test
    public void removeClinicalNoteNotPresentTest() {
        assertTrue(patient1.addClinicalNote(clinicalNote1));
        assertTrue(patient1.addClinicalNote(clinicalNote2));
        assertFalse(patient1.removeClinicalNote(clinicalNote3));
        assertEquals(2, patient1.getClinicalNotes().size());
        assertEquals(clinicalNote1, patient1.getClinicalNotes().get(0));
        assertEquals(clinicalNote2, patient1.getClinicalNotes().get(1));
    }

    @Test
    public void printAllergiesNoneTest() {
        assertEquals("No allergies", patient1.printAllergies());
    }

    @Test
    public void printAllergiesTest() {
        assertTrue(patient1.addAllergy("penicillin"));
        assertEquals("Penicillin", patient1.printAllergies());
        assertTrue(patient1.addAllergy("codeine"));
        assertEquals("Penicillin, codeine", patient1.printAllergies());
    }

    @Test
    public void printMedicationsNoneTest() {
        assertEquals("No medications", patient1.printMedications());
    }

    @Test
    public void printMedicationsTest() {
        assertTrue(patient1.addMedication("rosuvastatin"));
        assertEquals("Rosuvastatin", patient1.printMedications());
        assertTrue(patient1.addMedication("metformin"));
        assertEquals("Rosuvastatin, metformin", patient1.printMedications());
    }

    @Test
    public void printMedicalConditionsNoneTest() {
        assertEquals("No medical conditions", patient1.printMedicalConditions());
    }

    @Test
    public void printMedicalConditionsTest() {
        assertTrue(patient1.addMedicalCondition("hypertension"));
        assertEquals("Hypertension", patient1.printMedicalConditions());
        assertTrue(patient1.addMedicalCondition("diabetes mellitus"));
        assertEquals("Hypertension, diabetes mellitus", patient1.printMedicalConditions());
    }

    @Test
    public void printClinicalNotesNoneTest() {
        assertEquals(
                "---------------------------------------------------------------------------------" + "\n" 
                + "No clinical notes", 
                patient1.printClinicalNotes());
    }

    @Test
    public void printClinicalNotesTest() {
        assertTrue(patient1.addClinicalNote(clinicalNote1));
        assertEquals(
                "---------------------------------------------------------------------------------" + "\n" 
                + "1. Fever and cough" + "\n" 
                + "Detailed notes 1." + "\n" 
                + "Dr. K. Morris 01/30/2025" + "\n",
                patient1.printClinicalNotes());
        assertTrue(patient1.addClinicalNote(clinicalNote2));
        assertEquals(
                "---------------------------------------------------------------------------------" + "\n" 
                + "1. Fever and cough" + "\n" 
                + "Detailed notes 1." + "\n" 
                + "Dr. K. Morris 01/30/2025" + "\n" 
                + "---------------------------------------------------------------------------------" + "\n" 
                + "2. Skin infection" + "\n" 
                + "Detailed notes 2." + "\n" 
                + "Dr. J. Lee 02/03/2025" + "\n",
                patient1.printClinicalNotes());
    }

    @Test
    public void setFirstNameTest() {
        assertEquals("Paul", patient1.getFirstName());
        patient1.setFirstName("Steven");
        assertEquals("Steven", patient1.getFirstName());
    }

    @Test
    public void setLastNameTest() {
        assertEquals("Jackson", patient1.getLastName());
        patient1.setLastName("Li");
        assertEquals("Li", patient1.getLastName());
    }

    @Test
    public void setDateOfBirthAndAgeTest() {
        assertEquals(dateOfBirth1, patient1.getDateOfBirth());
        assertEquals(52, patient1.getAge());
        patient1.setDateOfBirth(dateOfBirth2);
        patient1.setAge(63);
        assertEquals(dateOfBirth2, patient1.getDateOfBirth());
        assertEquals(63, patient1.getAge());
    }

    @Test
    public void setPersonalHealthNumberTest() {
        assertEquals(9876543210L, patient1.getPersonalHealthNumber());
        patient1.setPersonalHealthNumber(9875573512L);
        assertEquals(9875573512L, patient1.getPersonalHealthNumber());
    }
}
