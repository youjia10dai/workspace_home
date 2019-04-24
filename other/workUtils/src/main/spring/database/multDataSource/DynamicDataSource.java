package main.spring.database.multDataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource{
    
    public final Logger log = Logger.getLogger(this.getClass());
    
    @Override
    protected Object determineCurrentLookupKey() {
        /*log.info(DBContextHolder.getDBType());*/
        //从DBContextHolder中获取当前数据源
        return DBContextHolder.getDBType();
    }
}