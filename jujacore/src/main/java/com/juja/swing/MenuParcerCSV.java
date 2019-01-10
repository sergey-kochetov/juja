import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MenuParcerCSV extends JFrame implements ActionListener {
    private JMenuBar mb;
    private JMenu file;
    private JMenuItem open;
    private JTextArea ta;
    private static List<String> data = new ArrayList<>();
    private static List<String> newData = new ArrayList<>();
    private static String day = "";
    private static String programName = "";
    private static String time = "";

    private MenuParcerCSV(){
        open=new JMenuItem("Open File");
        open.addActionListener(this);
        file=new JMenu("File");
        file.add(open);
        mb=new JMenuBar();
        mb.setBounds(0,0,800,20);
        mb.add(file);
        ta=new JTextArea(80,80);
        ta.setBounds(0,20,800,80);
        add(mb);
        add(ta);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == open){
            JFileChooser fc=new JFileChooser();
            int i = fc.showOpenDialog(this);
            if(i == JFileChooser.APPROVE_OPTION){
                File f = fc.getSelectedFile();
                String filepath = f.getPath();
                if (filepath.endsWith(".csv")) {
                    try {
                        readFile(filepath);
                        newData = newLine(data);
                        String newFileName = filepath.replace(".csv", "_new.csv");
                        writeFile(newFileName);
                        ta.setText("Success...");
                    } catch (Exception ex) {
                        ta.setText(ex.getMessage());
                    }
                } else {
                    ta.setText("Select only *.csv");
                }
            }
        }
    }

    private static List<String> newLine(List<String> data) {
        List<String> result = new ArrayList<>();
        try {
            for (String x : data) {
                String line = getNewLine(x);
                result.add(line);
            }
        } catch (Exception ignored) {}
        return result;
    }

    private static String getNewLine(String x) {
        String[] strs = x.split(",");
        if (strs.length < 30) {
            return x;
        }
        List<String> result = new ArrayList<>();
        boolean flag = false;

        if (!strs[1].equals("Program Name")) {
            if (!programName.isEmpty() && programName.equals(strs[1])) {
                flag = true;
            }
            programName = strs[1];
        }
        result.add(strs[1]);

        if (strs[2].contains("Comment")) {
            result.add("Comment");
        } else {
            String comment = "";
            if (!flag && !time.isEmpty()) {
                comment = deltaDate(strs[3].split(" ")[1], time);
            } else {
                if (!strs[3].split(" ")[1].equals(time)) {
                    comment =  deltaDate(strs[3].split(" ")[1], time);
                }
            }
            result.add(comment);
        }
        if (strs[3].contains("Time")) {
            result.add("Start Time");
        } else {
            String currentDay = strs[3].split(" ")[0];
            String currentTime = strs[3].split(" ")[1];
            if (!day.equals(currentDay) && !currentDay.equals("1900/01/01")  || day.trim().isEmpty()) {
                day = currentDay;
                result.set(1,  day);
            } else if (currentDay.equals("1900/01/01")) {
                result.set(1, "error");
                currentTime = time;
            }
            result.add(currentTime);
        }
        if (strs[4].contains("Time")) {
            result.add("Finish Time");
        } else {
            result.add(strs[4].split(" ")[1]);
            time = strs[4].split(" ")[1];
        }
        if (strs[5].contains("Board Count Max")) {
            result.add("Delta");
        } else {
            String start = deltaDate(result.get(result.size() - 1), result.get(result.size() - 2));
            //String start = deltaDate(strs[4].split(" ")[1], strs[3].split(" ")[1]);
            //if (start.length() == 9) {
            //    start =  "error";
            //}
            result.add(start);
        }
        if (strs[6].equals("Board Serial No")) {
            result.add("BoardNo");
        } else {
            result.add(strs[6]);
        }
        result.add(strs[8]);
        result.add(strs[12]);
        result.add(strs[13]);
        result.add(strs[14]);
        return String.join(",", result);
    }

    private static String deltaDate(String start, String end) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        try {
            long millis = format.parse(start).getTime() -  format.parse(end).getTime();
            String result =  String.format("+%02d:%02d:%02d",
                    //Hours
                    TimeUnit.MILLISECONDS.toHours(millis) -
                            TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis)),
                    //Minutes
                    TimeUnit.MILLISECONDS.toMinutes(millis) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    //Seconds
                    TimeUnit.MILLISECONDS.toSeconds(millis) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            if (result.startsWith("+00:0")) {
                result = result.replace("+00:0", "+");
            } else if (result.startsWith("+00:")) {
                result = result.replace("+00:", "+");
            }
            return result;
        } catch (Exception e) {
            return "";
        }
    }

    private static void writeFile(String fileName) throws Exception {
        try (FileWriter writer = new FileWriter(fileName)){
            for(String str: newData) {
                writer.write(str + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new Exception("Dont write file: " + fileName);
        }
    }

    private static void readFile(String fileName) throws Exception {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            data = stream.collect(Collectors.toList());
        } catch (IOException e) {
            throw new Exception("Dont read file: " + fileName);
        }
        data.remove(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MenuParcerCSV om=new MenuParcerCSV();
                om.setSize(500,500);
                om.setLayout(null);
                om.setVisible(true);
                om.setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        });
    }
}