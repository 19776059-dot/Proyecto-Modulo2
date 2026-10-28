
package batallanaval;

import java.util.Random;

public class ModosDeJuego {

    private static final Random random = new Random();

    // 1. TUTORIAL DE CÓMO JUGAR
    public static void mostrarTutorial() {
        System.out.println(Constantes.CYAN + Constantes.BOLD + "\n==========================================================");
        System.out.println("            📖 GUÍA Y TUTORIAL DE BATALLA NAVAL           ");
        System.out.println("==========================================================" + Constantes.RESET);
        
        System.out.println(Constantes.YELLOW + Constantes.BOLD + "\n1. OBJETIVO DEL JUEGO:" + Constantes.RESET);
        System.out.println("   Localizar y hundir toda la flota enemiga.");

        System.out.println(Constantes.YELLOW + Constantes.BOLD + "\n2. LECTURA Y SIMBOLOGÍA DEL TABLERO:" + Constantes.RESET);
        System.out.println("   " + Constantes.BLUE + Constantes.AGUA + " (Agua)" + Constantes.RESET + "   : Casilla de océano sin explorar.");
        System.out.println("   " + Constantes.GREEN + Constantes.BOLD + Constantes.BARCO + " (Barco)" + Constantes.RESET + "  : Parte de la ubicación de tus barcos.");
        System.out.println("   " + Constantes.RED + Constantes.BOLD + Constantes.IMPACTO + " (Impacto)" + Constantes.RESET + ": Fuego directo que alcanzó un barco.");
        System.out.println("   " + Constantes.YELLOW + Constantes.FALLO + " (Fallo)" + Constantes.RESET + "  : Disparo que cayó al agua.");

        System.out.println(Constantes.YELLOW + Constantes.BOLD + "\n3. CÓMO REALIZAR UN DISPARO O SALIR:" + Constantes.RESET);
        System.out.println("   Ingresa Letra + Número (Ej: A1, D4).");
        System.out.println("   * Puedes escribir 'SALIR' en tu turno para abandonar la partida.");

        System.out.println(Constantes.YELLOW + Constantes.BOLD + "\n4. COMPOSICIÓN DE LA FLOTA (4 BARCOS / 12 CASILLAS TOTALES):" + Constantes.RESET);
        System.out.println("   🚢 1 Portaaviones (4 casillas)");
        System.out.println("   ⚓ 1 Acorazado    (3 casillas)");
        System.out.println("   🛥️ 1 Submarino    (3 casillas)");
        System.out.println("   ⛵ 1 Lancha       (2 casillas)");

        System.out.println(Constantes.CYAN + Constantes.BOLD + "\n==========================================================" + Constantes.RESET);
        System.out.println(Constantes.YELLOW + Constantes.BOLD + "   >>> Presiona ENTER para regresar al menú... <<<" + Constantes.RESET);
        Entrada.scanner.nextLine();
    }

    // 2. JUGADOR VS COMPUTADORA
    public static void jugarVsCPU() {
        System.out.println(Constantes.CYAN + Constantes.BOLD + "\n--- MODO 1 JUGADOR VS CPU ---" + Constantes.RESET);
        String nombreJugador = Entrada.pedirTexto("👉 Ingresa el nombre del Jugador");
        
        // Si el usuario escribió SALIR al ingresar su nombre
        if (nombreJugador == null) {
            System.out.println(Constantes.YELLOW + Constantes.BOLD + "\n🏳️ Partida cancelada. Regresando al menú principal..." + Constantes.RESET);
            return;
        }

        String nombreCPU = "CPU-Bot";

        char[][] tBarcosP1 = Tablero.crearTablero();
        char[][] tDisparosP1 = Tablero.crearTablero();
        char[][] tBarcosCPU = Tablero.crearTablero();
        char[][] tDisparosCPU = Tablero.crearTablero();

        Tablero.colocarBarcosAleatorio(tBarcosP1);
        Tablero.colocarBarcosAleatorio(tBarcosCPU);

        int barcosP1 = Tablero.totalCasillasBarco();
        int barcosCPU = Tablero.totalCasillasBarco();

        boolean turnoP1 = true;
        while (barcosP1 > 0 && barcosCPU > 0) {
            if (turnoP1) {
                System.out.println(Constantes.YELLOW + Constantes.BOLD + "\n=== TURNO DE: " + nombreJugador.toUpperCase() + " ===" + Constantes.RESET);
                Tablero.mostrarTableros(tBarcosP1, tDisparosP1, nombreJugador);
                
                int[] coord = Entrada.pedirCoordenadas(tDisparosP1);
                
                // Si el jugador escribió SALIR en su turno
                if (coord == null) {
                    System.out.println(Constantes.YELLOW + Constantes.BOLD + "\n🏳️ Has abandonado la partida. Regresando al menú principal..." + Constantes.RESET);
                    return;
                }

                int f = coord[0], c = coord[1];

                if (tBarcosCPU[f][c] == Constantes.BARCO) {
                    tDisparosP1[f][c] = Constantes.IMPACTO;
                    tBarcosCPU[f][c] = Constantes.IMPACTO;
                    barcosCPU--;
                    System.out.println(Constantes.RED + Constantes.BOLD + "\n💥 ¡BOOM! ¡Fuego directo! Has impactado un barco enemigo. 💥" + Constantes.RESET);
                    Sonido.reproducirImpactoEnemigo();
                } else {
                    tDisparosP1[f][c] = Constantes.FALLO;
                    System.out.println(Constantes.BLUE + Constantes.BOLD + "\n💦 ¡Agua! El proyectil cayó al mar." + Constantes.RESET);
                    Sonido.reproducirAgua();
                }
            } else {
                System.out.println(Constantes.PURPLE + Constantes.BOLD + "\n=== TURNO DE " + nombreCPU + " ===" + Constantes.RESET);
                int f, c;
                do {
                    f = random.nextInt(Constantes.FILAS);
                    c = random.nextInt(Constantes.COLUMNAS);
                } while (tDisparosCPU[f][c] != Constantes.AGUA);

                System.out.println("La CPU dispara en: " + (char)('A' + f) + (c + 1));
                if (tBarcosP1[f][c] == Constantes.BARCO) {
                    tDisparosCPU[f][c] = Constantes.IMPACTO;
                    tBarcosP1[f][c] = Constantes.IMPACTO;
                    barcosP1--;
                    System.out.println(Constantes.RED + Constantes.BOLD + "💥 ¡ALERTA! La CPU ha alcanzado uno de tus barcos. 💥" + Constantes.RESET);
                    Sonido.reproducirImpactoRecibido();
                } else {
                    tDisparosCPU[f][c] = Constantes.FALLO;
                    tBarcosP1[f][c] = Constantes.FALLO;
                    System.out.println(Constantes.BLUE + Constantes.BOLD + "💦 La CPU ha fallado el tiro." + Constantes.RESET);
                    Sonido.reproducirAgua();
                }
            }
            turnoP1 = !turnoP1;
        }

        if (barcosCPU == 0) {
            System.out.println(Constantes.GREEN + Constantes.BOLD + "\n🏆 ¡VICTORIA ABSOLUTA, " + nombreJugador.toUpperCase() + "! Destruiste la flota enemiga. 🏆" + Constantes.RESET);
        } else if (barcosP1 == 0) {
            System.out.println(Constantes.RED + Constantes.BOLD + "\n💀 ¡DERROTA! La CPU ha dominado el océano. 💀" + Constantes.RESET);
        }
    }

    // 3. JUGADOR VS JUGADOR (RED LAN POR CABLE)
    public static void jugarRedLAN() {
        System.out.println(Constantes.CYAN + Constantes.BOLD + "\n--- MODO 2 JUGADORES (RED LAN) ---" + Constantes.RESET);
        System.out.println("1. Crear sala (Servidor / Host)");
        System.out.println("2. Unirse a sala (Cliente / Invitado)");
        int opcion = Entrada.leerEntero("Selecciona rol (1-2): ", 1, 2);

        Conexion conexion = new Conexion();
        boolean esHost = (opcion == 1);
        boolean esMiTurno = false;
        String miNombre = "";
        String nombreRival = "";

        if (esHost) {
            miNombre = Entrada.pedirTexto("👉 Tu nombre (Host)");
            if (miNombre == null) {
                System.out.println(Constantes.YELLOW + "🏳️ Cancelado. Regresando al menú..." + Constantes.RESET);
                return;
            }

            int puerto = 5000;
            if (!conexion.iniciarServidor(puerto)) return;
            
            conexion.enviarMensaje(miNombre);
            nombreRival = conexion.recibirMensaje();
            esMiTurno = true;
        } else {
            System.out.print("👉 Ingresa la IP de la otra PC (ej: 192.168.1.1): ");
            String ip = Entrada.scanner.nextLine().trim();
            if (ip.equalsIgnoreCase("SALIR")) {
                System.out.println(Constantes.YELLOW + "🏳️ Cancelado. Regresando al menú..." + Constantes.RESET);
                return;
            }

            miNombre = Entrada.pedirTexto("👉 Tu nombre (Invitado)");
            if (miNombre == null) {
                System.out.println(Constantes.YELLOW + "🏳️ Cancelado. Regresando al menú..." + Constantes.RESET);
                return;
            }

            int puerto = 5000;
            if (!conexion.conectarAHost(ip, puerto)) return;

            nombreRival = conexion.recibirMensaje();
            conexion.enviarMensaje(miNombre);
            esMiTurno = false;
        }

        System.out.println(Constantes.GREEN + Constantes.BOLD + "\n⚔️ ¡COMBATE INICIADO! " + miNombre + " VS " + nombreRival + " ⚔️" + Constantes.RESET);

        char[][] tBarcos = Tablero.crearTablero();
        char[][] tDisparos = Tablero.crearTablero();
        Tablero.colocarBarcosAleatorio(tBarcos);

        int misBarcosRestantes = Tablero.totalCasillasBarco();
        int rivalBarcosRestantes = Tablero.totalCasillasBarco();

        while (misBarcosRestantes > 0 && rivalBarcosRestantes > 0) {
            if (esMiTurno) {
                System.out.println(Constantes.YELLOW + Constantes.BOLD + "\n=== TU TURNO (" + miNombre.toUpperCase() + ") ===" + Constantes.RESET);
                Tablero.mostrarTableros(tBarcos, tDisparos, miNombre);

                int[] coord = Entrada.pedirCoordenadas(tDisparos);
                
                // Si el jugador escribe SALIR en LAN
                if (coord == null) {
                    System.out.println(Constantes.YELLOW + Constantes.BOLD + "\n🏳️ Has abandonado la partida. Regresando al menú..." + Constantes.RESET);
                    conexion.enviarMensaje("ABANDONO");
                    conexion.cerrar();
                    return;
                }

                int f = coord[0], c = coord[1];
                conexion.enviarMensaje(f + "," + c);

                String respuesta = conexion.recibirMensaje();
                if ("IMPACTO".equals(respuesta)) {
                    tDisparos[f][c] = Constantes.IMPACTO;
                    rivalBarcosRestantes--;
                    System.out.println(Constantes.RED + Constantes.BOLD + "\n💥 ¡BOOM! ¡Impactaste un barco de " + nombreRival + "! 💥" + Constantes.RESET);
                    Sonido.reproducirImpactoEnemigo();
                } else {
                    tDisparos[f][c] = Constantes.FALLO;
                    System.out.println(Constantes.BLUE + Constantes.BOLD + "\n💦 Disparo al agua." + Constantes.RESET);
                    Sonido.reproducirAgua();
                }
                esMiTurno = false;

            } else {
                System.out.println(Constantes.PURPLE + Constantes.BOLD + "\n⌛ Esperando a que " + nombreRival + " realice su disparo..." + Constantes.RESET);
                
                String disparoRecibido = conexion.recibirMensaje();
                
                // Si el rival se desconecta o envía señal de abandono
                if (disparoRecibido == null || "ABANDONO".equals(disparoRecibido)) {
                    System.out.println(Constantes.GREEN + Constantes.BOLD + "\n🏳️ " + nombreRival + " ha abandonado la partida. ¡Has ganado por abandono!" + Constantes.RESET);
                    conexion.cerrar();
                    return;
                }

                String[] partes = disparoRecibido.split(",");
                int f = Integer.parseInt(partes[0]);
                int c = Integer.parseInt(partes[1]);

                char letraFila = (char) ('A' + f);
                System.out.println("🎯 " + nombreRival + " disparó en: " + letraFila + (c + 1));

                if (tBarcos[f][c] == Constantes.BARCO) {
                    tBarcos[f][c] = Constantes.IMPACTO;
                    misBarcosRestantes--;
                    conexion.enviarMensaje("IMPACTO");
                    System.out.println(Constantes.RED + Constantes.BOLD + "💥 ¡TE HAN ALCANZADO! Daño en tu flota." + Constantes.RESET);
                    Sonido.reproducirImpactoRecibido();
                } else {
                    tBarcos[f][c] = Constantes.FALLO;
                    conexion.enviarMensaje("AGUA");
                    System.out.println(Constantes.BLUE + Constantes.BOLD + "💦 El disparo rival cayó al agua." + Constantes.RESET);
                    Sonido.reproducirAgua();
                }
                esMiTurno = true;
            }
        }

        if (misBarcosRestantes == 0) {
            System.out.println(Constantes.RED + Constantes.BOLD + "\n💀 ¡DERROTA! " + nombreRival + " ha destruido toda tu flota. 💀" + Constantes.RESET);
        } else if (rivalBarcosRestantes == 0) {
            System.out.println(Constantes.GREEN + Constantes.BOLD + "\n🏆 ¡VICTORIA ABSOLUTA, " + miNombre.toUpperCase() + "! Hundiste la flota enemiga. 🏆" + Constantes.RESET);
        }

        conexion.cerrar();
    }
}