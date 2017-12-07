package ir.pint.soltoon.soltoongame.ai;

import shared.data.Player;
import shared.data.action.Action;
import shared.data.action.AddAgent;
import shared.data.action.Nothing;
import shared.data.map.GameBoard;
import shared.data.map.GameObjectType;

public class My extends Player {

   public My(Long id) {
      super(id);
   }

   @Override
   public void init() {
   }
   
   @Override
   public void lastThingsToDo(GameBoard gameBoard) {
   
   }
   
   @Override
   public Action getAction(GameBoard gb) {
      if (gb.getMoneyByID(id) >= GameObjectType.musketeer.getCost())
         if (gb.getCellByIndex(0,0).gameObject ==null)
            return new AddAgent(new SampleGhoulAgent(GameObjectType.musketeer),0,0);
         else
            return new Nothing();
      else
         return new Nothing();
   }
}
