package com.isylph.utils.xml;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.List;

public class XMLUtil {

	/**
      * String 转 org.dom4j.Document
      * @param xml
      * @return
      * @throws DocumentException
      */
     public static Document strToDocument(String xml) throws DocumentException {
         return DocumentHelper.parseText(xml);
     }

     /**
      * org.dom4j.Document 转  com.alibaba.fastjson.JSONObject
      * @param xml
      * @return
      * @throws DocumentException
      */
     public static JSONObject xmlToJSONObject(String xml) throws DocumentException {
         return elementToJSONObject(strToDocument(xml).getRootElement());
     }

     /**
      * org.dom4j.Element 转  com.alibaba.fastjson.JSONObject
      * @param node
      * @return
      */
     public static JSONObject elementToJSONObject(Element node) {
         JSONObject result = new JSONObject();
         // 当前节点的名称、文本内容和属性
         List<Attribute> listAttr = node.attributes();// 当前节点的所有属性的list
         for (Attribute attr : listAttr) {// 遍历当前节点的所有属性
             result.put(attr.getName(), attr.getValue());
         }
         // 递归遍历当前节点所有的子节点
         List<Element> listElement = node.elements();// 所有一级子节点的list
         if (!listElement.isEmpty()) {
             for (Element e : listElement) {// 遍历所有一级子节点
                 if (e.attributes().isEmpty() && e.elements().isEmpty()) // 判断一级节点是否有属性和子节点
                     result.put(e.getName(), e.getTextTrim());// 沒有则将当前节点作为上级节点的属性对待
                 else {
                     if (!result.containsKey(e.getName())) // 判断父节点是否存在该一级节点名称的属性
                         result.put(e.getName(), new JSONArray());// 没有则创建
                     ((JSONArray) result.get(e.getName())).add(elementToJSONObject(e));// 将该一级节点放入该节点名称的属性对应的值中
                 }
             }
         }
         return result;
     }


     /**
      * xml转json
      * @param xmlStr
      * @return
      * @throws DocumentException
      */
     public static JSONObject xml2Json(String xmlStr) throws DocumentException{
         Document doc= DocumentHelper.parseText(xmlStr);
         JSONObject json=new JSONObject();
         dom4j2Json(doc.getRootElement(), json);
         return json;
     }

     /**
      * xml转json
      * @param element
      * @param json
      */
     public static void dom4j2Json(Element element,JSONObject json){
         //如果是属性
         for(Object o:element.attributes()){
             Attribute attr=(Attribute)o;
             if(!isEmpty(attr.getValue())){
//                 json.put("@"+attr.getName(), attr.getValue());
                 json.put(attr.getName(), attr.getValue());
             }
         }
         List<Element> chdEl=element.elements();
         if(chdEl.isEmpty()&&!isEmpty(element.getText())){//如果没有子元素,只有一个值
             json.put(element.getName(), element.getText());
         }

         for(Element e:chdEl){//有子元素
             if(!e.elements().isEmpty()){//子元素也有子元素
                 JSONObject chdjson=new JSONObject();
                 dom4j2Json(e,chdjson);
                 Object o=json.get(e.getName());
                 if(o!=null){
                     JSONArray jsona=null;
                     if(o instanceof JSONObject){//如果此元素已存在,则转为jsonArray
                         JSONObject jsono=(JSONObject)o;
                         json.remove(e.getName());
                         jsona=new JSONArray();
                         jsona.add(jsono);
                         jsona.add(chdjson);
                     }
                     if(o instanceof JSONArray){
                         jsona=(JSONArray)o;
                         jsona.add(chdjson);
                     }
                     json.put(e.getName(), jsona);
                 }else{
                     if(!chdjson.isEmpty()){
                         json.put(e.getName(), chdjson);
                     }
                 }


             }else{//子元素没有子元素
                 for(Object o:element.attributes()){
                     Attribute attr=(Attribute)o;
                     if(!isEmpty(attr.getValue())){
//                         json.put("@"+attr.getName(), attr.getValue());
                         json.put(attr.getName(), attr.getValue());
                     }
                 }
                 if(!e.getText().isEmpty()){
                     json.put(e.getName(), e.getText());
                 }
             }
         }
     }

     public static boolean isEmpty(String str) {

         if (str == null || str.trim().isEmpty() || "null".equals(str)) {
             return true;
         }
         return false;
     }

}
