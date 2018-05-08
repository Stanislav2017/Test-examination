package component;

import model.RightAnswer;

import java.io.IOException;
import java.util.List;

public interface AnswerComponent {

    List<RightAnswer> getRightAnswes() throws IOException;
}
