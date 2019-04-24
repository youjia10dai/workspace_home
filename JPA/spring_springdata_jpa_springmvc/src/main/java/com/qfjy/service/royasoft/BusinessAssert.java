package com.qfjy.service.royasoft;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public final class BusinessAssert {
    /**
     * 榛樿鏋勯�犲嚱鏁�
     */
    private BusinessAssert() {}

    /**
     * 瀵逛竴涓猙oolean琛ㄨ揪寮忚繘琛屾柇瑷�,濡傛灉琛ㄨ揪寮忕殑鍊间负false鍒欐姏鍑築usinessException
     * 
     * @param expression 琛ㄨ揪寮�
     * @param code 閿欒鐮�
     */
    public static void isTrue(boolean expression, String code) {
        String message = "[Assertion failed] - this expression must be true!";
        isTrue(expression, code, message);
    }

    /**
     * 瀵逛竴涓猙oolean琛ㄨ揪寮忚繘琛屾柇瑷�,濡傛灉琛ㄨ揪寮忕殑鍊间负false鍒欐姏鍑築usinessException
     * 
     * @param expression 琛ㄨ揪寮�
     * @param code 閿欒鐮�
     * @param message 閿欒淇℃伅鎻忚堪
     */
    public static void isTrue(boolean expression, String code, String message) {
        if (!expression) {
            throw new BusinessException(code, message);
        }
    }

    /**
     * 濡傛灉object涓嶄负null鍒欐姏鍑築usinessException
     * 
     * @param code 閿欒鐮�
     * @param object 瀵硅薄
     */
    public static void isNull(Object object, String code) {
        String message = "[Assertion failed] - the object argument must be null!";
        isNull(object, code, message);
    }

    /**
     * 濡傛灉object涓嶄负null鍒欐姏鍑築usinessException
     * 
     * @param object Object
     * @param code 閿欒鐮�
     * @param message 閿欒鎻忚堪
     */
    public static void isNull(Object object, String code, String message) {
        if (object != null) {
            throw new BusinessException(code, message);
        }
    }

    /**
     * 濡傛灉object涓簄ull鍒欐姏鍑築usinessException
     * 
     * @param code 閿欒鐮�
     * @param object Object
     */
    public static void notNull(Object object, String code) {
        String message = "[Assertion failed] - this argument is required; it must not be null!";
        notNull(object, code, message);
    }

    /**
     * 濡傛灉object涓簄ull鍒欐姏鍑築usinessException
     * 
     * @param object Object
     * @param code 閿欒鐮�
     * @param message 閿欒鎻忚堪
     */
    public static void notNull(Object object, String code, String message) {
        if (object == null) {
            throw new BusinessException(code, message);
        }
    }

    /**
     * 濡傛灉object涓嶄负绌哄��(null, "", " ", "null", empty collection, empty map, empty
     * array)鍒欐姏鍑築usinessException
     * 
     * @param code 閿欒鐮�
     * @param object Object
     */
    public static void isEmpty(Object object, String code) {
        String message = "[Assertion failed] - this object must be null or empty if it is a array銆乧ollection!";
        isEmpty(object, code, message);
    }

    /**
     * 濡傛灉object涓嶄负绌哄��(null, "", " ", "null", empty collection, empty map, empty
     * array)鍒欐姏鍑築usinessException
     * 
     * @param object Object
     * @param code 閿欒鐮�
     * @param message 閿欒鎻忚堪
     */
    public static void isEmpty(Object object, String code, String message) {
        if (!isEmptyObject(object)) {
            throw new BusinessException(code, message);
        }
    }

    /**
     * 濡傛灉object涓虹┖鍊�(null, "", " ", "null", empty collection, empty map, empty
     * array)鍒欐姏鍑築usinessException
     * 
     * @param code 閿欒鐮�
     * @param object Object
     */
    public static void notEmpty(Object object, String code) {
        String message = "[Assertion failed] - this object must be not null or not empty if it is a array銆乧ollection!";
        notEmpty(object, code, message);
    }

    /**
     * 濡傛灉object涓虹┖鍊�(null, "", " ", "null", empty collection, empty map, empty
     * array)鍒欐姏鍑築usinessException
     * 
     * @param code 閿欒鐮�
     * @param object Object
     * @param message 閿欒鎻忚堪
     */
    public static void notEmpty(Object object, String code, String message) {
        if (isEmptyObject(object)) {
            throw new BusinessException(code, message);
        }
    }

    private static boolean isEmptyObject(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof String) {
            return "".equals(((String) object).trim());
        } else if (object instanceof Collection<?>) {
            return ((Collection<?>) object).isEmpty();
        } else if (object instanceof Map<?, ?>) {
            return ((Map<?, ?>) object).isEmpty();
        } else if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        } else {
            return false;
        }
    }

    /**
     * 濡傛灉鏁扮粍array涓殑鍏冪礌瀛樺湪null鍊�,鍒欐姏鍑築usinessException
     * 
     * @param code 閿欒鐮�
     * @param array Object鏁扮粍
     */
    public static void noNullElements(Object[] array, String code) {
        String message = "[Assertion failed] - this array must not contain any null elements!";
        noNullElements(array, code, message);
    }

    /**
     * 濡傛灉鏁扮粍array涓殑鍏冪礌瀛樺湪null鍊�,鍒欐姏鍑築usinessException
     * 
     * @param array Object鏁扮粍
     * @param code 閿欒鐮�
     * @param message 閿欒鎻忚堪
     */
    public static void noNullElements(Object[] array, String code, String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new BusinessException(code, message);
                }
            }
        }
    }

    /**
     * 濡傛灉闆嗗悎collection涓殑鍏冪礌瀛樺湪null鍊�,鍒欐姏鍑築usinessException
     * 
     * @param code 閿欒鐮�
     * @param collection 闆嗗悎
     */
    public static void noNullElements(Collection<?> collection, String code) {
        String message = "[Assertion failed] - this collection must not contain any null elements!";
        noNullElements(collection, code, message);
    }

    /**
     * 濡傛灉闆嗗悎collection涓殑鍏冪礌瀛樺湪null鍊�,鍒欐姏鍑築usinessException
     * 
     * @param collection 闆嗗悎
     * @param code 閿欒鐮�
     * @param message 閿欒鎻忚堪
     */
    public static void noNullElements(Collection<?> collection, String code, String message) {
        if (collection != null) {
            for (Object element : collection) {
                if (element == null) {
                    throw new BusinessException(code, message);
                }
            }
        }
    }

    /**
     * 濡傛灉闆嗗悎map涓殑鍏冪礌瀛樺湪value=null鍊�,鍒欐姏鍑築usinessException
     * 
     * @param map Map
     * @param code 閿欒鐮�
     */
    public static void noNullValues(Map<?, ?> map, String code) {
        String message = "[Assertion failed] - this map must not contain any null value!";
        noNullValues(map, code, message);
    }

    /**
     * 濡傛灉闆嗗悎map涓殑鍏冪礌瀛樺湪value=null鍊�,鍒欐姏鍑築usinessException
     * 
     * @param map Map
     * @param message 閿欒鎻忚堪
     * @param code 閿欒鐮�
     */
    public static void noNullValues(Map<?, ?> map, String code, String message) {
        if (map != null) {
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if (entry.getValue() == null) {
                    throw new BusinessException(code, message);
                }
            }
        }
    }

    /**
     * 濡傛灉闆嗗悎map涓殑鍏冪礌瀛樺湪key=null鍊�,鍒欐姏鍑築usinessException
     * 
     * @param map Map
     * @param code 閿欒鐮�
     */
    public static void noNullKeys(Map<?, ?> map, String code) {
        String message = "[Assertion failed] - this map must not contain any null key!";
        noNullKeys(map, code, message);
    }

    /**
     * 濡傛灉闆嗗悎map涓殑鍏冪礌瀛樺湪key=null鍊�,鍒欐姏鍑築usinessException
     * 
     * @param map Map
     * @param message 閿欒鎻忚堪
     * @param code 閿欒鐮�
     */
    public static void noNullKeys(Map<?, ?> map, String code, String message) {
        if (map != null) {
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if (entry.getKey() == null) {
                    throw new BusinessException(code, message);
                }
            }
        }
    }

    /**
     * 濡傛灉瀵硅薄obj涓嶆槸鎸囧畾鐨勭被鍨媡ype,鍒欐姏鍑築usinessException
     * 
     * @param type 瀛楄妭鐮�
     * @param code 閿欒鐮�
     * @param obj Object
     */
    public static void isInstanceOf(Class<?> type, Object obj, String code) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            String message = "Object of class [" + (obj != null ? obj.getClass().getName() : "null") + "] must be an instance of " + type;
            throw new BusinessException(code, message);
        }
    }

    /**
     * 濡傛灉瀵硅薄obj涓嶆槸鎸囧畾鐨勭被鍨媡ype,鍒欐姏鍑築usinessException
     * 
     * @param type 瀛楄妭鐮�
     * @param obj Object
     * @param message 閿欒鎻忚堪
     * @param code 閿欒鐮�
     */
    public static void isInstanceOf(Class<?> type, Object obj, String code, String message) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw new BusinessException(code, message);
        }
    }

    /**
     * 濡傛灉subType鐨勭埗绫讳笉鏄痵uperType,鍒欐姏鍑築usinessException
     * 
     * @param superType 鐖跺瓧鑺傜爜瀵硅薄
     * @param subType 瀛楄妭鐮佸璞�
     * @param code 閿欒鐮�
     */
    public static void isAssignable(Class<?> superType, Class<?> subType, String code) {
        notNull(superType, "Type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            String message = subType + " is not assignable to " + superType;
            throw new BusinessException(code, message);
        }
    }

    /**
     * 濡傛灉subType鐨勭埗绫讳笉鏄痵uperType,鍒欐姏鍑築usinessException
     * 
     * @param superType 鐖跺瓧鑺傜爜瀵硅薄
     * @param subType 瀛楄妭鐮佸璞�
     * @param message 閿欒鎻忚堪
     * @param code 閿欒鐮�
     */
    public static void isAssignable(Class<?> superType, Class<?> subType, String code, String message) {
        notNull(superType, "Type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new BusinessException(code, message);
        }
    }
}
