package com.springboot.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.springboot.example.dto.MemberDTO;
import com.springboot.example.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	//생성자 주입 
	private final MemberService memberService;
	
	@GetMapping("/member/save")
	public String saveForm() {
		return "save";
	}
	
	@PostMapping("member/save")
	public String save(@ModelAttribute MemberDTO memberDTO) {
		System.out.println(memberDTO);
		memberService.save(memberDTO);
		return "login";
	}
	
	@GetMapping("/member/login")
	public String loginForm() {
		return "login";
	}
	
	@PostMapping("/member/login")
	public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
		MemberDTO loginResult = memberService.login(memberDTO);
		if(loginResult != null) {
			//로그인 성공 
			//로그인한 정보 세션에 담아줌  "loginEmail"은 main.html에서 타임리프 쓸 때 사용함
			session.setAttribute("loginEmail", loginResult.getMemberEmail());
			return "main";
		}else {
			//로그인 실패
			return "login";
		}
	}
	
	//리스트, db에 있는거 모든거 가져온다
	@GetMapping("/member/")
	public String findAll(Model model) {
		//여러명 조회하는거니까 List 타입으로 리턴 
		List<MemberDTO> memberDTOList = memberService.findAll();
		//어떠한 html로 가져갈 데이터가 있다면 model사용  
		//"memberList"는 list.html에서 사용 , 담은것 html에서 보여주기 
		model.addAttribute("memberList", memberDTOList);
		return "list";
	}
	
	@GetMapping("/member/{id}")
							//경로상의 값을 가져올때는 @pathvariable 사용
							//아이디에 해당하는 값을 db에서 확인 후 다시 화면으로 가져가야 함 , 그래서 model이 필요함 
	public String findById(@PathVariable Long id, Model model) {
		//한명이니까 DTO타입으로 리턴 
		MemberDTO memberDTO = memberService.findById(id);
		model.addAttribute("member", memberDTO);
		return "detail";
	}
	
	
	@GetMapping("/member/update")
							// 내 정보 : 세션에 있는 이메일 값을 통해서 db에서 정보를 가져옴, 이걸 model에 담아 화면에 보여줌
	public String updateForm(HttpSession session, Model model) {
										//getAttribute는 Object형 => 강제 형변환해줌
		String myEmail = (String) session.getAttribute("loginEmail");
		MemberDTO memberDTO = memberService.updateForm(myEmail);
							//updateMember는 html에 value에 ${updateMember.memberId} 이런식으로 씀 
		model.addAttribute("updateMember", memberDTO);
		return "update";
	}
	
	@PostMapping("/member/update")
	public String update(@ModelAttribute MemberDTO memberDTO) {
		memberService.update(memberDTO);
		//수정한 뒤에 다시 상세보기를 띄우기 위해 redirect사용?
		return "redirect:/member/" + memberDTO.getId();
	}
	
	@GetMapping("/member/delete/{id}")
	public String deleteById(@PathVariable Long id) {
		memberService.deleteById(id);
		return "redirect:/member/";
	}
	
	@GetMapping("/member/logout")
	public String logout(HttpSession session) {
			//invalidate:세션 무효화?
		session.invalidate();
		return "index";
		
	}
	
	
	
	
	
	
	
}
