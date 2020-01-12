package top.potens.core.util;

import com.google.common.base.Function;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className CollectionUtil
 * @projectName web-api
 * @date 2019/11/5 18:07
 */
public class CollectionUtil {
    /**
    *
    * 方法功能描述: 以 referenceList的顺序为参照 对dataList进行排序
    *
    * @author yanshaowen
    * @date 2019/11/5 18:55
    * @param dataList
    * @param referenceList
    * @param keyGetFunction
    * @return
    * @throws
    */
    public static <T> void referenceSort(List<T> dataList, List<Integer> referenceList, Function<T, Integer> keyGetFunction) {
        HashMap<Integer, Integer> map = new HashMap<>(16);
        int i = 0;
        for (Integer id : referenceList) {
            map.put(id, i++);
        }
        dataList.sort(new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                Integer id1 = keyGetFunction.apply(o1);
                Integer id2 = keyGetFunction.apply(o2);
                if (map.containsKey(id1) && map.containsKey(id2)) {
                    return map.get(id1).compareTo(map.get(id2));
                }
                return 0;
            }
        });
    }
}
