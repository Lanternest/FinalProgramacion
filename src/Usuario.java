class Usuario {
    private String nombreUsuario;
    private String password;
    private Cliente cliente;
    private boolean bloqueado;
    private int intentosFallidos;
    private static final int MAX_INTENTOS = 5;

    public Usuario(String nombreUsuario, String password, Cliente cliente) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.cliente = cliente;
        this.bloqueado = false;
        this.intentosFallidos = 0;
    }

    public boolean validarCredenciales(String nombreUsuario, String password) {
        if (bloqueado) {
            return false;
        }

        if (this.nombreUsuario.equals(nombreUsuario) && this.password.equals(password)) {
            intentosFallidos = 0;
            return true;
        } else {
            intentosFallidos++;
            if (intentosFallidos >= MAX_INTENTOS) {
                bloqueado = true;
            }
            return false;
        }
    }

    public void desbloquear() {
        bloqueado = false;
        intentosFallidos = 0;
    }

    // Getters
    public String getNombreUsuario() { return nombreUsuario; }
    public Cliente getCliente() { return cliente; }
    public boolean estaBloqueado() { return bloqueado; }
    public int getIntentosFallidos() { return intentosFallidos; }
    public int getIntentosRestantes() { return MAX_INTENTOS - intentosFallidos; }
}