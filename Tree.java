import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Tree {
	Node root;
	
	public Tree(String fileName) throws FileNotFoundException{
		read(fileName);
	}
	
	public void insert(String s, int i){
		root = insert(s, root, i);
	}
	
	protected Node insert(String str, Node t, int i){
		String s = str.toLowerCase();
		if( t == null){
			t = new Node(s);
			t.addToArray(i);
		}
		else{
			int com = s.compareToIgnoreCase(t.value);
			if ( com < 0){
				t.left = insert(s, t.left, i);
			}
			else if ( com > 0 ){
				t.right = insert(s, t.right, i);
			}
			else{
				t.addToArray(i);
			}
		}
		return t;
	}
	
	public Node search(String s, Node t){
		if (t == null || t.value.compareTo(s) == 0){
			return t;
		}
		if (s.compareTo(t.value) < 0){
			return search(s, t.left);
		}
		return search(s, t.right);
	}
	
	public void insert2(String str, int i){
		String s = str.toLowerCase();
		if (search(s, root) != null) {
			search(s, root).addToArray(i);
		}
		else{
			Node node = new Node(s);
			Node par = null;
			Node x = root;
			while( x != null){
				par = x;
				if (s.compareTo(x.value) < 0){
					x = x.left;
				}
				else {
					x = x.right;
				}
			}
			node.parent = par;
			
			if (par == null){
				root = node;
			}
			else{
				if (s.compareTo(par.value) < 0){
					par.left = node;
				}
				else{
					par.right = node;
				}
			}
			node.addToArray(i);
		}
	}
	
	public void inOrder(Node node){
		if(node != null){
			inOrder(node.left);
			System.out.printf("%-14s", node.value);
			node.wyswietl();
			System.out.println("");
			inOrder(node.right);
		}
	}
	
	public String wszerz(Node root){
		StringBuffer wszerz = new StringBuffer();
		Queue<Node> kolejka = new LinkedList<Node>();
		kolejka.add(root);
		while(!kolejka.isEmpty()){
			Node temp = kolejka.poll();
			if(temp.left != null){
				kolejka.add(temp.left);
			}
			if(temp.right != null){
				kolejka.add(temp.right);
			}
			wszerz.append(temp.value);
			wszerz.append(" ");
			temp = kolejka.peek();
		}
		return wszerz.toString();
	}
	
	public void read(String fileName) throws FileNotFoundException{
		Scanner fileScanner = new Scanner(new FileReader(fileName));
		int i = 1;
		
		while(fileScanner.hasNextLine()){
			String line = fileScanner.nextLine();
			Scanner lineScanner = new Scanner(line);
			
			while (lineScanner.hasNext()){
				String s = lineScanner.next();
				if (withMark(s) && s.substring(0, s.length()-1).isEmpty()){}
				else if (withMark(s)){
					insert2(s.substring(0, s.length()-1), i);
				}
				else{
					insert2(s, i);
				}
				
			}
			lineScanner.close();
			i++;
		}
		fileScanner.close();
	}
	
	public boolean remove(String s){
		return remove(search(s, root));
	}
	
	public boolean remove(Node toRemove){
		if (toRemove == null) return false;
		if(toRemove.left == null && toRemove.right == null){
			System.out.println("Nie mam dzieci");
			if (leftChild(toRemove)) toRemove.parent.left = null;
			else toRemove.parent.right = null;
		}
		else if (toRemove.left == null || toRemove.right == null){
			System.out.println("Mam jedno dziecko");
			if(toRemove.left != null && toRemove.right == null){
				if(leftChild(toRemove)) {
					toRemove.left.parent = toRemove.parent; 
					toRemove.parent.left = toRemove.left;
				}
				else{
					toRemove.left.parent = toRemove.parent;
					toRemove.parent.right = toRemove.left;
				}
			}
			else{
				if(leftChild(toRemove)) {
					toRemove.right.parent = toRemove.parent; 
					toRemove.parent.left = toRemove.right;
				}
				else{
					toRemove.right.parent = toRemove.parent;
					toRemove.parent.right = toRemove.right;
				}
			}
		}
		else{
			System.out.println("Mam dwoje dzieci");
			Node min = findMin(toRemove.right);
			toRemove.value = min.value;
			toRemove.indeksy = min.indeksy;
			remove(min);
		}
		return true;
	}
	
	
	public Node findMin(Node t){
		if (t.left == null) return t;
		return findMin(t.left);
	}
	
	public boolean leftChild(Node t){
		return t.parent.right == null || (t.parent.left != null && t.parent.left.value.compareTo(t.value) == 0);
	}
	
	public boolean isMark(char c){
		switch (c){
		case '.': case ',': case ':':
		case ';': case '!': case '?':
		case '-': 
			return true;
		}
		return false;
	}
	
	public boolean withMark(String s){
		char c = s.charAt(s.length()-1);
		return isMark(c);
	}

}
