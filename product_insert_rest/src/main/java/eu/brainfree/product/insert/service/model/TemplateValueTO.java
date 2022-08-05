package eu.brainfree.product.insert.service.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * @author Max on 05.08.2022
 * @project eu.brainfree.product.insert.service
 * @date 05.08.2022
 **/

@Value
@Builder
@Jacksonized
public class TemplateValueTO {

    String title;
    String category;
    String description;
    String price;
    String priceAccording;
}
