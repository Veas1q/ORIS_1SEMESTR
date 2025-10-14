package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class TemplateHandler {

    public void handle(String templateName, Map<String, String> data, Writer writer) throws IOException {//передаем им
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("templates/" + templateName); // 1. ЧТЕНИЕ ШАБЛОНА ИЗ РЕСУРСОВ
            String template = new String(is.readAllBytes(), StandardCharsets.UTF_8);  // Читаем все байты из InputStream и преобразуем в строку UTF-8
            //ищем сторку "${....}" и меняем
            for (Map.Entry<String, String> entry : data.entrySet()) {
                String param = "${" + entry.getKey() + "}"; // строка которую ищем в template
                template = template.replace(param, entry.getValue()); //меняем на наши значения которые передовались в классе TemplateTest
            }

            writer.write(template); //перезаписываем наш html файл


        } catch (IOException e) {
            System.out.println("Шаблон не найден");
            throw new RuntimeException(e);
        }
    }
}

