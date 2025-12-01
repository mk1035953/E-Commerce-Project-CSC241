import java.util.Scanner;
import java.io.File;

public class DataBase {
    public ArrayList<Item> products = new ArrayList<>();

    public DataBase() {
        try {
            Scanner sc = new Scanner(new File("Products.csv"));
            while(sc.hasNextLine()){
                String[] strs = sc.nextLine().split(",");
                addProducts(strs[0], strs[1], strs[2], Double.parseDouble(strs[3]), Integer.parseInt(strs[4]));
            }
        } catch (Exception e) {
        }
    }
    public void addProducts(String id, String name, String category, double price){
        products.add(new Item(id,name,category,price));
    }
    public void addProducts(String id, String name, String category, double price, int quantity){
        products.add(new Item(id,name,category,price,quantity));
    }
}