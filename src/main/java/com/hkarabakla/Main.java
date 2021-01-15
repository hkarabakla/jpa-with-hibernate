package com.hkarabakla;

import com.hkarabakla.config.JPAEntityManagerFactory;
import com.hkarabakla.entity.Gender;
import com.hkarabakla.entity.Lesson;
import com.hkarabakla.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.gen5.api.Assertions.assertFalse;

public class Main {

    public static void main(String[] args) {

        JPAEntityManagerFactory dbEntityManager = new JPAEntityManagerFactory();

        Lesson math = new Lesson();
        Lesson science = new Lesson();

        Student s1 = new Student();
        Student s2 = new Student();
        Student s3 = new Student();

        math.setName("Math");
        science.setName("Science");

        s1.setName("Student-1");
        s1.setGender(Gender.FEMALE);
        s1.setBirthDate(LocalDate.of(1998, 1, 1));

        s2.setName("Student-2");
        s2.setGender(Gender.MALE);
        s2.setBirthDate(LocalDate.of(1999, 10, 21));

        s3.setName("Student-3");
        s3.setGender(Gender.FEMALE);
        s3.setBirthDate(LocalDate.of(1994, 3, 12));

        math.setStudents(new HashSet<>(Arrays.asList(s1, s2)));
        science.setStudents(new HashSet<>(Arrays.asList(s1, s3)));


        try (SessionFactory sessionFactory = dbEntityManager.getSessionFactory();
             Session session = sessionFactory.openSession();) {

            // Math ve Science derslerini kaydedelim
            session.getTransaction().begin();
            session.save(math);
            session.save(science);
            session.getTransaction().commit();
            /*
             * Lesson sinifinda students degiskeninde cascade = CascadeType.PERSIST oldugu icin bu noktada
             * ogrenciler de veritabanina kaydedildi. Simdi bunu kontrol edelim.
             * */
            assertFalse(math.getStudents().stream().findFirst().get().getId() == null);
            assertFalse(science.getStudents().stream().findFirst().get().getId() == null);

            session.clear(); // s1FromDB objesini session contexten almak yerine tamamen veritabani tablosundan cekmek
                             // icin gerekli
            Student s1FromDB = session.find(Student.class, s1.getId());
            /*
            * Student sinifinda registeredLessons degiskeni uzerinde fetch = FetchType.EAGER oldugu icin ogrenciyi cekerken
            * kayit oldugu dersleri tutan registeredLessons objesi de veritabanindan degeerler cekilerek doldurulur
            * */
            System.out.println(s1FromDB);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
