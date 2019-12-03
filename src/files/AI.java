package files;

public class AI {
    private int board[][];
    public static final int absence = 0;
    public static final int playerOne = 1;
    public static final int playerTwo = 2;

    public AI() {
        board = new int[3][3];
    }

    public int getBoard(int a, int b) {
        if(a < 0 || a >= 3) {
            return absence;
        }
        if(b < 0 || b >= 3) {
            return absence;
        }

        return board[a][b];
    }

    public void setBoard(int a, int b, int token) {
        if(a < 0 || a >= 3) {
            return;
        } else if(b < 0 || b >= 3) {
            return;
        }

        board[a][b] = token;
    }

    public int []nextWinningMove(int token) {

        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(getBoard(i, j) == absence) {
                    board[i][j] = token;
                    boolean win = isWin(token);
                    board[i][j] = absence;
                    if(win) return new int[]{i,j};
                }
        return null;
    }

    public int inverse(int token) {
        return token == playerOne ? playerTwo : playerOne;
    }

    public int []nextMove(int token) {
        if(getBoard(1, 1) == absence) {
            return new int[]{1, 1};
        }
        int winMove[] = nextWinningMove(token);

        if(winMove!=null) {
            return winMove;
        }
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(getBoard(i, j) == absence)
                {
                    board[i][j] = token;
                    boolean ok = nextWinningMove(inverse(token)) == null;
                    board[i][j] = absence;
                    if(ok) return new int[]{i,j};
                }
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(getBoard(i, j) == absence)
                {
                    return new int[]{i,j};
                }
        return null;
    }

    public boolean isWin(int token) {
        final int DI[]={-1,0,1,1};
        final int DJ[]={1,1,1,0};
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++) {
                if(getBoard(i, j)!=token) {
                    continue;
                }
                for(int k=0;k<4;k++) {
                    int ctr = 0;
                    while(getBoard(i+DI[k]*ctr, j+DJ[k]*ctr)==token) {
                        ctr++;
                    }
                    if(ctr==3) {
                        return true;
                    }
                }
            }
        return false;
    }
}
