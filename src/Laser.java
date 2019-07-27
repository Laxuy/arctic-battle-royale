//Seal special ability that shoots a hitscan laser weapon with brief delay
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.AlphaComposite;

public class Laser extends GameObject {
   private Handler handler;
   private Player owner;
   private int cooldown, lifetime, shortestDistance;
   private boolean faceRight;
   private ArrayList<ID> damaged;
      
   public Laser(int x, int y, int height, boolean faceRight, Player owner, ID id, Handler handler) {
      super(x,y,id);
      this.height = height;
      this.owner = owner;
      this.handler = handler;
      this.faceRight = faceRight;
      cooldown = 20;
      lifetime = 60;
      damaged = new ArrayList<ID>();
      solid = false;
      
      shortestDistance = 1000;
      for(int i = 0; i < handler.getObjectList().size(); i++) {
         GameObject tempObject = handler.getObjectList().get(i);                     
         if (tempObject.id == ID.Platform) {
            if (y + height > tempObject.y && y < tempObject.y + tempObject.height) {
               if (faceRight && tempObject.x > x) {
                  shortestDistance = Math.min(tempObject.x - x, shortestDistance);
               } else if (!faceRight && tempObject.x + tempObject.width < x) {
                  shortestDistance = Math.min(x - (tempObject.x + tempObject.width), shortestDistance);
               }
            }
         }
      }
      width = shortestDistance;      
   } 

   @Override
   public Rectangle getBounds() {
      if (faceRight) {
         return new Rectangle(x,y,width,height);
      } else {
         return new Rectangle(x - shortestDistance,y,width,height);
      }
   }
   
   @Override
   public void tick() {     
      collision();
      
      if (cooldown > 0) {
         cooldown--;
      }
      if (lifetime > 0) {
         lifetime--;
      } else if (lifetime == 0) {
         handler.removeObject(this);
      }
   }
   
   //Method to check collision with platforms, players, and other weapons
   protected void collision() {
      for(int i = 0; i < handler.getObjectList().size(); i++) {
         GameObject tempObject = handler.getObjectList().get(i); 
         
         if (!tempObject.equals(this)) {            
            if (tempObject.id == ID.Player1 || tempObject.id == ID.Player2 || tempObject.id == ID.Player3 || tempObject.id == ID.Player4) {
               if (getBounds().intersects(tempObject.getBounds()) && tempObject != owner && cooldown == 0 && !damaged.contains(tempObject.id)) {
                  Player tempPlayer = (Player)tempObject;
                  if (tempPlayer.getShielded()) {
                     tempPlayer.setShielded(false);
                  } else {
                     tempPlayer.setHealth(tempPlayer.getHealth() - 30);
                  }
                  damaged.add(tempPlayer.id);
               }    
            } else if (tempObject.id == ID.Bullet || tempObject.id == ID.ReflectBullet || tempObject.id == ID.Mine || tempObject.id == ID.Bamboo) {
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
      if (id == ID.Laser) {
         if (cooldown == 0) {
            g.setColor(Color.blue);
         } else {
            g.setColor(Color.white);
         }
      }
      Graphics2D g2d = (Graphics2D)g;
      g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
      if (faceRight) {
         g.fillRect(x,y,width,height);
      } else {
         g.fillRect(x - shortestDistance, y, width, height);
      }
      g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
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
   
   public int getLifetime() {
      return lifetime;
   }
   
   public void setLifetime(int lifetime) {
      this.lifetime = lifetime;
   }
   
   public int getShortestDistance() {
      return shortestDistance;
   }
   
   public void setShortestDistance(int shortestDistance) {
      this.shortestDistance = shortestDistance;
   }
   
   public boolean getFaceRight() {
      return faceRight;
   }
   
   public void setFaceRight(boolean faceRight) {
      this.faceRight = faceRight;
   }
   
   public ArrayList<ID> getDamaged() {
      return damaged;
   }
   
   public void setDamaged(ArrayList<ID> damaged) {
      this.damaged = damaged;
   }
}