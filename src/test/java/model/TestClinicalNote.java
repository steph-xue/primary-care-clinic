package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestClinicalNote {

    private Date date1;
    private ClinicalNote clinicalNote1;

    // Tests fixture setup with a sample date and clinical note
    @BeforeEach
    public void runBefore() {
        date1 = new Date(1, 22, 2025);
        clinicalNote1 = new ClinicalNote("Eye infection", "Detailed notes 1.", "Dr. O. Harrison", date1);

    }

    // Tests that the constructor sets title, body, provider, and date correctly
    @Test
    public void constructorTest() {
        assertEquals("Eye infection", clinicalNote1.getClinicalNoteTitle());
        assertEquals("Detailed notes 1.", clinicalNote1.getClinicalNoteBody());
        assertEquals("Dr. O. Harrison", clinicalNote1.getClinicalNoteProvider());
        assertEquals(date1, clinicalNote1.getClinicalNoteDate());
    }

    // Tests that printClinicaNote formats the title, body, provider, and date correctly
    @Test
    public void printClinicalNoteTest() {
        assertEquals(
                "---------------------------------------------------------------------------------" + "\n"
                + "Eye infection" + "\n" 
                + "Detailed notes 1." + "\n"
                + "Dr. O. Harrison 01/22/2025" + "\n",
                clinicalNote1.printClinicaNote());
    }

    // Tests that setClinicalNoteTitle updates the note's title
    @Test
    public void setClinicalNoteTitleTest() {
        assertEquals("Eye infection", clinicalNote1.getClinicalNoteTitle());
        clinicalNote1.setClinicalNoteTitle("Asthma exacerbation");
        assertEquals("Asthma exacerbation", clinicalNote1.getClinicalNoteTitle());
    }

    // Tests that setClinicalNoteBody updates the note's body
    @Test
    public void setClinicalNoteBodyTest() {
        assertEquals("Detailed notes 1.", clinicalNote1.getClinicalNoteBody());
        clinicalNote1.setClinicalNoteBody("Detailed notes 2.");
        assertEquals("Detailed notes 2.", clinicalNote1.getClinicalNoteBody());
    }

    // Tests that setClinicalNoteProvider updates the note's provider
    @Test
    public void setClinicalNoteProviderTest() {
        assertEquals("Dr. O. Harrison", clinicalNote1.getClinicalNoteProvider());
        clinicalNote1.setClinicalNoteProvider("Dr. H. Zhang");
        assertEquals("Dr. H. Zhang", clinicalNote1.getClinicalNoteProvider());
    }

}
