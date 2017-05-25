import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Deck {
	LinkedList deck = null;
	public Deck(){
		deck = new LinkedList();
	}
	
	public void printDeck(){
		if(isEmpty())
			System.out.println("Error: The deck has not been initialized!");
		else
			deck.print();
		System.out.println();
	}
	
	public void createDeck(String input) throws IOException{
		deck = null;
		deck = new LinkedList();
		BufferedReader rd = new BufferedReader(new FileReader(input));
		String ln;
		String[] linesp;
		String s;
		int r;

		while((ln = rd.readLine())!=null && !ln.equals("")){
			linesp = ln.split(" ");
			s = linesp[0];
			r = Integer.parseInt(linesp[1]);
			insert(s,r);
		}
		rd.close();	
		rd = null;
		
		System.out.println("The file has been read without any error \n"
				+ "and the deck has been successfully created.");
	}
	
	
	//QUICK SORT
	
	public void quickSort(){
		System.out.println("\"Be the change that \nyou want to see in the world.\" (Mahatma Gandi)"
				+ "\n\nInitial Unsorted Deck:\n");
		deck.print();
		System.out.println("\n\nQuickSort Started: \n");
		quickSort(deck,0,deck.size()-1);
		System.out.println("QuickSort is finished");}
	
	public void quickSort(LinkedList l, int a, int b){
		
		if(a>=b) return;
		
		int left = a;
		int right = b-1;
		
		Card lc,rc;
		
		Card pivot = l.get(b);

		System.out.println("Pivot "+pivot+":");
		
		int rankTemp, suitTemp;
		
		while(left<=right){
			
			while(left<=right && l.get(left).compare(pivot)<0)left++;
			
			while(left<=right && l.get(right).compare(pivot)>0)right--;
			
			if(left<=right){
				lc = l.get(left); rc = l.get(right);
				rankTemp = lc.getRank(); suitTemp = lc.getSuit();
				
				lc.setSuit(rc.getSuit());
				lc.setRank(rc.getRank());
				rc.setSuit(suitTemp);
				rc.setRank(rankTemp);
			}
		}

		lc = l.get(left);
		rankTemp = lc.getRank(); suitTemp = lc.getSuit();
		lc.setSuit(pivot.getSuit());
		lc.setRank(pivot.getRank());
		pivot.setRank(rankTemp);
		pivot.setSuit(suitTemp);

		l.print();
		System.out.println("\n");
		quickSort(l,a,left-1);
		quickSort(l,left+1,b);
		
		
		/*
		LinkedList L = new LinkedList();
		LinkedList E = new LinkedList();
		LinkedList G = new LinkedList();
		
		while(!l.isEmpty()){
			dummy = l.removeFirst();
			
			comp = dummy.compare(pivot);
			
			if(comp<0)
				L.addLast(dummy.getSuitLetter(), dummy.getRank());
			else if(comp>0)
				G.addLast(dummy.getSuitLetter(), dummy.getRank());
			else
				E.addLast(dummy.getSuitLetter(), dummy.getRank());
		}

		System.out.println("Pivot "+pivot+": ");
		L.print(); G.print();
		System.out.println();
		quickSort(L);
		quickSort(G);

		while(!L.isEmpty()){
			Card a = L.removeFirst();
			l.addLast(a.getSuitLetter(), a.getRank());
		}
		while(!E.isEmpty()){
			Card a = E.removeFirst();
			l.addLast(a.getSuitLetter(), a.getRank());
		}
		while(!G.isEmpty()){
			Card a = G.removeFirst();
			l.addLast(a.getSuitLetter(), a.getRank());
		}
		*/
	}
	
	/*
	//MERGE SORT 2

	public void mergeSort(){
		mergeSort(deck);
	}
	
	public void mergeSort(LinkedList l){
		LinkedList temp = new LinkedList();
		
		for(int i=0; i<l.size();i++)
			temp.addLast("B", -1);
		
		mergeSort(l,temp,0,l.size()-1);
	}
	
	
	public void mergeSort(LinkedList l, LinkedList temp, int left, int right){
		if(left<right){
			int c = (left+right)/2;
			
			mergeSort(l,temp,left,c);
			mergeSort(l,temp,c+1,right);
			
			merge(l,temp,left,c+1,right);
			
		}
	}
	
	
	public void merge(LinkedList l, LinkedList temp, int left, int right, int rightEnd){
		int leftEnd = right-1;
		int k = left;
		int num = rightEnd-left+1;

		while(left <= leftEnd && right <= rightEnd)
            if(l.get(left).compare(l.get(right)) <= 0)
                temp.set(k++, l.get(left++));
            else
            	temp.set(k++, l.get(right++));
		
		while(left <= leftEnd)
			temp.set(k++, l.get(left++));
		
		while(right <= rightEnd)
			temp.set(k++, l.get(right++));
		
		for(int i = 0; i < num; i++, rightEnd--)
            l.set(rightEnd, temp.get(rightEnd));
	}
	
	
	*/
	
	//MERGE SORT
	public void mergeSort(){
		System.out.println("\"There two types of people in the world:\n"
				+ "the ones who can print merge-sort like D.I.\n"
				+ "and the ones who cannot.\" (Bugra Can Sefercik)\n\n"
				+ "Initial Unsorted Deck:\n");
		printDeck();
		System.out.println("\nMerge Sort Started:\n");
		mergeSort(deck);
		System.out.println("Merge Sort is finished.");
	}
	
	public void mergeSort(LinkedList l){
		int initSize = l.size();

		if(initSize<=1) return;
		
		int middle = (int)Math.ceil(l.size()/2);

		LinkedList leftList = new LinkedList();
		LinkedList rightList = new LinkedList();
		
		Card currentCard = l.removeFirst();
		
		//The list is divided into two lists.
		for(int i = 0; i<middle; i++){
			leftList.addLast(currentCard.getSuitLetter(), currentCard.getRank());
			currentCard = l.removeFirst();}
		
		for(int i = middle; i<initSize; i++){
			rightList.addLast(currentCard.getSuitLetter(), currentCard.getRank());
			currentCard = l.removeFirst();}
		
		
		mergeSort(leftList);
		mergeSort(rightList);
		merge(leftList,rightList,l);

		
	}
	
	private void merge(LinkedList ll, LinkedList rl, LinkedList l){
		
		Card currentLeftCard = ll.getFirst();
		Card currentRightCard = rl.getFirst();
		System.out.print("Left Part  : ");ll.print();
		System.out.println();
		System.out.print("Right Part : ");rl.print();
		System.out.println();
		
		int comp=0;
		while(currentLeftCard!=null || currentRightCard!=null){
			//If one of the sides are finished already.
			if(currentLeftCard==null){
				l.addLast(currentRightCard.getSuitLetter(), currentRightCard.getRank());
				rl.removeFirst();
			}else if(currentRightCard==null){
				l.addLast(currentLeftCard.getSuitLetter(), currentLeftCard.getRank());
				ll.removeFirst();
			}
			else{
				comp = currentLeftCard.compare(currentRightCard);
				if(comp<0){
					l.addLast(currentLeftCard.getSuitLetter(), currentLeftCard.getRank());
					ll.removeFirst();
				}else if(comp>0){
					l.addLast(currentRightCard.getSuitLetter(), currentRightCard.getRank());
					rl.removeFirst();
				}
			}
			currentLeftCard = ll.getFirst();
			currentRightCard = rl.getFirst();
		}

		System.out.print("Merged Left and Right: ");l.print();
		System.out.println("\n");
	}
	
	
	
	public Card insert(String suit, int rank){return deck.addLast(suit, rank);}

	public Card getFirst(){return deck.getFirst();}
	public Card getLast(){return deck.getLast();}
	public Card getNext(Card c){return c.getRight();}
	
	public boolean isEmpty(){return deck.isEmpty();}
	public int size(){return deck.size();}
	
}
