package com.trotyzyq.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import org.dom4j.*;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.trotyzyq.common.util.StringUtil.isEmpty;

/**
 * @author trotyzyq
 * xml操作工具类
 */

public class XmlUtil {

    /**
     * xml字符串转化为json格式
     * @return
     */
    public static void xmlToJson(Element element, JSONObject json){
        // 如果是属性
        for (Object o : element.attributes()) {
            Attribute attr = (Attribute) o;
            if (!isEmpty(attr.getValue())) {
                json.put("@" + attr.getName(), attr.getValue());
            }
        }
        List<Element> chdEl = element.elements();
        if (chdEl.isEmpty() && !isEmpty(element.getText())) {// 如果没有子元素,只有一个值
            json.put(element.getName(), element.getText());
        }

        for (Element e : chdEl) {// 有子元素
            if (!e.elements().isEmpty()) {// 子元素也有子元素
                JSONObject chdjson = new JSONObject();
                xmlToJson(e, chdjson);
                Object o = json.get(e.getName());
                if (o != null) {
                    JSONArray jsona = null;
                    if (o instanceof JSONObject) {// 如果此元素已存在,则转为jsonArray
                        JSONObject jsono = (JSONObject) o;
                        json.remove(e.getName());
                        jsona = new JSONArray();
                        jsona.add(jsono);
                        jsona.add(chdjson);
                    }
                    if (o instanceof JSONArray) {
                        jsona = (JSONArray) o;
                        jsona.add(chdjson);
                    }
                    json.put(e.getName(), jsona);
                } else {
                    if (!chdjson.isEmpty()) {
                        json.put(e.getName(), chdjson);
                    }
                }

            } else {// 子元素没有子元素
                for (Object o : element.attributes()) {
                    Attribute attr = (Attribute) o;
                    if (!isEmpty(attr.getValue())) {
                        json.put("@" + attr.getName(), attr.getValue());
                    }
                }
                if (!e.getText().isEmpty()) {
                    json.put(e.getName(), e.getText());
                }
            }
        }
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
            String elementName = el.getName();
            if (parentName.equals(elementName)) {
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
     * 获取指定节点的子节点
     * @param str document字符串
     * @param elementName 节点名称
     * @return
     */
    public static Element getElementByElement(String  str, String elementName){
        Document document =  strToXml(str);
        Element rootElement = document.getRootElement();
        for (Iterator i = rootElement.elementIterator(); i.hasNext();) {
            Element el = (Element) i.next();
            /** 遍历至找到这个节点 **/
            if (elementName.equals(el.getName())) {
                return  el;
            }
        }
        return null;
    }


    /**
     * 获取指定节点的子节点字符串
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
     * 获取指定节点的子节点字符串
     * @param str document字符串
     * @param elementName 节点名称
     * @return
     */
    public static String getStrByElement(String  str, String elementName){
        Document document =  strToXml(str);
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
     * 获取指定节点的文本
     * @param str document字符串
     * @param elementName 节点名称
     * @return
     */
    public static String getTextByElement(String  str, String elementName){
        Document document =  strToXml(str);
        Element rootElement = document.getRootElement();
        for (Iterator i = rootElement.elementIterator(); i.hasNext();) {
            Element el = (Element) i.next();
            System.out.println(el.getName());
            /** 遍历至找到这个节点 **/
            if (elementName.equals(el.getName())) {
                String text = el.getText().trim();
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

    /**
     * 将xml转换成json
     * @param xml
     * @param elementNames
     * @return jsonObject
     */
//    public static JSONObject xml2Json(String xml, List<String> elementNames,){
//
//    }
    private String xml = "";
    private Document document = null;

    @Before
    public void before(){
        document = DocumentHelper.createDocument();
        Element parent = document.addElement("first");
        Element s1 = parent.addElement("second1");
        parent.addElement("second2").setText("s2");
        s1.addElement("t1").setText("t1");
        s1.addElement("t2").setText("t2");

        xml =  document.asXML();
    }


    @Test
    public void testXmlToJson(){
        System.out.println(xml);
        Element element = document.getRootElement();
        JSONObject jsonObject =  new JSONObject();
        xmlToJson(element, jsonObject);
        System.out.println(jsonObject);
    }

    @Test
    public void xmlAddListNode(){
        xmlAddListNode(document, Lists.newArrayList("s3","s4"),"second1", "t3");
        System.out.println(document.asXML());
    }

    @Test
    public void createXML(){
        Map map = new HashMap();
        map.put("xx","123");
        map.put("yy","12");
        addElment(document, "Age", map);
        System.out.println(document.asXML());
        getTextByElement(document.asXML(),"xx");

    }


    @Test
    public void test1(){
        String s = "<DC_RequestResult>\n" +
                "  <ErrorInfo>字符串内容</ErrorInfo>\n" +
                "  <IsNotAllowed>true</IsNotAllowed>\n" +
                "  <IsSuccessful>true</IsSuccessful>\n" +
                "  <ResultObject>\n" +
                "<DC_BookResult>\n" +
                "  <QueueId>字符串内容</QueueId>\n" +
                "      <QueNameId>字符串内容</QueNameId>\n" +
                "<QueueName>字符串内容</QueueName>\n" +
                "      <QueueCode>字符串内容</QueueCode>\n" +
                "<Checkdate>字符串内容</Checkdate>\n" +
                "<TimeType>字符串内容</TimeType>\n" +
                "      <QueNumberTime>字符串内容</QueNumberTime>\n" +
                "<PatName>字符串内容</PatName>\n" +
                "      <PatSex>字符串内容</PatSex>\n" +
                "      <PatType>字符串内容</PatType>\n" +
                "<IsEmergency>字符串内容</IsEmergency>\n" +
                "<Modality>字符串内容</Modality>\n" +
                "<ModalityId>字符串内容</ModalityId>\n" +
                "<InPatientId>字符串内容</InPatientId>\n" +
                "<OutPatientId>字符串内容</OutPatientId>\n" +
                "<TelephoneNum>字符串内容</TelephoneNum>\n" +
                "<IsBooking>字符串内容</IsBooking>\n" +
                "<RemindInfo>字符串内容</RemindInfo>\n" +
                "<DC_BookResult>\n" +
                " </ResultObject>\n" +
                "</DC_RequestResult>";
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(s);
        s = m.replaceAll("");
//        String afS = s.replace("\"\\\\s*|\\t|\\r|\\n\"","").replace("/\\s*,","").trim();
        System.out.println(s);
    }

    @Test
    public void json(){
        String xml = "<DC_RequestResult><ErrorInfo>字符串内容</ErrorInfo><IsNotAllowed>true</IsNotAllowed><IsSuccessful>true</IsSuccessful><ResultObject><DTO_QueueNumberSourcePool><QueueId/><Date/><DTO_QueNumberInfo><QueNumberId>字符串内容</QueNumberId><QueNameId>字符串内容</QueNameId><QueNumber>字符串内容</QueNumber><QueNumberDate>字符串内容</QueNumberDate><QueNumberTime>字符串内容</QueNumberTime><QueNumberTimeRange>字符串内容</QueNumberTimeRange><TimeType>字符串内容</TimeType><IsBookable>true</IsBookable><IsUsed>true</IsUsed><HisOrderId>字符串内容</HisOrderId><ExamItems>字符串内容</ExamItems><PatName>字符串内容</PatName><PatSex>字符串内容</PatSex><PatType>字符串内容</PatType><RisOrderSeqs>字符串内容</RisOrderSeqs></DTO_QueNumberInfo><DTO_QueNumberInfo><QueNumberId>字符串内容</QueNumberId><QueNameId>字符串内容</QueNameId><QueNumber>字符串内容</QueNumber><QueNumberDate>字符串内容</QueNumberDate><QueNumberTime>字符串内容</QueNumberTime><QueNumberTimeRange>字符串内容</QueNumberTimeRange><TimeType>字符串内容</TimeType><IsBookable>true</IsBookable><IsUsed>true</IsUsed><HisOrderId>字符串内容</HisOrderId><ExamItems>字符串内容</ExamItems><PatName>字符串内容</PatName><PatSex>字符串内容</PatSex><PatType>字符串内容</PatType><RisOrderSeqs>字符串内容</RisOrderSeqs></DTO_QueNumberInfo></DTO_QueueNumberSourcePool></ResultObject></DC_RequestResult>";
    }

    @Test
    public void testGuavaMoreObjects(){
        String x = null;
        String s = MoreObjects.firstNonNull(x,"2");
        System.out.println(s);

        String z = "1|2" ;
        System.out.println(z.split("\\|")[1]);
    }


}
