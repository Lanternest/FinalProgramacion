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

    public static void main(String[] args)
    {
        PantallaBienvenida();
    }

    public static void PantallaBienvenida()
    {
        //Variables
        final String usuario="lomuchacho";
        final String pass="7218";

        int saldo=1000000;
//        Scanner miTeclado= new Scanner(System.in);
        Scanner miTecladoS=new Scanner(System.in);

        //Codigo
        System.out.println(color.verde+"Bienvenido al Homebanking");

        System.out.println(color.amarillo+"Ingrese su usuario"+color.reset);
        String iUsuario=miTecladoS.nextLine();
        iUsuario=iUsuario.toLowerCase();

        System.out.println(color.amarillo+"Ingrese su password"+color.reset);
        String iPass=miTecladoS.nextLine();

        ValidarDatos(iUsuario, usuario, iPass, pass);
        MenuPrincipal(saldo);
    }

    public static void ValidarDatos(String iUsuario, String usuario,String iPass, String pass)
    {
        //Variables que necesitamos dentro de esta función
        int intFallidos=0;
        int intRestantes=5;
        Scanner miTeclado= new Scanner(System.in);
        Scanner miTecladoS=new Scanner(System.in);
        //Código
        while(!iUsuario.equals(usuario) || !iPass.equals(pass))
        {
            System.out.println(color.rojo+"Usuario o contraseña incorrectos");
            intFallidos++;
            intRestantes--;
            if(intFallidos<5)
            {
                System.out.println("Número de intentos fallidos: "+intFallidos+". Le quedan "+intRestantes+" intentos restantes."+color.reset);
                System.out.println("Intente de nuevo");

                System.out.println(color.amarillo+"Ingrese su usuario "+color.reset);
                iUsuario=miTecladoS.nextLine();
                iUsuario=iUsuario.toLowerCase();

                System.out.println(color.amarillo+"Ingrese su password "+color.reset);
                iPass=miTecladoS.nextLine();
            }
            else if(intFallidos==5)
            {
                System.out.println("Su cuenta a sido bloqueada. Comuníquese con un asesor para blanqueo de PIN.");
                System.out.println("Saliendo del sistema."+color.reset);
                esperar();
                esperar();
                PantallaBienvenida();
            }
        }
    }

    public static void MenuPrincipal(int saldo)
    {
        final String usuario="grupo31";
        int extraccion=0;
        int montoServicio=0;
        Scanner miTeclado= new Scanner(System.in);


        System.out.println(color.verde+"\nMenú Principal:");
        System.out.println("1) Datos del usuario");
        System.out.println("2) Consultar saldo");
        System.out.println("3) Realizar una extracción");
        System.out.println("4) Transferencia");
        System.out.println("5) Pago online"+color.reset);
        System.out.print(color.amarillo+"Seleccione una opción: "+color.reset);

        try
        {
            int opc;
            opc=miTeclado.nextInt();

            switch (opc)
            {
                case 1:
                    System.out.println("\nDatos de usuario:"+"\nCaja de ahorro Número de cuenta: 58246789554778552"+ "\nTitular de la cuenta: Grupo31");
                    volverAlMenuOpciones(saldo);
                    break;
                case 2:
                    mostrarSaldo(saldo);
                    break;
                case 3:
                    saldo=extraccionDinero(saldo);
                    break;
                case 4:
                    saldo=Transferencia(saldo);
                    break;
                case 5:
                    saldo=PagoServicio(saldo);
                    break;
                default:
                    System.out.println(color.rojo+"Opción no válida. Inténtelo de nuevo."+color.reset);
                    MenuPrincipal(saldo);
                    break;
            }
        }
        catch (Exception e)
        {
            System.out.println(color.rojo+"Debe ingresar solo números"+color.reset);
            MenuPrincipal(saldo);
        }

    }

    public static int extraccionDinero (int saldo)
    {
        int montoRetiro=0;
        Scanner miTeclado= new Scanner(System.in);

        try
        {
            System.out.print(color.verde+"\nIngrese el monto a extraer: "+color.reset);
            montoRetiro=miTeclado.nextInt();

            if (saldo<montoRetiro)
            {
                System.out.println(color.rojo+"\nSu saldo es insuficiente para realizar la extracción" + color.reset);
                esperar();
                volverAlMenuOpciones(saldo);
            }
            else if (saldo>montoRetiro)
            {
                System.out.println(color.amarillo+"\nUsted realizó una extracción de: $"+montoRetiro + ". Su saldo disponible en pesos es de: $" + (saldo-montoRetiro)+color.reset);
                saldo=saldo-montoRetiro;
                FileWriter fw = new FileWriter("movimientos.txt", true);
                PrintWriter salida = new PrintWriter(fw);
                Date fecha = new Date();
                salida.println(fecha+" Extracción: $"+montoRetiro);
                salida.close();
                volverAlMenuOpciones(saldo);
                return saldo;
            }
            else if(saldo==montoRetiro)
            {
                System.out.println(color.amarillo+"\nUsted va a extraer la totalidad de su saldo");
                System.out.println("Usted realizó una extracción de: $"+montoRetiro+". Su saldo disponible en pesos es de: $"+(saldo-montoRetiro)+color.reset);
                saldo=saldo-montoRetiro;
                FileWriter fw = new FileWriter("movimientos.txt", true);
                PrintWriter salida = new PrintWriter(fw);
                Date fecha = new Date();
                salida.println(fecha+" Extracción: $"+montoRetiro);
                salida.close();
                volverAlMenuOpciones(saldo);
                return saldo;
            }
        }
        catch (Exception e)
        {
            System.out.println(color.rojo+"Debe ingresar solo números"+color.reset);
            extraccionDinero(saldo);
        }
        return 0;
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

    public static void mostrarSaldo(int saldo)
    {
        System.out.println("\nSaldo Disponible: $" + saldo);
        volverAlMenuOpciones(saldo);
    }

    public static void volverAlMenuOpciones(int saldo)
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
                MenuPrincipal(saldo);
            }
            else if (opc == 2)
            {
                System.out.println("Por favor, extraiga su tarjeta.");
                esperar();
                presentacionAlumnos();
                esperar();
                PantallaBienvenida();
            }
            else
            {
                System.out.println(color.rojo+"Opción no válida. Intente de nuevo"+color.rojo);
                volverAlMenuOpciones(saldo);
            }
        }
        catch (Exception e)
        {
            System.out.println(color.rojo+"Debe ingresar solo números"+color.reset);
            volverAlMenuOpciones(saldo);
        }
    }

    public static int PagoServicio (int saldo)
    {
        int montoServicio=0;
        String servicio = null;
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
                        volverAlMenuOpciones(saldo);
                    }
                    else if (saldo>montoServicio || saldo==montoServicio)
                    {
                        System.out.println(color.amarillo+"Usted realizó el pago de: "+servicio+ " por el valor de: $"+montoServicio+ ". Su saldo en pesos es de: $"+(saldo-montoServicio)+color.reset);
                        saldo=saldo-montoServicio;
                        FileWriter fw = new FileWriter("movimientos.txt", true);
                        PrintWriter salida = new PrintWriter(fw);
                        Date fecha = new Date();
                        salida.println(fecha+" Pago servicio "+servicio+": $" +montoServicio);
                        salida.close();
                        volverAlMenuOpciones(saldo);
                        return saldo;
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
                        volverAlMenuOpciones(saldo);
                    }
                    else if (saldo>montoServicio || saldo==montoServicio)
                    {
                        System.out.println(color.amarillo+"Usted realizó el pago de: "+servicio+ " por el valor de: $"+montoServicio+ ". Su saldo en pesos es de: $"+(saldo-montoServicio)+color.reset);
                        saldo=saldo-montoServicio;
                        FileWriter fw = new FileWriter("movimientos.txt", true);
                        PrintWriter salida = new PrintWriter(fw);
                        Date fecha = new Date();
                        salida.println(fecha+" Pago servicio "+servicio+": $" +montoServicio);
                        salida.close();
                        volverAlMenuOpciones(saldo);
                        return saldo;
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
                        volverAlMenuOpciones(saldo);
                    }
                    else if (saldo>montoServicio || saldo==montoServicio)
                    {
                        System.out.println(color.amarillo+"Usted realizó el pago de: "+servicio+ " por el valor de: $"+montoServicio+ ". Su saldo en pesos es de: $"+(saldo-montoServicio)+color.reset);
                        saldo=saldo-montoServicio;
                        FileWriter fw = new FileWriter("movimientos.txt", true);
                        PrintWriter salida = new PrintWriter(fw);
                        Date fecha = new Date();
                        salida.println(fecha+" Pago servicio "+servicio+": $" +montoServicio);
                        salida.close();
                        volverAlMenuOpciones(saldo);
                        return saldo;
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
                        volverAlMenuOpciones(saldo);
                    }
                    else if (saldo>montoServicio || saldo==montoServicio)
                    {
                        System.out.println(color.amarillo+"Usted realizó el pago de: "+servicio+ " por el valor de: $"+montoServicio+ ". Su saldo en pesos es de: $"+(saldo-montoServicio)+color.reset);
                        saldo=saldo-montoServicio;
                        FileWriter fw = new FileWriter("movimientos.txt", true);
                        PrintWriter salida = new PrintWriter(fw);
                        Date fecha = new Date();
                        salida.println(fecha+" Pago servicio "+servicio+": $" +montoServicio);
                        salida.close();
                        volverAlMenuOpciones(saldo);
                        return saldo;
                    }
                    break;
                default:
                    System.out.println(color.rojo+"Opción no válida. Intente de nuevo"+color.reset);
                    PagoServicio(saldo);
            }
        }
        catch (Exception e)
        {
            System.out.println(color.rojo+"Debe ingresar solo números"+color.reset);
            PagoServicio(saldo);
        }
        return 0;
    }

    public static int Transferencia (int saldo)
    {
        int transferencia=0;
        Scanner miTeclado= new Scanner(System.in);
        Scanner miTecladoS=new Scanner(System.in);
        String cuentaDestino;
        try
        {
            System.out.println(color.verde+"Ingrese alias o CBU/CVU de la cuenta a la que desea realizar la transferencia"+color.reset);
            cuentaDestino=miTecladoS.nextLine();

            System.out.print(color.verde+"Ingrese el monto que desea transferir: "+color.reset);
            transferencia=miTeclado.nextInt();

            if (saldo<transferencia)
            {
                System.out.println(color.rojo+"\nSu saldo es insuficiente para la transferencia que desea realizar" + color.reset);
                esperar();
                volverAlMenuOpciones(saldo);
                return saldo;
            }
            else if (saldo==transferencia)
            {
                System.out.println(color.amarillo+"\nUsted va a transferir todo su saldo disponible");
                System.out.println("Usted realizó una transferencia de pesos: $"+transferencia+ " a la cuenta: "+cuentaDestino+". Su saldo en pesos es de: $"+(saldo-transferencia)+color.reset);
                saldo=saldo-transferencia;
                FileWriter fw = new FileWriter("movimientos.txt", true);
                PrintWriter salida = new PrintWriter(fw);
                Date fecha = new Date();

                salida.println(fecha + " Transferencia: $"+transferencia+"A la cuenta  ALIAS/CBU"+cuentaDestino);					salida.close();
                volverAlMenuOpciones(saldo);
                return saldo;
            }
            else if(saldo>transferencia)
            {
                System.out.println(color.amarillo+"\nUsted realizó una transferencia de pesos: $"+transferencia+" a la cuenta: "+ cuentaDestino+". Su saldo en pesos es de: $"+(saldo-transferencia)+color.reset);
                saldo=saldo-transferencia;
                FileWriter fw = new FileWriter("movimientos.txt", true);
                PrintWriter salida = new PrintWriter(fw);
                Date fecha = new Date();
                salida.println(fecha+" Transferencia: $"+transferencia+" a la cuenta ALIAS/CBU: "+cuentaDestino);
                salida.close();
                volverAlMenuOpciones(saldo);
                return saldo;
            }
        }
        catch (Exception e)
        {
            System.out.println(color.rojo+"Debe ingresar solo números"+color.reset);
            Transferencia(saldo);
        }
        return 0;
    }

    public static void presentacionAlumnos()
    {
        System.out.println(color.violeta+"Proyecto Final de Programación I. Alumnos: ");
        System.out.println("Edgardo Arenas"+"\nDavid Adrover"+"\nLeandro Chiarello"+"\nAgustina Estrada"+"\nEmanuel Salvi"+color.reset);
    }

}