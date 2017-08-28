package mr.li.dance.ui.activitys.music;

import java.util.Random;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/22 0022
 * 描述:  生成随机数
 * 修订历史:
 */
public class PlayerUtiles {
    //产生一个与之前不同的随机数 如果随机范围小于2 则返回before
    public static int random(int randomScope, int before) {
        Random r = new Random();
        //防止随机到刚刚用过的数字
        if (randomScope < 2 || before < 0) {
            return before;
        } else {
            while (true) {
                int temp = r.nextInt(randomScope);
                if (temp != before) {
                    return temp;
                }
            }
        }
    }
}
