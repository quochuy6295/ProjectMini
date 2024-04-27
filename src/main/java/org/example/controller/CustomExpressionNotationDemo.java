package org.example.controller;

import org.example.entity.Employee;
import org.jxls.common.Context;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

public class CustomExpressionNotationDemo {

    static Logger logger = (Logger) LoggerFactory.getLogger(CustomExpressionNotationDemo.class);

    private static final String TEMPLATE = "custom_expression_notation_template.xlsx";
    private static final String OUTPUT = "target/custom_expression_notation_output.xlsx";

    public static void main(String[] args) throws ParseException, IOException {
        List<Employee> employees = generateSampleEmployeeData();
        try(InputStream is = CustomExpressionNotationDemo.class.getResourceAsStream(TEMPLATE)) {
            try(OutputStream os = new FileOutputStream(OUTPUT)) {
                Context context = new Context();
                context.putVar("employees", employees);
                JxlsHelper.getInstance().buildExpressionNotation("[[", "]]").processTemplate(is, os, context);
            }
        }
    }

    public static List<Employee> generateSampleEmployeeData() throws ParseException {
        List<Employee> employees = new ArrayList<Employee>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd", Locale.US);
        employees.add( new Employee("Elsa", dateFormat.parse("1970-Jul-10"), 1500, 0.15) );
        employees.add( new Employee("Oleg", dateFormat.parse("1973-Apr-30"), 2300, 0.25) );
        employees.add( new Employee("Neil", dateFormat.parse("1975-Oct-05"), 2500, 0.00) );
        employees.add( new Employee("Maria", dateFormat.parse("1978-Jan-07"), 1700, 0.15) );
        employees.add( new Employee("John", dateFormat.parse("1969-May-30"), 2800, 0.20) );
        return employees;
    }
}
