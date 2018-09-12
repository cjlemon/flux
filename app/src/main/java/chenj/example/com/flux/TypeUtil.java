package chenj.example.com.flux;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author chenj
 * @date 2018/1/25 0025
 */

public class TypeUtil {
    public static <T> T getType(int index, Object obj){
        if (obj == null){
            throw new NullPointerException("获取泛型的对象不能为空");
        }
        Type superclass = obj.getClass().getGenericSuperclass();
        if (superclass instanceof ParameterizedType){
            Type[] types = ((ParameterizedType) superclass).getActualTypeArguments();
            Type targetType = types[index];
            try {
                return ((Class<T>) targetType).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassCastException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Type getClassType(int index, Object obj){
        if (obj == null){
            throw new NullPointerException("获取泛型的对象不能为空");
        }
        Type superclass = obj.getClass().getGenericSuperclass();
        if (superclass instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) superclass).getActualTypeArguments();
            return types[index];
        }
        return null;
    }
}
