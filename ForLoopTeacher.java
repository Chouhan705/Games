import java.util.*;
import java.lang.*;
import java.text.*;

public class ForLoopTeacher
{
    static int length, breadth;
    static char[][] MAP;

    public static void main(String[] args)
    {
        System.out.println("Welcome");
        System.out.println("Here you will learn how to use for loop with better understanding");
        System.out.println("Enter 0 to begin");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        while (choice != 0)
        {
            System.out.println("Enter 0 to begin");
            choice = sc.nextInt();
        }
        if (choice == 0)
        {
            Start();
        }
    }

    public static void Start()
    {
        System.out.println("Let's Draw a map for your challenge");
        System.out.println("Enter the Length and Breadth for your map");
        System.out.println("Enter the same for a square map or different for a rectangular map");
        Scanner sc = new Scanner(System.in);
        length = sc.nextInt();
        breadth = sc.nextInt();
        GenerateMap(length, breadth);
        DrawMap();
        System.out.println("If you want to learn how this map of yours was made");
        System.out.println("Enter 0 else \nEnter any other number to continue the challenge");
        int choice = sc.nextInt();
        if (choice == 0)
        {
            ExplainMap();
        }
        else
        {
            ChooseGameMode();
        }
    }

    public static void GenerateMap(int length, int breadth)
    {
        MAP = new char[(length + 2)][(breadth + 2)];
        for (int i = 0; i < (length + 2); i++)
        {
            for (int j = 0; j < (breadth + 2); j++)
            {
                if (i == 0 || j == 0 || i == (length + 1) || j == (breadth + 1))
                {
                    MAP[i][j] = '#'; // Walls
                }
                else
                {
                    MAP[i][j] = ' '; // Empty space
                }
            }
        }
    }

    public static void DrawMap() {
        int verticalSpacing = 2; // Adjust this for vertical spacing
        int horizontalSpacing = 3; // Adjust this for horizontal spacing
    
        for (char[] display : MAP) {
            // Print each character in the row with horizontal spacing
            for (char ch : display) {
                System.out.print(ch); // Print character
                // Print horizontal spaces
                for (int s = 0; s < horizontalSpacing; s++) {
                    System.out.print(" "); // Print additional spaces
                }
            }
            System.out.println();
        }
    }
    

    public static void ExplainMap()
    {
        // Do in free time
    }

    public static void ChooseGameMode()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose your Game Mode");
        System.out.println("1. Randomly generated Treasures");
        System.out.println("2. Maze Runner");
        int choice = sc.nextInt();
        switch (choice)
        {
            case 1:
                RandomTreasure();
                break;
            case 2:
                PathTracer();
                break;
            default:
                break;
        }
    }

    public static void RandomTreasure()
    {
        Scanner sc = new Scanner(System.in);
        if (breadth % 2 != 0)
        {
            MAP[0][1] = ' '; // Entrance
            MAP[length + 1][breadth] = ' '; // Exit
        }
        else
        {
            MAP[0][breadth / 2] = ' '; // Entrance
            MAP[length + 1][breadth / 2] = ' '; // Exit
        }
        System.out.println("Enter the number of Treasures you want");
        int Treasures = sc.nextInt();
        for (int i = 0; i < Treasures; i++)
        {
            int x = (int) Math.floor(Math.random() * (length - 1 + 1) + 1);
            int y = (int) Math.floor(Math.random() * (breadth - 1 + 1) + 1);
            MAP[x][y] = 'X'; // Treasure
        }
        DrawMap();
    }

    public static void PathTracer()
    {
        // Maze generation logic will go here
        System.out.println("Maze Runner mode is not yet implemented.");
    }
}
