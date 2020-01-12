package top.potens.core.util;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import net.sf.cglib.beans.BeanCopier;

import java.util.List;
import java.util.WeakHashMap;

/**
 * Bean浅拷贝
 */
public class BeanCopierUtil {

    private static WeakHashMap<String, BeanCopier> container = new WeakHashMap<>();

    private static Interner interner = Interners.newWeakInterner();

    /**
     * @param source
     * @param target
     * @return
     */
    private static BeanCopier getBeanCopier(Class source, Class target) {
        String key = source.getName() +
                "_" +
                target.getName();
        BeanCopier copier = container.get(key);
        if (copier != null) {
            return copier;
        }
        synchronized (interner.intern(key)) {
            copier = container.get(key);
            if (copier != null) {
                return copier;
            }
            BeanCopier beanCopier = BeanCopier.create(source, target, false);
            container.put(key, beanCopier);
            return beanCopier;
        }
    }

    /**
     * Bean 拷贝对象
     *
     * @param source
     * @param targetClass
     * @return
     */
    public static <T> T convert(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        BeanCopier beanCopier = getBeanCopier(source.getClass(), targetClass);
        try {
            T target = targetClass.newInstance();
            beanCopier.copy(source, target, null);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("对象拷贝失败" + source + "_" + targetClass);
        }
    }
    public static void convert(Object source, Object target) {
        if (source == null) {
            return;
        }
        BeanCopier beanCopier = getBeanCopier(source.getClass(), target.getClass());
        try {
            beanCopier.copy(source, target, null);

        } catch (Exception e) {
            throw new RuntimeException("对象拷贝失败" + source + "_" + target.getClass());
        }
    }

    /**
     * 集合类的转换
     *
     * @param source
     * @param targetClass
     * @return
     */
    public static <E> List<E> convert(List source, Class<E> targetClass) {
        if (source == null) {
            return null;
        }
        try {
            if (source.isEmpty()) {
                return source.getClass().newInstance();
            }
            List<E> result = source.getClass().newInstance();

            for (Object each : source) {
                result.add(convert(each, targetClass));
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("对象拷贝失败" + source + "_" + targetClass);
        }
    }

    /**
     * 集合类的转换
     *
     * @param source
     * @param target
     * @param targetClass
     * @return
     */
    public static <E> void convert(List source, List<E> target, Class<E> targetClass) {
        if (source == null) {
            return;
        }
        try {
            if (source.isEmpty()) {
                return;
            }
            for (Object each : source) {
                target.add(convert(each, targetClass));
            }
        } catch (Exception e) {
            throw new RuntimeException("对象拷贝失败" + source + "_" + targetClass);
        }
    }

}

