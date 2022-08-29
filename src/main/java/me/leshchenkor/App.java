package me.leshchenkor;

import me.leshchenkor.entity.Event;
import me.leshchenkor.entity.Participant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class App {
    public static void main(String[] args) {
        SessionFactory sessionFactory = null;

        final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder().configure().build();

        try {
            sessionFactory = new MetadataSources(REGISTRY)
                    .addAnnotatedClass(Event.class)
                    .addAnnotatedClass(Participant.class)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception exc) {
            StandardServiceRegistryBuilder.destroy(REGISTRY);
        }
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(new Event("Screencast", new Date()));
        session.getTransaction().commit();
        session.close();

        session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Event").list();
        for (Event event : (List<Event>) result)
            System.out.println("Event ( " + event.getDate() + " ) " + event.getTitle());
        session.getTransaction().commit();
        session.close();

        session = sessionFactory.openSession();
        session.beginTransaction();
        Event event = session.load(Event.class, 1L);
        List<Participant> participants = getParticipants();
        for (Participant participant : participants) {
            session.save(participant);
        }
        event.setParticipantList(participants);
        session.save(event);
        session.getTransaction().commit();

         result = session.createQuery("from Event").list();
        for (Event iterable : (List<Event>) result)
            System.out.println("Event ( " + iterable.getDate() + " ) " + iterable.getTitle() +
                    " participants " + iterable.getParticipantList().size());
        session.close();

        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public static List<Participant> getParticipants() {
        return Arrays.asList(
                new Participant("Ivan", "Ivanoff"),
                new Participant("Inga", "Borge"),
                new Participant("John", "Malkovich"),
                new Participant("Brus", "Villis")
        );
    }
}
