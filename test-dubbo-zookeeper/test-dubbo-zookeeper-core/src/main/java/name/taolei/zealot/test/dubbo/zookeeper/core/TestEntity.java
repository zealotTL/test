package name.taolei.zealot.test.dubbo.zookeeper.core;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TestEntity implements Serializable{
    private Integer id;
    private String name;
}
