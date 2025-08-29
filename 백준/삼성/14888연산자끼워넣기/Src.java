import java.util.*;

public class Src{
    static int n;
    static int[] arr;
    static int[] operator = new int[4]; //+ - * /
    static int minSum=Integer.MAX_VALUE, maxSum=Integer.MIN_VALUE;
    public static void main(String[] args){
        setFields();
        search(1, arr[0]);
        System.out.println(maxSum);
        System.out.println(minSum);
    }

    static void setFields(){
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        arr = new int[n];
        for(int i=0; i<n; i++){
            arr[i] = sc.nextInt();
        }
        for(int i=0; i<4; i++){
            operator[i] = sc.nextInt();
        }
        sc.close();
    }

    static void search(int idx, int sum){    //idx: 1 ~ n-1
        if(idx >= n) {
            updateMinMax(sum);
            return;
        }
        for(int i=0; i<4; i++){
            if(operator[i] > 0){
                operator[i]--;
                search(idx+1, calc(i,sum,arr[idx]));
                operator[i]++;
            }
        }
    }

    static void updateMinMax(int sum){
        minSum = Math.min(minSum, sum);
        maxSum = Math.max(maxSum, sum);
    } 

    static int calc(int op, int a, int b){
        switch(op){
            case 0: return a+b;
            case 1: return a-b;
            case 2: return a*b;
            case 3: return a/b;
        }
        return 0;
    }
}





