package gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GameOfLife {

    private static final int FILAS = 40;
    private static final int COLUMNAS = 40;
    private static Matriz matriz;
    private static JPanel[][] paneles;
    private static Color colorCelula = Color.BLACK; // Color por defecto de la célula
    private static Timer timer; // Timer for game updates

    public static void main(String[] args) {
        matriz = new Matriz(FILAS, COLUMNAS);
        paneles = new JPanel[FILAS][COLUMNAS];

        // Inicializar JFrame
        JFrame tablero = new JFrame("Game of Life");
        tablero.setExtendedState(JFrame.MAXIMIZED_BOTH);
        tablero.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tablero.setLayout(new BorderLayout());

        CrearPaneles(tablero);
        JButton iniciarJuego = new JButton("Iniciar Juego");
        IniciarJuego(iniciarJuego);

        // Botón para seleccionar color
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

        // botón para pausar el juego
        JButton pausarJuego = new JButton("Pausar Juego");
        pausarJuego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer != null && timer.isRunning()) {
                    timer.stop(); // pausa el juego
                    
                } 
            }
        });

        // botón para reanudar el juego
        JButton reanudarJuego = new JButton("Reanudar Juego");
        reanudarJuego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer != null && !timer.isRunning()) {
                    timer.start(); // lo reanuda
                    
                }
            }
        });

        // botón para reiniciar el juego
        JButton reiniciarJuego = new JButton("Reiniciar Juego");
        reiniciarJuego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rebootJuego();
            }
        });

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(seleccionarColor);
        panelBotones.add(iniciarJuego);
        panelBotones.add(pausarJuego);
        panelBotones.add(reanudarJuego); 
        panelBotones.add(reiniciarJuego);
        
        tablero.add(panelBotones, BorderLayout.SOUTH);
        tablero.setVisible(true);
    }

    private static void rebootJuego() {
        matriz = new Matriz(FILAS, COLUMNAS); // Reinicia la matriz
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                paneles[i][j].setBackground(Color.WHITE); 
            }
        }
        
        if (timer != null && timer.isRunning()) { 
            timer.stop();
           
        }
    }

    public static void IniciarJuego(JButton iniciarJuego) {
        iniciarJuego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer == null || !timer.isRunning()) { 
                    timer = new Timer(500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            actualizarJuego();
                            dibujarTablero();
                        }
                    });
                    timer.start(); // inicia el juego
                   
                }
            }
        });
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

    public static void CrearPaneles(JFrame tablero) {
      JPanel panelJuego = new JPanel();
      panelJuego.setLayout(new GridLayout(FILAS, COLUMNAS));

      for (int i = 0; i < FILAS; i++) {

          for (int j = 0; j < COLUMNAS; j++) {
              JPanel panel = new JPanel();
              panel.setBackground(Color.WHITE);
              panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
              int fila = i;
              int col = j;

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
  }
}