public class Card {
	private int suit,rank,index;
	private Card right, left;
	public Card(String suit, int rank, int index){
		this.rank = rank;
		this.setIndex(index);
		setSuitLetter(suit);
	}

	public Card getRight() {return right;}
	public void setRight(Card right) {this.right = right;}
	public Card getLeft() {return left;}
	public void setLeft(Card left) {this.left = left;}	
	public void setSuitLetter(String suit){

		if(suit.equals("S"))
			this.suit = 0;
		else if(suit.equals("H"))
			this.suit = 1;
		else if(suit.equals("C"))
			this.suit = 2;
		else if(suit.equals("D"))
			this.suit = 3;
		else
			this.suit = -1;	
	}
	
	protected void setSuit(int i){suit = i;}
	
	public int getSuit(){return suit;}
	public String getSuitLetter(){
		if(suit==0)
			return "S";
		else if(suit==1)
			return "H";
		else if(suit==2)
			return "C";
		else if(suit==3)
			return "D";
		return null;
	}
	public int getRank(){return rank;}
	public void setRank(int r){rank = r;}
	
	public int compare(Card c){
		//Check for suits first
		if(getSuit()<c.getSuit())
			return -1;
		else if(c.getSuit()<getSuit())
			return 1;
		//If suits are same then check ranks.
		else if(getRank()<c.getRank())
			return -1;
		else if(c.getRank()<getRank())
			return 1;
		else
			return 0;
		
	}
	
	
	@Override
	public String toString(){
		return getSuitLetter()+"|"+getRank();
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
