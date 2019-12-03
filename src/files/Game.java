package files;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends JFrame implements ActionListener {
    private String [] sign = new String[]{"","X","O"};
    private JButton [][] btn = new JButton[3][3];
    private JButton playButton = new JButton("Start Game");
    private JLabel statusLabel = new JLabel("");
    private AI game = null;
    private int human = 0;
    private int ai = 0;
    private boolean process = false;

    private void setStatus(String s) {
        statusLabel.setText(s);
    }

    private void btnInfo(boolean enabled) {
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++) {
                btn[i][j].setEnabled(enabled);
                if(enabled) {
                    btn[i][j].setText(" ");
                }
            }
    }

    public Game() {
        setTitle("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mid = new JPanel(new GridLayout(3,3));
        Font font = new Font("Courier",Font.BOLD, 20);
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++) {
                btn[i][j] = new JButton(" ");
                btn[i][j].setFont(font);
                btn[i][j].addActionListener(this);
                btn[i][j].setFocusable(false);
                mid.add(btn[i][j]);
            }
        playButton.addActionListener(this);
        JPanel top = new JPanel();
            top.add(statusLabel);
        JPanel bot = new JPanel();
            bot.add(playButton);
        btnInfo(false);

        add(top,"North");
        add(mid,"Center");
        add(bot,"South");
        setSize(500,500);
        setLocationRelativeTo(null);
    }

    private void AITurn() {
        if(!process) {
            return;
        }

        int []position = game.nextMove(ai);

        if(position != null) {
            int i = position[0];
            int j = position[1];
            btn[i][j].setText(sign[ai]);
            game.setBoard(i,j,ai);
        }
        winner();
    }

    private void endGame(String information) {
        setStatus(information);
        btnInfo(false);
        process = false;
    }

    private void winner() {
        if(game.isWin(human)) {
            endGame("You win!");
        } else if(game.isWin(ai)) {
            endGame("You lose!");
        } else if(game.nextMove(human) == null && game.nextMove(ai) == null) {
            endGame("Draw!");
        }
    }

    private void click(int a, int b) {
        if(game.getBoard(a, b)==AI.absence) {
            btn[a][b].setText(sign[human]);
            game.setBoard(a, b, human);
            winner();
            AITurn();
        }
    }

    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if(source == playButton) {
            play();
        } else {
            for(int i=0; i<3; i++)
                for(int k=0; k<3; k++)
                    if(source == btn[i][k])
                        click(i, k);
        }
    }

    private void play() {
        game = new AI();
        human = AI.playerOne;
        ai = AI.playerTwo;
        setStatus("Your Turn");
        btnInfo(true);
        process = true;
    }
}
