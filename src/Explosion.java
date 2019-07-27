//Explosion object for bombs and drills that deals damage in an area and breaks platforms
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.Toolkit;
import java.awt.Image;

public class Explosion extends GameObject {
   private Handler handler;
   private int lifetime;
   private ArrayList<ID> damaged;
   private Image explosion = Toolkit.getDefaultToolkit().getImage("explosion.png");
      
   public Explosion(int x, int y, int width, int height, ID id, Handler handler) {
      super(x,y,width,height,id);
      this.handler = handler;
      lifetime = 10;
      damaged = new ArrayList<ID>();
      solid = false;
   } 

   @Override
   public Rectangle getBounds() {
      return new Rectangle(x,y,width,height);
   }
   
   @Override
   public void tick() {      
      if (lifetime > 0) {
         lifetime--;
      } else {
         handler.removeObject(this);
      }
      
      collision();
   }
   
   //Method to check collision with platforms, players, and other weapons
   public void collision() {
      for(int i = 0; i < handler.getObjectList().size(); i++) {
         GameObject tempObject = handler.getObjectList().get(i); 
         
         if (!tempObject.equals(this)) {            
            if (tempObject.id == ID.Platform) {
               Platform tempPlatform = (Platform)tempObject;
               if (getBounds().intersects(tempObject.getBounds())) {
                  if (tempPlatform.getBreakable()) {
                     tempPlatform.setRespawnTimer(500);
                     tempPlatform.disappear();
                  }
               }
            } else if (tempObject.id == ID.Player1 || tempObject.id == ID.Player2 || tempObject.id == ID.Player3 || tempObject.id == ID.Player4) {
               if (getBounds().intersects(tempObject.getBounds()) && !damaged.contains(tempObject.id)) {
                  Player tempPlayer = (Player)tempObject;
                  if (tempPlayer.getShielded()) {
                     tempPlayer.setShielded(false);
                  } else {
                     tempPlayer.setHealth(tempPlayer.getHealth() - 10);
                  }
                  damaged.add(tempPlayer.id);
               }    
            }
         }
      }
   }
   
   @Override
   public void render(Graphics g) {
      if (id == ID.Explosion) {
         g.drawImage(explosion, x, y, null);
      }
   }
   
   //Getters and setters
   public int getLifetime() {
      return lifetime;
   }
   
   public void setLifetime(int lifetime) {
      this.lifetime = lifetime;
   }
   
   public ArrayList<ID> getDamaged() {
      return damaged;
   }
   
   public void setDamaged(ArrayList<ID> damaged) {
      this.damaged = damaged;
   }
   
}