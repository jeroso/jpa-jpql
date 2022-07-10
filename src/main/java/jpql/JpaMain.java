package jpql;

import javax.persistence.*;
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

            // 타입정보가 명확할때 TypedQuery, 명확하지 않을때 Query
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
            // 페이징
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
            Team team = new Team();
            team.setName("team1");
            em.persist(team);
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(35);
            member.setType(MemberType.ADMIN);
            member.changeTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

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

            String query =
                    "select " +
                            "case when m.age <= 10 then '학생요금' " +
                            "     when m.age >= 60 then '경로요금' " +
                            "     else '일반요금' " +
                            "end " +
                            "from Member m";
            List<String> result = em.createQuery(query, String.class)
                    .getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            tx.commit();
            em.close();
        }
    }
}
