/**
 * Project Name: rabo_bank_inventory
 * Package Name: com.rabobank.service
 * Class Name: FileProcessServiceImpl.java
 * Description:
 *
 *
 * Created Date:May 6, 2019
 * Modified Date:May 6, 2019
 *
 * Copyright to Selvam
 */
package com.rabobank.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rabobank.controller.FileController;
import com.rabobank.helper.InventoryFileHelper;
import com.rabobank.model.BankInventory;
import com.rabobank.model.Contacts;
import com.rabobank.util.Constants;
import com.rabobank.util.StringUtil;

@Service
public class FileProcessServiceImpl implements IFileProcessService {

    /**
     * LOGGER
     */
    private static Logger       lOGGER       = LoggerFactory.getLogger(FileController.class);

    private static final Path   rootLocation = Paths.get("inputfiles");

    @Autowired
    private InventoryFileHelper inventoryFileHelper;

    /*
     * (non-Javadoc)
     *
     * @see com.rabobank.service.IFileProcessService#storefile(org.springframework.web.multipart.MultipartFile)
     *
     * May 6, 2019
     */
    @Override
    public String storefile(MultipartFile file) {
        String filePath = null;
        try {
            String stringAutoGeneration = UUID.randomUUID().toString();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
            String currentDate = formatter.format(new Date());
            filePath = currentDate + java.io.File.separator + stringAutoGeneration;
            new java.io.File(FileProcessServiceImpl.rootLocation + java.io.File.separator + filePath).mkdirs();
            Files.copy(file.getInputStream(), FileProcessServiceImpl.rootLocation.resolve(filePath + java.io.File.separator + file.getOriginalFilename()));
        } catch (Exception e) {
            FileProcessServiceImpl.lOGGER.error("Exception Occurred at storefile::" + e.getMessage());
        }
        return FileProcessServiceImpl.rootLocation + java.io.File.separator + filePath + java.io.File.separator + file.getOriginalFilename();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.rabobank.service.IFileProcessService#storefile(java.lang.String)
     *
     * May 6, 2019
     */
    @Override
    public Map<String, Object> processFile(String filePath) {
        Map<String, Object> responseObj = new HashMap<>();
        String message="Success";
        try {
            List<BankInventory> inputList = new ArrayList<>();
            if (StringUtil.isValidString(filePath)&&filePath.contains(".csv")) {
                FileProcessServiceImpl.lOGGER.info("CSV_______________");
                //Process CSV Files and return the validation response
                inputList = this.inventoryFileHelper.processCSVFileData(filePath);
            } else if (StringUtil.isValidString(filePath)&&filePath.contains(".xml")) {
                FileProcessServiceImpl.lOGGER.info("XML_______________");
                //Process XML Files and return the validation response
                inputList = this.inventoryFileHelper.processXMLFileData(filePath);
            }else {
                message="Please choose XML or CSV File type)";
            }
            responseObj.put(Constants.MESSAGE,message );
            responseObj.put(Constants.DATA,inputList );
        } catch (Exception e) {
            FileProcessServiceImpl.lOGGER.error("Exception Occurred at processFile::" + e.getMessage());
        }
        return responseObj;
    }


    /*
     * (non-Javadoc)
     *
     * @see com.rabobank.service.IFileProcessService#storefile(java.lang.String)
     *
     * May 6, 2019
     */
    @Override
    public Map<String, Object> processContactFile(String filePath) {
        Map<String, Object> responseObj = new HashMap<>();
        String message="Success";
        try {
            List<Contacts> inputList = new ArrayList<>();
            if (StringUtil.isValidString(filePath)&&filePath.contains(".csv")) {
                FileProcessServiceImpl.lOGGER.info("CSV_______________");
                //Process CSV Files and return the validation response
                inputList = this.inventoryFileHelper.processContactCSVFileData(filePath);
            }else {
                message="Please choose  CSV File type)";
            }
            responseObj.put(Constants.MESSAGE,message );
            responseObj.put(Constants.DATA,inputList );
        } catch (Exception e) {
            FileProcessServiceImpl.lOGGER.error("Exception Occurred at processContactFile::" + e.getMessage());
        }
        return responseObj;
    }
}
