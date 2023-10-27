/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.databaseexample;

/**
 *
 * @author antho
 */
public class Supervisor {
    private int supervisor_ID;
    private String first_name, last_name;
    
    Supervisor(int supervisor_ID, String first_name, String last_name){
        this.supervisor_ID = supervisor_ID;
        this. first_name = first_name;
        this. last_name = last_name;
    }

    /**
     * @return the supervisor_ID
     */
    public int getSupervisor_ID() {
        return supervisor_ID;
    }

    /**
     * @param supervisor_ID the supervisor_ID to set
     */
    public void setSupervisor_ID(int supervisor_ID) {
        this.supervisor_ID = supervisor_ID;
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
    
}
