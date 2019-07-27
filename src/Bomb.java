//Bomb powerup that launches like a projectile and explodes
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Image;

public class Bomb extends GameObject {
   private Handler handler;
   private Player owner;
   private int cooldown, lifetime, gravityCounter;
   private Image bombImage;
      
   public Bomb(int x, int y, int width, int height, int velX, int velY, Player owner, ID id, Handler handler) {
      super(x,y,width,height,id);
      this.velX = velX;
      this.velY = velY;
      this.owner = owner;
      this.handler = handler;
      cooldown = 5;
      lifetime = 100;
      gravityCounter = 0;
      bombImage = Toolkit.getDefaultToolkit().getImage("bomb.png");
      solid = false;
   } 

   @Override
   public Rectangle getBounds() {
      return new Rectangle(x,y,width,height);
   }
   
   @Override
   public void tick() {     
      collision();
    
      x += velX;
      y += velY;
      
      gravityCounter++;
      if (gravityCounter >= 2) {
         velY += 1;
         gravityCounter = 0;
      }
      
      if (x + width < 0 || x > Game.WIDTH) {
         handler.removeObject(this);
      }
      
      if (y + height < 0 || y > Game.HEIGHT) {
         handler.removeObject(this);
      }
      
      if (cooldown > 0) {
         cooldown--;
      }
      if (lifetime > 0) {
         lifetime--;
      } else if (lifetime == 0) {
         handler.addObject(new Explosion(x - 32 + width/2, y - 32 + height/2, 64, 64, ID.Explosion, handler));
         handler.removeObject(this);
      }
   }
   
   //Method to check collision with platforms, players, and other weapons
   private void collision() {
      for(int i = 0; i < handler.getObjectList().size(); i++) {
         GameObject tempObject = handler.getObjectList().get(i); 
         
         if (!tempObject.equals(this)) {            
            if (tempObject.id == ID.Platform) {
               if (velX > 0 && tempObject.x > x && velX > tempObject.x - (x+width) 
               && y + height > tempObject.y && y < tempObject.y + tempObject.height) {
                  velX*= -1;
                  x += tempObject.x - (x+width);
               } else if (velX < 0 && tempObject.x < x && Math.abs(velX) > x - (tempObject.x + tempObject.width)
               && y + height > tempObject.y && y < tempObject.y + tempObject.height) {
                  velX*= -1;
                  x -= x - (tempObject.x + tempObject.width);
               }
               if (velY > 0 && tempObject.y > y && velY > tempObject.y - (y+height)
               && x + width > tempObject.x && x < tempObject.x + tempObject.width) {
                  velY = -10;
                  y += tempObject.y - (y+height);
               } else if (velY < 0 && tempObject.y < y && Math.abs(velY) > y - (tempObject.y + tempObject.height)
               && x + width > tempObject.x && x < tempObject.x + tempObject.width) {
                  velY*= -1;
                  y -= y - (tempObject.y + tempObject.height);
               }
            } else if (tempObject.id == ID.Player1 || tempObject.id == ID.Player2 || tempObject.id == ID.Player3 || tempObject.id == ID.Player4) {
               if (getBounds().intersects(tempObject.getBounds()) && (tempObject != owner || cooldown == 0)) {
                  handler.addObject(new Explosion(x - 32 + width/2, y - 32 + height/2, 64, 64, ID.Explosion, handler));
                  handler.removeObject(this);
               }    
            } else if (tempObject.id == ID.Bullet || tempObject.id == ID.ReflectBullet || tempObject.id == ID.Mine) {
               if (getBounds().intersects(tempObject.getBounds())) {
                  handler.removeObject(tempObject);
               }
            } else if (tempObject.id == ID.Drill || tempObject.id == ID.Bomb) {
               if (getBounds().intersects(tempObject.getBounds())) {
                  handler.addObject(new Explosion(x - 32 + width/2, y - 32 + height/2, 64, 64, ID.Explosion, handler));
                  handler.addObject(new Explosion(tempObject.x - 32 + tempObject.width/2, tempObject.y - 32 + tempObject.height/2, 64, 64, ID.Explosion, handler));
                  handler.removeObject(this);
                  handler.removeObject(tempObject);
               }
            }
         }
      }
   }
   
   @Override
   public void render(Graphics g) {
      g.drawImage(bombImage,x,y,null);
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
   
   public int getGravityCounter() {
      return gravityCounter;
   }
   
   public void setGravityCounter(int gravityCounter) {
      this.gravityCounter = gravityCounter;
   }
}