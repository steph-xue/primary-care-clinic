<h1 align="center">
  Primary Care Clinic Application
</h1>

<h4 align="center">
  A desktop application for managing patient records and clinical notes in a primary care clinic setting.
</h4>

<p align="center">
  <img src="screenshots/logo.png" alt="logo" width="500"/>
</p>

<br>

## Table of Contents
- [Problem Statement](#problem-statement)
- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [User Interface Options](#user-interface-options)
- [Object-Oriented Design Reflections](#object-oriented-design-reflections)
- [JSON-Based Data Persistence](#json-based-data-persistence)
- [Unit Testing](#unit-testing)
- [Event Log](#event-log)
- [Future Improvements](#future-improvements)
- [Getting Started](#getting-started)

<br>

## Problem Statement

This project draws on my background in healthcare and my experience working across community pharmacy, hospital, and ambulatory care settings. Through these roles, I have used several electronic health record systems and seen how inefficient workflows, fragmented information, and unintuitive interfaces can make patient care more difficult for healthcare professionals. Building an EHR application allows me to combine my healthcare experience with my technical skills to explore how thoughtful software design can improve the way clinicians access, manage, and use patient information. It also reflects my broader interest in applying technology to streamline healthcare workflows and support more efficient, user-centered care.

<br>

## Overview

This project is a desktop Electronic Health Record (EHR) application designed to streamline and enhance patient management for clinicians working in primary care clinics. This application provides healthcare professionals with an intuitive and efficient platform to organize, update, and access patient information. It allows clinicians to manage an alphabetically sorted list of registered patients, add new patient profiles, update existing information, and remove patients from the system. Each patient record tracks demographic and clinical information, including name, date of birth, age, personal health number, allergies, medications, and medical conditions, alongside a full history of clinical notes documenting past visits. It is built entirely in Java, with a graphical interface built using Java Swing and a parallel command-line interface, both sharing the same underlying model and persistence logic so behavior stays consistent regardless of how the application is accessed. JSON handles serialization, allowing clinic data to be saved to and loaded from files between sessions, and JUnit Jupiter is used for unit testing across the model and persistence layers.

The application is designed for use by medical professionals and administrative staff, including physicians, clinical pharmacists, nurses, and medical office assistants, to streamline patient management and support accurate record keeping for improved patient care.

<br>

## Features

### Loading Screen
A loading screen displays with an animated loading indicator during application startup before automatically transitioning to the start screen.

<p align="center"><b>A loading screen is shown at startup</b></p>
<p align="center"><img src="./screenshots/landing.png?raw=true" alt="Loading Screen" width="650"></p>

<br>

### Loading Clinic Data from File or Creating a New Clinic
Upon starting the application, users can choose to load existing clinic data from a saved file, or create a new clinic under a new name.

<p align="center"><b>Users can choose to load saved clinic data from file or create a new clinic</b></p>
<p align="center"><img src="./screenshots/load.png?raw=true" alt="Load Clinic" width="650"></p>

<p align="center"><b>The selected clinic data has been successfully loaded</b></p>
<p align="center"><img src="./screenshots/loadSuccess.png?raw=true" alt="Load Clinic Success" width="650"></p>

<br>

### Renaming the Clinic
Users can rename the clinic at any time from the main menu.

<p align="center"><b>Users can enter in a new name for the clinic</b></p>
<p align="center"><img src="./screenshots/rename.png?raw=true" alt="Rename Clinic" width="650"></p>

<p align="center"><b>A confirmation message will appear before the clinic name is updated</b></p>
<p align="center"><img src="./screenshots/renameSuccess.png?raw=true" alt="Rename Clinic Success" width="650"></p>

<p align="center"><b>The clinic has been successfully renamed</b></p>
<p align="center"><img src="./screenshots/renameResult.png?raw=true" alt="Rename Clinic Result" width="650"></p>

<br>

### Viewing All Patient Records
Every patient record is displayed in a list view, sorted alphabetically by last name and then first name. Double clicking a patient in the list opens their full profile.

<p align="center"><b>The full view of all patient records</b></p>
<p align="center"><img src="./screenshots/viewAllPatients.png?raw=true" alt="View All Patients" width="650"></p>

<br>

### Viewing a Patient Record
Each patient profile displays the patient's information, including demographic details such as first name, last name, date of birth, age, and personal health number, along with their allergies, medications, medical conditions, and clinical notes. Clinical notes are displayed in reverse chronological order, with the most recent visit shown first.

<p align="center"><b>Patient demographics and medical history</b></p>
<p align="center"><img src="./screenshots/profile1.png?raw=true" alt="Patient Profile 1" width="650"></p>

<p align="center"><b>Clinical notes</b></p>
<p align="center"><img src="./screenshots/profile2.png?raw=true" alt="Patient Profile 2" width="650"></p>

<br>

### Editing and Removing a Patient Record
Users can edit any of a patient's demographic fields, with validation in place to ensure information is entered correctly and a clear error message shown if any check fails. Allergies, medications, medical conditions, and clinical notes can each be added or removed directly from the profile. A patient's record can also be deleted entirely.

<p align="center"><b>Users can edit patient demographic information</b></p>
<p align="center"><img src="./screenshots/editName.png?raw=true" alt="Edit Patient Name" width="650"></p>

<p align="center"><b>Users can add medical information such as allergies</b></p>
<p align="center"><img src="./screenshots/profileAddAllergy.png?raw=true" alt="Add Allergy" width="650"></p>

<p align="center"><b>The allergy has been successfully added</b></p>
<p align="center"><img src="./screenshots/profileAddAllergySuccess.png?raw=true" alt="Add Allergy Success" width="650"></p>

<p align="center"><b>Users can remove medical information such as allergies</b></p>
<p align="center"><img src="./screenshots/profileRemoveAllergy.png?raw=true" alt="Remove Allergy" width="650"></p>

<p align="center"><b>The allergy has been successfully removed</b></p>
<p align="center"><img src="./screenshots/profileRemoveAllergySuccess.png?raw=true" alt="Remove Allergy Success" width="650"></p>

<br>

### Adding a New Patient Record
Users can add a new patient record by entering the patient's demographic details, including first name, last name, date of birth, age, and personal health number, along with any number of allergies, medications, and medical conditions. Fields are validated to ensure information is valid and, where required, not left empty, with a clear error message shown if any check fails. The new patient then appears in the clinic's patient list immediately after being added.

<p align="center"><b>Users can create a new patient record</b></p>
<p align="center"><img src="./screenshots/newPatientEmpty1.png?raw=true" alt="New Patient Form 1" width="650"></p>
<p align="center"><img src="./screenshots/newPatientEmpty2.png?raw=true" alt="New Patient Form 2" width="650"></p>

<p align="center"><b>Patient record details can be filled in</b></p>
<p align="center"><img src="./screenshots/newPatient1.png?raw=true" alt="New Patient Filled 1" width="650"></p>
<p align="center"><img src="./screenshots/newPatient2.png?raw=true" alt="New Patient Filled 2" width="650"></p>

<p align="center"><b>Medical information like allergies can be added</b></p>
<p align="center"><img src="./screenshots/newPatientAddAllergy.png?raw=true" alt="New Patient Add Allergy" width="650"></p>

<p align="center"><b>The patient's record has been successfully created</b></p>
<p align="center"><img src="./screenshots/newPatientSuccess.png?raw=true" alt="New Patient Success" width="650"></p>

<p align="center"><b>The new patient can be found in the list all patient records</b></p>
<p align="center"><img src="./screenshots/newPatientSuccessViewAll.png?raw=true" alt="New Patient in List View" width="650"></p>

<p align="center"><b>The profile of the newly added patient can then be viewed</b></p>
<p align="center"><img src="./screenshots/newProfile1.png?raw=true" alt="New Patient Profile 1" width="650"></p>
<p align="center"><img src="./screenshots/newProfile2.png?raw=true" alt="New Patient Profile 2" width="650"></p>

<br>

### Adding a Clinical Note
Users can add a new clinical note with a visit title, the healthcare provider seen, and the visit details, with the visit date assigned automatically based on the current system date. No note can be saved with an empty title, provider, or body, with a clear error message shown if any field is left blank.

<p align="center"><b>Users can add new clinical notes</b></p>
<p align="center"><img src="./screenshots/addNoteEmpty.png?raw=true" alt="Add Note Empty" width="650"></p>

<p align="center"><b>Details of the patient encounter can be filled in</b></p>
<p align="center"><img src="./screenshots/addNote.png?raw=true" alt="Add Note Filled" width="650"></p>

<p align="center"><b>The new clinical note has been successfully added</b></p>
<p align="center"><img src="./screenshots/addNoteSuccess.png?raw=true" alt="Add Note Success" width="650"></p>

<p align="center"><b>Users can view all clinical notes in the patient's profile</b></p>
<p align="center"><img src="./screenshots/addNoteSuccessResult.png?raw=true" alt="Add Note Success Result" width="650"></p>

<br>

### Editing and Removing a Clinical Note
Existing clinical notes can have their title, body, and provider edited, or be removed entirely from the patient's record.

<p align="center"><b>Users can edit details of an existing clinical note</b></p>
<p align="center"><img src="./screenshots/editTitle.png?raw=true" alt="Edit Note Title" width="650"></p>

<p align="center"><b>The clinical note been successfully updated</b></p>
<p align="center"><img src="./screenshots/editTitleSuccess.png?raw=true" alt="Edit Note Title Success" width="650"></p>

<p align="center"><b>Users can remove an existing clinical note</b></p>
<p align="center"><img src="./screenshots/removeNote.png?raw=true" alt="Remove Note" width="650"></p>

<p align="center"><b>The clinical note has been successfully removed</b></p>
<p align="center"><img src="./screenshots/removeNoteSuccess.png?raw=true" alt="Remove Note Success" width="650"></p>

<br>

### Saving and Quitting
Upon quitting the application, users are given the option to save their clinic data to file or quit without saving.

<p align="center"><b>Users can choose to save before quitting or quit without saving</b></p>
<p align="center"><img src="./screenshots/save.png?raw=true" alt="Save and Quit" width="650"></p>

<br>

## Tech Stack

| Layer | Technologies |
|---|---|
| Frontend | Java Swing (graphical user interface) |
| Backend | Java (core application logic) |
| Storage | JSON (local file storage) |
| Testing | JUnit Jupiter (unit testing framework) |

> **Note:** Java Swing powers the graphical user interface, but the application can also be run through a command-line interface built in Java, both sharing the same underlying model and persistence logic.

<br>

## User Interface Options

To maximize usability and accessibility, the application implements two distinct user interfaces that share the same backend logic. Both interfaces interact with the same model and persistence layers, ensuring consistent behavior regardless of how the application is accessed.

### Command-Line Interface (CLI)
Found in `Main.java` in the cli folder (`main/ui/cli/Main.java`), the CLI offers a text-based interface ideal for terminal-based workflows, with simple menu-driven navigation and input validation to guide users through interactions.

### Graphical User Interface (GUI)
Found in `MainUI.java` in the gui folder (`main/ui/gui/MainUI.java`), the GUI provides a visual, user-friendly experience with clearly structured panels and a navigation bar that enables smooth switching between screens.

<br>

## Object-Oriented Design Reflections

Working on this project deepened my understanding of how object-oriented programming principles shape a well-designed system in practice, not just in theory. Applying modularity, reusability, the single responsibility principle, abstraction, encapsulation, inheritance, and polymorphism throughout the codebase helped me see firsthand how these principles lead to cleaner separation of models, easier reusability, and stronger data integrity.

<p align="center"><img src="./screenshots/uml.png?raw=true" alt="UML Design Diagram" width="800"></p>

### Modularity and Reusability
Each major concept in the clinic system is modeled as its own class, making the system modular for maintainability and reusability. These include:
- **Clinic**: manages and stores patients
- **Patient**: stores individual patient data and clinical notes
- **ClinicalNote**: holds individual clinical note entries for patients
- **Date**: stores date-related logic

### Single Responsibility Principle
Each class has one main, well-defined responsibility, which helps improve the scalability of the application by allowing new features to be added with minimal disruption to the existing codebase.

### Abstraction and Encapsulation
Abstraction is used to hide implementation details and expose only relevant functionality. Encapsulation is achieved by designing each model class with private fields and public getter and setter methods, ensuring that an object's internal state cannot be accessed or modified directly from outside the class, reducing the likelihood of bugs and unintended behavior.

### Inheritance and Polymorphism
Writable is an interface implemented by model classes such as Clinic, Patient, and ClinicalNote, allowing polymorphic behavior when writing data to JSON.

<br>

## JSON-Based Data Persistence

To allow users to save and reload their clinic data between sessions, the application implements data persistence using custom JSON-based file readers and writers, letting users choose between starting a new session or loading a previously saved clinic, with error handling in place for missing files to improve reliability.

### Writing Data
`JsonWriter.java` is responsible for converting the current state of the Clinic object into a well-structured JSON file, including all patients and their associated clinical notes, allowing for easy storage, backup, and transferability of clinic data.

### Reading Data
`JsonReader.java` reads a previously saved JSON file and reconstructs the complete clinic state by creating new Clinic, Patient, and ClinicalNote objects based on the serialized data.

<br>

## Unit Testing

Thorough testing was critical to ensuring the reliability and robustness of the application. JUnit Jupiter was used to write unit tests for every class and method in the model and persistence packages, including:

**Models:**
- TestClinic.java
- TestPatient.java
- TestClinicalNote.java
- TestData.java

**Persistence:**
- JsonReaderTest.java
- JsonTest.java
- JsonWriterTest.java

Every branch and conditional pathway was tested to ensure proper handling under all scenarios, resulting in 100% test coverage.

<br>

## Event Log

The application automatically generates a detailed event log that tracks all significant actions taken during a session, including changes to clinic information, additions and updates to patient records, and modifications to clinical notes. Each log entry includes a precise timestamp and a clear description of the action performed, providing a transparent and traceable history of user interactions within the system. This feature supports auditing, debugging, and better accountability for patient data management. The event log is implemented as a Singleton, ensuring the entire application shares a single, globally accessible log instance, and its contents are printed to the console when the application quits.

**Sample event log record:**
```
Fri Mar 28 01:54:35 PDT 2025 - Clinic name set to Medicare Plus
Fri Mar 28 01:55:26 PDT 2025 - Patient Mya Cornell added to the clinic
Fri Mar 28 01:56:04 PDT 2025 - Patient Ashley Davis added to the clinic
Fri Mar 28 01:56:36 PDT 2025 - Patient Amira Garcia added to the clinic
Fri Mar 28 01:56:55 PDT 2025 - Set first name of patient Amira Garcia to Freya
Fri Mar 28 01:57:00 PDT 2025 - Set last name of patient Freya Garcia to Kwon
Fri Mar 28 01:57:19 PDT 2025 - Set date of birth of patient Freya Kwon to 10/27/1987
Fri Mar 28 01:57:23 PDT 2025 - Set age of patient Freya Kwon to 54
Fri Mar 28 01:57:29 PDT 2025 - Set personal health number of patient Freya Kwon to 946846634
Fri Mar 28 01:58:00 PDT 2025 - Added new clinical note for patient Freya Kwon
Fri Mar 28 01:58:15 PDT 2025 - Clinical note title set to Heartburn
Fri Mar 28 01:58:25 PDT 2025 - Clinical note provider set to Dr. Nguyen
Fri Mar 28 01:58:34 PDT 2025 - Clinical note body set to Patient presents with frequent heartburn and acid regurgitation
Fri Mar 28 01:58:39 PDT 2025 - Removed clinical note for patient Freya Kwon
Fri Mar 28 01:58:40 PDT 2025 - Patient Freya Kwon removed from the clinic
Fri Mar 28 01:59:14 PDT 2025 - Clinic name set to Careplus
```

<br>

## Future Improvements

There are some things that would be refactored to improve the design of this project with more time:
- Refactoring the PrimaryCareClinicApp class of the UI package, which runs the functionality of the command-line interface, by breaking it into smaller, separate classes that each align with the single responsibility principle. This would ensure each class handles one distinct task (such as user input, command parsing, or output display) to improve cohesion, readability, and maintainability.
- Abstracting duplicated code in the model package into shared methods. For example, the code for addAllergy, addMedication, and addMedicalCondition could be abstracted into a single method to minimize duplication, improving reusability and making future updates easier since shared logic would only need to be changed in one place.
- Refactoring the NavigationBarUI class so that instead of being instantiated separately by multiple GUI screen classes, it is instantiated once for the entire application in the MainUI class. This would centralize control of navigation, prevent redundant instances, and ensure consistent behavior across all UI screens.

<br>

## Getting Started

Follow the steps below to set up and run the application on your own machine.

**Prerequisites**

Make sure Java is installed before you begin. You can check by running the command below, which should print a version number.
```bash
java --version
```

Install [Visual Studio Code](https://code.visualstudio.com/) along with the [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack), since the project's classpath is preconfigured for VS Code and automatically references the JAR files in the `lib` folder.

**1. Clone the repository**

This downloads a copy of the project to your computer and moves you into the project folder.
```bash
git clone https://github.com/steph-xue/primary-care-clinic.git
cd primary-care-clinic
```

**2. Open the project in VS Code**

Open the cloned `primary-care-clinic` folder in VS Code. The Java extension will automatically detect the source paths and reference the libraries in the `lib` folder.

**3. Run the application**

To run the command-line version of the application, run `Main.java` in the cli folder (`src/main/ui/cli/Main.java`).

To run the graphical version of the application, run `MainUI.java` in the gui folder (`src/main/ui/gui/MainUI.java`).
