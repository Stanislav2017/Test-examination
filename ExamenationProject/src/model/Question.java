package model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Question {

    @JsonProperty
    private Integer id;

    @JsonProperty("question_text")
    private String questionText;

    @JsonProperty("image_path")
    private String imagePath;

    @JsonProperty("answers")
    private List<VariantAnswer> answers = new ArrayList<>();

    public Question(Integer id, String questionText, String imagePath, List<VariantAnswer> answers) {
        this.id = id;
        this.questionText = questionText;
        this.imagePath = imagePath;
        this.answers = answers;
    }

    public Question() {
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", answers=" + answers +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<VariantAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<VariantAnswer> answers) {
        this.answers = answers;
    }
}
