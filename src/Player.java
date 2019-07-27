//Player class that handles all actions like shooting and using special abilities
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.AlphaComposite;
import java.awt.Toolkit;
import java.awt.Image;

public class Player extends GameObject {
   private Handler handler;
   private HUDHandler hudHandler;
   private boolean inAir, faceRight, shielded;
   private int health, shootCooldown, specialAmmo, abilityMeter, abilityCounter;
   private ID powerup, character;
   private boolean lP, rP;
   private HealthBar healthBar;
   private PlayerPowerup playerPowerup;
   private AbilityBar abilityBar;
   private Color shieldOutline;
   private Image charRight, charLeft;
      
   public Player(int x, int y, int width, int height, ID id, ID character, Handler handler, HUDHandler hudHandler) {
      super(x,y,width,height,id);
      this.character = character;
      this.handler = handler; 
      this.hudHandler = hudHandler;
      solid = true;  
      
      inAir = false;
      faceRight = true;
      shielded = false;  
      health = 100;
      shootCooldown = 0;
      specialAmmo = 0;
      abilityMeter = 0;
      abilityCounter = 0;
      powerup = ID.NoPowerup;
   
      lP = false;
      rP = false;
      
      shieldOutline = new Color(0, 128, 255);
      
      healthBar = new HealthBar(x, y - 10, width, 5, this, ID.HealthBar);
      playerPowerup = new PlayerPowerup(x, y, width, 10, this, ID.PlayerPowerup);
      if (id == ID.Player1) {
         abilityBar = new AbilityBar(50, Game.HEIGHT - 75, 100, 16, this, ID.AbilityBar);
      } else if (id == ID.Player2) {
         abilityBar = new AbilityBar(300, Game.HEIGHT - 75, 100, 16, this, ID.AbilityBar);
      } else if (id == ID.Player3) {
         abilityBar = new AbilityBar(550, Game.HEIGHT - 75, 100, 16, this, ID.AbilityBar);
      } else if (id == ID.Player4) {
         abilityBar = new AbilityBar(800, Game.HEIGHT - 75, 100, 16, this, ID.AbilityBar);
      }
      if (character == ID.Panda) {
         charRight = Toolkit.getDefaultToolkit().getImage("pandaRight.png");
         charLeft = Toolkit.getDefaultToolkit().getImage("pandaLeft.png");
      } else if (character == ID.Seal) {
         charRight = Toolkit.getDefaultToolkit().getImage("sealRight.png");
         charLeft = Toolkit.getDefaultToolkit().getImage("sealLeft.png");
      } else if (character == ID.Dino) {
         charRight = Toolkit.getDefaultToolkit().getImage("dinoRight.png");
         charLeft = Toolkit.getDefaultToolkit().getImage("dinoLeft.png");
      } else if (character == ID.Eagle) {
         charRight = Toolkit.getDefaultToolkit().getImage("eagleRight.png");
         charLeft = Toolkit.getDefaultToolkit().getImage("eagleLeft.png");
      }
      
      hudHandler.addObject(healthBar);
      hudHandler.addObject(playerPowerup);
      hudHandler.addObject(abilityBar);
   } 
    
   @Override
   public Rectangle getBounds() {
      return new Rectangle(x,y,width,height);
   }
   
   @Override
   public void tick() {
      collision();
      
      if (velY > 0) {
         inAir = true;
      }
      x += velX;
      y += velY;
      
      velY += 1;  
      
      if (rP) {
         velX = 5;
      } else if (lP) {
         velX = -5;
      }
            
      x = Game.clamp(x, 0, Game.WIDTH - width);
      y = Game.clamp(y, 0, Game.HEIGHT - height - 32);
      
      if (shootCooldown > 0)
         shootCooldown--; 
      if (health <= 0) {
         hudHandler.removeObject(healthBar);
         hudHandler.removeObject(playerPowerup);
         hudHandler.removeObject(abilityBar);
         handler.removeObject(this);
         Game.getAlive().remove(this);
      }          
      
      if (character == ID.Panda) {
         abilityCounter++;
      } else if (character == ID.Seal) {
         abilityCounter += 2;
      } else if (character == ID.Dino) {
         abilityCounter++;
      } else if (character == ID.Eagle) {
         abilityCounter += 3;
      } else {
         abilityCounter += 2;
      }
      if (abilityCounter >= 30) {
         if (abilityMeter < 100) {
            abilityMeter++;
         }
         abilityCounter = 0;
      }
   }
   
   //Method to check collision with platforms
   private void collision() {
      for(int i = 0; i < handler.getObjectList().size(); i++) {
         GameObject tempObject = handler.getObjectList().get(i);
      
         if (y >= Game.HEIGHT - height - 32) {
            velY = 0;
            inAir = false;
         }
         
         if (!tempObject.equals(this)) {              
            if (tempObject.solid) {
               if (velX > 0 && tempObject.x > x && velX > tempObject.x - (x+width) 
               && y + height > tempObject.y && y < tempObject.y + tempObject.height) {
                  velX = 0;
                  x += tempObject.x - (x+width);
               } else if (velX < 0 && tempObject.x < x && Math.abs(velX) > x - (tempObject.x + tempObject.width)
               && y + height > tempObject.y && y < tempObject.y + tempObject.height) {
                  velX = 0;
                  x -= x - (tempObject.x + tempObject.width);
               }
               if (velY > 0 && tempObject.y > y && velY > tempObject.y - (y+height)
               && x + width > tempObject.x && x < tempObject.x + tempObject.width) {
                  velY = 0;
                  y += tempObject.y - (y+height);
                  inAir = false;
               } else if (velY < 0 && tempObject.y < y && Math.abs(velY) > y - (tempObject.y + tempObject.height)
               && x + width > tempObject.x && x < tempObject.x + tempObject.width) {
                  velY = 0;
                  y -= y - (tempObject.y + tempObject.height);
               }
            }
         }           
      }
   }
   
   //Method to attack corresponding to shoot button
   public void action() {
      if (powerup == ID.NoPowerup) {
         if (character == ID.Seal) {
            if (faceRight) {
               handler.addObject(new Bullet(x + width, y + height/2 - 5, 
                           32, 10, 6, this, ID.Bullet, handler));
            } else {
               handler.addObject(new Bullet(x - 32, y + height/2 - 5, 
                           32, 10, -6, this, ID.Bullet, handler));
            }
         } else if (character == ID.Eagle) {
            if (faceRight) {
               handler.addObject(new Bullet(x + width, y + 5, 
                           32, 10, 8, this, ID.Bullet, handler));
               handler.addObject(new Bullet(x + width, y + height - 15, 
                           32, 10, 8, this, ID.Bullet, handler));
            } else {
               handler.addObject(new Bullet(x - 32, y + 5, 
                           32, 10, -8, this, ID.Bullet, handler));
               handler.addObject(new Bullet(x - 32, y + height - 15, 
                           32, 10, -8, this, ID.Bullet, handler));
            }
         } else {
            if (faceRight) {
               handler.addObject(new Bullet(x + width, y + height/2 - 5, 
                           32, 10, 8, this, ID.Bullet, handler));
            } else {
               handler.addObject(new Bullet(x - 32, y + height/2 - 5, 
                           32, 10, -8, this, ID.Bullet, handler));
            }
         }
         if (character == ID.Dino) {
            shootCooldown += 15;
         } else if (character == ID.Eagle) {
            shootCooldown += 20;
         }
      } else if (powerup == ID.DrillPowerup) {
         if (faceRight) {
            handler.addObject(new Drill(x + width, y + height/2 - 12, 
                        32, 24, 6, this, ID.Drill, handler));
         } else {
            handler.addObject(new Drill(x - 32, y + height/2 - 12, 
                        32, 24, -6, this, ID.Drill, handler));
         }
      } else if (powerup == ID.BombPowerup) {
         if (faceRight) {
            handler.addObject(new Bomb(x + width, y, 
                        32, 32, 8, -10, this, ID.Bomb, handler));
         } else {
            handler.addObject(new Bomb(x - 32, y, 
                        32, 32, -8, -10, this, ID.Bomb, handler));
         }
      } else if (powerup == ID.MinePowerup) {
         handler.addObject(new Mine(x, y + height - 8, 
                        32, 8, 6, this, ID.Mine, handler));
      } else if (powerup == ID.ReflectBulletPowerup) {
         if (character == ID.Seal) {
            if (faceRight) {
               handler.addObject(new ReflectBullet(x + width, y + height/2 - 5, 
                           32, 10, 6, this, ID.ReflectBullet, handler));
            } else {
               handler.addObject(new ReflectBullet(x - 32, y + height/2 - 5, 
                           32, 10, -6, this, ID.ReflectBullet, handler));
            }
         } else if (character == ID.Eagle) {
            if (faceRight) {
               handler.addObject(new ReflectBullet(x + width, y + 5, 
                           32, 10, 8, this, ID.ReflectBullet, handler));
               handler.addObject(new ReflectBullet(x + width, y + height - 15, 
                           32, 10, 8, this, ID.ReflectBullet, handler));
            } else {
               handler.addObject(new ReflectBullet(x - 32, y + 5, 
                           32, 10, -8, this, ID.ReflectBullet, handler));
               handler.addObject(new ReflectBullet(x - 32, y + height - 15, 
                           32, 10, -8, this, ID.ReflectBullet, handler));
            }
         } else {
            if (faceRight) {
               handler.addObject(new ReflectBullet(x + width, y + height/2 - 5, 
                           32, 10, 8, this, ID.ReflectBullet, handler));
            } else {
               handler.addObject(new ReflectBullet(x - 32, y + height/2 - 5, 
                           32, 10, -8, this, ID.ReflectBullet, handler));
            }
         } 
         if (character == ID.Dino) {
            shootCooldown += 15;
         } else if (character == ID.Eagle) {
            shootCooldown += 20;
         }        
      }
      if (specialAmmo > 1) {
         specialAmmo--;
      } else if (powerup != ID.NoPowerup) {
         powerup = ID.NoPowerup;
      }
      shootCooldown += 25;
   }
   
   //Method to attack using special ability
   public void ability() {
      if (character == ID.Panda) {
         handler.addObject(new BambooStorm(0, 0, 0, 0, this, ID.BambooStorm, handler));
      } else if (character == ID.Seal) {
         if (faceRight) {
            handler.addObject(new Laser(x + width, y + height/2 - 12, 
                  24, faceRight, this, ID.Laser, handler));
         } else {
            handler.addObject(new Laser(x, y + height/2 - 12, 
                  24, faceRight, this, ID.Laser, handler));
         }
      } else if (character == ID.Dino) {
         handler.addObject(new FireCloud(x + width/2 - 150, y + height/2 - 150, 
               300, 300, this, ID.FireCloud, handler));
      } else if (character == ID.Eagle) {
         if (faceRight) {
            handler.addObject(new TeleportBullet(x + width, y + height/2 - 5, 
                  32, 10, 25, this, ID.TeleportBullet, handler));
         } else {
            handler.addObject(new TeleportBullet(x - 32, y + height/2 - 5, 
                  32, 10, -25, this, ID.TeleportBullet, handler));         
         }
      }
      shootCooldown += 25;
      abilityMeter = 0;
   }
   
   @Override
   public void render(Graphics g) {
      if (faceRight) {
         g.drawImage(charRight, x, y, null);
      } else {            
         g.drawImage(charLeft, x, y, null);
      }         
      
      if (shielded) {
         g.setColor(Color.cyan);
         Graphics2D g2d = (Graphics2D)g;
         g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
         g2d.fillOval(x - 10,y - 10,width + 20,height + 20);
         g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
         g2d.setColor(shieldOutline);
         g2d.drawOval(x - 10,y - 10,width + 20,height + 20);
      } 
   }  
   
   //Getters and Setters
   public ID getCharacter() {
      return character;
   }
   
   public void setCharacter(ID character) {
      this.character = character;
   }
   
   public boolean getInAir() {
      return inAir;
   }
   
   public void setInAir(boolean inAir) {
      this.inAir = inAir;
   }
   
   public boolean getFaceRight() {
      return faceRight;
   }
   
   public void setFaceRight(boolean faceRight) {
      this.faceRight = faceRight;
   }
   
   public boolean getShielded() {
      return shielded;
   }
   
   public void setShielded(boolean shielded) {
      this.shielded = shielded;
   }
   
   public int getHealth() {
      return health;
   }
   
   public void setHealth(int health) {
      this.health = health;
   }
   
   public int getShootCooldown() {
      return shootCooldown;
   }
   
   public void setShootCooldown(int shootCooldown) {
      this.shootCooldown = shootCooldown;
   }
   
   public int getSpecialAmmo() {
      return specialAmmo;
   }
   
   public void setSpecialAmmo(int specialAmmo) {
      this.specialAmmo = specialAmmo;
   }
   
   public int getAbilityMeter() {
      return abilityMeter;
   }
   
   public void setAbilityMeter(int abilityMeter) {
      this.abilityMeter = abilityMeter;
   }
   
   public int getAbilityCounter() {
      return abilityCounter;
   }
   
   public void setAbilityCounter(int abilityCounter) {
      this.abilityCounter = abilityCounter;
   }
   
   public ID getPowerup() {
      return powerup;
   }
   
   public void setPowerup(ID powerup) {
      this.powerup = powerup;
   }

   public boolean getLP() {
      return lP;
   }
   
   public void setLP(boolean lP) {
      this.lP = lP;
   }
   public boolean getRP() {
      return rP;
   }
   
   public void setRP(boolean rP) {
      this.rP = rP;
   }
}