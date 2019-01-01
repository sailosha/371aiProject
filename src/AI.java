public class AI
{
    int searchDepth;
    MoveValidator validator = new MoveValidator();
    BoardEvaluation evaluate = new BoardEvaluation();
    public void move(boolean black,ChessBoard gameBoard,int depth)
    {
        double alpha,beta;
        alpha=-99999;
        beta=99999;
        searchDepth = depth;

        //黑子为正数
        //白子为负数
        alphaBetaPruning(gameBoard,-999,999,depth,black);
    }

    public double alphaBetaPruning(ChessBoard gameBoard,double alpha,double beta,int depth,boolean aIBlack)
    {
        if(depth==0)
        {
            return evaluate.evaluate(gameBoard);
        }
        if(aIBlack)
        {
            for(int i=0;i<=15;i++)
            {
                for (int x=0;x<8;x++)
                {
                    for (int y=0;y<8;y++)
                    {
                        Location temp = new Location(x,y);
                        if(validator.AICheck(gameBoard,gameBoard.ChessList[i].index,temp,true))
                        {
                            double value = alphaBetaPruning(gameBoard,-alpha,-beta,depth-1,false);
                            gameBoard.Undo();
                            if(searchDepth==depth&&value>alpha)
                            {
                                validator.AICheck(gameBoard,gameBoard.ChessList[i].index,temp,true);
                            }
                            alpha=(value>alpha)?value:alpha;
                            if(alpha>=beta)
                            {
                                return alpha;
                            }
                        }
                    }
                }
            }
            return alpha;
        }
        else
        {
            for(int i=16;i<=31;i++)
            {
                for (int x=0;x<8;x++)
                {
                    for (int y=0;y<8;y++)
                    {
                        Location temp = new Location(x,y);
                        if(validator.AICheck(gameBoard,gameBoard.ChessList[i].index,temp,false))
                        {
                            double value = alphaBetaPruning(gameBoard,-alpha,-beta,depth-1,true);
                            gameBoard.Undo();
                            if(searchDepth==depth&&value>alpha)
                            {
                                validator.AICheck(gameBoard,gameBoard.ChessList[i].index,temp,false);
                            }
                            alpha=(value>alpha)?value:alpha;
                            if(alpha>=beta)
                            {
                                return alpha;
                            }
                        }
                    }
                }
            }
            return alpha;
        }
    }
}
