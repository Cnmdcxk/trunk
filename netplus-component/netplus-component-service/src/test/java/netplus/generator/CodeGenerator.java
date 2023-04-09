package netplus.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.builder.GeneratorBuilder;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeGenerator {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        String help = "请输入" + tip + "：";
        System.out.println(help);

        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 数据源配置
//        DataSourceConfig dsc = new DataSourceConfig.
//                Builder("jdbc:mysql://103.10.1.215:30004/pdsp_system?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull",
//                "pdspadmin","pdspadmin123", "pdsp_system").build();

        DataSourceConfig dsc = new DataSourceConfig.
                Builder("jdbc:oracle:thin:@10.36.8.14:1521:test11g",
                "ygmalluser","ygmallDev", null).build();

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator(dsc);

        String projectPath = "E:/temp/code";

        mpg.global(GeneratorBuilder.globalConfigBuilder()
                .fileOverride().openDir(true)//.enableSwagger2()
                .outputDir(projectPath + "/src/main/java")
                .author("netplus").dateType(DateType.SQL_PACK).commentDate("yyyy-MM-dd")
                .build());

        mpg.packageInfo(new PackageConfig.Builder().parent("netplus.mall").moduleName("service").entity("pojo.ygmalluser").build());

        mpg.template(new TemplateConfig.Builder().build());

        mpg.strategy(new StrategyConfig.Builder()
//                .addInclude("system_datacenter", "system_dict_item")
                .addInclude("TBMQQ436") // 表名称大写
                .enableCapitalMode()
                .entityBuilder()
//                .superClass("com.baodata.dcom.core.object.BaseEntity")// 实体配置构建者
                .enableLombok()// 开启lombok模型
                .versionColumnName("version") //乐观锁数据库表字段
                .naming(NamingStrategy.underline_to_camel)// 数据库表映射到实体的命名策略
                .addSuperEntityColumns("id", "create_by", "create_time", "update_by", "update_time")
//                .addTableFills(new Column("create_time", FieldFill.INSERT))	//基于数据库字段填充
//                .addTableFills(new Column("create_user", FieldFill.INSERT))	//基于数据库字段填充
//                .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))	//基于模型属性填充
//                .addTableFills(new Property("updateUser", FieldFill.INSERT_UPDATE))	//基于模型属性填充
                .controllerBuilder() //控制器属性配置构建
                .enableRestStyle()
                .enableHyphenStyle()
                .entityParamBuilder() // 查询参数属性配置
//                .superClass("com.baodata.dcom.core.object.BaseParam")
                .mapperBuilder()
//                .superClass("com.baodata.dcom.core.common.mybatis.DCOMBaseMapper")
                .enableBaseResultMap()
                .enableBaseColumnList()
                .serviceBuilder()
//                .superServiceClass("com.baodata.dcom.core.service.CrudService")
                .build());

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
//        focList.add(new FileOutConfig(templatePath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
//                        + "/" + tableInfo.getEntityName() + "Dao" + StringPool.DOT_XML;
//            }
//        });

        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录，自定义目录用");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        */

        mpg.execute();
    }
}
