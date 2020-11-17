package lab7;

public class User {
    private int id;
    private String username;
    private String password;
    private int role;
    private String create;
    private String update;


    public User(){
        id = 0;
        username="";
        password="";
        role = 0;
        create="";
        update="";
    }
    public User(int id,String user,String password,int role,String create,String update){
        this.id=id;
        this.username=user;
        this.password=password;
        this.role = role;
        this.create=create;
        this.update=update;
    }
    public User(String user,String password,int role){
        this.username=user;
        this.password=password;
        this.role=role;
    }

    public int getId() {
        return id;
    }

    public int getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public void setUpdate(String delete) {
        this.update = delete;
    }

    public String getCreate() {
        return create;
    }

    public String getUpdate() {
        return update;
    }

    @Override
    public String toString() {
        return String.format("%-30d%-30s%-30s%-30d%-30s%-30s",id,username,password,role,create,update);
    }
}
