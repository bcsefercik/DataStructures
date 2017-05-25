import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Operations {
	private Visualization v;
	public BufferedReader rd;
	public FileReader fd;

	SkipList sl = null;
	BufferedReader in;
	
	//This method returns a skip list with a given input file.
	public SkipList init(String input) throws IOException{
		fd = new FileReader(input);
		rd = new BufferedReader(fd);
		String ln = rd.readLine();
		String[] ins = ln.split(" ");
		
		ln = rd.readLine();
		int size = Integer.parseInt(ln);
		sl = new SkipList(size);
		
		for(String i : ins){
			sl.insert(Integer.parseInt(i));
		}
		fd.close();
		fd=null;
		rd.close();	
		rd = null;
		
		return sl;
	}
	
	//This method load the skip list from a given file.
	public void load(String inp) throws IOException{
		fd = new FileReader(inp);
		rd = new BufferedReader(fd);
		String ln;
		String[] linesp;
		int kk;
		int ll;

		sl.empty();
		while((ln = rd.readLine())!=null && !ln.equals("")){
			linesp = ln.split(" ");
			kk = Integer.parseInt(linesp[0]);
			ll = Integer.parseInt(linesp[1]);
			sl.insert(kk, ll);
		}
		fd.close();
		fd=null;
		rd.close();	
		rd = null;
	}
	
	

	//This method allows us to listen user I/O.
	public void listen() throws IOException{
		GM
		String command;
		int key=0;
		while(true){
			System.out.print("\nNew command: ");
			String ln = in.readLine();
			String[] ins = ln.split("\\*");
			String[] fileins = ln.split(" ");
			String filename = "";
			command = ins[0].toLowerCase();
			if(ins.length>1)
				key = Integer.parseInt(ins[1]);
			
			if(fileins.length>1){
				command = fileins[0].toLowerCase();
				filename = fileins[1];}
			
			if(command.equals("exit")){
				System.out.println("Bye...");
				break;
			}else if(command.equals("search")){
				sl.search(key);
				v.visiualizeLastSearch();
			}else if(command.equals("insert")){
				sl.search(key);
				System.out.println("Search Result:");
				v.visiualizeLastSearch();
				sl.insert(key);
				System.out.println("\nSkip List After Insertion:");
				v.visiualizeSkipList();
			}else if(command.equals("save")){
				FileWriter writer;
				try {
					writer = new FileWriter("./"+filename);
					Node n = sl.getFrom(0,0).getRight();
					writer.write(n.getKey()+" "+sl.findLevel(n.getKey()));
					for(int i=1;i<sl.size();i++){
						n = n.getRight();
						writer.write("\r\n"+n.getKey()+" "+sl.findLevel(n.getKey()));
					}
		            writer.close();
		            writer = null;
					System.out.println("Skip List has been saved successfully.");
				}catch(IOException e){}
			}else if(command.equals("load")){
				load("./"+filename);
				v.visiualizeSkipList();		
			}else{
				System.out.println("Please enter a valid command.");
			}
		}
	}
	
	public void setVisualization(Visualization vis){v = vis;}
	
	
}
