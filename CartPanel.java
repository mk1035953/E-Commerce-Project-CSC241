import javax.swing.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.File;
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


    public CartPanel(Order currOrder){
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
        stateComboBox = new JComboBox<>(stateStrings);

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
        }
        if(e.getSource().equals(checkOutButton)){
            if(checkProducts()){

            }
            else{

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