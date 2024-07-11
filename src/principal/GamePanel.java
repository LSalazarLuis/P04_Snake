package principal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int ANCHO_PANTALLA = 600;
    static final int ALTURA_PANTALLA = 600;
    static final int UNIT_SIZE = 25; // tamaño de un objeto o elemento en pantalla
    static final int GAME_UNITS = (ANCHO_PANTALLA * ALTURA_PANTALLA) / UNIT_SIZE; // cantidad de elementos posibles en
                                                                                  // la ventana
    static final int DELAY = 75; // velocidad ms
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;// tamaño inicial de la serpiente
    int puntaje;
    int comidaX; // cordenas del eje x para la comida
    int comidaY; // cordenas del eje y para la comida
    char direccion = 'R';
    boolean running = false;
    Timer timer; // un objeto Timer genera eventos de accion a intervalos regulares
    Random random; // para crear un numero aleatorio

    public GamePanel() {

        random = new Random();
        setPreferredSize(new Dimension(ANCHO_PANTALLA, ALTURA_PANTALLA));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        iniciarJuego();

    }

    public void iniciarJuego() {

        nuevaComida();
        running = true;
        timer = new Timer(DELAY, this); // cada vez que el temporizador se dispara, se llama al método actionPerformed
                                        // de GamePanel.
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g) {

        if (running) {

            // dibujando cuadriculas (esto es solo para guiarme...)
            /*
            for (int i = 0; i < ALTURA_PANTALLA / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, ALTURA_PANTALLA);
                g.drawLine(0, i * UNIT_SIZE, ANCHO_PANTALLA, i * UNIT_SIZE);
            }
            */
            // dibujando la comida
            g.setColor(Color.red);
            g.fillOval(comidaX, comidaY, UNIT_SIZE, UNIT_SIZE);

            // dibujando la serpiente
            for (int i = 0; i < bodyParts; i++) {

                if (i == 0) {
                    // cabeza
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    // cuerpo
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }

            }

            // visor del puntaje
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Puntaje: " + puntaje, (ANCHO_PANTALLA - metrics.stringWidth("Puntaje: " + puntaje)) / 2, g.getFont().getSize());

        } else {
            finDelJuego(g);
        }

    }

    public void nuevaComida() {

        // cordenadas para la ubicacion de la comida.
        comidaX = random.nextInt((int) (ANCHO_PANTALLA / UNIT_SIZE)) * UNIT_SIZE;
        comidaY = random.nextInt((int) (ALTURA_PANTALLA / UNIT_SIZE)) * UNIT_SIZE;

    }

    public void mover() {

        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direccion) {

            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }

    public void verificarComida() {

        if ((x[0] == comidaX) && (y[0] == comidaY)) {
            bodyParts++;
            puntaje++;
            nuevaComida();
        }

    }

    public void verificarColisiones() {

        // colision cabez-cuerpo serpiente
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {

                running = false;

            }
        }

        // colision con borde izquierdo de la ventana
        if (x[0] < 0) {
            running = false;
        }
        // colision con borde derecho de la ventana
        if (x[0] > ANCHO_PANTALLA) {
            running = false;
        }
        // colision con borde superior de la ventana
        if (y[0] < 0) {
            running = false;
        }
        // colision con borde inferiro de la ventana
        if (y[0] > ALTURA_PANTALLA) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    public void finDelJuego(Graphics g) {

        // visor del puntaje
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Puntaje: " + puntaje, (ANCHO_PANTALLA - metrics.stringWidth("Puntaje: " + puntaje)) / 2, g.getFont().getSize());

        // texto GAME OVER
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (ANCHO_PANTALLA - metrics2.stringWidth("GAME OVER")) / 2, ALTURA_PANTALLA / 2);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {
            mover();
            verificarComida();
            verificarColisiones();
        }
        repaint();

    }

    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {

                case KeyEvent.VK_LEFT:
                    if (direccion != 'R')
                        direccion = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direccion != 'L')
                        direccion = 'R';
                    break;
                case KeyEvent.VK_UP:
                    if (direccion != 'D')
                        direccion = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if (direccion != 'U')
                        direccion = 'D';
                    break;

            }
        }

    }

}
