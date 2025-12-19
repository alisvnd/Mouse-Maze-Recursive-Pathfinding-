import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Player {
    private String name ;
    private String initial ;
    private int budget ;
    private Cell position ;
    private ArrayList <Property> properties ;
    private boolean skipRound;
    private Color color;

    Player (String name, Cell position, Color color){
        this.name = name ;
        this.initial = name.substring(0,1) ;
        this.budget = 10 ;
        this.position = position ;
        this.position.getPlayersOnCell().add(this);
        this.properties = new ArrayList<>() ;
        this.skipRound = false;
        this.color = color;
    }
    public boolean getSkipRound() {
        return skipRound;
    }
    public int getBudget() {
        return budget;
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public String getInitial() {
        return initial;
    }
    public String getName() {
        return name;
    }
    public Cell getPosition() {
        return position;
    }
    public ArrayList<Property> getProperties() {
        return properties;
    }
    public void setSkipRound(boolean skipRound) {
        this.skipRound = skipRound;
    }
    public void changeBudget(int change) {
        while (this.budget + change < 0){
            if (this.properties.size() > 0){
                this.getPropertyToSell().sellToBank();
            }
            else{
                JOptionPane losePane = new JOptionPane() ;
                JOptionPane.showMessageDialog(losePane, this.getName() + " loses!") ;
                JButton ok= new JButton("OK") ;
                losePane.add(ok);
                Monopoly.players.remove(this);
                this.position.getPlayersOnCell().remove(this);
                return;
            }
        }
        this.budget += change;
    }
    public void setName(String name) {
        this.name = name;
        this.initial = this.name.substring(0,1) ;
    }
    public void setPosition(Cell position) {
        this.position.getPlayersOnCell().remove(this);
        this.position = position;
        Monopoly.text.append(this.name + " moved to " + this.position.getSymbol() + "\n");
        position.getPlayersOnCell().add(this);
    }
    public Property getPropertyToSell(){
        Property propToSell = this.properties.get(0);
        for (int i = 1 ; i < this.properties.size(); i++){
            if (this.properties.get(i).getHouseCount() < propToSell.getHouseCount()){
                propToSell = this.properties.get(i) ;
            }
        }
        return propToSell ;
    }
    public void printBudget(){
        Monopoly.text.append(this.name + " has " + this.budget + " coins.\n"); 
    }
}