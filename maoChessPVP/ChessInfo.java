package maoChessPVP;
/**
 * @author Yiwei Mao
 * Last modify on 2018/12/14 ${time}
 */
public class ChessInfo
{
	Location index;
	int value;
	boolean isAlive;
	boolean isMoved;
	boolean blackSide;
	boolean enPassant;
	boolean blackAttacking;
	boolean whiteAttacking;
	String type;

	ChessInfo(int x,int y,int cost,boolean isBlack,boolean life,boolean moved,String name)
	{
		index = new Location(x,y);
		value = cost;
		isAlive = life;
		isMoved = moved;
		blackSide = isBlack;
		type = name;
		enPassant = false;
	}
}
