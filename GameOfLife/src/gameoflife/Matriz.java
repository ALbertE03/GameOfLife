package gameoflife;
import java.util.HashMap;
import java.util.ArrayList;

public class Matriz{
    private int row;
    private int col;
    private HashMap<String,Object> matriz;

    public Matriz(int row, int col){
        this.row =row;
        this.col = row;
        this.matriz= new HashMap<>();
    }

    private void agregar(int fila, int col,Object valor){
        if (fila >=0 && fila<this.fila && col>=0 && col <this.col){
            String clave = fila +','+col;
            matriz.put(clave,valor);
        } 
        

    }
}