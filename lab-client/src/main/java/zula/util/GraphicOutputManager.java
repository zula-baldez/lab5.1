package zula.util;

import zula.common.util.OutputManager;

import javax.swing.JTextArea;
import java.io.OutputStreamWriter;
import java.io.Serializable;

public class GraphicOutputManager extends OutputManager { //нужен, чтобы использовать старые конструкции и вывод в консоль заменить на вывод на UI
    private JTextArea jTextArea;
    public GraphicOutputManager() {
        super(new OutputStreamWriter(System.out));
    }

    public void setJTextField(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }
    @Override
    public void write(Serializable arg) {
        jTextArea.setText(jTextArea.getText() +'\n'+ arg.toString());
    }
}
