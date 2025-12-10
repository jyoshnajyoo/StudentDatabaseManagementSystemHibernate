package com.kodnest.StudentManagementSystemHibernate1;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class App 
{
	static SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	static Scanner scan = new Scanner(System.in);
    public static void main( String[] args )
    {
    	  System.out.println("WELCOME TO Student MANAGEMENT SYSTEM");
    	  while (true) {
    		System.out.println("PRESS1->AddStudent");
    		System.out.println("PRESS2->GetStudent");
    		System.out.println("PRESS3->DeleteStudent");
    		System.out.println("PRESS4->UpdateStudent");
    		System.out.println("PRESSother->Exit");
    		
    		int ch =scan.nextInt();
    	 
    
      	switch(ch) {
       	case 1: addStudent();
      	break;
     	case 2: getStudent();
        break;
     	case 3: deleteStudent();
     	break;
     	case 4: updateStudent();
     	break;
        default:System.out.println("THANKS FOR USING Student MANAGEMENT SYSTEM... TATA BYEE BYEE");
        factory.close();
        return;
     	}
    	  }
      
     
    }
    
    public static void addStudent() {
    	
    	Session session=factory.openSession();
    	Transaction transaction = session.beginTransaction();
    	System.out.println("Enter Student name, salary, email");
    	Student student = new Student(scan.next(),scan.nextInt(),scan.next());
    	session.persist(student);
    	transaction.commit();
    	session.close();
    }
    
    public static void getStudent() {
    	
    	Session session=factory.openSession();
    	Transaction transaction = session.beginTransaction();
    	System.out.println("Enter Student Id to get their details");
    	int id = scan.nextInt();
    Student obj =session.find(Student.class, id);
    	if (obj != null) {
    		System.out.println(obj);
    		System.out.println("Taken the Student details succesfully");
    	} else {
    		System.out.println("Student with Id: " + id + " Does not Exist please try again");
    	}
    	transaction.commit();
    	session.close();
    	
    }
 
    public static void deleteStudent() {
 	
    	Session session=factory.openSession();
    	Transaction transaction = session.beginTransaction();
    	System.out.println("Enter Student Id to Delete their details");
    	int id = scan.nextInt();
    	Student obj =session.find(Student.class, id);
    	if (obj != null) {
    		session.remove(obj);
    		System.out.println("Student details Deleted succesfully");
    	} else {
    		System.out.println("Student with Id: " + id + " Does not Exist please try again");
    	}
    	transaction.commit();
    	session.close();
    	
    }
 
    public static void updateStudent() {
 	
    Session session=factory.openSession();
	Transaction transaction = session.beginTransaction();
    System.out.println("Enter Student Id to update their details");
    int id = scan.nextInt();
    Student obj=session.find(Student.class, id);
    if (obj != null) {
 	   System.out.println("Enter new name, salary, email");
 	   obj.setName(scan.next());
 	   obj.setMarks(scan.nextInt());
 	   obj.setEmail(scan.next());
 	   session.merge(obj);
 	   System.out.println("Student details Updated succesfully");
    } else {
 	   System.out.println("Student with Id: " + id + " Does not Exist please try again");
    }
	transaction.commit();
    	session.close();
    	
    }
}
