package eu.brainfree.product.insert.service.logic.utils;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Max on 01.08.2022
 * @project xml-generator-parent
 * @date 01.08.2022
 **/

@Service
@RequiredArgsConstructor
public class InsertUtilsService {

    public String getUUIDAsString() {
        return UUID.randomUUID().toString();
    }

    public String getGeneratedArticleNumber() {
        return String.format("%s-%s-%s",
                RandomStringUtils.randomAlphabetic(2).toUpperCase(),
                RandomUtils.nextInt(99999, 1000000),
                RandomStringUtils.randomAlphabetic(1).toUpperCase());
    }
}
