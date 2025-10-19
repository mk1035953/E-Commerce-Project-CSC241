public class DataBase {
    private class Item{
        private String ID;
        private String Name;
        private String Category;
        private double Price;
        private int Quantity;

        public Item(){

        }
        public Item(String id, String name){
            ID = id;
            Name = name;
        }
        public Item(String id, String name, String category, double price){
            ID = id;
            Name = name;
            Category = category;
            Price = price;
        }
        public Item(String id, String name, String category, double price, int quantity){
            ID = id;
            Name = name;
            Category = category;
            Price = price;
            Quantity = quantity;
        }

        //Accessors
        public String getID(){return ID;}
        public String getName(){return Name;}
        public String getCategory(){return Category;}
        public double getPrice(){return Price;}
        public int getItemsLeft(){return Quantity;}

        //Mutators
        public void setID(String id){ID = id;}
        public void setName(String name){Name = name;}
        public void setCategory(String category){Category = category;}
        public void setItemsLeft(int quantity){Quantity = quantity;}
        public void setPrice(double amount){
            int temp = (int)(amount*100+0.5);
            Price = temp/100.0;//this Rounds to the nearest Cent
        }
    }
}