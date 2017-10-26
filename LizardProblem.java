import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
class Board{
    public Board(int[][] grid, int[][] lp,int[] fl){
        this.grid=grid;
        this.lp=lp;
        this.fl=fl;
    }
    int[][] lp;
    int[] fl;
    int[][] grid;
    
}

public class homework {
    public static int nl=0;
    public static int p;
    public static int n;
    public static int[][] grid;
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        List<String> ans=new ArrayList<>();
        ans=readFile("input.txt");
        String[] arrinput=ans.toArray(new String[ans.size()]);
        String method=arrinput[0];
        n=Integer.parseInt(arrinput[1]);
        p=Integer.parseInt(arrinput[2]);
        grid=new int[n][n];
        PrintStream o = new PrintStream(new File("output.txt"));
        
	       
        PrintStream console = System.out;
        
        
        System.setOut(o);
        
        int count=3;
        if(method.equals("BFS")){
            
            for(int i=0;i<n;i++){
                
                
                for(int j=0;j<n;j++){
                    grid[i][j]=Character.getNumericValue(arrinput[count].charAt(j));
                }
                count++;
            }
            boolean res=bfs(0,0);
            
            if(res){
                
                System.out.print("OK");
                for(int i=0;i<n;i++){
                    System.out.println();
                    for(int j=0;j<n;j++){
                        System.out.print(grid[i][j]);
                    }
                    
                }
            }
            else{
                System.out.print("FAIL");
            }
        }
        if(method.equals("DFS")){
            
            for(int i=0;i<n;i++){
                
                
                for(int j=0;j<n;j++){
                    grid[i][j]=Character.getNumericValue(arrinput[count].charAt(j));
                }
                count++;
            }
            boolean res=dfs(0,0);
            if(res){
                System.out.print("OK");
                for(int i=0;i<n;i++){
                    System.out.println();
                    for(int j=0;j<n;j++){
                        System.out.print(grid[i][j]);
                    }
                    
                }
            }
            else{
                System.out.print("FAIL");
            }
        }
        if(method.equals("SA")){
            
            int[][] gridsa=new int[n][n];
            for(int i=0;i<n;i++){
                
                for(int j=0;j<n;j++){
                    gridsa[i][j]=Character.getNumericValue(arrinput[count].charAt(j));
                }
                count++;
            }
            List<Integer> fl=new ArrayList<>();
            int[][] lp=new int[p][2];
            int l=0;
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    if(gridsa[i][j]==0){
                        fl.add((i*n)+j);
                        
                        
                    }
                }
                
            }
            for(int i=0;i<p;i++){
                int choice =(int) (Math.random()*(fl.size()-1));
                int r=(int)fl.get(choice)/n;
                int c=(int)fl.get(choice)%n;
                gridsa[r][c]=1;
                lp[l][0]=r;
                lp[l][1]=c;
                fl.remove(choice);
                l++;
            }
            int[] arr = new int[fl.size()];
            for(int i = 0; i < fl.size(); i++) arr[i] = fl.get(i);
            Board first = new Board(gridsa,lp,arr);
            Board res=simAnn(first,100,0,25,0.005,System.currentTimeMillis());
            int conf=noConflicts(res);
            if(conf==0){
                
                System.out.print("OK");
                for(int i=0;i<n;i++){
                    System.out.println();
                    for(int j=0;j<n;j++){
                        System.out.print(res.grid[i][j]);
                    }
                    
                    
                    
                }
            }
            else{
                System.out.print("FAIL");
            }
        }
        
    }
    public static boolean dfs(int row, int col){
        int j,k;
        
        if(nl==p)
            return true;
        if(row==n)
            return false;
        
        j=col;
        while(j<n){
            if(grid[row][j]==0&&isSafe(row,j)){
                grid[row][j]=1;
                nl++;
                boolean flag=false;
                k=j+1;
                while(k<n){
                    if(grid[row][k]==2){
                        if(dfs(row,k+1)){
                            return true;
                        }
                        else{
                            nl=nl-1;
                            grid[row][j]=0;
                            flag=true;
                            break;
                        }
                        
                        
                    }
                    k=k+1;
                }
                if(!flag){
                    if((p-nl)>count(row)){
                        grid[row][j]=0;
                        nl=nl-1;
                        return false;
                    }
                    row=row+1;
                    if(dfs(row,0)){
                        return true;
                    }
                    else{
                        row=row-1;
                        nl=nl-1;
                        grid[row][j]=0;
                    }
                }
                
            }
            j=j+1;
            
        }
        row=row+1;
        return dfs(row,0);
        
        
        
    }
    public static int count(int row){
        int c=0;
        int rows=n-row-1;
        for(int i=row;i<n;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j]==2)
                    c++;
            }
        }
        return (c+rows);
    }
    public static boolean isSafe(int r, int c){
        boolean row=false,col=false,diag1=false,diag2=false;
        row=isSafeRow(r,c);
        col=isSafeCol(r,c);
        diag1=isSafeDiag1(r,c);
        diag2=isSafeDiag2(r,c);
        if(row&&col&&diag1&diag2){
            return true;
        }
        return false;
        
    }
    public static boolean isSafeRow(int r,int c){
        //check left
        int llp=-1,ltp=-1,rlp=n+1,rtp=n+1;
        
        boolean left=false,right=false;
        
        
        
        
        for(int i=c;i>=0;i--){
            if(grid[r][i]==1){
                llp=i;
                break;
            }
        }
        for(int i=c;i>=0;i--){
            if(grid[r][i]==2){
                ltp=i;
                break;
            }}
        
        if(ltp>=llp){
            left=true;
        }
        for(int i=c;i<n;i++){
            if(grid[r][i]==1){
                rlp=i;
                break;
            }
            
            
        }
        for(int i=c;i<n;i++){
            if(grid[r][i]==2){
                rtp=i;
                break;
            }
        }
        if(rtp<=rlp){
            right=true;
        }
        
        if(right&&left){
            return true;
        }
        return false;
    }
    public static boolean isSafeCol(int r, int c){
        int llp=-1,ltp=-1,ulp=n+1,utp=n+1;
        boolean lower=false,upper=false;
        for(int i=r;i>=0;i--){
            if(grid[i][c]==1){
                llp=i;
                break;
            }
        }
        for(int i=r;i>=0;i--){
            if(grid[i][c]==2){
                ltp=i;
                break;
            }
        }
        if(ltp>=llp){
            lower=true;
        }
        for(int i=r;i<n;i++){
            if(grid[i][c]==1){
                ulp=i;
                break;
            }
        }
        for(int i=r;i<n;i++){
            if(grid[i][c]==2){
                utp=i;
                break;
            }
        }
        if(utp<=ulp){
            upper=true;
        }
        if(lower&&upper){
            return true;
        }
        return false;
        
    }
    public static boolean isSafeDiag1(int r, int c){
        int llp=-1,ltp=-1,rlp=n+1,rtp=n+1;
        boolean left=false,right=false;
        for(int i=r,j=c;i<n&&j>=0;i++,j--){
            if(grid[i][j]==1){
                llp=j;
                break;
            }
        }
        for(int i=r,j=c;i<n&&j>=0;i++,j--){
            if(grid[i][j]==2){
                ltp=j;
                break;
            }
        }
        if(ltp>=llp){
            left=true;
        }
        
        for(int i=r,j=c;i>=0&&j<n;i--,j++){
            if(grid[i][j]==1){
                rlp=j;
                break;
            }
        }
        for(int i=r,j=c;i>=0&&j<n;i--,j++){
            if(grid[i][j]==2){
                rtp=j;
                break;
            }
        }
        if(rtp<=rlp){
            right=true;
        }
        if(left&&right){
            return true;
        }
        return false;
    }
    public static boolean isSafeDiag2(int r, int c){
        int llp=-1,ltp=-1,ulp=n+1,utp=n+1;
        boolean lower=false,upper=false;
        for(int i=r,j=c;j>=0&&i>=0;i--,j--){
            if(grid[i][j]==1){
                llp=i;
                break;
            }
        }
        for(int i=r,j=c;j>=0&&i>=0;i--,j--){
            if(grid[i][j]==2){
                ltp=i;
                break;
            }
        }
        if(ltp>=llp){
            lower=true;
        }
        for(int i=r,j=c;i<n&&j<n;i++,j++){
            if(grid[i][j]==1){
                ulp=i;
                break;
            }
        }
        for(int i=r,j=c;i<n&&j<n;i++,j++){
            if(grid[i][j]==2){
                utp=i;
                break;
            }
        }
        if(utp<=ulp){
            upper=true;
        }
        if(lower&&upper){
            return true;
        }
        return false;
    }
    public static boolean prob(int delta,double temp){
        if(delta<=0){
            return true;
        }
        if(delta==temp){
            return false;
        }
        double C = 1/Math.log(delta / temp);
        double R = Math.random();
        
        if (R < C) {
            return true;
        }
        
        return false;
    }
    public static Board genNextBoard(Board board){
        int rand=(int)(Math.random() *(p-1));
        int rand_place=(int)(Math.random() *(board.fl.length-1));
        int t_r=(int)(board.fl[rand_place]/n);
        int t_c=(int)(board.fl[rand_place]%n);
        board.grid[t_r][t_c]=1;
        board.grid[board.lp[rand][0]][board.lp[rand][1]]=0;
        board.fl[rand_place]=n*board.lp[rand][0]+board.lp[rand][1];
        board.lp[rand][0]=t_r;
        board.lp[rand][1]=t_c;
        
        
        return board;
        
    }
    public static int noConflicts(Board board){
        int count=0;
        int[][] lp;
        int t_i,t_j,p_i,p_j;
        lp=board.lp;
        for(int i=0;i<p;i++){
            t_i=lp[i][0];
            t_j=lp[i][1];
            p_i=t_i+1;
            p_j=t_j+1;
            
            //checking row conflicts
            while(p_j<n){
                if (board.grid[t_i][p_j]==1)
                    count++;
                else if( board.grid[t_i][p_j]==2)
                    break;
                
                p_j++;
            }
            //checking column conflicts
            while(p_i<n){
                if (board.grid[p_i][t_j]==1)
                    count++;
                else if(board.grid[p_i][t_j]==2)
                    break;
                
                
                p_i++;
                
            }
            
            //checking the right lower diagonal 
            p_i=t_i+1;
            p_j=t_j+1;
            while(p_i<n&&p_j<n){
                if (board.grid[p_i][p_j]==1)
                    count++;
                else if(board.grid[p_i][p_j]==2)
                    break;
                p_i=p_i+1;
                p_j=p_j+1;
            }
            
            p_i=t_i-1;
            p_j=t_j+1;
            while(p_i>=0&&p_j<n){
                if (board.grid[p_i][p_j]==1)
                    count++;
                else if (board.grid[p_i][p_j]==2)
                    break;
                p_i=p_i-1;
                p_j=p_j+1;
                
            }
            
        }
        return count;
    }
    public static Board simAnn(Board curr_state, double i_t,double f_t,int iterations,double c_d_f,long start){
        while (i_t>f_t){
            for(int i=iterations;i>=0;i--){
                int[][] grid=new int[n][n];
                int[][] lp=new int[p][2];
                int[] fl=new int[curr_state.fl.length];
                //copying lp
                for(int j=0;j<p;j++)
                    for(int k=0;k<2;k++)
                        lp[j][k]=curr_state.lp[j][k];
                //copying fl
                for(int j=0;j<fl.length;j++)
                    fl[j]=curr_state.fl[j];
                //copying grid
                for(int j=0;j<n;j++)
                    for(int k=0;k<n;k++)
                        grid[j][k]=curr_state.grid[j][k];
                
                Board temp=new Board(grid,lp,fl);
                Board ns=genNextBoard(temp);
                int count_conflicts_present=noConflicts(curr_state);
                int count_conflicts_next=noConflicts(ns);
                if (count_conflicts_present==0)
                {	//System.out.println(count_conflicts_present);
                    return(curr_state);
                }
                int delta=count_conflicts_next-count_conflicts_present;
                //System.out.println(count_conflicts_present);
                if(prob(delta,i_t))
                    curr_state=ns;
            }
            
            i_t=i_t-c_d_f;
            
            long curr=System.currentTimeMillis();
            //System.out.println((curr-start));
            if((curr-start)>270000){
                break;	
            }
        }
        
        return (curr_state);
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
    public static boolean bfs(int row, int col){
        int j,k;
        
        if(nl==p)
            return true;
        if(row==n)
            return false;
        
        j=col;
        while(j<n){
            if(grid[row][j]==0&&isSafe(row,j)){
                grid[row][j]=1;
                nl++;
                boolean flag=false;
                k=j+1;
                while(k<n){
                    if(grid[row][k]==2){
                        if(bfs(row,k+1)){
                            return true;
                        }
                        else{
                            nl=nl-1;
                            grid[row][j]=0;
                            flag=true;
                            break;
                        }
                        
                        
                    }
                    k=k+1;
                }
                if(!flag){
                    if((p-nl)>count(row)){
                        grid[row][j]=0;
                        nl=nl-1;
                        return false;
                    }
                    row=row+1;
                    if(bfs(row,0)){
                        return true;
                    }
                    else{
                        row=row-1;
                        nl=nl-1;
                        grid[row][j]=0;
                    }
                }
                
            }
            j=j+1;
            
        }
        
        row=row+1;
        return bfs(row,0);
        
        
        
    }
    
}

