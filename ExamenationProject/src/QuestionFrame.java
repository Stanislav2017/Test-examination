import component.AnswerComponentImpl;
import component.QuestionComponentImpl;
import model.RightAnswer;
import model.VariantAnswer;
import model.Question;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Фрейм для тестирования. */
public class QuestionFrame extends JFrame {
    public static void main(String[] args) {
        new QuestionFrame();
    }

    /**
     * Поток с отсчетом времени. */
    private Thread timer;

    /**
     * Имя пользователя. */
    private static final String USER_NAME = "Stanislav2018";

    /**
     * Пароль для авторизации. */
    private static final String PASSWORD = generatePassword();

    /**
     * Максимальное количество правильных ответов. */
    private static final Integer MAX_RIGHT_ANSWER = 5;

    /**
     * Колличество правильных ответов. */
    private Integer currentRightAnswer = 0;

    /**
     * Номер панели с вопросами. */
    private Integer panelNumber = 0;

    /**
     * Список вопросов. */
    private List<Question> questions;

    /**
     * Панели с вопросами. */
    private Map<Integer, Box> questionPanels = new HashMap<>();

    /**
     * Панель с тестовыми вопросами. */
    private Box testPanel;

    /**
     * Панель авторизации. */
    private Box loginPanel;

    /**
     * Панель с результатами тестирования. */
    private Box rezultPanel;

    /**
     * Ответы пользователя. */
    private Map<Integer, Integer> userAnswers = new HashMap<>();

    /**
     * Правильные ответы. */
    private Map<Integer, Integer> rightAnswers;

    private QuestionFrame() {
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        super.setTitle("File system work");

        initLoginPanel();
        super.setVisible(true);
    }

    /**
     * Создает панель авторизации.
     */
    private void initLoginPanel() {
        this.loginPanel = Box.createVerticalBox();
        JLabel titleLabel = new JLabel("Authorization");
        titleLabel.setFont(new java.awt.Font("Times New Roman", 1, 20));
        titleLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        JLabel userNameLabel = new JLabel("Username :");
        JTextField usernameTextFiled = new JTextField(15);
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        usernamePanel.add(userNameLabel);
        usernamePanel.add(usernameTextFiled);

        JLabel passwordLabel = new JLabel("Password :");
        JPasswordField passwordField = new JPasswordField(15);
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        loginButton.setMaximumSize(new Dimension(250, 100));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String username = usernameTextFiled.getText();
                String password = passwordField.getText();
                login(username, password);
            }
        });

        this.loginPanel.add(Box.createVerticalStrut(30));
        this.loginPanel.add(titleLabel);
        this.loginPanel.add(Box.createVerticalStrut(30));
        this.loginPanel.add(usernamePanel);
        this.loginPanel.add(Box.createVerticalStrut(30));
        this.loginPanel.add(passwordPanel);
        this.loginPanel.add(Box.createVerticalStrut(30));
        this.loginPanel.add(loginButton);
        this.loginPanel.add(Box.createVerticalStrut(30));

        super.setSize(350, 250);
        super.add(this.loginPanel);
    }

    /**
     * Проверка авторизации.
     * @param userName имя пользователя.
     * @param password пароль.
     */
    private void login(String userName, String password) {
        boolean isUsernameCorrect = USER_NAME.equals(userName);
        boolean isPasswordCorrect = PASSWORD.equals(password);
        if (isUsernameCorrect && isPasswordCorrect) {
            try {
                this.cleanLoginPanel();
                this.initQuestion();
                this.initQuestionsPanels();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Неправильное имя пользователя или пароль",
                    "Информация",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Удаление панели авторизации.
     */
    private void cleanLoginPanel() {
        this.loginPanel.setVisible(false);
        getContentPane().remove(this.loginPanel);
    }

    /**
     * Загружает и генерирует варианты вопросов.
     * @throws IOException все возможные исключения.
     */
    private void initQuestion() throws IOException {
        QuestionComponentImpl questionComponent = new QuestionComponentImpl();
        this.questions = questionComponent.generateVariant();
    }

    /**
     * Создает панели с вопросами.
     * @throws IOException все возможные исключения.
     */
    private void initQuestionsPanels() throws IOException {
        this.testPanel = Box.createVerticalBox();
        this.testPanel.add(Box.createVerticalStrut(10));
        JPanel timePanel = this.createTimePanel();
        this.testPanel.add(timePanel);
        this.questions.stream().forEachOrdered(question -> {
            try {
                Box box = this.createQuestionPanel(question);
                box.setVisible(false);
                this.testPanel.add(box);
                this.questionPanels.put(this.panelNumber++, box);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.panelNumber = 0;

        Box buttonPanel = this.createButtonPanel();
        this.testPanel.add(buttonPanel);
        this.testPanel.add(Box.createVerticalStrut(20));
        this.questionPanels.get(0).setVisible(true);

        super.setSize(650, 450);
        super.add(this.testPanel);
    }

    /**
     * @return панель со временем отсчета.
     */
    private JPanel createTimePanel() {
        JLabel timeLabel = new JLabel();
        timeLabel.setFont(new java.awt.Font("Times New Roman", 1, 18));
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(timeLabel);

        this.timer = new Thread() {
            @Override
            public void run() {
                Integer minutes = 0;
                Integer seconds = 30;
                try {
                    for (;;) {
                        timeLabel.setText("Осталось времени :  " + minutes + " : " + seconds);
                        Thread.sleep(1000);
                        seconds--;
                        if (seconds == 0) {
                            if (minutes != 0) {
                                minutes--;
                                seconds = 59;
                            } else {
                                finishTest();
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();
        return panel;
    }

    /**
     * Загружает все правильные ответы.
     * @throws IOException все возможные исключения.
     */
    private void initRightAnswer() throws IOException {
        AnswerComponentImpl answerComponent = new AnswerComponentImpl();
        this.rightAnswers = answerComponent.getRightAnswes()
                .stream()
                .collect(Collectors.toMap(RightAnswer::getQuestionId, RightAnswer::getRightVariantAnswer));
    }

    /**
     * Считает количество правильных ответов.
     */
    private void calculateRightAnswer() {
        this.userAnswers.keySet().stream()
                .forEachOrdered(i -> {
                    if (this.userAnswers.get(i).intValue() == this.rightAnswers.get(i).intValue()) {
                        ++this.currentRightAnswer;
                    }
                });
    }

    /**
     * @param question
     * @return главную панель с вопросом.
     * @throws IOException все возможные исключения.
     */
    private Box createQuestionPanel(Question question) throws IOException {
        Box box = Box.createVerticalBox();
        box.add(Box.createVerticalStrut(10));
        JPanel titlePanel = this.createTitlePanel("Вопрос # " + (this.panelNumber + 1));
        box.add(titlePanel);
        box.add(Box.createVerticalStrut(10));

        Box bodyBox = Box.createHorizontalBox();
        Box questionPanel = this.createQuestionBodyPanel(question);
        bodyBox.add(Box.createHorizontalStrut(30));
        bodyBox.add(questionPanel);

        Box answerPanel = this.createAnswerBodyPanel(question);
        bodyBox.add(Box.createHorizontalStrut(50));
        bodyBox.add(answerPanel);
        bodyBox.add(Box.createHorizontalStrut(30));
        box.add(bodyBox);
        box.add(Box.createVerticalStrut(30));

        return box;
    }

    /**
     * @param title текст заголовка.
     * @return панель с заголовком.
     */
    private JPanel createTitlePanel(String title) {
        JLabel label = new JLabel(title);
        label.setFont(new java.awt.Font("Times New Roman", 1, 20));
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(label);
        return panel;
    }

    /**
     * @param question информация о вопросе.
     * @return панель с текстом вопроса.
     * @throws IOException все возможные исключения.
     */
    private Box createQuestionBodyPanel(final Question question) throws IOException {
        Box box = Box.createVerticalBox();

        JLabel textField = new JLabel("<html>" + question.getQuestionText() + "</html>");
        box.add(textField);
        box.add(Box.createVerticalStrut(5));

        if (question.getImagePath() != null) {
            BufferedImage myPicture = ImageIO.read(Paths.get(question.getImagePath()).toFile());
            Image image = new ImageIcon(myPicture).getImage().getScaledInstance(300, 150, Image.SCALE_SMOOTH);
            JLabel picLabel = new JLabel(new ImageIcon(image));
            box.add(picLabel);
        }
        return box;
    }

    /**
     * @param question информация о вопросе.
     * @return панель с вариантами ответов.
     */
    private Box createAnswerBodyPanel(final Question question) {
        List<VariantAnswer> answers = question.getAnswers();
        final Integer questionId = question.getId();
        Box box = Box.createVerticalBox();
        ButtonGroup buttonGroup = new ButtonGroup();
        for (VariantAnswer answer : answers) {
            final Integer answerId = answer.getId();
            JCheckBox checkBox = new JCheckBox("<html>" + answer.getVariantAnswer() + "</html>");
            checkBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent itemEvent) {
                    if (checkBox.isSelected()) {
                        userAnswers.put(questionId, answerId);
                    }
                }
            });
            if (!checkBox.isSelected()) {
                this.userAnswers.put(questionId, 0);
            }
            buttonGroup.add(checkBox);
            box.add(checkBox);
            box.add(Box.createVerticalStrut(5));
        }
        return box;
    }

    /**
     * @return панель с кнопками.
     */
    private Box createButtonPanel() {
        Box box = Box.createHorizontalBox();
        JButton prevButton = new JButton("<< Предведущий");
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                prevQuestion();
            }
        });
        JButton finishButton = new JButton("Завершить тест");
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                finishTest();
            }
        });
        JButton nextButton = new JButton("Следующий >>");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                nextQuestion();
            }
        });
        box.add(Box.createHorizontalStrut(30));
        box.add(prevButton);
        box.add(Box.createHorizontalGlue());
        box.add(finishButton);
        box.add(Box.createHorizontalGlue());
        box.add(nextButton);
        box.add(Box.createHorizontalStrut(30));

        return box;
    }

    /**
     * Переключает на предведущий вопрос.
     */
    private void prevQuestion() {
        if (this.panelNumber > 0) {
            this.questionPanels.get(this.panelNumber).setVisible(false);
            this.questionPanels.get(--this.panelNumber).setVisible(true);
        }
    }

    /**
     * Переключает на следующий вопрос.
     */
    private void nextQuestion() {
        if (this.panelNumber < this.questionPanels.size() - 1) {
            this.questionPanels.get(this.panelNumber ).setVisible(false);
            this.questionPanels.get(++this.panelNumber).setVisible(true);
        }
    }

    private void initRezultPanel() {
        this.rezultPanel = Box.createVerticalBox();

        JLabel titleLabel = new JLabel("Результаты прохождения тестирования");
        titleLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        titleLabel.setFont(new java.awt.Font("Times New Roman", 1, 18));

        String statusMessage = "Тест не пройден. Попробуйте еще....";
        JLabel statusLabel = new JLabel();
        statusLabel.setFont(new java.awt.Font("Times New Roman", 1, 16));
        statusLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        statusLabel.setForeground(Color.red);
        if (this.currentRightAnswer > MAX_RIGHT_ANSWER - 1) {
            statusMessage = "Поздравляю!!! Тест успешно пройден...";
            statusLabel.setForeground(Color.green);
        }
        statusLabel.setText(statusMessage);

        String testingEvaluationText = this.currentRightAnswer
                + " из "
                + MAX_RIGHT_ANSWER
                + " ("
                + ((this.currentRightAnswer * 100) / MAX_RIGHT_ANSWER + "%")
                + ")";
        JLabel testingEvaluationLabel = new JLabel(testingEvaluationText);
        testingEvaluationLabel.setFont(new java.awt.Font("Times New Roman", 1, 16));
        testingEvaluationLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        JButton newTestButton = new JButton("Начать тест...");
        newTestButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        newTestButton.setMaximumSize(new Dimension(200, 50));
        newTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cleanRezultPanel();
                try {
                    initQuestionsPanels();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        this.rezultPanel.add(Box.createVerticalStrut(30));
        this.rezultPanel.add(titleLabel);
        this.rezultPanel.add(Box.createVerticalStrut(30));
        this.rezultPanel.add(statusLabel);
        this.rezultPanel.add(Box.createVerticalStrut(30));
        this.rezultPanel.add(testingEvaluationLabel);
        this.rezultPanel.add(Box.createVerticalStrut(30));
        this.rezultPanel.add(newTestButton);
        this.rezultPanel.add(Box.createVerticalStrut(30));

        super.setSize(500, 250);
        super.add(this.rezultPanel);
    }

    private void cleanTestPanel() {
        this.testPanel.setVisible(false);
        this.questionPanels.clear();
        this.panelNumber = 0;
        getContentPane().remove(this.testPanel);
    }

    private void cleanRezultPanel() {
        this.rezultPanel.setVisible(false);
        getContentPane().remove(this.rezultPanel);
    }

    private void finishTest() {
        cleanTestPanel();
        try {
            initRightAnswer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        calculateRightAnswer();
        initRezultPanel();
        cleanTestRezult();
        this.timer.interrupt();
    }

    private void cleanTestRezult() {
        this.currentRightAnswer = 0;
        this.userAnswers.clear();
    }

    private static String generatePassword() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Long time = null;
        try {
            Date dateWithoutTime = sdf.parse(sdf.format(new Date()));
            time = dateWithoutTime.getTime()/100000;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String[] evenDay = {"a", "e", "f", "i", "j", "d", "n", "o", "p", "s", "t"};
        String[] addDay = {"b", "u", "c", "g", "v", "d", "i", "h", "p", "s", "w"};

        String stringTime = time.toString();

        String[] tempArray = new String[stringTime.length()];
        for (int i = 0; i < tempArray.length; i++) {
            tempArray[i] = String.valueOf(stringTime.charAt(i));
        }

        StringBuilder stringBuilder = new StringBuilder();
        final Integer dayOfMonth = new GregorianCalendar().get(Calendar.DAY_OF_MONTH);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Arrays.stream(tempArray)
                .forEachOrdered(s -> {
                    Integer number = Integer.parseInt(s);
                    if (number % 2 == 0) {
                        if (dayOfMonth % 2 == 0) {
                            stringBuilder.append(evenDay[number] + number);
                        } else {
                            stringBuilder.append(addDay[number] + number);
                        }
                    } else {
                        if (dayOfMonth % 2 == 0) {
                            stringBuilder.append(evenDay[number].toUpperCase() + number);
                        } else {
                            stringBuilder.append(addDay[number].toUpperCase() + number);
                        }
                    }
                    if ((atomicInteger.incrementAndGet() * 2) == 4){
                        if(stringBuilder.length() < 17) {
                            stringBuilder.append("-");
                            atomicInteger.set(0);
                        }
                    }
                });
        return stringBuilder.toString();
    }

    /*private void setSizeAndLocation(Integer width, Integer height) {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = ((screenSize.height - height) / 2);
        int screenWidth = ((screenSize.width - width) / 2);
        super.setSize(width, height);
        super.setLocation(screenWidth, screenHeight);
    }*/
}
