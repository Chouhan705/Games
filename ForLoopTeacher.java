import java.util.*;
import java.lang.*;
import java.text.*;


public class ForLoopTeacher
{
    public static void main(String[] args)
    {
        System.out.println("Welcome");
        System.out.println("Here you will learn how to use for loop with better understanding");
        System.out.println("Enter 0 to begin");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        while (choice!= 0)
        {
            System.out.println("Enter 0 to begin");
            choice = sc.nextInt();
        }
        if(choice == 0 )
        {
            Start();
        }
    }
    public static void Start()
    {
        System.out.println("Lets Draw a map for your challenge");
        System.out.println("Enter the Length and Breadth for your map");
        System.out.println("Enter the same for a square map or different for a rectangular map");
        Scanner sc = new Scanner(System.in);
        int length = sc.nextInt();
        int breadth = sc.nextInt();
        DrawMap(length,breadth);
        System.out.println("If  you want to learn how this was map of yours was made");
        System.out.println("Enter 0 else Enter any other number to continue the challenge");
        int choice = sc.nextInt();
        if(choice == 0)
        {
            ExplainMap();
        }
        else
        {
            GenerateObstacle();
        }

    }
    public static void DrawMap(int length , int breadth)
    {
        char[][] MAP = new char[(length+2)][(breadth+2)];
        for(int i=0;i<(length+2);i++)
        {
            for(int j=0;j<(breadth+2);j++)
            {
                if(i==0||j==0||i==(length+1)||j==(breadth+1))
                {
                    MAP[i][j]='#';
                }
                else
                {
                    MAP[i][j]=' ';
                }
            }
        }
    for(char[] display : MAP)
    {
        for(char ch : display)
        {
            System.out.print(ch+" ");
        }
        System.out.println();
    }
    }
    public static void ExplainMap()
    {
        
    }
    public static void GenerateObstacle()
    {

    }
}
