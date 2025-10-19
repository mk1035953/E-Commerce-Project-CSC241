public class Customer extends User{
    public Customer(String user, String pass){
        super(user, pass);
    }

    //Accessors
    public String getUserType(){return "Customer";}
}
