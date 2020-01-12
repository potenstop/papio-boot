package top.potens.core.plugin;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.math.BigDecimal;
import java.sql.Types;

/**
 * Created by wenshao on 2019/6/16.
 */
public class MyFullyQualifiesJavaTypePlugin extends JavaTypeResolverDefaultImpl {
    public MyFullyQualifiesJavaTypePlugin() {
        super();
        super.typeMap.put(Types.BIT, new JdbcTypeInformation("BIT",new FullyQualifiedJavaType(Integer.class.getName())));
        super.typeMap.put(Types.SMALLINT, new JdbcTypeInformation("SMALLINT",new FullyQualifiedJavaType(Integer.class.getName())));
        super.typeMap.put(Types.TINYINT, new JdbcTypeInformation("TINYINT",new FullyQualifiedJavaType(Integer.class.getName())));

        super.typeMap.put(Types.DOUBLE, new JdbcTypeInformation("DOUBLE",new FullyQualifiedJavaType(BigDecimal.class.getName())));
        super.typeMap.put(Types.FLOAT, new JdbcTypeInformation("FLOAT",new FullyQualifiedJavaType(BigDecimal.class.getName())));
        super.typeMap.put(Types.DECIMAL, new JdbcTypeInformation("DECIMAL",new FullyQualifiedJavaType(BigDecimal.class.getName())));
        super.typeMap.put(Types.BIGINT, new JdbcTypeInformation("BIGINT",new FullyQualifiedJavaType(BigDecimal.class.getName())));
    }
}
