package component;

import model.Question;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class QuestionComponentImpl implements QuestionComponent {

    private static final Path PATH = Paths.get("/home/stanislav/myprojects/ExamenationProject/src/json/questions.json");

    private List<Question> getQuestions() throws IOException {
        byte[] bytes = Files.readAllBytes(PATH);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        List<Question> questions = objectMapper.readValue(bytes, new TypeReference<List<Question>>(){});
        return questions;
    }

    public List<Question> generateVariant() throws IOException {
        List<Question> questions = this.getQuestions();
        Random random = new Random(5);
        List<Question> groupQuestion = questions.stream()
                    .collect(Collectors.toList())
                    .stream().limit(5).collect(Collectors.toList());
        return groupQuestion;
    }
}
