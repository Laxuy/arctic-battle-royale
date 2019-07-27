//HUD object that displays the owner's current powerup and special ammo
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Image;

public class PlayerPowerup extends HUDObject {
   private Player owner;
   private Color color;
   private Image powerupImage;
      
   public PlayerPowerup(int x, int y, int width, int height, Player owner, ID id) {
      super(x,y,width,height,id);
      this.owner = owner;
      color = new Color(0, 0, 0);
   } 
   
   @Override
   public void tick() { 
   }
      
   @Override
   public void render(Graphics g) {
      if (owner.getPowerup() != id.NoPowerup) {
         if (owner.getPowerup() == id.DrillPowerup) {
            powerupImage = Toolkit.getDefaultToolkit().getImage("drillPowerup.png");
         } else if (owner.getPowerup() == id.BombPowerup) {
            powerupImage = Toolkit.getDefaultToolkit().getImage("bombPowerup.png");
         } else if (owner.getPowerup() == id.MinePowerup) {
            powerupImage = Toolkit.getDefaultToolkit().getImage("minePowerup.png");
         } else if (owner.getPowerup() == id.ReflectBulletPowerup) {
            powerupImage = Toolkit.getDefaultToolkit().getImage("reflectPowerup.png");
         }
         g.drawImage(powerupImage, owner.getX() + 5, owner.getY() + owner.getHeight() + 5, 10, 10, null);
         g.setColor(Color.black);
         g.drawString(Integer.toString(owner.getSpecialAmmo()), owner.getX() + 20, owner.getY() + owner.getHeight() + 15); 
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