import java.util.*;
public class Explorer 
{
    static int x = 512;
    static int y = 512;

    //public static final String ANSI_RESET = "\u001B[0m"; 
    //public static final String ANSI_RED_BACKGROUND = "\u001B[41m"; 
    static char[][] Map = new char[1024][1024];
    public static void main(String[] args) 
    {
        for(int i=0; i<1024; i++)
        {
            for(int j=0; j<1024; j++)
            {
                Map[i][j] = ' ';
            }
                
        }
            
        printMap();
        Scanner sc = new Scanner(System.in);
        Map[x][y] = '*';

        while(true)
        {
            char choice = sc.next().charAt(0);
        switch(choice)
            {
                case 'W':
                case 'w':
                {
                    Map[x][y] = '#';
                    x--;
                    Map[x][y] = '*';
                    printMap();
                }
                break;
                case 'S':
                case 's':
                {
                    Map[x][y] = '#';
                    x++;
                    Map[x][y] = '*';
                    printMap();
                }
                break;
                case 'A':
                case 'a':
                {
                    Map[x][y] = '#';
                    y--;
                    Map[x][y] = '*';
                    printMap();
                }
                break;
                case 'D':
                case 'd':
                {
                    Map[x][y] = '#';
                    y++;
                    Map[x][y] = '*';
                    printMap();
                }
                break;
                case 'b':
                {
                    PrintBiggerMap();
                }
                break;
                case 'B':
                {
                    PrintMuchBiggerMap();
                }
            }
        }
    }
    
    public static void printMap()
    {
        System.out.println("\033[H\033[2J");
        for(int i = x-5  ; i <= x+5 ; i++)
        {
            for(int j = y-5 ; j <= y+5 ; j++)
            {
                if(i==x-5 || i == x+5)
                {
                    System.err.print("-"+" ");
                    continue;
                }
                else if(j==y-5 || j == y+5)
                {
                    System.out.print("|"+" ");
                    continue;
                }
                System.out.print(Map[i][j]+" ");
            }
            System.out.println();
        }
    }
    static void PrintBiggerMap()
    {
        System.out.println("\033[H\033[2J");
        for(int i = x-10  ; i <= x+10 ; i++)
        {
            for(int j = y-10 ; j <= y+10 ; j++)
            {
                if(i==x-10 || i == x+10)
                {
                    System.err.print("-"+" ");
                    continue;
                }
                else if(j==y-10 || j == y+10)
                {
                    System.out.print("|"+" ");
                    continue;
                }
                System.out.print(Map[i][j]+" ");
            }
            System.out.println();
        }
    }
    static void PrintMuchBiggerMap()
    {
        System.out.println("\033[H\033[2J");
        for(int i = x-25  ; i <= x+25 ; i++)
        {
            for(int j = y-25 ; j <= y+25 ; j++)
            {
                if(i==x-25 || i == x+25)
                {
                    System.err.print("-"+" ");
                    continue;
                }
                else if(j==y-25 || j == y+25)
                {
                    System.out.print("|"+" ");
                    continue;
                }
                System.out.print(Map[i][j]+" ");
            }
            System.out.println();
        }
    }
}
