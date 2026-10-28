package batallanaval;

import java.util.Random;

public class Tablero {

    private static final Random random = new Random();

    public static char[][] crearTablero() {
        char[][] t = new char[Constantes.FILAS][Constantes.COLUMNAS];
        for (int i = 0; i < Constantes.FILAS; i++) {
            for (int j = 0; j < Constantes.COLUMNAS; j++) {
                t[i][j] = Constantes.AGUA;
            }
        }
        return t;
    }

    public static int totalCasillasBarco() {
        int total = 0;
        for (int t : Constantes.TAMAÑOS_BARCOS) total += t;
        return total;
    }

    public static void colocarBarcosAleatorio(char[][] tablero) {
        for (int tam : Constantes.TAMAÑOS_BARCOS) {
            boolean colocado = false;
            while (!colocado) {
                int f = random.nextInt(Constantes.FILAS);
                int c = random.nextInt(Constantes.COLUMNAS);
                boolean horizontal = random.nextBoolean();

                if (sePuedeColocar(tablero, f, c, tam, horizontal)) {
                    for (int i = 0; i < tam; i++) {
                        if (horizontal) tablero[f][c + i] = Constantes.BARCO;
                        else tablero[f + i][c] = Constantes.BARCO;
                    }
                    colocado = true;
                }
            }
        }
    }

    public static boolean sePuedeColocar(char[][] tablero, int f, int c, int tam, boolean horizontal) {
        if (horizontal) {
            if (c + tam > Constantes.COLUMNAS) return false;
            for (int i = 0; i < tam; i++) {
                if (tablero[f][c + i] != Constantes.AGUA) return false;
            }
        } else {
            if (f + tam > Constantes.FILAS) return false;
            for (int i = 0; i < tam; i++) {
                if (tablero[f + i][c] != Constantes.AGUA) return false;
            }
        }
        return true;
    }

    public static void mostrarTableros(char[][] misBarcos, char[][] misDisparos, String nombreJugador) {
        System.out.println("\n  " + Constantes.BOLD + Constantes.WHITE + "TU TABLERO (" + nombreJugador + ")" + Constantes.RESET + "                " + Constantes.BOLD + Constantes.WHITE + "TABLERO DE DISPAROS" + Constantes.RESET);
        
        System.out.print("  ");
        for (int j = 1; j <= Constantes.COLUMNAS; j++) System.out.print(Constantes.CYAN + j + " " + Constantes.RESET);
        System.out.print("         ");
        System.out.print("  ");
        for (int j = 1; j <= Constantes.COLUMNAS; j++) System.out.print(Constantes.CYAN + j + " " + Constantes.RESET);
        System.out.println();

        for (int i = 0; i < Constantes.FILAS; i++) {
            char letraFila = (char) ('A' + i);
            
            // Tablero propio
            System.out.print(Constantes.CYAN + letraFila + " " + Constantes.RESET);
            for (int j = 0; j < Constantes.COLUMNAS; j++) {
                char cell = misBarcos[i][j];
                if (cell == Constantes.AGUA) System.out.print(Constantes.BLUE + cell + " " + Constantes.RESET);
                else if (cell == Constantes.BARCO) System.out.print(Constantes.GREEN + Constantes.BOLD + cell + " " + Constantes.RESET);
                else if (cell == Constantes.IMPACTO) System.out.print(Constantes.RED + Constantes.BOLD + cell + " " + Constantes.RESET);
                else System.out.print(cell + " ");
            }

            System.out.print("     ");

            // Tablero de disparos
            System.out.print(Constantes.CYAN + letraFila + " " + Constantes.RESET);
            for (int j = 0; j < Constantes.COLUMNAS; j++) {
                char cell = misDisparos[i][j];
                if (cell == Constantes.AGUA) System.out.print(Constantes.BLUE + cell + " " + Constantes.RESET);
                else if (cell == Constantes.IMPACTO) System.out.print(Constantes.RED + Constantes.BOLD + cell + " " + Constantes.RESET);
                else if (cell == Constantes.FALLO) System.out.print(Constantes.YELLOW + cell + " " + Constantes.RESET);
                else System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}