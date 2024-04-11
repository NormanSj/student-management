package com.example.studentmanagement.api;

import com.example.studentmanagement.StudentManagementApplication;
import com.example.studentmanagement.exceptions.InvalidUniversityClassException;
import com.example.studentmanagement.exceptions.StudentEmptyNameException;
import com.example.studentmanagement.exceptions.StudentNonExistException;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.service.StudentService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InvalidClassException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/student")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getALLStudents();
    }

    @GetMapping("/name")
    //localhost:8080/api/student/name?name=zhangsan
    public List<Student> getStudent(@RequestParam String name){
        return  studentService.getStudentByName(name);
    }

    @GetMapping("/contain_name")
    //localhost:8080/api/student/name?contain_name=T
    public List<Student> getStudentsContainName(@RequestParam String name){
        return  studentService.getStudentsContainName(name);
    }

    @GetMapping("/class")
    //localhost:8080/api/student/name?contain_name=T
    public List<Student> getStudentsContainName(@RequestParam int year, @RequestParam int number){
        return  studentService.getStudentsInclass(year, number);
    }


    @RequestMapping("/register")
    @PostMapping
    public ResponseEntity<String> registerStudent(@RequestBody Student student){
        try{
            Student saveStudent = studentService.addStudent(student);
            return ResponseEntity.ok("Resgisterd student." + student.toString());
        }catch (StudentEmptyNameException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
    }

    @PostMapping(path="assignclass/{sid}/{cid}")
    public ResponseEntity<String> assignClass(@PathVariable("sid") Long studentId, @PathVariable("cid") Long classId){
        try{
            Student updateStudent = studentService.assignClass(studentId, classId);
            return ResponseEntity.ok("Assigned class." + updateStudent.toString());

        }catch (StudentNonExistException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (InvalidUniversityClassException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }

    }





}