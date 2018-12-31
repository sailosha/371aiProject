package maoChessPVP;

public class AI
{
    int currenValue;
    public void move(boolean black,ChessBoard gameBoard)
    {
        BoardEvaluation evaluate = new BoardEvaluation();
        currenValue=evaluate.evaluate(gameBoard);
        if(black)
        {

        }
        else
        {

        }
    }
}
