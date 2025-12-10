import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class Order {
    private String user;
    private ArrayList<Item> Cart;

    //Order Class
    public Order(String username){
        user = username;
        Cart = new ArrayList<Item>();
    }

    //Should write order in the form: Username,Placed/Completed,Product1Id,numBought,Product2Id,numBought,...
    public void writeToFile(){
        if(isValid()){
            String filename = "Orders.csv";
            try(FileWriter writer = new FileWriter(filename, true)){
                writer.write(user+",Placed");

                for (int i = 0; i < Cart.size(); i++){
                    Item item = Cart.get(i);
                    writer.write("," + item.getID() + "," + item.getItemsLeft());
                }
                writer.write("\n");
            } catch (Exception e){e.printStackTrace();}
            updateProducts();
        }
        else{
            throw new IndexOutOfBoundsException("Invalid Cart");
        }
    }

    // Update the Products.csv quantities by subtracting the quantities
    public void updateProducts(){
        File productsFile = new File("Products.csv");
        if (!productsFile.exists()) return;

        HashMap<String, Integer> itemsBought = new HashMap<String, Integer>();
        for (int i = 0; i < Cart.size(); i++){
            Item item = Cart.get(i);
            String id = item.getID();
            int qty = item.getItemsLeft(); // using ItemsLeft as the quantity being purchased
            itemsBought.put(id, qty);
        }

        ArrayList<String> lines = new ArrayList<String>();

        try (BufferedReader reader = new BufferedReader(new FileReader(productsFile))){
            String line;
            while ((line = reader.readLine()) != null){
                lines.add(line);
            }
        } catch (IOException e){
            e.printStackTrace();
            return;
        }

        ArrayList<String> out = new ArrayList<String>();
        for (int i = 0; i < lines.size(); i++){
            String line = lines.get(i);
            String[] parts = line.split(",");

            if (parts.length >= 5){
                String id = parts[0];
                int qty = Integer.parseInt(parts[4]);
                Integer dec = itemsBought.get(id);
                if(dec==null){dec = 0;}
                qty = Math.max(0, qty - dec);
                parts[4] = String.valueOf(qty);
                out.add(String.join(",", parts));
            } else {
                out.add(line);
            }
        }

        try (FileWriter writer = new FileWriter(productsFile, false)){
            for (int i=0; i < out.size(); i++){
                writer.write(out.get(i));
                writer.write(System.lineSeparator());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    
        
    }
    public void addToCart(Item product){
        Cart.add(product);
    }
    public void removeFromCart(String id){
        for(int i = 0; i<Cart.size();i++){
            if(Cart.get(i).getID().equals(id)){
                Cart.remove(i);
            }
        }
    }
    public boolean isValid(){
        ArrayList<Item> products = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File("Products.csv"));
            while(sc.hasNextLine()){
                String[] item = sc.nextLine().split(",");
                Item product = new Item(item[0],item[1],item[2],Double.parseDouble(item[3]), Integer.parseInt(item[4]));
                products.add(product);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Product File Not Found");
        }

        for(Item cartItem: Cart){
            boolean tf = false;
            for(Item product : products){
                if(product.getID().equals(cartItem.getID())){
                    tf = tf||(product.getItemsLeft()>=cartItem.getItemsLeft());
                }
            }
            if(!tf){
                return false;
            }
        }
        return true;
    }
    public double getPrice(double tax){
        double sum = 0;
        for(Item product: Cart){
            sum += product.getItemsLeft()*product.getPrice();
        }
        return sum * (1+tax);
    }
    public String getUser(){return user;}
    public ArrayList<Item> getCart(){return Cart;}
    public void setCart(ArrayList<Item> newCart){
        Cart = newCart;
    }
    public static void main(String[] args) {
        Order order = new Order("User");
        Item item = new Item("ID1","Milk","Dairy",4.99,10);
        order.Cart.add(item);
        order.writeToFile();
    }
}