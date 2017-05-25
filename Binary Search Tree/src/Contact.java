
public class Contact {
	
	private String name,surname;
	private String number;
	
	public Contact(String n, String s, String p){
		setName(n);
		setSurname(s);
		setNumber(p);
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getNumber() {
		
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		return "{"+name+" "+surname+" - "+number+"}";
	}

}
