package com.epam.booking.service;


import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;

@Component
public class PDFGenerationService {

    @Autowired
    SpringTemplateEngine templateEngine;


    private byte[] generatePDFfromHtmlTemplate(Context context, String view) throws DocumentException {
        String html = templateEngine.process(view, context);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        return outputStream.toByteArray();
    }

    private ResponseEntity<byte[]> generatePDFResponse(byte[] contentsBytes, String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(fileName, fileName);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(contentsBytes, headers, HttpStatus.OK);
    }

    public ResponseEntity<byte[]> createPDFandGenerateResponse(Context context, String view, String fileName) throws DocumentException {
        return generatePDFResponse(generatePDFfromHtmlTemplate(context, view), fileName);
    }

}
