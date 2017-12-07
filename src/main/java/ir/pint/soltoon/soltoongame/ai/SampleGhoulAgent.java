package ir.pint.soltoon.soltoongame.ai;

import shared.data.Fighter;
import shared.data.action.Action;
import shared.data.action.Move;
import shared.data.action.Nothing;
import shared.data.action.Shoot;
import shared.data.map.*;

public class SampleGhoulAgent extends Fighter {


	public SampleGhoulAgent(GameObjectType type) {
		super(type);
	}

	@Override
	public void init() {

	}

	@Override
	public void lastThingsToDo(GameBoard gameBoard) {

	}

	@Override
	public Action getAction(GameBoard gb) {
		System.out.println(id);
		Direction dir;
		Cell cell;
		Cell now = gb.getObjectByID(gb.getMyID()).getCell();

		dir = Direction.up;
		cell = gb.getCellByDirection(now,dir);
		if (cell!=null && cell.gameObject ==null) return new Move(dir);

		dir = Direction.right;
		cell = gb.getCellByDirection(now,dir);
		if (cell!=null && cell.gameObject ==null) return new Move(dir);

		dir = Direction.down;
		cell = gb.getCellByDirection(now,dir);
		if (cell!=null && cell.gameObject ==null) return new Move(dir);

		dir = Direction.left;
		cell = gb.getCellByDirection(now,dir);
		if (cell!=null && cell.gameObject ==null) return new Move(dir);

		int x=gb.getWidth()-1, y=gb.getHeight()-1;
		if (now.getDistance(gb.getCellByIndex(x,y))<=type.getShootingRange()) {
			System.out.println("shootidam");
			return new Shoot(x, y);
		}
		return new Nothing();
	}
}