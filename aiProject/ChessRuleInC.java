#include <iostream>

#include <string>

#include <cmath>

using namespace std;



enum TResult

{//���״̬

    WHITEWIN = 1,//�׷�Ӯ

    BLACKWIN,//�ڷ�Ӯ

    STALEMATE,//����

    DRAW,//�;�

    DEAD,//���������

    PUZZLE,//�޷������ƶ�����

    ILLEGAL //�Ƿ�

};

const char RESULT[8][20]=

{//���״̬�����ʾ

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

{//��������

    SPACE = 0,

    PAWN,//��

    KING,//��

    QUEEN,//��

    ROOK,//��

    BISHOP,//��

    KNIGHT //��

};

enum TSide

{

    NONE = 0,

    WHITE,//�ڷ�

    BLACK //�׷�

};

typedef struct

{//����ÿ��λ�õı�ʾ

    TSide side;//�������

    TPieceType pt;//��������

}TPiece;

const int BOARDSIZE = 8;//���̴�С



typedef TPiece TBoard[BOARDSIZE][BOARDSIZE];//����

int n;//���ײ���

TResult result;//�����

/*

*��������������λ�Ĳ�������

*

* whitecastled:�׷��Ƿ��Ѿ�������λ

* blackcastled:�ڷ��Ƿ��Ѿ�������λ

* white0rookMoved: �׷���λ�ĳ��Ƿ��Ѿ��ƶ�

* white7rookMoved: �׷���λ�ĳ��Ƿ��Ѿ��ƶ�

* black0rookMoved: �ڷ���λ�ĳ��Ƿ��Ѿ��ƶ�

* black7rookMoved: �ڷ���λ�ĳ��Ƿ��Ѿ��ƶ�

* whitekingMoved: �׷����Ƿ��Ѿ��ƶ�

* blackkingMoved: �ڷ����Ƿ��Ѿ��ƶ�

*

*/

bool whitecastled,blackcastled,white0rookMoved,white7rookMoved,black0rookMoved,black7rookMoved,whitekingMoved,blackkingMoved;



TPieceType ChessType(const string& move)

{

    switch(move[0])

    {

    case 'K'://��

        return KING;

    case 'Q'://��

        return QUEEN;

    case 'R'://��

        return ROOK;

    case 'B'://��

        return BISHOP;

    case 'N'://��

        return KNIGHT;

    }

    return PAWN;//��

}

TSide Opponent(TSide side)

{//��ȡ��������

    if (side==WHITE)

        return BLACK;

    return WHITE;

}

void clear(TBoard b,int x,int y)

{//�������b��(x,y)λ��

    b[x][y].side = NONE;//�������

    b[x][y].pt = SPACE;//��������

}



void init(TBoard b)

{//��ʼ������

    int i,j;

    //�����������

    for(i=0;i<BOARDSIZE;++i)

        for(j=0;j<BOARDSIZE;++j)

        {

            clear(b,i,j);

        }

        //�ڷŸ�������

        for(i=0;i<BOARDSIZE;++i)

        {

            //����ǰ�����ǰ׷�

            b[0][i].side = WHITE;

            b[1][i].side = WHITE;

            b[1][i].pt = PAWN;//����ڶ����ǰ׷��ı�

            //������������Ǻڷ�

            b[6][i].side = BLACK;

            b[7][i].side = BLACK;

            b[6][i].pt = PAWN;//�����ڶ����Ǻڷ��ı�

        }

        b[0][0].pt = b[0][7].pt = b[7][0].pt = b[7][7].pt = ROOK;//��ʼ������λ��

        b[0][1].pt = b[0][6].pt = b[7][1].pt = b[7][6].pt = KNIGHT;//��ʼ�����λ��

        b[0][2].pt = b[0][5].pt = b[7][2].pt = b[7][5].pt = BISHOP;//��ʼ�����λ��

        b[0][3].pt = b[7][3].pt = QUEEN;//��ʼ�����λ��

        b[0][4].pt = b[7][4].pt = KING;//��ʼ������λ��

        //��ʼ��������λʹ�õĲ�������

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

{//����Ѿ�����������ʣ�������

    int i;

    char mv[20];

    for(i=k;i<n;++i)

    {

        scanf_s("%s",mv);

    }

}

void GetPosition(const string& move,int &x,int &y)

{//��������ƶ������л�ȡ���ӵ�Ŀ��λ��

    int k = 0;

    if(move[0]<'a')//����ĸ�Ǵ�д��ĸ

        k = 1;

    x = move[k+1]-'1';//��

    y = move[k]-'a';//��

}

bool OutOfBoard(int x,int y)

{//�����Ƿ񳬳����̽���

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

{//�ж��ܷ�ѱ���(x,y)�ƶ���(x2,y2),��flag=1ʱ����ʾ(x,y)ֱ���ƶ���(x2,y2),flagΪ������ʾ��(x,y)���ӵ�(x2,y2)

    if (flag==1)

    {//ֱ���ƶ�������ֱ��ǰ��һ��

        if (y!=y2||b[x2][y2].side!=NONE)

        {//y���겻�ܸı䣬�޷�ǰ��

            return false;

        }

        if (b[x][y].side==WHITE)

        {//������ǰ׷�

            if (x==1)

            {//�׷��ı��ǵ�һ���ƶ�

                return x2==2 || (x2==3&&b[2][y].side==NONE);//��һ���ƶ��������ƶ�����

            }

            else

            {

                return x2==x+1;//���ǵ�һ���ƶ�����ֻ����ǰ�ƶ���

            }

        }

        else

        {//������Ǻڷ�

            if (x==6)

            {//�ڷ��ı��ǵ�һ���ƶ�

                return x2==5 || (x2==4&&b[5][y].side==NONE);//��һ���ƶ��������ƶ�����

            }

            else

            {

                return x2==x-1;//���ǵ�һ���ƶ�����ֻ����ǰ�ƶ���

            }

        }

    }

    else

    {//�����ж�,����ʱ��x��ǰ��y����ı��

        if (b[x][y].side==WHITE)

        {//Ҫ���ӵ��ǰ׷�

            return (x2==x+1&&abs(y2-y)==1);

        }

        else 

        {//Ҫ���ӵ��Ǻڷ�

            return (x2==x-1&&abs(y2-y)==1);

        }

    }

    return false;

}



bool CanMoveKing(TBoard b,int x,int y,int x2,int y2)

{//�ж��ܷ������(x,y)�ƶ���(x2,y2)

    return (abs(x-x2)<=1&&abs(y-y2)<=1);

}

bool CanMoveRook(TBoard b,int x,int y,int x2,int y2)

{//�ж��ܷ�ѳ���(x,y)�ƶ���(x2,y2)

    int dx,dy,i,xx,yy;

    //�ж��ƶ��Ƿ���ֱ��

    if (x!=x2 && y!=y2)

    {

        return false;

    }

    //ֱ�߷�������

    if (x2<x)

        dx = -1;

    else

        dx = 1;

    if (y2<y)

        dy = -1;

    else

        dy = 1;

    //x�������ƶ�

    for (i=1;i<abs(y-y2);++i)

    {

        yy = y+i*dy;

        if (b[x][yy].side!=NONE)

        {//�м��������赲

            return false;

        }

    }

    //y�������ƶ�

    for (i=1;i<abs(x-x2);++i)

    {

        xx = x+i*dx;

        if (b[xx][y].side!=NONE)

        {//�м��������赲

            return false;

        }

    }

    return true;

}



bool CanMoveBishop(TBoard b,int x,int y,int x2,int y2)

{//�ж��ܷ�����(x,y)�ƶ���(x2,y2)

    int dx,dy,i,xx,yy;

    //�Ƿ�б���ƶ�

    if (abs(x-x2)!=abs(y-y2))

    {

        return false;

    }

    //ֱ�߷�������

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

        {//�м��������赲

            return false;

        }

    }

    return true;

}

bool CanMoveQueen(TBoard b,int x,int y,int x2,int y2)

{//�ж��ܷ������(x,y)�ƶ���(x2,y2)

    return CanMoveRook(b,x,y,x2,y2) || CanMoveBishop(b,x,y,x2,y2);//������ڳ�+��

}

bool CanMoveKnight(int x,int y,int x2,int y2)

{//�ж����ܷ��(x,y)�ƶ���(x2,y2)

    int xx,yy;

    xx = abs(x-x2);

    yy = abs(y-y2);

    return (xx+yy==3 && (xx==1 || yy==1));//�����գ�x����y������֮һ�ƶ�����һ�����ƶ���

}

bool CanMove(TBoard b,int x,int y,int x2,int y2,int flag)

{//�ж�һ�������ܷ��(x,y)�ƶ���(x2,y2),��flag=1ʱ��ֱ���ƶ���flag=2ʱ����ʾ��(x2,y2)�������Ӹ��Ե�

    //�ж��Ƿ�Խ��

    if (OutOfBoard(x,y)||OutOfBoard(x2,y2))

    {

        return false;

    }

    //�ж�ԭλ���Ƿ�������

    if (b[x][y].side==NONE)

    {

        return false;

    }

    //����ԭ��λ�������ӵĲ�ͬ�����ж��Ƿ�Ϸ�

    switch (b[x][y].pt)

    {

    case PAWN://��

        return CanMovePawn(b,x,y,x2,y2,flag);

    case KING://��

        return CanMoveKing(b,x,y,x2,y2);

    case QUEEN://��

        return CanMoveQueen(b,x,y,x2,y2);

    case ROOK://��

        return CanMoveRook(b,x,y,x2,y2);

    case BISHOP://��

        return CanMoveBishop(b,x,y,x2,y2);

    case KNIGHT://��

        return CanMoveKnight(x,y,x2,y2);

    }

    return false;

}

void GetSourcePosition(TBoard b,int x2,int y2,int &x,int &y,TPieceType ct,TSide side)

{/*�Ӹ�����λ��(x2,y2),����ct�����side,����ƶ������ӵ�ԭ��λ�ã�x,y)��,

 * ��x=-2ʱ����ʾ���ظ��ƶ�������Puzzle),x=-1ʱ��ʾû���ƶ�����(illegal)

 */

    int i,j,flag = 1;

    if(b[x2][y2].side!=NONE)//Ŀ��λ���Ƕ��ֵ����ӣ���˲�Ϊ���ӷ���

        flag = 2;

    for (i=0;i<BOARDSIZE;++i)

    {

        for (j=0;j<BOARDSIZE;++j)

        {

            if (b[i][j].side==side&&b[i][j].pt==ct)

            {//ԭλ�úϷ�������ͬһ����

                if (CanMove(b,i,j,x2,y2,flag))

                {

                    if (x==-1)

                    {//�ܹ��ƶ����Ҳ��ظ����ҵ�ԭ�����ӵ�λ��

                        x = i;

                        y = j;

                    }

                    else

                    {//�ܹ��ƶ������з�����˵�����ظ�

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

                white0rookMoved = true;//�׷��ų��Ѿ��ƶ�

            }

            if (y==7)

            {

                white7rookMoved = true;//�׷��ų��Ѿ��ƶ�

            }

        }

        return;

    }

    if (x==7)

    {

        if (y==0)

        {

            black0rookMoved = true;//�ڷ��ų��Ѿ��ƶ�

        }

        if (y==7)

        {

            black7rookMoved = true;//�ڷ��ų��Ѿ��ƶ�

        }

    }

}



void ChessMove(TBoard b,int x,int y,int x2,int y2)

{//���Ӵ�(x,y)�ƶ���(x2,y2)

    b[x2][y2].side = b[x][y].side;

    b[x2][y2].pt = b[x][y].pt;

    clear(b,x,y);//���ԭλ��

}

void MakeMove(TBoard b,const string& move,TSide side)

{//��������Ĳ���mv,���side�ƶ�����

    int x,y,x2,y2;

    GetPosition(move,x2,y2);//Ŀ��λ��

    if(b[x2][y2].side==side)

    {//Ŀ��λ�ô��Ѿ����ҷ��������ˣ��˲��Ƿ�

        result = ILLEGAL;

        return;

    }

    x = -1;

    GetSourcePosition(b,x2,y2,x,y,ChessType(move),side);//����Ѱ��ԭλ��

    if (x==-1)

    {//�Ƿ�״̬

        result = ILLEGAL;

        return;

    }

    else if (x==-2)

    {//�ظ�״̬

        result = PUZZLE;

        return;

    }

    //�ƶ��������ǳ�ʱ������������λ��������

    if (b[x][y].pt==ROOK)

    {

        MarkRookMove(side,x,y);

    }

    //�ƶ�����������ʱ������������λ��������

    if (b[x][y].pt==KING)

    {

        if (side==WHITE)//�׷����ƶ���

            whitekingMoved = true;

        else//�ڷ����ƶ���

            blackkingMoved = true;

    }

    ChessMove(b,x,y,x2,y2);//�ƶ�����

}

bool GridBeAttack(TBoard b,int x,int y,TSide byWho)

{//�ж�λ��(x,y)�������ܷ񱻳Ե�

    int i,j;

    for (i=0;i<BOARDSIZE;++i)

    {

        for (j=0;j<BOARDSIZE;++j)

        {

            if (b[i][j].side==byWho && CanMove(b,i,j,x,y,2))

            {//�ᱻ���ֳԵ���

                return true;

            }

        }

    }

    return false;

}

bool CanCastle(TBoard b,TSide side,int flag)

{//�ж��Ƿ��ܹ�����������λ

    int row,i;

    if (side==WHITE)

    {//�׷�������λ

        if (whitekingMoved==true)

        {//���Ѿ����ˣ�����������λ

            return false;

        }

        if (flag==3&&white7rookMoved==true)

        {//Ŀ�공�Ѿ����ˣ�����������λ

            return false;

        }

        if (flag==5&&white0rookMoved==true)

        {//Ŀ�공�Ѿ����ˣ�����������λ

            return false;

        }

    }

    else

    {//�ڷ�������λ

        if (blackkingMoved==true)

        {//���Ѿ�����

            return false;

        }

        if (flag==3 && black7rookMoved==true)

        {//Ŀ�공�Ѿ����ˣ�����������λ

            return false;

        }

        if (flag==5 && black0rookMoved==true)

        {//Ŀ�공�Ѿ����ˣ�����������λ

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

            {//����֮���Ƿ������ӣ�����������λ

                return false;

            }

        }

        for (i=0;i<5;++i)

        {

            if (GridBeAttack(b,row,i,Opponent(side))==true)

            {//��Ŀ��λ���ϻᱻ���ֳԵ�������������λ

                return false;

            }

        }

    }

    else

    {

        for (i=5;i<BOARDSIZE-1;++i)

        {

            if (b[row][i].side!=NONE)

            {//����֮���Ƿ������ӣ�����������λ

                return false;

            }

        }

        for (i=4;i<BOARDSIZE;++i)

        {

            if (GridBeAttack(b,row,i,Opponent(side)))

            {//��Ŀ��λ���ϻᱻ���ֳԵ�������������λ

                return false;

            }

        }

    }

    return true;//������Ҫ�󣬿���������λ

}

void Castle(TBoard b,TSide side,int flag)

{//����������λ,flag=3,��ʾ�������ĳ�King-side castle,flag=5ʱ����ʾQueen-side castle

    int row;

    if (side==WHITE)

    {

        if (whitecastled==true)

        {//�׷��Ƿ��Ѿ���λ���Ѿ��׹�λ������������

            result = ILLEGAL;

            return ;

        }

        else

            whitecastled = true;//������λ����

    }

    else

    {

        if (blackcastled==true)

        {//�ڷ��Ƿ��Ѿ���λ���Ѿ��׹�λ������������

            result = ILLEGAL;

            return;

        }

        else

            blackcastled = true;

    }

    if (CanCastle(b,side,flag)==true)

    {//�ж��Ƿ��ܹ���λ

        if (side==WHITE)

        {

            row = 0;

        }

        else

        {

            row = 7;

        }

        if (flag==3)

        {//����������λ

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

    {//�޷�������λ���˲��Ƿ�

        result = ILLEGAL;

    }

}

void GetKingPosition(TBoard b,TSide side,int &x,int &y)

{//Ѱ�ҹ�����λ��

    int i,j;

    for (i=0;i<BOARDSIZE;++i)

    {

        for (j=0;j<BOARDSIZE;++j)

        {

            if (b[i][j].pt==KING && b[i][j].side == side)

            {//�ҵ�ָ��������

                x = i;

                y = j;

                return;

            }

        }

    }

}

bool BeCheck(TBoard b,TSide side)

{//�ж��Ƿ񱻡�����"

    int x,y,i,j;

    TSide oppSide;

    GetKingPosition(b,side,x,y);//Ѱ�����side����

    oppSide = Opponent(side);//����

    for (i=0;i<BOARDSIZE;++i)

    {

        for (j=0;j<BOARDSIZE;++j)

        {

            if (b[i][j].side==oppSide && CanMove(b,i,j,x,y,2)==true)

            {//�ж϶����Ƿ��ܷ񽫾���flag=2��ʾ�˲��ǳ��ӣ�

                return true;

            }

        }

    }

    return false;

}

void CopyBoard(TBoard desBoard,TBoard srcBoard)

{//��������

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

{//�ж��Ƿ��ǽ���

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

                    {//�ж�side�������ܷ��(x,y)�ߵ�(x2,y2)

                        if ((x2!=x || y2!=y) && b[x][y].side!=b[x2][y2].side && CanMove(b,x,y,x2,y2,1))

                        {

                            if (b[x][y].pt==KING)

                            {

                                CopyBoard(b2,b);

                                ChessMove(b2,x,y,x2,y2);

                                if (!BeCheck(b2,side))

                                {//�ж�λ���Ƿ񱻡���������û�оͲ��ǽ���

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

{//�ж��Ƿ��Ѿ����������Ƿ�ĳһ����������

    int x,y,x2,y2;

    TBoard b2;

    if (!BeCheck(b,side))

    {//û�б�����

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

                            //���ܷ��(x,y)�ƶ���(x2,y2)

                            if (CanMove(b,x,y,x2,y2,1)==true)

                            {

                                CopyBoard(b2,b);

                                ChessMove(b2,x,y,x2,y2);

                                if (!BeCheck(b2,side)==true)

                                {//�ƶ���û�б�"����"

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

{//�������

    string move;//��ǰ��

    TSide side;

    side = WHITE;//�����ǰ׷�����

    int i;

    for(i=0;i<n;++i)

    {

        if(result==WHITEWIN||result==BLACKWIN||result==STALEMATE)

        {//����Ѿ�����������������û�����꣬״̬ΪDEAD

            result = DEAD;

            SkipInput(i);//������������

            return;

        }

        cin>>move;//���������ƶ�����

        if(move[0]!='O')

        {//����������λ

            MakeMove(b,move,side);//��������Ĳ���mv�����side�ƶ�����

        }

        else

        {//������λ

            Castle(b,side,move.length());

        }

        if (BeCheck(b,side)==true)

        {//�ƶ����Ӻ󱻶���"����"

            result = ILLEGAL;

        }

        if (result==PUZZLE || result==ILLEGAL)

        {//�Ƿ���������ԭ�������ӣ�����������

            SkipInput(i+1);

            return;

        }

        if (CheckMate(b,Opponent(side)))

        {//����������ʱ���жϻ�ʤ��

            if (side==WHITE)

            {

                result = WHITEWIN;

            }

            else

            {

                result = BLACKWIN;

            }

        

        }

        side = Opponent(side);//�����Է�����

    }

    if (result==DRAW && NoMoveAvailable(b,side)==true)

    {//�Ƿ��ǽ���

        result = STALEMATE;

    }

}

int main()

{

    TBoard board;//����

    //��������ض���

    freopen("chess.in","r",stdin);

    freopen("chess.out","w",stdout);

    while(cin>>n && n!=0)

    {

        init(board);//��ʼ������

        result = DRAW;//һ��ʼ������Ϊ�;�

        run(board);//�������

        cout<<RESULT[result]<<endl;

    }

    return 0;

}