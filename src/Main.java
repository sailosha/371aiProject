/**
 * @author Yiwei Mao
 * Last modify on 2018/12/14 ${time}
 */
import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
    {
        AI pc = new AI();
	    int side;
	    String moveString;
	    MoveValidator validator = new MoveValidator();
    	Scanner kb = new Scanner(System.in);
	    System.out.println("Please choose your side.\n1.Black\n2.White");
	    side = kb.nextInt();
	    while((side != 1) && (side != 2))
	    {
	    	System.out.println("Invalid number\nPlease choose your side.\n1.Black\n2.White");
		    side = kb.nextInt();
	    }
	    boolean playerIsBlack = side == 1;
    	ChessBoard gameBoard = new ChessBoard();
        Initializer start = new Initializer();
        BoardPrinter printer = new BoardPrinter();
        gameBoard.ChessList = start.startInitialize();
        gameBoard.moveList.add(gameBoard.ChessList);
        System.out.println("Please enter search depth");
        int depth = kb.nextInt();
        printer.print(gameBoard);
        moveString=kb.nextLine();
        if(playerIsBlack)
        {
            System.out.println("Black Move!");
            moveString=kb.nextLine();
            while(!moveString.contains("QUITE")&&!gameBoard.winCheck())
            {
                while (!validator.UserCheck(gameBoard, moveString, true))
                {
                    System.out.println("Invalid enter\nPlease enter again");
                    printer.print(gameBoard);
                    moveString = kb.nextLine();
                }
                printer.print(gameBoard);
                gameBoard.clearEnPassant(false);
                //ai HERE
                pc.move(false,gameBoard,depth);

                while (!validator.UserCheck(gameBoard, moveString,false))
                {
                    System.out.println("1Invalid enter\nPlease enter again");
                    printer.print(gameBoard);
                    moveString = kb.nextLine();
                }
                printer.print(gameBoard);
                gameBoard.clearEnPassant(true);
            }
        }
        else
            {
            System.out.println("Black Move!");
            //AI HERE
            /*
             */
            pc.move(true,gameBoard,depth);
            System.out.println("White Move!");
            moveString = kb.nextLine();
            while (!moveString.contains("QUITE")&&!gameBoard.winCheck())
            {
                while (!validator.UserCheck(gameBoard, moveString, playerIsBlack))
                {
                    System.out.println("Invalid enter\nPlease enter again");
                    printer.print(gameBoard);
                    moveString = kb.nextLine();
                }
                printer.print(gameBoard);
                gameBoard.clearEnPassant(true);
                /*
                AI here
                 */
                while (!validator.UserCheck(gameBoard, moveString, !playerIsBlack))
                {
                    System.out.println("Invalid enter\nPlease enter again");
                    printer.print(gameBoard);
                    moveString = kb.nextLine();
                }
                printer.print(gameBoard);
                gameBoard.clearEnPassant(false);
            }
        }
    }
}
