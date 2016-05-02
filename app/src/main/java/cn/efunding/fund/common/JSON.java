package cn.efunding.fund.common;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONStringer;
import org.json.JSONTokener;

import cn.efunding.fund.common.IDataGetterValue;

public final class JSON {

	
	public static Object decodeString(String string) throws Throwable{
		JSONTokener tokener = new JSONTokener(string);
		return decodeValue(tokener);
	}
	
	private static Object decodeValue(JSONTokener token) throws JSONException{
		
		char c = token.nextClean();
		
		if(c == '{'){
			return decodeObject(token);
		}
		else if(c == '['){
			return decodeArray(token);
		}
		else if(c == '"'){
			return token.nextString('"');
		}
		else if(c == '\''){
			return token.nextString('\'');
		}
		else if(c == ','){
			token.back();
			return null;
		}
		else if(c == '}' || c== ']'){
			return null;
		}
		else if(c != 0){
			
			StringBuilder sb = new StringBuilder();
			
			while(c != 0){
				
				if(c == '\t' || c == '\r' || c== '\n' || c==' '){
					break;
				}
				else if(c == ',' || c == '}' || c == ']'){
					token.back();
					break;
				}
				
				sb.append(c);
				
				c = token.next();
			}
			
			String v = sb.toString();
			
			if("false".equals(v)){
				return Boolean.valueOf(false);
			}
			else if("true".equals(v)){
				return Boolean.valueOf(true);
			}
			else if("null".equals(v)){
				return null;
			}
			else{
				double vv = Double.valueOf(v);
				if((double)(long)vv == vv) {
					return (long)vv;
				}
				else {
					return vv;
				}
			}
		}
		
		return null;
	}
	
	private static Map<String,Object> decodeObject(JSONTokener token) throws JSONException{
		
		HashMap<String,Object> object = new HashMap<String,Object>();
		
		String key = null;
		
		while((key = decodeKey(token)) != null){
			
			if( token.nextClean() == ':' ){
			
				Object v = decodeValue(token);
				if(v != null){
					object.put(key, v);
				}
				char c = token.nextClean();
				
				if(c == ','){
					continue;
				}
				else if(c == '}'){
					break;
				}
				else{
					token.next('}');
				}
			}
			else{
				
				token.next('}');
				
				break;
			}
		}
		
		return object;
	}
	
	private static List<Object> decodeArray(JSONTokener token) throws JSONException{
		
		ArrayList<Object> list = new ArrayList<Object>();
		
		Object v = null;
		char c;
		
		while((v = decodeValue(token)) != null){
			
			list.add(v);
			
			c = token.nextClean();
			
			if(c != ',' ){
				
				if(c != ']'){
					token.next(']');
				}
				
				break;
			}
		}
		
		return list;
	}
	
	private static String decodeKey(JSONTokener token) throws JSONException{
		
		char c = token.nextClean();
		
		if(c == '"'){
			return token.nextString('"');
		}
		else if(c == '\''){
			return token.nextString('\'');
		}
		else if(c == '}' || c == ']' || c == 0){
			return null;
		}
		else if(c == ':'){
			
			token.back();
			
			return "";
		}
		else {
			String key = token.nextString(':');
			
			token.back();
			
			return key;
		}

	}
	
	public static class Null extends Number{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public double doubleValue() {
			return 0;
		}

		@Override
		public float floatValue() {
			return 0;
		}

		@Override
		public int intValue() {
			return 0;
		}

		@Override
		public long longValue() {
			return 0;
		}
		
	}
	
	public static void encodeObject(Object object,JSONStringer stringer) throws JSONException{

		if(object != null){
			
			if(object instanceof Map){
				
				stringer.object();
				
				@SuppressWarnings("unchecked")
				Map<String,Object> map = (Map<String,Object>)object;
				
				for(String key : map.keySet()){
					stringer.key(key);
					encodeObject(map.get(key),stringer);
				}
				
				stringer.endObject();
				
			}
			else if(object instanceof List){
				
				stringer.array();
				
				@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) object;
				
				for(Object v : list){
					encodeObject(v,stringer);
				}
				
				stringer.endArray();
			}
			else if(object instanceof Set){
				
				stringer.array();
				
				@SuppressWarnings("unchecked")
				Set<Object> list = (Set<Object>) object;
				
				for(Object v : list){
					encodeObject(v,stringer);
				}
				
				stringer.endArray();
			}
			else if(object instanceof cn.efunding.fund.common.IDataGetterValue) {
				
				stringer.object();
				
				for(String key : ((cn.efunding.fund.common.IDataGetterValue) object).keys()){
					
					Object v = ((cn.efunding.fund.common.IDataGetterValue) object).get(key, null);
					
					if(v != null) {
						stringer.key(key);
						encodeObject(v,stringer);
					}
					
				}
				
				stringer.endObject();
			}
			else if(object.getClass().isArray()){
				
				stringer.array();
				
				int c = Array.getLength(object);

				for(int i=0;i<c;i++){
					encodeObject(Array.get(object, i),stringer);
				}
				
				stringer.endArray();
			}
			else if(object instanceof Number) {
				stringer.value(object);
			}
			else if(object instanceof String) {
				stringer.value(object);
			}
			else if(object instanceof Boolean) {
				stringer.value(object);
			}
			else if(object instanceof Null) {
				stringer.value(null);
			}
			else{
				
				stringer.object();
				
				Class<?> clazz = object.getClass();
				Set<String> fields = new HashSet<String>(4);
				
				while(clazz != null && clazz != Object.class) {
					
					for(Field field : clazz.getFields()) {
						
						if(! fields.contains(field.getName())) {
							
							Object v = null;
							
							try {
								v = field.get(object);
							}
							catch(Throwable e) {}
							
							if(v != null) {
								stringer.key(field.getName());
								encodeObject(v,stringer);
							}
							
							fields.add(field.getName());
							
						}
						
					}
					
					clazz = clazz.getSuperclass();
				}
				
				stringer.endObject();

			}
		}
		else{
			stringer.value(object);
		}
	}
	
	public static String encodeObject(Object object) throws Throwable{
		
		if(object == null){
			return "null";
		}
		
		JSONStringer stringer = new JSONStringer();
	
		encodeObject(object,stringer);
		
		return stringer.toString();
	}
}
