package com.springboot.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.example.entity.MemberEntity;

@Repository 
// <>안에는 어떤 entity인지, pk의 타입 적어주면 됨 
public interface MemberRepository extends JpaRepository<MemberEntity, Long>{
	//이메일로 회원 정보 조회 (select * from member_table where memberEmail = ? )
	//Optional: null방지, 보통 optional로  감싸서 보냄 
	Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
