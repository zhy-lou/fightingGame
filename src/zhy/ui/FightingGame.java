package zhy.ui;

import zhy.domain.EnemyCharacter;
import zhy.domain.HeroCharacter;

import java.util.ArrayList;
import java.util.Scanner;

public class FightingGame {
    public void gameStart(String username) {
        //1. 标题
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("   🎮"+username+"欢迎来到文字格斗游戏 🎮   ");
        System.out.println("╚════════════════════════════════════════╝");

        //2. 创建玩家角色（名称+属性分配）
        HeroCharacter player = creatPlayerCharacter(username);
        //3. 创建成功，展示玩家的所有属性和技能列表
        System.out.println("角色创建成功！");
        System.out.println("\uD83C\uDF1F初始的属性："+player.show());
        System.out.println("\uD83C\uDF1F拥有的机能："+player.showSkill());

        ArrayList<EnemyCharacter>enemyList =new ArrayList<>();
        enemyList.add(new EnemyCharacter("初级战士",80,15,10,"猛击"));
        enemyList.add(new EnemyCharacter("敏捷刺客",60,20,5,"快速攻击"));
        enemyList.add(new EnemyCharacter("重装坦克",120,10,20,"防御姿态"));
        enemyList.add(new EnemyCharacter("神秘法师",70,25,8,"火球术"));

    }

    public HeroCharacter creatPlayerCharacter(String username){
        System.out.println("创建您的角色：");
        System.out.println("您的角色名为：" + username);

        int points =20;
        System.out.println("请分配属性点（共20点）");
        System.out.println("1.生命值 (每点+10 HP)");
        System.out.println("2.攻击力 (每点+2 ATK)");
        System.out.println("3.防御力 (每点+1 DEF)");

        Scanner sc =new Scanner(System.in);

        String[]attributes={"生命值", "攻击力","防御力"};

        int[]values =new int[3];

        for (int i = 0; i < attributes.length; i++) {
            System.out.println("分配点数到"+attributes[i]+"剩余点数："+points+"):0");

            int input =sc.nextInt();
            if (input < 0){
                System.out.println("无效输入，默认分配0点！");
                input = 0 ;
            }

            if (input>points){
                System.out.println("属性点不足！剩余属性点全部分配到"+attributes[i]);
                input = points;
            }
            points =points -input;
            values[i] =input;
        }

        HeroCharacter player =new HeroCharacter(username,100+values[0]*10,
                10+values[1]*2,0 + values[2]*1);

        player.skillList.add("普通攻击");
        player.skillList.add("强力一击");
        player.skillList.add("生命获取");


        return player;
    }
}
