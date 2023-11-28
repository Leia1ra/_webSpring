package com.multi.campus;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelExtensionsKt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

// 현재 클래스 Controller가 되기 위해서는 public class 이전에 @Controller, @RestController표기하면 된다.
@Controller
public class TestController {


	
	// 매핑 - @RequestMapping(get방식 접속, post방식 접속),
	// 매핑 - @GetMapping(get방식 접속), @PostMapping(post방식 접속)
	@RequestMapping("/test1")	// /campus/test1
	public String test1(HttpServletRequest req, Model model) {
		// mapping되는 메소드의 매개변수 requset변수를 선언하여야 한다.
		String num = req.getParameter("num");
		String name = req.getParameter("name");
		System.out.println("서버에 넘어온 데이터 : "+num+", "+name);
		
		// 서버에서 뷰로 데이터 보내기
		// 매개 변수 model에 뷰로 보낸 데이터 기록하기
		model.addAttribute("data1", "goguma");
		model.addAttribute("data2", "123456");

		return "home";
	}
	
	@RequestMapping(value="/test2", method=RequestMethod.GET)
	public String test2(String name, int num, Model m) {
		System.out.println("이름 : " + name);
		System.out.println("번호 : " + (num+100));
		m.addAttribute("data1", "세종대왕");
		m.addAttribute("data2", 9999);
		
		return "home";
	}
	
	@GetMapping("/test3")
	public ModelAndView test3(TestVO vo) {
		System.out.println("num => " + vo.getNum());
		System.out.println("name => " + vo.getName());
		System.out.println("tel => " + vo.getTel());
		
		// Model(데이터)과 View(뷰페이지 이름)을 함께 처리하는 클래스
		ModelAndView mav = new ModelAndView();
		mav.addObject("data1", "seoul");
		mav.addObject("data2", "7777");
		
		mav.setViewName("home");
		
		return mav;
	}

	// @PostMapping("/test4")
	@RequestMapping(value = "/test4", method = RequestMethod.POST)
	public ModelAndView test4(String productName, int price){
		System.out.println("상품명 -> " + productName);
		System.out.println("가격   -> " + price);
		double p = price - (price*0.1);
		ModelAndView mav = new ModelAndView();
		mav.addObject("pName", productName);
		mav.addObject("sales", p);

		mav.setViewName("home");
		return mav;
	}
	@GetMapping("/test5")
	public ModelAndView test5Test(float testNo){
		ModelAndView mav = new ModelAndView();
		System.out.println(testNo);

		mav.setViewName("home");
		return mav;
	}
}
