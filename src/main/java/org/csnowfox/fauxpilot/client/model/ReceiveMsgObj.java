package org.csnowfox.fauxpilot.client.model;

public class ReceiveMsgObj {
    private String id;
    private String model;
    private String object;

    private ChoicesObj[] choices;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public ChoicesObj[] getChoices() {
        return choices;
    }

    public void setChoices(ChoicesObj[] choices) {
        this.choices = choices;
    }
}
