package View.Custom.TypeButtons;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextArea;
import app.OnlinePanel.CharFilter;
import javax.swing.text.AbstractDocument;

public class TexteBox extends JTextArea {
    public TexteBox(int rows, int columns, CharFilter filtre) {
        super(rows, columns);
        setBackground(new Color(255, 228, 181));
        setFont(new Font("Tahoma", Font.PLAIN, 47));
        ((AbstractDocument) getDocument()).setDocumentFilter(filtre);   
    }
}
