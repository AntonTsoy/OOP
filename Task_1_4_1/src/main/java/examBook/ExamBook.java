package examBook;

import java.util.ArrayList;

/**
 * Represents an exam book for a student with information about their semesters and performance.
 */
public class ExamBook {
    private int currentSemester;   // The number of the current semester.
    private int qualifiedWork;   // The mark for the qualification work.
    private ArrayList<Semester> semesters;   // The list of semesters for the student.

    public final String student;   // The name of the student.

    /**
     * Constructs a new ExamBook object for the given student name.
     *
     * @param name the name of the student.
     */
    public ExamBook(String name) {
        this.student = name;
        this.currentSemester = 0;
        this.qualifiedWork = 0;
        this.semesters = new ArrayList<>();
        newSemester(); // Create the first semester by default.
    }

    /**
     * Adds a new semester to the exam book.
     */
    public Semester newSemester() {
        Semester newHalf = new Semester(++currentSemester);
        this.semesters.add(newHalf);
        return newHalf;
    }

    /**
     * Retrieves the current semester.
     *
     * @return the current semester.
     */
    public Semester getSemester() {
        return semesters.get(currentSemester - 1);
    }

    /**
     * Retrieves the semester at the specified index.
     *
     * @param semesterNum the index of the semester to retrieve.
     * @return the semester at the specified index.
     */
    public Semester getSemester(int semesterNum) {
        return semesters.get(semesterNum - 1);
    }

    /**
     * Sets the mark for the qualification work.
     *
     * @param mark the mark for the qualification work.
     */
    public void setQualifier(int mark) {
        if (mark < 2 || mark > 5) {
            throw new IllegalArgumentException("Illegal MARK value!");
        }
        this.qualifiedWork = mark;
    }
    
    /**
     * Calculates the current average mark for all semesters.
     *
     * @return the current average mark.
     */
    public double getCurrentAverageMark() {
        int currentGrade = 0;
        int subjectsNum = 0;
        for (Semester sem : this.semesters) {
            currentGrade += sem.getGrade();
            subjectsNum += sem.quantityOfSubjects();
        }

        if (currentGrade == 0) {
            return 0;
        }

        return (double) currentGrade / (double) subjectsNum;
    }

    /**
     * Checks if the student is eligible for a raise based on the current semester's performance.
     *
     * @return true if eligible for a raise, false otherwise.
     */
    public boolean upSalary() {
        if (getSemester().getGrade() == 0) {
            return false;
        }

        return getSemester().getGrade() / getSemester().quantityOfSubjects() == 5;
    }

    /**
     * Checks if the student is eligible for a red diploma based on their academic performance.
     *
     * @return true if eligible for a red diploma, false otherwise.
     */
    public boolean redDiploma() {
        boolean isGood = true;
        for (Semester sem : this.semesters) {
            if (!sem.goodSemester()) {
                isGood = false;
                break;
            }
        }
        return currentSemester == 8 && getCurrentAverageMark() > 4.745
            && qualifiedWork == 5 && isGood;
    }
}