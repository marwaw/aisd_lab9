import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class RBTree {
	NodeRBT root;
	
	public RBTree(String fileName) throws FileNotFoundException{
		read(fileName); 
	}
	
	public RBTree(){
		root = null;
	}
	
	public NodeRBT search(String s, NodeRBT t){
		if (t == null || t.value.compareTo(s) == 0){
			return t;
		}
		if (s.compareTo(t.value) < 0){
			return search(s, t.left);
		}
		return search(s, t.right);
	}
	
	public void insert(String str, int i){
		String s = str.toLowerCase();
		if (search(s, root) != null) {
			search(s, root).addToArray(i);
		}
		else{
			NodeRBT node = new NodeRBT(s);
			NodeRBT par = null;
			NodeRBT x = root;
			while( x != null){
				par = x;
				if (s.compareTo(x.value) < 0){
					x = x.left;
				}
				else {
					x = x.right;
				}
			}
			node.setParent(par);
			
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
			node.setRed();
			node.addToArray(i);
			fixUp(node);
		}
	}
	
	public  void fixUp(NodeRBT t){
		System.out.println("Fix up "+t.value);
		if( root.value.compareTo(t.value) == 0){
//			System.out.println("Jestem korzeniem "+t.value);
			t.setBlack();
		}
		else{
			if ( isRed(t.parent) ){
//				System.out.println("Moj ojciec jest czerwony");
				if ((leftChild(t.parent) && (t.parent.parent.right == null || !isRed(t.parent.parent.right)))){
					if(leftChild(t)){
						System.out.println("LL");
						rotateRight(t.parent.parent);
						
					}
					else{
						System.out.println("LR");
						rotateLeft(t.parent);
						fixUp(t.left);
					}
				}
				else if (!leftChild(t.parent) && (t.parent.parent.left == null || !isRed(t.parent.parent.left))){
					if(leftChild(t)){
						System.out.println("RL");
						rotateRight(t.parent);
						fixUp(t.right);
					}
					else{
						System.out.println("RR");
						rotateLeft(t.parent.parent);
					}
				}
				else{
					System.out.println("RB");
					t.parent.parent.left.setBlack();
					t.parent.parent.right.setBlack();
					t.parent.parent.setRed();
					fixUp(t.parent.parent);
				}
			}
		}
	}
	
	public void remove(String s){
		remove(search(s, root));
	}
	
	public void delete(NodeRBT toRemove){
		if(toRemove.left == null && toRemove.right == null){
			System.out.println("Nie mam dzieci" + toRemove.value);
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
			System.out.println("Mam dwoje dzieci "+ toRemove.value );
			NodeRBT min = findMin(toRemove.right);
			toRemove.value = min.value;
			toRemove.indeksy = min.indeksy;
			remove(min);
		}
	}
	
	public void remove(NodeRBT toRemove){
		if(isRed(toRemove)){
			delete(toRemove);
		}
		else{
			if(leftChild(toRemove)){
				removeL(toRemove);
			}
			else{
				removeR(toRemove);
			}
		}
	}
	
	public void removeL(NodeRBT t){
		NodeRBT sib = t.parent.right;
		if (t.parent.right.color == 'r'){
			rotateLeft(t.parent);
			delete(t);
		}
		else{
			if(sib.right != null && sib.right.color == 'r' || sib.left != null && sib.left.color == 'r' ){
				rotateLeft(t.parent);
				delete(t);
			}
			else if ((sib.left == null || sib.left.color == 'b') || (sib.right == null || sib.right.color == 'b')){
				changeColors(sib);
				delete(t);
			}
		}
	}
	
	public void removeR(NodeRBT t){
		NodeRBT sib = t.parent.left;
		if (sib.color == 'r'){
			rotateRight(t.parent);
			delete(t);
		}
		else{
			if(sib.left != null && sib.left.color == 'r' || sib.right != null && sib.right.color == 'r' ){
				rotateRight(t.parent);
				delete(t);
			}
		}
	}
	
	public void changeColors(NodeRBT t){
		t.color = 'b';
		brother(t).color = 'r';
		t.parent.color = 'b';
//		changeColors();
	}
	
	public NodeRBT brother(NodeRBT t){
		if(leftChild(t)) return t.parent.right;
		return t.parent.left;
	}
	
	
	public NodeRBT findMin(NodeRBT t){
		if (t.left == null) return t;
		return findMin(t.left);
	}
	
	public boolean leftChild(NodeRBT t){
		return t.parent.right == null || (t.parent.left != null && t.parent.left.value.compareTo(t.value) == 0);
	}

	public void rotateLeft(NodeRBT t){
		System.out.println("Rotacja w lewo, wg "+t.value);
		NodeRBT x = t.right;
		t.right = x.left;
		if (x.left != null) x.left.parent = t;
		x.parent = t.parent;
		if (t.parent == null) root = x;
		else if (leftChild(t)) t.parent.left = x;
		else t.parent.right = x;
		x.left = t;
		t.parent = x;
		x.color = t.color;
		t.setRed();
	}
	
	public void rotateRight(NodeRBT t){
		System.out.println("Rotacja w prawo, wg "+t.value);
		NodeRBT x = t.left;
		t.left = x.right;
		if(x.right != null) x.right.parent = t;
		x.parent = t.parent;
		if (t.parent == null) root = x;
		else if (leftChild(t)) t.parent.left = x;
		else t.parent.right = x;
		x.right = t;
		t.parent = x;
		x.color = t.color;
		t.setRed();
	}
	
	private boolean isRed(NodeRBT t){
		return t != null && "r".compareTo(Character.toString(t.color))==0;
	}
	
	
	public void inOrder(NodeRBT node){
		if(node != null){
			inOrder(node.left);
			System.out.printf("%-14s%-3s", node.value, node.color);
			node.wyswietl();
			System.out.println("");
			inOrder(node.right);
		}
	}
	
	public void wszerz(NodeRBT root){
		Queue<NodeRBT> kolejka = new LinkedList<NodeRBT>();
		kolejka.add(root);
		while(!kolejka.isEmpty()){
			NodeRBT temp = kolejka.poll();
			if(temp.left != null){
				kolejka.add(temp.left);
			}
//			else System.out.println("LEFT NULL");
			if(temp.right != null){
				kolejka.add(temp.right);
			}
//			else System.out.println("RIGHT NULL");
//			if (temp.color == 'r' && ((temp.left != null && temp.left.color == 'r') || (temp.right != null && temp.right.color == 'r'))){
				System.out.printf("%-14s%-3s", temp.value, temp.color);
//			}
			temp.wyswietl();
			System.out.println("");
			temp = kolejka.peek();
		}
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
					insert(s.substring(0, s.length()-1), i);
				}
				else{
					insert(s, i);
				}
				
			}
			lineScanner.close();
			i++;
		}
		fileScanner.close();
	}
	
	public boolean isCorrectTree(){
        return isHeightCorrect(root) && root.color == 'B' && correctColors(root) && correctBlack(root);
    }

    public boolean correctBlack(NodeRBT node){
        if (node == null) return true;
        boolean b = countBlackLeft(node.left) == countBlackRight(node.right);
        return b && correctBlack(node.left) && correctBlack(node.right);

    }

    public int countBlackLeft(NodeRBT node){
        if (node == null) return 0;
        int n = 0;
        if (node.color == 'B') n++;
        return n + countBlackLeft(node.left);
    }

    public int countBlackRight(NodeRBT node){
        if (node == null) return 0;
        int n = 0;
        if (node.color == 'B') n++;
        return n + countBlackRight(node.right);
    }

    public boolean correctColors(NodeRBT node){
        if (node == null) return true;
        boolean b;
        if (node.color == 'B')
            b = true;
        else b = ((node.left == null || node.left.color == 'B') && (node.right == null ||node.right.color == 'B'));
        return correctColors(node.left) && correctColors(node.right) && b;
    }


    public boolean isHeightCorrect(NodeRBT node){
        if(node == null)
            throw new NullPointerException("We can't say if nil is correct");
        return maxHeight(node) <= 2 * minHeight(node);
    }
    
    public int maxHeight(NodeRBT node){
        if (node == null ) return 0;
        return 1 + Math.max(maxHeight(node.left), maxHeight(node.right)) ;
    }

    public int minHeight(NodeRBT node){
        if (node == null ) return 0;
        return 1 + Math.min(minHeight(node.left), minHeight(node.right)) ;
    }
	
	private boolean isMark(char c){
		switch (c){
		case '.': case ',': case ':':
		case ';': case '!': case '?':
		case '-': 
			return true;
		}
		return false;
	}
	
	private boolean withMark(String s){
		char c = s.charAt(s.length()-1);
		return isMark(c);
	}
	
	
	

}
