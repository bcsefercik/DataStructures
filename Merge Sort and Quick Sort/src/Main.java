import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static Deck d;
	static BufferedReader in;
	//This method allows us to listen user I/O.
	public static void listen() throws IOException{
		InputStreamReader isr = new InputStreamReader(System.in);
        in = new BufferedReader(isr);
		String command;
		while(true){
			System.out.print("\nNew command: ");
			String ln = in.readLine();
			String[] ins = ln.split("\\*");
			command = ins[0].toLowerCase();
			
			if(command.equals("exit")){
				System.out.println("Bye...");
				break;
			}else if(command.equals("createdeck")){
				d.createDeck(ins[1]);
			}else if(command.equals("printdeck") || command.equals("p")){
				d.printDeck();
			}else if(command.equals("mergesort")){
				d.mergeSort();
			}else if(command.equals("quicksort")){
				d.quickSort();
			}else{
				System.out.println("Please enter a valid command.");
			}
		}
	}
	
	
	
	
	
	
	public static void main(String[] args) throws IOException{
		d = new Deck();
		
		listen();
	}
	public static void p(Object o){System.out.println(o);}
}
