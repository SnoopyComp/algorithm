#include <vector>
#include <iostream>
#include <algorithm>
#define WHITE 0
#define RED 1
#define BLUE 2

using namespace std;

int di[] = {0,0,0,-1,1};
int dj[] = {0,1,-1,0,0};

vector<vector<vector<int>>> map;
vector<vector<int>> colorMap;
vector<int> directions;
int N, K;

bool moveK(int k);
pair<int,int> findLoc(int idx);

int main (){
    cin>>N>>K;
    colorMap.assign(N+2, vector<int>(N+2, BLUE));
    map.assign(N+2, vector<vector<int>>(N+2, vector<int>()));
    directions.assign(K+1,0);

    for(int i=1; i<=N; i++){
        for(int j=1; j<=N; j++){
            cin>>colorMap[i][j];
        }
    }

    for(int i=1; i<=K; i++){
        int idx_i, idx_j, dir;
        cin>>idx_i>>idx_j>>dir;
        map[idx_i][idx_j].push_back(i);
        directions[i] = dir;
    }

    for(int i=1; i<=1000; i++){
        for(int idx=1; idx<=K; idx++){
            if(moveK(idx)) {
                cout<<i;
                return 0;
            }
        }
    }
    cout<<"-1"<<endl;
}

bool moveK(int k){
    auto a = findLoc(k);
    int i=a.first, j=a.second;
    auto itr = find(map[i][j].begin(), map[i][j].end(), k);

    // 다음 위치 계산
    int dir = directions[k];
    int ni = i + di[dir], nj = j + dj[dir];
    switch(colorMap[ni][nj]){
        case WHITE:
            map[ni][nj].insert(map[ni][nj].end(), itr, map[i][j].end());
            map[i][j].erase(itr, map[i][j].end());
            break;
        case RED:
            map[ni][nj].insert(map[ni][nj].end(), map[i][j].rbegin(), make_reverse_iterator(itr));
            map[i][j].erase(itr, map[i][j].end());
            break;
            case BLUE:
            dir = dir - 1 + (dir%2)*2;
            directions[k] = dir;
            ni = i + di[dir], nj = j + dj[dir];
            if(colorMap[ni][nj] == BLUE) 
                break;
            else if (colorMap[ni][nj] == WHITE)
                map[ni][nj].insert(map[ni][nj].end(), itr, map[i][j].end());
            else if (colorMap[ni][nj] == RED)
                map[ni][nj].insert(map[ni][nj].end(), map[i][j].rbegin(), make_reverse_iterator(itr));
            map[i][j].erase(itr, map[i][j].end());
            break;
    }
    return map[ni][nj].size() >= 4;
}



pair<int,int> findLoc(int idx){
    for(int i=1; i<=N; i++){
        for(int j=1; j<=N; j++){
            if(find(map[i][j].begin(), map[i][j].end(), idx) != map[i][j].end()){
                return {i,j};
            }
        }
    }
    //something wrong
    return {-1,-1};
}