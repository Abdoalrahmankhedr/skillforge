package databases;

import models.Certificate;
import models.User;
import security.PasswordService;
import services.CourseService;

import java.time.LocalDate;
import java.util.regex.Pattern;

// Andrew :)

/*
    Please refer to the documentation of Database<T> for detailed information.

    Database class for all User subclasses.
    Stores all data in JSON file ("src/resources/users.json")

    This database can:
        - get user records          -> getRecords()
        - delete users              -> deleteUser()
        - add new users             -> addUser()
        - update user information   -> updateUser()
        - verify login credentials  -> login()
*/

public class UserDatabase extends Database<User> {
    private static UserDatabase instance;
    private int certificateIndex = 1;

    private UserDatabase(String filename) {
        super(filename, User.class);

        for (User u : getRecords()) {
            for (Certificate c : u.getCertificates()) {
                this.certificateIndex = Math.max(this.certificateIndex, c.getId());
            }
        }
    }

    public static UserDatabase getInstance() {
        /* To ensure the database would stay consistent on data change */
        if (instance == null) {
            instance = new UserDatabase("src/resources/users.json");
        }
        return instance;
    }

    public static boolean validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    public void deleteUser(int id) {deleteRecord(id);}
    public User getUserById(int id) {return getRecordById(id);}

    public User getUserByEmail(String email) {
        for (User user : getRecords()) {
            if (user.getEmail().equals(email)) return user;
        }

        return null;
    }

    public boolean addUser(String name, String email, String password, String role) {
        if (getUserByEmail(email) == null && validateEmail(email)) {
            User user = new User();
            user.setId(++this.index);
            user.setRole(role);     // Validation needed here
            user.setName(name);
            user.setEmail(email);   // Validation needed here
            user.setPassword(PasswordService.encode(password));

            insertRecord(user);
            return true;
        }

        return false;
    }

    public void updateUser(int id, String name, String email, String password, String role) {
        User user = getRecordById(id);
        if (user == null) {
            System.out.printf("[%s]: User attempting to update doesn't exist [ID] %d\n", logName, id);
            return;
        }

        /* Change filters */
        if (!user.getName().equals(name)) user.setName(name);

        // Validation needed here
        if (email != null && !user.getEmail().equals(email)) user.setEmail(email);

        String passwordHash = PasswordService.encode(password);
        if (password != null && !PasswordService.compare(passwordHash, user.getPassword())) user.setPassword(passwordHash);

        // Validation needed here
        if (!user.getRole().equals(role)) user.setRole(role);

        /* Remove the old one */
        deleteRecord(id);
        /* Add the changed one */
        insertRecord(user);
    }

    public void issueCertificate(int userId, int courseId) {
        /* Issues a certificate to a user, only if the course is complete */
        if (CourseService.isComplete(courseId, userId)) {
            User user = getRecordById(userId);

            if (user != null) {
                Certificate certificate = new Certificate();
                certificate.setIssued(LocalDate.now());
                certificate.setCourseId(courseId);
                certificate.setUserId(userId);
                certificate.setId(++this.certificateIndex);

                user.addCertificate(certificate);

                /* Remove the old one */
                deleteRecord(userId);
                /* Add the changed one */
                insertRecord(user);
            }
        }
    }

    public Boolean login(String email, String password) {
        /* Returns null if user doesn't exist, else true/false based on whether the password is matching */
        User user = getUserByEmail(email);
        if (user != null) {
            return PasswordService.compare(user.getPassword(), PasswordService.encode(password));
        } else return null;
    }
}
