package Junho6.BridgeControle;

public class Televisao implements ControleRemoto {

    @Override
    public void ligar() {
        System.out.println("Televisao ligada!");
    }

    @Override
    public void desligar() {
        System.out.println("Televisao desligada!");
    }

    @Override
    public void aumentar() {
        System.out.println("Volume da televisao aumentado");
    }

    @Override
    public void diminuir() {
        System.out.println("Volume da televisao diminuido");
    }

}
