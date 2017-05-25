import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class BCSBST <K,E>{
	
	@SuppressWarnings("hiding")
	protected class Node<K,E>{
		private E elt;
		private  Node<K,E> right;
		private  Node<K,E> left;
		private  Node<K,E> parent;
		private K key;
		private int x,y;
		
		public Node(K key, E e, Node<K,E> r, Node<K,E> l, Node<K,E> p){
			elt = e;
			right = r;
			left = l;
			parent = p;
			this.key = key;
		}

		public Node(K key, E e){
			elt = e;
			right = null;
			left = null;
			parent = null;
			this.key = key;
		}
		
		public Node<K,E> getParent() {return parent;}
		public Node<K,E> setParent(Node<K,E> parent) {this.parent = parent;return parent;}
		public Node<K,E> getLeft() {return left;}
		public Node<K,E> setLeft(Node<K,E> left) {this.left = left;return left;}
		public Node<K,E> getRight() {return right;}
		public Node<K,E> setRight(Node<K,E> right) {this.right = right;return right;}
		public E getElement() {return elt;}
		public void setElement(E elt) {this.elt = elt;}
		public K getKey() {return key;}
		public void setKey(K key) {this.key = key;}
		public boolean hasLeft(){return left!=null;}
		public boolean hasRight(){return right!=null;}
		public boolean isLeaf(){return (left==null&&right==null);}
		public boolean isRoot(){return parent==null;}
		public void setX(int a){x = a;}
		public int getX(){return x;}
		public void setY(int a){y = a;}
		public int getY(){return y;}
		
		
		private int compareTo(Node<K,E> nod){
			int r = 0;
			K a = this.getKey();
			K b = nod.getKey();
			if(a instanceof String)
				r = ((String) a).toLowerCase().compareTo(((String) b).toLowerCase());
			else if(a instanceof Integer)
				r = Integer.compare((int)a,(int)b);
			else if(a instanceof Long)
				r = Long.compare((int)a,(int)b);
			return r;
		}
		
		public int childDirection(){
			//If negative left child, if positive right child, else root.
			if(parent!=null)
				return this.compareTo(this.getParent());
			else
				return 0;
		}
		
	}
	private int size = 0;
	protected Node<K,E> root = null;
	
	public BCSBST(){}
	
	
	
	
	public Node<K,E> createNode(K key, E e, Node<K,E> r, Node<K,E> l, Node<K,E> p){
		return new Node<K,E>(key,e,r,l,p);
	}
	

	public int compare(K a,K b){
		int r = 0;
		if(a instanceof String)
			r = ((String) a).toLowerCase().compareTo(((String) b).toLowerCase());
		else if(a instanceof Integer)
			r = Integer.compare((int)a,(int)b);
		else if(a instanceof Long)
			r = Long.compare((long)a,(long)b);
		return r;
	}
	
	
	public void insert(K key, E elt) throws IllegalArgumentException{
		if(isEmpty()){
			addRoot(key,elt);
		}else{
			int direction = 0;
			int lastDirection = 0;
			Node<K,E> current = root();
			Node<K,E> parent = root();
			
			while(current!=null){
				parent = current;
				direction = compare(key,current.getKey());
				if(direction<0){
					current = parent.getLeft();
				}else if (direction>0){
					current = parent.getRight();
				}else{
					throw new IllegalArgumentException("This key, element pair is already in the tree.");
				}

				lastDirection = direction;
			}
			if(lastDirection<0)
				addLeft(parent,key,elt);
			else if(lastDirection>0)
				addRight(parent,key,elt);
		}
		size++;
	}
	

	
	
	public void remove(K key) throws IllegalArgumentException{
		
		Node<K,E> n = getNode(key);
		Node<K,E> p = n.getParent();
		
		if(n.isLeaf()){			
			if(n.childDirection()<0){
				p.setLeft(null);
				System.out.println("asd");
			}else if(n.childDirection()>0){
				p.setRight(null);
			}else{
				//root and leaf
				root = null;
			}
		}else{
			Node<K,E> rep;
			Node<K,E> tar;
			if(!n.hasLeft()){
				rep = n.getRight();
			}else if(!n.hasRight()){
				rep = n.getLeft();
			}else{
				rep = n.getLeft();
				tar = rep.getRight();
				while (tar!=null){
					rep = tar;
					tar = tar.getRight();
				}
			}
			Node<K,E> repParent = rep.getParent();
			if(n.isRoot())
				root=rep;
			//System.out.println("node: "+n.getKey()+ " rep: "+rep.getKey() +" rep parent: "+ repParent.getKey());

			if(rep.childDirection()<0){
				repParent.setLeft(rep.getRight());
				if(rep.hasRight())
					rep.getRight().setParent(repParent);
			}else{
				repParent.setRight(rep.getLeft());
				if(rep.hasLeft())
					rep.getLeft().setParent(repParent);
			}
			if(p!=null){
				if(n.childDirection()<0)
					p.setLeft(rep);
				else if(n.childDirection()>0)
					p.setRight(rep);
			}
			rep.setParent(n.getParent());
			if(!repParent.equals(n) || rep.isLeaf())
				rep.setLeft(n.getLeft());
			if(!repParent.equals(n) || rep.isLeaf())
				rep.setRight(n.getRight());

			if(n.hasRight())
				n.getRight().setParent(rep);
			if(n.hasLeft())
				n.getLeft().setParent(rep);
		}
		n.setLeft(null);
		n.setRight(null);
		n.setParent(null);
		n.setElement(null);
		size--;
	}
	
	public void delete(K key){
		remove(key);
	}
	
	public E get(K key) throws IllegalArgumentException{return getNode(key).getElement();}
	private Node<K,E> getNode(K key) throws IllegalArgumentException{
		int direction = 0;
		Node<K,E> current = root();
		
		while(current!=null){
			direction = compare(key,current.getKey());
			if(direction<0){
				current = current.getLeft();
			}else if (direction>0){
				current = current.getRight();
			}
			else{
				return current;
			}
		}
		throw new IllegalArgumentException("There is no element related to key '" +key+ "' in the tree.");
	}
	
	public K findCeil(K key) throws IllegalArgumentException{
		getNode(key);
		Node<K,E> n = root();
		K ceil = null;
		while (n != null) {
			if (compare(key, n.getKey())<0) {
				ceil = n.getKey();
				n = n.getLeft();
			} else {
				n = n.getRight();
			}
		}		
		return ceil;
	}
	
	public K findFloor(K key) throws IllegalArgumentException{
		getNode(key);
		Node<K,E> n = root();
		K floor = null;
		while (n != null) {
			if (compare(key, n.getKey())>0) {
				floor = n.getKey();
				n = n.getRight();
			} else {
				n = n.getLeft();
			}
		}		
		return floor;
	}
	
	public E rootElement(){return root.getElement();}
	private Node<K,E> root(){return root;}
	private Node<K,E> addRoot(K k,E e) throws IllegalStateException{
		if(!isEmpty()) throw new IllegalStateException("There is a root, already!");
		root = new Node<K,E>(k,e,null,null,null);
		return root;
	}

	private Node<K,E> addRight(Node<K,E> c,K k, E e) throws IllegalArgumentException{
		if(c.hasRight())
			throw new IllegalArgumentException("There is a right child, already!");	
		Node<K,E> r = new Node<K,E>(k,e,null,null,c);
		return c.setRight(r);
	}
	private Node<K,E> addLeft(Node<K,E> c,K k, E e) throws IllegalArgumentException{
		if(c.hasLeft())
			throw new IllegalArgumentException("There is a left child, already!");	
		Node<K,E> l = new Node<K,E>(k,e,null,null,c);
		return c.setLeft(l);
	}
	
	public ArrayList<E> preorder(){

		ArrayList<E> r = new ArrayList<E>();
		return preorder(root,r);
	}

	public ArrayList<E> preorder(Node<K,E> n, ArrayList<E> r){
		
		r.add(n.getElement());
		
		if(n.hasLeft())
			preorder(n.getLeft(),r);
		if(n.hasRight())
			preorder(n.getRight(),r);
		
		return r;
	}
	
	public ArrayList<E> postorder(){

		ArrayList<E> r = new ArrayList<E>();
		return postorder(root,r);
	}
	public ArrayList<E> postorder(Node<K,E> n, ArrayList<E> r){
		
		
		if(n.hasLeft())
			postorder(n.getLeft(),r);
		if(n.hasRight())
			postorder(n.getRight(),r);

		r.add(n.getElement());
		return r;
	}
	
	public ArrayList<E> breadthfirst(){
		ArrayList<E> r = new ArrayList<E>();
		if(!isEmpty()){
			LinkedBlockingQueue<Node<K,E>> q = new LinkedBlockingQueue<Node<K,E>>();
			q.add(root);
			while(!q.isEmpty()){
				try {
					Node<K,E> n = q.take();
					r.add(n.getElement());
					if(n.hasLeft())
						q.add(n.getLeft());
					if(n.hasRight())
						q.add(n.getRight());
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return r;
	}
	
	public boolean isEmpty(){ return size()==0;}
	public int size(){return size;}
	
	public void printFirst(K key){
		Node <K,E> a = getNode(key);
		String r = "";
		if(!a.isRoot())
			r+= "Parent: "+a.getParent().getKey()+" ";
		if(a.hasLeft())
			r+= "Left: "+a.getLeft().getKey()+" ";
		if(a.hasRight())
			r+= "Right: "+a.getRight().getKey();
		System.out.println(r);
	}
	
	private void printInOrder(Node<K,E> n) {
		if (n != null) {
			printInOrder(n.getLeft());
			System.out.println(n.getKey() + " --> " + n.getElement());
			printInOrder(n.getRight());
		}
	}
	
	public void printInOrder(){
		printInOrder(root);
	}
	
	
	public String spaces(int d){
		String s = "";
		for(int i=0; i<d;i++)
			s+=" ";
		return s;
	}
	
	public void printTree(){
		printTree(root,0);
        System.out.println("\n");
	}
	
	public void printTree(Node<K,E> n, int l){
	    if(n==null)
	         return;
	    printTree(n.getRight(), l+1);
	    if(l>0){

            System.out.print(spaces(24));
	        for(int i=0;i<l-1;i++)
	            System.out.print("|"+spaces(32));
	        System.out.println("|-----"+n.getElement());
	    }
	    else
	        System.out.println(n.getElement());
	    printTree(n.getLeft(), l+1);
	}    

}
