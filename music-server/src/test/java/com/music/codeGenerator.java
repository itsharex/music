package com.music;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * @author lyj
 * @data 2022/6/20 15:30
 */
public class codeGenerator {

    @Test
    void run(){
        final String projectPath = System.getProperty("user.dir");
        AutoGenerator autoGenerator = new AutoGenerator();

        GlobalConfig config = new GlobalConfig();
        config.setOutputDir(projectPath + "/src/main/java");
        config.setOpen(false);
        config.setAuthor("lyj");
        config.setServiceName("%sService");
        config.setIdType(IdType.AUTO);
        config.setDateType(DateType.ONLY_DATE);
        autoGenerator.setGlobalConfig(config);

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://127.0.0.1:3306/music");
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("123456");
        autoGenerator.setDataSource(dataSourceConfig);

        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.music");
        packageConfig.setEntity("entity");
        packageConfig.setMapper("mapper");
        packageConfig.setController("controller");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.Impl");
        autoGenerator.setPackageInfo(packageConfig);

        StrategyConfig strategy = new StrategyConfig();
        strategy.setCapitalMode(true);// 开启全局大写命名
        strategy.setNaming(NamingStrategy.underline_to_camel);// 实体类名驼峰
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);// 属性名驼峰
        strategy.setInclude(new String[] {"account","admin_user","collect","singer","song",
                "songmenu","songmenu_list","carousel"});// 设置要映射的表名
        strategy.setEntityLombokModel(true); // 使用 lombok
        strategy.setRestControllerStyle(true);// 开启 restful风格

        TableFill createTime = new TableFill("create_time", FieldFill.INSERT);
        TableFill updateTime = new TableFill("update_time",FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(createTime);
        tableFills.add(updateTime);
        strategy.setTableFillList(tableFills);

        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true);
        autoGenerator.setStrategy(strategy);

        autoGenerator.execute();
    }
}
