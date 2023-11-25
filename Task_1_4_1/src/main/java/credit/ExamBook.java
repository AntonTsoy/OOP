package credit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents an exam book for a student with information about their semesters and performance.
 */
public class ExamBook {
    private int currentHalfIdx;   // The number of the current semester.
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
        this.currentHalfIdx = 0;
        this.qualifiedWork = 0;
        this.semesters = new ArrayList<>();
        newSemester(); // Create the first semester by default.
    }

    /**
     * Adds a new semester to the exam book.
     */
    public Semester newSemester() {
        Semester newHalf = new Semester(++currentHalfIdx);
        this.semesters.add(newHalf);
        return newHalf;
    }

    /**
     * Retrieves the current semester.
     *
     * @return the current semester.
     */
    public Semester getSemester() {
        return semesters.get(currentHalfIdx - 1);
    }

    /**
     * Retrieves the semester at the specified index.
     *
     * @param halfNum the index of the semester to retrieve.
     * @return the semester at the specified index.
     */
    public Semester getSemester(int halfNum) {
        return semesters.get(halfNum - 1);
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

    private double averageMarkOfSemesters(int halfBeg, int halfEnd) {
        return IntStream.range(halfBeg, halfEnd + 1)
            .mapToObj(num -> this.getSemester(num))
            .flatMap(half -> IntStream.range(1, half.quantityOfSubjects() + 1)
                .mapToObj(idx -> half.getSubject(idx)))
            .mapToInt(Subject::getMark)
            .average()
            .orElse(0.0);
    }
    
    /**
     * Calculates the current average mark for all semesters.
     *
     * @return the current average mark.
     */
    public double getCurrentAverageMark() {
        return averageMarkOfSemesters(1, currentHalfIdx);
    }

    /**
     * Checks if the student is eligible for a raise based on the current semester's performance.
     *
     * @return true if eligible for a raise, false otherwise.
     */
    public boolean upSalary() {
        return averageMarkOfSemesters(currentHalfIdx, currentHalfIdx) > 4.95;
    }

    private Collection<Integer> diplomaMarks() {
        return IntStream.range(1, 8)  
            .mapToObj(this::getSemester)
            .flatMap(half -> IntStream.range(1, half.quantityOfSubjects() + 1)  
                .mapToObj(idx -> half.getSubject(idx)))
            .collect(Collectors.toMap(subj -> subj.discipline, subj -> subj.getMark(),
                (grade1, grade2) -> grade2)).values();
    }

    /**
     * Checks if the student is eligible for a red diploma based on their academic performance.
     *
     * @return true if eligible for a red diploma, false otherwise.
     */
    public boolean redDiploma() { 
        return currentHalfIdx == 8 && !diplomaMarks().contains(2) && !diplomaMarks().contains(3) 
            && diplomaMarks().stream().mapToInt(a -> a).sum() / diplomaMarks().size() > 4.75
            && qualifiedWork == 5;
    }
}