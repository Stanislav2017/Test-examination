import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Фрейм с генератором пароля. */
public class KeyGenerator extends JFrame {
    public static void main(String[] args) {
        new KeyGenerator();
    }

    /**
     * Панель для генерации пароля. */
    private Box keyGeneratorPanel;

    /**
     * Ширина. */
    private static final Integer WIDTH = 300;

    /**
     * Высота. */
    private static final Integer HEIGHT = 200;

    private KeyGenerator() {
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        super.setTitle("Key generator");

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = ((screenSize.height - HEIGHT) / 2);
        int screenWidth = ((screenSize.width - WIDTH) / 2);
        super.setSize(WIDTH, HEIGHT);
        super.setLocation(screenWidth, screenHeight);

        initKeyGenaratorPanel();

        super.setVisible(true);
    }

    /**
     * Создание панели генерации пароля.
     */
    private void initKeyGenaratorPanel() {
        this.keyGeneratorPanel = Box.createVerticalBox();

        JLabel keyTitleLabel = new JLabel("Key Generator");
        keyTitleLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        keyTitleLabel.setFont(new java.awt.Font("Times New Roman", 1, 18));

        JTextField keyTextField = new JTextField(18);
        keyTextField.setHorizontalAlignment(JTextField.CENTER);
        keyTextField.setEditable(false);
        keyTextField.setFont(new java.awt.Font("Times New Roman", 1, 14));
        JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        textFieldPanel.add(keyTextField);

        JButton keyButton = new JButton("Generate key");
        keyButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        keyButton.setMaximumSize(new Dimension(260, 50));
        keyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String key = generatePassword();
                keyTextField.setText(key);
            }
        });

        this.keyGeneratorPanel.add(Box.createVerticalStrut(20));
        this.keyGeneratorPanel.add(keyTitleLabel);
        this.keyGeneratorPanel.add(Box.createVerticalStrut(40));
        this.keyGeneratorPanel.add(keyButton);
        this.keyGeneratorPanel.add(Box.createVerticalStrut(40));
        this.keyGeneratorPanel.add(textFieldPanel);
        this.keyGeneratorPanel.add(Box.createVerticalStrut(20));
        super.add(this.keyGeneratorPanel);
    }

    /**
     * @return сгенерированный пароль.
     */
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
}
