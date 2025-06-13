package Junho13.Facade;

public class CarroFacade {
    CarroModelo modelo;
    CarroMotor motor;
    CarroMarca marca;
    CarroCor cor;

    public CarroFacade() {
        modelo = new CarroModelo();
        motor = new CarroMotor();
        marca = new CarroMarca();
        cor = new CarroCor();
    }

    public void construirCarroCompleto(String modelo, String marca, String motor, String cor) {
        System.out.println("----- Contruindo carro completo -------");
        this.modelo.setModelo(modelo);
        this.motor.setMotor(motor);
        this.marca.setMarca(marca);
        this.cor.setCor(cor);
        System.out.println("Carro de modelo " + modelo + ", marcar " + marca + ", motor " + motor + " e cor " + cor
                + "construindo com sucesso.");
    }

    public class Main {
        public static void main(String[] args) {
            CarroFacade facade = new CarroFacade();
            facade.construirCarroCompleto("Gol", "VW", "1.0", "Branco");
        }
    }

}
