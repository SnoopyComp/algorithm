#include <iostream>
#include <utility>
#include <vector>
#include <queue>
#define BOTTOM (DICE[0][0])

using namespace std;


int di[4] = {-1,0,1,0};  //상우하좌
int dj[4] = {0,1,0,-1};
int RIGHT=3, LEFT=4, MID=1, UP=2, DOWN=5, HIDDEN=6;
int DICE[3][3] = {{6,2,0},{4,1,3},{0,5,0}};
int N,M;
int K;

vector<vector<int>> map;

void rollDice(int dir){
    int hidden_i = 0, hidden_j = 0;
    int tmp = DICE[hidden_i][hidden_j];
    DICE[hidden_i][hidden_j] = DICE[1+di[dir]][1+dj[dir]];
    DICE[1+di[dir]][1+dj[dir]] = DICE[1][1];
    DICE[1][1] = DICE[1-di[dir]][1-dj[dir]];
    DICE[1-di[dir]][1-dj[dir]] = tmp;
}

int getNextDir(int i, int j, int dir){
    int nextDir;
    if(BOTTOM > map[i][j]) nextDir = (dir + 1)%4;
    else if(BOTTOM < map[i][j]) nextDir = (dir + 3)%4;
    else nextDir = dir;

    if(map[i+di[nextDir]][j+dj[nextDir]] < 0) nextDir = (nextDir+2)%4;
    return nextDir;
}

int getScore(int i, int j){
    int val = map[i][j];
    vector<vector<bool>> isVisited(N+2, vector<bool>(M+2,false));
    queue<pair<int, int>> q;

    q.push({i,j});
    int count = 0;

    while(!q.empty()){

        int ci = q.front().first;
        int cj = q.front().second;
        q.pop();
        if(map[ci][cj] != val || isVisited[ci][cj]) continue;

        count++;
        isVisited[ci][cj] = true;

        for(int idx=0; idx<4; idx++){
            int ni = ci + di[idx];
            int nj = cj + dj[idx];
            if(map[ni][nj] != val || isVisited[ni][nj]) continue;
            q.push({ni,nj});
        }
    }
    return val * count;
}


int main (){
    cin>>N>>M;
    cin>>K;

    map.assign(N+2, vector<int>(M+2,-1));
    for(int i=0; i<N; i++){
        for(int j=0; j<M; j++){
            cin>>map[i+1][j+1];
        }
    }

    int dir = 1;
    int score = 0;
    int i=1, j=1;
    while(K>0){
        K--;
        rollDice(dir);
        i = i + di[dir], j = j + dj[dir];
        score += getScore(i,j);
        dir = getNextDir(i,j,dir);
    }

    cout<<score;
}






/*

    2
4   1   3
    5

    6

to right

    2
6   4   1
    5

    3
*/