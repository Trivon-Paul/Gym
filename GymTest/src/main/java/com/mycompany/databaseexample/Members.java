/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.databaseexample;

/**
 *
 * @author antho
 */
public class Members {
    private int member_ID, total_visits, employee_sponsor, membership;
    private String first_name, last_name, join_date, last_visit;
    
    Members(int member_ID, String first_name, String last_name, String join_date, String last_visit, 
            int total_visits, int employee_sponsor, int membership){
        this. member_ID = member_ID;
        this.first_name = first_name;
        this.last_name = last_name;
        this.join_date = join_date;
        this.last_visit = last_visit;
        this.total_visits = total_visits;
        this.employee_sponsor = employee_sponsor;
        this.membership = membership;
    }

    /**
     * @return the member_ID
     */
    public int getMember_ID() {
        return member_ID;
    }

    /**
     * @param member_ID the member_ID to set
     */
    public void setMember_ID(int member_ID) {
        this.member_ID = member_ID;
    }

    /**
     * @return the total_visits
     */
    public int getTotal_visits() {
        return total_visits;
    }

    /**
     * @param total_visits the total_visits to set
     */
    public void setTotal_visits(int total_visits) {
        this.total_visits = total_visits;
    }

    /**
     * @return the employee_sponsor
     */
    public int getEmployee_sponsor() {
        return employee_sponsor;
    }

    /**
     * @param employee_sponsor the employee_sponsor to set
     */
    public void setEmployee_sponsor(int employee_sponsor) {
        this.employee_sponsor = employee_sponsor;
    }

    /**
     * @return the membership
     */
    public int getMembership() {
        return membership;
    }

    /**
     * @param membership the membership to set
     */
    public void setMembership(int membership) {
        this.membership = membership;
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
     * @return the join_date
     */
    public String getJoin_date() {
        return join_date;
    }

    /**
     * @param join_date the join_date to set
     */
    public void setJoin_date(String join_date) {
        this.join_date = join_date;
    }

    /**
     * @return the last_visit
     */
    public String getLast_visit() {
        return last_visit;
    }

    /**
     * @param last_visit the last_visit to set
     */
    public void setLast_visit(String last_visit) {
        this.last_visit = last_visit;
    }
}
