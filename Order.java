import java.io.FileWriter;
import java.util.ArrayList;

public class Order {
    private String user;
    private ArrayList<Item> Cart;

        //Order Class
        public Order(){
            Cart = new ArrayList<>();
        }
        //Should write order in the form: Username,Product1Id,numBought,Product2Id,numBought,...
        public void writeToFile(){
            String filename = user + "_order.csv";
            try(FileWriter writer = new FileWriter(filename, true)){
                writer.write(user);

                for (Item item : Cart){
                    writer.write("," + item.getID() + "," + item.getItemsLeft());
                }
            } catch (Exception e){
                e.printStackTrace();
        }
    }
}
