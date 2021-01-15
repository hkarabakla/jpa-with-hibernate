package com.hkarabakla.config;

import com.hkarabakla.entity.Lesson;
import com.hkarabakla.entity.Student;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class JPAEntityManagerFactory {

    private static SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        try {
            Properties properties = getProperties();

            Configuration configuration = new Configuration();
            configuration.setProperties(properties);
            configuration.addAnnotatedClass(Lesson.class);
            configuration.addAnnotatedClass(Student.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            return sessionFactory;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }

    private static Properties getProperties() throws IOException {
        Properties properties = new Properties();
        URL propertiesURL = Thread.currentThread()
                .getContextClassLoader()
                .getResource("application.properties");
        try (FileInputStream inputStream = new FileInputStream(propertiesURL.getFile())) {
            properties.load(inputStream);
        }
        return properties;
    }
}
