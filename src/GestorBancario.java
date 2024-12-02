import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GestorBancario {
    private List<Cliente> clientes;
    private Map<String, Usuario> usuarios; // nombreUsuario -> Usuario

    public GestorBancario() {
        this.clientes = new ArrayList<>();
        this.usuarios = new HashMap<>();
    }

    public boolean registrarCliente(String nombre, String apellido, String dni,
                                    String direccion, String telefono, String email,
                                    String numeroCuenta, int saldoInicial) {
        // Verificar si ya existe un cliente con ese DNI
        if (buscarClientePorDNI(dni) != null) {
            return false;
        }

        Cliente nuevoCliente = new Cliente(nombre, apellido, dni, direccion,
                telefono, email, numeroCuenta, saldoInicial);
        clientes.add(nuevoCliente);
        return true;
    }

    public boolean crearUsuario(String dni, String nombreUsuario, String password) {
        // Verificar si ya existe el usuario
        if (usuarios.containsKey(nombreUsuario)) {
            return false;
        }

        Cliente cliente = buscarClientePorDNI(dni);
        if (cliente == null) {
            return false;
        }

        cliente.crearUsuario(nombreUsuario, password);
        usuarios.put(nombreUsuario, cliente.getUsuario());
        return true;
    }

    public Cliente buscarClientePorDNI(String dni) {
        return clientes.stream()
                .filter(c -> c.getDni().equals(dni))
                .findFirst()
                .orElse(null);
    }

    public Usuario buscarUsuario(String nombreUsuario) {
        return usuarios.get(nombreUsuario);
    }

    public boolean validarCredenciales(String nombreUsuario, String password) {
        Usuario usuario = usuarios.get(nombreUsuario);
        return usuario != null && usuario.validarCredenciales(nombreUsuario, password);
    }

    public List<Cliente> getClientes() {
        return new ArrayList<>(clientes); // Retorna una copia de la lista
    }

    public int getCantidadClientes() {
        return clientes.size();
    }

    public int getCantidadUsuarios() {
        return usuarios.size();
    }
}