package gameoflife;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Matriz {

    private final int row;
    private final int col;
    private HashMap<String, Object> matriz;

    public Matriz(int row, int col) {
        this.row = row;
        this.col = row;
        this.matriz = new HashMap<>();
    }

    public void agregar(int fila, int col, Object valor) {
        if (fila >= 0 && fila < this.row && col >= 0 && col < this.col) {
            String clave = fila + "," + col;
            this.matriz.put(clave, valor);
        }
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public Object getElemeto(int fila, int col) {
        String clave = fila + "," + col;
        if (this.matriz.containsKey(clave)) {
            return this.matriz.get(clave);
        }
        // si devuelve false es que no hay niguna celula iniciada en esa casilla
        return false;
    }

    public void eliminar(int fila, int col) {
        String clave = fila + "," + col;
        if (this.matriz.containsKey(clave)) {
            this.matriz.remove(clave);
        }

    }

    public List<Object> elementosAdyacentes(int fila, int col) {
        List<Object> adyacentes = new ArrayList<>();
        int[][] movimientos = {
            {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}
        };
        for (int[] dir : movimientos) {
            int nuevaFila = fila + dir[0];
            int nuevaCol = col + dir[1];
            if (nuevaFila >= 0 && nuevaFila < this.row && nuevaCol >= 0 && nuevaCol < this.col) {
                Object valor = (Object) getElemeto(nuevaFila, nuevaCol);
                // los elementos que se agreguen que sean False significa que están muertas las células
                adyacentes.add(valor);
            }

        }
        return adyacentes;
    }

}
