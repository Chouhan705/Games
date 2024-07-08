import java.util.*;
import java.lang.*;
import java.text.*;
public class ForLoopTeacher
{
    static int length , breadth;
    static char[][] MAP;
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
        length = sc.nextInt();
        breadth = sc.nextInt();
        GenerateMap(length,breadth);
        DrawMap();
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
    public static void GenerateMap(int length , int breadth)
    {
        MAP = new char[(length+2)][(breadth+2)];
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
    }
    public static void DrawMap()
    {
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
        //Do in free time
    }
    public static void GenerateObstacle()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose Obstacle generation pattern");
        System.out.println("1. Randomly generated obstacles");
        System.out.println("2. Obstacles placed by you");
        int choice = sc.nextInt();
        System.out.println("Enter the number of Obstacles you want");
        int Obstacles = sc.nextInt();
        switch (choice) 
        {
            case 1:
            RandomObstacles(Obstacles);
            break;
            case 2:
            UserObstacles(Obstacles);
            break;

            default:
            break;
        }
    }
    public static void RandomObstacles(int Obstacles)
    {
        for(int i=0;i<Obstacles;i++)
        {
            int x = (int)Math.floor(Math.random()*(length-1+1)+1);
            int y = (int)Math.floor(Math.random()*(breadth-1+1)+1);
            MAP[x][y]='X';
        }
        DrawMap();
    }
    public static void UserObstacles(int Obstacles)
    {
        for(int i=0;i<Obstacles;i++)
        {
            System.out.println("Enter the coordinates of Obstacle "+(i+1));
            Scanner sc = new Scanner(System.in);
            int x = sc.nextInt();
            int y = sc.nextInt();
            MAP[x][y]='X';
        }
        DrawMap();
    }
}
