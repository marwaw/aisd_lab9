import java.util.ArrayList;

public class Node {
	String value;
	Node left;
	Node right;
	Node parent;
	ArrayList<Integer> indeksy = new ArrayList<>();
	
	public Node(String value){
		this.value = value;
	}
	
	public Node(String value, Node left){
		this.value = value;
		this.left = left;
	}
	
	public Node(String value, Node right, Node left){
		this.value = value;
		this.left = left;
		this.right = right;
	}
	
	public void addToArray(int i){
		indeksy.add(i);
	}
	
	public void wyswietl(){
		for(int i=0; i < indeksy.size(); i++){
			System.out.printf("%3d", indeksy.get(i));
		}
	}

}
