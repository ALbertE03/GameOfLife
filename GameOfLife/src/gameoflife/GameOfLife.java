package gameoflife;
import java.awt.*;
import javax.swing.*;
public class GameOfLife {

    public static void main(String[] args) {
        // faltaria interarlo con la matriz
        JFrame tablero = new JFrame("Tablero"+1+"x"+1);
        tablero.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tablero.setLayout(new GridLayout(10,10));
        for (int i = 0; i < 10*10; i++) {
            JPanel panel = new JPanel();
            panel.setBackground(Color.WHITE);
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            tablero.add(panel);
        }
        tablero.setSize(600,600);
        tablero.setVisible(true);
    }
}
