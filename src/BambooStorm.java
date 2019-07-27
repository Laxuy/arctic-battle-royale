//Panda special ability that spawns bamboo objects
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.Random;

public class BambooStorm extends GameObject {
   private Handler handler;
   private Player owner;
   private Random r;
   private int cooldown, lifetime;
      
   public BambooStorm(int x, int y, int width, int height, Player owner, ID id, Handler handler) {
      super(x,y,width,height,id);
      this.owner = owner;
      this.handler = handler;
      r = new Random();
      cooldown = 0;
      lifetime = 400;
      solid = false;     
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
         int bambooLocation = r.nextInt(4);
         switch (bambooLocation) {
            case 0: 
               handler.addObject(new Bamboo(-192, r.nextInt(Game.HEIGHT - 176) + 32, 256, 64, 15, 0, owner, ID.Bamboo, handler));
               break;
            case 1:
               handler.addObject(new Bamboo(Game.WIDTH - 64, r.nextInt(Game.HEIGHT - 176) + 32, 256, 64, -15, 0, owner, ID.Bamboo, handler));
               break;
            case 2:
               handler.addObject(new Bamboo(r.nextInt(Game.WIDTH - 128) + 32, -192, 64, 256, 0, 15, owner, ID.Bamboo, handler));
               break;
            case 3:
               handler.addObject(new Bamboo(r.nextInt(Game.WIDTH - 128) + 32, Game.HEIGHT - 64, 64, 256, 0, -15, owner, ID.Bamboo, handler));
               break;
            default:
               break;
         }
         cooldown = 25;
      }
      if (lifetime > 0) {
         lifetime--;
      } else if (lifetime == 0) {
         handler.removeObject(this);
      }
   }
      
   @Override
   public void render(Graphics g) {
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
}