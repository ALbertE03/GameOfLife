package gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GameOfLife {

    private static final int FILAS = 60;
    private static final int COLUMNAS = 60;
    private static Matriz matriz;
    private static JPanel[][] paneles;
    private static Color colorCelula = Color.BLACK; // Color por defecto de la célula

    public static void main(String[] args) {
        matriz = new Matriz(FILAS, COLUMNAS);
        paneles = new JPanel[FILAS][COLUMNAS];

        // Inicializar JFrame
        JFrame tablero = new JFrame("Game of Life");
        tablero.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tablero.setLayout(new BorderLayout());

        // Crear panel para el juego
        JPanel panelJuego = new JPanel();
        panelJuego.setLayout(new GridLayout(FILAS, COLUMNAS));

        // Crear paneles
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                JPanel panel = new JPanel();
                panel.setBackground(Color.WHITE);
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                int fila = i;
                int col = j;

                // Añadir MouseListener para pintar celulas
                panel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (panel.getBackground() == Color.WHITE) {
                            panel.setBackground(colorCelula); // celula viva con color seleccionado
                            matriz.agregar(fila, col, true);
                        } else {
                            panel.setBackground(Color.WHITE); // se muere
                            matriz.eliminar(fila, col);
                        }
                    }
                });

                paneles[i][j] = panel;
                panelJuego.add(panel);
            }
        }

        tablero.add(panelJuego, BorderLayout.CENTER);

        // Botton para seleccionar color
        JButton seleccionarColor = new JButton("Seleccionar Color");
        seleccionarColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color nuevoColor = JColorChooser.showDialog(tablero, "Seleccionar Color de Célula", colorCelula);
                if (nuevoColor != null) {
                    colorCelula = nuevoColor; // Actualiza el color seleccionado
                }
            }
        });

        // Boton para iniciar el juego
        JButton iniciarJuego = new JButton("Iniciar Juego");
        iniciarJuego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Timer timer = new Timer(500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actualizarJuego();
                        dibujarTablero();
                    }
                });
                timer.start();
            }
        });

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(seleccionarColor);
        panelBotones.add(iniciarJuego);

        tablero.add(panelBotones, BorderLayout.SOUTH);

        tablero.setSize(600, 600);
        tablero.setVisible(true);
    }

    private static void actualizarJuego() {
        // Lógica para actualizar el estado de las celulas segun las reglas del Juego de la Vida
        Matriz nuevaMatriz = new Matriz(FILAS, COLUMNAS);

        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                Object celulaActual = matriz.getElemeto(i, j);
                List<Object> adyacentes = matriz.elementosAdyacentes(i, j);

                int vivas = 0;
                for (Object adyacente : adyacentes) {
                    if (adyacente instanceof Boolean && (Boolean) adyacente) {
                        vivas++;
                    }
                }

                boolean nuevaCelula;
                if (celulaActual instanceof Boolean && (Boolean) celulaActual) {
                    nuevaCelula = (vivas == 2 || vivas == 3); // Sobrevive
                } else {
                    nuevaCelula = (vivas == 3); // Nace
                }

                nuevaMatriz.agregar(i, j, nuevaCelula);
            }
        }

        matriz = nuevaMatriz; // Actualiza la matriz original
    }

    private static void dibujarTablero() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                Object celula = matriz.getElemeto(i, j);
                if (celula instanceof Boolean && (Boolean) celula) {
                    paneles[i][j].setBackground(colorCelula); // Célula viva con color seleccionado
                } else {
                    paneles[i][j].setBackground(Color.WHITE); // Célula muerta
                }
            }
        }
    }
}
