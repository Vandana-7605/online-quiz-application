# Online Quiz Application

A console-based Online Quiz Application developed using Java. The application provides separate Admin and Student modules for managing and attempting quizzes.

## Features

### Admin Module

- Admin login authentication
- Create new quizzes
- Add multiple questions and options
- View available quizzes
- View all student results
- Delete quizzes
- Logout

### Student Module

- Student registration
- Student login authentication
- View available quizzes
- Attempt quizzes
- One attempt per quiz
- Automatic score calculation
- Percentage calculation
- Pass/Fail status
- View personal quiz results
- Logout

## Technologies Used

- Java 17
- Object-Oriented Programming (OOP)
- Java Collections
- File Handling
- Java Serialization
- Scanner for console input

## Data Storage

The application uses Java Serialization to store data persistently in:

- `users.dat` – Stores user information
- `quizzes.dat` – Stores quiz information
- `results.dat` – Stores quiz results

## Project Structure

```text
online quiz app/
│
├── QuizApp.java
├── Quiz.java
├── Question.java
├── User.java
│
├── users.dat
├── quizzes.dat
└── results.dat
```

## How to Run

### 1. Open the Project

Open the `online quiz app` folder in Visual Studio Code.

### 2. Check Java Version

Make sure Java 17 is installed.

```bash
java -version
```

```bash
javac -version
```

Both should show Java 17.

### 3. Compile the Java Files

Open the VS Code terminal inside the project folder and run:

```bash
javac Question.java Quiz.java User.java QuizApp.java
```

### 4. Run the Application

```bash
java QuizApp
```

## Application Flow

```text
Start Application
       |
       v
Register / Login
       |
       +------------------+
       |                  |
       v                  v
     Admin              Student
       |                  |
       v                  v
Create Quiz          View Quizzes
View Quizzes              |
View Results              v
Delete Quiz           Take Quiz
       |                  |
       |                  v
       |             Calculate Score
       |                  |
       |                  v
       |             Save Result
       |                  |
       |                  v
       |            View My Result
       |                  |
       +---------> Logout
```

## Key Highlights

- Separate Admin and Student functionality
- User authentication
- Quiz creation and management
- Multiple-choice questions
- Automatic answer evaluation
- Automatic score calculation
- Percentage calculation
- Pass/Fail result
- One-attempt restriction for students
- Persistent data storage
- Result management
- Object-oriented design
- Input validation

## Default Admin Account

The application creates a default administrator account if one does not already exist.

```text
Username: admin
Password: admin123
```

## Future Enhancements

- Graphical User Interface (GUI)
- MySQL or MongoDB database integration
- Web-based interface
- Quiz timer
- Question categories
- Difficulty levels
- Leaderboard
- Password encryption
- Detailed performance analytics