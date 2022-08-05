package eu.brainfree.product.insert.service.logic.xml;

import eu.brainfree.product.insert.service.logic.utils.InsertUtilsService;
import eu.brainfree.product.insert.service.model.TemplateValueTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author Max on 08.07.2022
 * @project xml-gnerator
 * @date 08.07.2022
 **/


@Service
@RequiredArgsConstructor
public class XMLInsertTemplateService {

    private final InsertUtilsService insertService;

    public String getTemplateHeader(int id, String author) {

        return getXMLHeader()
                + getChangeLogHeader()
                + getChangeSetHeader(id, author);

    }


    public String getInsertTemplate(TemplateValueTO to) {
        return getInsertOpen() +
                getColumn("id", insertService.getUUIDAsString())
                + getColumn("name", to.getTitle())
                + getColumn("category", to.getCategory())
                + getColumn("description", to.getDescription())
                + getColumn("price", to.getPrice())
                + getColumn("price_according", to.getPriceAccording())
                + getColumn("article_number", insertService.getGeneratedArticleNumber())
                + getColumn("created_date", "now()")
                + getInsertClose()
                + getChangeSetHeaderClose();
    }


    public String getTemplateForExistFile(int id, String author, TemplateValueTO to) {
        return "\n"
                + "<!-- Created: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss", Locale.GERMANY))
                + " --> \n"
                + getChangeSetHeader(id, author)
                + getInsertOpen()
                + getColumn("id", insertService.getUUIDAsString())
                + getColumn("name", to.getTitle())
                + getColumn("category", to.getCategory())
                + getColumn("description", to.getDescription())
                + getColumn("price", to.getPrice())
                + getColumn("price_according", to.getPriceAccording())
                + getColumn("article_number", insertService.getGeneratedArticleNumber())
                + getColumn("created_date", "now()")
                + getInsertClose()
                + getChangeSetHeaderClose();
    }

    private String getColumn(String name, String value) {
        return String.format("""
                <column name="%s" value="%s"/> \n
                """, name, value);
    }


    private String getChangeSetHeader(int id, String author) {
        return String.format(
                """
                         <changeSet id="%s" author="%s"> \n
                        """,
                id, author);
    }

    private String getInsertOpen() {
        return
                """
                                 <insert catalogName="products"
                                                dbms="postgresql"
                                                schemaName="public"
                                                tableName="products"> \n
                        """;
    }

    private String getXMLHeader() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n";
    }

    private String getChangeLogHeader() {
        return """
                        <databaseChangeLog
                                xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
                                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                                xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
                                 https://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd"> \n
                """;
    }

    public String getChangeLogHeaderClose() {
        return "</databaseChangeLog> \n";
    }

    private String getChangeSetHeaderClose() {
        return "</changeSet> \n";
    }

    private String getInsertClose() {
        return "</insert> \n";
    }



}
