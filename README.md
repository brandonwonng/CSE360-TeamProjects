# Team 3 Group Project CSE360

This is a JavaFX-based academic discussion platform inspired by tools like Ed Discussion. Built for educational environments, this application allows students to post questions, engage in threaded replies, mark questions as resolved, and communicate via private messaging, within a structured role-based system.

The system supports multiple user types, including **Students, Reviewers, Instructors, Staff, and Admins**, each with unique access and permissions. With future expansions, this application aims to provide a solution for in-class and remote academic Q&A, all managed through a custom local data system and a modular GUI interface.

## Features

- User account creation and authentication
- Admin panel with user role assignment
- Private messaging between users
- Security question-based password recovery
- Modular design using Java packages
- JavaFX GUI with custom screens and navigation

## Tools & Languages

- Java 21 (JRE 21.0.7)
- JavaFX 21.0.7
- H2 Database
- Eclipse IDE

## Project Structure

    TP1_v6/
    ├── src/
    │   ├── applicationMainMethodClasses/  # Main launcher (FCMainClass.java)
    │   ├── databaseClasses/               # Handles data storage and access
    │   ├── entityClasses/                 # Core data models (User, Question, etc.)
    │   ├── guiPageClasses/                # JavaFX GUI components
    ├── bin/                               # Compiled .class files
    ├── text/                              # Persistent data
    ├── .classpath / .project              # Eclipse project files
    └── build.fxbuild                      # JavaFX build configuration

## Running the Application

1. **Open in Eclipse**:
   - Import as an existing Java project.
   - Make sure the proper JavaFX SDK and H2 Database are configured in the project build path.

2. **Run**:
   - Execute `FCMainClass.java` in the `applicationMainMethodClasses` package.

## Authors

- Brandon Wong
- Clay Hauser
- John Hadley
- Noah Dow
