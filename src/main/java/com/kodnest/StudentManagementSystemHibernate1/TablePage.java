package com.kodnest.StudentManagementSystemHibernate1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class TablePage extends JFrame {

    static SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    JTable table;
    JLabel messageLabel;

    public TablePage(String message) {

        setTitle("Student Table View");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ------ TABLE MODEL ------
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Marks");
        model.addColumn("Email");

        // ------ FETCH ALL STUDENTS ------
        Session session = factory.openSession();
        List<Student> students = session.createQuery("from Student", Student.class).getResultList();
        session.close();

        // Add rows to table
        for (Student s : students) {
            model.addRow(new Object[]{s.getId(), s.getName(), s.getMarks(), s.getEmail()});
        }

        // ------ TABLE ------
        table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);
        add(pane, BorderLayout.CENTER);

        // ------ MESSAGE LABEL ------
        messageLabel = new JLabel(message, SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(messageLabel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
