package com.testcode.apartment.service;

import com.testcode.apartment.domain.Member;
import com.testcode.apartment.exception.DuplicateEmailMemberException;
import com.testcode.apartment.exception.NoExistsMemberException;
import com.testcode.apartment.repository.MemberRepository;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberServiceSpringBootTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;


    @Test
    void join() {
        // BDD Style
        // Given
        Member member = new Member("won", "tes@test.com");

        // When
        memberService.join(member);

        // Then - when 에 대한 값 체크.
        assertThat(member.getId()).isNotNull();
        assertThat(member.getId()).isPositive();

        Optional<Member> result = memberRepository.findById(member.getId());
        assertThat(result).isPresent();

    }

    @Test
    void join_DuplicateEmail() {

        // BDD Style
        // Given
        Member member1 = new Member("won", "tes@test.com");
        memberService.join(member1);

        Member member2 = new Member("won", "tes@test.com");

        // When
        ThrowableAssert.ThrowingCallable callable = () -> memberService.join(member2);

        // Then
        assertThatThrownBy(callable)
                .isInstanceOf(DuplicateEmailMemberException.class);
    }

    @Test
    void getMember() {
        // BDD Style
        // Given
        Member member1 = new Member("won", "tes@test.com");
        memberService.join(member1);

        // When
        Member member = memberService.getMember(member1.getId());

        // Then
        assertThat(member.getName()).isEqualTo(member1.getName());
        assertThat(member.getEmail()).isEqualTo(member1.getEmail());
    }


    @Test
    void getMember_noExist() {
        // BDD Style
        // Given
        int noExistMemberId = 0;

        // When
        ThrowableAssert.ThrowingCallable callable = () -> memberService.getMember(noExistMemberId);

        // Then
        assertThatThrownBy(callable)
                .isInstanceOf(NoExistsMemberException.class);

    }


}