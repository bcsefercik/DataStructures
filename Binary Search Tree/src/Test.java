import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Test {
	
	public static ArrayList<Contact> getContacts(String file){
		ArrayList<Contact> l = new ArrayList<Contact>();
		BufferedReader rd;
		try {
			rd = new BufferedReader(new FileReader(file));
			String[] line;
			String ln;
			Contact c;
			while(true){
				ln = rd.readLine();
				if(ln == null || ln.equals("")) break;
				line = ln.split(" ");
				c =new Contact(line[0],line[1],line[2]);
				l.add(c);
			}
			rd.close();	
		}catch(FileNotFoundException e1){} catch(IOException e){}
		return l;
	}

	//This method apply queries from a given text file on given tree.
	public static void applyCommands(String file, BCSBST<String, Contact> tree, String keytype){
		BufferedReader rd;
		try {
			rd = new BufferedReader(new FileReader(file));
			String[] line;
			String ln,key = null;
			Contact c;
			while(true){
				ln = rd.readLine();
				if(ln == null || ln.equals("")) break;
				line = ln.split(" ");

				if(keytype.toLowerCase().equals("name"))key = line[1];
				else if(keytype.toLowerCase().equals("surname"))key = line[2];
				else if(keytype.toLowerCase().equals("phone") || keytype.toLowerCase().equals("number"))key = line[3];
				
				if(line[0].toLowerCase().equals("insert")){
					c =new Contact(line[1],line[2],line[3]);
					tree.insert(key, c);
				}else if(line[0].toLowerCase().equals("delete")){
					tree.remove(key);
				}
				//tree.printTree();
			}
			rd.close();	
		}catch(FileNotFoundException e1){} catch(IOException e){}
	}
	
	public static void main(String[] args){
		try{
			ArrayList<Contact> contacts = getContacts("./database.txt");

			BCSBST<String,Contact> nameTree = new BCSBST<String,Contact>();
			BCSBST<String,Contact> surnameTree = new BCSBST<String,Contact>();
			BCSBST<String,Contact> numberTree = new BCSBST<String,Contact>();
			

			Contact c;
			for(int i=0; i<contacts.size(); i++){
				c = contacts.get(i);
				nameTree.insert(c.getName(), c);
				surnameTree.insert(c.getSurname(), c);
				numberTree.insert(c.getNumber(), c);
			}
			
			numberTree.printTree();
			applyCommands("./query.txt",numberTree,"number");
			numberTree.printTree();
			ArrayList<Contact> numberPreorder = numberTree.breadthfirst();
			for(int i=0; i<numberTree.size(); i++)
				System.out.println(numberPreorder.get(i));
			//System.out.println(numberTree.get("0222301329"));
			//numberTree.printFirst("0648572102");
		}catch(IllegalArgumentException e){
			System.out.println("Key is erronous!");
			e.printStackTrace();
		}
	}
	
}
