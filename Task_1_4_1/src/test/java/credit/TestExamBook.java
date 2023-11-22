package credit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class for tests.
 */
public class TestExamBook {

    Discipline osi;
    Discipline java;
    String irtegov;
    String vlasov;

    @BeforeEach
    void setUp() {
        osi = new Discipline("Operating Systems", "СХЙ-007");
        java = new Discipline("OOP", "ОПК-131");
        irtegov = "Irtegov";
        vlasov = "Alexander Vlasov";
    }

    @Test   
    void testDisciplineInitialized() {
        Subject subject = new Subject(java, 5, vlasov, false); 
        assertEquals(java, subject.discipline);  
    }

    @Test
    void testMarkInitialized() {
        Subject subject = new Subject(osi, 2, irtegov, true); 
        assertEquals(2, subject.getMark());
    }

    @Test
    void testTeacherInitialized() {  
        String teacher = "Mr. Smith";
        Subject subject = new Subject(osi, 4, teacher, false);
        assertEquals(teacher, subject.teacher); 
    }

    @Test
    void testIsExamInitialized() {
        Subject subject = new Subject(java, 3, irtegov, true);
        assertTrue(subject.isExam);
    }

    @Test
    void testSetValidMark() {
        Subject subject = new Subject(osi, 2, vlasov, false);      
        subject.setMark(3);
        assertEquals(3, subject.getMark());
    }

    @Test
    void testSetInvalidMark() {
        Discipline math = new Discipline("Math", "ЩШГ-812");
        Subject subject = new Subject(math, 4, "Vaskevich", true);
        assertThrows(IllegalArgumentException.class, () -> {
            subject.setMark(6);
        });
    }

    @Test
    void testNumberInitialized() {
        int testNumber = 2;
        Semester semester = new Semester(testNumber);
        assertEquals(testNumber, semester.halfNum);
    }

    @Test 
    void testSumInitializedToZero() {
        Semester semester = new Semester(1);
        assertEquals(0, semester.getGrade());
    }

    @Test
    void testGoodSemesterInitializedToFalse() {
        Semester semester = new Semester(2);
        assertFalse(semester.goodSemester());
    }

    @Test
    void testAddSubject() {
        Semester semester = new Semester(3); 
        Subject testSubject = new Subject(osi, 2, irtegov, true); 
        semester.addSubject(testSubject);
        assertEquals(1, semester.quantityOfSubjects());
    }

    @Test
    void testExistingSubjectReturned() {
        Semester semester = new Semester(4);
        Subject testSubject = new Subject(java, 5, vlasov, true); 
        semester.addSubject(testSubject);

        Subject result = semester.getSubject(1);
        assertSame(testSubject, result);  
    }
    
    @Test
    void testStudentNameInitialized() {
        String testName = "John";
        ExamBook book = new ExamBook(testName);
        assertEquals(testName, book.student);
    }

    @Test
    void testCurrentSemesterInitializedToZero() {
        ExamBook book = new ExamBook("Test");
        assertEquals(1, book.getSemester().halfNum);
    }

    @Test
    void testSemestersListInitialized() {
        ExamBook book = new ExamBook("Test");
        assertNotNull(book.getSemester(1));
    }

    @Test
    void testSemesterIsAdded() {
        ExamBook book = new ExamBook("Test");
        Semester newSem = book.newSemester();  
        assertEquals(2, newSem.halfNum);
    }

    @Test
    void testReturnsCorrectSemester() {
        ExamBook book = new ExamBook("Test");  
        book.newSemester();
        
        Semester semester = book.getSemester();
        assertEquals(2, semester.halfNum);
    }

    @Test
    void testInvalidMarkThrowsException() {
        ExamBook book = new ExamBook("Test");

        assertThrows(IllegalArgumentException.class, () -> {
            book.setQualifier(1);
        });
    }

    @Test
    void testCurrentAverageMarkOfEmptyBookReturnsZero() {
        ExamBook book = new ExamBook("Vasya");
        
        assertEquals(0, book.getCurrentAverageMark());
    }

    @Test
    void testAverageMarkCalculatesCorrectly() {
        ExamBook book = new ExamBook("Gena");
        
        book.getSemester().addSubject(new Subject(osi, 4, irtegov, true));
        book.newSemester();
        book.getSemester().addSubject(new Subject(java, 5, vlasov, false));
        
        assertEquals(4.5, book.getCurrentAverageMark());  
    }

    @Test
    void testUpSalaryEligible() {
        ExamBook book = new ExamBook("Tsoy");
        
        book.getSemester().addSubject(new Subject(java, 5, "Anton", true));
        
        assertTrue(book.upSalary());
    }

    @Test
    void testUpSalaryNotEligible() {
        ExamBook book = new ExamBook("Dima");
        
        Discipline math = new Discipline("Math", "DFJC");
        book.getSemester().addSubject(new Subject(math, 4, "Vova", false));
        
        assertFalse(book.upSalary());
    }

    @Test
    void testRedDiplomaEligible() {
        ExamBook book = new ExamBook("Test");
        Discipline template = new Discipline("TEMPLATE", "00-00");
        
        book.setQualifier(5);
        
        book.getSemester().addSubject(new Subject(template, 5, "DeadInside", false)); 
        for (int i = 2; i <= 8; i++) {
            book.newSemester().addSubject(new Subject(template, 5, "DeadInside", false)); 
        }
        
        assertTrue(book.redDiploma());
    }

    @Test
    void testRedNotEligibleLowAverageMark() {
        ExamBook book = new ExamBook("Test");
        Discipline template = new Discipline("TEMPLATE", "00-00");
        
        book.setQualifier(4);
        
        book.getSemester().addSubject(new Subject(template, 5, "DeadInside", false)); 
        for (int i = 2; i <= 8; i++) {
            book.newSemester().addSubject(new Subject(template, 4, "DeadInside", false)); 
        }
        
        
        assertFalse(book.redDiploma()); 
    }

    @Test
    void testRedDiplomaBadSemester() {
        ExamBook book = new ExamBook("Test");
        
        book.setQualifier(5);
        
        book.newSemester().addSubject(new Subject(java, 5, "Galina Bykova", false));
        book.newSemester().addSubject(new Subject(osi, 3, irtegov, true));
        
        assertFalse(book.redDiploma());
    }
}