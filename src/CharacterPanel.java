//HUD object part of the start screen that displays info about each character
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Image;

public class CharacterPanel extends HUDObject{
   private ID character, player;
   private ID[] availableCharacters;
   private CharacterPanel[] charPanels;
   private Image image;
      
   public CharacterPanel(int x, int y, ID[] availableCharacters, CharacterPanel[] charPanels, ID player, ID id) {
      super(x,y,id);
      character = ID.NoCharacter;
      this.player = player;
      this.availableCharacters = availableCharacters;
      this.charPanels = charPanels;
   } 
   
   @Override
   public void tick() {                 
      if (character == ID.Panda) {
         image = Toolkit.getDefaultToolkit().getImage("pandaPanel.png");
      } else if (character == ID.Seal) {
         image = Toolkit.getDefaultToolkit().getImage("sealPanel.png");
      } else if (character == ID.Dino) {
         image = Toolkit.getDefaultToolkit().getImage("dinoPanel.png");
      } else if (character == ID.Eagle) {
         image = Toolkit.getDefaultToolkit().getImage("eaglePanel.png");
      } else if (character == ID.NoCharacter) {
         if (player == ID.Player1) {
            image = Toolkit.getDefaultToolkit().getImage("p1CharPanel.png");
         } else if (player == ID.Player2) {
            image = Toolkit.getDefaultToolkit().getImage("p2CharPanel.png");
         } else if (player == ID.Player3) {
            image = Toolkit.getDefaultToolkit().getImage("p3CharPanel.png");
         } else if (player == ID.Player4) {
            image = Toolkit.getDefaultToolkit().getImage("p4CharPanel.png");
         }
      }      
   }
   
   //Method to scroll to the next character that is not taken
   public void scrollNext() {
      for (int i = 0; i < availableCharacters.length; i++) {
         ID tempCharacter = availableCharacters[i];
         if (tempCharacter == character) {
            character = ID.NoCharacter;
            for (int j = i + 1; j < availableCharacters.length; j++) {
               boolean taken = false;
               for (int k = 0; k < charPanels.length; k++) {
                  if (charPanels[k] != this && charPanels[k].getCharacter() == availableCharacters[j]) {
                     taken = true;
                     break;
                  }
               }
               if (!taken) {
                  character = availableCharacters[j];
                  break;
               }
            }
            break;
         }
      }
   }
   
   @Override
   public void render(Graphics g) {
      g.drawImage(image,x,y,null);
   }  
   
   //Getters and setters
   public ID getPlayer() {
      return player;
   }
   
   public void setPlayer(ID Player) {
      this.player = player;
   }
   
   public ID getCharacter() {
      return character;
   }
   
   public void setCharacter(ID character) {
      this.character = character;
   }
   
   public ID[] getAvailableCharacters() {
      return availableCharacters;
   }
   
   public void setAvailableCharacters(ID[] availableCharacters) {
      this.availableCharacters = availableCharacters;
   }
   
   public CharacterPanel[] getCharPanels() {
      return charPanels;
   }
   
   public void setAvailableCharacters(CharacterPanel[] charPanels) {
      this.charPanels = charPanels;
   }
}