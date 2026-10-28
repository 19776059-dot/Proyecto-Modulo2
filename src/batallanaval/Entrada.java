package batallanaval;

import java.util.Scanner;

public class Entrada {

    public static final Scanner scanner = new Scanner(System.in);

    // --- VALIDACIÓN DE TEXTO ---
    public static String pedirTexto(String mensaje) {
        String texto = "";
        while (true) {
            System.out.print(mensaje);
            texto = scanner.nextLine();

            if (texto != null && !texto.trim().isEmpty()) {
                return texto.trim();
            }
            System.out.println(Constantes.RED + Constantes.BOLD + "❌ [ERROR] Debes escribir un nombre válido. No puedes presionar solo la barra espaciadora." + Constantes.RESET);
        }
    }

    public static int leerEntero(String mensaje, int min, int max) {
        int val = -1;
        while (true) {
            System.out.print(mensaje);
            try {
                val = Integer.parseInt(scanner.nextLine().trim());
                if (val >= min && val <= max) break;
            } catch (Exception ignored) {}
            System.out.println(Constantes.RED + Constantes.BOLD + "❌ Opción no válida. Elige un número entre " + min + " y " + max + "." + Constantes.RESET);
        }
        return val;
    }

    public static int[] pedirCoordenadas(char[][] tableroDisparos) {
        int fila = -1, col = -1;
        while (true) {
            System.out.print("🎯 Ingresa la coordenada de disparo (ej. A1, C5) o escribe 'SALIR' para abandonar: ");
            String entrada = scanner.nextLine().trim().toUpperCase();

            // Detecta si el jugador decide abandonar la partida
            if (entrada.equals("SALIR") || entrada.equals("EXIT") || entrada.equals("S")) {
                return null;
            }

            if (entrada.length() >= 2) {
                char letraFila = entrada.charAt(0);
                String numColStr = entrada.substring(1);

                if (letraFila >= 'A' && letraFila < ('A' + Constantes.FILAS)) {
                    try {
                        int numCol = Integer.parseInt(numColStr);
                        if (numCol >= 1 && numCol <= Constantes.COLUMNAS) {
                            fila = letraFila - 'A';
                            col = numCol - 1;

                            if (tableroDisparos[fila][col] != Constantes.AGUA) {
                                System.out.println(Constantes.YELLOW + Constantes.BOLD + "⚠️ Ya disparaste en esa casilla. Intenta otra coordenada." + Constantes.RESET);
                                continue;
                            }
                            break;
                        }
                    } catch (NumberFormatException ignored) {}
                }
            }
            System.out.println(Constantes.RED + Constantes.BOLD + "❌ Coordenada inválida. Utiliza letras A-" + (char)('A' + Constantes.FILAS - 1) + " y números 1-" + Constantes.COLUMNAS + ", o escribe 'SALIR'." + Constantes.RESET);
        }
        return new int[]{fila, col};
    }
}