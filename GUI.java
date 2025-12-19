import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.*;

public class GUI extends JPanel {
    private JFrame sideFrame ;
    private JFrame mazeFrame ;
    private JButton setStart = new JButton("Set Start") ;
    private JButton setEnd = new JButton("Set End") ;
    private JButton addWall = new JButton("Add Wall") ;
    private JButton removeWall = new JButton("Remove Wall") ;
    private JButton findPath = new JButton("Find Path") ;
    private JButton reset = new JButton("Reset") ;
    private Cell [][] cells ;
    private MouseAdapter mouseClick ;
    MyPanel mazePanel ;

    GUI (Cell [][] cells) {
        this.cells = cells ;
        createSideFrame() ;
        createMazeFrame() ;
    }
    private void createSideFrame() {
        sideFrame = new JFrame() ;
        sideFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        sideFrame.setSize(360, 480) ;
        sideFrame.setLayout(new GridLayout(6,1)) ;
        sideFrame.add(setStart) ;
        sideFrame.add(setEnd) ;
        sideFrame.add(addWall) ;
        sideFrame.add(removeWall) ;
        sideFrame.add(findPath) ;
        sideFrame.add(reset) ;
        sideFrame.setLocation(1200,270);
        sideFrame.setVisible(true);
    }

    private void createMazeFrame() {
        mazeFrame = new JFrame() ;
        mazeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        mazeFrame.setSize(618, 641) ;
        mazeFrame.setLocation(550,200);
        mazePanel = new MyPanel() ;
        mazeFrame.add(mazePanel) ;
        mazeFrame.setVisible(true);
        mazePanel.repaint();
        actionListeners() ;
    }

    public void actionListeners(){
        setStart.addActionListener(e -> {
            if (mazePanel.getMouseListeners().length > 0) {
                mazePanel.removeMouseListener(mouseClick);
            }
            mouseClick = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    int x = e.getX()/120;
                    int y = e.getY()/120;
                    if (cells[x][y].getIsEmpty()) {
                        Maze.setMouse(x,y);
                        mazePanel.repaint();
                    }
                }
            } ;
            mazePanel.addMouseListener(mouseClick);
        });

        setEnd.addActionListener(e -> {
            if (mazePanel.getMouseListeners().length > 0) {
                mazePanel.removeMouseListener(mouseClick);
            }
            mouseClick = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    int x = e.getX()/120;
                    int y = e.getY()/120;
                    if (cells[x][y].getIsEmpty()) {
                        Maze.setCheese(x,y);
                        mazePanel.repaint();
                    }
                }
            } ;
            mazePanel.addMouseListener(mouseClick);
        });

        addWall.addActionListener(e -> {
            if (mazePanel.getMouseListeners().length > 0) {
                mazePanel.removeMouseListener(mouseClick);
            }
            mouseClick = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    int x = e.getX()/120;
                    int y = e.getY()/120;
                    if (cells[x][y].getIsEmpty()) {
                        cells[x][y].setHasWall(true);
                        mazePanel.repaint();
                    }
                }
            };
            mazePanel.addMouseListener(mouseClick);
        });

        removeWall.addActionListener(e -> {
            if (mazePanel.getMouseListeners().length > 0) {
                mazePanel.removeMouseListener(mouseClick);
            }
            mouseClick = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    int x = e.getX()/120;
                    int y = e.getY()/120;
                    if (cells[x][y].getHasWall()) {
                        cells[x][y].setHasWall(false);
                        mazePanel.repaint();
                    }
                }
            };
            mazePanel.addMouseListener(mouseClick);        
        });

        findPath.addActionListener(e -> {
            Maze.findWays(Maze.mouse);
            if (!Maze.createPath()){
                JOptionPane.showMessageDialog(null, "Cannot find a path") ;
            }
            mazePanel.repaint();
        });

        reset.addActionListener(e -> {
            Maze.reset();
            mazePanel.repaint();
        });
    }

    class MyPanel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g) ;
            for (int i = 0 ; i < cells.length ; i++) {
                for (int k = 0; k < cells[i].length; k++) {
                    cells[i][k].draw(g) ;
                }
            }
        }
    }
}
