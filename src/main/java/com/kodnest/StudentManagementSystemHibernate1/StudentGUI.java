package com.kodnest.StudentManagementSystemHibernate1;

import javax.swing.*;
import java.awt.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class StudentGUI extends JFrame {

    // Hibernate factory
    static SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    // Components
    JLabel title, l1, l2, l3, l4;
    JTextField t1, t2, t3, t4;
    JButton addBtn, getBtn, deleteBtn, updateBtn;

    public StudentGUI() {

        setTitle("Student Management System");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        // Title
        title = new JLabel("Student Management System");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(170, 20, 400, 30);
        add(title);

        // ID
        l1 = new JLabel("Enter Id:");
        l1.setBounds(60, 90, 120, 25);
        add(l1);
        t1 = new JTextField();
        t1.setBounds(200, 90, 250, 25);
        add(t1);

        // Name
        l2 = new JLabel("Enter Name:");
        l2.setBounds(60, 140, 120, 25);
        add(l2);
        t2 = new JTextField();
        t2.setBounds(200, 140, 250, 25);
        add(t2);

        // Marks
        l3 = new JLabel("Enter Marks:");
        l3.setBounds(60, 190, 120, 25);
        add(l3);
        t3 = new JTextField();
        t3.setBounds(200, 190, 250, 25);
        add(t3);

        // Email
        l4 = new JLabel("Enter Email:");
        l4.setBounds(60, 240, 120, 25);
        add(l4);
        t4 = new JTextField();
        t4.setBounds(200, 240, 250, 25);
        add(t4);

        // Buttons
        addBtn = new JButton("ADD");
        addBtn.setBounds(60, 320, 100, 35);
        add(addBtn);

        getBtn = new JButton("GET");
        getBtn.setBounds(180, 320, 100, 35);
        add(getBtn);

        deleteBtn = new JButton("DELETE");
        deleteBtn.setBounds(300, 320, 100, 35);
        add(deleteBtn);

        updateBtn = new JButton("UPDATE");
        updateBtn.setBounds(420, 320, 100, 35);
        add(updateBtn);

        // Button actions
        addBtn.addActionListener(e -> addStudent());
        getBtn.addActionListener(e -> getStudent());
        deleteBtn.addActionListener(e -> deleteStudent());
        updateBtn.addActionListener(e -> updateStudent());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }



    // ======================== ADD ========================
    private void addStudent() {
        try {
            String name = t2.getText();
            int marks = Integer.parseInt(t3.getText());
            String email = t4.getText();

            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();

            Student s = new Student(name, marks, email);
            session.persist(s);

            tx.commit();
            session.close();

            new TablePage("Student Added Successfully!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }



    // ======================== GET ========================
    private void getStudent() {
        try {
            int id = Integer.parseInt(t1.getText());

            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();

            Student s = session.find(Student.class, id);

            if (s != null) {

                t2.setText(s.getName());
                t3.setText(String.valueOf(s.getMarks()));
                t4.setText(s.getEmail());

                new TablePage("Fetched Student: " + s.toString());
            } else {
                JOptionPane.showMessageDialog(this, "Student Not Found!");
            }

            tx.commit();
            session.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }



    // ======================== DELETE ========================
    private void deleteStudent() {
        try {
            int id = Integer.parseInt(t1.getText());

            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();

            Student s = session.find(Student.class, id);

            if (s != null) {
                session.remove(s);
                new TablePage("Student Deleted Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Student Not Found!");
            }

            tx.commit();
            session.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }



    // ======================== UPDATE ========================
    private void updateStudent() {
        try {
            int id = Integer.parseInt(t1.getText());

            Session session = factory.openSession();
            Transaction tx = session.beginTransaction();

            Student s = session.find(Student.class, id);

            if (s != null) {

                s.setName(t2.getText());
                s.setMarks(Integer.parseInt(t3.getText()));
                s.setEmail(t4.getText());

                session.merge(s);

                new TablePage("Student Updated Successfully!");

            } else {
                JOptionPane.showMessageDialog(this, "Student Not Found!");
            }

            tx.commit();
            session.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }



    public static void main(String[] args) {
        new StudentGUI();
    }
}
