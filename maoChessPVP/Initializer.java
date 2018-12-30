package maoChessPVP;

/**
 * @author Yiwei Mao
 * Last modify on 2018/12/14 ${time}
 */
public class Initializer
{
	ChessInfo[] startInitialize()
	{
		ChessInfo[] newGame = new ChessInfo[33];
		newGame[0] = new ChessInfo(0,0,50,true,true,false,"BLACK_ROOK");
		newGame[1] = new ChessInfo(0,1,30,true,true,false,"BLACK_KNIGHT");
		newGame[2] = new ChessInfo(0,2,30,true,true,false,"BLACK_BISHOP");
		newGame[3] = new ChessInfo(0,3,90,true,true,false,"BLACK_QUEEN");
		newGame[4] = new ChessInfo(0,4,900,true,true,false,"BLACK_KING");
		newGame[5] = new ChessInfo(0,5,30,true,true,false,"BLACK_BISHOP");
		newGame[6] = new ChessInfo(0,6,30,true,true,false,"BLACK_KNIGHT");
		newGame[7] = new ChessInfo(0,7,50,true,true,false,"BLACK_ROOK");

		for(int i=0;i<8;i++)
		{
			newGame[8+i] = new ChessInfo(1,i,10,true,true,false,"BLACK_PAWN");
		}
		newGame[16] = new ChessInfo(7,0,50,false,true,false,"WHITE_ROOK");
		newGame[17] = new ChessInfo(7,1,30,false,true,false,"WHITE_KNIGHT");
		newGame[18] = new ChessInfo(7,2,30,false,true,false,"WHITE_BISHOP");
		newGame[19] = new ChessInfo(7,3,900,false,true,false,"WHITE_KING");
		newGame[20] = new ChessInfo(7,4,90,false,true,false,"WHITE_QUEEN");
		newGame[21] = new ChessInfo(7,5,30,false,true,false,"WHITE_BISHOP");
		newGame[22] = new ChessInfo(7,6,30,false,true,false,"WHITE_KNIGHT");
		newGame[23] = new ChessInfo(7,7,50,false,true,false,"WHITE_ROOK");


		for(int i=0;i<8;i++)
		{
			newGame[24+i] = new ChessInfo(6,i,10,false,true,false,"WHITE_PAWN");
		}
		newGame[32] = new ChessInfo(-1,-1,-1,false,false,false,"EMPTY");
		return newGame;
	}
}
