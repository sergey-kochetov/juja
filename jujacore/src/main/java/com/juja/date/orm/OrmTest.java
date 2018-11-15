package com.juja.date.orm;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;


public class OrmTest {
    public static void main(String[] args) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            Employee employee = new Employee();
            employee.setName("Vasya");
            employee.setJob("Java dev");
            employee.setDepartment("IT");
            employee.setAge(30);
            employee.setSalary(50000.0);

            session.save(employee);

            employee = new Employee();
            employee.setName("Masha");
            employee.setJob("Java dev");
            employee.setDepartment("IT");
            employee.setAge(25);
            employee.setSalary(45000.0);

            session.save(employee);

            employee = new Employee();
            employee.setName("Petya");
            employee.setJob("Java dev");
            employee.setDepartment("IT");
            employee.setAge(28);
            employee.setSalary(55000.0);

            session.save(employee);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Employee> list = Collections.emptyList();

        try (Session session = HibernateUtil.getSession()){
            session.beginTransaction();

            Query query =session.createQuery("FROM Employee");
            list = (List<Employee>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list != null && !list.isEmpty()) {
            for (Employee employee : list) {
                System.out.println(employee);
            }
        }
    }
}
