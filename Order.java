import java.io.*;
import java.util.HashMap;

public class Order {
    private String user;
    private ArrayList<Item> Cart;

    //Order Class
    public Order(String username){
        user = username;
        Cart = new ArrayList<Item>();
    }

    //Should write order in the form: Username,Product1Id,numBought,Product2Id,numBought,...
    public void writeToFile(){
        String filename = "Orders.csv";
        try(FileWriter writer = new FileWriter(filename, true)){
            writer.write(user);

            for (int i = 0; i < Cart.size(); i++){
                Item item = Cart.get(i);
                writer.write("," + item.getID() + "," + item.getItemsLeft());
            }
            writer.write("\n");
        } catch (Exception e){e.printStackTrace();}
        updateProducts();
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
    public static void main(String[] args) {
        Order order = new Order("User");
        Item item = new Item("ID1","Milk","Dairy",4.99,3);
        order.Cart.add(item);
        order.writeToFile();
    }
}