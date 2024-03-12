/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package error.mavenproject4;

import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author ASUS
 */
public class HibernateExample2 {

    private static SessionFactory factory;

    public HibernateExample2(SessionFactory factory) {
        this.factory = factory;
    }

    public static void main(String[] args) {
        SessionFactory factory = null;
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        if (factory != null) {
            HibernateExample2 hb = new HibernateExample2(factory);
            hb.listDepartment();
        } else {
            System.out.println("Failed to initialize SessionFactory.");
        }
    }

    public void listDepartment() {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String queryString = "FROM Department"; // Đảm bảo sử dụng tên entity chính xác (chữ hoa/chữ thường)
            List departments = session.createQuery(queryString).list();
            for (Iterator iterator = departments.iterator(); iterator.hasNext();) {
                Department department = (Department) iterator.next();
                System.out.println("Name:" + department.getName());
                System.out.println("StartDate:" + department.getStartDate());
                System.out.println("Budget:" + department.getBudget());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}
