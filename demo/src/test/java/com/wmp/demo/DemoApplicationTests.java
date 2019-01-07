package com.wmp.demo;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wmp.demo.service.DemoService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private DemoService demoService;
	
	@Test
	public void contextLoads() throws IOException {
		//
		String url = "http://www.naver.com";
		//String url = "<script>a<b><a>bc</script><div>defg</div><abc>123</abc><div>";
		Map<String, String> result = demoService.convert(url, "TXT", 50);
		System.out.println("==================");
		System.out.println(result.get("html"));
		System.out.println("==================");
		System.out.println(result.get("share"));
		System.out.println("==================");
		System.out.println(result.get("remain"));
		System.out.println("==================");
	}
}

