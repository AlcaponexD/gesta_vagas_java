package br.pedroso.jeison.gestao_vagas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class PrimeiroTeste {

    @Test
    public void can_possible_sum_two_numbers() {
        int result = calculate(2, 3);
        assertEquals(result, 5);
    }

    @Test
    public void can_possible_sum_two_numbers_incorrect() {
        int result = calculate(2, 3);
        assertNotEquals(result, 4);
    }

    public static int calculate(int num1, int num2) {
        return num1 + num2;
    }
}
