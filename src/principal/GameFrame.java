package principal;

import javax.swing.JFrame;

public class GameFrame extends JFrame{

    public GameFrame(){

        add(new GamePanel());
        setTitle("SnakeGame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

}
