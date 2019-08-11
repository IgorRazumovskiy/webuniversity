package ua.com.foxminded.webuniversity.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groups_sequence")
    @SequenceGenerator(name = "groups_sequence", sequenceName = "groups_id_seq", allocationSize = 1)
    private Integer id;

    @NotEmpty(message = "Group name can not be empty")
    @NotNull(message = "Group name can not be null")
    @Length(message = "Group name should have atleast 5 characters", min = 5)
    private String name;

    @Valid
    @JsonIgnore
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH })
    private List<Student> students = new ArrayList<>();

    @NotNull(message = "Number of students in group can not be null")
    @PositiveOrZero(message = "Number of students in group can not be negative")
    private Integer maxNumberOfStudents;

    public Group() {

    }

    public Group(String name, List<Student> students) {
        this.setName(name);
        this.setStudents(students);
    }

    public Group(String name, List<Student> students, Integer maxNumberOfStudents) {
        this.name = name;
        this.students = students;
        this.maxNumberOfStudents = maxNumberOfStudents;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        students.add(student);
        student.setGroup(this);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public Integer getMaxNumberOfStudents() {
        return maxNumberOfStudents;
    }

    public void setMaxNumberOfStudents(Integer maxNumberOfStudents) {
        this.maxNumberOfStudents = maxNumberOfStudents;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Group other = (Group) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    public String toString() {
        return "Group [id=" + id + ", name=" + name + ", maxNumberOfStudents=" + maxNumberOfStudents + "]";
    }

}
