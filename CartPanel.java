import javax.swing.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.File;
import java.awt.Font;

public class CartPanel implements ActionListener{
    private class State{
        private String name;
        private double salesTaxPercent;
        public State(String state, double taxPerc){
            name = state; salesTaxPercent = taxPerc;
        }
        public String getState(){return name;}
        public double getTax(){return salesTaxPercent;}
    }

    private JFrame frame;
    private JComboBox<String> stateComboBox;
    private ArrayList<State> states;
    private String[] stateStrings;
    private JButton checkOutButton;
    private JScrollPane scrollPane;
    private JTable table;
    private Order order;
    private JLabel orderLabel;
    private JLabel priceLabel;


    public CartPanel(Order currOrder, String user){
        states = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File("StateTax.csv"));
            while(sc.hasNextLine()){
                String[] strs = sc.nextLine().split(",");
                String state = strs[0];
                double tax = Double.parseDouble(strs[1]);
                states.add(new State(state, tax));
            }
        } catch (Exception e) {
        }
        order = currOrder;
        frame = new JFrame("Cart Panel");

        stateStrings = new String[states.size()];
        for(int i = 0;i<states.size();i++){
            stateStrings[i] = states.get(i).getState();
        }

        ArrayList<Item> products = order.getCart();
        String[] columnNames = {"ID", "Name", "Category", "Price", "Quantity"};
        String[][] strs = new String[products.size()][5];
        for(int i = 0;i<products.size();i++){
            Item product = products.get(i);
            strs[i][0] = product.getID();
            strs[i][1] = product.getName();
            strs[i][2] = product.getCategory();
            strs[i][3] = "$" + product.getPrice();
            strs[i][4] = Integer.toString(product.getItemsLeft());
        }

        table = new JTable(strs, columnNames);
        scrollPane = new JScrollPane(table);
        table.setEnabled(false);
        table.setRowHeight(25);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        scrollPane.setBounds(10, 100, 1200, 600);
        frame.add(scrollPane);
        stateComboBox = new JComboBox<>(stateStrings);

        priceLabel = new JLabel("Please Select a State");

        frame.setLayout(null);
        frame.setBounds(0,0,1600,900);
        frame.setVisible(true);
    }   
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(stateComboBox)){
            State currState = new State("", 0);
            for(State s: states){
                if(s.getState().equals(stateComboBox.getSelectedItem())){
                    currState = s;
                }
            }
            double price = 0;
            ArrayList<Item> cart = order.getCart();
            for(Item product:cart){
                price += product.getPrice()*product.getItemsLeft();
            }
            price *= (1+currState.getTax());
            priceLabel.setText("$" + (((int)(price*100))/100.0));
        }
        if(e.getSource().equals(checkOutButton)){
            if(checkProducts()){
                orderLabel.setText("Order Placed");
                order.writeToFile();
            }
            else{
                orderLabel.setText("Check Amounts of Items");
            }
        }
    }
    public boolean checkProducts(){
        try {
            Scanner sc = new Scanner(new File("Products.csv"));
            ArrayList<Item> products = new ArrayList<>();
            while(sc.hasNextLine()){
                String[] strs = sc.nextLine().split(",");
                products.add(new Item(strs[0],strs[1],strs[2],Double.parseDouble(strs[3]),Integer.parseInt(strs[4])));
            }
            ArrayList<Item> cart = order.getCart();
            for(int i = 0; i<cart.size(); i++){
                boolean tf = false;
                for(int j = 0; j<products.size(); j++){
                    if(products.get(j).getID().equals(cart.get(i).getID())&&products.get(j).getItemsLeft()>cart.get(i).getItemsLeft()){
                        tf = true;
                    }
                }
                if(!tf){
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}