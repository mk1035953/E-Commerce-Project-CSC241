import javax.swing.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.Font;

public class AnalyticsPanel implements ActionListener{
    private JFrame frame = new JFrame();
    private JScrollPane scrollPane;
    private JTable table;
    private Order avgOrder;
    private JButton retButton;
    private String user;

    public static void main(String[] args) {
        AdminPanel panel = new AdminPanel("Admin");
    }

    //Constructor
    public AnalyticsPanel(String username){
        user = username;
        ArrayList<Order> orders = new ArrayList<>();

        try {
        Scanner sc = new Scanner(new File("Orders.csv"));
        int count = 0;
        while(sc.hasNextLine()){
            String[] strs = sc.nextLine().split(",");
            if(strs.length==1){break;}
            Order order = lineToOrder(strs);
            orders.add(order);
            count++;
        }

        sc.close();
        sc = new Scanner(new File("Products.csv"));
        ArrayList<Item> products = new ArrayList<>();
        int numProducts = 0;
        while(sc.hasNextLine()){
            String[] strs = sc.nextLine().split(",");
            products.add(new Item(strs[0],strs[1],strs[2],Double.parseDouble(strs[3]), Integer.parseInt(strs[4])));
            numProducts++;
        }

        double[] numBoughts = new double[numProducts];
        for(int i = 0;i<numBoughts.length;i++){
            numBoughts[i] = 0;
        }

        sc.close();
        for(int i = 0; i<orders.size(); i++){
            ArrayList<Item> cart = orders.get(i).getCart();
            sc = new Scanner(new File("Products.csv"));
            int prodNum = 0;
            int cartNum = 0;
            insertionsort(cart);
            while(sc.hasNextLine()){
                String[] strs = sc.nextLine().split(",");
                if(strs[0].equals(cart.get(cartNum).getID())){
                    numBoughts[prodNum] += cart.get(cartNum).getItemsLeft();
                }
                prodNum++;
            }
            sc.close();
        }

        for(int i = 0;i<numBoughts.length;i++){
            numBoughts[i] = numBoughts[i] / count;
        }
        ArrayList<Item> avgCart = new ArrayList<>();
        for(int i = 0; i< numBoughts.length;i++){
            Item product = products.get(i);
            product.setItemsLeft((int)(numBoughts[i]+0.5));
            avgCart.add(product);
        }

        //Setup Swing stuff
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900);
        frame.setLayout(null);

        // Table column names
        String[] columnNames = {"ID", "Name", "Category", "Price", "Quantity"};

        avgOrder = new Order(username);
        avgOrder.setCart(avgCart);
        // Table data (rows)
        ArrayList<Item> Cart = avgOrder.getCart();
        String[][] strs = new String[Cart.size()][5];
        for(int i = 0;i<Cart.size();i++){
            Item product = Cart.get(i);
            strs[i][0] = product.getID();
            strs[i][1] = product.getName();
            strs[i][2] = product.getCategory();
            strs[i][3] = "$" + product.getPrice();
            strs[i][4] = Integer.toString(product.getItemsLeft());
        }
        //Set up Table
        table = new JTable(strs, columnNames);
        scrollPane = new JScrollPane(table);
        table.setEnabled(false);
        table.setRowHeight(25);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        scrollPane.setBounds(10, 100, 1200, 750);

        JLabel orderLabel = new JLabel("Average order");
        orderLabel.setBounds(10,40,300,30);
        orderLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        retButton = new JButton("return");
        retButton.setBounds(1300,750,160,50);
        retButton.addActionListener(this);

        //add the parts
        frame.add(scrollPane);
        frame.add(retButton);
        frame.add(orderLabel);

        frame.setVisible(true);
            
        } catch (FileNotFoundException e) {
        }

    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(retButton)){
            AdminPanel panel = new AdminPanel(user);
            frame.dispose();
        }
    }
    public Order lineToOrder(String[] orderLine){
        Order order = new Order(orderLine[0]);
        try {
            for(int i = 2;i<orderLine.length;i+=2){
                String str1 = orderLine[i];
                String str2 = orderLine[i+1];
                Scanner sc = new Scanner(new File("Products.csv"));
                ArrayList<Item> tempCart = order.getCart();
                while(sc.hasNextLine()){
                    String[] item = sc.nextLine().split(",");
                    Item product = new Item(item[0],item[1],item[2],Double.parseDouble(item[3]), Integer.parseInt(str2));
                    if(product.getID().equals(str1)){
                        tempCart.add(product);
                        Order tempOrder = new Order("");
                        tempOrder.setCart(tempCart);
                        if(tempOrder.isValid()){
                            order.setCart(tempCart);
                        }
                        else{
                            break;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return order;
    }
    public ArrayList<Item> insertionsort(ArrayList<Item> arr){  
        ArrayList<Item> tempArr = arr;
        for (int i = 0; i < tempArr.size(); i++) {
            Item temp = tempArr.get(i);
            int j = i - 1;

            while (j >= 0 && tempArr.get(j).getID().compareTo(temp.getID())<0) {
                tempArr.set(j+1,tempArr.get(j));
                j--;
            }

            tempArr.set(j+1,temp);
        }
        return tempArr;
    }
}
