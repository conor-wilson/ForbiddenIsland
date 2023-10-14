package cards;

import board.Board;
import ui.SpecialActionView;

public interface ActionCard{
	public static final SpecialActionView ui = SpecialActionView.getInstance();
	public static final Board board = Board.getInstance();
	
	public abstract void use();
}
