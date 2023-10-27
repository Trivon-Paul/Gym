/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.databaseexample;

/**
 *
 * @author antho
 */
public class Equipment {
    private int equipment_ID,employee_owner;
    private String cost, daily_usage;
    
    Equipment(int equipment_ID, String cost, String daily_usage, int employee_owner){
        this.equipment_ID = equipment_ID;
        this.cost = cost;
        this.daily_usage = daily_usage;
        this.employee_owner = employee_owner;
    }
        

    /**
     * @return the equipment_ID
     */
    public int getEquipment_ID() {
        return equipment_ID;
    }

    /**
     * @param equipment_ID the equipment_ID to set
     */
    public void setEquioment_ID(int equipment_ID) {
        this.equipment_ID = equipment_ID;
    }

    /**
     * @return the cost
     */
    public String getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(String cost) {
        this.cost = cost;
    }

    /**
     * @return the daily_usage
     */
    public String getDaily_usage() {
        return daily_usage;
    }

    /**
     * @param daily_usage the daily_usage to set
     */
    public void setDaily_usage(String daily_usage) {
        this.daily_usage = daily_usage;
    }

    /**
     * @return the employee_owner
     */
    public int getEmployee_owner() {
        return employee_owner;
    }

    /**
     * @param employee_owner the employee_owner to set
     */
    public void setEmployee_owner(int employee_owner) {
        this.employee_owner = employee_owner;
    }
    
    
}
