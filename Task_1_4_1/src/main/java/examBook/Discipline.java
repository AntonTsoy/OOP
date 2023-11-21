package examBook;

/**
 * Class for discribing common info about subject.
 * This info is constant data.
 */
public class Discipline {
    public final String disciplineTitle;
    public final String competencies;
    public String description;

    /**
     * Class constructor receive two Strings args.
     * This data defines discipline.
     *
     * @param disciplineTitle the name of subject.
     * @param competencies skills, which students will learn.
     */
    public Discipline(String disciplineTitle, String competencies) {
        this.disciplineTitle = disciplineTitle;
        this.competencies = competencies;
    }

}
