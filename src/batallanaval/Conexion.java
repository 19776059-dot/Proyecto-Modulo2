package batallanaval;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Conexion {
    private ServerSocket server;
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;

    public boolean iniciarServidor(int puerto) {
        try {
            System.out.println(Constantes.CYAN + "Esperando conexión del rival en el puerto " + puerto + "..." + Constantes.RESET);
            server = new ServerSocket(puerto);
            socket = server.accept();
            System.out.println(Constantes.GREEN + "¡Rival conectado desde " + socket.getInetAddress().getHostAddress() + "!" + Constantes.RESET);
            inicializarFlujos();
            return true;
        } catch (Exception e) {
            System.out.println(Constantes.RED + "Error al iniciar servidor: " + e.getMessage() + Constantes.RESET);
            return false;
        }
    }

    public boolean conectarAHost(String ip, int puerto) {
        try {
            System.out.println(Constantes.CYAN + "Conectando al Host (" + ip + ":" + puerto + ")..." + Constantes.RESET);
            socket = new Socket(ip, puerto);
            System.out.println(Constantes.GREEN + "¡Conexión exitosa con el Host!" + Constantes.RESET);
            inicializarFlujos();
            return true;
        } catch (Exception e) {
            System.out.println(Constantes.RED + "Error al conectar con el Host: " + e.getMessage() + Constantes.RESET);
            return false;
        }
    }

    private void inicializarFlujos() throws Exception {
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        salida = new PrintWriter(socket.getOutputStream(), true);
    }

    public void enviarMensaje(String msg) {
        if (salida != null) salida.println(msg);
    }

    public String recibirMensaje() {
        try {
            return entrada != null ? entrada.readLine() : null;
        } catch (Exception e) {
            return null;
        }
    }

    public void cerrar() {
        try {
            if (socket != null) socket.close();
            if (server != null) server.close();
        } catch (Exception ignored) {}
    }
}