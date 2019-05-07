/**
 * Project Name: rabo_bank_inventory
 * Package Name: com.rabobank.service
 * Class Name: IFileProcessService.java
 * Description:
 *
 *
 * Created Date:May 6, 2019
 * Modified Date:May 6, 2019
 *
 * Copyright to Selvam
 */
package com.rabobank.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface IFileProcessService {

    /**
     *
     * @param file
     * @return String
     *         May 6, 2019
     */
    String storefile(MultipartFile file);

    /**
     *
     * @param filePath
     * @return Map<String, Object>
     *         May 6, 2019
     */
    Map<String, Object> processFile(String filePath);

    /**
     *
     * @param filePath
     * @return  Map<String, Object>
     * May 6, 2019
     */
    Map<String, Object> processContactFile(String filePath);
}
