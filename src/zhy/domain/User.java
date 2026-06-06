package zhy.domain;

import java.util.Random;

public class User {
    private String id;
    private String username;
    private String password;
    private boolean status;

    public User() {
        id =creatID();

        status =true;
    }

    public User(String id, String username, String password, boolean state) {
        id = creatID();
        this.username = username;
        this.password = password;
        status =true;
    }

    //用户无法设置，是自动生成的，格式为：zhy+5位数字的随机数
    public String creatID(){
        StringBuilder sb = new StringBuilder("zhy");

        Random r =new Random();
        for (int i = 0; i < 5; i++) {
            int num = r.nextInt(10);
            sb.append(num);
        }
        return sb.toString();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setState(boolean status) {
        this.status = status;
    }
}
