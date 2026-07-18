package com.health.fitness;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.health.fitness.HealthIndexScore.FitnessLevel;
class HealthIndexScoreRobustnessTest {

	// withVALID
	@ParameterizedTest(name = "Normal: {0} - Inputs: VO2={1}, RHR={2}, HRR={3} => Expected: {4}")
	@CsvSource({
	    "TC001, 52.0, 40, 15, EXCELLENT",
	    "TC002, 52.0, 41, 15, STANDARD",
	    "TC003, 52.0, 130, 15, STANDARD",
	    "TC004, 52.0, 119, 15, STANDARD",
	    "TC005, 52.0, 220, 15, STANDARD",
	    "TC006, 0.0, 130, 15, POOR",
	    "TC007, 1.0, 130, 15, POOR",
	    "TC008, 102.0, 130, 15, STANDARD",
	    "TC009, 103.0, 130, 15, STANDARD",
	    "TC010, 52.0, 130, 0, STANDARD",
	    "TC011, 52.0, 130, 1, STANDARD",
	    "TC012, 52.0, 130, 29, STANDARD",
	    "TC013, 52.0, 130, 30, STANDARD",
	    "TC015, 104.0, 130, 15, STANDARD",
	    "TC019, 52.0, 130, 31, STANDARD"
	    
	})
	void testFinestLevelwithVALID(String id, double vo2, int rhr, int hrr, FitnessLevel expected) {
	    
	    HealthIndexScore scoreCalculator = new HealthIndexScore(vo2, rhr, hrr);
	     
	    assertEquals(expected, scoreCalculator.getFitnessLevel(), "Failed at " + id);
	}
	
	// withINVALID
	@ParameterizedTest(name = "Robust: {0} - VO2={1}, RHR={2}, HRR={3} => Expected: IllegalArgumentException")
    @CsvSource({
        "TC014, -1.0, 130, 15",
        "TC016, 52.0, 39, 15",
        "TC017, 52.0, 221, 15",
        "TC018, 52.0, 130, -1"  
    })
	void testFinestLevelwithINVALID(String id, double vo2, int rhr, int hrr) {
		assertThrows(IllegalArgumentException.class, 
				() -> new HealthIndexScore(vo2, rhr, hrr));
	}
}