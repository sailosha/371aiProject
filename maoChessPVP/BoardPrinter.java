package maoChessPVP;

/**
 * @author Yiwei Mao
 * Last modify on 2018/12/14 ${time}
 */
public class BoardPrinter
{
	public void print(ChessBoard gameBoard)
	{
		char a = 'a';
		System.out.print(" ");
		for (int l = 0; l < 8; l++)
		{
			System.out.print(String.format("%3s", a));
			a++;
		}
		System.out.println("\r");
		for (int i=0;i<8;i++)
		{
			System.out.print(8-i+" ");
			for(int j=0;j<8;j++)
			{
				Location position = new Location(i,j);
				if (gameBoard.checkLocation(position).type.equals("EMPTY"))
				{
					System.out.print("   ");
				}
				if (gameBoard.checkLocation(position).isAlive)
				{

					switch (gameBoard.checkLocation(position).type)
					{
						case "BLACK_PAWN":
							System.out.print("\u265F ");
							break;
						case "BLACK_ROOK":
							System.out.print("\u265C ");
							break;
						case "BLACK_KNIGHT":
							System.out.print("\u265E ");
							break;
						case "BLACK_BISHOP":
							System.out.print("\u265D ");
							break;
						case "BLACK_QUEEN":
							System.out.print("\u265B ");
							break;
						case "BLACK_KING":
							System.out.print("\u265A ");
							break;
						case "WHITE_PAWN":
							System.out.print("\u2659 ");
							break;
						case "WHITE_ROOK":
							System.out.print("\u2656 ");
							break;
						case "WHITE_KNIGHT":
							System.out.print("\u2658 ");
							break;
						case "WHITE_BISHOP":
							System.out.print("\u2657 ");
							break;
						case "WHITE_QUEEN":
							System.out.print("\u2655 ");
							break;
						case "WHITE_KING":
							System.out.print("\u2654 ");
							break;
						default:
							System.out.print("   ");
							break;
					}
				}
			}
			System.out.println();
		}
	}
}
