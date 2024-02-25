import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Student {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}

class StudentManagementSystem {
    private DefaultListModel<Student> studentListModel;

    public StudentManagementSystem() {
        this.studentListModel = new DefaultListModel<>();
    }

    public void addStudent(Student student) {
        studentListModel.addElement(student);
    }

    public void removeStudent(int rollNumber) {
        for (int i = 0; i < studentListModel.size(); i++) {
            if (studentListModel.get(i).getRollNumber() == rollNumber) {
                studentListModel.remove(i);
                return;
            }
        }
    }

    public Student searchStudent(int rollNumber) {
        for (int i = 0; i < studentListModel.size(); i++) {
            if (studentListModel.get(i).getRollNumber() == rollNumber) {
                return studentListModel.get(i);
            }
        }
        return null;
    }

    public DefaultListModel<Student> getStudentListModel() {
        return studentListModel;
    }
}

public class task3 {
    private JFrame frame;
    private StudentManagementSystem sms;
    private JTextField nameField, rollField, gradeField;
    private JTextArea outputArea;

    public task3() {
        frame = new JFrame(" Welcom to Student Management System");
        sms = new StudentManagementSystem();

        initializeUI();
    }

    private void initializeUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel(new GridLayout(5, 2));

        nameField = new JTextField();
        rollField = new JTextField();
        gradeField = new JTextField();
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        JButton addButton = new JButton("Add Student");
        JButton removeButton = new JButton("Remove Student");

        // Set background color for buttons
        addButton.setBackground(Color.GREEN);
        removeButton.setBackground(Color.RED);

        JButton searchButton = new JButton("Search Student");
        JButton displayButton = new JButton("Display All Students");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeStudent();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchStudent();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAllStudents();
            }
        });

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Roll Number:"));
        panel.add(rollField);
        panel.add(new JLabel("Grade:"));
        panel.add(gradeField);
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(searchButton);
        panel.add(displayButton);

        JScrollPane scrollPane = new JScrollPane(outputArea);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void addStudent() {
        String name = nameField.getText();
        int rollNumber = Integer.parseInt(rollField.getText());
        String grade = gradeField.getText();

        Student newStudent = new Student(name, rollNumber, grade);
        sms.addStudent(newStudent);

        clearFields();
    }

    private void removeStudent() {
        int rollToRemove = Integer.parseInt(rollField.getText());
        sms.removeStudent(rollToRemove);

        clearFields();
    }

    private void searchStudent() {
        int rollToSearch = Integer.parseInt(rollField.getText());
        Student searchedStudent = sms.searchStudent(rollToSearch);

        if (searchedStudent != null) {
            outputArea.setText("Student found - Name: " + searchedStudent.getName() +
                    ", Roll Number: " + searchedStudent.getRollNumber() +
                    ", Grade: " + searchedStudent.getGrade());
        } else {
            outputArea.setText("Student not found.");
        }
    }

    private void displayAllStudents() {
        DefaultListModel<Student> studentListModel = sms.getStudentListModel();
        outputArea.setText("");

        for (int i = 0; i < studentListModel.size(); i++) {
            Student student = studentListModel.get(i);
            outputArea.append("Name: " + student.getName() +
                    ", Roll Number: " + student.getRollNumber() +
                    ", Grade: " + student.getGrade() + "\n");
        }
    }

    private void clearFields() {
        nameField.setText("");
        rollField.setText("");
        gradeField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new task3();
            }
        });
    }
}
