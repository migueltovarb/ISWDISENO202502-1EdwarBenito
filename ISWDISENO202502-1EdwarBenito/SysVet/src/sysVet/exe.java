package sysVet;

import java.util.*;

public class exe {

    public static void main(String[] args) {
        List<Owner> owners = new ArrayList<>();
        List<Pet> pets = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int opt;

        while (true) {
            System.out.println("============================================");
            System.out.println("1. Registrar Dueño");
            System.out.println("2. Registrar Mascotas asociadas a un dueño");
            System.out.println("3. Consultar Historial Veterinario");
            System.out.println("4. Generar resumen por mascota");
            System.out.println("5. Salir");
            System.out.print("Opción: ");

            if (!sc.hasNextInt()) {
                System.out.println("⚠️ Entrada inválida. Ingrese un número del 1 al 5.");
                sc.nextLine();
                continue;
            }

            opt = sc.nextInt();
            sc.nextLine();

            switch (opt) {
                case 1:
                    String name = leerTextoNoVacio(sc, "Ingrese el nombre del dueño: ");
                    String id = leerTextoNoVacio(sc, "Ingrese el documento de identidad: ");
                    String contact = leerTextoNoVacio(sc, "Ingrese el número de teléfono: ");
                    owners.add(new Owner(name, id, contact));
                    System.out.println("Dueño registrado exitosamente.");
                    break;

                case 2:
                    if (owners.isEmpty()) {
                        System.out.println("No hay dueños registrados.");
                        break;
                    }

                    String pname = leerTextoNoVacio(sc, "Ingrese el nombre de la mascota: ");
                    String spc = leerTextoNoVacio(sc, "Ingrese la especie: ");

                    int age;
                    try {
                        System.out.print("Ingrese la edad de la mascota: ");
                        age = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Edad inválida.");
                        break;
                    }

                    System.out.println("Dueños registrados:");
                    for (int i = 0; i < owners.size(); i++) {
                        System.out.println(i + ". " + owners.get(i).toString());
                    }

                    int ownerIndex = leerIndice(sc, owners.size(), "Seleccione el dueño por índice: ");
                    if (ownerIndex == -1) break;

                    List<Control> controls = new ArrayList<>();
                    System.out.println("Registro de control para la mascota: " + pname);

                    String date = leerTextoNoVacio(sc, "Ingrese la fecha del control: ");
                    String obs = leerTextoNoVacio(sc, "Ingrese la observación: ");
                    Control control = new Control(date, obs);

                    System.out.println("Seleccione el tipo de control:");
                    System.out.println("1. Vacuna");
                    System.out.println("2. Desparasitación");
                    System.out.println("3. Chequeo");
                    System.out.print("Opción: ");
                    int type;
                    try {
                        type = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Tipo inválido.");
                        break;
                    }

                    switch (type) {
                        case 1:
                            control.setControlType(TypeControl.VACCINE);
                            break;
                        case 2:
                            control.setControlType(TypeControl.DEWORMING);
                            break;
                        case 3:
                            control.setControlType(TypeControl.CHECK);
                            break;
                        default:
                            System.out.println("Tipo de control no reconocido.");
                            break;
                    }

                    controls.add(control);
                    Pet newPet = new Pet(pname, spc, age, owners.get(ownerIndex), controls);
                    pets.add(newPet);
                    System.out.println("Mascota registrada exitosamente.");
                    break;

                case 3:
                    if (pets.isEmpty()) {
                        System.out.println("No hay mascotas registradas.");
                        break;
                    }

                    mostrarMascotas(pets);
                    int petIndex = leerIndice(sc, pets.size(), "Seleccione la mascota por índice: ");
                    if (petIndex == -1) break;

                    System.out.println(pets.get(petIndex).getControlSummary());
                    break;

                case 4:
                    if (pets.isEmpty()) {
                        System.out.println("No hay mascotas registradas.");
                        break;
                    }

                    mostrarMascotas(pets);
                    petIndex = leerIndice(sc, pets.size(), "Seleccione la mascota por índice: ");
                    if (petIndex == -1) break;

                    System.out.println(pets.get(petIndex).toString());
                    System.out.println(pets.get(petIndex).getControlSummary());
                    break;

                case 5:
                    System.out.println("Saliendo del sistema. ¡Gracias por usar sysVet!");
                    sc.close();
                    return;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    private static String leerTextoNoVacio(Scanner sc, String mensaje) {
        String entrada;
        while (true) {
            System.out.print(mensaje);
            entrada = sc.nextLine().trim();
            if (!entrada.isEmpty()) {
                return entrada;
            }
            System.out.println("Este campo no puede estar vacío.");
        }
    }

    private static int leerIndice(Scanner sc, int max, String mensaje) {
        int index;
        System.out.print(mensaje);
        try {
            index = Integer.parseInt(sc.nextLine());
            if (index < 0 || index >= max) {
                System.out.println("Índice fuera de rango.");
                return -1;
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return -1;
        }
        return index;
    }
    private static void mostrarMascotas(List<Pet> pets) {
        System.out.println("Mascotas registradas:");
        for (int i = 0; i < pets.size(); i++) {
            System.out.println(i + ". " + pets.get(i).toString());
        }
    }
}

