//Handles all key input for every player and the start screen
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter
{
   private Handler handler;
   private HUDHandler hudHandler;
   private Game game;
   
   public KeyInput(Handler handler, HUDHandler hudHandler, Game game) {
      super();
      this.handler = handler;
      this.hudHandler = hudHandler;
      this.game = game;
   }

   public void keyPressed(KeyEvent e) {
      int key = e.getKeyCode();
      
      if (game.getStarted()) {
         for(int i = 0; i < Game.getAlive().size(); i++) {
            GameObject tempObject = Game.getAlive().get(i);
         
            if(tempObject.getId() == ID.Player1) {
               Player tempPlayer = (Player)tempObject;
               if (tempPlayer.getHealth() > 0) {
                  if (key == KeyEvent.VK_W && !tempPlayer.getInAir()) {
                     tempPlayer.setVelY(-17);
                     tempPlayer.setInAir(true);
                  }
                  if (key == KeyEvent.VK_S && tempPlayer.getShootCooldown() == 0) {
                     tempPlayer.action();
                  }
                  if (key == KeyEvent.VK_D) {
                     tempPlayer.setRP(true);
                     tempPlayer.setVelX(5);
                     tempPlayer.setFaceRight(true);  
                  }          
                  if (key == KeyEvent.VK_A) {
                     tempPlayer.setLP(true);
                     tempPlayer.setVelX(-5);
                     tempPlayer.setFaceRight(false);  
                  }
                  if (key == KeyEvent.VK_Q && tempPlayer.getAbilityMeter() == 100) {
                     tempPlayer.ability();
                  }
               }
            } else if(tempObject.getId() == ID.Player2) {
               Player tempPlayer = (Player)tempObject;
               if (tempPlayer.getHealth() > 0) {
                  if (key == KeyEvent.VK_UP && !tempPlayer.getInAir()) {
                     tempPlayer.setVelY(-17);
                     tempPlayer.setInAir(true);
                  }
                  if (key == KeyEvent.VK_DOWN && tempPlayer.getShootCooldown() == 0) {
                     tempPlayer.action();
                  }
                  if (key == KeyEvent.VK_RIGHT) {
                     tempPlayer.setRP(true);
                     tempPlayer.setVelX(5); 
                     tempPlayer.setFaceRight(true);     
                  }      
                  if (key == KeyEvent.VK_LEFT) {
                     tempPlayer.setLP(true);
                     tempPlayer.setVelX(-5);
                     tempPlayer.setFaceRight(false);
                  }
                  if (key == KeyEvent.VK_SLASH && tempPlayer.getAbilityMeter() == 100) {
                     tempPlayer.ability();
                  }
               }
            } else if(tempObject.getId() == ID.Player3) {
               Player tempPlayer = (Player)tempObject;
               if (tempPlayer.getHealth() > 0) {
                  if (key == KeyEvent.VK_I && !tempPlayer.getInAir()) {
                     tempPlayer.setVelY(-17);
                     tempPlayer.setInAir(true);
                  }
                  if (key == KeyEvent.VK_K && tempPlayer.getShootCooldown() == 0) {
                     tempPlayer.action();
                  }
                  if (key == KeyEvent.VK_L) {
                     tempPlayer.setRP(true);
                     tempPlayer.setVelX(5); 
                     tempPlayer.setFaceRight(true);     
                  }      
                  if (key == KeyEvent.VK_J) {
                     tempPlayer.setLP(true);
                     tempPlayer.setVelX(-5);
                     tempPlayer.setFaceRight(false);
                  }
                  if (key == KeyEvent.VK_U && tempPlayer.getAbilityMeter() == 100) {
                     tempPlayer.ability();
                  }
               }
            } else if(tempObject.getId() == ID.Player4) {
               Player tempPlayer = (Player)tempObject;
               if (tempPlayer.getHealth() > 0) {
                  if (key == KeyEvent.VK_NUMPAD5 && !tempPlayer.getInAir()) {
                     tempPlayer.setVelY(-17);
                     tempPlayer.setInAir(true);
                  }
                  if (key == KeyEvent.VK_NUMPAD2 && tempPlayer.getShootCooldown() == 0) {
                     tempPlayer.action();
                  }
                  if (key == KeyEvent.VK_NUMPAD3) {
                     tempPlayer.setRP(true);
                     tempPlayer.setVelX(5); 
                     tempPlayer.setFaceRight(true);     
                  }      
                  if (key == KeyEvent.VK_NUMPAD1) {
                     tempPlayer.setLP(true);
                     tempPlayer.setVelX(-5);
                     tempPlayer.setFaceRight(false);
                  }
                  if (key == KeyEvent.VK_NUMPAD4 && tempPlayer.getAbilityMeter() == 100) {
                     tempPlayer.ability();
                  }
               }
            }
         
         }
      } else {
         for (int i = 0; i < hudHandler.getObjectList().size(); i++) {
            HUDObject tempObject = hudHandler.getObjectList().get(i);
            
            if (tempObject.getId() == ID.CharacterPanel) {
               CharacterPanel tempPanel = (CharacterPanel)tempObject;
               if (key == KeyEvent.VK_S && tempPanel.getPlayer() == ID.Player1) {
                  tempPanel.scrollNext();
               } else if (key == KeyEvent.VK_DOWN && tempPanel.getPlayer() == ID.Player2) {
                  tempPanel.scrollNext();
               } else if (key == KeyEvent.VK_K && tempPanel.getPlayer() == ID.Player3) {
                  tempPanel.scrollNext();
               } else if (key == KeyEvent.VK_NUMPAD2 && tempPanel.getPlayer() == ID.Player4) {
                  tempPanel.scrollNext();
               }
            }  
         }     
      }
      
   }
   
   public void keyReleased(KeyEvent e) {
      int key = e.getKeyCode();
      for(int i = 0; i < Game.getAlive().size(); i++) {
         GameObject tempObject = Game.getAlive().get(i);
         
         if(tempObject.getId() == ID.Player1) {
            Player tempPlayer = (Player)tempObject;
            if (tempPlayer.getHealth() > 0) {
               if (key == KeyEvent.VK_D) {
                  tempPlayer.setRP(false);
                  if(tempPlayer.getLP()){
                     tempPlayer.setVelX(-5);
                  } else{
                     tempPlayer.setVelX(0);
                  }
               }            
               if (key == KeyEvent.VK_A) {
                  tempPlayer.setLP(false);
                  if(tempPlayer.getRP()){
                     tempPlayer.setVelX(5);
                  } else{
                     tempPlayer.setVelX(0);
                  }
               }
            }
         } else if(tempObject.getId() == ID.Player2) {
            Player tempPlayer = (Player)tempObject;
            if (tempPlayer.getHealth() > 0) {
               if (key == KeyEvent.VK_RIGHT) {
                  tempPlayer.setRP(false);
                  if(tempPlayer.getLP()){
                     tempPlayer.setVelX(-5);
                  } else{
                     tempPlayer.setVelX(0);
                  }            
               }
               if (key == KeyEvent.VK_LEFT) {
                  tempPlayer.setLP(false);
                  if(tempPlayer.getRP()){
                     tempPlayer.setVelX(5);
                  } else{
                     tempPlayer.setVelX(0);
                  }
               }
            }
         } else if(tempObject.getId() == ID.Player3) {
            Player tempPlayer = (Player)tempObject;
            if (tempPlayer.getHealth() > 0) {
               if (key == KeyEvent.VK_L) {
                  tempPlayer.setRP(false);
                  if(tempPlayer.getLP()){
                     tempPlayer.setVelX(-5);
                  } else{
                     tempPlayer.setVelX(0);
                  }            
               }
               if (key == KeyEvent.VK_J) {
                  tempPlayer.setLP(false);
                  if(tempPlayer.getRP()){
                     tempPlayer.setVelX(5);
                  } else{
                     tempPlayer.setVelX(0);
                  }
               }
            }
         } else if(tempObject.getId() == ID.Player4) {
            Player tempPlayer = (Player)tempObject;
            if (tempPlayer.getHealth() > 0) {
               if (key == KeyEvent.VK_NUMPAD3) {
                  tempPlayer.setRP(false);
                  if(tempPlayer.getLP()){
                     tempPlayer.setVelX(-5);
                  } else{
                     tempPlayer.setVelX(0);
                  }            
               }
               if (key == KeyEvent.VK_NUMPAD1) {
                  tempPlayer.setLP(false);
                  if(tempPlayer.getRP()){
                     tempPlayer.setVelX(5);
                  } else{
                     tempPlayer.setVelX(0);
                  }
               }
            }
         }                     
      }
   }
}
