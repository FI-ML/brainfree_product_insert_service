package eu.brainfree.product.insert.service.logic;

import eu.brainfree.product.insert.service.logic.sql.CreateSQLFileService;
import eu.brainfree.product.insert.service.logic.utils.FileUtils;
import eu.brainfree.product.insert.service.logic.xml.CreateXmlFileService;
import eu.brainfree.product.insert.service.model.FileStatusTO;
import eu.brainfree.product.insert.service.model.TemplateTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author Max on 01.08.2022
 * @project xml-generator-parent
 * @date 01.08.2022
 **/

@Service
@RequiredArgsConstructor
public class FileService {

    private final Logger LOGGER = LogManager.getLogger(FileService.class);

    private final CreateXmlFileService xmlFileService;

    private final CreateSQLFileService createSQLFileService;

    public FileStatusTO createAndGetStatus(TemplateTO to) throws Exception {

        if (to.getWithSQL()) {
            return createSQLFileService.createFileAndGetStatus(to);
        } else {
            return xmlFileService.createFileAndGetStatus(to);
        }

    }


    public InputStream getFileAsInputStream(boolean sql) throws FileNotFoundException {
        String fileName = null;

        if (sql) {
            fileName = FileUtils.FILENAME + FileUtils.SQL_FILETYPE;
        } else {
            fileName = FileUtils.FILENAME + FileUtils.XML_FILETYPE;
        }


        File file = new File(FileUtils.UPLOAD_DIRECTORY + File.separator + fileName);

        if (file.exists()) {
            return new FileInputStream(file);
        } else {
            LOGGER.error("Cant find File " + file.getName() + " in path: " + file.getPath());
            return null;
        }
    }
}
