package com.trotyzyq.common.config.config;

import com.alibaba.fastjson.JSONObject;
import org.dom4j.Element;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 字段转换规则
 * <p/>
 * Created by wangjinpeng on 15/11/21.
 */
public class FieldRule {

    protected static org.slf4j.Logger logger = LoggerFactory.getLogger(FieldRule.class);

    //原先属性名
    private String field;
    //新属性名
    private String newField;
    //属性转换器
    private ValueParser valueParser;

    private FieldRule() {
        //不允许New
    }

    public String getField() {
        return field;
    }

    public FieldRule setField(String field) {
        this.field = field;
        return this;
    }

    public String getNewField() {
        return newField;
    }

    public FieldRule setNewField(String newField) {
        this.newField = newField;
        return this;
    }

    public ValueParser getValueParser() {
        return valueParser;
    }

    public FieldRule setValueParser(ValueParser valueParser) {
        this.valueParser = valueParser;
        return this;
    }

    public static FieldRule create(String field, String newField, ValueParser parser) {
        FieldRule rule = new FieldRule();
        rule.setField(field);
        rule.setNewField(newField);
        rule.setValueParser(parser);
        return rule;
    }

    public static FieldRule create(String field, String newField) {
        FieldRule rule = new FieldRule();
        rule.setField(field);
        rule.setNewField(newField);
        return rule;
    }

    public static FieldRule create(String field, ValueParser parser) {
        FieldRule rule = new FieldRule();
        rule.setField(field);
        rule.setValueParser(parser);
        return rule;
    }

    public static void parseJSON(JSONObject jsonObject, List<FieldRule> fieldRules) {

        try {
            if (fieldRules == null) {
                return;
            }
            for (FieldRule rule : fieldRules) {
                String field = rule.getField();
                String newField = rule.getNewField();
                ValueParser parser = rule.getValueParser();
                Object value = jsonObject.get(field);
                if (parser != null) {
                    value = rule.getValueParser().parse(value);
                }
                if (newField == null || field.equals(newField)) {
                    jsonObject.put(field, value);
                } else {
                    jsonObject.put(newField, value);
                    jsonObject.remove(field);
                }
            }
        } catch (Exception e) {
            logger.error("parseJSON =" + jsonObject, e);
        }


    }

    public static void parseXML(Element body, List<FieldRule> fieldRules) {
        if (fieldRules == null) {
            return;
        }
        for (FieldRule rule : fieldRules) {
            String field = rule.getField();
            String newField = rule.getNewField();
            ValueParser parser = rule.getValueParser();
            Object value = body.elementTextTrim(field);
            if (parser != null) {
                value = rule.getValueParser().parse(value);
            }
            if (newField == null || field.equals(newField)) {
                Element _ele = body.element(field);
                if (_ele == null) { //新增Field
                    _ele = body.addElement(field);
                }
                _ele.setText(value == null ? "" : value.toString());
            } else {
                if (value == null) {
                    continue;
                }
                body.addElement(newField).setText(value.toString());
                body.remove(body.element(field));
            }
        }

    }

    public static void parseMap(Map<String, String> map, List<FieldRule> fieldRules) {
        if (fieldRules == null) {
            return;
        }
        for (FieldRule fieldRule : fieldRules) {
            String key = fieldRule.getField();
            String newKey = fieldRule.getNewField();
            String value = map.get(key);
            ValueParser valueParser = fieldRule.getValueParser();
            if (valueParser != null) {
                value = (String) valueParser.parse(value);
            }
            if (newKey == null || key.equals(newKey)) {
                map.put(key, value);
            } else {
                map.put(newKey, value);
                map.remove(key);
            }
        }
    }
}
