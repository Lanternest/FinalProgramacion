public class Cliente {
    private String nombre;
    private String apellido;
    private String dni;
    private String direccion;
    private String telefono;
    private String email;
    private Usuario usuario;
    private String numeroCuenta;
    private int saldo;

    public Cliente(String nombre, String apellido, String dni, String direccion,
                   String telefono, String email, String numeroCuenta, int saldo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
    }

    // Método para crear usuario asociado al cliente
    public void crearUsuario(String nombreUsuario, String password) {
        this.usuario = new Usuario(nombreUsuario, password, this);
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDni() {
        return dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", numeroCuenta='" + numeroCuenta + '\'' +
                '}';
    }
}