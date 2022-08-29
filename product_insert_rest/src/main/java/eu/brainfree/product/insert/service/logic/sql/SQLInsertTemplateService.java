package eu.brainfree.product.insert.service.logic.sql;


import eu.brainfree.product.insert.service.logic.utils.InsertUtilsService;
import eu.brainfree.product.insert.service.model.TemplateValueTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author Max on 01.08.2022
 * @project xml-generator-parent
 * @date 01.08.2022
 **/

@Service
@RequiredArgsConstructor
public class SQLInsertTemplateService {

    private final InsertUtilsService insertService;

    private final String LINE_BREAK = "\n";

    private final ZoneId ZONE = ZoneId.of("Europe/Berlin");


    public String getInsertCommand() {
        return """
                INSERT INTO public.products (id, name, description, price,
                 price_according, article_number, category, created_date, last_modified_date)                                                         
                \n
                VALUES
                """;
    }

    public String getInsertValuesTemplate(TemplateValueTO template, boolean lastStatement) {
        String id = insertService.getUUIDAsString();
        String articleNumber = insertService.getGeneratedArticleNumber();
        String category = template.getCategory();
        String createdAt = getFormattedDate();
        String description = template.getDescription();
        String name = template.getTitle();
        String price = template.getPrice();
        String priceAccording = template.getPriceAccording();
        String close = lastStatement? ");" : "),";

        return LINE_BREAK.repeat(2)
                + "/**/"
                + LINE_BREAK
                + "("
                + inSqlSyntax(id) + ","
                + inSqlSyntax(name) + ","
                + LINE_BREAK
                + inSqlSyntax(description) + ","
                + LINE_BREAK
                + price + ","
                + inSqlSyntax(priceAccording) + ","
                + inSqlSyntax(articleNumber) + ","
                + inSqlSyntax(category) + ","
                + inSqlSyntax(createdAt) + ","
                + "null"
                + close;
    }

    private String getFormattedDate() {
       final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime
                .now(ZONE)
                .format(formatter);
    }


    private String inSqlSyntax(String value){
        return String.format("%s%s%s", "'",value,"'");
    }
}
