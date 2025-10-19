public class User {
    private String userName;
    private String passWord;
    private String name;

    public User(String user, String pass){
        userName = user;
        passWord = pass;
    }
    public boolean signIn(String user, String pass){
        boolean tf = true;
        tf = tf&&userName.equals(user);
        tf = tf&&passWord.equals(pass);
        return tf;
    }

    //Accessors
    public String getName(){return name;}
    public String getUserType(){return "User";}
    //Mutators
    public void setName(String Name){name = Name;}
    public boolean resetPassword(String oldPass, String newPass){
        boolean tf = oldPass.equals(passWord);//for basic security purposes
        if(tf){
            passWord = newPass;
        }
        return tf;
    }
}
