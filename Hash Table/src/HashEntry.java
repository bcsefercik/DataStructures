//This class simplifies the process and handling of hash map entries.
//A HashEntry object knows its position, key and value.
public class HashEntry {
	private String key;
	private String value;
	private int position;
	
	public HashEntry(String k, String v){
		
		setKey(k);
		setValue(v);
	}

	public String getKey() {return key;}
	public void setKey(String key) {this.key = key;}
	public String getValue() {return value;}
	public void setValue(String value) {this.value = value;}
	public int getPosition() {return position;}
	public void setPosition(int position) {this.position = position;}

}
