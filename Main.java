import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
//		Tree t = new Tree("myText1");
//		t.inOrder(t.root);
//		System.out.println(t.wszerz(t.root));
//		System.out.println(t.findMin(t.search("parasol", t.root).right).value);
//		System.out.println(t.remove("noœ"));
//		System.out.println(t.wszerz(t.root));
//		t.inOrder(t.root);
		
//		System.out.println(t.wszerz(t.root));
//		
		RBTree t1 = new RBTree("myText.txt");
//		System.out.println(t1.root.value);
//		t1.inOrder(t1.root);
//		System.out.println("wszerz");
		t1.wszerz(t1.root);
		t1.remove("deszcz");
		t1.wszerz(t1.root);
//		System.out.println(t1.correctColors(t1.root));


	}

}
