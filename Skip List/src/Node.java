//The Node class of a double linked list with standard methods is defined. 
//Its entries are Entry objects.
public class Node{
	private int key;
	private Node right;
	private Node left;
	private Node bottom;
	private int level=0;
	public Node(int key, Node r, Node l, Node b,int p){
		setKey(key);
		setRight(r);
		setBottom(b);
		setLeft(l);
		setLevel(p);
	}
	public Node(int key){
		this(key,null,null,null,0);
	}
	public Node getRight() {return right;}
	public void setRight(Node right) {this.right = right;}
	public Node getLeft() {return left;}
	public void setLeft(Node left) {this.left = left;}
	public Node getBottom() {return bottom;}
	public void setBottom(Node bottom) {this.bottom = bottom;}
	public int getLevel() {return level;}
	public void setLevel(int position) {this.level = position;}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}	
}