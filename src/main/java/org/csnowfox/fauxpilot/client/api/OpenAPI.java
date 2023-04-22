package org.csnowfox.fauxpilot.client.api;

import com.google.gson.Gson;
import com.vladsch.flexmark.util.misc.Utils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.csnowfox.fauxpilot.client.model.ReceiveMsgObj;
import org.csnowfox.fauxpilot.client.model.SendMsgObj;
import org.csnowfox.fauxpilot.client.model.ChoicesObj;
import org.csnowfox.fauxpilot.client.settings.AppSettingsState;

import java.io.IOException;

public class OpenAPI {

    public static String prompt(String prompt) {
        SendMsgObj sendMsgObj = new SendMsgObj();
        sendMsgObj.setPrompt(prompt);
        sendMsgObj.setTemperature(0.1);
        sendMsgObj.setMaxTokens(200);
        sendMsgObj.setStop(new String[]{"\n\n"});

        CloseableHttpClient httpClient = HttpClients.createDefault();

        String serverUrl = AppSettingsState.getInstance().serverUrl;
        if (Utils.isBlank(serverUrl)) {
            return null;
        }

        HttpPost httpPost = new HttpPost(serverUrl);

        String promptMsg = new Gson().toJson(sendMsgObj);
        HttpEntity entity = new StringEntity(promptMsg, ContentType.APPLICATION_JSON);
        httpPost.setEntity(entity);

        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            String responseString = new String(responseEntity.getContent().readAllBytes(), "UTF-8");
            ReceiveMsgObj receiveMsgObj = new Gson().fromJson(responseString, ReceiveMsgObj.class);
            String resultContent = "";
            int i = 1;
            for (ChoicesObj choices : receiveMsgObj.getChoices()) {
                resultContent = choices.getText() + "\r\n";
            }
            resultContent = resultContent.replace("\n", "\r\n");
            return resultContent;
        } catch (Exception e1) {
            return null;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException ex) {
                }
            }
        }
    }

}
