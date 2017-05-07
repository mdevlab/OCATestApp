package io.mdevlab.ocatestapp.test;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.mdevlab.ocatestapp.R;
import io.mdevlab.ocatestapp.model.Answer;
import io.mdevlab.ocatestapp.model.Chapter;
import io.mdevlab.ocatestapp.model.Question;
import io.mdevlab.ocatestapp.model.TestAnswer;
import io.mdevlab.ocatestapp.model.TestQuestion;
import io.mdevlab.ocatestapp.modelManager.AnswerManager;
import io.mdevlab.ocatestapp.modelManager.ChapterManager;
import io.mdevlab.ocatestapp.modelManager.QuestionManager;
import io.mdevlab.ocatestapp.util.Constants;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class ChapterTest {

    private static final String TAG = ChapterTest.class.getSimpleName();

    public static void deleteChapters() {
        ChapterManager.deleteChapters();
    }

    public static void readChapters() {
        RealmResults<Chapter> chapters = ChapterManager.getAllChapters();
        for (Chapter chapter : chapters)
            Log.e(TAG, chapter.toString());
    }

    public static Chapter createChapter(int index) {
        Chapter randomChapter = new Chapter();
        randomChapter.setId(ChapterManager.getNextIndex());
        randomChapter.setName("Chapter " + index);
        randomChapter.setSummary("Summary " + index);
        randomChapter.setQuestions(QuestionTest.createRandomQuestions(index));
        return randomChapter;
    }

    private static RealmList<Question> createRandomQuestions(int index) {
        RealmList<Question> questions = new RealmList<>();
        questions.add(createRandomQuestion(index));
        questions.add(createRandomQuestion(index));
        return questions;
    }

    public static Question createRandomQuestion(int index) {
        Question randomQuestion = new Question();
        randomQuestion.setId(QuestionManager.getNextIndex());
        randomQuestion.setType(Constants.SINGLE_ANSWER_QUESTION);
        randomQuestion.setExplanation("Explanation " + index);
        randomQuestion.setFavorite(true);

        randomQuestion.setAnswers(createRandomAnswers(index));
        return randomQuestion;
    }

    public static TestQuestion createRandomMultipleTestQuestion(int index, Context context) {
        TestQuestion randomQuestion = new TestQuestion();
        randomQuestion.setId(QuestionManager.getNextIndex());
        randomQuestion.setType(Constants.MULTIPLE_ANSWER_QUESTION);
        randomQuestion.setExplanation("Explanation " + index);
        randomQuestion.setFavorite(true);
        randomQuestion.setFlagged(false);
        randomQuestion.setStatement(context.getString(R.string.dummy_question));
        randomQuestion.setExplanation(context.getString(R.string.dummy_explanation));
        randomQuestion.setAnswers(createRandomTestAnswers(index));
        return randomQuestion;
    }

    public static TestQuestion createRandomSingleTestQuestion(int index, Context context) {
        TestQuestion randomQuestion = new TestQuestion();
        randomQuestion.setId(QuestionManager.getNextIndex());
        randomQuestion.setType(Constants.SINGLE_ANSWER_QUESTION);
        randomQuestion.setExplanation("Explanation " + index);
        randomQuestion.setFavorite(true);
        randomQuestion.setFlagged(false);
        randomQuestion.setStatement(context.getString(R.string.dummy_question));
        randomQuestion.setExplanation(context.getString(R.string.dummy_explanation));
        randomQuestion.setAnswers(createRandomTestAnswers(index));
        return randomQuestion;
    }


    public static List<TestQuestion> createListTestQuestion(Context mcontext) {
        List<TestQuestion> testQuestions = new ArrayList<>();
        for (int i = 0; i < 35; i++) {
            testQuestions.add(createRandomMultipleTestQuestion(i, mcontext));
        }
        for (int i = 35; i < 70; i++) {
            testQuestions.add(createRandomSingleTestQuestion((i), mcontext));
        }
        return testQuestions;
    }

    public static List<TestQuestion> createRandomTestQuestion(Context mcontext, int numrOfQuestions) {
        List<TestQuestion> testQuestions = new ArrayList<>();
        for (int i = 0; i < numrOfQuestions; i++) {
            testQuestions.add(createRandomSingleTestQuestion((i), mcontext));
        }
        return testQuestions;
    }


    public static RealmList<Answer> createRandomAnswers(int index) {
        RealmList<Answer> answers = new RealmList<>();
        answers.add(createRandomAnswer(index));
        answers.add(createRandomAnswer(index));
        return answers;
    }

    public static RealmList<TestAnswer> createRandomTestAnswers(int index) {
        RealmList<TestAnswer> answers = new RealmList<>();
        answers.add(createRandomTestAnswer(index));
        answers.add(createRandomTestAnswer(index));
        return answers;
    }

    public static Answer createRandomAnswer(int index) {
        Answer randomAnswer = new Answer();
        randomAnswer.setId(AnswerManager.getNextIndex());
        randomAnswer.setAnswer("Answer " + index);
        randomAnswer.setCorrect(true);
        return randomAnswer;
    }

    public static TestAnswer createRandomTestAnswer(int index) {
        TestAnswer randomAnswer = new TestAnswer();
        randomAnswer.setId(AnswerManager.getNextIndex());
        randomAnswer.setAnswer("Answer " + index);
        return randomAnswer;
    }

    private static void generateChapter() {
        ChapterManager.createChapter(createChapter(200));
    }

    public static void runAllTest() {
        deleteChapters();
        generateChapter();
        readChapters();
    }

    /**
     * Dummy Chapters 1,2,3,4,5,6
     *
     * @return
     */
    public static List<Chapter> prepareChapters() {
        List<Chapter> chapterList = new ArrayList<>();
        chapterList.add(ChapterTest.createChapter(0));
        chapterList.add(ChapterTest.createChapter(1));
        chapterList.add(ChapterTest.createChapter(2));
        chapterList.add(ChapterTest.createChapter(3));
        chapterList.add(ChapterTest.createChapter(4));
        chapterList.add(ChapterTest.createChapter(5));
        return chapterList;
    }
}
