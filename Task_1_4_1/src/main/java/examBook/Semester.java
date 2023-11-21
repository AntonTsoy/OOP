package examBook;

import java.util.ArrayList;

/**
 * Represents a semester with subjects and grades.
 */
public class Semester {
    private int sumOfMarks;
    private boolean isGood;
    private ArrayList<Subject> subjects; 

    public final int semesterNum;

    /**
     * Constructs a new Semester object with the given semester number.
     *
     * @param number the semester number.
     */
    public Semester(int number) {
        this.semesterNum = number;
        this.sumOfMarks = 0;
        this.isGood = true;

        this.subjects = new ArrayList<Subject>();
    }

    /**
     * Adds a new subject to the semester.
     *
     * @param newSubject the subject to add.
     */
    public void addSubject(Subject newSubject) {
        this.subjects.add(newSubject);
        this.sumOfMarks += newSubject.getMark();
        if (newSubject.getMark() < 4) {
            this.isGood = false;
        }
    }

    /**
     * Retrieves the subject at the specified index.
     *
     * @param subjectIdx the index of the subject to retrieve.
     * @return the subject at the specified index.
     */
    public Subject getSubject(int subjectIdx) {
        return subjects.get(subjectIdx - 1);
    }

    /**
     * Gets the sum of marks for the semester.
     *
     * @return the sum of marks.
     */
    public int getGrade() {
        return this.sumOfMarks;
    }

    /**
     * Gets the quantity of subjects in the semester.
     *
     * @return the quantity of subjects.
     */
    public int quantityOfSubjects() {
        return subjects.size();
    }

    /**
     * Checks whether the semester is considered good based on the marks.
     *
     * @return true if the semester is good, false otherwise.
     */
    public boolean goodSemester() {
        return this.isGood && subjects.size() > 0;
    }
}
