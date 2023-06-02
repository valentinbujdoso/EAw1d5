package cs544;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;

public class AppBook {
    private static EntityManagerFactory emf;

    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("cs544");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Book book1 = new Book("title1", "isbn1", "author1", 1.1, new Date(1,1,1));
        em.persist(book1);

        Book book2 = new Book("title2", "isbn2", "author2", 2.2, new Date(2,2,2));
        em.persist(book2);

        Book book3 = new Book("title3", "isbn3", "author3", 3.3, new Date(3,3,3));
        em.persist(book3);

        em.getTransaction().commit();
        em.close();

        em = emf.createEntityManager();
        em.getTransaction().begin();

        TypedQuery<Book> query = em.createQuery("from Book", Book.class);
        List<Book> bookList = query.getResultList();
        for(Book book: bookList){
            System.out.println(book);
        }

        em.getTransaction().commit();
        em.close();

        em = emf.createEntityManager();
        em.getTransaction().begin();

        query = em.createQuery("from Book", Book.class);
        bookList = query.getResultList();
        Book first = bookList.get(0);
        Book second = bookList.get(1);
        first.setTitle("newTitle");
        first.setPrice(4.4);
        em.merge(first);
        em.remove(second);
        em.getTransaction().commit();
        em.close();

        em = emf.createEntityManager();
        em.getTransaction().begin();

        query = em.createQuery("from Book", Book.class);
        bookList = query.getResultList();
        System.out.println("After modification: ");
        for(Book book: bookList){
            System.out.println(book);
        }

        em.getTransaction().commit();
        em.close();

    }
}
