package com.chenbing.SpringIoc;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassPathXmlApplicationContext implements BeanFactory{
    private Map<String,Object> beans = new HashMap<String,Object>();


    public ClassPathXmlApplicationContext() throws Exception{
        SAXBuilder sb = new SAXBuilder();//新建xml解析器

        Document xmlDoc = sb.build(this.getClass().getClassLoader().
                getResourceAsStream("bean.xml"));//构造文档对象
        Element root = xmlDoc.getRootElement();//获取根元素
        List<Element> list = root.getChildren("bean");//获取根元素下所有名字为bean的元素

        //遍历所有的bean元素，并将其放入beans中
        for(int i = 0;i<list.size();i++){
            Element e = (Element)list.get(i);//获取Element元素
            String id = e.getAttributeValue("id");//获取property的id属性的值
            String clazz = e.getAttributeValue("class");//获取property的class属性的值
            Object o = Class.forName(clazz).newInstance();//利用反射生成一个具体的实例
            System.out.println(" id: "+id+" class: "+clazz);
            beans.put(id, o);//将bean放入一个HashMap
            //遍历bean下的所有properyt属性，并调用setter方法，将值注入给对应的属性
            if(e.getChild("property")!=null){
                for(Element p : (List<Element>)e.getChildren("property")){
                    String name = p.getAttributeValue("name");
                    String bean = p.getAttributeValue("bean");
                    Object beanObject = beans.get(bean);
                    String methodName = "set" + name.substring(0,1).toUpperCase()+ name.substring(1);
                    System.out.println("setter method name = " +methodName);
                    Method m = o.getClass().getMethod(methodName,beanObject.getClass().getInterfaces()[0]);
                    m.invoke(o,beanObject);//调用setter方法
                }
            }
        }

    }

    public Object getBean(String id) {
        return beans.get(id);
    }
}
