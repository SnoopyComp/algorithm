import java.util.*;

public class Main{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        int[][] arr = new int[n][n];
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                arr[i][j] = sc.nextInt();
            }
        }
        Solution sol = new Solution();
        sol.solve(n, arr);
        sc.close();
    }
}

class Solution{
    int n;
    int[][] arr;
    int i,j;
    int exp;
    int[] di = {-1,0,0,1};
    int[] dj = {0,-1,1,0};

    void solve(int n, int[][] arr){
        this.n = n;
        this.arr = arr;
        init();
        int sum = 0;
        while(true){
            int dist = bfs();
            if(dist<0) break;
            sum += dist;
        }

        System.out.println(sum);
    }

    void init(){
        exp = 0;
        for(int idx1 = 0; idx1<n; idx1++){
            for(int idx2 = 0; idx2<n; idx2++){
                if(arr[idx1][idx2] == 9){
                    i = idx1; j=idx2;
                    arr[idx1][idx2] = 0;
                    return;
                }
            }
        }
    }

    int getSize(){
        int sum = 0;
        for(int idx=2; idx<7; idx++){
            sum += idx;
            if(exp < sum) return idx;
        }
        return 7;
    }

    int bfs(){  //find nearlist
        Deque<int[]> q = new LinkedList<>();
        q.add(new int[]{i,j});
        int[][] cache = new int[n][n];
        cache[i][j] = 1;

        int prev = 1000, last_i=1000, last_j=1000;
        
        while(!q.isEmpty()){
            int[] cur = q.pop();
            int ci = cur[0], cj = cur[1];
            if(cache[ci][cj] >= prev) break;

            for(int idx=0; idx<4; idx++){
                int ni = ci + di[idx], nj = cj + dj[idx];
                if(ni<0 || ni>=n || nj<0 || nj>=n) continue;
                if(cache[ni][nj] > 0) continue;
                if(arr[ni][nj] > getSize()) continue;

                // 먹을 수 있는 물고기 일때
                // prev 업데이트 *prev: 상어 ~ 물고기 까지의 거리
                // last_i보다 ni가 더 작으면 update
                // last_i == ni 이고, last_j == nj 이면 update
                // 둘다 아니면 업데이트 할 필요가 없음 -> 방문 기록만 남김


                if(arr[ni][nj]<getSize() && arr[ni][nj] > 0){
                    
                    prev = cache[ci][cj]+1;
                    if(last_i > ni){
                        last_i = ni; last_j = nj;
                    } else if (last_i == ni && last_j > nj){
                        last_i = ni; last_j = nj;
                    }                
                }
                q.add(new int[]{ni,nj});
                cache[ni][nj] = cache[ci][cj]+1;
            }
        }
        if(prev == 1000)
            return -1;
        exp++;
        i = last_i; j = last_j;
        arr[i][j] = 0;
        return prev-1;
    }
        void printAll(){
                for(int idx1 = 0; idx1<n; idx1++){
            for(int idx2 = 0; idx2<n; idx2++){
                System.out.print(arr[idx1][idx2] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
