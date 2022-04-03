package redblack_splay_assignment;

public class Main {

	public static void main(String[] args) {
		SplayTree tree = new SplayTree();
		tree.insert(33);
		tree.insert(44);
		tree.insert(67);
		tree.insert(5);
		tree.insert(89);
		tree.insert(41);
		tree.insert(98);
		tree.insert(1);
		tree.prettyPrint();
		tree.searchTree(33);
		tree.searchTree(44);
		tree.prettyPrint();
		tree.deleteNode(89);
		tree.deleteNode(67);
		tree.deleteNode(41);
		tree.deleteNode(5);
		tree.prettyPrint();
		tree.deleteNode(98);
		tree.deleteNode(1);
		tree.deleteNode(44);
		tree.deleteNode(33);
		tree.prettyPrint();
    	RedBlackTree bst = new RedBlackTree();
        bst.insert(8);
      	bst.insert(18);
      	bst.insert(5);
      	bst.insert(15);
      	bst.insert(17);
      	bst.insert(25);
      	bst.insert(40);
      	bst.insert(80);
      	bst.deleteNode(25);
      	bst.prettyPrint();
	}
}
