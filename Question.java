import java.io.Serializable;

public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    private String question;
    private String[] options;
    private int correctOptionIndex;

    public Question(String question, String[] options, int correctOptionIndex) {
        this.question = question;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public boolean isCorrectAnswer(int userAnswerIndex) {
        return userAnswerIndex == correctOptionIndex;
    }
}