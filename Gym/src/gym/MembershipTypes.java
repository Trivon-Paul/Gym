/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.databaseexample;

/**
 *
 * @author antho
 */
public class MembershipTypes {
    private int membership_type_ID, cost, duration;
    private String type;

    /**
     * @return the membership_type_ID
     */
    public int getMembership_type_ID() {
        return membership_type_ID;
    }

    /**
     * @param membership_type_ID the membership_type_ID to set
     */
    public void setMembership_type_ID(int membership_type_ID) {
        this.membership_type_ID = membership_type_ID;
    }

    /**
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
}
