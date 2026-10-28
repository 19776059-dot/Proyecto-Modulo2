package batallanaval;

public class BatallaNaval {

    public static void main(String[] args) {
        mostrarPortada();
        
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = Entrada.leerEntero("Selecciona una opción (1-4): ", 1, 4);
            switch (opcion) {
                case 1:
                    ModosDeJuego.mostrarTutorial();
                    break;
                case 2:
                    ModosDeJuego.jugarVsCPU();
                    break;
                case 3:
                    ModosDeJuego.jugarRedLAN();
                    break;
                case 4:
                    System.out.println(Constantes.CYAN + Constantes.BOLD + "\n⚡ ¡Gracias por jugar a Batalla Naval! Saliendo del juego... ⚡\n" + Constantes.RESET);
                    salir = true;
                    System.exit(0);
                    break;
            }
        }
    }

    // --- PORTADA Y MENÚ ---
    public static void mostrarPortada() {
        System.out.println(Constantes.CYAN + Constantes.BOLD + "========================================================================");
        System.out.println(" ██████╗  █████╗ ████████╗ █████╗ ██╗     ██╗      █████╗ ");
        System.out.println(" ██╔══██╗██╔══██╗╚══██╔══╝██╔══██╗██║     ██║     ██╔══██╗");
        System.out.println(" ██████╔╝███████║   ██║   ███████║██║     ██║     ███████║");
        System.out.println(" ██╔══██╗██╔══██║   ██║   ██╔══██║██║     ██║     ██╔══██║");
        System.out.println(" ██████╔╝██║  ██║   ██║   ██║  ██║███████╗███████╗██║  ██║");
        System.out.println(" ╚═════╝ ╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝  ╚═╝" + Constantes.RESET);
        
        System.out.println(Constantes.PURPLE + Constantes.BOLD + " ███╗   ██╗ █████╗ ██╗   ██╗ █████╗ ██╗     ");
        System.out.println(" ████╗  ██║██╔══██╗██║   ██║██╔══██╗██║     ");
        System.out.println(" ██╔██╗ ██║███████║██║   ██║███████║██║     ");
        System.out.println(" ██║╚██╗██║██╔══██║╚██╗ ██╔╝██╔══██║██║     ");
        System.out.println(" ██║ ╚████║██║  ██║ ╚████╔╝ ██║  ██║███████╗");
        System.out.println(" ╚═╝  ╚═══╝╚═╝  ╚═╝  ╚═══╝  ╚═╝  ╚═╝╚══════╝" + Constantes.RESET);
        System.out.println(Constantes.CYAN + Constantes.BOLD + "========================================================================" + Constantes.RESET);
        System.out.println(Constantes.YELLOW + Constantes.BOLD + "               >>> Presiona ENTER para ir al menú... <<<" + Constantes.RESET);
        Entrada.scanner.nextLine();
    }

    public static void mostrarMenu() {
        System.out.println(Constantes.PURPLE + Constantes.BOLD + "\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                    MENÚ DE OPERACIONES                   ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.println("║  1. 📖 Tutorial de cómo jugar                            ║");
        System.out.println("║  2. 👤 Jugador vs Computadora (CPU)                      ║");
        System.out.println("║  3. ⚔️ Jugador vs Jugador (Red LAN / Cable)              ║");
        System.out.println("║  4. 🚪 Salir del juego                                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝" + Constantes.RESET);
    }
}