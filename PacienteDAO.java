import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    public static boolean insertar(Paciente p) {
        String sql = "INSERT INTO Pacientes (nombre, edad, peso, estatura, antecedentes) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getEdad());
            ps.setDouble(3, p.getPeso());
            ps.setDouble(4, p.getEstatura());
            ps.setString(5, p.getAntecedentes());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar paciente: " + e.getMessage());
            return false;
        }
    }

    public static boolean actualizar(Paciente p) {
        String sql = "UPDATE Pacientes SET nombre=?, edad=?, peso=?, estatura=?, antecedentes=? WHERE id=?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getEdad());
            ps.setDouble(3, p.getPeso());
            ps.setDouble(4, p.getEstatura());
            ps.setString(5, p.getAntecedentes());
            ps.setInt(6, p.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar paciente: " + e.getMessage());
            return false;
        }
    }

    public static boolean eliminar(int id) {
        try (Connection con = ConexionBD.conectar()) {
            // Primero eliminar mediciones relacionadas
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM Mediciones WHERE paciente_id=?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM Pacientes WHERE id=?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar paciente: " + e.getMessage());
            return false;
        }
    }

    public static List<Paciente> obtenerTodos() {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, edad, peso, estatura, antecedentes FROM Pacientes ORDER BY nombre";
        try (Connection con = ConexionBD.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Paciente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getInt("edad"),
                    rs.getDouble("peso"),
                    rs.getDouble("estatura"),
                    rs.getString("antecedentes")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener pacientes: " + e.getMessage());
        }
        return lista;
    }
}
