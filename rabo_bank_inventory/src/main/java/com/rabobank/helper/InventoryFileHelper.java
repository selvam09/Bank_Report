/**
 * Project Name: rabo_bank_inventory
 * Package Name: com.rabobank.helper
 * Class Name: InventoryFileHelper.java
 * Description:
 *
 *
 * Created Date:May 6, 2019
 * Modified Date:May 6, 2019
 *
 * Copyright to Selvam
 */
package com.rabobank.helper;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.rabobank.model.BankInventory;
import com.rabobank.model.Contacts;
import com.rabobank.util.Constants;
import com.rabobank.util.StringUtil;

@Component
public class InventoryFileHelper {

    /**
     * LOGGER
     */
    private static Logger lOGGER = LoggerFactory.getLogger(InventoryFileHelper.class);

    /**
     * This method is used to process the CSV file from the filpath location and read the all columns and row values.
     * Check the condition if the referenceNumberis unique values if the values id unique then we given status like "Duplicate Reference data"
     * If the endBalance values matched with startBalance and mutation, if the value are correct then we are given like "Valid Data"
     * If the endBalance values matched with startBalance and mutation, if the value are not match then we are given like "Not Valid"
     *
     * @param filePath
     * @return List<BankInventory>
     *         May 6, 2019
     */
    public List<BankInventory> processCSVFileData(String filePath) {
        InventoryFileHelper.lOGGER.info("--------------processCSVFileData-------------");
        List<BankInventory> inputList = new ArrayList<>();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(filePath));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            BankInventory bankInventory = null;
            // Below object is used to check the duplicate reference number exists in the file
            Set<String> referenceNumberSet = new HashSet<>();
            for (CSVRecord csvRecord : csvParser) {
                bankInventory = new BankInventory();
                // Accessing Values by Column Index
                String referenceNumber = csvRecord.get(0);
                String accountNumber = csvRecord.get(1);
                String description = csvRecord.get(2);
                String startBalance = csvRecord.get(3);
                String mutation = csvRecord.get(4);
                String endBalance = csvRecord.get(5);
                // Handled header columns values not insert into return response
                if (StringUtil.isValidString(referenceNumber) && !referenceNumber.equalsIgnoreCase("Reference")) {
                    if ((StringUtil.isValidString(accountNumber) && StringUtil.isValidString(startBalance)) ||
                            (StringUtil.isValidString(accountNumber)&& StringUtil.isValidString(mutation)))  {
                        bankInventory.setReferenceNumber(referenceNumber);
                        bankInventory.setAccountNumber(accountNumber);
                        bankInventory.setDescription(description);
                        bankInventory.setStartBalance(startBalance);
                        bankInventory.setMutation(mutation);
                        bankInventory.setEndBalance(endBalance);
                        // Check duplicate object have referenceNumber if exits then we are adding duplicate status
                        if ((referenceNumberSet != null) && referenceNumberSet.contains(referenceNumber)) {
                            bankInventory.setValidationStatus(Constants.DUPLICATE);
                        } else {
                            String status = InventoryFileHelper.checkEndBalance(startBalance, mutation, endBalance);
                            bankInventory.setValidationStatus(status);
                            referenceNumberSet.add(referenceNumber);
                        }
                        inputList.add(bankInventory);
                    }
                }
            }
        } catch (IOException e) {
            InventoryFileHelper.lOGGER.error("IOException Occurred at prcessFileData::" + e.getMessage());
        } catch (Exception e) {
            InventoryFileHelper.lOGGER.error("Exception Occurred at prcessFileData::" + e.getMessage());
        }
        return inputList;
    }

    /**
     * This method is used to process the XML file from the filpath location and read the all columns and row values.
     * Check the condition if the referenceNumberis unique values if the values id unique then we given status like "Duplicate Reference data"
     * If the endBalance values matched with startBalance and mutation, if the value are correct then we are given like "Valid Data"
     * If the endBalance values matched with startBalance and mutation, if the value are not match then we are given like "Not Valid"
     *
     * @param filePath
     * @return
     *         May 6, 2019
     */
    public List<BankInventory> processXMLFileData(String filePath) {
        InventoryFileHelper.lOGGER.info("--------------processXMLFileData-------------");
        List<BankInventory> inputList = new ArrayList<>();
        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("record");
            // Below object is used to check the duplicate reference number exists in the file
            Set<String> referenceNumberSet = new HashSet<>();
            BankInventory bankInventory = null;
            for (int temp = 0; temp < nList.getLength(); temp++) {
                bankInventory = new BankInventory();
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String referenceNumber = eElement.getAttribute("reference");
                    String accountNumber = eElement.getElementsByTagName("accountNumber").item(0).getTextContent();
                    String description = eElement.getElementsByTagName("description").item(0).getTextContent();
                    String startBalance = eElement.getElementsByTagName("startBalance").item(0).getTextContent();
                    String mutation = eElement.getElementsByTagName("mutation").item(0).getTextContent();
                    String endBalance = eElement.getElementsByTagName("endBalance").item(0).getTextContent();
                    if ((StringUtil.isValidString(referenceNumber) && StringUtil.isValidString(accountNumber) && StringUtil.isValidString(startBalance)) || (StringUtil.isValidString(referenceNumber) && StringUtil.isValidString(
                            accountNumber) && StringUtil.isValidString(mutation))) {
                        bankInventory.setReferenceNumber(referenceNumber);
                        bankInventory.setAccountNumber(accountNumber);
                        bankInventory.setDescription(description);
                        bankInventory.setStartBalance(startBalance);
                        bankInventory.setMutation(mutation);
                        bankInventory.setEndBalance(endBalance);
                        // Check duplicate object have referenceNumber if exits then we are adding duplicate status
                        if ((referenceNumberSet != null) && referenceNumberSet.contains(referenceNumber)) {
                            bankInventory.setValidationStatus(Constants.DUPLICATE);
                        } else {
                            String status = InventoryFileHelper.checkEndBalance(startBalance, mutation, endBalance);
                            bankInventory.setValidationStatus(status);
                            referenceNumberSet.add(referenceNumber);
                        }
                        inputList.add(bankInventory);
                    }
                }
            }
        } catch (Exception e) {
            InventoryFileHelper.lOGGER.error("Exception Occurred at processXMLFileData::" + e.getMessage());
        }
        return inputList;
    }

    /**
     * Check startBalance and mutation are match with endBalance value
     *
     * @param startBalance
     * @param mutation
     * @param endBalance
     * @return String
     *         May 6, 2019
     */
    public static String checkEndBalance(String startBalance, String mutation, String endBalance) {
        String status = Constants.FAILURE;
        try {
            // Check startBalance and mutation are match with endBalance value
            if (StringUtil.isValidString(startBalance) && StringUtil.isValidString(mutation) && StringUtil.isValidString(endBalance)) {
                double startBalanceValue = Double.valueOf(startBalance);
                char operator = mutation.trim().charAt(0);
                double endBalanceValue = Double.valueOf(endBalance);
                String mutationVal = mutation.substring(1, mutation.length());
                double mutationValue = Double.valueOf(mutationVal);
                double result = 0;
                switch (operator) {
                    case '+':
                        result = startBalanceValue + mutationValue;
                        break;
                    case '-':
                        result = startBalanceValue - mutationValue;
                        break;
                    case '*':
                        result = startBalanceValue * mutationValue;
                        break;
                    case '/':
                        result = startBalanceValue / mutationValue;
                        break;
                        // operator doesn't match any case constant (+, -, *, /)
                    default:
                        break;
                }
                double calculatedValue = Math.round(result * 100.0) / 100.0;
                if (calculatedValue == endBalanceValue) {
                    status = Constants.SUCCESS;
                }
            }
        } catch (Exception e) {
            InventoryFileHelper.lOGGER.error("Exception Occurred at checkEndBalance::" + e.getMessage());
        }
        return status;
    }


    /**
     * This method is used to process the CSV file from the filpath location and read the all columns and row values.
     * Read all the contact information and return the response
     *
     * @param filePath
     * @return List<BankInventory>
     *         May 6, 2019
     */
    public List<Contacts> processContactCSVFileData(String filePath) {
        InventoryFileHelper.lOGGER.info("--------------processContactCSVFileData-------------");
        List<Contacts> inputList = new ArrayList<>();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(filePath));
            @SuppressWarnings("resource")
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            Contacts contacts = null;
            for (CSVRecord csvRecord : csvParser) {
                contacts = new Contacts();
                // Accessing Values by Column Index
                String firstName = csvRecord.get(0);
                String surName = csvRecord.get(1);
                String issueCount = csvRecord.get(2);
                String dob = csvRecord.get(3);
                // Handled header columns values not insert into return response
                if (StringUtil.isValidString(firstName) && !firstName.equalsIgnoreCase("First name")) {
                    contacts.setFirstName(firstName);
                    contacts.setSurName(surName);
                    contacts.setIssueCount(issueCount);
                    contacts.setDob(dob);;
                    inputList.add(contacts);
                }

            }
        } catch (IOException e) {
            InventoryFileHelper.lOGGER.error("IOException Occurred at processContactCSVFileData::" + e.getMessage());
        } catch (Exception e) {
            InventoryFileHelper.lOGGER.error("Exception Occurred at processContactCSVFileData::" + e.getMessage());
        }
        return inputList;
    }
}
