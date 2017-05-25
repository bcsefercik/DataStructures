public class SkipList {
	//Global inf and -inf values.
	static int inf = (int) Double.POSITIVE_INFINITY;
	static int minf = -inf;
	
	private int height = 0;
	private int max_size = 0;
	private int size = 0;
	private BCSLinkedList[] skip;
	private Node[] visited;
	
	
	public SkipList(int capacity){
		max_size = capacity;
		height = log2ceil(max_size);
		skip = new BCSLinkedList[height+1];
		visited = new Node[height+1];
		
		for(int i = 0; i<=height; i++){
			skip[i] = new BCSLinkedList();
			skip[i].addLast(minf);
			skip[i].addLast(inf);
			
			if(i!=0){
				skip[i].getFirst().setBottom(skip[i-1].getFirst());
				skip[i].getLast().setBottom(skip[i-1].getLast());
			}	
		}
	}
	
	public Node getFrom(int height,int position){
		Node d = skip[height].getFirst();
		if(position>0)
			for (int i=1; i<=position;i++)
				d = d.getRight();
		return d;
	}
	public Node insert(int key){
		Node n = search(key);
		int h = findLevel(key);
		
		for(int i=0; i<=h; i++){
			n = skip[i].addAfter(visited[i], key);
			
			if(i>0){
				n.setBottom(visited[i-1]);
			}
		}
		
		size++;
		
		return visited[0];
	}
	
	public void insert(int key,int height){
		Node dum = skip[0].addBefore(skip[0].getLast(),key);
		Node n = dum;
		for(int h=1; h<=height; h++){

			n = skip[h].addBefore(skip[h].getLast(),key);
			n.setBottom(dum);
			dum = n;
			//F.p(sl.skip[h].getLast().getLeft().getBottom().getKey()+" "+ sl.skip[h].getLast().getKey());
		
		}
		size++;
	}
	
	public int findLevel(int key){return ((key%height)+height)%height;}
	
	public Node search(int key){
		Node sn = skip[height].getFirst();
		int i = height;
		visited[i] = sn;				
		
		while(i>=0 && sn.getBottom()!=null){
			sn = sn.getBottom();
			
			while(sn.getRight()!=null && key>=sn.getRight().getKey())
				sn = sn.getRight();

			i--;
			visited[i] = sn;
		}
		return sn;
	}

	public void empty(){
		for(int i=0; i<=height; i++){
			skip[i].empty();
			
			if(i>0){
				skip[i].getFirst().setBottom(skip[i-1].getFirst());
				skip[i].getLast().setBottom(skip[i-1].getLast());}
		}
		size = 0;
	}
	

	
	public int height(){return height;}
	public int size(){return size;}
	protected void incSize(){size++;}
	
	//Implemented this method to calculate ceiling of logarithm of a given integer at base 2.
	//Since base 2 isn't defined in Java's Math class, 
	//I utilized simple mathematical trick. 
	public static int log2ceil(int a){
		return (int) Math.ceil(Math.log(a)/Math.log(2));
	}

	public Node[] getVisited(){return visited;}
	
	
	//A linked list class which is very similar to one that I have implemented in the previous project.
	//It used for the levels of the skip list.
	public class BCSLinkedList {
		
		//I decided to distinguish last and first entries of the list with the variables below. 
		private int inf = SkipList.inf;
		private Node head = null;
		private Node tail = null;
		private int size = 0;
		
		public BCSLinkedList(){}
		
		
		//With this method the given Entry object added to the end of the list.
		//For this project's purpose this approach is better in terms of time complexity.
		public Node addLast(int key){
			Node n = new Node(key);
			if(isEmpty()){
				head = n;
				tail = head;
			}else{
				Node a = tail;
				
				a.setRight(n);
				tail = n;
				tail.setLeft(a);
				
			}
			
			size++;
			
			return n;
		}
		
		public Node addAfter(Node prev, int key){
			if(prev==null || getNode(prev.getKey())==null)
				return null;
			
			Node n = new Node(key);
			n.setRight(prev.getRight());
			n.getRight().setLeft(n);
			n.setLeft(prev);
			prev.setRight(n);
			size++;
			return n;
		}

		
		public Node addBefore(Node bef, int key){
			if(bef==null || getNode(bef.getKey())==null)
				return null;
			
			Node n = new Node(key);
			
			n.setLeft(bef.getLeft());
			n.getLeft().setRight(n);
			n.setRight(bef);
			bef.setLeft(n);
			
			size++;
			return n;
		}
		
		
		public boolean isEmpty(){
			return size()==0;
		}
		
		
		//Checks whether the entry with a given key is in the list.
		public boolean contains(int key){
			return get(key)!=inf ? true : false;
		}
		
		//This method returns a Entry belonging to the given key.
		private Node getNode(int key){
			if(isEmpty())
				return null;
			
			Node prev = head;
			Node n = prev.getRight();
			
			if(prev.getKey()==key)
				return prev;
			while(n!=null){
				if(n.getKey()==key)
					return n;
				prev = n;
				n = n.getRight();
			}
			
			return null;
		}
		
		public void empty(){
			tail = null;
			head = null;
			size = 0;
			addLast(minf);
			addLast(inf);
		}
		
		
		//This method returns a Entry belonging to the given key.
		public int get(int key){
			if(isEmpty())
				return SkipList.inf;
			
			Node prev = head;
			Node n = prev.getRight();
			
			if(prev.getKey()==key)
				return prev.getKey();
			while(n!=null){
				if(n.getKey()==key)
					return n.getKey();
				prev = n;
				n = n.getRight();
			}
			
			return SkipList.inf;
		}
		
		//This method removes a Entry from the list belonging to a given key.
		public void remove(int key) throws IllegalArgumentException{
			if(isEmpty())
				throw new IllegalArgumentException("The key is illegal.");
			
			Node prev = head;
			Node n = prev.getRight();
			if(prev.getKey()==key){
				size--;

				head = n;
				n = null;
				return;}
			
			while(n!=null){
				if(n.getKey() == key){
					prev.setRight(n.getRight());
					n.setRight(null);
					size--;
					return;}
				n = n.getRight();
			}
		}
		
		public Node getFirst(){return head;}
		public Node getLast(){return tail;}
		public int size(){return size;}
	}
}
