package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//annotation을 지정을 해줘야 spring container에 올라가게 되고 관리 된다.

public class MemberService {

    private final MemberRepository memberRepository ;


    //내가 어떤 구현체를 주입 받을건지 정의해주는 부분
    //필드 주입 , 생성자 주입 , setter 주입 세 가지가 있는데 이 중 생성자 주입이 제일 많이 사용 된다. setter 주입 같은 경우 public으로 선언을 해서 모든 사람들이 접근이 가능하게 돼서 사용하지 않음
    public MemberService(MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
        System.out.println("memberService : " + memberRepository.getClass());
    }
    /**
     * 회원 가입
     */
    public Long join(Member member) {
        //같은 이름있는 중복 회원 X
            validateDuplicateMember(member);
            memberRepository.save(member);
            return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원 입니다.");
            });
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
