package br.edu.ifrs.canoas.richardburton.books;

import java.io.*;
import java.util.*;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;


@Stateless
public class ExportBookDataImpl extends OriginalBookResourceImpl implements ExportBookData {
    
    @Inject
    private TranslatedBookService translatedBookService;
    
    protected TranslatedBookService getTranslationService() {
        return translatedBookService;
    }
        
    @Inject
    private OriginalBookService originalBookService;

    @Override
    protected OriginalBookService getService() {
        return originalBookService;
    }

    public PDPageContentStream writeSearched(List<TranslatedBook> translations,
            PDPageContentStream contentStream, PDType0Font font, PDDocument document, PDPage page) throws IOException {
        
        PDRectangle mediabox = page.getMediaBox();
        float margin = 52;
        float startY = mediabox.getUpperRightY() - margin;
        float heightCounter = startY;
        
        contentStream.newLine();
        contentStream.setFont(font, 12);
        contentStream.setLeading(12.5f);

        for (final TranslatedBook translation : translations){
            
            String info = translation.formatToPDF().replace("ISBN: null", "");
            String[] split = info.split("\n");
            if (heightCounter< 40){
                contentStream = addPage(contentStream,document, font);
                heightCounter = mediabox.getUpperRightY() - 22;
            }            
            contentStream.newLine();
            contentStream.showText("Original book: "+ translation.getOriginal().getPDFString());//Write original book info
            contentStream.newLine();
            contentStream.showText("Translated by: " + split[0]);//Write translation authors
            heightCounter -=30;
            for (int x =1; x< split.length; x++){
                if (heightCounter< 30){
                    contentStream = addPage(contentStream,document, font);
                    heightCounter = mediabox.getUpperRightY() - 22;
                }    
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("       " + split[x]+ heightCounter);// Write publication info
                heightCounter -=25;
            }
            contentStream.newLine();
        }
        return contentStream; 
    }

    public PDPageContentStream writeAll(PDPageContentStream contentStream, PDType0Font font,
             PDDocument document, PDPage page )throws IOException {
        
        PDRectangle mediabox = page.getMediaBox();
        float margin = 22;
        float startY = mediabox.getUpperRightY() - 72;
        float heightCounter = startY;
        
        //ORIGINAL
        final List<OriginalBook> books = originalBookService.retrieve();

        contentStream.setLeading(0);
        String bookInfo = "";
        for (final OriginalBook book : books) {
            bookInfo = book.getPDFString();
            if (heightCounter< 110){
                contentStream = addPage(contentStream,document, font);
                heightCounter = mediabox.getUpperRightY()-22;
            }            
            
            contentStream.setFont(font, 14);
            contentStream.setLeading(16.5f);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText(" ");
            contentStream.newLine();
            contentStream.showText(bookInfo);// Write original title and authors
            heightCounter -=35;

            //TRANSLATIONS
            List<TranslatedBook> translations = book.getTranslations();
            contentStream.setFont(font, 12);
            contentStream.setLeading(12.5f);
            for (final TranslatedBook translation : translations){
                
                if (heightCounter< 100){
                    contentStream = addPage(contentStream,document, font);
                    heightCounter = mediabox.getUpperRightY()-margin;
                }

                String info = translation.formatToPDF().replace("ISBN: null", "");
                String[] split = info.split("\n");
                contentStream.newLine();
                contentStream.newLine();
                contentStream.showText("Translated by: " + split[0]);//Write translation authors
                heightCounter -=20;
                
                for (int x =1; x< split.length; x++){
                    if (heightCounter< 75){
                        contentStream = addPage(contentStream,document, font);
                        heightCounter = mediabox.getUpperRightY()-margin;
                    }
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("       " + split[x]);// Write publication info
                    heightCounter -=20;
                }
            }
            
        }
        return contentStream;
    }
    
    public ByteArrayOutputStream writePDF(List<TranslatedBook> translations, Boolean writeAll) throws IOException {
        InputStream fontStream = new FileInputStream("/home/ana/Documentos/Roboto-Regular.ttf");
        //InputStream fontStream = ExportDataResourceImpl.class.getClassLoader().getResourceAsStream("Roboto-Regular.ttf");
        PDType0Font font = PDType0Font.load(new PDDocument(), fontStream, true);
        
        PDPageContentStream contentStream;
        ByteArrayOutputStream output =new ByteArrayOutputStream();
        
        PDDocument document =new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        contentStream = new PDPageContentStream(document, page);
        
        contentStream.beginText();
        contentStream.setFont(font, 20);
        contentStream.setLeading(22.5f);
        contentStream.newLineAtOffset(10, 760);
        contentStream.showText("Richard Burton Platform");

        if(writeAll){
            contentStream = writeAll(contentStream, font, document, page);
        }
        else{
            contentStream = writeSearched(translations, contentStream, font, document, page);
        }

        contentStream.endText();
        contentStream.close();

        document.save(output);
        document.close();
        return output;
    }
    
    public PDPageContentStream addPage (PDPageContentStream oldContentStream, PDDocument document, PDType0Font font) throws IOException{
        oldContentStream.endText();
        oldContentStream.close();
        
        PDPage blankPage = new PDPage();
        document.addPage(blankPage);
        PDPageContentStream contentStream = new PDPageContentStream(document,blankPage);
        contentStream.beginText();
        contentStream.newLineAtOffset(10, 800);
        contentStream.setFont(font, 12);
        contentStream.setLeading(12.5f);
        contentStream.newLineAtOffset(0, -22);
        return contentStream;
    }

    public File createFile(List<TranslatedBook> translations, Boolean writeAll) throws IOException{
        File resultFile = File.createTempFile("doc",".pdf");
        ByteArrayOutputStream byteArrayOutputStream = writePDF(translations, writeAll);
        try(OutputStream outputStream = new FileOutputStream(resultFile)) {
            byteArrayOutputStream.writeTo(outputStream);
        }
        System.out.println("Please find your PDF File here: " + resultFile.getAbsolutePath());
        return resultFile;
    }

    private String getCSV(){
        String bookInfo = "";

        //ORIGINAL
        final List<OriginalBook> books = originalBookService.retrieve();

        for (final OriginalBook book : books) {
            
            bookInfo = bookInfo.concat(book.getCSVString()+"\n");
            
            //PUBLICATIONS
            final List<TranslatedBook> translations = book.getTranslations();
                
            for (final TranslatedBook translation : translations){
                bookInfo = bookInfo.concat(translation.formatToCSV()+"\n");
            }
        }
        return bookInfo;
    }
    
    public Response retrieveCSV(){
        final File file = new File ("data.csv");

        
        try (PrintWriter writer = new PrintWriter(file)) {
            
            final String CSV = getCSV();
            final StringBuilder sb = new StringBuilder();
            sb.append(CSV);
            
            writer.write(sb.toString());
            
            return Response.ok(file,"text/plain").build();

        } catch (final FileNotFoundException e) {

            System.out.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    public Response retrievePDF(){
        
        try{
            File file = createFile(null, true);
            return Response.ok(file,"application/pdf").build();
            
        }
        catch (IOException io){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
    }
    
    public Response retrievePDFSearch (Long afterId, int pageSize, String queryString, boolean useDefaultFields){
        List<TranslatedBook> books = getTranslationService().search(Long.valueOf(0), 500, queryString, useDefaultFields);

        try{
            File file = createFile(books,false);
            return Response.ok(file,"application/pdf").build();
            
        }
        catch (IOException io){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
}