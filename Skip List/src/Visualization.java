
public class Visualization {
	private SkipList sl;
	private int inf = SkipList.inf;
	private int minf = -inf;
	public Visualization(SkipList sl){
		this.sl = sl;
	}
	
	//This method prints the skip list list.
	public void visiualizeSkipList(){
		int number=0;
		Node dummy = sl.getFrom(0,0);
		
		Node next;
		for(int i=sl.height(); i>=0; i--){
			
			next = sl.getFrom(i,0);
			
			for(int j=0; j<(sl.size()+2); j++){

				number = next.getKey();
				//F.p(number);
				if(number==dummy.getKey()){

					if(number==minf)
						System.out.print("-inf");
					else if(number==inf)
						System.out.print("---inf");
					else
					System.out.print(repeatStr("-",2)+number);
				}
				else
					System.out.print(repeatStr("-",4));
				
				if(dummy.getKey()==number)
					next = next.getRight();
				
				dummy = dummy.getRight();
			}
			dummy = sl.getFrom(0,0);
			System.out.println();
		}
	}
	
	
	//This method prints the last search.
	public void visiualizeLastSearch(){
		int number=0;
		Node dummy;
		Node next;
		for(int i=sl.height(); i>=0; i--){
			dummy = sl.getFrom(0,0);
			next = sl.getFrom(i,0);
			String seperator="-";
			for(int j=0; j<(sl.size()+2); j++){

				number = next.getKey();
				
				if(i<sl.height() && sl.getVisited()[i+1].getKey()!=sl.getVisited()[i].getKey() && number>sl.getVisited()[i+1].getKey() && number<=sl.getVisited()[i].getKey())
					seperator = "*";
				else
					seperator = "-";

				if(number==dummy.getKey()){

					if(number==minf)
						System.out.print("-inf");
					else if(number==inf)
						System.out.print(repeatStr(seperator,3)+"inf");
					else
					System.out.print(repeatStr(seperator,2)+number);
				}
				else
					System.out.print(repeatStr(seperator,4));
				
				if(dummy.getKey()==number)
					next = next.getRight();
				
				dummy = dummy.getRight();
			}
			System.out.println();
		}
	}
	
	public String repeatStr(String s,int num){
		String a = s;
		for(int i=1;i<num;i++)
			a += s;
		return a;
	}
}
