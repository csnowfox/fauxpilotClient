package org.csnowfox.fauxpilot.client.model;

import com.google.gson.annotations.SerializedName;

public class SendMsgObj {
    @SerializedName("max_tokens")
    private int maxTokens;
    private String prompt;
    private String[] stop;

    private double temperature;

    public int getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(int maxTokens) {
        this.maxTokens = maxTokens;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String[] getStop() {
        return stop;
    }

    public void setStop(String[] stop) {
        this.stop = stop;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
