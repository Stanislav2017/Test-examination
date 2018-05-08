package model;

import org.codehaus.jackson.annotate.JsonProperty;

public class VariantAnswer {

    @JsonProperty
    private Integer id;

    @JsonProperty("variant_answer")
    private String variantAnswer;

    public VariantAnswer() {
    }

    public VariantAnswer(Integer id, String variantAnswer) {
        this.id = id;
        this.variantAnswer = variantAnswer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVariantAnswer() {
        return variantAnswer;
    }

    public void setVariantAnswer(String variantAnswer) {
        this.variantAnswer = variantAnswer;
    }
}
