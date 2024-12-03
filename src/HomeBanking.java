import java.util.Scanner;
import java.util.Date;
import java.io.*;

class color
{
    final static String reset = "\u001B[0m";
    final static String rojo = "\u001B[31m";
    final static String amarillo = "\u001B[33m";
    final static String violeta="\u001B[35m";
    final static String verde="\u001B[32m";
}

public class HomeBanking {
    private static final GestorBancario gestor = new GestorBancario();
    private static final Scanner scanner = new Scanner(System.in);
    private static Cliente clienteActual = null;

    public static void main(String[] args){
        IniciarDatosEjemplo();
        PantallaBienvenida();
    }

    private static void IniciarDatosEjemplo() {
        // Registrar algunos clientes de ejemplo
        gestor.registrarCliente("Claudia", "Naveda", "12345678",
                "Calle 123", "261 555-1234",
                "cnaveda@email.com", generarNumeroCuenta(),
                1000000);
        gestor.crearUsuario("12345678", "cnaveda", "7218");
    }

    public static void PantallaBienvenida()
    {
        System.out.println(color.verde + "Bienvenido al Homebanking" + color.reset);

        System.out.println(color.verde + "\n1. Iniciar Sesión");
        System.out.println("2. Registrar Nuevo Cliente");
        System.out.println("3. Salir" + color.reset);
        System.out.print(color.amarillo + "\nSeleccione una opción: " + color.reset);

        try {
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea

            switch (opcion) {
                case 1:
                    IniciarSesion();
                    break;
                case 2:
                    RegistrarNuevoCliente();
                    break;
                case 3:
                    System.out.println("Gracias por usar nuestro servicio.");
                    esperar();
                    presentacionAlumnos();
                    esperar();
                    PantallaBienvenida();
                default:
                    System.out.println(color.rojo + "Opción no válida. Intente nuevamente." + color.reset);
                    PantallaBienvenida();
            }
        } catch (Exception e) {
            System.out.println(color.rojo + "Entrada inválida. Intente nuevamente." + color.reset);
            scanner.nextLine(); // Limpiar buffer
            PantallaBienvenida();
        }
    }

    public static void IniciarSesion() {
        System.out.println(color.amarillo + "Ingrese su usuario" + color.reset);
        String iUsuario = scanner.nextLine().toLowerCase();

        System.out.println(color.amarillo + "Ingrese su password" + color.reset);
        String iPass = scanner.nextLine();

        if (ValidarDatos(iUsuario, iPass)) {
            Usuario usuario = gestor.buscarUsuario(iUsuario);
            clienteActual = usuario.getCliente();
            MenuPrincipal();
        } else {
            PantallaBienvenida();
        }
    }

    public static boolean ValidarDatos(String iUsuario, String iPass) {
        if (gestor.validarCredenciales(iUsuario, iPass)) {
            return true;
        }

        Usuario usuario = gestor.buscarUsuario(iUsuario);
        if (usuario != null && usuario.estaBloqueado()) {
            System.out.println(color.rojo +"Su cuenta ha sido bloqueada. Comuníquese con un asesor para blanqueo de PIN." + color.reset);
            esperar();
            return false;
        }

        System.out.println(color.rojo + "Usuario o contraseña incorrectos" + color.reset);
        System.out.println("Intentos restantes: " + usuario.getIntentosRestantes());
        return false;
    }

    public static void RegistrarNuevoCliente() {
        System.out.println(color.verde + "Registro de nuevo cliente" + color.reset);

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        System.out.print("DNI: ");
        String dni = scanner.nextLine();

        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        String numeroCuenta = generarNumeroCuenta();

        if (gestor.registrarCliente(nombre, apellido, dni, direccion,
                telefono, email, numeroCuenta, 0)) {
            System.out.println("Cliente registrado correctamente");

            System.out.print("Nombre de usuario deseado: ");
            String nombreUsuario = scanner.nextLine();

            System.out.print("Contraseña: ");
            String password = scanner.nextLine();

            if (gestor.crearUsuario(dni, nombreUsuario, password)) {
                System.out.println("Usuario creado correctamente");
            } else {
                System.out.println("Error al crear usuario");
            }
        } else {
            System.out.println("Error al registrar cliente");
        }

        PantallaBienvenida();
    }

    private static String generarNumeroCuenta() {
        return "58" + System.currentTimeMillis();
    }

//    public static void ValidarDatos(String iUsuario, String usuario,String iPass, String pass)
//    {
//        //Código
//        while(!iUsuario.equals(usuario) || !iPass.equals(pass))
//        {
//            System.out.println(color.rojo+"Usuario o contraseña incorrectos");
//            intFallidos++;
//            intRestantes--;
//            if(intFallidos<5)
//            {
//                System.out.println("Número de intentos fallidos: "+intFallidos+". Le quedan "+intRestantes+" intentos restantes."+color.reset);
//                System.out.println("Intente de nuevo");
//
//                System.out.println(color.amarillo+"Ingrese su usuario "+color.reset);
//                iUsuario=miTecladoS.nextLine();
//                iUsuario=iUsuario.toLowerCase();
//
//                System.out.println(color.amarillo+"Ingrese su password "+color.reset);
//                iPass=miTecladoS.nextLine();
//            }
//            else if(intFallidos==5)
//            {
//                System.out.println("Su cuenta a sido bloqueada. Comuníquese con un asesor para blanqueo de PIN.");
//                System.out.println("Saliendo del sistema."+color.reset);
//                esperar();
//                esperar();
//                PantallaBienvenida();
//            }
//        }
//    }

    public static void MenuPrincipal()
    {
        System.out.println(color.verde + "\nBienvenid@ " + clienteActual.getNombre());
        Scanner miTeclado= new Scanner(System.in);

        System.out.println("\nMenú Principal:");
        System.out.println("1) Datos del usuario");
        System.out.println("2) Consultar saldo");
        System.out.println("3) Realizar una extracción");
        System.out.println("4) Transferencia");
        System.out.println("5) Pago online");
        System.out.println("6) Cerrar Sesión"+color.reset);
        System.out.print(color.amarillo+"Seleccione una opción: "+color.reset);

        try
        {
            int opc;
            opc=miTeclado.nextInt();

            switch (opc)
            {
                case 1:
                    System.out.println("\nDatos de usuario:"+"\nCaja de ahorro Número de cuenta: " + clienteActual.getNumeroCuenta() + "\nTitular de la cuenta: " + clienteActual.getNombre()+ " "+ clienteActual.getApellido());
                    volverAlMenuOpciones();
                    break;
                case 2:
                    mostrarSaldo();
                    break;
                case 3:
                    extraccionDinero();
                    break;
                case 4:
                    Transferencia();
                    break;
                case 5:
                    PagoServicio();
                    break;
                case 6:
                    System.out.println("Gracias por usar nuestro servicio.");
                    esperar();
                    presentacionAlumnos();
                    esperar();
                    PantallaBienvenida();
                default:
                    System.out.println(color.rojo+"Opción no válida. Inténtelo de nuevo."+color.reset);
                    MenuPrincipal();
                    break;
            }
        }
        catch (Exception e)
        {
            System.out.println(color.rojo+"Debe ingresar solo números"+color.reset);
            MenuPrincipal();
        }

    }

    public static void extraccionDinero ()
    {
        int saldo = clienteActual.getSaldo();
        int montoRetiro;
        Scanner miTeclado= new Scanner(System.in);

        try
        {
            System.out.print(color.verde+"\nIngrese el monto a extraer: "+color.reset);
            montoRetiro=miTeclado.nextInt();

            if (saldo<montoRetiro)
            {
                System.out.println(color.rojo+"\nSu saldo es insuficiente para realizar la extracción" + color.reset);
                esperar();
                volverAlMenuOpciones();
            }
            else if (saldo>montoRetiro)
            {
                clienteActual.setSaldo(saldo-montoRetiro);
                System.out.println(color.amarillo+"\nUsted realizó una extracción de: $"+montoRetiro + ". Su saldo disponible en pesos es de: $" + clienteActual.getSaldo()+color.reset);
                FileWriter fw = new FileWriter("movimientos.txt", true);
                PrintWriter salida = new PrintWriter(fw);
                Date fecha = new Date();
                salida.println(fecha+" Extracción: $"+montoRetiro);
                salida.close();
                volverAlMenuOpciones();
            }
            else if(saldo==montoRetiro)
            {
                System.out.println(color.amarillo+"\nUsted va a extraer la totalidad de su saldo");
                clienteActual.setSaldo(saldo - montoRetiro);
                System.out.println("Usted realizó una extracción de: $"+montoRetiro+". Su saldo disponible en pesos es de: $"+clienteActual.getSaldo()+color.reset);
                FileWriter fw = new FileWriter("movimientos.txt", true);
                PrintWriter salida = new PrintWriter(fw);
                Date fecha = new Date();
                salida.println(fecha+" Extracción: $"+montoRetiro);
                salida.close();
                volverAlMenuOpciones();
            }
        }
        catch (Exception e)
        {
            System.out.println(color.rojo+"Debe ingresar solo números"+color.reset);
            extraccionDinero();
        }
    }

    public static void esperar()
    {
        try
        {
            Thread.sleep(5000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public static void mostrarSaldo()
    {
        System.out.println("\nSaldo Disponible: $" + clienteActual.getSaldo());
        volverAlMenuOpciones();
    }

    public static void volverAlMenuOpciones()
    {
        Scanner miTeclado=new Scanner(System.in);
        System.out.println(color.verde+"\n1. Volver al Menú Principal");
        System.out.println("2. Cerrar Sesión");
        System.out.print(color.amarillo+"Seleccione una opción: "+color.reset);

        try
        {
            int opc = miTeclado.nextInt();

            if (opc == 1)
            {
                MenuPrincipal();
            }
            else if (opc == 2)
            {
                System.out.println("Gracias por usar nuestro servicio.");
                esperar();
                presentacionAlumnos();
                esperar();
                PantallaBienvenida();
            }
            else
            {
                System.out.println(color.rojo+"Opción no válida. Intente de nuevo"+color.rojo);
                volverAlMenuOpciones();
            }
        }
        catch (Exception e)
        {
            System.out.println(color.rojo+"Debe ingresar solo números"+color.reset);
            volverAlMenuOpciones();
        }
    }

    public static void PagoServicio ()
    {
        int saldo = clienteActual.getSaldo();
        int montoServicio;
        String servicio;
        Scanner miTeclado= new Scanner(System.in);
        Scanner miTecladoS= new Scanner(System.in);

        System.out.println(color.verde+"\n1-Agua");
        System.out.println("2-Luz");
        System.out.println("3-Gas");
        System.out.println("4-Telefonia");
        System.out.print(color.amarillo+"Seleccionar servicio: "+color.reset);

        try
        {
            int opc;
            opc=miTeclado.nextInt();
            switch (opc)
            {
                case 1:
                    System.out.println(color.verde+"\nIngrese nombre de proveedor de AGUA:"+color.reset);
                    servicio=miTecladoS.nextLine();
                    System.out.print(color.verde+"Ingrese el monto a pagar: "+color.reset);
                    montoServicio=miTeclado.nextInt();

                    if (saldo<montoServicio)
                    {
                        System.out.println(color.rojo+"Su saldo es insuficiente para realizar el pago que desea realizar" + color.reset);
                        volverAlMenuOpciones();
                    }
                    else if (saldo>montoServicio || saldo==montoServicio)
                    {
                        clienteActual.setSaldo(saldo-montoServicio);
                        System.out.println(color.amarillo+"Usted realizó el pago de: "+servicio+ " por el valor de: $"+montoServicio+ ". Su saldo en pesos es de: $"+clienteActual.getSaldo()+color.reset);
                        FileWriter fw = new FileWriter("movimientos.txt", true);
                        PrintWriter salida = new PrintWriter(fw);
                        Date fecha = new Date();
                        salida.println(fecha+" Pago servicio "+servicio+": $" +montoServicio);
                        salida.close();
                        volverAlMenuOpciones();
                    }
                    break;
                case 2:
                    System.out.println(color.verde+"\nIngrese nombre de proveedor de LUZ:"+color.reset);
                    servicio=miTecladoS.nextLine();
                    System.out.print(color.verde+"Ingrese el monto a pagar: "+color.reset);
                    montoServicio=miTeclado.nextInt();

                    if (saldo<montoServicio)
                    {
                        System.out.println(color.rojo+"Su saldo es insuficiente para realizar el pago que desea realizar" + color.reset);
                        volverAlMenuOpciones();
                    }
                    else if (saldo>montoServicio || saldo==montoServicio)
                    {
                        clienteActual.setSaldo(saldo-montoServicio);
                        System.out.println(color.amarillo+"Usted realizó el pago de: "+servicio+ " por el valor de: $"+montoServicio+ ". Su saldo en pesos es de: $"+clienteActual.getSaldo()+color.reset);
                        FileWriter fw = new FileWriter("movimientos.txt", true);
                        PrintWriter salida = new PrintWriter(fw);
                        Date fecha = new Date();
                        salida.println(fecha+" Pago servicio "+servicio+": $" +montoServicio);
                        salida.close();
                        volverAlMenuOpciones();
                    }
                    break;
                case 3:
                    System.out.println(color.verde+"\nIngrese nombre de proveedor de GAS:"+color.reset);
                    servicio=miTecladoS.nextLine();
                    System.out.print(color.verde+"Ingrese el monto a pagar: "+color.reset);
                    montoServicio=miTeclado.nextInt();

                    if (saldo<montoServicio)
                    {
                        System.out.println(color.rojo+"Su saldo es insuficiente para realizar el pago que desea realizar" + color.reset);
                        volverAlMenuOpciones();
                    }
                    else if (saldo>montoServicio || saldo==montoServicio)
                    {
                        clienteActual.setSaldo(saldo-montoServicio);
                        System.out.println(color.amarillo+"Usted realizó el pago de: "+servicio+ " por el valor de: $"+montoServicio+ ". Su saldo en pesos es de: $"+clienteActual.getSaldo()+color.reset);
                        FileWriter fw = new FileWriter("movimientos.txt", true);
                        PrintWriter salida = new PrintWriter(fw);
                        Date fecha = new Date();
                        salida.println(fecha+" Pago servicio "+servicio+": $" +montoServicio);
                        salida.close();
                        volverAlMenuOpciones();
                    }
                    break;
                case 4:
                    System.out.println(color.verde+"\nIngrese nombre de proveedor de TELEFONIA"+color.reset);
                    servicio=miTecladoS.nextLine();
                    System.out.print(color.verde+"Ingrese el monto a pagar: "+color.reset);
                    montoServicio=miTeclado.nextInt();

                    if (saldo<montoServicio)
                    {
                        System.out.println(color.rojo+"Su saldo es insuficiente para realizar el pago que desea realizar"+color.reset);
                        volverAlMenuOpciones();
                    }
                    else if (saldo>montoServicio || saldo==montoServicio)
                    {
                        clienteActual.setSaldo(saldo-montoServicio);
                        System.out.println(color.amarillo+"Usted realizó el pago de: "+servicio+ " por el valor de: $"+montoServicio+ ". Su saldo en pesos es de: $"+clienteActual.getSaldo()+color.reset);
                        FileWriter fw = new FileWriter("movimientos.txt", true);
                        PrintWriter salida = new PrintWriter(fw);
                        Date fecha = new Date();
                        salida.println(fecha+" Pago servicio "+servicio+": $" +montoServicio);
                        salida.close();
                        volverAlMenuOpciones();
                    }
                    break;
                default:
                    System.out.println(color.rojo+"Opción no válida. Intente de nuevo"+color.reset);
                    PagoServicio();
            }
        }
        catch (Exception e)
        {
            System.out.println(color.rojo+"Debe ingresar solo números"+color.reset);
            PagoServicio();
        }
    }

    public static void Transferencia ()
    {
        int saldo = clienteActual.getSaldo();
        int transferencia;
        Scanner miTeclado= new Scanner(System.in);
        Scanner miTecladoS=new Scanner(System.in);
        String cuentaDestino = null;
        try
        {
            System.out.println(color.verde+"Ingrese alias o CBU/CVU de la cuenta a la que desea realizar la transferencia"+color.reset);
//            clientes.get=miTecladoS.nextLine();

            System.out.print(color.verde+"Ingrese el monto que desea transferir: "+color.reset);
            transferencia=miTeclado.nextInt();

            if (saldo<transferencia)
            {
                System.out.println(color.rojo+"\nSu saldo es insuficiente para la transferencia que desea realizar" + color.reset);
                esperar();
                volverAlMenuOpciones();
            }
            else if (saldo==transferencia)
            {
                clienteActual.setSaldo(saldo-transferencia);
                System.out.println(color.amarillo+"\nUsted va a transferir todo su saldo disponible");
                System.out.println("Usted realizó una transferencia de pesos: $"+transferencia+ " a la cuenta: "+cuentaDestino+". Su saldo en pesos es de: $"+clienteActual.getSaldo()+color.reset);
                FileWriter fw = new FileWriter("movimientos.txt", true);
                PrintWriter salida = new PrintWriter(fw);
                Date fecha = new Date();

                salida.println(fecha + " Transferencia: $"+transferencia+"A la cuenta  ALIAS/CBU"+cuentaDestino);					salida.close();
                volverAlMenuOpciones();
            }
            else if(saldo>transferencia)
            {
                clienteActual.setSaldo(saldo-transferencia);
                System.out.println(color.amarillo+"\nUsted realizó una transferencia de pesos: $"+transferencia+" a la cuenta: "+ cuentaDestino+". Su saldo en pesos es de: $"+clienteActual.getSaldo()+color.reset);
                FileWriter fw = new FileWriter("movimientos.txt", true);
                PrintWriter salida = new PrintWriter(fw);
                Date fecha = new Date();
                salida.println(fecha+" Transferencia: $"+transferencia+" a la cuenta ALIAS/CBU: "+cuentaDestino);
                salida.close();
                volverAlMenuOpciones();
            }
        }
        catch (Exception e)
        {
            System.out.println(color.rojo+"Debe ingresar solo números"+color.reset);
            Transferencia();
        }
    }

    public static void presentacionAlumnos()
    {
        System.out.println(color.violeta+"Proyecto Final de Programación I. Alumnos: ");
        System.out.println("Edgardo Arenas"+"\nDavid Adrover"+"\nLeandro Chiarello"+"\nAgustina Estrada"+"\nEmanuel Salvi"+color.reset);
    }
}