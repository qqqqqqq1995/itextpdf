package com.xuqiang.itext.test;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author xuqiang
 * @date 2018/12/29
 */
public class Test {
    public static void main(String[] args) throws Exception {
        // 创建一个文档
        Document document = new Document(PageSize.A4, PDFBuilder.marginX, PDFBuilder.marginX, PDFBuilder.marginY, PDFBuilder.marginY);
        // pdf输出流
        OutputStream outputStream = new FileOutputStream("E:\\Users\\Desktop\\工作\\吊车组杆方案模板\\吊车组杆方案-test.pdf");
        PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
        // 添加页眉页脚
        pdfWriter.setPageEvent(new PDFBuilder(new String[]{"新增糖厂坳配变解决110kV那前站10kV903线糖厂坡配变过载工程", "吊车组立电杆施工方案"}));
        document.open();
//        XMLWorkerHelper.getInstance().parseXHtml(
//                pdfWriter,
//                document,
//                new ByteArrayInputStream(
//                        "<html><style></style><body style=\"font-family: SimSun;\"><h1>第一页1p开始</h1></body></html>"
//                                .getBytes(StandardCharsets.UTF_8)),
//                StandardCharsets.UTF_8);
        // html生成pdf，注意文件要是utf-8编码
        // 不显示中文页面注意添加字体<body style=\"font-family: SimSun;\">
        // 标签格式严格，注意使用在线格式化工具格式化html代码
        XMLWorkerHelper.getInstance().parseXHtml(
                pdfWriter,
                document,
                new FileInputStream("E:\\Users\\Desktop\\工作\\吊车组杆方案模板\\吊车组杆方案.htm"),
                StandardCharsets.UTF_8);
        document.close();
    }
}
