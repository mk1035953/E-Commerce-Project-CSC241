import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.ActionListener;

public class CustomerPanel implements ActionListener{
    private String user;
    private DataBase database = new DataBase();
    private JFrame frame = new JFrame("Customer Panel");
    String[] filterTypes = {"ID", "Name", "Price", "Category","Quantity"};
    private JComboBox filters = new JComboBox<>(filterTypes);
    private JTable table;
    private JButton profileButton;
    private JRadioButton sortReverse;
    private JRadioButton sortTop;
    private boolean sortMethod = true;
    private ButtonGroup sortButtons;
    private JScrollPane scrollPane;
    private JTextField searchBox;
    private JButton searchButton;

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
        //Set up Table
        table = new JTable(strs, columnNames);
        scrollPane = new JScrollPane(table);
        table.setEnabled(false);
        table.setRowHeight(25);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        //Set up ProfileButton
        profileButton = new JButton("Profile");

        //Set up search feature
        JLabel searchLabel = new JLabel("Search");
        searchBox = new JTextField("Enter Item Name Here");
        searchButton = new JButton("Search");
        searchLabel.setBounds(10,10,200,30);
        searchButton.setBounds(410,40,100,50);
        searchBox.setBounds(10,40,400,50);
        searchButton.addActionListener(this);

        //Set up sortButtons
        sortReverse = new JRadioButton("Bottom-Top");
        sortTop = new JRadioButton("Top-Bottom");
        sortButtons = new ButtonGroup();
        sortButtons.add(sortTop);
        sortButtons.add(sortReverse);
        sortTop.setSelected(true);
        sortTop.setBounds(1300,320,200,20);
        sortReverse.setSelected(false);
        sortReverse.setBounds(1300,340,200,20);

        //Setup ActionListeners
        sortTop.addActionListener(this);
        sortReverse.addActionListener(this);
        profileButton.addActionListener(this);
        filters.addActionListener(this);

        //Setting the bounds
        lbl1.setBounds(1300, 275, 100, 20);
        filters.setBounds(1300,300,200,20);
        scrollPane.setBounds(10, 100, 1200, 600);
        profileButton.setBounds(1400,30,100,30);

        //add the parts
        frame.add(scrollPane);
        frame.add(lbl1);
        frame.add(filters);
        frame.add(profileButton);
        frame.add(sortTop);
        frame.add(sortReverse);
        frame.add(searchLabel);
        frame.add(searchBox);
        frame.add(searchButton);

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
            table.setEnabled(false);
            table.setRowHeight(25);
            table.setFont(new Font("SansSerif", Font.PLAIN, 14));
            table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
            scrollPane.setBounds(10, 100, 1200, 600);
            frame.add(scrollPane);
            frame.setVisible(true);
        }
        if(e.getSource().equals(sortTop)){
            sortMethod = true;
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
            table.setEnabled(false);
            table.setRowHeight(25);
            table.setFont(new Font("SansSerif", Font.PLAIN, 14));
            table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
            scrollPane.setBounds(10, 100, 1200, 600);
            frame.add(scrollPane);
            frame.setVisible(true);
        }
        if(e.getSource().equals(sortReverse)){
            sortMethod = false;
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
            table.setEnabled(false);
            table.setRowHeight(25);
            table.setFont(new Font("SansSerif", Font.PLAIN, 14));
            table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
            scrollPane.setBounds(10, 100, 1200, 600);
            frame.add(scrollPane);
            frame.setVisible(true);
        }
        if(e.getSource().equals(searchButton)){
            String str = (searchBox.getText());
            if(str.equals("")){
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
                table.setEnabled(false);
                table.setRowHeight(25);
                table.setFont(new Font("SansSerif", Font.PLAIN, 14));
                table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
                scrollPane.setBounds(10, 100, 1200, 600);
                frame.add(scrollPane);
                frame.setVisible(true);
            }
            else{
                ArrayList<Item> products = database.products;
                ArrayList<Item> temp = new ArrayList<>();
                for(int i = 0;i<products.size();i++){
                    if(products.get(i).getName().toLowerCase().equals(str.toLowerCase())){
                        temp.add(products.get(i));
                    }
                }
                String[] columnNames = {"ID", "Name", "Category", "Price", "Quantity"};
                String[][] strs = new String[temp.size()][5];
                for(int i = 0;i<temp.size();i++){
                    Item product = temp.get(i);
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
                table.setEnabled(false);
                table.setRowHeight(25);
                table.setFont(new Font("SansSerif", Font.PLAIN, 14));
                table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
                scrollPane.setBounds(10, 100, 1200, 600);
                frame.add(scrollPane);
                frame.setVisible(true);
            }
        }
    }
    public ArrayList<Item> insertionsort(ArrayList<Item> arr, int swi){  
        ArrayList<Item> tempArr = arr;
        for (int i = 0; i < tempArr.size(); i++) {
            Item temp = tempArr.get(i);
            int j = i - 1;

            if(sortMethod){
                switch (swi) {
                    case 0:
                        while (j >= 0 && tempArr.get(j).getID().compareTo(temp.getID())<0) {
                            tempArr.set(j+1,tempArr.get(j));
                            j--;
                        }
                        break;
                    case 1:
                        while (j >= 0 && tempArr.get(j).getName().compareTo(temp.getName())<0) {
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
                        while (j >= 0 && tempArr.get(j).getCategory().compareTo(temp.getCategory())<0) {
                            tempArr.set(j+1,tempArr.get(j));
                            j--;
                        }
                        break;
                    case 4:
                        while (j >= 0 && tempArr.get(j).getItemsLeft()>(temp.getItemsLeft())) {
                            tempArr.set(j+1,tempArr.get(j));
                            j--;
                        }
                        break;
                    default:
                        throw new AssertionError();
                }
            }
            else{
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
                        while (j >= 0 && tempArr.get(j).getPrice()>(temp.getPrice())) {
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
                    case 4:
                        while (j >= 0 && tempArr.get(j).getItemsLeft()<(temp.getItemsLeft())) {
                            tempArr.set(j+1,tempArr.get(j));
                            j--;
                        }
                        break;
                    default:
                        throw new AssertionError();
                }
            }
            tempArr.set(j+1,temp);
        }
        return tempArr;
    }
}