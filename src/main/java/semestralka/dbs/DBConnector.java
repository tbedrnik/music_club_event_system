package semestralka.dbs;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import semestralka.dbs.entities.*;
import semestralka.dbs.entities.embeddable.*;

public final class DBConnector {
    
    private static DBConnector INSTANCE;
    
    private static EntityManagerFactory emf;
    private static EntityManager em;
    
    private DBConnector() {}
    
    public static void setInstance(String PUname) {
        if(INSTANCE==null) {
            emf = Persistence.createEntityManagerFactory(PUname);
            em = emf.createEntityManager();
            INSTANCE = new DBConnector();
            LOG.log(Level.SEVERE, "Persistence unit has been set to {0}", PUname);
        } else {
            LOG.log(Level.SEVERE, "Persistance unit has already been set.");
        }
    }
    
    private static final Logger LOG = Logger.getLogger(DBConnector.class.getName());

    public static DBConnector getInstance() {
        return INSTANCE;
    }
    
    public EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * Accepts JPA Entity object and persists it to databse.
     * 
     * @param o
     */
    public void save(Object o) {
        begin();
        em.persist(o);
        commit();
    }
    
    public void delete(Object o) {
        begin();
        em.remove(o);
        commit();
    }
    
    public void begin() {
        em.getTransaction().begin();
    }
    
    public void commit() {
        em.getTransaction().commit();
    }
    
    /**
     * Gets all clubs from database.
     * @return List of all clubs.
     */
    public List<Club> getClubs() {
        return em.createQuery(
            "SELECT c FROM Club c",
            Club.class
        ).getResultList();
    }
    
    /**
     * Gets names of clubs.
     * @return List of clubs names.
     */
    public List<String> getClubNames() {
        return em.createQuery(
            "SELECT c.name FROM Club c"
        ).getResultList();
    }
    
    /**
     * Counts all clubs in database.
     * @return Total number of clubs.
     */
    public String getClubsTotal() {
        return em.createQuery(
                "SELECT COUNT(c) FROM Club c"
            ).getSingleResult().toString();
    }
    
    /**
     * Gets all people from database.
     * @return List of all people.
     */
    public List<Person> getPeople() {
        return em.createQuery(
            "SELECT p FROM Person p",
            Person.class
        ).getResultList();
    }
    
    /**
     * Counts all people in database.
     * @return Total number of people.
     */
    public Integer getPeopleTotal() {
        return Integer.valueOf(
            em.createQuery(
                "SELECT COUNT(p) FROM Person p"
            ).getSingleResult().toString()
        );
    }
    
    /**
     * Counts all employees in database.
     * @return Total number of employees.
     */
    public Integer getEmployeesTotal() {
        return Integer.valueOf(
            em.createQuery(
                "SELECT COUNT(p) FROM Employee p"
            ).getSingleResult().toString()
        );
    }
    
    /**
     * Counts all musicians in database.
     * @return Total number of musicians.
     */
    public Integer getMusiciansTotal() {
        return Integer.valueOf(
            em.createQuery(
                "SELECT COUNT(p) FROM Musician p"
            ).getSingleResult().toString()
        );
    }
    
    /**
     * Gets all bands from database.
     * @return List of all bands.
     */
    public List<Band> getBands() {
        return em.createQuery(
            "SELECT b FROM Band b",
            Band.class
        ).getResultList();
    }
    
    /**
     * Counts all bands in database.
     * @return Total number of bands.
     */
    public Integer getBandsTotal() {
        return Integer.valueOf(
            em.createQuery(
                "SELECT COUNT(b) FROM Band b"
            ).getSingleResult().toString()
        );
    }
    
    /**
     * Returns number of famous bands (bands having more than 10 shows).
     * @return Number of famous bands.
     */
    public Integer getFamousBandsTotal() {
        return Integer.valueOf(
            em.createQuery(
                "SELECT COUNT(b) FROM Band b JOIN Show s ON b.name = s.keyId.band GROUP BY b.name HAVING COUNT(s) > 10"
            ).getSingleResult().toString()
        );
    }
    
    /**
     * Returns list of events with date equals of after today.
     * @return List of future events.
     */
    public List<Event> getFutureEvents() {
        return em.createQuery(
                "SELECT e FROM Event e WHERE e.id.date >= CURRENT_DATE"
        ).getResultList();
    }
    
    public void deleteEvent(Event e) {
        begin();
        getShowsAtEvent(e).forEach((s) -> em.remove(s));
        getTicketsToEvent(e).forEach((t) -> em.remove(t));
        em.remove(e);
        commit();
    }
    
    public Event getEventByKey(EventKey ek) {
        return (Event) em.createQuery(
                "SELECT e FROM Event e WHERE e.id = :eventkey"
        ).setParameter("eventkey", ek)
        .getSingleResult();
    }
    
    /**
     * Returns list of events with date before today.
     * @return List of past events.
     */
    public List<Event> getPastEvents() {
        return em.createQuery(
                "SELECT e FROM Event e WHERE e.id.date < CURRENT_DATE"
        ).getResultList();
    }
    
    /**
     * Returns person's workplaces (clubs) in list.
     * @param p Person instance.
     * @return List of clubs.
     */
    public List<Club> getClubsWherePersonWorks(Person p) {
        return em.createQuery(
                "SELECT c FROM Club c JOIN Employee e ON c.name = e.keyId.club WHERE e.keyId.id = :personid"
        ).setParameter("personid", p.getId())
        .getResultList();
    }
    
    public List<Club> getClubsWherePersonDoesntWork(Person p) {
        Collection<Club> allClubs = getClubs();
        Collection<Club> workClubs = getClubsWherePersonWorks(p);
        allClubs.removeAll(workClubs);
        List<Club> rl = new ArrayList<>();
        rl.addAll(allClubs);
        return rl;
    }
    
    public List<Band> getBandsWherePersonPlays(Person p) {
        return em.createQuery(
                "SELECT b FROM Band b JOIN BandMember m ON b.name = m.keyId.band WHERE m.keyId.musician = :personid"
        ).setParameter("personid", p.getId())
        .getResultList();
    }
    
    public List<Person> getPeopleWhoWorkInClub(Club c) {
        return em.createQuery(
                "SELECT p FROM Person p JOIN Employee e ON p.id = e.keyId.id WHERE e.keyId.club = :clubname"
        ).setParameter("clubname", c.getName())
        .getResultList();
    }
    
    public List<Person> getPeopleWhoDontWorkInClub(Club c) {
        Collection<Person> allPeople = getPeople();
        Collection<Person> employedPeople = getPeopleWhoWorkInClub(c);
        allPeople.removeAll(employedPeople);
        List<Person> rl = new ArrayList<>();
        rl.addAll(allPeople);
        return rl;
    }
    
    public List<Event> getEventsAtClub(Club c) {
        return em.createQuery(
                "SELECT e FROM Event e WHERE e.id.club = :clubname"
        ).setParameter("clubname", c.getName())
        .getResultList();
    }
    
    /**
     * Adds new event to database.
     * @param name Name of event.
     * @param date String representation of date in format "yyyy-(m)m-(d)d"
     * @param time String representation of time in format "hh:mm:ss"
     * @param club Club instance.
     */
    public void addEvent(String name, String date, String time, Club club) {
        EventKey ek = new EventKey();
        ek.setClub(club.getName());
        ek.setDate(Date.valueOf(date));
        ek.setTime(Time.valueOf(time));
        
        Event e = new Event();
        e.setId(ek);
        e.setName(name);
        
        save(e);
    };
    
    /**
     * Adds new show to event.
     * @param event Event instance.
     * @param band Band instance.
     * @param time String representation of time in format "hh:mm:ss"
     */
    public void addShow(Event event, Band band, String time) {
        ShowKey sk = new ShowKey();
        sk.setBand(band.getName());
        sk.setEvent(event.getId());
        
        Show s = new Show();
        s.setId(sk);
        s.setShowtime(Time.valueOf(time));
        
        save(s);
    }
    
    /**
     * Returns list of shows at provided event.
     * @param e Event
     * @return List of shows.
     */
    public List<Show> getShowsAtEvent(Event e) {
        return em.createQuery(
                "SELECT s FROM Show s WHERE s.id.event = :eventId ORDER BY s.showtime ASC"
        ).setParameter("eventId",e.getId()).getResultList();
                
    }
    
    public Club getClubByName(String cn) {
        return (Club) em.createQuery(
                "SELECT c FROM Club c WHERE c.name = :clubname"
        ).setParameter("clubname",cn)
        .getSingleResult();
    }
    
    /**
     * Returns list of tickets to event that were sold before.
     * @param e Event
     * @return List of tickets.
     */
    public List<Ticket> getTicketsToEvent(Event e) {
        return em.createQuery(
                "SELECT t FROM Ticket t WHERE t.eventKey = :eventId"
        ).setParameter("eventId",e.getId()).getResultList();
    }
    
    /**
     * Terminates database connection.
     */
    public void end() {
        em.close();
        emf.close();
    }
    
    public void addEmployee(Club c, Person p) {
        EmployeeKey ek = new EmployeeKey();
        ek.setClub(c.getName());
        ek.setId(p.getId());
        Employee e = new Employee();
        e.setKeyId(ek);
        save(e);
    }
    
    public void removeEmployee(Club c, Person p) {
        Employee e = (Employee) em.createQuery("SELECT e FROM Employee e WHERE e.keyId.club = :club AND e.keyId.id = :person").setParameter("club", c.getName()).setParameter("person", p.getId()).getSingleResult();
        delete(e);
    }

    
}