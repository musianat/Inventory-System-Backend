package BSTree;

/**
 * Created by nathanielmusial on 3/24/17.

 */
public class BinarySearchTree {
    private Node root;
    public BinarySearchTree(){
        this(null);
    }
    public BinarySearchTree(Node root){
        this.root=root;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void insert(Node n){

            root=insert(root,n);
    }
    public Node insert(Node node,Node newNode){
        if(node==null){ //base case inserts the node
            node = newNode;
            return node;
        }
        if(newNode.getTitle().compareTo(node.getTitle())<0) //if less than
            node.left= insert(node.left,newNode); //go left
        else
            node.right=insert(node.right,newNode); //else go right

        return node;
    }

    public boolean isEmpty()
    {
        return root == null;
    }
        public void deleteNode(String title )
        {
            if (isEmpty()) //determines if tree is empty
                System.out.println("Tree Empty");
            else if (searchTree(title) == null)
                System.out.println(title +" is not present"); //determines if node asked to delte is there
            else
            {
                root = deleteNode(root, title);
                //System.out.println(title+ " deleted from the tree");
            }
        }
    private Node deleteNode(Node root, String title)
    {
        Node p, p2, n;//holder Nodes
        if (root.getTitle().equals(title)) //base case
        {
            Node lt, rt;
            lt = root.getLeft();//finds left
            rt = root.getRight(); //finds right
            if (lt == null && rt == null) //if leaf
                return null;
            else if (lt == null) //if left is null
            {
                p = rt;
                return p; //moves right
            }
            else if (rt == null)
            {
                p = lt;
                return p; //moves left
            }
            else
            {
                p2 = rt;
                p = rt;
                while (p.getLeft() != null)
                    p = p.getLeft();
                p.setLeft(lt);
                return p2;// removes ending
            }
        }
        if (title.compareTo(root.getTitle()) <0)  //if less than
        {
            n = deleteNode(root.getLeft(), title); //go left
            root.setLeft(n);
        }
        else
        {
            n = deleteNode(root.getRight(), title); //else go right
            root.setRight(n);
        }
        return root; //returns deleted node
    }
    public Node searchTree(String Title){
        return searchTree(root,Title);
    }
    public Node searchTree(Node r,String Title){
       if(r==null)//base case not found
           return null;
       if(Title.equals(r.Title)) //base case found
           return r;
       else{
           Node left=searchTree(r.left, Title);//go left
           Node right=searchTree(r.right,Title);//go right
           if(left!=null)
               return left;
           else
               return right;
       }
    }
    public void printInorder(){
        System.out.println("Title\tqAv\tqRt");
        printInorder(root);
    }
    public void printInorder(Node node){
        if (node == null) //base case
            return;//terminates

        printInorder(node.left); //go left

        System.out.println(node.toString()); //print

        printInorder(node.right); //go right
    }

    @Override
    public String toString() {
        return toString(root);
    }

    public static String toString(Node r){
        if(r==null)
            return "";
        else
            return toString(r.left)+r.toString()+"\n"+toString(r.right); //appends binary tree nodes inorder
    }
}
