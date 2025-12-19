import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Property extends Cell{
    private boolean isSold ;
    private int rent ;
    private int houseCount ;
    private int[] rents ;
    private Player owner ;
    private int price ;
    private int houseCost;

    Property (int cellNo, String symbol, int price, int houseCost, int[] rents, int x, int y){
        super(cellNo, symbol, x, y, Color.white); ;
        this.isSold = false ;
        this.houseCount = 0 ;
        this.price = price ;
        this.rent = rents[0] ;
        this.rents = rents ;
        this.houseCost = houseCost ;
        this.owner = null ;
    }
    public String toString(){
        if(this.isSold){
            return this.getSymbol() + " is owned by " + this.owner.getName() + ". Price is " + this.price + " coins. There are " + this.houseCount + " houses on this property. Rent is " + this.getRent() + " coins.";
        }
        return this.getSymbol() + " is not owned by anyone. Price is " + this.price + " coins. Rent is " + this.getRent() + " coins.";
    }
    public int getHouseCost() {
        return houseCost;
    }
    public int getHouseCount() {
        return houseCount;
    }
    public Player getOwner() {
        return owner;
    }
    public int getPrice() {
        return price;
    }
    public int getRent() {
        return rent;
    }
    public int[] getRents() {
        return rents;
    }
    public boolean getisSold(){
        return isSold ;
    }
    public void setHouseCount(int houseCount) {
        this.houseCount = houseCount;
    }
    public void setRent() {
        this.rent = this.rents[this.houseCount];
    }
    public void buy (Player owner){
        this.owner = owner ;
        this.color = owner.getColor() ;
        this.isSold = true ;
        this.owner.getProperties().add(this) ;
        this.owner.changeBudget(-this.price);
        Monopoly.text.append(owner.getName() + " bought the property on " + this.getSymbol() + "\n");
        this.owner.printBudget();
    }
    public void buildHouse (){
        this.houseCount += 1 ;
        this.setRent();
        this.owner.changeBudget(-this.houseCost);
        Monopoly.text.append(this.owner.getName() + " built a house on " + this.getSymbol() + "\n");
        this.owner.printBudget();
    }
    public void sellToBank (){
        this.isSold = false ;
        this.setHouseCount(0);
        this.setRent();
        this.owner.getProperties().remove(this) ;
        this.owner.changeBudget(this.price);
        Monopoly.text.append(this.owner.getName() + " sold a house.\n");
        this.owner.printBudget();
        this.owner = null ;
        this.color = Color.white ;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect((super.getX()-1)*120, (super.getY()-1)*120, 120, 120);
        g.setColor(Color.BLACK);
        g.drawRect((super.getX()-1)*120, (super.getY()-1)*120, 120, 120);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 36));
        g.drawString(super.getSymbol(), (super.getX()-1)*120 + 45, (super.getY()-1)*120 + 72);
        for (int i = 0; i < this.houseCount; i++){
            g.setColor(Color.BLACK);
            g.fillRect((super.getX()-1)*120 + 24*i, (super.getY()-1)*120, 22, 22);
        }
        for (int i = 0; i < super.getPlayersOnCell().size() && i < 2; i++){
            g.setColor(super.getPlayersOnCell().get(i).getColor());
            g.fillOval((super.getX()-1)*120 + 60*i, (super.getY()-1)*120, 60, 60);
            g.setColor(Color.BLACK);
            g.drawOval((super.getX()-1)*120 + 60*i, (super.getY()-1)*120, 60, 60);
        }
        for (int i = 2; i < super.getPlayersOnCell().size(); i++){
            g.setColor(super.getPlayersOnCell().get(i).getColor());
            g.fillOval((super.getX()-1)*120 + 60*(i-2), (super.getY()-1)*120 + 60, 60, 60);
            g.setColor(Color.BLACK);
            g.drawOval((super.getX()-1)*120 + 60*(i-2), (super.getY()-1)*120 + 60, 60, 60);
        }
    }
    
}
