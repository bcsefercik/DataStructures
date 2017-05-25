package project;

import java.util.*;

public class Digraph {
	// TODO : complete the graph data structure
	private int V;
	private int E;
	private LinkedList<Integer> adj[];
	

	public int[] places;
	private int[] topoSorted;
	private ArrayList<Integer> backed;
	private ArrayList<Integer> visited;
	private ArrayList<Integer> left;
	
	private boolean acycic = true;
	
	//Remove cycle
	private MyStack<Integer> cycleEdges;
	private boolean[] cycleStacked;
	private boolean[] cycleExplored;
	private int[] cycleForest;
	private Edge res,dum,gum;
	public ArrayList<Edge> cycList;
	
	
	//within distance
	public LinkedList<Integer>[] distances;
	private int[][] edgeTypes;
	private boolean[] explored;
	
	private LinkedList<Integer> postorder; 
	

	
	
	// create an empty graph with V vertices
	@SuppressWarnings("unchecked")
	public Digraph(int V) {
		topoSorted = new int[V];
		places = new int[V];
		backed = new ArrayList<Integer>();
		visited = new ArrayList<Integer>();
		left = new ArrayList<Integer>();

	    postorder = new LinkedList<Integer>();
		
		distances = (LinkedList<Integer> [])(new LinkedList[V+1]);
		edgeTypes = new int[V][V];
		// 0 unexplored
		// 1 discovery
		// 2 cross
		explored = new boolean[V];
		
		cycleExplored = new boolean[V];
		cycleForest = new int[V];
		cycleStacked = new boolean[V];
			// 0 unexplored
			// 1 explored
			// 2 back

		cycList = new ArrayList<Edge>();
		
		for(int i=0;i<V;i++){
			explored[i]= false;
			cycleExplored[i] = false;
			cycleStacked[i] = false;
			
			
			distances[i] = new LinkedList<Integer>();}
		
		this.V = V;
		this.E = 0;
		adj = (LinkedList<Integer> [])(new LinkedList[V]);
		for (int i = 0; i < V; i++) {
			adj[i] = new LinkedList<Integer>();
		}
	}
	
	// return the number of vertices
	public int getNumVertices() {
		return V;
	}
	
	// return the number of edges
	public int getNumEdges() {
		return E;
	}

	// add a new edge between vertices v and w
	public void addEdge(int v, int w) {
		if (adj[v].contains(w)) return;
		adj[v].add(w);
		E++;
	}
	// add a new edge between vertices v and w
	public void removeEdge(int v, int w) {
		if (!adj[v].contains(w)) return;
		adj[v].remove(new Integer(w));
		E--;
	}
	
	// return the list of vertices which are adjacent to vertex v
	public Iterable<Integer> getNeighbors(int v) {
		return adj[v];
	}
	
	// remove a single edge that is part of a directed cycle, and return the edge that is removed
	public Edge removeEdgeInCycle() {
		// TODO : complete this method
		cycleEdges = null;
		for(int i=0;i<V;i++){
			cycleExplored[i] = false;
			cycleStacked[i] = false;}
		for(int i=0;i<V;i++)
			if(!cycleExplored[i])
				cycleDFS(i);
		
		if((cycleEdges==null))return null;
		
		int a = cycleEdges.pop();
		int b = cycleEdges.pop();
		int c;
		res = new Edge(a,b);
		ArrayList<Edge> ee = new ArrayList<Edge>();
		ee.add(res);
		while(!cycleEdges.isEmpty()){
			c = cycleEdges.pop();
			res = new Edge(b,c);
			ee.add(res);
			a = b;
			b = c;
		}
		
		ee.sort(Edge.DEF);
		res = ee.get(0);
		removeEdge(res.v,res.w);
		return res;
	}
	


	public MyStack<Integer> cycleDFS(int v){
		cycleExplored[v] = true;
		cycleStacked[v] = true;
		
		for(int w: getNeighbors(v)){
			if(!cycleExplored[w]){
				cycleForest[w] = v;
				cycleDFS(w);
			}else if(cycleStacked[w]){
				cycleEdges = new MyStack<Integer>();
				for(int o=v; o!=w; o = cycleForest[o]){
					cycleEdges.push(o);
				}
				cycleEdges.push(w);
				cycleEdges.push(v);
			}
		}
		
		cycleStacked[v] = false;
		
		return cycleEdges;
	}
	
	
	
	// remove one edge per cycle until there are no more cycles, and return the list of edges in the order in which they are removed
	public List<Edge> removeAllCycles() {
		// TODO : complete this method
		LinkedList<Edge> l = new LinkedList<Edge>();
		
		while(true){
			dum = removeEdgeInCycle();
			
			if(dum==null) break;
			
			gum = new Edge(dum.v,dum.w);
			l.addLast(gum);}
		return l;
	}

	private void postDFS(int v){
		visited.add(v);
		left.remove(new Integer(v));
		backed.add(v);
        for (int w : this.getNeighbors(v)) {
            if (!visited.contains(w)) {
                postDFS(w);
            }else if(backed.contains(w)){
				acycic = false;

			}
        }
        postorder.addLast(v);
		backed.remove(new Integer(v) );
	        

	        
	}
	
	public List<Integer> topologicalSort() {
		postorder.clear();
		visited.clear();
		backed.clear();
		left.clear();

		for (int v = 0; v < V; v++)
			left.add(v);
		
		
		for (int v = 0; v < V; v++)
            if (!visited.contains(v)) postDFS(v);
		
        if(!acycic) return null;
        
        
        MyStack<Integer> rs = new MyStack<Integer>();
        
        
        while(!postorder.isEmpty())
            rs.push(postorder.removeFirst());

        
        places = new int[V];
        topoSorted = new int[V];
        int r = 0;

        while(!rs.isEmpty())
        	places[rs.pop()] = r++;
        
        
		LinkedList<Integer> l = new LinkedList<Integer>();

		for(int k = 0; k<V; k++){
			topoSorted[places[k]] = k;}
		
		for(int k = 0; k<V; k++)
			l.addLast(topoSorted[k]);
			
		return l;
	}
	
	
	public boolean isTopological(List<Integer> sort) {
		// TODO : complete this method
		boolean bb = true;
		if(E==0)return true;
		
		for (int v = 0; v < V; v++){
			bb = true;
			postorder.clear();
			backed.clear();
			visited.clear();
			acycic = true;
			left.clear();

			for (int i = 0; i < V; i++)
				left.add(i);
			
            postDFS(v);
		
            while(!left.isEmpty())
            	postDFS(left.get(left.size()-1));
            
            if(!acycic) return false;
            
            
            MyStack<Integer> rs = new MyStack<Integer>();
            
            
            while(!postorder.isEmpty())
                rs.push(postorder.removeFirst());

            
            places = new int[V];
            topoSorted = new int[V];
            int r = 0;

            while(!rs.isEmpty())
            	places[rs.pop()] = r++;
            
            
    		LinkedList<Integer> l = new LinkedList<Integer>();

    		for(int k = 0; k<V; k++){
    			topoSorted[places[k]] = k;}

    		for(int k = 0; k<V; k++){
    			l.addLast(topoSorted[k]);
    			if(topoSorted[k]!=sort.get(k))
    				bb = false;}
    		
    
            if(bb)break;
		}

		
		return bb;
	}
		

	public void BFS(int s){
		distances[0].addLast(s);
		
		int i = 0;
		explored[s] = true;
		
		while (i<V &&!distances[i].isEmpty()){
			for (int v : distances[i])
				for(int e : this.getNeighbors(v)){
					if(edgeTypes[v][e]==0){
						if(!explored[e]){
							edgeTypes[v][e]=1;
							explored[e] = true;
							distances[i+1].addLast(e);
						}else{
							edgeTypes[v][e]=2;
						}
					}
				}
			i++;
		}
	}
	
	// generalized form of the verticesWithinDistance2
	// return vertices within distance d from vertex v
	@SuppressWarnings("unchecked")
	public List<Integer> verticesWithinDistance(int v, int d) {
		// TODO : complete this method
		
		distances = (LinkedList<Integer> [])(new LinkedList[V]);
		edgeTypes = new int[V][V];
		// 0 unexplored
		// 1 discovery
		// 2 cross
		
		explored = new boolean[V];
		
		for(int i=0;i<V;i++)
			explored[i]= false;
		
		for (int i = 0; i < V; i++) {
			distances[i] = new LinkedList<Integer>();}
		
		BFS(v);
		
		LinkedList<Integer> l = new LinkedList<Integer>();
		
		for(int j=0; j<=d; j++)
			for(int a : distances[j])
				l.addLast(a);

		
		return l;
	}


}