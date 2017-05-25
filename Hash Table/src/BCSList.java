
public class BCSList {
	
	//The Node class of a single linked list with standard methods is defined. 
	//Its entries are HashEntry objects.
	protected class Node{
		private HashEntry elt;
		private Node right;
		public Node(HashEntry e, Node r){
			setElt(e);
			setRight(r);
		}
		public Node(HashEntry e){
			this(e,null);
		}
		public HashEntry getElt() {return elt;}
		public void setElt(HashEntry elt) {this.elt = elt;}
		public Node getRight() {return right;}
		public void setRight(Node right) {this.right = right;}	
	}
	
	//I decided to distinguish last and first entries of the list with the variables below. 
	private Node head = null;
	private Node tail = null;
	private int size = 0;
	
	public BCSList(){}
	
	
	//With this method the given HashEntry object added to the end of the list.
	//For this project's purpose this approach is better in terms of time complexity.
	public void addLast(HashEntry e){
		Node n = new Node(e);
		if(isEmpty()){
			head = n;
			tail = n;
		}else{
			tail.setRight(n);
			tail = n;
		}
		
		size++;
	}
	//This method returns the first entry of the list, 
	//then removes the object from the list.
	//For this project's purpose this approach is better in terms of time complexity.
	public HashEntry removeFirst(){
		HashEntry t = null;
		if(isEmpty())
			return t;
		
		t = head.getElt();
		head = head.getRight();
		
		if(size==1)
			tail = null;
		
		size--;
		return t;
	}
	
	//It puts the entries of the nodes of the list into an array, then return this array.
	//I needed this make some processes easier.
	public HashEntry[] toArray(){

		if(isEmpty())
			return null;
		
		HashEntry[] a = new HashEntry[size()];

		Node n = head;
		for(int i=0; i<size(); i++){
			a[i] = n.getElt();
			n = n.getRight();
		}
		return a;
	}
	
	
	public boolean isEmpty(){
		return size()==0;
	}
	
	
	//Checks whether the entry with a given key is in the list.
	public boolean contains(String key){
		return get(key)!=null ? true : false;
	}
	
	
	//This method returns a HashEntry belonging to the given key.
	public HashEntry get(String key){
		if(isEmpty())
			return null;
		
		Node prev = head;
		Node n = prev.getRight();
		
		if(prev.getElt().getKey().equals(key))
			return prev.getElt();
		while(n!=null){
			if(n.getElt().getKey().equals(key))
				return n.getElt();
			prev = n;
			n = n.getRight();
		}
		
		return null;
	}
	
	//This method removes a HashEntry from the list belonging to a given key.
	public void remove(String key) throws IllegalArgumentException{
		if(isEmpty())
			throw new IllegalArgumentException("The key is illegal.");
		
		Node prev = head;
		Node n = prev.getRight();
		if(prev.getElt().getKey().equals(key)){
			size--;

			head = n;
			prev.setElt(null);
			n = null;
			return;}
		
		while(n!=null){
			if(n.getElt().getKey().equals(key)){
				prev.setRight(n.getRight());
				n.setRight(null);
				n.setElt(null);
				size--;
				return;}
			n = n.getRight();
		}
	}
	
	public int size(){return size;}
}
