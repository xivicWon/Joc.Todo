package com.testcode.apartment.service;

import com.testcode.apartment.domain.Member;
import com.testcode.apartment.exception.DuplicateEmailMemberException;
import com.testcode.apartment.repository.MemberRepository;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;


    @Test
    void join() {
        // Given
        Member member = new Member("won", "tes@test.com");

        // When
        memberService.join(member);

        // Then
        then(memberRepository).should().save(member);

    }

    @Test
    void join_duplicateEmail() {
        // Given
        Member member1 = new Member("won", "tes@test.com");
        given(memberRepository.findByEmail(any())).willReturn(Optional.of(member1));

        // When
        Member member2 = new Member("won", "tes@test.com");
        ThrowableAssert.ThrowingCallable throwingCallable = () -> memberService.join(member2);

        // Then
        assertThatThrownBy(throwingCallable).isInstanceOf(DuplicateEmailMemberException.class);
        then(memberRepository).should(never()).save(member2);
    }

    @Test
    void getMember() {
        //???
    }
}