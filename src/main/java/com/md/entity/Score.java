package com.md.entity;

import com.md.utils.Entity;
import lombok.Data;

@Data
public class Score extends Entity {

    /**
     *
     */
    private Integer courseId;
    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private Double score;
    /**
     *
     */
    private Integer sectionId;
    /**
     *
     */
    private Integer stuId;



    /**
     * 课程信息
     */
    private Course course;


    private Section section;


    private Student student;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }


    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getCourseId() {
        return courseId;
    }
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getSectionId() {
        return sectionId;
    }
    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }
    public Integer getStuId() {
        return stuId;
    }
    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }
}
