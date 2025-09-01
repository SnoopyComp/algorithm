import java.util.*;

public class Main {
    public static void main(String[] args){
        int R, C, T;
        Scanner sc = new Scanner(System.in);
        R = sc.nextInt();
        C = sc.nextInt();
        T = sc.nextInt();

        int[][] arr = new int[R+2][C+2];

        Arrays.fill(arr[0],-1);
        Arrays.fill(arr[R+1],-1);
        for(int i=1; i<=R; i++){
            Arrays.fill(arr[i],-1);
            for(int j=1; j<=C; j++){
                arr[i][j] = sc.nextInt();
            }
        }

        Solution s = new Solution();
        s.sol(R,C,T,arr);
        sc.close();
    }
}

class Solution{
    int r,c,t;
    int xi;
    int[][] arr;
    int[] di = {0,1,0,-1};
    int[] dj = {1,0,-1,0};

    void sol(int R, int C, int T, int arr[][]){
        r=R; c=C; t=T;
        this.arr = arr;
        
        for(int i=1; i<=r; i++)
            if(arr[i][1] <0){ 
                xi = i;
                break;
            }
            
        for(int i=0; i<t; i++){
            diffuse();
            rotate();
        }
        System.out.println(sum());
    }

    void diffuse(){
        int[][] cache = new int[arr.length][];
        for(int i=0; i<arr.length; i++){
            cache[i] = Arrays.copyOf(arr[i], arr[i].length);
        }
        for(int i=1; i<=r; i++){
            for(int j=1; j<=c; j++){
                if(cache[i][j] <= 0) continue;
                int move = cache[i][j]/5;
                if(move == 0) continue;
                for(int k=0; k<4; k++){
                    int ni=i+di[k]; int nj=j+dj[k];
                    if(cache[ni][nj]<0) continue;
                    arr[i][j] -= move;
                    arr[ni][nj] += move;
                }
            }
        }
    }

    void rotate(){
        for(int i=xi-1; i>1; i--)
            arr[i][1] = arr[i-1][1];
        for(int i=xi+2; i<r; i++)
            arr[i][1] = arr[i+1][1];
        
        for(int j=1; j<c; j++){
            arr[1][j] = arr[1][j+1];
            arr[r][j] = arr[r][j+1];
        }

        for(int i=1; i<= xi-1; i++)
            arr[i][c] = arr[i+1][c];
        for(int i=r; i>= xi+2; i--)
            arr[i][c] = arr[i-1][c];

        for(int j=c; j>0; j--){
            arr[xi][j] = arr[xi][j-1];
            arr[xi+1][j] = arr[xi+1][j-1];
        }

        arr[xi][2] = 0;
        arr[xi+1][2] = 0;
    }

    int sum(){
        int sum = 0;
        for(int i=1; i<=r; i++){
            for(int j=1; j<=c; j++){
                if(arr[i][j] <= 0) continue;
                sum += arr[i][j];
            }
        }
        return sum;
    }
}
    
