package main.spring.database.multDataSource;

public class DBContextHolder{
    public static final String zw6 = "zw6";
    public static final String zw3 = "zw3";
    public static final String zw2 = "zw2";
    
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    
    public static void setDBType(String dbType) {
        contextHolder.set(dbType);
    }
    
    public static String getDBType() {
        return contextHolder.get();
    }
    
    public static void clearDBType() {
        contextHolder.remove();
    }
}
