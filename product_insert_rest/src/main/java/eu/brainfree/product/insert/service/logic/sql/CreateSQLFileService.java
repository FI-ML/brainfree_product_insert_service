package eu.brainfree.product.insert.service.logic.sql;


import eu.brainfree.product.insert.service.model.FileStatusTO;
import eu.brainfree.product.insert.service.model.TemplateTO;
import eu.brainfree.product.insert.service.model.TemplateValueTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static eu.brainfree.product.insert.service.logic.utils.FileUtils.FILENAME;
import static eu.brainfree.product.insert.service.logic.utils.FileUtils.SQL_FILETYPE;
import static eu.brainfree.product.insert.service.logic.utils.FileUtils.UPLOAD_DIRECTORY;

/**
 * @author Max on 01.08.2022
 * @project xml-generator-parent
 * @date 01.08.2022
 **/

@Service
@RequiredArgsConstructor
public class CreateSQLFileService {

    private final Logger LOGGER = LogManager.getLogger(CreateSQLFileService.class);

    private final SQLInsertTemplateService sqlInsertTemplateService;

    public FileStatusTO createFileAndGetStatus(TemplateTO to)  {

        FileStatusTO status = new FileStatusTO();
        File file = new File(UPLOAD_DIRECTORY + File.separator + FILENAME + SQL_FILETYPE);
        StringBuilder template = new StringBuilder();
        if(file.exists()){
            template.append(getFileTemplate(file));
        }else {
            template.append(sqlInsertTemplateService.getInsertCommand());
        }

        List<TemplateValueTO> values =  to.getTemplateValueTOS();

        TemplateValueTO value =  values.get( to.getTemplateValueTOS().size() - 1);

        values.forEach(x -> {
            if(x == value){
                template.append(sqlInsertTemplateService.getInsertValuesTemplate(x, true));
            }else {
                template.append(sqlInsertTemplateService.getInsertValuesTemplate(x, false));
            }
        });

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(template.toString());
        } catch (IOException e) {
            LOGGER.error("Can't write file", e.getMessage());
        }

        if(file.exists()){
            LOGGER.info("FILE PATH: " + file.getAbsolutePath());
            status.setStatus("SQL FILE WAS CREATED");
        }else {
            status.setStatus("SQL FILE ISN'T CREATED");
        }

        return status;
    }


    private String getFileTemplate(File file) {
        if (!file.exists()) {
            return "";
        }
        try {
            String txt = FileUtils.readFileToString(file, "UTF-8");
            int end = txt.length() - 1;
            txt = txt.substring(0, end);
            return txt;

        } catch (IOException e) {
            LOGGER.error("Can't read file", e.getMessage());
            return "";
        }
    }
}
