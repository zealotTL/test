package name.taolei.zealot.test.druid;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MyDataSource extends AbstractRoutingDataSource {
    public static final String datasource1 = "datasource1";
    public static final String datasource2 = "datasource2";
    
    private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();
    
    public static void setDataSourceKey(String dataSource) {
        dataSourceKey.set(dataSource);
    }
    
    @Override
    protected Object determineCurrentLookupKey() {
        // TODO Auto-generated method stub
        return dataSourceKey.get();
    }
}
