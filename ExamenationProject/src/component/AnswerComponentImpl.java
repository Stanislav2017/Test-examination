package component;

import model.RightAnswer;
import model.VariantAnswer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AnswerComponentImpl implements AnswerComponent {

    @Override
    public List<RightAnswer> getRightAnswes() throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get("/home/stanislav/myprojects/ExamenationProject/src/json/answers.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        List<RightAnswer> variantAnswers = objectMapper.readValue(bytes, new TypeReference<List<RightAnswer>>(){});
        return variantAnswers;
    }
}
