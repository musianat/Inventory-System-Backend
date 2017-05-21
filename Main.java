import java.io.*;
import java.util.*;
import BSTree.*;
/**
 * Created by nathanielmusial on 3/24/17.
 * Project 4
 * ntm160030
 */
public class Main {

    public static void main(String args[])throws IOException{

        BinarySearchTree inventory; //Variables and files required for the program
        File inv= new File("inventory.dat"); //opens existing file
        File trans = new File("transaction.log"); //opens existing file
        File kiosk = new File("redbox_kiosk.txt");//creates new file
        File logFile=new File("error.log");//creates new file
        if(logFile.exists()) //deletes existing log file
            logFile.delete();

        inventory=readInventory(inv); //processing inventory
       // inventory.printInorder();
        inventory=readTransactions(inventory, trans,logFile); //processing orders
       // inventory.printInorder();

        writeToFile(inventory,kiosk); //empties contents of binary tree to file in formatted output
    }

    public static boolean isInteger(String num)throws InputMismatchException{ //checks if string can be parsed to an integer
        try{
            Integer.parseInt(num);
            return true;
        }
        catch (InputMismatchException exp){
            return false;
        }
        catch (NumberFormatException exp1){
            return false;
        }
    }

    public static boolean isValidTitle(String title){
        if(title.charAt(0)=='"'&&title.charAt(title.length()-1)=='"')
            return true; //need to add check
        else
            return false;
    }

    public static BinarySearchTree readInventory( File inv) throws IOException{
            Scanner in= new Scanner(inv);
        BinarySearchTree tree=new BinarySearchTree();
            String line;
            String arr[];
           // String title;
           // int avaliable;
          // int rented;
            while(in.hasNextLine()){
                line= in.nextLine();
                arr=line.split(",");//splits by commas (Title,Available,Rented)

                String Title=arr[0];
                Title=Title.substring(1,Title.length()-1);

                Node n = new Node(Title,Integer.parseInt(arr[1]),Integer.parseInt(arr[2]));//creates node from inventory file
                tree.insert(n);
            }
            return tree;
    }

    public static BinarySearchTree readTransactions(BinarySearchTree tree,File trans,File logFile)throws IOException{

        Scanner in= new Scanner(trans);//parses transactions file
        Scanner chopper;//parses line
        String line;
        String arr[];//splits after operations is removed from the string
        String op;
        String title="";
        int amount=0;
        Node holder;
        Boolean valid;

        while(in.hasNextLine()){

            line=in.nextLine();
            chopper=new Scanner(line);
            op=chopper.next();//
            if(chopper.hasNext())
                title=line.substring(op.length()+1);//parses out title/amount from the line

            valid=false;

            if(isValidTitle(title)){
                title=title.substring(1,title.length()-1);//removes "" from the title
                valid=true;
            }
            else if(title.charAt(title.length()-1)!='"') {
                if(title.contains(",")) {
                    arr = title.split(",");
                    title = arr[0];//stores title

                    if (isInteger(arr[1])) {
                        amount = Integer.parseInt(arr[1]);

                        if(isValidTitle(title)) {
                            title = title.substring(1, title.length() - 1); //removes "" from title
                            valid=true;
                        }
                    }
                }
            }


            if(valid) {

                if (op.equals("rent")) {
                        holder=tree.searchTree(title);
                        if(holder!=null){
                            holder.setRented(holder.getRented()+1); //rents out movies
                            holder.setAvailable(holder.getAvailable()-1);
                        }
                }
                else if (op.equals("return")) {

                    holder=tree.searchTree(title);

                    if(holder!=null){
                        holder.setRented(holder.getRented()-1);//returns movie
                        holder.setAvailable(holder.getAvailable()+1);
                    }
                }
              else  if (op.equals("remove")) {

                    holder=tree.searchTree(title);

                    if(holder!=null){
                        holder.setAvailable(holder.getAvailable()-amount);//removes the number of copies requested
                        //System.out.println(holder.toString());
                        //System.out.println(holder.getRented());
                        if(holder.getAvailable()==0 && holder.getRented()==0) //if there are none available and none rented out deletes the title from the system
                            tree.deleteNode(title);
                    }
                }
                else if (op.equals("add")) {

                    holder=tree.searchTree(title);

                    if(holder!=null){
                        holder.setAvailable(holder.getAvailable()+amount); //adds amount of copies requeted
                    }
                    else{
                        Node n = new Node(title,amount,0);//creates a new node and inserets it into the tree
                        tree.insert(n);
                    }
                }
                else {
                    writeErrorLog(line,logFile);//writes to error log
                  //  System.out.println("error");
                }
            }
            else {
                writeErrorLog(line,logFile); //writes to error log
               // System.out.println("error");
            }
        }
        return tree;//returns modified tree
        }

    public static void writeErrorLog(String line,File file)throws IOException{

            FileWriter out=new FileWriter(file,true);//opens and appends error to the file
            out.write(line+"\n");
            out.close();
}

    public static void writeToFile(BinarySearchTree tree , File kiosk)throws IOException{
            PrintWriter out= new PrintWriter(kiosk);
            toFile(tree.getRoot(),out);
            out.close();
    }

    public static void toFile(Node r,PrintWriter out)throws IOException{
        if(r==null)
            return;
        toFile(r.getLeft(),out);
        out.printf("%-30s%d\t%d\n",r.getTitle(),r.getAvailable(),r.getRented());//prints formated in order
        toFile(r.getRight(),out);
    }
}