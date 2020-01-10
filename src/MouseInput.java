//Handles mouse input on the start screen
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter
{
   private HUDHandler hudHandler;
   private Game game;
   
   public MouseInput(HUDHandler hudHandler, Game game) {
      super();
      this.hudHandler = hudHandler;
      this.game = game;
   }
   
   @Override
   public void mouseClicked(MouseEvent e) {
      if (!game.getStarted()) {
         for (int i = 0; i < hudHandler.getObjectList().size(); i++) {
            HUDObject tempObject = hudHandler.getObjectList().get(i);
         
            if (tempObject.getId() == ID.StartButton) {
               if (e.getX() > tempObject.getX() && e.getX() < tempObject.getX() + tempObject.getWidth()
               && e.getY() > tempObject.getY() && e.getY() < tempObject.getY() + tempObject.getHeight()) {
                  int numberPlayers = 0;
                  for (int j = 0; j < game.getCharPanels().length; j++) {
                     if (game.getCharPanels()[j].getCharacter() != ID.NoCharacter) {
                        numberPlayers++;
                     }
                  }
                  if (numberPlayers > 1) {
                     game.createStage();
                  }
               }
            }
         }   
      }
   }
}
