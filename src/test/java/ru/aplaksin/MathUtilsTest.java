package ru.aplaksin;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("When running MathUtils")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
        //optional annotation PER_METHOD or PER_CLASS
class MathUtilsTest {
    MathUtils mathUtils;

    TestInfo testInfo;
    TestReporter testReporter;

    @BeforeAll
    static void beforeAllInit() {
        System.out.print("This needs to run before all");
    }

    @BeforeEach
    void init(TestInfo testInfo,TestReporter testReporter) { // TestInfo,TestReporter are optional
        this.testInfo = testInfo;
        this.testReporter = testReporter;
        mathUtils = new MathUtils();
    }

    @AfterEach
    void cleanup() {
        System.out.print("Cleaning up...");
    }

    @Nested
    @DisplayName("add method")
    @Tag("Math")
    class AddTest {

        @Test
        @DisplayName("When adding two positive numbers")
        void testAddPositive() {
            int expected = 2;
            int actual = mathUtils.add(1, 1);
            assertEquals(expected, actual, "The add method should add two numbers"); // message is optional
        }

        @Test
        @DisplayName("When adding two negative numbers")
        void testAddNegative() {
            int expected = -2;
            int actual = mathUtils.add(-1, -1);
            assertEquals(expected, actual,
                    () -> "should return sun " + expected + " but returned " + actual); //lambda in string optimized assert it will compute string only if test fails
        }
    }

    @Test
    @DisplayName("multiply method")
    @Tag("Math") //tests launch by tag in configuration
    void testMultiply() {
        testReporter.publishEntry("Running " + testInfo.getDisplayName() + " with tags " + testInfo.getTags());
        assertAll(
                () -> assertEquals(4, mathUtils.multiply(2, 2)),
                () -> assertEquals(0, mathUtils.multiply(2, 0)),
                () -> assertEquals(-2, mathUtils.multiply(2, -1))
        );
    }

    @Test
    @EnabledOnOs(OS.LINUX)
    void testDivide() {
        boolean isServerUp = false;
        assumeTrue(isServerUp); // if false test will be ignored
        assertThrows(ArithmeticException.class, () -> mathUtils.divide(1, 0), "Divide by zero should throw");
    }

    @RepeatedTest(3)
    @Tag("Circle")
    void testComputeCircleArea(RepetitionInfo repetitionInfo) { // RepetitionInfo gets info about repeated tests like current rep index or total rep etc
        assertEquals(314.1592653589793, mathUtils.computeCircleArea(10),
                "Should return right circle area");
    }

    @Disabled //ignores test
    @Test
    @DisplayName("Fail test")
    void testDisabled() {
        fail("This test should be disabled");
    }
}
