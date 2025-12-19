import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Monopoly {
    public static Cell[] cells = new Cell[16] ;
    public static Cell[] middle = new Cell[9] ;
    public static ArrayList<Player> players = new ArrayList<>() ;
    public static JButton roll = new JButton("Roll") ;
    public static JButton buy = new JButton("Buy") ;
    public static JButton sell = new JButton("Sell") ;
    public static JButton build = new JButton("Build") ;
    public static JButton endTurn = new JButton("End Turn") ;
    public static Player currentPlayer ;
    public static int round = 0;
    public static JFrame frame ;
    public static boolean specialRoll = false;
    public static JTextArea text = new JTextArea("Welcome to Monopoly!\n") ;
    public static void main(String[] args) {
        initializeGame();
        //playGame();
    }

    public static void initializeGame(){
        createCells() ;
        JFrame aframe = new JFrame() ;
        aframe.setSize(400,400);
        aframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aframe.setLocation(800, 300);
        JPanel panel = new JPanel(new GridLayout(5,2,2,2)) ;
        JLabel redName = new JLabel("Red Player:") ;
        redName.setForeground(Color.RED);
        JTextField enterRed = new JTextField() ;
        JLabel blueName = new JLabel("Blue Player:") ;
        blueName.setForeground(Color.BLUE);
        JTextField enterBlue = new JTextField() ;
        JLabel yellowName = new JLabel("Yellow Player:") ;
        yellowName.setForeground(Color.YELLOW);
        JTextField enterYellow = new JTextField() ;
        JLabel greenName = new JLabel("Green Player:") ;
        greenName.setForeground(Color.GREEN);
        JTextField enterGreen = new JTextField() ;
        JButton start = new JButton("START");
        panel.add(redName) ;
        panel.add(enterRed) ;
        panel.add(blueName) ;
        panel.add(enterBlue) ;
        panel.add(yellowName) ;
        panel.add(enterYellow) ;
        panel.add(greenName) ;
        panel.add(enterGreen) ;
        panel.add(start) ;
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                players.add(new Player(enterRed.getText(), cells[0], Color.RED)) ;
                players.add(new ComputerPlayer(enterBlue.getText(), cells[0], Color.BLUE)) ;
                players.add(new ComputerPlayer(enterYellow.getText(), cells[0], Color.YELLOW)) ;
                players.add(new ComputerPlayer(enterGreen.getText(), cells[0], Color.GREEN)) ;
                aframe.dispose();
                shuffleList(players) ;
                currentPlayer = players.get(0) ;
                createScreen();
            }
        });
        aframe.add(panel);
        aframe.setVisible(true);
    }

    public static void createScreen(){
        frame = new JFrame() ;
        frame.setSize(1800,641);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(100, 200);
        frame.setLayout(new GridLayout(1,3));
        MyPanel leftPanel = new MyPanel(new GridLayout(5,5,0,0)) ;
        leftPanel.setSize(600,400);
        leftPanel.repaint();

        JPanel midPanel = new JPanel() ;
        midPanel.setSize(new Dimension(600,400));
        midPanel.setBackground(Color.WHITE);
        text.setFont(new Font("Arial", Font.PLAIN, 12));
        midPanel.add(text);
        // JScrollPane scrollPane = new JScrollPane(text);
        // scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        // scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // scrollPane.setSize(600,400);
        // midPanel.add(scrollPane);

        JPanel rightPanel = new JPanel(new GridLayout(5,1,0,0)) ;
        rightPanel.setSize(600,400);
        roll.setEnabled(false);
        buy.setEnabled(false);
        sell.setEnabled(false);
        build.setEnabled(false);
        endTurn.setEnabled(false);
        rightPanel.add(roll);
        rightPanel.add(buy);
        rightPanel.add(sell);
        rightPanel.add(build);
        rightPanel.add(endTurn);

        frame.add(leftPanel);
        frame.add(midPanel) ;
        frame.add(rightPanel);
        frame.setVisible(true);
        actionListeners(frame);
        startTurn();
    }

    public static void startTurn(){
        if (players.indexOf(currentPlayer) == 0){
            round++;
            text.append("Round: " + round + "\n");
        }
        if(round < 101 && players.size() > 1){
            if (!(currentPlayer instanceof ComputerPlayer)){  
                //text.setText("");
            }
            if (!currentPlayer.getSkipRound()){
                text.append("        " + currentPlayer.getName() + "'s Turn.\n");
                if (!(currentPlayer instanceof ComputerPlayer)){  
                    roll.setEnabled(true);
                }
                else{
                    move(currentPlayer);
                    playTurn(currentPlayer);
                    makeChoices(currentPlayer);
                    nextTurn();
                }
            }
            else{
                text.append(currentPlayer.getName() + " skipped this round.\n");
                currentPlayer.setSkipRound(false);
                nextTurn();
            }
        }
        else if (players.size() == 1){
            JOptionPane winPane = new JOptionPane() ;
            JOptionPane.showMessageDialog(winPane, players.get(0).getName() + " wins the game in round " + round) ;
            JButton ok= new JButton("OK") ;
            winPane.add(ok);
        }
        else if (round == 101){
            String s = "Game Over! It's a draw between ";
            for (int i = 0 ; i < players.size() ; i++){
                s+= (players.get(i).getName() + " ");
            }
            JOptionPane drawPane = new JOptionPane();
            JOptionPane.showMessageDialog(drawPane, s) ;
            JButton ok= new JButton("OK") ;
            drawPane.add(ok);
        }
        
    }

    public static void nextTurn(){
        currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size()) ;
        startTurn();
    }

    public static void shuffleList(ArrayList<Player> players){
        Random random = new Random() ;
        for (int i = 0 ; i < 30 ; i++){
            int j = random.nextInt(players.size()) ;
            players.add(players.get(j)) ;
            players.remove(j) ;
        }
    }

    public static void actionListeners(JFrame frame){
        roll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (specialRoll){
                    roll.setEnabled(false);
                    switch (rollDie(currentPlayer)){
                        case 1:
                            currentPlayer.changeBudget(-2);
                            break;
                        case 2:
                            currentPlayer.changeBudget(-1);
                            break;
                        case 3:
                            currentPlayer.setPosition(cells[currentPlayer.getPosition().getCellNo()+1]);
                            break;
                        case 4:
                            currentPlayer.setPosition(cells[currentPlayer.getPosition().getCellNo()+2]);
                            break;
                        case 5:
                            currentPlayer.changeBudget(1);
                            currentPlayer.setPosition(cells[currentPlayer.getPosition().getCellNo()+1]);
                            break;
                        case 6:
                            currentPlayer.changeBudget(2);
                            currentPlayer.setPosition(cells[currentPlayer.getPosition().getCellNo()+2]);
                            break;
                    }
                    frame.repaint();
                }
                else{
                    roll.setEnabled(false); 
                    move(currentPlayer);
                }
                enableButtons();
            }
        });

        buy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Property prop = ((Property) currentPlayer.getPosition()) ;
                prop.buy(currentPlayer);
                if (currentPlayer.getBudget() > prop.getHouseCost()){
                    build.setEnabled(true);
                }
                buy.setEnabled(false);
                frame.repaint();
            }
        });

        sell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame sellFrame = new JFrame() ;
                sellFrame.setSize(200,400);
                sellFrame.setLocation(800,400);
                JPanel sellPanel = new JPanel() ;
                int n = currentPlayer.getProperties().size();
                sellPanel.setLayout(new GridLayout(n + 1,1));
                for (int i = 0; i < n ; i++){
                    final int k = i ;
                    JButton button = new JButton("Sell " + currentPlayer.getProperties().get(i).getSymbol() + " for " + currentPlayer.getProperties().get(i).getPrice()) ;
                    sellPanel.add(button);
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane sellPane = new JOptionPane() ;
                            JOptionPane.showMessageDialog(sellPane, "Sold " + currentPlayer.getProperties().get(k).getSymbol() + " for " + currentPlayer.getProperties().get(k).getPrice());
                            JButton ok= new JButton("OK") ;
                            sellPane.add(ok);
                            currentPlayer.getProperties().get(k).sellToBank();
                            sellFrame.dispose();
                            frame.setVisible(true);
                            sell.setEnabled(false);
                            frame.repaint();
                        }
                    });
                }
                JButton returnButton = new JButton("Return") ;
                returnButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        sellFrame.dispose();
                        frame.setVisible(true);
                    }
                });
                sellPanel.add(returnButton);
                sellFrame.add(sellPanel) ;
                frame.setVisible(false);
                sellFrame.setVisible(true);
            }
        });

        build.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame buildFrame = new JFrame() ;
                buildFrame.setSize(400,400);
                buildFrame.setLocation(800,400);
                JPanel buildPanel = new JPanel() ;
                int n = currentPlayer.getProperties().size();
                buildPanel.setLayout(new GridLayout(n + 1,1));
                for (int i = 0; i < n ; i++){
                    final int k = i ;
                    if(currentPlayer.getProperties().get(i).getHouseCount() < 4 && currentPlayer.getBudget() >= currentPlayer.getProperties().get(i).getHouseCost()){
                        JButton button = new JButton("Build on " + currentPlayer.getProperties().get(i).getSymbol() + " price: " + currentPlayer.getProperties().get(i).getHouseCost() + " . Rent from " + currentPlayer.getProperties().get(i).getRent() + " to " + currentPlayer.getProperties().get(i).getRents()[currentPlayer.getProperties().get(i).getHouseCount() + 1]) ;
                        buildPanel.add(button);
                        button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane buildPane = new JOptionPane() ;
                            JOptionPane.showMessageDialog(buildPane, "You built on " + currentPlayer.getProperties().get(k).getSymbol()) ;
                            JButton ok= new JButton("OK") ;
                            buildPane.add(ok);
                            currentPlayer.getProperties().get(k).buildHouse();
                            buildFrame.dispose();
                            frame.setVisible(true);
                            build.setEnabled(false);
                            frame.repaint();
                        }
                    });
                    }
                }
                JButton returnButton = new JButton("Return") ;
                returnButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        buildFrame.dispose();
                        frame.setVisible(true);
                    }
                });
                buildPanel.add(returnButton);
                buildFrame.add(buildPanel) ;
                frame.setVisible(false);
                buildFrame.setVisible(true);
            }
        });
         
        endTurn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buy.setEnabled(false);
                sell.setEnabled(false);
                build.setEnabled(false);
                endTurn.setEnabled(false);
                nextTurn();
            }
        });
    }

    public static void enableButtons(){
        if (currentPlayer.getPosition() instanceof Property){
            Property currentProperty = (Property) currentPlayer.getPosition() ;
            if (currentProperty.getisSold()){
                if (currentProperty.getOwner()!= currentPlayer){
                    payRent(currentPlayer, currentProperty) ;
                }
            }
            else if (currentPlayer.getBudget() >= ((Property)(currentPlayer.getPosition())).getPrice()){
                buy.setEnabled(true);
            }
        }
        else {
            int i =  Integer.valueOf(currentPlayer.getPosition().getSymbol()) ;
            switch (i){
                case 1:
                    if(!specialRoll){
                        roll.setEnabled(true);
                        specialRoll = true;
                        return;
                    }
                case 2:
                    currentPlayer.changeBudget(4);
                    for (int k = 0; k < players.size(); k++){
                        players.get(k).changeBudget(-1);
                    }
                    break;
                case 3:
                    currentPlayer.setSkipRound(true);
                    break;
            }
            specialRoll = false;
        }
        if (currentPlayer.getProperties().size() > 0){
            sell.setEnabled(true);
        }
        for (int i = 0; i < currentPlayer.getProperties().size(); i++){
            if (currentPlayer.getProperties().get(i).getHouseCount() < 4 && currentPlayer.getBudget() >= currentPlayer.getProperties().get(i).getHouseCost()){
                build.setEnabled(true);
                break;
            }
        }
        endTurn.setEnabled(true);
    }



    public static int rollDie(Player currentPlayer) {
        Random random = new Random();
        int i = random.nextInt(6) + 1 ;
        text.append(currentPlayer.getName() + " rolled a " + i + "\n");  
        return i ;  
    }

    public static void move (Player currentPlayer){
        int i = rollDie(currentPlayer);
        int newCellNo = currentPlayer.getPosition().getCellNo()+i ;
        if (newCellNo > 15){
            if (newCellNo == 16){
                currentPlayer.changeBudget(3);
            }
            currentPlayer.setPosition(cells[newCellNo - 16]);
            currentPlayer.changeBudget(3);
        }
        else{
            currentPlayer.setPosition(cells[newCellNo]);
        }
        frame.repaint();
    }

    public static void playTurn(Player currentPlayer){
        Scanner sc = new Scanner(System.in) ;
        if (currentPlayer.getPosition() instanceof Property){
            Property currentProperty = (Property) currentPlayer.getPosition() ;
            text.append(currentProperty.toString()+"\n");
            if (currentProperty.getisSold()){
                if (currentProperty.getOwner()!= currentPlayer){
                    payRent(currentPlayer, currentProperty) ;
                }
            }
            else {
                boolean buy = false ;
                if (currentPlayer.getBudget() >= currentProperty.getPrice()){
                    if (currentPlayer instanceof ComputerPlayer){
                        buy = ((ComputerPlayer) currentPlayer).buyProperty() ;
                    }
                    if (buy){
                        currentProperty.buy(currentPlayer);
                    }
                }             
            }
        }
        else{
            int i =  Integer.valueOf(currentPlayer.getPosition().getSymbol()) ;
            switch (i){
                case 1:
                    switch (rollDie(currentPlayer)){
                        case 1:
                            currentPlayer.changeBudget(-2);
                            break;
                        case 2:
                            currentPlayer.changeBudget(-1);
                            break;
                        case 3:
                            currentPlayer.setPosition(cells[currentPlayer.getPosition().getCellNo()+1]);
                            playTurn(currentPlayer);
                            break;
                        case 4:
                            currentPlayer.setPosition(cells[currentPlayer.getPosition().getCellNo()+2]);
                            playTurn(currentPlayer);
                            break;
                        case 5:
                            currentPlayer.changeBudget(1);
                            currentPlayer.setPosition(cells[currentPlayer.getPosition().getCellNo()+1]);
                            playTurn(currentPlayer);
                            break;
                        case 6:
                            currentPlayer.changeBudget(2);
                            currentPlayer.setPosition(cells[currentPlayer.getPosition().getCellNo()+2]);
                            playTurn(currentPlayer);
                            break;
                    }
                    break;
                case 2:
                    currentPlayer.changeBudget(4);
                    for (int k = 0; k < players.size(); k++){
                        players.get(k).changeBudget(-1);
                    }
                    break;
                case 3:
                    currentPlayer.setSkipRound(true);
                    break;
            }
        }
        frame.repaint();
    }
    public static void makeChoices(Player currentPlayer){
        Scanner sc = new Scanner(System.in);
        if (currentPlayer instanceof ComputerPlayer){
            if (currentPlayer.getBudget() < 4 && currentPlayer.getProperties().size() > 0){
                currentPlayer.getPropertyToSell().sellToBank();
            }
            else if (currentPlayer.getBudget() > 2 && currentPlayer.getProperties().size() > 0){
                ((ComputerPlayer)currentPlayer).buildHouse();
            }
        }
        frame.repaint();
    }
    public static void payRent(Player currentPlayer, Property currentProperty){
        int rent = currentProperty.getRent() ;
        currentProperty.getOwner().changeBudget(rent);
        currentPlayer.changeBudget(-rent); 
        text.append(currentProperty.getOwner().getName() + " earned " + rent + " coins from rent.\n");
    }

    public static void createCells (){
        int [] abc = {1,2,3,4,6} ;
        int [] def = {2,2,3,3,7} ;
        int [] ghi = {1,3,4,6,7} ;
        int [] jkl = {3,3,6,6,9} ;
        cells[0] = new Cell(0, "0", 1, 1, Color.gray) ;
        cells[1] = new Property(1, "A", 2, 1, abc, 2,1) ;
        cells[2] = new Property(2, "B", 2, 1, abc, 3,1) ;
        cells[3] = new Property(3, "C", 2, 1, abc, 4,1) ;
        cells[4] = new Cell(4, "1", 5, 1, Color.gray) ;
        cells[5] = new Property(5, "D", 4, 1, def, 5, 2) ;
        cells[6] = new Property(6, "E", 4, 1, def, 5, 3) ;
        cells[7] = new Property(7, "F", 4, 1, def, 5, 4) ;
        cells[8] = new Cell(8, "2", 5, 5, Color.gray) ;
        cells[9] = new Property(9, "G", 6, 2, ghi, 4, 5) ;
        cells[10] = new Property(10, "H", 6, 2, ghi, 3, 5) ;
        cells[11] = new Property(11, "I", 6, 2, ghi, 2, 5) ;
        cells[12] = new Cell(12, "3", 1, 5, Color.gray) ;
        cells[13] = new Property(13, "J", 8, 3, jkl, 1,4) ;
        cells[14] = new Property(14, "K", 8, 3, jkl, 1,3) ;
        cells[15] = new Property(15, "L", 8, 3, jkl, 1,2) ;
        middle[0] = new Cell(1, "", 2, 2, Color.DARK_GRAY) ;
        middle[1] = new Cell(2, "", 2, 3, Color.DARK_GRAY) ;
        middle[2] = new Cell(3, "", 2, 4, Color.DARK_GRAY) ;
        middle[3] = new Cell(4, "", 3, 2, Color.DARK_GRAY) ;
        middle[4] = new Cell(5, "", 3, 3, Color.DARK_GRAY) ;
        middle[5] = new Cell(6, "", 3, 4, Color.DARK_GRAY) ;
        middle[6] = new Cell(7, "", 4, 2, Color.DARK_GRAY) ;
        middle[7] = new Cell(8, "", 4, 3, Color.DARK_GRAY) ;
        middle[8] = new Cell(9, "", 4, 4, Color.DARK_GRAY) ;
    }
}