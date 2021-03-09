package com.sktelecom.swingmsa.mcatalog.context.util;

import java.beans.FeatureDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Transient;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.sktelecom.swingmsa.mcatalog.context.base.enums.EnumModel;

/**
 * 
 * @author P123113
 *
 */
public class Utils {
	
	/**
	 * 
	 * @param source
	 * @return
	 */
	public static String[] getNullPropertyNames(Object source) {
	    final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
	    return Stream.of(wrappedSource.getPropertyDescriptors())
	            .map(FeatureDescriptor::getName)
	            .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
	            .toArray(String[]::new);
	}
	
	/**
	 * 
	 * @param <T>
	 * @param enumClass
	 * @param enumValue
	 * @return
	 */	
	public static <T extends Enum<T> & EnumModel> String getEnumValue(Class<T> enumClass, T enumValue) {
		T rtn = EnumSet.allOf(enumClass).stream()
				.filter(v -> v.equals(enumValue))
				.findFirst().orElse(null);
		return rtn==null?"":rtn.getValue();
	}
	
	/**
	 * 
	 * @param map
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<?> paramToList(Object map, Class<?> entity) throws Exception {
		
		List<Map<String, Object>> input;
		List<Object> rtnList = new ArrayList<Object>();
		
		if(map instanceof List) {
			input = (List<Map<String, Object>>) map;
		} else {
			input = new ArrayList<>();
			input.add((Map<String, Object>) map);
		}
				
		for(Map<String, Object> m : input) {
			if(!m.isEmpty()) {
				Object info = entity.newInstance();
				castFromMap(info, m);
				rtnList.add(info);
			}
		}
		
		return rtnList;
	}
	
	/**
	 * 
	 * @param entity
	 * @param input
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void castFromMap(Object entity, Map<String, Object> input) throws Exception {
		Field[] fields = entity.getClass().getDeclaredFields();
		for(Field f : fields) {
			Object val = input.get(f.getName());
			if(val != null) {
				String colValue = val.toString();
				
				f.setAccessible(true);
				if(f.getType().isEnum()) {
					f.set(entity, Enum.valueOf((Class<Enum>) f.getType(), colValue));						
				} else {
					if(f.getAnnotation(Transient.class) == null) {						

						if(colValue.equals("") && (f.getType().equals(Integer.class) || f.getType().equals(Long.class))) {
							Column column = f.getAnnotation(Column.class);
							if(column != null && !column.nullable() ) {
								f.set(entity, Class.forName(f.getType().getName()).getConstructor(String.class).newInstance("0"));
							}
						} else {
							f.set(entity, Class.forName(f.getType().getName()).getConstructor(String.class).newInstance(colValue));
						}
					}
				}
				f.setAccessible(false);
			}
		}
	}
}
