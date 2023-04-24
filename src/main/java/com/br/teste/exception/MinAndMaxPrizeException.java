package com.br.teste.exception;

public class MinAndMaxPrizeException extends DefaultApiException {

    public MinAndMaxPrizeException() {
        super("Ocorreu um erro ao gerar o retorno, não é possível gerar o menor e o maior intevalo de prêmios!");
    }
}
