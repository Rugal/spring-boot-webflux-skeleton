# Spring Boot Web Flux Skeleton

1. test
   1. unit
   2. integration
2. endpoint
3. r2dbc


## requirement
As school board, I would like to have a system for managing course and registration amongst student and teacher.  

### student

1. browse all teachers, with teacher name and faculty name
2. browse all courses, with course id, course credit and teacher information
3. search teacher by name, 
4. search course by name
5. register course only if student not yet register this course
6. deregister course only if student already register this course but grade not yet given
7. browser my registered courses & grade

### teacher

1. browse all teachers, with teacher name and faculty name
2. browse all courses, with course id, course credit and teacher information
3. search teacher by name, 
4. search course by name
5. create course for student to registration
7. browse all students registered in course created by myself, with student name, faculty
8. grade student registered in course created by myself, the grade is from 0 - 100

### admin

1. create students with name and falcuty
2. create teachers with name and falcuty
