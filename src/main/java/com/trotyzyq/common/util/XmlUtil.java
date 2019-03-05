package com.trotyzyq.common.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

    public static void main(String[] args) {
        Document document = DocumentHelper.createDocument();
        Element parent = document.addElement("Parent");
        parent.addElement("Age");
        parent.addElement("Name").setText("zyq");
        Map map = new HashMap();
        map.put("xx","123");
        map.put("yy","12");
        addElment(document, "Age", map);
        System.out.println(document.asXML());
        String x = getStrByElement(document,"Age");
        System.out.println(x);
    }
}
