package com.mycompany.databaseexample;

public class Employee {
    private int employee_ID, supervisor_ID;
    private String first_name, last_name, hire_date;
    
    Employee(int employee_ID, String first_name, String last_name,String hire_date, int members){
        this.employee_ID = employee_ID;
        this.first_name = first_name;
        this.last_name = last_name;
        this.hire_date = hire_date;
        this.members = members;
    }

    /**
     * @return the employee_ID
     */
    public int getEmployee_ID() {
        return employee_ID;
    }

    /**
     * @param employee_ID the employee_ID to set
     */
    public void setEmployee_ID(int employee_ID) {
        this.employee_ID = employee_ID;
    }

    /**
     * @return the members
     */
    public int getSupervisor_ID() {
        return members;
    }

    /**
     * @param members the members to set
     */
    public void setSupervisor_ID(int supervisor_ID) {
        this.members = members;
    }

    /**
     * @return the first_name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * @param first_name the first_name to set
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * @return the last_name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * @param last_name the last_name to set
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * @return the hire_date
     */
    public String getHire_date() {
        return hire_date;
    }

    /**
     * @param hire_date the hire_date to set
     */
    public void setHire_date(String hire_date) {
        this.hire_date = hire_date;
    }
    
   
}