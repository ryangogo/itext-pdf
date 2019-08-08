package com.ryan.itextpdf.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * describe：
 * Created with IDEA
 * author:ryan
 * Date:2019/8/6
 * Time:下午3:08
 */

public class ItextUtil {


    public static void generatePdfStream(String fileName, String fontName,
                                         Map<String, String> data, HttpServletResponse response) throws Exception {
        PdfReader reader = new PdfReader(fileName);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        /* 将要生成的目标PDF文件名称 */
        PdfStamper ps = new PdfStamper(reader, bos);
        PdfContentByte under = ps.getUnderContent(1);
        /* 使用中文字体 */
        BaseFont bf = BaseFont.createFont(fontName, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        ArrayList<BaseFont> fontList = new ArrayList<>();
        fontList.add(bf);
        /* 取出报表模板中的所有字段 */
        AcroFields fields = ps.getAcroFields();
        fields.setSubstitutionFonts(fontList);
        fillData(fields, data);
        /* 必须要调用这个，否则文档不会生成的 */
        ps.setFormFlattening(true);
        ps.close();
        //在建多一份document导出。
        Document doc = new Document();
        PdfCopy copy = new PdfCopy(doc, response.getOutputStream());
        doc.open();
        PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
        copy.addPage(importPage);

        response.setHeader("Content-disposition", "formPDF");
        response.setHeader("Content-type", "application/pdf;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename=formPDF.pdf");
        response.setHeader("Pragma", "No-cache");

        doc.close();
    }

    public static void fillData(AcroFields fields, Map<String, String> data)
            throws IOException, DocumentException {
        for (String key : data.keySet()) {
            String value = data.get(key);
            fields.setField(key, value); // 为字段赋值,注意字段名称是区分大小写
        }
    }

}
