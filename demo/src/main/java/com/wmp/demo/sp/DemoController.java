package com.wmp.demo.sp;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wmp.demo.service.DemoService;

@Controller
public class DemoController {
	//
	@Autowired
	private DemoService demoService;
	
	@GetMapping("/")
	public String index() {
		//
		return "demo";
	}
	
	@ResponseBody
	@GetMapping("/test")
	public Map<String, String> test(@RequestParam String url, @RequestParam String type, @RequestParam Integer unit) throws IOException {
		//
		Map<String, String> result = demoService.convert(url, type, unit);
		return result;
	}
}
