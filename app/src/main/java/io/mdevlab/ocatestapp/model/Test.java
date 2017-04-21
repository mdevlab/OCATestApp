package io.mdevlab.ocatestapp.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class Test extends RealmObject {

    public static final String ID_COLUMN = "id";

    private int id;
    private long duration;
    private int numberOfCompletedQuestions;
    private int totalNumberOfQuestions;
    /*
    Either final test or customized test
    Both are constants and are defined in this class
     */
    private int type;
    private RealmList<TestQuestion> questions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getNumberOfCompletedQuestions() {
        return numberOfCompletedQuestions;
    }

    public void setNumberOfCompletedQuestions(int numberOfCompletedQuestions) {
        this.numberOfCompletedQuestions = numberOfCompletedQuestions;
    }

    public int getTotalNumberOfQuestions() {
        return totalNumberOfQuestions;
    }

    public void setTotalNumberOfQuestions(int totalNumberOfQuestions) {
        this.totalNumberOfQuestions = totalNumberOfQuestions;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public RealmList<TestQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(RealmList<TestQuestion> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", duration=" + duration +
                ", progress=" + numberOfCompletedQuestions + "/" + totalNumberOfQuestions +
                ", type=" + type +
                ", questions=" + questions.size() +
                '}';
    }
}