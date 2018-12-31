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
                checkLocation(next).isMoved=true;
                break;
            case 2:
                break;
            case 3:
                Location temp = new Location(old.x,next.y);
                checkLocation(temp).index=new Location(-1,-1);
                checkLocation(temp).isAlive=false;
                checkLocation(old).index=next;
                checkLocation(next).isMoved=true;
                break;
                default:
        }
	}

	public boolean winCheck()
    {
        if(ChessList[4].isAlive==false&&ChessList[19].isAlive==true)
        {
            System.out.println("White WIN!!");
            return true;
        }
        if(ChessList[4].isAlive==true&&ChessList[19].isAlive==false)
        {
            System.out.println("Black WIN!!");
            return true;
        }
        return false;
    }

    public void clearEnPassant(boolean claerBlack)
    {
        if(claerBlack)
        {
            for(int i=8;i<16;i++)
            {
                ChessList[i].enPassant=false;
            }
        }
        else
        {
            for(int i=24;i<32;i++)
            {
                ChessList[i].enPassant=false;
            }
        }
    }
}
