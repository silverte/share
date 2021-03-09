package com.sktelecom.swingmsa.mcatalog.context.base.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sktelecom.swingmsa.mcatalog.context.base.enums.EnumValue;
import com.sktelecom.swingmsa.mcatalog.context.base.exception.BizException;
import com.sktelecom.swingmsa.mcatalog.context.mapper.EnumMapper;

@RestController
@RequestMapping("/api/v1")
public class ShareRestController {
	
	@Autowired
	private EnumMapper enumMapper;
		
	@PostMapping("/enumListAll")
	public Map<String, Object> getAllEnums(@RequestBody Map<String, Object> params) {	
		
		Map<String, Object> result = new HashMap<>();
		Map<String, String> message = new HashMap<>();
		
		try {
			
			Map<String, List<EnumValue>> enumList = enumMapper.getAllEnum();		
			
			result.put("output1", enumList);
						
	        message.put("code", "0");
	        message.put("message", "정상 처리되었습니다.");
	 
	        result.put("error", message);
		} catch(Exception e) {
			throw new BizException(e);
		}
		
		return result;
	}
	
	@PostMapping("/enumList")
	@SuppressWarnings("unchecked")
	public Map<String, Object> getEnum(@RequestBody Map<String, Object> params) {
		
		Map<String, Object> result = new HashMap<>();
		Map<String, String> message = new HashMap<>();
		
		try {
			Map<String, Object> input1 = (Map<String, Object>) params.get("input1");
			String colNm = input1.get("colNm").toString();
			
			Map<String, List<EnumValue>> enumList = enumMapper.getEnum(colNm);
			
			result.put("output1", enumList);
			
	        message.put("code", "0");
	        message.put("message", "정상 처리되었습니다.");
	 
	        result.put("error", message);
		} catch(Exception e) {
			throw new BizException(e);
		}
		
		return result;
	}
}
