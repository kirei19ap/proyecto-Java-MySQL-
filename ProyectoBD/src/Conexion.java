import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexion {

    private static final String CONTROLADOR = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3307/mastambo";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    static {
        try {
            Class.forName(CONTROLADOR);
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el controlador");
            e.printStackTrace();
        }
    }

    public Connection conectar() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
            System.out.println("Conexión OK");
        } catch (SQLException e) {
            System.out.println("Error en la conexión");
            e.printStackTrace();
        }
        return conexion;
    }

    public boolean existeProducto(String nombreProducto) {
        boolean existe = false;
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
            String query = "SELECT COUNT(*) FROM mercaderia WHERE producto = ?";
            statement = conexion.prepareStatement(query);
            statement.setString(1, nombreProducto);
            rs = statement.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                existe = (count > 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return existe;
    }

    public void agregarProducto(String nombreProducto, double costo, int stock) {
        Connection conexion = null;
        PreparedStatement statement = null;
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
            String query = "INSERT INTO mercaderia (producto, costo, stock) VALUES (?, ?, ?)";
            statement = conexion.prepareStatement(query);
            statement.setString(1, nombreProducto);
            statement.setDouble(2, costo);
            statement.setInt(3, stock);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void modificarProducto(String nombreProducto, double nuevoCosto, int nuevoStock) {
        Connection conexion = null;
        PreparedStatement statement = null;
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
            String query = "UPDATE mercaderia SET costo = ?, stock = ? WHERE producto = ?";
            statement = conexion.prepareStatement(query);
            statement.setDouble(1, nuevoCosto);
            statement.setInt(2, nuevoStock);
            statement.setString(3, nombreProducto);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public double obtenerCostoProducto(String nombreProducto) {
        double costo = 0;
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
            String query = "SELECT costo FROM mercaderia WHERE producto = ?";
            statement = conexion.prepareStatement(query);
            statement.setString(1, nombreProducto);
            rs = statement.executeQuery();
            if (rs.next()) {
                costo = rs.getDouble("costo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return costo;
    }

    public int obtenerStockProducto(String nombreProducto) {
        int stock = 0;
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
            String query = "SELECT stock FROM mercaderia WHERE producto = ?";
            statement = conexion.prepareStatement(query);
            statement.setString(1, nombreProducto);
            rs = statement.executeQuery();
            if (rs.next()) {
                stock = rs.getInt("stock");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return stock;
    }

    public void eliminarProducto(String nombreProducto) {
        Connection conexion = null;
        PreparedStatement statement = null;
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
            String query = "DELETE FROM mercaderia WHERE producto = ?";
            statement = conexion.prepareStatement(query);
            statement.setString(1, nombreProducto);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
