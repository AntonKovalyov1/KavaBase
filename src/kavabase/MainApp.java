package kavabase;

import java.io.IOException;
import kavabase.DataFormat.DataType;
import kavabase.DataFormat.Operator;
import kavabase.Query.Prompt;

/**
 *
 * @author Anton
 */
public class MainApp {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
//       FileOperations.createMetaData();
        Prompt.run();
//        RandomAccessFile raf = new RandomAccessFile(FileOperations.TABLES_PATH, "rw");
//        Page page = FileOperations.readPage(raf, 0);
//        //FileOperations.getMetaData();
//        System.out.println(FileOperations.getMetaData().
//                get("davisbase_columns").getColumns().get(5).getColumnName());
//        DataType.Int x = new DataType.Int(24567);
//        System.out.println(x.getData().toString().length());
//        System.out.print("| ");
//        System.out.format("%-5s", "1");
//        System.out.format("%-10s", "h");
//        RandomAccessFile raf = new RandomAccessFile(FileOperations.COLUMNS_PATH, "rw");
//        ArrayList<DataType> row = new ArrayList<>();
//        row.add(new DataType.Int(14));
//        row.add(new DataType.CustomText("test1"));
//        row.add(new DataType.CustomText("testhuihuoihuhiohoiuhiuhiuhihuhuihiohihoi"));
//        row.add(new DataType.CustomText("testhiuhoiuhuihihiuhihyggtyffyftftyfyfytftyftyfytf"));
//        row.add(new DataType.TinyInt((byte)1));
//        row.add(new DataType.CustomText("test"));
//        FileOperations.insert(raf, row);
//
//        raf = new RandomAccessFile(FileOperations.TABLES_PATH, "rw");
//        ArrayList<DataType> row2 = new ArrayList<>();
//        row2.add(new DataType.Int(3));
//        row2.add(new DataType.CustomText("test1"));
//        FileOperations.insert(raf, row2);
//        ArrayList<TableMetaData> metaData = FileOperations.getMetaData();
//        TableMetaData t = new TableMetaData("davisbase_columns");
////        int index = metaData.indexOf(t);
//        FileOperations.selectAll(metaData.get(1));
//        for (Map.Entry<String, TableMetaData> current : metaData.entrySet()) {
//            System.out.println(current.getValue().getColumns().size());
//        }
    }    
}
