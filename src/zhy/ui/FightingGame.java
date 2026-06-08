package zhy.ui;

import zhy.domain.EnemyCharacter;
import zhy.domain.HeroCharacter;

import java.util.ArrayList;
import java.util.Random;
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

        int count = 1;
        int wins =0;
        while (player.isAlive()){

            if (wins != 0){
                for (int i = 0; i < enemyList.size(); i++) {
                    EnemyCharacter c = enemyList.get(i);
                    c.maxHP =c.maxHP+10;
                    c.HP = c.maxHP;
                    c.attack =c.attack +3;
                    c.defense =c.defense+2;
                    c.defending =false;
                }
            }


            Random r =new Random();
            int index = r.nextInt(enemyList.size());
            EnemyCharacter enemy =enemyList.get(index);
            System.out.println(enemy.show());


            System.out.println("═══════════════════════════════════════");
            System.out.println("⚔\uFE0F 第 "+count+"场战斗开始！对手: "+enemy.name);
            int round = 1;
            while (player.isAlive()){
                System.out.println("---------------------------------------");
                System.out.println("⚔\uFE0F 第 "+round+"回合开始！");

                System.out.println(getHealthBar(player.name, player.HP, player.maxHP));
                System.out.println(getHealthBar(enemy.name, enemy.HP, enemy.maxHP));

                playerTurn(player,enemy);

                if (!enemy.isAlive()){
                    System.out.println("\uD83C\uDF89 你击败了 "+enemy.name+" !");
                    wins++;
                    break;
                }

                enemyTurn(enemy,player);

                if (!player.isAlive()){
                    System.out.println("你被"+enemy.name+"击败了...");
                    break;
                }
                round++;


            }


            if (player.isAlive()){
                int healHP = r.nextInt(20, 41);
                player.heal(healHP);
                System.out.println("\uD83D\uDC9A 战斗结束！你恢复了 "+healHP+" 点生命值");
                System.out.println("\uD83C\uDFC6 当前胜场: "+ wins);
                System.out.println("═══════════════════════════════════════");
            }

            if (player.isAlive() && wins > 0 && wins%3 ==0){
                System.out.println("\uD83C\uDF1F恭喜！你获得了属性提升！");
                player.maxHP += 30;
                player.attack += 5;
                player.defense += 3;

                System.out.println("当前的最大生命值 +30 ，攻击力 +5 ，防御力 +3");
                System.out.println("当前的属性为："+player.show());
            }

            if (player.isAlive()){
                System.out.println("继续下一场战斗？（y/n）");
                Scanner sc  = new Scanner(System.in);
                String choose = sc.next();
                if ("y".equalsIgnoreCase(choose)){
                    count++;
                    continue;
                }else if ("n".equalsIgnoreCase(choose)){
                    break;
                }else {
                    System.out.println("没有这个选项，默认游戏继续！");
                    continue;
                }
            }



        }


        System.out.println("════════════════════════════════════");
        System.out.println("游戏结束！");
        System.out.println("总胜场为："+wins);
        System.out.println("感谢游玩文字游戏格斗版！");

        System.exit(0);
    }

    private void enemyTurn(EnemyCharacter enemy, HeroCharacter player) {
        System.out.println("====="+enemy.name +"的回合=====");

        String action ="普通攻击";
        Random r =new Random();
        int num = r.nextInt(9);
        if (num >=4){
            action =enemy.skill;
        }

        switch (action){
            case "普通攻击":
                System.out.println("敌人采取了普通攻击");
                int damage1 = caculateDamage(enemy.attack, player.defense);
                System.out.println("⚔\uFE0F "+enemy.name+ " 对你使用了普通攻击，造成 "+damage1+" 点伤害！");
                player.takeDamage(damage1);
                break;


            case "猛击":
                System.out.println("当前战士采取了猛击");
                int damage2 = caculateDamage((int) (enemy.attack * 1.5), player.defense);
                System.out.println("\uD83D\uDCA5 "+enemy.name+ " 对你使用了猛击，造成 "+damage2+" 点伤害！");
                break;


            case "快速攻击":
                System.out.println("当前刺客采取了快速攻击");
                int damage3 =0;
                for (int i = 0; i < 2; i++) {
                    int temp =caculateDamage(enemy.attack*2, player.defense);
                    damage3 =damage3+temp;
                }
                System.out.println("⚔\uFE0F "+enemy.name+ " 对你使用了快速攻击，造成 "+damage3+" 点伤害！");
                player.takeDamage(damage3);
                break;


            case "防御姿态":
                System.out.println("当前坦克采取了防御姿态 buff");
                enemy.defending=true;
                System.out.println("\uD83C\uDF1F"+enemy.name+ " 摆出了防御姿态！");
                break;


            case "火球术":
                System.out.println("当前法师采取了火球术");
                int damage4 =caculateDamage((int) (enemy.attack*1.8), player.defense);
                System.out.println("\uD83D\uDCA5 "+enemy.name+ " 对你使用了火球术，造成 "+damage4+" 点伤害！");
                player.takeDamage(damage4);
                break;
        }
    }

    public String getHealthBar(String name , int Hp ,int maxHP){
        int barLength = 20 ;

        int filled =(int) ((Hp *1.0 / maxHP) *barLength);

        StringBuilder sb =new StringBuilder();
        sb.append(name).append(": [");


        for (int i = 0; i < barLength; i++) {
            if (i <filled){
                sb.append("█");
            }else {
                sb.append(" ");
            }
        }

        sb.append("] ").append(Hp).append("/").append(maxHP).append(" HP");
        return sb.toString();
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



    public void playerTurn(HeroCharacter player,EnemyCharacter enemy){
//    ===== 你的回合 =====
//1. 普通攻击
//2. 强力一击 (消耗10HP)
//3. 生命汲取 (消耗10HP，恢复生命)
//    选择行动 (1-3): 2
        System.out.println("===== 你的回合 =====");
        System.out.println("1. 普通攻击");
        System.out.println("2. 强力一击 (消耗10HP)");
        System.out.println("3. 生命汲取 (消耗10HP，恢复生命)");
        System.out.println("选择行动 (1-3): ");
        Scanner sc =new Scanner(System.in);
        String choose = sc.next();
        switch (choose){
            default:
                System.out.println("没有这个操作，默认使用普通攻击");

                case "1" :
                int damage1 = caculateDamage(player.attack, enemy.defense);
                System.out.println("⚔\uFE0F你对"+enemy.name+" 使用了普通攻击，造成"+damage1+"点伤害！");
                enemy.takeDamage(damage1);
                break;


                case "2":
                if (player.HP>10){
                    player.takeDamage(10);
                    int damage2 = caculateDamage((int) (player.attack * 1.8), enemy.defense);
                    System.out.println("\uD83D\uDCA5 消耗10HP，你对"+enemy.name+" 使用了强力一击，造成"+damage2+"点伤害！");
                    enemy.takeDamage(damage2);

                }else {
                    System.out.println("体力不足，攻击失败！");
                }
                break;


                case "3":
                if (player.HP>10){
                    player.takeDamage(10);
                    Random r =new Random();
                    int healHP = r.nextInt(21);
                    player.heal(healHP);

                    System.out.println("\uD83D\uDC9A 消耗10HP，你使用了生命汲取，恢复了"+healHP+"点生命值！");

                }else {
                    System.out.println("体力不足，生命恢复失败！");
                }


                break;
        }


    }



    public int caculateDamage(int attack,int defense){
        int damage = attack -defense;
        if (damage < 1){
            damage =1;
        }
        return damage;
    }
}
