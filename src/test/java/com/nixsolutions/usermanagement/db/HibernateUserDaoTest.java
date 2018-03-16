package com.nixsolutions.usermanagement.db;

import java.util.Collection;
import java.util.Date;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

import com.nixsolutions.usermanagement.User;

import org.hibernate.classic.Session;

public class HibernateUserDaoTest extends DatabaseTestCase {
    private HibernateUserDao dao;
    private Session currentSession;

    protected void setUp() throws Exception {
        dao = new HibernateUserDao();
        currentSession = HibernateUtils.getSessionFactory().getCurrentSession();
        currentSession.beginTransaction();
        super.setUp();
        currentSession.getTransaction().commit();
    }

    /*
     * Test method for 'com.nixsolutions.usermanagement.db.HsqldbUserDao.create(User)'
     */
    public void testCreate() {
        try {
            User user = new User();
            user.setFirstName("John");
            user.setLastName("Doe");
            user.setDateOfBirth(new Date());
            assertNull(user.getId());
            user = dao.create(user);
            assertNotNull(user);
            assertNotNull(user.getId());
        } catch (DatabaseException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void testFindAll() {
        try {
            Collection collection = dao.findAll();
            assertNotNull("Collection is null", collection);
            assertEquals("Collection size.", 2, collection.size());
        } catch (DatabaseException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    protected IDatabaseConnection getConnection() throws Exception {
            /* get connection from the session */
        return new DatabaseConnection(currentSession.connection());
    }

    protected IDataSet getDataSet() throws Exception {
        IDataSet dataSet = new XmlDataSet(getClass().getClassLoader()
                .getResourceAsStream("usersDataSet.xml"));
        return dataSet;
    }

}
