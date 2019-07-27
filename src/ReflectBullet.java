//Reflect bullet powerup that reflects multiple times before disappearing
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;

public class ReflectBullet extends Bullet {
   int bounces, cooldown;

   public ReflectBullet(int x, int y, int width, int height, int velX, Player owner, ID id, Handler handler) {
      super(x,y,width,height,velX,owner,id,handler);
      bounces = 5;
      cooldown = 5;
   } 
   
   @Override
   public void tick() {
      super.tick();
      if (cooldown > 0) {
         cooldown--;
      }
   }
   
   //Method to check collision with platforms, players, and other weapons
   @Override
   public void collision() {
      for(int i = 0; i < handler.getObjectList().size(); i++) {
         GameObject tempObject = handler.getObjectList().get(i); 
         
         if (!tempObject.equals(this)) {            
            if (tempObject.id == ID.Platform) {
               if (owner.getCharacter() == ID.Seal) {
                  if (velY > 0 && tempObject.y > y && velY > tempObject.y - (y+height)
                     && x + width > tempObject.x && x < tempObject.x + tempObject.width) {
                     velY = -5;
                     y += tempObject.y - (y+height);
                  }
               }
               if (velX > 0 && tempObject.x > x && velX > tempObject.x - (x+width) 
                  && y + height > tempObject.y && y < tempObject.y + tempObject.height) {
                  velX *= -1;
                  x += tempObject.x - (x+width);
                  if (bounces > 0) {
                     bounces--;
                  }
               } else if (velX < 0 && tempObject.x < x && Math.abs(velX) > x - (tempObject.x + tempObject.width)
                  && y + height > tempObject.y && y < tempObject.y + tempObject.height) {
                  velX *= -1;
                  x -= x - (tempObject.x + tempObject.width);
                  if (bounces > 0) {
                     bounces--;
                  }
               }
               if (bounces == 0) {
                  handler.removeObject(this);
               }
            } else if (tempObject.id == ID.Player1 || tempObject.id == ID.Player2 || tempObject.id == ID.Player3 || tempObject.id == ID.Player4) {
               if (getBounds().intersects(tempObject.getBounds())) {
                  if (tempObject != owner) {
                     Player tempPlayer = (Player)tempObject;
                     if (owner.getCharacter() == ID.Dino) {
                        handler.addObject(new Explosion(x - 32 + width/2, y - 32 + height/2, 64, 64, ID.Explosion, handler));
                     } else {
                        if (tempPlayer.getShielded()) {
                           tempPlayer.setShielded(false);
                        }else if (owner.getCharacter() == ID.Eagle) {
                           tempPlayer.setHealth(tempPlayer.getHealth() - 5);
                        } else {
                           tempPlayer.setHealth(tempPlayer.getHealth() - 10);
                        }
                     }
                     handler.removeObject(this);
                  } else if (cooldown == 0) {
                     handler.removeObject(this);
                  }
               }    
            } else if (tempObject.id == ID.Bullet || tempObject.id == ID.ReflectBullet) {
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
      g.drawImage(bulletImage,x,y,null);
   }
   
   //Getters and Setters
   public int getBounces() {
      return bounces;
   }
   
   public void setBounces(int bounces) {
      this.bounces = bounces;
   }
   
   public int getCooldown() {
      return cooldown;
   }
   
   public void setCooldown(int cooldown) {
      this.cooldown = cooldown;
   }
}
