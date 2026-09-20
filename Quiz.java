import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Quiz implements Serializable {

    private static final long serialVersionUID = 1L;

    private String quizName;
    private List<Question> questions;

    public Quiz(String quizName) {
        this.quizName = quizName;
        this.questions = new ArrayList<>();
    }

    public String getQuizName() {
        return quizName;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }
}