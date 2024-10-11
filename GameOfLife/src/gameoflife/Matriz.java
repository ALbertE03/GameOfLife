package gameoflife;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class Matriz {

    private int row;
    private int col;
    private HashMap<String, Object> matriz;

    public Matriz(int row, int col) {
        this.row = row;
        this.col = row;
        this.matriz = new HashMap<>();
    }

    public void agregar(int fila, int col, Object valor) {
        if (fila >= 0 && fila < this.row && col >= 0 && col < this.col) {
            String clave = fila + "," + col;
            matriz.put(clave, valor);
        }
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public Object getElemeto(int fila, int col) {
        // aqui necesito comprobar cuando una clave no está en el diccionario
        // si no está devuelve 0, o False 🤔
        // xq el get a una clave que no existe debe dar excepción a si e en la pitón
        String clave = fila + "," + col;
        return this.matriz.get(clave);
    }

    public void eliminar(int fila, int col) {
        // comprobar lo mismo aqui de las claves ...
        String clave = fila + "," + col;
        this.matriz.remove(clave);
    }

    // pinga que cantidad de errores de tipos de datos 😂 puto java
    // casteos gotys
    public List<Object> elementosAdyacentes(int fila, int col) {
        List<Object> adyacentes = new ArrayList<>();
        int[][] movimientos = {
            {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}
        };
        // falta tener en cuenta cuando un movimiennto se salga del tamaño de la matriz
        for (int[] dir : movimientos) {
            int nuevaFila = fila + dir[0];
            int nuevaCol = col + dir[1];
            Object valor = (Object) getElemeto(nuevaFila, nuevaCol);
            // los elementos que se agreguen que sean 0 significa que están muertas las células
            adyacentes.add(valor);
        }
        return adyacentes;
    }

}
