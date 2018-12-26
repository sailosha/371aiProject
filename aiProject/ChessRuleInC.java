#include <iostream>

#include <string>

#include <cmath>

using namespace std;



enum TResult

{//结局状态

    WHITEWIN = 1,//白方赢

    BLACKWIN,//黑方赢

    STALEMATE,//僵局

    DRAW,//和局

    DEAD,//过多的输入

    PUZZLE,//无法决定移动棋子

    ILLEGAL //非法

};

const char RESULT[8][20]=

{//结局状态输出表示

    "",

    "White Win",

    "Black Win",

    "Stalemate",

    "Draw",

    "Dead Moves",

    "Puzzle Move",

    "Illegal Move"

};

enum TPieceType

{//棋子类型

    SPACE = 0,

    PAWN,//兵

    KING,//王

    QUEEN,//后

    ROOK,//车

    BISHOP,//象

    KNIGHT //马

};

enum TSide

{

    NONE = 0,

    WHITE,//黑方

    BLACK //白方

};

typedef struct

{//棋盘每个位置的表示

    TSide side;//所属玩家

    TPieceType pt;//棋子类型

}TPiece;

const int BOARDSIZE = 8;//棋盘大小



typedef TPiece TBoard[BOARDSIZE][BOARDSIZE];//棋盘

int n;//棋谱步数

TResult result;//最后结局

/*

*用来进行王车易位的布尔变量

*

* whitecastled:白方是否已经王车易位

* blackcastled:黑方是否已经王车易位

* white0rookMoved: 白方号位的车是否已经移动

* white7rookMoved: 白方号位的车是否已经移动

* black0rookMoved: 黑方号位的车是否已经移动

* black7rookMoved: 黑方号位的车是否已经移动

* whitekingMoved: 白方王是否已经移动

* blackkingMoved: 黑方王是否已经移动

*

*/

bool whitecastled,blackcastled,white0rookMoved,white7rookMoved,black0rookMoved,black7rookMoved,whitekingMoved,blackkingMoved;



TPieceType ChessType(const string& move)

{

    switch(move[0])

    {

    case 'K'://王

        return KING;

    case 'Q'://后

        return QUEEN;

    case 'R'://车

        return ROOK;

    case 'B'://象

        return BISHOP;

    case 'N'://马

        return KNIGHT;

    }

    return PAWN;//兵

}

TSide Opponent(TSide side)

{//获取对手类型

    if (side==WHITE)

        return BLACK;

    return WHITE;

}

void clear(TBoard b,int x,int y)

{//清空棋盘b的(x,y)位置

    b[x][y].side = NONE;//所属玩家

    b[x][y].pt = SPACE;//棋子类型

}



void init(TBoard b)

{//初始化棋盘

    int i,j;

    //清空整个棋盘

    for(i=0;i<BOARDSIZE;++i)

        for(j=0;j<BOARDSIZE;++j)

        {

            clear(b,i,j);

        }

        //摆放各个棋子

        for(i=0;i<BOARDSIZE;++i)

        {

            //棋盘前两行是白方

            b[0][i].side = WHITE;

            b[1][i].side = WHITE;

            b[1][i].pt = PAWN;//上面第二行是白方的兵

            //棋盘最后两行是黑方

            b[6][i].side = BLACK;

            b[7][i].side = BLACK;

            b[6][i].pt = PAWN;//倒数第二行是黑方的兵

        }

        b[0][0].pt = b[0][7].pt = b[7][0].pt = b[7][7].pt = ROOK;//初始化车的位置

        b[0][1].pt = b[0][6].pt = b[7][1].pt = b[7][6].pt = KNIGHT;//初始化马的位置

        b[0][2].pt = b[0][5].pt = b[7][2].pt = b[7][5].pt = BISHOP;//初始化象的位置

        b[0][3].pt = b[7][3].pt = QUEEN;//初始化后的位置

        b[0][4].pt = b[7][4].pt = KING;//初始化王的位置

        //初始化王车易位使用的布尔变量

        whitecastled = false;

        blackcastled = false;

        white0rookMoved = false;

        white7rookMoved = false;

        black0rookMoved = false;

        black7rookMoved = false;

        whitekingMoved = false;

        blackkingMoved = false;

}    

void SkipInput(int k)

{//棋局已经结束，忽略剩余的输入

    int i;

    char mv[20];

    for(i=k;i<n;++i)

    {

        scanf_s("%s",mv);

    }

}

void GetPosition(const string& move,int &x,int &y)

{//从输入的移动步骤中获取棋子的目标位置

    int k = 0;

    if(move[0]<'a')//首字母是大写字母

        k = 1;

    x = move[k+1]-'1';//行

    y = move[k]-'a';//列

}

bool OutOfBoard(int x,int y)

{//棋子是否超出棋盘界限

    if (x<0||y<0)

    {

        return true;

    }

    if (x>BOARDSIZE||y>BOARDSIZE)

    {

        return true;

    }

    return false;

}

bool CanMovePawn(TBoard b,int x,int y,int x2,int y2,int flag)

{//判断能否把兵从(x,y)移动到(x2,y2),当flag=1时，表示(x,y)直接移动到(x2,y2),flag为其他表示从(x,y)吃子到(x2,y2)

    if (flag==1)

    {//直接移动，即兵直线前进一格

        if (y!=y2||b[x2][y2].side!=NONE)

        {//y坐标不能改变，无法前进

            return false;

        }

        if (b[x][y].side==WHITE)

        {//下棋的是白方

            if (x==1)

            {//白方的兵是第一次移动

                return x2==2 || (x2==3&&b[2][y].side==NONE);//第一次移动兵可以移动格或格

            }

            else

            {

                return x2==x+1;//不是第一次移动，就只能向前移动格

            }

        }

        else

        {//下棋的是黑方

            if (x==6)

            {//黑方的兵是第一次移动

                return x2==5 || (x2==4&&b[5][y].side==NONE);//第一次移动兵可以移动格或格

            }

            else

            {

                return x2==x-1;//不是第一次移动，就只能向前移动格

            }

        }

    }

    else

    {//吃子判断,吃子时，x向前格，y坐标改变格

        if (b[x][y].side==WHITE)

        {//要吃子的是白方

            return (x2==x+1&&abs(y2-y)==1);

        }

        else 

        {//要吃子的是黑方

            return (x2==x-1&&abs(y2-y)==1);

        }

    }

    return false;

}



bool CanMoveKing(TBoard b,int x,int y,int x2,int y2)

{//判断能否把王从(x,y)移动到(x2,y2)

    return (abs(x-x2)<=1&&abs(y-y2)<=1);

}

bool CanMoveRook(TBoard b,int x,int y,int x2,int y2)

{//判断能否把车从(x,y)移动到(x2,y2)

    int dx,dy,i,xx,yy;

    //判断移动是否是直线

    if (x!=x2 && y!=y2)

    {

        return false;

    }

    //直线方向增量

    if (x2<x)

        dx = -1;

    else

        dx = 1;

    if (y2<y)

        dy = -1;

    else

        dy = 1;

    //x方向上移动

    for (i=1;i<abs(y-y2);++i)

    {

        yy = y+i*dy;

        if (b[x][yy].side!=NONE)

        {//中间有棋子阻挡

            return false;

        }

    }

    //y方向上移动

    for (i=1;i<abs(x-x2);++i)

    {

        xx = x+i*dx;

        if (b[xx][y].side!=NONE)

        {//中间有棋子阻挡

            return false;

        }

    }

    return true;

}



bool CanMoveBishop(TBoard b,int x,int y,int x2,int y2)

{//判断能否把象从(x,y)移动到(x2,y2)

    int dx,dy,i,xx,yy;

    //是否斜向移动

    if (abs(x-x2)!=abs(y-y2))

    {

        return false;

    }

    //直线方向增量

    if (x2<x)

        dx = -1;

    else

        dx = 1;

    if (y2<y)

        dy = -1;

    else

        dy = 1;

    for (i=1;i<abs(x-x2);++i)

    {

        xx = x+i*dx;

        yy = y+i*dy;

        if (b[xx][yy].side!=NONE)

        {//中间有棋子阻挡

            return false;

        }

    }

    return true;

}

bool CanMoveQueen(TBoard b,int x,int y,int x2,int y2)

{//判断能否把王从(x,y)移动到(x2,y2)

    return CanMoveRook(b,x,y,x2,y2) || CanMoveBishop(b,x,y,x2,y2);//王后等于车+象

}

bool CanMoveKnight(int x,int y,int x2,int y2)

{//判断马能否从(x,y)移动到(x2,y2)

    int xx,yy;

    xx = abs(x-x2);

    yy = abs(y-y2);

    return (xx+yy==3 && (xx==1 || yy==1));//马行日，x或者y这两者之一移动格，另一方向移动格

}

bool CanMove(TBoard b,int x,int y,int x2,int y2,int flag)

{//判断一个棋子能否从(x,y)移动到(x2,y2),当flag=1时，直接移动，flag=2时，表示把(x2,y2)处的棋子给吃掉

    //判断是否越界

    if (OutOfBoard(x,y)||OutOfBoard(x2,y2))

    {

        return false;

    }

    //判断原位置是否有棋子

    if (b[x][y].side==NONE)

    {

        return false;

    }

    //根据原来位置上棋子的不同类型判断是否合法

    switch (b[x][y].pt)

    {

    case PAWN://兵

        return CanMovePawn(b,x,y,x2,y2,flag);

    case KING://王

        return CanMoveKing(b,x,y,x2,y2);

    case QUEEN://后

        return CanMoveQueen(b,x,y,x2,y2);

    case ROOK://车

        return CanMoveRook(b,x,y,x2,y2);

    case BISHOP://象

        return CanMoveBishop(b,x,y,x2,y2);

    case KNIGHT://马

        return CanMoveKnight(x,y,x2,y2);

    }

    return false;

}

void GetSourcePosition(TBoard b,int x2,int y2,int &x,int &y,TPieceType ct,TSide side)

{/*从给出的位置(x2,y2),类型ct和玩家side,求出移动的棋子的原来位置（x,y)，,

 * 当x=-2时，表示有重复移动方案（Puzzle),x=-1时表示没有移动可能(illegal)

 */

    int i,j,flag = 1;

    if(b[x2][y2].side!=NONE)//目标位置是对手的棋子，则此步为吃子方案

        flag = 2;

    for (i=0;i<BOARDSIZE;++i)

    {

        for (j=0;j<BOARDSIZE;++j)

        {

            if (b[i][j].side==side&&b[i][j].pt==ct)

            {//原位置合法并且是同一个子

                if (CanMove(b,i,j,x2,y2,flag))

                {

                    if (x==-1)

                    {//能够移动并且不重复，找到原来棋子的位置

                        x = i;

                        y = j;

                    }

                    else

                    {//能够移动并且有方案，说明有重复

                        x = -2;

                        return;

                    }

                }

            }

        }

    }

}

void MarkRookMove(TSide side,int x,int y)

{

    if (side==WHITE)

    {

        if (x==0)

        {

            if (y==0)

            {

                white0rookMoved = true;//白方号车已经移动

            }

            if (y==7)

            {

                white7rookMoved = true;//白方号车已经移动

            }

        }

        return;

    }

    if (x==7)

    {

        if (y==0)

        {

            black0rookMoved = true;//黑方号车已经移动

        }

        if (y==7)

        {

            black7rookMoved = true;//黑方号车已经移动

        }

    }

}



void ChessMove(TBoard b,int x,int y,int x2,int y2)

{//棋子从(x,y)移动到(x2,y2)

    b[x2][y2].side = b[x][y].side;

    b[x2][y2].pt = b[x][y].pt;

    clear(b,x,y);//清空原位置

}

void MakeMove(TBoard b,const string& move,TSide side)

{//根据输入的步骤mv,玩家side移动棋子

    int x,y,x2,y2;

    GetPosition(move,x2,y2);//目标位置

    if(b[x2][y2].side==side)

    {//目标位置处已经有我方的棋子了，此步非法

        result = ILLEGAL;

        return;

    }

    x = -1;

    GetSourcePosition(b,x2,y2,x,y,ChessType(move),side);//尝试寻找原位置

    if (x==-1)

    {//非法状态

        result = ILLEGAL;

        return;

    }

    else if (x==-2)

    {//重复状态

        result = PUZZLE;

        return;

    }

    //移动的棋子是车时，设置王车易位布尔变量

    if (b[x][y].pt==ROOK)

    {

        MarkRookMove(side,x,y);

    }

    //移动的棋子是王时，设置王车易位布尔变量

    if (b[x][y].pt==KING)

    {

        if (side==WHITE)//白方王移动了

            whitekingMoved = true;

        else//黑方王移动了

            blackkingMoved = true;

    }

    ChessMove(b,x,y,x2,y2);//移动棋子

}

bool GridBeAttack(TBoard b,int x,int y,TSide byWho)

{//判断位置(x,y)的棋子能否被吃掉

    int i,j;

    for (i=0;i<BOARDSIZE;++i)

    {

        for (j=0;j<BOARDSIZE;++j)

        {

            if (b[i][j].side==byWho && CanMove(b,i,j,x,y,2))

            {//会被对手吃掉的

                return true;

            }

        }

    }

    return false;

}

bool CanCastle(TBoard b,TSide side,int flag)

{//判断是否能够进行王车易位

    int row,i;

    if (side==WHITE)

    {//白方王车易位

        if (whitekingMoved==true)

        {//王已经动了，不能王车易位

            return false;

        }

        if (flag==3&&white7rookMoved==true)

        {//目标车已经动了，不能王车易位

            return false;

        }

        if (flag==5&&white0rookMoved==true)

        {//目标车已经动了，不能王车易位

            return false;

        }

    }

    else

    {//黑方王车易位

        if (blackkingMoved==true)

        {//王已经动了

            return false;

        }

        if (flag==3 && black7rookMoved==true)

        {//目标车已经动了，不能王车易位

            return false;

        }

        if (flag==5 && black0rookMoved==true)

        {//目标车已经动了，不能王车易位

            return false;

        }

    }

    if (side==WHITE)

        row = 0;

    else

        row = 7;

    if (flag==5)

    {

        for (i=1;i<4;++i)

        {

            if (b[row][i].side!=NONE)

            {//王车之间是否有棋子，若有则不能易位

                return false;

            }

        }

        for (i=0;i<5;++i)

        {

            if (GridBeAttack(b,row,i,Opponent(side))==true)

            {//在目标位置上会被对手吃掉，不能王车易位

                return false;

            }

        }

    }

    else

    {

        for (i=5;i<BOARDSIZE-1;++i)

        {

            if (b[row][i].side!=NONE)

            {//王车之间是否有棋子，若有则不能易位

                return false;

            }

        }

        for (i=4;i<BOARDSIZE;++i)

        {

            if (GridBeAttack(b,row,i,Opponent(side)))

            {//在目标位置上会被对手吃掉，不能王车易位

                return false;

            }

        }

    }

    return true;//检查符合要求，可以王车易位

}

void Castle(TBoard b,TSide side,int flag)

{//进行王车易位,flag=3,表示靠近王的车King-side castle,flag=5时，表示Queen-side castle

    int row;

    if (side==WHITE)

    {

        if (whitecastled==true)

        {//白方是否已经易位，已经易过位，不能再易了

            result = ILLEGAL;

            return ;

        }

        else

            whitecastled = true;//设置易位变量

    }

    else

    {

        if (blackcastled==true)

        {//黑方是否已经易位，已经易过位，不能再易了

            result = ILLEGAL;

            return;

        }

        else

            blackcastled = true;

    }

    if (CanCastle(b,side,flag)==true)

    {//判断是否能够易位

        if (side==WHITE)

        {

            row = 0;

        }

        else

        {

            row = 7;

        }

        if (flag==3)

        {//进行王车易位

            ChessMove(b,row,4,row,6);

            ChessMove(b,row,7,row,5);

        }

        else

        {

            ChessMove(b,row,4,row,2);

            ChessMove(b,row,0,row,3);

        }

    }

    else

    {//无法王车易位，此步非法

        result = ILLEGAL;

    }

}

void GetKingPosition(TBoard b,TSide side,int &x,int &y)

{//寻找国王的位置

    int i,j;

    for (i=0;i<BOARDSIZE;++i)

    {

        for (j=0;j<BOARDSIZE;++j)

        {

            if (b[i][j].pt==KING && b[i][j].side == side)

            {//找到指定方的王

                x = i;

                y = j;

                return;

            }

        }

    }

}

bool BeCheck(TBoard b,TSide side)

{//判断是否被“将军"

    int x,y,i,j;

    TSide oppSide;

    GetKingPosition(b,side,x,y);//寻找玩家side的王

    oppSide = Opponent(side);//对手

    for (i=0;i<BOARDSIZE;++i)

    {

        for (j=0;j<BOARDSIZE;++j)

        {

            if (b[i][j].side==oppSide && CanMove(b,i,j,x,y,2)==true)

            {//判断对手是否能否将军，flag=2表示此步是吃子，

                return true;

            }

        }

    }

    return false;

}

void CopyBoard(TBoard desBoard,TBoard srcBoard)

{//复制棋盘

    int i,j;

    for (i=0;i<BOARDSIZE;++i)

    {

        for (j=0;j<BOARDSIZE;++j)

        {

            desBoard[i][j].pt = srcBoard[i][j].pt;

            desBoard[i][j].side = srcBoard[i][j].side;

        }

    }

}

bool NoMoveAvailable(TBoard b,TSide side)

{//判断是否是僵局

    int x,y,x2,y2;

    TBoard b2;

    for (x=0;x<BOARDSIZE;++x)

    {

        for (y=0;y<BOARDSIZE;++y)

        {

            if (b[x][y].side==side)

            {

                for (x2=0;x2<BOARDSIZE;++x2)

                {

                    for (y2=0;y2<BOARDSIZE;++y2)

                    {//判断side方的王能否从(x,y)走到(x2,y2)

                        if ((x2!=x || y2!=y) && b[x][y].side!=b[x2][y2].side && CanMove(b,x,y,x2,y2,1))

                        {

                            if (b[x][y].pt==KING)

                            {

                                CopyBoard(b2,b);

                                ChessMove(b2,x,y,x2,y2);

                                if (!BeCheck(b2,side))

                                {//判断位置是否被”将军“，没有就不是僵局

                                    return false;

                                }

                            }

                            else

                            {

                                return false;

                            }

                        }

                    }

                }

            }

        }

    }

    return true;

}

bool CheckMate(TBoard b,TSide side)

{//判断是否已经结束（即是否某一方被将死）

    int x,y,x2,y2;

    TBoard b2;

    if (!BeCheck(b,side))

    {//没有被将死

        return false;

    }

    for (x=0;x<BOARDSIZE;++x)

    {

        for (y=0;y<BOARDSIZE;++y)

        {

            if (b[x][y].side==side)

            {

                for (x2=0;x2<BOARDSIZE;++x2)

                {

                    for (y2=0;y2<BOARDSIZE;++y2)

                    {

                        if ((x!=x2 || y!=y2) && b[x2][y2].side!=side)

                        {

                            //王能否从(x,y)移动到(x2,y2)

                            if (CanMove(b,x,y,x2,y2,1)==true)

                            {

                                CopyBoard(b2,b);

                                ChessMove(b2,x,y,x2,y2);

                                if (!BeCheck(b2,side)==true)

                                {//移动后没有被"将军"

                                    return false;

                                }

                            }

                            if (b[x][y].pt==PAWN && CanMove(b,x,y,x2,y2,2)==true)

                            {

                                CopyBoard(b2,b);

                                ChessMove(b2,x,y,x2,y2);

                                if (!BeCheck(b2,side))

                                {

                                    return false;

                                }

                            }

                        }

                    }

                }

            }

        }

    }

    return true;

}

void run(TBoard b)

{//下棋过程

    string move;//当前步

    TSide side;

    side = WHITE;//起手是白方先走

    int i;

    for(i=0;i<n;++i)

    {

        if(result==WHITEWIN||result==BLACKWIN||result==STALEMATE)

        {//棋局已经结束，但还有输入没处理完，状态为DEAD

            result = DEAD;

            SkipInput(i);//忽略其余输入

            return;

        }

        cin>>move;//输入棋子移动步骤

        if(move[0]!='O')

        {//不是王车易位

            MakeMove(b,move,side);//根据输入的步骤mv，玩家side移动棋子

        }

        else

        {//王车易位

            Castle(b,side,move.length());

        }

        if (BeCheck(b,side)==true)

        {//移动棋子后被对手"将军"

            result = ILLEGAL;

        }

        if (result==PUZZLE || result==ILLEGAL)

        {//非法输入或不清楚原来的棋子，则跳过数据

            SkipInput(i+1);

            return;

        }

        if (CheckMate(b,Opponent(side)))

        {//被”将死“时，判断获胜方

            if (side==WHITE)

            {

                result = WHITEWIN;

            }

            else

            {

                result = BLACKWIN;

            }

        

        }

        side = Opponent(side);//换到对方下了

    }

    if (result==DRAW && NoMoveAvailable(b,side)==true)

    {//是否是僵局

        result = STALEMATE;

    }

}

int main()

{

    TBoard board;//棋盘

    //输入输出重定向

    freopen("chess.in","r",stdin);

    freopen("chess.out","w",stdout);

    while(cin>>n && n!=0)

    {

        init(board);//初始化棋盘

        result = DRAW;//一开始假设结果为和局

        run(board);//下棋过程

        cout<<RESULT[result]<<endl;

    }

    return 0;

}