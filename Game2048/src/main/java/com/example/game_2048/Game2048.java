package com.example.game_2048;
import java.util.*;
public class Game2048 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to 2048!");
        int[][] arr = new int[4][4];
        while (true){
            printBox(arr);
            System.out.print("Try to move using A/W/S/D for Left/Up/Down/Right: ");
            char move = sc.nextLine().charAt(0);
            switch (move){
                case 'D':{
                    arr = moveRight(arr);
                    break;
                }
                case 'A':{
                    arr = moveLeft(arr);
                    break;
                }
            }
        }




    }
    public static void printBox(int[][] arr){
        int column = arr.length;
        int row = arr[1].length;
        arr = generateNum(arr, column, row);
        for (int[] columns : arr){
            for (int rows : columns){
                if (rows != 0){
                    System.out.print("[" + rows + "]");
                }
                else{
                    System.out.print("[ ]");
                }
            }
            System.out.println();
        }
    }

    public static int[][] generateNum(int[][] arr, int column, int row){
        Random r = new Random();
        int count = 0;
        while (count != 2) {
            int x = r.nextInt(4), y = r.nextInt(4), z = r.nextInt(2, 5);
            if (z == 3){ z--;}
            if (arr[x][y] != 0){ continue;}
            arr[x][y] = z;
            count++;
        }
        return arr;
    }

    public static int[][] moveRight(int[][] arr){
        int column = arr.length;
        int row = arr[0].length;
        for (int i = 0; i < column; i++){
            for (int j = row-1; j >= 0; j--){
                int rightEdge = 3 - j;
                if (arr[i][j] != 0){
                    for (int z = 1; z <= rightEdge; z++){
                        if (arr[i][j+z] == 0){
                            arr[i][j+z] = arr[i][j+z-1];
                            arr[i][j+z-1] = 0;
                        }
                        else if (arr[i][j+z] == arr[i][j+z-1]){
                            arr[i][j+z] += arr[i][j+z+1];
                            arr[i][j+z-1] = 0;
                        }
                    }
                }
            }
        }
        return arr;
    }

    public static int[][] moveLeft(int[][] arr){
        int column = arr.length;
        int row = arr[0].length;
        for (int i = 0; i < column; i++){
            for (int j = 1; j < row; j++){
                if (arr[i][j] != 0){
                    for (int z = 1; z < j+1; z++){
                        if (arr[i][j-z] == 0){
                            arr[i][j-z] = arr[i][j-z+1];
                            arr[i][j-z+1] = 0;
                        }
                        else if (arr[i][j-z] == arr[i][j-z+1]){
                            arr[i][j-z] += arr[i][j-z+1];
                            arr[i][j-z+1] = 0;
                        }
                    }
                }
            }
        }
        return arr;
    }
}
