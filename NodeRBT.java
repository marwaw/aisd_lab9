import java.util.ArrayList;

public class NodeRBT {
	String value;
	NodeRBT left;
	NodeRBT right;
	NodeRBT parent;
	char color; //r = red; b = black
	ArrayList<Integer> indeksy = new ArrayList<>();
	
	public NodeRBT(String value){
		this.value = value;
//		this.color = 'r';
	}
	
	public NodeRBT(String value, NodeRBT left){
		this.value = value;
		this.left = left;
//		this.color = 'r';
	}
	
	public NodeRBT(String value, NodeRBT right, NodeRBT left){
		this.value = value;
		this.left = left;
		this.right = right;
//		this.color = 'r';
	}
	
	public void setParent(NodeRBT t){
		this.parent = t;
	}
	
	public void setRed(){
		this.color = 'r';
	}
	
	public void setBlack(){
		this.color = 'b';
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
