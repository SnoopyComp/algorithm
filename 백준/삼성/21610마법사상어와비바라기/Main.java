import java.util.*;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] arr = new int[n][n];
        for(int i=0; i<n ;i++){
            for(int j=0; j<n; j++){
                arr[i][j] = sc.nextInt();
            }
        }

        int[][] arr2 = new int[m][2];
        for(int i=0; i<m; i++){
            for(int j=0; j<2; j++){
                arr2[i][j] = sc.nextInt();
            }
        }

        Solution s = new Solution();
        s.sol(n, m, arr, arr2);
    }
}

class Solution{
    int[][] map;
    int n,m;
    int[][] move;
    int[][] cloud;
    int[] di = {0,0,-1,-1,-1,0,1,1,1};
    int[] dj = {0,-1,-1,0,1,1,1,0,-1};
    void sol(int n, int m, int[][] arr1, int[][] arr2){
        this.n = n; this.m = m; this.map = arr1; this.move = arr2;
        cloud = new int[n][n];
        cloud[n-1][0] = 1; cloud[n-1][1] = 1; cloud[n-2][0] = 1; cloud[n-2][1] = 1;

        int result = 0;
        for(int i=0; i<m; i++) result = step(i);
        System.out.println(result);
    }
    
    int step(int idx){
        int dir = move[idx][0];
        int dist = move[idx][1];
        //구름 이동
        int[][] movedCloud = new int[n][n];
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(cloud[i][j] == 0) continue;
                movedCloud[nextIdx(i,di[dir]*dist)][nextIdx(j,dj[dir]*dist)] = 1;
            }
        }
        //비 내림
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(movedCloud[i][j] == 0) continue;
                map[i][j]++;
            }
        }

        //복사버그
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(movedCloud[i][j] == 0) continue;
                for(int k=1; k<=4; k++){
                    int ni = i + di[k*2];
                    if(!isValid(ni)) continue;
                    int nj = j + dj[k*2];
                    if(!isValid(nj)) continue;
                    if(map[ni][nj] == 0) continue;
                    map[i][j]++;
                }
            }
        }

        int sum = 0;
        int[][] newCloud = new int[n][n];
        //구름 추출
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(movedCloud[i][j] == 0 && map[i][j] >= 2){
                    map[i][j] -= 2;
                    newCloud[i][j]++;
                }
                sum += map[i][j];
            }
        }
        cloud = newCloud;
        return sum;
    }

    int nextIdx(int idx, int move){
        return (idx + n*25 + move)%n;
    }

    boolean isValid(int idx){
        return idx>=0 && idx<n;
    }

    void printAll(){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
