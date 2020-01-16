package top.potens.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className MybatisGenerator
 * @projectName papio-framework
 * @date 2020/1/16 9:50
 */
public class MybatisGenerator {
    private static MybatisGenerator instance = new MybatisGenerator();
    private List<Map<String, String>> tableList = new ArrayList<>();
    private String jdbcConnectionURL = "";
    private String jdbcDriverClass = "com.mysql.jdbc.Driver";
    private String jdbcUserId = null;
    private String jdbcPassword = null;
    private String modelTargetPackage = null;
    private String modelTargetProject = null;
    private String mapTargetPackage = null;
    private String mapTargetProject = null;
    private String clientTargetPackage = null;
    private String clientTargetProject = null;


    private MybatisGenerator() {

    }
    public static MybatisGenerator getInstance(){
        return instance;
    }

    public MybatisGenerator setJdbcConnectionURL(String jdbcConnectionURL) {
        this.jdbcConnectionURL = jdbcConnectionURL;
        return this;
    }

    public MybatisGenerator setJdbcDriverClass(String jdbcDriverClass) {
        this.jdbcDriverClass = jdbcDriverClass;
        return this;
    }

    public MybatisGenerator setJdbcUserId(String jdbcUserId) {
        this.jdbcUserId = jdbcUserId;
        return this;
    }

    public MybatisGenerator setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
        return this;
    }

    public MybatisGenerator setModelTargetPackage(String modelTargetPackage) {
        this.modelTargetPackage = modelTargetPackage;
        return this;
    }

    public MybatisGenerator setModelTargetProject(String modelTargetProject) {
        this.modelTargetProject = modelTargetProject;
        return this;
    }

    public MybatisGenerator setMapTargetPackage(String mapTargetPackage) {
        this.mapTargetPackage = mapTargetPackage;
        return this;
    }

    public MybatisGenerator setMapTargetProject(String mapTargetProject) {
        this.mapTargetProject = mapTargetProject;
        return this;
    }

    public MybatisGenerator setClientTargetPackage(String clientTargetPackage) {
        this.clientTargetPackage = clientTargetPackage;
        return this;
    }

    public MybatisGenerator setClientTargetProject(String clientTargetProject) {
        this.clientTargetProject = clientTargetProject;
        return this;
    }

    public String getJdbcConnectionURL() {
        return jdbcConnectionURL;
    }

    public String getJdbcDriverClass() {
        return jdbcDriverClass;
    }

    public String getJdbcUserId() {
        return jdbcUserId;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public String getModelTargetPackage() {
        return modelTargetPackage;
    }

    public String getModelTargetProject() {
        return modelTargetProject;
    }

    public String getMapTargetPackage() {
        return mapTargetPackage;
    }

    public String getMapTargetProject() {
        return mapTargetProject;
    }

    public String getClientTargetPackage() {
        return clientTargetPackage;
    }

    public String getClientTargetProject() {
        return clientTargetProject;
    }

    public MybatisGenerator addTable (String tableName, String primaryKeyName) {
        Map<String, String> map = new HashMap<>();
        map.put("tableName", tableName);
        map.put("primaryKeyName", primaryKeyName);
        tableList.add(map);
        return this;
    }
    public Configuration getConfig() {
        Configuration configuration = new Configuration();

        Context context = new Context(null);
        context.setId("Tables");
        context.setTargetRuntime("MyBatis3");
        context.addProperty("autoDelimitKeywords", "true");
        context.addProperty("beginningDelimiter", "`");
        context.addProperty("endingDelimiter", "`");
        PluginConfiguration mergepluginConfiguration = new PluginConfiguration();
        mergepluginConfiguration.setConfigurationType("top.potens.generator.OverIsMergeAblePlugin");
        context.addPluginConfiguration(mergepluginConfiguration);

        PluginConfiguration serializablePluginConfiguration = new PluginConfiguration();
        serializablePluginConfiguration.setConfigurationType("org.mybatis.generator.plugins.SerializablePlugin");
        context.addPluginConfiguration(serializablePluginConfiguration);

        CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
        commentGeneratorConfiguration.addProperty("suppressDate", "true");
        commentGeneratorConfiguration.addProperty("suppressAllComments", "true");
        commentGeneratorConfiguration.addProperty("addRemarkComments", "true");
        context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);

        // jdbc驱动
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(this.getJdbcConnectionURL());
        jdbcConnectionConfiguration.setDriverClass(this.getJdbcDriverClass());
        jdbcConnectionConfiguration.setUserId(this.getJdbcUserId());
        jdbcConnectionConfiguration.setPassword(this.getJdbcPassword());
        // 设置为 true 可以获取 tables 信息, 解决生成文件缺少 xxxByPrimaryKey 的问题
        jdbcConnectionConfiguration.addProperty("useInformationSchema", "true");
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        // 类型转换
        JavaTypeResolverConfiguration javaTypeResolverConfiguration = new JavaTypeResolverConfiguration();
        javaTypeResolverConfiguration.setConfigurationType("top.potens.generator.MyFullyQualifiesJavaTypePlugin");
        javaTypeResolverConfiguration.addProperty("forceBigDecimals", "true");
        context.setJavaTypeResolverConfiguration(javaTypeResolverConfiguration);

        // 自动生成model 必须存在的文件夹，不然无法生成
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetPackage(this.getModelTargetPackage());
        javaModelGeneratorConfiguration.setTargetProject(this.getModelTargetProject());
        javaModelGeneratorConfiguration.addProperty("enableSubPackages", "true");
        javaModelGeneratorConfiguration.addProperty("trimStrings", "true");
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        // 自动生成mapper 必须存在的文件夹，不然无法生成
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetPackage(this.getMapTargetPackage());
        sqlMapGeneratorConfiguration.setTargetProject(this.getMapTargetProject());
        sqlMapGeneratorConfiguration.addProperty("enableSubPackages", "true");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        // 自动生成mapper的接口 必须存在的文件夹，不然无法生成
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetPackage(this.getClientTargetPackage());
        javaClientGeneratorConfiguration.setTargetProject(this.getClientTargetProject());
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        javaClientGeneratorConfiguration.addProperty("enableSubPackages", "true");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);
        // 遍历table
        for (Map<String, String> tableName : this.tableList) {
            TableConfiguration tableConfiguration = new TableConfiguration(context);
            tableConfiguration.setTableName(tableName.get("tableName"));
            tableConfiguration.setDelimitIdentifiers(true);
            GeneratedKey generatedKey = new GeneratedKey(tableName.get("primaryKeyName"), "Mysql", true, null);
            tableConfiguration.setGeneratedKey(generatedKey);
            context.addTableConfiguration(tableConfiguration);
        }
        configuration.addContext(context);
        return configuration;
    }
    public void run() throws Exception{
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;


        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = null;
        Configuration config = this.getConfig();
        myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        System.out.println("生成成功");
    }
}

