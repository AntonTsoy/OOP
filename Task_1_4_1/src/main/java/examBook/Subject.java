package examBook;

/**
 * Class for describing subject info, which poses for particular semester.
 */
public class Subject {
    private int mark;
    
    public String teacher;
    public final boolean isExam;
    public final Discipline discipline;

    /**
     * Standard class constructor.
     *
     * @param subject the object of class Subject.
     * @param mark int, which describes mark for this subject.
     * @param teacher String with teacher name.
     * @param isExam flag about exam or differiciate credit.
     */
    public Subject(Discipline subject, int mark, String teacher, boolean isExam) {
        this.discipline = subject;
        setMark(mark);
        this.teacher = teacher;
        this.isExam = isExam;
    }

    /**
     * Change value of Mark variable.
     *
     * @param mark valid value lies between 2 and 5.
     */
    public void setMark(int mark) {
        if (mark < 2 || mark > 5) {
            throw new IllegalArgumentException("Illegal MARK value!");
        }
        this.mark = mark;
    }

    /**
     * You will receive Mark value.
     *
     * @return mark for this subject.
     */
    public int getMark() {
        return this.mark;
    }
}
