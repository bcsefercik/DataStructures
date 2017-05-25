import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Test {
	
	//This method takes an input file and creates 
	//an output file which contains result of given commands.
	public static void query(BCSHashTable t, String input, String output){
		BufferedReader rd;
		try {
			rd = new BufferedReader(new FileReader(input));
			FileWriter writer = new FileWriter(output);
			
			String key = null;
			String value = null;
			String command = null;
			String ln;
			int i = 0;
			while(true){
				ln = rd.readLine();
				if(ln == null || ln.equals("")) break;
				i = ln.indexOf(" ");
				key = ln.substring(0, i);
				value = ln.substring(i,ln.length()).trim();
				t.put(key,value);
			}
			

			for(HashEntry he : t.entrySet()){
				System.out.println(he.getPosition()+":"+he.getKey()+" "+he.getValue());
				writer.write(he.getPosition()+":"+he.getKey()+" "+he.getValue()+"\r\n");
			}

			writer.write("\r\n");
			
			while(true){
				ln = rd.readLine();
				if(ln == null || ln.equals("")) break;

				i = ln.indexOf(" ");
				
				if(i>0){
					command = ln.substring(0, i).toLowerCase();
					value = ln.substring(i,ln.length()).trim();
				}else{
					command = ln;
				}

				
				///////Commands are distinguished and executed.
				
				if(command.equals("put")){
					int j = value.indexOf(" ");
					key = value.substring(0, j);
					value = value.substring(j,value.length()).trim();
					t.put(key, value);

					writer.write("\r\n");
					for(HashEntry he : t.entrySet()){
						writer.write(he.getPosition()+":"+he.getKey()+" "+he.getValue()+"\r\n");
					}
				}else if(command.equals("size")){
					writer.write("\r\nSize of the table is:"+t.size()+"\r\n");
				}else if(command.equals("containskey")){
					String r = Boolean.toString(t.containsKey(value));
					r = Character.toUpperCase(r.charAt(0)) + r.substring(1);
					writer.write("\r\n"+r+"\r\n");
				}
				
			}
			rd.close();	
            writer.close();
		}catch(FileNotFoundException e1){} catch(IOException e){}
	}
	
	public static void main(String[] args){
		BCSHashTable m = new BCSHashTable();
		
		query(m,"./input.txt","./output.txt");
		

		
	}
}
