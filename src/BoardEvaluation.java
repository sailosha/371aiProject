/**
 * @author Yiwei Mao
 * Last modify on 2018/12/14 ${time}
 */
public class BoardEvaluation
{
	int evaluate(ChessBoard gameBoard)
	{
		int score = 0;
		for(int i=0;i<gameBoard.ChessList.length-1;i++)
		{
			if(i<=15)
			{
				score += gameBoard.ChessList[i].value;
			}
			else
			{
				score -= gameBoard.ChessList[i].value;
			}
		}
		return score;
	}
}
