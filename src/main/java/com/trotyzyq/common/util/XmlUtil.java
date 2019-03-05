package com.trotyzyq.common.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Test;

import java.util.*;

/**
 * @author trotyzyq
 * xml操作工具类
 */

public class XmlUtil {

    /**
     * xml字符串转化为json格式
     * @param xml
     * @return
     */
    public static String xmlToJson(String xml){
        JSONObject object = null;
        try {
            object = XML.toJSONObject(xml);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  object.toString();
    }

    /** xml转化为字符串格式**/
    public static void xmlToStr(Document document){
        System.out.println(document.asXML());
    }

    /**
     * 字符串转化为xml
     * @param str 需要被转化的字符串
     * @return Document
     */
    public static Document strToXml(String str){
        if(null == str || str.isEmpty()){
            return null;
        }
        Document document = null;
        try {
            document = DocumentHelper.parseText(str);
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
        return document;
    }

    /**
     * 往指定的节点添加节点
     * @param document document文档
     * @param elementName 指定节点的名称
     * @param element 被添加的节点
     */
    public static void addElement(Document document, String elementName, Element element){
        Element rootElement = document.getRootElement();
        for (Iterator i = rootElement.elementIterator(); i.hasNext();) {
            Element el = (Element) i.next();
            /** 遍历至找到这个节点 **/
            if (elementName.equals(el.getName())) {
                el.add(element);
                break;
            }
        }
        System.out.println(document.asXML());
    }

    /**
     * 往指定的节点添加list
     * @param document
     * @param stringList
     * @param parentName 被添加的节点名称
     * @param nodeName  添加的节点名称
     */
    public static void xmlAddListNode(Document document, List<String> stringList, String parentName, String nodeName){
        Element rootElement = document.getRootElement();
        for (Iterator i = rootElement.elementIterator(); i.hasNext();) {
            Element el = (Element) i.next();
            /** 遍历至找到这个节点 **/
            if (parentName.equals(el.getName())) {
                for(String str : stringList){
                    el.addElement(nodeName).setText(str);
                }
                break;
            }
        }
    }
    /**
     * 往指定的节点添加节点信息
     * @param document document文档
     * @param elementName 指定节点的名称
     * @param paramMap 被添加节点的信息
     */
    public static void addElment(Document document, String elementName, Map<String,String> paramMap){
        Element rootElement = document.getRootElement();
        for (Iterator i = rootElement.elementIterator(); i.hasNext();) {
            Element el = (Element) i.next();
            /** 遍历至找到这个节点 **/
            if (elementName.equals(el.getName())) {
                Iterator iterator = paramMap.keySet().iterator();
                while(iterator.hasNext()){
                    String str = (String) iterator.next();
                    el.addElement(str).setText(paramMap.get(str));
                }
                break;
            }
        }
    }

    /**
     * 获取指定节点的字符串
     * @param document dom文档
     * @param elementName 节点名称
     * @return
     */
    public static String getStrByElement(Document document, String elementName){
        Element rootElement = document.getRootElement();
        for (Iterator i = rootElement.elementIterator(); i.hasNext();) {
            Element el = (Element) i.next();
            /** 遍历至找到这个节点 **/
            if (elementName.equals(el.getName())) {
                String text = el.asXML();
                return text;
            }
        }
        return null;
    }

    /**
     * 通过map转化成xml
     * @param rootName 头节点名称
     * @param map 需要生成的参数
     * @return xml document文档
     */
    public static Document map2Xml(String rootName, Map map){
        Document document = DocumentHelper.createDocument();
        document.addElement(rootName);
        /** 获取头节点**/
        Element rootElement = document.getRootElement();
        /** 遍历map并赋值**/
        for(Iterator iterator =  map.keySet().iterator();iterator.hasNext();){
            String name = (String) iterator.next();
            rootElement.addElement(name).setText((String) map.get(name));
        }
        return document;
    }


    /** 生成xml **/
    @Test
    public void createXML(){
        Document document = DocumentHelper.createDocument();
        Element parent = document.addElement("Parent");
        parent.addElement("Age");
        parent.addElement("Name").setText("zyq");
        Map map = new HashMap();
        map.put("xx","123");
        map.put("yy","12");
        addElment(document, "Age", map);
        System.out.println(document.asXML());
    }

    @Test
    public  void getStr(){
        Map map = new HashMap();
        map.put("xx","123");
        map.put("yy","12");
        Document document = map2Xml("test", map);
        List list = new ArrayList();
        list.add("333");
        list.add("444");
        xmlAddListNode(document, list, "xx" , "zz");
        System.out.println(document.asXML());
    }
}
