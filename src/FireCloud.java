//Dino special ability that blocks weapons and damages over time
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.AlphaComposite;
import java.awt.Toolkit;
import java.awt.Image;

public class FireCloud extends GameObject {
   private Handler handler;
   private Player owner;
   private int cooldown, lifetime;
   private boolean faceRight;
   private ArrayList<ID> damaged;
   private Image fireball;
      
   public FireCloud(int x, int y, int width, int height, Player owner, ID id, Handler handler) {
      super(x,y,width,height,id);
      this.owner = owner;
      this.handler = handler;
      cooldown = 50;
      lifetime = 500;
      damaged = new ArrayList<ID>();
      solid = false;     
      fireball = Toolkit.getDefaultToolkit().getImage("fireCloud.png");
   } 

   @Override
   public Rectangle getBounds() {
      return new Rectangle(x+(width - (int)(width/Math.sqrt(2)))/2,y+(height - (int)(height/Math.sqrt(2)))/2,(int)(width/Math.sqrt(2)),(int)(height/Math.sqrt(2)));
   }
   
   @Override
   public void tick() {     
      collision();
      
      if (cooldown%2 == 0) {
         x--;
         y--;
         width += 2;
         height += 2;
      }    
      if (cooldown > 0) {
         cooldown--;
      } else {
         cooldown = 50;
         damaged.clear();
      }
      if (lifetime > 0) {
         lifetime--;
      } else if (lifetime == 0) {
         handler.removeObject(this);
      }
   }
   
   //Method to check collision with players and other weapons
   protected void collision() {
      for(int i = 0; i < handler.getObjectList().size(); i++) {
         GameObject tempObject = handler.getObjectList().get(i); 
         
         if (!tempObject.equals(this)) {            
            if (tempObject.id == ID.Player1 || tempObject.id == ID.Player2 || tempObject.id == ID.Player3 || tempObject.id == ID.Player4) {
               if (getBounds().intersects(tempObject.getBounds()) && tempObject != owner && !damaged.contains(tempObject.id)) {
                  Player tempPlayer = (Player)tempObject;
                  if (tempPlayer.getShielded()) {
                     tempPlayer.setShielded(false);
                  } else {
                     tempPlayer.setHealth(tempPlayer.getHealth() - 15);
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
      Graphics2D g2d = (Graphics2D)g;
      if (cooldown <= 5) {
         g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
      } else {
         g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
      }
      g2d.drawImage(fireball, x, y, width, height, null);
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
   
   public ArrayList<ID> getDamaged() {
      return damaged;
   }
   
   public void setDamaged(ArrayList<ID> damaged) {
      this.damaged = damaged;
   }
}