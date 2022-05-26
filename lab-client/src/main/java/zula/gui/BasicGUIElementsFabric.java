package zula.gui;

import zula.util.Constants;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public final class BasicGUIElementsFabric {
    private BasicGUIElementsFabric() {
        throw new Error();
    }
    public static JLabel createBasicLabel(String text) {
        JLabel basicLabel = new JLabel(text);
        basicLabel.setFont(Constants.mainFont);
        return basicLabel;
    }

    public static JTextField createBasicJTextField() {
        JTextField jTextField = new JTextField();
        jTextField.setFont(Constants.mainFont);
        return jTextField;
    }
    public static JComboBox<String> createBasicComboBox(String[] elements) {
        JComboBox<String> jComboBox = new JComboBox<>(elements);
        jComboBox.setFont(Constants.mainFont);
        return jComboBox;
    }
    public static JButton createBasicButton(String text) {
        JButton jButton = new JButton();
        jButton.setFont(Constants.mainFont);
        jButton.setText(text);
        return jButton;
    }
}
