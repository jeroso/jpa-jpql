package jpql;

import javax.persistence.*;
import java.awt.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory ef = Persistence.createEntityManagerFactory("hello");
        EntityManager em = ef.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
/*
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);
            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(20);
            em.persist(member2);
*/
            /**
             * 타입정보가 명확할때 TypedQuery, 명확하지 않을때 Query
             */
/*
            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
            Query query3 = em.createQuery("select m.username, m.age from Member m");
            List<Member> resultList = em.createQuery("select m from Member m", Member.class).getResultList();
            for (Member member1 : resultList) {
                System.out.println("member1.getUsername() = " + member1.getUsername());
            }
            // 결과가 없거나 많게되면 Exception 발생하므로 하나일때만 사용해야 함.
            Member singleResult = em.createQuery("select m from Member m where m.id = 1L", Member.class).getSingleResult();
            System.out.println("singleResult = " + singleResult.getAge());

            Member singleResult1 = em.createQuery("select m from Member m where m.username=:username", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();
            System.out.println("singleResult1.getUsername = " + singleResult1.getUsername());
            //프로젝션
            List<MemberDTO> result = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();
            MemberDTO memberDTO = result.get(0);
            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
            System.out.println("memberDTO.getAge() = " + memberDTO.getAge());
*/
            /**
             * 페이징 처리 - setFirstResult, setMaxResult
             */
    /*        for (int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setUsername("member" + i);
                member.setAge(i);
                em.persist(member);
            }
            String query = "select m from Member m order by m.id desc";
            List<Member> result = em.createQuery(query, Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();
            System.out.println(result.size());
            for (Member member : result) {
                System.out.println(member);
            }
    */
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
//            member.setAge(35);
//            member.setType(MemberType.ADMIN);
            member1.changeTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
//            member.setAge(35);
//            member.setType(MemberType.ADMIN);
            member2.changeTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.changeTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            /**
             * 조인
             */
//            String query = "select m from Member m join m.team t where t.name = :teamName";
//            List<Member> result = em.createQuery(query, Member.class)
//                    .setParameter("teamName", "team1")
//                    .getResultList();
//            System.out.println("result = " + result);

//            String query = "select m.username, 'HELLO', TRUE from Member m " +
////                    "where m.type= jpql.MemberType.ADMIN";
//                    "where m.type = :userType";
//            List<Object[]> result = em.createQuery(query)
//                    .setParameter("userType", MemberType.USER)
//                    .getResultList();
//
//            for (Object[] objects : result) {
//                System.out.println("objects[0] = " + objects[0]);
//                System.out.println("objects[1] = " + objects[1]);
//                System.out.println("objects[2] = " + objects[2]);
//            }

            /**
             * case 식
             */
//            String query =
//                    "select " +
//                            "case when m.age <= 10 then '학생요금' " +
//                            "     when m.age >= 60 then '경로요금' " +
//                            "     else '일반요금' " +
//                            "end " +
//                            "from Member m";
//            List<String> result = em.createQuery(query, String.class)
//                    .getResultList();
//            for (String s : result) {
//                System.out.println("s = " + s);
//            }

            /**
             * 조건식 case 식 - coalesce, nullif
             */
//            String query = "select coalesce(m.username,'이름 없는 회원') from Member m";
//            String query = "select nullif(m.username,'관리자') from Member m";
//            List<String> result = em.createQuery(query, String.class)
//                    .getResultList();
//            for (String s : result) {
//                System.out.println("s = " + s);
//            }

            /**
             * JPQL 기본 함수 - concat, substring, trim, lower,upper, length, locate, abs, sqrt, mod, size, index(JPA 용도)
             */
//            String query = "select concat('a','b') From Member m";
/*
            String query = "select locate('de','abcdefg') from Member m";   // 뒤에 인자에서 앞에 인자를 찾는다. 출력 : 4
            List<Integer> result = em.createQuery(query, Integer.class)
                    .getResultList();
            for (Integer integer : result) {
                System.out.println("integer = " + integer);
            }
*/
/*
            String query = "select size(t.members) from Team t";

            List<Integer> result = em.createQuery(query, Integer.class)
                    .getResultList();
            for (Integer integer : result) {
                System.out.println("integer = " + integer);
            }
*/
            /**
             * join fetch : SQL 조인 종류 X
             * JPQL에서 성능최적화를 위해 제공하는 기능 : 연관된 엔티티나 컬렉션을 SQL 한번에 함께 조회하는 기능
             */

            /**
             * FetchType.Lazy
             * 회원1, 팀A (SQL)
             * 회원2, 팀A (1차 캐시)
             * 회원3, 팀B (SQL)
             * 회원이 100명인 경우 -> N + 1 문제
             */
            // String query = "select m from Member m";
            /**
             * N + 1 문제를 해결하기 위해 join fetch 를 사용 하여 한번의 쿼리로 가져옴
             * join fetch 로 가져온 값은 프록시 데이터가 아닌 실제 데이터 이다.
             */
            String query = "select m from Member m join fetch m.team";
            List<Member> result = em.createQuery(query, Member.class)
                    .getResultList();

            for (Member member : result) {
                System.out.println("member : "  + member.getUsername());
                System.out.println("team : " + member.getTeam().getName());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            tx.commit();
            em.close();
        }
    }
}
