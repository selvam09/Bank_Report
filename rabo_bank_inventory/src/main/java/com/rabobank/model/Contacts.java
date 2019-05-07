/**
 * Project Name: rabo_bank_inventory
 * Package Name: com.rabobank.model
 * Class Name: Contacts.java
 * Description:
 *
 *
 * Created Date:May 6, 2019
 * Modified Date:May 6, 2019
 *
 * Copyright to CandidateID
 */
package com.rabobank.model;

public class Contacts {

    public String firstName;

    public String surName;

    public String issueCount;

    public String dob;

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the surName
     */
    public String getSurName() {
        return this.surName;
    }

    /**
     * @param surName the surName to set
     */
    public void setSurName(String surName) {
        this.surName = surName;
    }

    /**
     * @return the issueCount
     */
    public String getIssueCount() {
        return this.issueCount;
    }

    /**
     * @param issueCount the issueCount to set
     */
    public void setIssueCount(String issueCount) {
        this.issueCount = issueCount;
    }

    /**
     * @return the dob
     */
    public String getDob() {
        return this.dob;
    }

    /**
     * @param dob the dob to set
     */
    public void setDob(String dob) {
        this.dob = dob;
    }
}
