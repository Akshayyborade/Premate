package com.Premate.Service;

import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.Premate.dto.QuestionPaper;
import com.Premate.dto.Section;
import com.Premate.dto.PaperMetadata;
import com.Premate.dto.Question;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class DocumentGenerationService {
    
    private void addHeader(Document document, PaperMetadata metadata) throws Exception {
        Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
        Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

        Paragraph title = new Paragraph(metadata.getBoard() + " - " + metadata.getSubject(), headerFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph details = new Paragraph(
            "Class: " + metadata.getClassLevel() + 
            "  |  Total Marks: " + metadata.getTotalMarks() + 
            "  |  Duration: " + metadata.getDuration() + " minutes",
            normalFont
        );
        details.setAlignment(Element.ALIGN_CENTER);
        document.add(details);
        document.add(new Paragraph("\n"));
    }

    private void addSection(Document document, Section section) throws Exception {
        Font sectionFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        Font questionFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

        Paragraph sectionTitle = new Paragraph(section.getName(), sectionFont);
        sectionTitle.setSpacingBefore(10);
        document.add(sectionTitle);

        int questionNumber = 1;
        for (Question question : section.getQuestions()) {
            Paragraph questionPara = new Paragraph(
                questionNumber + ". " + question.getText() + 
                " (" + question.getMarks() + " marks)",
                questionFont
            );
            questionPara.setIndentationLeft(20);
            document.add(questionPara);

            // Add options if it's an MCQ
            if (question.getOptions() != null && !question.getOptions().isEmpty()) {
                char optionLetter = 'a';
                for (String option : question.getOptions()) {
                    Paragraph optionPara = new Paragraph(
                        "    " + optionLetter + ") " + option,
                        questionFont
                    );
                    document.add(optionPara);
                    optionLetter++;
                }
            }
            questionNumber++;
        }
    }

    private void addHeaderDOCX(XWPFDocument document, PaperMetadata metadata) {
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        titleRun.setBold(true);
        titleRun.setFontSize(14);
        titleRun.setText(metadata.getBoard() + " - " + metadata.getSubject());

        XWPFParagraph details = document.createParagraph();
        details.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun detailsRun = details.createRun();
        detailsRun.setFontSize(12);
        detailsRun.setText(
            "Class: " + metadata.getClassLevel() + 
            "  |  Total Marks: " + metadata.getTotalMarks() + 
            "  |  Duration: " + metadata.getDuration() + " minutes"
        );

        document.createParagraph(); // Empty line
    }

    private void addSectionDOCX(XWPFDocument document, Section section) {
        XWPFParagraph sectionTitle = document.createParagraph();
        XWPFRun sectionRun = sectionTitle.createRun();
        sectionRun.setBold(true);
        sectionRun.setFontSize(12);
        sectionRun.setText(section.getName());

        int questionNumber = 1;
        for (Question question : section.getQuestions()) {
            XWPFParagraph questionPara = document.createParagraph();
            questionPara.setIndentationLeft(360); // 0.25 inch
            XWPFRun questionRun = questionPara.createRun();
            questionRun.setFontSize(12);
            questionRun.setText(
                questionNumber + ". " + question.getText() + 
                " (" + question.getMarks() + " marks)"
            );

            // Add options if it's an MCQ
            if (question.getOptions() != null && !question.getOptions().isEmpty()) {
                char optionLetter = 'a';
                for (String option : question.getOptions()) {
                    XWPFParagraph optionPara = document.createParagraph();
                    optionPara.setIndentationLeft(720); // 0.5 inch
                    XWPFRun optionRun = optionPara.createRun();
                    optionRun.setFontSize(12);
                    optionRun.setText(optionLetter + ") " + option);
                    optionLetter++;
                }
            }
            questionNumber++;
        }
    }

    public byte[] generatePDF(QuestionPaper paper) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            
            addHeader(document, paper.getMetadata());
            
            for (Section section : paper.getSections()) {
                addSection(document, section);
            }
            
            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }
    
    public byte[] generateDOCX(QuestionPaper paper) {
        XWPFDocument document = new XWPFDocument();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        try {
            addHeaderDOCX(document, paper.getMetadata());
            
            for (Section section : paper.getSections()) {
                addSectionDOCX(document, section);
            }
            
            document.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate DOCX", e);
        }
    }
} 