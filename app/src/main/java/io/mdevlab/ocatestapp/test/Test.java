package io.mdevlab.ocatestapp.test;

import android.content.Context;
import android.util.Log;

import io.mdevlab.ocatestapp.model.Chapter;
import io.mdevlab.ocatestapp.util.Mapper;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class Test {

    private static final String TAG = Test.class.getSimpleName();

    /**
     * Method that runs 2 sets of tests:
     * - Test creating a test along with its questions and answers
     * - Test creating a chapter with its questions and answers
     */
    public static void runAllTests() {
        TestAnswerTest.deleteTestAnswers();
        TestQuestionTest.deleteTestQuestions();
        TestTest.runAllTest();
        TestQuestionTest.readTestQuestions();
        TestAnswerTest.readTestAnswers();

        Log.e(TAG, "-----------------");
        Log.e(TAG, " ");
        Log.e(TAG, "-----------------");

        AnswerTest.deleteAnswers();
        QuestionTest.deleteQuestions();
        ChapterTest.runAllTest();
        QuestionTest.readQuestions();
        AnswerTest.readAnswers();
    }

    /**
     * Method that deletes all answers, questions and chapters, then regenerates them
     * To call when first populating the database
     *
     * @param context: Context of calling class
     */
    public static void runChapterJsonToRealmTest(Context context) {
        Log.e(TAG, "Started deleting..");
        AnswerTest.deleteAnswers();
        QuestionTest.deleteQuestions();
        ChapterTest.deleteChapters();

        Mapper.instance().fromJsonToRealm(context, "chapters.json", Chapter.class);

        Log.e(TAG, "Started reading..");
        ChapterTest.readChapters();
        QuestionTest.readQuestions();
        AnswerTest.readAnswers();
    }
}
