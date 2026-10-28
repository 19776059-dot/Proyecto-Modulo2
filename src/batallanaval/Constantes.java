package batallanaval;

public class Constantes {

    // Estilos y Colores ANSI (Alta Intensidad y Negrita)
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String UNDERLINE = "\u001B[4m";

    public static final String RED = "\u001B[91m";
    public static final String GREEN = "\u001B[92m";
    public static final String YELLOW = "\u001B[93m";
    public static final String BLUE = "\u001B[94m";
    public static final String PURPLE = "\u001B[95m";
    public static final String CYAN = "\u001B[96m";
    public static final String WHITE = "\u001B[97m";

    // Símbolos del tablero
    public static final char AGUA = '~';
    public static final char BARCO = 'S';
    public static final char IMPACTO = 'X';
    public static final char FALLO = 'O';

    // Dimensiones y Flota
    public static final int FILAS = 8;
    public static final int COLUMNAS = 8;
    public static final int[] TAMAÑOS_BARCOS = {4, 3, 3, 2}; // Flota de 4 barcos (12 casillas en total)
}