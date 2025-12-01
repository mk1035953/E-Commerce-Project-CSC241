import javax.crypto.spec.RC2ParameterSpec;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CustomerPanel implements ActionListener{
    private String user;
    private DataBase database = new DataBase();
    private JFrame frame = new JFrame("Customer Panel");
    String[] filterTypes = {"ID", "Name", "Price", "Category"};
    private JComboBox filters = new JComboBox<>(filterTypes);
    private JTable table;
    private JButton profileButton;
    private JRadioButton sortReverse;
    private JRadioButton sortTop;
    private ButtonGroup sortButtons;
    private JScrollPane scrollPane;
    public static void main(String[] args) {
        CustomerPanel panel = new CustomerPanel("User");
    }
    public CustomerPanel(String user){
        JLabel lbl1 = new JLabel("Filters");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900);
        frame.setLayout(null);

        // Table column names
        String[] columnNames = {"ID", "Name", "Category", "Price", "Quantity"};

        // Table data (rows)
        String[][] strs = new String[database.products.size()][5];
        for(int i = 0;i<database.products.size();i++){
            Item product = database.products.get(i);
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

        profileButton = new JButton("Profile");

        sortReverse = new JRadioButton("Bottom-Top");
        sortTop = new JRadioButton("Top-Bottom");
        
        sortButtons = new ButtonGroup();
        sortButtons.add(sortTop);
        sortButtons.add(sortReverse);

        profileButton.addActionListener(this);
        filters.addActionListener(this);

        lbl1.setBounds(1300, 275, 100, 20);
        filters.setBounds(1300,300,200,20);
        scrollPane.setBounds(10, 100, 1200, 600);
        profileButton.setBounds(1400,30,100,30);

        frame.add(scrollPane);
        frame.add(lbl1);
        frame.add(filters);
        frame.add(profileButton);

        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(profileButton)){
            ProfileWindow window = new ProfileWindow(user, false);
            frame.dispose();
        }
        if(e.getSource().equals(filters)){
            String filter = (String)filters.getSelectedItem();
            for(int i = 0;i<filterTypes.length;i++){
                if(filterTypes[i].equals(filter)){
                    database.products = insertionsort(database.products, i);
                }
            }
            String[] columnNames = {"ID", "Name", "Category", "Price", "Quantity"};
            String[][] strs = new String[database.products.size()][5];
            for(int i = 0;i<database.products.size();i++){
                Item product = database.products.get(i);
                strs[i][0] = product.getID();
                strs[i][1] = product.getName();
                strs[i][2] = product.getCategory();
                strs[i][3] = "$" + product.getPrice();
                strs[i][4] = Integer.toString(product.getItemsLeft());
            }

            frame.remove(scrollPane);
            frame.setVisible(false);
            table = new JTable(strs, columnNames);
            scrollPane = new JScrollPane(table);
            scrollPane.setBounds(10,100,1000,600);
            frame.add(scrollPane);
            frame.setVisible(true);
        }
    }
    public static ArrayList<Item> insertionsort(ArrayList<Item> arr, int swi){  
        ArrayList<Item> tempArr = arr;
        for (int i = 0; i < tempArr.size(); i++) {
            Item temp = tempArr.get(i);
            int j = i - 1;

            switch (swi) {
                case 0:
                    while (j >= 0 && tempArr.get(j).getID().compareTo(temp.getID())>0) {
                        tempArr.set(j+1,tempArr.get(j));
                        j--;
                    }
                    break;
                case 1:
                    while (j >= 0 && tempArr.get(j).getName().compareTo(temp.getName())>0) {
                        tempArr.set(j+1,tempArr.get(j));
                        j--;
                    }
                    break;
                case 2:
                    while (j >= 0 && tempArr.get(j).getPrice()<(temp.getPrice())) {
                        tempArr.set(j+1,tempArr.get(j));
                        j--;
                    }
                    break;
                case 3:
                    while (j >= 0 && tempArr.get(j).getCategory().compareTo(temp.getCategory())>0) {
                        tempArr.set(j+1,tempArr.get(j));
                        j--;
                    }
                    break;
                default:
                    throw new AssertionError();
            }

            tempArr.set(j+1,temp);
        }
        return tempArr;
    }
}
