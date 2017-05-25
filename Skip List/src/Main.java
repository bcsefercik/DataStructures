import java.io.IOException;

public class Main {
	private static SkipList skiplist;
	public static void main(String[] args) throws IOException{
		
		Operations ops = new Operations();
		//Initialize skip list with a given input file with init() operator. 
		skiplist = ops.init("input.txt");
		//Initialization of visualization.
		Visualization vis = new Visualization(skiplist);
		
		//Operations class knows the visualization class.
		ops.setVisualization(vis);
		
		
		//After all initializations program starts to get user commands from the console.
		ops.listen();
	}
}
