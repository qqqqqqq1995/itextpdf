package com.xuqiang.itext.test;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;

/**
 * @author xuqiang
 * @date 2019/1/2
 */
public class PDFBuilder extends PdfPageEventHelper {

    private Phrase leftHeader;
    private Phrase rigntHeader;

    static final int marginX = 90;
    static final int marginY = 70;

    private static BaseFont baseFont;
    // 生成下划线空白占位符
    private static String Blank;
    // 页眉字体
    private static Font font;
    // 下划线字体
    private static Phrase blankPhrase;

    PDFBuilder(String[] header) {
        this.leftHeader = new Phrase(header[0], PDFBuilder.font);
        this.rigntHeader = new Phrase(header[1], PDFBuilder.font);
    }

    static {
        try {
            // 中文字体依赖itext得itext-asian包
            baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 168; i++) {
                sb.append("\u00a0");
            }
            Blank = sb.toString();
            font = new Font(PDFBuilder.baseFont, 9, Font.UNDEFINED);
            blankPhrase = new Phrase(PDFBuilder.Blank, new Font(PDFBuilder.baseFont, Font.DEFAULTSIZE, Font.UNDERLINE));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param writer
     * @param document
     */
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        int yMargin = -20;
        float top = document.top(yMargin);
        // 第一页不生成页眉页脚
        if (document.getPageNumber() == 1) {
            return;
        }
        //生成下划线，使用空格占位
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_LEFT, PDFBuilder.blankPhrase,
                document.left(-1), top, 0);
        //生成左侧页眉
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_LEFT, leftHeader,
                document.left(), top, 0);
        //生成右侧页眉
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_RIGHT, rigntHeader,
                document.right(), top, 0);
        //生成页脚页数
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_RIGHT, new Phrase(String.valueOf(document.getPageNumber() - 1), PDFBuilder.font),
                document.right(), document.bottom(yMargin), 0);

    }
}
