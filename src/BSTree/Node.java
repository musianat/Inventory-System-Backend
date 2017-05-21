package BSTree;

/**
 * Created by nathanielmusial on 3/24/17.
 * Project 4
 * ntm160030
 */
public class Node {
    String Title;
    int available;
    int rented;
    Node left;
    Node right;

    public Node(){
        this("",0,0);
    }
    public Node(String Title,int available,int rented){
        this.Title=Title;
        this.available=available;
        this.rented=rented;
    }

    public String getTitle() {
        return Title;
    }

    public int getAvailable() {
        return available;
    }

    public int getRented() {
        return rented;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public void setRented(int rented) {
        this.rented = rented;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return Title+"\t"+available+"\t"+rented;
    }
}
