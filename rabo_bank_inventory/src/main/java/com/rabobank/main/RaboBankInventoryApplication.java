/**
 * Project Name: rabo_bank_inventory
 * Package Name: com.rabobank.main
 * Class Name: RaboBankInventoryApplication.java
 * Description:
 *
 *
 * Created Date:May 6, 2019
 * Modified Date:May 6, 2019
 *
 * Copyright to Selvam
 */

package com.rabobank.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.rabobank.*" })
public class RaboBankInventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(RaboBankInventoryApplication.class, args);
    }

}
