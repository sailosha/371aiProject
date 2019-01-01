import java.lang.reflect.GenericArrayType;

import static java.lang.Math.abs;

/**
 * @author Yiwei Mao
 * Last modify on 2018/12/15 ${time}
 */
public class MoveValidator
{
    Location old,next;
    public boolean UserCheck(ChessBoard gameBoard,String moveString,boolean blackMove)
    {
        String[] components = moveString.split(" ");
        if (components.length > 3)
        {
            System.err.println("\r1Please provide valid move!");
            return false;
        }
        else {
            if (components[0].length() != 2 || components[2].length() != 2) {
                System.err.println("\r2Please provide valid move!");
                return false;
            } else {
                if (components[0].charAt(0) < 'a' || components[0].charAt(0) > 'h' || components[0].charAt(1) < '1' || components[0].charAt(1) > '8') {
                    System.err.println("\r3Please provide valid move!");
                    return false;
                } else {
                    if (components[2].charAt(0) < 'a' || components[2].charAt(0) > 'h' || components[2].charAt(1) < '1' || components[2].charAt(1) > '8') {
                        System.err.println("\r4Please provide valid move!");
                        return false;
                    } else {
                        char[] firstLocation = components[0].toCharArray();
                        old = new Location(8 - Character.getNumericValue(firstLocation[1]), Character.getNumericValue((char) (firstLocation[0] - 49)));

                        char[] nextLocation = components[2].toCharArray();
                        next = new Location(8 - Character.getNumericValue(nextLocation[1]), Character.getNumericValue((char) (nextLocation[0] - 49)));

                        for (int i = 0; i < 32; i++) {
                            if (gameBoard.checkLocation(old).index.compairTo(old)) {
                                if (gameBoard.checkLocation(old).blackSide != blackMove) {
                                    System.err.println("\nNot your chess, please only move your own chess");
                                    return false;
                                } else
                                    {
                                        return check(gameBoard,blackMove);
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public boolean AICheck(ChessBoard gameBoard,Location aiOld,Location aiNext,boolean blackMove)
    {
        old=aiOld;
        next=aiNext;
        return check(gameBoard,blackMove);
    }
	public boolean check(ChessBoard gameBoard,boolean blackMove) {
        {
            if (gameBoard.checkLocation(old).type.contains("ROOK")) {
                if (!gameBoard.checkLocation(next).type.contains("EMPTY") && gameBoard.checkLocation(next).blackSide == blackMove) {
                    System.err.println("Can not move to your own chess!");
                    return false;
                }
                //竖着走
                if (old.x == next.x) {
                    if (next.y > old.y) {
                        for (int j = old.y + 1; j < next.y; j++) {
                            //if blocked
                            Location temp = new Location(old.x, j);
                            if (!"EMPTY".equals(gameBoard.checkLocation(temp).type)) {
                                System.err.println("Path blocked!");
                                return false;
                            }
                        }
                    } else {
                        for (int j = next.y + 1; j < old.y; j++) {
                            //if blocked
                            Location temp = new Location(old.x, j);
                            if (!"EMPTY".equals(gameBoard.checkLocation(temp).type)) {
                                System.err.println("Path blocked!");
                                return false;
                            }
                        }
                    }

                }
                //横着走
                if (old.y == next.y) {
                    if (old.x > next.x) {

                        for (int j = next.x + 1; j < old.x; j++) {
                            //if blocked
                            Location temp = new Location(j, old.y);
                            if (!"EMPTY".equals(gameBoard.checkLocation(temp).type)) {
                                System.err.println("Path blocked!");
                                return false;
                            }
                        }
                    } else {
                        for (int j = old.x + 1; j < next.x; j++) {
                            //if blocked
                            Location temp = new Location(j, old.y);
                            if (!"EMPTY".equals(gameBoard.checkLocation(temp).type)) {
                                System.err.println("Path blocked!");
                                return false;
                            }
                        }
                    }
                }
                gameBoard.move(old, next, 1);
                return true;

            }
            if (gameBoard.checkLocation(old).type.contains("KNIGHT")) {
                if (!gameBoard.checkLocation(next).type.contains("EMPTY") && gameBoard.checkLocation(next).blackSide == blackMove) {
                    System.err.println("Can not move to your own chess!");
                }
                if (abs(next.x - old.x) == 1) {
                    if (abs(next.y - old.y) == 2) {
                        gameBoard.move(old, next, 1);
                        return true;
                    } else {
                        System.err.println("Invalid move!");
                        return false;
                    }
                } else {
                    if (abs(next.y - old.y) == 1) {
                        if (abs(next.x - old.x) == 2) {
                            gameBoard.move(old, next, 1);
                            return true;
                        } else {
                            System.err.println("Invalid move!");
                            return false;
                        }
                    } else {
                        System.err.println("Invalid move!");
                        return false;
                    }
                }
            }

            if (gameBoard.checkLocation(old).type.contains("BISHOP")) {
                if (!gameBoard.checkLocation(next).type.contains("EMPTY") && gameBoard.checkLocation(next).blackSide == blackMove) {
                    System.err.println("Can not move to your own chess!");
                    return false;
                }

                if (abs(next.x - old.x) == abs(next.y - old.y))
                {
                    if (next.x > old.x)
                    {
                        if (next.y > old.y)
                        {
                            for (int j = old.x + 1; j < next.x; j++)
                            {
                                for (int k = old.y + 1; k < next.y; k++)
                                {
                                    Location temp = new Location(j, k);
                                    if (!"EMPTY".equals(gameBoard.checkLocation(temp).type))
                                    {
                                        System.err.println("Path blocked!");
                                        return false;
                                    }
                                }
                            }
                        } else {
                            for (int j = old.x + 1; j < next.x; j++) {
                                for (int k = next.y + 1; k < old.y; k++) {
                                    Location temp = new Location(j, k);
                                    if (!"EMPTY".equals(gameBoard.checkLocation(temp).type)) {
                                        System.err.println("Path blocked!");
                                        return false;
                                    }
                                }
                            }
                        }
                    } else {
                        if (next.y > old.y) {
                            for (int j = next.x + 1; j < old.x; j++) {
                                for (int k = old.y + 1; k < next.y; k++) {
                                    Location temp = new Location(j, k);
                                    if (!"EMPTY".equals(gameBoard.checkLocation(temp).type)) {
                                        System.err.println("Path blocked!");
                                        return false;
                                    }
                                }
                            }
                        } else {
                            for (int j = next.x + 1; j < old.x; j++) {
                                for (int k = next.y + 1; k < old.y; k++) {
                                    Location temp = new Location(j, k);
                                    if (!"EMPTY".equals(gameBoard.checkLocation(temp).type)) {
                                        System.err.println("Path blocked!");
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                    gameBoard.move(old, next, 1);
                    return true;
                } else {
                    System.err.println("Invalid Move!");
                    return false;
                }
            }

            if (gameBoard.checkLocation(old).type.contains("QUEEN")) {
                if (!gameBoard.checkLocation(next).type.contains("EMPTY") && gameBoard.checkLocation(next).blackSide == blackMove) {
                    System.err.println("Can not move to your own chess!");
                    return false;
                }
                if (old.x == next.x) {
                    for (int j = old.y + 1; j < next.y; j++) {
                        //if blocked
                        Location temp = new Location(old.x, j);
                        if (!"EMPTY".equals(gameBoard.checkLocation(temp).type)) {
                            System.err.println("Path blocked!");
                            return false;
                        }
                    }
                }
                if (old.y == next.y) {
                    for (int j = old.x + 1; j < next.x; j++) {
                        //if blocked
                        Location temp = new Location(j, old.y);
                        if (!"EMPTY".equals(gameBoard.checkLocation(temp).type)) {
                            System.err.println("Path blocked!");
                            return false;
                        }
                    }
                }
                if (abs(next.x - old.x) == abs(next.y - old.y)) {
                    if (next.x > old.x) {
                        if (next.y > old.y) {
                            for (int j = old.x + 1; j < next.x; j++) {
                                for (int k = old.y + 1; k < next.y; k++) {
                                    Location temp = new Location(j, k);
                                    if (!"EMPTY".equals(gameBoard.checkLocation(temp).type)) {
                                        System.err.println("Path blocked!");
                                        return false;
                                    }
                                }
                            }
                        } else {
                            for (int j = old.x + 1; j < next.x; j++) {
                                for (int k = next.y + 1; k < old.y; k++) {
                                    Location temp = new Location(j, k);
                                    if (!"EMPTY".equals(gameBoard.checkLocation(temp).type)) {
                                        System.err.println("Path blocked!");
                                        return false;
                                    }
                                }
                            }
                        }
                    } else {
                        if (next.y > old.y) {
                            for (int j = next.x + 1; j < old.x; j++) {
                                for (int k = old.y + 1; k < next.y; k++) {
                                    Location temp = new Location(j, k);
                                    if (!"EMPTY".equals(gameBoard.checkLocation(temp).type)) {
                                        System.err.println("Path blocked!");
                                        return false;
                                    }
                                }
                            }
                        } else {
                            for (int j = next.x + 1; j < old.x; j++) {
                                for (int k = next.y + 1; k < old.y; k++) {
                                    Location temp = new Location(j, k);
                                    if (!"EMPTY".equals(gameBoard.checkLocation(temp).type)) {
                                        System.err.println("Path blocked!");
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
                gameBoard.move(old, next, 1);
                return true;
            }
            if (gameBoard.checkLocation(old).type.contains("KING")) {
                if (!gameBoard.checkLocation(next).type.contains("EMPTY") && gameBoard.checkLocation(next).blackSide == blackMove) {
                    System.err.println("Can not move to your own chess!");
                    return false;
                }
                Location temp1 = new Location(0, 0);
                Location temp2 = new Location(0, 7);
                if (!blackMove) {
                    temp1 = new Location(7, 0);
                    temp2 = new Location(7, 7);
                }
                if (!gameBoard.checkLocation(temp1).isMoved || !gameBoard.checkLocation(temp2).isMoved) {
                    if (next.x == old.x) {
                        if (next.y > old.y) {
                            for (int cas = old.y + 1; cas < next.y + 1; cas++) {
                                Location temp = new Location(old.x, cas);
                                if (!gameBoard.checkLocation(temp).type.contains("EMPTY")) {
                                    System.err.println("Path Blocked");
                                    return false;
                                }
                                if ((gameBoard.checkLocation(temp).blackAttacking != blackMove) || (gameBoard.checkLocation(temp).whiteAttacking == blackMove)) {
                                    System.err.println("bu neng song chi");
                                    return false;
                                }
                            }
                            gameBoard.move(old, next, 2);
                            return true;
                        }
                    }
                }
                if (abs(next.y - old.y) < 2 && abs(next.x - old.x) < 2) {
                    if ((gameBoard.checkLocation(next).blackAttacking != blackMove) || (gameBoard.checkLocation(next).whiteAttacking == blackMove)) {
                        System.err.println("bu neng song chi");
                        return false;
                    }
                    gameBoard.move(old, next, 1);
                    return true;
                } else {
                    System.err.println("Invalid Move!");
                    return false;
                }
            }


            if (gameBoard.checkLocation(old).type.contains("PAWN")) {
                if (!gameBoard.checkLocation(next).type.contains("EMPTY") && gameBoard.checkLocation(next).blackSide == blackMove) {
                    System.err.println("Can not move to your own chess!");
                    return false;
                }
                if (blackMove) {
                    if (next.x < old.x) {
                        System.err.println("Pawn can not go back!");
                        return false;
                    }
                } else {
                    if (next.x > old.x) {
                        System.err.println("Pawn can not go back!");
                        return false;
                    }
                }

                if (!gameBoard.checkLocation(old).isMoved) {
                    if (next.y == old.y && abs(next.x - old.x) <= 2) {
                        if (!"EMPTY".equals(gameBoard.checkLocation(next).type)) {
                            System.err.println("Place has benn occupied");
                            return false;
                        } else {
                            gameBoard.checkLocation(old).enPassant = true;
                            gameBoard.move(old, next, 1);
                            return true;
                        }
                    }
                }
                if (next.y == old.y && abs(next.x - old.x) == 1) {
                    if (!"EMPTY".equals(gameBoard.checkLocation(next).type)) {
                        System.err.println("Place has benn occupied");
                        return false;
                    } else {
                        gameBoard.move(old, next, 1);
                        return true;
                    }
                }

                if (!gameBoard.checkLocation(next).type.contains("EMPTY") && gameBoard.checkLocation(next).blackSide != blackMove) {
                    if (abs(next.x - old.x) == 1 && abs(next.y - old.y) == 1) {
                        gameBoard.move(old, next, 1);
                        return true;
                    } else {
                        System.err.println("Invalid move");
                        return false;
                    }
                } else {
                    if (gameBoard.checkLocation(next).type.contains("EMPTY")) {
                        if (abs(next.x - old.x) == 1 && abs(next.y - old.y) == 1) {
                            Location temp = new Location(old.x, next.y);
                            if (gameBoard.checkLocation(temp).enPassant) {
                                gameBoard.move(old, next, 3);
                            } else {
                                System.err.println("Invalid move");
                                return false;
                            }
                        }
                    }
                }
            }
            return false;
        }
    }
}
