package com.md.entity;

import com.md.utils.Entity;
import lombok.Data;

@Data
public class Section extends Entity {
    /**
     *
     */
    private Integer clazzId;
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
    private String remark;
    /**
     *
     */
    private Integer teacherId;
    /**
     *
     */
    private String type;
    /**
     *
     */
    private Integer year;


    /**
     * 课程
     */
    private Course course;

    /**
     * 老师
     */
    private Teacher teacher;

    private Clazz clazz;

    /**
     * 选课的数量，辅助查询
     */
    private  Integer selected;

    public Integer getClazzId() {
        return clazzId;
    }
    public void setClazzId(Integer clazzId) {
        this.clazzId = clazzId;
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
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Integer getTeacherId() {
        return teacherId;
    }
    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }
}
