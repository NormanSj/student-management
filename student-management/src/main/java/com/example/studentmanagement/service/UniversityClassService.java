package com.example.studentmanagement.service;

import com.example.studentmanagement.dao.UniversityClassDao;
import com.example.studentmanagement.model.UniversityClass;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.studentmanagement.exceptions.InvalidUniversityClassException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class UniversityClassService {

    UniversityClassDao universityClassDao;

    @Autowired
    public UniversityClassService(UniversityClassDao universityClassDao) {
        this.universityClassDao = universityClassDao;
    }

    public List<UniversityClass> getAllClasses(){
        return (List<UniversityClass>) universityClassDao.findAll();

    }

    public UniversityClass addClass(UniversityClass universityClass){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        if (universityClass.getYear() < currentYear) {
            throw  new InvalidUniversityClassException("Cannot add class in the past");
        }
        if (universityClass.getYear() > currentYear + 1) {
            throw  new InvalidUniversityClassException("Cannot add class in future");

        }

        return universityClassDao.save(universityClass);
    }
}
