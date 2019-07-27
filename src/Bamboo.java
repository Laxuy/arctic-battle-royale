//Panda special ability bullet
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.Toolkit;
import java.awt.Image;

public class Bamboo extends GameObject {
   private Handler handler;
   private Player owner;
   private int cooldown;
   private ArrayList<ID> damaged;
   private Image bambooImage;
      
   public Bamboo(int x, int y, int width, int height, int velX, int velY, Player owner, ID id, Handler handler) {
      super(x,y,width,height,id);
      this.velX = velX;
      this.velY = velY;
      this.owner = owner;
      this.handler = handler;
      cooldown = 50;
      damaged = new ArrayList<ID>();
      solid = false;
      if (velX != 0) {
         bambooImage = Toolkit.getDefaultToolkit().getImage("bambooHorizontal.png");
      } else {
         bambooImage = Toolkit.getDefaultToolkit().getImage("bambooVertical.png");
      }
   } 

   @Override
   public Rectangle getBounds() {
      return new Rectangle(x,y,width,height);
   }
   
   @Override
   public void tick() { 
      if (cooldown > 0) {
         cooldown--;
      } else {     
         x += velX;
         y += velY;
      }
      
      if (x + width < 0 || x > Game.WIDTH) {
         handler.removeObject(this);
      }
      if (y + height < 0 || y > Game.HEIGHT) {
         handler.removeObject(this);
      }
      
      collision();
   }
   
   //Method to check collision with platforms, players, and other weapons
   public void collision() {
      for(int i = 0; i < handler.getObjectList().size(); i++) {
         GameObject tempObject = handler.getObjectList().get(i); 
         
         if (!tempObject.equals(this)) {            
            if (tempObject.id == ID.Player1 || tempObject.id == ID.Player2 || tempObject.id == ID.Player3 || tempObject.id == ID.Player4) {
               if (getBounds().intersects(tempObject.getBounds()) && tempObject != owner && !damaged.contains(tempObject.id) && cooldown == 0) {
                  Player tempPlayer = (Player)tempObject;
                  if (tempPlayer.getShielded()) {
                     tempPlayer.setShielded(false);
                  } else {
                     tempPlayer.setHealth(tempPlayer.getHealth() - 20);
                  }
                  damaged.add(tempPlayer.id);
               }    
            } else if (tempObject.id == ID.Bullet || tempObject.id == ID.ReflectBullet || tempObject.id == ID.Mine) {
               if (getBounds().intersects(tempObject.getBounds())) {
                  handler.removeObject(tempObject);
               }
            } else if (tempObject.id == ID.Drill || tempObject.id == ID.Bomb) {
               if (getBounds().intersects(tempObject.getBounds())) {
                  handler.addObject(new Explosion(tempObject.x - 32 + tempObject.width/2, tempObject.y - 32 + tempObject.height/2, 64, 64, ID.Explosion, handler));
                  handler.removeObject(tempObject);
               }
            }
         }
      }
   }
   
   @Override
   public void render(Graphics g) {
      g.drawImage(bambooImage,x,y,null);
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
   
   public ArrayList<ID> getDamaged() {
      return damaged;
   }
   
   public void setDamaged(ArrayList<ID> damaged) {
      this.damaged = damaged;
   }
}