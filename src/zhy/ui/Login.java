package zhy.ui;

import zhy.domain.User;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Login {
    public void start() {
        System.out.println("游戏的登陆注册界面打开了！");


        ArrayList<User> list = new ArrayList<>();

        while (true) {
            System.out.println("╔════════════════════════════════╗");
            System.out.println("    🎮 欢迎来到文字格斗游戏 🎮   ");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("请选择操作：1登录 2注册 3退出");

            Scanner sc = new Scanner(System.in);
            String choose = sc.next();

            switch (choose) {
                case "1" -> login(list);
                case "2" -> register(list);
                case "3" -> {
                    System.out.println("用户选择了退出操作~");
                    System.exit(0);
                }
                default -> System.out.println("输入有误，请重新输入~");
            }
        }
    }

    public void login(ArrayList<User> list) {
        System.out.println("用户选择了登录操作~");

//	用户名如果未注册提示：用户名未注册，请先注册
        Scanner sc =new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username =sc.next();
        if (!contains(list,username)){
            System.out.println("用户名"+username+"不存在，请先注册！");
            return;
        }

//	用户被锁定提示：用户xxx已经锁定，请联系黑马程序员官方客服：XXX-XXXXX
        int index = findIndex(list, username);
        User u =list.get(index);
        if (!u.isStatus()) {
            System.out.println("用户"+username+"已经锁定，请联系工作人员：110");
            return;
        }

//	验证码错误提示：验证码输入错误，请重新输入，并生成一个新的验证码
//	判断用户名和密码是否正确，有3次机会，满3次账户锁定。
        String rightPassword =u.getPassword();

        for (int i = 0; i < 3; i++) {
            System.out.println("请输入密码：");
            String password =sc.next();

            while (true) {
                String rightcode = getCode();
                System.out.println("验证码："+ rightcode);

                System.out.println("请输入验证码：");
                String code =sc.next();
                if (rightcode.equalsIgnoreCase(code)){
                    System.out.println("验证码正确");
                    break;
                }else {
                    System.out.println("验证码错误");
                    continue;
                }
            }
//            System.out.println("right"+rightPassword);
//            System.out.println("pass"+password);
            if (rightPassword.equals(password)){
                System.out.println("登录成功，游戏启动！");
                break;
            }else {
                System.out.println("登录失败，密码错误！");
                if (i==2){
                    u.setState(false);
                    System.out.println("当前账号已锁定，请联系工作人员：110");
                    return;
                }else {
                    System.out.println("还剩下"+(2-i)+"次机会");
                }
            }
        }
    }

    public void register(ArrayList<User> list) {
        System.out.println("用户选择了注册操作~");

        User u = new User();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("请输入用户名：");
            String username = sc.next();

//        用户名唯一
//                * 长度必须在3 ~ 16位
            if (!checkLen(3, 16, username)) {
                System.out.println("用户名不符合要求，长度必须在3 ~ 16位");
                continue;
            }

//                * 只能由字母、数字组成，不能是纯数字
            if (!checkUsername(username)) {
                System.out.println("用户名只能由字母、数字组成，不能是纯数字");
                continue;
            }

            if (contains(list, username)) {
                System.out.println("当前的用户名已存在，请重新输入");
                continue;
            }

            u.setUsername(username);
            break;
        }

//        密码要求：
//                *长度3 ~ 8
        while (true) {
            System.out.println("请输入密码：");
            String passWord1 = sc.next();
            System.out.println("请再次输入密码：");
            String passWord2 = sc.next();

            if (!checkLen(3, 8, passWord1)) {
                System.out.println("用户名不符合要求，长度必须在3 ~ 16位");
                continue;
            }

//                * 只能是字母加数字的组合，不能有其他字母
            if (!checkPassWord(passWord1)) {
                System.out.println("用户名只能是字母加数字的组合，不能有其他字母");
                continue;
            }

            if (!passWord1.equals(passWord2)){
                System.out.println("两次输入的密码不一致，请重新输入。");
                continue;
            }

            u.setPassword(passWord1);
            break;
        }
        list.add(u);
        System.out.println("用户："+u.getUsername()+"注册成功！");
    }

    public boolean checkLen(int minLen, int maxLen, String username) {

        return username.length() <= maxLen && username.length() >= minLen;
    }

    public int[] getCount(String userInfo) {
        int charCount = 0;
        int numCount = 0;
        int otherCount = 0;

        for (int i = 0; i < userInfo.length(); i++) {
            char c = userInfo.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                charCount++;
            } else if (c >= '0' && c <= '9') {
                numCount++;
            } else {
                otherCount++;
            }
        }
        return new int[]{charCount, numCount, otherCount};
    }

    public boolean checkUsername(String username) {

        int[] count = getCount(username);


        return count[0] > 0 &&  count[2] == 0;
    }

    public boolean checkPassWord(String username) {

        int[] arr = getCount(username);


        return arr[0] > 0 &&  arr[1] > 0 &&  arr[2] == 0;
    }

    public boolean contains(ArrayList<User>list,String username){
        for (int i = 0; i < list.size(); i++) {
            User u =list.get(i);
            if (u.getUsername().equals(username)){
                return  true;
            }
        }
        return false;
    }

    public int findIndex(ArrayList<User>list,String username){
        for (int i = 0; i < list.size(); i++) {
            User u =list.get(i);
            if (u.getUsername().equals(username)){
                return  i;
            }
        }
        return -1;
}

    public static String getCode(){
//        #### 2.3 验证码规则：
//         长度为5
//         由4位大写或者小写字母和1位数字组成，同一个字母可重复
//         数字可以出现在任意位置
//         比如：aQa1K
        ArrayList<Character>list =new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char)('a'+i));
            list.add((char)('A'+i));
        }

        StringBuilder sb =new StringBuilder();
        Random r =new Random();
        for (int i = 0; i < 4; i++) {
            int index = r.nextInt(list.size());
            Character c = list.get(index);
            sb.append(c);
        }

        sb.append(r.nextInt(10));
        char[]arr = sb.toString().toCharArray();
        int i =r.nextInt(arr.length);

        char temp =arr[i];
        arr[i] =arr[arr.length-1];
        arr[arr.length-1]= temp;

        String code =new String(arr);


        return code;
    }
}
