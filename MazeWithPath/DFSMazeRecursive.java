//depth first traversal for the path
import java.util.*;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Stack;

public class DFSMazeRecursive
{       
    private InputGraphicMaze maze;
    private int R, C; 
    private boolean[][] V;

    public DFSMazeRecursive() 
    {       
       // an R rows x C columns maze
       maze = new InputGraphicMaze();
       R=maze.Rows(); C=maze.Cols();
       V=new boolean[R+1][C+1];
       for (int i=1; i<=R; i++)
          for (int j=1; j<=C; j++) V[i][j]=false;
       // Path holds the cells of the path
       LinkedList<Point> Path = new LinkedList<Point>();
       // Create the path
       CreatePath(maze, 1, 1, Path);
       // show the path in the maze
       maze.showPath(Path);
    }

    public boolean CreatePath(InputGraphicMaze maze, int srow, int scol, LinkedList<Point> L){
        boolean pathFound=false; 
        Point u = new Point(srow,scol);
        int r=(int) u.getX(), c=(int) u.getY();
        System.out.println("Visiting ("+r+","+c+")");
        V[r][c]=true;
        //checking if at end
        if ((r==R)&&(c==C)) {
        	L.addFirst(u);
        	System.out.println("added ("+r+","+c+") to path");
        	pathFound=true;
        	return pathFound;
        }
        //checks adjacent cells if not visited and path not found
        else {
        	if ((r>1) && (!V[r-1][c]) && (maze.can_go(r, c,'U')) && (!pathFound)) 
          		{pathFound = CreatePath(maze,r-1,c,L);}
        	if ((c<C) && (!V[r][c+1]) && (maze.can_go(r, c,'R')) && (!pathFound))        
          		{pathFound = CreatePath(maze,r,c+1,L);}
        	if ((r<R) && (!V[r+1][c]) && (maze.can_go(r, c, 'D')) && (!pathFound))             
          		{pathFound = CreatePath(maze,r+1,c,L);}
        	if ((c>1) && (!V[r][c-1]) && (maze.can_go(r, c, 'L')) && (!pathFound))             
          		{pathFound = CreatePath(maze,r,c-1,L);}
        }
           if(pathFound==true) {
        	   L.addFirst(u);
        	   System.out.println("added ("+r+","+c+") to path");
           }
        return pathFound;
    }

    public static void main(String[] args)
    {new DFSMazeRecursive();}
}