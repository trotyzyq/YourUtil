package com.trotyzyq.common.util;

import com.alibaba.fastjson.JSON;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * solr工具类
 * @Author trotyzyq
 */
@Component
public class SolrUtil {
//    private Logger logger = LoggerFactory.getLogger(SolrUtil.class);
//
//    /** solr core的地址**/
//    private String url = "http://localhost:8983/solr/novel";
//
//    private SolrServer solrServer = new HttpSolrServer(url);
//
//    /**
//     * 保存文档数据
//     * @param map  k:field   v:obejct
//     * @return
//     */
//    public Result save(Map<String,Object> map){
//        UpdateResponse response = null;
//        SolrInputDocument inputDocument = new SolrInputDocument();
//        for (String key : map.keySet()) {
//            Object v = map.get(key);
//            inputDocument.addField(key,v);
//        }
//        try {
//            solrServer.add(inputDocument);
//            response = solrServer.commit();
//        } catch (Exception e) {
//            logger.error("插入 solr 异常 数据:" + JSON.toJSONString(map));
//        }
//
//        if(response.getStatus() == 0){
//            return new Result().success(map);
//        }
//        return new Result("2","保存失败");
//    }
//
//    /**
//     * 修改solr数据
//     * @param map
//     * @return
//     */
//    public Result update(Map<String, Object> map){
//        UpdateResponse response = null;
//        SolrInputDocument inputDocument = new SolrInputDocument();
//        //修改id为1的商品的信息（如果该商品不存在其实就是添加了）
//        map.forEach((k,v) -> {
//            inputDocument.addField(k,v);
//        });
//        try {
//            solrServer.add(inputDocument);
//            response = solrServer.commit();
//        } catch (Exception e) {
//            logger.error("修改 solr异常：修改数据" + JSON.toJSONString(map));
//            throw new RuntimeException(e);
//        }
//        if(response.getStatus() == 0){
//            return new Result().success(map);
//        }
//        return new Result("2","修改失败");
//    }
//
//    /**
//     * 通过id 删除数据
//     * @param id
//     * @return
//     */
//    public Result deleteById(String id) {
//        UpdateResponse response = null;
//        try {
//            solrServer.deleteById(id);
//            solrServer.commit();
//        } catch (Exception e) {
//            logger.error("删除 solr 异常 : id = " +id );
//            throw new RuntimeException(e);
//        }
//        if(response.getStatus() == 0){
//            return new Result().success(id);
//        }
//        return new Result("2","删除失败");
//    }
//
//    /**
//     * 通过query 删除数据  *可用来删除所有数据  慎用，易删除很多数据
//     * @param query
//     * @return
//     */
//    public Result deleteByQuery(String query) {
//        UpdateResponse response = null;
//        try {
//            solrServer.deleteByQuery(query);
//            response = solrServer.commit();
//        } catch (Exception e) {
//            logger.error("删除 solr 异常 : query = " + query );
//            throw new RuntimeException(e);
//        }
//        if(response.getStatus() == 0){
//            return new Result().success(query);
//        }
//        return new Result("2","删除失败");
//    }
//
//    /**
//     * 通过搜索条件搜索数据
//     * @param queryMap
//     * @return
//     */
//    public Result search(Map<String,Object> queryMap){
//        //1.创建连接
//        SolrServer solrServer = new HttpSolrServer(url);
//        //2.创建查询语句
//        SolrQuery query = new SolrQuery();
//        //3.设置查询条件
//        queryMap.forEach((k,v) -> {
//            query.set("q",k+":"+(String)v);
//        });
//        query.setStart(0);
//        query.setRows(500);//分页条件
//
//        //4.执行查询
//        QueryResponse queryResponse = null;
//        try {
//            queryResponse = solrServer.query(query);
//        } catch (Exception e) {
//            logger.error("查询 solr 异常 : 数据 = " + JSON.toJSONString(queryMap) );
//            throw new RuntimeException(e);
//        }
//        if (queryResponse.getStatus() != 0){
//            return new Result("2","查询失败");
//        }
//
//
//        SolrDocumentList documentList = queryResponse.getResults();
//        List<Map<String,Object>> resultList = new ArrayList<>();
//        for (SolrDocument solrDocument : documentList) {
//            Map<String,Object> fieldNameMap = solrDocument.getFieldValueMap();
//            Iterator<String> iterator = fieldNameMap.keySet().iterator();
//            /** 通过该map赋值**/
//            Map<String,Object> finalMap = new HashMap();
//            while (iterator.hasNext()){
//                String key = iterator.next();
//                finalMap.put(key,fieldNameMap.get(key));
//            }
//            resultList.add(finalMap);
//        }
//        return new Result().success(queryMap,resultList);
//    }
//
//    /**
//     * 通过搜索条件搜索数据
//     * @param queryMap
//     * @param orderBy  按照某个字段排序
//     * @param desc  降序：true 升序:false  默认升序
//     * @return
//     */
//    public Result searchOrder(Map<String,Object> queryMap, String orderBy, Boolean desc){
//        //1.创建连接
//        SolrServer solrServer = new HttpSolrServer(url);
//        //2.创建查询语句
//        SolrQuery query = new SolrQuery();
//        //3.设置查询条件
//        queryMap.forEach((k,v) -> {
//            query.set("q",(String)v);
//        });
//        /** 如果orderBy不为空**/
//        if(! StringUtil.isEmpty(orderBy)){
//            if (false == desc){
//                query.setSort(orderBy, SolrQuery.ORDER.desc);
//            }else {
//                query.setSort(orderBy, SolrQuery.ORDER.asc);
//            }
//        }
//        query.setStart(0);
//        query.setRows(100);//分页条件
//        //4.执行查询
//        QueryResponse queryResponse = null;
//        try {
//            queryResponse = solrServer.query(query);
//        } catch (Exception e) {
//            logger.error("查询 solr 异常 : 数据 = " + JSON.toJSONString(queryMap) );
//            throw new RuntimeException(e);
//        }
//        if (queryResponse.getStatus() != 0){
//            return new Result("2","查询失败");
//        }
//
//        //5.取文档列表public class SolrDocumentList extends ArrayList<SolrDocument>
//        SolrDocumentList documentList = queryResponse.getResults();
//        List<Map<String,Object>> resultList = new ArrayList<>();
//        for (SolrDocument solrDocument : documentList) {
//            Map fieldNameMap = solrDocument.getFieldValueMap();
//            resultList.add(fieldNameMap);
//        }
//        return new Result().success(queryMap,resultList);
//    }
//
//    /*
//     * 简单查询:查询单个商品信息
//     */
//    @Test
//    public void testSimpleQ() throws Exception {
//        Map map = new HashMap();
//        map.put("novelName","斗罗大陆");
//        map.put("novelAuthor","斗罗大陆");
//        Result result = search(map);
//        System.out.println(result);
//    }
//
//
//
//    /*
//     * 测试复杂查询：取高亮
//     */
//    @Test
//    public void testHighLighting() throws Exception {
//        //1、创建连接
//        SolrServer solrServer = new HttpSolrServer(url);
//        //2、创建查询语句
//        SolrQuery query = new SolrQuery();
//        //3、设置查询条件
//        query.set("q", "夏普");//设置查询关键字
//        query.setSort("id", SolrQuery.ORDER.desc);//按照id降序排列
//        query.setStart(1);
//        query.setRows(5);//分页条件
//        query.set("df", "item_title");
//
//        //开启高亮显示
//        query.setHighlight(true);
//        query.setHighlightSimplePre("<em>");
//        query.setHighlightSimplePost("</em>");
//
//        //4、执行查询
//        QueryResponse queryResponse = solrServer.query(query);
//        //5、取高亮
//        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
//        //6、获取文档列表
//        SolrDocumentList documentList = queryResponse.getResults();
//        //7、遍历查询结果
//        for (SolrDocument solrDocument : documentList) {
//            System.out.print(solrDocument.get("id") + " ");
//            List<String> titleList = highlighting.get(solrDocument.get("id")).get("item_title");
//            if (titleList != null && titleList.size() > 0) {
//                //能取到高亮,输出高亮
//                System.out.print(titleList.get(0));
//            }
//            System.out.println();
//        }
//    }
//
//    @Test
//    public void test2(){
//        deleteByQuery("*");
//    }
}

