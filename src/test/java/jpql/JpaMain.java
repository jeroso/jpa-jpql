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

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            tx.commit();
            em.close();
        }
    }
}