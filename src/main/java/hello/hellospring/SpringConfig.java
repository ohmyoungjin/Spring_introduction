package hello.hellospring;
import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

     //DB env 생성자로 받아서 가지고 오는 로직 부분
       private final DataSource dataSource;
        @Autowired
        public SpringConfig(DataSource dataSource) {
            this.dataSource = dataSource;
        }
    /* JPA 사용할 때는 EntityManager를 설정해줘야 한다 ( DB 정보를 주입해주는 부분) */
//   private EntityManager em;
//
//    @Autowired
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }
    //springConfig 파일 자체의 생성자 어떤 구현체를 주입받을지 선언하는 부분
    //생성자로 어떤 인터페이스를 사용할건지 적용 public SpringConfig => memberRepository()호출됨과 어떤 구현체를 사용할건지 return
    //이후에 memberRepository init 됨 ( private final MemberRepository memberRepository )
    //이후에 MemberService에서 사용할건지 넣어주게 됨 ( public MemberService memberService() )
    //*** return new MemberService(memberRepository()); 이 부분에서 MemoryMemberRepository를 구현체로 쓰게 되면
    //굳이 ()를 안붙여도 되는데 나머지 3개를 쓰려면 ()를 붙여줘야한다 .. 이거 잘 모르겠다 .. 공부하자
    //실행순서 넣어둠
    //3
//    private final MemberRepository memberRepository;
//    //1
//    public SpringConfig(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
    //4
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

//    @Bean
//    public TimeTraceAop timeTraceAop() {
//        return new TimeTraceAop();
//    }
    //2
    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
         return new JdbcMemberRepository(dataSource);
//         return new JdbcTemplateMemberRepository(dataSource);
//        return new JpaMemberRepository(em);
    }
}