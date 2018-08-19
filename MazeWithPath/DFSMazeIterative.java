//depth first traversal for the path
import java.util.*;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Stack;

public class DFSMazeIterative
{       
    private InputGraphicMaze maze;
    private int R, C; 
    private boolean[][] V;

    public DFSMazeIterative() 
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
       CreatePath(maze, 1, 1, R, C, Path);
       // show the path in the maze
       maze.showPath(Path);
    }

    // Creates the path through maze, starting at cell (srow, scol)
    // and ending at cell (erow and ecol),  in L
    public boolean CreatePath(InputGraphicMaze maze, int srow, int scol, int erow, int ecol, LinkedList<Point> L){
    	int r=srow, c=scol, R=maze.Rows(), C=maze.Cols(); int size=R*C+1;
        Point[] P=new Point[size];
        boolean done=false; V[srow][scol]=true; 
        int scell=(srow-1)*C+scol; int cell;
        P[scell]=new Point(0,0); Point u=new Point(r, c);
        //using stack to ensure each node is visited in depth first traversal
        Stack<Point> Q=new Stack<Point>(); Q.push(u);         
        while ((Q.size()!=0)&&(!done)){
        	//updating most recent point
           u=Q.pop(); r=(int) u.getX(); c=(int) u.getY();
           V[r][c]=true;
           //if at end point, quit
           if ((r==erow)&&(c==ecol)) done=true;
           //otherwise push to queue and continue visiting nodes
           else {   	   
        	   if ((r>1)&&(!V[r-1][c])&&(maze.can_go(r, c,'U'))) 
        		   
               	{P[(r-2)*C+c]=u; Q.push(new Point(r-1, c));}
        	   if ((c<C)&&!(V[r][c+1])&&(maze.can_go(r, c,'R')))           
               	{P[(r-1)*C+c+1]=u; Q.push(new Point(r, c+1));}
        	   if ((r<R)&&(!V[r+1][c])&&(maze.can_go(r, c, 'D')))             
               	{P[r*C+c]=u; Q.push(new Point(r+1, c));}              
        	   if ((c>1)&&(!V[r][c-1])&&(maze.can_go(r, c, 'L')))             
               	{P[(r-1)*C+c-1]=u; Q.push(new Point(r, c-1));}
           }
        }
        //add points to path stored in P to L until we are back at start point
        while (!u.equals(P[scell])) 
        {         
           r=(int) u.getX(); c=(int) u.getY();
           L.addFirst(u); u=P[(r-1)*C+c]; 
        }
        return done;
    }

    public static void main(String[] args)
    {new DFSMazeIterative();}
}