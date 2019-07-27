//HUD object that displays each player's health over their player
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;

public class HealthBar extends HUDObject {
   private Player owner;
   private Color color;
      
   public HealthBar(int x, int y, int width, int height, Player owner, ID id) {
      super(x,y,width,height,id);
      this.owner = owner;
      color = new Color(20, 155, 20);
   } 
   
   @Override
   public void tick() {      
      owner.setHealth(Game.clamp(owner.getHealth(), 0, 100));
   }
      
   @Override
   public void render(Graphics g) {
      if (owner.getHealth() > 50) {
         color = new Color(20 + (135 * (100 - owner.getHealth()))/50, 155, 20);
      } else {
         color = new Color(155, 155 - (135 * (50 - owner.getHealth()))/50, 20);
      }
      g.setColor(color);
      g.fillRect(owner.getX(),owner.getY()-10,(width * owner.getHealth())/100,height);
      if (owner.getAbilityMeter() == 100) {
         g.setColor(Color.white);
         g.drawRect(owner.getX(),owner.getY()-10,(width * owner.getHealth())/100,height);
         g.drawRect(owner.getX()-1,owner.getY()-11,(width * owner.getHealth())/100 +2,height+2);
      }
   }
   
   //Getters and Setters
   public Player getOwner() {
      return owner;
   }
   
   public void setOwner(Player owner) {
      this.owner = owner;
   } 
   
}