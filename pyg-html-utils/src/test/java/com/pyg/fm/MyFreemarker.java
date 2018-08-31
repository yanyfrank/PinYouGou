package com.pyg.fm;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;


public class MyFreemarker {

    /**
     * 需求：freemarker入门案例
     * 生成静态页面三要素
     * 1，模板（com.pyg.ftl）
     * 2，准备数据（在测试中模拟数据）
     * 3，freemarker生成静态页面API
     * 模板数据：
     *      通过模板取值表达式获取值：${}
     *      括号：${key} key 就是map的key
     */
    @Test
    public void test01() throws Exception {
        //创建freemarker核心配置对象
        Configuration cf = new Configuration(Configuration.getVersion());
        //设置模板所在路径
        cf.setDirectoryForTemplateLoading(new File("E:\\Ideaprojects\\pyg-html-utils\\src" +
                "\\main\\resources\\template"));
        //设置模板编码
        cf.setDefaultEncoding("utf-8");

        //读取指定路径下的模板文件
        Template template = cf.getTemplate("hello.com.pyg.ftl");

        //创建map对象，封装数据（模拟数据）
        Map maps = new HashMap();
        maps.put("name","张三");
        maps.put("message","习总来视察");

        //创建输出流对象，把生成的静态页面写入磁盘
        Writer out = new FileWriter(new File("E:\\Ideaprojects\\" +
                "pyg-html-utils\\src\\main\\resources\\out\\first.html"));

        //生成静态页面
        template.process(maps,out);

        out.close();

    }

    /**
     * 需求：模板啊页面获取对象数据
     * 生成静态页面三要素
     * 1，模板（com.pyg.ftl）
     * 2，准备数据（在测试中模拟数据）
     * 3，freemarker生成静态页面API
     * 模板数据：
     *      通过模板取值表达式获取值：${}
     *      括号：${key} key 就是map的key
     */
    @Test
    public void test02() throws Exception {
        //创建freemarker核心配置对象
        Configuration cf = new Configuration(Configuration.getVersion());
        //设置模板所在路径
        cf.setDirectoryForTemplateLoading(new File("E:\\Ideaprojects\\pyg-html-utils\\src" +
                "\\main\\resources\\template"));
        //设置模板编码
        cf.setDefaultEncoding("utf-8");

        //读取指定路径下的模板文件
        Template template = cf.getTemplate("person.com.pyg.ftl");

        //创建对象
        Person p = new Person();
        p.setId(1000);
        p.setUsername("张无忌");
        p.setSex("男");
        p.setAge("10");
        p.setAddress("冰火岛");

        //创建map对象，封装数据（模拟数据）
        Map maps = new HashMap();
        maps.put("p",p);


        //创建输出流对象，把生成的静态页面写入磁盘
        Writer out = new FileWriter(new File("E:\\Ideaprojects\\" +
                "pyg-html-utils\\src\\main\\resources\\out\\person.html"));

        //生成静态页面
        template.process(maps,out);

        out.close();

    }

    /**
     * 需求：模板啊页面获取对象数据
     * 生成静态页面三要素
     * 1，模板（com.pyg.ftl）
     * 2，准备数据（在测试中模拟数据）
     * 3，freemarker生成静态页面API
     * 模板数据：
     *      通过模板取值表达式获取值：${}
     *      括号：${key} key 就是map的key
     */
    @Test
    public void test03() throws Exception {
        //创建freemarker核心配置对象
        Configuration cf = new Configuration(Configuration.getVersion());
        //设置模板所在路径
        cf.setDirectoryForTemplateLoading(new File("E:\\Ideaprojects\\pyg-html-utils\\src" +
                "\\main\\resources\\template"));
        //设置模板编码
        cf.setDefaultEncoding("utf-8");

        //读取指定路径下的模板文件
        Template template = cf.getTemplate("assign.com.pyg.ftl");


        //创建map对象，封装数据（模拟数据）
        Map maps = new HashMap();
        maps.put("username","张三");
        maps.put("message","习总来视察");



        //创建输出流对象，把生成的静态页面写入磁盘
        Writer out = new FileWriter(new File("E:\\Ideaprojects\\" +
                "pyg-html-utils\\src\\main\\resources\\out\\assign.html"));

        //生成静态页面
        template.process(maps,out);

        out.close();

    }


    /**
     * 需求：模板啊页面获取对象数据
     * 生成静态页面三要素
     * 1，模板（com.pyg.ftl）
     * 2，准备数据（在测试中模拟数据）
     * 3，freemarker生成静态页面API
     * 模板数据：
     *      通过模板取值表达式获取值：${}
     *      括号：${key} key 就是map的key
     */
    @Test
    public void test04() throws Exception {
        //创建freemarker核心配置对象
        Configuration cf = new Configuration(Configuration.getVersion());
        //设置模板所在路径
        cf.setDirectoryForTemplateLoading(new File("E:\\Ideaprojects\\pyg-html-utils\\src" +
                "\\main\\resources\\template"));
        //设置模板编码
        cf.setDefaultEncoding("utf-8");

        //读取指定路径下的模板文件
        Template template = cf.getTemplate("ifelse.com.pyg.ftl");


        //创建map对象，封装数据（模拟数据）
        Map maps = new HashMap();
        maps.put("flag",3);




        //创建输出流对象，把生成的静态页面写入磁盘
        Writer out = new FileWriter(new File("E:\\Ideaprojects\\" +
                "pyg-html-utils\\src\\main\\resources\\out\\ifelse.html"));

        //生成静态页面
        template.process(maps,out);

        out.close();

    }

    /**
     * 需求：freemarker list指令
     * 生成静态页面三要素
     * 1，模板（com.pyg.ftl）
     * 2，准备数据（在测试中模拟数据）
     * 3，freemarker生成静态页面API
     * 模板数据：
     *      通过模板取值表达式获取值：${}
     *      括号：${key} key 就是map的key
     */
    @Test
    public void test05() throws Exception {
        //创建freemarker核心配置对象
        Configuration cf = new Configuration(Configuration.getVersion());
        //设置模板所在路径
        cf.setDirectoryForTemplateLoading(new File("E:\\Ideaprojects\\pyg-html-utils\\src" +
                "\\main\\resources\\template"));
        //设置模板编码
        cf.setDefaultEncoding("utf-8");

        //读取指定路径下的模板文件
        Template template = cf.getTemplate("list.com.pyg.ftl");

        //创建集合，封装数据
        List<Person> pList = new ArrayList<>();

        //创建对象
        Person p1 = new Person();
        p1.setId(12321);
        p1.setUsername("赵敏");
        p1.setSex("女");
        p1.setAge("29");
        p1.setAddress("大都");

        Person p2 = new Person();
        p2.setId(12321);
        p2.setUsername("周芷若");
        p2.setSex("女");
        p2.setAge("28");
        p2.setAddress("峨眉");

        Person p3 = new Person();
        p3.setId(12321);
        p3.setUsername("张三丰");
        p3.setSex("男");
        p3.setAge("150");
        p3.setAddress("武当");

        pList.add(p1);
        pList.add(p2);
        pList.add(p3);


        //创建map对象，封装数据（模拟数据）
        Map maps = new HashMap();
        maps.put("pList",pList);




        //创建输出流对象，把生成的静态页面写入磁盘
        Writer out = new FileWriter(new File("E:\\Ideaprojects\\" +
                "pyg-html-utils\\src\\main\\resources\\out\\list.html"));

        //生成静态页面
        template.process(maps,out);

        out.close();

    }


    /**
     * 需求：freemarker eval内建函数
     * 生成静态页面三要素
     * 1，模板（com.pyg.ftl）
     * 2，准备数据（在测试中模拟数据）
     * 3，freemarker生成静态页面API
     * 模板数据：
     *      通过模板取值表达式获取值：${}
     *      括号：${key} key 就是map的key
     */
    @Test
    public void test06() throws Exception {
        //创建freemarker核心配置对象
        Configuration cf = new Configuration(Configuration.getVersion());
        //设置模板所在路径
        cf.setDirectoryForTemplateLoading(new File("E:\\Ideaprojects\\pyg-html-utils\\src" +
                "\\main\\resources\\template"));
        //设置模板编码
        cf.setDefaultEncoding("utf-8");

        //读取指定路径下的模板文件
        Template template = cf.getTemplate("eval.com.pyg.ftl");






        //创建输出流对象，把生成的静态页面写入磁盘
        Writer out = new FileWriter(new File("E:\\Ideaprojects\\" +
                "pyg-html-utils\\src\\main\\resources\\out\\eval.html"));

        //生成静态页面
        template.process(null,out);

        out.close();

    }


    /**
     * 需求：freemarker 时间格式化函数
     * 生成静态页面三要素
     * 1，模板（com.pyg.ftl）
     * 2，准备数据（在测试中模拟数据）
     * 3，freemarker生成静态页面API
     * 模板数据：
     *      通过模板取值表达式获取值：${}
     *      括号：${key} key 就是map的key
     */
    @Test
    public void test07() throws Exception {
        //创建freemarker核心配置对象
        Configuration cf = new Configuration(Configuration.getVersion());
        //设置模板所在路径
        cf.setDirectoryForTemplateLoading(new File("E:\\Ideaprojects\\pyg-html-utils\\src" +
                "\\main\\resources\\template"));
        //设置模板编码
        cf.setDefaultEncoding("utf-8");

        //读取指定路径下的模板文件
        Template template = cf.getTemplate("date.com.pyg.ftl");

        Map maps = new HashMap();
        maps.put("today",new Date());


        //创建输出流对象，把生成的静态页面写入磁盘
        Writer out = new FileWriter(new File("E:\\Ideaprojects\\" +
                "pyg-html-utils\\src\\main\\resources\\out\\date.html"));

        //生成静态页面
        template.process(maps,out);

        out.close();

    }

    /**
     * 需求：freemarker null处理
     * 生成静态页面三要素
     * 1，模板（com.pyg.ftl）
     * 2，准备数据（在测试中模拟数据）
     * 3，freemarker生成静态页面API
     * 模板数据：
     *      通过模板取值表达式获取值：${}
     *      括号：${key} key 就是map的key
     *  语法1：${name?default(”默认值“)}
     *  语法2：${name!"默认值"}
     *  语法3：
     *  <#if name??>
     *      ${name}
     *  </#if>
     */
    @Test
    public void test08() throws Exception {
        //创建freemarker核心配置对象
        Configuration cf = new Configuration(Configuration.getVersion());
        //设置模板所在路径
        cf.setDirectoryForTemplateLoading(new File("E:\\Ideaprojects\\pyg-html-utils\\src" +
                "\\main\\resources\\template"));
        //设置模板编码
        cf.setDefaultEncoding("utf-8");

        //读取指定路径下的模板文件
        Template template = cf.getTemplate("null.com.pyg.ftl");

        //创建map对象，封装数据（模拟数据）
        Map maps = new HashMap();
        maps.put("name",null);
        maps.put("message","习总来视察");

        //创建输出流对象，把生成的静态页面写入磁盘
        Writer out = new FileWriter(new File("E:\\Ideaprojects\\" +
                "pyg-html-utils\\src\\main\\resources\\out\\null.html"));

        //生成静态页面
        template.process(maps,out);

        out.close();

    }

}
