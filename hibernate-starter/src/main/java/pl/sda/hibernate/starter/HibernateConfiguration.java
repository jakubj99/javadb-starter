package pl.sda.hibernate.starter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import pl.sda.commons.Utils;
import pl.sda.hibernate.starter.entities.CourseEntity;
import pl.sda.hibernate.starter.entities.StudentEntity;

public class HibernateConfiguration {
    public static void main(String[] args) {
        /**
         * Krok 1: Konfiguracja Hibernate - ustawiamy parametry Hibernate (dostęp do bazy danych, parametry, cache itp)
         */
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate-2.cfg.xml")
                /**
                 * Uwaga! ustawiając parametry przez applySetting dodajemy prefix hibernate.* do nazwy parametru !
                 * ustawiając w ten sposób parametry nadpisujemy parametry z pliku
                 */
                //.applySetting("hibernate.show_sql", false)
                //.applySetting("hibernate.connection.username", "not-user")
                .build();

        /**
         * Krok 2: Konfiguracja Hibernate - ustawiamy mapowania klas-encji
         */
        Metadata metadata = new MetadataSources(registry)
                /**
                 * Można dodać pojedynczą klasę-encję
                 */
                .addAnnotatedClass(CourseEntity.class)
                .addAnnotatedClass(StudentEntity.class)
                .buildMetadata();

        try(SessionFactory sessionFactory = metadata.buildSessionFactory();
            Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();

            CourseEntity courseEntity = new CourseEntity("JavaGda11", "Sopot", Utils.parse("2018-01-01"), Utils.parse("2018-09-01"));
            session.save(courseEntity);

            StudentEntity studentEntity = new StudentEntity("Jarek", 3, "OK", "1.1.1");
            session.save(studentEntity);

            transaction.commit();
        }
    }
}
