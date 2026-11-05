package batallanaval;

import java.util.Random;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

public class Sonido {

    private static final Random random = new Random();
    private static volatile boolean musicaActiva = false;

    // ==========================================
    // 0. Música de fondo sintética (Loop 8-bit)
    // ==========================================
    public static synchronized void reproducirMusicaFondo() {
        if (musicaActiva) return; // Evita iniciar múltiples hilos de música
        musicaActiva = true;

        Thread hiloMusica = new Thread(() -> {
            try {
                float sampleRate = 8000f;
                AudioFormat af = new AudioFormat(sampleRate, 8, 1, true, false);
                SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
                sdl.open(af, 2048);
                sdl.start();

                // Frecuencias (Hz) para una melodía de fondo en bucle estilo retro naval (La menor)
                double[] notas = { 110.0, 130.81, 146.83, 164.81, 130.81, 146.83, 110.0, 98.0 };
                int msPorNota = 300;

                while (musicaActiva) {
                    for (double freq : notas) {
                        if (!musicaActiva) break;

                        int muestras = (int) (sampleRate * (msPorNota / 1000.0));
                        byte[] buf = new byte[1];

                        for (int i = 0; i < muestras; i++) {
                            if (!musicaActiva) break;
                            double atenua = 1.0 - ((double) i / muestras);
                            // Genera tono suave combinando seno y armónico
                            double onda = Math.sin(2 * Math.PI * i * freq / sampleRate) * 30.0
                                        + Math.sin(2 * Math.PI * i * (freq / 2) / sampleRate) * 15.0;
                            buf[0] = (byte) (onda * atenua);
                            sdl.write(buf, 0, 1);
                        }
                    }
                }

                sdl.drain();
                sdl.close();
            } catch (Exception ignored) {}
        });

        hiloMusica.setDaemon(true);
        hiloMusica.start();
    }

    public static synchronized void detenerMusicaFondo() {
        musicaActiva = false;
    }

    // 1. Sonido cuando acertamos un disparo a la flota enemiga
    public static void reproducirImpactoEnemigo() {
        new Thread(() -> {
            try {
                float sampleRate = 8000f;
                AudioFormat af = new AudioFormat(sampleRate, 8, 1, true, false);
                SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
                sdl.open(af);
                sdl.start();

                byte[] buf = new byte[1];
                int duracionMuestras = (int) (sampleRate * 0.45);
                for (int i = 0; i < duracionMuestras; i++) {
                    double atenua = 1.0 - ((double) i / duracionMuestras);
                    byte ruido = (byte) ((random.nextInt(256) - 128) * atenua);
                    buf[0] = ruido;
                    sdl.write(buf, 0, 1);
                }

                sdl.drain();
                sdl.close();
            } catch (Exception e) {
                java.awt.Toolkit.getDefaultToolkit().beep();
            }
        }).start();
    }

    // 2. Sonido cuando dañan nuestros propios barcos
    public static void reproducirImpactoRecibido() {
        new Thread(() -> {
            try {
                float sampleRate = 8000f;
                AudioFormat af = new AudioFormat(sampleRate, 8, 1, true, false);
                SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
                sdl.open(af);
                sdl.start();

                byte[] buf = new byte[1];
                int duracionMuestras = (int) (sampleRate * 0.55);
                for (int i = 0; i < duracionMuestras; i++) {
                    double atenua = 1.0 - ((double) i / duracionMuestras);
                    double tonoGrave = Math.sin(2 * Math.PI * i * 110 / sampleRate) * 60;
                    byte ruido = (byte) (((random.nextInt(256) - 128) * 0.7 + tonoGrave) * atenua);
                    buf[0] = ruido;
                    sdl.write(buf, 0, 1);
                }

                sdl.drain();
                sdl.close();
            } catch (Exception e) {
                java.awt.Toolkit.getDefaultToolkit().beep();
            }
        }).start();
    }

    // 3. Sonido cuando cae un disparo al agua
    public static void reproducirAgua() {
        new Thread(() -> {
            try {
                float sampleRate = 8000f;
                AudioFormat af = new AudioFormat(sampleRate, 8, 1, true, false);
                SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
                sdl.open(af);
                sdl.start();

                byte[] buf = new byte[1];
                int duracionMuestras = (int) (sampleRate * 0.25);
                for (int i = 0; i < duracionMuestras; i++) {
                    double atenua = 1.0 - ((double) i / duracionMuestras);
                    double frecuencia = 450.0 - (300.0 * i / duracionMuestras);
                    byte muestra = (byte) (Math.sin(2 * Math.PI * i * frecuencia / sampleRate) * 100 * atenua);
                    buf[0] = muestra;
                    sdl.write(buf, 0, 1);
                }

                sdl.drain();
                sdl.close();
            } catch (Exception ignored) {}
        }).start();
    }
}
