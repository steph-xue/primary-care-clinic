package model;

import org.json.JSONObject;
import persistence.Writable;

// A class representing a clinical note for a patient's visit at the clinic with a title, body, name of 
// healthcare provider seen, and date of visit
public class ClinicalNote implements Writable {

    private String title;
    private String body;
    private String healthCareProvider;
    private Date visitDate;

    // EFFECTS: constructs a clinical note with a given title, body, name of healthcare provider seen, and current date;
    // title, body, and healthcare provider name are capitalized
    public ClinicalNote(String title, String body, String name, Date date) {
        String titleCapitalized = title.substring(0, 1).toUpperCase() + title.substring(1);
        this.title = titleCapitalized;
        
        String bodyCapitalized = body.substring(0, 1).toUpperCase() + body.substring(1);
        this.body = bodyCapitalized;

        String nameCapitalized = name.substring(0, 1).toUpperCase() + name.substring(1);
        this.healthCareProvider = nameCapitalized;

        this.visitDate = date;
    }

    // EFFECTS: prints summary of the clinical note in String format
    public String printClinicaNote() {
        StringBuilder result = new StringBuilder();

        result.append("---------------------------------------------------------------------------------" + "\n");
        result.append(getClinicalNoteTitle() + "\n");
        result.append(getClinicalNoteBody() + "\n");
        result.append(getClinicalNoteProvider() + " ");
        result.append(getClinicalNoteDate().printDate() + "\n");

        return result.toString();
    }

    // Setters
    // MODIFIES: this
    // EFFECTS: sets clinical note's title with given title (capitalized)
    public void setClinicalNoteTitle(String title) {
        String titleCapitalized = title.substring(0, 1).toUpperCase() + title.substring(1);
        this.title = titleCapitalized;
        EventLog.getInstance().logEvent(new Event("Clinical note title set to " + this.title));
    }

    // MODIFIES: this
    // EFFECTS: sets clinical note's body with given body (capitalized)
    public void setClinicalNoteBody(String body) {
        String bodyCapitalized = body.substring(0, 1).toUpperCase() + body.substring(1);
        this.body = bodyCapitalized;
        EventLog.getInstance().logEvent(new Event("Clinical note body set to " + this.body));
    }

    // MODIFIES: this
    // EFFECTS: sets clinical note's healthcare provider's name with given name (capitalized)
    public void setClinicalNoteProvider(String name) {
        String nameCapitalized = name.substring(0, 1).toUpperCase() + name.substring(1);
        this.healthCareProvider = nameCapitalized;
        EventLog.getInstance().logEvent(new Event("Clinical note provider set to " + this.healthCareProvider));
    }

    // Getters
    // EFFECTS: gets clinical note's title
    public String getClinicalNoteTitle() {
        return title;
    }

    // EFFECTS: gets clinical note's body
    public String getClinicalNoteBody() {
        return body;
    }

    // EFFECTS: gets clinical note's healthcare provider's name
    public String getClinicalNoteProvider() {
        return healthCareProvider;
    }

    // EFFECTS: gets clinical note's date of visit
    public Date getClinicalNoteDate() {
        return visitDate;
    }

    // EFFECTS: returns clinical note as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("body", body);
        json.put("provider", healthCareProvider);
        json.put("visitdate", visitDateToJson());
        return json;
    }

    // EFFECTS: returns visit date for this clinical note as a JSON object
    private JSONObject visitDateToJson() {
        JSONObject json = new JSONObject();
        json.put("month", visitDate.getMonth());
        json.put("day", visitDate.getDay());
        json.put("year", visitDate.getYear());
        return json;
    }

}