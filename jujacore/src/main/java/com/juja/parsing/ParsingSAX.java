package com.juja.parsing;

import org.junit.Assert;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParsingSAX {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        //D:\projects\juja\jujacore\src\main\resources
        File file = new File("jujacore/src/main/resources/file.xml");
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        MyHandler handler = new MyHandler();
        xmlReader.setContentHandler(handler);
        xmlReader.parse(String.valueOf(file));

        Report report = handler.getReport();
        System.out.println("\nReport number: " + report.number);

        report.employeeList.forEach(System.out::println);
    }
}

class MyHandler extends DefaultHandler {
    private static final String REPORT_TAG = "report";
    private static final String EMPLOYERS_TAG = "employers";
    private static final String EMPLOYEE_TAG = "employee";
    private static final String NAME_TAG = "name";
    private static final String AGE_TAG = "age";
    private static final String SALARY_TAG = "salary";

    private static final String REPORT_NUMBER_ATTRIBUTE = "number";
    private static final String EMPLOYERS_DEPARTAMENT_ATTRIBUTE = "departament";
    private static final String EMPLOEE_NUMBER_ATTRIBUTE = "number";
    private static final String CURRENCY_ATTRIBUTE = "currency";

    private Report report;
    private Employee currentEmployee;
    private String employersDepartament;
    private String currentElement;


    Report getReport() {
        return report;
    }

    public void startDocument() throws SAXException {
        System.out.println("Starting XML parsing...");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = qName;
        switch (currentElement) {
            case REPORT_TAG: {
                report = new Report();
                report.number = Integer.valueOf(attributes.getValue(REPORT_NUMBER_ATTRIBUTE));
            } break;
            case  EMPLOYERS_TAG: {
                report.employeeList = new ArrayList<>();
                if (employersDepartament == null) {
                    employersDepartament = attributes.getValue(EMPLOYERS_DEPARTAMENT_ATTRIBUTE);
                }
            } break;
            case EMPLOYEE_TAG : {
                currentEmployee = new Employee();
                currentEmployee.setNumber(Integer.valueOf(attributes.getValue(EMPLOEE_NUMBER_ATTRIBUTE)));
                currentEmployee.setDepartment(employersDepartament);
            } break;
            case SALARY_TAG : {
                currentEmployee.setSalary(new Employee.Salary());
                currentEmployee.getSalary().setCurrency(attributes.getValue(CURRENCY_ATTRIBUTE));
            } break;
            default: {
                // do nothing
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String text = new String(ch, start, length);
        if (text.contains("<") || currentElement == null) {
            return;
        }
        switch (currentElement) {
            case NAME_TAG : {
                currentEmployee.setName(text);
            } break;
            case AGE_TAG : {
                currentEmployee.setAge(Integer.valueOf(text));
            } break;
            case SALARY_TAG : {
                currentEmployee.getSalary().setValue(Double.valueOf(text));
            } break;
            default: {
                // do nothing
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case EMPLOYEE_TAG : {
                report.employeeList.add(currentEmployee);
                currentEmployee = null;
            } break;
            default: {
                // do nothing
            }
            currentElement = null;
        }
    }
    public void endDocument() throws SAXException {
        System.out.println("XML parsing is completed.");
    }
}

class Report {
    Integer number;
    List<Employee> employeeList;
}


