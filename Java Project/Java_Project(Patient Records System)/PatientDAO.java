import java.sql.*;
import java.util.*;

public class PatientDAO {

    public static void addPatient(Patient p) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO patients(name, age, disease, file_path) VALUES (?, ?, ?, ?)");
            ps.setString(1, p.name);
            ps.setInt(2, p.age);
            ps.setString(3, p.disease);
            ps.setString(4, p.filePath);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Patient> getAllPatients() {
        List<Patient> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM patients");

            while (rs.next()) {
                Patient p = new Patient();
                p.id = rs.getInt("id");
                p.name = rs.getString("name");
                p.age = rs.getInt("age");
                p.disease = rs.getString("disease");
                p.filePath = rs.getString("file_path");
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void deletePatient(int id) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM patients WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}