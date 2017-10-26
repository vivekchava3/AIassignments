import java.io.*;
import java.util.*;

public class homework {
	public static int n;
	public static boolean[][] visited;
	public static int count;
	public static int[] xcheck={0,1,0,-1};
	public static int[] ycheck={1,0,-1,0};
	//public static char[][] grid;
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		char grid[][];
		List<String> ans=new ArrayList<>();
		ans=readFile("input.txt");
		String[] arrinput=ans.toArray(new String[ans.size()]);
		n=Integer.parseInt(arrinput[0]);
		grid=new char[n][n];
		int line=3;
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				grid[i][j]=arrinput[line].charAt(j);
			}
			line++;
		}	
		/*List<Connect> li=new ArrayList<>();
		li=findConnected(grid);
		int r=li.get(0).row;
		int c=li.get(0).col;
		char[][] mod=applyGravity(grid,r,c);
		PrintStream o = new PrintStream(new File("output.txt"));
		 
	       
        PrintStream console = System.out;
 
        
        System.setOut(o);
        c=c+65;
        r=r+1;
        char first=(char)c;
        String pos=first+Integer.toString(r);
        
        System.out.println(pos);
        for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				System.out.print(mod[i][j]);
			}
			System.out.println();
		}*/
		List<Connect> li=new ArrayList<>();
		li=findConnected(grid);
		int r=li.get(0).row;
		int c=li.get(0).col;
		Board b=new Board(grid,0,0,0);
		alphabeta(b,4,-9999999,9999999,true,5);
		for(int i=0;i<b.child.size();i++){
			if(b.val==b.child.get(i).val){
				r=b.child.get(i).row;
				c=b.child.get(i).col;
				break;
			}
		}
		char[][] mod=applyGravity(grid,r,c);
		PrintStream o = new PrintStream(new File("output.txt"));
		 
	       
        PrintStream console = System.out;
 
        
        System.setOut(o);
        c=c+65;
        r=r+1;
        char first=(char)c;
        String pos=first+Integer.toString(r);
        
        System.out.println(pos);
        for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				System.out.print(mod[i][j]);
			}
			System.out.println();
		}
	}
	public static List<String> readFile(String filename)

	{
	  List<String> records = new ArrayList<String>();
	  try
	  {
	    BufferedReader reader = new BufferedReader(new FileReader(filename));
	    String line;
	    while ((line = reader.readLine()) != null)
	    {
	      records.add(line);
	    }
	    reader.close();
	    return records;
	  }
	  catch (Exception e)
	  {
	    System.err.format("Exception occurred trying to read '%s'.", filename);
	    e.printStackTrace();
	    return null;
	  }
	}
	public static char[][] applyGravity(char[][] grid, int row, int col){
		char[][] temp = new char[n][n];
		for(int x=0;x<n;x++)
			for(int y=0;y<n;y++)
				temp[x][y]=grid[x][y];
		//code goes here for pop
		if(temp[row][col]!='*')
		temp=dfsPlace(row,col,temp,temp[row][col]);
		//code goes here for gravity
		
		for(int k=0;k<n;k++){
			int i=n-1,j=n-1;
		while(i>=0&&j>=0){
			if(i==j){
				if(temp[i][k]!='*'){
					i--;
					j--;
				}
				else{
					j--;
				}
			}
			else if(temp[j][k]=='*'){
				j--;
			}
			else {
				char t=temp[j][k];
				temp[j][k]=temp[i][k];
				temp[i][k]=t;
				i--;
				j--;
			}
			
			
		}
		}
		
		
		
		return temp;	
	}
	public static char[][] dfsPlace(int i, int j, char[][] grid, int val){
		grid[i][j]='*';
		for (int k=0;k<4;k++){
			int x=i+xcheck[k];
			int y=j+ycheck[k];
			if(checkEqual(x,y,grid,val)/*&&!visited[x][y]*/){
				dfsPlace(x,y,grid,val);
			}
		}
		return grid;
	}
	public static List<Connect> findConnected(char[][] grid){
		//code goes here
		List<Connect> list=new ArrayList<>();
		visited=new boolean[n][n];
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if(!visited[i][j]&&grid[i][j]!='*'){
					count=0;
					char val=grid[i][j];
					dfsSearch(i,j,grid,val);
					list.add(new Connect(count,i,j));
				}
			}
		}
		Collections.sort(list, new Comparator<Connect>() {
		    @Override
		    public int compare(Connect l1, Connect l2) {
		       
		        return l1.val > l2.val ? -1 : (l1.val < l2.val) ? 1 : 0;
		    }
		});

		return list;
	}
	public static void dfsSearch(int i,int j,char[][] grid,int val){
		visited[i][j]=true;
		count++;
		for (int k=0;k<4;k++){
			int x=i+xcheck[k];
			int y=j+ycheck[k];
			if(checkEqual(x,y,grid,val)&&!visited[x][y]){
				dfsSearch(x,y,grid,val);
			}
		}
	}
	public static boolean checkEqual(int i,int j,char[][] grid,int val){
		if(i<0) return false;
		if(j<0) return false;
		if(i>=n) return false;
		if(j>=n) return false;
		
		if(grid[i][j]!=val) return false;
		
		return true;
		
	}
	public static int alphabeta(Board b, int depth, int alpha, int beta, boolean max,int br){
		int v;
		b.child=generateChildren(b);
		if(depth==0||b.child.size()==0){
		return b.max-b.min;
	}
		
	if(max){
		v=-9999999;
		
		for(int i=0;i<b.child.size()&&i<br;i++){
			
			b.child.get(i).max=b.max+b.child.get(i).fruit;
			b.child.get(i).min=b.min;
			v=Math.max(v,alphabeta(b.child.get(i),depth-1,alpha,beta,false,br));
			b.val=v;
			alpha=Math.max(alpha, b.val);
			if(beta<=alpha) break;
		}
		return b.val;
		
		
	}
	else{
		v=9999999;
		for(int i=0;i<br&&i<b.child.size();i++){
			b.child.get(i).min=b.min+b.child.get(i).fruit;
			b.child.get(i).max=b.max;
			v=Math.min(v,alphabeta(b.child.get(i),depth-1,alpha,beta,true,br));
			b.val=v;
			beta=Math.min(alpha, b.val);
			if(beta<=alpha) break;
		}
		return b.val;
	
	}
	
	}
	public static List<Board> generateChildren(Board b){
		List<Connect> li =new ArrayList<>();
		li=findConnected(b.grid);
		for(int i=0;i<li.size();i++){
			
			b.child.add(new Board(applyGravity(b.grid,li.get(i).row,li.get(i).col),li.get(i).row,li.get(i).col,li.get(i).val));
			
		}
		return b.child;
	}
	
}
class Board{
	public char[][] grid;
	List<Board> child =new ArrayList<>();
	int row;
	int col;
	int val;
	int fruit;
	int max;
	int min;
	Board(char[][] grid,int row,int col, int fruit){
		this.grid=grid;
		this.row=row;
		this.col=col;
		this.fruit=fruit*fruit;
		//this.val=val;
	}
}
class Connect{
	int val;
	int row;
	int col;
Connect(int val,int row,int col){
	this.val=val;
	this.row=row;
	this.col=col;
}
}
