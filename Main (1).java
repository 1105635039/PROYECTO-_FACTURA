package Facturita;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Scanner;

/*
 * Facilito no sierto
 *  @Autores: Sebastian Orellana, Emilio Galvez, Jostin Vasuqez,Gyna Yupangui.
 */
public class Main {
    private static float IVA = 0.15f;

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        // Producto Creatina1 = new Producto("Codigo producto", "Nombre producto", <<Precio productof(float)>>, <<stock>>);
        Producto Creatina1 = new Producto("DP001", "Dragon Pharma", 30.00f, 100);
        Producto Creatina2 = new Producto("GS002", "Sebas Strong  ", 25.00f, 100);
        Producto Creatina3 = new Producto("PP003", "Pepe's Muscle", 40.00f, 100);
        Producto Creatina4 = new Producto("JS004", "Java's ++    ", 35.00f, 100);
        Producto Creatina5 = new Producto("ML005", "Mamalong     ", 27.00f, 100);

        Receptor receptor = ingresarDatosReceptor(sc);
        realizarCompra(sc, Creatina1, Creatina2, Creatina3, Creatina4, Creatina5, receptor);
        sc.close();
    }

    private static void realizarCompra(Scanner sc, Producto p1, Producto p2, Producto p3, Producto p4, Producto p5, Receptor receptor) {
        int cantidadin;
        float valorProducto, subtotal = 0;
        boolean continuarCompra = true;
        String respuesta, seleccCreatina;

        while (continuarCompra) {
            System.out.println("\n\n\t| --- Inventario Creatinas x Power --- |\n");
            System.out.println("| Codigo | Nombre          | Precio  | Stock |");
            System.out.println(p1.toString());
            System.out.println(p2.toString());
            System.out.println(p3.toString());
            System.out.println(p4.toString());
            System.out.println(p5.toString());
            System.out.print("\n| ~ Ingrese el codigo del producto: "); seleccCreatina = sc.nextLine().toUpperCase();

            Producto productoSeleccionado = null;
            switch (seleccCreatina) {
                case "DP001": productoSeleccionado = p1; break;
                case "GS002": productoSeleccionado = p2; break;
                case "PP003": productoSeleccionado = p3; break;
                case "JS004": productoSeleccionado = p4; break;
                case "ML005": productoSeleccionado = p5; break;
                default:
                    System.out.println("| ~ Ingrese correctamente el codigo del producto");
                    break;
            }
            System.out.println("| ~ Producto seleccionado: " + productoSeleccionado.getNombre());
            System.out.print("| ~ Que cantidad desea comprar?: ");
            cantidadin = sc.nextInt();

            if (productoSeleccionado.vender(cantidadin)) {
                valorProducto = productoSeleccionado.total(cantidadin);
                subtotal += valorProducto;
                System.out.println("\n\t| --- Producto " + productoSeleccionado.getNombre() + " --- |");
                System.out.println("| ~ Valor unitario del producto: $" + productoSeleccionado.getPrecio());
                System.out.println("| ~ Valor a pagar por " + cantidadin + " unidades de "+ productoSeleccionado.getNombre() + ": $" + valorProducto);
            } else {
                System.out.println("| ~ No hay stock para la cantidad ingresada.");
            }
            System.out.println("| ~ Stock disponible: " + productoSeleccionado.getCantidad());

            System.out.print("\n| ~ Desea agregar otro producto? (si/no): "); sc.nextLine();
            respuesta = sc.nextLine().toLowerCase();

            if (respuesta.equals("no") || respuesta.equals("n") || respuesta.equals("0")) {
                continuarCompra = false;
            }
            mostrarTotalCompra(subtotal, receptor);
        }
        
    }

    private static Receptor ingresarDatosReceptor(Scanner sc) {
        String nombres, apellidos, provincia;
        String cedulaORuc = "",numCelular="",ultTres;
        boolean datosValidos = false;
        boolean numCelularValido = false;

        System.out.println("\n\t| --- Ingrese los datos del receptor --- |\n");
        System.out.print("| - Nombres: "); nombres = sc.nextLine();
        System.out.print("| - Apellidos: "); apellidos = sc.nextLine();

        while (!datosValidos) {
            System.out.print("| - Cedula o RUC: "); cedulaORuc = sc.nextLine().toLowerCase();
            
            if (cedulaORuc.length() == 10 && esNumerico(cedulaORuc)) {
                provincia = validarCedulaProvincia(cedulaORuc);
                if (provincia != null) {
                    System.out.println("| ~ Pertenece a la provincia de: "+provincia);
                    datosValidos = true;
                } else {
                    System.out.println("| ~ Ingrese codigo de provincia desde 01 a 24.");
                }
            } else if (cedulaORuc.length() == 13 && esNumerico(cedulaORuc)) {
                ultTres = cedulaORuc.substring(cedulaORuc.length()-3);
                provincia = validarCedulaProvincia(cedulaORuc);
                if (provincia != null) {
                    System.out.println("| ~ Pertenece a la provincia de: "+provincia);
                    datosValidos = true;
                } else {
                    System.out.println("| ~ Ingrese codigo de provincia desde 01 a 24.");
                }
                if (ultTres.equalsIgnoreCase("001")) {
                    datosValidos = true;
                } if (!datosValidos) {
                    System.out.println("| ~ El RUC termina en 001.");
                }
            } else {
                System.out
                        .println("| ~ La cedula ingresado debe tener 10 digitos o el RUC ingresado debe tener 13 digitos.");
            }
        }
        
        while (!numCelularValido) {
            System.out.print("| - Celular: ");
            numCelular = sc.nextLine();
    
            if (numCelular.length() == 10 && validadorCelular(numCelular)) {
                numCelularValido = true;
            } else {
                System.out.println("| ~ El celular ingresado debe tener 10 digitos y empezar por 09.");
            }
        }
        LocalDate diaCompra = LocalDate.now(); 
        return new Receptor(nombres, apellidos, cedulaORuc, numCelular, diaCompra);
    }

    private static boolean esNumerico(String cadena) {
        try {
            Long.parseLong(cadena);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void mostrarTotalCompra(float subtotal, Receptor receptor) {
        DecimalFormat df = new DecimalFormat();
        float valorIva = subtotal * IVA;
        float totalConIVA = subtotal + valorIva;
        
        System.out.println("\n\t| --- Factura a entregar al receptor --- |"+receptor);
        System.out.println("\t| ~~ Valores a pagar ~~ |");
        System.out.println("| ~ SUBTOTAL: $" + subtotal +
                "\n| ~ IVA (" +df.format(IVA * 100f)+"%): $" + valorIva +
                "\n| ~ TOTAL A PAGAR: $" +totalConIVA);
    }
    private static String validarCedulaProvincia(String cedulaORuc){
        if (cedulaORuc.length() >= 10) {
            String codigoProvincia = cedulaORuc.substring(0, 2);
            switch (codigoProvincia) {
                case "01": return "Azuay";
                case "02": return "Bolivar";
                case "03": return "Cañar";
                case "04": return "Carchi";
                case "05": return "Cotopaxi";
                case "06": return "Chimborazo";
                case "07": return "El Oro";
                case "08": return "Esmeraldas";
                case "09": return "Guayas";
                case "10": return "Imbabura";
                case "11": return "Loja";
                case "12": return "Los Rios";
                case "13": return "Manabi";
                case "14": return "Morona Santiago";
                case "15": return "Napo";
                case "16": return "Pastaza";
                case "17": return "Pichincha";
                case "18": return "Tungurahua";
                case "19": return "Zamora Chinchipe";
                case "20": return "Galapagos";
                case "21": return "Sucumbios";
                case "22": return "Orellana";
                case "23": return "Santo Domingo de los Tsachilas";
                case "24": return "Santa Elena";
                default: return null;
            }
        } else {
            return null;
        }
    }

    public static boolean validadorCelular(String numCelular) {
        return esValido(numCelular); 
    }

    public static boolean esValido(String numero) {
        if (numero.length() != 10) {
            return false;
        }
        if (!numero.startsWith("09")) {
            return false;
        }
        
        return true;
    }
}
