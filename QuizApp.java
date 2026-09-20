import java.io.*;
import java.util.*;

public class QuizApp {

    private static final String USERS_FILE = "users.dat";
    private static final String QUIZZES_FILE = "quizzes.dat";
    private static final String RESULTS_FILE = "results.dat";

    private static Scanner scanner = new Scanner(System.in);

    private static List<User> users = new ArrayList<>();
    private static List<Quiz> quizzes = new ArrayList<>();
    private static List<Result> results = new ArrayList<>();

    public static void main(String[] args) {

        loadUsers();
        loadQuizzes();
        loadResults();

        createDefaultAdmin();

        while (true) {

            System.out.println();
            System.out.println("=================================");
            System.out.println("        ONLINE QUIZ APP");
            System.out.println("=================================");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("=================================");

            int choice = readInt("Enter your choice: ", 1, 3);

            switch (choice) {

                case 1:
                    register();
                    break;

                case 2:
                    login();
                    break;

                case 3:
                    saveAll();
                    System.out.println("Thank you for using Online Quiz App!");
                    scanner.close();
                    return;
            }
        }
    }

    // =====================================================
    // DEFAULT ADMIN
    // =====================================================

    private static void createDefaultAdmin() {

        boolean adminExists = false;

        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase("admin")) {
                adminExists = true;
                break;
            }
        }

        if (!adminExists) {

            users.add(new User(
                    "admin",
                    "admin123",
                    "ADMIN"
            ));

            saveUsers();

            System.out.println();
            System.out.println("Default admin account created.");
            System.out.println("Username: admin");
            System.out.println("Password: admin123");
        }
    }

    // =====================================================
    // REGISTER
    // =====================================================

    private static void register() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("          REGISTER");
        System.out.println("=================================");

        String username;

        while (true) {

            System.out.print("Enter username: ");
            username = scanner.nextLine().trim();

            if (username.isEmpty()) {
                System.out.println("Username cannot be empty.");
                continue;
            }

            if (username.length() < 3) {
                System.out.println("Username must contain at least 3 characters.");
                continue;
            }

            boolean exists = false;

            for (User user : users) {
                if (user.getUsername().equalsIgnoreCase(username)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                System.out.println("Username already exists.");
                continue;
            }

            break;
        }

        String password;

        while (true) {

            System.out.print("Enter password: ");
            password = scanner.nextLine();

            if (password.isEmpty()) {
                System.out.println("Password cannot be empty.");
                continue;
            }

            if (password.length() < 4) {
                System.out.println("Password must contain at least 4 characters.");
                continue;
            }

            break;
        }

        users.add(new User(
                username,
                password,
                "STUDENT"
        ));

        saveUsers();

        System.out.println();
        System.out.println("Registration successful!");
        System.out.println("You can now login.");
    }

    // =====================================================
    // LOGIN
    // =====================================================

    private static void login() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("            LOGIN");
        System.out.println("=================================");

        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User loggedInUser = null;

        for (User user : users) {

            if (user.getUsername().equalsIgnoreCase(username)
                    && user.checkPassword(password)) {

                loggedInUser = user;
                break;
            }
        }

        if (loggedInUser == null) {

            System.out.println();
            System.out.println("Invalid username or password.");
            return;
        }

        System.out.println();
        System.out.println("Login successful!");

        if (loggedInUser.getRole().equalsIgnoreCase("ADMIN")) {
            adminMenu(loggedInUser);
        } else {
            studentMenu(loggedInUser);
        }
    }

    // =====================================================
    // ADMIN MENU
    // =====================================================

    private static void adminMenu(User admin) {

        while (true) {

            System.out.println();
            System.out.println("=================================");
            System.out.println("           ADMIN MENU");
            System.out.println("=================================");
            System.out.println("Logged in as: " + admin.getUsername());
            System.out.println("---------------------------------");
            System.out.println("1. Create Quiz");
            System.out.println("2. View Available Quizzes");
            System.out.println("3. View All Results");
            System.out.println("4. Delete Quiz");
            System.out.println("5. Logout");
            System.out.println("=================================");

            int choice = readInt("Enter your choice: ", 1, 5);

            switch (choice) {

                case 1:
                    createQuiz();
                    break;

                case 2:
                    viewAvailableQuizzes();
                    break;

                case 3:
                    viewAllResults();
                    break;

                case 4:
                    deleteQuiz();
                    break;

                case 5:
                    System.out.println("Logged out successfully.");
                    return;
            }
        }
    }

    // =====================================================
    // CREATE QUIZ
    // =====================================================

    private static void createQuiz() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("           CREATE QUIZ");
        System.out.println("=================================");

        String quizName;

        while (true) {

            System.out.print("Enter quiz name: ");
            quizName = scanner.nextLine().trim();

            if (quizName.isEmpty()) {
                System.out.println("Quiz name cannot be empty.");
                continue;
            }

            boolean duplicate = false;

            for (Quiz quiz : quizzes) {

                if (quiz.getQuizName().equalsIgnoreCase(quizName)) {
                    duplicate = true;
                    break;
                }
            }

            if (duplicate) {
                System.out.println("A quiz with this name already exists.");
                continue;
            }

            break;
        }

        int numberOfQuestions =
                readInt("Enter number of questions: ", 1, 100);

        Quiz quiz = new Quiz(quizName);

        for (int i = 0; i < numberOfQuestions; i++) {

            System.out.println();
            System.out.println("---------------------------------");
            System.out.println("Question " + (i + 1));
            System.out.println("---------------------------------");

            String questionText;

            while (true) {

                System.out.print("Enter question: ");
                questionText = scanner.nextLine().trim();

                if (!questionText.isEmpty()) {
                    break;
                }

                System.out.println("Question cannot be empty.");
            }

            String[] options = new String[4];

            for (int j = 0; j < 4; j++) {

                while (true) {

                    System.out.print(
                            "Enter option " + (j + 1) + ": "
                    );

                    options[j] = scanner.nextLine().trim();

                    if (!options[j].isEmpty()) {
                        break;
                    }

                    System.out.println("Option cannot be empty.");
                }
            }

            int correctAnswer =
                    readInt(
                            "Enter correct option (1-4): ",
                            1,
                            4
                    );

            Question question = new Question(
                    questionText,
                    options,
                    correctAnswer - 1
            );

            quiz.addQuestion(question);
        }

        quizzes.add(quiz);

        saveQuizzes();

        System.out.println();
        System.out.println("Quiz created successfully!");
        System.out.println("Quiz name: " + quizName);
        System.out.println("Questions: " + numberOfQuestions);
    }

    // =====================================================
    // DELETE QUIZ
    // =====================================================

    private static void deleteQuiz() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("           DELETE QUIZ");
        System.out.println("=================================");

        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available.");
            return;
        }

        for (int i = 0; i < quizzes.size(); i++) {

            Quiz quiz = quizzes.get(i);

            System.out.println(
                    (i + 1)
                            + ". "
                            + quiz.getQuizName()
                            + " ("
                            + quiz.getQuestions().size()
                            + " questions)"
            );
        }

        int choice = readInt(
                "Select quiz to delete (0 to cancel): ",
                0,
                quizzes.size()
        );

        if (choice == 0) {
            System.out.println("Delete operation cancelled.");
            return;
        }

        Quiz selectedQuiz = quizzes.get(choice - 1);

        System.out.println();
        System.out.println(
                "Are you sure you want to delete \""
                        + selectedQuiz.getQuizName()
                        + "\"?"
        );

        System.out.println("1. Yes");
        System.out.println("2. No");

        int confirm = readInt("Enter choice: ", 1, 2);

        if (confirm == 1) {

            String deletedQuizName = selectedQuiz.getQuizName();

            quizzes.remove(selectedQuiz);

            saveQuizzes();

            // Remove associated results
            results.removeIf(result ->
                    result.quizName.equalsIgnoreCase(deletedQuizName)
            );

            saveResults();

            System.out.println("Quiz deleted successfully.");
        } else {

            System.out.println("Delete operation cancelled.");
        }
    }

    // =====================================================
    // STUDENT MENU
    // =====================================================

    private static void studentMenu(User student) {

        while (true) {

            System.out.println();
            System.out.println("=================================");
            System.out.println("          STUDENT MENU");
            System.out.println("=================================");
            System.out.println("Logged in as: " + student.getUsername());
            System.out.println("---------------------------------");
            System.out.println("1. View Available Quizzes");
            System.out.println("2. Take Quiz");
            System.out.println("3. View My Results");
            System.out.println("4. Logout");
            System.out.println("=================================");

            int choice = readInt("Enter your choice: ", 1, 4);

            switch (choice) {

                case 1:
                    viewAvailableQuizzesForStudent(student);
                    break;

                case 2:
                    takeQuiz(student);
                    break;

                case 3:
                    viewMyResults(student);
                    break;

                case 4:
                    System.out.println("Logged out successfully.");
                    return;
            }
        }
    }

    // =====================================================
    // VIEW ALL QUIZZES - ADMIN
    // =====================================================

    private static void viewAvailableQuizzes() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("        AVAILABLE QUIZZES");
        System.out.println("=================================");

        if (quizzes.isEmpty()) {

            System.out.println("No quizzes available.");
            return;
        }

        for (int i = 0; i < quizzes.size(); i++) {

            Quiz quiz = quizzes.get(i);

            System.out.println(
                    (i + 1)
                            + ". "
                            + quiz.getQuizName()
                            + " ("
                            + quiz.getQuestions().size()
                            + " questions)"
            );
        }
    }

    // =====================================================
    // VIEW AVAILABLE QUIZZES - STUDENT
    // =====================================================

    private static void viewAvailableQuizzesForStudent(User student) {

        System.out.println();
        System.out.println("=================================");
        System.out.println("        AVAILABLE QUIZZES");
        System.out.println("=================================");

        boolean available = false;

        for (Quiz quiz : quizzes) {

            if (!hasAttempted(
                    student.getUsername(),
                    quiz.getQuizName())) {

                available = true;

                System.out.println(
                        (getQuizNumber(quiz) + 1)
                                + ". "
                                + quiz.getQuizName()
                                + " ("
                                + quiz.getQuestions().size()
                                + " questions)"
                );
            }
        }

        if (!available) {

            System.out.println("All quizzes have been completed.");
            System.out.println("There are no quizzes available to attempt.");
        }
    }

    // =====================================================
    // TAKE QUIZ
    // =====================================================

    private static void takeQuiz(User student) {

        System.out.println();
        System.out.println("=================================");
        System.out.println("           TAKE QUIZ");
        System.out.println("=================================");

        List<Quiz> availableQuizzes = new ArrayList<>();

        for (Quiz quiz : quizzes) {

            if (!hasAttempted(
                    student.getUsername(),
                    quiz.getQuizName())) {

                availableQuizzes.add(quiz);
            }
        }

        if (availableQuizzes.isEmpty()) {

            System.out.println(
                    "All quizzes have been completed."
            );

            System.out.println(
                    "There are no quizzes available to attempt."
            );

            return;
        }

        for (int i = 0; i < availableQuizzes.size(); i++) {

            Quiz quiz = availableQuizzes.get(i);

            System.out.println(
                    (i + 1)
                            + ". "
                            + quiz.getQuizName()
                            + " ("
                            + quiz.getQuestions().size()
                            + " questions)"
            );
        }

        int choice = readInt(
                "Select quiz: ",
                1,
                availableQuizzes.size()
        );

        Quiz selectedQuiz = availableQuizzes.get(choice - 1);

        System.out.println();
        System.out.println("=================================");
        System.out.println(
                "QUIZ: " + selectedQuiz.getQuizName()
        );
        System.out.println("=================================");

        int score = 0;

        List<Question> questions =
                new ArrayList<>(selectedQuiz.getQuestions());

        // Randomize question order
        Collections.shuffle(questions);

        for (int i = 0; i < questions.size(); i++) {

            Question question = questions.get(i);

            System.out.println();
            System.out.println(
                    "Q" + (i + 1) + ". "
                            + question.getQuestion()
            );

            String[] options = question.getOptions();

            for (int j = 0; j < options.length; j++) {

                System.out.println(
                        (j + 1) + ". " + options[j]
                );
            }

            int answer = readInt(
                    "Your answer (1-4): ",
                    1,
                    4
            );

            if (question.isCorrectAnswer(answer - 1)) {

                System.out.println("Correct!");
                score++;

            } else {

                System.out.println(
                        "Wrong! Correct answer: "
                                + options[
                                question.getCorrectOptionIndex()
                                ]
                );
            }
        }

        int totalQuestions = questions.size();

        int wrongAnswers = totalQuestions - score;

        double percentage =
                ((double) score / totalQuestions) * 100;

        String status;

        if (percentage >= 40) {
            status = "PASSED";
        } else {
            status = "FAILED";
        }

        Result result = new Result(
                student.getUsername(),
                selectedQuiz.getQuizName(),
                score,
                totalQuestions,
                percentage
        );

        results.add(result);

        saveResults();

        System.out.println();
        System.out.println("=================================");
        System.out.println("          QUIZ COMPLETED");
        System.out.println("=================================");
        System.out.println(
                "Student: " + student.getUsername()
        );
        System.out.println(
                "Quiz: " + selectedQuiz.getQuizName()
        );
        System.out.println("---------------------------------");
        System.out.println(
                "Total Questions : " + totalQuestions
        );
        System.out.println(
                "Correct Answers : " + score
        );
        System.out.println(
                "Wrong Answers   : " + wrongAnswers
        );
        System.out.printf(
                "Percentage      : %.2f%%%n",
                percentage
        );
        System.out.println(
                "Status          : " + status
        );
        System.out.println("---------------------------------");
        System.out.println(
                "This quiz cannot be attempted again."
        );
        System.out.println("=================================");
    }

    // =====================================================
    // MY RESULTS
    // =====================================================

    private static void viewMyResults(User student) {

        System.out.println();
        System.out.println("=================================");
        System.out.println("          MY RESULTS");
        System.out.println("=================================");

        boolean found = false;

        for (Result result : results) {

            if (result.username.equalsIgnoreCase(
                    student.getUsername())) {

                found = true;

                String status;

                if (result.percentage >= 40) {
                    status = "PASSED";
                } else {
                    status = "FAILED";
                }

                System.out.println();
                System.out.println(
                        "Quiz: " + result.quizName
                );

                System.out.println(
                        "Score: "
                                + result.score
                                + "/"
                                + result.totalQuestions
                );

                System.out.printf(
                        "Percentage: %.2f%%%n",
                        result.percentage
                );

                System.out.println(
                        "Status: " + status
                );

                System.out.println("---------------------------------");
            }
        }

        if (!found) {
            System.out.println("No quizzes completed yet.");
        }
    }

    // =====================================================
    // ALL RESULTS
    // =====================================================

    private static void viewAllResults() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("          ALL RESULTS");
        System.out.println("=================================");

        if (results.isEmpty()) {

            System.out.println("No results available.");
            return;
        }

        for (Result result : results) {

            String status;

            if (result.percentage >= 40) {
                status = "PASSED";
            } else {
                status = "FAILED";
            }

            System.out.println();
            System.out.println(
                    "Student: " + result.username
            );

            System.out.println(
                    "Quiz: " + result.quizName
            );

            System.out.println(
                    "Score: "
                            + result.score
                            + "/"
                            + result.totalQuestions
            );

            System.out.printf(
                    "Percentage: %.2f%%%n",
                    result.percentage
            );

            System.out.println(
                    "Status: " + status
            );

            System.out.println("---------------------------------");
        }
    }

    // =====================================================
    // CHECK ATTEMPT
    // =====================================================

    private static boolean hasAttempted(
            String username,
            String quizName) {

        for (Result result : results) {

            if (result.username.equalsIgnoreCase(username)
                    && result.quizName.equalsIgnoreCase(quizName)) {

                return true;
            }
        }

        return false;
    }

    // =====================================================
    // QUIZ NUMBER
    // =====================================================

    private static int getQuizNumber(Quiz selectedQuiz) {

        for (int i = 0; i < quizzes.size(); i++) {

            if (quizzes.get(i) == selectedQuiz) {
                return i;
            }
        }

        return 0;
    }

    // =====================================================
    // INPUT VALIDATION
    // =====================================================

    private static int readInt(
            String message,
            int min,
            int max) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            try {

                int value = Integer.parseInt(input);

                if (value >= min && value <= max) {
                    return value;
                }

                System.out.println(
                        "Please enter a number between "
                                + min
                                + " and "
                                + max
                                + "."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input. Please enter a number."
                );
            }
        }
    }

    // =====================================================
    // FILE HANDLING - USERS
    // =====================================================

    private static void saveUsers() {

        try (ObjectOutputStream output =
                     new ObjectOutputStream(
                             new FileOutputStream(USERS_FILE))) {

            output.writeObject(users);

        } catch (IOException e) {

            System.out.println(
                    "Error saving users: "
                            + e.getMessage()
            );
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadUsers() {

        File file = new File(USERS_FILE);

        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream input =
                     new ObjectInputStream(
                             new FileInputStream(file))) {

            users = (List<User>) input.readObject();

        } catch (Exception e) {

            users = new ArrayList<>();
        }
    }

    // =====================================================
    // FILE HANDLING - QUIZZES
    // =====================================================

    private static void saveQuizzes() {

        try (ObjectOutputStream output =
                     new ObjectOutputStream(
                             new FileOutputStream(QUIZZES_FILE))) {

            output.writeObject(quizzes);

        } catch (IOException e) {

            System.out.println(
                    "Error saving quizzes: "
                            + e.getMessage()
            );
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadQuizzes() {

        File file = new File(QUIZZES_FILE);

        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream input =
                     new ObjectInputStream(
                             new FileInputStream(file))) {

            quizzes = (List<Quiz>) input.readObject();

        } catch (Exception e) {

            quizzes = new ArrayList<>();
        }
    }

    // =====================================================
    // FILE HANDLING - RESULTS
    // =====================================================

    private static void saveResults() {

        try (ObjectOutputStream output =
                     new ObjectOutputStream(
                             new FileOutputStream(RESULTS_FILE))) {

            output.writeObject(results);

        } catch (IOException e) {

            System.out.println(
                    "Error saving results: "
                            + e.getMessage()
            );
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadResults() {

        File file = new File(RESULTS_FILE);

        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream input =
                     new ObjectInputStream(
                             new FileInputStream(file))) {

            results = (List<Result>) input.readObject();

        } catch (Exception e) {

            results = new ArrayList<>();
        }
    }

    // =====================================================
    // SAVE EVERYTHING
    // =====================================================

    private static void saveAll() {

        saveUsers();
        saveQuizzes();
        saveResults();
    }

    // =====================================================
    // RESULT CLASS
    // =====================================================

    private static class Result implements Serializable {

        private static final long serialVersionUID = 1L;

        private String username;
        private String quizName;
        private int score;
        private int totalQuestions;
        private double percentage;

        public Result(
                String username,
                String quizName,
                int score,
                int totalQuestions,
                double percentage) {

            this.username = username;
            this.quizName = quizName;
            this.score = score;
            this.totalQuestions = totalQuestions;
            this.percentage = percentage;
        }
    }
}