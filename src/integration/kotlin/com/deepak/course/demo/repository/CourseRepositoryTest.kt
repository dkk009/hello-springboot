package com.deepak.course.demo.repository

import com.deepak.course.demo.entity.Course
import com.deepak.course.demo.entity.Instructor
import com.deepak.course.demo.util.TestPostgresDatabaseInit
import com.deepak.course.demo.util.courseList
import com.deepak.course.demo.util.instructorList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.util.stream.Stream

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CourseRepositoryTest: TestPostgresDatabaseInit(){
    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var instructorRepository: InstructorRepository
    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()
        val instructorList = instructorRepository.saveAll(instructorList.map {
            Instructor(id = null, name = it.name)
        })

        courseRepository.saveAll(courseList.map {
            Course(name = it.name, category = it.category, id = null, instructor = instructorList.first())
        })
    }

    @Test
    fun findCourseWithNameTest() {
        val data = courseRepository.findByName("Test course1")
        Assertions.assertEquals(1, data.size)
    }

    @Test
    fun findCourseWithNameNativeQueryTest() {
        val data = courseRepository.findByName("Test course1")
        Assertions.assertEquals(1, data.size)
    }

    @ParameterizedTest
    @MethodSource("courseAndSize")
    fun `find course name with multiple input`(courseName: String, expectedCount: Int) {
        val data = courseRepository.findByName(courseName = courseName)
        Assertions.assertEquals(expectedCount, data.size)
    }

    companion object {
        @JvmStatic
        fun courseAndSize(): Stream<Arguments> {
            return Stream.of(Arguments.of("Test course1", 1), Arguments.of("Test course2", 1))
        }
    }
}