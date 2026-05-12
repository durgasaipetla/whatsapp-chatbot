package com.whatsapp.service;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import com.whatsapp.entity.Contact;

@Service
public class ExcelService {

    public List<Contact> readExcel(String filePath) {

        List<Contact> contacts = new ArrayList<>();

        try {

            URL url = new URL(filePath);

            InputStream file = url.openStream();

            Workbook workbook =
                    WorkbookFactory.create(file);

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {

                if (row.getRowNum() == 0)
                    continue;

                String name =
                        getCellValue(row.getCell(0));

                String phone =
                        getCellValue(row.getCell(1));

                contacts.add(
                        new Contact(name, phone, true));
            }

            workbook.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return contacts;
    }

    private String getCellValue(Cell cell) {

        if (cell == null)
            return "";

        switch (cell.getCellType()) {

            case STRING:
                return cell.getStringCellValue().trim();

            case NUMERIC:
                return String.valueOf(
                        (long) cell.getNumericCellValue());

            case BOOLEAN:
                return String.valueOf(
                        cell.getBooleanCellValue());

            default:
                return "";
        }
    }
}