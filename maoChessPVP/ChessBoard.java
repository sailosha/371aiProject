package maoChessPVP;

import javax.xml.validation.Validator;

/**
 * @author Yiwei Mao
 * Last modify on 2018/12/14 ${time}
 */
public class ChessBoard
{
	ChessInfo[] ChessList;

	public ChessInfo checkLocation(Location index)
	{
		for(int i=0;i<ChessList.length;i++)
		{
			if(ChessList[i].index.compairTo(index))
			{
				return ChessList[i];
			}
		}
		return ChessList[32];
	}

	public void move(Location old, Location next, int moveString )
    //1. default,
    //2. castling
    //3. enPassant
	{
	    switch(moveString)
        {
            case 1:
                checkLocation(next).index=new Location(-1,-1);
                checkLocation(next).isAlive=false;
                checkLocation(old).index=next;
                checkLocation(next).idMoved=true;
                break;
            case 2:
                break;
            case 3:
                Location temp = new Location(old.x,next.y);
                checkLocation(temp).index=new Location(-1,-1);
                checkLocation(temp).isAlive=false;
                checkLocation(old).index=next;
                checkLocation(next).idMoved=true;
                break;
                default:

        }
	}
}
