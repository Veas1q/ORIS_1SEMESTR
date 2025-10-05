package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler {

    final static Logger logger = LogManager.getLogger(RequestHandler.class);

    public void handle(Socket clientSocket) {
        try {
            // Поток для чтения данных от клиента
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            // Читаем пакет от клиента
            String lineOne = reader.readLine();
            System.out.println(lineOne);
            logger.debug(lineOne);
            String[] components = lineOne.split(" ");
            String method = components[0];
            //TODO реализовать определение метода (GET, POST,...) для передачи как параметра в сервис
            // http://localhost:8080/resource/part?name=tat&region=16
            // URI /resource/pa
            //
            // При наличии извлечь параметры и поместить в Map

            String path = components[1];
            int index   = path.indexOf('?');

            String resource = path;
            String query = null;
            if (index != -1){
                resource = path.substring(0, index);//resource
                query = path.substring(index + 1);//name=tat&region=16
            }
            Map<String, String> params = new HashMap<>();
            if(query != null){
                String[] entry = query.split("&");// {"name=tat", "region=16"}
                for(String s: entry){
                    String[] param = s.split("=");//{"name", "tat"}
                    if (param.length > 1){
                        params.put(param[0], param[1]);
                    }else{
                        params.put(param[0], "");
                    }
                }
            }
            if (resource.equals("/shutdown")) {
                logger.info("server stopped by client");
//                break;
            }
            while (true) {
                // Читаем пакет от клиента
                String message = reader.readLine();
                System.out.println(message);
                logger.debug(message);

                if (message.isEmpty()) {
                    logger.debug("end of request header");
                    OutputStream os = clientSocket.getOutputStream();
                    logger.debug("outputStream" + os);
                    IResourceService resourceService = Application.resourceMap.get(resource);
                    if (resourceService != null) {
                        // TODO передавать метод, передавать Map с параметрами в функцию service
                        resourceService.service("GET", params, os);
                    } else {
                        new NotFoundService().service("GET", params, os);
                    }
                    os.flush();
                    logger.debug("outputStream" + os);
                    break;
                }

                //clientSocket.close();
            }
        } catch (IOException e) {
            logger.atError().withThrowable(e);
        }

    }

}