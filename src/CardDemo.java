import java.util.*;

//扑克牌类
class Card {
    public String suit;//花色
    public String rank;//点数

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "(" + suit + " " + rank +  ")";
    }
}


public class CardDemo {
    public static void main(String[] args) {
        //创建一副牌
        System.out.println("创建一副牌");
        List<Card> poker = newPoker();

        //洗牌 (这里使用Collection类的shuffle方法来进行随机洗牌)
        Collections.shuffle(poker);
        System.out.println(poker);

        //创建玩家数组列表
        List<List<Card>> players = new ArrayList<>();
        //新增两个数组列表 表示两个玩家的两副牌
        players.add(new ArrayList<Card>());
        players.add(new ArrayList<Card>());

        //依次发牌
        //将刚才重新洗好的牌的最上面开始交替给玩家发牌
        for (int cardIndex = 0; cardIndex < 5; cardIndex++) {
            for (int playerIndex = 0; playerIndex < 2; playerIndex++) {
                List<Card> playerCards = players.get(playerIndex);//先得到一个玩家数组
                Card curCard = poker.remove(0);//拿到这副牌最上面的一张
                playerCards.add(curCard);//将拿到的牌给到玩家手中
            }
        }

        //新建两个玩家的点数表和花色表
        int[] RankOfPlayer1 = getRankArray(players.get(0));
        int[] SuitOfPlayer1 = getSuitArray(players.get(0));
        int[] RankOfPlayer2 = getRankArray(players.get(1));
        int[] SuitOfPlayer2 = getSuitArray(players.get(1));

        int typeOfPlayer1 = pokerType(RankOfPlayer1, SuitOfPlayer1);
        int typeOfPlayer2 = pokerType(RankOfPlayer2, SuitOfPlayer2);

        //展示玩家1被发到的扑克牌及牌型
        System.out.println("玩家1：");
        System.out.println(players.get(0));
        printType(typeOfPlayer1);
        //展示玩家2被发到的不可拍及牌型
        System.out.println("玩家2：");
        System.out.println(players.get(1));
        printType(typeOfPlayer2);
    }

    //无对 Zilch 不能排成以上组合的牌，以点数决定大小。
    private static final int Zilch = 1;
    //一对One Pair  两张相同点数的牌。
    private static final int OnePair = 2;
    //两对 Two Pairs  两张相同点数的牌，加另外两张相同点数的牌
    private static final int TwoPairs = 3;
    //三条 ThreeOfAKind  有三张同一点数的牌。
    private static final int ThreeOfAKind = 4;
    //顺子 Straight  五张顺连的牌。
    private static final int Straight = 5;
    //同花 Flush  五张同一花色的牌。
    private static final int Flush = 6;
    //满堂红 Fullhouse  三张同一点数的牌，加一对其他点数的牌
    private static final int FullHouse = 7;
    //四条 FourOfAKind  有四张同一点数的牌。
    private static final int FourOfAKind = 8;
    //同花顺 Straight Flush  同一花色，顺序的牌。
    private static final int straightFlush = 9;
    //同花大顺 Royal Flush 最高为Ace（一点）的同花顺
    private static final int royalFlush = 10;

    //黑桃>红桃>草花>方块
    private static final String[] SUITS = {"♠", "♥", "♣", "♦"};

    //新建一副牌
    private static List<Card> newPoker() {
        List<Card> poker = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 2; j <= 10; j++) {
                poker.add(new Card(SUITS[i],j + ""));
            }
            poker.add(new Card(SUITS[i] , "J"));
            poker.add(new Card(SUITS[i] , "Q"));
            poker.add(new Card(SUITS[i] , "K"));
            poker.add(new Card(SUITS[i] , "A"));
        }
        return poker;
    }

    //根据数字打印牌型
    private static void printType(int key) {
        if (key == 1) {
            System.out.println("无对儿");
        } else if (key == 2) {
            System.out.println("一对儿");
        } else if (key == 3) {
            System.out.println("两对儿");
        } else if (key == 4) {
            System.out.println("三条儿");
        } else if (key == 5) {
            System.out.println("顺子");
        } else if (key == 6) {
            System.out.println("同花");
        } else if (key == 7) {
            System.out.println("满堂红");
        } else if (key == 8) {
            System.out.println("四条");
        } else if (key == 9) {
            System.out.println("同花顺");
        } else if (key == 10) {
            System.out.println("同花大顺");
        } else {
            System.out.println("无牌型");
        }
    }

    //花色用数字表示以便比较  "♥"  1 , "♠"   2  , "♣"  3  , "♦"  4
    private static int getSuit(String str) {
        //黑桃>红桃>草花>方块  4  3  2  1
        if ("♦".equals(str)) {
            return 1;
        } else if ("♣".equals(str)) {
            return 2;
        } else if ("♥".equals(str)) {
            return 3;
        } else if ("♠".equals(str)) {
            return 4;
        } else {
            return 0;
        }
    }

    //点数都用数字表示以便比较
    private static int getRank(String str) {
        for (int i = 2; i <= 10; i++) {
            if (("" + i).equals(str)) {
                return i;
            }
        }
        if ("A".equals(str)) {
            return 14;
        } else if ("J".equals(str)) {
            return 11;
        } else if ("Q".equals(str)) {
            return 12;
        } else if ("K".equals(str)) {
            return 13;
        } else {
            return 0;
        }
    }

    //创建一个玩家的点数数组
    //其中 0 和 1 下标不存放元素，A 存放在 14 下标位置
    private static int[] getRankArray(List<Card> card) {
        int[] rankArray = new int[15];
        for (int i = 0; i < 5; i++) {
            int tmp = getRank(card.get(i).rank);
            rankArray[tmp]++;
        }
        return rankArray;
    }

    //创建一个玩家的花色数组
    private static int[] getSuitArray(List<Card> card) {
        int[] suitArray = new int[5];
        for (int i = 0; i < 5; i++) {
            int tmp = getSuit(card.get(i).suit);
            suitArray[tmp]++;
        }
        return suitArray;
    }

    //判断玩家手中是哪副牌，并返回牌型相对应的静态常量值
    private static int pokerType(int[] rankArray, int[] suitArray) {
        //创建两个 ArrayList 用来保存扑克牌的花色数量和点数数量
        //同时利用 size 和 contains 功能来对牌型进行判断
        List<Integer> aRank = new ArrayList<>();
        List<Integer> aSuit = new ArrayList<>();
        if (rankArray[14] > 0) {
            //如果A存在，则让第一个下标元素也存A，方便后面的排序比较
            rankArray[1] = rankArray[14];
        }
        for (int i = 1; i <= 13; i++) {
            if (rankArray[i] != 0) {
                aRank.add(rankArray[i]);
            }
        }
        for (int i = 1; i <= 4; i++) {
            if (suitArray[i] != 0) {
                aSuit.add(suitArray[i]);
            }
        }

        //五张牌中只有两种点数
        if (aRank.size() == 2) {
            if (aRank.contains(4)) {
                //四条：有四张同一点数的牌 返回8
                return FourOfAKind;
            } else {
                //满堂红：三张同一点数的牌，加一对其他点数的牌 返回7
                return FullHouse;
            }
        } else if (aRank.size() == 3) {
            //五张牌中只有三种点数
            if (aRank.contains(3)) {
                //三条 有三张同一点数的牌 返回4
                return ThreeOfAKind;
            } else {
                //两对 两张相同点数的牌，加另外两张相同点数的牌 返回3
                return TwoPairs;
            }
        } else if (aRank.size() == 4) {
            //五张牌有四种点数
            //一对 One Pair 两张相同点数的牌。
            return OnePair;
        } else if (aRank.size() == 5) {
            //五张牌中有五种点数
            int aSort = 0;//记录连对张数
            //从2号下标开始到13号下标，比较是否连续，2号下标元素为A
            for (int i = 2; i < rankArray.length - 1; i++) {
                if (rankArray[i - 1] == 1 && rankArray[i] == 1) {
                    aSort++;
                }
            }
            if (aSort == 4 && aSuit.contains(5)) {
                //同花顺  同一花色，顺序的牌。
                return straightFlush;
            } else if (aSort == 4 && aSuit.contains(5)
                    && rankArray[1] == 1 && rankArray[13] == 1) {
                //同花大顺  10 J Q K A
                return royalFlush;
            } else if (aSort == 4){
                //顺子  五张顺连的牌
                return Straight;
            } else {
                //无对 Zilch 不能排成以上组合的牌，以点数决定大小。
                return Zilch;
            }
        }
        return 0;
    }
}
