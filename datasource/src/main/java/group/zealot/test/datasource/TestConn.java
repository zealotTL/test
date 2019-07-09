package group.zealot.test.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;
import group.zealot.test.datasource.thread.MyThreadPoolManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class TestConn {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    DruidDataSource druidDataSource;
    @Autowired
    HikariDataSource hikariDataSource;

    private void init(int i, boolean fg) {
        druidDataSource.setMinIdle(i);
        druidDataSource.setMaxActive(i);
        druidDataSource.setInitialSize(i);
        druidDataSource.setTestOnBorrow(fg);//获取连接前测试
        druidDataSource.setMaxWait(5000);

        hikariDataSource.setMinimumIdle(i);
        hikariDataSource.setMaximumPoolSize(i);
        hikariDataSource.setConnectionTimeout(5000);
    }

    @Test
    public void go() {
        int poolSiz = 1;//数据库连接数
        int threadSize = 100;//并发线程数
        int count = 100 * 10000;//请求数量
        boolean fg = false;//获取连接前测试
        init(poolSiz, fg);
        connTime(poolSiz, threadSize, count);
    }

    public void connTime(int poolSiz, int threadSize, int count) {
        logger.info("connTime poolSiz:" + poolSiz + " threadSize:" + threadSize + " count:" + count);
        logger.info("DruidDataSource");
        MyThreadPoolManager pool = new MyThreadPoolManager(threadSize);
        deal(getConnection(druidDataSource, count, pool), false);
        logger.info("HikariDataSource");
        deal(getConnection(hikariDataSource, count, pool), false);
    }


    /**
     * 循环获取连接
     *
     * @return LinkedList<Long> 储存每次调用时长毫秒
     */
    private LinkedList<Long> getConnection(DataSource dataSource, int count) {
        LinkedList<Long> list = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            Long l = testConnection(dataSource);
            if (l != null) {
                list.add(l);
            }
        }
        return list;
    }

    /**
     * 多线程并发
     *
     * @return LinkedList<Long> 储存每次调用时长毫秒
     */
    private LinkedList<Long> getConnection(DataSource dataSource, int count, MyThreadPoolManager pool) {
        LinkedList<Long> list = new LinkedList<>();
        List<Future<Long>> futureList = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            futureList.add(pool.submit(() -> {
                return testConnection(dataSource);
            }));
        }
        for (Future<Long> future : futureList) {
            Long l = null;
            try {
                l = future.get();
            } catch (Exception e) {
                logger.warn("Future", e);
            }
            if (l != null) {
                list.add(l);
            }
        }
        return list;
    }


    private void deal(LinkedList<Long> list, boolean showAllInfo) {
        logger.info("成功getConnection " + list.size() + " 次");
        logger.info("max " + format(getMax(list)));
        logger.info("min " + format(getMin(list)));
        logger.info("ave " + format(getAve(list)));
        if (showAllInfo) {
            logger.info("详细信息如下");
            for (Long d : list) {
                logger.info(" " + format(d));
            }
        }
    }

    private Long getAve(LinkedList<Long> list) {
        long sum = 0;
        for (Long l : list) {
            sum += l;
        }
        return sum / list.size();
    }

    private Long getMax(LinkedList<Long> list) {
        Long max = Long.MIN_VALUE;
        for (Long l : list) {
            if (max < l) {
                max = l;
            }
        }
        return max;
    }

    private Long getMin(LinkedList<Long> list) {
        Long min = Long.MAX_VALUE;
        for (Long l : list) {
            if (min > l) {
                min = l;
            }
        }
        return min;
    }

    private Long testConnection(DataSource dataSource) {
        LocalTime start = LocalTime.now();
        try (Connection connection = getConnection(dataSource)) {
            LocalTime end = LocalTime.now();
            long d = end.toNanoOfDay() - start.toNanoOfDay();
//            query(connection);
            return d;
        } catch (SQLException e) {
//            logger.warn("SQLException", e);
            return null;
        }
    }

    private Connection getConnection(DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }

    private void query(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeQuery("select 1");
    }


    public String format(long data) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(data);
    }

}
