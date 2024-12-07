import java.util.*;

public class QuizGenerator {
    static Scanner scanner = new Scanner(System.in);
    static List<Quiz> quizzes = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Welcome to the Quiz Generator!");
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Create Quiz");
            System.out.println("2. Add Question to Quiz");
            System.out.println("3. Take Quiz");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    createQuiz();
                    break;
                case 2:
                    addQuestionToQuiz();
                    break;
                case 3:
                    takeQuiz();
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void createQuiz() {
        System.out.print("Enter quiz name: ");
        String quizName = scanner.nextLine();
        quizzes.add(new Quiz(quizName));
        System.out.println("Quiz '" + quizName + "' created successfully!");
    }

    static void addQuestionToQuiz() {
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available. Create a quiz first.");
            return;
        }

        System.out.println("Available Quizzes:");
        for (int i = 0; i < quizzes.size(); i++) {
            System.out.println((i + 1) + ". " + quizzes.get(i).getName());
        }

        System.out.print("Choose a quiz to add a question to: ");
        int quizChoice = scanner.nextInt() - 1;
        scanner.nextLine(); 

        if (quizChoice < 0 || quizChoice >= quizzes.size()) {
            System.out.println("Invalid quiz choice.");
            return;
        }

        Quiz selectedQuiz = quizzes.get(quizChoice);

        System.out.print("Enter question text: ");
        String questionText = scanner.nextLine();

        System.out.println("Enter 4 options:");
        List<String> options = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            System.out.print("Option " + (i + 1) + ": ");
            options.add(scanner.nextLine());
        }

        System.out.print("Enter correct option number (1-4): ");
        int correctAnswer = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        selectedQuiz.addQuestion(new Question(questionText, options, correctAnswer));
        System.out.println("Question added successfully to '" + selectedQuiz.getName() + "'.");
    }

    static void takeQuiz() {
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available.");
            return;
        }

        System.out.println("Available Quizzes:");
        for (int i = 0; i < quizzes.size(); i++) {
            System.out.println((i + 1) + ". " + quizzes.get(i).getName());
        }

        System.out.print("Choose a quiz to take: ");
        int quizChoice;
        try {
            quizChoice = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return;
        }

        if (quizChoice < 0 || quizChoice >= quizzes.size()) {
            System.out.println("Invalid quiz choice.");
            return;
        }

        Quiz selectedQuiz = quizzes.get(quizChoice);
        int score = 0;

        System.out.println("\nStarting Quiz: " + selectedQuiz.getName());
        for (Question question : selectedQuiz.getQuestions()) {
            System.out.println("\n" + question.getQuestionText());
            List<String> options = question.getOptions();
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i));
            }

            System.out.print("Your answer (1-4): ");
            int answer;
            try {
                answer = Integer.parseInt(scanner.nextLine());
                if (answer < 1 || answer > 4) {
                    System.out.println("Invalid answer. Must be between 1 and 4.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                continue;
            }

            if (answer == question.getCorrectAnswer()) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong! The correct answer was: " + question.getOptions().get(question.getCorrectAnswer() - 1));
            }
        }

        int totalQuestions = selectedQuiz.getQuestions().size();
        double percentage = ((double) score / totalQuestions) * 100;

        System.out.println("\nQuiz Completed! Your Score: " + score + "/" + totalQuestions);
        System.out.println("Score Percentage: " + String.format("%.2f", percentage) + "%");

        // Provide feedback based on percentage
        if (percentage == 100) {
            System.out.println("Excellent! You got a perfect score!");
        } else if (percentage >= 75) {
            System.out.println("Great job! You have a strong understanding of the material.");
        } else if (percentage >= 50) {
            System.out.println("Good effort! Consider reviewing some areas to improve.");
        } else {
            System.out.println("Keep trying! Review the material and try again.");
        }
    }
}

class Quiz {
    private String name;
    private List<Question> questions;

    public Quiz(String name) {
        this.name = name;
        this.questions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }
}

class Question {
    private String questionText;
    private List<String> options;
    private int correctAnswer;

    public Question(String questionText, List<String> options, int correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }
}
