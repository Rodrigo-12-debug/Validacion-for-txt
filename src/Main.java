import javax.swing.*;
import java.io.*;
import java.util.HashMap;

public class Main {
    private static final String ARCHIVO = "usuarios.txt";

    public static void main(String[] args) {
        HashMap<String, String> usuarios = cargarUsuarios();

        String opcion = JOptionPane.showInputDialog("¿Deseas registrarte o iniciar sesión? (registrarse/iniciar)");

        if (opcion == null) return; // Cancelar

        if (opcion.equalsIgnoreCase("registrarse") || opcion.equalsIgnoreCase("registrar") || opcion.equalsIgnoreCase("registrarme")) {
            registrarUsuario(usuarios);
        } else if (opcion.equalsIgnoreCase("iniciar")) {
            iniciarSesion(usuarios);
        } else {
            JOptionPane.showMessageDialog(null, "Opción no válida.");
        }
    }

    private static HashMap<String, String> cargarUsuarios() {
        HashMap<String, String> mapa = new HashMap<>();
        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            // Si el archivo no existe, crearlo vacío
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error creando archivo de usuarios.");
                System.exit(1);
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(":", 2);
                if (partes.length == 2) {
                    mapa.put(partes[0], partes[1]);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error leyendo archivo de usuarios.");
            System.exit(1);
        }
        return mapa;
    }

    private static void registrarUsuario(HashMap<String, String> usuarios) {
        String nuevoUsuario = JOptionPane.showInputDialog("Ingresa nuevo usuario:");
        if (nuevoUsuario == null || nuevoUsuario.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre de usuario inválido.");
            return;
        }
        nuevoUsuario = nuevoUsuario.trim();

        if (usuarios.containsKey(nuevoUsuario)) {
            JOptionPane.showMessageDialog(null, "El usuario ya existe.");
            return;
        }

        String nuevaContrasena = JOptionPane.showInputDialog("Ingresa nueva contraseña:");
        if (nuevaContrasena == null || nuevaContrasena.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Contraseña inválida.");
            return;
        }

        usuarios.put(nuevoUsuario, nuevaContrasena);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            bw.write(nuevoUsuario + ":" + nuevaContrasena);
            bw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error guardando usuario.");
            return;
        }
        JOptionPane.showMessageDialog(null, "Usuario registrado con éxito.");
    }

    private static void iniciarSesion(HashMap<String, String> usuarios) {
        String usuario = JOptionPane.showInputDialog("Usuario:");
        if (usuario == null || usuario.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre de usuario inválido.");
            return;
        }
        usuario = usuario.trim();

        if (!usuarios.containsKey(usuario)) {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
            return;
        }

        String contrasena = JOptionPane.showInputDialog("Contraseña:");
        if (contrasena == null) {
            JOptionPane.showMessageDialog(null, "Contraseña inválida.");
            return;
        }

        if (usuarios.get(usuario).equals(contrasena)) {
            JOptionPane.showMessageDialog(null, "Bienvenido, " + usuario + "!");
        } else {
            JOptionPane.showMessageDialog(null, "Contraseña incorrecta.");
        }
    }
}
