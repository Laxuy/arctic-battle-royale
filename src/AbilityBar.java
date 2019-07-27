//HUD object displaying ability meter for each character
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.GradientPaint;
import java.awt.Toolkit;
import java.awt.Image;

public class AbilityBar extends HUDObject {
   private Player owner;
   private GradientPaint redToGreen;
   private Image ownerImage;
      
   public AbilityBar(int x, int y, int width, int height, Player owner, ID id) {
      super(x,y,width,height,id);
      this.owner = owner;
      redToGreen = new GradientPaint(x, 0, new Color(204, 0, 0), x + width, 0,
            new Color(51, 204, 51));
      if (owner.getCharacter() == ID.Panda) {
         ownerImage = Toolkit.getDefaultToolkit().getImage("pandaRight.png");
      } else if (owner.getCharacter() == ID.Seal) {
         ownerImage = Toolkit.getDefaultToolkit().getImage("sealRight.png");
      } else if (owner.getCharacter() == ID.Dino) {
         ownerImage = Toolkit.getDefaultToolkit().getImage("dinoRight.png");
      } else if (owner.getCharacter() == ID.Eagle) {
         ownerImage = Toolkit.getDefaultToolkit().getImage("eagleRight.png");
      }
   } 
   
   @Override
   public void tick() {      
      owner.setAbilityMeter(Game.clamp(owner.getAbilityMeter(), 0, 100));
   }
      
   @Override
   public void render(Graphics g) {
      Graphics2D g2d = (Graphics2D)g;
      g2d.drawImage(ownerImage,x,y,null);
      g2d.setPaint(redToGreen);
      g2d.fillRect(x + 50, y + 10, (width * owner.getAbilityMeter())/100, height);
      g2d.setColor(Color.white);
      g2d.drawRect(x + 50, y + 10, width, height);
      if (owner.getAbilityMeter() == 100) {
         g2d.drawRect(x + 49, y + 9, width + 2, height + 2);
         g2d.drawRect(x + 48, y + 8, width + 4, height + 4);
         g2d.drawRect(x + 47, y + 7, width + 6, height + 6);
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