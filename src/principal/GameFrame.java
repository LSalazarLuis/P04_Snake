package principal;

import java.awt.Toolkit;

import javax.swing.JFrame;

public class GameFrame extends JFrame{

    public GameFrame(){

        add(new GamePanel());
        setTitle("SnakeGame");
        setIconImage(Toolkit.getDefaultToolkit().getImage("src/assets/icono2.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

}
