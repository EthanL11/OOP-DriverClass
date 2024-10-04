// Ethan Liang
//unit9.hw

import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;

public class DriverClass {
  public static void main(String[] args) {
    LinkedList list = new LinkedList();

      list.addNewEmployee(new Employee ("Kim Oz", 1235.5, 3));
      list.addNewEmployee(new Employee ("Rim Oz", 8235.5, 1));
      list.addNewEmployee(new Employee ("Dane Ali", 3235.5, 0));
      list.addNewEmployee(new Employee ("Aidan Jones", 2035.5, 2));
      list.addNewEmployee(new Employee ("Nadia Jones", 5035.5, 3));
      list.addNewEmployee(new Employee ("Ed Renu", 6035, 2));
      list.addNewEmployee(new Employee ("Naadi Jones", 36035.75, 5));

      //The TAs may use less or more names.

      list.printAllEmployees();

      System.out.println("The highest net salary = " + list.highestNetSalary());

      list.deleteEmployeeByName("Rim Oz");

      list.deleteEmployeeByName("Nadia Jones");

      System.out.println( list.searchByName("Gary D. Richardson") );

    list.printAllEmployees();
  }
}//end of DriverClass

//____________________________
class LinkedList{

  Node company;

  public LinkedList () {

    company = null;

  }

  public void printAllEmployees () {
    // loops through and prints all nodes
    Node temp = company;
    while(temp != null){
      System.out.printf("%s\n", temp.getEmployee().toString());
      Node temp2 = temp.getBelow();
      while(temp2 != null){
        System.out.printf("%s\n", temp2.getEmployee().toString());
        temp2 = temp2.getBelow();
      }
      temp = temp.getnext();
    }
  }

  //returns true if id exists
  public boolean searchById (int id) {
    Node temp = company;
    while(temp != null)
      {
        if(temp.getEmployee().getId() == id)
        {
          return true;
        }
        temp = temp.getnext();
      }
    return false;
  }
  
  public void addNewEmployee (Employee e) {
    // create new node
    Node newnode = new Node(e);
    // check if list is empty
    if(company == null){
      company = newnode;
      company.setnext(null);
      company.setBelow(null);
    }
    //find where to add
    else{
      Node temp = company;
      //Checks if Id exists
      if(searchById(e.getId())){ 
        //if Id exists, find where to put it
        int tmpId = newnode.getEmployee().getId();
        //find matching id
        while(temp.getnext() != null){
          if(temp.getEmployee().getId() == tmpId)
            break;
          temp = temp.getnext();
        }
        //go to bottom of id list
          while(temp.getBelow() != null){
            temp = temp.getBelow();
          }
          //add new node to bottom of id list
          temp.setBelow(newnode);
          temp.getBelow().setnext(null);
          temp.getBelow().setBelow(null);
        
      }
      //if  Id does not exist, add to end of list
      else{
        while(temp.getnext() != null){
          temp = temp.getnext();
        }
        temp.setnext(newnode);
        temp.getnext().setnext(null);
        temp.getnext().setBelow(null);
      }
    }
  }

  //returns integer based on name
  public int convToId(String name){
  String temp_name = name.toUpperCase();
    int asciiValue = 0;
    for (int i = 0; i < temp_name.length(); i++) {
      asciiValue += (int) temp_name.charAt(i);
    }
    return asciiValue;
  }
  //searches list based on name
  public boolean searchByName (String name) {
    int tmpId =convToId(name);
    Node temp = company;
    if(searchById(tmpId)){ 
      //find matching id
      while(temp.getnext() != null){
        if(temp.getEmployee().getId() == tmpId)
          break;
        temp = temp.getnext();
    
      }
    }
    //cheaks Id location in list for name
    while(temp != null)  {
        if(temp.getEmployee().getName().equals(name))
        {
          return true;
        }
        temp = temp.getBelow();
      }
    
      return false;
  }
  
  //returns highest net salary based on calculation
  public double highestNetSalary () {
    double highestNetSalary = 0;
    Node temp = company;
    while(temp != null){
      double netsalary = temp.getEmployee().getsalary()*0.91 + (temp.getEmployee().getnumberOfDependent() *0.01*temp.getEmployee().getsalary());
      if(netsalary > highestNetSalary){
        highestNetSalary = netsalary;
      }
      if(temp.getBelow() != null){
        Node temp2 = temp.getBelow();
        while(temp2 != null){
          netsalary = temp2.getEmployee().getsalary()*0.91 + (temp2.getEmployee().getnumberOfDependent() *0.01*temp2.getEmployee().getsalary());
          if(netsalary > highestNetSalary){
            highestNetSalary = netsalary;
          }
          temp2 = temp2.getBelow();
        }
      }
      temp = temp.getnext();
    }
      
    return highestNetSalary;
  }
  
  //Deletes employee based on name and reconnects list
  public void deleteEmployeeByName (String name){
    Node temp = company;
    //null front case
    if(company == null){
      return;
    }
    //front case
    if(company.getEmployee().getName() == name){
      // w/below
      if(company.getBelow() != null){
        Node tmpN = company.getnext();
        company.getBelow().setnext(tmpN);
        company = company.getBelow();
      }
      else{
        //w/o below
        company = company.getnext();
      }
      return;
    }
    // mid case
    while(temp.getnext() != null){
      
      if(temp.getnext().getEmployee().getName() == name){
        //if node to del has below, connect below to next
        if(temp.getnext().getBelow() != null){
          Node tmpN = temp.getnext().getnext();
          Node tmpB = temp.getnext().getBelow();
          temp.setnext(tmpB);
          tmpB.setnext(tmpN);
        }
        //else just connect to next
        else{
          temp.setnext(temp.getnext().getnext());
        }
      }
      
      //if name is not on top, look below
      else if(temp.getBelow() != null){
        Node temp2 = temp.getBelow();
        while(temp2 != null){
          if(temp2.getEmployee().getName() == name){
            temp.setBelow(temp2.getBelow());
          }  
          temp2 = temp2.getBelow();
        }
      }  
      temp = temp.getnext();
    }
    return;
  }
}
//______________________________
class Employee {

  private String name; //Keep these fields private!

  private int id;

  private int numberOfDependent;

  private double salary;
  
  @Override

  public String toString () {

    double net_salary = salary * 0.91 + (numberOfDependent * 0.01 * salary);
    return "[" + id + ", " + name + ", " + net_salary + "]";
    
  }

  public Employee (String name, double salary, int numberOfDependent){
    this.name = name;
    this.salary = salary;
    this.numberOfDependent = numberOfDependent;
    String temp_name = name.toUpperCase();
    int asciiValue = 0;
    for (int i = 0; i < temp_name.length(); i++) {
        asciiValue += (int) temp_name.charAt(i);
    }
    this.id = asciiValue;
  }

  public String getName() {
    return name;
  }

  public int getId(){
    return id;
  }

  public int getnumberOfDependent(){
    return numberOfDependent;
  }

  public double getsalary(){
    return salary;
  }
}
//______________________________
class Node {

  private Employee e; //Keep these fields private!

  private Node next;

  private Node below;

  public Node(Employee e)
  {
    this.e = e;
  }

  public Node getnext(){
    return next;
  }

  public void setnext(Node next){
    this.next = next;
  }
  public void setBelow(Node below){
    this.below = below;
  }

  public Node getBelow(){
    return below;
  }

  public Employee getEmployee(){
    return e;
  }
}