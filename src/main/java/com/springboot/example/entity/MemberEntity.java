package com.springboot.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.springboot.example.dto.MemberDTO;

import lombok.Getter;
import lombok.Setter;

@SequenceGenerator(
	    name = "MEMBER_IDX_SEQ_GEN",
	    sequenceName = "MEMBER_IDX_SEQ",
	    initialValue = 1,
	    allocationSize = 1		
	)


//entity 클래스는 테이블 역할
@Entity
@Getter
@Setter
@Table(name = "member_table")
public class MemberEntity {
	
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "MEMBER_IDX_SEQ_GEN"
    )
    private Long id;
    
    @Column(unique = true)
    private String memberEmail;
    
    @Column
    private String memberPassword;
    
    @Column
    private String memberName;
    
    //save를 위해 작성 
    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
    	MemberEntity memberEntity = new MemberEntity();
    	memberEntity.setMemberEmail(memberDTO.getMemberEmail());
    	memberEntity.setMemberPassword(memberDTO.getMemberPassword());
    	memberEntity.setMemberName(memberDTO.getMemberName());
    	return memberEntity;
    }
    
    //update를 위해 작성 , id추가
    public static MemberEntity toupdateMemberEntity(MemberDTO memberDTO) {
    	MemberEntity memberEntity = new MemberEntity();
    	memberEntity.setId(memberDTO.getId());
    	memberEntity.setMemberEmail(memberDTO.getMemberEmail());
    	memberEntity.setMemberPassword(memberDTO.getMemberPassword());
    	memberEntity.setMemberName(memberDTO.getMemberName());
    	return memberEntity;
    }
    
    
    
    
    
    
    
    
    
    
    
    
	
}
