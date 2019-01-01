import javax.xml.validation.Validator;
import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * @author Yiwei Mao
 * Last modify on 2018/12/14 ${time}
 */
public class ChessBoard
{
	ChessInfo[] ChessList;
	ArrayList<ChessInfo[]> moveList;

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
                moveList.add(ChessList);
                dangerIndicator();
                break;
            case 2:
                moveList.add(ChessList);
                dangerIndicator();
                break;
            case 3:
                Location temp = new Location(old.x,next.y);
                checkLocation(temp).index=new Location(-1,-1);
                checkLocation(temp).isAlive=false;
                checkLocation(old).index=next;
                checkLocation(next).isMoved=true;
                moveList.add(ChessList);
                dangerIndicator();
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
    public void Undo()
    {
        moveList.remove(moveList.size());
        ChessList=moveList.get(moveList.size());
    }

    public void dangerIndicator()
    {
        int x,y;
        for(int i=0;i<8;i++)
        {
            for (int j=0;j<8;j++)
            {
                x=i;
                y=j;
                Location temp = new Location(i,j);
                searchLoop: for(x=i+1;x<8;x++)
                {
                    Location attacker = new Location(x,y);
                    if(checkLocation(attacker).isAlive)
                    {
                        if(checkLocation(attacker).type.contains("QUEEN")||checkLocation(attacker).type.contains("ROOK"))
                        {
                            if(checkLocation(attacker).blackSide)
                            {
                                checkLocation(temp).blackAttacking=true;
                                break searchLoop;
                            }
                            else
                            {
                                checkLocation(temp).whiteAttacking=true;
                                break searchLoop;
                            }
                        }
                        if(abs(x-i)<=1&&abs(y-j)<=1)
                        {
                            if(checkLocation(attacker).type.contains("KING"))
                            {
                                if(checkLocation(attacker).blackSide)
                                {
                                    checkLocation(temp).blackAttacking=true;
                                    break searchLoop;
                                }
                                else
                                {
                                    checkLocation(temp).whiteAttacking=true;
                                    break searchLoop;
                                }
                            }
                        }
                    }
                }

                searchLoop: for(x=i-1;x>0;x--)
                {
                    Location attacker = new Location(x,y);
                    if(checkLocation(attacker).isAlive)
                    {
                        if(checkLocation(attacker).type.contains("QUEEN")||checkLocation(attacker).type.contains("ROOK"))
                        {
                            if(checkLocation(attacker).blackSide)
                            {
                                checkLocation(temp).blackAttacking=true;
                                break searchLoop;
                            }
                            else
                            {
                                checkLocation(temp).whiteAttacking=true;
                                break searchLoop;
                            }
                        }
                        if(abs(x-i)<=1&&abs(y-j)<=1)
                        {
                            if(checkLocation(attacker).type.contains("KING"))
                            {
                                if(checkLocation(attacker).blackSide)
                                {
                                    checkLocation(temp).blackAttacking=true;
                                    break searchLoop;
                                }
                                else
                                {
                                    checkLocation(temp).whiteAttacking=true;
                                    break searchLoop;
                                }
                            }
                        }
                    }
                }

                searchLoop:for(y=j+1;y<8;y++)
                {
                    Location attacker = new Location(x,y);
                    if(checkLocation(attacker).isAlive)
                    {
                        if(checkLocation(attacker).type.contains("QUEEN")||checkLocation(attacker).type.contains("ROOK"))
                        {
                            if(checkLocation(attacker).blackSide)
                            {
                                checkLocation(temp).blackAttacking=true;
                                break searchLoop;
                            }
                            else
                            {
                                checkLocation(temp).whiteAttacking=true;
                                break searchLoop;
                            }
                        }
                        if(abs(x-i)<=1&&abs(y-j)<=1)
                        {
                            if(checkLocation(attacker).type.contains("KING"))
                            {
                                if(checkLocation(attacker).blackSide)
                                {
                                    checkLocation(temp).blackAttacking=true;
                                    break searchLoop;
                                }
                                else
                                {
                                    checkLocation(temp).whiteAttacking=true;
                                    break searchLoop;
                                }
                            }
                        }
                    }
                }

                searchLoop:for(y=j-1;y>0;y--)
                {
                    Location attacker = new Location(x,y);
                    if(checkLocation(attacker).isAlive)
                    {
                        if(checkLocation(attacker).type.contains("QUEEN")||checkLocation(attacker).type.contains("ROOK"))
                        {
                            if(checkLocation(attacker).blackSide)
                            {
                                checkLocation(temp).blackAttacking=true;
                                break searchLoop;
                            }
                            else
                            {
                                checkLocation(temp).whiteAttacking=true;
                                break searchLoop;
                            }
                        }
                        if(abs(x-i)<=1&&abs(y-j)<=1)
                        {
                            if(checkLocation(attacker).type.contains("KING"))
                            {
                                if(checkLocation(attacker).blackSide)
                                {
                                    checkLocation(temp).blackAttacking=true;
                                    break searchLoop;
                                }
                                else
                                {
                                    checkLocation(temp).whiteAttacking=true;
                                    break searchLoop;
                                }
                            }
                        }
                    }
                }

                searchLoop:while(x<8||j<8)
                {
                    x+=1;
                    y+=1;
                    Location attacker = new Location(x,y);
                    if(checkLocation(attacker).isAlive)
                    {
                        if(checkLocation(attacker).type.contains("QUEEN")||checkLocation(attacker).type.contains("BISHOP"))
                        {
                            if(checkLocation(attacker).blackSide)
                            {
                                checkLocation(temp).blackAttacking=true;
                                break searchLoop;
                            }
                            else
                            {
                                checkLocation(temp).whiteAttacking=true;
                                break searchLoop;
                            }
                        }
                        if(abs(x-i)<=1&&abs(y-j)<=1)
                        {
                            if(checkLocation(attacker).type.contains("KING"))
                            {
                                if(checkLocation(attacker).blackSide)
                                {
                                    checkLocation(temp).blackAttacking=true;
                                    break searchLoop;
                                }
                                else
                                {
                                    checkLocation(temp).whiteAttacking=true;
                                    break searchLoop;
                                }
                            }
                        }
                        if(abs(x-i)==1&&abs(y-j)==1)
                        {
                            if(checkLocation(attacker).type.contains("BLACK_PAWN"))
                            {
                                checkLocation(temp).blackAttacking=true;
                                break searchLoop;
                            }
                        }
                    }

                }

                searchLoop:while(x<8||j>0)
                {
                    x+=1;
                    j-=1;
                    Location attacker = new Location(x,y);
                    if(checkLocation(attacker).isAlive)
                    {
                        if(checkLocation(attacker).type.contains("QUEEN")||checkLocation(attacker).type.contains("BISHOP"))
                        {
                            if(checkLocation(attacker).blackSide)
                            {
                                checkLocation(temp).blackAttacking=true;
                                break searchLoop;
                            }
                            else
                            {
                                checkLocation(temp).whiteAttacking=true;
                                break searchLoop;
                            }
                        }
                        if(abs(x-i)<=1&&abs(y-j)<=1)
                        {
                            if(checkLocation(attacker).type.contains("KING"))
                            {
                                if(checkLocation(attacker).blackSide)
                                {
                                    checkLocation(temp).blackAttacking=true;
                                    break searchLoop;
                                }
                                else
                                {
                                    checkLocation(temp).whiteAttacking=true;
                                    break searchLoop;
                                }
                            }
                        }
                        if(abs(x-i)==1&&abs(y-j)==1)
                        {
                            if(checkLocation(attacker).type.contains("BLACK_PAWN"))
                            {
                                checkLocation(temp).blackAttacking=true;
                                break searchLoop;
                            }
                        }
                    }
                }

                searchLoop:while(x>0||j<8)
                {
                    x-=1;
                    y+=1;
                    Location attacker = new Location(x,y);
                    if(checkLocation(attacker).isAlive)
                    {
                        if(checkLocation(attacker).type.contains("QUEEN")||checkLocation(attacker).type.contains("BISHOP"))
                        {
                            if(checkLocation(attacker).blackSide)
                            {
                                checkLocation(temp).blackAttacking=true;
                                break searchLoop;
                            }
                            else
                            {
                                checkLocation(temp).whiteAttacking=true;
                                break searchLoop;
                            }
                        }
                        if(abs(x-i)<=1&&abs(y-j)<=1)
                        {
                            if(checkLocation(attacker).type.contains("KING"))
                            {
                                if(checkLocation(attacker).blackSide)
                                {
                                    checkLocation(temp).blackAttacking=true;
                                    break searchLoop;
                                }
                                else
                                {
                                    checkLocation(temp).whiteAttacking=true;
                                    break searchLoop;
                                }
                            }
                        }
                        if(abs(x-i)==1&&abs(y-j)==1)
                        {
                            if(checkLocation(attacker).type.contains("WHITE_PAWN"))
                            {
                                checkLocation(temp).whiteAttacking=true;
                                break searchLoop;
                            }
                        }
                    }

                }

                searchLoop:while(x>0||j>0)
                {
                    x-=1;
                    y-=1;
                    Location attacker = new Location(x,y);
                    if(checkLocation(attacker).isAlive)
                    {
                        if(checkLocation(attacker).type.contains("QUEEN")||checkLocation(attacker).type.contains("BISHOP"))
                        {
                            if(checkLocation(attacker).blackSide)
                            {
                                checkLocation(temp).blackAttacking=true;
                                break searchLoop;
                            }
                            else
                            {
                                checkLocation(temp).whiteAttacking=true;
                                break searchLoop;
                            }
                        }
                        if(abs(x-i)<=1&&abs(y-j)<=1)
                        {
                            if(checkLocation(attacker).type.contains("KING"))
                            {
                                if(checkLocation(attacker).blackSide)
                                {
                                    checkLocation(temp).blackAttacking=true;
                                    break searchLoop;
                                }
                                else
                                {
                                    checkLocation(temp).whiteAttacking=true;
                                    break searchLoop;
                                }
                            }
                        }
                        if(abs(x-i)==1&&abs(y-j)==1)
                        {
                            if(checkLocation(attacker).type.contains("WHITE_PAWN"))
                            {
                                checkLocation(temp).whiteAttacking=true;
                                break searchLoop;
                            }
                        }
                    }
                }
                Location one = new Location(x+1,y+2);
                Location two = new Location(x+1,y-2);
                Location three = new Location(x-1,y+2);
                Location four = new Location(x-1,y-2);
                Location five = new Location(x+2,y+2);
                Location six = new Location(x+2,y-2);
                Location seven = new Location(x+2,y+1);
                Location eight = new Location(x+2,y-1);
                if(checkLocation(one).isAlive&&checkLocation(one).type.contains("KNIGHT"))
                {
                    if(checkLocation(one).blackSide)
                    {
                        checkLocation(temp).blackAttacking=true;
                    }
                    else
                        {
                            checkLocation(temp).whiteAttacking=true;
                        }
                }
                if(checkLocation(two).isAlive&&checkLocation(two).type.contains("KNIGHT"))
                {
                    if(checkLocation(two).blackSide)
                    {
                        checkLocation(temp).blackAttacking=true;
                    }
                    else
                        {
                            checkLocation(temp).whiteAttacking=true;
                        }
                }
                if(checkLocation(three).isAlive&&checkLocation(three).type.contains("KNIGHT"))
                {
                    if(checkLocation(three).blackSide)
                    {
                        checkLocation(temp).blackAttacking=true;
                    }
                    else
                        {
                            checkLocation(temp).whiteAttacking=true;
                        }
                }
                if(checkLocation(four).isAlive&&checkLocation(four).type.contains("KNIGHT"))
                {
                    if(checkLocation(four).blackSide)
                    {
                        checkLocation(temp).blackAttacking=true;
                    }
                    else
                        {
                            checkLocation(temp).whiteAttacking=true;
                        }
                }
                if(checkLocation(five).isAlive&&checkLocation(five).type.contains("KNIGHT"))
                {
                    if(checkLocation(five).blackSide)
                    {
                        checkLocation(temp).blackAttacking=true;
                    }
                    else
                        {
                            checkLocation(temp).whiteAttacking=true;
                        }
                }
                if(checkLocation(six).isAlive&&checkLocation(six).type.contains("KNIGHT"))
                {
                    if(checkLocation(six).blackSide)
                    {
                        checkLocation(temp).blackAttacking=true;
                    }
                    else
                        {
                            checkLocation(temp).whiteAttacking=true;
                        }
                }
                if(checkLocation(seven).isAlive&&checkLocation(seven).type.contains("KNIGHT"))
                {
                    if(checkLocation(seven).blackSide)
                    {
                        checkLocation(temp).blackAttacking=true;
                    }
                    else
                        {
                            checkLocation(temp).whiteAttacking=true;
                        }
                }
                if(checkLocation(eight).isAlive&&checkLocation(eight).type.contains("KNIGHT"))
                {
                    if (checkLocation(eight).blackSide)
                    {
                        checkLocation(temp).blackAttacking = true;
                    }
                    else
                        {
                            checkLocation(temp).whiteAttacking = true;
                        }
                }
            }
        }
    }
}
