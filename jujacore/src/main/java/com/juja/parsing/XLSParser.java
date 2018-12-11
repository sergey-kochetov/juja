package com.juja.parsing;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class XLSParser {

    public static HSSFWorkbook readWorkbook(String filename) {
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filename));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            return wb;
        } catch (Exception e) {
            return null;
        }
    }

    public static void writeWorkbook(HSSFWorkbook wb, String fileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            //Обработка ошибки
        }
    }

    public static void main2(String[] args) {
        XLSParser main = new XLSParser();
        String fileName = "D:\\test\\bl0612.xls";
        HSSFWorkbook hssfWorkbook = readWorkbook(fileName);
        System.out.println(Files.exists(Paths.get(fileName)));
    }

    private static final String FILE_NAME = "D:\\test\\bl0612.xls";

    public static void main(String[] args) {

        try {
            FileInputStream fis = new FileInputStream(FILE_NAME);
            Workbook wb = new HSSFWorkbook(fis);
            Sheet sheet = wb.getSheetAt(0);

            List<MyData> dataSets = new LinkedList<>();
            for (Row currentRow : sheet) {
                List<String> list = new LinkedList<>();
                for (Cell currentCell : currentRow) {
                    list.add(strCellValue(currentCell));
                }
                if (list.size() > 3 && list.size() < 40) {
                    MyData data = new MyData();
                    data.count =list.get(1);
                    data.programName = list.get(2);
                    data.startTime = list.get(4);
                    data.finishTime = list.get(5);
                    data.boardSerialNo = list.get(6);
                    data.finishFlag = list.get(7);
                    data.mountCTA = list.get(8);
                    data.transferCT = list.get(12);       //12
                    data.StandbyCT = list.get(13);        //13
                    data.MarkRecCTA = list.get(14);       //14
                    data.pickUpError = list.get(18);         //18
                    data.operatorCallTime = list.get(25); //25
                    data.recoveryTime = list.get(26);     //26
                    data.mountedBlocks = list.get(28);       //28
                    data.upstreamStandbyTime = list.get(30); //30
                    data.downstreamStandbyTime = list.get(31); //31
                    dataSets.add(data);
                    System.out.println(data.toString());
                }
                //System.out.println(dataSets.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static String  strCellValue(Cell cell) {
        switch (cell.getCellTypeEnum()) {
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case STRING:
                return cell.getRichStringCellValue().getString();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return String.valueOf(cell.getDateCellValue());
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    public static Date parseDate(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        return sdf.parse(date);
    }

    static class MyData {
        String count;
        String programName;      //2
        String startTime;          //4
        String finishTime;         //5
        String boardSerialNo;       //6
        String finishFlag;          //7
        String mountCTA;         //8
        String transferCT;       //12
        String StandbyCT;        //13
        String MarkRecCTA;       //14
        String pickUpError;         //18
        String operatorCallTime; //25
        String recoveryTime;     //26
        String mountedBlocks;       //28
        String upstreamStandbyTime; //30
        String downstreamStandbyTime; //31

        @Override
        public String toString() {
            return "MyData{" +
                    "count='" + count + '\'' +
                    ", programName='" + programName + '\'' +
                    ", startTime='" + startTime + '\'' +
                    ", finishTime='" + finishTime + '\'' +
                    ", boardSerialNo='" + boardSerialNo + '\'' +
                    ", finishFlag='" + finishFlag + '\'' +
                    ", mountCTA='" + mountCTA + '\'' +
                    ", transferCT='" + transferCT + '\'' +
                    ", StandbyCT='" + StandbyCT + '\'' +
                    ", MarkRecCTA='" + MarkRecCTA + '\'' +
                    ", pickUpError='" + pickUpError + '\'' +
                    ", operatorCallTime='" + operatorCallTime + '\'' +
                    ", recoveryTime='" + recoveryTime + '\'' +
                    ", mountedBlocks='" + mountedBlocks + '\'' +
                    ", upstreamStandbyTime='" + upstreamStandbyTime + '\'' +
                    ", downstreamStandbyTime='" + downstreamStandbyTime + '\'' +
                    '}';
        }

        private String getTime(Date date) {
            Format formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String s = formatter.format(date);
            return s;
        }
    }
}
