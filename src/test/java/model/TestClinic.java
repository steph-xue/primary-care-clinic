package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestClinic {

    private Date dateOfBirth1;
    private Date dateOfBirth2;
    private Date dateOfBirth3;
    private Patient patient1;
    private Patient patient2;
    private Patient patient3;
    private Clinic clinic;
    
    @BeforeEach
    public void runBefore() {
        dateOfBirth1 = new Date(4, 17, 1976);
        dateOfBirth2 = new Date(12, 5, 1961);
        dateOfBirth3 = new Date(8, 12, 1968);
        patient1 = new Patient("Ethan", "Patel", dateOfBirth1, 48, 9871546730L);
        patient2 = new Patient("James", "Carter", dateOfBirth2, 63, 9870588417L);
        patient3 = new Patient("Mia", "Davis", dateOfBirth3, 56, 9870682118L);
        clinic = new Clinic("Medicare Clinic");
    }

    @Test
    public void constructorTest() {
        assertEquals("Medicare Clinic", clinic.getClinicName());
        assertTrue(clinic.getPatients().isEmpty());
    }

    @Test
    public void addPatientTest() {
        assertEquals(0, clinic.getPatients().size());
        assertTrue(clinic.addPatient(patient1));
        assertEquals(1, clinic.getPatients().size());
        assertEquals(patient1, clinic.getPatients().get(0));
    }

    @Test
    public void addPatientMultipleTest() {
        assertEquals(0, clinic.getPatients().size());
        assertTrue(clinic.addPatient(patient2));
        assertTrue(clinic.addPatient(patient3));
        assertEquals(2, clinic.getPatients().size());
        assertEquals(patient2, clinic.getPatients().get(0));
        assertEquals(patient3, clinic.getPatients().get(1));
    }

    @Test
    public void addPatientMultipleSortingLastNameTest() {
        assertEquals(0, clinic.getPatients().size());
        assertTrue(clinic.addPatient(patient1));
        assertTrue(clinic.addPatient(patient3));
        assertTrue(clinic.addPatient(patient2));
        assertEquals(3, clinic.getPatients().size());
        assertEquals(patient2, clinic.getPatients().get(0));
        assertEquals(patient3, clinic.getPatients().get(1));
        assertEquals(patient1, clinic.getPatients().get(2));
    }

    @Test
    public void addPatientDuplicateTest() {
        assertEquals(0, clinic.getPatients().size());
        assertTrue(clinic.addPatient(patient1));
        assertFalse(clinic.addPatient(patient1));
        assertEquals(1, clinic.getPatients().size());
        assertEquals(patient1, clinic.getPatients().get(0));
    }

    @Test
    public void removePatientTest() {
        assertTrue(clinic.addPatient(patient1));
        assertEquals(1, clinic.getPatients().size());
        assertEquals(patient1, clinic.getPatients().get(0));
        assertTrue(clinic.removePatient(patient1));
        assertEquals(0, clinic.getPatients().size()); 
    }

    @Test
    public void removePatientMultipleTest() {
        assertTrue(clinic.addPatient(patient2));
        assertTrue(clinic.addPatient(patient3));
        assertEquals(2, clinic.getPatients().size());
        assertEquals(patient2, clinic.getPatients().get(0));
        assertEquals(patient3, clinic.getPatients().get(1));
        assertTrue(clinic.removePatient(patient2));
        assertEquals(1, clinic.getPatients().size());
        assertEquals(patient3, clinic.getPatients().get(0));
        assertTrue(clinic.removePatient(patient3));
        assertEquals(0, clinic.getPatients().size());
    }

    @Test
    public void removePatientNotPresentTest() {
        assertTrue(clinic.addPatient(patient2));
        assertTrue(clinic.addPatient(patient3));
        assertFalse(clinic.removePatient(patient1));
        assertEquals(2, clinic.getPatients().size());
        assertEquals(patient2, clinic.getPatients().get(0));
        assertEquals(patient3, clinic.getPatients().get(1));
    }

    @Test
    public void printPatientRecordsNoneTest() {
        assertEquals(
                "---------------------------------------------------------------------------------" 
                + "\n" 
                + "No patient records", 
                clinic.printPatientRecords());
    }

    @Test
    public void printPatientRecordOneTest() {
        assertTrue(clinic.addPatient(patient2));
        assertEquals(
                "---------------------------------------------------------------------------------" 
                + "\n" 
                + "1. James Carter" + "\n" 
                + "Date of Birth: 12/05/1961" + "\n" 
                + "Age: 63" + "\n" 
                + "Personal Health Number: 9870588417" + "\n",
                clinic.printPatientRecords());
    }

    @Test
    public void printPatientRecordMultipleTest() {
        assertTrue(clinic.addPatient(patient2));
        assertTrue(clinic.addPatient(patient3));
        assertEquals(2, clinic.getPatients().size());
        assertEquals(
                "---------------------------------------------------------------------------------" + "\n"
                + "1. James Carter" + "\n" 
                + "Date of Birth: 12/05/1961" + "\n" 
                + "Age: 63" + "\n" 
                + "Personal Health Number: 9870588417" + "\n"
                + "---------------------------------------------------------------------------------" + "\n" 
                + "2. Mia Davis" + "\n" 
                + "Date of Birth: 08/12/1968" + "\n" 
                + "Age: 56" + "\n" 
                + "Personal Health Number: 9870682118" + "\n",
                clinic.printPatientRecords());
    }

    @Test
    public void printPatientRecordMultipleSortedTest() {
        assertTrue(clinic.addPatient(patient1));
        assertTrue(clinic.addPatient(patient2));
        assertEquals(2, clinic.getPatients().size());
        assertEquals(
                "---------------------------------------------------------------------------------" + "\n" 
                + "1. James Carter" + "\n" 
                + "Date of Birth: 12/05/1961" + "\n" 
                + "Age: 63" + "\n"
                + "Personal Health Number: 9870588417" + "\n"
                + "---------------------------------------------------------------------------------" + "\n" 
                + "2. Ethan Patel" + "\n" 
                + "Date of Birth: 04/17/1976" + "\n" 
                + "Age: 48" + "\n" 
                + "Personal Health Number: 9871546730" + "\n",
                clinic.printPatientRecords());
    }

    @Test
    public void setClinicNameTest() {
        assertEquals("Medicare Clinic", clinic.getClinicName());
        clinic.setClinicName("PlusCare Clinic");
        assertEquals("PlusCare Clinic", clinic.getClinicName());
    }

    @Test
    public void setPatientsTest() {
        List<Patient> patients = new ArrayList<>();
        patients.add(patient1);
        patients.add(patient2);
        clinic.setPatients(patients);
        assertEquals(patients, clinic.getPatients());
    }

    
}
