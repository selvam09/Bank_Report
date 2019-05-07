/**
 * Project Name: rabo_bank_inventory
 * Package Name: com.rabobank.controller
 * Class Name: FileController.java
 * Description:
 * This service class is mainly used to validate the bank data and return the status.
 *
 * Created Date:May 6, 2019
 * Modified Date:May 6, 2019
 *
 * Copyright to Selvam
 */

package com.rabobank.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rabobank.service.IFileProcessService;
import com.rabobank.util.Constants;

/**
 * @author Selvam
 *
 */
@RestController
@RequestMapping(path = "/api")
public class FileController {

    /**
     * LOGGER
     */
    private static Logger       lOGGER = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private IFileProcessService fileProcessService;

    /**
     * This Service is used to validate the bank statement reports and return the validation status.
     * Both xml and csv file processed by this service if any other formats will given then it will given error message.
     * Based on mutation we have calculated total values are matching with end balance value.
     *
     * @param file
     * @return ResponseEntity<Object>
     *         May 6, 2019
     */
    @PostMapping(value = "/fileupload")
    public ResponseEntity<Object> fileUpload(@RequestParam("file") MultipartFile file) {
        FileController.lOGGER.info("/fileupload/{}", file);
        long startTime = System.currentTimeMillis();
        Map<String, Object> responseObj = new HashMap<>();
        try {
            String filePath = this.fileProcessService.storefile(file);
            FileController.lOGGER.info("/filePath/{}", filePath);
            responseObj = this.fileProcessService.processFile(filePath);
        } catch (Exception e) {
            FileController.lOGGER.error("Exception occurred at fileUpload: {}", e.getMessage());
        }
        long endTime = System.currentTimeMillis() - startTime;
        responseObj.put(Constants.EXECUTION_TIME, endTime);
        return new ResponseEntity<>(responseObj, HttpStatus.OK);
    }

    /**
     * This Service is used to validate the contacts imports and return the results.
     * Both xml and csv file processed by this service if any other formats will given then it will given error message.
     *
     * @param file
     * @return ResponseEntity<Object>
     *         May 6, 2019
     */
    @PostMapping(value = "/contactupload")
    public ResponseEntity<Object> contactUpload(@RequestParam("file") MultipartFile file) {
        FileController.lOGGER.info("/contactupload/{}", file);
        long startTime = System.currentTimeMillis();
        Map<String, Object> responseObj = new HashMap<>();
        try {
            String filePath = this.fileProcessService.storefile(file);
            FileController.lOGGER.info("/contactupload file path/{}", filePath);
            responseObj = this.fileProcessService.processContactFile(filePath);
        } catch (Exception e) {
            FileController.lOGGER.error("Exception occurred at contactupload: {}", e.getMessage());
        }
        long endTime = System.currentTimeMillis() - startTime;
        responseObj.put(Constants.EXECUTION_TIME, endTime);
        return new ResponseEntity<>(responseObj, HttpStatus.OK);
    }
}
