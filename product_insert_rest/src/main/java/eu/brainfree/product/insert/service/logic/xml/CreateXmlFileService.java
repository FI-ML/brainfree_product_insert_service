package eu.brainfree.product.insert.service.logic.xml;

import eu.brainfree.product.insert.service.model.FileStatusTO;
import eu.brainfree.product.insert.service.model.TemplateTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

import static eu.brainfree.product.insert.service.logic.utils.FileUtils.FILENAME;
import static eu.brainfree.product.insert.service.logic.utils.FileUtils.UPLOAD_DIRECTORY;
import static eu.brainfree.product.insert.service.logic.utils.FileUtils.XML_FILETYPE;

/**
 * @author Max on 08.07.2022
 * @project xml-gnerator
 * @date 08.07.2022
 **/


@Service
@RequiredArgsConstructor
public class CreateXmlFileService {

    private final Logger LOGGER = LogManager.getLogger(CreateXmlFileService.class);

    private final XMLInsertTemplateService xmlInsertTemplateService;

    public FileStatusTO createFileAndGetStatus(TemplateTO to) throws Exception {


        FileStatusTO fileUploadResponse = new FileStatusTO();

        write(to);
        File file = new File(UPLOAD_DIRECTORY + File.separator + FILENAME + XML_FILETYPE);
        if (file.exists()) {
            fileUploadResponse.setStatus("XML FILE WAS CREATED");
            LOGGER.info("FILE PATH: " + file.getAbsolutePath());
            return fileUploadResponse;
        }
        fileUploadResponse.setStatus("FILE NOT EXIST");
        return fileUploadResponse;
    }

    private void write(TemplateTO to) throws IOException {
        File file = new File(UPLOAD_DIRECTORY + File.separator + FILENAME + XML_FILETYPE);

        StringBuilder template = new StringBuilder();

        if (!file.exists()) {
            template.append(xmlInsertTemplateService.getChangeLogHeaderClose());
        } else {
            String txt = FileUtils.readFileToString(file, "UTF-8");
            int end = txt.length() - 24;
            LOGGER.info("TEXT TEMPLATE: " + txt.substring(end, end + 24));

            txt = txt.substring(0, end);

            template.append(txt);

            AtomicInteger id = new AtomicInteger(2);

            to.getTemplateValueTOS().forEach(insertTemplate -> {
                template.append(xmlInsertTemplateService.getTemplateForExistFile(id.get(), to.getAutor(), insertTemplate));
                id.incrementAndGet();
            });
        }
        template.append(getXmlTemplate(to));


        try (FileWriter writer = new FileWriter(file)) {
            writer.write(template.toString());
        } catch (
                IOException e) {
            LOGGER.error("can't create File " + e.getMessage());
        }

    }

    public InputStream getFileAsResource() throws IOException {

        String fileName = FILENAME + XML_FILETYPE;
        File file = new File(UPLOAD_DIRECTORY + File.separator + fileName);

        if (file.exists()) {
            return new FileInputStream(file);
        } else {
            LOGGER.error("Cant find File " + file.getName() + " in path: " + file.getPath());
            return null;
        }
    }

    private String getXmlTemplate(TemplateTO to) {

        StringBuilder template = new StringBuilder();
        template.append(xmlInsertTemplateService.getTemplateHeader(0, to.getAutor()));
        to.getTemplateValueTOS().forEach(insertTemplate -> {
            template.append(xmlInsertTemplateService.getInsertTemplate(insertTemplate));
        });
        template.append(xmlInsertTemplateService.getChangeLogHeaderClose());
        return template.toString();
    }
}
