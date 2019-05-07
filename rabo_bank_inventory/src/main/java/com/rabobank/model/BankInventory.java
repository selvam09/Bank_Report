/**
 * Project Name: rabo_bank_inventory
 * Package Name: com.rabobank.model
 * Class Name: BankInventory.java
 * Description:
 *
 *
 * Created Date:May 6, 2019
 * Modified Date:May 6, 2019
 *
 * Copyright to Selvam
 */
package com.rabobank.model;

public class BankInventory {

    public String referenceNumber;

    public String accountNumber;

    public String description;

    public String startBalance;

    public String mutation;

    public String endBalance;

    public String validationStatus;

    /**
     * @return the referenceNumber
     */
    public String getReferenceNumber() {
        return this.referenceNumber;
    }

    /**
     * @param referenceNumber the referenceNumber to set
     */
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * @return the accountNumber
     */
    public String getAccountNumber() {
        return this.accountNumber;
    }

    /**
     * @param accountNumber the accountNumber to set
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the startBalance
     */
    public String getStartBalance() {
        return this.startBalance;
    }

    /**
     * @param startBalance the startBalance to set
     */
    public void setStartBalance(String startBalance) {
        this.startBalance = startBalance;
    }

    /**
     * @return the mutation
     */
    public String getMutation() {
        return this.mutation;
    }

    /**
     * @param mutation the mutation to set
     */
    public void setMutation(String mutation) {
        this.mutation = mutation;
    }

    /**
     * @return the endBalance
     */
    public String getEndBalance() {
        return this.endBalance;
    }

    /**
     * @param endBalance the endBalance to set
     */
    public void setEndBalance(String endBalance) {
        this.endBalance = endBalance;
    }

    /**
     * @return the validationStatus
     */
    public String getValidationStatus() {
        return this.validationStatus;
    }

    /**
     * @param validationStatus the validationStatus to set
     */
    public void setValidationStatus(String validationStatus) {
        this.validationStatus = validationStatus;
    }

}
