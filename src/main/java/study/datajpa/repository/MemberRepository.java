package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name)"
         + " from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    List<Member> findListByUsername(String username);   // 값이 없으면 빈 result를 반환한다
    Member findMemberByUsername(String username);   // 값이 없으면 null. JOA는 Exception이 발생,결과가 2개 이상이면 에러
    Optional<Member> findOptionalByUsername(String username);


//    public List<Member> findByPage(int age, int offset, int limit) {
//        return em.createQuery("select m from Member m where m.age = :age order by m.username desc", Member.class)
//                .setParameter("age", age)
//                .setFirstResult(offset)
//                .setMaxResults(limit)
//                .getResultList();
//    }
//
//    public long totalCount(int age) {
//        return em.createQuery("select count(m) from Member m where m.age = :age", Long.class)
//                .setParameter("age", age)
//                .getSingleResult();
//    }

    Page<Member> findByAge(int age, Pageable pageable);
}
