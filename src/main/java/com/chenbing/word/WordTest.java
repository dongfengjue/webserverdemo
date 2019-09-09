package com.chenbing.word;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordTest {
    private static final String docxReadPath = "D:\\word\\poitest.doc";
//    private static final String docxReadPath = "C:\\Users\\chenbing\\Documents\\WeChat Files\\c17281798\\FileStorage\\File\\2019-08\\template_report.doc";

    private static final String docxWritePath = "D:\\word\\poiout.doc";
    /**
     * 读取文件
     * @param srcPath
     * @return XWPFDocument
     */
    private static XWPFDocument read_file(String srcPath)
    {
        String[] sp = srcPath.split("\\.");
        if ((sp.length > 0) && sp[sp.length - 1].equalsIgnoreCase("doc"))
        {
            try {
                OPCPackage pack = POIXMLDocument.openPackage(srcPath);
                XWPFDocument doc = new XWPFDocument(pack);
                return doc;
            } catch (IOException e) {
                System.out.println("读取文件出错！");
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 插入文字与表格
     * @param document
     * @return XWPFDocument
     */
    private static XWPFDocument insertParagraph(XWPFDocument document){
        Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
        int ind = 1;
        //获取段落位置
        while (itPara.hasNext()) {
            XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
            XWPFRun xrun = paragraph.createRun();
            xrun.setText("这是第"+ ind++ +"个段落！");

            //插入表格
            XmlCursor cursor = paragraph.getCTP().newCursor();
            XWPFTable tb = document.insertNewTbl(cursor);
            //行
            XWPFTableRow row = tb.getRow(0);
            row.addNewTableCell();
            row.getCell(0).setText("0");
            row.getCell(1).setText("1");
        }
        return document;
    }

    /**
     * 替换变量
     * @param document
     * @return XWPFDocument
     */
    private static XWPFDocument replaceValue(XWPFDocument document,Map<String,String> replaceMap){
        Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
        while (itPara.hasNext()) {
            XWPFParagraph paragraph =  itPara.next();
            List<XWPFRun> runs = paragraph.getRuns();
            for (int i = 0; i < runs.size(); i++)
            {
                String oneparaString = runs.get(i).getText(runs.get(i).getTextPosition());
                System.out.println(oneparaString);
                for (Map.Entry<String,String> entry : replaceMap.entrySet()) {
                    System.out.println("------0"+entry.getValue());
                    oneparaString = oneparaString.replace("${"+entry.getKey()+"}",entry.getValue());
                }
//                runs.get(i).setText(oneparaString,i);
                setTextWithBreak(runs,oneparaString,i);
            }
        }
        return document;
    }

    private static void setTextWithBreak(List<XWPFRun> runs,String paraString,Integer index){
        String[] oneparaArray = paraString.split("\n");
        //将原来数据置空
        runs.get(index).setText("",index);
        for (int j = 0; j < oneparaArray.length ; j++) {
            System.out.println("----\n----"+oneparaArray[j]);
            runs.get(index).setText(oneparaArray[j]);
            if(j < oneparaArray.length - 1){
                runs.get(index).addBreak();
            }
        }
    }

    /**
     * 替换表格变量
     * @param document
     * @return XWPFDocument
     */
    private static XWPFDocument replaceTableValue(XWPFDocument document,Map<String,List<Map<String,String>>> replaceTableMap){

        Iterator<XWPFTable> itTable = document.getTablesIterator();
        List<String> replaceList = new ArrayList<>();
        int ind = 0;
        while (itTable.hasNext()){
            ind++;
            XWPFTable table = itTable.next();
            //行
            int rcount = table.getNumberOfRows();
            List<String> notesList = new ArrayList<>();
            for (int i = 0; i < rcount; i++){
                XWPFTableRow row = table.getRow(i);
                //列
                List<XWPFTableCell> cells = row.getTableCells();
                int len = cells.size();
                for(int j = 0;j < len;j++){
                    XWPFTableCell xc = cells.get(j);
                    String sc = xc.getText();
                    System.out.println("第"+ ind +"个表格，第"+ (i+1) +"行，第"+ (j+1) +"列：" +sc);
                    if(i == 1){
                        // 获取注释配置
                        notesList.add(sc);

                    }

                }

                if(i == 1){
                    table.removeRow(i);
                }
            }

            for(Map.Entry<String,List<Map<String,String>>> entry:replaceTableMap.entrySet()){
                List<Map<String,String>> mapList = entry.getValue();
                for(int i = 0 ;i < mapList.size();i++){
                    Map<String,String> map = mapList.get(i);
                    XWPFTableRow newRow = table.createRow();
                    for(int j = 0 ; j < notesList.size();j++){
                        String key = pattern(notesList.get(j));
                        System.out.println("-----------"+i+"-------key------"+key+"--------value-------"+map.get(key));
                        newRow.getCell(j).setText(map.get(key));
                    }
                }
            }

        }

        return document;
    }

    private static String pattern(String str){
        Pattern compile = Pattern.compile("\\$\\{([^}]*)\\}");
        Matcher matcher = compile.matcher(str);
        if(matcher.find()){
            System.out.println("lllllllllll:{}"+matcher.group());
            System.out.println("wwwwwwwwwww:{}"+matcher.group(1));
            return matcher.group(1);
        }else{
            return null;
        }
    }
    /**
     * 写入文件到磁盘
     * @param document
     * @param path
     */
    private static void writeDoc(XWPFDocument document,String path){
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(path);
            document.write(fOut);
            fOut.close();
            fOut = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 遍历段落内容
     * @param document
     * @return XWPFDocument
     */
    private static XWPFDocument readPar(XWPFDocument document){
        Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
        while (itPara.hasNext()) {
            XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
            //run表示相同区域属性相同的字符，结果以‘，’分隔；
            List<XWPFRun> runs = paragraph.getRuns();
            for (int i = 0; i < runs.size(); i++)
            {
                String oneparaString = runs.get(i).getText(runs.get(i).getTextPosition());
                System.out.println(oneparaString);
            }
        }
        return document;
    }

    /**
     * 遍历所有表格的内容
     * @param document
     */
    private static void readTableContent(XWPFDocument document){
        Iterator<XWPFTable> itTable = document.getTablesIterator();
        int ind = 0;
        while (itTable.hasNext()){
            ind++;
            XWPFTable table = (XWPFTable) itTable.next();
            //行
            int rcount = table.getNumberOfRows();
            for (int i = 0; i < rcount; i++){
                XWPFTableRow row = table.getRow(i);
                //列
                List<XWPFTableCell> cells = row.getTableCells();
                int len = cells.size();
                for(int j = 0;j < len;j++){
                    XWPFTableCell xc = cells.get(j);
                    String sc = xc.getText();
                    System.out.println("第"+ ind +"个表格，第"+ (i+1) +"行，第"+ (j+1) +"列：" +sc);
                }
            }
        }
    }

    private static void addCustomHeadingStyle(XWPFDocument docxDocument, String strStyleId, int headingLevel) {

        CTStyle ctStyle = CTStyle.Factory.newInstance();
        ctStyle.setStyleId(strStyleId);

        CTString styleName = CTString.Factory.newInstance();
        styleName.setVal(strStyleId);
        ctStyle.setName(styleName);

        CTDecimalNumber indentNumber = CTDecimalNumber.Factory.newInstance();
        indentNumber.setVal(BigInteger.valueOf(headingLevel)); // lower number > style is more prominent in the formats bar
        ctStyle.setUiPriority(indentNumber);

        CTOnOff onoffnull = CTOnOff.Factory.newInstance();
        ctStyle.setUnhideWhenUsed(onoffnull); // style shows up in the formats bar
        ctStyle.setQFormat(onoffnull); // style defines a heading of the given level
        CTPPr ppr = CTPPr.Factory.newInstance();
        ppr.setOutlineLvl(indentNumber);
        ctStyle.setPPr(ppr);

        XWPFStyle style = new XWPFStyle(ctStyle); // is a null op if already defined
        XWPFStyles styles = docxDocument.createStyles();

//        style.setType(STStyleType.PARAGRAPH);
        styles.addStyle(style);

    }

    private static  void setText(XWPFDocument docxDocument,String string,Boolean isBold){
        XWPFParagraph paragraph = docxDocument.createParagraph();
        //  设置格式
        addCustomHeadingStyle(docxDocument, "五号", 1);
        XWPFRun run = paragraph.createRun();
        run.setText(string);
        run.setBold(isBold);
        //换行
//        run.addBreak();
        paragraph.setStyle("五号");
    }

    private static void setCell(XWPFTableCell cell){
        //单元格属性
        CTTcPr cellPr = cell.getCTTc().addNewTcPr();
        cellPr.addNewVAlign().setVal(STVerticalJc.CENTER);
        //设置宽度
        cellPr.addNewTcW().setW(BigInteger.valueOf(3000));
    }
    private static void insertTable(XWPFDocument docxDocument){
        XWPFParagraph paragraph = docxDocument.createParagraph();

        //插入表格
        XmlCursor cursor = paragraph.getCTP().newCursor();
        XWPFTable tb = docxDocument.insertNewTbl(cursor);
        //行
        XWPFTableRow row = tb.getRow(0);
        row.setCantSplitRow(true);
        row.setHeight(500);
        row.getCell(0).setText("标题");
        setCell(row.getCell(0));

        row.addNewTableCell();
        row.getCell(1).setText("描述");
        setCell(row.getCell(1));
        row.addNewTableCell();
        row.getCell(2).setText("工作建议");
        setCell(row.getCell(2));

//        XWPFTable tb2 = docxDocument.insertNewTbl(cursor);
        XWPFTableRow row2 = tb.createRow();
        row.setHeight(500);
//        row2.addNewTableCell();
        row2.getCell(0).setText("${title}");
        setCell(row2.getCell(0));
        row2.getCell(1).setText("${desc}");
        setCell(row2.getCell(1));
        row2.getCell(2).setText("${support}");
        setCell(row2.getCell(2));
    }

//    更新时间：
//    ${updatetime}
//    项目简介：
//    ${introduction}
//    项目进展：
//    ${progress}
//    存在的问题：
//    标题 描述 工作建议
//    ${title} ${desc} ${support}
//    工作计划：
//    ${plan}
    public static void writeSimpleDocxFile() throws IOException {
        XWPFDocument docxDocument = new XWPFDocument(); // 中文版的最好还是按照word给的标题名来，否则级别上可能会乱
        //  设置格式
//        addCustomHeadingStyle(docxDocument, "五号", 1);
//        addCustomHeadingStyle(docxDocument, "标题 2", 2); // 标题1
//        XWPFParagraph paragraph = docxDocument.createParagraph();
//        XWPFRun run = paragraph.createRun();
        setText(docxDocument,"更新时间：",true);
        setText(docxDocument,"${updatetime}",false);
        setText(docxDocument,"",false);
        setText(docxDocument,"项目简介：",true);
        setText(docxDocument,"${introduction}",false);
        setText(docxDocument,"",false);
        setText(docxDocument,"项目进展：",true);
        setText(docxDocument,"${progress}",false);
        setText(docxDocument,"",false);
        setText(docxDocument,"存在的问题：",true);
        insertTable(docxDocument);
//        insertTable3(docxDocument);
//        setText(docxDocument,"标题 描述 工作建议");
//        setText(docxDocument,"${title} ${desc} ${support}");
        setText(docxDocument,"工作计划：",true);
        setText(docxDocument,"${plan}",false);
//        insertTable2(docxDocument);

//        XWPFParagraph paragraph2 = docxDocument.createParagraph();
//        XWPFRun run2 = paragraph2.createRun();
//        run2.setText("标题 2${bbb}");
//        paragraph2.setStyle("标题 2"); // 正文
//        // 创建段落
//        XWPFParagraph paragraphX = docxDocument.createParagraph();
//        XWPFRun runX = paragraphX.createRun();
//        runX.setText("正文"); // word写入到文件

//        //插入表格
//        XmlCursor cursor = paragraph.getCTP().newCursor();
//        XWPFTable tb = docxDocument.insertNewTbl(cursor);
//        //行
//        XWPFTableRow row = tb.getRow(0);
//        row.setCantSplitRow(false);
//        row.getCell(0).setText("标题");
//        row.addNewTableCell();
//        row.getCell(1).setText("描述");
//        row.addNewTableCell();
//        row.getCell(2).setText("工作建议");
//
////        XWPFTable tb2 = docxDocument.insertNewTbl(cursor);
//        XWPFTableRow row2 = tb.createRow();
////        row2.addNewTableCell();
//        row2.getCell(0).setText("${title}");
//        row2.getCell(1).setText("${desc}");
//        row2.getCell(2).setText("${support}");


        FileOutputStream fos = new FileOutputStream(docxReadPath);
        docxDocument.write(fos);
        fos.close();
    }

    public static void main(String[] args) throws IOException {
        // 新建word
        writeSimpleDocxFile();

        Map<String,String> replaceMap = new HashMap();

        replaceMap.put("updatetime","替换\na");
        replaceMap.put("introduction","替换\nb");
        replaceMap.put("progress","替换\nbprogress");
        replaceMap.put("plan","替换\nplan");

        Map<String,List<Map<String,String>>> replaceTableMap = new HashMap();

        List<Map<String,String>> valueList = new ArrayList<>();
        Map<String,String> valueMap = new HashMap<>();
        valueMap.put("title","1111title\n1111");
        valueMap.put("desc","11111desc111");
        valueMap.put("support","1111support1111");

        Map<String,String> valueMap2 = new HashMap<>();
        valueMap2.put("title","111title11111");
        valueMap2.put("desc","11111desc111");
        valueMap2.put("support","111support11111");

        valueList.add(valueMap2);
        valueList.add(valueMap);

        replaceTableMap.put("problem",valueList);

        XWPFDocument document = read_file(docxReadPath);
        document = replaceValue(document,replaceMap);
        document = replaceTableValue(document,replaceTableMap);
//        document = insertParagraph(document);
//        readPar(document);
//        //重新写入，否则表格内容读取不到。
        writeDoc(document, docxWritePath);
        document = read_file(docxWritePath);
        readTableContent(document);
    }
}

