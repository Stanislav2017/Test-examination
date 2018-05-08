package model;

import org.codehaus.jackson.annotate.JsonProperty;

public class RightAnswer {

    @JsonProperty("question_id")
    private Integer questionId;

    @JsonProperty("right_variant_answer")
    private Integer rightVariantAnswer;

    public RightAnswer() {
    }

    public RightAnswer(Integer questionId, Integer rightVariantAnswer) {
        this.questionId = questionId;
        this.rightVariantAnswer = rightVariantAnswer;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getRightVariantAnswer() {
        return rightVariantAnswer;
    }

    public void setRightVariantAnswer(Integer rightVariantAnswer) {
        this.rightVariantAnswer = rightVariantAnswer;
    }
}
