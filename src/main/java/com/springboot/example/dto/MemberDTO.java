package com.springboot.example.dto;

import lombok.Setter;
import lombok.ToString;

import com.springboot.example.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor //기본생성자 생성
@ToString    //출력할 때 메서드 
@AllArgsConstructor
public class MemberDTO {
	
	private Long id;
	private String memberEmail;
	private String memberPassword;
	private String memberName;
	
	public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setId(memberEntity.getId());
		memberDTO.setMemberEmail(memberEntity.getMemberEmail());
		memberDTO.setMemberPassword(memberEntity.getMemberPassword());
		memberDTO.setMemberName(memberEntity.getMemberName());
		return memberDTO;
	}
}
