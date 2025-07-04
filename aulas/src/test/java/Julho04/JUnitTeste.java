package Julho04;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JUnitTeste {
    Operacao op = new Operacao();

    @Test
    public void testAdicao() {
        assertEquals(4.0, op.adicao(2, 2));
        assertEquals(3.0, op.adicao(1, 1));
        assertEquals(6.0, op.adicao(2, 4));

    }

    @Test
    public void testSubtracao() {
        assertEquals(1.0, op.subtracao(2, 1));
        assertEquals(1.0, op.subtracao(3, 2));
        assertEquals(-2.0, op.subtracao(2, 4));
    }

    @Test
    public void testMultiplicacao() {
        assertEquals(2.0, op.multiplicacao(2, 1));
        assertEquals(6.0, op.multiplicacao(3, 2));
        assertEquals(8.0, op.multiplicacao(2, 4));

    }

    @Test
    public void testDivisao() {
        assertEquals(2, op.divisao(4, 2));
        assertEquals(3.0, op.divisao(6, 2));
        assertEquals(5, op.divisao(12, 6));
    }
}