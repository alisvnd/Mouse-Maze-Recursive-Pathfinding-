import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class MyPanel extends JPanel {

    public MyPanel(GridLayout gridLayout) {
        super(gridLayout);
    }

    public MyPanel() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < 16; i++){
            Monopoly.cells[i].draw(g);
        }
        for (int i = 0; i < 9; i++){
            Monopoly.middle[i].draw(g);
        }
        for (int i = 0; i < Monopoly.players.size(); i++){
            Player player = Monopoly.players.get(i) ;
            g.setColor(player.getColor());
            g.drawString(player.getName() + ": " + player.getBudget(), 260, 210 + 60*i);
        }
    }   
}
   