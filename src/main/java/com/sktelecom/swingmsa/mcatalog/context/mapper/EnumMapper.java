package com.sktelecom.swingmsa.mcatalog.context.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sktelecom.swingmsa.mcatalog.context.base.enums.EnumModel;
import com.sktelecom.swingmsa.mcatalog.context.base.enums.EnumValue;

@Component
public class EnumMapper {
	private Map<String, List<EnumValue>> mapFactory = new HashMap<>();
		
	private static List<EnumValue> toEnumValues(Class<? extends EnumModel> e) {
		return Arrays.stream(e.getEnumConstants())
				.map(EnumValue::new)
				.collect(Collectors.toList());
	}
	
	public void put(String key, Class<? extends EnumModel> e) {
		mapFactory.put(key, toEnumValues(e));
	}
	
	public Map<String, List<EnumValue>> getAllEnum() {
		return mapFactory;		
	}
	
	public Map<String, List<EnumValue>> getEnum(String keys) {		
		
		return Arrays.stream(keys.split(","))
				.collect(Collectors.toMap(
						Function.identity(), 
						key -> {
							List<EnumValue> map = mapFactory.get(key);
							if(map == null) {
								map = new ArrayList<EnumValue>(Arrays.asList(new EnumValue()));
							}
							return map;
						}));
	}
}
