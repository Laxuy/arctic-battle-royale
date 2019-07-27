//Mine powerup that damages when stepped on, follows gravity
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Image;

public class Mine extends GameObject {
   private Handler handler;
   private Player owner;
   private int cooldown;
   private Image mine = Toolkit.getDefaultToolkit().getImage("mine.png");
      
   public Mine(int x, int y, int width, int height, int velY, Player owner, ID id, Handler handler) {
      super(x,y,width,height,id);
      this.velY = velY;
      this.owner = owner;
      this.handler = handler;
      cooldown = 50;
      solid = false;
   } 

   @Override
   public Rectangle getBounds() {
      return new Rectangle(x,y,width,height);
   }
   
   @Override
   public void tick() {     
      collision();
    
      y += velY;
      
      velY += 1;
      if (y + height < 0 || y > Game.HEIGHT) {
         handler.removeObject(this);
      }
      if (cooldown > 0) {
         cooldown--;
      }
   }
   
   //Method to check collision with platforms, players, and other weapons
   protected void collision() {
      for(int i = 0; i < handler.getObjectList().size(); i++) {
         GameObject tempObject = handler.getObjectList().get(i); 
         
         if (!tempObject.equals(this)) {            
            if (tempObject.id == ID.Platform) {
               if (velY > 0 && tempObject.y > y && velY > tempObject.y - (y+height)
               && x + width > tempObject.x && x < tempObject.x + tempObject.width) {
                  velY = 0;
                  y += tempObject.y - (y+height);
               }
            } else if (tempObject.id == ID.Player1 || tempObject.id == ID.Player2 || tempObject.id == ID.Player3 || tempObject.id == ID.Player4) {
               if (getBounds().intersects(tempObject.getBounds()) && (tempObject != owner || cooldown == 0)) {
                  Player tempPlayer = (Player)tempObject;
                  if (tempPlayer.getShielded()) {
                     tempPlayer.setShielded(false);
                  } else {
                     tempPlayer.setHealth(tempPlayer.getHealth() - 10);
                  }
                  handler.removeObject(this);
               }    
            } else if (tempObject.id == ID.Bullet || tempObject.id == ID.ReflectBullet || tempObject.id == ID.Mine) {
               if (getBounds().intersects(tempObject.getBounds())) {
                  handler.removeObject(this);
                  handler.removeObject(tempObject);
               }
            }
         }
      }
   }
   
   @Override
   public void render(Graphics g) {
      if (id == ID.Mine) {
         g.drawImage(mine, x, y, null);
      }
   }
   
   //Getters and Setters
   public Player getOwner() {
      return owner;
   }
   
   public void setOwner(Player owner) {
      this.owner = owner;
   } 
   
   public int getCooldown() {
      return cooldown;
   }
   
   public void setCooldown(int cooldown) {
      this.cooldown = cooldown;
   }
}