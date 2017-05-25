
public class LinkedList {
 
	private Card head = null;
	private Card tail = null;
	private int size = 0;
	
	public LinkedList(){}
	
	
	public Card addLast(String suit, int rank){
		Card n = new Card(suit,rank,size);
		if(isEmpty()){
			head = n;
			tail = n;
		}else{
			Card a = tail;

			tail = n;
			a.setRight(tail);
			tail.setLeft(a);
		}

		size++;
		return n;
	}
	
	
	
	public Card removeFirst(){
		Card t = null;
		if(isEmpty())
			return t;
		t = head;
		
		head = t.getRight();
		
		if(size==1){
			tail = null;
			head = null;
		}
		
		size--;
		return t;
	}
	
	public boolean isEmpty(){
		return size()==0;
	}
	
	
	//Checks whether the entry with a given key is in the list.
	public boolean contains(String s, int r){
		return get(s,r)!=null ? true : false;
	}
	
	public Card get(String s, int r){
		if(isEmpty())
			return null;
		
		int initSize = size();
		
		Card prev = head;
		Card n = prev.getRight();
		
		if(prev.getRank()==r && s.toUpperCase().equals(prev.getSuitLetter()))
			return prev;
		
		while(initSize>1){
			if(n.getRank()==r && s.toUpperCase().equals(n.getSuitLetter()))
				return n;
			prev = n;
			n = n.getRight();
			initSize--;
		}
		
		return null;
	}
	
	public Card get(int i){
		if(isEmpty() || i>=size || i<0)
			return null;
		
		Card n = head;
		
		while(true){
			if(n.getIndex()==i)
				return n;
			n = n.getRight();
		}
		
	}
	
	
	public void set(int i, String suit, int rank){
		Card c = get(i);
		c.setSuitLetter(suit);
		c.setRank(rank);
	}
	
	public void set(int i, Card a){
		set(i,a.getSuitLetter(),a.getRank());
	}


	public void remove(String s, int r) throws IllegalArgumentException{
		if(isEmpty())
			throw new IllegalArgumentException("The key is illegal.");
		
		Card prev = head;
		Card n = prev.getRight();
		if(prev.getRank()==r && s.toUpperCase().equals(prev.getSuitLetter())){
			size--;

			head = n;
			n.setRight(n);
			prev.setRight(null);
			prev.setLeft(null);
			prev=null;
			return;}
		int initSize = size();
		
		while(initSize>1){
			if(n.getRank()==r && s.toUpperCase().equals(n.getSuitLetter())){
				n.getLeft().setRight(n.getRight());
				n.getRight().setLeft(n.getLeft());
				n=null;
				size--;
				return;}
			initSize--;
		}
	}
	
	public void print(){
		Card c = getFirst();
		
		for (int i=0; i<size(); i++){
			System.out.print(c);
			if(i<(size()-1))
				System.out.print("->");
			if(c.getRank()<10)
				System.out.print(" ");
			c = c.getRight();
		}
	}
	
	public int size(){return size;}

	public Card getFirst(){return head;}
	public Card getLast(){return tail;}
}
