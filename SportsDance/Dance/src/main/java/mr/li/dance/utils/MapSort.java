package mr.li.dance.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/2 0002
 * 描述:
 * 修订历史:
 */
public class MapSort {
    /**
     *
     * @Title: sortMap
     * @Description: 对集合内的数据按key的字母顺序做排序
     */
    public List<Map.Entry<String, String>> sortMap(final Map<String, String> map) {
        final List<Map.Entry<String, String>> infos = new ArrayList<Map.Entry<String, String>>(map.entrySet());

        // 重写集合的排序方法：按字母顺序
        Collections.sort(infos, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(final Map.Entry<String, String> o1, final Map.Entry<String, String> o2) {
                return (o1.getKey().toString().compareTo(o2.getKey()));
            }

        });

        return infos;
    }
}
