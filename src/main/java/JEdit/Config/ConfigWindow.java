package JEdit.Config;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
public class ConfigWindow extends JFrame {
    int stage;
    Config config = Config.INSTANCE;


    /* Components */
    String username, theme;

    public ConfigWindow() {
        FlatDarculaLaf.setup();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setVisible(true);

        x();

    }

    public void render() {
        config.saveBoolean("no",false);
        switch (stage) {
            case 0 -> x();
            case 1 -> y();
            case 2 -> z();
            default -> System.exit(0);
        }
    }

    public void x() {

        setSize(445, 230);
        setTitle("Name");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        setContentPane(contentPane);

        JLabel headingLabel = new JLabel("Enter a username");

        headingLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPane.add(headingLabel, gbc);

        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 1;
        contentPane.add(textField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton cancelButton = new JButton("Cancel");
        JButton nextButton = new JButton("Next >");

        cancelButton.addActionListener(e -> System.exit(0));

        nextButton.addActionListener(e -> {

            username = textField.getText();
            stage = 1;
            render();
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(nextButton);
        gbc.gridy = 2;
        contentPane.add(buttonPanel, gbc);
    }

    public void y() {

        getContentPane().removeAll();
        revalidate();

        setSize(460, 220);
        GridBagConstraints gbc = new GridBagConstraints();
        setTitle("Theme");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        setContentPane(contentPane);

        JLabel headingLabel = new JLabel("Select a theme");

        headingLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        contentPane.add(headingLabel, gbc);

        JButton nextButton = new JButton("Next >");
        nextButton.setEnabled(false);

        JRadioButton[] radioButtons = new JRadioButton[3];
        radioButtons[0] = new JRadioButton("Light");
        radioButtons[1] = new JRadioButton("Dark");
        radioButtons[2] = new JRadioButton("System");

        ButtonGroup buttonGroup = new ButtonGroup();
        for (int i = 0; i < radioButtons.length; i++) {
            buttonGroup.add(radioButtons[i]);
            radioButtons[i].addActionListener(e -> nextButton.setEnabled(true));
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.gridx = i;
            contentPane.add(radioButtons[i], gbc);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton(" < Back");
        JButton cancelButton = new JButton("Cancel");

        backButton.addActionListener(e -> {
            stage = 0;
            render();
        });

        cancelButton.addActionListener(e -> {
            System.exit(1);
        });

        nextButton.addActionListener(e -> {

            if (radioButtons[0].isSelected()) {
                theme = "Light";
            } else if (radioButtons[1].isSelected()) {
                theme = "Dark";
            } else if (radioButtons[2].isSelected()) {
                theme = "System";
            }
            stage = 2;
            render();
        });

        buttonPanel.add(backButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(nextButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        contentPane.add(buttonPanel, gbc);

        revalidate();
    }

    public void z() {

        getContentPane().removeAll();
        revalidate();

        setSize(500, 245);
        GridBagConstraints gbc = new GridBagConstraints();
        setTitle("Style");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        setContentPane(contentPane);

        JLabel headingLabel = new JLabel("Select a style");

        headingLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        contentPane.add(headingLabel, gbc);

        JLabel fontSizeLabel = new JLabel("Font Size:");
        JComboBox<Integer> fontSizeDropdown = new JComboBox<>();
        for (int i = 1; i <= 36; i++) {
            fontSizeDropdown.addItem(i);
        }
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        contentPane.add(fontSizeLabel, gbc);
        gbc.gridx = 1;
        contentPane.add(fontSizeDropdown, gbc);

        JLabel fontStyleLabel = new JLabel("Font Style:");
        JComboBox<String> fontStyleDropdown = new JComboBox<>(new String[]{"Regular", "Bold", "Italic"});
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        contentPane.add(fontStyleLabel, gbc);
        gbc.gridx = 1;
        contentPane.add(fontStyleDropdown, gbc);

        JButton backButton = new JButton(" < Back");
        JButton cancelButton = new JButton("Cancel");
        JButton finishButton = new JButton("Finish");

        backButton.addActionListener(e -> {
            stage = 1;
            render();
        });

        cancelButton.addActionListener(e -> {
            System.exit(1);
        });

        finishButton.addActionListener(e -> {


            if(isNull(username,theme, Objects.requireNonNull(fontStyleDropdown.getSelectedItem()).toString())) {
                System.exit(0);
            }

            config.saveString("username", username);
            config.saveString("theme", theme);
            config.saveString("fontStyle", fontStyleDropdown.getSelectedItem().toString());
            config.saveInt("fontSize", fontSizeDropdown.getSelectedIndex());

            System.exit(0);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(finishButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        contentPane.add(buttonPanel, gbc);

        revalidate();
    }
    public boolean isNull(String ... s){
        for(String string : s){
            if(string == null) return true;
        }
        return false;
    }
}