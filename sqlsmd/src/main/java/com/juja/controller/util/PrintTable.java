package com.juja.controller.util;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PrintTable {
    private static final int MIN_LENGTH = 15;

    public static String buildTable(List<String> tableColumns, Set<Map<String, Object>> tableData) {
        List<Integer> lengthRow = lengthRowData(tableColumns);
        return String.format("%s%s", PrintTable.printHeader(tableColumns, lengthRow),
                PrintTable.printTable(tableData, lengthRow));
    }

    private static String printTable(Set<Map<String, Object>> tableData, List<Integer> lengthRow) {
        StringBuilder result = new StringBuilder();
        for (Map<String, Object> aTableData : tableData) {
            result.append(printRow(aTableData, lengthRow));
        }
        result.append(printSeparator(lengthRow));
        return result.toString();
    }

    private static String printRow(Map<String, Object> row, List<Integer> lengthRow) {
        StringBuilder result = new StringBuilder("|");
        int index = 0;
        for (Map.Entry<String, Object> p : row.entrySet()) {
            result.append(Strings.padEnd(p.getValue().toString(), lengthRow.get(index++), ' '))
                    .append("|");
        }
        result.append(System.lineSeparator());
       return result.toString();
    }

    private static String printHeader(List<String> tableColumns, List<Integer> lengthRow) {
        StringBuilder result = new StringBuilder();
        result.append(printSeparator(lengthRow)).append(System.lineSeparator());
        result.append("|");
        for (int i = 0; i < tableColumns.size() ; i++) {
            result.append(Strings.padEnd(tableColumns.get(i),
                    lengthRow.get(i), ' ')).append("|");
        }
        result.append(System.lineSeparator());
        result.append(printSeparator(lengthRow)).append(System.lineSeparator());
        return result.toString();
    }

    private static String printSeparator(List<Integer> lengthRow) {
        StringBuilder result = new StringBuilder("+");
        for (int length : lengthRow) {
            result.append(Strings.repeat("-", length));
            result.append("+");
        }
        return result.toString();
    }

    private static List<Integer> lengthRowData(List<String> heads) {
        List<Integer> result = new ArrayList<>();
        result.add(MIN_LENGTH / 2);
        for (int i = 1; i < heads.size(); i++) {
            if (heads.get(i).length() >= MIN_LENGTH) {
                result.add(heads.get(i).length());
            } else {
                result.add(MIN_LENGTH);
            }
        }
        return result;
    }
}
