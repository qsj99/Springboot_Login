package com.springboot.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springboot.example.dto.MemberDTO;
import com.springboot.example.entity.MemberEntity;
import com.springboot.example.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	
	public void save(MemberDTO memberDTO) {
		//1. dto -> entity 변환 (memberEntity에서)
		MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
		//2. repository의 save 메서드 호출/ save는 jpa에서 제공
		memberRepository.save(memberEntity);
		
		//repository의 save메서드 호출 (조건: entity 객체를 넘겨줘야 함) 
		
	}

	public MemberDTO login(MemberDTO memberDTO) {
		//1. 회원이 입력한 이메일로 DB에서 조회
		//2. DB에서 조회한 비밀번호가 사용자가 입력한 비밀번호가 일치하는지 판단
		Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
		if(byMemberEmail.isPresent()) {
			//조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다)
		MemberEntity memberEntity =	byMemberEmail.get();
		if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
			//비민번호 일치
			//entity -> dto 변환 후 리턴
			MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
			return dto;
		}else {
			//비민번호 불일치(로그인 실패)
			return null;
		}
		}else {
			//조회 결과가 없다(해당 이메일 가진 회원이 없다)
			return null;
		}
	}

	public List<MemberDTO> findAll() {
		List<MemberEntity>memberEntityList = memberRepository.findAll();
		List<MemberDTO>memberDTOList = new ArrayList<>();
		for(MemberEntity memberEntity: memberEntityList) {
			memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
		//	MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
		//	memberDTOList.add(memberDTO);
		}
		return memberDTOList;
	}

	public MemberDTO findById(Long id) {
		Optional<MemberEntity>optionalMemberEntiry = memberRepository.findById(id);
		if(optionalMemberEntiry.isPresent()) {
			
//          MemberEntity memberEntity = optionalMemberEntity.get();  ==> Entity 타입으로 바꾸고 
//          MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity); ==> toMemberDTO를 통해 DTO로 바꾸고 
//          return memberDTO;   ==> DTO리턴
										//optional로 감싸져있는걸 까고  dto로 받아주고 컨트롤러로 전달?
			return MemberDTO.toMemberDTO(optionalMemberEntiry.get());

		}else {
			return null;
		}
	}

	public MemberDTO updateForm(String myEmail) {
//		아이디를 통해서 내 정보가 있는지 확인하는 것이기 때문에 
//		만들어놨던 findByMemberEmail 사용 
		Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
		if(optionalMemberEntity.isPresent()) {
			return MemberDTO.toMemberDTO(optionalMemberEntity.get());
		}else
		return null;
	}

	public void update(MemberDTO memberDTO) {
//		memberRepository에는 update메서드 없어 그래서 save메서드를 또 써야하는데 
//		save 메서드는 id값이 없으면 insert쿼리문을 실행 
//		id값이 있다면 update 쿼리문을 실행함 !! 
		memberRepository.save(MemberEntity.toupdateMemberEntity(memberDTO));
		
	}

	public void deleteById(Long id) {
		memberRepository.deleteById(id);
		
	}








}











