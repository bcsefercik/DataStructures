
import java.util.Iterator;


public class BCSHashTable {
	private static final double LAMBDA = 0.75;
	private int table_size;
	private int size = 0;
	private BCSList[] table;
	
	public BCSHashTable(int s){
		table_size = s;
		table = new BCSList[table_size];
	}
	//If hash table size is not specified.
	public BCSHashTable(){this(53);}
	
	
	//It takes key and value and puts the entry into right place.
	public String put(String key, String value){
		int hash = hash(key);
		BCSList l = table[hash];
		HashEntry he = new HashEntry(key,value);
		
		if(l==null)
			l = new BCSList();
		
		if(l.contains(key))
			return l.get(key).getValue();
			
		add(hash,he);
		size++;
		
		if(loadFactor()>LAMBDA)
			rehash(2*table_size-1);
		
		return null;
	}	
	
	
	//Removes entry belonging to given key.
	public void remove(String key) throws IllegalArgumentException{

		if(!containsKey(key))
			throw new IllegalArgumentException("The key is not in the table.");
		
		int hash = hash(key);
		BCSList l = table[hash];
		

		l.remove(key);
		
		if(table[hash].isEmpty())
			table[hash] = null;
		
		size--;
	}	
	
	//I needed this method to make things easier.
	private void add(int index, HashEntry he){
		if(table[index]==null)
			table[index] = new BCSList();	
		if(!table[index].contains(he.getKey()))
			table[index].addLast(he);
		
		he.setPosition(index);
		
		
	}
	
	//It rehashes the table to the given sized table.
	private void rehash(int new_size){
		int h = 0;
		BCSList[] oldTable = new BCSList[table_size];
		oldTable = table.clone();
		table = null;
		table = new BCSList[new_size];
		table_size = new_size;
		
		for(BCSList l : oldTable){
			if(l!=null){
				for(HashEntry he : l.toArray()){
					h = hash(he.getKey());
					add(h,he);
				}
			}

			l=null;
		}
		
		oldTable=null;
	}
	
	//Returns iterable for entries.
	public Iterable<HashEntry> entrySet(){

		return new EntryIterable();
	}
	
	//Checks is there an entry with a given key.
	public boolean containsKey(String key){
		if(table[hash(key)]==null)
			return false;
		return table[hash(key)].contains(key);
	}
	
	
	//Returns number of elements in the table.
	public int size(){return size;}
	
	//Gives the load factor.
	public double loadFactor(){return (double)size/(double)table_size;}
	
	//It is given method in the instructions but does the job with a given size.
	private int hash(String key, int table_size){
		int hash = 0;
		int p=23; 
		
		for (int i = 0; i < key.length(); i++) 
			hash = (p*hash+key.charAt(i))%table_size;
		
		return hash;
	}
	
	private int hash(String key){
		return hash(key,table_size);
	}
	
	//Empties the table.
	public void empty(){
		table = null;
		table = new BCSList[table_size];
	}
	
	public boolean isEmpty(){
		return size()==0;
	}
	
	//Returns iterable for keys.
	public Iterable<String> keySet(){
		return new KeyIterable();
	}
	
	
	//It is a standard iterator class. It contains standard procedures of an iterator.
	protected class KeyIterator implements Iterator<String>{

		//last returned ones
		private int currentIndex = 0;
		private HashEntry[] currentList;
		private int arrayIndex = 0;
		private int arraySize = 0;
		
		public KeyIterator(){
			if(!isEmpty() && currentList == null)
				while(table[currentIndex]==null)
					currentIndex++;
			
			currentList = table[currentIndex].toArray();
			arraySize = table[currentIndex].size();
			arrayIndex = -1;
		}
		
		public String next(){
			if(!hasNext())
				return null;
			
			if(arrayIndex<(arraySize-1)){
				arrayIndex++;
				return currentList[arrayIndex].getKey();
			}
			
			int i = currentIndex+1;
			while (i<table_size){
				if(table[i]!=null){
					currentIndex = i;
					currentList = table[currentIndex].toArray();
					arrayIndex = 0;
					arraySize = table[currentIndex].size();
					return currentList[arrayIndex].getKey();
					
				}		

				i++;
			}
			
			return null;
			
		}

		
		public boolean hasNext(){
			if(isEmpty())
				return false;
			
			if(arrayIndex<(arraySize-1))
				return true;
			
			int i = currentIndex;

			while (i<(table_size-1)){
				i++;
				if(table[i]!=null)
					return true;
				
			}

			return false;
		}
		
	}
	
	private class KeyIterable implements Iterable<String>{

		@Override
		public Iterator<String> iterator() {
			// TODO Auto-generated method stub
			return new KeyIterator();
		}
		
	}
	//It is a standard iterator class. It contains standard procedures of an iterator.
	protected class EntryIterator implements Iterator<HashEntry>{

		//last returned ones
		private int currentIndex = 0;
		private HashEntry[] currentList;
		private int arrayIndex = 0;
		private int arraySize = 0;
		
		public EntryIterator(){
			if(!isEmpty() && currentList == null)
				while(table[currentIndex]==null && currentIndex<(table_size-1))
					currentIndex++;
			
			currentList = table[currentIndex].toArray();
			arraySize = table[currentIndex].size();
			arrayIndex = -1;
		}
		
		public HashEntry next(){
			if(!hasNext())
				return null;
			
			if(arrayIndex<(arraySize-1)){
				arrayIndex++;
				return currentList[arrayIndex];
			}
			
			int i = currentIndex+1;
			while (i<table_size){
				if(table[i]!=null){
					currentIndex = i;
					currentList = table[currentIndex].toArray();
					arrayIndex = 0;
					arraySize = table[currentIndex].size();
					return currentList[arrayIndex];
					
				}		

				i++;
			}
			
			return null;
			
		}

		
		public boolean hasNext(){
			if(isEmpty())
				return false;
			
			if(arrayIndex<(arraySize-1))
				return true;
			
			int i = currentIndex;

			while (i<(table_size-1)){
				i++;
				if(table[i]!=null)
					return true;
				
			}

			return false;
		}
		
	}
	
	private class EntryIterable implements Iterable<HashEntry>{

		@Override
		public Iterator<HashEntry> iterator() {
			// TODO Auto-generated method stub
			return new EntryIterator();
		}
		
	}
}
