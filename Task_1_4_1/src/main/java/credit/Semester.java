package credit;

import java.util.ArrayList;

/**
 * Represents a semester with subjects.
 */
public class Semester {
    private ArrayList<Subject> subjects; 

    public final int halfNum;

    /**
     * Constructs a new Semester object with the given semester number.
     *
     * @param number the semester number.
     */
    public Semester(int number) {
        this.halfNum = number;
        this.subjects = new ArrayList<Subject>();
    }

    /**
     * Adds a new subject to the semester.
     *
     * @param newSubject the subject to add.
     */
    public void addSubject(Subject newSubject) {
        this.subjects.add(newSubject);
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
     * Gets the quantity of subjects in the semester.
     *
     * @return the quantity of subjects.
     */
    public int quantityOfSubjects() {
        return subjects.size();
    }
}
