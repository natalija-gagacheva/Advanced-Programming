package labs.lab2.z1;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

enum OPERATOR_ENUM { VIP, ONE, TMOBILE }

/* TODO: Од класата Contact не треба да може директно да се инстанцира објект. */
abstract class Contact {
    private String datum; //YYYY-MM-DD

    public Contact(String datum) {
        this.datum = datum;
    }
    public boolean isNewerThan(Contact other) {
        int result = this.datum.compareTo(other.datum);
        return result>0;
    }

    public String getDatum() {
        return datum;
    }

    //ne znam dali treba da bidi public or not
    public abstract String getType();

    public abstract String toString();
}

class EmailContact extends Contact {
    private String email;

    public EmailContact(String datum,String email) {
        super(datum);
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getType() {
        return "Email";
    }

    @Override
    public String toString() {
        return "\"" + email + '\"';
    }
}

class PhoneContact extends Contact {
    private String phonenumber;
    private OPERATOR_ENUM operator;

    public PhoneContact(String datum, String phonenumber) {
        super(datum);
        this.phonenumber = phonenumber;
        OPERATOR_ENUM op = getOperator();
        this.operator=op;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getThirdDigit(String number) {
        String sub = number.substring(2,3);
        return sub;
    }

    public OPERATOR_ENUM getOperator() {

        if (getThirdDigit(phonenumber).equals("0") || getThirdDigit(phonenumber).equals("1") ||getThirdDigit(phonenumber).equals("2")) {
            return OPERATOR_ENUM.TMOBILE;
        } else if (getThirdDigit(phonenumber).equals("5") || getThirdDigit(phonenumber).equals("6") ) {
            return OPERATOR_ENUM.ONE;
        } else
            return OPERATOR_ENUM.VIP;
    }

    @Override
    public String getType() {
        return "Phone";
    }

    @Override
    public String toString() {
        return "\"" + phonenumber + '\"';
    }
}

class Student {
    private String firstname;
    private String lastname;
    private String city;
    private int age;
    private long index;
    private Contact [] nizaOdKontakti;

    public Student(String firstname, String lastname, String city, int age, long index) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.city = city;
        this.age = age;
        this.index = index;
        nizaOdKontakti = new Contact[0];
    }

    public void addEmailContact(String date, String email) {

        ArrayList<Contact> listaOdKontakti = new ArrayList<>(Arrays.asList(nizaOdKontakti));
        listaOdKontakti.add(new EmailContact(date,email));
        nizaOdKontakti = listaOdKontakti.toArray(nizaOdKontakti);
    }

    public void addPhoneContact(String date, String phone) {
        ArrayList<Contact> listaOdKontakti = new ArrayList<>(Arrays.asList(nizaOdKontakti));
        listaOdKontakti.add(new PhoneContact(date,phone));
        nizaOdKontakti = listaOdKontakti.toArray(nizaOdKontakti);

    }

    public Contact[] getEmailContacts() {
        return Arrays
                .stream(nizaOdKontakti)
                .filter(kontakt -> kontakt.getType().equals("Email"))
                .toArray(numOfElemInStream -> new Contact[numOfElemInStream]);
    }

    public Contact[] getPhoneContacts() {
        return Arrays
                .stream(nizaOdKontakti)
                .filter(kontakt -> kontakt.getType().equals("Phone"))
                .toArray(numOfElemInStream -> new Contact[numOfElemInStream]);
    }

    public String getCity() {
        return city;
    }

    public String getFullName() {
        return String.format(firstname+" "+lastname);
    }

    public long getIndex() {
        return index;
    }

    public Contact getLatestContact() {

        Contact max = nizaOdKontakti[0];
        for (int i=0; i<nizaOdKontakti.length; i++) {
            if ( nizaOdKontakti[i].isNewerThan(max)) {
                max = nizaOdKontakti[i];
            }
        }

        return max;
    }

    public int numberOfContacts() {
        return nizaOdKontakti.length;
    }

    @Override
    public String toString() {
        return "{" +
                "\"ime\":\"" + firstname + '\"' +
                ", \"prezime\":\"" + lastname + '\"' +
                ", \"vozrast\":" + age +
                ", \"grad\":\"" + city + '\"' +
                ", \"indeks\":" + index +
                ", \"telefonskiKontakti\":" + Arrays.toString(getPhoneContacts()) +
                ", \"emailKontakti\":" + Arrays.toString(getEmailContacts()) +
                '}';
    }
}

class Faculty {
    private String name;
    private Student [] nizaOdStudenti;

    public Faculty(String name, Student [] nizaOdStudenti) {
        this.name=name;
        this.nizaOdStudenti = nizaOdStudenti;
    }

    public long countStudentsFromCity(String cityName) {
        return Arrays
                .stream(nizaOdStudenti)
                .filter(student -> student.getCity().equals(cityName))
                .count();
    }

    public Student getStudent(long index) {
        return Arrays
                .stream(nizaOdStudenti)
                .filter(student -> student.getIndex() == index)
                .findFirst()
                .orElse(null);
    }

    public double getAverageNumberOfContacts(){

        double sum = 0;
        for (int i=0; i<nizaOdStudenti.length; i++) {
            sum += nizaOdStudenti[i].numberOfContacts();
        }

        return sum/nizaOdStudenti.length;

    }

    public Student getStudentWithMostContacts() {

        Student max = nizaOdStudenti[0];

        for (Student student : nizaOdStudenti) {
            if (student.numberOfContacts() > max.numberOfContacts()) {
                max = student;
            } else if (student.numberOfContacts() == max.numberOfContacts()) {
                if (student.getIndex() > max.getIndex()) {
                    max = student;
                }
            }
        }

        return max;
    }

    @Override
    public String toString() {
        return "{" +
                "\"fakultet\":\"" + name + '\"' +
                ", \"studenti\":" + Arrays.toString(nizaOdStudenti) +
                "}";
    }
}
public class ContactsTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        Faculty faculty = null;

        int rvalue = 0;
        long rindex = -1;

        DecimalFormat df = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            rvalue++;
            String operation = scanner.next();

            switch (operation) {
                case "CREATE_FACULTY": {
                    String name = scanner.nextLine().trim();
                    int N = scanner.nextInt();

                    Student[] students = new Student[N];

                    for (int i = 0; i < N; i++) {
                        rvalue++;

                        String firstName = scanner.next();
                        String lastName = scanner.next();
                        String city = scanner.next();
                        int age = scanner.nextInt();
                        long index = scanner.nextLong();

                        if ((rindex == -1) || (rvalue % 13 == 0))
                            rindex = index;

                        Student student = new Student(firstName, lastName, city,
                                age, index);
                        students[i] = student;
                    }

                    faculty = new Faculty(name, students);
                    break;
                }

                case "ADD_EMAIL_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String email = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addEmailContact(date, email);
                    break;
                }

                case "ADD_PHONE_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String phone = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addPhoneContact(date, phone);
                    break;
                }

                case "CHECK_SIMPLE": {
                    System.out.println("Average number of contacts: "
                            + df.format(faculty.getAverageNumberOfContacts()));

                    rvalue++;

                    String city = faculty.getStudent(rindex).getCity();
                    System.out.println("Number of students from " + city + ": "
                            + faculty.countStudentsFromCity(city));

                    break;
                }

                case "CHECK_DATES": {

                    rvalue++;

                    System.out.print("Latest contact: ");
                    Contact latestContact = faculty.getStudent(rindex)
                            .getLatestContact();
                    if (latestContact.getType().equals("Email"))
                        System.out.println(((EmailContact) latestContact)
                                .getEmail());
                    if (latestContact.getType().equals("Phone"))
                        System.out.println(((PhoneContact) latestContact)
                                .getPhonenumber()
                                + " ("
                                + ((PhoneContact) latestContact).getOperator()
                                .toString() + ")");

                    if (faculty.getStudent(rindex).getEmailContacts().length > 0
                            && faculty.getStudent(rindex).getPhoneContacts().length > 0) {
                        System.out.print("Number of email and phone contacts: ");
                        System.out
                                .println(faculty.getStudent(rindex)
                                        .getEmailContacts().length
                                        + " "
                                        + faculty.getStudent(rindex)
                                        .getPhoneContacts().length);

                        System.out.print("Comparing dates: ");
                        int posEmail = rvalue
                                % faculty.getStudent(rindex).getEmailContacts().length;
                        int posPhone = rvalue
                                % faculty.getStudent(rindex).getPhoneContacts().length;

                        System.out.println(faculty.getStudent(rindex)
                                .getEmailContacts()[posEmail].isNewerThan(faculty
                                .getStudent(rindex).getPhoneContacts()[posPhone]));
                    }

                    break;
                }

                case "PRINT_FACULTY_METHODS": {
                    System.out.println("Faculty: " + faculty.toString());
                    System.out.println("Student with most contacts: "
                            + faculty.getStudentWithMostContacts().toString());
                    break;
                }

            }

        }

        scanner.close();
    }
}
